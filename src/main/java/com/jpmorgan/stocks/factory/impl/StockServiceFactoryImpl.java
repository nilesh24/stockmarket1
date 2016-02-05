package com.jpmorgan.stocks.factory.impl;

import com.jpmorgan.stocks.factory.StockServiceFactory;
import com.jpmorgan.stocks.factory.SpringService;
import com.jpmorgan.stocks.service.StockService;
import com.jpmorgan.stocks.service.StockService;

/**
 * Implementation of the Factory of the services
 * 
 * @author Nilesh Mehta
 *
 */
public class StockServiceFactoryImpl implements StockServiceFactory {

	/**
	 * Returns the singleton instance of the which implements the five
	 * operations to calculate the dividend yield, P/E Ratio, Stock Price, GBCE
	 * All Share Index and record trades for a given stock.
	 * 
	 * @return An object of type stockService
	 */

	private static final StockServiceFactory INSTANCE = new StockServiceFactoryImpl();

	/**
	 * Private constructor for the factory which prevents new instance
	 */
	private StockServiceFactoryImpl() {
		SpringService.INSTANCE.getClass();
	}

	/**
	 * Returns the singleton instance of the factory of the services
	 * 
	 * @return An object of the StockServiceFactory
	 */
	public static StockServiceFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns the stockService Object
	 */
	public StockService getStockService() {
		return SpringService.INSTANCE.getBean("stockService", StockService.class);
	}

}
