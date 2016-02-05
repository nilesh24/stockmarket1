/**
 * 
 */
package com.jpmorgan.stocks.model;

import java.util.Date;

/**
 * This is a model class to store Trade object with getter and setter methods.
 * 
 * @author Nilesh Mehta
 */
public class Trade {

	/**
	 * variable to store stock
	 */
	private Stock stock;
	
	/**
	 * variable to store price
	 */
	private double price;
	
	/**
	 * variable to store shareQuantity
	 */
	private int shareQuantity;
	
	/**
	 * variable to store trade type
	 */
	private TradeType tradeType;
	
	/**
	 * variable to timeStamp
	 */
	private Date timeStamp;

	/**
	 * Default constructor
	 */
	public Trade() {

		this.stock = null;
		this.price = 0.0;
		this.shareQuantity = 0;
		this.tradeType = TradeType.SELL;
		this.timeStamp = null;
	}

	/**
	 * Getter method for stock
	 * @return stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * Setter method for stock
	 * @param stock
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * Getter method for price
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Setter method for price
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Getter method for share quantity
	 * @return share quantity
	 */
	public int getShareQuantity() {
		return shareQuantity;
	}

	/**
	 * Setter method for share quatity
	 * @param shareQuantity
	 */
	public void setShareQuantity(int shareQuantity) {
		this.shareQuantity = shareQuantity;
	}

	/**
	 * Getter method for trade type
	 * @return trade type
	 */
	public TradeType getTradeType() {
		return tradeType;
	}

	/**
	 * Setter method for trade type
	 * @param tradeType
	 */
	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * Getter method for timestamp
	 * @return timestamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Setter method for timestamp
	 * @param timeStamp
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}
