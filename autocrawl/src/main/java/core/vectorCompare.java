package core;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import misc.toolbox;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import db.ManageDB;
import db.RunDB;




public class vectorCompare
{
	ArrayList<ArrayList<Integer>> pvList = new ArrayList<ArrayList<Integer>>();
	public void add(WebDriver driver, ArrayList<Integer> pv)
	{
		pvList.add(pv);
	}
	public boolean isDuplicate(ArrayList<Integer> pv)
	{
		boolean dup = false;;
		for(int i=0;i<pvList.size();i++)
		{
			if(pv.equals(pvList.get(i)))
			{
				dup = true;
				System.out.println("[INFO-Pages]: Page is duplciate with page #[" + (i+1) + "]");
				break;
			}
		}
		return dup;
	}
	public void updateFile()
	{
		System.out.println("[Info:] update file.......");
		String NodePath="009";String KeyID="02A";
		String fileSnapShot="./web/"+NodePath+".html";
		String fileline="";String FullPageSource="";
		int iFound=0; int iStart=0; int iEnd=0;
		BufferedReader br =null; boolean bFileRead=false;
		try{
			br = new BufferedReader (new FileReader(fileSnapShot));
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
		while(!bLastLine) {			
			iFound=fileline.indexOf(KeyID);
			if(iFound!=-1){
				fileline="<font color='red'><B>Found here!"+"</B></font>"+fileline;
			}
			
			FullPageSource=FullPageSource+fileline+"\n";;
			try{
				fileline = br.readLine();
			}catch(Exception e2){}
			if(fileline==null){
				bLastLine=true;
			}
		}
		String FileName=NodePath+"_1.html";
		String FolderFileName="./web/"+FileName;
		try{  
			File webfile = new File(FolderFileName);
		    if(webfile.exists()){
		    	System.out.println("[Info:] Detect old file "+FileName+", delete it now.");
		    	webfile.delete();
		    }
			//http://jaczhao.iteye.com/blog/1616716
	        FileWriter writer = new FileWriter(FolderFileName, true);  
	        writer.write(FullPageSource);  
	        writer.close();
	    }catch(IOException e){  
	        //e.printStackTrace();  
	    }
	}
	public String getStrVector(WebDriver driver,String DOMType,String NodePath,String ProjectID,String DBPosition,String ProductName,int iFindElementTime,boolean bNewFeature)
	{
		if(false){
			List<WebElement> allElements=null; WebElement e=null; String target="";
			String mainHandle = driver.getWindowHandle();
			String[] handles = driver.getWindowHandles().toArray(new String[0]);
			System.out.println("handles.length="+handles.length);
//			driver.switchTo().window(handles[handles.length - 1]);
			driver.switchTo().window(handles[1]);
			try{
				toolbox.captureScrn("./screen/", "test.png", driver);
			}catch(Exception ecp){}
			toolbox.waitSecond(5);
			String PageFilter="//span[@class='x-tab-strip-inner']/span[@class='x-tab-strip-text  ']";
			allElements = driver.findElements(By.xpath(PageFilter));
			int getSeq=0;
			for (WebElement oneNode : allElements) {  
				getSeq++;
//			for(int getSeq=1;getSeq<4;getSeq++){
//				target="("+PageFilter+")["+getSeq+"]";
//				e = driver.findElement(By.xpath(target));
//				e.click();
				oneNode.click();
				toolbox.waitSecond(5);
				try{
					toolbox.captureScrn("./screen/", getSeq+"-popup.png", driver);
				}catch(Exception ecp){}
				
			}
			toolbox.waitSecond(5);
			driver.close(); //close the popup window
			toolbox.waitSecond(5);
			driver.switchTo().window(mainHandle);
		}
		if(false){
		Set<String> windows = driver.getWindowHandles();
		if(windows.size() > 1)
		{
			Iterator<String> id = windows.iterator();
			String old_window = id.next();
			WebDriver popup = null ;
			
			if(id.hasNext()){
				RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] A popup window is found for "+NodePath);				
				System.out.println("[Info:] A popup window is found for "+NodePath);						
//				String screenFolder = "./screen/";
				try{
					popup = driver.switchTo().window(id.next());					
//					try{
//						toolbox.captureScrn(screenFolder, "test.png", driver);
//					}catch(Exception eg){}
					RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Close the popup window for "+NodePath);	
				    System.out.println("[Info:] Close the popup window for "+NodePath);	
				    popup.close();
				}
				catch(UnreachableBrowserException ex)
				{
					 System.out.println("[ERROR-POPUP]: UnreachableBrowserException: Close the popup window.\n"); 
					 popup.close();
				}
			 }
			 System.out.println("[ACTION-POPUP]: Go back to parent window!\n");	
			 driver.switchTo().window(old_window);	
		}
		}
		String pv="";String vectorText;
		try{
			int i=0;
			int sum = 0;
			String nodeText;
			List<WebElement> count = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
//			if(DOMType.equals("frame")) {
//				
//			}
			try{
				count = driver.findElements(By.cssSelector("body *")); 	
				//pv=String.valueOf(driver.findElements(By.cssSelector("body *")).size()); //xpath="//body//*"; Child or subchild
				sum = count.size();
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrVector, can not run findElements for "+ NodePath);
//				System.out.println("[Error:] getStrVector failure. "+ e.getMessage());
				 //how to do ? , also for findChild, screen capture	 
			 }
			if(sum==0){ 
				/*  
				 * 
				 Not necessary since driver.switchTo().frame should be executed in concurrentPath previously.
				 * 
				 */
				if(DOMType.equals("frame")){ 
					String frameXPATH="//frame[2]";	
					switch(ProductName){
			    		case "ServiceCatalog": 
			    			frameXPATH="//frame[2]";	
			    		break;
					}
					try{
						driver.switchTo().frame(driver.findElement(By.xpath(frameXPATH)));		//Duplication switch, it has been executed 	in concurrentPath			
						count = driver.findElements(By.cssSelector("body *")); 	
						sum = count.size();
					}catch(Exception eae){
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] driver.switchTo().frame(driver.findElement failure. Driver is out of control, or can not run findElements for "+ NodePath);
					}
				}
			}
			pv=String.valueOf(sum);
			 
			if(bNewFeature){//bNewFeature for input value, etc.
				vectorText=ProjectID;
				double timer_begin_pv = System.currentTimeMillis()/1000.000;
				for (WebElement e : count) {  
					nodeText= e.getText();if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
					vectorText=vectorText+nodeText;
				}
				double timer_end_pv = System.currentTimeMillis()/1000.000;
				double time_spent_pv = timer_end_pv - timer_begin_pv;		
				System.out.println("[Info:] Get vectorText time spent: " + time_spent_pv + " seconds"); //330.6710000038147 seconds ->Multiple threads
				
				String sql="update NodeList set vectorText='"+vectorText+"' where ";
				sql+=" ProjectID='"+ProjectID+"' ";      			
				sql+=" and NodePath='"+NodePath+"'";
				try{
					ManageDB m5=new ManageDB();
					m5.getConnection(DBPosition);
					m5.updateSQL(sql);   
					m5.close();
				 }catch(Exception ex){
					 RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Check Vector.getStr or DB connection");
//					 System.out.println("[DB info:] Check Vector.getStr or DB connection");
				 }
			}
			if(sum>0){ //driver is normal
				String xml_level = "body > *"; //xpath="//body/*"; Direct child
				for (i = 1; i < 200; i++) 
				{
					sum = 0;
					//utility.waitMin(iFindElementTime); 
					//if(i>50&&i<150){ 
						count = driver.findElements(By.cssSelector(xml_level));
						
						sum = count.size();
						if (sum == 0) 
						{				
							break;
						}
						pv+=String.valueOf(count.size());
					//}
					xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
				}	
			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
		}
		return pv;
	}
	public boolean analysisUIString(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		System.out.println("[Info:] Start to get UI String...");
		String pv="";boolean bDifferentUI = false; 
		boolean bLeafPage = false;
		try{
			int sum = 0;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
				String PageFilter="/html/head/title|//span|//div|//button|//a|//input|//option";
				allElements = driver.findElements(By.xpath(PageFilter)); 
//				allElements = driver.findElements(By.cssSelector("body *")); 	
				sum = allElements.size();
				System.out.println("[Info:] sum = "+sum);
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}			
			String tagName,nodeId,nodeName,nodeValue,nodeTitle,nodeText,display;
			if(sum>0){ 				
				ManageDB mdb = new ManageDB();
				mdb.getConnection(DBPosition);
//				double timer_begin = System.currentTimeMillis()/1000.000;
				int seq=0;
				for (WebElement e : allElements) {  
					seq ++;
//					System.out.println("[Info:] seq = "+seq);
//					tagName = e.getTagName();
//					nodeId = e.getAttribute("id");if(nodeId!=null){nodeId=nodeId.replace('\'', '&');} else { nodeId=""; } 
//					nodeName = e.getAttribute("name");if(nodeName!=null){nodeName=nodeName.replace('\'', '&');} else { nodeName=""; } 
//		        	if(nodeName.equals("null")){nodeName=""; } 
//					nodeValue = e.getAttribute("value");if(nodeValue!=null){nodeValue=nodeValue.replace('\'', '&');} else { nodeValue=""; } 
//		        	if(nodeValue.equals("null")){nodeValue=""; } 		        	
//					nodeTitle = e.getAttribute("title");if(nodeTitle!=null){nodeTitle=nodeTitle.replace('\'', '&');} else { nodeTitle=""; }  
//		        	
					nodeText= e.getText();
					//System.out.println(seq+" = "+nodeText);
					if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
					
					display="";
//		        	display+="  tagName="+tagName;
//		        	display+="  nodeId="+nodeId;
//		        	display+="  nodeValue="+nodeValue;
//		        	display+="  nodeTitle="+nodeTitle;
		        	display+="  nodeText="+nodeText;
		        	
		        	//System.out.println(seq+" TEXT= "+nodeText);
		        	
		        	if(nodeText.contains("Error")){
		        		bLeafPage = true;
		        		//System.out.println(seq+" TEXT");
		        		System.out.println(seq+" Error TEXT= "+nodeText);
		        		//System.out.println(seq+" TEXT= "+nodeText);
		        		//System.out.println("11 nodeText = "+nodeText);
		        	}
					}
			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
		}
		return bLeafPage;
	}
	public boolean getUIContext(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		System.out.println("[Info:] Start to get UIContext...");
		String pv="";boolean bDifferentUI = false;
		try{
			int sum = 0;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
				String PageFilter="/html/head/title|//span|//div|//button|//a|//input|//option";
				allElements = driver.findElements(By.xpath(PageFilter)); 
//				allElements = driver.findElements(By.cssSelector("body *")); 	
				sum = allElements.size();
				System.out.println("[Info:] sum = "+sum);
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}			
			String tagName,nodeId,nodeName,nodeValue,nodeTitle,nodeText,display;
			if(sum>0){ 
				StringTokenizer st1 = null;
				String uiContext = "";String sql=""; boolean bUnique = false; 
				String oneline="";String PLOCString = "";String KeyID="";	int iCounter=0;
				ManageDB mdb = new ManageDB();
				mdb.getConnection(DBPosition);
//				double timer_begin = System.currentTimeMillis()/1000.000;
				int seq=0;
				for (WebElement e : allElements) {  
					seq ++;
					System.out.println("[Info:] seq = "+seq);
					tagName = e.getTagName();
					nodeId = e.getAttribute("id");if(nodeId!=null){nodeId=nodeId.replace('\'', '&');} else { nodeId=""; } 
					nodeName = e.getAttribute("name");if(nodeName!=null){nodeName=nodeName.replace('\'', '&');} else { nodeName=""; } 
		        	if(nodeName.equals("null")){nodeName=""; } 
					nodeValue = e.getAttribute("value");if(nodeValue!=null){nodeValue=nodeValue.replace('\'', '&');} else { nodeValue=""; } 
		        	if(nodeValue.equals("null")){nodeValue=""; } 		        	
					nodeTitle = e.getAttribute("title");if(nodeTitle!=null){nodeTitle=nodeTitle.replace('\'', '&');} else { nodeTitle=""; }  
		        	nodeText= e.getText();if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
		        	display="  tagName="+tagName;
		        	display+="  nodeId="+nodeId;
		        	display+="  nodeValue="+nodeValue;
		        	display+="  nodeTitle="+nodeTitle;
		        	display+="  nodeText="+nodeText;
		        	
		        	oneline=nodeText;
					if(oneline.length()>200){
						 oneline=oneline.substring(0,200);
					}
					nodeText = oneline;
					 if(nodeText.indexOf("]]")!=-1){
						 st1 =new StringTokenizer(nodeText,"]]");
							while(st1.hasMoreTokens()){
								
								PLOCString=st1.nextToken().replace('\n', ' ').trim()+"]]";
								if(PLOCString.length()>8){
									
									KeyID=PLOCString.substring(PLOCString.length()-8);
									sql="insert into UIText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
									bUnique = mdb.updateUnique(sql);
									if(bUnique){
										
										iCounter++;
										System.out.println(iCounter+" : "+KeyID);
										bDifferentUI = true;
									}
								}
//								System.out.println(KeyID);
							}
					 }
		        	

				}
				mdb.close();

			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
		}
		return bDifferentUI;
	}
	public boolean getUIContextNoThread(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		double timer_begin1 = System.currentTimeMillis()/1000.000;
		System.out.println("[Info:] Start to get UIContext...");
		boolean bDifferentUI = false;
		try{
			int sum = 0;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
//				String PageFilter="/html/head/title|//span|//div|//button|//a|//input|//option";
//				allElements = driver.findElements(By.xpath(PageFilter)); 
				allElements = driver.findElements(By.cssSelector("body *")); 	
				sum = allElements.size();
				System.out.println("[Info:] sum = "+sum);
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}			
			String tagName,nodeId,nodeName,nodeValue,nodeTitle,nodeText,display;
			if(sum>0){ 
				String uiContext = "";String sql=""; boolean bUnique = false; 
				String oneline="";String PLOCString = "";String KeyID="";	int iCounter=0;
				ManageDB mdb = new ManageDB();
				mdb.getConnection(DBPosition);
				int seq=0;
				for (WebElement e : allElements) {  
					seq ++;
//					tagName = e.getTagName();
//					nodeId = e.getAttribute("id");if(nodeId!=null){nodeId=nodeId.replace('\'', '&');} else { nodeId=""; } 
//					nodeName = e.getAttribute("name");if(nodeName!=null){nodeName=nodeName.replace('\'', '&');} else { nodeName=""; } 
//		        	if(nodeName.equals("null")){nodeName=""; } 
//					nodeValue = e.getAttribute("value");if(nodeValue!=null){nodeValue=nodeValue.replace('\'', '&');} else { nodeValue=""; } 
//		        	if(nodeValue.equals("null")){nodeValue=""; } 		        	
//					nodeTitle = e.getAttribute("title");if(nodeTitle!=null){nodeTitle=nodeTitle.replace('\'', '&');} else { nodeTitle=""; }  
		        	nodeText= e.getText();if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
		      		        	
		        	oneline=nodeText;
					if(oneline.length()>200){
						 oneline=oneline.substring(0,200);
					}
					nodeText = oneline;
					
//					int iPosition = nodeText.indexOf("]]");
//					if(iPosition!=-1){
//						 iCounter++;
//						 bDifferentUI = true;
//						 KeyID=nodeText.substring(iPosition-6,iPosition+2);
//						 PLOCString =nodeText ;
//						 sql="insert into UIText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
//						 bUnique = mdb.updateUnique(sql);
//						 if(bUnique){
//						 	System.out.println("[Info:] KeyID="+KeyID);
//						 }
//					}
					
					 if(nodeText.indexOf("]]")!=-1){
						 StringTokenizer st1 =new StringTokenizer(nodeText,"]]");
							while(st1.hasMoreTokens()){
								
								PLOCString=st1.nextToken().replace('\n', ' ').trim()+"]]";
								if(PLOCString.length()>8){
									
									KeyID=PLOCString.substring(PLOCString.length()-8);
									sql="insert into UIText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
									bUnique = mdb.updateUnique(sql);
									if(bUnique){
										iCounter++;
										System.out.println(iCounter+" : "+KeyID);
										bDifferentUI = true;
									}
								}
							}
					 }//end if
					 sql="insert into NodeAllText set Content='"+oneline+"'";
					 mdb.updateSQL(sql);
					 System.out.println(oneline);
				}//end for
				sql="update UIText set KeyNumber="+iCounter+" where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateUnique(sql);
				mdb.close();

			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] get getUIContextNoThread time spent: " + time_spent1 + " seconds");	
 		//[Info:] get getUIContextNoThread time spent: 87.50200009346008 seconds
		return bDifferentUI;
	}
	public boolean getUIContextFromFile(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		double timer_begin1 = System.currentTimeMillis()/1000.000;
		System.out.println("[Info:] Start to get UIContext...");
		boolean bDifferentUI = false; boolean bNewPLOC = false; boolean bNewFuzzy = false;
		int iCounter=0; int iNewString=0; int iNewPLOCString = 0; int iNewFuzzyNumber=0;
		try{
			int sum = 0;
			String filename="./web/"+NodePath+".html";
			File fileSnapShot= new File(filename); 
			if(fileSnapShot.exists()){ 
				String uiContext = "";
				String sql=""; 
				boolean bUnique = false; 
				String fileline="";String nodeText="";String oneline=""; String sqlText="";
				String PLOCString = "";String KeyID="";	
				ManageDB mdb = new ManageDB();
				mdb.getConnection(DBPosition);
				
				sql="delete from PlocTable where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				sql="delete from NewFuzzyTable where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				sql="delete from NodeAllText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				
				int seq=0; int iStart=0; int iEnd=0; int iLength=0; int iTest=0; boolean bInteger=false;int iSpectial=0;
				BufferedReader br = new BufferedReader (new FileReader(fileSnapShot));
				while((fileline = br.readLine()) != null) {   
					
					seq ++;
					fileline=fileline.trim();
					iStart=fileline.indexOf("<");
					iEnd=fileline.indexOf(">");
					iLength=fileline.length();
					if(iLength>iEnd){
						if(iStart>-1){
							if(iEnd>-1){
								nodeText=fileline.substring(0,iStart)+fileline.substring(iEnd+1);
							}else{
								nodeText=fileline.substring(0,iStart);
							}	
						}else{
							nodeText=fileline;
						}
					}else{
						if(iStart>-1){
							nodeText=fileline.substring(0,iStart);
						}else{
							nodeText=fileline;
						}
					}
					nodeText=nodeText.trim();					
					iStart=nodeText.indexOf("<");
					if(iStart>0){
						nodeText=nodeText.substring(0,iStart);
					}
					nodeText=nodeText.trim();	
					if(nodeText.length()>200){
						nodeText=nodeText.substring(0,200);
					}					
					nodeText=nodeText.replace('\'', '&');
					sqlText=nodeText;
					nodeText=nodeText.replace(',', '0');
					nodeText=nodeText.replace('.', '0');
					nodeText=nodeText.replace('%', '0');
					nodeText=nodeText.replace(' ', '0');
					try{
						iTest=Integer.parseInt(nodeText);
						bInteger=true;
					}catch(Exception ex){
						bInteger=false;
					}
					if(bInteger){
						nodeText="";
						sqlText="";
					}					
					nodeText=nodeText.replace('0', ' ');
					iSpectial=0;
					if(nodeText.indexOf("]]")!=-1){
						 StringTokenizer st1 =new StringTokenizer(nodeText,"]]");
							while(st1.hasMoreTokens()){
								
								//PLOCString=st1.nextToken().replace('\n', ' ').trim()+"]]";
								PLOCString=st1.nextToken()+"]]";
								if(PLOCString.length()>10){
									
									KeyID=PLOCString.substring(PLOCString.length()-10);
									iSpectial=KeyID.indexOf("[[");
									if(iSpectial!=-1){
										KeyID=KeyID.substring(iSpectial);
										if(PLOCString.length()>250){
											PLOCString=PLOCString.substring(0,250);
										}
										PLOCString=PLOCString.replace('\'', '&');
										sql="insert into PlocTable set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
										bUnique = mdb.updateUnique(sql);
										iCounter++;
										//System.out.println("[Info:] KeyID="+KeyID);
										if(bUnique){
											iNewPLOCString++;
											bNewPLOC = true;
										}
										//New Fuzzy
										sql="insert into NewFuzzyTable set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
										bUnique = mdb.updateUnique(sql);
										if(bUnique){
											iNewFuzzyNumber++;
											bNewFuzzy = true;
											
										}
									}
								}
							}
					 }//end if
					 if(!sqlText.equals("")){
						 //System.out.println("nodeText="+nodeText);
						 sql="insert into NodeAllText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+sqlText+"'";
						 bUnique = mdb.updateUnique(sql);
						 if(bUnique){
							 iNewString++;
							 bDifferentUI = true;								
						 }
					 }
				}//end while
				br.close();
//				sql="update UIText set KeyNumber="+iCounter+" where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
//				mdb.updateUnique(sql);
				if(bNewPLOC){
					sql="update NodeList set plocNumber="+iNewPLOCString+",newPloc=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				if(bNewFuzzy){
					sql="update NodeList set fuzzyNumber="+iNewFuzzyNumber+",newFuzzy=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				if(bDifferentUI){
					sql="update NodeList set newStringNumber="+iNewString+",newString=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				mdb.close();
			}//if
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] End to get UIContext. NodePath "+NodePath+" get "+iNewString+" new strings,"+iNewPLOCString+" new PLOC strings,"+iNewFuzzyNumber+" new fuzzy strings. Time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");
		return bDifferentUI;
	}
	public boolean getUIStringFromSnapshot(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime) throws Exception
	{
		double timer_begin1 = System.currentTimeMillis()/1000.000;
		//System.out.println("[Info:] Start to get UIContext...");
		boolean bDifferentUI = false; boolean bNewPLOC = false; boolean bNewFuzzy = false;
		int iNewString=0; int iNewPLOCString = 0; int iNewFuzzyNumber=0;
		//try{
			int sum = 0;
			String filename="./web/"+NodePath+".html";
			File fileSnapShot= new File(filename); 
			if(fileSnapShot.exists()){ 
				String uiContext = "";
				String sql=""; 
				boolean bUnique = false; 
				String fileline=""; String newline=""; 
				String linePart=""; String nodeText="";
				String testText="";String afterText="";
				String PLOCString = "";String KeyID="";	
				ManageDB mdb = new ManageDB();
				mdb.getConnection(DBPosition);
				ResultSet rs_check = null;
				boolean bNotFound=false;
				
				sql="delete from PlocTable where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				sql="delete from NewFuzzyTable where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				sql="delete from NodeAllText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				sql="delete from NodeContext where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
				mdb.updateSQL(sql);
				
				int iStart=0; int iEnd=0; int iLength=0; int iEqual=0;
				int iNewStart=0; int iNewEnd=0;
				int iTest=0; boolean bInteger=false; int iScript=0;
				BufferedReader br =null; boolean bFileRead=false;
				try{
					br = new BufferedReader (new FileReader(fileSnapShot));
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
				while(!bLastLine) {		
					fileline=fileline.trim();					
					iEnd=fileline.indexOf(">");
					iScript=fileline.indexOf("script");
					iLength=fileline.length();
					if(iEnd>-1&&iScript==-1){
						if(iLength>iEnd){
							afterText=fileline.substring(iEnd+1);
							afterText=afterText.replace('=', ' ');
							afterText=afterText.trim();							
						}else{
							afterText="";
						}
						testText=afterText;
						testText=testText.replace(',', '0');
						testText=testText.replace('.', '0');
						testText=testText.replace('%', '0');
						testText=testText.replace(' ', '0');
						testText=testText.replace('>', '0');
						testText=testText.replace('<', '0');
						try{
							iTest=Integer.parseInt(testText);
							bInteger=true;
						}catch(Exception ex){
							bInteger=false;
						}
						if(bInteger){
							newline=fileline;
						}else{
							if(afterText.equals("")){
								newline=fileline;
							}else{
								newline=fileline.substring(0,iEnd-1)+" nodetext=\"nodetext@"+afterText+"\">";
							}
						}
					}else{
						if(fileline.equals("")){
							newline="";
						}else{
							//newline=fileline;
							newline="";
						}
					}				
					newline=newline.replaceAll("=]", "@]");
					//newline=newline.replaceAll("[=", "[@");
					if(newline.indexOf("=")!=-1){
						 StringTokenizer st1 =new StringTokenizer(newline,"=");
							while(st1.hasMoreTokens()){
								linePart=st1.nextToken(); //<span title="? Favorites[[0AiT]]" alt="? Favorites[[0AiT]]"								
								iStart=linePart.indexOf("[[");
								iEnd=linePart.indexOf("]]");
								if(iStart>-1&&iEnd>-1&&iStart<iEnd){
									KeyID=linePart.substring(iStart,iEnd+2);	
									KeyID=KeyID.replaceAll("@]","=]");
									//KeyID=KeyID.replaceAll("[@","[=");
									PLOCString=linePart.substring(1,iStart);
									iEqual=PLOCString.indexOf("nodetext@");
									if(iEqual>-1){									
										PLOCString=PLOCString.substring(9);
									}
									if(PLOCString.length()>200){
										PLOCString=PLOCString.substring(0,200);										
									}
									PLOCString=PLOCString.replace('\'', '&');
									PLOCString=PLOCString.trim();
									nodeText=PLOCString;
									
									sql="select Active from NodeContext where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
									rs_check=mdb.getSQL(sql);
									if(rs_check.next()){
										bNotFound=false;
									}else{
										bNotFound=true;
									}
									rs_check.close();	
									if(bNotFound){
										sql="insert into NodeContext set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
										mdb.updateUnique(sql);
									}
									
									sql="select Active from NodeAllText where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
									rs_check=mdb.getSQL(sql);
									if(rs_check.next()){
										bNotFound=false;
									}else{
										bNotFound=true;
									}
									rs_check.close();	
									if(bNotFound){
										sql="insert into NodeAllText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
										mdb.updateUnique(sql);
										iNewString++;
										bDifferentUI = true;								
									}

									sql="select Active from PlocTable where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"'";
									rs_check=mdb.getSQL(sql);
									if(rs_check.next()){
										bNotFound=false;
									}else{
										bNotFound=true;
									}
									rs_check.close();																								
									if(bNotFound){
										sql="insert into PlocTable set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
										mdb.updateUnique(sql);	
										iNewPLOCString++;
										bNewPLOC = true;
									}
									
									//New Fuzzy
									sql="select Active from NewFuzzyTable where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"'";
									rs_check=mdb.getSQL(sql);
									if(rs_check.next()){
										bNotFound=false;
									}else{
										bNotFound=true;
									}
									rs_check.close();									
									if(bNotFound){
										sql="insert into NewFuzzyTable set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
										mdb.updateUnique(sql);
										iNewFuzzyNumber++;
										bNewFuzzy = true;
										
									}//if(bUnique)
								}
								else{
									iStart=linePart.indexOf("nodetext"); //<span title="" nodetext="Administrator, Niku">
									iEqual=linePart.indexOf("@");
									iLength=linePart.length();
									if(iStart>-1){
										if(iEqual>iStart){
											iNewStart=iEqual+1;
											iNewEnd=iLength-2;
											if(iNewEnd>=iNewStart){
												nodeText=linePart.substring(iEqual+1,iLength-2);
											}
										}else{
											nodeText="";
										}
									}
									testText=nodeText;
									testText=testText.replace(',', '0');
									testText=testText.replace('.', '0');
									testText=testText.replace('%', '0');
									testText=testText.replace(' ', '0');
									testText=testText.replace('>', '0');
									testText=testText.replace('<', '0');
									try{
										iTest=Integer.parseInt(testText);
										bInteger=true;
									}catch(Exception ex){
										bInteger=false;
									}
									if(bInteger){
										nodeText="";
									}else{
										sql="select Active from NodeContext where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
										rs_check=mdb.getSQL(sql);
										if(rs_check.next()){
											bNotFound=false;
										}else{
											bNotFound=true;
										}
										rs_check.close();	
										if(bNotFound){
											sql="insert into NodeContext set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
											mdb.updateUnique(sql);
										}
										
										sql="select Active from NodeAllText where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
										rs_check=mdb.getSQL(sql);
										if(rs_check.next()){
											bNotFound=false;
										}else{
											bNotFound=true;
										}
										rs_check.close();
										if(bNotFound){
											sql="insert into NodeAllText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
										 	mdb.updateUnique(sql);
											iNewString++;
											bDifferentUI = true;								
										}
									 }
								}
							}//while(st1.hasMoreTokens()){
					}//end if
					try{
						fileline = br.readLine();
					}catch(Exception e2){}
					if(fileline==null){
						bLastLine=true;
					}
				}//end while
				if(bFileRead){
					try{
						br.close();
					}catch(Exception e3){}
				}
				if(bNewPLOC){
					sql="update NodeList set plocNumber="+iNewPLOCString+",newPloc=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				if(bNewFuzzy){
					sql="update NodeList set fuzzyNumber="+iNewFuzzyNumber+",newFuzzy=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				if(bDifferentUI){
					sql="update NodeList set newStringNumber="+iNewString+",newString=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				rs_check = null;
				mdb.close();
			}//if
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
//		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		String loginfo="[Info:] Get UIContext sucessfully. NodePath "+NodePath+" get "+iNewString+" new strings,"+iNewPLOCString+" new PLOC strings,"+iNewFuzzyNumber+" new fuzzy strings. Time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds";
		System.out.println(loginfo);
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,loginfo);
		return bDifferentUI;
	}
	
	public boolean searchStringFromSnapshot(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime) throws Exception
	{
		double timer_begin1 = System.currentTimeMillis()/1000.000;
		//System.out.println("[Info:] Start to get UIContext...");
		boolean bDifferentUI = false; boolean bNewPLOC = false; boolean bNewFuzzy = false;
		int iNewString=0; int iNewPLOCString = 0; int iNewFuzzyNumber=0;
		//try{
			int sum = 0;
			String filename="./web/"+NodePath+".html";
			File fileSnapShot= new File(filename); 
			if(fileSnapShot.exists()){
				String uiContext = "";
				String sql=""; 
				boolean bUnique = false; 
				String fileline=""; String newline=""; 
				String linePart=""; String nodeText="";
				String testText="";String afterText="";
				String PLOCString = "";String KeyID="";	
				ManageDB mdb = new ManageDB();
				mdb.getConnection(DBPosition);
				ResultSet rs_check = null;
				boolean bNotFound=false;
				
//				sql="delete from PlocTable where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
//				mdb.updateSQL(sql);
//				sql="delete from NewFuzzyTable where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
//				mdb.updateSQL(sql);
//				sql="delete from NodeAllText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
//				mdb.updateSQL(sql);
//				sql="delete from NodeContext where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
//				mdb.updateSQL(sql);
				
				int iStart=0; int iEnd=0; int iLength=0; int iEqual=0;
				int iNewStart=0; int iNewEnd=0;
				int iTest=0; boolean bInteger=false; int iScript=0;
				BufferedReader br =null; boolean bFileRead=false;
				try{
					br = new BufferedReader (new FileReader(fileSnapShot));
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
				while(!bLastLine) {		
					fileline=fileline.trim();					
					iEnd=fileline.indexOf(">");
					iScript=fileline.indexOf("script");
					iLength=fileline.length();
					if(iEnd>-1&&iScript==-1){
						if(iLength>iEnd){
							afterText=fileline.substring(iEnd+1);
							afterText=afterText.replace('=', ' ');
							afterText=afterText.trim();							
						}else{
							afterText="";
						}
						testText=afterText;
						testText=testText.replace(',', '0');
						testText=testText.replace('.', '0');
						testText=testText.replace('%', '0');
						testText=testText.replace(' ', '0');
						testText=testText.replace('>', '0');
						testText=testText.replace('<', '0');
						try{
							iTest=Integer.parseInt(testText);
							bInteger=true;
						}catch(Exception ex){
							bInteger=false;
						}
						if(bInteger){
							newline=fileline;
						}else{
							if(afterText.equals("")){
								newline=fileline;
							}else{
								newline=fileline.substring(0,iEnd-1)+" nodetext=\"nodetext@"+afterText+"\">";
							}
						}
					}else{
						if(fileline.equals("")){
							newline="";
						}else{
							//newline=fileline;
							newline="";
						}
					}				
					newline=newline.replaceAll("=]", "@]");
					//newline=newline.replaceAll("[=", "[@");
					if(newline.indexOf("=")!=-1){
						 StringTokenizer st1 =new StringTokenizer(newline,"=");
							while(st1.hasMoreTokens()){
								linePart=st1.nextToken(); //<span title="? Favorites[[0AiT]]" alt="? Favorites[[0AiT]]"								
								iStart=linePart.indexOf("[[");
								iEnd=linePart.indexOf("]]");
								if(iStart>-1&&iEnd>-1&&iStart<iEnd){
									KeyID=linePart.substring(iStart,iEnd+2);	
									KeyID=KeyID.replaceAll("@]","=]");
									//KeyID=KeyID.replaceAll("[@","[=");
									PLOCString=linePart.substring(1,iStart);
									iEqual=PLOCString.indexOf("nodetext@");
									if(iEqual>-1){									
										PLOCString=PLOCString.substring(9);
									}
									if(PLOCString.length()>200){
										PLOCString=PLOCString.substring(0,200);										
									}
									PLOCString=PLOCString.replace('\'', '&');
									PLOCString=PLOCString.trim();
									nodeText=PLOCString;
									
//									sql="select Active from NodeContext where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
//									rs_check=mdb.getSQL(sql);
//									if(rs_check.next()){
//										bNotFound=false;
//									}else{
//										bNotFound=true;
//									}
//									rs_check.close();	
//									if(bNotFound){
//										sql="insert into NodeContext set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
//										mdb.updateUnique(sql);
//									}
//									
//									sql="select Active from NodeAllText where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
//									rs_check=mdb.getSQL(sql);
//									if(rs_check.next()){
//										bNotFound=false;
//									}else{
//										bNotFound=true;
//									}
//									rs_check.close();	
//									if(bNotFound){
//										sql="insert into NodeAllText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
//										mdb.updateUnique(sql);
//										iNewString++;
//										bDifferentUI = true;								
//									}

									sql="select * from AllPUID where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"'";									
									rs_check=mdb.getSQL(sql);
									if(rs_check.next()){
										bNotFound=false;
										//System.out.println(sql+";");
										//System.out.println("----------------Found PUID"+KeyID);
									}else{
										bNotFound=true;
										//System.out.println("Not found PUID"+KeyID);
									}
									rs_check.close();																								
//									if(bNotFound){
//										sql="insert into PlocTable set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
//										mdb.updateUnique(sql);	
//										iNewPLOCString++;
//										bNewPLOC = true;
//									}
									
									//New Fuzzy
//									sql="select Active from NewFuzzyTable where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"'";
//									rs_check=mdb.getSQL(sql);
//									if(rs_check.next()){
//										bNotFound=false;
//									}else{
//										bNotFound=true;
//									}
//									rs_check.close();									
//									if(bNotFound){
//										sql="insert into NewFuzzyTable set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',KeyID='"+KeyID+"',Content='"+PLOCString+"'";
//										mdb.updateUnique(sql);
//										iNewFuzzyNumber++;
//										bNewFuzzy = true;										
//									}
									//if(bUnique)
								}
								else{
									iStart=linePart.indexOf("nodetext"); //<span title="" nodetext="Administrator, Niku">
									iEqual=linePart.indexOf("@");
									iLength=linePart.length();
									if(iStart>-1){
										if(iEqual>iStart){
											iNewStart=iEqual+1;
											iNewEnd=iLength-2;
											if(iNewEnd>=iNewStart){
												nodeText=linePart.substring(iEqual+1,iLength-2);
											}
										}else{
											nodeText="";
										}
									}
									testText=nodeText;
									testText=testText.replace(',', '0');
									testText=testText.replace('.', '0');
									testText=testText.replace('%', '0');
									testText=testText.replace(' ', '0');
									testText=testText.replace('>', '0');
									testText=testText.replace('<', '0');
									try{
										iTest=Integer.parseInt(testText);
										bInteger=true;
									}catch(Exception ex){
										bInteger=false;
									}
									if(bInteger){
										nodeText="";
									}else{
//										sql="select Active from NodeContext where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
//										rs_check=mdb.getSQL(sql);
//										if(rs_check.next()){
//											bNotFound=false;
//										}else{
//											bNotFound=true;
//										}
//										rs_check.close();	
//										if(bNotFound){
//											sql="insert into NodeContext set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
//											mdb.updateUnique(sql);
//										}
//										
//										sql="select Active from NodeAllText where Content='"+nodeText+"' and ProjectID='"+ProjectID+"'";
//										rs_check=mdb.getSQL(sql);
//										if(rs_check.next()){
//											bNotFound=false;
//										}else{
//											bNotFound=true;
//										}
//										rs_check.close();
//										if(bNotFound){
//											sql="insert into NodeAllText set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Content='"+nodeText+"'";
//										 	mdb.updateUnique(sql);
//											iNewString++;
//											bDifferentUI = true;								
//										}
									 }
								}
							}//while(st1.hasMoreTokens()){
					}//end if
					try{
						fileline = br.readLine();
					}catch(Exception e2){}
					if(fileline==null){
						bLastLine=true;
					}
				}//end while
				if(bFileRead){
					try{
						br.close();
					}catch(Exception e3){}
				}
				if(bNewPLOC){
					sql="update NodeList set plocNumber="+iNewPLOCString+",newPloc=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				if(bNewFuzzy){
					sql="update NodeList set fuzzyNumber="+iNewFuzzyNumber+",newFuzzy=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				if(bDifferentUI){
					sql="update NodeList set newStringNumber="+iNewString+",newString=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
					mdb.updateSQL(sql);
				}
				rs_check = null;
				mdb.close();
			}//if
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
//		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		String loginfo="[Info:] Get UIContext sucessfully. NodePath "+NodePath+" get "+iNewString+" new strings,"+iNewPLOCString+" new PLOC strings,"+iNewFuzzyNumber+" new fuzzy strings. Time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds";
		System.out.println(loginfo);
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,loginfo);
		return bDifferentUI;
	}
	
	public String getPartStrVector(WebDriver driver,String DOMType,String NodePath,String ProjectID,String DBPosition,String ProductName,int iFindElementTime,boolean bNewFeature)
	{
		
		String pv="";
		try{
			int i=0;
			int sum = 0;
			List<WebElement> count = null;
			//toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
				count = driver.findElements(By.cssSelector("body *")); 	
				sum = count.size();
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrVector, can not run findElements for "+ NodePath);
     		}			
			pv=String.valueOf(sum);			 
			if(sum>0){ //driver is normal
				String xml_level = "body > *"; //xpath="//body/*"; Direct child
				for (i = 1; i < 200; i++) 
				{
					sum = 0;
					if(i==10||i==20||i==30||i==40||i==50||i==110||i==120||i==130||i==140||i==150||i==199){ 
						count = driver.findElements(By.cssSelector(xml_level));
						sum = count.size();
						if (sum == 0) 
						{				
							break;
						}
						pv+=String.valueOf(count.size());
					}
					xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
				}	
			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrVector failure. Return pv=0 ");
		}
		return pv;
	}
	public String getPartVector(WebDriver driver,String ProjectFilter,String ParentFilter,int LevelID,String NodePath,String ProjectID,String DBPosition,String ProductName,int iFindElementTime,boolean bNewFeature)
	{
		String pv="";int seq=0;int iSpecialCount=0;	int sum = 0;
		try{
			String tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,onkeydown,nodeType,nodeText,nodeTitle,nodeTitleAux,onclick;
			String nodeSrc="";
			boolean bVisible = false; ExpectedCondition<WebElement> strClickable; boolean bClickable=false;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
				allElements = driver.findElements(By.xpath(ProjectFilter));
				sum = allElements.size();
			}catch(Exception eall){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getPartVector failure.");
			}
			if(sum>0){
				if(bNewFeature){//bNewFeature for input value, etc.
					for (WebElement e : allElements) {  
						seq++;
						bVisible = e.isDisplayed();
						strClickable = ExpectedConditions.elementToBeClickable(e);
			        	if(strClickable!=null ){bClickable=true;}else{bClickable=false;}	
			        	tagName = e.getTagName();	
			        	nodeId = e.getAttribute("id");if(nodeId!=null){nodeId=nodeId.replace('\'', '&');} else { nodeId=""; } 
			        	nodeHref = e.getAttribute("href");if(nodeHref!=null){nodeHref=nodeHref.replace('\'', '&');} else { nodeHref=""; } 
			        	nodeName = e.getAttribute("name");if(nodeName!=null){nodeName=nodeName.replace('\'', '&');} else { nodeName=""; } 
			        	nodeValue = e.getAttribute("value");if(nodeValue!=null){nodeValue=nodeValue.replace('\'', '&');} else { nodeValue=""; } 
			        	nodeClass= e.getAttribute("class");if(nodeClass!=null){nodeClass=nodeClass.replace('\'', '&');} else { nodeClass="null"; }
			        	nodeType= e.getAttribute("type");if(nodeType!=null){nodeType=nodeType.replace('\'', '&');} else { nodeType=""; } 
			        	onkeydown = e.getAttribute("onkeydown");if(onkeydown!=null){onkeydown=onkeydown.replace('\'', '&');} else { onkeydown=""; }
			        	onclick = e.getAttribute("onclick");if(onclick!=null){onclick=onclick.replace('\'', '&');}  else { onclick=""; }
			    		nodeTitle = e.getAttribute("title");if(nodeTitle!=null){nodeTitleAux=nodeTitle.replace('\'', '&');} else { nodeTitleAux=""; }  
			        	nodeText= e.getText();if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
			        	nodeSrc= e.getAttribute("src");if(nodeSrc!=null){nodeSrc=nodeSrc.replace('\'', '&');} else { nodeSrc=""; }
//			        	if(bVisible){
//			        		if(DOMFilter.includer(ProjectID,DBPosition,ProductName,tagName,ParentFilter,seq,LevelID,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText)){
//			        			if(DOMFilter.excluder(ProjectID,DBPosition,ProductName,tagName,ParentFilter,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText,nodeSrc)){
//			        				iSpecialCount++;
//			        				if(seq==1){
//			        					pv=""+seq;
//			        				}else{
//			        					pv=pv+"-"+seq;
//			        				}
//			        			}
//				        	}
//		        		}
					}
				}		
			}
		}
		catch(UnhandledAlertException e)
		{
			//e.printStackTrace();
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getPartVector failure. Return pv=0_0 ");
//			System.out.println("[Error:] getStrVector failure. "+ e.getMessage());
		}
		pv=iSpecialCount+"_"+pv;
		return pv;
	}
}

