package com.tcs.dao;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcs.model.User;
import com.tcs.model.UserRole;


@Repository
public class UserDaoImpl implements UserDaoInterface {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public User findUserByUsername(String username) {
		
		System.out.println("Inside findUserByUsername : ");
		
		// creates a session with the back-end database.
		Session session = sessionFactory.getCurrentSession();
		
		Transaction t1 = session.beginTransaction();
		
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.ilike("username", username));
		User user = (User) cr.uniqueResult();
		
		
	/*	Query q1 = session.createQuery("from User where username = :username");
		q1.setParameter("username", username);
		User user = (User) q1.uniqueResult();  */
		
		
		/*Query q2 = session.createQuery("from UserRole where username = :username");
		q2.setParameter("username", username);
		List<UserRole> roles = q2.list();
		Set<UserRole> userRoles = new HashSet<UserRole>(roles);
		user.setUserRoles(userRoles);*/
		
		
		if(user != null)
		{
			System.out.println("user is not null in DAO");
			System.out.println("Username : " + user.getUsername());
			System.out.println("Password : " + user.getPassword());
			System.out.println("Enabled : " + user.isEnabled());
			//Set<UserRole> userRoles = user.getUserRoles();
			
			System.out.println("1 here");
			
				Iterator<UserRole> itr = user.getUserRoles().iterator();
				while(itr.hasNext())
				{
					UserRole userRole = itr.next();
					System.out.println("Role : " + userRole.getRole());
				}
				
				System.out.println("2 here");
			
			
		}
		t1.commit();
		//session.close();
		return user;
	}
		

}
