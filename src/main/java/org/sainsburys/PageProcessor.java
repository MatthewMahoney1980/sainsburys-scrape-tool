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
	
	public Element getFirstLinkFromGridItem(Element gridItem) {
		Elements links = getAllElementsOfType(gridItem.toString(), "a[href]");
		if (links.size() > 0) {
			return links.get(0);
		}
		return null;
	}
}
