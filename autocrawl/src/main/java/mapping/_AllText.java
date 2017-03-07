package mapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;  
import java.util.regex.Matcher;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Interface.IMode;

public class _AllText {
	String container;
	WebDriver driver;
	String screenshotName;
	public _AllText(){
		
	}
	public _AllText(String container,  WebDriver driver, String screenshotName){
		this.container = container;
		this.driver = driver;
		this.screenshotName = screenshotName;
	}
	public void find(){
		ArrayList<String> LogContext = new ArrayList<String>();
//		ArrayList<String> Text2Screen = new ArrayList<String>();
		System.out.println("# " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Start Mapping.......");
		LogContext.add("# " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Start Mapping.......");
		System.out.println("/***************************************\n* Start collecting elements in container: " + container);
		LogContext.add("/***************************************\n* Start collecting elements in container: " + container);
		String XPath = container + "//*";
//		System.out.println("Finding for XPath: " + XPath);
		List<WebElement> elementsList = driver.findElements(By.xpath(XPath));
		
		String allText = driver.findElement(By.xpath("//*")).getText();
		String regex = "\\[\\[";	
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(allText);
		int i=0;
		while(m.find()){
			i= i + 1;
		}
		

		System.out.println("* <<" + elementsList.size() + ">> elements in Total, <<" + i + ">> PLOC strings");
		System.out.println("* Get all strings: " + allText);
		LogContext.add("* <<" + elementsList.size() + ">> elements in Total, <<" + i + ">> PLOC strings");
		LogContext.add("* Get all strings: " + allText);
		
/*************DEBUG*******************/
		int d = 0;
		ArrayList<String> frameList = new ArrayList<String>();
		for(WebElement e: elementsList){
			if(e.getTagName().equals("frame") || e.getTagName().equals("iframe")){
				d = d + 1;
				System.out.println(" " + e.getTagName());
				System.out.println("  " + e.getAttribute("name"));
				frameList.add(e.getAttribute("name"));
			}
//			System.out.println(e.getTagName());
		}
		System.out.println(d + " frame!");
		for(int j=0;j<d;j++){
//			String currentWindow = frameList.get(j);
			WebDriver temp = driver;
			driver.switchTo().frame(j);
			List<WebElement> elementsList2 = driver.findElements(By.xpath("//body//*"));
			System.out.println(elementsList2.size() + " elements in frame #" + j);
//			for(WebElement e2: elementsList2){
//				toolbox.highlightElementY(driver, e2);
//			}
//			this.find();
//			driver.switchTo().frame(currentWindow);
			driver = temp;
		}
		
//		 for(WebElement e: elementsList){
//				String trim_text = e.getText().trim();
//				if(!trim_text.equals("")){
////				    toolbox.highlightElementY(driver, e);
//
////					String[] concat = trim_text.split("]]");
//					
////						Text2Screen.add("Screenshot: " + screenshotName);
//					    LogContext.add(" -> #" + trim_text + "#");
////					    Text2Screen.add("Find: " + trim_text + "");
//					    String coordinate = "@ Coordinate: " + e.getLocation();
//						String dimension = "@ Dimension: " + e.getSize();
////						Text2Screen.add("Coordinate: " + e.getLocation());
////						Text2Screen.add("Dimension: " + e.getSize());
////						Text2Screen.add("---------------------------------------------");
////						log.WriteToTaglessLog(coordinate + "\n" + dimension);
//						LogContext.add("     " + coordinate + "\r\n     " + dimension);
//					
//					
//				}
//				else{
//				    LogContext.add(" -> !" + e.getText().trim() + "!");
//
//				}
//			}
		 new Log().WriteToMainLog(LogContext);
//		 new Log().WriteToText2Screen(Text2Screen);
	}
}
