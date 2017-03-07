package hardcode;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


import misc.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import db.ManageDB;

public class hardcode
{
	//define UI spy variables
	//File file_hardcode = new File("./report_hardcode.txt"); 
	
	int hardcodescreen_id;
	int page_count= 0;
	int[] hardcode_count= new int[]{0};
	int[] string_count =  new int[]{0};	
	
	String hardcodeScreenPath = "";//"./hardcode_screenshot/";
	 
	

	/*To check if have hardcode problem in current web page, and hardcode validator function begins from below function */

	public boolean detectHardcode(String ProjectID,String DBPosition,String NodePath,WebDriver driver, utility util)
	{
		System.out.println("***************************************************");
		System.out.println("Hardcode Validator 1.05 starts");
		System.out.println("***************************************************");
	    ArrayList<String> hardcodeLog = new ArrayList<String>();
	    hardcodeScreenPath = "./output/" + ProjectID + "/hardcode_screenshot/";
		boolean context = this.extractContexts(ProjectID,DBPosition,NodePath,driver, util, hardcodeLog);
		//	boolean value = this.detectValueHardcode(driver);
		//System.out.println(context +" " +  value);
		if( context )
		{
			// write to log file
//			try {  
//				FileWriter fw =new FileWriter("./report_hardcode.txt",true);
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");					
//				fw.append(strTemp + "------" + df.format(new java.util.Date()) + "\r\n"); 
//				if(fw!=null)  
//					fw.close();  
//				} catch (IOException e) {  
//					 //LogInfo.error(this.getClass().getName(),e.fillInStackTrace().toString());  
//					e.printStackTrace();  
//				}  
			util.getIssueScrn(NodePath,hardcodeScreenPath, driver, hardcodeLog);
			//		hardcodescreen_id++;
			//		System.out.println("After ID: " + hardcodescreen_id);
		}
	    return context; 
	}
	// to confirm if the node has string which is out of each element
	public static String findTaglessString(WebDriver driver, int loc_id ) throws NoSuchElementException{
//      boolean hasChild = true;
//      System.out.println("Start finding tagless context");
		String result = "";
		
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
        }
        else{
//            System.out.println("Node #" + loc_id + " has " + children.size() + " children");

              //comment by miao System.out.println(">>>>>>>>>>>Tagless string found in Node #"  + loc_id+ "<<<<<<<");
              result = removeDupStr(parentText2, subText);
     
        }
        }
        return result;
  }
	
	public static String removeDupStr(String str1_Parent, String str2_Children){
        String[] splitStr = str2_Children.split("\n");
        for(String s: splitStr){
//            System.out.println("[Removing: ]" + s);
              str1_Parent = str1_Parent.replace(s, "");
        }
//      System.out.println("Result: " + str1_Parent);
        return str1_Parent;
  }



	/*To check if current element is the leaf element */
	
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

	/*To extract all elements of current one web page, and assert if have harkcode problem. */

	boolean extractContexts(String ProjectID,String DBPosition,String NodePath,WebDriver driver, utility util, ArrayList<String> hardcodeLog) 
	{
		int seq = 0;
		boolean hardcodeExist = false;
        List<WebElement> elementsList = driver.findElements(By.cssSelector("body *"));
		//System.out.println("Nodes number in Total: " + elementsList.size());		 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean visible = false;		
		for(WebElement e: elementsList){
			try{
				/*check if element is visible*/
		        visible = e.isDisplayed(); 	 
		        /*filter out by visible and clickable*/
		        if(visible)
				{	                	
					/* detect hardcode strings in value*/
		           // String value = e.getAttribute("value");
		        	String value = e.getText();
		            if(value == null || value.equals("")) value = e.getAttribute("value");
		            if(value != null)
					{
						value = value.trim();
						value = value.replaceAll("\t", "").trim();
						value = value.replaceAll("\r", " ").trim();
						value = value.replaceAll("\n", " ").trim();
						
		                boolean hardcode_value = this.assertPseudoHardcode(ProjectID,DBPosition,NodePath,value, hardcodeLog);
	                	if(hardcode_value)
						{		
	                		hardcodeExist = true;
	                		utility.highlightElementY(driver, e);
	                	}
		            }
		                		
	        		js.executeScript("arguments[0].setAttribute('loc_id', arguments[1]);",e, seq);
	        			
	        		//check if out of string under this node
//	        		String outstr = findTaglessString(driver, seq);
//	        		if(!outstr.equals("")){
//	        			boolean hardcode = this.assertPseudoHardcode(ProjectID,DBPosition,NodePath,outstr, hardcodeLog);
//	        			if(hardcode){	        				
//	        				hardcodeExist = true;
//	        				utility.highlightElementY(driver, e);
//	        				
//	        			}
//	        		}
	        		   
	        		
	        		 
	        		 
	        			boolean hasChildren = hasChild(driver, seq);
	        			/*if the node has no child*/
					
	        			if(!hasChildren)
	        			{
	        				String txtvalue = e.getText().trim();
	        				if(txtvalue==null | txtvalue.equals(""))
	        					txtvalue = e.getAttribute("value");
						
	                	
	        				boolean hardcode= this.assertPseudoHardcode(ProjectID,DBPosition,NodePath,txtvalue, hardcodeLog); 

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
				//e2.printStackTrace();
//				System.out.println("[Warning]: Error occurs when detecting element #" + seq);
				utility.writeToLOG("[Warning]: Error occurs when detecting element #" + seq, "./error.log");
			}
			catch(WebDriverException e3)
			{
//				System.out.println("[Warning]: Error occurs when detecting element #" + seq);
				
				utility.writeToLOG("[Warning]: Error occurs when detecting element #" + seq, "./error.log");
			}
              	
		}

		 return hardcodeExist;
	 }
	
	
	
	
	/*to assert if the string has hardcode problem*/
	boolean assertPseudoHardcode(String ProjectID,String DBPosition,String NodePath,String label, ArrayList<String> hardcodeLog){
		boolean hardcodeExist = false;
		if (label != null)
			if(!label.endsWith("]]")){
				if(label.indexOf("]]") !=-1)
					label = label.substring(label.lastIndexOf("]") + 1, label.length());
				hardcodeExist = assertHardcode(ProjectID,DBPosition,NodePath,label.trim(), hardcodeLog);
			}
	
		return hardcodeExist;

	}
	
	boolean assertHardcode(String ProjectID,String DBPosition,String NodePath,String label, ArrayList<String> hardcodeLog){
		boolean hardcodeExist = false;
		String copyright="Copyright\\s.?\\s\\d{4}\\sCA\\sTechnologies.\\sAll\\srights\\sreserved.*";
		//String copyright="Copyright*";
		//System.out.println(label);
        //check if it is copyright        
        if(!label.matches(copyright)) 
        	hardcodeExist = assertHardcode1(ProjectID,DBPosition,NodePath,label.trim(), hardcodeLog);
        return hardcodeExist;
	}

    boolean assertHardcode1(String ProjectID,String DBPosition,String NodePath,String label, ArrayList<String> hardcodeLog)
    {
		
				ArrayList<String> termList = this.getTermlist();
                boolean hardcodeExist = false;
                boolean term=false;
                String number = "\\(?\\d+%?\\)?";
                //String number = "[0-9]+(.[0-9]+)?(%)?";                                       
                //String english_string = "((\\pP)*)([a-zA-Z]+)(\\pP)*";           
                String english_string = "((\\pP)*)([a-zA-Z]+[-]?[a-zA-Z]+)(\\pP)*";
                //split the string into single word*/      
                
             // whole string to compare term list 
                for(int s=0;s<termList.size();s++){
                     if(label.trim().equals(termList.get(s).trim())){
                           term=true;
                     }
                }
                if(!term){          
	                //System.out.println("*********source:" + label + "********");
	                String[] split = Pattern.compile("\\s").split(label);
	                
	                String strENU = "";
	                
	                ManageDB m = new ManageDB();
					m.getConnection(DBPosition);
	                ResultSet rs_search=null; 
	                boolean bSameHardcode=false;
	                String UniqueStringListID="0";
	                
	                for(int j=0;j<split.length;j++)
	                {           
	                			//System.out.println("current str:" + split[j] + "********");
	                			
	                            //try to match termnology defined by user.                      
	                            if (split[j].trim().equals("")) continue;
	                            term = Matchterm(termList,split[j]);                                                          
	                            
	                            if (term)
	                                        continue;                                  
	                            /*if the single word is an English word and it is not a number*/
	                            if(split[j].matches(english_string) && !split[j].matches(number))
	                            {
	                            
	                                        strENU = strENU + " " + split[j];
	                                      // System.out.println("enu str:" + strENU + "********");
	                                        term = Matchterm(termList,strENU.trim());
	                                        		if(term){
									                            strENU="";
									                            hardcodeExist = false;                           
	                                        					}
													else {
	                                                     hardcodeExist = true;                           
	
															}                                                    
	
	                            }
	                            else
	                            {
	                                        if(!strENU.equals("")){                                                     
	//                                                    String strTemp = "[Hard Code]:Find an English string [" +strENU.trim() +"] in [" + label.trim() + "]\r\n";
	//                                                    System.out.println(strTemp);
	//                                                    hardcodeLog.add(strTemp);
	//                                                    strENU = "";
	                                        	hardcodeExist = true;
	                                                    break;
	                                        	}
	                            }
	                }
	                if(hardcodeExist){
		                if(!strENU.equals("")){ 
		                	
		                	String Hardcode=strENU.trim();
		                	String s2=label.trim();
		                	Hardcode=Hardcode.replace('\'', ' ');
		                	Hardcode=Hardcode.replace(',', ' ');	
							if(Hardcode.length()>200){
								Hardcode=Hardcode.substring(0,200);
							}
		                	
							String sql="";	
							sql="select * from UniqueStringList where ";
							sql+="Hardcode='"+Hardcode+"'";
							sql+="and ProjectID='"+ProjectID+"'";
							bSameHardcode=true;
							try{
								rs_search=m.getSQL(sql); 
								if(rs_search.next()){
									bSameHardcode=true;
								}else{
									bSameHardcode=false;
								}
								rs_search.close();
								rs_search=null;
							}catch(Exception ex){}
							if(bSameHardcode){
							 
							}else{
								sql="insert into UniqueStringList set ";
								sql+="Hardcode='"+Hardcode+"'";
								sql+=",ProjectID='"+ProjectID+"'";
								m.updateUnique(sql);
							 }
							 sql="select * from UniqueStringList where ";
							 sql+="Hardcode='"+Hardcode+"'";
							 sql+="and ProjectID='"+ProjectID+"'";
							 try{
								 rs_search=m.getSQL(sql); 
								 if(rs_search.next()){
									 UniqueStringListID=rs_search.getString("UniqueStringListID");
								 }
								 rs_search.close();
								 rs_search=null;
							 }catch(Exception ex2){}
							sql="insert into HardcodeList set ";
							sql+="ProjectID='"+ProjectID+"'";
							sql+=",NodePath='"+NodePath+"'";
							sql+=",UniqueStringListID="+UniqueStringListID;
							sql+=",Hardcode='"+Hardcode+"'";
							m.updateSQL(sql);
							
							
							String strTemp = "Find an English string [" +strENU.trim() +"] in [" + label.trim() + "]\r\n";
		                    //System.out.println(strTemp);
		                    hardcodeLog.add(strTemp);
	                	}              
	                }      
	                m.close();
                }
                return hardcodeExist;
    }


	
	void highlightElement(WebDriver driver, String label)
	{
		ArrayList<WebElement> elements = this.getElements(driver, label);
		//System.out.println(elements.size());
		for(WebElement e: elements)
		{
			utility.highlightElementY(driver, e);
		}				
			
	}

	// try to match Terminology defined by user (by default single char is included)
	
	boolean Matchterm( ArrayList<String> termList, String label){
		boolean MatchTerm = false;
		//single char, pass
		if(label.length()==1){
			MatchTerm = true;			
		}else
		{
			// try to match term list
			for(int k=0;k<termList.size();k++)
			{
				if(label.matches(termList.get(k).toString()))
				{
					MatchTerm = true;
					break;
				}
			}	
			if(!MatchTerm){
				//not match term, confirm if at the end have other character
//				System.out.println("debug" + label);
				String labeltemp = label.substring(0, label.length() -1);
				for(int k=0;k<termList.size();k++)
				{
					if(labeltemp.matches(termList.get(k).toString()))
					{
						MatchTerm = true;
						break;
					}
				}	
			}
			if(!MatchTerm){
				//not match term, confirm if at the begin have other character
				String labeltemp = label.substring(1, label.length());
				for(int k=0;k<termList.size();k++)
				{
					if(labeltemp.matches(termList.get(k).toString()))
					{
						MatchTerm = true;
						break;
					}
				}	
			}
		
			if(!MatchTerm){
				//not match term, confirm if at the begin and end have other character
				String labeltemp = label.substring(1, label.length() -1);
				for(int k=0;k<termList.size();k++)
				{
					if(labeltemp.matches(termList.get(k).toString()))
					{
						MatchTerm = true;
						break;
					}
				}	
			}
		}
		return MatchTerm;
	}

	/*read Terminology file defined by user, */
	 ArrayList<String> getTermlist() 
	{
		ArrayList<String> term_list = new ArrayList<String>();
		String text = new String();
		
		File file_term = new File("./Terminology.txt"); 
		try
		{
			if(file_term.exists())
			{
				BufferedReader br = new BufferedReader (new FileReader(file_term));
				while((text = br.readLine()) != null)  
				{
					text = text.trim();
					if (!text.equals("")){	
						term_list.add(text);	
						//System.out.println("[Terminology: ==]"+text);
					}
				}
				br.close();
			}
			else
			{
				//FileWriter fw = new FileWriter("./Terminology.txt");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return term_list;
	}
	
	


	/*---------------------------------------------------------------------------------------------------------*/



	
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
	
	
}
