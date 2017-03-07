package core;

import misc.toolbox;
import db.ManageDB;
import db.RunDB;

public class productInfo {
	public static String getWebURL(String ProductName){
		String ProductServerPrefix="";String ProductServer="";
		String URL=loginStart.readXML("URL"); if(URL==null){URL="";}
		int iStart=URL.indexOf("//")+2;
		ProductServerPrefix=URL.substring(0,iStart);
		ProductServer=URL.substring(iStart);
		int iEnd=ProductServer.indexOf("/");
		if(iEnd==-1){
			System.out.println("[Alert:] Cannot get ProductServer info for "+ProductName);
		}else{ 
			ProductServer=ProductServer.substring(0,iEnd);
		}
		ProductServerPrefix=ProductServerPrefix+ProductServer;
		//System.out.println(ProductServerPrefix);
		String WebURL="";
		
		switch(ProductName){
			case "SiteMinder":	
				WebURL=ProductServerPrefix+"/iam/siteminder/console/";
			break;
			case "ITPAM":	
				WebURL=ProductServerPrefix+"/itpam/";
			break;			
			case "Clarity": 
				WebURL=ProductServerPrefix+"/niku/nu";
			break;
			case "ServiceCatalog": 
				WebURL=ProductServerPrefix+"/usm/";
			break;		
			case "Aspera": 
				WebURL=ProductServerPrefix+"/";
			break;	
			case "MCC": 
				WebURL=URL;
			break;	
		}
		if(ProductName.equals("unknown")){
			System.out.println("[Alert:] This project "+ProductName+" is not supported.");
			System.out.println("[Solution:] Please contact with the author to add support plug in code.");
		}
		return WebURL;
	}
	public static String getProductServerPort(String ProductName){
		String ProductServerPrefix="";String ProductServer="";
		String URL=loginStart.readXML("URL"); if(URL==null){URL="";}
		int iStart=URL.indexOf("//")+2;
		ProductServerPrefix=URL.substring(0,iStart);
		ProductServer=URL.substring(iStart);
		int iEnd=ProductServer.indexOf("/");
		if(iEnd==-1){
			System.out.println("[Alert:] Cannot get ProductServer info for "+ProductName);
		}else{
			ProductServer=ProductServer.substring(0,iEnd);
		}
		ProductServerPrefix=ProductServerPrefix+ProductServer;
		
		return ProductServerPrefix;
	}
	public static int getPageLoadTime(String ProductName){
		int iPageLoadTime=3;
		switch(ProductName){
			case "SiteMinder":	
				iPageLoadTime=0;
			break;
			case "Clarity": 
				iPageLoadTime=10;
			break;
		}
		return iPageLoadTime;
	}
	public static int getFindElementTime(String ProductName){
		int iFindElementTime=3;
		switch(ProductName){
			case "SiteMinder":	
				iFindElementTime=0;
			break;
			case "Clarity": 
				iFindElementTime=10;
			break;  
		}
		return iFindElementTime;
	}
	
