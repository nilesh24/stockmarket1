package com.jpmorgan.stocks.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jpmorgan.stocks.dao.StockDao;
import com.jpmorgan.stocks.model.Trade;
import com.jpmorgan.stocks.model.Stock;
import com.jpmorgan.stocks.model.StockType;
import com.jpmorgan.stocks.service.StockService;
import com.jpmorgan.stocks.util.StockUtility;

/**
 * This class provides the five operations to calculate the dividend yield, P/E
 * Ratio, Stock Price, GBCE All Share Index and record trades for a given stock.
 * 
 * @author Nilesh Mehta
 *
 */
@Service
public class StockServiceImpl implements StockService {

	/**
	 * Stores StockDao object
	 */
	StockDao stockDao;

	/**
	 * Logger object
	 */
	Logger logger = Logger.getLogger(StockServiceImpl.class);

	/**
	 * Getter method for StockDao
	 * 
	 * @return stockDao
	 */
	public StockDao getStockDao() {
		return stockDao;
	}

	/**
	 * Setter method for stockDao
	 * 
	 * @param stockDao
	 */
	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	/**
	 * This calculates Dividend yield
	 * 
	 * @param stock
	 *            symbol
	 * @return double
	 */
	@Override
	public double calculateDividendYield(String stockSymbol) throws Exception {

		/**
		 * Variable used for input price, stock type,last dividend,fixed
		 * dividend and parValue
		 */
		double dividendYield = -1.0;
		double inputPrize;
		StockType stockType;
		double lastDividend;
		double fixedDividend;
		double parValue;

		try {
			logger.debug("Calculating Dividend Yield for the stock symbol: " + stockSymbol);
			Stock stock = stockDao.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if (stock == null) {
				throw new Exception("The stock symbol [" + stockSymbol + "] is not supported.");
			}

			// input price with value zero does not make any sense and could
			// produce a zero division
			if (stock.getInputPrice() <= 0.0) {
				throw new Exception(
						"The ticker price for the stock [" + stockSymbol + "] should be greater than zero (0).");
			}

			inputPrize = stock.getInputPrice();
			stockType = stock.getStockType();

			if (inputPrize > 0.0) {
				if (stockType == StockType.COMMON) {
					lastDividend = stock.getLastDividend();

					dividendYield = StockUtility.getDividendYieldForCommon(lastDividend, inputPrize);
				} else {
					// PREFERRED stock type
					fixedDividend = stock.getFixedDividend();
					parValue = stock.getParValue();

					dividendYield = StockUtility.getDividendYieldForPreferred(fixedDividend, inputPrize, parValue);
				}

			}

			logger.debug("In Logger Dividend Yield calculated: " + dividendYield);

		} catch (Exception exception) {
			logger.error("Error calculating Dividend Yield for the stock symbol: " + stockSymbol + ".", exception);
			throw new Exception("Error calculating Dividend Yield for the stock symbol: " + stockSymbol + ".",
					exception);
		}
		return dividendYield;
	}

	/**
	 * This method used to calculate PR Ratio
	 * 
	 * @param stock
	 *            symbol
	 * @return double - value of PE ratio
	 */
	@Override
	public double calculatePERatio(String stockSymbol) throws Exception {
		double peRatio = -1.0;
		double dividendYield = -1.0;

		try {
			logger.debug("Calculating P/E Ratio for the stock symbol: " + stockSymbol);
			Stock stock = stockDao.getStockBySymbol(stockSymbol);
			double inputPrize = stock.getInputPrice();

			// If the stock is not supported the a exception is raised
			if (stock == null) {
				throw new Exception("The stock symbol [" + stockSymbol + "] is not supported");
			}

			// Input price with value zero does not make any sense and could
			// produce a zero division
			if (stock.getInputPrice() <= 0.0) {
				throw new Exception(
						"The ticker price for the stock [" + stockSymbol + "] should be greater than zero (0).");
			}

			else if (inputPrize > 0.0) {
				dividendYield = calculateDividendYield(stockSymbol);
				peRatio = StockUtility.getPeRatio(inputPrize, dividendYield);

				logger.debug(" P/E Ratiocalculated: " + peRatio);
			}

		} catch (Exception exception) {
			logger.error("Error calculating P/E Ratio for the stock symbol: " + stockSymbol + ".", exception);
			throw new Exception("Error calculating P/E Ratio for the stock symbol: " + stockSymbol + ".", exception);
		}
		return peRatio;
	}

	/**
	 * This method help to record the trades
	 */
	@Override
	public boolean recordTrade(Trade trade) throws Exception {
		boolean recordResult = false;
		try {
			logger.debug("Begin recordTrade with trade object: ");
			logger.debug(trade);

			// trade should be an object
			if (trade == null) {
				throw new Exception("Trade object to record should be a valid object and it's null.");
			}

			// stock should be an object
			if (trade.getStock() == null) {
				throw new Exception("A trade should be associated with a stock and the stock for the trade is null.");
			}

			// shares quantity should be greater than zero
			if (trade.getShareQuantity() <= 0) {
				throw new Exception("Shares quantity in the trade to record should be greater than zero.");
			}

			// shares price should be greater than zero
			if (trade.getPrice() <= 0.0) {
				throw new Exception("Shares price in the trade to record should be greater than zero.");
			}

			recordResult = stockDao.recordTrade(trade);

			// Update the ticker price for the stock
			if (recordResult) {
				trade.getStock().setInputPrice((trade.getPrice()));
			}

		} catch (Exception exception) {
			logger.error("Error when trying to record a trade.", exception);
			throw new Exception("Error when trying to record a trade.", exception);
		}
		return recordResult;
	}

