package validators;


import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import misc.utility;


	
public class CopyOfhardcode
{
	//define UI spy variables
	File file_hardcode = new File("./report_hardcode.txt"); 
	
	int hardcodescreen_id;
	int page_count= 0;
	int[] hardcode_count= new int[]{0};
	int[] string_count =  new int[]{0};	
//	List<String> hardcode_list = new ArrayList();
	String hardcodeScreenPath = "./hardcode_screenshot/";
	
	static boolean hasChild(WebDriver driver, int loc_id)
	 {
		 boolean hasChild = true;
//		 System.out.println("//body//*[@loc_id=" + loc_id +"]");
		 String child = "//body//*[@loc_id=" + loc_id +"]/*";
		 List<WebElement> children= driver.findElements(By.xpath(child));
//			WebElement nodeToParent = driver.findElement(By.xpath("//body//*[@loc_id='" + loc_id + "']"));	

//		 System.out.println(children.size());
		 if(children.size() == 0)
		 {
			 hasChild = false;
		 }
		 return hasChild;
	 }
	boolean extractContexts(WebDriver driver, utility util)
	{
		 int seq = 0;
		 boolean hardcodeExist = false;
         List<WebElement> elementsList = driver.findElements(By.cssSelector("body *"));
		 System.out.println("Nodes number in Total: " + elementsList.size());
		 
		 JavascriptExecutor js = (JavascriptExecutor) driver;

		 boolean visible = false;

		
			 for(WebElement e: elementsList){
				 try{
						 /*visible*/
		               visible = e.isDisplayed(); 	 
		               	/*filter out by visible and clickable*/
		               	if(visible)
			                {	                	
		               		/* detect hardcode strings in value*/
		               		String value = e.getAttribute("value");
		                	if(value != null){
		                		value = value.trim();
		                		boolean hardcode_value = this.assertHardcode(value);
	                			if(hardcode_value){
	                				hardcodeExist = true;
	                				utility.highlightElementY(driver, e);
	                			}
		                	}
		                		
	        				js.executeScript(
	        						"arguments[0].setAttribute('loc_id', arguments[1]);",
	        						e, seq);
	        				
	        			
	                	 boolean hasChildren = hasChild(driver, seq);
	                	 /*if the node has no child*/
	                	if(!hasChildren)
	                	   {

	                			String context = e.getText().trim();
//	                			System.out.println(context);
	                			boolean hardcode = this.assertHardcode(context);
	                			if(hardcode){
	                				hardcodeExist = true;
	                				utility.highlightElementY(driver, e);
	                			}
	                	   }
	                }
              	seq++;
				 }
				 catch(StaleElementReferenceException e2)
				 {
	//				 e2.printStackTrace();
				 }
              	
              }

		 return hardcodeExist;
	 }
	
	
	
	
	int findChildren(WebDriver driver, List<WebElement> parent,String currentLevel, ArrayList<String> stringList, boolean hardcodeExist)
	{
//		boolean hardcodeExist = false;
		int childrenNumber=0;
		
		   for(int i=0;i<parent.size();i++)
		   {
			   String text;
			   String nextLevel = currentLevel+ "[" + (i+1) + "]/*";
			   WebElement currentElement = driver.findElement(By.xpath(currentLevel+ "[" + (i+1) + "]"));
			   List<WebElement> children = driver.findElements(By.xpath(nextLevel));
			   childrenNumber = children.size();
			   if(childrenNumber !=0)
			   {
				   this.findChildren(driver, children, nextLevel, stringList, hardcodeExist);
			   }
			   else
			   {
				   text = currentElement.getText();
				   if(!text.equals(""))
				   {
					   
					   hardcodeExist = this.assertHardcode(text);
				   }
			   }
		   }
		
		return childrenNumber;
	}
	
	ArrayList<WebElement> findElements(WebDriver driver, List<WebElement> parent,String currentLevel, String Label, ArrayList<WebElement> elements)
	{
		
		int childrenNumber=0;
//		ArrayList<WebElement> elements = new ArrayList<WebElement>();
		   for(int i=0;i<parent.size();i++)
		   {
			   String text;
			   String nextLevel = currentLevel+ "[" + (i+1) + "]/*";
			   WebElement currentElement = driver.findElement(By.xpath(currentLevel+ "[" + (i+1) + "]"));
			   List<WebElement> children = driver.findElements(By.xpath(nextLevel));
			   childrenNumber = children.size();
			   if(childrenNumber !=0)
			   {
				   this.findElements(driver, children, nextLevel, Label, elements);
			   }
			   else
			   {
				   text = currentElement.getText();
				   if(text.equals(Label))
					   elements.add(currentElement);
			   }
		   }
		
		return elements;
	}
	
