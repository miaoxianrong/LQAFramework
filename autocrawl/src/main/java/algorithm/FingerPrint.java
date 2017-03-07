package algorithm;


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




public class FingerPrint
{
	ArrayList<ArrayList<Integer>> fpList = new ArrayList<ArrayList<Integer>>();
	
	public String getUniqueObjectAndFingerPrintAttributeBAK(String ParentNodePath,String NodePath,int ParentSeq,String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText,String nodeSrc, String nodeTarget, String nodeNgclick, String TagOrURL){
		int attributeFP=0;
		String uniqueObject="NONE";		
		String UniqueObjectAndFingerPrintAttribute="";
		
		String MenuPath="";
		int iPos=NodePath.indexOf("-");		
		if(iPos>-1){
			MenuPath=NodePath.substring(0,iPos);
		}else{
			MenuPath="000";
		}
		if(tagName.equals("a")&&TagOrURL.equals("URL")){
			attributeFP=1;
		}
		if(ProductName.equals("ITKO")){
			if(tagName.equals("a")&&TagOrURL.equals("Tag")){
				if(!nodeTitle.isEmpty()){
					uniqueObject="nodeTitle="+nodeTitle;
					attributeFP=1;
				}
				if(!nodeText.isEmpty()){
					uniqueObject="nodeText="+nodeText;
					attributeFP=1;
				}
				if(nodeClass.isEmpty()&&nodeTitle.isEmpty()&&nodeText.isEmpty()){
					uniqueObject="strangetag";		        					
					if(!nodeNgclick.isEmpty()){
						uniqueObject=nodeNgclick.replace(',', '&');
					}
					attributeFP=1;
				}
				if(nodeNgclick.contains("selected")){
					uniqueObject="nodeText="+nodeText;
					attributeFP=1;	
				}
				if(nodeNgclick.contains("grid.getCellValue")){
					uniqueObject=MenuPath+"_nodeNgclick_"+nodeNgclick;
					uniqueObject=uniqueObject.replace(',', '&');
	    			attributeFP=1;
				}
			}
			if(tagName.equals("button")){
				uniqueObject="nodeText="+nodeText;
				attributeFP=1;
			}
		}
		if(ProductName.equals("CASM")){
    		if(tagName.equals("option")){
    			uniqueObject="OptionValue="+nodeValue;
    			attributeFP=1;
    		}
    		if(tagName.equals("input")&&nodeType.equals("image")&&nodeName.contains("Filter")){
    			uniqueObject="input_image_"+nodeName;
    			attributeFP=1;
    		}
    		if(tagName.equals("input")&&nodeType.equals("submit")&&nodeName.contains("tabchange")){
    			uniqueObject=MenuPath+"_input_submit_"+nodeValue;
    			attributeFP=1;
    		}
    		if(tagName.equals("a")){
    			if(nodeTitle.contains("View")){
    				uniqueObject=MenuPath+"_a_title=View";
    				attributeFP=1;
    			}
    			if(nodeTitle.contains("Modify")){
    				uniqueObject=MenuPath+"_a_title=Modify";
    				attributeFP=1;
    			}
    			if(nodeTitle.contains("Delete")){
    				uniqueObject=MenuPath+"_a_title=Delete";
    				attributeFP=1;
    			}
    		}
		}
		UniqueObjectAndFingerPrintAttribute=String.valueOf(attributeFP)+attributeFP;
		return UniqueObjectAndFingerPrintAttribute;
	}
	public boolean getFingerPrintBAK(WebDriver driver,String ProjectID,String ProductName,String DBPosition,String NodePath,int iFindElementTime,String tagName, String nodeType, String nodeName, int attributeFP)
	{
		double timer_begin=System.currentTimeMillis()/1000.000;
		double timer_end=System.currentTimeMillis()/1000.000;
		double time_spent;
		
		System.out.println("[Info:] The method getFingerPrint starts to analysis...");
		String nodeText="";
		StringTokenizer stNodeText = null;
		String newNodeText="";
		
		
		String PlocBeforeChar="[[";
		String PlocAfterChar="]]";		
		int iBefore=0;
		int iAfter=0;
		int iAfter2=0;
		String PLOCString="";
		String PUID="";
		
		ManageDB m = new ManageDB();
		m.getConnection(DBPosition);
		String sql="";
		boolean bHasNewString=false;
		boolean bHasNewStructure=false;
		boolean bUniqueFingerPrint=false;
		m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
		try{			
			toolbox.waitSecond(iFindElementTime);			
			try{
				timer_begin = System.currentTimeMillis()/1000.000;
				String PageFilter="//body";  //option to get dropdown, //** to get tooltip
//				if(ProductName.equals("ITKO")){
//					PageFilter="//a|//h3|//h4|//h6|//div[not(contains(@class, 'Date'))]";
//					PageFilter+="|//strong|//tab-heading|//span|//button|//label|//option";
//					//    //a[text()]
//				}
//				System.out.println("PageFilter="+PageFilter);
				String allText=driver.findElement(By.xpath(PageFilter)).getText(); 
				if(allText!=null){
					nodeText=allText.replace('\'', ' ');
					nodeText=nodeText.replace(',', ' ');
					nodeText=nodeText.replace('%', ' ');
				} else { 
					nodeText=""; 
				}					
	        	nodeText=nodeText.trim();
	        	
	        	if(ProductName.equals("ITKO")){
	        		//for PUID only start
		        	if(!nodeText.isEmpty()){
		        		stNodeText =new StringTokenizer(nodeText,PlocAfterChar);
		        		newNodeText="";
		        		while(stNodeText.hasMoreTokens()){
		        			PLOCString=stNodeText.nextToken()+PlocAfterChar;
		        			iBefore=PLOCString.indexOf(PlocBeforeChar);
							iAfter=PLOCString.indexOf(PlocAfterChar);
							iAfter2=iAfter+2;
							try{
								if(iBefore>-1&&iAfter2>iBefore){
									PUID=PLOCString.substring(iBefore,iAfter2);
								}
							}catch(Exception ek){
								System.out.println("[Alert:] Find out a mapping issue. PLOCString is "+PLOCString);
							}
		        			newNodeText+=PUID;
		        		}	        		
		        	} 
		        	//for PUID only end 		        	 
	        	}else{
	        		// for all strings, not only for PLOC strings start
		        	if(!nodeText.isEmpty()){
		        		stNodeText =new StringTokenizer(nodeText);
		        		newNodeText="";
		        		while(stNodeText.hasMoreTokens()){
		        			newNodeText+=stNodeText.nextToken();
		        		}	        		
		        	}   
		        	// for all strings, not only for PLOC strings end
	        	}
	        	
	        	int len1=newNodeText.length();
		        if(len1>2000){
		        	newNodeText=newNodeText.substring(0,1999);
		        }
				//System.out.println("newNodeText="+newNodeText);
				
	        	sql="select NodePath from FingerPrintText where ProjectID='"+ProjectID+"'";
				sql+=" and Content='"+newNodeText+"'";
				//System.out.println("sql="+sql);
				try{
					ResultSet rs=m.getSQL(sql);
					if(rs.next()){
						String OtherNodePath=rs.getString("NodePath");
						System.out.println("[Info:] NodePath "+NodePath+" has the same UI strings with other NodePath "+OtherNodePath);
						//RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" has the same UI strings with other NodePath "+OtherNodePath);
						bHasNewString=false;
					}else{
						bHasNewString=true;
					}
					rs.close();
					rs=null;
				}catch(Exception ers){}
				if(bHasNewString){					
					sql="insert into FingerPrintText set ProjectID='"+ProjectID+"'";
	        		sql+=",NodePath='"+NodePath+"'";
	        		sql+=",Content='"+newNodeText+"'";
	        		m.updateUnique(sql); 
				}else{
					//System.out.println("[Info:] existing the same UI strings...............");
				}
				timer_end = System.currentTimeMillis()/1000.000;
				time_spent = timer_end - timer_begin;	
				System.out.println("[Info:] allText time spent: " + time_spent + " seconds");
				
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}		
		}
		//catch(UnhandledAlertException e)
		catch(Exception e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		m.close();
		/****************  The below is structure ***************/
		String structure=""; String structure250="";
		boolean bSpecialFingerPrint=false;
		if(attributeFP==1){
			bSpecialFingerPrint=true;
			bHasNewStructure=true;		
		}
		if(ProductName.equals("CASM")){
			if(tagName.equals("option")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;			   	
			}
			if(tagName.equals("input")&&nodeType.equals("image")&&nodeName.contains("Filter")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;		
			}
			if(tagName.equals("input")&&nodeType.equals("submit")&&nodeName.contains("tabchange")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;	// not false	
			}
		}
		if(bSpecialFingerPrint){	
			System.out.println("[Info:] NodePath " + NodePath + " is a special FingerPrint which may be a unique object");
    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath + " is a special FingerPrint");
		}
		else{
			try{
				int i=0;
				int sum = 0;
				List<WebElement> count = null;
				try{
					count = driver.findElements(By.cssSelector("body *")); 	
					sum = count.size();
				}catch(Exception eae){
					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrFingerPrint, can not run findElements for "+ NodePath);
	     		}			
				structure=String.valueOf(sum);			 
				if(sum>0){ //driver is normal
					String xml_level = "body > *"; //xpath="//body/*"; Direct child
					for (i = 1; i < 200; i++) 
					{
						sum = 0;
						//if(i==10||i==20||i==30||i==40||i==50||i==110||i==120||i==130||i==140||i==150||i==199){ 
							count = driver.findElements(By.cssSelector(xml_level));
							sum = count.size();
							if (sum == 0) 
							{				
								break;
							}
							structure+=String.valueOf(count.size());
						//}
						xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
					}	
				}
			}
			catch(UnhandledAlertException e)
			{
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
			}
			ManageDB m28 = new ManageDB();
			m28.getConnection(DBPosition);
			structure250=structure; 
			int len=structure250.length();
	        if(len>250){
	        	structure250=structure.substring(0,250);
	        }
			String sql_update="update NodeList set vector='"+structure250+"',uniquevector=1 where "; //branch=1,leaf=0 is removed
		    sql_update+=" ProjectID='"+ProjectID+"' ";
		    sql_update+=" and NodePath='"+NodePath+"'";
		    bHasNewStructure = m28.updateUnique(sql_update);
		    m28.close();
		}
		
	    if(bHasNewString||bHasNewStructure){
	    	bUniqueFingerPrint=true;
	    	if(bHasNewString){
	    		System.out.println("[Info:] NodePath " + NodePath + " has new strings");//: "+newNodeText);
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has new strings");
	    	}
	    	if(bHasNewStructure){	    		
	    		System.out.println("[Info:] NodePath " + NodePath + " has new structure: "+structure250);
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has new structure");
	    	}
	    }else{
	    	System.out.println("[Info:] NodePath " + NodePath + " has no new strings and has no new structure, it may be removed soon.");
	    	RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has no new strings and has no new structure, it may be removed soon.");
	    }	    
//		timer_end = System.currentTimeMillis()/1000.000;
//		time_spent = timer_end - timer_begin;	
//		System.out.println("[Info:] getFingerPrint total time spent: " + time_spent + " seconds");
		return bUniqueFingerPrint;
	}
	public boolean getFingerPrintForPLOCBAK(WebDriver driver,String ProjectID,String ProductName,String DBPosition,String NodePath,int iFindElementTime,String tagName, String nodeType, String nodeName, int attributeFP)
	{
		double timer_begin=System.currentTimeMillis()/1000.000;
		double timer_end=System.currentTimeMillis()/1000.000;
		double time_spent;
		
		System.out.println("[Info:] The method getFingerPrint starts to analysis...");
		String nodeText="";
		StringTokenizer stNodeText = null;
		String newNodeText="";
		
		
		String PlocBeforeChar="[[";
		String PlocAfterChar="]]";		
		int iBefore=0;
		int iAfter=0;
		int iAfter2=0;
		String PLOCString="";
		String PUID="";
		
		ManageDB m = new ManageDB();
		m.getConnection(DBPosition);
		String sql="";
		boolean bHasNewString=false;
		boolean bHasNewStructure=false;
		boolean bUniqueFingerPrint=false;
		m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
		try{			
			toolbox.waitSecond(iFindElementTime);			
			try{
				timer_begin = System.currentTimeMillis()/1000.000;
				String PageFilter="//body";  //option to get dropdown, //** to get tooltip
//				if(ProductName.equals("ITKO")){
//					PageFilter="//a|//h3|//h4|//h6|//div[not(contains(@class, 'Date'))]";
//					PageFilter+="|//strong|//tab-heading|//span|//button|//label|//option";
//					//    //a[text()]
//				}
//				System.out.println("PageFilter="+PageFilter);
				String allText=driver.findElement(By.xpath(PageFilter)).getText(); 
				if(allText!=null){
					nodeText=allText.replace('\'', ' ');
					nodeText=nodeText.replace(',', ' ');
					nodeText=nodeText.replace('%', ' ');
				} else { 
					nodeText=""; 
				}					
	        	nodeText=nodeText.trim();
	        	
	        	//for PUID only start
	        	if(!nodeText.isEmpty()){
	        		stNodeText =new StringTokenizer(nodeText,PlocAfterChar);
	        		newNodeText="";
	        		while(stNodeText.hasMoreTokens()){
	        			PLOCString=stNodeText.nextToken()+PlocAfterChar;
	        			iBefore=PLOCString.indexOf(PlocBeforeChar);
						iAfter=PLOCString.indexOf(PlocAfterChar);
						iAfter2=iAfter+2;
						try{
							if(iBefore>-1&&iAfter2>iBefore){
								PUID=PLOCString.substring(iBefore,iAfter2);
							}
						}catch(Exception ek){
							System.out.println("[Alert:] Find out a mapping issue. PLOCString is "+PLOCString);
						}
	        			newNodeText+=PUID;
	        		}	        		
	        	} 
	        	//for PUID only end 
	        	
	        	int len1=newNodeText.length();
		        if(len1>2000){
		        	newNodeText=newNodeText.substring(0,1999);
		        }
				//System.out.println("newNodeText="+newNodeText);
				
	        	sql="select NodePath from FingerPrintText where ProjectID='"+ProjectID+"'";
				sql+=" and Content='"+newNodeText+"'";
				//System.out.println("sql="+sql);
				try{
					ResultSet rs=m.getSQL(sql);
					if(rs.next()){
						String OtherNodePath=rs.getString("NodePath");
						System.out.println("[Info:] NodePath "+NodePath+" has the same UI strings with other NodePath "+OtherNodePath);
						//RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" has the same UI strings with other NodePath "+OtherNodePath);
						bHasNewString=false;
					}else{
						bHasNewString=true;
					}
					rs.close();
					rs=null;
				}catch(Exception ers){}
				if(bHasNewString){					
					sql="insert into FingerPrintText set ProjectID='"+ProjectID+"'";
	        		sql+=",NodePath='"+NodePath+"'";
	        		sql+=",Content='"+newNodeText+"'";
	        		m.updateUnique(sql); 
				}else{
					//System.out.println("[Info:] existing the same UI strings...............");
				}
				timer_end = System.currentTimeMillis()/1000.000;
				time_spent = timer_end - timer_begin;	
				System.out.println("[Info:] allText time spent: " + time_spent + " seconds");
				
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}		
		}
		//catch(UnhandledAlertException e)
		catch(Exception e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		m.close();
		/****************  The below is structure ***************/
		String structure=""; String structure250="";
		boolean bSpecialFingerPrint=false;
		if(attributeFP==1){
			bSpecialFingerPrint=true;
			bHasNewStructure=true;		
		}
		if(ProductName.equals("CASM")){
			if(tagName.equals("option")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;			   	
			}
			if(tagName.equals("input")&&nodeType.equals("image")&&nodeName.contains("Filter")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;		
			}
			if(tagName.equals("input")&&nodeType.equals("submit")&&nodeName.contains("tabchange")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;	// not false	
			}
		}
		if(bSpecialFingerPrint){	
			System.out.println("[Info:] NodePath " + NodePath + " is a special FingerPrint which may be a unique object");
    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath + " is a special FingerPrint");
		}
		else{
			try{
				int i=0;
				int sum = 0;
				List<WebElement> count = null;
				try{
					count = driver.findElements(By.cssSelector("body *")); 	
					sum = count.size();
				}catch(Exception eae){
					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrFingerPrint, can not run findElements for "+ NodePath);
	     		}			
				structure=String.valueOf(sum);			 
				if(sum>0){ //driver is normal
					String xml_level = "body > *"; //xpath="//body/*"; Direct child
					for (i = 1; i < 200; i++) 
					{
						sum = 0;
						//if(i==10||i==20||i==30||i==40||i==50||i==110||i==120||i==130||i==140||i==150||i==199){ 
							count = driver.findElements(By.cssSelector(xml_level));
							sum = count.size();
							if (sum == 0) 
							{				
								break;
							}
							structure+=String.valueOf(count.size());
						//}
						xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
					}	
				}
			}
			catch(UnhandledAlertException e)
			{
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
			}
			ManageDB m28 = new ManageDB();
			m28.getConnection(DBPosition);
			structure250=structure; 
			int len=structure250.length();
	        if(len>250){
	        	structure250=structure.substring(0,250);
	        }
			String sql_update="update NodeList set vector='"+structure250+"',uniquevector=1 where "; //branch=1,leaf=0 is removed
		    sql_update+=" ProjectID='"+ProjectID+"' ";
		    sql_update+=" and NodePath='"+NodePath+"'";
		    bHasNewStructure = m28.updateUnique(sql_update);
		    m28.close();
		}
		
	    if(bHasNewString||bHasNewStructure){
	    	bUniqueFingerPrint=true;
	    	if(bHasNewString){
	    		System.out.println("[Info:] NodePath " + NodePath + " has new strings");//: "+newNodeText);
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has new strings");
	    	}
	    	if(bHasNewStructure){	    		
	    		System.out.println("[Info:] NodePath " + NodePath + " has new structure: "+structure250);
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has new structure");
	    	}
	    }else{
	    	System.out.println("[Info:] NodePath " + NodePath + " has no new strings and has no new structure, it may be removed soon.");
	    	RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has no new strings and has no new structure, it may be removed soon.");
	    }	    
//		timer_end = System.currentTimeMillis()/1000.000;
//		time_spent = timer_end - timer_begin;	
//		System.out.println("[Info:] getFingerPrint total time spent: " + time_spent + " seconds");
		return bUniqueFingerPrint;
	}
	public boolean getFingerPrintForText(WebDriver driver,String ProjectID,String ProductName,String DBPosition,String NodePath,int iFindElementTime,String tagName, String nodeType, String nodeName, int attributeFP)
	{
		double timer_begin=System.currentTimeMillis()/1000.000;
		double timer_end=System.currentTimeMillis()/1000.000;
		double time_spent;
		
		System.out.println("[Info:] The method getFingerPrint starts to analysis...");
		String nodeText="";
		StringTokenizer stNodeText = null;
		String newNodeText="";
		
		
		String PlocBeforeChar="[[";
		String PlocAfterChar="]]";		
		int iBefore=0;
		int iAfter=0;
		int iAfter2=0;
		String PLOCString="";
		String PUID="";
		
		ManageDB m = new ManageDB();
		m.getConnection(DBPosition);
		String sql="";
		boolean bHasNewString=false;
		boolean bHasNewStructure=false;
		boolean bUniqueFingerPrint=false;
		m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
		try{			
			toolbox.waitSecond(iFindElementTime);			
			try{
				timer_begin = System.currentTimeMillis()/1000.000;
				String PageFilter="//body";  //option to get dropdown, //** to get tooltip
//				if(ProductName.equals("ITKO")){
//					PageFilter="//a|//h3|//h4|//h6|//div[not(contains(@class, 'Date'))]";
//					PageFilter+="|//strong|//tab-heading|//span|//button|//label|//option";
//					//    //a[text()]
//				}
//				System.out.println("PageFilter="+PageFilter);
				String allText=driver.findElement(By.xpath(PageFilter)).getText(); 
				if(allText!=null){
					nodeText=allText.replace('\'', ' ');
					nodeText=nodeText.replace(',', ' ');
					nodeText=nodeText.replace('%', ' ');
				} else { 
					nodeText=""; 
				}					
	        	nodeText=nodeText.trim();
	        	// for all strings, not only for PLOC strings start
	        	if(!nodeText.isEmpty()){
	        		stNodeText =new StringTokenizer(nodeText);
	        		newNodeText="";
	        		while(stNodeText.hasMoreTokens()){
	        			newNodeText+=stNodeText.nextToken();
	        		}	        		
	        	}   
	        	// for all strings, not only for PLOC strings end
	        	int len1=newNodeText.length();
	        	int len2000=0;
		        if(len1>2000){
		        	len2000=len1-2000;
		        	newNodeText=newNodeText.substring(len2000);//newNodeText.substring(0,1999);
		        }
				//System.out.println("newNodeText="+newNodeText);
				
	        	sql="select NodePath from FingerPrintText where ProjectID='"+ProjectID+"'";
				sql+=" and Content='"+newNodeText+"'";
				//System.out.println("sql="+sql);
				try{
				ResultSet rs=m.getSQL(sql);
				if(rs.next()){
					String OtherNodePath=rs.getString("NodePath");
					System.out.println("[Info:] NodePath "+NodePath+" has the same UI strings with other NodePath "+OtherNodePath);
					//RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+" has the same UI strings with other NodePath "+OtherNodePath);
					bHasNewString=false;
				}else{
					bHasNewString=true;
				}
				rs.close();
				rs=null;
				}catch(Exception ers){}
				if(bHasNewString){					
					sql="insert into FingerPrintText set ProjectID='"+ProjectID+"'";
	        		sql+=",NodePath='"+NodePath+"'";
	        		sql+=",Content='"+newNodeText+"'";
	        		m.updateUnique(sql); 
				}else{
					//System.out.println("[Info:] existing the same UI strings...............");
				}
				timer_end = System.currentTimeMillis()/1000.000;
				time_spent = timer_end - timer_begin;	
				System.out.println("[Info:] allText time spent: " + time_spent + " seconds");
				
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}		
		}
		//catch(UnhandledAlertException e)
		catch(Exception e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		m.close();
		
		
		//bHasNewString=true;
		
		
		/****************  The below is structure ***************/
		String structure=""; String structure250="";
		boolean bSpecialFingerPrint=false;
		if(attributeFP==1){
			bSpecialFingerPrint=true;
			bHasNewStructure=true;		
		}
		if(ProductName.equals("CASM")){
			if(tagName.equals("option")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;			   	
			}
			if(tagName.equals("input")&&nodeType.equals("image")&&nodeName.contains("Filter")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;		
			}
			if(tagName.equals("input")&&nodeType.equals("submit")&&nodeName.contains("tabchange")){
				bSpecialFingerPrint=true;
				bHasNewStructure=true;	// not false	
			}
		}
		if(bSpecialFingerPrint){	
			System.out.println("[Info:] NodePath " + NodePath + " is a special FingerPrint which may be a unique object");
    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath + " is a special FingerPrint");
		}
		else{
			try{
				int i=0;
				int sum = 0;
				List<WebElement> count = null;
				try{
					count = driver.findElements(By.cssSelector("body *")); 	
					sum = count.size();
				}catch(Exception eae){
					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrFingerPrint, can not run findElements for "+ NodePath);
	     		}			
				structure=String.valueOf(sum);			 
				if(sum>0){ //driver is normal
					String xml_level = "body > *"; //xpath="//body/*"; Direct child
					for (i = 1; i < 200; i++) 
					{
						sum = 0;
						//if(i==10||i==20||i==30||i==40||i==50||i==110||i==120||i==130||i==140||i==150||i==199){ 
							count = driver.findElements(By.cssSelector(xml_level));
							sum = count.size();
							if (sum == 0) 
							{				
								break;
							}
							structure+=String.valueOf(count.size());
						//}
						xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
					}	
				}
			}
			catch(UnhandledAlertException e)
			{
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
			}
			ManageDB m28 = new ManageDB();
			m28.getConnection(DBPosition);
			structure250=structure; 
			int len=structure250.length();
	        if(len>250){
	        	structure250=structure.substring(0,250);
	        }
			String sql_update="update NodeList set vector='"+structure250+"',uniquevector=1 where "; //branch=1,leaf=0 is removed
		    sql_update+=" ProjectID='"+ProjectID+"' ";
		    sql_update+=" and NodePath='"+NodePath+"'";
		    bHasNewStructure = m28.updateUnique(sql_update);
		    m28.close();
		}
		
	    if(bHasNewString||bHasNewStructure){
	    	bUniqueFingerPrint=true;
	    	if(bHasNewString){
	    		System.out.println("[Info:] NodePath " + NodePath + " has new strings");//: "+newNodeText);
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has new strings");
	    	}
	    	if(bHasNewStructure){	    		
	    		System.out.println("[Info:] NodePath " + NodePath + " has new structure: "+structure250);
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has new structure");
	    	}
	    }else{
	    	System.out.println("[Info:] NodePath " + NodePath + " has no new strings and has no new structure, it may be removed soon.");
	    	RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] NodePath "+NodePath+ " has no new strings and has no new structure, it may be removed soon.");
	    }	    
//		timer_end = System.currentTimeMillis()/1000.000;
//		time_spent = timer_end - timer_begin;	
//		System.out.println("[Info:] getFingerPrint total time spent: " + time_spent + " seconds");
		return bUniqueFingerPrint;
	}
	
	public String getOldFingerPrintBAK(WebDriver driver,String DOMType,String NodePath,String ProjectID,String DBPosition,String ProductName,int iFindElementTime,boolean bNewFeature)
	{		
		System.out.println("[Info:] The method getFingerPrint starts to get FingerPrint...");
		String fp="";String FingerPrintText;
		try{
			int i=0;
			int sum = 0;
			String nodeText;
			List<WebElement> count = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
				count = driver.findElements(By.cssSelector("body *")); 	
				sum = count.size();
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrFingerPrint, can not run findElements for "+ NodePath);
			 }
			if(sum==0){ 				
				if(DOMType.equals("frame")){ 
					String frameXPATH="//frame[2]";	
					switch(ProductName){
			    		case "ServiceCatalog": 
			    			frameXPATH="//frame[2]";	
			    		break;
					}
					try{ 
						driver.switchTo().frame(driver.findElement(By.xpath(frameXPATH)));		//Duplication switch, it has been executed 	in PathConcurrent			
						count = driver.findElements(By.cssSelector("body *")); 	
						sum = count.size();
					}catch(Exception eae){
						RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] driver.switchTo().frame(driver.findElement failure. Driver is out of control, or can not run findElements for "+ NodePath);
					}
				}
			}
			fp=String.valueOf(sum);
			 
			if(bNewFeature){//bNewFeature for input value, etc.
				FingerPrintText=ProjectID;
				double timer_begin_fp = System.currentTimeMillis()/1000.000;
				for (WebElement e : count) {  
					nodeText= e.getText();if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
					FingerPrintText=FingerPrintText+nodeText;
				}
				double timer_end_fp = System.currentTimeMillis()/1000.000;
				double time_spent_fp = timer_end_fp - timer_begin_fp;		
				System.out.println("[Info:] Get FingerPrintText time spent: " + time_spent_fp + " seconds"); //330.6710000038147 seconds ->Multiple threads
				
				String sql="update NodeList set vectorText='"+FingerPrintText+"' where ";
				sql+=" ProjectID='"+ProjectID+"' ";      			
				sql+=" and NodePath='"+NodePath+"'";
				//try{
					ManageDB m5=new ManageDB();
					m5.getConnection(DBPosition);
					m5.updateSQL(sql);   
					m5.close();
				 //}catch(Exception ex){
					 //RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Check FingerPrint.getStr or DB connection");
//					 System.out.println("[DB info:] Check FingerPrint.getStr or DB connection");
				 //}
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
						fp+=String.valueOf(count.size());
					//}
					xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
				}	
			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		return fp;
	}
	public boolean analysisUIStringForLeafPageBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		System.out.println("[Info:] The method analysisUIStringForLeafPage starts to get UI String...");
		String fp="";boolean bDifferentUI = false; 
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
				//System.out.println("[Info:] sum = "+sum);
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}			
			String tagName,nodeId,nodeName,nodeValue,nodeTitle,nodeText,display;
			StringTokenizer stNodeText = null;
			String newNodeText="";
			String limitedNodeText250="";
			String allText="";
			if(sum>0){ 				
				ManageDB m = new ManageDB();
				m.getConnection(DBPosition);
				ResultSet rs = null;
				String sql="";
				boolean bInsertSuccess=false;
				m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
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
		        	nodeText=nodeText.trim();
		        	if(seq<90){		        	
		        	if(!nodeText.isEmpty()){
		        		stNodeText =new StringTokenizer(nodeText);
		        		newNodeText="";
		        		while(stNodeText.hasMoreTokens()){
		        			newNodeText+=stNodeText.nextToken();
		        		}
		        		limitedNodeText250=newNodeText;
		        		if(newNodeText.length()>250){
		        			limitedNodeText250=newNodeText.substring(0,250);
		        		}
		        		sql="insert into FingerPrintText set ProjectID='"+ProjectID+"'";
		        		sql+=",NodePath='"+NodePath+"'";
		        		sql+=",Content='"+limitedNodeText250+"'";
		        		bInsertSuccess=m.updateUnique(sql); 
		        		if(bInsertSuccess){
		        			System.out.println(" allText= "+allText);
		        			System.out.println(seq+" TEXT= "+newNodeText);
		        			if(allText.indexOf(newNodeText)==-1){
				        		allText+=newNodeText;
				        		System.out.println("/********* new string *********/ ");
				        	}else{
				        		System.out.println("/-------- included string ---------/ ");
				        	}
		        		}			        	
		        	}
		        	}
		        	
		        	if(nodeText.contains("Error")){
		        		bLeafPage = true;
		        		//commented by 0623
//		        		System.out.println(seq+" Error TEXT= "+nodeText);
		        		
		        		
		        		//System.out.println(seq+" TEXT");		        		
		        		//System.out.println(seq+" TEXT= "+nodeText);
		        		//System.out.println("11 nodeText = "+nodeText);
		        	}
				}				
				rs=null;
				m.close();
			}
		}
		//catch(UnhandledAlertException e)
		catch(Exception e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		return bLeafPage;
	}
	public boolean webElementFingerPrintBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		double timer_begin = System.currentTimeMillis()/1000.000;
		System.out.println("[Info:] The method strFingerPrint starts to analysis...");
		boolean bHasNewString = false; 
		try{
			int sum = 0;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime); 
			try{
				String PageFilter="/html/head/title|//span|//div|//button|//a|//input|//option";
				allElements = driver.findElements(By.xpath(PageFilter)); 
				sum = allElements.size();
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}			
			String nodeText="";
			StringTokenizer stNodeText = null;
			String newNodeText="";
			String limitedNodeText250="";
			if(sum>0){ 				
				ManageDB m = new ManageDB();
				m.getConnection(DBPosition);
				String sql="";
				boolean bInsertSuccess=false;
				m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");

				int seq=0;
				for (WebElement e : allElements) {  
					seq ++;
					nodeText= e.getText();
					if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }
					
		        	nodeText=nodeText.trim();
		        	if(!nodeText.isEmpty()){
		        		stNodeText =new StringTokenizer(nodeText);
		        		newNodeText="";
		        		while(stNodeText.hasMoreTokens()){
		        			newNodeText+=stNodeText.nextToken();
		        		}
		        		limitedNodeText250=newNodeText;
		        		if(newNodeText.length()>250){
		        			limitedNodeText250=newNodeText.substring(0,250);
		        		}
		        		sql="insert into FingerPrintText set ProjectID='"+ProjectID+"'";
		        		sql+=",NodePath='"+NodePath+"'";
		        		sql+=",Content='"+limitedNodeText250+"'";
		        		bInsertSuccess=m.updateUnique(sql); 
		        	}		        	
				}				
				m.close();
				if(bInsertSuccess){
					bHasNewString=true;
				}
			}
		}
		//catch(UnhandledAlertException e)
		catch(Exception e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		double timer_end = System.currentTimeMillis()/1000.000;
		double time_spent = timer_end - timer_begin;	
		System.out.println("[Info:] strFingerPrint time spent: " + time_spent + " seconds");
		return bHasNewString;
	}
	public boolean getFP_StringBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		double timer_begin=System.currentTimeMillis()/1000.000;
		double timer_end=System.currentTimeMillis()/1000.000;
		double time_spent;
		System.out.println("[Info:] The method getFP_String starts to analysis...");
		boolean bHasNewString = false; 
		String nodeText="";
		StringTokenizer stNodeText = null;
		String newNodeText="";
		String limitedNodeText250="";
		ManageDB m = new ManageDB();
		m.getConnection(DBPosition);
		String sql="";
		try{
			int sum = 0;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime);			
			try{
				timer_begin = System.currentTimeMillis()/1000.000;
				String PageFilter="//body";
				String allText=driver.findElement(By.xpath(PageFilter)).getText(); 
				if(allText!=null){
					nodeText=allText.replace('\'', ' ');
					nodeText=nodeText.replace(',', ' ');
				} else { 
					nodeText=""; 
				}					
	        	nodeText=nodeText.trim();
	        	if(!nodeText.isEmpty()){
	        		stNodeText =new StringTokenizer(nodeText);
	        		newNodeText="";
	        		while(stNodeText.hasMoreTokens()){
	        			newNodeText+=stNodeText.nextToken();
	        		}	        		
	        	}       
				System.out.println("newNodeText="+newNodeText);
				//System.out.println(allText);
				System.out.println("limitedNodeText250="+limitedNodeText250);
				timer_end = System.currentTimeMillis()/1000.000;
				time_spent = timer_end - timer_begin;	
				System.out.println("[Info:] allText time spent: " + time_spent + " seconds");
				
				
				
				timer_begin = System.currentTimeMillis()/1000.000;
				PageFilter="/html/head/title|//span|//div|//button|//a|//input|//option";
				allElements = driver.findElements(By.xpath(PageFilter)); 
				sum = allElements.size();
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get UIContext, can not run findElements for "+ NodePath);
     		}			
			
			
			
			if(sum>0){ 				
				
				boolean bInsertSuccess=false;
				m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
				//m.updateSQL("delete from FingerPrintText where ProjectID='"+ProjectID+"'");
				int seq=0;
				for (WebElement e : allElements) {  
					seq ++;
					nodeText= e.getText();
					if(nodeText!=null){nodeText=nodeText.replace('\'', '&');} else { nodeText=""; }					
		        	nodeText=nodeText.trim();
		        	if(!nodeText.isEmpty()){
		        		stNodeText =new StringTokenizer(nodeText);
		        		newNodeText="";
		        		while(stNodeText.hasMoreTokens()){
		        			newNodeText+=stNodeText.nextToken();
		        		}
		        		limitedNodeText250=newNodeText;
		        		if(newNodeText.length()>250){
		        			limitedNodeText250=newNodeText.substring(0,250);
		        		}
		        		sql="insert into FingerPrintText set ProjectID='"+ProjectID+"'";
		        		sql+=",NodePath='"+NodePath+"'";
		        		sql+=",Content='"+limitedNodeText250+"'";
		        		//System.out.println("[FingerPrintText Info:] sql="+sql);
		        		//bInsertSuccess=m.updateUnique(sql); 
		        	}		        	
				}	
			}
			
