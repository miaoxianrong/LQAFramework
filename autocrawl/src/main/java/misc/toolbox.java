package misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;

import core.loginStart;
import core.productInfo;
import db.ManageDB;
import db.RunDB;

public class toolbox {
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
	public static String getTime(){
		String sNow="";
		java.util.Date now=new java.util.Date();		
		int iYear=now.getYear()+1900;
		int iMonth=now.getMonth()+1;
		int iDay=now.getDate();
		int iHour=now.getHours();
		int iMinute=now.getMinutes();
		int iSeconds=now.getSeconds();
		String sYear=String.valueOf(iYear);
		String sMonth=String.valueOf(iMonth);
		if(iMonth<10){
			sMonth="0"+sMonth;
		}
		String sDay=String.valueOf(iDay);
		if(iDay<10){
			sDay="0"+sDay;
		}
		String sHour=String.valueOf(iHour);
		if(iHour<10){
			sHour="0"+sHour;
		}
		String sMinute=String.valueOf(iMinute);
		if(iMinute<10){
			sMinute="0"+sMinute;
		}
		sNow=sYear+"-"+sMonth+"-"+sDay+" "+sHour+":"+sMinute+":"+String.valueOf(iSeconds);
		return sNow;
	}
public static void setID(WebDriver driver, List<WebElement> allElements) {
		
//		List<WebElement> allElements = driver.findElements(By.xpath("//body//*"));	
		int i=10000000;
		for(WebElement e: allElements)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try{
			js.executeScript(
					"arguments[0].setAttribute('loc_id', arguments[1]);",
					e, i);
			}
			catch(StaleElementReferenceException e1 )
			{
//				System.out.println("Element is no longer attached to the DOM");
			}
		

		}
	
	}
	
	static boolean tagFilter(String tag)
	{
		boolean special = true;
		String[] tagList = {"tr","td", "tbody","table","form"};
		for(String s: tagList)
		{
			if(s.equals(tag)){
				special = false;
			}
		}
		
		
//		System.out.println(tag + ":" + special);
		
		return special;
	}
	public static void setReport(String ProjectID,String ProductName,String DebugServer,String EmailContact,String DBPosition, String RunMode){
		String Report_UserName=EmailContact;
		String AllUserName=",unknown,liahu01,genzh01,liuyu04,sonmi02,xioza01,caimi03,miaxi01,";		
		int iSignal=EmailContact.indexOf("@ca.com");
		if(iSignal>0){
			Report_UserName=EmailContact.substring(0,iSignal);
		}
		System.out.println("[Info:] Set Parameter and Project Report before initialization.");
		boolean bExist=true;//AllUserName.contains(","+Report_UserName+",");		
		if(bExist){
			try{
				ManageDB m1 = new ManageDB();
				m1.getConnection(DBPosition);	
				String sql="";
				sql="insert into ProjectReport set ";
	    		sql+="ProjectID='"+ProjectID+"'";
	    		sql+=",ProductName='"+ProductName+"'";
	    		sql+=",UserName='"+Report_UserName+"'";
	    		//System.out.println("sql="+sql);
				boolean bInsert=m1.updateUnique(sql);
				if(!bInsert){
					sql="update ProjectReport set ";
					sql+="ProductName='"+ProductName+"'";
		    		sql+=",UserName='"+Report_UserName+"'";
		    		sql+=" where ProjectID='"+ProjectID+"'";		    		
		    		m1.updateUnique(sql);
				}
//				sql="delete from NodeLog";System.out.println("sql="+sql);
//				m1.updateUnique(sql);				
				m1.close();
			}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearDB or DB connection");}
		}else{
			System.out.println("[Alert:] Stop running. EmailContact should be valid pmf@ca.com, please confirm it.");
			System.exit(0);
		}		
	}	
	public static void setParameter(String ProjectID,String ProductName,String ProductURL, String UserName,String PassWord, String TenantID, String Language, String DebugServer,String EmailContact,String DBPosition, String RunMode){
		String Report_UserName=EmailContact;
		String AllUserName=",unknown,liahu01,genzh01,liuyu04,sonmi02,xioza01,caimi03,miaxi01,";		
		int iSignal=EmailContact.indexOf("@ca.com");
		if(iSignal>0){
			Report_UserName=EmailContact.substring(0,iSignal);
		}
		System.out.println("[Info:] Set Parameter and Project Report before initialization.");
		boolean bExist=true;AllUserName.contains(","+Report_UserName+",");		
		if(bExist){
			try{
				ManageDB m1 = new ManageDB();
				m1.getConnection(DBPosition);	
				String sql="";
				sql="insert into ProjectReport set ";
	    		sql+="ProjectID='"+ProjectID+"'";
	    		sql+=",ProductName='"+ProductName+"'";
	    		sql+=",UserName='"+Report_UserName+"'";
	    		//System.out.println("sql="+sql);
				boolean bInsert=m1.updateUnique(sql);
				if(!bInsert){
					sql="update ProjectReport set ";
					sql+="ProductName='"+ProductName+"'";
		    		sql+=",UserName='"+Report_UserName+"'";
		    		sql+=" where ProjectID='"+ProjectID+"'";		    		
		    		m1.updateUnique(sql);
				}
//				sql="delete from NodeLog";System.out.println("sql="+sql);
//				m1.updateUnique(sql);
				
				sql="insert into ProjectParameter set ";
	    		sql+="ProjectID='"+ProjectID+"'";
	    		sql+=",ProductName='"+ProductName+"'";
	    		sql+=",ProductAlias='"+ProductName+"'";
	    		sql+=",URL='"+ProductURL+"'";
	    		sql+=",UserName='"+UserName+"'";
	    		sql+=",Password='"+PassWord+"'";
	    		sql+=",TenantID='"+TenantID+"'";
	    		sql+=",Language='"+Language+"'";
	    		sql+=",DebugServer='"+DebugServer+"'";
	    		//System.out.println("sql="+sql);
	    		bInsert=m1.updateUnique(sql);
				if(!bInsert){
					sql="update ProjectParameter set ";
					sql+="ProductName='"+ProductName+"'";
					sql+=",ProductAlias='"+ProductName+"'";
					sql+=",URL='"+ProductURL+"'";
			    	sql+=",UserName='"+UserName+"'";
			    	sql+=",Password='"+PassWord+"'";
			    	sql+=",TenantID='"+TenantID+"'";
			    	sql+=",Language='"+Language+"'";
		    		sql+=",DebugServer='"+DebugServer+"'";
		    		sql+=" where ProjectID='"+ProjectID+"'";
		    		//System.out.println("sql="+sql);
		    		m1.updateUnique(sql);
				}
				m1.close();
			}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearDB or DB connection");}
		}else{
			System.out.println("[Alert:] Stop running. EmailContact should be valid pmf@ca.com, please confirm it.");
			System.exit(0);
		}		
	}	
	public static boolean clearProject(String ProjectID,String ProductName,String DebugServer,String EmailContact,String DBPosition, String RunMode){
		boolean bDebug=false; if(RunMode.equals("Debug")){bDebug=true;}
		boolean bClearSuccess=false;		
		bClearSuccess=true;
		try{
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);			
			//m1.updateSQL("delete from ProjectReport");
			m1.updateSQL("delete from NodeList where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NodeLog where ProjectID='"+ProjectID+"'");
			//if(bDebug){
			m1.updateSQL("delete from HardcodeList where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from UniqueStringList where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from truncationlist where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from iformatlist where ProjectID='"+ProjectID+"'");
			
			m1.updateSQL("delete from PlocTable where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NewFuzzyTable where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NodeAllText where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NodeContext where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"'");
			
			//input value
			m1.updateSQL("delete from InputNodeList where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NameValueList where ProjectID='"+ProjectID+"'");
			//} 				
			m1.close();
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearDB or DB connection");}	
		
		return bClearSuccess;
	}	
	public static boolean clearSameProduct(String ProjectID,String ProductName,String DebugServer,String EmailContact,String DBPosition, String RunMode){
		boolean bDebug=false; if(RunMode.equals("Debug")){bDebug=true;}
		boolean bClearSuccess=false;		
		bClearSuccess=true;
		try{
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);			
			m1.updateSQL("delete from NodeList where ProjectName='"+ProductName+"'");	
			m1.close();
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearSameProduct or DB connection");}	
		
		return bClearSuccess;
	}	
	public static void AnalysisI18NFile(String ProjectID, String DBPosition){
		try{
			String FileName="./project/"+ProjectID+".i18n";
			File mappingFile= new File(FileName);
			String oneline = "";String KeyID="";  String PartKeyID="";
			int iPUID=0; String sql="";
			boolean bNotFound=false; 
			String Status="";String LStatus="";String Target="";String puid="";
			if(mappingFile.exists())
			{
				 String ReasonID="";
				 String NotFoundReason="";
				 System.out.println("[Info:] "+FileName+" is found. Start to import PLOC strings in i18n file...");
				 ManageDB m = new ManageDB(); 
				 m.getConnection(DBPosition);
				 m.updateSQL("delete from I18NFile where ProjectID='"+ProjectID+"'");
				 ResultSet rs=null; int iCounter=0;
				 int i=0;  int mapped=0;
				 boolean bNotPUID=false;
				 //BufferedReader br = new BufferedReader (new FileReader(mappingFile));
				 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mappingFile), "UTF-8"));
				 while((oneline = br.readLine()) != null)  
				 {
					 i++;
					 mapped=0;
					 if(oneline.indexOf("Status =")!=-1){
						 if(oneline.indexOf("LStatus =")==-1){
							 Status=oneline.substring(9); 				
						 }else{
							 LStatus=oneline.substring(10); 
						 }
					 }
					 if(oneline.indexOf("Target =")!=-1){
						 Target=oneline.substring(9); 
						 
					 }
					 iPUID=oneline.indexOf("puid=");
					 bNotPUID=false;
					 if(iPUID==-1){							 
						 iPUID=oneline.indexOf("Rule =");
						 if(iPUID==-1){							 
							 bNotPUID=true;
							 iPUID=oneline.indexOf("]]");
						 }
					 }
					 if(iPUID!=-1){	
						 puid=oneline;
						 if(bNotPUID){
							 PartKeyID=puid.substring(iPUID-4,iPUID)+"]]";
							 KeyID="[["+PartKeyID;
						 }else{
							 PartKeyID=puid.substring(iPUID+5,iPUID+9)+"]]";
							 KeyID="[["+PartKeyID;
						 }
						 sql="select * from UIText where KeyID = '"+KeyID+"' and ProjectID='"+ProjectID+"'";
						 rs=m.getSQL(sql);
						 if(rs.next()){
							 mapped=1;
						 }else{
							 mapped=0;
						 }
						 rs.close();	
						 Target=Target.replace('\'', ' ');
						 Target=Target.replace(',', ' ');
						 Status=Status.replace('\'', ' ');
						 Status=Status.replace(',', ' ');
						 LStatus=LStatus.replace('\'', ' ');
						 LStatus=LStatus.replace(',', ' ');
						 
						 ReasonID="0";
						 NotFoundReason="";
						
						 if(mapped==1){
							 ReasonID="0";
							 NotFoundReason="";
						 }						 
						 if(Target.length()>250){
							 Target=Target.substring(0,250);
						 }
						 sql="select KeyID from I18NFile where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"'";
						 try{
								rs = m.getSQL(sql);
								if(rs.next()){
									bNotFound = false;
								}else{
									bNotFound = true;
								}
								rs.close();
						 }catch(Exception e){}
						 sql="insert into I18NFile set ";
						 sql+="KeyID='"+KeyID+"'";
						 sql+=",Content='"+Target+"'";
						 sql+=",Status='"+Status+"'";
						 sql+=",LStatus='"+LStatus+"'";
						 sql+=",mapped="+mapped;
						 sql+=",NotFoundReason='"+NotFoundReason+"'";
						 sql+=",ReasonID="+ReasonID;
						 sql+=",ProjectID='"+ProjectID+"'";
						 //System.out.println(sql); 
						 //select KeyID from I18NFile where KeyID like '[[01dD%' and ProjectID='00000815';
						
						 if(bNotFound){
							 	m.updateUnique(sql);
								iCounter++;
								if(mapped==1){
									System.out.println(iCounter+" : "+KeyID+", mapped");
								}else{
									System.out.println(iCounter+" : "+KeyID);
								}
						}
					 }			 
					 
				 }
				 
				 rs=null;
				 br.close();
				 m.close();
			}
		}
		catch(Exception e)
		{
			
		}
		System.out.print("\nEnd, Turn to next step to do not found reason analysis");
		//toolbox.waitSecond(10);
	}
	public static void AnalysisReason(String ProjectID, String DBPosition){
		System.out.print("Start to do not found reason analysis");
		ManageDB m = new ManageDB(); 
		m.getConnection(DBPosition);
		ResultSet rs=null;
		String ReasonID="";
		String NotFoundReason="";
		String Content="";
		ArrayList<ArrayList<String>> allNodes = new ArrayList<ArrayList<String>>(); 
		String sql="select * from ReasonList";
		 try{
				rs = m.getSQL(sql);
				while(rs.next()){
					ArrayList<String> onenode = new ArrayList<String>(); 
					ReasonID=rs.getString("ReasonID");
					onenode.add(ReasonID); //0
					NotFoundReason=rs.getString("NotFoundReason");
					onenode.add(NotFoundReason); //1
					Content=rs.getString("Content");
					onenode.add(Content); //2	
					allNodes.add((ArrayList<String>)onenode.clone());
				}
				rs.close();
		 }catch(Exception e){}
		 String I18NFileID=""; 				 
		 StringTokenizer stArray=null;
		 String oneword="";
		 ArrayList<String> getNode = new ArrayList<String>(); 
		 for(int j=0;j<allNodes.size();j++){
			 getNode = allNodes.get(j);
			 ReasonID=getNode.get(0);
			 NotFoundReason=getNode.get(1);
			 Content=getNode.get(2);
			 stArray=new StringTokenizer(Content,"@");
			 while(stArray.hasMoreTokens()){
					oneword=stArray.nextToken();
					ArrayList<String> AllI18NFileID = new ArrayList<String>(); 
					sql="select I18NFileID from I18NFile where Content like '%"+oneword+"%' and mapped=0 and ProjectID='"+ProjectID+"'";
					try{
						rs = m.getSQL(sql);
						while(rs.next()){
							I18NFileID=rs.getString("I18NFileID");
							AllI18NFileID.add(I18NFileID);
						}
						rs.close();
					}catch(Exception e){}
					for(int k=0;k<AllI18NFileID.size();k++){
						sql="update I18NFile set ";
						sql+="ReasonID="+ReasonID;
						sql+=",NotFoundReason='"+NotFoundReason+"'";
						sql+=" where I18NFileID="+AllI18NFileID.get(k)+" and mapped=0 and ProjectID='"+ProjectID+"'";
						m.updateSQL(sql);
						//System.out.println(sql);
					}
			}
		 }				 	
		 rs=null;
		 m.close();
		 System.out.print("\nEnd for not found reason analysis");
	}
	public static void countMapping(String ProjectID, String DBPosition){
		boolean bUI = false; bUI = true;
		if(bUI){ 
			String nodeText="";StringTokenizer st1 = null; int iCounter = 0;
			String PLOCString = "";String sql=""; 
			boolean bNotFound=false; boolean bUnique = false; boolean bDifferentPage = false;
			boolean bInsert = false;  bInsert=true;
			if(bInsert){
				try{
					String FileName="./project/text-to-screen_"+ProjectID+".txt";
					File mappingFile= new File(FileName); 					
					String oneline = "";String KeyID="";	bUnique=true;		
					if(mappingFile.exists())
					{
						 System.out.println("[Info:] "+FileName+" is found. Start to import PLOC strings in text_to_screen file.");
						 ManageDB m = new ManageDB();
						 m.getConnection(DBPosition);
						 ResultSet rs = null;
						 //m.updateSQL("delete from PlocTable where ProjectID='"+ProjectID+"'");
						 //m.updateSQL("delete from NewFuzzyTable where ProjectID='"+ProjectID+"'");
						 //m.updateSQL("delete from NodeAllText where ProjectID='"+ProjectID+"'");						 
						 //m.updateSQL("delete from NodeContext where ProjectID='"+ProjectID+"'");
						 m.updateSQL("delete from UIText where ProjectID='"+ProjectID+"'");
						 int i=0;
						 //BufferedReader br = new BufferedReader (new FileReader(mappingFile));
						 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mappingFile), "UTF-8"));

						 while((oneline = br.readLine()) != null)  
						 {
							 i++;
							 if(oneline.indexOf("Find:")!=-1){									 
								 oneline=oneline.replace('\'', '&');
								 if(oneline.length()>200){
									 oneline=oneline.substring(0,200);
								 }
								 
//								 System.out.println(oneline);
								 
//								 sql="insert into NodeText set Content='"+oneline+"'";
//								 m.updateUnique(sql);
								 
								 nodeText = oneline;
								 if(nodeText.indexOf("]]")!=-1){
									 	
						        		st1 =new StringTokenizer(nodeText,"]]");
										while(st1.hasMoreTokens()){
											
											PLOCString=st1.nextToken().replace('\n', ' ').trim()+"]]";
											if(PLOCString.length()>8){												
												KeyID=PLOCString.substring(PLOCString.length()-8);
												if(PLOCString.length()>250){
													PLOCString=PLOCString.substring(0,250);
												}
												sql="select KeyID from UIText where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"'";
												try{
													rs = m.getSQL(sql);
													if(rs.next()){
														bNotFound=false;
													}else{
														bNotFound=true;
													}
													rs.close();
												}catch(Exception e){}
												if(bNotFound){
													sql="insert into UIText set KeyID='"+KeyID+"',Content='"+PLOCString+"', ProjectID='"+ProjectID+"'";
													m.updateUnique(sql);
													iCounter++;
													System.out.println(iCounter+" : "+KeyID);
													bDifferentPage = true;
												}
											}
//											System.out.println(KeyID);
										}
										
						         }
								 
//								 sql="insert into NodeAllText set Content='"+oneline+"'";
//								 m.updateSQL(sql);
								 
//								 System.out.println(oneline);
								 
								 
							 }
						 }
						System.out.println("ProjectID:"+ProjectID);
						br.close();
						sql="select count(Content) from NodeText where ProjectID='"+ProjectID+"'";
						try{
							ResultSet rs3=m.getSQL(sql);
							if(rs3.next()){
								//System.out.println("Mapping strings/units="+rs.getInt(1));
							}
							rs3.close();
							rs3=null;
						}catch(Exception ex1){}
						
						sql="select count(Content) from UIText where ProjectID='"+ProjectID+"'";
						try{
							ResultSet rs0=m.getSQL(sql);
							if(rs0.next()){
								System.out.println("Mapping PLOC strings/units="+rs0.getInt(1));
							}
							rs0.close();
							rs0=null;
						}catch(Exception ex0){}
						
						sql="select count(Content) from NodeAllText where ProjectID='"+ProjectID+"'";
						try{
							ResultSet rs1=m.getSQL(sql);
							if(rs1.next()){
								//System.out.println("Mapping all strings/units="+rs1.getInt(1));
							}
							rs1.close();
							rs1=null;
						}catch(Exception ex2){}	
						rs = null;
						//System.out.print("\nEnd, Waiting 10 seconds for next steps(i18n file).");
						m.close();
						//toolbox.waitSecond(10);
					}else{
						//System.out.println("[Alert:] File text-to-screen.txt not exists");
					}		
				}
				catch(IOException e)
				{
						
				}
			}
			
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
	public static void web_clear(String web_path)
	{
		//System.out.println("[ACTION]: Clear the screenshot folder.\n");
		File fi = new File(web_path);
	    File[] file = fi.listFiles();
	    for(int i=0;i<file.length;i++)
	    {
	    	file[i].delete();
	    }
	}
	public static void confirmScreenFolder(String ProjectID)
	{
		String[] screenshot_path = new String[5];
		screenshot_path[0] = "./output/"+ProjectID+"/screen";
		screenshot_path[1] = "./output/"+ProjectID+"/hardcode_screenshot";
		screenshot_path[2] = "./output/"+ProjectID+"/truncation_screenshot";
		screenshot_path[3] = "./output/"+ProjectID+"/iFormat_screenshot";
		screenshot_path[4] = "./output/"+ProjectID+"/web";
	
		for (int i=0; i<5; i++)
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
	public static void addFeatureScreenFolder(String Feature,String ProjectID){
		File file = new File ("./screen/"+ProjectID+"/"+Feature);
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
	public static void createSnapshot(WebDriver driver,String DBPosition,String DebugServer, String ProjectID,String ProductName,String NodePath, String RunMode){
		System.out.println("[Info:] Create snapshot for "+NodePath);
		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Create snapshot for "+NodePath);
		double timer_begin1 = System.currentTimeMillis()/1000.000;
		String WebURL=productInfo.getWebURL(ProductName);
		String ProductServerPort=productInfo.getProductServerPort(ProductName);
		String PageSource = driver.getPageSource();		
		String FullPageSource=PageSource;
		String incSource = ""; 
		//if(PageSource.contains("frame")){
		if(true){
			FullPageSource="";
			String sourceLine=""; String srcFile=""; String incFile=""; String URLincFile=""; 
			boolean bFrame=false;boolean bScript=false;boolean bCSS=false;
			int i_src = 0; int iStart = 0; boolean bGetStart=false; int iEnd = 0; boolean bGetEnd=false; 
			StringTokenizer st1 =new StringTokenizer(PageSource,"<");
			int iCount = 0; int iFileNumber=0; String FileNewName="";
			int iOldFileStart=0;
			int iOldFileEnd=0;
			while(st1.hasMoreTokens()){
				iCount ++;
				bFrame=false;
				sourceLine=st1.nextToken();
				if(sourceLine.contains("frame")||sourceLine.contains("Frame")){
					if(sourceLine.contains("frameset")||sourceLine.contains("Frameset")||sourceLine.contains("FrameSet")||sourceLine.contains("iframe")){
						bFrame=false;						
					}else{
						iFileNumber++;
						bFrame=true;
						sourceLine=sourceLine.replaceAll("/>", ">");
						sourceLine=sourceLine.replaceAll("/ >", ">");
						FileNewName=NodePath+"_"+iFileNumber+".html";
					}
				}
//				if(sourceLine.contains(".js")&&sourceLine.contains("<script")){
//					iFileNumber++;
//					bScript=true;
//					FileNewName=NodePath+"_"+iFileNumber+".js";
//					System.out.println("<script-------"+sourceLine);
//				}
//				if(sourceLine.contains("text/css")){
//					iFileNumber++;
//					bCSS=true;
//					FileNewName=NodePath+"_"+iFileNumber+".css";
//				}
				if(bFrame||bScript||bCSS){
					
				}else{
					//sourceLine=sourceLine.replaceFirst(WebURL, "#?");
					i_src = sourceLine.indexOf("<link href=");
					if(i_src!=-1){
						sourceLine=sourceLine.replaceFirst("<link href=\"", "<link href=\""+WebURL);
					}
					i_src = sourceLine.indexOf(ProductServerPort);
					if(i_src!=-1){
						sourceLine=sourceLine.replaceFirst(ProductServerPort, DebugServer);
					}					
//					if(sourceLine.contains("10.131")){
//						System.out.println("10.131----"+sourceLine);
//					}
					if(iCount==1){
						FullPageSource="<"+sourceLine+"\n";
					}else{
						FullPageSource=FullPageSource+"<"+sourceLine+"\n";
					}
				}
				if(bFrame){
					i_src = sourceLine.indexOf("src");
					if(i_src!=-1){
						incFile=sourceLine.substring(sourceLine.indexOf("src"));
						iStart=incFile.indexOf("\"");
						if(iStart==-1){
							iStart=incFile.indexOf("'");
							if(iStart!=-1){
								incFile=incFile.substring(iStart+1);
								bGetStart=true; 
							}
						}else{
							incFile=incFile.substring(iStart+1);
							bGetStart=true;
						}
						if(iStart==-1){
							iStart=incFile.indexOf("=");
							if(iStart!=-1){
								incFile=incFile.substring(iStart);
								bGetStart=true;
							}
						}
						if(bGetStart){
							iEnd=incFile.indexOf("\"");
							if(iEnd==-1){
								iEnd=incFile.indexOf("'");
								if(iEnd==-1){
									
								}else{
									incFile=incFile.substring(0,iEnd);
									bGetEnd=true;
								}
							}else{
								incFile=incFile.substring(0,iEnd);
								bGetEnd=true;
							}
						}
						if(bGetStart){
							URLincFile=WebURL+incFile;
							driver.get(URLincFile);
							incSource=driver.getPageSource();							
							StringTokenizer st2 =new StringTokenizer(incSource,"<");	
							int nCount=0; String incSourceLine="";String FullIncSouce="";
							while(st2.hasMoreTokens()){
								nCount ++;
								incSourceLine=st2.nextToken();
								incSourceLine=incSourceLine.replaceFirst(ProductServerPort, DebugServer);
								if(iCount==1){
									FullIncSouce="<"+incSourceLine+"\n";
								}else{
									FullIncSouce=FullIncSouce+"<"+incSourceLine+"\n";
								}
							}
							toolbox.writeFile(FileNewName,FullIncSouce);
						}
					}	
					//change file name
					iOldFileStart=sourceLine.indexOf(incFile);
					iOldFileEnd=iOldFileStart+incFile.length();
					if(iOldFileStart!=-1&&iOldFileEnd!=-1){
						sourceLine=sourceLine.substring(0,iOldFileStart)+FileNewName+sourceLine.substring(iOldFileEnd);
					}else{
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] NodePath "+NodePath+" file name "+incFile+" cannot be changed.");
					}
					FullPageSource=FullPageSource+"<"+sourceLine;
				}
				//script
//				if(bScript){
//					i_src = sourceLine.indexOf("src");
//					if(i_src!=-1){
//						incFile=sourceLine.substring(sourceLine.indexOf("src"));
//						iStart=incFile.indexOf("\"");
//						if(iStart==-1){
//							iStart=incFile.indexOf("'");
//							if(iStart!=-1){
//								incFile=incFile.substring(iStart+1);
//								bGetStart=true; 
//							}
//						}else{
//							incFile=incFile.substring(iStart+1);
//							bGetStart=true;
//						}
//						if(iStart==-1){
//							iStart=incFile.indexOf("=");
//							if(iStart!=-1){
//								incFile=incFile.substring(iStart);
//								bGetStart=true;
//							}
//						}
//						if(bGetStart){
//							iEnd=incFile.indexOf("\"");
//							if(iEnd==-1){
//								iEnd=incFile.indexOf("'");
//								if(iEnd==-1){
//									
//								}else{
//									incFile=incFile.substring(0,iEnd);
//									bGetEnd=true;
//								}
//							}else{
//								incFile=incFile.substring(0,iEnd);
//								bGetEnd=true;
//							}
//						}
//						if(bGetStart){
//							URLincFile=WebURL+incFile;
//							driver.get(URLincFile);
//							incSource=driver.getPageSource();
//							toolbox.writeFile(FileNewName,incSource);
//						}
//					}	
//					//change file name
//					iOldFileStart=sourceLine.indexOf(incFile);
//					iOldFileEnd=iOldFileStart+incFile.length();
//					if(iOldFileStart!=-1&&iOldFileEnd!=-1){
//						sourceLine=sourceLine.substring(0,iOldFileStart)+FileNewName+sourceLine.substring(iOldFileEnd);
//					}else{
//						RunDB.addLog(ProjectID, DBPosition, "[Alert:] NodePath "+NodePath+" file name "+incFile+" cannot be changed.");
//					}
//					FullPageSource=FullPageSource+"<"+sourceLine;
//				}
//				//css
//				if(bCSS){
//					i_src = sourceLine.indexOf("src");
//					if(i_src!=-1){
//						incFile=sourceLine.substring(sourceLine.indexOf("src"));
//						iStart=incFile.indexOf("\"");
//						if(iStart==-1){
//							iStart=incFile.indexOf("'");
//							if(iStart!=-1){
//								incFile=incFile.substring(iStart+1);
//								bGetStart=true; 
//							}
//						}else{
//							incFile=incFile.substring(iStart+1);
//							bGetStart=true;
//						}
//						if(iStart==-1){
//							iStart=incFile.indexOf("=");
//							if(iStart!=-1){
//								incFile=incFile.substring(iStart);
//								bGetStart=true;
//							}
//						}
//						if(bGetStart){
//							iEnd=incFile.indexOf("\"");
//							if(iEnd==-1){
//								iEnd=incFile.indexOf("'");
//								if(iEnd==-1){
//									
//								}else{
//									incFile=incFile.substring(0,iEnd);
//									bGetEnd=true;
//								}
//							}else{
//								incFile=incFile.substring(0,iEnd);
//								bGetEnd=true;
//							}
//						}
//						if(bGetStart){
//							URLincFile=WebURL+incFile;
//							driver.get(URLincFile);
//							incSource=driver.getPageSource();
//							toolbox.writeFile(FileNewName,incSource);
//						}
//					}	
//					//change file name
//					iOldFileStart=sourceLine.indexOf(incFile);
//					iOldFileEnd=iOldFileStart+incFile.length();
//					if(iOldFileStart!=-1&&iOldFileEnd!=-1){
//						sourceLine=sourceLine.substring(0,iOldFileStart)+FileNewName+sourceLine.substring(iOldFileEnd);
//					}else{
//						RunDB.addLog(ProjectID, DBPosition, "[Alert:] NodePath "+NodePath+" file name "+incFile+" cannot be changed.");
//					}
//					FullPageSource=FullPageSource+"<"+sourceLine;
//				}
			}
		}
		String filename=NodePath+".html";
		toolbox.writeFile(filename,FullPageSource);
		try{		
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);
			String sql="update NodeList set snapshot=1 where NodePath ='"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			m1.close();
		}catch(Exception ex){System.out.println("[DB Error:] Check toolbox.createSnapshot or DB connection");}

		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		//System.out.println("[Info:] Get snapshot time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");	 
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Get snapshot time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");	 
	}
	
	public static void getInputValueParameter(WebDriver driver,String DBPosition,String ProjectID,String NodePath,String LocalString){
		System.out.println("[Info:] Get input value parameter for "+NodePath);
		//RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Get input value parameter for "+NodePath);
		double timer_begin1 = System.currentTimeMillis()/1000.000;
		String PageSource = driver.getPageSource();		
		WebElement e =null; String InputXPATH="";
		boolean bVisible = false; ExpectedCondition<WebElement> strClickable; boolean bClickable=false;
		try{
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);
			ResultSet rs2 = null;  boolean bNotExist=true;
			String sourceLine=""; String sql=""; 
//			sql="delete from NameValueList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
//			m1.updateSQL(sql);			
			String InputProperty="";String InputName=""; 
			String InputType=""; String InputTitle=""; String InputValue="";			
			int i_input = 0; int i_type = 0;  int i_title = 0; int i_name = 0; int i_ng_model = 0; 
			int i_space=0; int i_count = 0; int i_sigal=0;
			StringTokenizer st1 =new StringTokenizer(PageSource,"<");
			
//			String oneline="";
//			String FileName="./utf8.txt";
//			File utf8File= new File(FileName);
//			if(utf8File.exists())
//			{
//				 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(utf8File), "UTF-8"));
//				 if((oneline = br.readLine()) != null)  {
//					 System.out.println("[info:] utf8 string="+oneline);
//				 }
//			}
//			oneline=LocalString;
			while(st1.hasMoreTokens()){
				long lTemp=Math.round(Math.random()*8999+1000);
				//InputValue="TestData"+String.valueOf(lTemp);
				
				InputValue=String.valueOf(lTemp);
				sourceLine=st1.nextToken();
				i_input = sourceLine.indexOf("input");
				if(i_input!=-1){
					i_type = sourceLine.indexOf("type=");
					if(i_type!=-1){
						InputType=sourceLine.substring(i_type+6);
						i_space=InputType.indexOf("\"");
						if(i_space>2){
							InputType=InputType.substring(0,i_space);
						}
					}
					i_title = sourceLine.indexOf("title=");
					if(i_title!=-1){
						InputTitle=sourceLine.substring(i_title+7);
						i_space=InputTitle.indexOf("\"");
						if(i_space>2){
							InputTitle=InputTitle.substring(0,i_space);
						}
						if(InputTitle.length()>145){
							InputTitle=InputTitle.substring(0,145);
						}
						InputTitle=InputTitle.replace(',', ' ');
						InputTitle=InputTitle.replace('\'', ' ');
						
						i_sigal = InputTitle.indexOf("e.g.");
						if(i_sigal!=-1){
							InputValue=InputTitle.substring(i_sigal+5);
						}
						i_sigal = InputValue.indexOf(" ");
						if(i_sigal!=-1){
							InputValue=InputValue.substring(0,i_sigal-1);
						}	
//						System.out.println("[info:] InputTitle="+InputTitle);
//						System.out.println("[info:] InputValue="+InputValue);
					}
					i_name = sourceLine.indexOf("name=");
					if(i_name!=-1){
						InputProperty="name";
						InputName=sourceLine.substring(i_name+6);
						i_space=InputName.indexOf("\"");
						if(i_space>2){
							InputName=InputName.substring(0,i_space);
						}
					}else{				
						i_ng_model = sourceLine.indexOf("ng-model");
						if(i_ng_model!=-1){
							InputProperty="ng-model";
							InputName=sourceLine.substring(i_ng_model+10);
							i_space=InputName.indexOf("\"");
							if(i_space>2){
								InputName=InputName.substring(0,i_space);
							}
						}
					}
					if(InputType.equals("text")||InputType.equals("password")||InputType.equals("file")){
//						System.out.println("[sourceLine:] sourceLine="+sourceLine);
//						System.out.println("[Infoxxxxxx:] InputProperty="+InputProperty+",InputType="+InputType+",InputName="+InputName);
//						System.out.println("[InputName:] InputName="+InputName);
						if(InputName.contains("Name")||InputName.contains("name")){
							InputValue=LocalString;//"TestNAME";
							String Today="";
							java.util.Date now=new java.util.Date();
							int iYear=now.getYear()+1900;
							int iMonth=now.getMonth()+1;
							int iDay=now.getDate();
							int iHour=now.getHours();
							int iMinute=now.getMinutes();
							int iSeconds=now.getSeconds();
							String sYear=String.valueOf(iYear);
							String sMonth=String.valueOf(iMonth);
							if(iMonth<10){
								sMonth="0"+sMonth;
							}
							String sDay=String.valueOf(iDay);
							if(iDay<10){
								sDay="0"+sDay;
							}
							String sHour=String.valueOf(iHour);
							if(iHour<10){
								sHour="0"+sHour;
							}
							String sMinute=String.valueOf(iMinute);
							if(iMinute<10){
								sMinute="0"+sMinute;
							}
							String sScecond=String.valueOf(iSeconds);
							if(iSeconds<10){
								sScecond="0"+sScecond;
							}
							Today=sYear+sMonth+sDay+sHour+sMinute+sScecond;
							InputValue=InputValue+"_UTF8_";//+Today;
						}
						if(InputName.contains("url")){
							InputValue="http://www.ca.com:80";
						}
						if(InputName.contains("path")){
							InputValue="/lisabank/examples";
						}						 
						if(InputType.equals("file")){
							InputValue="";
						}
						InputXPATH = "//input[@"+InputProperty+"='"+InputName+"']";	
						try{
							e = driver.findElement(By.xpath(InputXPATH)); 
							bVisible = e.isDisplayed(); 
				        	strClickable = ExpectedConditions.elementToBeClickable(e);
				        	if(strClickable!=null ){
				        		bClickable=true;
				        	}else{
				        		bClickable=false;
				        	}	
						}catch(Exception ee){
							
						}
						if(bVisible&&bClickable){
							if(InputType.equals("file")){
								
							}else{
								//e.clear();
							}
							i_count++;
							if(i_count==1){
								bNotExist=true;
								sql="select * from InputNodeList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
								rs2=m1.getSQL(sql);
								if(rs2.next()){
							          	bNotExist=false;
							    }
							    rs2.close();
							    if(bNotExist){
							    	sql="insert into InputNodeList set ProjectID='"+ProjectID+"'";
							    	sql=sql+",NodePath='"+NodePath+"'";
								    m1.updateSQL(sql);
							  }
							}
//							System.out.println("[Info:] InputTitle="+InputTitle+",InputProperty="+InputProperty+",InputType="+InputType+",InputName="+InputName);
							sql="select * from NameValueList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"' and InputName='"+InputName+"'";
							bNotExist=true;
							rs2=m1.getSQL(sql);
							if(rs2.next()){
						          	bNotExist=false;
						    }
						    rs2.close();
						    if(bNotExist){
								sql="insert into NameValueList set "+"ProjectID='"+ProjectID+"'";
								sql=sql+",NodePath='"+NodePath+"'";
								sql=sql+",InputProperty='"+InputProperty+"'";
								sql=sql+",InputName='"+InputName+"'";
								sql=sql+",InputType='"+InputType+"'";
								sql=sql+",InputTitle='"+InputTitle+"'";
								sql=sql+",InputValue='"+InputValue+"'"; 
								m1.updateSQL(sql);
//								System.out.println(sql);
						    }
						}//if(bVisible&&bClickable){
						else{
							//System.out.println("[Info:] Not clickable or visible for InputTitle="+InputTitle+",InputProperty="+InputProperty+",InputType="+InputType+",InputName="+InputName);
						}
					}
				}	
			}
			rs2=null;
			m1.close();
		}catch(Exception ex){System.out.println("[DB Error:] Check toolbox.getInputValueParameter or DB connection");}
