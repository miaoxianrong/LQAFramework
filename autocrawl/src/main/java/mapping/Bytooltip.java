package mapping;

//import gui.tool;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import com.CaptureScreen;

public class Bytooltip extends _Mode {
	
	String projectId="";
	String dropId="";
	
	public Bytooltip(int symbol,String container,  WebDriver driver, String screenshotName,String projectid, String dropid){
		
		this.symbol_index = symbol;
		this.driver = driver;
		this.screenshotName = screenshotName;
		this.container = container;
		
		this.symbol_index = symbol;		
		
		this.projectId = projectid;
		this.dropId = dropid;
	}
	@Override
	public void find() {
		
		//Map<String,WebElement> ele_tooltips = avaiableElement_tooltips(driver);	
		Map<String,WebElement> ele_tooltips = gettipnodes();
		//System.out.println("* <<" + ele_tooltips.size() + ">> elements has tips");
		if(ele_tooltips != null && ele_tooltips.size() >0)
			Logtip(ele_tooltips);
	}	
	
	public Map<String,WebElement> gettipnodes(){
		Map<String,WebElement> resultmap= new LinkedHashMap<String,WebElement>();
		List<String> Attributefortip =new ArrayList<String>();
		
		Attributefortip.add("title");
		//Attributefortip.add("data-qtip");
		Attributefortip.add("tip");
		
		for(int i=0;i<Attributefortip.size();i++){
			String xpath = this.container + "//*[contains(@" + Attributefortip.get(i).trim() + ",'" + Symbol.getLeftByIndex(symbol_index)+ "')]";			
			//System.out.println(xpath);
			List<WebElement> ee = driver.findElements(By.xpath(xpath));
			if(ee.size()>0)
				for(WebElement e: ee){
					resultmap.put(e.getAttribute(Attributefortip.get(i).trim()), e);
				}
		}
		
		return resultmap;
	}
	
	public void Logtip(Map<String,WebElement> map){
		
		Set set = map.keySet();
		Iterator it = set.iterator();
		int screenid =0;
		while(it.hasNext()){			
			String keys = (String)it.next();			
			WebElement e = map.get(keys);
			try{
			// for(WebElement e: elementsList){
				//CaptureScreen.captureTooltip(driver, ProjectID, drop);
				 String symbol_r = Symbol.getRightByIndex(symbol_index);
					String trim_text = keys.trim();
					if(!trim_text.equals("")){
	//				    toolbox.highlightElementY(driver, e);
						String screenshotName = "";// tool.captureTooltip(driver, projectId, dropId, e,screenid );
						
						String[] concat = trim_text.split(symbol_r);
						for(String s: concat){
							Text2Screen.add("Screenshot: " + screenshotName);
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
					else{
					    LogContext.add(" -> !" + e.getText().trim() + "!");
	
					}
				//}
			}
			catch(StaleElementReferenceException ex){
				System.out.println("Element is no longer attached to the DOM");
			}
			
			screenid ++;
		}
	
		 new Log().WriteToMainLog(LogContext);
		 new Log().WriteToText2Screen(Text2Screen,this.projectId);
	}
	

public static Map<String,WebElement> avaiableElement_tooltips(WebDriver driver) {
		
		List<String> Xpathtooltips =new ArrayList<String>();
		
		
		List<WebElement> listEles_tooptips = new ArrayList<WebElement>();
		
		Map<String,List<WebElement>> mapall = new LinkedHashMap<String,List<WebElement>>();
		Map<String,WebElement> map= new LinkedHashMap<String,WebElement>();
		
		Xpathtooltips.add("//body//button");
		Xpathtooltips.add("//body//a");
		Xpathtooltips.add("//body//span");
		Xpathtooltips.add("//body//input");
		Xpathtooltips.add("//body//td");
		Xpathtooltips.add("//body//img");
		Xpathtooltips.add("//body//div");
		
		//Xpathtooltips.add("//tr//a");
		//Xpathtooltips.add("//a//span");
		//Xpathtooltips.add("//input");
		//Xpathtooltips.add("//td");
		
		for(int k=0;k<Xpathtooltips.size();k++){
			List<WebElement> ee = driver.findElements(By.xpath(Xpathtooltips.get(k).trim()));
			mapall.put(Xpathtooltips.get(k).trim(), ee);
		}
		
		
		for(int j=0;j<mapall.size();j++){
			
			List<WebElement> els = mapall.get(Xpathtooltips.get(j).trim());
			
			System.out.println("total elements:" + els.size());
		
			for(int i=0;i<els.size();i++){
			
				try {
					
					if (!els.get(i).getAttribute("data-qtip").toString().equals("")){
						map.put(els.get(i).getAttribute("data-qtip").toString(), els.get(i));
					}
					
				}catch(Exception error){
					if (!els.get(i).getAttribute("title").toString().equals("")){
						map.put(els.get(i).getAttribute("title").toString(), els.get(i));
					}
				}
			}
		}
		
		System.out.println("total elements for tips:" + map.size());
		
		/*
		Set set = map.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			
			String keys = (String)it.next();
			WebElement ele= map.get(keys);
			listEles_tooptips.add(ele);
			
		}*/
			
		
			return  map;
	}

 public void capturetips(){
	 
 }
@Override
public void LogText() {
	// TODO Auto-generated method stub
	
}
@Override
public void LogAttribute(String attribute) {
	// TODO Auto-generated method stub
	
}

		/*private static String returnxpath (WebDriver driver,WebElement element)  {
			
			String temp = element.toString();
			String xpath="";
			
			if (temp.endsWith("]")){
				
				String [] strs = temp.replace(":", ";").split(";");
				
					if (strs[strs.length-1].endsWith("]")){
						int pos = strs[strs.length-1].lastIndexOf("]");
						strs[strs.length-1].substring(0,pos).trim();
					    xpath = strs[strs.length-1].substring(0,pos).trim();
					}
				
			}	
			
			return xpath;
		}*/

}
