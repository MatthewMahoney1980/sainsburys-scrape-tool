package org.sainsburys.objects;

@SuppressWarnings("unused")
public class ScrapedPage {
	private Result[] results;
	private Total total;
	
	public ScrapedPage(Result[] results, Total total) {
		this.results = results;
		this.total = total;
	}
}
