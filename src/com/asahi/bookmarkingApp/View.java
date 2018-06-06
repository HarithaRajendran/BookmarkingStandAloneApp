package com.asahi.bookmarkingApp;

import java.util.List;

import com.asahi.bookmarkingApp.constants.KidFriendlyStatus;
import com.asahi.bookmarkingApp.constants.UserType;
import com.asahi.bookmarkingApp.controllers.BookmarkController;
import com.asahi.bookmarkingApp.entities.Bookmark;
import com.asahi.bookmarkingApp.entities.User;
import com.asahi.bookmarkingApp.partner.Shareable;

public class View {
	public static void browse(User user, List<List<Bookmark>> bookmarks) {

		System.out.println("\n" + user.getEmail() + " is browsing items....");
		int bookmarkCount = 0;

		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				// Bookmarking..
				//if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean isBookmarked = getBookmarkDecision(bookmark);
					if (isBookmarked) {
						bookmarkCount++;

						BookmarkController.getInstance().saveUserBookmark(user, bookmark);

						System.out.println("New Item Bookmarked --" + bookmark);
					}
				//}
				
				if ((user.getUserType().equals(UserType.EDITOR))
						|| (user.getUserType().equals(UserType.CHIEF_EDITOR))) {
					
					// mark as kid Friendly
					if ((bookmark.isKidFriendlyEligible())
							&& (bookmark.getIsKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN))) {
						KidFriendlyStatus kidFriendlyStatus=getKidFriendlyStatusDecision(bookmark);
						if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setIsKidFriendlyStatus(user,kidFriendlyStatus,bookmark);
						}
					}
					//Sharing!!
					if(bookmark.getIsKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED)
							&& bookmark instanceof Shareable){
						boolean isShared=getShareDecision();
						if(isShared) {
							BookmarkController.getInstance().share(user,bookmark);
						}
					}
				}
			}

		}
		// for(int i=0;i<DataStore.USER_BOOKMARK_LIMIT;i++) {
		//
		// int typeOffSet=(int)(Math.random()*DataStore.BOOKMARK_TYPES_COUNT);
		// int bookmarkOffSet=(int)(Math.random()*DataStore.BOOKMARK_TYPE_PER_COUNT);
		//
		// Bookmark bookmark=bookmarks[typeOffSet][bookmarkOffSet];
		//
		// BookmarkController.getInstance().saveUserBookmark(user,bookmark);
		//
		// System.out.println(bookmark);
		// }
	}

	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true : false;
	}

	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
		double decision = Math.random();
		return decision < 0.4 ? KidFriendlyStatus.APPROVED
				: (decision > 0.4 && decision < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
	}

	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;
	}

	/*
	 * public static void bookmark(User user,Bookmark[][] bookmarks) {
	 * 
	 * System.out.println("\n"+ user.getEmail()+" is bookmarking");
	 * 
	 * for(int i=0;i<DataStore.USER_BOOKMARK_LIMIT;i++) {
	 * 
	 * int typeOffSet=(int)(Math.random()*DataStore.BOOKMARK_TYPES_COUNT); int
	 * bookmarkOffSet=(int)(Math.random()*DataStore.BOOKMARK_TYPE_PER_COUNT);
	 * 
	 * Bookmark bookmark=bookmarks[typeOffSet][bookmarkOffSet];
	 * 
	 * BookmarkController.getInstance().saveUserBookmark(user,bookmark);
	 * 
	 * System.out.println(bookmark); } }
	 */
}
