package com.asahi.bookmarkingApp.entities;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.asahi.bookmarkingApp.constants.BookGenre;
import com.asahi.bookmarkingApp.partner.Shareable;

public class Book extends Bookmark implements Shareable{
	
	private int publicationYear;
	private String publisher;
	private String[] author;
	private BookGenre genre;
	private double amazonRating;

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String[] getAuthor() {
		return author;
	}

	public void setAuthor(String[] author) {
		this.author = author;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	@Override
	public String toString() {
		return "Book [publicationYear=" + publicationYear + ", publisher=" + publisher + ", author="
				+ Arrays.toString(author) + ", genre=" + genre + ", amazonRating=" + amazonRating + "]";
	}

	@Override
	public boolean isKidFriendlyEligible() {
		if(genre.equals(BookGenre.PHILOSOPHY)||genre.equals(BookGenre.SELF_HELP)) {
			return false;
		}
		return true;
	}

	@Override
	public String getItemData() {
		StringBuilder builder=new StringBuilder();
		builder.append("<item>\n");
			builder.append("\t<type> Book </type>\n");
			builder.append("\t\t<title> ").append(getTitle()).append(" </title>\n");
			builder.append("\t\t<authors> ").append(StringUtils.join(author,",")).append(" </authors>\n");
			builder.append("\t\t<publicationYear> ").append(publicationYear).append(" </publicationYear>\n");
			builder.append("\t\t<genre> ").append(genre).append(" </genre>\n");
			builder.append("\t\t<amazonRating> ").append(amazonRating).append(" </amazonRating>\n");
		builder.append("</item>");
		
		return builder.toString();
	}

}
