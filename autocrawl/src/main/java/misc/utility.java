package misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.UnreachableBrowserException;



public class utility {

	public int screenID;
	public int issuescreenID;
	//adjust browser window size
		public static void timer(double timer_beforenumber)
		{
			System.out.println("//-------------Timer-----------");
//			AI_sensor s = new AI_sensor();
//			DecimalFormat df = new DecimalFormat("0.0");
//			int screen_count = s.files_filter("./screen");
//			double timer_afternumber = System.currentTimeMillis()/60000.000;
//			double time_spent = timer_afternumber- timer_beforenumber;
//			screen_count = s.files_filter("./screen");
//			double metric1 = (screen_count/time_spent)*60*24;
//			double metric2 = (screen_count/time_spent);
//			System.out.println("Time spent: " + df.format(time_spent) + " minutes");
//			System.out.println("Avarage: " + df.format(metric2) + " screens per minutes");							
//			System.out.println("Avarage: " + df.format(metric1) + " screens per day\n");
		}
		public static void writeToLOG(String context, String fileName)
		 {
			
			try{
			FileWriter fw= new FileWriter(fileName,true) ;	
			BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(context);
			bw.newLine();
		    bw.flush();
		    fw.close();
			bw.close();
			}
			catch(IOException e)
			{
				
			}
	}
		public static void waitMin(int second,String s1)
		{
//			System.out.println("[Info:] Wait " + second + " seconds!");
			try {
				int i;
				//second = second +5;
				for(i=second;i>0;i--)
				{
				//System.out.println(i);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}	
		public ArrayList<String> readUrlLOG(String urlfile)
		 {
			ArrayList<String> url_List = new ArrayList<String>();
			try{
			 File url= new File(urlfile); 
			 String page = "";
			
					if(url.exists())
					{
					 BufferedReader br = new BufferedReader (new FileReader(url));
					while((page = br.readLine()) != null)  
					 {
						url_List.add(page);
					 }
					br.close();
					}
			}
			catch(IOException e)
			{
				
			}
			return url_List;
	    }	
		public static void getScreenFolder()
		{
			String[] screenshot_path = new String[4];
			screenshot_path[0] = "./screen";
			screenshot_path[1] = "./hardcode_screenshot";
			screenshot_path[2] = "./truncation_screenshot";
			screenshot_path[3] = "./iFormat_screenshot";


		
			for (int i=0; i<4; i++)
			{
				//create the screenshot folder	
				File file = new File (screenshot_path[i]);
				if(!file.exists())
				{
					file.mkdirs();
				}
				else
				{
					//clear the screenshot folder	
					screen_clear(screenshot_path[i]);									
				}
			}
		}
		public static void confirmScreenFolder()
		{
			String[] screenshot_path = new String[4];
			screenshot_path[0] = "./screen";
			screenshot_path[1] = "./hardcode_screenshot";
			screenshot_path[2] = "./truncation_screenshot";
			screenshot_path[3] = "./iFormat_screenshot";


		
			for (int i=0; i<4; i++)
			{
				//create the screenshot folder	
				File file = new File (screenshot_path[i]);
				if(!file.exists())
				{
					file.mkdirs();
				}
				else
				{
					//clear the screenshot folder	
					//screen_clear(screenshot_path[i]);									
				}
			}
		}
		public void adjustWindow(WebDriver driver)
		{
	/*		Dimension window = driver.manage().window().getSize();
					    int height = window.height;
					    height = height/2;
					    Dimension win = new Dimension(window.width,height);
					    driver.manage().window().setSize(win);*/
		}
		
		
		public void getIssueScrn(String NodePath,String screenFolder, WebDriver driver, ArrayList<String> issueLog)
		 {
			String FullScreenFolderAndName = screenFolder + issuescreenID + ".png";
			FullScreenFolderAndName = screenFolder + NodePath + ".png";
			String screenName = NodePath + ".png";
			// write to log file
			try {  
				FileWriter fw =new FileWriter("./report_hardcode.txt",true);
								
				//fw.append(issuescreenID + ".png" + "\r\n" );
				//fw.append(NodePath + ".png" + "\r\n" ); 
				for(String s: issueLog)
				{
					fw.append("[" + screenName + "]:" + s); 
				}
				
				if(fw!=null)  
					fw.close();  
				} catch (IOException e) {  
					 //LogInfo.error(this.getClass().getName(),e.fillInStackTrace().toString());  
					e.printStackTrace();  
				}  
			issuescreenID++;
			try
			{
			//	driver = new Augmenter().augment(driver);
			
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(scrFile, new File(FullScreenFolderAndName));
			System.out.println("Take an issue screenshot: " + FullScreenFolderAndName);
			
			}
			catch(WebDriverException e)
			{
			//	System.out.println("//----------Screen Capture failed! Capture again!");
				waitMin(5, "//----------Screen Capture failed! Capture again!");
				driver = new Augmenter().augment(driver);
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				System.out.println("Screenshot: " + FullScreenFolderAndName);
			
				try {
					FileUtils.copyFile(scrFile, new File(FullScreenFolderAndName));
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

	}
//		
//		public void getIssueScrn(String screenFolder, WebDriver driver)
//		 {
//			String screenName = screenFolder + issuescreenID + ".png";
//			issuescreenID++;
//			try
//			{
//		//		driver = new Augmenter().augment(driver);
//		
//			File scrFile = ((TakesScreenshot) driver)
//					.getScreenshotAs(OutputType.FILE);
//		
//			FileUtils.copyFile(scrFile, new File(screenName));
//			System.out.println("Screenshot: " + screenName);
//		
//			}
//			catch(WebDriverException e)
//			{
//		//		System.out.println("//----------Screen Capture failed! Capture again!");
//				toolbox.waitSecond(5);
//				driver = new Augmenter().augment(driver);
//				File scrFile = ((TakesScreenshot) driver)
//						.getScreenshotAs(OutputType.FILE);
//				System.out.println("Screenshot: " + screenName);
//		
//				try {
//					FileUtils.copyFile(scrFile, new File(screenName));
//					
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//			catch(IOException e)
//			{
//				e.printStackTrace();
//			}
//
//	}
	//define loc id for each element
		public static void number_Element(WebDriver driver, WebElement element,
				int seq_number) {
			for (int i = 0; i < 2; i++) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].setAttribute('loc_id', arguments[1]);",
						element, seq_number);
				
			}
		}

		public static void add_onmouseover(WebDriver driver, WebElement element,
				String string) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('onmouseover', arguments[1]);",
					element, "tooltip.pop(this," + string + ")");
		}

