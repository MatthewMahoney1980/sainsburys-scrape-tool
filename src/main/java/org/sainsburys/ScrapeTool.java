package org.sainsburys;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ScrapeTool {
	
	public final static Logger logger = Logger.getLogger(ScrapeTool.class);
	
	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1.2");
		
		BasicConfigurator.configure();
		
		String url = null;
		String outputFilePath = null;
		for (String arg: args){
			if (arg.startsWith("-Durl=")){
				url = arg.substring(arg.lastIndexOf("=") + 1, arg.length());
			}
			if (arg.startsWith("-DoutputFile=")){
				outputFilePath = arg.substring(arg.lastIndexOf("=") + 1, arg.length());
			}
		}
		if (url == null) {
			logger.error("Must specify url as command line param, eg. -Durl=yoururl.com");
			System.exit(0);
		}
		PageProcessor pageProcessor = new PageProcessor();
		String processedJSON = pageProcessor.scrapePage(url);
		
		// readout processedJSON in console/log file, and write to specific export file if command line param instructs so
		logger.info(processedJSON);
		if (outputFilePath != null) {
			PrintWriter out = null;
			try {
				out = new PrintWriter(outputFilePath);
				out.write(processedJSON);
				out.close();
			}
			catch (FileNotFoundException e) {
				logger.error("Could not write to file: " + outputFilePath);
			}
		}
	}
}
