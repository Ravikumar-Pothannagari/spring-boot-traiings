/**
 * 
 */
package com.ibm.userauthenticationservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.userauthenticationservice.model.User;

/**
 * @author RavikumarPothannagar
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsernameAndPassword(String username,String password);
	List<User> findByUsername(String username);

}
