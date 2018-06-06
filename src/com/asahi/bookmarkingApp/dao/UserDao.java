package com.asahi.bookmarkingApp.dao;

import java.util.List;

import com.asahi.bookmarkingApp.DataStore;
import com.asahi.bookmarkingApp.entities.User;

public class UserDao {
	public List<User> getUsers() {
		return DataStore.getUsers();
	}
}
