/**
 * 
 */
package com.ibm.orderservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.orderservice.model.Orderdet;

/**
 * @author RavikumarPothannagar
 *
 */
public interface OrderRepository extends JpaRepository<Orderdet, Long> {
	
	List<Orderdet> findByUsername(String username);

}
