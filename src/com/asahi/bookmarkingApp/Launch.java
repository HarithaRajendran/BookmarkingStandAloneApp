package com.asahi.bookmarkingApp;

import java.util.List;

import com.asahi.bookmarkingApp.bgjobs.WebpageDownloaderTask;
import com.asahi.bookmarkingApp.entities.Bookmark;
import com.asahi.bookmarkingApp.entities.User;
import com.asahi.bookmarkingApp.managers.BookmarkManager;
import com.asahi.bookmarkingApp.managers.UserManager;

public class Launch {
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;
	
	private static void loadData() {
		System.out.println("Loading Data...");
		System.out.println();
		DataStore.loadData();
		
		users=UserManager.getInstance().getUsers();
		bookmarks=BookmarkManager.getInstance().getBookmarks();
				
//		System.out.println("Printing Data");
//		System.out.println();
//		printUserData();
//		printBookmarkData();
	}

	private static void printBookmarkData() {
		for(List<Bookmark> bookmarkList:bookmarks) {
			for(Bookmark bookmark:bookmarkList) {
				System.out.println(bookmark);
			}
			System.out.println();
		}
	}

	private static void printUserData() {
		for(User user:users) {
			System.out.println(user);	
		}
		System.out.println();
	}
	
	private static void start() {
		//System.out.println("2.Bookmarking.....");
		for(User user:users) {
			View.browse(user,bookmarks);
		}
	}

	public static void main(String[] args) {
		loadData();
		start();
		
		//Background job
		//runDownloaderJob();
	}
	
	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}
	
}
