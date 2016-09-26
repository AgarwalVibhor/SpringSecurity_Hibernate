package com.tcs.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.dao.UserDaoInterface;
import com.tcs.model.UserRole;

@Service("customService")
public class MyUserDetailsService implements UserDetailsService{
	/*
	 * Create a custom UserDetailsService, load com.tcs.model.User from DAO and build this user's 
	 * authorities.
	 * In the end return org.springframework.security.core.userdetails.User
	 */
	
	@Autowired
	private UserDaoInterface dao;
	
	// get user from the database via hibernate
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
	
		System.out.println("Inside loadUserByUsername : ");
		com.tcs.model.User user = dao.findUserByUsername(username);
		System.out.println("after dao, user is : " + user);
		if(user != null)
		{
			System.out.println("user is not null in Service");
			System.out.println("Username : " + user.getUsername());
			System.out.println("Password : " + user.getPassword());
			System.out.println("Enabled : " + user.isEnabled());
			Set<UserRole> userRoles = user.getUserRoles();
			System.out.println("userRoles : " + userRoles);
			if(userRoles != null)
			{
				Iterator<UserRole> itr = userRoles.iterator();
				while(itr.hasNext())
				{
					UserRole userRole = itr.next();
					System.out.println("Role : " + userRole.getRole());
				}
			}
			else
			{
				System.out.println("No roles here !");
			}
		}
		else
		{
			System.out.println("user is null in Service");
		}
		System.out.println("-------------------------");
		List<GrantedAuthority> userAuthorities = buildUserAuthority(user.getUserRoles());
		/*
		 * 'userAuthorities' is a list containing objects of the implementation class of GrantedAuthority :
		 * SimpleGrantedAuthority
		 */
		System.out.println("Hello 1");
		UserDetails userDetails = buildUserForAuthentication(user, userAuthorities);
		System.out.println("Hello 2");
		return userDetails;
	}
	
	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles)
	{
		System.out.println("Inside buildUserAuthority : ");
		System.out.println("-------------------------");
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if(userRoles != null)
		{
			System.out.println("Hello 3");
		}
		/*
		 * GrantedAuthority is an interface. So 'authorities' cannot have objects of GrantedAuthority.
		 * 'authorities' can contain objects of 'implementation class of GrantedAuthority : SimpleGrantedAuthority'.
		 */
	/*	for(UserRole userRole : userRoles)
		{
			System.out.println("Inside loop");
			authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		
		*/
		Iterator<UserRole> itr = userRoles.iterator();
		if(itr.hasNext())
		{
			System.out.println("Has Next");
		}
		else
		{
			System.out.println("No Next");
		}
		while(itr.hasNext())
		{
			System.out.println("Inside loop");
			UserRole userRole =itr.next();
			String role = userRole.getRole();
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		System.out.println("Hello 4");
		
		List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>(authorities);
		/*
		 * 'userAuthorities' is a list containing objects of the implementation class of GrantedAuthority :
		 * SimpleGrantedAuthority
		 */
		
		return userAuthorities;
	}
	
	
	private User buildUserForAuthentication(com.tcs.model.User user, List<GrantedAuthority> authorities)
	{
		System.out.println("Inside buildUserForAuthentication : ");
		System.out.println("Username : " + user.getUsername());
		System.out.println("Password : " + user.getPassword());
		System.out.println("Enabled : " + user.isEnabled());
		Iterator<GrantedAuthority> itr = authorities.iterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
		System.out.println("---------------------------------");
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	
	
	

}
