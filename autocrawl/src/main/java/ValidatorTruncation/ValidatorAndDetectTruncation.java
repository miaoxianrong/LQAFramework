package ValidatorTruncation;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

import ValidatorTruncation.ValidatorUtil;


public class ValidatorAndDetectTruncation {
	
	static private String getScreenStatus;
	
	
	public static String getGetScreenStatus() {
		return getScreenStatus;
	}
	public static void setGetScreenStatus(String getScreenStatus) {
		ValidatorAndDetectTruncation.getScreenStatus = getScreenStatus;
	}
	
	public void truncationCheck(WebDriver driver,String projectID) throws Exception{
		
		ValidatorUtil.adjustWindow(driver);
		
		LinkedHashSet set = new LinkedHashSet();
		List<Thread> threadList = new ArrayList<Thread>();
		
			
			//ArrayList<String >tag = ValidatorUtil.createTaglist();  //createTaglist(); //ValidatorUtil.createTaglist();
			
			Pattern p = Pattern.compile(".*");
			Matcher m = p.matcher(driver.findElement(By.cssSelector("body")).getText());
			System.out.println("test:"+driver.findElement(By.cssSelector("body")).getText());
			
			while(m.find())
			{

				if(!m.group().toString().equals(""))
				{
					
					String text = m.group().toString().trim();
					System.out.println("Find:" + m.group().toString().trim());
					
					if(!isNumber(text)){
					set.add(text);
					
					
					}
					
				}									
			}
				
			System.out.println("set.size"+set.size());
			Iterator itr = set.iterator();
			int tempTime = set.size();
			
			while (itr.hasNext()){
				
				String text = itr.next().toString();
				CurrentTucationDetect ct = new CurrentTucationDetect(driver,text,set,projectID);
				Thread td = new Thread(ct);
				td.setName(text);
				td.start();
				threadList.add(td);
				
			}
			for (Thread td_:threadList){
				try {
					td_.join();	
				}catch (Exception error) {
					error.printStackTrace();
				}
				
			}
			System.out.println("Closing browser!");
			ValidatorUtil.waitMin(3);
			//driver.close();
		
					
				
				
			
	}
	public void getScrnTruncation (String ProjectID,WebDriver driver) throws Exception {
		
		System.out.println("Start capturing screen for truncation from remote desktop");
		
		String name = getStringDateShort().replaceAll(":", "").replaceAll("\\s", "").replaceAll("-", "");
		
		WebDriver augmenteDriver = (WebDriver) new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot)augmenteDriver).getScreenshotAs(OutputType.FILE);
		
		
		String path = "./output/"+ProjectID+"/truncation_screenshot//"; 
		
		 try {    
			 
			 FileUtils.copyFile(screenshot, new File(path+name+".png"));  

	     } catch (Exception e) {  
	         //e.printStackTrace();  
	         ValidatorUtil.getScrn(driver);

	     }  

	   
		
	}
	public static String getStringDateShort() {
		
		
        Random random = new Random(10);
       
        
		while (true){
		
			  Date currentTime = new Date();
			  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  String dateString = formatter.format(currentTime);
			  return dateString+ random.nextInt();
			}
		
		  
		  
		 } 

	public static void highlightElement(WebDriver driver, WebElement element) 
	
	{          
		JavascriptExecutor js = (JavascriptExecutor) driver;       
		 js.executeScript("element = arguments[0];" +  "original_style = element.getAttribute('style');" +                
			"element.setAttribute('style', original_style + \";" + "background: yellow; border: 2px solid red;\");" +            
			"setTimeout(function(){element.setAttribute('style', original_style);}, 1000);", element); 
	}
	public static ArrayList<String> createTaglist()
{
	ArrayList<String> tag_list= new ArrayList<String>();
	tag_list.add("body");
	return tag_list;
}
	public static boolean isNumber (String str) {
	
	boolean isNumber = false;
	isNumber = str.matches("[0-9]+");
	
	if (isNumber){
		
		isNumber=true;
	}
	
	return isNumber;
}
	
	public void testThread (WebDriver driver, LinkedHashSet set,String ProjectID) {
		
		
		String text = ValidatorAndDetectTruncation.getGetScreenStatus();
		
		if (text!=null){
			try {
				getScrnTruncation(ProjectID,driver);
				System.out.println("Finished");	
			}catch (Exception error) {
				error.printStackTrace();
			}
			
		}else {
				System.out.println("No truncation string");
				System.out.println("Finished");
		}
		
	}
 
}
