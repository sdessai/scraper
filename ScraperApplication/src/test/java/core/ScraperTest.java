package core;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

import org.skyscreamer.jsonassert.JSONAssert;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class ScraperTest {
	
	
	
	
	@Test 
    public void testgetFinalJson()  {
		
		Scraper tester = new Scraper();
		
		
		String URLString = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
		
		
		Document doc = tester.getDocument(URLString);
		
		JSONObject data = tester.getFinalJSON(doc);
		
		String expected = null;
		
		try{
			
			
			expected = new String(Files.readAllBytes(Paths.get("src/test/resources/ExpectedJson.txt")));
			System.out.println("======expected JSONObject =====   "+expected );
					
		}catch (IOException e) {
			
			e.printStackTrace();
		
		}
		
		try{
			
			JSONAssert.assertEquals(expected, data, false);
		
		}catch(JSONException e){
			
			e.printStackTrace();
		
		}
		
	}
	

	
	
}
