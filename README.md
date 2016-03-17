# scraper
A simple HTML scraper designed specifically for a target HTML

This is a simple scraper application developed in Java 1.8 to scrape a particular HTML page.

The URL for the target page is "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html"
For convenience URL is hard coded in the app.

Application is bundled as an Maven project and following dependencies exist in POM.XML
    	===================================================================================
    		
  		
  		<dependency>
    		<groupId>org.glassfish</groupId>
    		<artifactId>javax.json</artifactId>
    		<version>1.0.4</version>
		</dependency>
		
		<dependency>
			<groupId>org.json</groupId>
			<artifactId>json</artifactId>
			<version>20090211</version>
		</dependency>
		
		
  		<dependency>
			<groupId>org.jsoup</groupId>
			<artifactId>jsoup</artifactId>
			<version>1.7.2</version>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
		</dependency>
		<dependency>
    		<groupId>org.skyscreamer</groupId>
    		<artifactId>jsonassert</artifactId>
    		<version>1.3.0</version>
			<scope>test</scope>
		</dependency>
	======================================================================	
		
		How to run the application:
		1) Doenload zip to the local drive
		2) Unpack the zip
		3) Open Eclipse
		4) File -> Import
		5) On the Import Dialog box select Maven -> Existing Maven Project and click Next
		6) On the Import Maven Projects dialog Browse to the unzipped scraper-master directory (step 2)
		7) Select the checkbox under projects (/ScraperApplication/pom.xml ....)
		8) Click on Finish
		9) Go to the Project menu and click on Clean...
		2) The main class is called Scraper.java
		3) When the class is run main method will print the JSONObject on console
		4) It is also possible to run the ScraperTest.Java which contain unit tests to assert the state of the JSON Objet 
		   returned by public JSONObject getFinalJSON(Document doc) method.
		5) Also possible to call public JSONObject getFinalJSON(Document doc) method directly on a object of Scraper Class.
		6) NB. Document referes to org.jsoup.nodes.Document; Check Jsoup dependency above.
		
