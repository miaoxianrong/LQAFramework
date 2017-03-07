package customlibrary;

import java.sql.ResultSet;

import core.DOMFilter;
import core.productInfo;
import db.ManageDB;
import db.RunDB;

public class FilterSetting {
	
	public static String setMenuFilter(String ProjectID,String ProductName,String DBPosition){
		String MenuFilter=RunDB.getFirstXpath(ProjectID, ProductName, DBPosition);
		boolean bProdMatch=false; String ReturnMenuFilter="";
		if(MenuFilter.isEmpty()){
			
			switch(ProductName){		
				case "CloudMonitor":	
					MenuFilter="//a[starts-with(@id,'MENU_')]|//a[contains(@class,'sign-out')]";
					bProdMatch=true;
				break;
				case "APM-TeamCenter":
					MenuFilter = "//a[contains(@class,'viewMenuItem')]";
					bProdMatch=true;
				break;
				case "APM-WebView":
					MenuFilter = "//span[@class='GMDBJMCDDXC']";
					bProdMatch=true;
				break;
				case "APM-CEM":
					MenuFilter = "//a[contains(@name,'Menu')]|//a[@name='cem']";
					bProdMatch=true;
				break;
				case "MAA":
					MenuFilter = "//a[starts-with(@href,'#/')]";
					//"//a[starts-with(@href,'#global')]";
					// "//a[starts-with(@href,'#/a')]";
					bProdMatch=true;
				break;
				case "MCC":
					MenuFilter = "//a[contains(@ng-href,'#tenant:')]";
					MenuFilter += "|//a[@ng-click='yrAcc()']";
					//"//a[starts-with(@href,'#global')]";
					// "//a[starts-with(@href,'#/a')]";
					/*
					 * <a class="panel-link panel-link-dashboard" id="Dashboard" data-nav="#tenant:dashboard" ng-href="#tenant:dashboard" href="#tenant:dashboard">
		                <span class="panel-icon ui-icon-dashboard"></span>
		                Dashboard
		                <span class="ui-notification-badge"></span>
		              </a>
					 */
					/*
    				 * <a id="yrAcc" href="" ng-click="yrAcc()" ng-hide="isMasteradminFlow" class="ng-binding">Your Account</a>
    				 */
					bProdMatch=true;
				break;
				case "ITKO":
					//MenuFilter = "//button|//input[@type='checkbox']";
					MenuFilter = "//button|//input[@type='checkbox' and not(contains(@class,'ng-untouched'))]";
					//MenuFilter += "|//div/ul[@class='nav nav-list']//a[not(contains(@class,'ng-untouched'))]|//div/a[not(contains(@class,'ng-untouched'))]|//li/a[not(contains(@class,'ng-untouched'))]|//a[contains(@ng-click,'View')]|//div[contains(@class,'name ng-binding')]";
					MenuFilter += "|//div/ul[@class='nav nav-list']//a|//a[not(contains(@class,'ng-untouched'))]|//div[contains(@class,'name ng-binding')]";
					
					MenuFilter = "//a[@ng-click='select(item);']"; ////div/ul[@class='nav nav-list']//a
					bProdMatch=true;
				break;
				case "CASM":
					MenuFilter = "//a[starts-with(@href,'?task.tag=')]";
					bProdMatch=true;
				break;
				case "SiteMinder":
					MenuFilter = "//a[starts-with(@href,'?task.tag=')]";
					bProdMatch=true;
				break;
				case "SPP":
					MenuFilter = "//a";
					bProdMatch=true;
				break;
				case "SAAS":
					MenuFilter = "//a";
					bProdMatch=true;
				break;		
				case "Aspera":	
					MenuFilter="//a[starts-with(@href,'/ca-manual/')]";
					bProdMatch=true;
				break;				
				
				case "ControlMinder":	
					MenuFilter="//div/ul/li/a[starts-with(@href,'?task.tag=')]";
					bProdMatch=true;
				break;
				case "Nimsoft":	
					MenuFilter="//button[contains(@id,'item-group-btnEl')]";
					bProdMatch=true;
				break;
				case "Clarity": 
					MenuFilter="//a[starts-with(@href,'#action:')]";
					bProdMatch=true;
				break;
				case "ServiceCatalog": 
					MenuFilter="//a[starts-with(@href,'wpf?Node=')]";
					bProdMatch=true;
				break;				
			}
			if(bProdMatch){
				ReturnMenuFilter=MenuFilter+"|//name_menu";
			}else{
				MenuFilter=customlibrary.FilterSetting.setProductFilter(ProductName);
				ReturnMenuFilter=MenuFilter+"|//name_menu";
			}
			try{
				ManageDB m1 = new ManageDB();
				m1.getConnection(DBPosition);
				//System.out.println("[Info:] Insert MenuFilter into DB");
				String sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
				sql=sql+",ParentName='-1'";
				sql=sql+",XPATHName='menu'";
				sql=sql+",PathSeq=1";
				sql=sql+",XpathPath='1'";
				String XPATH=MenuFilter;
				XPATH=XPATH.replace('\'', '&');
				sql=sql+",XPATH='"+XPATH+"'";
				m1.updateSQL(sql);		
				m1.close();
			}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}	
		}else{
			ReturnMenuFilter=MenuFilter;
		}			
		return ReturnMenuFilter;
	}
	public static String setProductFilter(String ProductName){
		String ProductFilter="//a|//option[@value]|//input[@type]|//button|//img|//span|//td[@onclick]|//h4";
		//ProductFilter="ClickAndEditNow";
		switch(ProductName){
			case "unknown":
				ProductFilter="//option[@value]|//input[@type]|//a|//button|//div|//img|//span";
			break;
			case "CloudMonitor":
				ProductFilter="//a[contains(@onclick,'_desc_more_')]";
				ProductFilter+="|//div[@class='learnmorebox']//td";
				ProductFilter+="|//div[@class='learnmorecontent']//a[@class='blue more']";
				//ProductFilter+="|//input[contains(@onclick,'new_monitor_dialog')]";
				ProductFilter+="|//input[contains(@class,'formbtn')]";
				ProductFilter+="|//a[@content='advanced_monitor_div']|//tr[@class='centered more-row']//a";
				ProductFilter+="|//div[@class='monitor_row']/span";
				ProductFilter+="|//a[contains(@onclick,'expert')]";
				ProductFilter+="|//a[@class='fancy ga-event']";
				ProductFilter+="|//input[contains(@class,'formbtn-dark')]";
				ProductFilter+="|//div[contains(@id,'button')]//button";
				ProductFilter+="|//div[contains(@id,'tab')]//button";
			break;	
			case "APM-TeamCenter":
				ProductFilter="//td[contains(@class,'ribbon-menu-item')]/a";
				ProductFilter+="|//div[@id='ca-navbar-top']/div[4]|//a[@id='generateAPIKeyMenuEntry']";
				ProductFilter+="|//button[contains(@class,'btn btn-tertiary dropdown-toggle') or contains(@class,'t-add-perspective')]";
				ProductFilter+="|//i[contains(@class,'glyphicon glyphicon-calendar')]";
			break;
			case "APM-WebView":	
				ProductFilter="//em/span/span[@class='GMDBJMCDASC']";
				ProductFilter+="|//span[@class='webview-Common-ListItem']";
			break;
			case "APM-CEM":
				ProductFilter="//a[not(contains(@href,'Detail.html')) and not(contains(@href,'default.html')) and not(starts-with(@href,'http')) and not(contains(@href,'help')) and not(contains(@href,'logout')) and not(starts-with(@href, '?')) and not(contains(@id,'Tree'))]|//div[contains(@id,'ehelpItem_item')]//a";
				//ProductFilter+="|//table[@class='gridTable']/tbody/tr[1]//a";
				ProductFilter+="|//input[@type !='text' and not(contains(@name,'close')) and not(contains(@name,'save')) and not(contains(@name,'delete')) and not(contains(@name,'stop')) and not(contains(@name,'finish')) and not(contains(@name,'export')) and not(contains(@name,'enable')) and not(contains(@name,'search')) and not(contains(@name,'disable')) and not(contains(@class,'Checkbox')) and not(contains(@class,'image'))]";
				ProductFilter+="|//table[@class='ehelpTableClass']//a";
				break;
			case "MAA":
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "MCC":
				//ProductFilter="//a";//"//leaf";
				ProductFilter="//a[@ng-click]";
				ProductFilter+="|//button";
				ProductFilter+="|//div[@ng-click]"; 
				ProductFilter+="|//input[@type='radio']"; 
			break;
			case "ITKO":
				//ProductFilter = "//button|//input[@type='checkbox' and not(contains(@class,'ng-untouched'))]";
				ProductFilter = "//input[@type='checkbox' and not(contains(@class,'ng-untouched'))]";
				//ProductFilter += "|//div/ul[@class='nav nav-list']//a|//div/a|//li/a|//a[contains(@ng-click,'View')]|//div[contains(@class,'name ng-binding')]";
				//ProductFilter += "|//div/ul[@class='nav nav-list']//a[not(contains(@class,'ng-untouched'))]|//div/a[not(contains(@class,'ng-untouched'))]|//li/a[not(contains(@class,'ng-untouched'))]|//a[contains(@ng-click,'View')]|//div[contains(@class,'name ng-binding')]";
				ProductFilter += "|//a[not(contains(@class,'ng-untouched')) and not(contains(@ng-click,'select(item)'))]|//div[contains(@class,'name ng-binding')]";
			break;
			case "CASM":
				ProductFilter="//a|//option[@value]|//input[@type]|//button|//img|//span|//td[@onclick]|//h4";
				ProductFilter="//input[@type]|//button|//option[@value]|//a[starts-with(@href,'?task.nest.tag=')]";
			break;
			case "SiteMinder":	
				ProductFilter="//a|//option[@value]|//input[@type]|//button|//img|//span|//td[@onclick]|//h4";
				ProductFilter="//option[@value]|//input[@type]|//a[@href='#']|//a[starts-with(@href,'?task.nest.tag=')]|//button";
				ProductFilter="//input[@type]|//button|//option[@value]|//a[starts-with(@href,'?task.nest.tag=')]";
			break;
			case "SDM":
				ProductFilter="//td[@class='tab_container']/table/tbody/tr/td[2]/a";
			break;
			case "APM":
				ProductFilter="//a|//option|//input|//button|//img|//span";
			break;
			case "ControlMinder":
				/*ProductFilter="//a[@href and @href !='#'and @href !='?' and not(contains(@href,'help'))and not(contains(@href,'about'))and not(contains(@href,'logout'))and not(contains(@href,'_octo'))]|"
				+ "//input[@type !='image'and not(contains(text(),'Close'))and not(contains(text(),'Clear'))and not(contains(text(),'Cancel'))and not(contains(text(),'Refresh'))]|"
				 + "//button";*/
				ProductFilter="//a[contains(@href,'javascript:toggleTableHideShowRow')]|//a[contains(@href,'lnkAdvSearchWorldview')]|"
				+ "//a[contains(@href,'javascript:resetSearchOperator')]|//a[starts-with(@id,'tab')]|//a[contains(@href,'lnkAdvSearchWorldview')]|"
				+ "//input[contains(@id,'main:btnGo')]|//input[contains(@class,'im-button-ok')]|//input[contains(@name,'action.standardsearch.search')]|"
				+ "//input[contains(@name,'tabchange')]|//input[contains(@id,'main:showDialog')]|//input[contains(@id,'main:btnBrowse')]|"
				+ "//input[contains(@id,'main:hostBrowse')]|//input[contains(@id,'main:btnSearchObjectSearch')]";	
				//ProductFilter="//a|//option|//input|//button|//img|//span";				
			break;
			
			case "Nimsoft":
				ProductFilter="//button[contains(@id,'item-group-btnEl')]";
			break;
			case "ITPAM": 				
				ProductFilter="//ul[@role='tablist']/li[contains(@class,'x-component')]/a[2]/em/span/span";
			break;
			
			case "Aspera":	
				//ProductFilter="//input[@type]|//a[onclick]|//button|//div|//img|//span";
				ProductFilter="//nav/ul/li/a/span/img";	
			break;
			case "Clarity": 
				ProductFilter="//span[@onclick]";
				ProductFilter+="|//span[@class='ppm_tab_inner']/span/a";
				ProductFilter+="|//div[@class='ppm_tab_menu']/table/tbody/tr/td/div/a";
				ProductFilter+="|//table[@class='ppm_grid']/tbody/tr[1]/td[1]/a";
				ProductFilter+="|//table[@class='ppm_grid']/tbody/tr[1]/td[2]/a"; 
				ProductFilter+="|//button[starts-with(@onclick,'navigateToURL')and not(contains(text(),'Return'))and not(contains(text(),'Clear'))]";
				ProductFilter+="|//img[@onclick]";
				//mingzhe
				ProductFilter+="|//a[@class='ppm_button_menu_item']";
				ProductFilter+="|//span[@class='ppm_filter_icons']/span/img";
				ProductFilter+="|//button[contains(text(),'New')]";
				ProductFilter+="|//button[@class='ppm_umenu_button' and contains(text(),'Configure')]";
				//miao
				ProductFilter+="|//div[@class='ppm_fixed_button_bar']";
			break;
			case "Clarity-bak": 
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
	public static boolean setExcluder(String ParentNodePath,String NodePath,int ParentSeq,String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText,String nodeSrc, String nodeTarget, String nodeNgclick)
	{
		boolean bValid = true;
		String PageFilter="";
		ParentFilter=ParentFilter.replace('&','\'');
		String logInfo="";		
		boolean bSwitch = false; //bSwitch = true; 
		//CloudMonitor
		if(nodeHref.contains("adobe.com")){
			bValid = false;					
		}
		if(nodeHref.contains("apache.org")){
			bValid = false;					
		}
		//MCC
		if(ProductName.equals("MCC")){
			if(nodeHref.contains("help")){
				bValid = false;					
			}
		}
		if(nodeHref.contains("www.ca.com")){
			bValid = false;					
		}
		if(nodeHref.contains("google.com")){
			bValid = false;					
		}
		//ITKO
		if(ProductName.equals("LISA")||ProductName.equals("ITKO")){
			if(nodeHref.contains("ogout")){ //logout
				bValid = false;					
			}
		}
		if(nodeHref.contains("wiki.")){
			bValid = false;					
		}		
		//CASM
		if(nodeText.contains("Close")||nodeText.contains("Cancel")){ 
			bValid = false;
		}
		if(nodeValue.contains("Close")||nodeValue.contains("Cancel")){ 
			bValid = false;
		}		
    	switch(ProductName){    
    		case "CloudMonitor":   
    			if(tagName.equals("a")){
    				if(nodeClass.equals("help ga-event")){
    					bValid = false;
    				}
    				if(nodeText.contains("Pseudo")){
    					bValid = false;
    				}
    				if(nodeHref.contains("logviewer.php")){
    					bValid = false;
    				}
    			}
    			if(tagName.equals("div")){
    				if(nodeId.equals("language")||nodeId.equals("regions")){
    					bValid = false;
    				}
    			}
	            if(nodeClass.contains("help ga-event")||nodeId.contains("language")||nodeHref.contains("www.ca.com")||nodeHref.contains("docops.ca.com")||nodeHref.contains("iwassosm.ca.com")||nodeHref.contains("samlgwsm.ca.com")){
	               bValid = false;
	            }
	            if(tagName.equals("span")){
    				if(nodeId.contains("MENU_")){
    					bValid = false;
    				}
    			}
	            String newURL=nodeHref.replaceAll("/","_");	           
	            nodeHref=newURL;
	            if(nodeHref.contains("_en")||nodeHref.contains("_nl")||nodeHref.contains("_de")||nodeHref.contains("_es")||nodeHref.contains("_fr")||nodeHref.contains("_it")||nodeHref.contains("_ja")||nodeHref.contains("pt_br")||nodeHref.contains("_zh_cn")){
					bValid = false;
				}
            break;
	    	case "MCC":
				if(nodeText.contains("CANCEL")||nodeText.contains("BACK")||nodeText.contains("DONE")){
					bValid = false;
				}
			break;	    	
	    	case "MAA":
	    		String hrefTail="";
                int iStart=0;
                int iLength=nodeHref.length();
                iStart=nodeHref.indexOf("7080");
                if(iStart>0&&iStart<iLength){
		    		hrefTail=nodeHref.substring(iStart);
		    		if(hrefTail.equals("7080/admin/maa/#")){
		    			bValid = false;
		    		}
                }
	    	break;
	    	case "ITKO": 
	    		
				if(nodeTarget.equals("_blank")){
					bValid = false;					
				}
				if(nodeNgclick.contains("link")){//goToLink(link)
					bValid = false;		
				}
				if(nodeNgclick.contains("selectPage")){
					bValid = false;		
				}
				if(nodeNgclick.contains("reload")){
					bValid = false;		
				}
				if(nodeNgclick.contains("refresh")){
					bValid = false;		
				}
				if(nodeClass.contains("dashboard")){
					bValid = false;		
				}
			break;
	    	case "CASM":
	    		if(tagName.equals("input")){ //delete multi, search, clear , Name(SearchSort)
					if(nodeName.contains("Sort")||nodeName.contains("multiDelete")||nodeName.contains("clear")||nodeName.contains("standardsearch.search")||nodeName.contains("ResultPage")){
						bValid = false; //||nodeName.contains("Filter")
					}
					if(nodeType.equals("hidden")||nodeType.equals("text")){ //||nodeType.equals("image")
						bValid = false;
					}
					if(nodeType.equals("image")){
						if(nodeName.equals("action.Filter.qfi_1")||nodeName.equals("action.Filter.qfd_0")){ //Insert After , action.Filter.qfd_0 delete since Vetor page does not change
							//bValid = false;
						}
						if(nodeId.contains("horizontal")){
							//bValid = false;
						}
						if(nodeName.contains("arameter")){
							bValid = false;
						}
					}
				}
				if(tagName.equals("option")){ 
					if(nodeValue.contains("CA.SM")){
						if(nodeText.contains("Affiliate Agent")){
							
						}else{
							bValid = false;
						}
					}
					if(nodeValue.equals("")||nodeValue.contains("NAME")||nodeValue.equals("Desc")){
						bValid = false;
					}
					if(nodeValue.equals("EQUALS")||nodeValue.contains("_")||nodeValue.equals("CONTAINS")){
						bValid = false;
					}
				}
				if(nodeType.equals("checkbox")){ 
					if(nodeName.contains("IsAgent4x")){ // Agent Type settings
						
					}else{
						bValid = false;
					}
				}
				
	    	break;
	    	case "SiteMinder":
	    		if(tagName.equals("input")){ //delete multi, search, clear , Name(SearchSort)
					if(nodeName.contains("Sort")||nodeName.contains("multiDelete")||nodeName.contains("clear")||nodeName.contains("standardsearch.search")||nodeName.contains("ResultPage")){
						bValid = false; //||nodeName.contains("Filter")
					}
					if(nodeType.equals("hidden")||nodeType.equals("text")){ //||nodeType.equals("image")
						bValid = false;
					}
					if(nodeType.equals("image")){
						if(nodeName.equals("action.Filter.qfi_1")||nodeName.equals("action.Filter.qfd_0")){ //Insert After , action.Filter.qfd_0 delete since Vetor page does not change
							//bValid = false;
						}
						if(nodeId.contains("horizontal")){
							//bValid = false;
						}
						if(nodeName.contains("arameter")){
							bValid = false;
						}
					}
				}
				if(tagName.equals("option")){ 
					if(nodeValue.contains("CA.SM")){
						if(nodeText.contains("Affiliate Agent")){
							
						}else{
							bValid = false;
						}
					}
					if(nodeValue.equals("")||nodeValue.contains("NAME")||nodeValue.equals("Desc")){
						bValid = false;
					}
					if(nodeValue.equals("EQUALS")||nodeValue.contains("_")||nodeValue.equals("CONTAINS")){
						bValid = false;
					}
				}
				if(nodeType.equals("checkbox")){ 
					if(nodeName.contains("IsAgent4x")){ // Agent Type settings
						
					}else{
						bValid = false;
					}
				}
				
	    	break;
			case "SiteMinder_BAK":	
				if(tagName.equals("a")){
						if(nodeHref.contains("#")){
							if(onclick.contains("toggleMenu")){ //left main menu
								bValid = false;
								System.out.println("[Debug:] Exclude toggleMenu");
							}
							if(nodeTitle==null){ //left sub menu under main menu
								bValid = false;
								System.out.println("[Debug:] Exclude nodeTitle==null");
							}
							else{
								if(nodeTitle.contains("")){ //Different with title="Create IDP -> SP Partnership"
									bValid = false;
									System.out.println("[Debug:] Exclude nodeTitle.contains()");
								}
							}
						}
				}
				if(tagName.equals("input")){ //delete multi, search, clear , Name(SearchSort)
					if(nodeName.contains("Sort")||nodeName.contains("multiDelete")||nodeName.contains("clear")||nodeName.contains("standardsearch.search")||nodeName.contains("ResultPage")){
						bValid = false;
					}
					if(nodeType.equals("hidden")||nodeType.equals("text")||nodeType.equals("checkbox")){ //||nodeType.equals("image")
						bValid = false;
					}
					if(nodeType.equals("image")){
						if(nodeName.equals("action.Filter.qfi_1")||nodeName.equals("action.Filter.qfd_0")){ //Insert After , action.Filter.qfd_0 delete since Vetor page does not change
							bValid = false;
						}
					}

				}
				if(tagName.equals("option")){ //only name, desc, starts with, ....etc.
					//if(nodeValue.contains("%FRIENDLY_NAME%")||nodeValue.contains("Desc")||nodeValue.contains("STARTS_WITH")||nodeValue.contains("CONTAINS")||nodeValue.contains("ENDS_WITH")){
					if(nodeValue.contains("Desc")||nodeValue.contains("STARTS_WITH")||nodeValue.contains("ENDS_WITH")||nodeValue.equals("1")||nodeValue.equals("2")){
							
					}else{
						bValid = false;
					}
				}
				

				
			break;
    		case "Aspera":
    			if(ParentFilter.contains("suburl")){
    				if(nodeText.trim().equals("")){
						bValid = false;
					}
    			}
    		break;
    		case "ITPAM":
    			if(tagName.equals("a")){
    				if(nodeText.contains("Refresh")||nodeText.contains("Help")||nodeText.contains("Documentation")||nodeText.contains("Book Shelf")||nodeText.contains("Best Practices")||nodeText.contains("ca.com")){
    					bValid = false;
    				}
    			}
    			PageFilter="//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div";  //9 items
             	PageFilter+="|//button";                 	//new,share, delete, unshare
             	PageFilter+="|//img[@class='x-form-trigger x-form-trigger-arrow']"; //my report
             	if(ParentFilter.equals(PageFilter)){
    				if(tagName.equals("button")){
    					if(nodeText.trim().equals("")){
    						bValid = false;
    					}
    					if(nodeText.contains("Help")){
    						bValid = false;
    					}
    					if(nodeText.contains("Search")){
    						bValid = false;
    					}
    				}
                } 
             	if(ParentFilter.contains("//operations[@level='2']")){
			 		if(nodeText.trim().equals("")){
						bValid = false;
					}
			 		if(nodeText.trim().contains("Links")){
						bValid = false;
					}
                } 
             	if(tagName.equals("span")){
    				if(nodeText.contains("Configuration Browser")){
    					bValid = false;
    				}
    			}
             	if(ParentFilter.contains("operations_")){
	    			if(nodeText.contains("Reply") || nodeText.contains("Take")  
	    					|| nodeText.contains("Help") || nodeText.contains("Deactivate") 
	    					|| nodeText.contains("Lock") || nodeText.contains("Unlock")
	    					|| nodeText.contains("Refresh") || nodeText.contains("Clear")
	    					){
	    				bValid = false;
	    			}
            	}
             	//by sonmi02
             	//following is for testing configuration, so exclude all tabs.
             	if(tagName.contains("span")){
    				if(nodeText.contains("Library")||nodeText.contains("Designer")){
    					//bValid = false;
    				}
    			}
             	//if(tagName.equals("a")){
    				//if(nodeId.equals("PAM_HOME_PAGE_userSettingsLink")){
    					//bValid = false;
    				//}
    			//}
             	if(tagName.equals("input")){
        			if(nodeClass.equals("PagingToolbar_RowsOnPage_cobbx_comboBox-input"))
        				bValid = false;
        			}
             	if(tagName.equals("button")){
    				if(nodeText.contains("Install"))
    					bValid = false;
    			}
    		break;
	    	case "ServiceCatalog": 
	    		
	    		if(tagName.equals("a")){ 
                    if(nodeHref.contains("help")||nodeId.contains("request-")||nodeId.equals("catalog_rate_item_change")||nodeId.equals("system_use_sp_catalog")||nodeId.equals("system_use_spconfig")){
                    //||nodeHref.contains("Node=iclaunchpad.pad") service_designer
                            bValid = false; if(bSwitch) {System.out.println("Exclude 1") ;}
                    } 
                    //miao
                    if(nodeHref.contains("Node=iclaunchpad.pad")){ 
						bValid = false; if(bSwitch) {System.out.println("Exclude 2") ;}
					}
                    
                    if(nodeHref.equals("javascript:window.parent.close()")){ 
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 3") ;}
                    } 
					if(nodeHref.equals("javascript:deleteclick()")){
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 4") ;}
                    } 
                    if(nodeName.equals("bar_show_btn")){ 
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 5") ;}
                    } 
                    if(nodeName.equals("bar_hide_btn")){ 
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 6") ;}
                    } 
                    if(nodeName.equals("deleteCheckedButton")){ 
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 7") ;}
                    } 
				
					//third level menu 
					/*if(nodeClass.equals("sidenavlink_selected" ) && tagName.equals("a")){
						bValid = false;
					}*/

					if(nodeHref.contains("javascript:showHideDetails")){
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 8") ;}
                    } 
                    if(onclick.equals("deleteClick(event,this.parentNode.parentNode);")){ 
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 9") ;}
                    } 
                    if(onclick.equals("editClick(event,this.parentNode.parentNode);")){ 
                            bValid = false;  if(bSwitch) {System.out.println("Exclude 10") ;}
                    } 
                    
                    if(onclick.equals("editConfiguration(this)")){ 
                            bValid = false;   if(bSwitch) {System.out.println("Exclude 11") ;}
                    } 
					if(nodeHref.equals("javascript:addprintjob()")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 12") ;}
					}

	    		}
				if(tagName.equals("img")){
					if(nodeSrc.contains("wminus.gif")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 13") ;}
					}
					if(nodeTitle.contains("Deselect Services")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 14") ;}
					}
					if(nodeClass.contains("hide_show_button")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 15") ;}
					}
					if(nodeClass.contains("pmimg")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 16") ;}
					}
					if(onclick.contains("Node=icguinode.images")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 17") ;}
					}
					if(nodeTitle.contains("Click to expand")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 18") ;}
					}
					if(nodeName.equals("bar_hide_btn")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 19") ;}
					}						
				}
				if(tagName.equals("span")){
					if(nodeClass.equals("firstlevelspan")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 20") ;}
					}
					if(onclick.contains("toggleIt(this.parentNode.parentNode")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 21") ;}
					}
					if(onclick.equals("onClickServerTime()")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 22") ;}
					}

				}
				if(tagName.equals("input")){ 
					if(nodeType.equals("hidden")||nodeType.equals("text")||nodeTitle.contains("Search")||nodeType.equals("checkbox")|| nodeName.contains("refresh")||nodeName.contains("view")||nodeName.contains("update")||nodeName.contains("delete")||nodeName.contains("cancel")||nodeName.contains("save")||nodeName.contains("Save")||nodeName.contains("Reset")){ //||nodeType.equals("image")
						bValid = false;  if(bSwitch) {System.out.println("Exclude 23") ;}
					}
					if(nodeTitle.contains("Start Export")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 24") ;}
					}
					if(nodeTitle.contains("CA EEM")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 25") ;}
					}	
					if(nodeTitle.contains("Refresh")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 26") ;}
					}
					
					if(nodeValue.contains("InfoView")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 27") ;}
					}					
					if(nodeValue.contains("Set Folders")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 28") ;}
					}
					if(nodeValue.contains("Set Permissions")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 29") ;}
					}
					if(nodeValue.contains("Delete Items")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 30") ;}
					}
					if(onclick.equals("editMultipleClick(event);event.returnValue=false;")){
						bValid = false;  if(bSwitch) {System.out.println("Exclude 31") ;}
					}
					
				}
//				if(nodeClass.equals("x-tree3-node-text")){
//					bValid = false;  if(bSwitch) {System.out.println("Exclude 32") ;}
//				}				
				if(tagName.equals("td") && nodeTitle.contains("Change the profile ownership")){
					bValid = false;  if(bSwitch) {System.out.println("Exclude 33") ;}
				}
				//home --dashboard
				if(tagName.equals("td") && nodeTitle.contains("Hide Dashboard Administration")){
					bValid = false;  if(bSwitch) {System.out.println("Exclude 34") ;}
				}
				//end home --dashboard
				//miao
//	    		if(ParentFilter.contains("a_home_request")){
//	    			if(nodeText.contains("Home")){
//	    				bValid = false;
//	    			}
//	    		}
			break;
	    	
			
			case "Clarity": //Clarity				
				if(tagName.equals("div")){
					if(nodeClass.equals("ppm_fixed_button_bar")){ //data check
						bValid = false;
					}
				}
				
				if(tagName.equals("a")){
					if(nodeHref.contains("void(0)")){
						bValid = false;
					}
				}		
				if(tagName.equals("span")){
					if(nodeTitle.contains("Options")){
						bValid = false;
					}
				}
				//from 4100 to 2529
				if(nodeName.contains("Remove")){ //1133
					bValid = false;
				}
				if(nodeText.contains("Show")){ //274
					bValid = false;
				}
				if(nodeText.contains("Filter")){ //127
					bValid = false;
				}
				if(nodeText.contains("Return")){ //129
					bValid = false;
				}
			break;
    	}    	
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;		
		String DBParentName="";	String DBseq=""; 	
		String DBexcluderID=""; String DBexcluderName="";
		String DBtagName="";String DBAttribute="";String DBAttributeCondition="";String DBAttributeValue="";		
		String sql="select * from excluderList where ProjectID='"+ProjectID+"'"; 
		boolean bTag=false;boolean bAttribute=false;boolean bParent=false;
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){				
				DBexcluderID=rs.getString("excluderID");
				DBexcluderName=rs.getString("excluderName");
				DBtagName=rs.getString("tagName");
				DBAttribute=rs.getString("Attribute");
				DBAttributeCondition=rs.getString("AttributeCondition");
				DBAttributeValue=rs.getString("AttributeValue");				
				DBParentName=rs.getString("ParentName");
				DBseq=rs.getString("ParentSeq");
				
				bTag=false;
				bAttribute=false;
				bParent=false;
				if(!DBtagName.equals("-1")){
					bTag=true;
				}
				if(!DBParentName.equals("-1")){
					bParent=true;				
				}
				if(!DBAttribute.equals("-1")){		
					bAttribute=true;
				}
				//if(!bTag&&!bParent&&!bAttribute){}
				if(bTag&&!bParent&&!bAttribute){
					if(tagName.equals(DBtagName)){	
						bValid = false;
						logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						//System.out.println("[XPATH:] Exclude Tag="+tagName);
						break;
					}
				}
				if(!bTag&&bParent&&!bAttribute){
					if(ParentFilter.contains(DBParentName)){
						bValid = false;
						logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						//System.out.println("[XPATH:] Exclude ParentName="+DBParentName);
						break;
					}
				}
				if(bTag&&bParent&&!bAttribute){
					if(ParentFilter.contains(DBParentName)){
						if(tagName.equals(DBtagName)){
							bValid = false;
							logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							//System.out.println("[XPATH:] Exclude Tag="+tagName+" and ParentName="+DBParentName);							
							break;
						}
					}
				}				
				if(!bTag&&!bParent&&bAttribute){
					bValid=DOMFilter.getExcludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
					if(!bValid){
						logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						//System.out.println("[XPATH:] Exclude "+DBAttribute+" "+DBAttributeCondition+" "+DBAttributeValue);
						break;
					}
				}
				if(bTag&&!bParent&&bAttribute){
					if(tagName.equals(DBtagName)){
						bValid=DOMFilter.getExcludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
						if(!bValid){
							logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							//System.out.println("[XPATH:] Exclude Tag="+DBtagName+" and "+DBAttribute+" "+DBAttributeCondition+" "+DBAttributeValue);
							break;
						}
					}
				}
				if(!bTag&&bParent&&bAttribute){
					if(ParentFilter.contains(DBParentName)){
						bValid=DOMFilter.getExcludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
						if(!bValid){
							logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							//System.out.println("[XPATH:] Exclude ParentName="+DBParentName+" and "+DBAttribute+" "+DBAttributeCondition+" "+DBAttributeValue);
							break;
						}
					}
				}
				if(bTag&&bParent&&bAttribute){
					if(ParentFilter.contains(DBParentName)){
						if(tagName.equals(DBtagName)){
							bValid=DOMFilter.getExcludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
							if(!bValid){
								logInfo="[Exclude:] seq "+ParentSeq+" ExcluderName=<a target=_blank href=../develop/excluder_edit.jsp?ProjectID="+ProjectID+"&excluderID="+DBexcluderID+"><font color=blue>"+DBexcluderName+"</font></a>";
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								logInfo="[Exclude:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								//System.out.println("[XPATH:] Exclude "+DBAttribute+" "+DBAttributeCondition+" "+DBAttributeValue);
								break;
							}
						}
					}
				}				
			}//while			
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close(); 
		return bValid;
	}
	public String setUniqueObjectAndFingerPrintAttribute(String ParentNodePath,String NodePath,int ParentSeq,String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText,String nodeSrc, String nodeTarget, String nodeNgclick, String TagOrURL){
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
		switch(ProductName){			
			case "CloudMonitor":
				if(tagName.equals("input")){
					uniqueObject=MenuPath+"_input_" + nodeName;
					attributeFP=1;
				}		
//				if(!nodeText.isEmpty()){
//					uniqueObject=MenuPath+"_text_" + nodeName;
//					attributeFP=1;
//					System.out.println("not empty:"+uniqueObject);
//				}else{
//					attributeFP=0;
//					System.out.println("Is empty:"+uniqueObject);
//				}
			break;
			case "APM-TeamCenter":
				//admin
				if(tagName.equals("div") & nodeClass.equals("nav-collapse navbar-brand pull-right")){
					uniqueObject=tagName + nodeClass;
				    attributeFP=1;
				}
				//tab 
				if(tagName.equals("a")&&nodeNgclick.contains("setRibbon(")){
					uniqueObject=MenuPath+"_nodeNgclick_"+nodeNgclick;
	    			attributeFP=1;
				}
			break;
			case "APM-WebView":
				if(tagName.equals("span")&&nodeClass.equals("webview-Common-ListItem")){
		               uniqueObject="nodeText="+nodeText;
		               attributeFP=1;
				}
				if(tagName.equals("span")&&nodeClass.equals("GMDBJMCDASC")){
		               uniqueObject="nodeText="+nodeText;	             
		               attributeFP=1;
				}
				if(tagName.equals("span")&&nodeClass.equals("GMDBJMCDDXC")){
		               uniqueObject="nodeText="+nodeText;	             
		               attributeFP=1;
				}			
				if(tagName.equals("span")&&nodeClass.equals("GMDBJMCDPJC GMDBJMCDAKC")){
		               uniqueObject="nodeText="+nodeText;	             
		               attributeFP=1;
		               /*			
		  			  <span class="GMDBJMCDPJC GMDBJMCDAKC" unselectable="on"> New Action
		               */
				}	
				if(tagName.equals("span")&&nodeClass.equals("GMDBJMCDPJC")){
		               uniqueObject="nodeText="+nodeText;	             
		               attributeFP=1;
		               /*
		                * <span class="GMDBJMCDPJC" unselectable="on"> New Metric Grouping 
		                */
				}
			break;
			case "ITKO":
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
			break;
			case "CASM":
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
			break;	
			case "SiteMinder":
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
			break;	
		}		
		//uniqueObject=ProjectID+"_"+uniqueObject;
		UniqueObjectAndFingerPrintAttribute=String.valueOf(attributeFP)+uniqueObject;
		return UniqueObjectAndFingerPrintAttribute;
	}
	//	public static boolean subMenuFilter(WebElement e, String ProjectID){
	//		//main menu: <a target=_blank href="#">Administration</a>
	//		//sub menu: <a target=_blank href="#" id="m-20" class="sidenavmenu_selected" onclick="toggleMenu('20');" title="Admin UI"><img id="mi-20" alt="" title="" src="ca/images/arrow_asc_bk.png" border="0" align="bottom">Admin UI</a>
	//		
	//		//URL or feature: <a target=_blank href="?task.tag=ManageReportServerConnection" onclick="return selectMenu(this);" title="Report Server Connections"><img alt="Report Server Connections" title="" src="ca/images/arrow_normal.gif" border="0" align="bottom">Report Server Connections</a>
	//		//note: special <td class="r5_dropdown_data_col"><a target=_blank href="#" onkeydown="javascrit:DDSubmit('main');" onclick="return false" title="Create IDP -> SP Partnership">SAML2 IDP -&gt; SP</a></td>
	//		boolean bSubMenu=false;
	//		String tagName,nodeHref,onclick;
	//		tagName = e.getTagName();	     
	//		nodeHref = e.getAttribute("href");
	//		onclick = e.getAttribute("onclick");
	//		
	//		String nodeTitle,nodeName,nodeClass,onkeydown,nodeType,nodeText;
	//		nodeTitle = e.getAttribute("title");
	//    	nodeName = e.getAttribute("name");
	//    	nodeClass= e.getAttribute("class");	        	
	//    	nodeType= e.getAttribute("type");	   
	//    	onkeydown = e.getAttribute("onkeydown");    	
	//		if(tagName.equals("a")){
	//			if(nodeHref!=null){
	//				if(nodeHref.contains("#")){
	//					if(onclick!=null){
	//						if(onclick.contains("toggleMenu")){
	//							bSubMenu=true;
	//						}
	//					}
	//					if(nodeTitle==null){ //Different with title="Create IDP -> SP Partnership"
	//						bSubMenu=true;
	//					}
	//					else{
	//						if(nodeTitle.contains("")){ //Different with title="Create IDP -> SP Partnership"
	//							bSubMenu=true;
	//						}
	//					}
	//				}
	//			}
	//		}
	//		nodeText = e.getText();
	//		
	//		return bSubMenu;
	//	}
		public static String setMulitiFilter(String ParentNodePath,String NodePath,int LevelID,String DBPosition,String ProjectID,String ProductName,String ParentFilter,int ParentSeq,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
		{
			
			if(tagName.equals("a")){
						if(nodeHref!=null){
							if(nodeHref.contains("#")){
								if(onclick!=null){
									if(onclick.contains("toggleMenu")){
									}
								}
								if(nodeTitle==null){
								}
								else{
									if(nodeTitle.contains("")){ 
									}
								}
							}
						}
					}
				return nodeTitle;
				
		}
}