		public static void highlightElementY(WebDriver driver, WebElement element) {
	        String eStyle = element.getAttribute("Style");
	               String forecolor = "color: red;";       
	               String backcolor = "background-color: yellow;";
	            String outline = "outline: 1px solid rgb(126,255,136);";
	               String color;
	              
	                     color = eStyle + forecolor + backcolor + outline;

	               JavascriptExecutor js = (JavascriptExecutor) driver;
	               js.executeScript(
	                            "arguments[0].setAttribute('style', arguments[1]);",
	                            element, color);
	 }

		public static void highlightElementY_Backup(WebDriver driver, WebElement element) {
			for (int i = 0; i < 5; i++) {
				String forecolor = "color: red;";		
				String backcolor = "background-color: yellow;";
				String outline = "outline: 1px solid rgb(126,255,136);";
				String color;
				
					 color = forecolor + backcolor + outline;
				
				  

//				String color_org = element.getAttribute("style").toString();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						element, color);

//				js.executeScript(
//						"arguments[0].setAttribute('style', arguments[1]);",
//						element, color_org);

			}
		}	
	//highlight an element
		public static void highlight_Element(WebDriver driver, WebElement element) {
			
			for (int i = 0; i < 5; i++) {
				String changecolor = "background-color: red;";
				String color_org = element.getAttribute("style").toString();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						element, changecolor);

				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						element, color_org);

			}
		}

	//take a screenshot
		public static void getScrn(String screenFolder,WebDriver driver)
				throws IOException {
			//String screenName = screenFolder + screenID + ".png";
			String screenName = screenFolder + ".png";
			//screenID++;
			try
			{
				//driver = new Augmenter().augment(driver);
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(screenName));
			}
			catch(WebDriverException e)
			{
				System.out.println("//----------Screen Capture failed! Capture again!");
				toolbox.waitSecond(5);
				
				driver = new Augmenter().augment(driver);
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(screenName));
				
			}

		}
		public static void screen_clear(String screenshot_path)
		{
			//System.out.println("[ACTION]: Clear the screenshot folder.\n");
			File fi = new File(screenshot_path);
		    File[] file = fi.listFiles();
		    for(int i=0;i<file.length;i++)
		    {
		    	file[i].delete();
		    }
		}
	}
	//String html[] = { "a", "abbr", "acronym", "address", "applet", "area",
	//"b", "base", "basefont", "bdo", "big", "blockquote", "body",
	//"br", "button", "caption", "center", "cite", "code", "col",
	//"colgroup", "dd", "del", "dfn", "dir", "div", "dl", "dt", "em",
	//"fieldset", "font", "form", "frame", "frameset", "head",
	//"header", "h1", "h2", "h3", "h4", "h5", "h6", "hr", "html",
	//"i", "iframe", "img", "input", "ins", "kbd", "label", "legend",
	//"li", "link", "map", "menu", "meta", "noframes", "noscript",
	//"object", "ol", "optgroup", "option", "p", "param", "pre", "q",
	//"s", "samp", "script", "select", "small", "span", "strike",
	//"strong", "style", "sub", "sup", "table", "tbody", "td",
	//"textarea", "tfoot", "th", "thead", "title", "tr", "tt", "u",
	//"ul", "var" };
