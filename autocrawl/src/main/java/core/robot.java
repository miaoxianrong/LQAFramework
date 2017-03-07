package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

import com.codecowboy.jxmail.JXMail;
import com.codecowboy.jxmail.JXMessage;

import algorithm.FingerPrint;
import db.RunDB;
import misc.SendMail;
import misc.toolbox;

public class robot {

	public static void main(String[] args) {
		System.out.println("/*****  Welcome ! *****/");
		System.out.println("/***** Auto crawl *****/");		
		System.out.println("/***** Version 1.2  *****/");
		double timer_begin = System.currentTimeMillis()/60000.000;	
		DecimalFormat df = new DecimalFormat("0.0");	
				
		 
		
		String RunMode=loginStart.readXML("RunMode"); if(RunMode==null){RunMode="RecordResume";} if(RunMode.equals("")){RunMode="RecordResume";}
		String PreferedBrowser="Chrome"; //FireFox, Chrome
		String RunningMethod=loginStart.readXML("RunningMethod"); if(RunningMethod==null){RunningMethod="WebService";}		
		System.out.println("[Info:] RunningMethod: "+RunningMethod);
		String EmailSubject="";
		String EmailBody="";		
		
		String screenFolder = "./output/00000000/screen/"; String webFolder = "./output/00000000/web/";
		String ProjectID="uncertern now";//loginStart.readXML("ProjectID");if(ProjectID==null){ProjectID="00000";}
		String ProductName="unknown";
		String ProductType="";
		String ProductURL="";
		String UserName="";
		String PassWord="";
		String TenantID="";
		String Language="";
		String sRunInfo="";
		int iActualScreenNum=0;
		int iSetScreenNum=customlibrary.Parameter.setMaxNumber(ProductName);
		
		String DBPosition=loginStart.readXML("DBServer"); if(DBPosition==null){DBPosition="localhost";}
		String EmailContact=loginStart.readXML("EmailContact");if(EmailContact==null){EmailContact="miaxi01@ca.com";}
		String DebugServer=loginStart.readXML("DebugServer");if(DebugServer==null){DebugServer=DBPosition;}
		String PluginAction=loginStart.readXML("PluginAction"); if(PluginAction==null){PluginAction="";}
		String PageLoadTime=loginStart.readXML("PageLoadTime"); if(PageLoadTime==null){PageLoadTime="5";}
		int iPageLoadTime=5;try{iPageLoadTime=Integer.parseInt(PageLoadTime);}catch(Exception ex){}
		String FindElementTime=loginStart.readXML("FindElementTime"); if(FindElementTime==null){FindElementTime="1";}
		int iFindElementTime=1;try{iFindElementTime=Integer.parseInt(FindElementTime);}catch(Exception ex){}
		String TimeFromParamter=loginStart.readXML("TimeFromParamter");if(TimeFromParamter==null){TimeFromParamter="";}
		boolean bDefaultTime=false; if(TimeFromParamter.equals("yes")){ bDefaultTime=true; } 
		String UploadFile=loginStart.readXML("UploadFile"); if(UploadFile==null){UploadFile="no";}
		
		//String RunMode=loginStart.readXML("RunMode"); if(RunMode==null){RunMode="RecordResume";} if(RunMode.equals("")){RunMode="RecordResume";}
		boolean bRecord=false; if(RunMode.equals("Record")){bRecord=true;}
		boolean bPlay=false; if(RunMode.equals("Play")){bPlay=true;}
		boolean bDebug=false; if(RunMode.equals("Debug")){bDebug=true;}  
		boolean bRecordResume=false; if(RunMode.equals("RecordResume")){bRecordResume=true;}
		boolean bPlayResume=false; if(RunMode.equals("PlayResume")){bPlayResume=true;}
		boolean bDebugResume=false; if(RunMode.contains("DebugResume")){bDebugResume=true;}
		boolean bExtractNode=false;
		/* Test servlet
		ProjectID="00000789";
		for(int iLevelID=1;iLevelID<=100;iLevelID++){
			System.out.println("[Info:] Test "+iLevelID);
			RunDB.doPost(ProjectID,DBPosition);
			toolbox.waitSecond(1);
		}
		System.exit(0);
		*/
		
		String CrawlID=loginStart.readXML("CrawlID"); if(CrawlID==null){CrawlID="0";}
		int iCrawlID = 0;
		try{
			iCrawlID=Integer.parseInt(CrawlID); 
		}catch(Exception emaxi){}
		if(iCrawlID<0){iCrawlID=0;}
		
		/*Set maximum grid count  |  sessions | pool threads*/
		String AgentID=loginStart.readXML("AgentID"); if(AgentID==null){AgentID="0";}
		int iAgentID = 0;
		try{
			iAgentID=Integer.parseInt(AgentID); 
		}catch(Exception emaxi){}
		if(iAgentID<0){iAgentID=0;}
		
		String DefinedHub=loginStart.readXML("Hub");
		String UseHubNode=loginStart.readXML("UseHubNode"); if(UseHubNode==null){UseHubNode="no";} 
		boolean bUseHubNode=false; if(UseHubNode.equals("yes")){bUseHubNode=true;}
		String sessions=loginStart.readXML("Session"); if(sessions==null){sessions="1";} 
		int max_instance =  0;
		try{
			max_instance=Integer.parseInt(sessions); 
		}catch(Exception emax){}
		if(max_instance<1){max_instance=1;}
		
		/*Set maximum testing depth*/		
		String MaxDepth=loginStart.readXML("MaxDepth"); 
		if(MaxDepth==null){MaxDepth="15";} 
		if(MaxDepth.equals("")){MaxDepth="9";}
		int iMaxDepth = 15;
		try{
			iMaxDepth=Integer.parseInt(MaxDepth); 
		}catch(Exception emdx){}
		if(iMaxDepth<1){iMaxDepth=1;}	
		int iDefinedMaxDepth=0;
		/* set start id and end id */
		String StartID=loginStart.readXML("StartID");
		if(StartID==null){StartID="1";} 
		if(StartID.equals("")){StartID="1";}
		int iStartID = 1;
		try{
			iStartID=Integer.parseInt(StartID); 
		}catch(Exception emdxs){}
		
		String EndID=loginStart.readXML("EndID");
		if(EndID==null){EndID="0";}
		if(EndID.equals("")){EndID="0";}
		int iEndID= 1;
		try{
			iEndID=Integer.parseInt(EndID); 
		}catch(Exception emdxe){}
		/* set multiple feature tree */
		boolean bInitFeatureTree = false;
		String FeatureTree=loginStart.readXML("OnlyRunFeatureTree"); if(FeatureTree==null){FeatureTree="";}
		if(FeatureTree.equals("")){bInitFeatureTree=false;}else{bInitFeatureTree=true;}
		if(bInitFeatureTree){
			ProjectID=loginStart.readXML("ProjectID");if(ProjectID==null){ProjectID="00000";}
			boolean bValidNode=false;
			String oneTree="";
			StringTokenizer stAllTree =new StringTokenizer(FeatureTree+",",",");
			while(stAllTree.hasMoreTokens()){
				oneTree=stAllTree.nextToken();
				bValidNode = RunDB.checkNodePath(ProjectID,DBPosition, oneTree);
				if(bValidNode){
					if(bDebug||bRecord||bPlay){
						RunDB.addLog(ProjectID,DBPosition,oneTree,1,"[Info:] Before starting, delete child trees of NodePath "+oneTree);
						RunDB.deleteChildTree(ProjectID,DBPosition,oneTree);
					}
				}
			}
		}		

		// From web 
		
		boolean bWebProjectActive=false;
		boolean bStandalone=true;//false;//true;
		boolean bExistingSameRunningProject=false;
		boolean bInitialization=false;
		int iRunningProjectNumber=0;
		String ProjectName="";
		
		if(true){ //while(true)
			timer_begin = System.currentTimeMillis()/60000.000;	
			if(RunningMethod.equals("Standalone")){
			//if(bStandalone){
				ProjectID=loginStart.readXML("ProjectID");if(ProjectID==null){ProjectID="00000";}
				//if(bDebug||bRecord|bPlay){
					ProductName=loginStart.readXML("ProductName");
					ProjectName=RunDB.getProjectName(DBPosition,ProjectID);
					ProductURL=loginStart.readXML("URL");
					UserName=loginStart.readXML("UserName");
					PassWord=loginStart.readXML("Password");
					TenantID=loginStart.readXML("TenantID");
					Language=loginStart.readXML("Language");
					toolbox.setParameter(ProjectID,ProductName,ProductURL,UserName,PassWord, TenantID, Language,DebugServer,EmailContact,DBPosition,RunMode);
				//}
			}
			if(RunningMethod.equals("WebService")){
				iCrawlID=0;
				bWebProjectActive=false;
				//ProjectStatus=1 and Active=0 
				String WebProjectID = RunDB.getProjectIDforWebService(DBPosition,iCrawlID); 
				if(WebProjectID.equals("NoActiveProject")){
					bWebProjectActive=false;
					PluginAction="UIContext"; 
				}else{
					bWebProjectActive=true;
					ProjectID=WebProjectID;		
					ProductName=RunDB.getProductName(DBPosition,ProjectID);
					ProjectName=RunDB.getProjectName(DBPosition,ProjectID);
					System.out.println("[Info:] Active project "+ProjectID+"-"+ProjectName+" is detected, automation starts to crawl ... ");
					PluginAction=RunDB.getPluginAction(DBPosition,ProjectID);
					System.out.println("[Info:] Active project "+ProjectID+", Plugin action: "+PluginAction);
					ProductType=productInfo.getProductType(ProductName);
					EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
					System.out.println("[Info:] EmailContact: "+EmailContact);
				}
				bExistingSameRunningProject=RunDB.setProjectRunningStatusForWebService(DBPosition, ProjectID);		
				if(bExistingSameRunningProject){
				
				}else{
					iRunningProjectNumber=RunDB.getRunningProjectNumber(DBPosition, ProjectID);
					if(iRunningProjectNumber>=5){
						EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"This task is not allowed to run since 5 projects are running.";
						EmailBody="This task is not allowed to run since 5 projects are running."+"\n";
						EmailBody+="The system can only support 5 projects which are running at the same time. "+"\n";
						EmailBody+="The information of this project has been deleted. Please wait for a while and submit information again."+"\n";
						SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
						System.out.println("[Info:] "+EmailSubject);
						RunDB.removeProject(DBPosition, WebProjectID);
						System.exit(0);
					}
				}
				//ProjectStatus=1 and Active=1 in queue from suspending (ProjectStatus from 3 to 1 and Active=1)
				
			}
			if(RunningMethod.equals("Standby")){
				bWebProjectActive=false;
				String WebProjectID = RunDB.getProjectID(DBPosition,iCrawlID);
				if(WebProjectID.equals("NoActiveProject")){
					bWebProjectActive=false;
				}else{
					bWebProjectActive=true;
					ProjectID=WebProjectID;		
					ProductName=RunDB.getProductName(DBPosition,ProjectID);
					ProjectName=RunDB.getProjectName(DBPosition,ProjectID);
					System.out.println("[Info:] Active project "+ProjectID+"-"+ProjectName+" is detected, automation starts to crawl ... ");
					PluginAction=RunDB.getPluginAction(DBPosition,ProjectID);
					System.out.println("[Info:] Active project "+ProjectID+", Plugin action: "+PluginAction);
					ProductType=productInfo.getProductType(ProductName);
					EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
				}
				if(!bWebProjectActive){
					while(!bWebProjectActive){
						WebProjectID = RunDB.getProjectID(DBPosition,iCrawlID);
						if(WebProjectID.equals("NoActiveProject")){
							bWebProjectActive=false;
							System.out.println("[Info:] No active project is detected, waiting for new project... ");
							toolbox.waitSecond(6);	
						}else{
							bWebProjectActive=true;
							ProjectID=WebProjectID;
							System.out.println("[Info:] The project "+ProjectID+" is activated, automation starts to crawl now ");
							
							ProductName=RunDB.getProductName(DBPosition,ProjectID);
							ProductType=productInfo.getProductType(ProductName);
							EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
							PluginAction=RunDB.getPluginAction(DBPosition,ProjectID);
						}
					}
				}	
				iRunningProjectNumber=RunDB.getRunningProjectNumber(DBPosition, ProjectID);
				if(iRunningProjectNumber>=5){
					EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"This task is not allowed to run since 5 projects are running.";
					EmailBody="This task is not allowed to run since 5 projects are running."+"\n";	
					EmailBody+="The system can only support 5 projects which are running at the same time. "+"\n";
					EmailBody+="The information of this project has been deleted. Please wait for a while and submit information again."+"\n";
					SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
					System.out.println("[Info:] "+EmailSubject);
					RunDB.removeProject(DBPosition, WebProjectID);
					System.exit(0);
				}
				bExistingSameRunningProject=RunDB.setProjectRunningStatus(DBPosition, ProjectID, iCrawlID);	
			}
			
			ProductName=RunDB.getProductName(DBPosition,ProjectID);
			//toolbox.clearSameProduct(ProjectID,ProductName,DebugServer,EmailContact,DBPosition,RunMode);
			
			if(bExistingSameRunningProject){
				EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"This task is not allowed to run since the same project with the same drop is running.";
				EmailBody="This task is not allowed to run since the same project with the same drop is running."+"\n";	
				EmailBody+="Please go to the 'Status and Report' tab to monitor or submit the same task after it is finished."+"\n";
				SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
				System.out.println("[Info:] "+EmailSubject);
				System.exit(0);
			}else{
				System.out.println("[Info:] Initialization check done. No duplication project is found. Auto crawl will start to run soon.");
				RunDB.setProjectStartTime(DBPosition, ProjectID);
			}
			
			PreferedBrowser= customlibrary.Parameter.setPreferedBrowser(ProductName,DefinedHub);			
//			if(ProductName.equals("CASM")||ProductName.equals("SiteMinder")){				
//				PreferedBrowser="FireFox";
//			}
			
			iDefinedMaxDepth=customlibrary.Parameter.setMaxDepth(ProductName);
			if(iMaxDepth>=iDefinedMaxDepth){
				iMaxDepth=iDefinedMaxDepth;
			}
			
			RunDB.clearFirstXpathForStandalone(ProjectID, DBPosition);
			screenFolder = "./output/"+ProjectID+"/screen/"; 
			webFolder = "./output/"+ProjectID+"/web/";
			String HardcodeFolder  = "./output/"+ProjectID+"/hardcode_screenshot/"; 
			String TruncationFolder  = "./output/"+ProjectID+"/truncation_screenshot/"; 
			String iFormatFolder  = "./output/"+ProjectID+"/iFormat_screenshot/"; 
			Language = RunDB.getLanguage(DBPosition,ProjectID);	
			System.out.println("[Info:] ProjectID="+ProjectID+",ProductName="+ProductName+",Language="+Language+",PluginAction="+PluginAction+",max screen number="+iSetScreenNum+",Depth="+iMaxDepth);
			
			EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"Auto crawl for "+PluginAction+" has been launched";
			EmailBody="Auto crawl for "+PluginAction+" has been launched."+"\n";			
			if(bDebug||bRecord){
				SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
			}
			//Set Parameter and Report
			//String AllUserName=",liahu01,genzh01,liuyu04,sonmi02,xioza01,caimi03,miaxi01,";	
			if(bDebug||bRecord){
					
				if(bWebProjectActive){
//					ProductURL=RunDB.getProductURL(DBPosition,ProjectID);
//					System.out.println("ProductURL "+ProductURL);
//					UserName=RunDB.getUserName(DBPosition,ProjectID);
//					System.out.println("UserName "+UserName);
//					PassWord=RunDB.getPassWord(DBPosition,ProjectID);
//					System.out.println("PassWord "+PassWord);
//					TenantID=RunDB.getTenantID(DBPosition,ProjectID);
//					System.out.println("TenantID "+TenantID);
//					Language=RunDB.getLanguage(DBPosition,ProjectID);
//					System.out.println("Language "+Language);
//					toolbox.waitSecond(1000000);
					toolbox.setReport(ProjectID,ProductName,DebugServer,EmailContact,DBPosition,RunMode);
				}else{
					
				}
				//toolbox.waitSecond(1000000);
			}
			//Count Mapping rate 
	//		toolbox.countMapping(ProjectID, DBPosition);
	//		toolbox.AnalysisI18NFile(ProjectID, DBPosition);
	//		toolbox.AnalysisReason(ProjectID, DBPosition);
			//toolbox.waitSecond(100000000);
	        //Initialize  updated 0926
			
			
			RunDB.getXpathFromElementsXML(ProjectID,DBPosition);
			String ImportType=loginStart.readXML("ImportType"); if(ImportType==null){ImportType="";}
			String ImportFile=loginStart.readXML("ImportFile"); if(ImportFile==null){ImportFile="";}
			if(!ImportFile.isEmpty()){
				RunDB.importFromCSVFile(ProjectID, DBPosition, ImportType, ImportFile);			
			}
			
			//clear screen folder
			String sExtractResult = "";  
	        toolbox.confirmScreenFolder(ProjectID);        
			if(bDebug||bRecord||bPlay){
				String initializing_info="[Info:] Robot is initializing for record ....";
				if(bPlay){
					initializing_info="[Info:] Robot is initializing for "+PluginAction+" ....";
				}
				RunDB.addLog(ProjectID,DBPosition,"000",1,initializing_info);
				System.out.println(initializing_info);
				toolbox.screen_clear(screenFolder);
				toolbox.screen_clear(HardcodeFolder);
				toolbox.screen_clear(TruncationFolder);
				toolbox.screen_clear(iFormatFolder);
				toolbox.web_clear(webFolder);				
			}
			//clear project
			if(iAgentID<2){
				if(bDebug||bRecord){
					//clear database 
					//String ImportTypeSample=loginStart.readXML("ImportTypeSample"); if(ImportTypeSample==null){ImportTypeSample="";}
					//if(ImportTypeSample.contains("XPATH")){
						boolean bClearSuccess=toolbox.clearProject(ProjectID,ProductName,DebugServer,EmailContact,DBPosition,RunMode);
						if(bClearSuccess){
							String MenuFilter=customlibrary.FilterSetting.setMenuFilter(ProjectID,ProductName,DBPosition);
							System.out.println("[Info:] Initialization with MenuFilter "+MenuFilter);	
							RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:] Initialization with MenuFilter "+MenuFilter.replace('\'', '&'));	
							ProductURL=RunDB.getProductURL(DBPosition,ProjectID);//if(ProductURL==null){ProductURL="";} 
							System.out.println("[Info:] ProductURL: "+ProductURL);
							sExtractResult = navigationPath.initPath(PluginAction,ProjectID,DBPosition,DebugServer,ProductName,ProductURL,iPageLoadTime,iFindElementTime,MenuFilter,RunMode, bUseHubNode,ProductType,PreferedBrowser);
							PluginAction=RunDB.regetPluginAction(DBPosition,ProjectID);
							if(sExtractResult.contains("success")){
								bInitialization=true;
			//					if(ProductType.contains("MenuURL_2Layer")){
			//						boolean bHasUngetSubMenu =true;		
			//						while(bHasUngetSubMenu){
			//							navigationPath.initLayer2MenuPath(ProjectID,DBPosition, WebServer,ProductName,iPageLoadTime,iFindElementTime,initProjectFilter,RunMode,bUseHubNode,ProductType);
			//							toolbox.waitSecond(3);
			//							bHasUngetSubMenu = RunDB.UngetSubMenu(ProjectID);
			//						}
			//					}
							}else{
								bInitialization=false;
								RunDB.addLog(ProjectID,DBPosition,"000",2,"[Alert:] Initialization failure, please try again.");
								System.out.println("[Alert:] Initialization failure, please try again.");
								EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"Alert: Auto crawl for "+PluginAction+" cannot be initialized.";
								EmailBody="Auto crawl cannot be initialized."+"\n";	
								EmailBody+="Possible reason 1: URL is not correct."+"\n";
								EmailBody+="Possible reason 2: Login information is not correct."+"\n";
								EmailBody+="Possible reason 3: Hub/node is not running."+"\n";
								SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
								System.out.println("[Info:] "+EmailBody);
								RunDB.setProjectStatusToPauseAndExit(DBPosition, ProjectID,"000");
							}
						}
//					}else{
//						System.out.println("[Alert:] Stop running. parameter.xml is not the latest, please replace it with new parameter.xml.");
//						toolbox.waitSecond(100000000);
//					}
				}
				if(bPlay){ 
					RunDB.initPlay(ProjectID,DBPosition);
					RunDB.clearClickFail(ProjectID,DBPosition);
				}		
			}
			
			boolean bHasUnTouch =false;		
			boolean bRunForEver=false;
			if(bRunForEver){
				RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:] Robot will run with forever mode");
				while(bRunForEver){
					for(int iLevelID=1;iLevelID<=iMaxDepth;iLevelID++){						
						bHasUnTouch = RunDB.UnTouch(ProjectID,DBPosition,iLevelID,iStartID,iEndID,FeatureTree,PluginAction,RunMode);
						while(bHasUnTouch){
							navigationPath.clickPath(iAgentID,ProjectID,DBPosition,DebugServer,ProductName,PluginAction,iPageLoadTime,iFindElementTime, max_instance,iLevelID,iStartID,iEndID,FeatureTree,RunMode,bUseHubNode,iMaxDepth,PreferedBrowser);
							toolbox.waitSecond(1);
							bHasUnTouch = RunDB.UnTouch(ProjectID,DBPosition,iLevelID,iStartID,iEndID,FeatureTree,PluginAction,RunMode);
						}
						toolbox.waitSecond(1);
					}					
					toolbox.waitSecond(5);					
					RunDB.clearClickFail(ProjectID,DBPosition);
				}
			}
			//Start to test
	//		if(ProjectID.equals("00000641")) RunDB.clearSubLevel(ProjectID); //only for service catalog
			
