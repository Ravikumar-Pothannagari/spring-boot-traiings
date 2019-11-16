package com.ibm.currencymanagerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ibm.currencymanagerservice.model.Currency;

/**
 * @author RavikumarPothannagar
 *
 */
public interface CurrencyConversionFactorRepository extends JpaRepository<Currency, Long> {
	/**
	 * This method is used to find Currency Conversion Factor details.
	 * 
	 * @param from
	 * @param to
	 * @return Currency
	 */
	Currency findByFromAndTo(String from, String to);

}
