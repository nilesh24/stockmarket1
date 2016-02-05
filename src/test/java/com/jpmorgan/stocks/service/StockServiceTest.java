package com.jpmorgan.stocks.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.jpmorgan.stocks.factory.StockServiceFactory;
import com.jpmorgan.stocks.factory.impl.StockServiceFactoryImpl;
import com.jpmorgan.stocks.factory.SpringService;
import com.jpmorgan.stocks.dao.StockDao;
import com.jpmorgan.stocks.dao.impl.StockDaoImpl;
import com.jpmorgan.stocks.model.Stock;
import com.jpmorgan.stocks.model.Trade;
import com.jpmorgan.stocks.service.impl.StockServiceImpl;

/**
 * This is Test class which is used to test StockService with help of JUnit
 * 
 * @author Nilesh Mehta
 *
 */
@Service
public class StockServiceTest {

	/**
	 * Logger object
	 */
	Logger logger = Logger.getLogger(StockServiceTest.class);

	/**
	 * This method verifies that stock service factory object is singleton
	 */
	@Test
	public void testStockServicesFactoryASingleton() {

		logger.info("Start  isStockServicesFactoryASingleton ...");

		StockServiceFactory factoryInstanceA = StockServiceFactoryImpl.getInstance();
		StockServiceFactory factoryInstanceB = StockServiceFactoryImpl.getInstance();

		Assert.assertNotNull(factoryInstanceA);
		Assert.assertNotNull(factoryInstanceB);

		Assert.assertTrue(factoryInstanceA.equals(factoryInstanceB));

		logger.info("Finish isStockServicesFactoryASingleton ...OK");

	}

	/**
	 * This method verifies that Stock Service object is singleton
	 */
	@Test
	public void testStockServicesASingleton() {

		logger.info("Start isStockServicesASingleton ");

		StockServiceFactory factoryInstance = StockServiceFactoryImpl.getInstance();

		StockService simpleStockServiceA = factoryInstance.getStockService();
		StockService simpleStockServiceB = factoryInstance.getStockService();

		Assert.assertNotNull(simpleStockServiceA);
		Assert.assertNotNull(simpleStockServiceB);

		Assert.assertTrue(simpleStockServiceA.equals(simpleStockServiceB));

		logger.info("Finish isStockServicesASingleton");

	}

	/**
	 * Test class written to verify the spring config loading
	 */
	@Test
	public void testSpringService() {
		final String SPRING_CONTEXT_FILE_NAME = "classpath*:*stocks-*-config.xml";
		logger.info("Loading Spring Context for Super Simple Stocks.");
		AbstractApplicationContext springContext = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE_NAME);
		springContext.registerShutdownHook();
		System.out.println("In Spring Config check");
		// StockDaoImpl stackDao=
		// (StockDaoImpl)springContext.getBean("stockDao");
		// StockServiceImpl stockService =(StockServiceImpl)
		springContext.getBean("stockService");
		// StockServiceImpl stockService =(StockServiceImpl)
		SpringService.INSTANCE.getBean("stockService", StockService.class);
		// tockDao stackDao= (StockDao)stockService.getStockDao();
		StockDao stockDao = SpringService.INSTANCE.getBean("stockDao", StockDao.class);
		System.out.println(stockDao.getStocks().toString());