	/**
	 * This method used to calculate stock prise as summation for selected range
	 * It uses Predicate class for it.
	 * 
	 * @param stockSymbol
	 * @param minutesRange
	 *            - 15 if sum is needed for last 15 minutes
	 * @return double stock price value after calculations (stockPrice =
	 *         tradePriceSum / shareQuantitySum)
	 * @throws Exception
	 */
	public double calculateStockPriceInRange(String stockSymbol, int minutesRange) throws Exception {

		logger.debug("Trades in the original collection: " + getTradesNumber());

		double stockPrice = 0.0;

		@SuppressWarnings("unchecked")
		Collection<Trade> trades = CollectionUtils.select(stockDao.getTrades(),
				new StockPredicate(stockSymbol, minutesRange));

		logger.debug(
				"Trades in the filtered collection by [" + stockSymbol + "," + minutesRange + "]: " + trades.size());

		// Calculate the summation
		double shareQuantitySum = 0.0;
		double tradePriceSum = 0.0;
		for (Trade trade : trades) {
			// Calculate the summation of Trade Price x Quantity
			tradePriceSum += (trade.getPrice() * trade.getShareQuantity());
			// Acumulate Quantity
			shareQuantitySum += trade.getShareQuantity();
		}

		// calculate the stock price
		if (shareQuantitySum > 0.0) {
			stockPrice = tradePriceSum / shareQuantitySum;
		}

		return stockPrice;
	}

	/**
	 * This method used to null check stock and invoke other method. (Calculate
	 * Volume Weighted Stock Price based on trades in past 15 minutes)
	 * 
	 * @param stock
	 *            symbol
	 * @double - stock price value after calculations (stockPrice =
	 *         tradePriceSum / shareQuantitySum)
	 * 
	 */
	public double calculateStockPrice(String stockSymbol) throws Exception {
		double stockPrice = 0.0;

		try {
			logger.debug("Calculating Stock Price for the stock symbol: " + stockSymbol);
			Stock stock = stockDao.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if (stock == null) {
				throw new Exception(
						"The stock symbol [" + stockSymbol + "] is not supported by the Super Simple Stock system.");
			}

			stockPrice = calculateStockPriceInRange(stockSymbol, 15);

			logger.debug(" Stock Price calculated: " + stockPrice);

		} catch (Exception exception) {
			logger.error("Error calculating P/E Ratio for the stock symbol: " + stockSymbol + ".", exception);
			throw new Exception("Error calculating P/E Ratio for the stock symbol: " + stockSymbol + ".", exception);

		}

		return stockPrice;
	}

	/**
	 * This method is used to calculate GBCE All share index It uses method from
	 * (StatUtils.geometricMean(stockPricesArray))
	 * 
	 * @return double - GBCE value
	 */
	@Override
	public double calculateGBCEAllShareIndex() throws Exception {
		double allShareIndex = 0.0;

		// Calculate stock price for all stock in the system
		Map<String, Stock> stocks = stockDao.getStocks();

		ArrayList<Double> stockPrices = new ArrayList<Double>();

		for (String stockSymbol : stocks.keySet()) {
			double stockPrice = calculateStockPriceInRange(stockSymbol, 0);
			if (stockPrice > 0) {
				stockPrices.add(stockPrice);
			}
		}

		if (stockPrices.size() >= 1) {
			double[] stockPricesArray = new double[stockPrices.size()];

			for (int i = 0; i <= (stockPrices.size() - 1); i++) {
				stockPricesArray[i] = stockPrices.get(i).doubleValue();
			}
			// Calculates the GBCE All Share Index
			allShareIndex = StatUtils.geometricMean(stockPricesArray);
		}
		return allShareIndex;
	}

	/**
	 * This is inner class to handle predicate
	 */
	private class StockPredicate implements Predicate {
		/**
		 *
		 */
		private Logger logger = Logger.getLogger(StockPredicate.class);

		/**
		 * 
		 */
		private String stockSymbol = "";

		/**
		 * 
		 */
		private Calendar dateRange = null;

		/**
		 * 
		 * @param stockSymbol
		 * @param minutesRange
		 */
		public StockPredicate(String stockSymbol, int minutesRange) {
			this.stockSymbol = stockSymbol;
			if (minutesRange > 0) {
				dateRange = Calendar.getInstance();
				dateRange.add(Calendar.MINUTE, -minutesRange);
				logger.debug(String.format("Filter date pivot: %tF %tT", dateRange.getTime(), dateRange.getTime()));
			}

		}

		/**
		 * 
		 */
		public boolean evaluate(Object tradeObject) {
			Trade trade = (Trade) tradeObject;
			boolean shouldBeInclude = trade.getStock().getStockSymbol().equals(stockSymbol);
			if (shouldBeInclude && dateRange != null) {
				shouldBeInclude = dateRange.getTime().compareTo(trade.getTimeStamp()) <= 0;
			}
			return shouldBeInclude;
		}

	}

	/**
	 * This method returns trdae numbers
	 * 
	 * @return int - trade number 
	 */
	public int getTradesNumber() throws Exception {
		return stockDao.getTrades().size();
	}

}
