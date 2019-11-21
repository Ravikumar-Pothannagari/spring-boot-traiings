/**
 * 
 */
package com.ibm.currencymanagerservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibm.currencymanagerservice.dao.CurrencyConversionFactorRepository;
import com.ibm.currencymanagerservice.model.Currency;


/**
 * @author RavikumarPothannagar
 *
 */

@Service
public class CurrencyManagerServiceImpl implements CurrencyManagerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CurrencyConversionFactorRepository repository;

	@Override
	public Currency findByFromAndTo(String from, String to) {
		logger.info("CurrencyManagerServiceImpl->findByFromAndTo(String from, String to) -->START");
		Currency currencyConversionFactor = repository.findByFromAndTo(from, to);
		logger.info("CurrencyManagerServiceImpl->findByFromAndTo(String from, String to) -->END");
		return currencyConversionFactor;
	}

	@Override
	public Currency addConversionFactor(Currency currency) {
		logger.info("CurrencyManagerServiceImpl->addConversionFactor(Currency currency) -->START");
		Currency currencyConversionFactorUpdated = repository.save(currency);
		logger.info("CurrencyManagerServiceImpl->addConversionFactor(Currency currency) -->END");
		return currencyConversionFactorUpdated;
	}

	@Override
	public Currency updateConversionFactor(Currency currency) {
		logger.info("CurrencyManagerServiceImpl->updateConversionFactor(Currency currency) -->START");
		Currency newEntity = null;
		Optional<Currency> currencyFrmDB = repository.findById(currency.getID());
		if (currencyFrmDB.isPresent()) {
			newEntity = currencyFrmDB.get();
			newEntity.setFrom(currency.getFrom());
			newEntity.setTo(currency.getTo());
			newEntity.setFactorValue(currency.getFactorValue());
			newEntity = repository.save(newEntity);

		} else {
			newEntity = repository.save(currency);

		}
		logger.info("CurrencyManagerServiceImpl->updateConversionFactor(Currency currency) -->END");
		return newEntity;
	}

	@Override
	public boolean deleteConversionFactor(Long ID) {
		logger.info("CurrencyManagerServiceImpl->deleteConversionFactor(Long ID) -->START");
		Optional<Currency> currencyConversionFactorFrmDB = repository.findById(ID);
		if (currencyConversionFactorFrmDB.isPresent()) {
			repository.deleteById(ID);
		}
		logger.info("CurrencyManagerServiceImpl->deleteConversionFactor(Long ID) -->START");
		return true;
	}

	@Override
	public List<Currency> findAll() {
		logger.info("CurrencyManagerServiceImpl->findAll() -->START");
		List<Currency> currencyList = repository.findAll();
		logger.info("CurrencyManagerServiceImpl->findAll() -->END");
		return currencyList;
	}

}
