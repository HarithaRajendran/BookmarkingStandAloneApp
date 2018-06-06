package com.asahi.bookmarkingApp.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.asahi.bookmarkingApp.managers.BookmarkManager;

class WebLinkTest {

	@Test
	void testIsKidFriendlyEligible() {
		//test1: porn in url -- false
		WebLink webLink=BookmarkManager.getInstance().createWebLink(2000,	"Taming Tiger, Part 1",	"http://www.javaworld.com/article/2072759/core-java/taming-porn--part-1.html", "http://www.javaworld.com");
		
		boolean isKidFriendlyEligible=webLink.isKidFriendlyEligible();
		
		assertFalse("Porn in url - isKidFriendlyEligible method return false",isKidFriendlyEligible);
		
		//test2: porn in title --false
		webLink=BookmarkManager.getInstance().createWebLink(2000,	"Taming porn, Part 1",	"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-1.html", "http://www.javaworld.com");
		
		isKidFriendlyEligible=webLink.isKidFriendlyEligible();
		
		assertFalse("Porn in title - isKidFriendlyEligible method return false",isKidFriendlyEligible);
		
		//test3: adult in host --false
		webLink=BookmarkManager.getInstance().createWebLink(2000,	"Taming Tiger, Part 1",	"http://www.javaworld.com/article/2072759/core-java/taming-porn--part-1.html", "http://www.adult.com");
		
		isKidFriendlyEligible=webLink.isKidFriendlyEligible();
		
		assertFalse("adult in host - isKidFriendlyEligible method return false",isKidFriendlyEligible);
		
		//test4:adult in url , but not in host part --true
		webLink=BookmarkManager.getInstance().createWebLink(2000,	"Taming Tiger, Part 1",	"http://www.javaworld.com/article/2072759/core-java/taming-adult--part-1.html", "http://www.javaworld.com");
		
		isKidFriendlyEligible=webLink.isKidFriendlyEligible();
		
		assertTrue("adult in url, but not in host - isKidFriendlyEligible method return true",isKidFriendlyEligible);
		
		
		//test5:adult in title only -- true
        webLink=BookmarkManager.getInstance().createWebLink(2000,	"Taming adult, Part 1",	"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-1.html", "http://www.javaworld.com");
		
		isKidFriendlyEligible=webLink.isKidFriendlyEligible();
		
		assertTrue("adult in title - isKidFriendlyEligible method return true",isKidFriendlyEligible);
	}

}