//			int iNewStringNumber=0; String newStringNumber="";
//			sql="select count(*) from FingerPrintText where ProjectID='"+ProjectID+"'";
//			sql+=" and NodePath='"+NodePath+"'";
//			ResultSet rs=m.getSQL(sql);
//			if(rs.next()){
//				iNewStringNumber=rs.getInt(1);
//			}
//			rs.close();
//			newStringNumber=iNewStringNumber+"";
//			rs=null;
//			sql="update NodeList set newStringNumber="+newStringNumber;
//			sql+=" where ProjectID='"+ProjectID+"'";
//			sql+=" and NodePath='"+NodePath+"'";
//			m.updateUnique(sql); 
//			m.close();
//			if(iNewStringNumber>0){
//				bHasNewString=true;
//			}else{
//				bHasNewString=false;
//			}
		}
		//catch(UnhandledAlertException e)
		catch(Exception e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		timer_end = System.currentTimeMillis()/1000.000;
		time_spent = timer_end - timer_begin;	
		System.out.println("[Info:] sourceFingerPrint time spent: " + time_spent + " seconds");
		return bHasNewString;
	}
	public boolean getUIContextBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
	{
		System.out.println("[Info:] Start to get UIContext...");
		String fp="";boolean bDifferentUI = false;
		try{
			int sum = 0;
			List<WebElement> allElements = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			try{
				String PageFilter="/html/head/title|//span|//div|//button|//a|//input|//option";
				allElements = driver.findElements(By.xpath(PageFilter)); 
//				allElements = driver.findElements(By.cssSelector("body *")); 	
				sum = allElements.size();
				//System.out.println("[Info:] sum = "+sum);
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
		        	
//					 if(nodeText.indexOf("]]")!=-1){
//		        		st1 =new StringTokenizer(nodeText,"]]");
//						while(st1.hasMoreTokens()){
//							uiContext=st1.nextToken().replace('\n', ' ').trim()+"]]";
//							uiContext=uiContext.substring(2,uiContext.length()-9);
//							sql="insert into UIText set Content='"+uiContext+"', ProjectID='"+ProjectID+"'";
//							bUnique = mdb.updateUnique(sql);
//							if(bUnique){
//								bDifferentPage = true;
//							}
//							System.out.println(uiContext);
//						}
//		        	}
				}
				mdb.close();
//				double timer_end = System.currentTimeMillis()/1000.000;
//				double time_spent = timer_end - timer_begin;	
//				
//				System.out.println("[Info:] Total display time spent: " + time_spent+ " seconds");	
			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		return bDifferentUI;
	}
	public boolean getUIContextNoThreadBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
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
				//System.out.println("[Info:] sum = "+sum);
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
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] get getUIContextNoThread time spent: " + time_spent1 + " seconds");	
 		//[Info:] get getUIContextNoThread time spent: 87.50200009346008 seconds
		return bDifferentUI;
	}
	public boolean getUIContextFromFileBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime)
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
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] End to get UIContext. NodePath "+NodePath+" get "+iNewString+" new strings,"+iNewPLOCString+" new PLOC strings,"+iNewFuzzyNumber+" new fuzzy strings. Time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds");
		return bDifferentUI;
	}
	public boolean getUIStringFromSnapshotBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime) throws Exception
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
//			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
//		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		String loginfo="[Info:] Get UIContext sucessfully. NodePath "+NodePath+" get "+iNewString+" new strings,"+iNewPLOCString+" new PLOC strings,"+iNewFuzzyNumber+" new fuzzy strings. Time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds";
		System.out.println(loginfo);
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,loginfo);
		return bDifferentUI;
	}
	
	public boolean searchStringFromSnapshotBAK(WebDriver driver,String ProjectID,String DBPosition,String NodePath,int iFindElementTime) throws Exception
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
//			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
//		}
		double timer_end1 = System.currentTimeMillis()/1000.000;
		double time_spent1 = timer_end1 - timer_begin1;	
		String loginfo="[Info:] Get UIContext sucessfully. NodePath "+NodePath+" get "+iNewString+" new strings,"+iNewPLOCString+" new PLOC strings,"+iNewFuzzyNumber+" new fuzzy strings. Time spent: " + String.valueOf(time_spent1).substring(0,3) + " seconds";
		System.out.println(loginfo);
		RunDB.addLog(ProjectID,DBPosition,NodePath,1,loginfo);
		return bDifferentUI;
	}
	
	public String getFP_StructureBAK(WebDriver driver,String DOMType,String NodePath,String ProjectID,String DBPosition,String ProductName,int iFindElementTime,boolean bNewFeature)
	{
		double timer_begin=System.currentTimeMillis()/1000.000;
		double timer_end=System.currentTimeMillis()/1000.000;
		double time_spent;
		System.out.println("[Info:] The method getFP_Structure starts to analysis...");
		String structure="";
		try{
			int i=0;
			int sum = 0;
			List<WebElement> count = null;
			toolbox.waitSecond(iFindElementTime); //if remove, will get an error: Element is no longer attached to the DOM
			timer_begin=System.currentTimeMillis()/1000.000;
			try{
				count = driver.findElements(By.cssSelector("body *")); 	
				sum = count.size();
			}catch(Exception eae){
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Driver is not normal after clicking. can not get StrFingerPrint, can not run findElements for "+ NodePath);
     		}			
			structure=String.valueOf(sum);			 
			if(sum>0){ //driver is normal
				String xml_level = "body > *"; //xpath="//body/*"; Direct child
				for (i = 1; i < 200; i++) 
				{
					sum = 0;
					//if(i==10||i==20||i==30||i==40||i==50||i==110||i==120||i==130||i==140||i==150||i==199){ 
						count = driver.findElements(By.cssSelector(xml_level));
						sum = count.size();
						if (sum == 0) 
						{				
							break;
						}
						structure+=String.valueOf(count.size());
					//}
					xml_level = xml_level + " > *"; //xpath="//body/*/*/*/*/*/*/*"; Direct child
				}	
			}
		}
		catch(UnhandledAlertException e)
		{
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getStrFingerPrint failure. Return fp=0 ");
		}
		timer_end=System.currentTimeMillis()/1000.000;
		time_spent = timer_end - timer_begin;	
		System.out.println("[Info:] Structure of FingerPrint time spent: " + time_spent + " seconds");
		return structure;
		
	}
	public String getPartFingerPrintBAK(WebDriver driver,String ProjectFilter,String ParentFilter,int LevelID,String NodePath,String ProjectID,String DBPosition,String ProductName,int iFindElementTime,boolean bNewFeature)
	{
		String fp="";int seq=0;int iSpecialCount=0;	int sum = 0;
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
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getPartFingerPrint failure.");
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
//			        					fp=""+seq;
//			        				}else{
//			        					fp=fp+"-"+seq;
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
			RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Drive is not normal after clicking. getPartFingerPrint failure. Return fp=0_0 ");
//			System.out.println("[Error:] getStrFingerPrint failure. "+ e.getMessage());
		}
		fp=iSpecialCount+"_"+fp;
		return fp;
	}
}

