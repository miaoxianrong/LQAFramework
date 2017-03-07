package mapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;




import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Interface.ILog;



public class toolbox {
	public int screenID;
	String PID;
	public static void mkDir(String fullpath){
		
        File path = new File(fullpath);
        if(!path.exists()){
        	makeDir(path);         	
        }
	}
	public static void highlightElementY(WebDriver driver, WebElement element) {
		for (int i = 0; i < 5; i++) {
			String forecolor = "color: red;";		
			String backcolor = "background-color: yellow;";
			String outline = "outline: 1px solid rgb(126,255,136);";
			String color;
			
				 color = forecolor + backcolor + outline;
			
			  

//			String color_org = element.getAttribute("style").toString();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, color);

//			js.executeScript(
//					"arguments[0].setAttribute('style', arguments[1]);",
//					element, color_org);

		}
	}
    /** 
     * Enhancement of java.io.File#mkdir() 
     * Create the given directory . If the parent folders don't exists, we will create them all. 
     * @see java.io.File#mkdir() 
     * @param dir the directory to be created 
     */  
    public static void makeDir(File dir) {  
        if(! dir.getParentFile().exists()) {  
            makeDir(dir.getParentFile());  
        }  
        dir.mkdir();  
    }  
	public static void writeToFile(ArrayList<String> text_to_string)
	{
		try {
			OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream("./screen/text-to-screen.txt", true), "utf-8");
			bw.write("\r\n"); 
			for(int i=0;i<text_to_string.size();i++)
			{
				bw.write(text_to_string.get(i));
				bw.write("\r\n"); 
			}
			bw.write("--------------------------------------------------------------------");
			bw.write("\r\n"); 			
		    bw.flush();		  
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	public static void writeToLOG(String context, String fileName)
	 {
		
		try{
		FileWriter fw= new FileWriter(fileName,true) ;	
		BufferedWriter bw = new BufferedWriter(fw);
     bw.write(context);
		bw.newLine();
	    bw.flush();
	    fw.close();
		bw.close();
		}
		catch(IOException e)
		{
			
		}
}
public static int setID(WebDriver driver, List<WebElement> allElements) {
		
//		List<WebElement> allElements = driver.findElements(By.xpath("//body//*"));	
		int i=0;
		ExecutorService pool = Executors.newFixedThreadPool(250);
		for(WebElement e: allElements)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try{
				concurrentSetID cID = new concurrentSetID();
				cID.e = e;
				cID.i = i;
				cID.js = js;
				Thread t = new Thread(cID);
		        pool.execute(t);
		        i++;
			}
			catch(StaleElementReferenceException e1 )
			{
				System.out.println("Element is no longer attached to the DOM");
			}
		

		}
		 pool.shutdown();
         
			while(!pool.isTerminated())
			    {
				waitMin(1, "");
			    } 
	return i;
	}
	public static void findTaglessString(WebDriver driver, int loc_id, String screenshot, String container){
//		 boolean hasChild = true;
			ArrayList<String> LogContext = new ArrayList<String>();
			LogContext.add("# " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Start finding tagless context");
		 String parent = container + "//*[@loc_id=" + loc_id +"]";
		 String child = container + "//*[@loc_id=" + loc_id +"]/*";
		 WebElement e = driver.findElement(By.xpath(parent));
		 List<WebElement> children= driver.findElements(By.xpath(child));
		 
		 if(children.size() != 0){
			 String subText="";
			 String parentText = e.getText().trim();
			 for(WebElement e2:children){
				 	subText =subText + e2.getText().trim() + "\n";
		 		}		 
			 String parentText2 = parentText.replaceAll("\r", "");
			 String subText2 = subText.replaceAll("\r", "");
			 parentText2 = parentText2.replaceAll("\n", "");
			 subText2 = subText2.replaceAll("\n", "");
			 if(parentText2.equals(subText2)){
			 }
			 else{
					System.out.println(">>>>>>>>>>>Tagless string found in Node #"  + loc_id+ "<<<<<<<");
					LogContext.add(" >>>>>>>>>>>Tagless string found in Node #"  + loc_id+ "<<<<<<<");
//					log.WriteToTaglessLog(">>>>>>>>>>>Tagless string found in Node #"  + loc_id+ "<<<<<<<");
					LogContext.add(" The parent string is: " + parentText2);
//					log.WriteToTaglessLog("The parent string is: " + parentText2);
					LogContext.add(" The sub string is: " + subText);
//					log.WriteToTaglessLog("The sub string is: " + subText);
					String result = removeDupStr(parentText2, subText);
//					log.WriteToTaglessLog("After remove: " + result);
					LogContext.add(" After remove: " + result);
//					log.WriteToTaglessLog(result + " has [[ :" + result.contains("[["));
					LogContext.add(" " + result + " has [[ :" + result.contains("[["));
					if(result.contains("[[")){
		//				System.out.println(result + " has PLOC ID");
						//do mapping for parentNode
						String coordinate = "Coordinate: " + e.getLocation();
						String dimension = "Dimension: " + e.getSize();
//						log.WriteToTaglessLog(coordinate + "\n" + dimension);
						LogContext.add(" " + coordinate + " " + dimension);
						if(e.getLocation().x > 0 && e.getLocation().y > 0 && e.getSize().height >0 && e.getSize().width > 0){
						System.out.println(screenshot);
						 System.out.println("Find tagless: " + result);
						System.out.println(coordinate);
						System.out.println(dimension);        						      
						System.out.println("-------------------------\n");	        					
						ArrayList<String> text_to_string = new ArrayList<String>();
						text_to_string.add(screenshot);
						text_to_string.add("Find: " + result);
						text_to_string.add(coordinate);
						text_to_string.add(dimension);
						writeToFile(text_to_string);
						text_to_string = null;
						
						ILog log = new Log();
						log.WriteToTaglessLog(LogContext);
						
						
						
					}
					}
					else{
						System.out.println("Ignore: " + result);
					}

			
		
		 }
		 }
		 
	}
	public static String removeDupStr(String str1_Parent, String str2_Children){
		String[] splitStr = str2_Children.split("\n");
		for(String s: splitStr){
//			System.out.println("[Removing: ]" + s);
			str1_Parent = str1_Parent.replace(s, "");
		}
//		System.out.println("Result: " + str1_Parent);
		return str1_Parent;
	}
	void setPID(String pid){
		this.PID = pid;
	}
	public String getScrn(String screenFolder, WebDriver driver)
	 {
SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss"); 
format.setTimeZone(TimeZone.getTimeZone("GMT"));
Date date = new Date();
String strDate = format.format(date);

String screenName = PID + "_" + strDate + "_" + screenID + ".png";
String screenName_full = screenFolder + screenName;
screenID++;
try
{
//	driver = new Augmenter().augment(driver);

File scrFile = ((TakesScreenshot) driver)
		.getScreenshotAs(OutputType.FILE);

FileUtils.copyFile(scrFile, new File(screenName_full));
System.out.println("Screenshot: " + screenName_full);

}
catch(WebDriverException e)
{
//	System.out.println("//----------Screen Capture failed! Capture again!");
	waitMin(5, "//----------Screen Capture failed! Capture again!");
//	driver = new Augmenter().augment(driver);
	File scrFile = ((TakesScreenshot) driver)
			.getScreenshotAs(OutputType.FILE);
	System.out.println("Screenshot: " + screenName_full);

	try {
		FileUtils.copyFile(scrFile, new File(screenName_full));
		
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
catch(IOException e)
{
	e.printStackTrace();
}
		return screenName;
}
	public static void waitMin(int second, String reason)
	{
		if(!reason.equals(""))
		      System.out.println(reason + "! Wait " + second + " seconds!");
		try {
			int i;
			//second = second +5;
			for(i=second;i>0;i--)
			{
		//	System.out.println(i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public static String getProjectID()
	{
		return readXML("ProjectID");
	}
	static String readXML(String key) 
	{	    	
		String value = null;
		try
		{	            
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	            
			DocumentBuilder db = factory.newDocumentBuilder();	            
			Document doc = db.parse(new File("./config.xml"));	            
			Element elmtInfo = doc.getDocumentElement();	            
			NodeList nodes = elmtInfo.getChildNodes();            
	            
			for (int i = 0; i < nodes.getLength(); i++)	            
			{	               
				Node result = nodes.item(i);	                
				if (result.getNodeType() == Node.ELEMENT_NODE && result.getNodeName().equalsIgnoreCase(key))	                
				{	                	
					value = result.getTextContent();	                   
					//System.out.println(value);	               
				}	            
			}	        
		}	        
		catch (ParserConfigurationException e)	        
		{	            
			e.printStackTrace();	        
		}	        
		catch (SAXException e)	        
		{	            
			e.printStackTrace();	        
		}	        
		catch (IOException e)	        
		{	            
			e.printStackTrace();	        
		}			
		return value;	
	}
}
