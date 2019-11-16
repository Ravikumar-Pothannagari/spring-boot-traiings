package com.ibm.currencyconvertor.model;

import java.math.BigDecimal;

/**
 * @author RavikumarPothannagar
 *
 */

public class Currency {

	private Long ID;
	private String from;
	private String to;
	private BigDecimal factorValue;
	private BigDecimal quantity;
	private BigDecimal totalCalculatedAmount;
	private int port;

	public Currency() {

	}

	/**
	 * @param iD
	 * @param from
	 * @param to
	 * @param factorValue
	 */
	public Currency(Long iD, String from, String to, BigDecimal factorValue, BigDecimal quantity,
			BigDecimal totalCalculatedAmount, int port) {
		super();
		ID = iD;
		this.from = from;
		this.to = to;
		this.factorValue = factorValue;
		this.quantity = quantity;
		this.totalCalculatedAmount = totalCalculatedAmount;
		this.port = port;
	}

	/**
	 * @return the iD
	 */
	public Long getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(Long iD) {
		ID = iD;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the factorValue
	 */
	public BigDecimal getFactorValue() {
		return factorValue;
	}

	/**
	 * @param factorValue
	 *            the factorValue to set
	 */
	public void setFactorValue(BigDecimal factorValue) {
		this.factorValue = factorValue;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the totalCalculatedAmount
	 */
	public BigDecimal getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}

	/**
	 * @param totalCalculatedAmount
	 *            the totalCalculatedAmount to set
	 */
	public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}

}
