package com.asahi.bookmarkingApp.entities;

import com.asahi.bookmarkingApp.constants.KidFriendlyStatus;

public abstract class Bookmark {
	
	private long id;
	private String title;
	private String profileUrl;
	private KidFriendlyStatus isKidFriendlyStatus=KidFriendlyStatus.UNKNOWN;
	private User kidFriendlyMarkedBy;
	private User sharedBy;

	public KidFriendlyStatus getIsKidFriendlyStatus() {
		return isKidFriendlyStatus;
	}

	public void setIsKidFriendlyStatus(KidFriendlyStatus isKidFriendlyStatus) {
		this.isKidFriendlyStatus = isKidFriendlyStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	public abstract boolean isKidFriendlyEligible();

	public User getKidFriendlyMarkedBy() {
		return kidFriendlyMarkedBy;
	}

	public void setKidFriendlyMarkedBy(User kidFriendlyMarkedBy) {
		this.kidFriendlyMarkedBy = kidFriendlyMarkedBy;
	}

	public User getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(User sharedBy) {
		this.sharedBy = sharedBy;
	}

}
