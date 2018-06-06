package com.asahi.bookmarkingApp.bgjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.asahi.bookmarkingApp.dao.BookmarkDao;
import com.asahi.bookmarkingApp.entities.WebLink;
import com.asahi.bookmarkingApp.util.HttpConnect;
import com.asahi.bookmarkingApp.util.IOUtil;

public class WebpageDownloaderTask implements Runnable {

	private boolean downloadAll = false;

	private static BookmarkDao dao = new BookmarkDao();

	private static final long TIME_FRAME = 3000000000l;

	ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);

	private static class Downloader<T extends WebLink> implements Callable<T> {
		private T weblink;

		public Downloader(T weblink) {
			this.weblink = weblink;
		}

		public T call() {

			String htmlPage;
			try {
				if (!weblink.getUrl().endsWith(".pdf")) {
					weblink.setDownloadStatus(WebLink.DownloadStatus.FAILURE);
					htmlPage = HttpConnect.download(weblink.getUrl());
					weblink.setHtmlPage(htmlPage);
				} else {
					weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			return weblink;

		}
	}

	public WebpageDownloaderTask(Boolean downloadAll) {
		this.downloadAll = downloadAll;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			// Get WebLinks
			List<WebLink> webLinks = getWebLinks();
			// Download concurrently
			if (webLinks.size() > 0) {
				download(webLinks);
			} else {
				System.out.println("No New WebLinks To download.....");
			}
			// wait
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		downloadExecutor.shutdown();
	}

	private void download(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = getTasks(webLinks);
		List<Future<WebLink>> futures = new ArrayList<>();

		try {
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Future<WebLink> future : futures) {

			try {
				if (!future.isCancelled()) {
					WebLink webLink = future.get();
					String webPage = webLink.getHtmlPage();
					if (webPage != null) {
						IOUtil.write(webPage, webLink.getId());
						webLink.setDownloadStatus(WebLink.DownloadStatus.FAILURE);
						System.out.println("Download Success: "+webLink.getUrl());
					}
					else {
						System.out.println("Not Downloaded: "+webLink.getUrl());
					}

				}
				else {
					System.out.println("\n\nTask is cancelled --> "+Thread.currentThread());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private List<Downloader<WebLink>> getTasks(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = new ArrayList<>();

		for (WebLink weblink : webLinks) {
			tasks.add(new Downloader<WebLink>(weblink));
		}
		return tasks;
	}

	private List<WebLink> getWebLinks() {
		List<WebLink> webLinks = null;

		if (downloadAll) {
			webLinks = dao.getAllWebLinks();
			downloadAll = false;
		} else {
			webLinks = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
		}
		return webLinks;
	}
}