	boolean getStringList(WebDriver driver)
	{
		ArrayList<String> stringList = new ArrayList<String>();
		String xml_root = "//body/*";
		boolean hardcodeExist = false;
		List<WebElement> allElement = driver.findElements(By.xpath(xml_root));
//		
      
		this.findChildren(driver, allElement, xml_root, stringList, hardcodeExist);
		
		
		
		return hardcodeExist;
	}
	
	ArrayList<WebElement> getElements(WebDriver driver, String Label)
	{
		
		ArrayList<WebElement> elements = new ArrayList<WebElement>();
		String xml_root = "//body/*";
		
		List<WebElement> allElement = driver.findElements(By.xpath(xml_root));
//		
      
		this.findElements(driver, allElement, xml_root, Label, elements);
		
		
		
		return elements;
	}
	
	ArrayList<String> filter(ArrayList<String> stringList)
	{
		ArrayList<String> stringFilter = new ArrayList<String>();
		ArrayList<String> term_lists = getTermlist();
		String number = "[0-9]+(.[0-9]+)?(%)?(:)?(.)?";
		boolean term = false;
		
		for(String e: stringList)
		{
/*if the string is a number, skip it;*/
			if(e.trim().matches(number))
			{
				
				continue;
			}

//if the string is blank, skip it;
			if(e.trim().equals(""))
				continue;	
			
//if the string is a term, skip it;			
			for(int k=0;k<term_lists.size();k++)
			{
				if(e.trim().matches(term_lists.get(k).toString()))
				{
					//System.out.println("This is a Terminology, skip it!\n");
					term = true;
					break;
				}
			}
			if (term)
				continue;
			
//if the string is not a number, not blank, not a term, add it to string list;
		stringFilter.add(e);
		}
		return stringFilter;
	}
	
boolean assertHardcode(String label)
	{
		ArrayList<String> termList = this.getTermlist();
		boolean hardcodeExist = false;
		boolean term=false;
		String number = "[0-9]+(.[0-9]+)?(%)?";				
		String english_string = "((\\pP)*)([a-zA-Z]+)(\\pP)*";	

//split the string into single word*/	
		String[] split = Pattern.compile("\\s").split(label);
		
		for(int j=0;j<split.length;j++)
		{	
//				System.out.println("[" + split[j] + "]");
/*if the single word is a term, skip it.*/
			for(int k=0;k<termList.size();k++)
			{
				if(split[j].matches(termList.get(k).toString()))
				{
					term = true;
//					System.out.println("This is a term");
					break;
				}
			}
			if (term)
				continue;			
			/*if the single word is an English word and it is not a number*/
			if(split[j].matches(english_string) && !split[j].matches(number))
			{
				System.out.println("[Hard Code]:Find an English string [" +split[j] +"] in [" + label.trim() + "]. Probably this is a hard code!Highlight it!\n");
				hardcodeExist = true;			 
				break;
			}
			else
			{
//				System.out.println("This is not an English string!");
			}
		}
		return hardcodeExist;
	}
	
