package core;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import db.ManageDB;
import db.RunDB;
import misc.toolbox;

public class navigationPath {
	public static String initPath(String PluginAction,String ProjectID,String DBPosition,String DebugServer,String ProductName,String ProductURL,int iPageLoadTime, int iFindElementTime, String MenuFilter, String RunMode,boolean bUseHubNode, String ProductType, String PreferedBrowser)
	{
		String NodePath="000";
		String sExtractResult = "";
		WebDriver driver=null; 
		boolean bDebug=false; if(RunMode.contains("Debug")){bDebug=true;}
		boolean isLogin = false; 
 		boolean bDriverActive=false;
 		String Hub=loginStart.readXML("Hub");
		String HubConsole=Hub.replaceFirst("wd/hub", "grid/console");
		vectorCompare pv = new vectorCompare();
		try{
	 		try{ 
				driver=browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
				bDriverActive = true;
			}
	    	catch(Exception ex){
	    		RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Cannot start WebDriver session with local/remote hub.");
				RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Please check Hub with "+HubConsole);
	    	}		
	 		isLogin = customlibrary.LoginSetting.setLoginInfoAndStart(PluginAction,driver,ProjectID,DBPosition,NodePath, ProductURL,iPageLoadTime);
	 		if(!isLogin)
			{
	 			try{
	 				driver.quit();
	 			}catch(Exception ex){}
	 			try{ 
	 				driver = browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
	 			}
	 	    	catch(Exception ex){
	 	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Cannot login, launch 2 times ! ");
	 	    	}
	 			isLogin = customlibrary.LoginSetting.setLoginInfoAndStart(PluginAction,driver,ProjectID,DBPosition,NodePath, ProductURL,iPageLoadTime+5);
			}	
	 		if(!isLogin)
			{
	 			try{
	 				driver.quit();
	 			}catch(Exception ex){}
	 			try{ 
	 				driver = browserLaunch.launch(ProductURL,ProductName,ProjectID,DBPosition,NodePath,bUseHubNode,PreferedBrowser);
	 			}
	 	    	catch(Exception ex){
	 	    		RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Alert:] Cannot login, launch 3 times ! ");
	 	    	}
	 			bDriverActive = true;
	 			isLogin = customlibrary.LoginSetting.setLoginInfoAndStart(PluginAction,driver,ProjectID,DBPosition,NodePath,ProductURL,iPageLoadTime+10);
			}		 		
	 		if(isLogin){
	 			if(ProductName.equals("MAA")){
	 				System.out.println("[Info:] Login successfully for node path "+NodePath+". Refresh for MAA now.");
	 				driver.navigate().refresh();
	 			}
	 			if(ProductName.equals("SDM")){
	 				System.out.println("Start to turn to toolbarframe");
		        	driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='toolbarframe']")));
		        	System.out.println("Turn to toolbarframe successfully");
		        	RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Login successfully");
	 			}
	 			boolean bCheckPageLoadSpecifiedElement=true; boolean bCheckPageLoadFinish = true; 
				String pageSource="";String pageSourceAfterServeralSeconds="";
				//not extract for non HTML, so set false for bCheckPageLoadSpecifiedElement,bCheckPageLoadFinish
				bCheckPageLoadSpecifiedElement=false; bCheckPageLoadFinish=false;
				bCheckPageLoadSpecifiedElement=loginStart.checkPageLoadDone(driver,ProjectID,ProductName,DBPosition,"000");
				int iCheckLoadCounter=1;
				while(!bCheckPageLoadSpecifiedElement){
					iCheckLoadCounter++;
					if(iCheckLoadCounter>3){
						break;
					}
					RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] NodePath 000 page is NOT loaded. Wait "+iFindElementTime*iCheckLoadCounter+" seconds to load "+iCheckLoadCounter+" times.");
					toolbox.waitSecond(iFindElementTime);
					bCheckPageLoadSpecifiedElement=loginStart.checkPageLoadDone(driver,ProjectID,ProductName,DBPosition,"000");
				}
				if(bCheckPageLoadSpecifiedElement)
				{		
					if(ProductName.equals("CloudMonitor")){
						bCheckPageLoadFinish=true;
					}else{
						try{
							pageSource=driver.getPageSource();
						}catch(Exception eg){
							pageSource="null_1";
						}
						RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Specified html element of NodePath 000 page is loaded. Check other elements such as layer.");
						toolbox.waitSecond(5);
						try{
							pageSourceAfterServeralSeconds=driver.getPageSource();
						}catch(Exception eg1){
							pageSourceAfterServeralSeconds="null_2";
						}
						if(pageSourceAfterServeralSeconds.equals(pageSource)){
							RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] All elements of NodePath 000 page are loaded.");
							bCheckPageLoadFinish=true;
						}else{
							RunDB.addLog(ProjectID,DBPosition,NodePath,1, "[Info:] Other elements of NodePath 000 page are NOT loaded. Wait loading for several seconds ....");
							bCheckPageLoadFinish=false;
						}
					}
					int iCheckLoadFinishCounter=1;
					while(!bCheckPageLoadFinish){
						iCheckLoadFinishCounter++;
						if(iCheckLoadFinishCounter>4){
							break;
						}
						pageSource=pageSourceAfterServeralSeconds;
						toolbox.waitSecond(5);
						try{
							pageSourceAfterServeralSeconds=driver.getPageSource();
						}catch(Exception eg1){
							pageSourceAfterServeralSeconds="null_"+iCheckLoadFinishCounter;
						}
						if(pageSourceAfterServeralSeconds.equals(pageSource)){
							RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Other elements of NodePath 000 page are also loaded. All elements of NodePath 000 page are loaded.");
							bCheckPageLoadFinish=true;
						}else{RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Other elements of NodePath 000 page are NOT loaded. Wait loading for several seconds ....");
							bCheckPageLoadFinish=false;
						}
					}
				}
				if(bCheckPageLoadFinish){
		 			if(bDebug){
						toolbox.createSnapshot(driver,DBPosition,DebugServer,ProjectID,ProductName,"000",RunMode);
					}
		 			bDriverActive = true; boolean bCaptureSucess=false;
		 			//String mainURL=loginStart.readXML("URL");if(mainURL==null){mainURL="";}
		 			//if(ProductURL.equals(mainURL)){
		 				bCaptureSucess=toolbox.captureScrn("./output/"+ProjectID+"/screen/", "000.png", driver);
		 				if(bCaptureSucess){
		 					RunDB.addLog(ProjectID,DBPosition,NodePath,1,"[Info:] Success to capture main page: 000.png");
		 				}else{
		 					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Alert:] Initializatin failure, can not capture main page");
		 					RunDB.addLog(ProjectID,DBPosition,NodePath,2,"[Solution:] Please try to replace selinium lib files with correct lib files");
		 				}
		 			//}
					//Extract child nodes
					int mySeqAsParentSeq = 0;
					int LevelID=0; 
					String LastNodePathAsParentPath = "000"; String RootURLNodePath=""; String Feature="";
					
					RunDB.setFirstXpath(ProjectID, DBPosition,ProductName,MenuFilter);
					sExtractResult = extract.findChild(driver,iPageLoadTime,Feature,LastNodePathAsParentPath,RootURLNodePath, mySeqAsParentSeq, ProjectID,DBPosition,DebugServer,ProductName, MenuFilter,LevelID, RunMode,ProductType,"HTML", "a", "all");
				}else{
					System.out.println("[Alert:] Page loading failure. It can not be initialized. Drive will quit.");
				}
			}else{
	 			System.out.println("[Alert:] Login failure. It can not be initialized. Drive will quit.");
	 		}
	 		if(bDriverActive){
 				try{  
 			    	driver.quit(); 
 			    }catch(Exception exa){}
 			}
		}catch(Exception ex){
			if(bDriverActive){
 				try{  
 			    	driver.quit(); 
 			    }catch(Exception exb){}
 			}
		}
		return sExtractResult;
	}
	public static void initLayer2MenuPath(String PluginAction,String ProjectID,String DBPosition,String WebServer,String ProductName,int iPageLoadTime, int iFindElementTime, String ProjectFilter, String RunMode,boolean bUseHubNode, String ProductType,String PreferedBrowser)
	{
		RunDB.addLog(ProjectID,DBPosition,"000",1, "[Info:] Robot starts to extract layer2 menu URLs.\n");	
//		System.out.println("[Info:] Robot starts to extract layer2 menu URLs.\n");						
		ArrayList<String> nodePathArray = RunDB.getLayer1MenuURL(ProjectID,DBPosition);
		String NodePath=""; int iTotal=nodePathArray.size();String sql="";String nodeURL="";
		String sExtractResult = "";
		for(int k=0;k<iTotal;k++){
				NodePath=nodePathArray.get(k);
			
				sExtractResult="";
		 		ManageDB m0 = new ManageDB();
		 		m0.getConnection(DBPosition);
		 		try{
			 		sql="select * from NodeList where TagOrURL='URL' and mainFeature=1 and getSubMenu=0 and ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
			 		ResultSet rs0=m0.getSQL(sql);
			 		if(rs0.next()){
			 			nodeURL=rs0.getString("MenuHref");
			 		}			 		
			 		rs0.close();
		 		}catch(Exception em0){}
		 		m0.close();
		 		sExtractResult = navigationPath.initPath(PluginAction,ProjectID,DBPosition,WebServer,ProductName,nodeURL,iPageLoadTime,iFindElementTime,ProjectFilter,RunMode, bUseHubNode,ProductType,PreferedBrowser);
		 		if(sExtractResult.contains("success")){
		 			ManageDB m1 = new ManageDB();
		 			m1.getConnection(DBPosition);
		 			m1.updateSQL("update NodeList set getSubMenu=1 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
		 			m1.close();
		 		}
	 		
		}	
	}
	public static void clickPath(int iAgentID,String ProjectID,String DBPosition,String DebugServer,String ProductName,String PluginAction,int iPageLoadTime,int iFindElementTime, int max_instance,int iLevelID,int iStartID,int iEndID,String FeatureTree,String RunMode,boolean bUseHubNode,int iMaxDepth,String PreferedBrowser){
		ExecutorService pool = Executors.newFixedThreadPool(max_instance);   
		ArrayList<String> nodePathArray = RunDB.getNodePath(iAgentID,ProjectID,DBPosition,max_instance,iLevelID,iStartID,iEndID,FeatureTree,PluginAction,RunMode); //Get all untouchonce nodes
		String NodePath=""; int iTotal=nodePathArray.size();
		//iTotal=0; //debug
		for(int k=0;k<iTotal;k++){
			NodePath=nodePathArray.get(k);
			concurrentPath cp= new concurrentPath();
			cp.ProjectID=ProjectID;
			cp.DBPosition=DBPosition;
			cp.DebugServer=DebugServer;
			cp.ProductName=ProductName;
			cp.PluginAction=PluginAction;
			cp.iPageLoadTime=iPageLoadTime;
			cp.iFindElementTime=iFindElementTime;
			cp.RunMode=RunMode;
			cp.iMaxDepth=iMaxDepth;
			cp.bUseHubNode=bUseHubNode;
			cp.PreferedBrowser=PreferedBrowser;
			cp.NodePath=NodePath;
			try{
				Thread t = new Thread(cp);
				pool.execute(t);
			}catch(Exception ex){
				System.out.println("[Alert:] Hub/Nodes may have many inactive threads with poor performance. Try restart them.");
			}
		}
		try{
			pool.shutdown(); 
			while(!pool.isTerminated())
			{
					toolbox.waitSecond(10);	
			}   
		}catch(Exception ex3){}
	}
	public static void clickOneNode(boolean bTestPath,String ProjectID,String DBPosition,String DebugServer,String ProductName,String PluginAction,int iPageLoadTime, int iFindElementTime, String NodePath, int max_instance,String RunMode,boolean bUseHubNode,String PreferedBrowser){
		ExecutorService pool = Executors.newFixedThreadPool(max_instance);   
		concurrentPath cp= new concurrentPath();
		cp.ProjectID=ProjectID;
		cp.DBPosition=DBPosition;
		cp.DebugServer=DebugServer;
		cp.ProductName=ProductName;
		cp.PluginAction=PluginAction;
		cp.iPageLoadTime=iPageLoadTime;
		cp.iFindElementTime=iFindElementTime;
		cp.RunMode=RunMode;
		cp.bTestPath=bTestPath;
		cp.bUseHubNode=bUseHubNode;
		cp.PreferedBrowser=PreferedBrowser;
		cp.NodePath=NodePath;
		try{
			Thread t = new Thread(cp);
			pool.execute(t);
		}catch(Exception ex){
			System.out.println("[Alert:] Hub/Nodes may have many inactive threads with poor performance. Try restart them.");
		}
		try{
			pool.shutdown(); 
			while(!pool.isTerminated())
			{
					toolbox.waitSecond(10);	
			}   
		}catch(Exception ex1){}
	}
}
