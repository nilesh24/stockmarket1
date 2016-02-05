/**
 * 
 */
package com.jpmorgan.stocks.model;

/**
 * Model class for Stock object
 * 
 * @author Nilesh Mehta
 *
 */
public class Stock {

	/**
	 * Member Variable for stock symbol
	 */
	private String stockSymbol;

	/**
	 * Member Variable for stock type
	 */
	private StockType stockType;

	/**
	 * Member Variable for last dividend
	 */
	private double lastDividend;

	/**
	 * Member Variable for fixed divident
	 */
	private double fixedDividend;

	/**
	 * Member Variable for patValue
	 */
	private double parValue;

	/**
	 * Member Variable for input price
	 */
	private double inputPrice;

	/**
	 * Getter method for stock symbol
	 * 
	 * @return stock symbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * Setter method for stock symbol
	 * 
	 * @param stockSymbol
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	/**
	 * Getter method for stock type
	 * 
	 * @return stock type
	 */
	public StockType getStockType() {
		return stockType;
	}

	/**
	 * Setter method for stock type
	 * 
	 * @param stockType
	 */
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	/**
	 * Getter method for last dividend
	 * 
	 * @return last dividend
	 */
	public double getLastDividend() {
		return lastDividend;
	}

	/**
	 * Setter method for last dividend
	 * 
	 * @param lastDividend
	 */
	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * Getter method for fixed dividend
	 * 
	 * @return fixed dividend
	 */
	public double getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * Setter method for fixed dividend
	 * 
	 * @param fixedDividend
	 */
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * Getter method for parValue
	 * 
	 * @return parValue
	 */
	public double getParValue() {
		return parValue;
	}

	/**
	 * Setter method for parValue
	 * 
	 * @param parValue
	 */
	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	/**
	 * Getter method for input price
	 * @return price
	 */
	public double getInputPrice() {
		return inputPrice;
	}

	/**
	 * Setter method for price
	 * @param inputPrice
	 */
	public void setInputPrice(double inputPrice) {
		this.inputPrice = inputPrice;
	}

	/**
	 * Default constructor to initialize values
	 */
	Stock() {
		stockSymbol = null;
		stockType = StockType.COMMON;
		lastDividend = 0.0;
		fixedDividend = 0.0;
		parValue = 0.0;
		inputPrice=0.0;

	}

}
