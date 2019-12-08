package org.sainsburys;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageProcessor {
	public Elements getAllElementsOfType(String content, String elementType) {
		Document doc = Jsoup.parse(content);
		return doc.select(elementType);
	}
	
	public String getFirstLinkURLFromGridItem(Element gridItem) {
		Elements links = getAllElementsOfType(gridItem.toString(), "a[href]");
		if (links.size() > 0) {
			String linkURL = links.get(0).attr("href");
			String processedLinkURL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
					linkURL.substring(linkURL.indexOf("/shop"));
			return processedLinkURL;
		}
		return null;
	}
}