	boolean detectContextHardcode(WebDriver driver) 
	{		
//		FileWriter fw_hardcode = null;
//		try {
//			fw_hardcode = new FileWriter("./report_hardcode.txt",true);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		BufferedWriter bw_hardcode = new BufferedWriter(fw_hardcode);
//		ArrayList<String> term_lists = this.getTermlist();
//		ArrayList<String> stringList = new ArrayList<String>();
		boolean hardcode_exist = false;
		

		hardcode_exist = this.getStringList(driver);
		
//        stringList = this.filter(stringList);
//		System.out.println("The string list is: " + stringList.toString() + "\n");
//		int s=0;

			
		
		
		/*start highlight the hard code string	*/	
//		while(stringList.size()!=0)
//		{
//			
//			boolean term = false;
//			String number = "[0-9]+(.[0-9]+)?(%)?";				
//			String english_string = "(\\w+\\s)*\\w+(:)?(.)?";			
////			System.out.println(stringList.get(s).toString());
////split the string into single word*/	
//			String[] split = Pattern.compile("\\s").split(stringList.get(s).toString());
//			
//			for(int j=0;j<split.length;j++)
//			{	
//
///*if the single word is a term, skip it.*/
//				for(int k=0;k<term_lists.size();k++)
//				{
//					if(split[j].matches(term_lists.get(k).toString()))
//					{
//						//System.out.println("This is a Terminology, skip it!\n");
//						term = true;
//						break;
//					}
//				}
//				if (term)
//					continue;
//				
//				/*if the single word is an English word and it is not a number*/
//				if(split[j].matches(english_string) && !split[j].matches(number))
//				{
//					System.out.println("[Hard Code]:Find an English string [" +split[j] +"] in [" + stringList.get(s).toString().trim() + "]. Probably this is a hard code!Highlight it!\n");
//					try {
//						bw_hardcode.write("[Hard Code]: [" + stringList.get(s).toString() + "]. Probably this is a hard code issue! ");
//					    bw_hardcode.newLine();
//	                    bw_hardcode.flush();
//						
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//                
//                    System.out.println("Highlight: " + stringList.get(s).toString());
//					this.highlightElement(driver,stringList.get(s).toString());
//					hardcode_exist = true;
//					 
//					break;
//				}
//				else
//				{
//					//s++;
//				}
//			}
//
//
//			stringList.remove(s);
//		}


		return hardcode_exist;
	}

	boolean detectValueHardcode(WebDriver driver) 
	{
//	    System.out.println("START VALUE");
		boolean hardcode_exist = false;

		String number = "[0-9]+(.[0-9]+)?(%)?";				
		String english_string = "(\\w+\\s)*\\w+(:)?(.)?";	
		
        List<WebElement> allElements = driver.findElements(By.cssSelector("body *"));

        for(WebElement e: allElements)
        {
        	String value = e.getAttribute("value");
        	if(value != null){
        		value = value.trim();
//        		 System.out.println("[INFO-ValueHardcode]: " + value);
        		if(value.matches(english_string) && !value.matches(number))
					{	  
        			  utility.highlightElementY(driver, e);
	        		  hardcode_exist = true;
	        		  
	        		}
        	}
		
        }
		return hardcode_exist;
	}
	
public void detectHardcode(WebDriver driver, utility util)
{
	System.out.println("***************************************************");
	System.out.println("Hardcode Validator 1.05 starts");
	System.out.println("***************************************************");
	
	boolean context = this.extractContexts(driver, util);
//	boolean value = this.detectValueHardcode(driver);
	//System.out.println(context +" " +  value);
	if( context )
	{
//		System.out.println("Current ID: " + hardcodescreen_id);
//	     hardcodescreen_id = util.screenID;
	 
		// comment on 2015/1/23 due to update util.getIssueScrn(hardcodeScreenPath, driver);
//		hardcodescreen_id++;
//		System.out.println("After ID: " + hardcodescreen_id);
	}
	          
}
	
void highlightElement(WebDriver driver, String label)
		{
                ArrayList<WebElement> elements = this.getElements(driver, label);
//                System.out.println(elements.size());
							for(WebElement e: elements)
							{
								utility.highlightElementY(driver, e);
							}
						
			
		}
			
	
	
	 List<String> deduplicate(List<String> hardcode_list)
	{
		List<String> hardcode_dedup = new ArrayList<String>();
		hardcode_dedup.add(hardcode_list.get(0));
		for(int i=1;i<hardcode_list.size();i++)
		{
			boolean string_exist = false;
			for(int j=0;j<hardcode_dedup.size();j++)
			{
				if(hardcode_list.get(i).toString().equals(hardcode_dedup.get(j)))
				{
					string_exist  = true;
					break;
				}
				
				
			}
			if(!string_exist)
			{
				hardcode_dedup.add(hardcode_list.get(i));
			}
		}
		return hardcode_dedup;
	}

	 ArrayList<String> getTermlist() 
	{
		ArrayList<String> term_list = new ArrayList<String>();
		String text = new String();
		
		File file_term = new File("./Terminology.txt"); 
		try{
		if(file_term.exists())
		{
		 BufferedReader br = new BufferedReader (new FileReader(file_term));
		while((text = br.readLine()) != null)  
		 {
			 term_list.add(text);
		 }
		br.close();
		}
		else
		{
//			FileWriter fw = new FileWriter("./Terminology.txt");
		}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		 return term_list;
	}
	

	
	
}
