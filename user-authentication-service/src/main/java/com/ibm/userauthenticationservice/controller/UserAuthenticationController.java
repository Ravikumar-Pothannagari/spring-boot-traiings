/**
 * 
 */
package com.ibm.userauthenticationservice.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.userauthenticationservice.dao.UserRepository;
import com.ibm.userauthenticationservice.model.RequestUser;
import com.ibm.userauthenticationservice.model.ResponseUser;
import com.ibm.userauthenticationservice.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author RavikumarPothannagar
 *
 */
@RestController
public class UserAuthenticationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String secret = "test";

	@Autowired
	UserRepository repository;

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public @ResponseBody ResponseUser saveuser(@RequestBody RequestUser requestUser) {
		ResponseUser res = new ResponseUser();
		User userobj = new User();
		if (repository.findByUsername(requestUser.getUsername()).size() >= 1) {
			List<User> existingUser = repository.findByUsername(requestUser.getUsername());
			res.setUser(existingUser.get(0));
			res.setStatus("USER ALREADY EXISTS, Use different name");
		} else {
			userobj.setUsername(requestUser.getUsername());
			userobj.setPassword(requestUser.getPassword());
			repository.save(userobj);
			res.setUser(userobj);
			res.setStatus("USER added successfully.");
		}
		logger.info("{}", userobj);
		return res;

	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsers() {
		List<User> userlist = repository.findAll();
		logger.info("{}", userlist);
		return userlist;
	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public @ResponseBody List<User> getUserByName(@PathVariable String username) {
		List<User> userlist = repository.findByUsername(username);
		logger.info("{}", userlist);
		return userlist;
	}

	@GetMapping("/getToken/{username}")
	public String getUserToken(@PathVariable String username) {
		List<User> userlist = repository.findByUsername(username);
		return userlist.get(0).getUsertoken();
	}

	@GetMapping("/validateUserToken/{username}")
	public @ResponseBody Boolean validateUserToken(@PathVariable String username) {
		List<User> userobjs = repository.findByUsername(username);

		if (userobjs != null) {
			try {
				User userobj = userobjs.get(0);
				System.out.println("user token:" + userobj.getUsertoken());
				if (userobj.getUsertoken() == null) {
					return false;
				} else if (isTokenExpired(userobj.getUsertoken()))
					return false;
			} catch (Exception e) {
				System.out.println("exception:" + e.getMessage());
				return false;
			}
		}
		return true;
	}

	@RequestMapping(value = "/login", method = RequestMethod.PUT)
	public @ResponseBody ResponseUser login(@RequestBody RequestUser req) {
		ResponseUser res = new ResponseUser();
		User userobj = new User();
		userobj.setUsername(req.getUsername());
		userobj.setPassword(req.getPassword());
		if (!validateLoginUser(req)) {
			res.setUser(userobj);
			res.setStatus("Invalid Credentials, Try again..");
			return res;
		} else {
			userobj = repository.findByUsernameAndPassword(req.getUsername(), req.getPassword());
			userobj.setUsertoken(generateToken(req.getUsername()));
			repository.save(userobj);
			res.setUser(userobj);
			res.setStatus("Login success");
			return res;
		}
	}

	@RequestMapping(value = "/logout/{username}", method = RequestMethod.PUT)
	public @ResponseBody ResponseUser logout(@PathVariable String username) {
		ResponseUser res = new ResponseUser();
		List<User> users = repository.findByUsername(username);
		User userobj = users.get(0);
		userobj.setUsertoken(revokeToken(username));
		repository.save(userobj);
		res.setUser(userobj);
		res.setStatus(username + " logout success");
		return res;
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String generateToken(String username) {
		long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

		Map<String, Object> claims = new HashMap<>();
		String subject = username; // userDetails.getUsername();
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private boolean validateLoginUser(RequestUser req) {
		User resUser = repository.findByUsernameAndPassword(req.getUsername(), req.getPassword());
		if (resUser != null) {
			return true;
		} else {
			return false;
		}
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private String revokeToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		String subject = username; // userDetails.getUsername();
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

}
