package customlibrary;

import java.sql.ResultSet;

import db.ManageDB;

public class ExtensionSupport {
	public static int setFrame(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//supported, not tested
		int frame = 0;
		ParentFilter=ParentFilter.replace('&','\'');    		
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where frame=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
					frame = 1;
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return frame;
	}
	public static int setiFrame(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//supported, not tested
		int iframe = 0;
		ParentFilter=ParentFilter.replace('&','\'');    		
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where iframe=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
					iframe = 1;
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return iframe;
	}
	public static boolean setMouseover(String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//supported, not tested
		boolean bMouseover = false;
		ParentFilter=ParentFilter.replace('&','\'');    
    	switch(ProductName){	    	
	    	case "Any": 
	    		bMouseover = false;	
			break;
	    	case "Clarity":
	    		bMouseover = true;	
			break;
    	}
    	bMouseover = true;	
		return bMouseover;
	}
	public static int setDoubleClick(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//not supported now.
		int doubleclick = 0;
		ParentFilter=ParentFilter.replace('&','\'');  
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where doubleclick=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
					doubleclick = 1;
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return doubleclick;
	}
	public static int setMouseright(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//supported, not tested
		int mouseright = 0;
		ParentFilter=ParentFilter.replace('&','\'');    		
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where mouseright=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
					mouseright = 1;
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return mouseright;
	}
	public static int setNewtab(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//supported, not tested
		int newtab = 0;
		ParentFilter=ParentFilter.replace('&','\'');    
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where newtab=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
					newtab = 1;
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return newtab;
	}
	public static int setPopup(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		//supported, not tested
		int popup = 0;
		String PageFilter="";
		ParentFilter=ParentFilter.replace('&','\'');    
		if(ParentFilter.contains("popup")){ 
			popup = 1;
		}
		MyPageFilter=MyPageFilter.replace('&','\'');
    	switch(ProductName){	    	
	    	case "Any": 
	    		popup = 0;	
			break;	    	
	    	case "ServiceCatalog": 	   
	            if(ParentFilter.contains("service_designer_end")){ 
	            	if(nodeType.equals("button")){
	            		popup = 1;  
	            	}
				}	
	            if(nodeClass.equals("about")){
	    			popup = 1;				
	    		}
	    		if(nodeName.equals("depBtn")){
	    			popup = 1;
	    		}
	    		if(nodeName.equals("buBtn")){
	    			popup = 1;
	    		}	    		
	    		if(tagName.equals("a") && nodeTitle.contains("Localization")){
	    			popup = 1;
	    		}
	    		if(tagName.equals("input") && nodeTitle.contains("Add Set")){
	    			popup = 1;
	    		}
	    		if(tagName.equals("a") && nodeHref.equals("javascript:searchtenantpopup()")){
	    			popup = 1;
		    	}
	    		
	    		if(nodeName.equals("domainButton")){
	    			popup = 1;
	    		}
	    		
	    		if(nodeTitle.contains("Options")){
	    			popup = 1;
	    		}
	    		
	    		if(nodeTitle.contains("Add Dashboard")){
	    			popup = 1;
	    		}
	    		if(nodeTitle.contains("Change Business Unit")){
	    			popup = 1;
	    		}
	    		if(nodeTitle.contains("Filter Events")){
	    			popup = 1;
	    		}
	    		if(nodeTitle.contains("Filter Alerts")){
	    			popup = 1;
	    		}
	    		if(tagName.equals("a") && nodeHref.equals("javascript:savequery()")){
	    			popup = 1;
	    		}
	    		
	    		if(tagName.equals("a") && nodeHref.equals("javascript:loadquery()")){
	    			popup = 1;
	    		}
	    		
	    		if(tagName.equals("input") && nodeValue.contains("Create New Layout")){
	    			popup = 1;	
				}
	    		if(tagName.equals("input") && nodeTitle.contains("View Scheduler System Alerts")){
	    			popup = 1;	
				}
	    		
	    		if(tagName.equals("input") && nodeTitle.contains("Assign to Cost Pools")){
	    			popup = 1;	
	    		}
	    		if(tagName.equals("input") && nodeTitle.contains("Add Cost Pool")){
	    			popup = 1;	
	    		}
	    		if(tagName.equals("a") && nodeHref.contains("javascript:showPluginDetails")){
	    			popup = 1;	
	    		}
	    		
	    		if(tagName.equals("a") && nodeTitle.contains("Click to Edit Set")){
	    			popup = 1;
	    		}
	    		//home---> message
	    		if(tagName.equals("img") && onclick.equals("showdetails(this)")){
	    			popup = 1;
		    	}
	    		//end home message
	    		//accounting tab
	    		//accounting ---bugget planning
	    		if(tagName.equals("input") && nodeTitle.contains("Add Cost Element")){
	    			popup = 1;
	    		}	    		
	    		if(tagName.equals("a") && onclick.equals("editclick(this)")){
	    			popup = 1;
	    		}
	    		if(tagName.equals("input") && nodeTitle.contains("Add Cost Pool")){
	    			popup = 1;
	    		}
	    	    //account---configure
	    		if(ParentFilter.contains("//modifyimgend")){		    		
	    			popup = 1;
		    	}
	    		if(ParentFilter.contains("//accountconfigperiod")){		    		
	    			popup = 1;
		    	}
	    		
	    		if(ParentFilter.contains("//exchange_rates-end")){		    		
	    			popup = 1;
		    	}
	    		if(ParentFilter.contains("//fileddefinition")){		    		
	    			popup = 1;
		    	}
	    		if(ParentFilter.contains("//profilemanagement") && onclick.contains("viewtable(")){		    		
	    			popup = 1;
		    	}
	    		if(ParentFilter.contains("//aggregationstatus")){
	    			popup = 1;
	    		}
	    		//end account tab
	    		//home-->request
	    		if(ParentFilter.contains("b_home_request_open")){
	    			if(nodeHref.contains("javascript")){
	    				popup = 1;
	    			}
	    			if(nodeText.contains("Submitted") || nodeText.contains("Hold")){
	    				popup = 1;
					}
	    		}

	    		if(ParentFilter.contains("d_home_request_open_profile_trail_popup")){
	    			popup = 1;
	    		}
	    		
			break;
	    	case "ITPAM": 
	    		if(ParentFilter.equals("//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']")){
		    		if(tagName.equals("button")){ 
	                    popup = 1; 
		            } 
	    		}	    		
	    		PageFilter="//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div";  //9 items
             	PageFilter+="|//button";                 	//new,share, delete, unshare
             	PageFilter+="|//img[@class='x-form-trigger x-form-trigger-arrow']"; //my report
	    		if(ParentFilter.equals(PageFilter)){
	    			if(tagName.equals("div")){
	    				popup = 1;  
	    			}
	    		}
	    		
	    		if(ParentFilter.contains("operations_")){
	    			if(nodeText.contains("Open") ){
	    				popup = 1;  
	    			}
            	}
	    		if(tagName.equals("button")){
	    			if(nodeText.contains("AGENT_CONSOLE_BTN")){
	    				popup = 1;
	    			}
	    		}
			break;
    	}
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where popup=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
					popup = 1;
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return popup;
	}
	public static int setLeaf(String ProjectID,String DBPosition,String ProductName,int LevelID, String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText, String nodeNgclick)
	{
		//supported, not tested
		String PageFilter="";
		ParentFilter=ParentFilter.replace('&','\'');
		MyPageFilter=MyPageFilter.replace('&','\'');
		//condition 1:  different page, same vector with parent page
		boolean bSwitch = false; // bSwitch = true; 
		int leaf = 0;		
		if(ParentFilter.contains("leaf")){
        	leaf = 1; 
        	if(bSwitch) {System.out.println("Leaf ParentFilter") ;}
        }
//		if(MyPageFilter.contains("//leaf")){
//        	leaf = 1; 
//        	if(bSwitch)  {System.out.println("Leaf MyPageFilter") ;}
//       }
		if(nodeTitle==null){nodeTitle="";} 
    	switch(ProductName){    	
	    	case "ITKO": 
				if(nodeNgclick.contains("actions.deleteAction")){//goToLink(link)
					leaf = 1;	//grid.getCellValue(row, col).actions.deleteAction(grid.getCellValue(row, col).data)
				}
			break;
			case "CASM":	//SiteMinder	
				if(tagName.equals("a")||tagName.equals("img")){
					leaf = 1;
				}
				if(tagName.equals("input")){
					if(nodeType.equals("image")){
						leaf = 1;
						if(nodeName.equals("action.Filter.qfi_0")){
							//<input type="image" src="/iam/siteminder/ui/skin/idm/image/tasks/add.png" id="action.Filter.qfi_0" name="action.Filter.qfi_0" class="im-button-img" alt="Insert Before" title="Insert Before" value="y">
							leaf = 1;
						}
						if(nodeName.equals("action.Filter.qfi_1")||nodeName.equals("action.Filter.qfd_0")){ //Insert After , action.Filter.qfd_0 delete since Vetor page does not change
							leaf = 1;
						}
					}
					if(nodeType.equals("submit")){ //for input
						if(nodeName.contains("Delete")||nodeName.contains("action.Create")){ // should add more for it
							//<input class="im-button-small" type="submit" value="Delete Agent" name="action.standardsearch.multiDelete"></input>
							//Create Agent Type
							//<input type="submit" name="action.CreateAction" value="Create" class="im-button" id="imh_7">
							//<input type="submit"  value="Create" name="action.CreateValue">
							leaf = 1;
						}						
					}
					if(nodeType.equals("radio")){
						if(nodeValue.contains("copy")||nodeValue.equals("0")||nodeValue.equals("1")){
							//condition 1:  different page, same vector with parent page
							//<input type="radio" name="createType" value="copy" onclick="imToggleCreateCopy();" id="createCopyType">
							//<input id="imh_5" type="radio" onclick="imSubmitAction('action.refresh')" value="0" name="agentTypeType"><label for="imh_5">RADIUS</label>
							//<input id="imh_4" type="radio" onclick="imSubmitAction('action.refresh')" checked="checked" value="1" name="agentTypeType"></input><label for="imh_4">SiteMinder</label>
							
							leaf = 1;
						}
					}
				}
				if(tagName.equals("option")){
					//commented leaf = 1;
				}
				if(tagName.equals("a")){
					if(nodeHref.contains("View")||nodeHref.contains("Modify")||nodeHref.contains("Delete")){
						leaf = 1;
					}
				}
				if(tagName.equals("action.nestingDone")){
					leaf = 1;
				}
				if(tagName.equals("button")){
					if(nodeText.contains("Submit")){
						leaf = 1;
					}
				}
				
			break;
	    	case "ServiceCatalog":	//ServiceCatalog
	    		//home-->dashboard subtab
    			
    			if(tagName.equals("td") && nodeClass.equals("quickdashbg") && nodeId.equals("optionsspan")){
    				leaf = 1; if(bSwitch) {System.out.println("Leaf 1") ;}
    			} 
    			if(tagName.equals("td") && nodeClass.equals("quickdashbg") && nodeId.equals("savelayoutspan")){
    				leaf = 1; if(bSwitch) {System.out.println("Leaf 2") ;}
    			}    			
    			if(tagName.equals("td") && nodeClass.equals("quickdashbg") && nodeId.equals("showlibraryspan")){
    				leaf = 1; if(bSwitch) {System.out.println("Leaf 3") ;}
    			}
    			if(tagName.equals("td") && nodeClass.equals("quickdashbg") && nodeId.equals("addspan")){
    				leaf = 1; if(bSwitch) {System.out.println("Leaf 4") ;}
    			}
    			//end home-->dashboard subtab
    			
    			
    			// message subtab    			
	    		if(tagName.equals("img") && onclick.equals("showdetails(this)")){
	    			leaf = 1; if(bSwitch) {System.out.println("Leaf 5") ;}
		    	}
	    		//search subtab
	    		if(tagName.equals("a") && nodeText.contains("BTO:spadmin")){
	    			leaf = 1; if(bSwitch) {System.out.println("Leaf 6") ;}
		    	}
	    		if(tagName.equals("a") && nodeHref.equals("javascript:chsearch()")){
	    			leaf = 1; if(bSwitch) {System.out.println("Leaf 7") ;}
	    		}
    			//End Home tab
    			//begin accounting tab
	    		if(tagName.equals("a") && onclick.equals("editclick(this)")){
	    			leaf = 1; if(bSwitch) {System.out.println("Leaf 8") ;}
	    		}
	    		if(tagName.equals("a") && nodeClass.equals("table_control_link") && nodeTitle.contains("Assign to Cost Elements")){
	    			leaf = 1; if(bSwitch) {System.out.println("Leaf 9") ;}
	    		}
	    		//end accounting tab
	    		
	    		//request>cart
		    	if(nodeHref.contains("Node=icguinode.catalogrequestsearchpre")){ //catalogcheckout
		    		leaf = 1; 
		    		if(bSwitch)  {
		    			System.out.println("Leaf 10") ;
		    		}
		    	}
		    	if (nodeClass.equals("servicename_big_side_1")){
		    		leaf = 1; 
		    		if(bSwitch)  {
		    			System.out.println("Leaf 11") ;
		    		}
		    	}
    			//end request>cart
		    	//account---? confirgure
		    	if(ParentFilter.contains("//modifyimgend")){		    		
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 12") ;}
		    	}
		    	if(ParentFilter.contains("//exchange_rates-end")){		    		
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 13") ;}
		    	}
		    	if(ParentFilter.contains("//fileddefinition-end")){		    		
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 14") ;}
		    	}
		    	
		    	//if(tagName.equals("a") && nodeClass.contains("_selected")){
		    		//leaf = 1;
				//}
		    	//end accounting--configure
		    	if(tagName.equals("img") && nodeClass.equals(" x-tree3-node-icon") && ParentFilter.contains("(//img[contains(@src,'/usm/gwt/sbIde/clear.cache.gif')])[10]")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 15") ;}
				}
		    	
		    	if(nodeHref.contains("usm/wpf?Node=iclaunchpad.subscription_management")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 16") ;}
	    		}
		    	//policy tab
		    	if(tagName.equals("img") && nodeClass.equals(" x-tree3-node-icon") && ParentFilter.equals("//img[@class=' x-tree3-node-icon']")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 17") ;}
				}
		    	if(tagName.equals("img") && onclick.contains("showCalendar")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 18") ;}
		    	}
		    	
		    	//filter
		    	if(ParentFilter.contains("//table[@style='width: 100%;']/tbody/tr[2]/td/table[@class='blue']/tbody/tr/td[3]/a[@href='javascript:void(0)']/img")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 19") ;}
		    	}
		    	//end filter
		    	if(tagName.equals("input")){
			    	if(nodeTitle.contains("Close") | nodeTitle.contains("Search") | nodeTitle.contains("Add")){
			    		leaf = 1; if(bSwitch) {System.out.println("Leaf 20") ;}
			    	}
			    	if(nodeValue.contains("Create Data Object")){
			    		leaf = 1; if(bSwitch) {System.out.println("Leaf 21") ;}
					}
			    	if(nodeValue.contains("Create Data View")){
			    		leaf = 1; if(bSwitch) {System.out.println("Leaf 22") ;}
					}
			    	if(nodeValue.contains("Create New Layout")){
			    		leaf = 1; if(bSwitch) {System.out.println("Leaf 22") ;}
					}
			    	
		    	}
		    	if(tagName.equals("a") && nodeName.equals("a_BTO")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 23") ;}
		    	}
		    	if(tagName.equals("a") && nodeTitle.contains("Business Unit Management")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 24") ;}
		    	}

		    	if(tagName.equals("a") && onclick.equals("javascript:chsearch()")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 25") ;}
		    	}
		    	
		    	if(tagName.equals("a") && nodeHref.contains("javascript:showPluginDetails")){
		    		leaf = 1;	 if(bSwitch) {System.out.println("Leaf 26") ;}
	    		}
		    	
		    	if(tagName.equals("a") && nodeHref.contains("javascript:detail(")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 27") ;}
				}
		    	
		    	if(nodeTitle.contains("Change Business Unit")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 28") ;}
		    	}
		    	if(nodeTitle.contains("Filter Events")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 29") ;}
		    	}
		    	if(nodeTitle.contains("Filter Alerts")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 30") ;}
		    	}
		    	if(nodeTitle.contains("Add News Message")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 31") ;}
		    	}	
		    	
		    	if(nodeTitle.contains("List All Failed Actions")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 32") ;}
		    	}
		    	if(tagName.equals("span") && onclick.equals("selectobject(this)") && nodeClass.contains("foldername")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 33") ;}
				}
		    	if(tagName.equals("a") && nodeHref.contains("usm/wpf?Node=icguinode.ruletypeedit")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 34") ;}
				}
		    	
		    	if(tagName.equals("a") && nodeHref.equals("javascript:searchtenantpopup()")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 35") ;}
		    	}
		    	if(tagName.equals("a") && nodeTitle.equals("Click to view rules")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 36") ;}
				}
		    	
		    	if(tagName.equals("a") && nodeTitle.contains("Click to Edit Set")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 37") ;}
	    		}
		    	
		    	if(tagName.equals("a") && nodeTitle.contains("Click to Edit Cost Element")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 38") ;}
	    		}
		    	if(tagName.equals("option")){
		    		leaf = 1;  if(bSwitch) {System.out.println("Leaf 39") ;}
		    	}
		    	if(tagName.equals("span") && onclick.contains("toggleIt2(this")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 40") ;}
		    	}
		    	
		    	if(tagName.equals("a")){
		    		if(nodeHref.equals("javascript:savequery()") | nodeHref.equals("javascript:loadquery()") ){
		    			leaf = 1; if(bSwitch) {System.out.println("Leaf 41") ;}
		    		}
		    	}
		    	//catagory ---> form
		    	if(ParentFilter.contains("form_designer_end")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 42") ;}
		    	}
		    	if(ParentFilter.contains("service_designer_end")){ 
		    		leaf = 1;    if(bSwitch) {System.out.println("Leaf 43") ;}
				}
		    	//if(PageFilter.equals("//leaf")){
		    		//leaf = 1;
		    	//}
		    	//administrator---> business unit
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-tl-showaccounts")){
		    		leaf = 1;  if(bSwitch) {System.out.println("Leaf 44") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-tl-tenantprofile")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 45") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-tl-servicesummary")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 46") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-al-acctinfosubscriptionframe")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 47") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-al-requests")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 48") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-al-acctinfostatementslist")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 49") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-al-accountprofile")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 50") ;}
		    	}
		    	if(tagName.equals("a") && nodeClass.equals("tabs") && nodeId.equals("group-al-acctinfobilling")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 51") ;}
		    	}
		    	//administrator--> report builder
		    	if(ParentFilter.contains("//report_listend")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 52") ;}
		    	}
		    	
		    	if(ParentFilter.contains("//dataview-end")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 53") ;}
		    	}
		    	
		    	if(ParentFilter.contains("//layout-end")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 54") ;}
		    	}
		    		    	
		    	//end report builder
		    	
		    	//begin dashboard builder
		    	if(nodeHref.contains("/wpf?Node=iclaunchpad.portal_management")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 55") ;}
		    	}
		    	
		    	//dashboard builder
		    	
		    	//begin administrator--->event rule action
		    	if(ParentFilter.contains("//eventruleaction-end")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 56") ;}
		    	}	    	
		
		    	//end administrator--->event rule action
		    	
		    	//begin tools ---schedule
		    	if(ParentFilter.contains("//toolschedule-end")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 57") ;}
		    	}
		    	//end tools---schedule
		    	
		    	//begin tools--links
		    	if(nodeHref.contains("wpf?Node=iclaunchpad.linksmanagement")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 58") ;}
		    	}
		    	//end tools---links
		    	//begin tools---plugin
		    	if(ParentFilter.contains("//toolsplugin-end")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 59") ;}
		    	}
		    	//end tools--plugin
		    	//accounting management
		    	if(ParentFilter.contains("//accountmanagement")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 60") ;}
		    	}
		    	if(ParentFilter.contains("//accountconfigperiod")){		    		
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 61") ;}
		    	}
		    	if(ParentFilter.contains("//profilemanagement")){		    		
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 62") ;}
		    	}
		    	
		    	if(ParentFilter.contains("//datamanagement")){		    		
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 63") ;}
		    	}
		    	
		    	if(ParentFilter.contains("//aggregationstatus")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 64") ;}
	    		}
		    	if(nodeHref.contains("wpf?Node=iclaunchpad.application_event")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 65") ;}
		    	}
		    	if(ParentFilter.contains("//application_metric")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 66") ;}
	    		}
		    	if(ParentFilter.contains("//application_pkg")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 67") ;}
	    		}
		    	if(nodeHref.contains("wpf?Node=iclaunchpad.sla_violation_adjustments")){
		    		leaf = 1; if(bSwitch) {System.out.println("Leaf 68") ;}
		    	}	    	
		    	//end accounting management
		    	//home-->request
		    	if(ParentFilter.contains("b_home_request_open")){
	    			if(nodeHref.contains("javascript")){
	    				leaf = 1; if(bSwitch) {System.out.println("Leaf 69") ;}
	    				
	    			}
	    		}
	    		if(ParentFilter.contains("c_home_request_open_profile")){
	    			if(nodeText.contains("Trail")){
	    				leaf = 0;
	    			}else{
	    				leaf = 1;  if(bSwitch) {System.out.println("Leaf 70") ;}
	    				
	    			}
	    		}
	    		//end home-->request
	    	break;
    		case "ITPAM":
