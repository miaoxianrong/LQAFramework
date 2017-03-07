package customlibrary;

import misc.SendMail;
import misc.toolbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import core.productInfo;
import db.RunDB;

public class LoginSetting {
	public static String setProjectIDforProductName(String DBPosition,String ProjectID){
		String ProductName=RunDB.getProductName(DBPosition,ProjectID);
		
		switch(ProjectID){
			case "00001239-B-1":	
				ProductName="APM-TeamCenter";				
			break;
			case "00001239-5":	
				ProductName="APM-WebView";				
			break;		
			case "00001239-A-1":	
				ProductName="APM-CEM";			
			break;
			case "00000707-18":
				ProductName="ITKO";
			break;
			case "00001104-5":
				ProductName="ITKO";
			break;
			case "00001104-6":
				ProductName="ITKO";
			break;
			case "00001104-1":
				ProductName="ITKO";
			break;
			case "00001108":
				ProductName="ITKO";
			break;
			case "00000759":
				ProductName="PlayBook";
			break;
			case "00000772B":
				ProductName="clouditsm";//"CSM";
			break;
			case "00000772":	
				ProductName="CSM";//"Nimsoft";
			break;
			case "12898":
				ProductName="UIM";
			break;
			case "00000852":
				ProductName="SDM";
			break;
			case "00000371":
				ProductName="CCA";
			break;
			case "00000644":	
				ProductName="MEM"; //628
			break;
			case "00000624":	
				ProductName="MDM";
			break;
			case "00000622":	
				ProductName="MAM";
			break;
			case "00000982":
				ProductName="SPP";
			break;
			case "00000949":	
				ProductName="MCC";
			break;	
			case "00000815":	
				ProductName="MCC";
			break;	
			case "0000954-8":	
				ProductName="MCC"; 
			break;	
			case "00000282":	
				ProductName="SAAS";
			break;		
			case "00000281":	
				ProductName="CloudMonitor";
			break;
			case "training001":	
				ProductName="CloudMonitor";
			break;
			case "00000954-1":	
				ProductName="MAA"; //793
			break;
			case "00001320-20":	
				ProductName="ControlMinder";
			break;					
			case "12283":	
				ProductName="Aspera";
			break;	
			case "00000165":	
				ProductName="ITPAM";
			break;	
			case "00000372":	
				ProductName="Clarity";
			break;	
			case "00000929":	
				ProductName="Clarity14.2";
			break;
			case "00000641":	
				ProductName="ServiceCatalog";
			break;	
			case "00000265-1":
				ProductName="SiteMinder"; //SiteMinder
			break;
			case "00000265":
				ProductName="CASM"; 
			break;
			case "00000265-7":
				ProductName="CASM"; 
			break;
			case "00000265-5":
				ProductName="CASM"; 
			break;
			case "00000265-6":
				ProductName="CASM"; 
			break;
			case "00000265-9":
				ProductName="CASM"; 
			break;
		}
		if(ProductName.equals("unknown")){
			System.out.println("[Alert:] The project "+ProjectID+" is not supported, the tool will quit.");
			System.out.println("[Solution:] Please contact with the author to add support plug in code.");
		}else{
			//System.out.println("[Info:] For Project "+ProjectID+", its ProductName="+ProductName);
		}
		//System.out.println("[Info:] For Project "+ProjectID+", its ProductName="+ProductName);
		return ProductName;
	}
	public static boolean setLoginInfoAndStart(String PluginAction,WebDriver driver,String ProjectID,String DBPosition,String NodePath,String ProductURL, int iPageLoadTime)
	{
		boolean isLoginSucceed = false;
		//String ProjectID = readXML("ProjectID");
		String ProductName=setProjectIDforProductName(DBPosition,ProjectID);
		//System.out.println("[ProductName:] "+ProductName);
		String url = ProductURL;//readXML("URL");
		String user = RunDB.getUserName(DBPosition,ProjectID);//readXML("UserName");
		if(user==null){
			user="none";
			System.out.println("[Alert:] UserName in DB or parameter.xml is not set correctly.");
		}
		String pass = RunDB.getPassWord(DBPosition,ProjectID);//readXML("PassWord");
		if(pass==null){
			pass="none";
			System.out.println("[Alert:] PassWord in DB or parameter.xml is not set correctly.");
		}
		String TenantID = RunDB.getTenantID(DBPosition,ProjectID);//readXML("TenantID");		
		if(TenantID==null){
			TenantID="none";
			System.out.println("[Alert:] TenantID in DB or parameter.xml is not set correctly.");
		} 
		if(NodePath.equals("000")){
			System.out.println("[Info:] Username: "+user);
			System.out.println("[Info:] Password: "+pass);
			System.out.println("[Info:] TenantID: "+TenantID);
		}
		double load_begin = System.currentTimeMillis()/1000.000;	
		boolean bNotGetURL = false;
		boolean bGetPName = false;
		boolean bFindElement = true;
		boolean bURLException = false;
		String EmailSubject="";
		String EmailBody="";
		try{
			RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Robot is accessing "+ProductName+" with URL " + url);
			driver.get(url); 
			bNotGetURL = false;
		}catch(Exception ex){
			bNotGetURL = true;			
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Cannot access "+ProductName+" with web url: "+url);
		}  
		double load_end = System.currentTimeMillis()/1000.000;
		double load_spent = load_end - load_begin;	
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] URL achieving time : " + String.valueOf(load_spent).substring(0,3) + " seconds");
		String ErrorDescription="";
		if(bNotGetURL){
			EmailSubject="[Alert:] Cannot access "+ProductName+" with url: "+url;
			EmailBody=EmailSubject;
			if(NodePath.equals("000")){
				SendMail.sendExceptionAlertEmail(ProjectID,DBPosition,NodePath,ProductName, EmailSubject, EmailBody,"");
				RunDB.setProjectStatusToPauseAndExit(DBPosition, ProjectID,NodePath);
			}else{
				//RunDB.setProjectStatusToPause(DBPosition, ProjectID,NodePath);
				//Try 3 times when running
			}
		}else{
			if(NodePath.equals("000")){
				String pageSource=driver.getPageSource();
				if(pageSource.contains("Server not found")||pageSource.contains("<body><pre></pre></body>")||pageSource.contains("<body></body>")||pageSource.contains("The connection has timed out")){
					bURLException=true;
					ErrorDescription="[Alert:] URL "+url+" is not correct."+"\n";
					ErrorDescription+="[Info:] Project status is changed to Suspend from Running."+"\n";
					ErrorDescription+="[Info:] Auto crawl will stop and exit. A notification email will be sent out."+"\n";
				}else{
					bURLException=false;
				}
				if(bURLException){
					try{
						toolbox.captureScrn("./output/"+ProjectID+"/screen/", "login.png", driver);
					}catch(Exception e1){
						
					}
					System.out.println(ErrorDescription);
					EmailSubject="[Alert:] URL "+url+" is not correct.";
					EmailBody=ErrorDescription;
					SendMail.sendExceptionAlertEmail(ProjectID,DBPosition,NodePath,ProductName,EmailSubject,ErrorDescription,"./output/"+ProjectID+"/screen/login.png");
					RunDB.addLog(ProjectID,DBPosition,NodePath,4, ErrorDescription);
					RunDB.setProjectStatusToPauseAndExit(DBPosition, ProjectID,"000");	
				}
			}
			RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Wait page loading("+iPageLoadTime+" seconds). Then, input username and password to login ......");
			switch(ProductName){
				case "ITKO": 
					toolbox.waitSecond(iPageLoadTime);
					bGetPName=true;
					try{		
						WebElement username_UIM = driver.findElement(By.xpath("//input[@name='username']"));
						WebElement password_UIM = driver.findElement(By.xpath("//input[@name='password']"));
						username_UIM.sendKeys(user);
						password_UIM.sendKeys(pass);
						driver.findElement(By.xpath("//button[@type='submit']")).click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 	
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime); 
				        if(isLoginSucceed){				        	
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "APM-TeamCenter": 
					bGetPName=true;
					try{						
						toolbox.waitSecond(iPageLoadTime); 						
						driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
						WebElement username_APM = driver.findElement(By.xpath("//input[@name='j_username']"));							
				    	//WebElement password_APM = driver.findElement(By.cssSelector("input[name='j_password']"));
						username_APM.clear(); 
				        username_APM.sendKeys(user); 		
				        
				        WebElement password_APM = driver.findElement(By.cssSelector("input[name='j_password']"));
				        password_APM.clear(); 
				        password_APM.sendKeys(pass); 	
				        
				        WebElement login_APM = driver.findElement(By.xpath("//input[@type='submit']"));
				        //(By.cssSelector("input[name='loginForm:loginId_loginButton']"));
				       
				        login_APM.click();	
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}    
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "APM-WebView":
					bGetPName=true;
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					driver.switchTo().frame("LoginFrame");
					
					String username_xpath = "//input[@name='j_username']";
					String login_button = "//input[@id='webview-loginPage-login-button']";
					
					try{
						WebElement username = driver.findElement(By.xpath(username_xpath));
						username.clear();
						username.sendKeys(user);
						
						WebElement password_APM = driver.findElement(By.cssSelector("input[name='j_password']"));
				        password_APM.clear(); 
				        password_APM.sendKeys(pass); 	
//						System.out.println(username_xpath);
	//					misc.toolbox.captureScrn("./screen", "/test.png", driver);
					}
					catch(Exception e){
						e.printStackTrace();
						isLoginSucceed=false;
						bFindElement =false;
					}
					
					try{
						//System.out.println(login_button);
						WebElement login_btn = driver.findElement(By.xpath(login_button));
						login_btn.click();
					}
					catch(Exception e){
						e.printStackTrace();
						isLoginSucceed=false;
						bFindElement =false;
					}
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(bFindElement){
						isLoginSucceed = true;
						if(isLoginSucceed){				        	
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "APM-CEM": 
					bGetPName=true;
					try{						
						toolbox.waitSecond(iPageLoadTime); 								
						//driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
						WebElement username_APM = driver.findElement(By.xpath("//input[@name='j_username']"));		
						username_APM.clear(); 
				        username_APM.sendKeys(user);
				        
				        WebElement password_APM = driver.findElement(By.cssSelector("input[name='j_password']"));
				        password_APM.clear(); 
				        password_APM.sendKeys(pass); 	
				        
				        WebElement login_APM = driver.findElement(By.xpath("//input[@type='submit']"));
				        //(By.cssSelector("input[name='loginForm:loginId_loginButton']"));
				       
				        login_APM.click();	
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}   
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "SDM": 
					bGetPName=true;
					try{		
						WebElement username_UIM = driver.findElement(By.xpath("//input[@name='USERNAME']"));
						WebElement password_UIM = driver.findElement(By.xpath("//input[@name='PIN']"));
						username_UIM.sendKeys(user);
						password_UIM.sendKeys(pass);
						//driver.findElement(By.xpath("//input[@type='submit']")).click();
						driver.findElement(By.xpath("//a[@onclick]")).click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
						
					} 
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//html", 1,iPageLoadTime); 
				        RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
//				        if(isLoginSucceed){		
//				        	System.out.println("Start to turn to toolbarframe");
//				        	driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='toolbarframe']")));
//				        	System.out.println("Turn to toolbarframe successfully");
//				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
//				        }
					}
				break;
				case "UIM": 
					bGetPName=true;
					try{		
						WebElement username_UIM = driver.findElement(By.xpath("//input[@name='_58_login']"));
						WebElement password_UIM = driver.findElement(By.xpath("//input[@name='_58_password']"));
						username_UIM.sendKeys(user);
						password_UIM.sendKeys(pass);
						driver.findElement(By.xpath("//input[@type='submit']")).click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 	
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime); 
				        
				        if(isLoginSucceed){				        	
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "PlayBook": 
					bGetPName=true;
					toolbox.waitSecond(iPageLoadTime); 
					isLoginSucceed=true;
				break;
				case "clouditsm": 
					bGetPName=true;
					try{	
						toolbox.waitSecond(iPageLoadTime);
						driver.findElement(By.xpath("//input[@type='text']")).sendKeys("admin@poc.com");
						driver.findElement(By.xpath("//table[3]//input")).clear();                      		
						driver.findElement(By.xpath("//input[@type='password']")).sendKeys("admin");
						driver.findElement(By.xpath("//div[2]/child::em//button")).click();
						toolbox.waitSecond(iPageLoadTime);
						driver.findElement(By.xpath("//img[@class='x-tool-close']")).click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 	
					if(bFindElement){
						isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//body", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
					
				break;	
				case "CSM": 
					bGetPName=true;					
					try
					{
						toolbox.waitSecond(iPageLoadTime); 
						String continue_xpath="//div[@id='continue_btn']/span[1]";
						continue_xpath="//div[@fwid='fw_locale_preference_continue_btn']/span[1]";
						WebElement continue_CSM = driver.findElement(By.xpath(continue_xpath));
						continue_CSM.click();
					}
					catch(Exception ex1)
					{
						bFindElement =false;
						System.out.println("[Alert:] Cannot find and click continue");
					}
					try
					{
						toolbox.waitSecond(iPageLoadTime); 
						//System.out.println("[Info:] Try to find and click Accept");
						String accept_xpath="//div[@fwid='fw_acceptBtn']/span[1]";
						WebElement accept_CSM = driver.findElement(By.xpath(accept_xpath));
						accept_CSM.click();
						
					}
					catch(Exception ex2)
					{
						bFindElement =false;
						System.out.println("[Alert:] Cannot find and click Accept");
					}
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username = driver.findElement(By.xpath("//input[@fwid='fw_SSU_userName_input']"));
						WebElement password = driver.findElement(By.xpath("//input[@fwid='fw_SSU_password_input']"));
						WebElement tenantID = driver.findElement(By.xpath("//input[@fwid='fw_SSU_input']"));
						username.clear(); 
				        username.sendKeys(user); 
				        password.clear(); 
				        password.sendKeys(pass); 
				        //tenantID.clear(); 	
				        //tenantID.sendKeys(TenantID); 
				        WebElement login_sm = driver.findElement(By.xpath("//div[@fwid='fw_authentication_loginBtn']/span[1]"));
				        login_sm.click();
					}catch(Exception ex){
						System.out.println("[Alert:] login click failure");
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "CCA": 
					bGetPName=true;
					try{	
						toolbox.waitSecond(iPageLoadTime);
						WebElement username_CCA = driver.findElement(By.name("username"));
						WebElement password_CCA =driver.findElement(By.name("password"));
						username_CCA.sendKeys(user);
						password_CCA.sendKeys(pass);
						driver.findElement(By.xpath("/html/body/div/table/tbody/tr[3]/td/table/tbody/tr[2]/td/div/table/tbody/tr[6]/td[2]/table/tbody/tr/td/div/table/tbody/tr/td[2]/table/tbody/tr/td/div/div")).click();
						//driver.findElement(By.xpath("//table[@calss='button_center']/tbody/tr/td/div/div")).click();			
						//System.out.println("find .........");
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 	
					if(bFindElement){
						isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//td[2]/div/div", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;				
				case "SPP": 
					bGetPName=true;
					toolbox.waitSecond(iPageLoadTime); 
					isLoginSucceed=true;
				break;
				case "MEM": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username = driver.findElement(By.xpath("//input[@ng-model='credentials.email']"));
						WebElement password = driver.findElement(By.xpath("//input[@ng-model='credentials.password']"));
						WebElement tenantID = driver.findElement(By.xpath("//input[@ng-model='credentials.tenantId']"));
						username.clear(); 
				        username.sendKeys(user); 
				        password.clear(); 
				        password.sendKeys(pass); 
				        tenantID.clear(); 	
				        tenantID.sendKeys(TenantID); 
				        WebElement login_sm = driver.findElement(By.xpath("//button/span[@class]"));
				        login_sm.click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}     
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//cite/span", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }	
					}
				break;
				case "MCC": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username = driver.findElement(By.xpath("//input[@ng-model='credentials.email']"));
						WebElement password = driver.findElement(By.xpath("//input[@ng-model='credentials.password']"));
						WebElement tenantID = driver.findElement(By.xpath("//input[@ng-model='credentials.tenantId']"));
						
						username.clear(); 
				        username.sendKeys(user); 
				        password.clear(); 
				        password.sendKeys(pass); 
				        tenantID.clear(); 	
				        tenantID.sendKeys(TenantID); 				       
				        WebElement login_sm = driver.findElement(By.xpath("//button/span[@class]"));
				        login_sm.click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}     
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//cite/span", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "MAA": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username = driver.findElement(By.xpath("//input[@ng-model='credentials.email']"));
						WebElement password = driver.findElement(By.xpath("//input[@ng-model='credentials.password']"));
						WebElement tenantID = driver.findElement(By.xpath("//input[@ng-model='credentials.tenantId']"));
						username.clear(); 
				        username.sendKeys(user); 
				        password.clear(); 
				        password.sendKeys(pass); 
				        tenantID.clear(); 	
				        //TenantID="MDO-ORG";
				        tenantID.sendKeys(TenantID); 
				        WebElement login_sm = driver.findElement(By.xpath("//button/span[@class]"));
				        login_sm.click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly."+ex.getMessage());//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        	System.out.println("[Info:] Refresh for MAA now.");
			 				driver.navigate().refresh();
			 				if(NodePath.equals("000")){
			 					System.out.println("[Info:] PluginAction="+PluginAction);
			 					if(PluginAction.contains("UIContext")){
			 						System.out.println("[Info:] Check product server to confirm whether it is a PLOC build for UIContext plugin action.");
			 						String pageSource= driver.getPageSource();
			 						if(pageSource.contains("[[")&&pageSource.contains("]]")){
			 							System.out.println("[Info:] It is a PLOC build.");
			 						}else{			 							
			 							String newPluginAction=RunDB.resetPluginActionNotUIContext(DBPosition, ProjectID);
			 							ErrorDescription="[Alert:] It is not a PLOC build since no PLOC strings are found."+"\n";
			 							//ErrorDescription+="[Information:] Auto crawl will stop and exit."+"\n";
			 							System.out.println(ErrorDescription);
			 							SendMail.sendNotPLOCBuildAlertEmail(ProjectID,DBPosition,NodePath,ProductName,ErrorDescription,"");
			 							RunDB.addLog(ProjectID,DBPosition,NodePath,4, "[Alert:] It is not a PLOC build since no PLOC strings are found.");
			 							RunDB.addLog(ProjectID,DBPosition,NodePath,4, "[Alert:] PluginAction is changed to "+newPluginAction+" in ProjectParameter for "+ProjectID);
			 							//RunDB.setProjectPauseStatus(DBPosition, ProjectID,NodePath);								
			 							//System.exit(0);			 							
			 						}
			 					}else{
			 						System.out.println("[Info:] Login successfully.");
			 					}
			 				}
				        }
					}
				break;
				case "SAAS": 
					bGetPName=true;
					try{						
						
						WebElement username_Saas = driver.findElement(By.xpath("//input[@name='USER']"));
						WebElement password_Saas = driver.findElement(By.xpath("//input[@name='PASSWORD']"));
						username_Saas.sendKeys(user);
						password_Saas.sendKeys(pass);
						driver.findElement(By.xpath("//div[@class='submit-button login-button']/input[2]")).click();
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					} 	
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime); 
				        
				        if(isLoginSucceed){				        	
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "CloudMonitor":
					bGetPName=true;
					try{						
						
						driver.findElement(By.xpath("//a[@class='sign-in no-border']")).click();
						WebElement username_CloudMonitor = driver.findElement(By.xpath("//input[@name='vemail']"));
						WebElement password_CloudMonitor =driver.findElement(By.xpath("//input[@name='vpasswd']"));
						username_CloudMonitor.sendKeys(user);
						password_CloudMonitor.sendKeys(pass);
						driver.findElement(By.xpath("//input[@id='loginBtn']")).click();						
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}   
					if(bFindElement){
						isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;			
				
				case "APM": 
					bGetPName=true;
					try{						
						toolbox.waitSecond(iPageLoadTime); 						
						driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
						WebElement username_APM = driver.findElement(By.xpath("//input[@name='j_username']"));							
				    	//WebElement password_APM = driver.findElement(By.cssSelector("input[name='j_password']"));
						username_APM.clear(); 
				        username_APM.sendKeys(user); 				       
				        WebElement login_APM = driver.findElement(By.xpath("//input[@type='submit']"));
				        //(By.cssSelector("input[name='loginForm:loginId_loginButton']"));
				        login_APM.click();	
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}  
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"xpath","//a", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
					
				break;				
				case "Aspera": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username_sm = driver.findElement(By.cssSelector("input#username"));
						WebElement password_sm = driver.findElement(By.cssSelector("input#password"));
						username_sm.clear(); 
				        username_sm.sendKeys(user); 
				        password_sm.clear(); 
				        password_sm.sendKeys(pass); 
				        WebElement login_sm = driver.findElement(By.cssSelector("input#login"));
				        login_sm.click();	
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}   
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a#logout_link", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				
				case "Nimsoft": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username_Nimsoft = driver.findElement(By.id("textfield-1013-inputEl")); 
				        WebElement password_Nimsoft = driver.findElement(By.id("textfield-1014-inputEl")); 
				        WebElement login_Nimsoft = driver.findElement(By.id("button-1017-btnEl")); 
				        username_Nimsoft.clear(); 
				        username_Nimsoft.sendKeys(user); 
				        password_Nimsoft.clear(); 
				        password_Nimsoft.sendKeys(pass);      
				        login_Nimsoft.click(); 
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}    
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","img.x-tool-close", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        	driver.findElement(By.cssSelector("img.x-tool-close")).click();            
				        }
					}    
					
				break;
				case "ControlMinder": 
					bGetPName=true;
                    try{ 
                       toolbox.waitSecond(iPageLoadTime); 
                       WebElement username_cm = driver.findElement(By.name("username")); 
                       WebElement password_cm = driver.findElement(By.name("password")); 
                       username_cm.clear(); 
                       username_cm.sendKeys(user);                     
                       password_cm.clear();                     
                       password_cm.sendKeys(pass); 
                       WebElement login_cm = driver.findElement(By.cssSelector("input.loginButton")); 
                       login_cm.click();    
                    }catch(Exception ex){ 
                 	   System.out.println("[Alert:]"+ex.getMessage());
                         RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
                         isLoginSucceed=false; 
                         bFindElement =false;
                    } 
                    if(bFindElement){
                       isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a.r5_welcomebannerlink", 1,iPageLoadTime);   
                       if(isLoginSucceed){ 
                            RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully"); 
                       } 
                    }  
                break;
				case "SiteMinder": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username_sm = driver.findElement(By.cssSelector("input#username"));
						WebElement password_sm = driver.findElement(By.cssSelector("input#password"));
						username_sm.clear(); 
				        username_sm.sendKeys(user); 
				        password_sm.clear(); 
				        password_sm.sendKeys(pass); 
				        WebElement login_sm = driver.findElement(By.cssSelector("input#signinButton"));
				        login_sm.click();	
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}     
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a[href='?task.tag=GlobalSiteMap']", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "CASM": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); 
						WebElement username_sm = driver.findElement(By.cssSelector("input#username"));
						WebElement password_sm = driver.findElement(By.cssSelector("input#password"));
						username_sm.clear(); 
				        username_sm.sendKeys(user); 
				        password_sm.clear(); 
				        password_sm.sendKeys(pass); 
				        WebElement login_sm = driver.findElement(By.cssSelector("input#signinButton"));
				        login_sm.click();	
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}  
					if(bFindElement){
				        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a[href='?task.tag=GlobalSiteMap']", 1,iPageLoadTime);   
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;
				case "Clarity": 
					bGetPName=true;
					try{
						toolbox.waitSecond(iPageLoadTime); //set 5
						WebElement username_Clarity = driver.findElement(By.id("ppm_login_username")); 
				        WebElement password_Clarity = driver.findElement(By.id("ppm_login_password")); 
				        WebElement login_Clarity = driver.findElement(By.id("ppm_login_button")); 
				        username_Clarity.clear(); 
				        username_Clarity.sendKeys(user); 
				        password_Clarity.clear(); 
				        password_Clarity.sendKeys(pass);      
				        login_Clarity.click();  
					}catch(Exception ex){
						System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
					}   
					if(bFindElement){
				        isLoginSucceed=toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a#ppm_header_logout", 1,iPageLoadTime);  
				        if(isLoginSucceed){
				        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
				        }
					}
				break;				
				case "ServiceCatalog": 
					bGetPName=true;
                    try{
                        toolbox.waitSecond(iPageLoadTime); //set 5                                               
	                    WebElement username_sc = driver.findElement(By.cssSelector("input#username"));  
	                    WebElement password_sc = driver.findElement(By.cssSelector("input#pass"));      
	                    username_sc.clear();        
	                    username_sc.sendKeys(user);       
	                    password_sc.clear();        
	                    password_sc.sendKeys(pass);        
	                    WebElement login_sc = driver.findElement(By.cssSelector("input#submit"));       
	                    login_sc.click(); 
                    }catch(Exception ex){
                    	System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
                    	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;
                    }     
                    if(bFindElement){
	                    isLoginSucceed=toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a.logout", 1,iPageLoadTime);  
	                     if(isLoginSucceed){
	                    	 RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
	                     }
                    }
                 break;
				case "ITPAM": 
					bGetPName=true;
                    try{
                        toolbox.waitSecond(iPageLoadTime); //set 5     
                        WebElement username_pam = driver.findElement(By.cssSelector("input[name='j_username']"));
                        WebElement password_pam = driver.findElement(By.cssSelector("input[name='j_password']"));
                        username_pam.clear(); 
                        username_pam.sendKeys(user); 
                        password_pam.clear(); 
                        password_pam.sendKeys(pass); 
                        WebElement login_pam = driver.findElement(By.cssSelector("button#loginbtn"));
                        login_pam.click();
                    }catch(Exception ex){
                    	System.out.println("[Alert:] login failure since login xpath is not found or xpath is not given correctly.");//+ex.getMessage());
                    	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:]: PageLoadTime="+iPageLoadTime+", Login failure, "+ProductName+" is very slow! It will not check waitforelement.");
						isLoginSucceed=false;
						bFindElement =false;						
                    }   
                    if(bFindElement){
                        isLoginSucceed = toolbox.waitforelement(PluginAction,ProductName,driver,ProjectID,DBPosition,NodePath,"css","a#PAM_HOME_PAGE_signoutLink", 1,iPageLoadTime);
	                    if(isLoginSucceed){
	                    	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
	                     }
                    }
                    break;
			}
			if(bFindElement){
				
			}else{
				ErrorDescription="[Alert:] No valid DOM elements are found on login page. Login failed."+"\n";
				ErrorDescription+="[Possible reason 1#:] Username, password, TenantID elements cannot be found. Please change code to modify xpath correctly."+"\n";
				ErrorDescription+="[Possible reason 2#:] Product server is very slow, please restart product server or increase page loading time with the parameter iPageLoadTime."+"\n";
				ErrorDescription+="[Possible reason 3#:] Product URL is not correct."+"\n";
				if(NodePath.equals("000")){
					ErrorDescription+="[Info:] Project status is changed to Suspend from Running."+"\n";
					ErrorDescription+="[Info:] Auto crawl will stop and exit. A notification email will be sent out."+"\n";	
					EmailSubject="[Alert:] No valid DOM elements are found on login page. Login failed.";
					EmailBody=ErrorDescription;
					SendMail.sendExceptionAlertEmail(ProjectID,DBPosition,NodePath,ProductName,EmailSubject,EmailBody,"");
					System.out.println(ErrorDescription);	
					RunDB.addLog(ProjectID,DBPosition,NodePath,4, ErrorDescription);					
					RunDB.setProjectStatusToPauseAndExit(DBPosition, ProjectID,NodePath);	
				}else{
					System.out.println(ErrorDescription);	
					RunDB.addLog(ProjectID,DBPosition,NodePath,4, ErrorDescription);
				}				
			}
			if(bGetPName){
				
			}else{
				System.out.println("[Alert:]: Could not find product name in start method of loginStart.java");
			}
		}
		return isLoginSucceed;
	}
}
