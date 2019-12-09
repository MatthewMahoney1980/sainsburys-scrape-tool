package org.sainsburys;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sainsburys.objects.Result;
import org.sainsburys.objects.ResultWithKCal;
import org.sainsburys.objects.ScrapedPage;
import org.sainsburys.objects.Total;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PageProcessor {
	
	public final static Logger logger = Logger.getLogger(PageProcessor.class);
	
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
	
	public Integer getKcalPer100g(String content) {
		Element kcalElement = getFirstElementOfType(content, "td.nutritionLevel1");
		if (kcalElement == null) {
			return null;
		}
		String kcalString = kcalElement.text();
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
	
	public String scrapePage(String url) {
		PageReader pageReader = new PageReader();
		String content = null;
		try {
			content = pageReader.readWebPage(url);
		}
		catch (Exception e) {
			logger.error("Could not read data source from url: " + url);
			System.exit(0);
		}
		return getProcessedJSON(content);
	}
	
	public String getProcessedJSON(String content) {
		BigDecimal gross = new BigDecimal(0);
		ArrayList<Result> resultsArrayList = new ArrayList<Result>();
		
		Elements gridItems = getAllElementsOfType(content, "li.gridItem");
		for (Element gridItem : gridItems) {
			String linkURL = getFirstLinkURLFromGridItem(gridItem);
			
			Result result = getResultFromItemURL(linkURL);
			if (result == null) {
				logger.error("Not adding null result to resultsArrayList");
				continue;
			}
			gross = gross.add(result.getUnitPrice());
			resultsArrayList.add(result);
		}
		
		BigDecimal vat = gross.multiply(new BigDecimal(0.2)).setScale(2, RoundingMode.CEILING);
		Total total = new Total(gross, vat);
		Result[] results = new Result[resultsArrayList.size()];
		results = resultsArrayList.toArray(results);
		ScrapedPage scrapedPage = new ScrapedPage(results, total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(scrapedPage);
		
		return json;
	}
	
	private Result getResultFromItemURL(String url) {
		PageReader pageReader = new PageReader();
		String content = null;
		try {
			content = pageReader.readWebPage(url);
		}
		catch (Exception e) {
			logger.error("Could not read product info data from url: " + url);
			return null;
		}
		String productContent = getProductContent(content).toString();
		if (productContent == null) {
			logger.error("Null product content found at: " + url);
			return null;
		}
		
		String title = getTitle(productContent);
		Integer kcals = getKcalPer100g(productContent);
		BigDecimal unitPrice = getPricePerUnit(productContent);
		String description = getDescription(productContent);
		
		if (kcals == null) {
			return new Result(title, unitPrice, description);
		}
		return new ResultWithKCal(title, kcals, unitPrice, description);
	}
}