//    			if(MyPageFilter.equals("//end@not_extract")){ 
//    				leaf = 1;    				
//    			}
    			if(ParentFilter.equals("//a[@href='#']")){ //Home
    				leaf = 1;    				
    			}
    			if(ParentFilter.equals("//img[@class=' x-tree3-node-joint']")){
    				leaf = 1;   
                }    	
    			if(ParentFilter.contains("operations_span_div_end")){
    				leaf = 1;  
            	}
    			if(ParentFilter.contains("operations_link_END")){
    				leaf = 1;  
            	}	    			
    			//Manage User Resource and Installation
                if(ParentFilter.contains("//leaf")){
                	leaf = 1;
                }
                
                //report after OK
                if(ParentFilter.equals("//input[@type='image']")){ 
                	leaf = 1;   	
                }
                //button and img
	    		if(ParentFilter.equals("//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div|//button|//img[@class='x-form-trigger x-form-trigger-arrow']")){
	    			if(tagName.equals("button")||tagName.equals("img")){
	    				leaf = 1; 
	    			}	    			
                } 
	    		if(ParentFilter.equals("//operations_img_span[@level='4']")){                	
                	if(tagName.equals("span")){
                		leaf = 1; 
                	}
                }
	    		
    			PageFilter="//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div";  //9 items
             	PageFilter+="|//button";                 	//new,share, delete, unshare
             	PageFilter+="|//img[@class='x-form-trigger x-form-trigger-arrow']"; //my report
             	if(ParentFilter.equals(PageFilter)){
//    				leaf = 1;   
                } 
             	
    		break;
    		case "Clarity":
    			if(LevelID>3){
	    			if(tagName.equals("img")){
			    		leaf = 1;
			    		if(bSwitch)  {
			    			System.out.println("Leaf 1 img") ;
			    		}
					}
	    			if(tagName.equals("span")){
			    		leaf = 1;
			    		if(bSwitch)  {
			    			System.out.println("Leaf 2 span") ;
			    		}
					}
    			}
//    			if(tagName.equals("button")){
//		    		leaf = 1;
//		    		if(bSwitch)  {
//		    			System.out.println("Leaf 3 button") ;
//		    		}
//				}
    		break;
    		
		}
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;
		String sql="select XPATHName from XpathList where leaf=1 and ProjectID='"+ProjectID+"'"; 
		String DBXPATHName="";
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){
				DBXPATHName=rs.getString("XPATHName");
				if(ParentFilter.contains(DBXPATHName)){
		        	leaf = 1; 
		        	if(bSwitch) {System.out.println("Leaf since ParentFilter contains:"+DBXPATHName) ;}
		        }
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();  
		return leaf;
	}
}