	public static String getProductFilterForProduct(String ProductName){
		String ProductFilter="//a|//option[@value]|//input[@type]|//button|//img|//span|//td[@onclick]|//h4";
		//ProductFilter="ClickAndEditNow";
		switch(ProductName){
			case "unknown":
				ProductFilter="//option[@value]|//input[@type]|//a|//button|//div|//img|//span";
			break;
			case "MCC":
				ProductFilter="//a";//"//leaf";
			break;
			case "ITKO":
				ProductFilter = "//button|//input[@type='checkbox' and not(contains(@class,'ng-untouched'))]";
				//ProductFilter += "|//div/ul[@class='nav nav-list']//a|//div/a|//li/a|//a[contains(@ng-click,'View')]|//div[contains(@class,'name ng-binding')]";
				//ProductFilter += "|//div/ul[@class='nav nav-list']//a[not(contains(@class,'ng-untouched'))]|//div/a[not(contains(@class,'ng-untouched'))]|//li/a[not(contains(@class,'ng-untouched'))]|//a[contains(@ng-click,'View')]|//div[contains(@class,'name ng-binding')]";
				ProductFilter += "|//a[not(contains(@class,'ng-untouched')) and not(contains(@ng-click,'select(item)'))]|//div[contains(@class,'name ng-binding')]";
			break;
			case "CASM":
				ProductFilter="//a|//option[@value]|//input[@type]|//button|//img|//span|//td[@onclick]|//h4";
				ProductFilter="//input[@type]|//button|//option[@value]|//a[starts-with(@href,'?task.nest.tag=')]";
			break;
			case "SDM":
				ProductFilter="//td[@class='tab_container']/table/tbody/tr/td[2]/a";
			break;
			case "APM":
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "APM-CEM":
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "MAA":
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "ControlMinder":
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "CloudMonitor":	
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "Nimsoft":
				ProductFilter="//button[contains(@id,'item-group-btnEl')]";
			break;
			case "ITPAM": 				
				ProductFilter="//ul[@role='tablist']/li[contains(@class,'x-component')]/a[2]/em/span/span";
			break;
			case "SiteMinder":	
				ProductFilter="//option[@value]|//input[@type]|//a[@href='#']|//a[starts-with(@href,'?task.nest.tag=')]|//button";
			break;
			case "Aspera":	
				//ProductFilter="//input[@type]|//a[onclick]|//button|//div|//img|//span";
				ProductFilter="//nav/ul/li/a/span/img";	
			break;
			case "Clarity": 
				//miao
				ProductFilter="//span[@onclick]";
				ProductFilter+="|//span[@class='ppm_tab_inner']/span/a";
				ProductFilter+="|//div[@class='ppm_tab_menu']/table/tbody/tr/td/div/a";
				ProductFilter+="|//table[@class='ppm_grid']/tbody/tr[1]/td[1]/a";
				ProductFilter+="|//table[@class='ppm_grid']/tbody/tr[1]/td[2]/a";
				//<button xmlns:urlencoder="java:java.net.URLEncoder" type="button" onclick="navigateToURL('nu?action=npt.setObjectUserPartitions','navFromActionId=revmgr.departmentList','navToActionId=revmgr.departmentProperties','objectCode=department','ui.page.space=revmgr.departmentList');" class="ppm_button ">New</button>
				ProductFilter+="|//button[starts-with(@onclick,'navigateToURL')]"; 	
				//<img class="ui-browse-lookup-image" src="ui/uitk/images/s.gif" onclick="javascript:setParentArray('page','parent_department_id_text','parent_department_id');clarity.uitk.ext.browse.BrowseFieldExt.setDialogListener(this,false);openWindow('page','odf.browsePage','medium','attributeCode=parent_department_id','objectCode=department','','lookup_type=DPT_PARENT_DEPT_PARAMETERIZED','partitionCode=NIKU.ROOT','viewType=properties','browseCardinality=1','__param_entity_id_constrain=__entity_id','__param_department_code_constrain=__department_code','param_user_id_constrain=1');" onkeypress="javascript:if(clarity.uitk.Utility.checkEnter(event)){setParentArray('page','parent_department_id_text','parent_department_id');clarity.uitk.ext.browse.BrowseFieldExt.setDialogListener(this,false);openWindow('page','odf.browsePage','medium','attributeCode=parent_department_id','objectCode=department','','lookup_type=DPT_PARENT_DEPT_PARAMETERIZED','partitionCode=NIKU.ROOT','viewType=properties','browseCardinality=1','__param_entity_id_constrain=__entity_id','__param_department_code_constrain=__department_code','param_user_id_constrain=1');}" id="d31526259e1350" name="Browse" title="Browse Parent Department" alt="Browse Parent Department" tabindex="0" style="width: 17px; height: 22px; background: url(ui/uitk/images/fields.png) no-repeat -17px -20px" align="absmiddle">
				ProductFilter+="|//img[@onclick]";
				//mingzhe
				ProductFilter+="|//a[@class='ppm_button_menu_item']";
				ProductFilter+="|//span[@class='ppm_filter_icons']/span/img";
				//miao
				//<div id="ppm_workspace_bb" class="ppm_fixed_button_bar" style="display: none;"></div>
				ProductFilter+="|//div[@class='ppm_fixed_button_bar']";
//				ProductFilter="//a[starts-with(@href,'#action:')]";
//				ProductFilter+="|//input";
//				ProductFilter+="|//button";
//				ProductFilter+="|//span";
//				ProductFilter+="|//img";
//				ProductFilter+="|//tr/td/a[@xmlns:urlencoder='java:java.net.URLEncoder'][1]";
//				ProductFilter+="|//a[@href='javascript: void(0)']";
			break;
			case "ServiceCatalog":
//				ProductFilter="//a[starts-with(@href,'wpf?Node=')]|//a[@class='about']|//a[contains(@href,'javascript')]|//input|//button|//img|//span|//td[@onclick]";
//				ProductFilter+="|//div[@style='display: block;']//div/img[2]";
//				ProductFilter+="|//div[@style='display: block;']//div/span[2]";
				
				ProductFilter="//a[starts-with(@href,'wpf?Node=')]|//a[@class='about']|//a[contains(@href,'javascript')]";
//				ProductFilter+="|//a[@title='Click to Edit'][1]";
				ProductFilter+="|//a[contains(@title,'Click to Edit')][1]";
				ProductFilter+="|//a[@id='list_view']";
				ProductFilter+="|//input[contains(@name,'Btn')]|//input[contains(@name,'btn')]|//input[contains(@name,'Button')]|//input[contains(@name,'button')]";
				ProductFilter+="|//input[@name='add_field']|//input[@name='enabledate']";  
				ProductFilter+="|//img[@onclick]|//img[@class='x-form-trigger x-form-trigger-arrow ']";
				ProductFilter+="|//span[@onclick]|//span[@class='x-tree3-node-text']|//span[@class='x-tab-strip-text']";
				ProductFilter+="|//td[@onclick]";
			break;			
		}
		return ProductFilter;
	}
	public static String getProductType(String ProductName){
		String ProductType="MenuTag_NodeTag";
		switch(ProductName){
			case "MCC":	
				ProductType="MenuURL_NodeTag";
			break;
			case "SAAS":	
				ProductType="MenuTag_NodeTag";
			break;			
			case "APM":	
				ProductType="MenuTag_NodeTag";
			break;
			case "APM-CEM":	
				ProductType="MenuURL_NodeTag";
			break;
			case "SiteMinder":	
				ProductType="MenuURL_NodeTag";
			break;
			case "CloudMonitor":	
				ProductType="MenuURL_NodeTag";
			break;
			case "ControlMinder":	
				ProductType="MenuTag_NodeTag";
			break;
			case "MAA":	
				ProductType="MenuTag_NodeTag";
			break;
			case "Nimsoft":	
				ProductType="MenuTag_NodeTag";
			break;
			case "Aspera":	
				//ProductType="MenuURL_NodeTag";
				ProductType="MenuTag_NodeURLorTag";
			break;
			case "ITPAM":	
				ProductType="MenuTag_NodeTag";
			break;
			
			case "ServiceCatalog":
				ProductType="MenuURL_2Layer_NodeTag";
			break;
			case "Clarity": //Clarity - current
				ProductType="MenuURL_NodeURLorTag";  //one tag -> to many URLs or Tags
			break;
			case "Clarity2": 
				ProductType="MenuTag_NodeURL"; //one tag -> to many URLs
			break;
			case "Clarity3": 
				ProductType="MenuURL_NodeURL";
			break;
			case "Any": //Any
				ProductType="MenuTag_NodeTag";
			break;
		}
		
		return ProductType;
	}
	public static String getFeature(String ProductName,String nodeHref,String nodeTitle,String nodeText){
		String Feature="";
		switch(ProductName){
			case "SiteMinder":	
			
			break;
			case "ServiceCatalog":
				Feature=nodeText.trim();
			break;
			case "Clarity": 
				Feature=nodeTitle.trim();
			break;
		}
		
		return Feature;
	}
}
