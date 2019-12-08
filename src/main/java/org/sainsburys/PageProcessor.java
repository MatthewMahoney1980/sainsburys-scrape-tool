package org.sainsburys;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PageProcessor {
	public Elements getAllElementsOfType(String content, String elementType) {
		Document doc = Jsoup.parse(content);
		return doc.select(elementType);
	}
}
