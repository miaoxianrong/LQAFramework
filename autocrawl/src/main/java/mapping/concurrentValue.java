package mapping;

import java.util.List;

import org.openqa.selenium.WebElement;

public class concurrentValue  implements Runnable{ 
	WebElement e;
	List<WebElement> elementsList;
	public void run(){
		
		 String value = e.getAttribute("value");
    	 if(value != null && value.contains("[[")){
    		 elementsList.add(e);
    	 }
	}
}
