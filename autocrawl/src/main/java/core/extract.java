package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import misc.SendMail;
import misc.toolbox;
import customlibrary.FilterSetting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import db.ManageDB;
import db.RunDB;


public class extract {
	public static String findChild(WebDriver driver,int iFindElementTime,String ParentFeature,String NodePath, String RootURLNodePath,int ParentSeq,String ProjectID,String DBPosition,String DebugServer,String ProductName,String PageFilter,int LevelID, String RunMode,String ProductType,String ParentDOMType, String ParentTagName, String ParentNodeType){
		//session start
		boolean bDebug=false; if(RunMode.contains("Debug")){bDebug=true;}
		boolean bDebugResumeXPATH=false; if(RunMode.equals("DebugResumeXPATH")){bDebugResumeXPATH=true;}
		String sExtractResult="unkown"; String DOMType="HTML";		
        double timer_begin_1 = System.currentTimeMillis()/60000.000;
        DecimalFormat df = new DecimalFormat("0.0");
        boolean bFind0Element= false;
        //String EmailContact=loginStart.readXML("EmailContact");if(EmailContact==null){EmailContact="miaxi01@ca.com";}
                
	    RunDB.clearExtractFail(ProjectID, DBPosition, NodePath);   //extract times = click (fail) times
	    System.out.println("[Info:] NodePath "+NodePath+" is trying to find and extract childs");
	    RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] NodePath "+NodePath+" is trying to find and extract childs");
		String ParentNodePath = NodePath;
		String ParentRootURLNodePath = RootURLNodePath;
		List<WebElement> allElements=null;int iTotalFind=0;
		
		boolean bCheckPageLoadSpecifiedElement=true; 
		if(bDebug){
			String nodeURL=DebugServer+"/web/"+NodePath+".html";
			RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Switch to snapshot URL:"+nodeURL);
			double load_begin = System.currentTimeMillis()/1000.000;
			driver.get(nodeURL);
			double load_end = System.currentTimeMillis()/1000.000;
			double load_spent = load_end - load_begin;
			RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] URL achieving time : " + String.valueOf(load_spent).substring(0,3) + " seconds");
			
			bCheckPageLoadSpecifiedElement=loginStart.checkPageLoadDone(driver,ProjectID,ProductName,DBPosition,NodePath);
			int iCheckLoadCounter=1;
			while(!bCheckPageLoadSpecifiedElement){
				iCheckLoadCounter++;
				if(iCheckLoadCounter>3){
					break;
				}
				RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] NodePath "+NodePath+" page is NOT loaded. Wait "+iFindElementTime*iCheckLoadCounter+" seconds to load "+iCheckLoadCounter+" times.");
				toolbox.waitSecond(iFindElementTime);
				bCheckPageLoadSpecifiedElement=loginStart.checkPageLoadDone(driver,ProjectID,ProductName,DBPosition,NodePath);
			}
		}
		
		int iName=PageFilter.indexOf("|//name_");
		String ExecutePageFilter="";
		
		int iFrameLen=0;
 		String allFrame="";
 		String oneFrame="";
 		StringTokenizer stFrame = null;
		
		if(bCheckPageLoadSpecifiedElement){
			RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Start to find elements after NodePath "+NodePath+" page is fully loaded.");
			try{
				 try{
					 boolean bUpdatePageFilter = false;
					 if(bDebugResumeXPATH){
						 String ExtractNodeXPATH=loginStart.readXML("ExtractNodeXPATH");
						 if(ExtractNodeXPATH==null){ExtractNodeXPATH="";}
						 if(ExtractNodeXPATH.equals("")){
							 bUpdatePageFilter = false;
						 }else{
							 bUpdatePageFilter = true;
							 PageFilter = ExtractNodeXPATH+"|//name_debug";
						 } 
					 }
					 iName=PageFilter.indexOf("|//name_");
					 if(iName>0){
						 ExecutePageFilter=PageFilter.substring(0,iName);
					 }else{
						 ExecutePageFilter=PageFilter;
					 }
					 	//System.out.println("[Extract:] Before ExecutePageFilter="+ExecutePageFilter);
						iFrameLen=ExecutePageFilter.indexOf("/html/");
						if(iFrameLen>0){
							driver.switchTo().defaultContent();
							allFrame=ExecutePageFilter.substring(0,iFrameLen);
							stFrame = new StringTokenizer(allFrame,"-");
							while(stFrame.hasMoreTokens()){
								oneFrame="//"+stFrame.nextToken();								
								try{
									//System.out.println("[Extract:] start for oneFrame="+oneFrame);
									driver.switchTo().frame(driver.findElement(By.xpath(oneFrame)));
									//System.out.println("Page source of "+oneFrame+"="+driver.getPageSource());
									//System.out.println("[Extract:] end for oneFrame="+oneFrame);
								}catch(Exception ef){
									RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Node path "+NodePath+" [Suspend, frame alert]"+ef.getMessage());
									System.out.println("[Alert:] Node path "+NodePath+" [Suspend, frame alert]"+ef.getMessage());
								}
							}
							ExecutePageFilter=ExecutePageFilter.substring(iFrameLen);
							//System.out.println("[Extract:] After ExecutePageFilter="+ExecutePageFilter);
						}
						

		    		 allElements = driver.findElements(By.xpath(ExecutePageFilter)); 
		    		 
		    		 if(bUpdatePageFilter){
						 PageFilter=PageFilter.replace('\'', '&');
						 RunDB.setPageFilter(ProjectID,DBPosition,NodePath,PageFilter);
						 //toolbox.displayMulElements(allElements);
		    		 }
					 iTotalFind=allElements.size();
					 if(iTotalFind>300){
//						 SendMail.send("Find alert", "[Alert:] "+ProjectID+"-"+ProductName+": NodePath "+NodePath+" find more than 200 elements.Please modify xpath.", EmailContact);
						 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] NodePath "+NodePath+" find more than 300 elements. Please confirm xpath.");
					 }
					 if(iTotalFind==0){
						 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" find 0 element. Try to execute findElements again.");
						 System.out.println("[Info:] NodePath "+NodePath+" find 0 element. Try to execute findElements again.");
						 toolbox.waitSecond(5);
						 allElements = driver.findElements(By.xpath(ExecutePageFilter)); 
						 iTotalFind=allElements.size();
						 if(iTotalFind==0){
							 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] "+ProjectID+", NodePath "+NodePath+" still find 0 child/element.");
							 System.out.println("[Alert:] "+ProjectID+", NodePath "+NodePath+" still find 0 child/element.");
						 }else{
							 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] "+ProjectID+", NodePath "+NodePath+" can find "+iTotalFind+" childs/elements now.");
						 }
					 }
				 }catch(Exception eae){
					 System.out.println("[Alert:] findElements: No element is found or driver is out of control, may be due to pop up/iFindElementTime/ProjectFilter/PageFilter, can not get elements of "+NodePath);	
					 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] findElements: No element is found or driver is out of control, may be due to pop up/iFindElementTime/ProjectFilter/PageFilter, can not get elements of "+NodePath);		 
				 }
				 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] **** NodePath "+NodePath+" gets "+iTotalFind+" elements(nodes)");
				 System.out.println("[Info:] **** NodePath "+NodePath+" gets "+iTotalFind+" elements(nodes)");
				 
				 // with PageFilter="+PageFilter); 
				 if(iTotalFind==0){
//					 if(LevelID==1){
//					 } //if(LevelID==1){
					 try{							 
						 if(ProductName.equals("ServiceCatalog")){ //add code for click concurrentPath and vector
							 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" try to search frame");
//							 driver.switchTo().frame(driver.findElement(By.cssSelector("frame[id='cataloggui']")));
							 driver.switchTo().frame(driver.findElement(By.xpath("//frame[2]")));
							 allElements = driver.findElements(By.xpath(ExecutePageFilter));
							 iTotalFind=allElements.size();
							 if(iTotalFind==0){
								 driver.switchTo().frame(driver.findElement(By.xpath("//iframe[1]")));
								 allElements = driver.findElements(By.xpath(ExecutePageFilter));
								 iTotalFind=allElements.size();
								 if(iTotalFind==0){
									 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] PageFilter may has no normal format xpath, please check NodePath "+NodePath+" PageFilter");
									 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Elements in Frame cannot be found, try to find iFrame");
									 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Elements in iFrame still cannot be found");
								 }else{
									 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Success to get "+iTotalFind+" elements from iFrame.");
									 DOMType="iframe";
								 }
							 }else{
								 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Success to get "+iTotalFind+" elements from Frame.");
								 DOMType="frame";
							 }
						 }
					 }catch(Exception eframe){
						 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Frame: No element is found. Or, driver is not normal after clicking. Also, can not find frame for "+ NodePath);
					 }
					 if(iTotalFind==0){
						 bFind0Element= true; 
					 }
				 }
