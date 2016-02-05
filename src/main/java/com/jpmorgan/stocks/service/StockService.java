package com.jpmorgan.stocks.service;

import com.jpmorgan.stocks.model.Trade;


/**
 * This interface provides contract to implements the five operations to calculate the dividend yield, 
 * P/E Ratio, Stock Price, GBCE All Share Index and record trades for a given stock.
 * 
 * Nilesh Mehta
 */
public interface StockService {

	
		/**
		 * Calculates the dividend yield 
		 * 
		 * @param stockSymbol Stock symbol.
		 * @return A double value which represents the dividend yield 
		 * @throws Exception A exception raised during the execution of the method due to an error.
		 */
	
		public double calculateDividendYield(String stockSymbol) throws Exception;
		
		/**
		 * Calculates the P/E Ratio for a given stock.
		 * 
		 * @param stockSymbol Stock symbol.
		 *  @return A double value which represents the P/E Ratio for a given stock.
		 * @throws Exception A exception raised during the execution of the method due to an error.
		 */
		public double calculatePERatio(String stockSymbol) throws Exception;
		
		/**
		 * Record a trade in the  Stocks application.
		 * 
		 * @param trade Trade object to record.
		 * @return True, when the record is successful. Other case, False.
		 * @throws Exception A exception raised during the execution of the method due to an error.
		 */
		public boolean recordTrade(Trade trade) throws Exception;
		
		/**
		 * Calculates stock price
		 * 
		 * @param stockSymbol
		 * @return double 
		 * @throws Exception A exception raised during the execution of the method due to an error.
		 */
		public double calculateStockPrice(String stockSymbol) throws Exception;
		
		
		/**
		 * Calculates the GBCE All Share Index using the geometric mean of prices for all stocks.
		 *  
		 * Consider only the stock's prices greater than zero.
		 *  
		 * @return double value
		 * @throws Exception A exception raised during the execution of the method due to an error.
		 */
		public double calculateGBCEAllShareIndex() throws Exception;
		
	}

	

