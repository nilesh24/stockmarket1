
package com.jpmorgan.stocks.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jpmorgan.stocks.dao.StockDao;
import com.jpmorgan.stocks.model.Stock;
import com.jpmorgan.stocks.model.Trade;

/**
 * Implementation of the Stock Dao. Provides method to set and get Stock and
 * Trade objects from memory.
 * 
 * @author Nilesh Mehta
 *
 */
@Repository
public class StockDaoImpl implements StockDao {

	/**
	 * This is a reference for logger object
	 */
	private Logger logger = Logger.getLogger(StockDaoImpl.class);

	/**
	 * This is reference for Map of Stock object
	 */
	private HashMap<String, Stock> stocks = null;

	/**
	 * This is reference for List of Trade object
	 */
	private List<Trade> trades = null;

	/**
	 * This is a default constructor for object creations
	 */
	public StockDaoImpl() {
		trades = new ArrayList<Trade>();
		stocks = new HashMap<String, Stock>();
	}

	/**
	 * This is methods sets the Stock objects
	 * 
	 * @param stock
	 *            - Map of stock objects
	 */

	public void setStocks(HashMap<String, Stock> stocks) {
		this.stocks = stocks;
	}

	/**
	 * This is methods returns the Stock objects
	 * 
	 * @return stock objects
	 */
	public HashMap<String, Stock> getStocks() {
		// returns stocks
		return stocks;
	}

	/**
	 * This is method sets Trade objects
	 * 
	 * @param Trade
	 *            objects
	 */

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	/**
	 * This is method returns Trade objects
	 * 
	 * @return Trade objects
	 * @exception it
	 *                can throw exceptions
	 */
	@Override
	public List<Trade> getTrades() throws Exception {
		// returns trade
		return trades;
	}

	/**
	 * This method will record trade in memory.
	 * 
	 * @param trade
	 *            which need to added
	 * @exception it
	 *                can throw exceptions
	 */
	@Override
	public boolean recordTrade(Trade trade) throws Exception {
		boolean result = false;
		try {
			result = trades.add(trade);
		} catch (Exception exception) {
			throw new Exception("An error has occurred recording a trade in the system's backend.", exception);
		}
		return result;

	}

	/**
	 * This method return stock object for particular stock symbol
	 * 
	 * @param stock
	 *            symbol
	 * @exception it
	 *                can throw exceptions
	 */
	@Override
	public Stock getStockBySymbol(String symbol) throws Exception {
		Stock stock = null;
		try {
			if (symbol != null) {
				stock = stocks.get(symbol);
			}
		} catch (Exception exception) {
			logger.error("An error has occurred recovering the stock object for the stock symbol: " + symbol + ".",
					exception);
		}
		return stock;
	}

}