		logger.info("Spring Context checked");
	}

	/**
	 * This method verify the dividend calculation for both stock types.
	 */
	@Test
	public void testCalculateDividendYieldTest() {
		logger.info("Start calculateDividendYieldTest ...");

		StockServiceFactory factoryInstance = StockServiceFactoryImpl.getInstance();
		try {
			// Create the stock service and verify it's not null object
			StockService stockService = factoryInstance.getStockService();
			Assert.assertNotNull(stockService);

			System.out.println("stockService::" + stockService.toString());

			StockDao stockDao = SpringService.INSTANCE.getBean("stockDao", StockDao.class);

			int tradesNumber = stockDao.getTrades().size();
			logger.info("Trades number: " + tradesNumber);

			// Calculates the dividend yield for the stock symbol
			String[] stockSymbols = { "TEA", "POP", "ALE", "GIN", "JOE" };

			// Set input Values
			Map<String, Stock> stocks = stockDao.getStocks();
			double[] inputPrizes = { 1, 2, 5, 1, 2 };
			for (int i = 0; i < stocks.size(); i++) {
				stockDao.getStockBySymbol(stockSymbols[i]).setInputPrice(inputPrizes[i]);
				;

			}

			for (String stockSymbol : stockSymbols) {
				double dividendYield = stockService.calculateDividendYield(stockSymbol);
				logger.info(stockSymbol + " - DividendYield calculated: " + dividendYield);
				Assert.assertTrue(dividendYield >= 0.0);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			;
			logger.error(exception);
			Assert.assertTrue(false);
		}

		logger.info("Finish calculateDividendYield ");
	}

	/**
	 * This method helps to verify the PE ratio calculations
	 */
	@Test
	public void testCalculatePERatio() {
		logger.info("Start calculatePERatioTest ...");

		try {
			// Create the stock service and verify it's not null object
			StockServiceFactory factoryInstance = StockServiceFactoryImpl.getInstance();
			StockService stockService = factoryInstance.getStockService();
			Assert.assertNotNull(stockService);

			StockDao stockDao = SpringService.INSTANCE.getBean("stockDao", StockDao.class);
			int tradesNumber = stockDao.getTrades().size();
			logger.info("Trades number: " + tradesNumber);

			// Calculates the P/E Ratio for the stock Symbol
			String[] stockSymbols = { "TEA", "POP", "ALE", "GIN", "JOE" };

			// Set input Values
			Map<String, Stock> stocks = stockDao.getStocks();
			double[] inputPrizes = { 1, 2, 5, 1, 2 };
			for (int i = 0; i < stocks.size(); i++) {
				stockDao.getStockBySymbol(stockSymbols[i]).setInputPrice(inputPrizes[i]);

			}

			for (String stockSymbol : stockSymbols) {
				double peRatio = stockService.calculatePERatio(stockSymbol);
				logger.info(stockSymbol + " - P/E Ratio calculated: " + peRatio);
				Assert.assertTrue(peRatio >= 0.0);
			}
		} catch (Exception exception) {
			logger.error(exception);
			Assert.assertTrue(false);
		}

		logger.info("Finish calculatePERatioTest ...OK");
	}

	/**
	 * This method help to verify the record a trade
	 */
	@Test
	public void testRecordATrade() {
		logger.info("Start recordATradeTest ...");

		// Create the stock service and verify it's not null object
		StockServiceFactory factoryInstance = StockServiceFactoryImpl.getInstance();
		StockService stockService = factoryInstance.getStockService();
		Assert.assertNotNull(stockService);

		// Recover the trades configured in spring for the unit test

		ArrayList<Trade> tradeList = SpringService.INSTANCE.getBean("tradeList", ArrayList.class);
		Assert.assertNotNull(tradeList);
		logger.info("Trade List size: " + tradeList.size());

		try {
			// Initial trades are empty, means, trades number equals to zero
			StockDao stockDao = SpringService.INSTANCE.getBean("stockDao", StockDao.class);
			int tradesNumber = stockDao.getTrades().size();
			logger.info("Trades number: " + tradesNumber);

			Assert.assertEquals(tradesNumber, 0);

			// Insert many trades in the stock system
			for (Trade trade : tradeList) {
				boolean result = stockService.recordTrade(trade);
				Assert.assertTrue(result);
			}

			// After record trades, the number of trades should be equal to the
			// trades list
			tradesNumber = stockDao.getTrades().size();

			logger.info("Trades number: " + tradesNumber);

			Assert.assertEquals(tradesNumber, tradeList.size());

		} catch (Exception exception) {
			logger.error(exception);
			Assert.assertTrue(false);
		}

		logger.info("Finish recordATradeTest ...OK");

	}

	/**
	 * This method helps to verify - Calculate Volume Weighted Stock Price based
	 * on trades in past 15 minutes
	 */
	@Test
	public void testCalculateStockPrice() {
		try {
			// Create the stock service and verify it's not null object
			StockServiceFactory factoryInstance = StockServiceFactoryImpl.getInstance();
			StockService stockService = factoryInstance.getStockService();

			Assert.assertNotNull(stockService);

			StockDao stockDao = SpringService.INSTANCE.getBean("stockDao", StockDao.class);
			int tradesNumber = stockDao.getTrades().size();
			logger.info("Trades number: " + tradesNumber);

			// Calculates the Stock Price for all stocks
			String[] stockSymbols = { "TEA", "POP", "ALE", "GIN", "JOE" };
			// String[] stockSymbols = {"TEA"};
			for (String stockSymbol : stockSymbols) {
				double stockPrice = stockService.calculateStockPrice(stockSymbol);
				logger.info(stockSymbol + " - Stock Price calculated: " + stockPrice);

				Assert.assertTrue(stockPrice >= 0.0);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error(exception);
			Assert.assertTrue(false);
		}

	}

	/**
	 * This method help to verify GBCE All Share Index calculations
	 */
	@Test
	public void testCalculateGBCEAllShareIndex() {
		try {
			// Create the stock service and verify it's not null object
			StockServiceFactory factoryInstance = StockServiceFactoryImpl.getInstance();
			StockService stockService = factoryInstance.getStockService();

			Assert.assertNotNull(stockService);

			StockDao stockDao = SpringService.INSTANCE.getBean("stockDao", StockDao.class);
			int tradesNumber = stockDao.getTrades().size();
			logger.info("Trades number: " + tradesNumber);

			double GBCEAllShareIndex = stockService.calculateGBCEAllShareIndex();
			logger.info("GBCE All Share Index: " + GBCEAllShareIndex);

			Assert.assertTrue(GBCEAllShareIndex > 0.0);

		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error(exception);

			Assert.assertTrue(false);
		}

	}

}
