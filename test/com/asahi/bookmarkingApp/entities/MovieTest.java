package com.asahi.bookmarkingApp.entities;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.asahi.bookmarkingApp.constants.MovieGenre;
import com.asahi.bookmarkingApp.managers.BookmarkManager;

class MovieTest {

	@Test
	void testIsKidFriendlyEligible() {
		//test1
		Movie movie=BookmarkManager.getInstance().createMovie(3000,	"Citizen Kane","",1941,new String[] {"Orson Welles","Joseph Cotten"},new String[] {"Orson Welles"},MovieGenre.HORROR,8.5);
		
		boolean isKidFriendlyEligible=movie.isKidFriendlyEligible();
		
		assertFalse("For HORROR genre-false",isKidFriendlyEligible);
		
		//test2
		movie=BookmarkManager.getInstance().createMovie(3000,	"Citizen Kane","",1941,new String[] {"Orson Welles","Joseph Cotten"},new String[] {"Orson Welles"},MovieGenre.THRILLERS,8.5);
	
        isKidFriendlyEligible=movie.isKidFriendlyEligible();
		
		assertFalse("For THRILLERS genre-false",isKidFriendlyEligible);
	}

}