			// for debug, record, play, input value, etc. Vector.getStr, NewFeature	
			boolean bDeleteChildTree=false;boolean bDeleteTree=false;
			boolean bTest=false;boolean bTestPath=false; String testNodePath=""; String ExtractNode="";
			if(bDebugResume||bRecordResume||bPlayResume){
				//Delete child tree and extract parent node again
				String DeleteChildTree=loginStart.readXML("DeleteChildTree"); if(DeleteChildTree==null){DeleteChildTree="";}
				if(DeleteChildTree.equals("")){bDeleteChildTree=false;bTestPath=false;}else{bDeleteChildTree=true;bTestPath=true;bTest=true;}
				if(bDeleteChildTree){
					String oneTree="";boolean bValidNode=false;
					StringTokenizer stAllTree =new StringTokenizer(DeleteChildTree+",",",");
					while(stAllTree.hasMoreTokens()){
						oneTree=stAllTree.nextToken();
						bValidNode = RunDB.checkNodePath(ProjectID,DBPosition, oneTree);
						if(bValidNode){
							RunDB.addLog(ProjectID,DBPosition,oneTree,1,"[Info:] Delete child trees of NodePath "+oneTree);
							RunDB.deleteChildTree(ProjectID,DBPosition,oneTree);
						}					
					}
				}
				//Delete tree for play
				String DeleteTree=loginStart.readXML("DeleteTree"); if(DeleteTree==null){DeleteTree="";}
				if(DeleteTree.equals("")){bDeleteTree=false;bTestPath=false;}else{bDeleteTree=true;bTestPath=true;bTest=true;}
				if(bDeleteTree){
					String oneTree="";boolean bValidNode=false;
					StringTokenizer stAllTree =new StringTokenizer(DeleteTree+",",",");
					while(stAllTree.hasMoreTokens()){
						oneTree=stAllTree.nextToken();
						bValidNode = RunDB.checkNodePath(ProjectID,DBPosition, oneTree);
						if(bValidNode){
							RunDB.deleteTree(ProjectID,DBPosition, oneTree);
						}
					}
				}	
				//Run test path
				
				testNodePath=loginStart.readXML("TestPath"); if(testNodePath==null){testNodePath="";}
				if(testNodePath.equals("")){bTestPath=false;}else{bTestPath=true;bTest=true;}
				if(bTestPath){ 
					RunDB.addLog(ProjectID,DBPosition,testNodePath,1,"[Info:] Start to test with NodePath "+testNodePath);
					if(bDebugResume) System.out.println("[Info:] Start to test with NodePath "+testNodePath);
					RunDB.clearTouchClickFail(ProjectID,DBPosition, testNodePath);
					String oneNode=""; boolean bValidNode=false;
					StringTokenizer stAllNodes =new StringTokenizer(testNodePath+",",",");
					while(stAllNodes.hasMoreTokens()){
						oneNode=stAllNodes.nextToken();
						bValidNode = RunDB.checkNodePath(ProjectID,DBPosition, oneNode);
						if(bValidNode){
							navigationPath.clickOneNode(bTestPath,ProjectID,DBPosition,DebugServer,ProductName,PluginAction,iPageLoadTime,iFindElementTime,oneNode, max_instance, RunMode,bUseHubNode,PreferedBrowser);
						}else{
							System.out.println("[Alert:] Invalid test node path "+oneNode);						
						}
					}
	
				}
				
				ExtractNode=loginStart.readXML("ExtractNode");if(ExtractNode==null){ExtractNode="";}
				if(ExtractNode.equals("")){bExtractNode=false;}else{bExtractNode=true;bTest=true;}
				if(bExtractNode){
					boolean bValidNode=false;
					String oneTree="";
					StringTokenizer stAllTree =new StringTokenizer(ExtractNode+",",",");
					while(stAllTree.hasMoreTokens()){
						oneTree=stAllTree.nextToken();
						bValidNode = RunDB.checkNodePath(ProjectID,DBPosition, oneTree);
						if(bValidNode){
							RunDB.addLog(ProjectID,DBPosition,oneTree,1,"[Info:] Starting ...");
							RunDB.addLog(ProjectID,DBPosition,oneTree,1,"[Info:] Before extracting, delete child trees of NodePath "+oneTree);
							RunDB.deleteChildTree(ProjectID,DBPosition,oneTree);
						}
					}
					RunDB.addLog(ProjectID,DBPosition,ExtractNode,1,"[Info:] Start to extract NodePath "+ExtractNode);
					RunDB.clearTouchClickFail(ProjectID,DBPosition, ExtractNode);
					String oneNode=""; 
					StringTokenizer stAllNodes =new StringTokenizer(ExtractNode+",",",");
					while(stAllNodes.hasMoreTokens()){
						oneNode=stAllNodes.nextToken();
						bValidNode = RunDB.checkNodePath(ProjectID,DBPosition, oneNode);
						if(bValidNode){
							navigationPath.clickOneNode(bTestPath,ProjectID,DBPosition,DebugServer,ProductName,PluginAction,iPageLoadTime,iFindElementTime,oneNode, max_instance, RunMode,bUseHubNode,PreferedBrowser);
						}else{
							System.out.println("[Info:] Invalid test node path "+oneNode);						
						}
					}
				}
				
				boolean bTestForever=false; //bTestForever=true;
				if(bTestForever){ //TestForever mode
					RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:] Robot will test with forever mode");
					while(bDebugResume){
						//Delete child tree and extract parent node again
						DeleteChildTree=RunDB.getTestPath(ProjectID,DBPosition);
						if(DeleteChildTree.equals("")){bDeleteChildTree=false;}else{bDeleteChildTree=true;bTest=true;}
						if(bDeleteChildTree){
							String oneTree="";
							StringTokenizer stAllTree =new StringTokenizer(DeleteChildTree+",",",");
							while(stAllTree.hasMoreTokens()){
								oneTree=stAllTree.nextToken();
								RunDB.deleteChildTree(ProjectID,DBPosition, oneTree);
							}
						}				
						//Run test path
						testNodePath=RunDB.getTestPath(ProjectID,DBPosition);
						if(testNodePath.equals("")){bTestPath=false;}else{bTestPath=true;bTest=true;}
						if(bTestPath){ 
							RunDB.clearTouchClickFail(ProjectID,DBPosition, testNodePath);
							String oneNode="";
							StringTokenizer stAllNodes =new StringTokenizer(testNodePath+",",",");
							while(stAllNodes.hasMoreTokens()){
								oneNode=stAllNodes.nextToken();
								navigationPath.clickOneNode(bTestPath,ProjectID,DBPosition,DebugServer,ProductName,PluginAction,iPageLoadTime,iFindElementTime,oneNode, max_instance, RunMode,bUseHubNode,PreferedBrowser);
							}
						}
						RunDB.resetTestPath(ProjectID,DBPosition, testNodePath);
						System.out.println("\n[Info:] Robot will run other testing path after several seconds.");
						toolbox.waitSecond(5);
					}
				}
				
			}//if(bDebugResume||bRecordResume||bPlayResume){
			if(bTest){
				RunDB.addLog(ProjectID,DBPosition,testNodePath,1,"[Info:] Completely to run testing path "+testNodePath);
				RunDB.addLog(ProjectID,DBPosition,testNodePath,1,"[Info:] Set <ExtractNode></ExtractNode> in parameter.xml to run all/other node paths now. ");
			}
			else{
				// Start to run from here
				sRunInfo="[Info:] Robot is starting to click node path to ";
				sRunInfo+=RunMode;
				System.out.println(sRunInfo);
				RunDB.addLog(ProjectID,DBPosition,testNodePath,1,sRunInfo);
				int iTotalTryTimes=1;
				if(bPlayResume){
					iTotalTryTimes=1;
				}
				
				if(iAgentID<2){				
					for(int iTryTimesForClickFail=0;iTryTimesForClickFail<iTotalTryTimes;iTryTimesForClickFail++){
						for(int iLevelID=1;iLevelID<=iMaxDepth;iLevelID++){
							//set queueTimes to avoid possible recycle for ever
							//int iLevelID=iMaxDepth;
							bHasUnTouch = RunDB.UnTouch(ProjectID,DBPosition,iLevelID,iStartID,iEndID,FeatureTree,PluginAction,RunMode);
							iActualScreenNum=RunDB.getScreenNum(ProjectID,DBPosition);						
							if(iActualScreenNum<iSetScreenNum){
								while(bHasUnTouch){
									navigationPath.clickPath(iAgentID,ProjectID,DBPosition,DebugServer,ProductName,PluginAction,iPageLoadTime,iFindElementTime, max_instance,iLevelID,iStartID,iEndID,FeatureTree,RunMode,bUseHubNode,iMaxDepth,PreferedBrowser);
									toolbox.waitSecond(1);
									bHasUnTouch = RunDB.UnTouch(ProjectID,DBPosition,iLevelID,iStartID,iEndID,FeatureTree,PluginAction,RunMode);
									
									iActualScreenNum=RunDB.getScreenNum(ProjectID,DBPosition);						
									if(iActualScreenNum>iSetScreenNum){
										bHasUnTouch=false;
									}
								}
								toolbox.waitSecond(1);				
							}
						}
						//iMaxDepth not iMaxDepth=1,2,3, be changed to iMaxDepth<9
						//Complete, send email
						double timer_end_middle = System.currentTimeMillis()/60000.000;
						double time_spent_middle = timer_end_middle - timer_begin;	
//						if(iTryTimesForClickFail==1){
//							RunDB.addLog(ProjectID,DBPosition,testNodePath,1,"[Info:] Run once time spent: " + df.format(time_spent_middle) + " minutes. Handle catched nodes continuously");	 
//							RunDB.setProjectFinishStatus(DBPosition, ProjectID, iCrawlID);
//							EmailSubject="["+ProjectID+"]"+"Auto crawl for "+PluginAction+" has been crawled successfully";
//							EmailBody="Auto crawl for "+PluginAction+" has been crawled once successfully"+".\n";
//							EmailBody+="Run time:            "+df.format(time_spent_middle) +" minutes."+"\n";
//							SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
//							System.out.println("[Info:] Run once successfully. You can stop now. Robot will handle catched nodes continuously.");
//						}
						toolbox.waitSecond(60);	
						RunDB.clearClickFail(ProjectID,DBPosition);
					}
				}else{
					while(iAgentID>1){
						for(int iLevelID=1;iLevelID<=iMaxDepth;iLevelID++){
							navigationPath.clickPath(iAgentID,ProjectID,DBPosition,DebugServer,ProductName,PluginAction,iPageLoadTime,iFindElementTime, max_instance, iLevelID,iStartID, iEndID,FeatureTree,RunMode,bUseHubNode,iMaxDepth,PreferedBrowser);
						}
					}
					toolbox.waitSecond(10);
				}
				if(bPlay || bPlayResume){
					RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:] Successfully to capture "+RunDB.getPlocNum(ProjectID,DBPosition)+" screens with play or play resume mode");
				}else{
					RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:] Successfully to capture "+RunDB.getScreenNum(ProjectID,DBPosition)+" screens totally");
				}
				
			}
			//ic.binary_compare("./truncation_screenshot//");
			double timer_end = System.currentTimeMillis()/60000.000;
			double time_spent = timer_end - timer_begin;	
			if(PluginAction.contains("UIContext")){
				if(UploadFile.equals("yes")||UploadFile.equals("Yes")){
					misc.UploadFile.TransferToFTP(ProjectID);
				}
			}
			RunDB.setProjectIDFinish(DBPosition,ProjectID);	
			RunDB.setProjectEndTime(DBPosition, ProjectID);
			
			if(true){//if(iTryTimesForClickFail==1){
				RunDB.addLog(ProjectID,DBPosition,testNodePath,1,"[Info:] Run once time spent: " + df.format(time_spent) + " minutes. Handle catched nodes continuously");	 
				RunDB.setProjectFinishStatus(DBPosition, ProjectID, iCrawlID);
				EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"Auto crawl for "+PluginAction+" is done successfully";
				EmailBody="Auto crawl for "+PluginAction+" has been finished successfully"+".\n";	
				if(PluginAction.contains("UIContext")){
					EmailBody+="Successfully to capture "+RunDB.getScreenNum(ProjectID,DBPosition)+" screens totally."+"\n";
					EmailBody+="Screens are stored in \\\\webauto\\output\\"+ProjectID+"\\screen."+"\n";
					EmailBody+="(Access username=causer, password=causer)."+"\n";
					if(UploadFile.equals("yes")){
						EmailBody+="Screen files have been uploaded to FTP server xlat.ca.com and ftpcai."+"\n";
					}else{
						EmailBody+="Screen files have not been uploaded to FTP server due to parameter setting, please upload them manually."+"\n";
					}
				}
				EmailBody+="Run start time:            "+RunDB.getProjectStartTime(DBPosition, ProjectID)+"\n";
				EmailBody+="Run end time:            "+RunDB.getProjectEndTime(DBPosition, ProjectID)+"\n";
				EmailBody+="Total run time:            "+df.format(time_spent) +" minutes."+"\n";
				SendMail.send(DBPosition,ProjectID,ProductName,"000",EmailSubject,EmailBody,EmailContact,"");
				System.out.println("[Info:] Run once successfully. ");
			}
			
			RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:] Total time spent: " + df.format(time_spent) + " minutes");	
			RunDB.addLog(ProjectID,DBPosition,"000",1,"[Info:]  /********  All operations have been executed by the robot.  End. ******/"); 
			System.out.println("[Info:] Total time spent: " + df.format(time_spent) + " minutes"); 
			System.out.println("[Info:]  /********  All operations have been executed by the robot.  End. ******/"); 
		}	
	}	

}
