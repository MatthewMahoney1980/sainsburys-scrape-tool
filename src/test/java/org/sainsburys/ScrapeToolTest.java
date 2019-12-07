package org.sainsburys;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ScrapeToolTest {
	@Test
	public void when_ReadingFromURL_then_ValidContentReturned(){
		PageReader pageReader = new PageReader();
		String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		String content = pageReader.readWebPage(url);
		assertNotNull(content);
	}
}
