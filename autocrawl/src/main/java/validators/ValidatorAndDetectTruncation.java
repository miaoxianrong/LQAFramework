package validators;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import validators.DetectElementArea;
import validators.ValidatorAndParser;
import validators.ValidatorUtil;

public class ValidatorAndDetectTruncation {
	

	
	public void truncationCheck(WebDriver driver) throws Exception{
		
		ValidatorUtil.adjustWindow(driver);
		ValidatorAndParser par = new ValidatorAndParser();
		DetectElementArea a = new DetectElementArea();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int count_h=0, count_v=0;
		
		/*
			ArrayList<String >tag = ValidatorUtil.createTaglist();
			Pattern p = Pattern.compile(".*");
			Matcher m = p.matcher(driver.findElement(By.cssSelector("body")).getText());
			
			while(m.find())
			{

				if(!m.group().toString().equals(""))
				{
					String text = m.group().toString().trim();
					System.out.println("Find:" + m.group().toString().trim());
					int count[];
					count = par.locate_element(driver, text, tag);
					count_h = count_h + count[0];
					count_v = count_v + count[1];
					System.out.println("count.length"+count.length);
					System.out.println("-------------------------\n");
					
				}									
			}
				//System.out.println("Horizonal truncation count:" + count_h);
				//System.out.println("Vertical truncation count:" + count_v);
				
				if (count_h+count_v>0) {
					
					this.getScrnTruncation(driver);
					
				}else {
					
					System.out.println("Page need to detect again!");
				}
			*/
					a.area_for_each(driver);
				
				
			
	}
	public void getScrnTruncation (WebDriver driver) throws Exception {
		
		System.out.println("Start capturing screen for truncation");
		
		String name = getStringDateShort().replaceAll(":", "").replaceAll("\\s", "").replaceAll("-", "");
		
		WebDriver augmenteDriver = (WebDriver) new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot)augmenteDriver).getScreenshotAs(OutputType.FILE);
		
		
		String path = "./truncation_screenshot//"; //./truncation_screenshot// //E:\\test\\
		
		 try {    
			 
			 FileUtils.copyFile(screenshot, new File(path+name+".png"));  //"C:\\DTPscript\\"+folder_name+"\\"+ name+".png" //E:\\test

	     } catch (Exception e) {  

	         e.printStackTrace();  

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

}
