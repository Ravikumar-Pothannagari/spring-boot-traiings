/**
 * 
 */
package com.ibm.orderservice.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author RavikumarPothannagar
 *
 */

@FeignClient(name="user-auth-service")
@RibbonClient(name="user-auth-service")
public interface UserAuthServiceProxy {
	//@GetMapping("/validateUserToken/{username}")
	@RequestMapping(value = "/validateUserToken/{username}", method = RequestMethod.GET) 
	public Boolean validateUserToken(@PathVariable("username")  String username);

	//@GetMapping("/getToken/{username}")
	@RequestMapping(value = "/getToken/{username}", method = RequestMethod.GET) 
	public String getUserToken(@PathVariable("username")  String username);
}
