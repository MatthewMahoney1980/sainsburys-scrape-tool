package org.sainsburys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class PageReader {
	public String readWebPage(String url) throws MalformedURLException, IOException {
		Scanner scanner = new Scanner(new URL(url).openStream(), "UTF-8");
		String content = scanner.useDelimiter("\\A").next();
		scanner.close();
		return content;
	}
}
