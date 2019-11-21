package com.ibm.currencymanagerservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ibm.currencymanagerservice.model.Currency;
import com.ibm.currencymanagerservice.service.CurrencyManagerService;

/**
 * @author RavikumarPothannagar
 *
 */
@RefreshScope
@RestController
@RequestMapping("/currency")
public class CurrencyManagerServiceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Environment environment;

	@Autowired
	CurrencyManagerService service;

	@GetMapping("/retrieve/from/{from}/to/{to}")
	public Currency retrieveCurrencyConversionFactor(@PathVariable String from, @PathVariable String to) {

		logger.info(
				"CurrencyManagerServiceController->retrieveCurrencyConversionFactor(@PathVariable String from, @PathVariable String to) -->START");

		Currency currency = service.findByFromAndTo(from, to);
		currency.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		logger.info(
				"CurrencyManagerServiceController->retrieveCurrencyConversionFactor(@PathVariable String from, @PathVariable String to) -->END");
		return currency;
	}

	@GetMapping("/retrieve")
	public List<Currency> retrieveCurrencyConversionFactor() {
		logger.info(
				"CurrencyManagerServiceController->retrieveCurrencyConversionFactor() -->START");
		List<Currency> currencyList = service.findAll();
		for(Currency currency:currencyList) {
			currency.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		 }
		logger.info(
				"CurrencyManagerServiceController->retrieveCurrencyConversionFactor() -->END");
		return currencyList;
	}
	
	/**
	 * This operation will add currency conversion factor
	 * 
	 * @param currencyConversionFactor
	 * @return
	 */
	@PostMapping("/add")
	public Currency addConversionFactor(@RequestBody Currency currency) {
		logger.info("CurrencyManagerServiceController->addConversionFactor(@RequestBody Currency currency) -->START");
		Currency currencyUpdated = service.addConversionFactor(currency);
		currencyUpdated.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("CurrencyManagerServiceController->addConversionFactor(@RequestBody Currency currency) -->END");
		return currencyUpdated;
	}

	@PutMapping("/update")
	public Currency updateConversionFactor(@RequestBody Currency currency) {
		logger.info(
				"CurrencyManagerServiceController->updateConversionFactor(@RequestBody Currency currency) -->START");
		Currency currencyUpdated = service.updateConversionFactor(currency);
		currencyUpdated.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("CurrencyManagerServiceController->updateConversionFactor(@RequestBody Currency currency) -->END");
		return currencyUpdated;
	}

	@DeleteMapping("/remove/{id}")
	public HttpStatus deleteConversionFactor(@PathVariable("id") Long ID) {
		logger.info("CurrencyManagerServiceController->deleteConversionFactor(@PathVariable(\"id\") Long ID) -->START");
		boolean isConversionFactorDeleted = service.deleteConversionFactor(ID);

		logger.info("ID:" + ID + " removed:" + isConversionFactorDeleted);

		logger.info("CurrencyManagerServiceController->deleteConversionFactor(@PathVariable(\"id\") Long ID) -->END");
		return HttpStatus.FORBIDDEN;
	}
}
