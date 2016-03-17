package core;

import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class Scraper {

	/**
	 *  
	 * Parses the document object to retrieve product information, price, description and size 
	 * @param doc  Document object constructed from the URL
	 * @return JSONobject containing the product information, price and description
	 */
	public JSONObject getFinalJSON(Document doc){
		//get the product listing page
				
		
				double total = 0.00;
				
				
				JSONArray ja = new JSONArray();
				
				
				//Get the desired element from the target document
				Elements products = doc.select("div.product");
				
				
				for(Element product:products){
						
					
					String productInfo = getProductInfo(product);
						
					
					double PPU = getPriceInfo_PPU(product);
						
					
					total += PPU;
						
					
					//DecimalFormat format = new DecimalFormat("0.00");
					PPU = Math.round(PPU);
						
					
					//String PPM = getPriceInfo_PPM(product);
					String descriptionURL = getDescriptionURL(product);
						
					
					String productDescription = getProductDescription(descriptionURL);
						
					
					int size = getDescSize(descriptionURL)/1000;  //convert size to kb
						
					
					JSONObject productJSO = new JSONObject();
						
					
					try{
								
						productJSO.put("title", productInfo);
								
						
						productJSO.put("size", size+"kb");
								
						
						productJSO.put("unit_price", PPU);
								
						
						productJSO.put("description", productDescription);
						
					}catch(JSONException e){
							
						e.printStackTrace();
						
					}
					
						
					ja.put(productJSO);
				
				}
						
						
				total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
						
						
				JSONObject joMain = new JSONObject();
						
				
				try{
								
					
					joMain.put("result", ja);
								
					
					joMain.put("total", total);
						
				
				}catch(JSONException e){
							
					
					e.printStackTrace();
						
				
				}
						
				
				return joMain;
				
	}
	
	
	
	/**
	 * Gets the size of the Description target HTML
	 * @param descriptionURL
	 * @return
	 */
	private int getDescSize(String descriptionURL) {
		
		
		HttpURLConnection conn;
		
		
		int size = 0;	
		
		
		try {
			
			
			conn = (HttpURLConnection) new URL(descriptionURL).openConnection();
			
			
			size = conn.getContentLength();
		
		
			} catch (IOException e) {
				
				
				e.printStackTrace();
			
			}
		
		
		return size;
	
	}
	
	/**
	 * 
	 * Get the information associated with the products
	 * 
	 * @param product
	 * @return
	 */
	private String getProductInfo(Element product){
		
		Element productInfo =  product.select("div.productInfo").first();
		
		return productInfo.text();
	}
	
	/**
	 * 
	 * Get the price of the product in of Price per Unit
	 * 
	 * @param product
	 * @return
	 */
	private double getPriceInfo_PPU(Element product){
		
		
		Element child = product.select("div.pricing").first();
		
		
		Element pricePerUnit =  child.select("p.pricePerUnit").first();
		
		
		double ppu = 0.0;
		
		
		if(pricePerUnit!=null&&pricePerUnit.text()!=null){
			
			
			ppu = Double.valueOf(pricePerUnit.text().substring(pricePerUnit.text().indexOf("&pound")+"&pound".length(), pricePerUnit.text().indexOf("/unit")));
		
		}
		
		
		return ppu;
	
	
	}
	
	/**
	 * 
	 * Get the price of the product in of Price per Measure
	 * 
	 * @param product
	 * @return
	 */
	
	private String getPriceInfo_PPM(Element product){
		
		Element child = product.select("div.pricing").first();
		
		
		Element pricePerMeasure =  child.select("p.pricePerMeasure").first();
		
		
		return pricePerMeasure.text();
	
	
	}
	
	/**
	 * Gets the Description associated with each product
	 * 
	 * 
	 * @param descriptionURL URL for the specific description
	 * @return Description associated with the URL
	 */
	private String getProductDescription(String descriptionURL){
		
		
		Document doc_desc = getDocument(descriptionURL);
		
		
		assert doc_desc != null : "Failed to get Desription from " + descriptionURL;
		
		
		Element Desc = doc_desc.select("htmlcontent").first();
		
		
		Element Desc_text = doc_desc.select("div.productText").first();
		
		
		return Desc_text.text();
	
	
	}
	
	
	/**
	 * Gets the URL to retrieve product description
	 * 
	 * 
	 * @param product Product for which description URL is requested
	 * @return a string containing the absolute URL for description associated with the product 
	 */
	private String getDescriptionURL(Element product){
		
		
		Element link = product.select("a").first();
		
		
		//String relHref = link.attr("href"); // == "/"
		String absHref = link.attr("abs:href"); // "http://jsoup.org/"
		
		
		return absHref;
	}
	
	
	/**
	 * Returns a HTML document pointed by URLSTring
	 * 
	 * 
	 * @param URLString string specifying URL from which document to be fetched
	 * @return document from the URL location
	 */
	public Document getDocument(String URLString){
		
		
		Document doc = null;
		
		
		try {
			
			
			doc = Jsoup.connect(URLString).get();
					
		
		} catch (IOException ioe) {
			
			
			ioe.printStackTrace();
		
		
		}
		
		assert doc == null : "Failed to get the requested Document " + URLString;
		
		
		return doc;
	
	}
	
	
	public static void main (String args[]) {

		Scraper scr = new Scraper();
		
		
		String targetURL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
		
		
		Document doc = scr.getDocument(targetURL);
		
		
		JSONObject jso = scr.getFinalJSON(doc);
		
		
		System.out.println("=======JSONObject==========  "+jso);
	
	}
	
}





	