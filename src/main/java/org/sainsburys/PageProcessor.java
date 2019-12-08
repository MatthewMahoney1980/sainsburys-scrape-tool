package org.sainsburys;

import java.math.BigDecimal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageProcessor {
	public Elements getAllElementsOfType(String content, String elementType) {
		Document doc = Jsoup.parse(content);
		return doc.select(elementType);
	}
	
	private Element getFirstElementOfType(String content, String elementType) {
		Elements elements = getAllElementsOfType(content, elementType);
		if (elements != null && elements.size() > 0) {
			return elements.get(0);
		}
		return null;
	}
	
	public String getFirstLinkURLFromGridItem(Element gridItem) {
		Element link = getFirstElementOfType(gridItem.toString(), "a[href]");
		if (link != null) {
			String linkURL = link.attr("href");
			String processedLinkURL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
					linkURL.substring(linkURL.indexOf("/shop"));
			return processedLinkURL;
		}
		return null;
	}
	
	public String getProductContent(String content) {
		return getFirstElementOfType(content, "div.productContent").toString();
	}
	
	public String getTitle(String content) {
		return getFirstElementOfType(content, "h1").text();
	}
	
	public int getKcalPer100g(String content) {
		String kcalString = getFirstElementOfType(content, "td.nutritionLevel1").text();
		return Integer.parseInt(kcalString.substring(0, kcalString.indexOf("k")));
	}
	
	public BigDecimal getPricePerUnit(String content) {
		String priceString = getFirstElementOfType(content, "p.pricePerUnit").text();
		String trimmedPriceString = priceString.substring(1, priceString.indexOf("/"));
		return new BigDecimal(trimmedPriceString);
	}
	
	public String getDescription(String content) {
		String productText = getFirstElementOfType(content, "div.productText").toString();
		return getFirstElementOfType(productText, "p").text();
	}
}
