package com.jpmorgan.stocks.factory;

import com.jpmorgan.stocks.service.StockService;

/**
 * This factory interface is to provide access to service object
 * 
 * @author Nilesh Mehta
 *
 */
public interface StockServiceFactory {

	/**
	 * Returns the singleton instance of the Service, which implements the five
	 * operations to calculate the dividend yield, P/E Ratio, Stock Price, GBCE
	 * All Share Index and record trades for a given stock.
	 * 
	 * @return An object of type StockService
	 */
	public StockService getStockService();

}
