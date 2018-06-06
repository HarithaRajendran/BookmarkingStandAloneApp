package com.asahi.bookmarkingApp.controllers;

import com.asahi.bookmarkingApp.constants.KidFriendlyStatus;
import com.asahi.bookmarkingApp.entities.Bookmark;
import com.asahi.bookmarkingApp.entities.User;
import com.asahi.bookmarkingApp.managers.BookmarkManager;

public class BookmarkController {
	private static BookmarkController instance=new BookmarkController();
	private BookmarkController() {}
	
	public static BookmarkController getInstance() {
		return instance;
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().saveUserbookmark(user,bookmark);
	}

	public void setIsKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		BookmarkManager.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus,bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user,bookmark);
	}
}
