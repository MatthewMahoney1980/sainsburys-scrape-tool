# Sainsbury's Scrape Tool

Simple application for scraping contents of product page found at provided URL.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

* Git
* Maven
* Java 8

### Installing

First, clone the project

> git clone https://github.com/MatthewMahoney1980/sainsburys-scrape-tool.git

Then navigate to the newly cloned folder and build with the following command:

> mvn package

## Running the tests

The tests will run as part of the build process above

## Running the application from the command line

Having compiled successfully, the application can then be run from the project root folder via:

> java -jar target/sainsburys-scrape-tool-jar-with-dependencies.jar -Durl=https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html

If you want to also export the processed JSON to a specific file, you can provide that with -Doutputfile={path to file}, eg.:

> java -jar target/sainsburys-scrape-tool-jar-with-dependencies.jar -Durl=https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html -DoutputFile=processedOutput.json

### Notes

* I don't usually commit broken code, but for the purposes of demonstrating XP practises, I have committed tests *before* writing the actual code that makes them pass. In reality, this might be done as a pair programming exercise, and the the commit would include the test *and* the working code

* I have added some rudimentary logging and error handling, the latter of which typically displays the error and then terminates the program. This is not an API that requires extra robustness, to catch errors and continue to function. For example, providing an unsuitable product page URL is not something we can recover from in this command line tool, it should just exit with an error.

* Lastly, given more time and perhaps a few more classes, I would be tempted to tidy up the project package structure. For now though, it's uncluttered enough and does the job.