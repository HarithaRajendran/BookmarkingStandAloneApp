package com.asahi.bookmarkingApp.entities;

import com.asahi.bookmarkingApp.partner.Shareable;

public class WebLink extends Bookmark implements Shareable{
	
	private String url;
	private String host;
	private String htmlPage;
	private DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED;

	public enum DownloadStatus{
		NOT_ATTEMPTED,
		SUCCESS,
		FAILURE,
		NOT_ELIGIBLE
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "WebLink [url=" + url + ", host=" + host + "]";
	}

	@Override
	public boolean isKidFriendlyEligible() {
		if(url.contains("porn") || getTitle().contains("porn")|| host.contains("adult")) {
			return false;
		}
		return true;
	}

	@Override
	public String getItemData() {
		StringBuilder builder=new StringBuilder();
		builder.append("<item>\n");
			builder.append("\t<type> WebLink </type>\n");
			builder.append("\t\t<title> ").append(getTitle()).append(" </title>\n");
			builder.append("\t\t<url> ").append(url).append(" </url>\n");
			builder.append("\t\t<host> ").append(host).append(" </host>\n");
		builder.append("</item>");
		
		return builder.toString();
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

}