//				 toolbox.displayMulElements(allElements); 
			 }catch(Exception ex){
				 System.out.println("[Alert] "+ex.getMessage());
				 bFind0Element= true;
				 //Debug, what kind of exception? execute setClickExtractFail?
//				 RunDB.setClickExtractFail(ProjectID,DBPosition,ParentNodePath);//for the node, set clickfail=1
//				 RunDB.setClickTimes(ProjectID,DBPosition,ParentNodePath); //click times +1					
			 }
			 if(LevelID==0){
					String filename="./url.txt"; 
					File fileElement= new File(filename); 
					if(fileElement.exists()){
						bFind0Element=false;
					}
			 }
			 if(bFind0Element){
				 if(NodePath.equals("000")){
					 	String ErrorDescription="[Alert:] No DOM elements are found on mian page extracting."+"\n";
					 	ErrorDescription+="[Info:] It may be due to network problem occasionally, please try again."+"\n";
					 	ErrorDescription+="[Info:] Project status is changed to Suspend from Running."+"\n";
						ErrorDescription+="[Info:] Auto crawl will stop and exit. "+"\n";	
						String EmailSubject="[Alert:] No DOM elements are found on mian page extracting. ";
						String EmailBody=ErrorDescription;
						SendMail.sendExceptionAlertEmail(ProjectID,DBPosition,NodePath,ProductName,EmailSubject,EmailBody,"");
						System.out.println(ErrorDescription);	
						RunDB.addLog(ProjectID,DBPosition,NodePath,4, ErrorDescription);
						RunDB.setProjectStatusToPauseAndExit(DBPosition, ProjectID,NodePath);			
				 }
				 sExtractResult = "success_0"; 
				 String sql="update NodeList set touchonce=1,branch=0,hasChild=0,extractfail=0,childNum=0,childList='' where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
				 ManageDB m2 = new ManageDB();
				 m2.getConnection(DBPosition);
			     m2.updateSQL(sql);
			     m2.close();   
				 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] set touchonce=1,Find 0 child/element for parent node "+NodePath+", please check PageFilter"); 
				 
				 if(ProductName.equals("Clarity")){
					 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] ProjectID "+ProjectID+", NodePath "+ParentNodePath+" cannot find elements. Set click fail. Please increasee <FindElementTime> in parameter.xml");
