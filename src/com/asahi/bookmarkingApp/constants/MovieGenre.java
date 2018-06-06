package com.asahi.bookmarkingApp.constants;

public enum MovieGenre {
	
	CLASSICS("Classics"),
	DRAMA("Drama"),
	COMEDY("Comedy"),
	THRILLERS("Thrillers"),
	SHOWS("Shows"),
	HORROR("Horor");
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
//	CLASSICS("Classics"),
	

	private MovieGenre(String name) {
		this.name = name;
	}
	
	private String name;
	public String getName() {
		return name;
	}
}
