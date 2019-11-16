package com.ibm.currencymanagerservice.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RavikumarPothannagar
 *
 */
@Entity(name = "CURRENCY")
public class Currency {

	@Id
	private Long ID;

	@Column(name = "CURRENCY_FROM")
	private String from;

	@Column(name = "CURRENCY_TO")
	private String to;

	@Column(name = "CONVERSOIN_FACTOR_VALUE")
	private BigDecimal factorValue;

	private int port;

	public Currency() {

	}

	/**
	 * @param iD
	 * @param from
	 * @param to
	 * @param factorValue
	 */
	public Currency(Long iD, String from, String to, BigDecimal factorValue, int port) {
		super();
		ID = iD;
		this.from = from;
		this.to = to;
		this.factorValue = factorValue;
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

}