//					 SendMail.send("Running status alert","ProjectID "+ProjectID+", NodePath "+ParentNodePath+" cannot find elements. Set click fail. Please increasee <FindElementTime>"+iFindElementTime+"</FindElementTime> in parameter.xml",EmailContact);
					
					 sExtractResult = "find_fail"; 
					 /*					  * 
					  * set clickfail=1 /click times +1
					  * 
					  */
					 RunDB.setClickExtractFail(ProjectID,DBPosition,ParentNodePath);//for the node, set clickfail=1
					 RunDB.setClickTimes(ProjectID,DBPosition,ParentNodePath); //click times +1
					 
					 RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] setClickExtractFail. setClickTimes.Cannot load page for parent node "+NodePath+" , please check test_css of checkPageLoadDone");
				 }
			 }else{
				 LevelID=LevelID+1; // Level+1, from 0,1,2,3,4,5,6 to 1,2,3,4,5,6,7, etc.
				 
				 //Go to child nodes
				 if(LevelID==1&&ProductType.contains("MenuURL")){
					 RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Robot is extracting menu URL ...");
				 }
				 RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Robot starts to extract  ...");
				 sExtractResult = extract.extracting(allElements,PageFilter,ParentFeature,DOMType,ParentNodePath,ParentRootURLNodePath, ProjectID,DBPosition,DebugServer,ProductName, ParentSeq, LevelID, RunMode,ProductType, ParentTagName, ParentNodeType);
				 
			 }
		}else{ //if(bCheckPageLoadDone){
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] ProjectID "+ProjectID+", NodePath "+ParentNodePath+" cannot find elements. Set click fail. Please increasee <FindElementTime> in parameter.xml");
//			SendMail.send("Running status alert","ProjectID "+ProjectID+", NodePath "+ParentNodePath+" cannot find elements. Set click fail. Please increasee <FindElementTime>"+iFindElementTime+"</FindElementTime> in parameter.xml",EmailContact);
			sExtractResult = "find_fail"; 
			 /*					  * 
			  * set clickfail=1 /click times +1
			  * 
			  */
			RunDB.setClickExtractFail(ProjectID,DBPosition,ParentNodePath);//for the node, set clickfail=1
			RunDB.setClickTimes(ProjectID,DBPosition,ParentNodePath); //click times +1
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] setClickExtractFail. setClickTimes.Cannot load page for parent node "+NodePath+" , please check test_css of checkPageLoadDone");
			System.out.println("[Alert:] setClickExtractFail. setClickTimes. Cannot load page for parent node "+NodePath+" , please check test_css of checkPageLoadDone");
			
		}
 		double timer_end_1 = System.currentTimeMillis()/60000.000;
		double time_spent_1 = timer_end_1 - timer_begin_1;	
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Extract time spent: " + df.format(time_spent_1) + " minutes");
		//System.out.println("[Info:] Extract time spent: " + df.format(time_spent_1) + " minutes");
		return sExtractResult;
	}
	
	static String extracting(List<WebElement> allElements,String ParentFilter,String ParentFeature,String DOMType, String ParentNodePath, String ParentRootURLNodePath,String ProjectID,String DBPosition,String DebugServer,String ProductName,int ParentSeq,  int LevelID, String RunMode,String ProductType, String ParentTagName, String ParentNodeType){	
		boolean bValidNode=false; int iRadio=0;
		boolean bDebugResumeXPATH=false; if(RunMode.equals("DebugResumeXPATH")){bDebugResumeXPATH=true;}
		boolean bExtractSuccess = true; String sExtractResult="";String fingerVector=ProjectID+"_";
		int iTotal=allElements.size();
		String WebURL=productInfo.getWebURL(ProductName); //System.out.println(WebURL);		
		String TagOrURL;String MenuHref;String Feature;String RootURLNodePath,PageFilter;
		if(LevelID==1&&ProductType.contains("MenuURL")){
			RunDB.addLog(ProjectID,DBPosition,ParentNodePath,1, "[Info:] Extracting "+iTotal+" child nodes(URLs) from main page, please wait for a while.");
		}
		else{
			RunDB.addLog(ProjectID,DBPosition,ParentNodePath,1,"[Info:] Extracting "+ParentNodePath+" ("+iTotal+" child nodes), please wait for a while.");
		}		
		
		String sql,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,onkeydown,nodeType,nodeText,nodeTitle,onclick,nodeNgclick, nodeTarget;//,strID;
		int special,visible,clickable,hasChild,leaf,branch,mainFeature;
		int popup,newtab,dropdown,doubleclick,mouseright,mouseover,tips,frame,iframe;
		int mainMenuFilter,includerFilter,excluderFilter,noLessLeafFilter,noMoreUniqueFilter,forceSetBranchFilter;
		nodeId="";nodeHref="";nodeName="";nodeValue="";nodeClass="";onkeydown="";nodeType="";nodeText="";nodeTitle="";onclick="";nodeTarget=""; nodeNgclick="";
	    String nodeSrc="";PageFilter="";String MyDOMType="";String EmailContact=""; String logInfo="";
		int seq=0;String childList="";String uniqueObject="";String NodePath="";Feature=""; int iLevel1NodePath = 0;
		boolean bSpecial = false; int iSpecialCount=0;boolean bIdentify=false;boolean bURLtoTag=false; boolean bAllChildsInFamilily = false;
	    boolean bVisible = false; ExpectedCondition<WebElement> strClickable; boolean bClickable=false;
		try{
			sExtractResult="";
			double timer_begin1 = System.currentTimeMillis()/1000.000;
			ArrayList<ArrayList<String>> allNodes = new ArrayList<ArrayList<String>>(); 
	        for (WebElement e : allElements) {  
	        	seq++;
	        	
	        	ArrayList<String> onenode = new ArrayList<String>(); 
	        	
	        	if(sExtractResult.equals("fail_element")){
	        		EmailContact=loginStart.readXML("EmailContact");
	        		if(EmailContact==null){EmailContact="miaxi01@ca.com";}
	        		//SendMail.send("Extract alert", ProjectID+"-"+ProductName+": "+ParentNodePath+" may be hidden or its child element is out of control", EmailContact,"");
	        		//RunDB.setLeaf(ProjectID, DBPosition, ParentNodePath); //does not extract again
	        		break; 
	        	}
	        	/* All changed unique variables should be initialized */

	        	
	        	try{
		        	bVisible = e.isDisplayed(); if(bVisible){visible=1;}else{visible=0;}
		        	strClickable = ExpectedConditions.elementToBeClickable(e);
		        	if(strClickable!=null ){clickable=1;bClickable=true;}else{clickable=0;bClickable=false;}	
		        	onenode.add(""+visible); //0
		        	onenode.add(""+clickable); //1
		        	tagName = e.getTagName(); 
		        	onenode.add(tagName); //2		        	
		        	nodeTarget = e.getAttribute("target");if(nodeTarget!=null){nodeTarget=nodeTarget.replace('\'', '&');} else { nodeTarget=""; } 
		        	onenode.add(nodeTarget); //3
		        	nodeId = e.getAttribute("id");if(nodeId!=null){nodeId=nodeId.replace('\'', '&');} else { nodeId=""; } 
		        	onenode.add(nodeId);//4
		        	nodeHref = e.getAttribute("href");if(nodeHref!=null){nodeHref=nodeHref.replace('\'', '&');} else { nodeHref=""; } 
		        	if(nodeHref.contains(".html")){
		        		nodeHref=nodeHref.replaceAll(DebugServer+"/web/"+ParentNodePath+".html", WebURL);
		        	}else{
		        		nodeHref=nodeHref.replaceAll(DebugServer+"/web/", WebURL);
		        	}
		        	nodeHref=nodeHref.replaceAll(DebugServer, WebURL);
		        	
		        	onenode.add(nodeHref);//5
		        	nodeName = e.getAttribute("name");if(nodeName!=null){nodeName=nodeName.replace('\'', '&');} else { nodeName=""; } 
		        	if(nodeName.equals("null")){nodeName=""; } 
		        	onenode.add(nodeName);//6
		        	nodeValue = e.getAttribute("value");if(nodeValue!=null){nodeValue=nodeValue.replace('\'', '&');} else { nodeValue=""; } 
		        	nodeValue=nodeValue.trim(); if(nodeValue.equals("null")){nodeValue="";}
		        	if(nodeValue.equals("null")){nodeValue=""; }
		        	onenode.add(nodeValue);//7
		        	nodeClass= e.getAttribute("class");if(nodeClass!=null){nodeClass=nodeClass.replace('\'', '&');} else { nodeClass="null"; }
		        	if(nodeClass.equals("null")){nodeClass=""; }
		        	onenode.add(nodeClass);//8
		        	nodeType= e.getAttribute("type");if(nodeType!=null){nodeType=nodeType.replace('\'', '&');} else { nodeType=""; } 
		        	if(nodeType.equals("null")){nodeType=""; }
		        	onenode.add(nodeType);//9
		        	onkeydown = e.getAttribute("onkeydown");if(onkeydown!=null){onkeydown=onkeydown.replace('\'', '&');} else { onkeydown=""; }
		        	if(onkeydown.length()>100){
		        		onkeydown=onkeydown.substring(0,100);
			        }
		        	onenode.add(onkeydown);//10
		        	onclick = e.getAttribute("onclick");if(onclick!=null){onclick=onclick.replace('\'', '&');}  else { onclick=""; }
		        	if(onclick.length()>100){
		        		onclick=onclick.substring(0,100);
			        }
		        	onenode.add(onclick);//11
		    		nodeTitle = e.getAttribute("title");
		    		if(nodeTitle!=null){nodeTitle=nodeTitle.replace('\'', '&');} else {nodeTitle="";}  		    		
		    		nodeTitle=nodeTitle.trim(); if(nodeTitle.equals("null")){nodeTitle="";}
		    		onenode.add(nodeTitle);//12		    		
		    		nodeText= e.getText();if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
		        	nodeText=nodeText.trim(); if(nodeText.equals("null")){nodeText="";}
		        	onenode.add(nodeText);//13
		        	nodeSrc= e.getAttribute("src");if(nodeSrc!=null){nodeSrc=nodeSrc.replace('\'', '&');} else { nodeSrc=""; }
		        	onenode.add(nodeSrc);//14
		        	nodeNgclick= e.getAttribute("ng-click");if(nodeNgclick!=null){nodeNgclick=nodeNgclick.replace('\'', '&');} else { nodeNgclick=""; }
		        	onenode.add(nodeNgclick);//15	
		        	fingerVector+=tagName.charAt(0);
		        	//add onenode to allNodes    
		        	allNodes.add((ArrayList<String>)onenode.clone());
	        	}catch(Exception ec0){
	        		//System.out.println("[Excetion:]"+ec0.getMessage());
	        		logInfo="[Alert:] For WebElements, ParentNodePath "+ParentNodePath+" gets an error message="+ec0.getMessage();
	        		logInfo=logInfo.replace('\'', '&');
	        		if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
    				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,logInfo);	        		
	        		bExtractSuccess = false;
	        		sExtractResult="fail_element";
	        		//cannot execute RunDB since m1 is not closed now.	
	        		RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,"[Alert:] Missing child "+seq+" of "+ParentNodePath);
	        		break; //does not take effect. Why?
	    		} 
	        }
	        double timer_end1 = System.currentTimeMillis()/1000.000;
			double time_spent1 = timer_end1 - timer_begin1;	
			RunDB.addLog(ProjectID,DBPosition,ParentNodePath,1, "[Info:] Get WebElements time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");	
			System.out.println("[Info:] Get WebElements time spent : " + String.valueOf(time_spent1).substring(0,3) + " seconds");
			if(LevelID==1){
				String filename="./url.txt"; String fileline="";
				int iIndex1=0; int iIndex2=0;
				File fileElement= new File(filename); 
				if(fileElement.exists()){
					RunDB.addLog(ProjectID, DBPosition, "000", 1, "[Info:] Detect url.txt. Start to get URL from url.txt");
					
					BufferedReader br =null; boolean bFileRead=false;
					try{
						br = new BufferedReader (new FileReader(fileElement));
						bFileRead=true;
					}catch(Exception ex){
						bFileRead=false;
					}
					boolean bLastLine=false;
					try{
						fileline = br.readLine();
					}catch(Exception e2){}
					if(fileline==null){
						bLastLine=true;
					}
					int iCount=0;
					while(!bLastLine) {		
						fileline=fileline.trim();
						iCount++;	
						ArrayList<String> onenode = new ArrayList<String>(); 
						onenode.add("1"); //0
			        	onenode.add("1"); //1
			        	onenode.add("a"); //2		        	
			        	onenode.add("");//3
			        	onenode.add("url.txt");//4
			        	nodeHref = fileline; if(nodeHref!=null){nodeHref=nodeHref.replace('\'', '&');} else { nodeHref=""; } 
			        	if(nodeHref.contains(".html")){
			        		nodeHref=nodeHref.replaceAll(DebugServer+"/web/"+ParentNodePath+".html", WebURL);
			        	}else{
			        		nodeHref=nodeHref.replaceAll(DebugServer+"/web/", WebURL);
			        	}
			        	nodeHref=nodeHref.replaceAll(DebugServer, WebURL);					        	
			        	onenode.add(nodeHref);//5
			        	onenode.add("");//6
			        	onenode.add("");//7
			        	onenode.add("FromFile");//8
			        	onenode.add("");//9
			        	onenode.add("");//10
			        	onenode.add("");//11
			    		onenode.add("line_"+iCount);//12		
			    		iIndex1=nodeHref.lastIndexOf("/");
			    		iIndex2=nodeHref.lastIndexOf(".");
			    		if(iIndex1>0){
			    			nodeText=nodeHref.substring(iIndex1+1);
//			    			if(iIndex2>0){
//			    				if(iIndex2>iIndex1){
//			    					nodeText=nodeHref.substring(iIndex1+1,iIndex2);
//			    				}else{
//			    					nodeText=nodeHref.substring(iIndex1+1);
//			    				}
//			    			}else{
//			    				nodeText=nodeHref.substring(iIndex1+1);
//			    			}
			    		}else{
			    			nodeText=nodeHref;
			    		}
			    		if(nodeText.isEmpty()){
			    			nodeText="main";
			    		}
			    		onenode.add(nodeText);//13
			        	onenode.add("");//14		        	
			        	//add onenode to allNodes    
			        	allNodes.add((ArrayList<String>)onenode.clone());						
						
						try{
							fileline = br.readLine();
						}catch(Exception e2){
							System.out.println("[Error:]"+e2.getMessage());
						}
						if(fileline==null){
							bLastLine=true;
						}else{
							bLastLine=false;
						}				
					}//end while				
					if(bFileRead){
						try{
							br.close();
						}catch(Exception e3){}
					}
					RunDB.addLog(ProjectID,DBPosition,"000",1, "[Info:] Get "+iCount+" URLs");
				}
			}
			int iMyPathSeq=1;
			
			double timer_begin2 = System.currentTimeMillis()/1000.000;
			ArrayList<String> getNode = new ArrayList<String>(); 
	        for(int j=0;j<allNodes.size();j++){
	        	/* All changed unique variables should be initialized */
	        	sql="";	uniqueObject=""; iLevel1NodePath = 0; MyDOMType=DOMType;TagOrURL="Tag";
	        	bSpecial = false; MenuHref=""; special=0;branch=0;leaf=0;hasChild=0;mainFeature=0;
	        	popup=0;newtab=0;dropdown=0;doubleclick=0;mouseright=0;mouseover=0;tips=0;frame=0;iframe=0;
	        	mainMenuFilter=0;includerFilter=0;excluderFilter=0;noLessLeafFilter=0;noMoreUniqueFilter=0;forceSetBranchFilter=0;
	        	uniqueObject="";NodePath="";RootURLNodePath=ParentRootURLNodePath;
	        	
	        	seq=j+1;
	        	
	        	getNode = allNodes.get(j);
	        	
	        	try{ 
	        		visible=Integer.parseInt(getNode.get(0));
	        	}catch(Exception ev){
	        		visible=0;
	        	} 
	        	if(visible==1){bVisible=true;}else{bVisible=false;}
	        	try{ 
	        		clickable=Integer.parseInt(getNode.get(1));
	        	}catch(Exception ec){
	        		clickable=0;
	        	}
	        	if(clickable==1){bClickable=true;}else{bClickable=false;}	        	
	        	tagName=getNode.get(2); 	     
	        	nodeTarget=getNode.get(3);
	        	nodeId=getNode.get(4);
	        	nodeHref=getNode.get(5);
	        	nodeName=getNode.get(6);
	        	nodeValue=getNode.get(7);
	        	nodeClass=getNode.get(8);
	        	nodeType=getNode.get(9);
	        	onkeydown=getNode.get(10);
	        	onclick=getNode.get(11);
	        	nodeTitle=getNode.get(12);
	        	nodeText=getNode.get(13);
	        	nodeSrc=getNode.get(14);	
	        	nodeNgclick=getNode.get(15);	
	        	//System.out.println("visible="+visible+",clickable="+clickable+",tagName="+tagName+",nodeTarget="+nodeTarget+",nodeId="+nodeId+",nodeHref="+nodeHref+",nodeName="+nodeName+",nodeValue="+nodeValue+",nodeClass="+nodeClass+",nodeType="+nodeType+",onkeydown="+onkeydown+",nodeTitle="+nodeTitle+",nodeText="+nodeText+",nodeSrc="+nodeSrc);
	        	try{
		        	if(LevelID==1&&ProductType.contains("MenuURL")&&tagName.equals("a")){
		        		TagOrURL="URL";
		        		MenuHref=nodeHref; 
		        		
		        		iLevel1NodePath=RunDB.getLevel1NodeNum(ProjectID,DBPosition)+1;
		        		if(iLevel1NodePath<10){
		        			NodePath="00"+String.valueOf(iLevel1NodePath);
		        		}else{
		        			if(iLevel1NodePath<100){
		        				NodePath="0"+String.valueOf(iLevel1NodePath);
		        			}else{
		        				NodePath=""+String.valueOf(iLevel1NodePath);
		        			}
		        		}
		        		RootURLNodePath = ""; 
		        		ParentSeq=0;
		        		
		        		bIdentify=true; bURLtoTag=false;		        		
		        		if(customlibrary.ExtensionSupport.setMouseover(ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText)){
		        			if(tagName.equals("a")&&nodeHref.contains("http")){ 	
			        			if(nodeHref.length()==(nodeHref.lastIndexOf("#")+1)){
			        				bIdentify=false; bURLtoTag =true;
			        				logInfo="[URL#:] Element seq "+seq+" may not be passed due to last symbol #. Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
			        			}else{
			        				bIdentify=true; //pure valid URL, can be invisible
			        			}
			        		}
		        		}	   
		        		if(ProductName.equals("ControlMinder")){
		        			bIdentify=true;		
		        		}
		        		bIdentify=true;
		        		if(bIdentify){		        			
			        		if(MenuFilter.mainMenuFilter(ProductName,tagName,nodeClass,nodeHref)){ 
				        		mainMenuFilter=1;  
				        		if(MenuFilter.mainMenuIncluder(ProductName,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText)){
				        			includerFilter=1; 
				        			if(MenuFilter.mainMenuExcluder(ProductName,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeSrc,nodeTarget,nodeNgclick)){
				        				excluderFilter=1;
				        				if(MenuFilter.mainFeatureFilter(ProductName, tagName, nodeHref, nodeClass,nodeTarget)){
						        			mainFeature=1; 
						        			special=1; bSpecial=true;
							        		visible=1; clickable=1; //force to set as 1
							        		branch=1; hasChild=1; 
							        		uniqueObject=ProjectID+"_unique_"+nodeHref; // to be unique URL, there is a bug for Thread since NodePath may be still same.
							        		RootURLNodePath=NodePath;
							        		Feature=productInfo.getFeature(ProductName, nodeHref, nodeTitle, nodeText); 	
							        		if(!Feature.equals("")){
							        			Feature=NodePath+"-"+Feature;
							        			Feature=Feature.replace(':', '_');
							        			Feature=Feature.replace(' ', '_');
							        		}		
							        		
							        		PageFilter=DOMFilter.setPageFilter(ParentNodePath,NodePath,LevelID,DBPosition,ProjectID,ProductName,ParentFilter,seq,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
							        		PageFilter=PageFilter.replace('\'', '&');
					        				//Below is added at 07/31
					        				if(!PageFilter.equals(ParentFilter) || !PageFilter.equals(customlibrary.FilterSetting.setProductFilter(ProductName))){
					        					branch=1; hasChild=1;  
					        				}
					        				PageFilter=PageFilter.replace('\'', '&');
					        				if(PageFilter.equals("//skip[@not_special]")){
					        					branch=0; hasChild=0; special=0; bSpecial=false;
					        				}
					        				leaf=customlibrary.ExtensionSupport.setLeaf(ProjectID,DBPosition,ProductName,LevelID,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeNgclick);
					        				//System.out.println("leaf="+leaf);
					        				if(leaf==0){			        					
					        					branch=DOMFilter.setBranch(ProductName,tagName,ParentFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
					        					forceSetBranchFilter=branch;
					        				}else{
					        					noLessLeafFilter=1;
					        					branch=0; hasChild=0; //important for if(!PageFilter....){ branch=1; hasChild=1;}
					        				}			        				
					        				uniqueObject=DOMFilter.setUnique(bURLtoTag,ProjectID,LevelID,ProductName,ParentFilter,ParentNodePath,NodePath,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeSrc);			        				
					        				if(uniqueObject.contains("_unique_")){ //no loop no duplication
					        					noMoreUniqueFilter=1;
					        					if(leaf==0){
					        						// may branch, since it's unique object, so it should be real branch.
					        						// vector is also set as uniqueObject, so it will not be checked for vector.
					        						branch=1; hasChild=1; 
					        					}
					        				}
					        				
					        				if(bURLtoTag){
					        					System.out.println("[Debug:] URL is changed to Tag for "+uniqueObject);
					        					RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,"[Debug:] URL is changed to Tag for seq="+seq+","+uniqueObject);
					        					TagOrURL="Tag";	
					        				}else{
					        					//System.out.println("uniqueObject="+uniqueObject);
						        				if(uniqueObject.contains("http")){ //debug	
						        					if(ProductName.equals("CASM")&&LevelID>1){
						        						TagOrURL="Tag";	
						        					}else{
						        						TagOrURL="URL";	
						        						nodeHref=nodeHref.replace('\'', '&');
						        						MenuHref=nodeHref; 
						        					}
						    		        		branch=1; hasChild=1; leaf=0;
						        				}
					        				}
					        				popup=customlibrary.ExtensionSupport.setPopup(ProjectID,DBPosition,ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
					        				if(popup==1){
					        					MyDOMType="popup"; //It's not possible to be frame and iFrame
					        				}
					        				
					        				logInfo="[Success:] Element seq "+seq+" is passed. It will be valided for unique. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
					        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
					        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);	
						        		}else{		
						        			logInfo="[Discard:] Element seq "+seq+" is not passed by **mainFeatureFilter**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
					        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
					        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						        		}
					        		}else{
					        			logInfo="[Discard:] Element seq "+seq+" is not passed by **mainMenuExcluder**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
				        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
				        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
					        		}
					        	}else{	  
					        		uniqueObject=ProjectID+"_"+NodePath; 
					        		special=0; bSpecial = false; branch=0; hasChild=0;
					        		logInfo="[Discard:] Element seq "+seq+" is not passed by **mainMenuIncluder**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);	
					        	}
			        		}else{
			        			logInfo="[Discard:] Element seq "+seq+" is not passed by **mainMenuFilter**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
		        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
		        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);		        				
			        		}
		        		}else{
		        			logInfo="[Discard:] Element seq "+seq+" is not passed by **identify**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
	        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
	        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
		        		}
		        	}else{
		        		Feature=ParentFeature;
		        		TagOrURL="Tag";
		        		if(LevelID==1){
		        			iLevel1NodePath=seq;
			        		if(iLevel1NodePath<10){
			        			NodePath="00"+String.valueOf(iLevel1NodePath);
			        		}else{
			        			if(iLevel1NodePath<100){
			        				NodePath="0"+String.valueOf(iLevel1NodePath);
			        			}else{
			        				NodePath=""+String.valueOf(iLevel1NodePath);
			        			}
			        		}
		        		}else{
		        			NodePath=ParentNodePath+"-"+seq;
		        		}
		        		bIdentify=false; bURLtoTag=false;
		        		if(customlibrary.ExtensionSupport.setMouseover(ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText)){
		        			if(tagName.equals("a")&&nodeHref.contains("http")){ 	
			        			if(nodeHref.length()==(nodeHref.lastIndexOf("#")+1)){
			        				bIdentify=false; bURLtoTag =true;
			        				logInfo="[URL#:] Element seq "+seq+" may not be passed due to last symbol #. Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
			        			}else{
			        				bIdentify=true; //pure valid URL, can be invisible
			        			}
			        		}
		        		}		        
		        		if(ProductType.contains("URLToTag")){
		        			bURLtoTag =true;
		        		}
		        		if(bVisible&&bClickable){
		        			bIdentify=true;
		        		}else{
		        			if(!bIdentify){
			        			if(!bVisible){
//			        				if(bDebugResumeXPATH){
			        				logInfo="[Discard:] Element seq "+seq+" is not passed by **non-visible**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);				        				
//			        				}
			        			}
			        			if(!bClickable){
//			        				if(bDebugResumeXPATH){
			        				logInfo="[Discard:] Element seq "+seq+" is not passed by **non-clickable**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);				        				
//			        				}
			        			}
		        			}
		        		}  		
		        		if(bIdentify){
			        		if(DOMFilter.setIncluder(ParentNodePath,NodePath,seq,ProjectID,DBPosition,ProductName,tagName,ParentFilter,seq,LevelID,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText)){
			        			includerFilter=1; //System.out.println("[Info:] pass include seq="+seq);
			        			if(customlibrary.FilterSetting.setExcluder(ParentNodePath,NodePath,seq,ProjectID,DBPosition,ProductName,tagName,ParentFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeSrc,nodeTarget,nodeNgclick)){
			        				excluderFilter=1; 
			        				//System.out.println("[Info:] pass exclude nodeHref="+nodeHref); 
			        				special=1; bSpecial=true;
			        				if(ProductName.equals("CASM")&&LevelID>1){
		        						TagOrURL="Tag";	
		        						nodeHref="#";
	        		        			MenuHref="#";
		        					}
			        				PageFilter=DOMFilter.setPageFilter(ParentNodePath,NodePath,LevelID,DBPosition,ProjectID,ProductName,ParentFilter,seq,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        				if(!PageFilter.equals(ParentFilter) || !PageFilter.equals(customlibrary.FilterSetting.setProductFilter(ProductName))){
			        					branch=1; hasChild=1;  
			        				}
			        				PageFilter=PageFilter.replace('\'', '&');
			        				if(PageFilter.equals("//skip[@not_special]")){
			        					branch=0; hasChild=0; special=0; bSpecial=false;
			        				}
			        				leaf=customlibrary.ExtensionSupport.setLeaf(ProjectID,DBPosition,ProductName,LevelID,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeNgclick);
			        				//System.out.println("leaf="+leaf);
			        				if(leaf==0){			        					
			        					branch=DOMFilter.setBranch(ProductName,tagName,ParentFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        					forceSetBranchFilter=branch;
			        				}		
			        				if(PageFilter.equals("//leaf")){
			        					leaf=1;
			        				}
			        				if(leaf==1){
			        					noLessLeafFilter=1;
			        					branch=0; hasChild=0; //important for if(!PageFilter....){ branch=1; hasChild=1;}
			        				} 
			        				uniqueObject=DOMFilter.setUnique(bURLtoTag,ProjectID,LevelID,ProductName,ParentFilter,ParentNodePath,NodePath,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeSrc);			        				
			        				if(uniqueObject.contains("_unique_")){ //no loop no duplication
			        					noMoreUniqueFilter=1;
			        					if(leaf==0){
			        						// may branch, since it's unique object, so it should be real branch.
			        						// vector is also set as uniqueObject, so it will not be checked for vector.
			        						branch=1; hasChild=1; 
			        					}
			        				}
			        				if(bURLtoTag){
			        					System.out.println("[Debug:] URL is changed to Tag for "+uniqueObject);
			        					RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,"[Debug:] URL is changed to Tag for seq="+seq+","+uniqueObject);
			        					TagOrURL="Tag";	
			        				}else{
			        					//System.out.println("uniqueObject="+uniqueObject);
				        				if(uniqueObject.contains("http")){ //debug	
				        					if(ProductName.equals("CASM")&&LevelID>1){
				        						TagOrURL="Tag";	
				        						nodeHref="#";
			        		        			MenuHref="#";
				        					}else{				        						
				        						TagOrURL="URL";	
				        						nodeHref=nodeHref.replace('\'', '&');
				        						RootURLNodePath=NodePath; //changed 
				        						MenuHref=nodeHref; 
				        					}
				    		        		branch=1; hasChild=1; leaf=0;
				        				}
			        				}
			        				popup=customlibrary.ExtensionSupport.setPopup(ProjectID,DBPosition,ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        				if(popup==1){
			        					MyDOMType="popup"; 
			        				}
			        				newtab=customlibrary.ExtensionSupport.setNewtab(ProjectID,DBPosition,ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        				if(newtab==1){
			        					MyDOMType="newtab"; 
			        				}
			        				mouseright=customlibrary.ExtensionSupport.setMouseright(ProjectID,DBPosition,ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        				if(mouseright==1){
			        					MyDOMType="mouseright"; 
			        				}
			        				frame=customlibrary.ExtensionSupport.setFrame(ProjectID,DBPosition,ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        				if(frame==1){
			        					MyDOMType="frame"; 
			        				}
			        				iframe=customlibrary.ExtensionSupport.setiFrame(ProjectID,DBPosition,ProductName,tagName,ParentFilter,PageFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
			        				if(iframe==1){
			        					MyDOMType="iframe"; 
			        				}			        				
			        				logInfo="[Success:] Element seq "+seq+" is passed by filter. It will be valided for unique.Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);				        				
			        			}else{	
			        				//if(bDebugResumeXPATH){
			        				logInfo="[Discard:] Element seq "+seq+" is not passed by **excluder**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
			        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
			        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);	
			        				//}
			        				//nomeaning ?
			        				uniqueObject=ProjectID+"_"+NodePath; 
					        		special=0; bSpecial = false;
					        	}
				        	}else{	   
				        		//if(bDebugResumeXPATH){
				        		logInfo="[Discard:] Element seq "+seq+" is not passed by **includer**. Tag="+tagName+",Title="+nodeTitle+",Text="+nodeText+",Class="+nodeClass+",href="+nodeHref;
		        				if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
		        				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);			        				
				        		//}
				        		//nomeaning ?
				        		uniqueObject=ProjectID+"_"+NodePath; 
				        		special=0; bSpecial = false;
				        	}
		        		}else{
		        			uniqueObject=ProjectID+"_"+NodePath; 
		        			special=0; bSpecial = false;
		        		}
		        	}
		        	//if(bSpecial){
		        		if(uniqueObject.length()>250){
		        			uniqueObject=uniqueObject.substring(0,250);
				        }
		        		ParentFilter=ParentFilter.replace('\'', '&');
		        		Feature="";	
		        		
		        		if(LevelID==1){
		        			//Set it as branch forcely for menufilter
	        				branch=1; hasChild=1; leaf=0;		
		        		}		
		        		
		        		int attributeFP=0;
		        		String getResult="";
		        		FilterSetting fs = new FilterSetting();
		        		getResult=fs.setUniqueObjectAndFingerPrintAttribute(ParentNodePath,NodePath,seq,ProjectID,DBPosition,ProductName,tagName,ParentFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeSrc,nodeTarget,nodeNgclick,TagOrURL);
		        		
		        		String sAttributeFP=getResult.charAt(0)+"";
		        		String getUniqueObject=getResult.substring(1);
		        		if(!getUniqueObject.equals("NONE")){
		        			//uniqueObject=getUniqueObject;
		        			uniqueObject=ProjectID+"_"+getUniqueObject;
		        		}
		        		if(uniqueObject.length()>250){
		        			uniqueObject=uniqueObject.substring(0,250);
				        }
		        		try{
		        			attributeFP=Integer.parseInt(sAttributeFP);
		        		}catch(Exception exi){
		        			attributeFP=1;
		        		}
			        	sql="insert into NodeList set ";
			    		sql+="ProjectID='"+ProjectID+"'";
			    		sql+=",ProjectName='"+ProductName+"'";
			    		sql+=",ParentFilter='"+ParentFilter+"'";
			    		sql+=",PageFilter='"+PageFilter+"'";
			    		sql+=",DOMType='"+MyDOMType+"'";
			    		sql+=",Feature='"+Feature+"'";
			    		sql+=",TagOrURL='"+TagOrURL+"'";
			    		sql+=",LevelID="+LevelID;
			    		sql+=",ParentSeq="+ParentSeq;
			    		sql+=",NodePath='"+NodePath+"'";
			    		sql+=",RootURLNodePath='"+RootURLNodePath+"'";
			    		sql+=",seq="+seq;	  
			    		sql+=",MenuHref='"+MenuHref+"'";
			    		sql+=",mainFeature="+mainFeature;
			    		sql+=",branch="+branch;
			    		sql+=",hasChild="+hasChild;
			    		sql+=",leaf="+leaf;
			    		//sql+=",popup="+popup;
			    		sql+=",mainMenuFilter="+mainMenuFilter;
			    		sql+=",includerFilter="+includerFilter;
			    		sql+=",excluderFilter="+excluderFilter;
			    		sql+=",noLessLeafFilter="+noLessLeafFilter;
			    		sql+=",forceSetBranchFilter="+forceSetBranchFilter;
			    		sql+=",noMoreUniqueFilter="+noMoreUniqueFilter;
			    		sql+=",special="+special;
			    		sql+=",visible="+visible;
			    		sql+=",clickable="+clickable;
			    		if(special==0){
			    			uniqueObject=ProjectID+"_"+NodePath;
			    		}
			    		sql+=",vector='"+uniqueObject+"'";
			    		sql+=",uniqueObject='"+uniqueObject+"'";
			    		sql+=",tagName='"+tagName+"'";
			    		sql+=",nodeId='"+nodeId+"'";
			    		sql+=",nodeHref='"+nodeHref+"'";
			    		sql+=",nodeName='"+nodeName+"'";
			    		sql+=",nodeValue='"+nodeValue+"'";
			    		sql+=",nodeClass='"+nodeClass+"'";
			    		sql+=",nodeType='"+nodeType+"'";
			    		sql+=",onkeydown='"+onkeydown+"'";
			    		sql+=",onclick='"+onclick+"'";
			    		sql+=",nodeTitle='"+nodeTitle+"'";
			    		sql+=",nodeText='"+nodeText+"'"; 
			    		sql+=",attributeFP="+attributeFP;			    		
			    		//System.out.println("[Info:] sql "+sql);
			    		boolean bUnique = false;
			    		bValidNode=true;
			    		
			    		if(nodeType.equals("radio")){
			    			iRadio++;
			    		}
			    		if(iRadio==1){
			    			bValidNode=false;
			    		}
			    		if(tagName.equals("input")&&nodeType.equals("text")){
			    			bValidNode=false;
		    			}
			    		if(tagName.equals("input")&&nodeType.equals("password")){
			    			bValidNode=false;
		    			}
			    		if(ParentTagName.equals("option")&&tagName.equals("option")){
			    			bValidNode=false;
			    		}
			    		if(ParentTagName.equals("option")&&nodeType.equals("radio")){
			    			bValidNode=false;
			    		}
			    		if(ParentNodeType.equals("radio")&&nodeType.equals("radio")){
			    			bValidNode=false;
			    		}			    		
			    		if(bValidNode){
			    			ManageDB m11 = new ManageDB();
			    			m11.getConnection(DBPosition); 
			    			bUnique=m11.updateUniqueSQL(seq,ProjectID,ParentNodePath,sql,bDebugResumeXPATH); 
			    			m11.close();
			    		}
		    			if(bUnique){
		    				if(LevelID==1){		    					
		    					if(nodeClass.equals("FromFile")){
		    						iMyPathSeq++;		    						
		    						sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
		    						sql=sql+",tagName='a'";
		    						sql=sql+",Attribute='nodeHref'";
		    						sql=sql+",AttributeCondition='contains'";
		    						sql=sql+",AttributeValue='"+nodeText+"'";
		    						sql=sql+",ParentName='-1'";
		    						sql=sql+",LevelID=1";
		    						sql=sql+",PathSeq="+iMyPathSeq;
		    						sql=sql+",XpathPath='"+iMyPathSeq+"'";
		    						sql=sql+",XPATH='//a|//input'";
		    						sql=sql+",ParentSeq=''";
		    						sql=sql+",DOMType='-1'";
		    						sql=sql+",XPATHName='"+nodeText+"'";
		    						//m1.updateSQL(sql);
		    					}
		    				}
		    				if(bSpecial){
		    					iSpecialCount++;
		    				}
		    				if(iSpecialCount==1){
			    				childList=""+seq;
			    			}else{
			    				childList=childList+","+seq;
			    			}
		    			}else{
		    				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,"[Debug:] Discard seq=" + seq + ",uniqueObject="+uniqueObject);
		    			}
		        	//}
	        	}catch(Exception ec){
	        		logInfo="[Alert:] For ArrayList, ParentNodePath "+ParentNodePath+" gets an error message="+ec.getMessage();
	        		logInfo=logInfo.replace('\'', '&');
	        		if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
    				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,logInfo);
    				System.out.println(logInfo);
    				
    				bExtractSuccess = false;
	        		sExtractResult="fail_element";
	        		//cannot execute RunDB since m1 is not closed now.
		    		RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,"[Alert:] For ArrayList, missing child "+seq+" of "+ParentNodePath);
	        		break; //does not take effect. Why?
	    		} 
	        } //for end	       
	        double timer_end2 = System.currentTimeMillis()/1000.000;
			double time_spent2 = timer_end2 - timer_begin2;	
			RunDB.addLog(ProjectID,DBPosition,ParentNodePath,1,"[Info:] DB filter time spent: " + String.valueOf(time_spent2).substring(0,3) + " seconds");	
		}catch(Exception ex){   
	    	bExtractSuccess = false;
	    	sExtractResult="fail_unknown"; //not easy to happen
	    	RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,"[Alert:] Unknown failure, WebElement or DB Exception. "+ParentNodePath+" Extracting failed. It can not get attributes from WebElement or update DB error."+" RunDB.java, method=extractDB");
	    	System.out.println("[Alert:] ParentNodePath "+ParentNodePath+" gets an error message="+ex.getMessage());  
	    } 
		 //end of for and try 
		if(bExtractSuccess){
			sExtractResult="extract_success";
			sExtractResult="success_unique"; //remove if vector take effective
	        if(LevelID>1){ //set touchonce for parent nodes whose levelid is from 1 	        	
	        	ManageDB m2 = new ManageDB();
	        	m2.getConnection(DBPosition);
	        	int iChildNum = 0;
	        	sql="select count(NodePath) from NodeList where ProjectID='"+ProjectID+"' and NodePath like '"+ParentNodePath+"-%'";	        	
	    		try{
		        	ResultSet rs = m2.getSQL(sql);
		    		if(rs.next()){
		    			iChildNum=rs.getInt(1);
		    		}
		    		rs.close();
	    		}catch(Exception ers){}	   
	    		if(iChildNum==iSpecialCount){
	    			bAllChildsInFamilily = true;
	    		}
	    		//if(bAllChildsInFamilily){
		        	if(iSpecialCount==0){ //set for Parent NodePath branch=0/1,hasChild=0/1,childNum=xx
			        	sql="update NodeList set touchonce=1,branch=0,hasChild=0,extractfail=0,childNum=0,childList='' where ProjectID='"+ProjectID+"' and NodePath ='"+ParentNodePath+"'";
			        }
			        else{
			        	sql="update NodeList set touchonce=1,branch=1,hasChild=1,extractfail=0,childNum="+iSpecialCount+",childList='"+childList+"' where ProjectID='"+ProjectID+"' and NodePath ='"+ParentNodePath+"'";
			        }
			        m2.updateSQL(sql);
	    		//}
		        m2.close();
		        
		        sExtractResult="extract_success";
	        }	    
	        
	        //compare Parent and child's childnum and childlist, if same, remove tree 
	        if(LevelID==1){	      
	        	System.out.println("[Info:] **** Extract successfully. Add "+ iSpecialCount +" valid nodes for extracted node path "+ParentNodePath+"(which has "+iTotal+" child nodes totally)");
	        	RunDB.addLog(ProjectID,DBPosition,ParentNodePath,1,"[Info:] Extract successfully.  Add "+ iSpecialCount +" valid nodes for extracted node path "+ParentNodePath+"(which has "+iTotal+" child nodes totally)");
	        }else{
		        //if(bAllChildsInFamilily){
		        	System.out.println("[Info:] **** Extract successfully. Add "+ iSpecialCount +" valid nodes for extracted node path "+ParentNodePath+"(which has "+iTotal+" child nodes totally)");
		        	RunDB.addLog(ProjectID,DBPosition,ParentNodePath,1,"[Info:] **** Extract successfully. Add "+ iSpecialCount +" valid nodes for extracted node path "+ParentNodePath+"(which has "+iTotal+" child nodes totally)");
		        //}
	        }
	    }
		else
	    {
			if(sExtractResult.equals("fail_element")){
				RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,"[Alert:] **** Element is out of control for "+ParentNodePath);
	    		RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,"[Alert:] **** Missing child of "+ParentNodePath);
	    		EmailContact=loginStart.readXML("EmailContact");
	    		if(EmailContact==null){EmailContact="miaxi01@ca.com";}
	    		//SendMail.send("Extract alert", ProjectID+"-"+ProductName+": "+ParentNodePath+" may be hidden or its child element is out of control", EmailContact,"");
	    		//RunDB.setLeaf(ProjectID, DBPosition, ParentNodePath); //does not extract again
			}
			RunDB.deleteChildTree(ProjectID,DBPosition, ParentNodePath); //delete extracted child tree and set touchonce=0 for ParentNodePath
			RunDB.setExtractFail(ProjectID,DBPosition, ParentNodePath); //for monitoring and debugging
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] **** Set ExtractFail. Cannot extract for node "+ParentNodePath+".");
			/*					  * 
			  * set clickfail=1 /click times +1 , to avoid recycle for ever
			  * 
			  */
			RunDB.addLog(ProjectID,DBPosition,ParentNodePath,2,"[Alert:] **** Extract failure, set ClickFail/ClickTimes for "+ParentNodePath);
			RunDB.setClickFail(ProjectID,DBPosition,ParentNodePath);//for the node, set clickfail=1
			RunDB.setClickTimes(ProjectID,DBPosition,ParentNodePath); //may be new feature, so click times +1
			
		}
		return sExtractResult;
	}
}

