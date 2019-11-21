package com.ibm.currencymanagerservice.service;

import java.util.List;

import com.ibm.currencymanagerservice.model.Currency;

/**
 * @author RavikumarPothannagar
 *
 */
public interface CurrencyManagerService {

	/**
	 * This method is used to find the Currency Conversion Factor details.
	 * 
	 * @param from
	 * @param to
	 * @return Currency
	 */
	Currency findByFromAndTo(String from, String to);

	/**
	 * This method is used to save the Currency Conversion Factor details.
	 * 
	 * @param currency
	 * @return Currency
	 */
	Currency addConversionFactor(Currency currency);

	/**
	 * This method is used to update the Currency Conversion Factor details.
	 * 
	 * @param currency
	 * @return Currency
	 */
	Currency updateConversionFactor(Currency currency);

	/**
	 * This method is used to delete the Currency Conversion Factor details.
	 * 
	 * @param iD
	 * @return boolean
	 */
	boolean deleteConversionFactor(Long ID);

	/**
	 * This method is used to find all the Currency Conversion Factor details.
	 * 
	 * @return List<Currency>
	 */
	List<Currency> findAll();
}