//		try{		
//			ManageDB m1 = new ManageDB();
//			m1.getConnection(DBPosition);
//			String sql="update NodeList set snapshot=1 where NodePath ='"+NodePath+"' and ProjectID='"+ProjectID+"'";
//			m1.updateSQL(sql);
//			m1.close();
//		}catch(Exception ex){System.out.println("[DB Error:] Check toolbox.getInputValueParameter or DB connection");}

		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		//System.out.println("[Info:] Get input value time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");
		//RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Get input value time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");	 
	}
	public static void writeFile(String FileName,String FullPageSource){
		String FolderFileName="./web/"+FileName;
		try{  
			File webfile = new File(FolderFileName);
		    if(webfile.exists()){
		    	//System.out.println("[Info:] Detect old file "+FileName+", delete it now.");
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
	public static void deleteScreen(String ProjectID,String NodePath,String DBPosition){
		String FileName=NodePath+".png";
		String FolderFileName="./screen/"+FileName;
		try{  
			File webfile = new File(FolderFileName);
		    if(webfile.exists()){
		    	RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Not unique, or not extract successfully, delete the screen "+FileName);
		    	webfile.delete();
		    }
			String sql="update NodeList set validNode=0, capture=0, screenName='', screenDesc='' where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);
			m1.updateSQL(sql);
			m1.close();
	    }catch(Exception e){  
	        //e.printStackTrace();  
	    }
	}
	
	//take a screenshot
	public static String captureScrnUpdateDB(String ProjectID,String RunMode, String DBPosition,String screenFolder,String Feature,String NodePath, WebDriver driver,String nameAux,String PluginAction)
			throws IOException {
		screenFolder = "./output/"+ProjectID+"/screen/"; 
		System.out.println("[Info:] Start to capture screen for "+ProjectID+":"+NodePath);
		boolean bPlay=false; if(RunMode.equals("Play")||RunMode.equals("PlayResume")){bPlay=true;}
		boolean bUIContext=false;
		if(PluginAction.contains("UIContext")){		
			bUIContext=true;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss"); 
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        String strDate = format.format(date);
        String FolderAndName="";
		String screenDesc=nameAux;
		String screenName = NodePath + ".png"; 
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select newPloc from NodeList";
		sql+=" where newPloc=1 and ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
		if(bPlay||bUIContext){
			try{
				ResultSet rs=m1.getSQL(sql);
				if(rs.next()){
					screenName="_NEWPLOC_"+screenName;
				}
				rs.close();
			}catch(Exception ex){}
			
			int iDrop=0;
			String newProjectID=ProjectID;
			iDrop=ProjectID.indexOf("drop");
			if(iDrop!=-1){
				newProjectID=ProjectID.substring(0,iDrop);
			}
			iDrop=ProjectID.indexOf("Drop");
			if(iDrop!=-1){
				newProjectID=ProjectID.substring(0,iDrop);
			}
			iDrop=ProjectID.indexOf("-");
			if(iDrop!=-1){
				newProjectID=ProjectID.substring(0,iDrop);
			}
			//screenName=ProjectID + "_" + strDate + "_" + screenName;
			screenName=ProjectID + "_" + NodePath + "_" + strDate+ ".png"; 
		}else{
			 screenName = NodePath + ".png"; 
		}
//		screenName = NodePath + nameAux + ".png"; 
		
//		screenFolder=webServer;
		if(bPlay||bUIContext){
			FolderAndName = screenFolder + screenName;
		}else{
			FolderAndName = screenFolder + Feature + "/" + screenName;
		} 
		if(Feature.equals("")){
			FolderAndName = screenFolder + screenName;
		}
		FolderAndName = screenFolder + screenName; //remove when creating GUIMap
		boolean bCaptureSuccess = false;	
		try{
			//driver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FolderAndName));
			bCaptureSuccess = true;
			
//			System.out.println("[Info:] Capture the screen "+screenName);	
			sql="update NodeList set validNode=1, capture=1, screenName='"+screenName+"' ";
			sql+=",screenDesc='"+screenDesc+"' ";
			sql+=" where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
			m1.updateSQL(sql); //not only for recort, but for play			
		}catch(WebDriverException e){
			bCaptureSuccess = false;			
			toolbox.waitSecond(5);
			
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FolderAndName));
		}
		m1.close();
		if(bCaptureSuccess){
			System.out.println("[Info:] Capture the screen "+screenName);
			RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Capture the screen "+screenName);
		}else{
			System.out.println("[Alert:] Screen Capture failed. Capture again.");
			RunDB.addLog(ProjectID,DBPosition,NodePath,2, "[Alert:] Screen Capture failed. Capture again.");
		}
		return screenName;
	}
	public static boolean captureScrn(String screenFolder,String screenName, WebDriver driver)
					throws IOException {
		boolean bCaptureSucess = false;
		String FolderAndName = screenFolder + screenName;
		try{
			//driver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FolderAndName));
			bCaptureSucess = true;
		}catch(WebDriverException e){
			bCaptureSucess = false;
//			System.out.println("[Alert:] Screen Capture failed! Capture again!");
			toolbox.waitSecond(5);
//			driver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FolderAndName));
			bCaptureSucess = true;
		}
		return bCaptureSucess;
	}
	//wait x seconds
	public static void waitSecond(int second)
	{
//		System.out.println("[Info:] Wait " + second + " seconds!");
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
	//waiting for an element
	public static boolean waitforelement(String PluginAction,String ProductName,WebDriver driver, String ProjectID,String DBPosition,String NodePath,String ByType,String test, int times, int iPageLoadTime)
	{
		String ErrorDescription="";
		boolean found = false;
		boolean bFindResult = false;
		try
		{
			toolbox.waitSecond(iPageLoadTime);  
			if(ByType.equals("css")){
				driver.findElement(By.cssSelector(test));
			}
			if(ByType.equals("xpath")){
				driver.findElement(By.xpath(test));
			}
			found = true;
			bFindResult  = true;
		}
		catch(Exception ex1)
		{
			found = false;
			bFindResult = false;
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Login failure 1 time. Node page after login is not loaded");
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Current page loading time ="+iPageLoadTime);
		}
		if(!found){
			System.out.println("[Info]: Try to find main page 2 times!");
			try
			{
				toolbox.waitSecond(iPageLoadTime);  
				if(ByType.equals("css")){
					driver.findElement(By.cssSelector(test));
				}
				if(ByType.equals("xpath")){
					driver.findElement(By.xpath(test));
				}
				found = true;
				bFindResult = true;
			}
			catch(Exception ex2)
			{
				found = false;
				bFindResult = false;
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Login failure 2 times. Node page after login is not loaded");
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Current page loading time ="+iPageLoadTime*2);
			}
		}
		if(!found){
			System.out.println("[Info]: Try to find main page 3 times!");
			try
			{
				toolbox.waitSecond(iPageLoadTime);  
				if(ByType.equals("css")){
					driver.findElement(By.cssSelector(test));
				}
				if(ByType.equals("xpath")){
					driver.findElement(By.xpath(test));
				}
				found = true;
				bFindResult = true;
			}
			catch(Exception ex3)
			{
				found = false;
				bFindResult = false;
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Login failure 3 times. Node page after login is not loaded");
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Current page loading time ="+iPageLoadTime*3);
			}
		}
		if(!found){
			System.out.println("[Info]: Try to find main page 4 times!");
			try
			{
				toolbox.waitSecond(iPageLoadTime);  
				if(ByType.equals("css")){
					driver.findElement(By.cssSelector(test));
				}
				if(ByType.equals("xpath")){
					driver.findElement(By.xpath(test));
				}
				found = true;
				bFindResult = true;
			}
			catch(Exception ex4)
			{
				found = false;
				bFindResult = false;
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Login failure 4 times. Node page after login is not loaded");
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Current page loading time ="+iPageLoadTime*4);
			}
		}
		if(!found){
			System.out.println("[Info]: Try to find main page 5 times!");
			try
			{
				toolbox.waitSecond(iPageLoadTime);  
				if(ByType.equals("css")){
					driver.findElement(By.cssSelector(test));
				}
				if(ByType.equals("xpath")){
					driver.findElement(By.xpath(test));
				}
				found = true;
				bFindResult = true;
			}
			catch(Exception ex5)
			{
				found = false;
				bFindResult = false;
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Login failure 5 times. Node page after login is not loaded");
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:]: Current page loading time ="+iPageLoadTime*5);
			}
		}
		if(found){			
			if(NodePath.equals("000")){
				System.out.println("[Info:] PluginAction="+PluginAction);
				if(PluginAction.contains("UIContext")){
					System.out.println("[Info:] Check product server to confirm whether it is a PLOC build for UIContext plugin action.");
					String pageSource= driver.getPageSource();
					if(pageSource.contains("[[")&&pageSource.contains("]]")){
						System.out.println("[Info:] It is a PLOC build.");
					}else{
						if(!ProductName.equals("MAA")){
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
					}
				}else{
					System.out.println("[Info:] Login successfully.");
				}
			}
		}else{
			ErrorDescription="[Alert:] No valid DOM elements are found on main page for NodePath "+NodePath+". Login failure."+"\n";
			ErrorDescription+="[Possible reason 1#:] Username, password, TenantID elements are not correct. Please provide correct username or password, even for TenantID."+"\n";
			ErrorDescription+="[Possible reason 2#:] Product server response time is too much to run."+"\n";
			ErrorDescription+="[Possible reason 3#:] Main page is crashed."+"\n";
			if(NodePath.equals("000")){
				ErrorDescription+="[Information:] Auto crawl will stop and exit. "+"\n";
			}
			System.out.println(ErrorDescription);	
			if(NodePath.equals("000")){	
				System.out.println("A notification email will be sent out. NodePath="+NodePath);
				String EmailSubject="[Alert:] No valid DOM elements are found on main page. Login failure.";
				String EmailBody=ErrorDescription;
				SendMail.sendExceptionAlertEmail(ProjectID,DBPosition,NodePath,ProductName,EmailSubject,EmailBody,"");
				System.out.println(ErrorDescription);	
				RunDB.addLog(ProjectID,DBPosition,NodePath,4, ErrorDescription);
				RunDB.setProjectStatusToPauseAndExit(DBPosition, ProjectID,NodePath);	
				
			}else{
				RunDB.addLog(ProjectID,DBPosition,NodePath,2, ErrorDescription);
			}
		}
		return bFindResult;
	}		

	public static void displayOneAtt(WebElement e){
		try{
			String sql,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,onkeydown,nodeType,nodeText,nodeTitle,onclick;
			tagName = e.getTagName();	
        	nodeId = e.getAttribute("id");
        	nodeHref = e.getAttribute("href");
        		if(nodeHref==null){nodeHref="";} 
        	nodeName = e.getAttribute("name");
        	nodeValue = e.getAttribute("value");
        		if(nodeValue==null){nodeValue="";} 
        	nodeClass= e.getAttribute("class");     
        		if(nodeClass!=null){nodeClass=nodeClass.replace('\'', '&');} else { nodeClass=""; }
        	nodeType= e.getAttribute("type");	   
        	onkeydown = e.getAttribute("onkeydown");	
        		if(onkeydown!=null){onkeydown=onkeydown.replace('\'', '&');} else { onkeydown=""; }
        	onclick = e.getAttribute("onclick");
        		if(onclick!=null){onclick=onclick.replace('\'', '&');}  else { onclick=""; }
    		nodeTitle = e.getAttribute("title");
        	nodeText= e.getText();
        		if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
	        sql="[Info:] Node attributes :";
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
	        System.out.println(sql);	         
        }catch(Exception ex){
        	System.out.println("[Alert:]Cannot display the attributes. toolbox.java method=displayOneAtt");
        } 
        
	}
	
	public static void displayMulAtt(WebDriver driver,String target, String byMethod, String screenName){
		List<WebElement> allElements = null;
		try{	        
	        switch(byMethod){
		        case "xpath":
		        	allElements=driver.findElements(By.xpath(target));
		        	break;
		        case "id":
		        	allElements=driver.findElements(By.id(target));
		        	break;
		        case "css":
		        	allElements=driver.findElements(By.cssSelector(target));
		        	break;
		        case "name":
		        	allElements=driver.findElements(By.name(target));
		        	break;
		        case "className":
		        	allElements=driver.findElements(By.className(target));
		        	break;
		        case "tagName":
		        	allElements=driver.findElements(By.tagName(target));
		        	break;
	        }
	        System.out.println("[Info:] "+ screenName +" has "+allElements.size()+" nodes by "+target);      
		}catch(Exception e){
        	System.out.println("[Alert:] Cannot find elements by "+target);
        } 
		toolbox.waitSecond(2); //wait for get attributes
		try{
			String sql,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,onkeydown,nodeType,nodeText,nodeTitle,onclick;
        	int i=0;
	        for (WebElement e : allElements) {  
	        	i++;
	        	tagName = e.getTagName();	
	        	nodeId = e.getAttribute("id");
	        	nodeHref = e.getAttribute("href");
	        		if(nodeHref==null){nodeHref="";} 
	        	nodeName = e.getAttribute("name");
	        	nodeValue = e.getAttribute("value");
	        		if(nodeValue==null){nodeValue="";} 
	        	nodeClass= e.getAttribute("class");     
	        		if(nodeClass!=null){nodeClass=nodeClass.replace('\'', '&');} else { nodeClass=""; }
	        	nodeType= e.getAttribute("type");	   
	        	onkeydown = e.getAttribute("onkeydown");	
	        		if(onkeydown!=null){onkeydown=onkeydown.replace('\'', '&');} else { onkeydown=""; }
	        	onclick = e.getAttribute("onclick");
	        		if(onclick!=null){onclick=onclick.replace('\'', '&');}  else { onclick=""; }
	    		nodeTitle = e.getAttribute("title");
	        	nodeText= e.getText();
	        		if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
	        	sql="[Info:] Node attributes :";
	        		sql+=" "+i+":";
	        		sql+=",tagName='"+tagName+"'";
		    		sql+=",nodeId='"+nodeId+"'";
		    		//sql+=",nodeHref='"+nodeHref+"'";
		    		sql+=",nodeName='"+nodeName+"'";
		    		sql+=",nodeValue='"+nodeValue+"'";
		    		sql+=",nodeClass='"+nodeClass+"'";
		    		sql+=",nodeType='"+nodeType+"'";
		    		sql+=",onkeydown='"+onkeydown+"'";
		    		sql+=",onclick='"+onclick+"'";
		    		sql+=",nodeTitle='"+nodeTitle+"'";
		    		sql+=",nodeText='"+nodeText+"'";
	        	System.out.println(sql);
	        } 
        }catch(Exception e){
        	System.out.println("[Alert:] Can not get attributes from WebElement."+" toolbox.java, method=displayMulAtt");
        } 
        
	}
	public static void displayMulElements(List<WebElement> allElements){
		int iTotal=allElements.size();
		System.out.println("[Info:] displayMulElements: Show "+iTotal+" attributes for allElements.");
		//toolbox.waitSecond(2); //wait for get attributes
		try{
			String sql,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,onkeydown,nodeType,nodeText,nodeTitle,onclick,onload;
        	int i=0;
	        for (WebElement e : allElements) {  
	        	i++;
	        	tagName = e.getTagName();	
	        	nodeId = e.getAttribute("id");
	        	nodeHref = e.getAttribute("href");
	        		if(nodeHref==null){nodeHref="";} 
	        	nodeName = e.getAttribute("name");
	        	nodeValue = e.getAttribute("value");
	        		if(nodeValue==null){nodeValue="";} 
	        	nodeClass= e.getAttribute("class");     
	        		if(nodeClass!=null){nodeClass=nodeClass.replace('\'', '&');} else { nodeClass=""; }
	        	nodeType= e.getAttribute("type");	   
	        	onkeydown = e.getAttribute("onkeydown");	
	        		if(onkeydown!=null){onkeydown=onkeydown.replace('\'', '&');} else { onkeydown=""; }
	        	onload = e.getAttribute("onload");	
	        		if(onload!=null){onload=onload.replace('\'', '&');} else { onload=""; }
	        	onclick = e.getAttribute("onclick");
	        		if(onclick!=null){onclick=onclick.replace('\'', '&');}  else { onclick=""; }
	    		nodeTitle = e.getAttribute("title");
	        	nodeText= e.getText();
	        		if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
	        	sql="[Info:] Node attributes :";
	        		sql+=" "+i+":";
	        		sql+=",tagName='"+tagName+"'";
		    		sql+=",nodeId='"+nodeId+"'";
		    		sql+=",nodeHref='"+nodeHref+"'";
		    		sql+=",nodeName='"+nodeName+"'";
		    		sql+=",nodeValue='"+nodeValue+"'";
		    		sql+=",nodeClass='"+nodeClass+"'";
		    		sql+=",nodeType='"+nodeType+"'";
		    		sql+=",onkeydown='"+onkeydown+"'";
		    		sql+=",onclick='"+onclick+"'";
		    		sql+=",onload='"+onload+"'";
		    		sql+=",nodeTitle='"+nodeTitle+"'";
		    		sql+=",nodeText='"+nodeText+"'";
	        	System.out.println(sql);
	        } 
        }catch(Exception e){
        	System.out.println("[Alert:] Can not get attributes from WebElement."+" toolbox.java, method=displayMulAtt");
        } 
        
	}
	public static void displayAllText(WebDriver driver,String target, String byMethod, String screenName){
		List<WebElement> allElements = null;
		try{	        
	        switch(byMethod){
		        case "xpath":
		        	allElements=driver.findElements(By.xpath(target));
		        	break;
		        case "id":
		        	allElements=driver.findElements(By.id(target));
		        	break;
		        case "css":
		        	allElements=driver.findElements(By.cssSelector(target));
		        	break;
		        case "name":
		        	allElements=driver.findElements(By.name(target));
		        	break;
		        case "className":
		        	allElements=driver.findElements(By.className(target));
		        	break;
		        case "tagName":
		        	allElements=driver.findElements(By.tagName(target));
		        	break;
	        }
	        System.out.println("[Info:] "+ screenName +" has "+allElements.size()+" nodes by "+target);      
		}catch(Exception e){
        	System.out.println("[Alert:] Cannot find elements by "+target);
        } 
		//utility.waitSecond(2); //wait for get attributes of SiteMinder , test it again
		try{
	        String tagName,nodeHref,nodeName,nodeClass,onkeydown,nodeType,nodeText,value;
	        String display;
        	int i=0;
        	boolean bNotEmpty = false;
	        for (WebElement e : allElements) {  
	        	i++;
//	        	tagName = e.getTagName();	        	
//	        	nodeHref = e.getAttribute("href");
//	        	nodeName = e.getAttribute("name");
//	        	nodeClass= e.getAttribute("class");	        	
//	        	nodeType= e.getAttribute("type");	   
//	        	onkeydown = e.getAttribute("onkeydown");
	        	value= e.getAttribute("value");
	        	nodeText= e.getText();
	        	bNotEmpty = false;
	        	display="[Info:] ";
	        	if(nodeText!=null){
	        		if(!nodeText.trim().equals("")){
	        			display=display + " [text=] " + nodeText;
	        			bNotEmpty = true;
	        		}
	        	}	        	
	        	if(value!=null){
	        		if(!value.trim().equals("")){
	        			display=display + " [value=] " + value;
	        			bNotEmpty = true;
	        		}
	        	}
	        	if(bNotEmpty){
	        		System.out.println(display);
	        	}
	        } 
        }catch(Exception e){
        	System.out.println("[Alert:] Can not get attributes from WebElement ."+" toolbox.java, method=displayAllText");
        } 
        
	}
	
}
