package mapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;



import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Interface.ILog;

public class concurrentMapping implements Runnable {

	WebElement e;
	int seq;
	WebDriver driver;
	String container;
	String screenshot;
	String detection;
	ILog log;
	
	public void run(){
		 try{
			
			 /*visible*/
           boolean visible = e.isDisplayed();

           String locid = e.getAttribute("loc_id");
//          System.out.println(e.getText().trim() + visible);
          
           	/*filter out by visible and clickable*/
//           System.out.println(seq);
           	if(visible)
                {	                		                	      	
        			String context = e.getText().trim();
        			 String value = e.getAttribute("value");
        	           if(value !=null && value.contains("[[")){
        	        	   context = value;
        	           }
        	          
        			if(!context.equals(""))
        			{
        			String coordinate = "Coordinate: " + e.getLocation();
        			
        			int height,width;
        			
        			height = e.getSize().getHeight() + 10;
        			width = e.getSize().getWidth();
        			
        		
        			
					String dimension = "Dimension: (" + width + ", " + height + ")";
//					 System.out.println(context + coordinate + dimension);
					boolean allZero = false;
					if(e.getLocation().x == 0 && e.getLocation().y == 0 && e.getSize().height == 0 && e.getSize().width == 0){
						allZero = true;
					}

					if(!allZero){
					/* split concatination string*/
					String[] concat = context.split("]]");
					if(concat.length > 1){
						System.out.println(concat.length + " units found in " + context);
					}
					for(String s: concat){
						System.out.println(screenshot);
						System.out.println("Find: " + s + "]]");
						System.out.println(coordinate);
						System.out.println(dimension);        						      
						System.out.println("-------------------------\n");	        					
						ArrayList<String> text_to_string = new ArrayList<String>();
						text_to_string.add(screenshot);
						text_to_string.add("Find: " + s + "]]");
						text_to_string.add(coordinate);
						text_to_string.add(dimension);
						writeToFile(text_to_string);
						text_to_string = null;
					}
				
				
					}
        			}
        	   if(locid != null){
        		   int i = Integer.parseInt(locid);
        		   toolbox.findTaglessString(driver, i, screenshot, container);
        	   }
//        	System.gc();
        }
          
	 }
	 catch(StaleElementReferenceException e2)
	 {
	
	 }
		 
	}
	
	static boolean hasChild(WebDriver driver, int loc_id)
	 {
		 boolean hasChild = true;
		 String child = "//body//*[@loc_id=" + loc_id +"]/*";
		 List<WebElement> children= driver.findElements(By.xpath(child));
		 if(children.size() == 0)
		 {
			 hasChild = false;
		 }
		 return hasChild;
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
	
}
