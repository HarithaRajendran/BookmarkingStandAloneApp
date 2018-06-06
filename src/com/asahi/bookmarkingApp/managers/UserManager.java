package com.asahi.bookmarkingApp.managers;

import java.util.List;

import com.asahi.bookmarkingApp.constants.Gender;
import com.asahi.bookmarkingApp.constants.UserType;
import com.asahi.bookmarkingApp.dao.UserDao;
import com.asahi.bookmarkingApp.entities.User;

public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao=new UserDao();

	private UserManager() {

	}

	public static UserManager getInstance() {
		return instance;
	}

	public User createUser(long id, String email, String password, String firstName, String lastName, Gender gender,
			UserType userType) {
		
		User user=new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);
		
		return user;
	}
	
	public List<User> getUsers() {
		return dao.getUsers();
	}
}
