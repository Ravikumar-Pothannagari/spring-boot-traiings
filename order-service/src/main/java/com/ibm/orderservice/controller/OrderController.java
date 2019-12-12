
package com.ibm.orderservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ibm.orderservice.dao.OrderRepository;
import com.ibm.orderservice.model.Orderdet;
import com.ibm.orderservice.model.RequestOrder;
import com.ibm.orderservice.model.ResponseBill;
import com.ibm.orderservice.model.ResponseOrder;
import com.ibm.orderservice.service.UserAuthServiceProxy;
import com.ibm.orderservice.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * @author RavikumarPothannagar
 *
 */
@RestController
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserAuthServiceProxy userproxy;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${USER-AUTHENTICATION-SERVICE_SERVICE_HOST:184.173.5.222}")
	private String userAuthenticationServiceHost;
	
	@Value("${USER-AUTHENTICATION-SERVICE_SERVICE_PORT:30202}")
	private String userAuthenticationServicePort;
	
	@RequestMapping(value = "/updateorder/{orderid}", method = RequestMethod.PUT)
	public @ResponseBody ResponseOrder updateorder(@RequestBody RequestOrder req, @PathVariable Long orderid) {
		Optional<Orderdet> orderOpt = repository.findById(orderid);
		Orderdet orderobj = orderOpt.get();

		ResponseOrder res = new ResponseOrder();
		logger.info("in addorder...........");
		if (req.getUsername() == null || req.getUsername().length() == 0) {
			res.setOrder(orderobj);
			res.setStatus("FAILED");
			res.setStatusMsg("Retry your Order. User name is blank");
			logger.info("User name is blank");
			return res;
		}

		if (validateUserToken(req.getUsername())) {
			res.setOrder(orderobj);
		} else {
			res.setStatus("FAILED");
			res.setStatusMsg("User token expired");
			logger.info("Retry your Order. User token expired");
			return res;
		}

		
		orderobj.setUsername(req.getUsername());
		orderobj.setAddress(req.getAddress());
		orderobj.setItem(req.getItem());
		orderobj.setAmount(req.getAmount());
		orderobj.setUsertoken(getUserToken(req.getUsername()));
		
		logger.info("USER TOKEN:" + orderobj.getUsertoken());
		String transToken = commonUtil.generateTransactionToken(req.getUsername());
		
		orderobj.setTranstoken(transToken);
		Orderdet savedOrder = repository.save(orderobj);
		logger.info("{}", savedOrder);
		res.setOrder(savedOrder);
		res.setStatus("SUCCESS");
		res.setStatusMsg("Order update is success");
		return res;

	}

	@RequestMapping(value = "/addorder", method = RequestMethod.POST)
	public @ResponseBody ResponseOrder saveorder(@RequestBody RequestOrder req) {
		Orderdet orderobj = new Orderdet();
		ResponseOrder res = new ResponseOrder();
		logger.info("in addorder...........");
		if (req.getUsername() == null || req.getUsername().length() == 0) {
			res.setOrder(orderobj);
			res.setStatus("FAILED");
			res.setStatusMsg("Retry your Order. User name is blank");
			logger.info("User name is blank");
			return res;
		}

		if (validateUserToken(req.getUsername())) {
			res.setOrder(orderobj);
		} else {
			res.setStatus("FAILED");
			res.setStatusMsg("User token expired");
			logger.info("Retry your Order. User token expired");
			return res;
		}

		
		orderobj.setUsername(req.getUsername());
		orderobj.setAddress(req.getAddress());
		orderobj.setItem(req.getItem());
		orderobj.setAmount(req.getAmount());
		orderobj.setUsertoken(getUserToken(req.getUsername()));
		
		logger.info("USER TOKEN:" + orderobj.getUsertoken());
		
		String transToken = commonUtil.generateTransactionToken(req.getUsername());
		orderobj.setTranstoken(transToken);
		Orderdet savedOrder = repository.save(orderobj);
		logger.info("{}", savedOrder);
		res.setOrder(savedOrder);
		res.setStatus("SUCCESS");
		res.setStatusMsg("Order is success");
		return res;

	}

	@RequestMapping(value = "/bill/{username}", method = RequestMethod.PUT)
	public @ResponseBody ResponseBill getBillForUser(@PathVariable String username) {
		ResponseBill bill = new ResponseBill();
		List<Orderdet> orderlist = repository.findByUsername(username);

		logger.info("{}", orderlist);
		int totalAmount = 0;
		List<ResponseOrder> resOrderList = new ArrayList<ResponseOrder>();
		String billstatus = "SUCCESS";
		String billstatusmsg = "Thanks for shopping";

		for (Orderdet orderdet : orderlist) {
			ResponseOrder resOrder = new ResponseOrder();
			resOrder.setOrder(orderdet);
			if (commonUtil.validateTransactionToken(orderdet.getTranstoken(), username)) {
				totalAmount = totalAmount + orderdet.getAmount();
				orderdet.setTranstoken(commonUtil.revokeToken(username));
				resOrder.setStatus("SUCCESS");
				resOrder.setStatusMsg("added to bill");
			} else {
				billstatus = "partial Bill";
				resOrder.setStatus("FAILED");
				resOrder.setStatusMsg(
						"removed from bill, transaction token has expired, reorder order id" + orderdet.getOrderid());
			}
			repository.save(orderdet);
			resOrderList.add(resOrder);
		}
		bill.setTotalAmount(totalAmount);
		bill.setOrderlist(resOrderList);
		bill.setStatus(billstatus);
		bill.setStatusMsg(billstatusmsg);
		return bill;
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public @ResponseBody List<Orderdet> getOrders() {
		List<Orderdet> orderlist = repository.findAll();
		logger.info("{}", orderlist);
		return orderlist;
	}

	@RequestMapping(value = "/orders/{username}", method = RequestMethod.GET)
	public @ResponseBody List<Orderdet> getOrdersByUser(@PathVariable String username) {
		List<Orderdet> orderlist = repository.findByUsername(username);
		logger.info("{}", orderlist);
		return orderlist;
	}

	
	@RequestMapping(value = "/user-authentication-service/validateUserToken/{username}", method = RequestMethod.GET)
	public Boolean validateUserToken(@PathVariable String username) {
		logger.info("OrderController >> validateUserToken(@PathVariable String username) -->START");
		Boolean isValidToken = false;
		//boolean isFeignClientEnabled = true;
		boolean isFeignClientEnabled = false;
		if(isFeignClientEnabled) {
			//FeignClient
			isValidToken =  userproxy.validateUserToken(username);	
		}else {
			//using rest template
			isValidToken = validateUserTokenFromRestTemplate(username);
		}
		logger.info("OrderController >> validateUserToken(@PathVariable String username) -->END");
		return isValidToken;
	}
	
	@RequestMapping(value = "/user-authentication-service/getToken/{username}", method = RequestMethod.GET)
	public String getUserToken(@PathVariable String username) {
		logger.info("OrderController >> getUserToken(@PathVariable String username) -->START");
		String userToken = null;
		//boolean isFeignClientEnabled = true;
		boolean isFeignClientEnabled = false;
		if(isFeignClientEnabled) {
			//FeignClient
			userToken =  userproxy.getUserToken(username);	
		}else {
			//using rest template
			userToken = getUserTokenFromRestTemplate(username);
		}
		logger.info("OrderController >> getUserToken(@PathVariable String username) -->END");
		return userToken;
	}
	
	
	private Boolean validateUserTokenFromRestTemplate(String username) {
		logger.info("OrderController >> validateUserTokenFromRestTemplate(@PathVariable String username) -->START");
		//String url = "http://localhost:8899/validateUserToken/{username}";
		String url = "http://"+userAuthenticationServiceHost+":"+userAuthenticationServicePort+"/validateUserToken/{username}";
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("username", username);
		ResponseEntity<Boolean> responseEntity = new RestTemplate().getForEntity(url, Boolean.class, uriVariables);
		Boolean isValidToken = responseEntity.getBody();
		logger.info("OrderController >> validateUserTokenFromRestTemplate(@PathVariable String username) -->END");
		return isValidToken;
	}
	
	
	private String getUserTokenFromRestTemplate(String username) {
		logger.info("OrderController >> getUserTokenFromRestTemplate(@PathVariable String username) -->START");
		//String url = "http://localhost:8899/getToken/{username}";
		String url = "http://"+userAuthenticationServiceHost+":"+userAuthenticationServicePort+"/getToken/{username}";
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("username", username);
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class, uriVariables);
		String userToken = responseEntity.getBody();
		logger.info("OrderController >> getUserTokenFromRestTemplate(@PathVariable String username) -->END");
		return userToken;
	}
	
}
