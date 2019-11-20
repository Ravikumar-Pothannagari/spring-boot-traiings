package com.ibm.currencyconvertor.service;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ibm.currencyconvertor.model.Currency;

/**
 * @author RavikumarPothannagar
 *
 */

@RefreshScope
@FeignClient(name = "curency-manager-service")
@RibbonClient(name = "curency-manager-service")
public interface CurrencyExchangeServiceProxy {
	@GetMapping("/currency/retrieve/from/{from}/to/{to}")
	public Currency retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
	
}	
