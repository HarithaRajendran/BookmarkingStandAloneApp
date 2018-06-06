package com.asahi.bookmarkingApp.managers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import com.asahi.bookmarkingApp.DataStore;
import com.asahi.bookmarkingApp.constants.BookGenre;
import com.asahi.bookmarkingApp.constants.KidFriendlyStatus;
import com.asahi.bookmarkingApp.constants.MovieGenre;
import com.asahi.bookmarkingApp.dao.BookmarkDao;
import com.asahi.bookmarkingApp.entities.Book;
import com.asahi.bookmarkingApp.entities.Bookmark;
import com.asahi.bookmarkingApp.entities.Movie;
import com.asahi.bookmarkingApp.entities.User;
import com.asahi.bookmarkingApp.entities.UserBookmark;
import com.asahi.bookmarkingApp.entities.WebLink;
import com.asahi.bookmarkingApp.util.HttpConnect;
import com.asahi.bookmarkingApp.util.IOUtil;

public class BookmarkManager {

	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {
	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenre genre, double imdbRating) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);
		return movie;
	}

	public Book createBook(long id, String title, int publicationYear, String publisher, String[] author, BookGenre genre,
			double amazonRating) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthor(author);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);
		return book;
	}

	public WebLink createWebLink(long id, String title, String url, String host) {
		WebLink webLink = new WebLink();
		webLink.setId(id);
		webLink.setTitle(title);
		webLink.setUrl(url);
		webLink.setHost(host);
		return webLink;
	}

	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveUserbookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);

		/*if (bookmark instanceof WebLink) {
			try {
				String url = ((WebLink) bookmark).getUrl();
				if (!url.endsWith(".pdf")) {
					String webPage = HttpConnect.download(url);

					if (webPage != null) {
						IOUtil.write(webPage, bookmark.getId());
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}*/

		dao.saveUserBookmark(userBookmark);
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		bookmark.setIsKidFriendlyStatus(kidFriendlyStatus);
		bookmark.setKidFriendlyMarkedBy(user);
		
		dao.updateKidFriendlyStatus(bookmark);
		System.out.println(
				"Kid friendly status " + kidFriendlyStatus + " , Marked By: " + user.getEmail() + " , " + bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		bookmark.setSharedBy(user);

		System.out.println("Data to be shared: ");
		if (bookmark instanceof Book) {
			System.out.println(((Book) bookmark).getItemData());
		} else if (bookmark instanceof WebLink) {
			System.out.println(((WebLink) bookmark).getItemData());
		}
		
		dao.sharedByInfo(bookmark);
	}

}
