package core;

import java.text.DecimalFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.sql.ResultSet;

import misc.SendMail;
import misc.toolbox;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import algorithm.FingerPrint;
import ValidatorIFormat.iFormat;
import ValidatorTruncation.I18nValidator;
import ValidatorTruncation.truncationCheck;


import db.ManageDB;
import db.RunDB;
import validators.*;
import mapping.NewMapping;
import misc.utility;
import hardcode.*;


public class concurrentPath implements Runnable{

	String NodePath;
	String ProjectID;
	String DBPosition;
	String PreferedBrowser;
	String DebugServer;
	String ProductName;
	String PluginAction;
	boolean bUseHubNode;
	boolean bTestPath;
	String RunMode;
	int iMaxDepth;
	int iPageLoadTime;
	int iFindElementTime;
	FingerPrint fp = new FingerPrint();
	//hardcode h = new CopyOfhardcode();
	utility util = new utility();
	vectorCompare pv = new vectorCompare();
	String screenshot_folder = "./screen/";
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean bLeaf=false;String leaf="0";
		boolean bBranch=false;String branch="0";
		boolean bMayBranch=false;
		boolean bRecord=false; if(RunMode.contains("Record")){bRecord=true;}
		boolean bPlay=false; if(RunMode.contains("Play")){bPlay=true;}	
		boolean bDebug=false; if(RunMode.contains("Debug")){bDebug=true;}
		boolean bDebugResumeXPATH=false; if(RunMode.equals("DebugResumeXPATH")){bDebugResumeXPATH=true;}
		String sql="";
		String ParentFilter="";
		String PageFilter="";
		String PageContainer="";
		String TagOrURL="";
		String Feature="";
		String tagName="";
		String nodeType="";
		String nodeName="";
		int attributeFP=0;
		//String nodeNgclick="";
		String RootURLNodePath="";
		String middleNodePath="";
		String InputProperty="";
		String InputName="";
		String InputType="";
		String InputValue="";
		String InputXPATH="";
		String LocalString="";
		WebElement InputNameXPATH=null;
		String target="";
		boolean bCheckPageLoadSpecifiedElement=true; 
		boolean bCheckPageLoadFinish = true; 
		String pageSource="";
		String pageSourceAfterServeralSeconds="";
		int LevelID=0;
		WebElement e = null;
		String url="";		
		WebDriver driver=null;
		boolean bGetData = false;
		boolean bGetURL = false;
		boolean bAction = false;
		boolean bFind = false;
 		boolean bClick = false;
 		int noMoreUniqueFilter=0;
 		String DOMType="";
 		String sExtractNodesResult = "";
 		boolean isLogin = false; 
 		boolean bDriverActive=false; 		
 		String ExecuteParentFilter="";
 		int iFrameLen=0;
 		String allFrame="";
 		String oneFrame="";
 		StringTokenizer stFrame = null;
 		String EmailContact=loginStart.readXML("EmailContact");if(EmailContact==null){EmailContact="miaxi01@ca.com";}
		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] /***** Start for queue node "+NodePath+" ******/");
		System.out.println("[Info:] /***** Start for queue node "+NodePath+" ******/");
		double timer_begin;
		double timer_end;
		double time_spent;
		DecimalFormat df = new DecimalFormat("0.0");		
		int seq=0; String getSeq="";//String touchtime="";
		String ProductURL=RunDB.getProductURL(DBPosition,ProjectID);//loginStart.readXML("URL");
		
 		String nodeURL=""; String snapshot=""; int iSnapshot=0; boolean bDebugSnapshot = false;
 		
 		ManageDB m0 = new ManageDB();
 		m0.getConnection(DBPosition);
 		try{	 		
 			if(PluginAction.contains("UTF8Validation")){
				sql="select * from LocalStringList";
				ResultSet rs_loc=m0.getSQL(sql);
				if(rs_loc.next()){
					LocalString=rs_loc.getString("LocalString");
				}
				rs_loc.close();	
 			}
	 		sql="select * from NodeList where special=1 and clickfail=0 and ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
	 		ResultSet rs0=m0.getSQL(sql);
	 		if(rs0.next()){
	 			nodeURL=rs0.getString("MenuHref");
	 			if(rs0.getString("branch").equals("1")){
	 				bBranch=true;
	 			}
	 			if(rs0.getString("leaf").equals("1")){
	 				bLeaf=true;
	 			}
	 			PageFilter=rs0.getString("PageFilter");
	 			PageFilter=PageFilter.replace('&','\'');
	 			PageContainer=rs0.getString("PageContainer");
	 			PageContainer=PageContainer.replace('&','\'');
	 			tagName=rs0.getString("tagName");
	 			nodeType=rs0.getString("nodeType");
	 			nodeName=rs0.getString("nodeName");
	 			attributeFP=Integer.parseInt(rs0.getString("attributeFP"));
	 			Feature=rs0.getString("Feature");
	 			TagOrURL=rs0.getString("TagOrURL");
	 			DOMType=rs0.getString("DOMType");
	 			LevelID=Integer.parseInt(rs0.getString("LevelID"));
	 			getSeq=rs0.getString("seq");
	 			RootURLNodePath=rs0.getString("RootURLNodePath");
	 			noMoreUniqueFilter=Integer.parseInt(rs0.getString("noMoreUniqueFilter"));
	 			snapshot=rs0.getString("snapshot");
	 			iSnapshot=Integer.parseInt(snapshot);
	 			if(bDebugResumeXPATH){
	 				if(iSnapshot==1){	 					
	 					bDebugSnapshot = true;
	 				}
	 			}
	 		}
	 		rs0.close();
	 		//rs0=null;
 		}catch(Exception em0){ 			
 			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Fail to get data from DB. concurrentPath.java");
 		}
 		m0.close();
		if(bDebugSnapshot){
			boolean bLaunched = false;
			try{ 
				driver=browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
				bLaunched = true;
			}
	    	catch(Exception ex){
	    		bLaunched = false;
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Cannot lauch ! Please check Hub/node configuration.");
	    	}	
			if(bLaunched){
				RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Cool! Get a snapshot page. Without login, quickly do XPATH analysis with " + nodeURL);
				System.out.println("[Info:] Cool! Get a snapshot page. Without login, quickly do XPATH analysis with " + nodeURL);
				bDriverActive=true;
				try{
					pv.getUIStringFromSnapshot(driver, ProjectID, DBPosition, NodePath, iFindElementTime);
				}catch(Exception ex2){}	
				extract.findChild(driver,iFindElementTime,Feature, NodePath,RootURLNodePath, Integer.valueOf(getSeq), ProjectID,DBPosition,DebugServer,ProductName,PageFilter, LevelID, RunMode,"NodeTag",DOMType, tagName, nodeType);
				if(bDriverActive){
	 				try{  
	 			    	driver.quit(); 	 			    	
	 			    }catch(Exception exa){}
	 			}
			}
		}else{
	 		try{ 
				driver=browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
			}
	    	catch(Exception ex){
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Cannot lauch ! Please check Hub/node configuration.");
	    	}	
	        
	 		isLogin = false; bDriverActive=false;
	 		if(driver!=null){
	 			bDriverActive=true;
	 			isLogin = customlibrary.LoginSetting.setLoginInfoAndStart(PluginAction,driver,ProjectID,DBPosition,NodePath,ProductURL,iPageLoadTime);
	 		}
	 		if(!isLogin)
			{
	 			try{	 				
	 				driver.quit();
	 			}catch(Exception ex){}
	 			try{ 
	 				RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Driver quits, launch browser 2 times. PageLoadTime plus 5");
	 				driver = browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
	 			}
	 	    	catch(Exception ex){
	 	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Cannot login, launch 2 times ! ");
	 	    	}
	 			isLogin = customlibrary.LoginSetting.setLoginInfoAndStart(PluginAction,driver,ProjectID,DBPosition,NodePath,ProductURL,iPageLoadTime+5);
			}	
	 		if(!isLogin)
			{
	 			try{
	 				driver.quit();
	 			}catch(Exception ex){}
	 			try{ 
	 				RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Driver quits, launch browser 3 times. PageLoadTime plus 10");
	 				driver = browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
	 			}
	 	    	catch(Exception ex){
	 	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Cannot login, launch 3 times ! ");
	 	    	}
	 			isLogin = customlibrary.LoginSetting.setLoginInfoAndStart(PluginAction,driver,ProjectID,DBPosition,NodePath,ProductURL,iPageLoadTime+10);
			}	
	 		if(isLogin){		
	 			System.out.println("[Info:] Login successfully for node path "+NodePath+".");
	 			
	 			RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Start to go through node path "+NodePath+".");
	 			bFind = false;
		 		bClick = false;
		 		if(TagOrURL.equals("URL")){		
		 			try{	
		 				System.out.println("[Info:] Go through node path "+NodePath+" with URL: "+nodeURL);
		 				double load_begin = System.currentTimeMillis()/1000.000;
		 				if(ProductName.equals("Aspera")){
		 					String CurrentURL=driver.getCurrentUrl();
		 					int i_rpid  = CurrentURL.indexOf("rpid=");
		 					String rpid=CurrentURL.substring(i_rpid+5);
		 					i_rpid=nodeURL.indexOf("rpid=");
		 					nodeURL=nodeURL.substring(0,i_rpid+5)+rpid;
		 				}
		 				driver.get(nodeURL);		 				
						bClick = true;
						double load_end = System.currentTimeMillis()/1000.000;
						double load_spent = load_end - load_begin;	
						RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] URL achieving time : " + String.valueOf(load_spent).substring(0,3) + " seconds");
					
						if(ProductName.equals("ServiceCatalog")){
							if(DOMType.equals("frame")){
								System.out.println("[Info:] Switch to Frame.");
								RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Switch to Frame.");
								driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
							}
							if(DOMType.equals("iframe")){
								System.out.println("[Info:] Switch to iFrame.");
								RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Switch to iFrame.");
								driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
							}
						}else{
							if(DOMType.equals("frame")){
								System.out.println("[Info:] Switch to Frame.");
								RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Switch to Frame.");
								driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
							}
							if(DOMType.equals("iframe")){
								System.out.println("[Info:] Switch to iFrame.");
								RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Switch to iFrame.");
								driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
							}
						}
					}catch(Exception ex){
						bClick = false;
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Fail to get Node's URL");
						System.out.println("[Alert:] Fail to get Node's URL: "+url);
					}
		 		}
		 		else
		 		{
		 			
					StringTokenizer st1 =new StringTokenizer(NodePath,"-");
					int j=0; int iName=0;
					while(st1.hasMoreTokens()){
						bFind = false;
				 		bClick = false;
				 		bMayBranch=false;
				 		bLeaf=false;
				 		bGetData = false;
				 		bAction = false;
						getSeq=st1.nextToken();
						j++;
						if(j==1){
							middleNodePath=getSeq;
						}else{
							middleNodePath=middleNodePath+"-"+getSeq;
						}
						if(middleNodePath.contains(RootURLNodePath)){	
							if(bPlay){ //test it //from RunDB.java getNodePath
								sql="select * from NodeList where ProjectID='"+ProjectID+"' and NodePath='"+middleNodePath+"'";
								//For middlepath, not necessary for playonce=0 and screenName<>'' and special=1 and clickfail=0 and 
							}else{
								sql="select * from NodeList where ProjectID='"+ProjectID+"' and NodePath='"+middleNodePath+"'";
								//For middlepath, not necessary for touchonce=0 and special=1 and clickfail=0 and
							}
							ManageDB m1 = new ManageDB();
					 		m1.getConnection(DBPosition);
							try{
								
								ResultSet rs = m1.getSQL(sql);
								if(rs.next()){
									branch=rs.getString("branch"); if(branch.equals("1")){bBranch=true;}else{bBranch=false;}
									leaf=rs.getString("leaf"); 
									if(leaf.equals("1")){
										bLeaf=true;
									}else{
										bLeaf=false;
									}									
									ParentFilter=rs.getString("ParentFilter");
									ParentFilter=ParentFilter.replace('&','\'');
									iName=ParentFilter.indexOf("|//name_");
									if(iName>0){
										ExecuteParentFilter=ParentFilter.substring(0,iName);
									 }else{
										 ExecuteParentFilter=ParentFilter;
									 }
									PageFilter=rs.getString("PageFilter");
									PageFilter=PageFilter.replace('&','\'');
									PageContainer=rs.getString("PageContainer");
						 			PageContainer=PageContainer.replace('&','\'');
									Feature=rs.getString("Feature");
									TagOrURL=rs.getString("TagOrURL");
									DOMType=rs.getString("DOMType");
									LevelID=Integer.parseInt(rs.getString("LevelID")); 
									if(LevelID==iMaxDepth){
										bLeaf=true;
										System.out.print("[Info:] LevelID "+LevelID+"=MaxDepth "+iMaxDepth+", so set it as leaf for "+NodePath);
									}
									url=rs.getString("MenuHref");
									noMoreUniqueFilter=Integer.parseInt(rs.getString("noMoreUniqueFilter"));
									bGetData = true;
								}else{
									bGetData = false;
								}
								rs.close();
								//rs=null;
							 }catch(Exception ex){ //try{ ResultSet rs = m1.getSQL(sql);
								 bGetData = false;
								 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Cannot get DB data of "+middleNodePath);
								 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Check concurrentPath or DB connection for "+middleNodePath);
							 }
							 m1.close();
							 if(bGetData){
								//System.out.println("ExecuteParentFilter="+ExecuteParentFilter);
								iFrameLen=ExecuteParentFilter.indexOf("/html/");
								if(iFrameLen>0){
									driver.switchTo().defaultContent();
									allFrame=ExecuteParentFilter.substring(0,iFrameLen);
									stFrame = new StringTokenizer(allFrame,"-");
									while(stFrame.hasMoreTokens()){
										oneFrame="//"+stFrame.nextToken();
										try{
											//System.out.println("[Extract:] start for oneFrame="+oneFrame);
											//System.out.println("Page source of "+oneFrame+"="+driver.getPageSource());
											driver.switchTo().frame(driver.findElement(By.xpath(oneFrame)));
										}catch(Exception ef){
											RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] middle node path "+middleNodePath+" [Suspend, frame alert]"+ef.getMessage());
											System.out.println("[Alert:] middle node path "+middleNodePath+" [Suspend, frame alert]"+ef.getMessage());
										}
									}
									ExecuteParentFilter=ExecuteParentFilter.substring(iFrameLen);
									//System.out.println("ExecuteParentFilter="+ExecuteParentFilter);
								}
								if(TagOrURL.equals("URL")){
									try{		
										RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Click middle node path "+middleNodePath+" with URL");
										System.out.println("[Info:] URL="+url);
										double load_begin = System.currentTimeMillis()/1000.000;
										if(ProductName.equals("Aspera")){
						 					String CurrentURL=driver.getCurrentUrl();
						 					int i_rpid  = CurrentURL.indexOf("rpid=");
						 					String rpid=CurrentURL.substring(i_rpid+5);
						 					i_rpid=url.indexOf("rpid=");
						 					url=url.substring(0,i_rpid+5)+rpid;
						 				}
										driver.get(url);					    
										bClick = true;
										double load_end = System.currentTimeMillis()/1000.000;
										double load_spent = load_end - load_begin;	
										RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] URL achieving time : " + String.valueOf(load_spent).substring(0,3) + " seconds");
									
										if(ProductName.equals("ServiceCatalog")){
											if(DOMType.equals("frame")){
												System.out.println("[Info:] Switch to Frame.");
												driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
											}
											if(DOMType.equals("iframe")){
												System.out.println("[Info:] Switch to iFrame.");
												driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
											}
										}else{
											if(DOMType.equals("frame")){
												System.out.println("[Info:] Switch to Frame.");
												driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
											}
											if(DOMType.equals("iframe")){
												System.out.println("[Info:] Switch to iFrame.");
												driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
											}
										}
									}catch(Exception ex){
										bClick = false;
										RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Fail to get URL of "+middleNodePath);
										System.out.println("[Alert:] Fail to get URL "+url);
									}
								}else{ //tag
									if(ProductName.equals("ServiceCatalog")){
										if(DOMType.equals("frame")){
											System.out.println("[Info:] Switch to Frame.");
											driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
										}
										if(DOMType.equals("iframe")){
											System.out.println("[Info:] Switch to iFrame.");
											driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
										}
									}else{
										if(DOMType.equals("frame")){
											System.out.println("[Info:] Switch to Frame.");
											driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
										}
										if(DOMType.equals("iframe")){
											System.out.println("[Info:] Switch to iFrame.");
											driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
										}
									}
									target="("+ExecuteParentFilter+")["+getSeq+"]";
									try{
										toolbox.waitSecond(iFindElementTime);
										//System.out.println("[Info:] "+middleNodePath+" clicking xpath="+target);
										e = driver.findElement(By.xpath(target));
										bFind = true;
									}catch(Exception ex){
										bFind = false;
										RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] Fail to run findElement of node "+middleNodePath);
										System.out.println("[Alert:] Fail to run findElement for node "+middleNodePath);
										RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] 1# Do nothing if you set many sessions, it will recover automatically.");
										RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] 2# FindElementTime is not enough for clicking, please increase <FindElementTime></FindElementTime> in parameter.xml");
										RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] 3# Node element and/or vector changed, please set <NodePath>"+middleNodePath+"</NodePath> of parameter.xml to analysis");
										RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] Force to quit queue "+NodePath+" at "+middleNodePath+", Robot will try again automatically.");
										break;
									}
									if(bFind){ // can be removed since break when bFind=false;
										try{
											//RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Start to click middle node "+middleNodePath);
											if(DOMType.equals("mouseright")){
												bAction=true;
												Actions actions=new Actions(driver);
												actions.contextClick(e).perform();
												bClick = true;
												//double click
												//Actions action = new Actions(driver); action.doubleClick(myElemment); action.perform();`
											}
											if(!bAction){
												if(PageFilter.contains("master2login")){
													WebElement password = driver.findElement(By.xpath("//input[@ng-model='credentials.password']"));
													password.clear(); 
											        password.sendKeys("EEmaster1234!!");	
												}
												e.click();			
												bClick = true;
											}
											if(DOMType.equals("popup")||DOMType.equals("newtab")){										
												RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Switch to popup for node "+middleNodePath);
												if(ProductName.equals("ITPAM")){
													toolbox.waitSecond(20);
												}
												String[] handles = driver.getWindowHandles().toArray(new String[0]);
												int iHandlesLength = handles.length;
												if(iHandlesLength==1){
													toolbox.waitSecond(10);
													handles = driver.getWindowHandles().toArray(new String[0]);
													iHandlesLength = handles.length;
												}
												if(iHandlesLength==1){
													toolbox.waitSecond(10);
													handles = driver.getWindowHandles().toArray(new String[0]);
													iHandlesLength = handles.length;
												}
												if(iHandlesLength==2){
													try{
														driver.switchTo().window(handles[1]);
														bClick = true;
	//													String oldTab = driver.getWindowHandle();
	//													ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	//													System.out.println("newTab size="+newTab.size()+" remove old tab");
	//												    newTab.remove(oldTab);
													    // change focus to new tab
	//												    driver.switchTo().window(newTab.get(0));
	//												    driver.switchTo().window(newTab.get(0));
													    
	//												    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	//												    System.out.println("tabs2="+tabs2);
	//												    driver.switchTo().window(tabs2.get(0));
	//												    driver.close();
	//												    driver.switchTo().window(tabs2.get(0));
														
													}catch(Exception es){
														bClick = false;
	//													System.out.println("err="+es.getMessage());
														RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Wait too long time(>40 seconds) for new window, or popupFilter is not set correctly. Fail to switch to popup of node "+middleNodePath);
													}
												}else{
													bClick = false;
												}
											}else{
												bClick = true;
											}
										}catch(Exception ex){
											bClick = false;
											System.out.println("[Alert:] Fail to click queue "+NodePath);
											RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] Fail to click queue "+NodePath);
											RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] 1# Do nothing if you set many sessions, it will recover automatically.");
											RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] 2# FindElementTime is not enough for clicking, please increase <FindElementTime></FindElementTime> in parameter.xml");
											RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] 3# Node element and/or vector changed, please set <NodePath>"+middleNodePath+"</NodePath> of parameter.xml to analysis");
											RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Info:] Force to quit queue "+NodePath+" at "+middleNodePath+", Robot will try again automatically.");
											break;
										}
									}									
								} //else is tag								
							 }//if get data from DB							 
						}//if(middleNodePath.contains(RootURLNodePath)){		
						if(bClick){
							toolbox.waitSecond(iFindElementTime);
							//System.out.println("[Input value:] start to input value after clicking..");
							//if(tagName.equals("input")||tagName.equals("button")){							
							if(PluginAction.contains("UTF8Validation")){
								if(!middleNodePath.equals(NodePath)){
									ManageDB m2 = new ManageDB();
							 		m2.getConnection(DBPosition);
									try{																
								 		sql="select * from NameValueList where NodePath='"+middleNodePath+"' and ProjectID='"+ProjectID+"'";
								 		ResultSet rs2 = m2.getSQL(sql);
								 		//System.out.println("[Input value:] sql="+sql);
										while(rs2.next()){										
											InputProperty = rs2.getString("InputProperty");
											InputName = rs2.getString("InputName");
											InputType = rs2.getString("InputType");
											InputValue = rs2.getString("InputValue");
											InputXPATH = "//input[@"+InputProperty+"='"+InputName+"']";									
											//System.out.println("[InputXPATH:] InputXPATH="+InputXPATH);
											try{
												InputNameXPATH = driver.findElement(By.xpath(InputXPATH));
												if(InputType.equals("file")){
													
												}else{
													InputNameXPATH.clear();
												}
												InputNameXPATH.sendKeys(InputValue);
											}catch(Exception e3){
	//											System.out.println("[Alert:] InputName "+InputName+" of "+middleNodePath+" cannot be entered since this element is disabled or not used.");
												RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Alert:] InputName "+InputName+" of "+middleNodePath+" cannot be entered since this element is disabled or not used.");
											}
											//System.out.println("[Input value:] InputXPATH="+InputXPATH+",InputValue="+InputValue);
										}
		//								if(middleNodePath.equals("023-1")){	
		//									try{
		//									WebElement upload = driver.findElement(By.xpath("//input[@name='wsdlFile']"));
		//							        upload.sendKeys("d:\\temp\\test.txt");							        
									        //driver.findElement(By.id("submit")).click();
									        //driver.findElement(By.tagName("img"));
									        //ipmort junit.framework.Assert;
									        //Assert.assertEquals("darkbulb.jpg (image/jpeg)", driver.findElement(By.tagName("p")).getText());
		//									}catch(Exception e5){
		//										System.out.println("[Alert:] Filename of "+middleNodePath+" cannot be entered since this element is disabled or not used.");
		//										RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Alert:] Filename of "+middleNodePath+" cannot be entered since this element is disabled or not used.");
		//									}
		//								}
										//https://saucelabs.com/resources/selenium-file-upload
										//http://stackoverflow.com/questions/16896685/how-to-upload-files-using-selenium-webdriver-in-java
										//<input type="file" style="width: 40em" class="im-bigFormField" name="wsdlFile">
										//https://miaxi01jpn.ca.com:8443/iam/siteminder/console/ui7/index.jsp?task.tag=SecureWSDL
										/*
								 		if(rs2.next()){
											WebElement username_UIM = driver.findElement(By.xpath("//input[@name='j_username']"));
											WebElement password_UIM = driver.findElement(By.xpath("//input[@name='j_password']"));
											username_UIM.sendKeys("administrator");
											password_UIM.sendKeys("cajcaj");
											toolbox.waitSecond(5);
											driver.findElement(By.xpath("//input[@type='submit']")).click();
											toolbox.waitSecond(10);
								 		}
								 		*/
										rs2.close();
									}
									catch(Exception e2){
										System.out.println("[Alert:] DB error when executing input value for middle node "+middleNodePath);
										RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Alert:] DB error when executing input value for middle node "+middleNodePath);
									}
									m2.close();
								}//if(!middleNodePath.equals(NodePath)){
							}
							//System.out.println("[Info:] End to click middle node "+middleNodePath);
							//RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] End to click middle node "+middleNodePath);
						
						}
					}//while
					
		 		}//end of if(!nodeURL.equals("")){
				//For Last step, capture screen, update vector, find children	
		 		
		 		/**** Must set clickfail=1 /clicktimes +1 for every click/extract fail exit, to avoid recycle for ever ***/
		 		
	 		
		 		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Start to check loading for NodePath "+NodePath);
		 		
				//not extract for non HTML, so set false for bCheckPageLoadSpecifiedElement,bCheckPageLoadFinish
				bCheckPageLoadSpecifiedElement=false; bCheckPageLoadFinish=false;
		 		if(bClick){
					bCheckPageLoadSpecifiedElement=loginStart.checkPageLoadDone(driver,ProjectID,ProductName,DBPosition,NodePath);
					int iCheckLoadCounter=1;
					while(!bCheckPageLoadSpecifiedElement){
						iCheckLoadCounter++;
						if(iCheckLoadCounter>3){
							break;
						}
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] NodePath "+NodePath+" page is NOT loaded. Wait "+iFindElementTime*iCheckLoadCounter+" seconds to load "+iCheckLoadCounter+" times.");
						toolbox.waitSecond(iPageLoadTime);
						bCheckPageLoadSpecifiedElement=loginStart.checkPageLoadDone(driver,ProjectID,ProductName,DBPosition,NodePath);
					}
					if(bCheckPageLoadSpecifiedElement)
					{		
						try{
							pageSource=driver.getPageSource();
						}catch(Exception eg){
							pageSource="null_1";
						}
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Specified html element of NodePath "+NodePath+" page is loaded. Check other elements such as layer.");
						if(iPageLoadTime>4){
							toolbox.waitSecond(iPageLoadTime);
						}else{
							toolbox.waitSecond(5);
						}
						try{ 
							pageSourceAfterServeralSeconds=driver.getPageSource();
						}catch(Exception eg1){
							pageSourceAfterServeralSeconds="null_2";
						}
												
						//toolbox.writeFile(NodePath+"_after.html",pageSourceAfterServeralSeconds);
						int iBeforeLength=pageSource.length();
						int iAfterLength=pageSourceAfterServeralSeconds.length();
												
						if(iBeforeLength==iAfterLength){
							RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] All elements of NodePath "+NodePath+" page are loaded.");
							//System.out.println("[Info:] All elements of NodePath "+NodePath+" page are loaded.");
							bCheckPageLoadFinish=true;
						}else{
							RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Other elements of NodePath "+NodePath+" page are NOT loaded. Wait loading for several seconds ....");
							bCheckPageLoadFinish=false;
						}
						
						int iCheckLoadFinishCounter=1;
						while(!bCheckPageLoadFinish){
							iCheckLoadFinishCounter++;
							if(iCheckLoadFinishCounter>4){
								bCheckPageLoadFinish=true; //force to pass
								break;
							}
							pageSource=pageSourceAfterServeralSeconds;
							
							if(iPageLoadTime>4){
								toolbox.waitSecond(iPageLoadTime);
							}else{
								toolbox.waitSecond(5);
							}
							try{
								pageSourceAfterServeralSeconds=driver.getPageSource();
							}catch(Exception eg1){
								pageSourceAfterServeralSeconds="null_"+iCheckLoadFinishCounter;
							}
							iBeforeLength=pageSource.length();
							iAfterLength=pageSourceAfterServeralSeconds.length();
							if(iBeforeLength==iAfterLength){
								RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Other elements of NodePath "+NodePath+" page are also loaded. All elements of NodePath "+NodePath+" page are loaded.");
								System.out.println("[Info:] Other elements of NodePath "+NodePath+" page are also loaded. All elements of NodePath "+NodePath+" page are loaded.");
								bCheckPageLoadFinish=true;
							}else{RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Other elements of NodePath "+NodePath+" page are NOT loaded. Wait loading for several seconds ....");
								bCheckPageLoadFinish=false;
							}
						}
					}
		 		}
				if(bCheckPageLoadFinish){
					boolean bCapture = false; 
					boolean bUnique=false;
					if(PluginAction.contains("UTF8Validation")){
						if(pageSource.indexOf(LocalString)!=-1){	
							String stringtext="";
							System.out.println("[Input value:] Local string is detected in source content.");
							List<WebElement> elements = driver.findElements(By.xpath("//a|//span")); 
									//driver.findElements(By.xpath("//a[contains(@href,'View')]")); 
							//System.out.println(elements.size());
							for(WebElement e1: elements)
							{
								stringtext= e1.getText();
								if(stringtext!=null){
									if(stringtext.indexOf(LocalString)!=-1){
										utility.highlightElementY(driver, e1);
									}
								}
							}	
							
							try{
									String deleteXPATH = "//a[contains(@href,'Delete')][last()]";
									deleteXPATH = "//a[contains(@title,'UTF8_')]";
									WebElement e3 = driver.findElement(By.xpath(deleteXPATH)); 
									toolbox.waitSecond(2);
									e3.click();
									toolbox.waitSecond(2);
									//<button type="button" id="ext-gen55" class=" x-btn-text">&nbsp;&nbsp;&nbsp;Yes&nbsp;&nbsp;&nbsp;</button>
									deleteXPATH = "(//button[@class=' x-btn-text'])[1]";
									WebElement e4 = driver.findElement(By.xpath(deleteXPATH)); 
									toolbox.waitSecond(2);
									e4.click();
									toolbox.waitSecond(2);
							}catch(Exception ex34){
									System.out.println("[Input value:] Error : "+ex34.getMessage());
							}
						}
						toolbox.getInputValueParameter(driver,DBPosition,ProjectID,NodePath,LocalString);
						//System.out.println("[Input value:] Enter input value for "+NodePath);					
						ManageDB m22 = new ManageDB();
				 		m22.getConnection(DBPosition);
						try{																
					 		sql="select * from NameValueList where NodePath='"+NodePath+"' and ProjectID='"+ProjectID+"'";
					 		ResultSet rs22 = m22.getSQL(sql);
					 		//System.out.println("[Input value:] sql22="+sql);
							while(rs22.next()){										
								InputProperty = rs22.getString("InputProperty");
								InputName = rs22.getString("InputName");
								InputType = rs22.getString("InputType");
								InputValue = rs22.getString("InputValue");
								InputXPATH = "//input[@"+InputProperty+"='"+InputName+"']";									
								//System.out.println("[InputXPATH:] InputXPATH="+InputXPATH);
								try{
									InputNameXPATH = driver.findElement(By.xpath(InputXPATH));
									if(InputType.equals("file")){
										
									}else{
										InputNameXPATH.clear();
									}
									InputNameXPATH.sendKeys(InputValue);
								}catch(Exception e3){
//									System.out.println("[Alert:] InputName "+InputName+" of "+NodePath+" cannot be entered since this element is disabled or not used.");
									RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Alert:] InputName "+InputName+" of "+middleNodePath+" cannot be entered since this element is disabled or not used.");
								}
								//System.out.println("[Input value:] InputXPATH="+InputXPATH+",InputValue="+InputValue);
							}					 		
							rs22.close();
						}
						catch(Exception e2){
							System.out.println("[Alert:] DB error when executing input value for middle node "+middleNodePath);
							RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Alert:] DB error when executing input value for middle node "+middleNodePath);
						}
						m22.close();
					}
			        if(bDebug){		
			        	boolean bDifferentUI=false;
						toolbox.createSnapshot(driver,DBPosition,DebugServer,ProjectID,ProductName,NodePath,RunMode);
						//Also get Frame UIContext keyID
						try{
							//bDifferentUI=pv.getUIStringFromSnapshot(driver, ProjectID, DBPosition, NodePath, iFindElementTime);
							bDifferentUI=pv.searchStringFromSnapshot(driver, ProjectID, DBPosition, NodePath, iFindElementTime);
						}catch(Exception ex1){}
						if(bDifferentUI){
							bUnique=true;
						}else{
							bUnique=false;
						}
			        }			        
					if(bPlay){
						bCapture = true; //sql+=" and screenName<>'' "; of UnTouch
						sql="update NodeList set playonce=1 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
						RunDB.updateDB(sql,DBPosition);
					}
					else{
						boolean bLeafPage=false;//pv.analysisUIString( driver, ProjectID, DBPosition, NodePath, iFindElementTime);
						if(bLeafPage){
							bLeaf=true;
							RunDB.updateDB("update NodeList set leaf=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'",DBPosition);
							System.out.println("[Info:] Find error message, turn to leaf page for "+NodePath);
						}
						
						//bUnique = fp.getFingerPrint( driver, ProjectID,ProductName, DBPosition, NodePath, iFindElementTime,tagName, nodeType, nodeName,attributeFP);
						//bUnique = fp.getFingerPrintForPLOC( driver, ProjectID,ProductName, DBPosition, NodePath, iFindElementTime,tagName, nodeType, nodeName,attributeFP);
						bUnique = fp.getFingerPrintForText( driver, ProjectID,ProductName, DBPosition, NodePath, iFindElementTime,tagName, nodeType, nodeName,attributeFP);
						if(bUnique){
							bCapture = true;
							System.out.println("[Info:] NodePath "+NodePath+ " UI is unique. It will be captured.");
						}else{
							bCapture = false;
							System.out.println("[Info:] NodePath "+NodePath+ " UI is NOT unique. It will not be captured.");
						}
						
						if(bLeaf){
							System.out.println("[Info:] NodePath "+NodePath+" is a leaf.");
							RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" is a leaf.");
							sql="update NodeList set touchonce=1 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
							if(bUnique){
								sql="update NodeList set touchonce=1 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
							}else{
								//It is commented and will be recovered sql="update NodeList set touchonce=1,special=0 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
							}
							RunDB.updateDB(sql,DBPosition);
							bCapture = true;
//							bUnique = true; 
						}else{
							if(bUnique){
			        			bMayBranch = true;
			        		}else{
			        			bMayBranch = false;
			        		}
							if(bBranch || bMayBranch){ 
								if(bBranch){
									System.out.println("[Info:] NodePath "+NodePath+" is a branch.");
									RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" is a branch.");
									bUnique = true;
									bCapture = true;
								}
								if(bMayBranch){
									System.out.println("[Info:] NodePath "+NodePath+" may be a branch.");	
									RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" may be a branch.");
								}
								seq=Integer.valueOf(getSeq); //last seq of nodepath xx_xx_xx_getSeq
								int mySeqAsParentSeq = seq;
								String LastNodePathAsParentPath = NodePath;
								String ProductType="NodeTag"; // force to set Tag for all sub children
								
								if(bUnique){	
									if(!bTestPath){
										sExtractNodesResult = extract.findChild(driver,iFindElementTime,Feature, LastNodePathAsParentPath,RootURLNodePath, mySeqAsParentSeq, ProjectID,DBPosition,DebugServer,ProductName,PageFilter, LevelID, RunMode,ProductType,DOMType, tagName, nodeType);
									}else{
										sExtractNodesResult="success_not_extract";
									}
									if(sExtractNodesResult.contains("success")){
										if(bTestPath){
											RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] The node "+NodePath+" has been executed successfully. ");
										}else{
											RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] The node "+NodePath+" is extracted successfully. ");
										}
					 				}else{ // fail
					 					System.out.println("[Alert:] The node "+NodePath+" fails to be extracted and cannot be set as touchonce status. It will be executed again. ");
					 					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] The node "+NodePath+" fails to be extracted and cannot be set as touchonce status. It will be executed again. ");
					 				}
									if(sExtractNodesResult.contains("success")){ // for layers, different screens
										bCapture = true;
									}else{	
										bCapture = false;
									}
								}else{
									//?or just update and capture? 
									//bCapture = true;
									//sql="delete from NodeList where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
									//sql="delete from NodeList where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
									sql="update NodeList set touchonce=1,special=0 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
									//sql="update NodeList set touchonce=1,special=1 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
									RunDB.updateDB(sql,DBPosition);
									bCapture = false;
								}
							}else{ 
								//?or just update and capture? bCapture = true;
								//bCapture = true;
								//sql="delete from NodeList where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
								//sql="delete from NodeList where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
								sql="update NodeList set touchonce=1,special=0 where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
								RunDB.updateDB(sql,DBPosition);
								bCapture = false;
							}//if(bBranch||bMayBranch){
						}//if(bLeaf){
					}//if(bPlay){ 	
					if(bCapture){
						try{	
							String screenName = toolbox.captureScrnUpdateDB(ProjectID,RunMode,DBPosition,"./screen/",Feature, NodePath, driver,"",PluginAction);			
							System.out.println("[Info:] PluginAction="+PluginAction);
							boolean bI18NIssue=false;
							//PluginAction="";						
							if(PluginAction.contains("UIContext")){		
								timer_begin = System.currentTimeMillis()/60000.000;	
								System.out.println("[Info:] Start to do UI-Context for NodePath "+NodePath);
								PageContainer="//body";
								toolbox.waitSecond(3);
								NewMapping newM = new NewMapping(driver, screenName, "//*",0,ProjectID,"3",false);       
								newM.start();

								if(tagName.contains("option")&&ExecuteParentFilter.contains("option")){							
									//CaptureDropDown.TextFormat.captureDropdownMenu(driver, ExecuteParentFilter);
									
									//The below is removed at 2015/03/04 and will be recovered 
									//CaptureDropDownScreen.captureDropdownMenu(driver);										
							    }
								sql="update NodeList set iMapped=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
								ManageDB m91 = new ManageDB();
								m91.getConnection(DBPosition);
								m91.updateSQL(sql);
								m91.close();
								timer_end = System.currentTimeMillis()/60000.000;
								time_spent = timer_end - timer_begin;	
								System.out.println("[Info:] UI-Context time spent: " + df.format(time_spent) + " minutes"); 
								
							}			
							if(PluginAction.contains("Hardcode")){
								timer_begin = System.currentTimeMillis()/60000.000;	
								System.out.println("[Info:] Start to do Hardcode analysis for NodePath "+NodePath);
								//commented since old h.detectHardcode(driver, util);		
								hardcode h9=new hardcode();
								bI18NIssue=h9.detectHardcode(ProjectID,DBPosition,NodePath,driver, util);
								bI18NIssue=true;
								if(bI18NIssue){
									sql="update NodeList set iHardcode=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
								}else{
									sql="update NodeList set iHardcode=9 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
								}
								//System.out.println("sql="+sql);
								ManageDB m92 = new ManageDB();
								m92.getConnection(DBPosition);
								m92.updateSQL(sql);
								m92.close();
								timer_end = System.currentTimeMillis()/60000.000;
								time_spent = timer_end - timer_begin;	
								System.out.println("[Info:] Hardcode time spent: " + df.format(time_spent) + " minutes"); 
								
							}
								
							if(PluginAction.contains("iFormat")){	
								timer_begin = System.currentTimeMillis()/60000.000;	
								System.out.println("[Info:] Start to do iFormat analysis for NodePath "+NodePath);
								utility util = new utility();
								iFormat iformat = new iFormat();
								bI18NIssue=iformat.iFormatDector(NodePath, driver, util, DBPosition,ProjectID,"5");
								
								if(bI18NIssue){
									sql="update NodeList set iFormatError=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
									ManageDB m93 = new ManageDB();
									m93.getConnection(DBPosition);
									m93.updateSQL(sql);
									m93.close();
								}else{
									sql="update NodeList set iFormatError=9 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
									ManageDB m94 = new ManageDB();
									m94.getConnection(DBPosition);
									m94.updateSQL(sql);
									m94.close();
								}
								timer_end = System.currentTimeMillis()/60000.000;
								time_spent = timer_end - timer_begin;	
								System.out.println("[Info:] iFormat time spent: " + df.format(time_spent) + " minutes"); 
								
							}
							if(PluginAction.contains("Truncation")){
								timer_begin = System.currentTimeMillis()/60000.000;	
								System.out.println("[Info:] Start to do Truncation analysis for NodePath "+NodePath);
								
								I18nValidator validator = new truncationCheck();								
								try {
			                        toolbox.waitSecond(3);
			                        //validator.detectTruncation(driver, "//body/*");
			                        validator.detectTruncation( ProjectID, 5, DBPosition,  NodePath,  driver, "");
			                        
				                } catch (Exception et) {
				                        et.printStackTrace();
				                }
								//sql="update NodeList set iTruncated=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
								//m9.updateSQL(sql);
								
//								ValidatorAndDetectTruncation t = new ValidatorAndDetectTruncation();
//								try {
//			                        toolbox.waitSecond(3);
//			                        t.truncationCheck(driver);
//				                } catch (Exception et) {
//				                        et.printStackTrace();
//				                }
								timer_end = System.currentTimeMillis()/60000.000;
								time_spent = timer_end - timer_begin;	
								System.out.println("[Info:] Truncation time spent: " + df.format(time_spent) + " minutes");
								
							}										
				        }catch(Exception ex){
				        	System.out.println("[Alert:]"+ex.getMessage());
				        }
					}else{						
						//toolbox.deleteScreen(ProjectID,NodePath,DBPosition);
					}
				}else{ //if(bCheckPageLoadFinish){							
					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Click node "+NodePath+" failure, set clickfail = 1.");
					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] The node "+NodePath+" is not clicked successfully. It will be clicked again.");
					 /*					  * 
					  * set clickfail=1 /click times +1
					  * 
					  */
					RunDB.setClickFail(ProjectID,DBPosition,NodePath);//for the node, set clickfail=1
					RunDB.setClickTimes(ProjectID,DBPosition,NodePath); //may be new feature, so click times +1
