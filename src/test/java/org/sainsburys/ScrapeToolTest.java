package org.sainsburys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

public class ScrapeToolTest {
	
	@BeforeClass
	public static void setup() {
		System.setProperty("https.protocols", "TLSv1.2");
	}
	
	@Test
	public void when_ReadingFromURL_then_ValidContentReturned(){
		PageReader pageReader = new PageReader();
		String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		String content = null;
		try {
			content = pageReader.readWebPage(url);
		}
		catch (Exception e) {
			// no need to log errors for tests, only to use stack trace to identify root cause of issues
			e.printStackTrace();
		}
		assertNotNull(content);
	}
	
	@Test
	public void when_NotNullContentStringProvided_then_CanExtractAllGridItemElements() {
		PageReader pageReader = new PageReader();
		String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		String content = null;
		try {
			content = pageReader.readWebPage(url);
		}
		catch (Exception e) {
			// no need to log errors for tests, only to use stack trace to identify root cause of issues
			e.printStackTrace();
		}
		PageProcessor pageProcessor = new PageProcessor();
		Elements elements = pageProcessor.getAllElementsOfType(content, "li.gridItem");
		for (Element element : elements) {
			assertEquals(element.tagName(), "li");
			assertEquals(element.attr("class"), "gridItem");
		}
		assertEquals(elements.size(), 17);
	}
}
