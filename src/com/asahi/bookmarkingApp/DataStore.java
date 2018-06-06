package com.asahi.bookmarkingApp;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.asahi.bookmarkingApp.constants.BookGenre;
import com.asahi.bookmarkingApp.constants.Gender;
import com.asahi.bookmarkingApp.constants.MovieGenre;
import com.asahi.bookmarkingApp.constants.UserType;
import com.asahi.bookmarkingApp.entities.Bookmark;
import com.asahi.bookmarkingApp.entities.User;
import com.asahi.bookmarkingApp.entities.UserBookmark;
import com.asahi.bookmarkingApp.managers.BookmarkManager;
import com.asahi.bookmarkingApp.managers.UserManager;
import com.asahi.bookmarkingApp.util.IOUtil;

public class DataStore {

	private static List<User> users = new ArrayList<>();
	private static List<List<Bookmark>> bookmarks = new ArrayList<>();

	private static List<UserBookmark> userBookmarks = new ArrayList<>();

	static void loadData() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false", "root", "root");
				Statement stmt = con.createStatement()){
			loadUsers(stmt);
			loadWebLinks(stmt);
			loadMovies(stmt);
			loadBooks(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void loadUsers(Statement stmt) throws SQLException {
		// users[0]=UserManager.getInstance().createUser(1000, "user0@asahi.com",
		// "pass","Hari","Raj",Gender.FEMALE,UserType.USER);
		// users[1]=UserManager.getInstance().createUser(1001, "user1@asahi.com",
		// "pass","Achu","Raj",Gender.FEMALE,UserType.USER);
		// users[2]=UserManager.getInstance().createUser(1002, "user2@asahi.com",
		// "pass","Latha","Raj",Gender.FEMALE,UserType.EDITOR);
		// users[3]=UserManager.getInstance().createUser(1003, "user3@asahi.com",
		// "pass","Hari","Raj",Gender.MALE,UserType.EDITOR);
		// users[4]=UserManager.getInstance().createUser(1004, "user4@asahi.com",
		// "pass","Hari","Raj",Gender.FEMALE,UserType.CHIEF_EDITOR);

		// -----String[] data=new String[TOTAL_USER_COUNT];
		/*List<String> data = new ArrayList<>();
		IOUtil.read(data, "User");

		for (String row : data) {
			String[] values = row.split("\t");

			Gender gender = Gender.MALE;
			if (values[5].equals("f")) {
				gender = Gender.FEMALE;
			} else if (values[5].equals("t")) {
				gender = Gender.TRANSGENDER;
			}

			User user = UserManager.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3],
					values[4], gender, values[6]);
			users.add(user);
		}
*/
		
		String query = "Select * from User";
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			long id = rs.getLong("id");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int gender_id = rs.getInt("gender_id");
			Gender gender = Gender.values()[gender_id];
			int user_type_id = rs.getInt("user_type_id");
			UserType userType = UserType.values()[user_type_id];
			
			User user = UserManager.getInstance().createUser(id, email, password, firstName,
					lastName, gender, userType);
			users.add(user);
		}
	}

	private static void loadWebLinks(Statement stmt) throws SQLException {
		/*
		 * bookmarks[0][0]=BookmarkManager.getInstance().createWebLink(2000,
		 * "Taming Tiger, Part 1",
		 * "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-1.html",
		 * "http://www.javaworld.com");
		 * bookmarks[0][1]=BookmarkManager.getInstance().createWebLink(2001,
		 * "Taming Tiger, Part 2",
		 * "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
		 * "http://www.javaworld.com");
		 * bookmarks[0][2]=BookmarkManager.getInstance().createWebLink(2002,
		 * "Taming Tiger, Part 3",
		 * "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-3.html",
		 * "http://www.javaworld.com");
		 * bookmarks[0][3]=BookmarkManager.getInstance().createWebLink(2003,
		 * "Taming Tiger, Part 4",
		 * "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-4.html",
		 * "http://www.javaworld.com");
		 * bookmarks[0][4]=BookmarkManager.getInstance().createWebLink(2004,
		 * "Taming Tiger, Part 5",
		 * "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-5.html",
		 * "http://www.javaworld.com");
		 */

		/*List<String> data = new ArrayList<>();
		IOUtil.read(data, "Web-Link");
		List<Bookmark> bookmarkList = new ArrayList<>();
		for (String row : data) {
			String[] values = row.split("\t");
			Bookmark bookmark = BookmarkManager.getInstance().createWebLink(Long.parseLong(values[0]), values[1],
					values[2], values[3] ,values[4] );
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);*/
		
		String query = "Select * from WebLink";
		ResultSet rs = stmt.executeQuery(query);
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		while(rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			String url = rs.getString("url");
			String host = rs.getString("host");
			Bookmark bookmark = BookmarkManager.getInstance().createWebLink(id, title,
					url, host );
			bookmarkList.add(bookmark);
			
		}
		bookmarks.add(bookmarkList);
	}

	private static void loadMovies(Statement stmt) throws SQLException {
		/*
		 * bookmarks[1][0]=BookmarkManager.getInstance().createMovie(3000,
		 * "Citizen Kane","",1941,new String[] {"Orson Welles","Joseph Cotten"},new
		 * String[] {"Orson Welles"},MovieGenre.CLASSICS,8.5);
		 * bookmarks[1][1]=BookmarkManager.getInstance().createMovie(3001,
		 * "Citizen Kane","",1942,new String[] {"Orson Welles","Joseph Cotten"},new
		 * String[] {"Orson Welles"},MovieGenre.COMEDY,8.5);
		 * bookmarks[1][2]=BookmarkManager.getInstance().createMovie(3002,
		 * "Citizen Kane","",1943,new String[] {"Orson Welles","Joseph Cotten"},new
		 * String[] {"Orson Welles"},MovieGenre.DRAMA,8.5);
		 * bookmarks[1][3]=BookmarkManager.getInstance().createMovie(3003,
		 * "Citizen Kane","",1944,new String[] {"Orson Welles","Joseph Cotten"},new
		 * String[] {"Orson Welles"},MovieGenre.HORROR,8.5);
		 * bookmarks[1][4]=BookmarkManager.getInstance().createMovie(3004,
		 * "Citizen Kane","",1945,new String[] {"Orson Welles","Joseph Cotten"},new
		 * String[] {"Orson Welles"},MovieGenre.THRILLERS,8.5);
		 */

	/*	List<String> data = new ArrayList<>();
		IOUtil.read(data, "Movie");

		List<Bookmark> bookmarkList = new ArrayList<>();
		for (String row : data) {
			String[] values = row.split("\t");
			String[] cast = values[3].split(",");
			String[] directors = values[4].split(",");
			Bookmark bookmark = BookmarkManager.getInstance().createMovie(Long.parseLong(values[0]), values[1], "",
					Integer.parseInt(values[2]), cast, directors, MovieGenre.valueOf(values[5]),
					Double.parseDouble(values[6]) , values[7] );
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);*/
		
		String query = "Select Movie.id,title ,release_year,GROUP_CONCAT(Actor.name SEPARATOR ',') AS cast,GROUP_CONCAT(Director.name SEPARATOR ',') AS directors ,movie_genre_id,imdb_rating,kid_friendly_status"
				+ " from Movie,Actor,Movie_Actor,Director,Movie_Director "
				+ " where Movie.id = Movie_Actor.movie_id and Movie_Actor.actor_id = Actor.id and "
				+ " Movie.id = Movie_Director.movie_id and Movie_Director.director_id = Movie_Director.id group by Movie.id";
		ResultSet rs = stmt.executeQuery(query);
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		while(rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int releaseYear = rs.getInt("release_year");
			String[] cast = rs.getString("cast").split(",");
			String[] directors = rs.getString("directors").split(",");
			int genre_id = rs.getInt("movie_genre_id");
			MovieGenre genre = MovieGenre.values()[genre_id];
			double imdbRating = rs.getDouble("imdb_rating");
			
			Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title, "",
					releaseYear, cast, directors, genre,
					imdbRating);
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
		
	}

	private static void loadBooks(Statement stmt) throws SQLException{
		/*
		 * bookmarks[2][0]=BookmarkManager.getInstance().createBook(4000,
		 * "Walden",1854,"Wilder Publications",new String[]
		 * {"Henry David Thoreau"},BookGenre.BIOGRAPHY,4.3);
		 * bookmarks[2][1]=BookmarkManager.getInstance().createBook(4001,
		 * "Walden",1855,"Wilder Publications",new String[]
		 * {"Henry David Thoreau"},BookGenre.PHILOSOPHY,4.3);
		 * bookmarks[2][2]=BookmarkManager.getInstance().createBook(4002,
		 * "Walden",1846,"Wilder Publications",new String[]
		 * {"Henry David Thoreau"},BookGenre.ROMANCE,4.3);
		 * bookmarks[2][3]=BookmarkManager.getInstance().createBook(4003,
		 * "Walden",1857,"Wilder Publications",new String[]
		 * {"Henry David Thoreau"},BookGenre.SELF_HELP,4.3);
		 * bookmarks[2][4]=BookmarkManager.getInstance().createBook(4004,
		 * "Walden",1858,"Wilder Publications",new String[]
		 * {"Henry David Thoreau"},BookGenre.CHILDREN,4.3);
		 */
		/*List<String> data = new ArrayList<>();
		IOUtil.read(data, "Book");

		List<Bookmark> bookmarkList = new ArrayList<>();
		for (String row : data) {
			String[] values = row.split("\t");
			String[] author = values[4].split(",");
			Bookmark bookmark = BookmarkManager.getInstance().createBook(Long.parseLong(values[0]), values[1],
					Integer.parseInt(values[2]), values[3], author, BookGenre.valueOf(values[5]), Double.parseDouble(values[6]));
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);*/
		
		String query = "Select Book.id , title,publication_year,Publisher.name,GROUP_CONCAT(Author.name SEPARATOR ',') AS authors,book_genre_id,amazon_rating,created_date "
				+"from Book,Publisher,Author,Book_Author "
				+"where Book.publisher_id = Publisher.id and Book.id = Book_Author.book_id and Book_Author.author_id = Author.id group by Book.id";
		
			ResultSet rs = stmt.executeQuery(query);
			
			List<Bookmark> bookmarkList = new ArrayList<>();
			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				int publicationYear = rs.getInt("publication_year");
				String publisher = rs.getString("name");
				String[] authors = rs.getString("authors").split(",");
				int genre_id = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");
				
				Date createdDate = rs.getDate("created_date");
			//	System.out.println("createdDate : "+createdDate);
				Timestamp timeStamp = rs.getTimestamp(8);
			//	System.out.println("Timestamp: "+timeStamp.toLocalDateTime());
			//	System.out.println("LocalDateTime: "+timeStamp.toLocalDateTime());
				
				
				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title,
						publicationYear, publisher , authors, genre, amazonRating);
				bookmarkList.add(bookmark);
			}
			bookmarks.add(bookmarkList);

	}

	public static List<User> getUsers() {
		return users;
	}

	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}

	public static void add(UserBookmark userBookmark) {
		userBookmarks.add(userBookmark);
	}

}
