/**
 * 
 */
package com.ibm.orderservice.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.orderservice.dao.OrderRepository;
import com.ibm.orderservice.model.Orderdet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author RavikumarPothannagar
 *
 */
@Service
public final class CommonUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String secret = "test";

	@Autowired
	private OrderRepository repository;

	/**
	 * This method is used to generate the transaction token for given user.
	 * 
	 * @param username
	 * @return
	 */
	public String generateTransactionToken(String username) {
		logger.info("CommonUtil >> generateTransactionToken(String username) >> START");
		String generatedTransactionToken = null;
		if (null != username && (!"".equals(username.trim()))) {
			long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
			Map<String, Object> claims = new HashMap<>();
			// username as subject
			generatedTransactionToken = Jwts.builder().setClaims(claims).setSubject(username)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		}
		logger.info("CommonUtil >> generateTransactionToken(String username) >> END");
		return generatedTransactionToken;
	}

	public String revokeToken(String username) {
		logger.info("CommonUtil >> revokeToken(String username) >> START");
		String revokedTokenToken = null;
		if (null != username && (!"".equals(username.trim()))) {
			long JWT_TOKEN_VALIDITY = 60;
			Map<String, Object> claims = new HashMap<>();
			// username as subject
			revokedTokenToken = Jwts.builder().setClaims(claims).setSubject(username)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		}
		logger.info("CommonUtil >> revokeToken(String username) >> END");
		return revokedTokenToken;
	}

	public boolean validateOrder(Orderdet orderobj) {
		Optional<Orderdet> resOrder = repository.findById(orderobj.getOrderid());
		Orderdet order = resOrder.get();
		if (order != null) {
			return validateTransactionToken(order.getTranstoken(), order.getUsername());
		}
		return false;
	}

	public Boolean validateTransactionToken(String token, String username) {
		String uname = getUserFromToken(token);
		return (uname.equals(username) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// retrieve order id from jwt token
	public String getUserFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
}
