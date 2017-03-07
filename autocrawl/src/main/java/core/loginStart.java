package core;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import misc.toolbox;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import db.RunDB;
import misc.SendMail;
public class loginStart {

	
	public static String readXML(String key) 
	{
	    	String value = null;
	        try
	        {
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = factory.newDocumentBuilder();
                    // URL url = this.getClass().getResource("/com/path/to/file.txt")
                    //File file = new File(url.toURI());
	            Document doc = db.parse(new File("./parameter.xml"));
	            //Document doc = db.parse(new File("c:\\jar\\parameter.xml"));
	            Element elmtInfo = doc.getDocumentElement();
	            NodeList nodes = elmtInfo.getChildNodes();
	            
	            for (int i = 0; i < nodes.getLength(); i++)
	            {
	                Node result = nodes.item(i);
	                if (result.getNodeType() == Node.ELEMENT_NODE && result.getNodeName().equalsIgnoreCase(key))
	                {
	                	value = result.getTextContent();
	                }
	            }
	        }
	        catch (ParserConfigurationException ex)
	        {
	        	System.out.println("[Error:] parameter.xml is not configured correctly, please check it.");
	            //ex.printStackTrace();
	        }
	        catch (SAXException ex)
	        {
	        	System.out.println("[Error:] parameter.xml is not configured correctly, please check it.");
	            //ex.printStackTrace();
	        }
	        catch (IOException ex)
	        {
	        	System.out.println("[Error:] parameter.xml is not configured correctly, please check it.");
	            //ex.printStackTrace();
	        }
			return value;
	}
	public static boolean checkPageLoadDone(WebDriver driver, String ProjectID,String ProductName,String DBPosition,String NodePath)
	{
//		double timer_begin_2 = System.currentTimeMillis()/1000.000;
	
		boolean found = false;
		String test_css="body *";
		String test_xpath="/html";
		WebElement e =null;
//		switch(ProductName){
//    		case "ServiceCatalog": 
//    			test_css="a.about"; 
//    		break;
//    		case "SiteMinder": 
//    			test_css="a.ca-footer-linktext"; 
//    		break;
//    		case "Clarity": 
//    			test_css="table.ppm_grid"; 
//    			test_css="div.ppm_page_content"; 
//    			test_css="table.ppm_portlets"; 
//    			test_css="div.ppm_fixed_button_bar";
//    		break;
//    		case "ITPAM": 
//    			test_css="a#PAM_HOME_PAGE_signoutLink";
//    		break;
//		}
		test_xpath="/html";
		try
		{			
			//e=driver.findElement(By.cssSelector(test_css));
			e=driver.findElement(By.xpath(test_xpath));
			found = true;
		}
		catch(Exception ex)
		{
			found = false;
		}
		if(found){
			
		}else{
			test_xpath="/frame";
			try
			{			
				e=driver.findElement(By.xpath(test_xpath));
				found = true;
			}
			catch(Exception ex)
			{
				found = false;
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Page load failure with "+test_css+" for NodePath "+NodePath);			
			}
		}
//		double timer_end_2 = System.currentTimeMillis()/1000.000;
//		double time_spent_2 = timer_end_2 - timer_begin_2;	
//		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] checkPageLoadDone time spent: " + time_spent_2 + " seconds");
		return found;
	}		
	
}
