package core;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import misc.toolbox;
import misc.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;

import core.vectorCompare;

public class bridge {
	public static void inputValue(WebDriver driver) //miao input to inputValue
	{
		double timer_begin = System.currentTimeMillis()/60000.000;	
		List<WebElement> all = driver.findElements(By.cssSelector("body *"));
		
		for(WebElement e: all){
			//System.out.println("finding input field ....");
			String tag = e.getTagName();
			String name = e.getAttribute("name");
			if(tag.equals("input")){
				
				if(name.equals("subject.Name")||name.equals("Filter.0.Value")){
					System.out.println("input value 192....");
					//e.clear();
					e.sendKeys("192");
				}
			}
		}
		double timer_end = System.currentTimeMillis()/60000.000;	
		double time = timer_end - timer_begin;
		System.out.println("Input value Time: " + time);
	}
	public static void captureDropdownList(WebDriver driver, utility util, String screenFolder)
	{
	         
		List<WebElement> all = driver.findElements(By.cssSelector("body *"));
		for(WebElement e: all)
		{
			String tag = e.getTagName();
			String nodeName = e.getAttribute("name");
			if(tag.equals("select"))
			{
				if(nodeName.equals("subject.AgentType")||nodeName.equals("subject.Type")||nodeName.equals("subject.Namespace")){
					System.out.println("Select is found!");  
					Select sel = new Select(e);
					try{
						for(int i=0;i<sel.getOptions().size();i++)
						{
							sel.selectByIndex(i);
							toolbox.waitSecond(1); //"Capturing dropdown list->Begin");
							try{
							util.getScrn(screenFolder, driver);
							}catch(Exception eg){}
							toolbox.waitSecond(1);// "Capturing dropdown list->Done");
						}
	//				e.sendKeys(Keys.chord(Keys.ALT , Keys.ARROW_DOWN));
						toolbox.waitSecond(1);// "Capturing dropdown list->Begin");
	//				util.getDropdownScrn(screenFolder, driver);
						toolbox.waitSecond(1);//"Capturing dropdown list->Done");
					}
					catch(Exception ex0)
					{
						
					}
				 }
			}
			     
		}

	}
	public static void capturePopupWindow(WebDriver driver, int screen_id, utility util,vectorCompare pv) 
	{
		
		Set<String> windows = driver.getWindowHandles();
		if(windows.size() > 1)
		{
		Iterator<String> id = windows.iterator();
		String old_window = id.next();
		
			while (id.hasNext())
			{
		
			
				System.out.println("[INFO-POPUP]: A popup window is found! Take screenshot");
				WebDriver popup = driver.switchTo().window(id.next());
				String screenFolder = "./screen/";
//				this.input(popup);
				try{
					try{
						util.getScrn(screenFolder, popup);
					}catch(Exception eg){}
					screen_id++;	
				    System.out.println("[ACTION-POPUP]: Close the popup window.\n");
				  popup.close();
				}
				catch(UnreachableBrowserException ex)
				{
					 System.out.println("[ERROR-POPUP]: UnreachableBrowserException: Close the popup window.\n"); 
					popup.close();
				}
				    
			
				
			}
			System.out.println("[ACTION-POPUP]: Go back to parent window!\n");	
		driver.switchTo().window(old_window);	
		}
		
	}
}
