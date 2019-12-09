package org.sainsburys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

public class ScrapeToolTest {
	
	public static PageProcessor pageProcessor;
	
	@BeforeClass
	public static void setup() {
		System.setProperty("https.protocols", "TLSv1.2");
		pageProcessor = new PageProcessor();
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
			fail("Could not read source data from url: " + url);
		}
		assertNotNull(content);
	}
	
	@Test
	public void when_NotNullContentStringProvided_then_CanExtractAllGridItemElements() {
		
		String content;
		try {
			content = FileUtils.readFileToString(new File("src/test/resources/page-source.html"), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			fail("Could not read source file from test resources");
			return;
		}
		
		Elements elements = pageProcessor.getAllElementsOfType(content, "li.gridItem");
		for (Element element : elements) {
			assertEquals(element.tagName(), "li");
			assertEquals(element.attr("class"), "gridItem");
		}
		assertEquals(elements.size(), 17);
	}
	
	@Test
	public void when_GridItemProvided_then_CanExtractLinkToDetailPage() {
		
		String content;
		try {
			content = FileUtils.readFileToString(new File("src/test/resources/gridItem.html"), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			fail("Could not read source file from test resources");
			return;
		}
		
		Elements gridItems = pageProcessor.getAllElementsOfType(content, "li.gridItem");
		Element gridItem = gridItems.get(0);
		
		String linkURL = pageProcessor.getFirstLinkURLFromGridItem(gridItem);
		assertEquals(linkURL, "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html");
	}
	
	@Test
	public void when_ProductInfoReadFromLink_then_CanExtractTheFourRequiredAttributes() {
		
		String content;
		try {
			content = FileUtils.readFileToString(new File("src/test/resources/product-detail-page-source.html"), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			fail("Could not read source file from test resources");
			return;
		}
		
		String productContent = pageProcessor.getProductContent(content).toString();
		assertNotNull(productContent);
		String title = pageProcessor.getTitle(productContent);
		assertEquals(title, "Sainsbury's Strawberries 400g");
		int kcalValue = pageProcessor.getKcalPer100g(productContent);
		assertEquals(kcalValue, 33);
		BigDecimal pricePerUnit = pageProcessor.getPricePerUnit(productContent);
		assertEquals(pricePerUnit, new BigDecimal(1.75));
		String description = pageProcessor.getDescription(productContent);
		assertEquals(description, "by Sainsbury's strawberries");
	}
	
	@Test
	public void when_NotNullContentStringProvided_then_CanReturnJSONResponseAsExpected() {
		String content;
		try {
			content = FileUtils.readFileToString(new File("src/test/resources/product-detail-page-source.html"), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			fail("Could not read source file from test resources");
			return;
		}
		String processedJSON = pageProcessor.getProcessedJSON(content);
		assertNotNull(processedJSON);
	}
}
