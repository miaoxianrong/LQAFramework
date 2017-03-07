package mapping;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import Interface.ILog;




public class mapping {
	



	

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
    public static void main(String[] args){    	
    	
    	
    	
    	FirefoxProfile profile = new FirefoxProfile();                 
    	profile.setPreference("intl.accept_languages","ko");                       
    	WebDriver driver = new  FirefoxDriver(profile);
    	toolbox tool = new toolbox();
    	tool.setPID("00000981");
//  	System.setProperty("webdriver.chrome.driver", "D://chromedriver.exe");
//		ChromeOptions options = new ChromeOptions();
//	    options.addArguments("--lang=zh-tw");
//      WebDriver driver = new ChromeDriver(options);  
    
  	
  	
//    	 DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//    	    PhantomJSDriver driver = new PhantomJSDriver(capabilities);
    	
    	driver.get("http://lqa-pc-vm2:8181/");
    	
    	boolean cycle = true;
    	while(cycle){
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    		try {
				String in = br.readLine();
				String screenname = tool.getScrn("./screen/", driver);
				//NewMapping newM = new NewMapping(driver, screenname, "//*",2);		
		    	//newM.start();
//				System.out.println(in);
//				List<WebElement> es = driver.findElements(By.xpath(in));
//				System.out.println(es.size() + "elements");
//				
//				for(WebElement e: es){
//					System.out.println(e.getTagName());
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    	driver.quit();
    }
	public static boolean start(WebDriver driver, String screenName, String container)
	 {
		ArrayList<String> LogContext = new ArrayList<String>();
		ILog log = new Log();
		log.createLogFilePath();
		boolean isComplete = false;
		
		String plocStr = container + "//*[contains(text(),'[[')]";
		String containerAll = container + "//*";
//		String rootStr = "//body" + container;
		String detection = "";
		ExecutorService pool;
		DecimalFormat df = new DecimalFormat("0.0");	
		double timer_begin = System.currentTimeMillis()/1000.000;	
		
/*
 * 
 * gray out the container
 * 
 * */
//		WebElement eContainer = driver.findElement(By.xpath(container));
//		String opacity= "opacity: 0.1;";
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript(
//				"arguments[0].setAttribute('style', arguments[1]);",
//				eContainer, opacity);	
//		
//		System.out.println("/*************\n* Start collecting elements in container: " + container);
//		LogContext.add("/*************\n* Start collecting elements in container: " + container);
/* 
 * 
 * collecting elements in containers
 * 
 * 
 * */
		List<WebElement> inContainers = driver.findElements(By.xpath(containerAll));
		System.out.println("* <<" + inContainers.size() + ">> elements in container");
		LogContext.add("* <<" + inContainers.size() + ">> elements in container");
/* 
 * 
 * collecting elements with PLOC ID
 * 
 * */
		
         List<WebElement> elementsList = driver.findElements(By.xpath(plocStr));
        		
		System.out.println("* <<" + elementsList.size() + ">> elements has PLOC ID");
		LogContext.add("* <<" + elementsList.size() + ">> elements has PLOC ID");
		 for(WebElement e: elementsList){
				String trim_text = e.getText().trim();
				if(!trim_text.equals("")){
				    LogContext.add(" -> #" + e.getText().trim() + "#");
				    toolbox.highlightElementY(driver, e);
				    String coordinate = "@ Coordinate: " + e.getLocation();
					String dimension = "@ Dimension: " + e.getSize();
//					log.WriteToTaglessLog(coordinate + "\n" + dimension);
					LogContext.add("     " + coordinate + "\r\n     " + dimension);
				}
				else{
				    LogContext.add(" -> !" + e.getText().trim() + "!");

				}
			}
		
		
/* 
 * 
 * set PLOC ID
 * 
 * */
//		double timer_setID_begin = System.currentTimeMillis()/1000.000;		
//		 toolbox.setID(driver, inContainers);
//		 double timer_setID_end = System.currentTimeMillis()/1000.000;
//		 double time_setID_spent = timer_setID_end- timer_setID_begin;		
		 
/* 
 * 
 * 
 * Adding attribute values in element list
 * 
 * 
 * */
		double timer_value_begin = System.currentTimeMillis()/1000.000;
		
		String xPathValues = container + "//*[contains(@value,'[[')]";
		String xPathPlaceHolder = container + "//*[contains(@placeholder,'[[')]";
		List<WebElement> values = driver.findElements(By.xpath(xPathValues));
		List<WebElement> placeholders = driver.findElements(By.xpath(xPathPlaceHolder));
		System.out.println("* <<" + values.size() + ">> elements has @value attribute with PLOC ID");
		System.out.println("* <<" + placeholders.size() + ">> elements has @placeholders attribute with PLOC ID");
		LogContext.add("* <<" + values.size() + ">> elements has @value attribute with PLOC ID");
		LogContext.add("* <<" + placeholders.size() + ">> elements has @placeholders attribute with PLOC ID");
		for(WebElement e: values){
			String trim_text = e.getAttribute("value").trim();
			if(!trim_text.equals("") && trim_text.contains("[[")){
			    LogContext.add(" -> #" + e.getText().trim() + "#");
			    toolbox.highlightElementY(driver, e);
			    String coordinate = "@ Coordinate: " + e.getLocation();
				String dimension = "@ Dimension: " + e.getSize();
//				log.WriteToTaglessLog(coordinate + "\n" + dimension);
				LogContext.add("     " + coordinate + "\r\n     " + dimension);
			}
			else{
			    LogContext.add(" -> !" + e.getAttribute("value").trim() + "!");

			}
		}
		for(WebElement e: placeholders){
			elementsList.add(e);
		}
		double timer_value_end = System.currentTimeMillis()/1000.000;
		double time_value_spent = timer_value_end- timer_value_begin;		
//		System.out.println("Collecting Attribute elements: " + df.format(time_value_spent));
		 System.out.println("* Total elements count: " + elementsList.size());
		 LogContext.add("* Total elements count: " + elementsList.size());
		 System.out.println("/*************\n");
		 LogContext.add("/*************\n");
		 
		log.WriteToMainLog(LogContext);
		 System.out.println("SSSSSSSSSSSSSSSS");
		 try {
			Thread.sleep(1000000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
/* 
 * 
 * start mapping 
 * 
 * */
		 String screenshot = "Screenshot: " + screenName;
		 pool = Executors.newFixedThreadPool(50);
			 for(WebElement e: elementsList){
			         concurrentMapping CM = new concurrentMapping();
			         
			         CM.driver = driver;
			         CM.e = e;
			         CM.screenshot = screenshot;
			        CM.container = container;
			        CM.detection = detection;
			        CM.log = log;
			         Thread t = new Thread(CM);
			         
			    
			        pool.execute(t);
//			       System.out.println(Thread.activeCount());
             }
			 pool.shutdown();
		         
					while(!pool.isTerminated())
					    {
						waitMin(1, "");
					    } 

//				opacity= "opacity: 1;";
//					js.executeScript(
//							"arguments[0].setAttribute('style', arguments[1]);",
//							eContainer, opacity);	
			 double timer_end = System.currentTimeMillis()/1000.000;
			double time_spent = timer_end- timer_begin;		
			double metric = time_spent/inContainers.size();
			
//			toolbox.writeToLOG(df.format(time_spent) + " " + df.format(metric), "mapping.time.log");
			LogContext.add("Total Times: " + df.format(time_spent));
			System.out.println("Mapping is complete.......");
			LogContext.add("Mapping is complete.......");
			log.WriteToMainLog(LogContext);
			System.gc();
			return isComplete;
	 }
 
}
