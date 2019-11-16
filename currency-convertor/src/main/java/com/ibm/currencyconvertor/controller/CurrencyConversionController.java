package com.ibm.currencyconvertor.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ibm.currencyconvertor.model.Currency;
import com.ibm.currencyconvertor.service.CurrencyExchangeServiceProxy;

/**
 * @author RavikumarPothannagar
 *
 */
@RefreshScope
@RestController
@RequestMapping("/converter")
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/get/from/{from}/to/{to}/quantity/{quantity}")
	public Currency convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		logger.info(
				"CurrencyConversionController->convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) -->START");
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<Currency> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency/retrieve/from/{from}/to/{to}", Currency.class, uriVariables);
		Currency response = responseEntity.getBody();

		Currency conversionValueObj = new Currency(response.getID(), from, to, response.getFactorValue(), quantity,
				quantity.multiply(response.getFactorValue()), response.getPort());

		logger.info(
				"CurrencyConversionController->convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) -->END");
		return conversionValueObj;
	}

	@GetMapping("/get-feign/from/{from}/to/{to}/quantity/{quantity}")
	public Currency convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		logger.info(
				"CurrencyConversionController->convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) -->START");
		Currency response = proxy.retrieveExchangeValue(from, to);
		logger.info("{}", response);
		Currency conversionValueObj = new Currency(response.getID(), from, to, response.getFactorValue(), quantity,
				quantity.multiply(response.getFactorValue()), response.getPort());
		logger.info(
				"CurrencyConversionController->convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) -->END");
		return conversionValueObj;
	}

}

