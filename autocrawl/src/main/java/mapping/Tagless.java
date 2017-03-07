package mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Tagless extends _Mode{
	public Tagless(){
		
	}
	public Tagless(int symbol,String container,  WebDriver driver, String screenshotName){
		this.symbol_index = symbol;
		this.driver = driver;
		this.screenshotName = screenshotName;
		this.container = container;
		this.XPath = container;
	}
	
	@Override
	public void find(){		
		this.elementsList = findTaglessElement();
		System.out.println("* <<" + elementsList.size() + ">> elements has PLOC ID");
		LogContext.add("* <<" + elementsList.size() + ">> elements has PLOC ID");
		LogText();
	}
	
	public List<WebElement> findTaglessElement(){
		List<WebElement> resultlist = new ArrayList<WebElement>();
		System.out.println("current container: " + container);
		List<WebElement> allElements = driver.findElements(By.xpath(container + "//*"));
		System.out.println("current all element: -------" + allElements.size());
		
		int totale = toolbox.setID(driver, allElements);
		for(int i=0;i<totale;i++){
			WebElement etemp= findTaglessStringe(driver, i);
			if(etemp != null) resultlist.add(etemp);
		}
		
		return resultlist;
	}
	
	
	
	// to confirm if the node has string which is out of each element
		public static WebElement findTaglessStringe(WebDriver driver, int loc_id ) throws NoSuchElementException{
//	      boolean hasChild = true;
//	      System.out.println("Start finding tagless context");
			WebElement result = null;
			
	        String parent = "//body" + "//*[@loc_id=" + loc_id +"]";
	        String child = "//body" + "//*[@loc_id=" + loc_id +"]/*";
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
		         }else{
		           
		              result = e;
		     
		         	}
		        }
	        return result;
	  }
		@Override
		public void LogText() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void LogAttribute(String attribute) {
			// TODO Auto-generated method stub
			
		}
}
