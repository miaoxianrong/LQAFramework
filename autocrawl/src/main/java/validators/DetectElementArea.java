package validators;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


import validators.ValidatorAndDetectTruncation;
import validators.ValidatorUtil;

public class DetectElementArea {
	

	
	public boolean detection(WebElement element)
	{
		
	
		
		boolean truncation = false;
		
		int parent_area = this.get_area(element);
		int area=0;
		
		try
		{	
			
			
			WebElement child_first = element.findElement(By.xpath("./*[1]"));
			System.out.println("Parent web element area: " + element.getTagName() + ": " + this.get_area(element));
			
			List<WebElement> objs_temp = child_first.findElements(By.xpath("./following-sibling::*"));
			
			System.out.println(objs_temp.size() + 1 + " child web elements:");
			area = area + output_valid_area(child_first);
			for(WebElement e: objs_temp)
			{
			area = area + output_valid_area(e);
			
			}
			
			System.out.println("In total area: " + area);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("This element has no child!" );
		}
		if(parent_area < (area*0.85)) //area*0.95
		{
			
			try {
				
				//Report log = new Report();
				System.out.println("!!!!!!!!!!!!!!!!!!!!!Truncation detected!" +"elementText"+element.getText());
				String pa = Integer.toString(parent_area);
				//log.PrintlnwirteReport("parent_area:"+pa+"XXXXX"+"area:"+area+"xxxxxxx"+"area*0.85:"+area*0.85+"xxxxxxxx");	
				//log.PrintlnwirteReport("elementText"+element.getText());
				//log.PrintlnwirteReport(Report.getStringDateShort());
				//log.PrintlnwirteReportflush();
				//log.PrintlnwirteReportClose();
				
			}catch (Exception error) {
				
			}
			
			
			truncation = true;
		}
		return truncation;
	}

	public int get_area(WebElement element)
	{
		
		int height = element.getSize().height;
		int width = element.getSize().width;
		return height*width;
	}
	public boolean valid(WebElement element)
	{
		boolean valid = false;
		try{
			List<WebElement> objs_temp = element.findElements(By.xpath("./*"));
			//List<WebElement> objs_temp =element.findElements(By.xpath("//body//*"));
			if(!element.getText().trim().equals("") || objs_temp.size() != 0)
			 valid = true;
		}
		catch(StaleElementReferenceException e)
		{
			System.out.println("[ERROR]: Element is no longer attached to the DOM!");
		}
		return valid;
	}
	public int output_valid_area(WebElement e)
	{
		int area=0;
		if(valid(e))
		{
			System.out.println(e.getTagName()+ ": " + this.get_area(e));		
			area = this.get_area(e);
		}
		else
		{
			System.out.println(e.getTagName() + ": " + "This element is not valid");
		}
		return area;
	}
	
	public void area_for_each(WebDriver driver) throws Exception
	{
		int seq_number = 1;
		int truncation_count = 0;
		List<WebElement> objs_temp = driver.findElements(By.cssSelector("body *"));
		
		for(WebElement e: objs_temp)
		{
			
			if (elementExit(driver,e)){
				
				//System.out.println("Element #: " + seq_number);
				
				if(valid(e))
				{
					if(detection(e))
					{
						truncation_count++;
						ValidatorUtil.highlight_Element(driver, e);
						//ValidatorAndDetectTruncation.highlightElement(driver, e);
						System.out.println("xxxxxxxxxxxxxxxx"+" "+truncation_count);
						
					}
						System.out.println("\n");
				}
				else
				{
					try
					{
						System.out.println(e.getTagName() + ": " + "This element is not valid\n");
					}
					catch(StaleElementReferenceException e2)
					{
						
					}
				}
				seq_number++;	
			}
			
		}
			if (truncation_count>0) {
				ValidatorAndDetectTruncation  dt = new  ValidatorAndDetectTruncation();
				dt.getScrnTruncation(driver);
				System.out.println("there are"+" "+truncation_count+" "+"truncated strings in current page");
				System.out.println("Detetion of the truncation is completed,please refer to truncation folder for screens");
			}else {
				System.out.println("There are not any truncated strins in this page.");
			}
			
	}
	

public boolean elementExit (WebDriver driver,WebElement element)  {
	
	boolean ele = false ;
	try {
		boolean visible = element.isDisplayed();
		
		if (visible && ExpectedConditions.elementToBeClickable(By.xpath(element.toString()))!=null) {
			
			if (elementElement(driver,element.getText())){
				
				ele = true;
				
			}else {
				System.out.println("Element is invalidated element");
				
				ele = false ;
			}
		}
	}catch (Exception erro ){
		System.out.println("Element not found in the cache");
	}
	
	
	
	
	
	return ele;
}

public  boolean  elementElement (WebDriver driver,String str) {
	
	String xpathtemp = "//body//*[text()='%s']";
 	String xpath ="temp";
 	xpath = String.format(xpathtemp ,str);
 	boolean existElement = false ;
 	
   try {
	   driver.findElement(By.xpath(xpath));
	   existElement=true;
   }catch (Exception error){
	   //System.out.println("Element is not existed in current page");
	   existElement=false;
   } 
   
   return existElement;
}
}
