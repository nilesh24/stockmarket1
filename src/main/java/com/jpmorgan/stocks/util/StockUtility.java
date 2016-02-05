package com.jpmorgan.stocks.util;

/**
 * This is utility class and holds the formulas.
 * 
 * @author Nilesh Mehta
 */
public class StockUtility {

	/**
	 * This method hold formula for dividend yield for common stock
	 * 
	 * @param lastDividend
	 * @param inputPrise
	 * @return double - dividend yield
	 */
	public static double getDividendYieldForCommon(double lastDividend, double inputPrise) {
		double dividendYield = -1.0;

		dividendYield = lastDividend / inputPrise;

		return dividendYield;

	}

	/**
	 * This method hold formula for dividend yield for Preffered stock
	 * 
	 * @param fixedDividend
	 * @param inputPrice
	 * @param parValue
	 * @return double
	 */
	public static double getDividendYieldForPreferred(double fixedDividend, double inputPrise, double parValue) {
		double dividendYield = -1.0;

		// PREFERRED
		dividendYield = (fixedDividend * parValue) / inputPrise;

		return dividendYield;
	}

	/**
	 * This method returns PE ratio
	 * 
	 * @param inputPrise
	 * @param dividend
	 * @return
	 */
	public static double getPeRatio(double inputPrise, double dividend) {
		double peRatio = 0.0;

		if (inputPrise > 0.0) {
			peRatio = inputPrise / dividend;
		}

		return peRatio;
	}

}
