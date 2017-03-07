package core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import misc.SendMail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import db.RunDB;

public class browserLaunch extends DesiredCapabilities { //stable, pass

		
		
		public static DesiredCapabilities ffRemote (String locale) {
			
			 FirefoxProfile profile = new FirefoxProfile(); 
		     profile.setPreference("intl.accept_languages",locale); 
		     profile.setPreference("dom.max_script_run_time", 0);
		     profile.setPreference("dom.max_chrome_script_run_time", 0);	 
		     DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		     capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		    
		     return  capabilities;
		     
		}
		public static DesiredCapabilities ChromeRemote (String locale) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--lang="+locale); 
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			//c:\Curiosity\jre7_run\bin\java -jar c:\Curiosity\selenium-server-standalone-2.40.0.jar -role node -hub http://miaxi01enu2003:4444/grid/register
			// -Dwebdriver.chrome.driver="c:\chromedriver.exe" -maxSession 5 
			// -browser "browserName=chrome,maxInstances=5,platfrom=ANY,seleniumProtocal=WebDriver"
			return  capabilities;		     
		}
		
		public static WebDriver ffLoc(String locale) throws org.openqa.selenium.firefox.NotConnectedException{ 
			WebDriver driver = null;
	        FirefoxProfile profile = new FirefoxProfile(); 
	        profile.setPreference("intl.accept_languages",locale); 
	        /*
			 * 
			 * org.openqa.selenium.firefox.NotConnectedException: Unable to connect to host 127.0.0.1 on port 7057 after 45000 ms. Firefox console output:
			 * 
			 */
			driver = new FirefoxDriver(profile);
	        return driver; 
		}
		
		public static WebDriver chromeLoc(String locale){ 

			System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe");
			//System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe --disable-web-security");
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--disable-web-security --lang="+locale); //disable-popup-blocking
			options.addArguments("--lang="+locale);
			WebDriver driver = new ChromeDriver(options);  
			
	        return driver; 
		}
		
		public static WebDriver launch(String ProductURL,String ProductName,String ProjectID,String DBPosition,String NodePath,boolean bUseHubNode, String PreferedBrowser) { //throws MalformedURLException,org.openqa.selenium.firefox.NotConnectedException {
			WebDriver driver = null;
			boolean bHubRunning = true;
			String locale = RunDB.getLanguage(DBPosition,ProjectID);	//loginStart.readXML("Language");
			if(bUseHubNode){
				String Hub=loginStart.readXML("Hub");				
				Hub=customlibrary.Parameter.setHub(ProductName,ProductURL);
				String HubConsole=Hub.replaceFirst("wd/hub", "grid/console");
				DesiredCapabilities capability = null;
				//DesiredCapabilities capability = browserLaunch.ffRemote(locale);
				//DesiredCapabilities capability = browserLaunch.ChromeRemote(locale);	
				if(PreferedBrowser.equals("FireFox")){
					capability = browserLaunch.ffRemote(locale);
					//System.out.println("[Info:] Prefered browser "+PreferedBrowser);
				}
				if(PreferedBrowser.equals("Chrome")){
					capability = browserLaunch.ChromeRemote(locale);	
					//System.out.println("[Info:] Prefered browser "+PreferedBrowser);
				}
				try{					
					driver = new RemoteWebDriver (new URL(Hub),capability);
					//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECOND S); //TEST
					driver.manage().window().maximize();	
					bHubRunning = true;
				}catch(Exception ex){
					bHubRunning = false;
					//System.out.println(ex.getMessage());
					System.out.println("[Alert:] Cannot start "+PreferedBrowser+"/WebDriver session with remote hub/nodes.");
					System.out.println("[Alert:] Please check Hub with "+HubConsole);
					RunDB.addLog(ProjectID,DBPosition,NodePath,4,"[Alert:] Cannot start FireFox/WebDriver session with remote hub/nodes.");
					RunDB.addLog(ProjectID,DBPosition,NodePath,4,"[Alert:] Please check Hub with "+HubConsole);
				}
				if(bHubRunning){
					
				}else{
					String EmailSubject="[Alert:] Cannot start "+PreferedBrowser+"/WebDriver session with remote hub/nodes.";
					String EmailBody="[Alert:] Please check Hub with "+HubConsole+".";
					String AttachedFile="";
					String EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
					//EmailContact="miaxi01@ca.com";
					if(NodePath.equals("000")){
						//SendMail.send(DBPosition,ProjectID,ProductName,NodePath,EmailSubject,EmailBody,EmailContact,AttachedFile);
						//SendMail.send(DBPosition,ProjectID,ProductName,NodePath,EmailSubject,EmailBody,"miaxi01@ca.com",AttachedFile);
					}
				}
			}else{
				boolean bChrome=false; String ActiveBrowser="FireFox";
				String filename="c://chromedriver.exe";
				File fileElement= new File(filename); 		
				if(fileElement.exists()){
					bChrome=true;
					ActiveBrowser="Chrome";
				}else{
					bChrome=false;
					ActiveBrowser="FireFox";
				}
				if(PreferedBrowser.equals("FireFox")){
					bChrome=false;
					ActiveBrowser="FireFox";
				}
				if(PreferedBrowser.equals("Chrome")){
					bChrome=true;
					ActiveBrowser="Chrome";
				}
				try{
					if(bChrome){
						driver = browserLaunch.chromeLoc(locale);
					}else{
						driver = browserLaunch.ffLoc(locale);
					}
					//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //TEST
					driver.manage().window().maximize();
					
				}catch(Exception ex){					
					System.out.println("[Alert:] Cannot start local "+ActiveBrowser+"/WebDriver session. Please check Firefox version(only 20.0 is valid) or lib.");
					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Cannot start local "+ActiveBrowser+"/WebDriver session. Please check Firefox version(only 20.0 is valid) or lib.");
				}
			}
			return driver;
			
		}
		public static WebDriver launchLocal(String ProjectID,String DBPosition,String NodePath) throws MalformedURLException {
			WebDriver driver = null;
			String locale = RunDB.getLanguage(DBPosition,ProjectID);	//loginStart.readXML("Language");
			try{
				driver = browserLaunch.ffLoc(locale);
				driver.manage().window().maximize();
			}catch(Exception ex){		
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Cannot start WebDriver session");
//				System.out.println("[Alert:] Cannot start WebDriver session");
			}
		
			
			return driver;
			
		}
		public static WebDriver launchHub(String ProjectID,String DBPosition,String NodePath) throws MalformedURLException {
			WebDriver driver = null;
			String Hub=loginStart.readXML("Hub");
			String HubConsole=Hub.replaceFirst("wd/hub", "grid/console");
			String locale = RunDB.getLanguage(DBPosition,ProjectID);	//loginStart.readXML("Language");
			DesiredCapabilities capability = browserLaunch.ffRemote(locale);
			try{
				driver = new RemoteWebDriver (new URL(Hub),capability);
				driver.manage().window().maximize();
			}catch(Exception ex){		
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Cannot start WebDriver session");
				RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Solution:] 1# Do nothing if you set many sessions, it will recover automatically.");
				RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Solution:] 2# Please check Hub with "+HubConsole);
//				System.out.println("[Alert:] Cannot start WebDriver session");
//				System.out.println("[Solution:] 1# Do nothing if you set many sessions, it will recover automatically.");
//				System.out.println("[Solution:] 2# Please check Hub with "+HubConsole);
			}
			return driver;
			
		}

	}


