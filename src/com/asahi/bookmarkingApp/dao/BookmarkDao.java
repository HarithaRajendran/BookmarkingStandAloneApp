package com.asahi.bookmarkingApp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.asahi.bookmarkingApp.DataStore;
import com.asahi.bookmarkingApp.entities.Book;
import com.asahi.bookmarkingApp.entities.Bookmark;
import com.asahi.bookmarkingApp.entities.Movie;
import com.asahi.bookmarkingApp.entities.UserBookmark;
import com.asahi.bookmarkingApp.entities.WebLink;

public class BookmarkDao {
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveUserBookmark(UserBookmark userBookmark) {
		// DataStore.add(userBookmark);

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false",
				"root", "root"); Statement stmt = con.createStatement()) {

			if (userBookmark.getBookmark() instanceof Book) {
				saveUserBook(userBookmark, stmt);
			} else if (userBookmark.getBookmark() instanceof Movie) {
				saveUserMovie(userBookmark, stmt);
			} else {
				saveUserWebLink(userBookmark, stmt);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into User_Book (user_id ,book_id) values (" + userBookmark.getUser().getId() + ","
				+ userBookmark.getBookmark().getId() + ")";

		stmt.executeUpdate(query);
	}

	private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into User_Movie (user_id ,movie_id) values (" + userBookmark.getUser().getId() + ","
				+ userBookmark.getBookmark().getId() + ")";

		stmt.executeUpdate(query);
	}

	private void saveUserWebLink(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into User_WebLink (user_id ,weblink_id) values (" + userBookmark.getUser().getId() + ","
				+ userBookmark.getBookmark().getId() + ")";

		stmt.executeUpdate(query);
	}

	// Later use for Data Base
	public List<WebLink> getAllWebLinks() {
		List<WebLink> result = new ArrayList<WebLink>();

		List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
		List<Bookmark> allWebLinks = bookmarks.get(0);

		for (Bookmark bookmark : allWebLinks) {
			result.add((WebLink) bookmark);
		}
		return result;
	}

	public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
		List<WebLink> result = new ArrayList<>();

		List<WebLink> allWebLinks = getAllWebLinks();

		for (WebLink webLink : allWebLinks) {
			if (webLink.getDownloadStatus().equals(downloadStatus)) {
				result.add(webLink);
			}
		}
		return result;
	}

	public void updateKidFriendlyStatus(Bookmark bookmark) {
		int kidFriendlyStatus = bookmark.getIsKidFriendlyStatus().ordinal();
		long userId = bookmark.getKidFriendlyMarkedBy().getId();

		String tableToUpdate = "Book";

		if (bookmark instanceof Movie) {
			tableToUpdate = "Movie";
		} else if (bookmark instanceof WebLink) {
			tableToUpdate = "WebLink";
		}

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false",
				"root", "root"); Statement stmt = con.createStatement()) {
			String query = "update " + tableToUpdate + " set kid_friendly_status = " + kidFriendlyStatus
					+ ", kid_friendly_marked_by = " + userId + " where id = " + bookmark.getId();
			
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sharedByInfo(Bookmark bookmark) {
		long userId = bookmark.getSharedBy().getId();
		
		String tableToUpdate = "Book";
		if(bookmark instanceof WebLink) {
			tableToUpdate = "WebLink";
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?userSSL=false", "root", "root");
				Statement stmt = con.createStatement()){
			
			String query = "update "+ tableToUpdate + " set shared_by = "+ userId +" where id = " + bookmark.getId();
			System.out.println("query(updateKidFriendlyStatus ): "+query);
			
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
