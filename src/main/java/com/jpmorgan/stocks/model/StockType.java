/**
 * 
 */
package com.jpmorgan.stocks.model;

/**
 * This enum is used to define StockType
 *
 *@author Nilesh Mehta
 */
public enum StockType {
	
	/**
	 * Indicates that a stock is common and the dividend yield is calculated with last dividend.
	 */
	COMMON,
	
	/**
	 * Indicates that a stock is preferred and the dividend yield is calculated with fixed dividend.
	 */
	PREFERRED

}
