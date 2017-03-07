package mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Interface.IMode;


public abstract class _Mode implements IMode{
	int symbol_index;
	WebDriver driver;
	String screenshotName;
	String container;
	String XPath;
	String attribute;
	List<WebElement> elementsList;
	
	ArrayList<String> LogContext = new ArrayList<String>();
	ArrayList<String> Text2Screen = new ArrayList<String>();
	public _Mode(){
		
	}
	abstract public void find();
	public List<WebElement> searchByXPath(){
		System.out.println("Searching elements by XPath: " + XPath);
		return driver.findElements(By.xpath(XPath));
	}

	public void LogText(String projectid){

		try{
		 for(WebElement e: elementsList){
			 String symbol_r = Symbol.getRightByIndex(symbol_index);
				String trim_text = e.getText().trim();
				if(!trim_text.equals("")){
//				    toolbox.highlightElementY(driver, e);

					String[] concat = trim_text.split(symbol_r);
					for(String s: concat){
						if(s.indexOf("[[") !=-1){
							
							Text2Screen.add("Screenshot: " + screenshotName);
							s= s.replaceAll("\r", " ").trim();
							s= s.replaceAll("\n", " ").trim();

						    LogContext.add(" -> #" + s + symbol_r + "#");
						    Text2Screen.add("Find: " + s + symbol_r);
						    String coordinate = "@ Coordinate: " + e.getLocation();
							String dimension = "@ Dimension: " + e.getSize();
							Text2Screen.add("Coordinate: " + e.getLocation());
							Text2Screen.add("Dimension: " + e.getSize());
							Text2Screen.add("---------------------------------------------");
	//						log.WriteToTaglessLog(coordinate + "\n" + dimension);
							LogContext.add("     " + coordinate + "\r\n     " + dimension);
						}
					}
					
				}
				else{
				    LogContext.add(" -> !" + e.getText().trim() + "!");

				}
			}
		}
		catch(StaleElementReferenceException e){
			System.out.println("Element is no longer attached to the DOM");
		}
		 new Log().WriteToMainLog(LogContext);
		 new Log().WriteToText2Screen(Text2Screen, projectid);
	}
	
	public void LogAttribute(String attribute,String projectid){

		try{
		 for(WebElement e: elementsList){
			 String symbol_r = Symbol.getRightByIndex(symbol_index);
				String trim_text = e.getAttribute(attribute).trim();
				if(!trim_text.equals("")){
//				    toolbox.highlightElementY(driver, e);

					String[] concat = trim_text.split(symbol_r);
					for(String s: concat){
						if(s.indexOf("[[") !=-1 ){
							
							Text2Screen.add("Screenshot: " + screenshotName);
							s= s.replaceAll("\r", " ").trim();
							s= s.replaceAll("\n", " ").trim();

						    LogContext.add(" -> #" + s + symbol_r + "#");
						    Text2Screen.add("Find: " + s + symbol_r);
						    String coordinate = "@ Coordinate: " + e.getLocation();
							String dimension = "@ Dimension: " + e.getSize();
							Text2Screen.add("Coordinate: " + e.getLocation());
							Text2Screen.add("Dimension: " + e.getSize());
							Text2Screen.add("---------------------------------------------");
	//						log.WriteToTaglessLog(coordinate + "\n" + dimension);
							LogContext.add("     " + coordinate + "\r\n     " + dimension);
						}
					}
					
				}
				else{
				    LogContext.add(" -> !" + e.getText().trim() + "!");

				}
			}
		}
		catch(StaleElementReferenceException e){
			System.out.println("Element is no longer attached to the DOM");
		}
		 new Log().WriteToMainLog(LogContext);
		 new Log().WriteToText2Screen(Text2Screen,projectid);
	}
	
	

}
