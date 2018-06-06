package com.asahi.bookmarkingApp.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.asahi.bookmarkingApp.constants.BookGenre;
import com.asahi.bookmarkingApp.managers.BookmarkManager;

class BookTest {

	@Test
	void testIsKidFriendlyEligible() {
		//test1
		Book book=BookmarkManager.getInstance().createBook(4000,	"Walden",1854,"Wilder Publications",new String[] {"Henry David Thoreau"},BookGenre.PHILOSOPHY,4.3);
		
		boolean isKidFriendlyEligible=book.isKidFriendlyEligible();
		
		assertFalse("philosophy genre-false",isKidFriendlyEligible);
		
		//test2
		BookmarkManager.getInstance().createBook(4000,	"Walden",1854,"Wilder Publications",new String[] {"Henry David Thoreau"},BookGenre.SELF_HELP,4.3);
		
		isKidFriendlyEligible=book.isKidFriendlyEligible();
		
		assertFalse("self help genre-false",isKidFriendlyEligible);
		
	}

}
