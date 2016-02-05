/**
 * 
 */
package com.jpmorgan.stocks.dao;

import java.util.List;
import java.util.Map;

import com.jpmorgan.stocks.model.Stock;
import com.jpmorgan.stocks.model.Trade;

/**
 * This interface is to represent the StockDao. It holds in memory data.
 *
 * @author Nilesh Mehta
 *
 */
public interface StockDao {

	/**
	 * This method helps to record a trade represented by the object * <i>trade</i>.
	 * 
	 * @param trade -  The trade object to record.
	 * 
	 * @return True when the operation of record a trade is successful. Other case, False.
	 * 
	 * @throws Exception  A exception during the operation of record a trade.
	 */
	public boolean recordTrade(Trade trade) throws Exception;

	/**
	 * Returns the array list that contains all the trades.
	 * 
	 * @return The array list that contains all the trades 
	 */
	public List<Trade> getTrades() throws Exception;
	
	
	/**
	 * It returns the Stock for particular stock symbol
	 * @param stockSymbol Stock symbol 
	 * @return the stock object
	 */
	public Stock getStockBySymbol(String symbol) throws Exception;

	/**
	 * Returns all the stocks for Stocks application.
	 * 
	 * @return The map that contains all the stocks
	 */
	public Map<String, Stock> getStocks();

}