//					RunDB.setTreeClickFail(ProjectID,middleNodePath);//for the whole tree, set clickfail=1
				}
	 		}else{ //if(isLogin){ 	
	 			//SendMail.send("Environment performance alert email",ProductName+" page loading time = "+iPageLoadTime+"+10(additional) seconds, it is very slow for LQA Framework, please check Hub/Nodes or increase PageLoadTime","miaxi01@ca.com","");
	 			RunDB.addLog(ProjectID,DBPosition,NodePath,4,"[Alert:] Login failure, page loading time = "+iPageLoadTime+"+10(additional) seconds, it is very slow for "+NodePath+". Drive will quit.");
	 			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Login failure, Robot cannot go step by step for "+NodePath+". Drive will quit.");
	 			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Solution:] 1# Do nothing for "+NodePath+" if you set many sessions, it will recover automatically.");
	 			 /*					  * 
				  * set clickfail=1 /click times +1
				  * 
				  */
	 			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Not login, set ClickFail/ClickTimes for "+NodePath);
	 			RunDB.setClickFail(ProjectID,DBPosition,NodePath);//for the node, set clickfail=1
				RunDB.setClickTimes(ProjectID,DBPosition,NodePath); //may be new feature, so click times +1
	 		}
 			if(bDriverActive){
 				try{  
 					System.out.println("[Info:] Driver will quit for NodePath "+NodePath);//+". If you wait for 1+ second, please confirm Firefox is version 20.0");
 					RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Driver will quit for NodePath "+NodePath);//+". If you wait for 1+ second, please confirm Firefox is version 20.0");
 			    	driver.quit(); 
 			    	//System.out.println("[Info:] Driver quits successfully for NodePath "+NodePath);
 			    	RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Driver quits successfully for NodePath "+NodePath);
 			    }catch(Exception exa){}
 			}
		}//if(bDebugSnapshot){
	} //run()
	
}
