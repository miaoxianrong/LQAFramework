package core;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

import db.ManageDB;
import db.RunDB;


public class DOMFilter {
	
	
	
	public static boolean setExcluderFilter(String ParentNodePath,String NodePath,int ParentSeq,String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText,String nodeSrc, String nodeTarget, String nodeNgclick)
	{
		boolean bValid = true;
		String PageFilter="";
		ParentFilter=ParentFilter.replace('&','\'');
		String logInfo="";
		boolean bSwitch = false; //bSwitch = true; 
		//MCC
		if(nodeHref.contains("help")){
			bValid = false;					
		}
		if(nodeHref.contains("ca.com")){
			bValid = false;					
		}
		//ITKO
		if(nodeHref.contains("logout")){
			bValid = false;					
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
	//				if(nodeType.equals("radio")){ //radio new , same page, same vector with parent page
	//					if(nodeId.equals("createNewType")){
	//						bValid = false;
	//					}
	//				}
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
		//bValid=true;
		return bValid;
	}
	public static boolean setIncluder(String ParentNodePath,String NodePath,int ParentSeq,String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,int seq,int LevelID,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		boolean bValid = false;
		String PageFilter="";
		ParentFilter=ParentFilter.replace('&','\'');    
		String logInfo="";
		boolean bProdMatch=false;
    	switch(ProductName){	    	
	    	case "Any": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "MCC": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "SAAS": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "CloudMonitor": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "APM": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "Aspera": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "ControlMinder": 
	    		bValid = true;	
	    		bProdMatch=true;
			break;
	    	case "Nimsoft": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "MAA": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "ITPAM": 
				bValid = true;	
				if(ParentFilter.contains("operations_task_button_layer")){                	
            		if( nodeText.contains("Users") || nodeText.contains("Groups") || nodeText.contains("Assignees") || nodeText.contains("Delegates")){ //nodeText.contains("Task Properties") ||
            			bValid = true;	
            		}else{
            			bValid = false;
            		}
            	}
				bProdMatch=true;
			break;
	    	case "ServiceCatalog": 
	    		bValid = true;	
	    		if(LevelID==2){
	    			if(nodeClass.contains("subtab_")){
						bValid = true;
					}else{
						bValid=false;
					}
	    		}
	    		bProdMatch=true;
			break;
			case "SiteMinder":	
				//main menu: <a target=_blank href="#">Administration</a>
				//sub menu: <a target=_blank href="#" id="m-20" class="sidenavmenu_selected" onclick="toggleMenu('20');" title="Admin UI"><img id="mi-20" alt="" title="" src="ca/images/arrow_asc_bk.png" border="0" align="bottom">Admin UI</a>
				
				//URL or feature: <a target=_blank href="?task.tag=ManageReportServerConnection" onclick="return selectMenu(this);" title="Report Server Connections"><img alt="Report Server Connections" title="" src="ca/images/arrow_normal.gif" border="0" align="bottom">Report Server Connections</a>
				//note: special <td class="r5_dropdown_data_col"><a target=_blank href="#" onkeydown="javascrit:DDSubmit('main');" onclick="return false" title="Create IDP -> SP Partnership">SAML2 IDP -&gt; SP</a></td>
				
				if(tagName.equals("a")){
					bValid = true;
				}
				if(tagName.equals("input")){
					bValid = true;
				}
				if(tagName.equals("option")){
					bValid = true;
				}
				if(tagName.equals("button")){
					bValid = true;
				}
				bProdMatch=true;
			break;
			
			case "Clarity": 
				bValid = true;
//				if(tagName.equals("a")){
//					if(nodeClass.equals("none")||nodeHref.contains("object_code")||nodeHref.contains("javascript")||nodeHref.contains("odf_")){
//						bValid = true;
//					}
//				}
				bProdMatch=true;
			break;
    	}
    	if(!bProdMatch){
    		bValid = true;
    	}
    	
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs_xpath = null;
		String DBParentName="";	String DBXPATHName=""; String DBseq=""; boolean bGetResult=false;
		String DBtagName="";String DBAttribute="";String DBAttributeCondition="";String DBAttributeValue="";		
		String sql=""; 
		String nodeSeq="";
		sql="select XPATHName,seq from XpathList where ProjectID='"+ProjectID+"'"; 
		
		try{
			rs_xpath=mdb.getSQL(sql);
			while(rs_xpath.next()){
				DBXPATHName=rs_xpath.getString("XPATHName");
				DBseq=","+rs_xpath.getString("seq")+",";
				nodeSeq=","+String.valueOf(seq)+",";				
				if(DBseq.length()>2){
					if(ParentFilter.contains(DBXPATHName)){  						
	            		if(DBseq.contains(nodeSeq)){ 
	            			bValid = true;		            			
	            		}else{
	            			bValid = false;
	            		}
	            	}
				}
			}
			rs_xpath.close();
			rs_xpath=null;
		}catch(Exception e1){}		
		
		ResultSet rs = null;
		String DBincluderID=""; String DBincluderName="";
		sql="select * from includerList where ProjectID='"+ProjectID+"'"; 
		boolean bTag=false;boolean bAttribute=false;boolean bParent=false;
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){	
				DBincluderID=rs.getString("includerID");
				DBincluderName=rs.getString("includerName");
				DBtagName=rs.getString("tagName");
				DBAttribute=rs.getString("Attribute");
				DBAttributeCondition=rs.getString("AttributeCondition");
				DBAttributeValue=rs.getString("AttributeValue");				
				DBParentName=rs.getString("ParentName");
								
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
						bValid = true;
						break;
					}else{
						bValid = false;
						logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						break;							
					}
				}
				if(!bTag&&bParent&&!bAttribute){
					if(ParentFilter.contains(DBParentName)){
						bValid = true;
						break;
					}else{
						bValid = false;
						logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						break;							
					}
				}
				if(bTag&&bParent&&!bAttribute){
					if(ParentFilter.contains(DBParentName)){
						if(tagName.equals(DBtagName)){
							bValid = true;
							break;
						}else{
							bValid = false;
							logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							break;							
						}
					}else{
						bValid = false;
						logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						break;
					}
				}				
				if(!bTag&&!bParent&&bAttribute){
					bValid=DOMFilter.getIncludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
					if(!bValid){
						logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
						RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
						break;
					}
				}				
				if(bTag&&!bParent&&bAttribute){
					if(tagName.equals(DBtagName)){
						bValid=DOMFilter.getIncludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
						if(!bValid){
							logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							break;
						}
					}
				}
				if(!bTag&&bParent&&bAttribute){
					if(ParentFilter.contains(DBParentName)){
						bValid=DOMFilter.getIncludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
						if(!bValid){
							logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							break;
						}
					}
				}
				if(bTag&&bParent&&bAttribute){
					if(ParentFilter.contains(DBParentName)){
						if(tagName.equals(DBtagName)){
							bValid=DOMFilter.getIncludeFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
							if(!bValid){
								logInfo="[Includer:] Not include seq "+ParentSeq+" IncluderName=<a target=_blank href=../develop/includer_edit.jsp?ProjectID="+ProjectID+"&includerID="+DBincluderID+"><font color=blue>"+DBincluderName+"</font></a>";
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								logInfo="[Includer:] Not include seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
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
	public static int setDoubleClickForNode(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
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
	public static int setMouserightForNode(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
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
	public static int setNewtabForNode(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
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
	public static int setPopupForNode(String ProjectID,String DBPosition,String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
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
	public static boolean setMouseoverForNode(String ProductName,String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
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
	public static boolean matchPageFilterByAttribute(String DBAttribute,String DBAttributeCondition,String DBAttributeValue,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		boolean bMatch=false;
		switch(DBAttribute){
		case "nodeId":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeId.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;			 
				}
			}else{
				if(nodeId.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}
		break;
		case "nodeHref":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeHref.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}else{
				if(nodeHref.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;
				}
			}
		break;
		case "nodeText":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeText.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}else{
				if(nodeText.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}
		break;
		case "nodeTitle":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeTitle.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;							
				}
			}else{
				if(nodeTitle.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}
		break;
		
		case "nodeClass":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeClass.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}else{
				if(nodeClass.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}
		break;
		case "nodeName":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeName.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}else{
				if(nodeName.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}
		break;
		case "nodeValue":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeValue.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}else{
				if(nodeValue.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}
		break;
		case "nodeType":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeType.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}else{
				if(nodeType.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;				 
				}
			}
		break;
		case "onclick":	
			if(DBAttributeCondition.equals("contains")){
				if(onclick.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}else{
				if(onclick.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}
		break;
		case "onkeydown":	
			if(DBAttributeCondition.equals("contains")){
				if(onkeydown.contains(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" contains "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}else{
				if(onkeydown.equals(DBAttributeValue)){
					//System.out.println("[XPATH:] Attribute "+DBAttribute+" equals "+DBAttributeValue+"  to xpath");
					bMatch=true;					 
				}
			}
		break;
		}//switch
		return bMatch;
	}
	public static boolean getExcludeFilterByAttribute(String DBAttribute,String DBAttributeCondition,String DBAttributeValue,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		boolean bValid = true;
		switch(DBAttribute){
		case "nodeId":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeId.contains(DBAttributeValue)){
					bValid = false;				 
				}
			}else{
				if(nodeId.equals(DBAttributeValue)){
					bValid = false;					 
				}
			}
		break;
		case "nodeHref":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeHref.contains(DBAttributeValue)){
					bValid = false;					 
				}
			}else{
				if(nodeHref.equals(DBAttributeValue)){
					bValid = false;				 
				}
			}
		break;
		case "nodeText":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeText.contains(DBAttributeValue)){
					bValid = false;				 
				}
			}else{
				if(nodeText.equals(DBAttributeValue)){
					bValid = false;				 
				}
			}
		break;
		case "nodeTitle":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeTitle.contains(DBAttributeValue)){
					bValid = false;						
				}
			}else{
				if(nodeTitle.equals(DBAttributeValue)){
					bValid = false;					 
				}
			}
		break;
		
		case "nodeClass":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeClass.contains(DBAttributeValue)){
					bValid = false;			 
				}
			}else{
				if(nodeClass.equals(DBAttributeValue)){
					bValid = false;		 
				}
			}
		break;
		case "nodeName":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeName.contains(DBAttributeValue)){
					bValid = false;			 
				}
			}else{
				if(nodeName.equals(DBAttributeValue)){
					bValid = false;					 
				}
			}
		break;
		case "nodeValue":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeValue.contains(DBAttributeValue)){
					bValid = false;				 
				}
			}else{
				if(nodeValue.equals(DBAttributeValue)){
					bValid = false;				 
				}
			}
		break;
		case "nodeType":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeType.contains(DBAttributeValue)){
					bValid = false;				 
				}
			}else{
				if(nodeType.equals(DBAttributeValue)){
					bValid = false;					 
				}
			}
		break;
		case "onclick":	
			if(DBAttributeCondition.equals("contains")){
				if(onclick.contains(DBAttributeValue)){
					bValid = false;					 
				}
			}else{
				if(onclick.equals(DBAttributeValue)){
					bValid = false;					 
				}
			}
		break;
		case "onkeydown":	
			if(DBAttributeCondition.equals("contains")){
				if(onkeydown.contains(DBAttributeValue)){
					bValid = false;					 
				}
			}else{
				if(onkeydown.equals(DBAttributeValue)){
					bValid = false;				 
				}
			}
		break;
		}//switch
		return bValid;
	}
	public static boolean getIncludeFilterByAttribute(String DBAttribute,String DBAttributeCondition,String DBAttributeValue,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		boolean bValid = false;
		switch(DBAttribute){
		case "nodeId":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeId.contains(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}else{
				if(nodeId.equals(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}
		break;
		case "nodeHref":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeHref.contains(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}else{
				if(nodeHref.equals(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}
		break;
		case "nodeText":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeText.contains(DBAttributeValue)){					
					bValid = true;	 
				}else{
					bValid = false;
				}
			}else{
				if(nodeText.equals(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}
		break;
		case "nodeTitle":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeTitle.contains(DBAttributeValue)){
					bValid = true;						
				}else{
					bValid = false;
				}
			}else{
				if(nodeTitle.equals(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}
		break;
		
		case "nodeClass":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeClass.contains(DBAttributeValue)){
					bValid = true;			 
				}else{
					bValid = false;
				}
			}else{
				if(nodeClass.equals(DBAttributeValue)){
					bValid = true;		 
				}else{
					bValid = false;
				}
			}
		break;
		case "nodeName":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeName.contains(DBAttributeValue)){
					bValid = true;			 
				}else{
					bValid = false;
				}
			}else{
				if(nodeName.equals(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}
		break;
		case "nodeValue":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeValue.contains(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}else{
				if(nodeValue.equals(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}
		break;
		case "nodeType":	
			if(DBAttributeCondition.equals("contains")){
				if(nodeType.contains(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}else{
				if(nodeType.equals(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}
		break;
		case "onclick":	
			if(DBAttributeCondition.equals("contains")){
				if(onclick.contains(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}else{
				if(onclick.equals(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}
		break;
		case "onkeydown":	
			if(DBAttributeCondition.equals("contains")){
				if(onkeydown.contains(DBAttributeValue)){
					bValid = true;					 
				}else{
					bValid = false;
				}
			}else{
				if(onkeydown.equals(DBAttributeValue)){
					bValid = true;				 
				}else{
					bValid = false;
				}
			}
		break;
		}//switch
		return bValid;
	}
	public static String getPageFilterForProduct(String ParentNodePath,String NodePath,int LevelID,String DBPosition,String ProjectID,String ProductName,String ParentFilter,int ParentSeq,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		ParentFilter=ParentFilter.replace('&','\'');
		String ProductFilter=customlibrary.FilterSetting.setProductFilter(ProductName);
		String PageFilter = ProductFilter;
		// Note: The symbol & cannot be included in PageFilter.   
		String logInfo="";
    	switch(ProductName){	    	
	    	case "Any": 
	    		PageFilter = PageFilter+"|//leaf";
			break;
	    	case "ControlMinder": 
	    		PageFilter = "//input[@type='submit']|//leaf";	
			break;
	    	case "ControlMonitor": 
	    		PageFilter = PageFilter+"|//leaf";
			break;
	    	case "MAA": 
	    		PageFilter = "//a|//option|//input|//button|//img|//span";		
	    		PageFilter = PageFilter+"|//leaf";
			break;
	    	case "APM": 
	    		PageFilter = PageFilter+"|//leaf";		
			break;
	    	case "Aspera": 
//	    		if(ParentFilter.contains("main")){
//	    			PageFilter="//a[starts-with(@href,'/ca-manual/')]|suburl";
//	    		}
//	    		if(ParentFilter.contains("suburl")){
//	    			PageFilter="//input[@type]|//a[onclick]|//button|//div|//img|//span|general";
//	    		}
	    		PageFilter = PageFilter+"|//leaf";
			break;
	    	case "ServiceCatalog": 
//	    		PageFilter="//leaf[@wait_for_create_xpath]";
	    		//home->request 
	    		
	    		if(nodeHref.contains("Node=iclaunchpad.my_requestscatalog")){	    			
	    			PageFilter = "//a[contains(@href, 'wpf?Node=icguinode.catalogrequestsearchpre')]|//a[contains(@href, 'wpf?Node=icguinode.catalogcheckout')]";	    			
	    		}
	    		if(nodeHref.contains("Node=icguinode.catalogbrowse")){	    			
	    			PageFilter = "//table/tbody/tr/td/a[@class='folder_label']/span|//table/tbody/tr/td/li/a[@class='datalink']";	    			
	    		}
	    		if(ParentFilter.equals("//table/tbody/tr/td/a[@class='folder_label']/span|//table/tbody/tr/td/li/a[@class='datalink']")){
	    				PageFilter="//span[@class='servicename_big_side_1']|//a[@id='anchor_1']";	
	    		}	    		
	    		if(ParentFilter.equals("//span[@class='servicename_big_side_1']|//a[@id='anchor_1']")){
    				PageFilter= ParentFilter;
	    		}
	    		
	    		//miao request start
	    		
	    		if(nodeHref.contains("icguinode.catalogpendingitems")){		
	    			PageFilter = "//div[@class='x-grid3-body']/div[1]/table/tbody/tr/td[2]/div/a"; //Name
	    			PageFilter += "|//div[@class='x-grid3-body']/div[1]/table/tbody/tr/td[3]/div/a"; //Requestd By
	    			PageFilter += "|//div[@class='x-grid3-body']/div/table/tbody/tr/td[8]/div/a"; // Submitted or Hold
	    			PageFilter += "|//div[@class='x-grid3-body']/div[1]/table/tbody/tr/td[last()]/div/table/tbody/tr[2]/td[2]/em"; ///em/button //img of drop down 
	    			PageFilter += "|//b_home_request_open";	
    			}
    			if(nodeHref.contains("icguinode.catalogrequesteditems")){		
	    			PageFilter = "//table[@class='table']/tbody/tr[1]/td/div[4]/a"; //The number of times you have ordered this item is: 1
	    			PageFilter += "|//leaf";
    			}
    			
	    		if(ParentFilter.contains("b_home_request_open")){
	    			if(nodeHref.contains("icguinode.requestprofile")){
	    				PageFilter = "/html/body/table/tbody/tr[3]/td/table[3]/tbody/tr/td/a[@class='tabs'][1]"; //Tracking
	    				PageFilter += "|/html/body/table/tbody/tr[3]/td/table[3]/tbody/tr/td/a[@class='tabs'][2]"; //Audit Trail
	    				PageFilter += "|/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/input"; //Email
	    				PageFilter += "|/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[4]/table/tbody/tr/td[2]/input"; //Copy
	    				PageFilter += "|/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[5]/table/tbody/tr/td[2]/input"; //Edit
	    				PageFilter += "|/html/body/table/tbody/tr[3]/td/table[3]/tbody/tr/td[11]/table/tbody/tr[2]/td/a"; //Notes
	    				PageFilter += "|/html/body/table/tbody/tr[3]/td/table[3]/tbody/tr/td[13]/table/tbody/tr[2]/td/a"; //Attachments
	    				PageFilter += "|//c_home_request_open_profile";
	    			}
	    			if(tagName.equals("em")){ // or button  img of drop down
	    				PageFilter = "/html/body/div[contains(@class,'x-menu')]/div/div/a/img"; // drop down
	    				PageFilter += "|//leaf";
	    				PageFilter += "|//d_home_request_open_dropdown";
	    			}
	    		}
	    		//Audit Trail
	    		if(ParentFilter.contains("c_home_request_open_profile")){
	    			if(nodeText.contains("Trail")){	    				
	    				PageFilter = "/html/body/table/tbody/tr[3]/td/table[3]/tbody/tr/td/table[2]/tbody/tr[2]/td[8]/a/img";
	    				PageFilter += "|//d_home_request_open_profile_trail_popup";
	    			}
	    		}
	    		//miao --home--> request end
	    		
	    		//begin adminstrator//configure tab
	    		if(nodeHref.contains("usm/wpf?Node=iclaunchpad.tools_configuration")){	    			
	    			PageFilter = "//table[@class='configLinks']/tbody/tr/td/span/a";
	    		}
	    		
	    		if(ParentFilter.contains("//table[@class='configLinks']/tbody/tr/td/span/a")){
	    			PageFilter="//table[@style='width: 100%;']/tbody/tr[2]/td/table[@class='blue']/tbody/tr/td[3]/a[@href='javascript:void(0)']/img";
	    		}
	    		if(nodeHref.contains("/usm/wpf?Node=iclaunchpad.fiscal_periods")){
	    			PageFilter="//input|//a[@onclick]";
	    		}
	    		//end adminstrator//configure tab
	    		
	    		//begin administrator---> report builder
	    		if(tagName.equals("a") && nodeClass.equals("table_view_options_link") && onclick.equals("toggleView()")){
	    			PageFilter = "//table[@id='reportobjs_list']/tbody/tr[40]/td[6]/img";
	    			PageFilter += "|//table[@id='reportobjs_list']/tbody/tr[45]/td[6]/img";
	    			PageFilter += "|//report_listend";
	    		}
	    		
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.report_data_views")){
	    			PageFilter ="//img[@src='images/wplus.gif']";
	    			PageFilter += "|//input[@name='aButton']";
	    			PageFilter += "|//plusflag-dataview";	    			
	    		}
	    		if(ParentFilter.contains("//plusflag-dataview")){
	    			PageFilter = "//table[@id='reportobjs_tree']/tbody/tr[3]/td[8]/img";	    			
	    			PageFilter += "|//table[@id='reportobjs_tree']/tbody/tr[8]/td[8]/img";
	    			PageFilter += "|//table[@id='reportobjs_tree']/tbody/tr[14]/td[8]/img";
	    			PageFilter += "|//table[@id='reportobjs_tree']/tbody/tr[31]/td[8]/img";
	    			PageFilter += "|//table[@id='reportobjs_tree']/tbody/tr[39]/td[8]/img";
	    			PageFilter += "|//table[@id='reportobjs_tree']/tbody/tr[45]/td[8]/img";
	    			PageFilter += "|//input[@nodeName='aButton']";
	    			PageFilter += "|//dataview-end";
	    		}
	    		
	    		if(nodeHref.contains("/wpf?Node=iclaunchpad.report_layouts")){
	    			PageFilter ="//table[@id='reportobjs_tree']/tbody/tr[2]/td/span/img[1]";
	    			PageFilter += "|//input[@name='aButton']";
	    			PageFilter += "|//plusflag-layout";
	    		}
	    		if(ParentFilter.contains("//plusflag-layout")){
	    			PageFilter = "//table[@id='reportobjs_tree']/tbody/tr[3]/td[7]/img";			
	    		
	    			PageFilter += "|//layout-end";
	    		}
	    		//end administrator---> report builder
	    		
	    		//begin administrator--->event rule action
	    		if(nodeHref.contains("/wpf?Node=iclaunchpad.rule_management")){
	    			PageFilter = "//input|//table[@id='searchresultstable']/tbody/tr[2]/td[4]/a/img";
	    			PageFilter += "|//eventruleaction-end";
	    		}
	    		//end administrator--->event rule action
	    		
	    		//begin administrator--->tools--/schedule
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.scheduler")){
	    			PageFilter = "//input[@name='addButton']|//table[@id='taskobjs']/tbody/tr[2]/td[6]/a/img";
	    			PageFilter += "|//toolschedule-end";
	    		}
	    		
	    		//end  administrator--->tools
	    		
	    		//begin tools--plugin
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.plugins")){
	    			PageFilter = "//a[contains(@href,'javascript:showPluginDetails')]";
	    			PageFilter += "|//toolsplugin-end";
	    		}
	    		
	    		//end tools--plugin
	    		
	    		//Acconting---data mediation  3-15-17,18,19
//	    		if(nodeHref.contains("wpf?Node=iclaunchpad.data_mediation")){
//	    			PageFilter="//a[contains(@class,'sidenavlink')]";
//	    			PageFilter+="|//datamediathird";
//	    		}
//	    		
//	    		if(ParentFilter.contains("//datamediathird") && nodeHref.contains("wpf?Node=iclaunchpad.data_processing_field")){
//	    			PageFilter="//table[@id='maintable']/tbody/tr[2]/td[2]/a";
//	    			PageFilter+="|//fileddefinition";	    			
//	    		}
	    		
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.data_mediation")){ 
                    PageFilter="//a[contains(@class,'sidenavlink')]"; 
                    PageFilter+="|//datamediathird"; 
	            } 
	            
	            if(ParentFilter.contains("//datamediathird") && nodeHref.contains("wpf?Node=iclaunchpad.data_processing_field")){
	
	                    PageFilter="//table[@id='maintable']/tbody/tr[2]/td[2]/a"; 
	                    PageFilter+="|//fileddefinition-link";                          
	            } 
	            if(ParentFilter.contains("//fileddefinition-link")){ 
	                    PageFilter="//option"; 
	                    PageFilter+="|//leaf";   
	            }
	
	            
	    		if(ParentFilter.contains("//datamediathird") && nodeHref.contains("wpf?Node=iclaunchpad.data_processing_profile_list")){
	    			PageFilter="//input[@name='addBtn']";
	    			PageFilter+="|//table[@id='tabNav']/tbody/tr/td/table/tbody/tr/td[2]/table[3]/tbody/tr/td/table[2]/tbody/tr[2]/td[9]/img[2]";
	    			PageFilter+="|//table[@id='tabNav']/tbody/tr/td/table/tbody/tr/td[2]/table[3]/tbody/tr/td/table[2]/tbody/tr[2]/td[9]/img[3]";
	    			PageFilter+="|//table[@id='tabNav']/tbody/tr/td/table/tbody/tr/td[2]/table[3]/tbody/tr/td/table[2]/tbody/tr[2]/td[9]/img[4]";
	    			PageFilter+="|//profilemanagement";	    			
	    		}
	    		//data management
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.data_processing_file")){
	    			PageFilter="//input[@name='importButton']|//input[@name='schButton']";
	    			PageFilter+="|//datamanagement";
	    		}
	    		//aggregation status
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.data_system_status")){
	    			PageFilter="//input[@name='schButton']";
	    			PageFilter+="|//aggregationstatus";
	    		}
	    		//metric	    		
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.application_metric")|nodeHref.contains("wpf?Node=iclaunchpad.application_pkg")){
	    			PageFilter="//table[@id='searchresultstable']/tbody/tr[2]/td[7]/a/img";
	    			PageFilter+="|//application_metric";
	    		}
	    		//application
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.application_pkg")){
	    			PageFilter="//table[@id=searchresultstable']/tbody/tr[2]/td[6]/a[1]/img";
	    			PageFilter+="|//application_pkg";
	    		}
	    		//adjustment
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.adjustments")){
	    			PageFilter="//a[contains(@class,'sidenavlink')]";
	    			PageFilter+="|//adjustment";
	    		}
	    		if(ParentFilter.contains("adjustment") && nodeHref.contains("wpf?Node=iclaunchpad.general_adjustments")){
	    			PageFilter="//input[@name='addButton']";
	    			PageFilter+="|//general";
	    		}
	    		
	    		//end Acconting---data mediation
	    		
	    		//Acconting---configure  3-16
	    		if(nodeHref.contains("usm/wpf?Node=iclaunchpad.billing_configuration")){
	    			PageFilter="//a[@class='anchor']|//a[starts-with(@href,'wpf?Node=')]";
	    		}
	    		if(ParentFilter.equals("//a[@class='anchor']|//a[starts-with(@href,'wpf?Node=')]")){
	    			PageFilter="//table[@id='configtable']/tbody/tr[2]/td[3]/a/img";
	    			PageFilter+="|//modifyimgend";
	    		}
	    		
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.fiscal_periods")){
	    			PageFilter="//input[@type='button']";
	    			PageFilter+="|//accountconfigperiod";
	    		}
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.exchange_rates")){
	    			PageFilter="//input[@onclick='selectDateRange()']";
	    			PageFilter+="|//exchange_rates-end";
	    		}
	    		
	    		//end Acconting---configure  3-16
	    		
	    		//accounting ---account management
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.subscriptions")){
	    			PageFilter = "//table[@id='searchresulttable']/tbody/tr[2]/td[6]/a/img";
	    			PageFilter += "|//table[@id='searchresulttable']/tbody/tr[2]/td[7]/a/img";
	    			PageFilter += "|//table[@id='searchresulttable']/tbody/tr[2]/td[8]/a/img";
	    			PageFilter += "|//table[@id='searchresulttable']/tbody/tr[2]/td[9]/a/img";
	    			PageFilter += "|//table[@id='searchresulttable']/tbody/tr[2]/td[10]/a/img";
	    			PageFilter += "|//input[contains(@onclick,'Node=icguinode.addregaccount')]";
	    			PageFilter += "|//accountmanagement";
	    		}
	    		//end account management
	    		
	    		//Catagory---configure  
	    		if(nodeHref.contains("usm/wpf?Node=iclaunchpad.catalog_configuration")){
	    			PageFilter="//a[starts-with(@href,'wpf?Node=')]|//a[contains(@href,'javascript:show')]";
	    		}
	    		if(ParentFilter.equals("//a[starts-with(@href,'wpf?Node=')]|//a[contains(@href,'javascript:show')]")){
	    			PageFilter="//img[contains(@src,'images/modify.gif')]|//input[contains(@title, 'Add')]";
	    		}	    		
	    		//end Catagory---configure  
	    		
	    		
	    		if(tagName.equals("a") && nodeHref.contains("usm/wpf?Node=iclaunchpad.tools")){
	    			PageFilter = PageFilter + "|//option";
	    		}
	    		
	    		//catalog : service offering tab
	    		if(nodeHref.contains("wpf?Node=iclaunchpad.catalog_builder")){
	    			PageFilter = "//a[starts-with(@href,'wpf?Node=')]";
	    		}
	    			    		
	    		//catalog_builder service_designer
	    		
	    		if(nodeHref.contains("Node=iclaunchpad.service_designer")){   
	    			PageFilter="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    			PageFilter+="/div/div/img[2]";
	    			PageFilter+="|//service_designer_01";
	    		}
	    		if(ParentFilter.contains("service_designer_")){ 
	    			if(ParentFilter.contains("service_designer_01")){ 	    				
	    				PageFilter="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    				PageFilter+="/div/div[2]";
						PageFilter+="/div/div/img[2][@onload]";
						PageFilter+="|";
						PageFilter+="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    				PageFilter+="/div/div[2]";
						PageFilter+="/div/div/span[2]";
						PageFilter+="|//service_designer_02";
	    			}
	    			if(ParentFilter.contains("service_designer_02")){ 	  			
	    				PageFilter="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div/img[2][@onload]";
	    				PageFilter+="|";
	    				PageFilter+="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div/span[2]";
	    				PageFilter+="|//service_designer_03";
	    				//skip above	    
	    				if(ParentSeq==1){
	    					PageFilter="//ul/li/a[@class='x-tab-right']/em/span/span"; 
	    					PageFilter+="|//table/tbody/tr/td/table/tbody/tr/td[2]/input";
	    					PageFilter+="|//service_designer_right";
	    				}else{
	    					PageFilter="//leaf";
	    					// seq <> 1 , many, not leaf
	    					PageFilter="//ul/li/a[@class='x-tab-right']/em/span/span"; 
	    					PageFilter+="|//table/tbody/tr/td/table/tbody/tr/td[2]/input";
	    					PageFilter+="|//service_designer_right";
	    				}
	    				//skip all above
	    				if(nodeText.contains("Business Cards")){ //only one item can be guided to right
	    					PageFilter="//ul/li/a[@class='x-tab-right']/em/span/span"; 
	    					PageFilter+="|//table/tbody/tr/td/table/tbody/tr/td[2]/input";
	    					PageFilter+="|//service_designer_right";
	    				}else{
	    					PageFilter="//leaf";
	    				}
	    			}
	    			if(ParentFilter.contains("service_designer_03")){ 
	    				PageFilter="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div/img[2][@onload]";
	    				PageFilter+="|";
	    				PageFilter+="//div[@class='x-panel-body x-panel-body-noheader']/div/div/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div[2]";
	    				PageFilter+="/div/div/span[2]";
	    				PageFilter+="|//service_designer_04";
	    				//skip above
	    			}
	    			if(ParentFilter.contains("service_designer_right")){ 
	    				PageFilter="//leaf";
	    				//also set unique object for tag a
	    				if(nodeText.contains("Option Groups")){ 
//	    					PageFilter="//ul/li/a[@class='x-tab-right']/em/span/span[last()]";  //Definition
	    					PageFilter="//ul/li/a/em/span/span";  //Definition
	    					PageFilter="//table/tbody/tr/td/div/div/div/div[2]/div[2]/div/div/div/ul/li[2]/a/em/span/span";  //Definition
	    					PageFilter+="|//service_designer_right_OptionGroups";
	    				}
	    				if(ParentFilter.contains("service_designer_right_OptionGroups")){
	    					//table[@class='selection-container']/tbody/tr[1]
	    					PageFilter="//tr[@name='layout_row'][1]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div[1]/ul/li[1]/a[@onclick]";
	    					PageFilter+="|//service_designer_end";
		    			}
	    				if(nodeText.contains("Permissions")){ 
	    					PageFilter="//a[@id='tab_groups'][@onclick]"; //Groups
	    					PageFilter+="|//service_designer_end";
	    				}
	    				if(nodeText.contains("Related Offerings")){ 
	    					PageFilter="//table[@name='cattreetable']/tbody/tr/td/span/a"; //Related Offerings
	    					PageFilter+="|//service_designer_right_related";
	    				}
	    				if(ParentFilter.contains("service_designer_right_related")){
	    					PageFilter="//table[@name='cattreetable']/tbody/tr/td/span/a"; 
	    					PageFilter+="|//service_designer_right_related_1";
		    			}
	    				if(ParentFilter.contains("service_designer_right_related_1")){
	    					PageFilter="//table[@name='cattreetable']/tbody/tr/td/span/a"; 
	    					PageFilter+="|//leaf";
		    			}
	    			}
	    			if(ParentFilter.contains("service_designer_end")){ 
	    				PageFilter="//leaf";
	    			}
	    		}  		
	    		
	    		
	    		//begin forms tab   
	    		if(nodeHref.contains("Node=iclaunchpad.form_designer")){	    //02-8		 	
	    			PageFilter = "//img[contains(@class,'x-tree3-node-joint')]";	
	    			PageFilter +="|//form_designer_00";
	    		}
	    		if(ParentFilter.contains("form_designer_00")){
	    			PageFilter="//div[2]/div/div/img[2]";
	    			PageFilter +="|//div[2]/div[2]/div/div/img[2]";
	    			PageFilter +="|//div[2]/div[2]/div[2]/div/img[2]";	
	    			PageFilter +="|//form_designer_01";
	    		}
	    		if(ParentFilter.contains("form_designer_01")){
	    			PageFilter="//div[2]/div[2]/div[2]/div/div/img[2]";
	    			PageFilter +="|//form_designer_02";	    
	    		}
	    		if(ParentFilter.contains("form_designer_02")){
	    			PageFilter="//div[2]/div[2]/div[2]/div/div[2]/div[2]/div/img[2]";
	    			PageFilter +="|//form_designer_03";	   
	    		}
	    		if(ParentFilter.contains("form_designer_03")){
	    			PageFilter="//div[2]/div[2]/div[3]/div/img[3]";  
	    			PageFilter +="|//form_designer_end";	
	    		}
	    		if(ParentFilter.contains("form_designer_end")){
	    			PageFilter="//leaf";  
	    		}	
	    		//end forms tab
	    		
	    		//begin policy tab
	    		if(nodeHref.contains("/usm/wpf?Node=iclaunchpad.policy_builder")){
	    			PageFilter ="//img[2]";
	    		}
	    		
	    		if(tagName.equals("img") && nodeClass.equals(" x-tree3-node-joint") && ParentFilter.equals("//img[2]")){
	    			
	    			PageFilter = "//img[@class=' x-tree3-node-icon']";
	    		}
	    		//end policy tab
	    		
	    		if(nodeClass.equals("about")){
	    			PageFilter = "//span[@class='x-tab-strip-inner']/span[@class='x-tab-strip-text  ']";		
	    		}

	    		//Miao to generate sub menu
	    		if(LevelID==1){
	    			PageFilter="//a[starts-with(@href,'wpf?Node=')]";
	    		}
			break;	
	    	case "ITPAM": 
	    		    		
	    		//set xpath for last checkbox to check all
	    		
	    		if(LevelID>0){
	    			PageFilter="//a";
	    			PageFilter+="|//input";
	    			PageFilter+="|//button";
	    			PageFilter+="|//span";
	    		}
	    		PageFilter="//leaf[@wait_for_create_xpath]";
	    		
	    		//skip for all operations sub items which have not been given new PageFilter
	    		if(ParentFilter.contains("operations_")){
//                	PageFilter="//skip[@not_special]";
                }	
	    		
	    		/******* Home *********/
	    		if(tagName.equals("span")){ 
                    if(nodeText.contains("Home")){ 
                    	PageFilter="//a[@href='#']";
                    } 
	            } 
	    		if(ParentFilter.equals("//a[@href='#']")){
	    			PageFilter="//leaf@1_not_unique";
                }
	    		/******* Reports *********/
	    		if(tagName.equals("span")){ 
                    if(nodeText.contains("Reports")){ 
                    	//last() -> check all                    	
                    	PageFilter="//div/div[last()]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']"; //checkbox
                    } 
	            } 
	    		if(ParentFilter.equals("//div/div[last()]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']")){
	    			PageFilter="//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div";  //9 items
                 	PageFilter+="|//button";                 	//new,share, delete, unshare
                 	PageFilter+="|//img[@class='x-form-trigger x-form-trigger-arrow']"; //my report
                }
	    		
	    		//9 items report
	    		if(ParentFilter.equals("//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div|//button|//img[@class='x-form-trigger x-form-trigger-arrow']")){
	    			if(tagName.equals("div")){
	    				PageFilter="//input[contains(@value,'OK')]"; 
	    			}
                } 
	    		if(ParentFilter.equals("//input[contains(@value,'OK')]")){
	    			PageFilter="//input[@type='image']";
                }
	    		if(ParentFilter.equals("//input[@type='image']")){
	    			PageFilter="//leaf@1_and_unique";
                }
	    		//button and img
	    		if(ParentFilter.equals("//div[contains(@class,'x-grid3-row-collapsed')]/table[@class='x-grid3-row-table']/tbody/tr/td[2]/div|//button|//img[@class='x-form-trigger x-form-trigger-arrow']")){
	    			if(tagName.equals("button")||tagName.equals("img")){
	    				PageFilter="//leaf@1_not_unique";
	    			}	    			
                } 
	    		
	    		
                
	    		
	    		/******* Operations *********/
	    		if(tagName.equals("span")){ 
                    if(nodeText.contains("Operations")){ 
                    	//div links under link
                    	//span under Content Package, Process Watch, Start Requests, DataSets, Resources, Schedules
 					 	PageFilter="//div[@class='x-grid3-body']/div/table/tbody/tr/td/div[contains(@class,'-LINK')]";  
 					 	PageFilter+="|//div[@class='x-panel-body x-panel-body-noheader x-panel-body-noborder']/div/div/span";
 					 	PageFilter+="|//operations[@level='2']";
                    } 
	            } 
	    		
                //Content Package, Process Watch, Start Requests, DataSets, Resources, Schedules
                //img
                if(ParentFilter.contains("//operations[@level='2']")){
                	if(tagName.equals("span")){
                		//PageFilter="//div[@class='x-grid3-body']/div/table/tbody/tr/td/div/div/div/img[2]"; 
                		//PageFilter="//div[@class=' x-panel x-component ']/div[2]/div[@class='x-panel-body']/div[@class='Operations_SRF_Panel']/div[2]/div[@class='x-panel-body x-panel-body-noheader']/div[2]/div[@class='x-grid3']/div[@class='x-grid3-viewport']/div[2]/div[@class='x-grid3-body']/div/table/tbody/tr/td/div/div/div/img[2]";
                		PageFilter="//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div/div/img[2][@onload]";
                		if(nodeText.contains("Content Packages")){
                			PageFilter+="|//operations_ContentPackages[@level='3']";
                		}
                		if(nodeText.contains("Process Watch")){
                			PageFilter+="|//operations_ProcessWatch[@level='3']";
                		}
                		if(nodeText.contains("Start Requests")){
                			PageFilter+="|//operations_StartRequests[@level='3']";
                			
                			//not click img, go to span directly
                			PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
            				PageFilter+="|//operations_span";
                		}
                		if(nodeText.contains("Datasets")){
                			PageFilter+="|//operations_Datasets[@level='3']";
                			
                			//not click img, go to span directly
                			PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
            				PageFilter+="|//operations_span";
                		}
                		if(nodeText.contains("Resources")){
                			PageFilter+="|//operations_Resources[@level='3']";
                			
                			//not click img, go to span directly
                			PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
            				PageFilter+="|//operations_span";
                		}
                		if(nodeText.contains("Schedules")){
                			PageFilter+="|//operations_Schedules[@level='3']";
                			
                			//not click img, go to span directly
                			PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
            				PageFilter+="|//operations_span";
                		}
                	}
                }
                //img and span
                if(ParentFilter.contains("operations_")){
                	if(ParentFilter.contains("StartRequests")){
                		switch(ParentSeq){ //adjust for new build
                			case 2: //PAM_PreDefinedContent
                				PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter+="|//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'Getting Started')]/div/img[2][@onload][contains(@style,'-66px')]";
                				PageFilter+="|//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'User Interaction Forms')]/div/img[2][@onload][contains(@style,'-66px')]";
                				PageFilter+="|//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter+="|//operations_span_StartRequests[@level='4']";
                			break;
                		}
                	}
                	if(ParentFilter.contains("operations_Datasets")){
                		switch(ParentSeq){ 
                			case 1: //only click first img, it can be customized
//                				PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter="//div[@class='x-grid3-body']/div[last()]/table/tbody/tr/td/div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter+="|//operations_span_Datasets[@level='4']";
                			break;
                		}
                	}
                	if(ParentFilter.contains("operations_Resources")){
                		switch(ParentSeq){ 
                			case 1: //only click first img, it can be customized
//                				PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter="//div[@class='x-grid3-body']/div[last()]/table/tbody/tr/td/div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter+="|//operations_span_Resources[@level='4']";
                			break;
                		}
                	}
                	if(ParentFilter.contains("operations_Schedules")){
                		switch(ParentSeq){ 
                			case 1: //only click first img, it can be customized
                				PageFilter="//div[@class='x-grid3-body']/div[last()]/table/tbody/tr/td/div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter+="|//operations_span_Schedules[@level='4']";
                			break;
                		}
                	}
                	
                	if(ParentFilter.contains("operations_ProcessWatch")){
                		switch(ParentSeq){ //adjust for new build
                			case 1: //ContentPackage
                				PageFilter="//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'Pack')]/div/img[2][@onload][contains(@style,'-66px')]";
                            	PageFilter+="|//operations_ProcessWatch_ContentPackage[@level='4']";
                			break;
                			case 2: //ContentPackage
                				PageFilter="//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'Pack')]/div/img[2][@onload][contains(@style,'-66px')]";
                            	PageFilter+="|//operations_ProcessWatch_ContentPackage[@level='4']";
                			break;
                		}
                		
                		//click img once, not click img again, go to span directly
                		PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
        				PageFilter+="|//operations_span";
                		
                	}
                	if(ParentFilter.contains("operations_ProcessWatch_ContentPackage")){
                		switch(ParentSeq){ //adjust for new build
                			case 1: //ContentPack
                				PageFilter="//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'Process')]/div/img[2][@onload][contains(@style,'-66px')]";
                            	PageFilter+="|//operations_ProcessWatch_Content_Pack[@level='5']";
                			break;
                		}
                		
//                		PageFilter="//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'Process')]/div/img[2][@onload][contains(@style,'-66px')]";
//                    	PageFilter+="|//operations_ProcessWatch_Content_Pack[@level='5']";
                    	
                    	PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
        				PageFilter+="|//operations_span_ProcessWatch[@level='6']";
                		
                	}
                	if(ParentFilter.contains("operations_ProcessWatch_Content_Pack")){ //Only 2 layers, not layers, so discard, it is defined above
                		switch(ParentSeq){ //adjust for new build
                			case 1: //Process Watch
//                				PageFilter+="|//div[@class=' x-panel x-component ']/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/div/table/tbody/tr/td/div/div[contains(@id,'Test')]/div/img[2][@onload][contains(@style,'-66px')]";
                				PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
                				PageFilter+="|//operations_span_ProcessWatch[@level='6']";
                			break;
                		}
                		
                		PageFilter="//div[@class='x-grid3-cell-inner x-grid3-col-displayName']/div/div/span[2]";
        				PageFilter+="|//operations_span_ProcessWatch[@level='6']";
                	}
                	if(ParentFilter.contains("operations_span")){
                		PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
            			PageFilter+="|//operations_div_checker";
                		
                		//It is better to set //" x-grid3-hd-inner x-grid3-hd-checker x-component"
                		if(ParentFilter.contains("_ProcessWatch")){
	                		if(ParentSeq==4){//or remove it
	                			PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
	                			PageFilter+="|//operations_div_checker[@level='7']";
	                		}
                		}
                		
                		//not use if(seq==4){. If customization, please change it
                		if(ParentFilter.contains("_StartRequests")){
                			PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                			PageFilter+="|//operations_div_checker[@level='5']";
                		}
                		if(ParentFilter.contains("_Datasets")){
                			PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                			PageFilter+="|//operations_div_checker[@level='5']";
                		}
                		if(ParentFilter.contains("_Resources")){
                			PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                			PageFilter+="|//operations_div_checker[@level='5']";
                		}
                		if(ParentFilter.contains("_Schedules")){
                			PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                			PageFilter+="|//operations_div_checker[@level='5']";
                		}
                	}
                	if(ParentFilter.contains("operations_div_checker")){
                		PageFilter="//table[@isenabled='true']/tbody/tr[2]/td[2]/em/button";
//                		PageFilter+="|//operations_button_end";
                		PageFilter+="|//operations_link_button_all";
                		
                	}                	
                	if(ParentFilter.contains("operations_button_end")){
                		PageFilter="//leaf@1_and_unique";
                	}
                }
                
                
                //div links under Link
                if(ParentFilter.contains("//operations[@level='2']")){
                	if(tagName.equals("div")){
                		PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                    	//PageFilter="//div[@class='x-grid3-scroller']/div/div/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']"; //all selected                		
//                		PageFilter="//div[@class=' x-panel x-component ']/div[2]/div[@class='x-panel-body']/div[@class='Operations_SRF_Panel']/div[2]/div[@class='x-panel-body x-panel-body-noheader']/div[2]/div[@class='x-grid3']/div[@class='x-grid3-viewport']/div[2]/div[@class='x-grid3-body']/div/table/tbody/tr/td/div/div/div/img[2]";
//                		PageFilter+="|//operations_div_link_span[@level='3']";
                		if(nodeText.contains("Process Instances")){
                			PageFilter+="|//operations_link-ProcessInstances[@level='3']";
                		}
                		if(nodeText.contains("Operators")){
                			PageFilter+="|//operations_link-Operators[@level='3']";
                		}
                		if(nodeText.contains("Tasks")){
                			PageFilter="//div/div[3]/table/tbody/tr/td/div/img[@class='x-form-trigger x-form-trigger-arrow']";
                			PageFilter+="|//operations_task_img_all";
                		}
                		if(nodeText.contains("Active Schedules")){
                			PageFilter+="|//operations_link-ActiveSchedules[@level='3']";
                		}
                		if(nodeText.contains("Global Schedules")){
                			PageFilter+="|//operations_link-GlobalSchedules[@level='3']";
                		}
                		if(nodeText.contains("Start Requests")){
                			PageFilter+="|//operations_link-StartRequests[@level='3']";
                		} 
                	}
                }
//                if(ParentFilter.contains("//operations_link-Tasks")){
//                	PageFilter="//div/div[3]/table/tbody/tr/td/div/img[@class='x-form-trigger x-form-trigger-arrow']";
//                	PageFilter+="|//operations_link-Tasks-Right";
//                }
                if(ParentFilter.contains("//operations_task_img_all")){
                	PageFilter="/html/body/div[5]/div/div[3]";
                	PageFilter+="|//operations_task_got_to_checker";
                }
                if(ParentFilter.contains("operations_task_got_to_checker")){
                	PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                	//PageFilter="//div[@class='x-grid3-scroller']/div/div/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']"; //all selected
                	PageFilter+="|//operations_task_go_to_button";
                }                
                if(ParentFilter.contains("//operations_task_go_to_button") ){                	
            		PageFilter="//table[@isenabled='true']/tbody/tr[2]/td[2]/em/button|//operations_task_nonlink_button_all";    
            	}
                if(ParentFilter.contains("//operations_link")){                	
            		PageFilter="//table[@isenabled='true']/tbody/tr[2]/td[2]/em/button|//operations_link_button_all";      
            		if( ParentFilter.contains("//operations_link-ActiveSchedules") || ParentFilter.contains("//operations_link-GlobalSchedules")){
            			PageFilter="//table/tbody/tr[2]/td[2]/em/button[@aria-disabled='false']|//operations_link_button_all";  
            		}
            	}
                if(ParentFilter.contains("operations_task_nonlink_button_all")){
                	PageFilter="//leaf@1_not_unique";
                	if(nodeText.contains("Delegate") || nodeText.contains("Transfer") || nodeText.contains("Properties")){
                		//PageFilter="//ul[@class='x-tab-strip x-tab-strip-top']/li[contains(@id,'tasklist')]/a[2]/em/span[contains(@class,'x-tab-strip-inner']/span";
                		PageFilter="//ul[@class='x-tab-strip x-tab-strip-top']/li/a[2]/em/span[contains(@class,'x-tab')]/span";
            			PageFilter+="|//operations_task_button_layer";
	    			}
                	if(nodeText.contains("Task Properties")){
                		PageFilter="//table/tbody/tr[2]/td[2]/em/button";
            			PageFilter+="|//operations_button_globalschedule_layer";
	    			}
        		}
                if(ParentFilter.contains("operations_link_button_all")){
                	PageFilter="//leaf@1_not_unique";
                	if(nodeText.contains("Properties")){
                		PageFilter="//div[@class='x-window-body']/div/div[2]/div/div/div/div/table/tbody/tr/td/div";
                		PageFilter+="|//operations_span_div_end";
	    			}
                	if(nodeText.contains("Task Properties")){
                		PageFilter="//table/tbody/tr[2]/td[2]/em/button";
            			PageFilter+="|//operations_button_globalschedule_layer";
	    			}
        		}
                if(ParentFilter.contains("operations_button_globalschedule_layer")){
                	//PageFilter="//div[@class='x-window-mo']/div/div[2]/div/div/div/span";
                	PageFilter="//div[@class='x-window-body']/div/div[2]/div/div/div/div/table/tbody/tr/td/div";
                	PageFilter+="|//operations_span_div_end";
        		}
                if(ParentFilter.contains("operations_task_button_layer")){ //task
                	//PageFilter="//div[@class='x-window-mo']/div/div[2]/div/div/div/span";
//                	PageFilter="//div[@class='x-window-body']/div/div[2]/div/div/div/div/table/tbody/tr/td/div";
//        			PageFilter+="|//operations_span_div_end";
                	PageFilter="//leaf@1_and_unique";
        		}
            	if(ParentFilter.contains("operations_span_div_end")){
            		PageFilter="//leaf@1_and_unique";
            	}       
            	
                //Set Leaf for other div under Links , remove if not used
                if(ParentFilter.equals("//operations_link_END")){
                	PageFilter="//leaf@1_not_unique";
                }
                
	    		//Link of Links
                //duplicate definition
//                if(ParentFilter.contains("//div[@class='x-grid3-body']/div/table/tbody/tr/td/div[contains(@class,'-LINK')]")){
//                	if(nodeClass.contains("-LINK")){
//                		PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
//                	}
//                }
                // Link of Content Package, Process Watch, Start Requests, DataSets, Resources, Schedules
                if(ParentFilter.contains("???????")){
                	if(nodeClass.contains("-LINK")){
                		PageFilter="//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']";
                	}
                }
                //First button line, should be unique for name
                if(ParentFilter.equals("//div[@class='x-grid3-scroller']/div/div[1]/table/tbody/tr/td/div/div[@class='x-grid3-row-checker']")){
                	 PageFilter="//table[starts-with(@id,'PW_TB_TLBR_BTN')]/tbody/tr[2]/td[2]/em/button";
                }
                if(ParentFilter.equals("//table[starts-with(@id,'PW_TB_TLBR_BTN')]/tbody/tr[2]/td[2]/em/button")){
                	//Set unique with name
                }
                //DataSet
                if(ParentFilter.equals("//table[starts-with(@id,'PW_TB_TLBR_BTN')]/tbody/tr[2]/td[2]/em/button")){
                	PageFilter="//img[@class=' x-tree3-node-joint']";
                }
                //Set Leaf for DataSet childs
                if(ParentFilter.equals("//img[@class=' x-tree3-node-joint']")){
                	PageFilter="//leaf@1_not_unique";
                }
                
                /**** Configuration ********/
                
                if(tagName.equals("span")){ 	    			
                    if(nodeText.contains("Configuration Browser")){ 
                    	
	    			}else{
	                    if(nodeText.contains("Configuration")){ 
	   					 	PageFilter="//div[@class='x-panel-body x-panel-body-noheader x-panel-body-noborder']/div/div/span|//div[@class='x-grid3-body']/div/table/tbody/tr/td/div/div/div/img[2]";
	   					 	PageFilter+="|//Configuration[@level='2']";
	                    } 
	    			}
                }
                /*** miao
                //if(ParentFilter.equals("//div[@class='x-panel-body x-panel-body-noheader x-panel-body-noborder']/div/div/span|//div[@class='x-grid3-body']/div/table/tbody/tr/td/div/div/div/img[2]")){
                if(ParentFilter.contains("//Configuration[@level='2']")){
                	if(tagName.equals("span")){// all child spans in different levels under Manage User Resource and Installation, such as agent resource on level 2, 3 
                		PageFilter="//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]";
                		PageFilter+="|//Configuration[@only_span]";
                	}
                	if(tagName.equals("img")){ //for domain img, add Default Environment img
                		PageFilter="//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]|//div[@class='x-grid3-body']/div[2]/table/tbody/tr/td/div/div/div/img[2]";
                		PageFilter+="|//Configuration[@img_span]";
                	}                	
                }
                
                //Manage User Resource and Installation
                if(ParentFilter.contains("//Configuration[@img_span]")){
                	PageFilter="//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]"; 	
                	PageFilter+="|//Configuration[@only_span]";
                }
                //Manage User Resource and Installation
                if(ParentFilter.contains("//Configuration[@only_span]")){
                	PageFilter="//turn_right";        	
                	PageFilter+="|//Configuration[@right]";
                }
                if(ParentFilter.contains("//Configuration[@right]")){
                	PageFilter="//leaf";	
                }
                ***/
              //if(ParentFilter.equals("//div[@class='x-panel-body x-panel-body-noheader x-panel-body-noborder']/div/div/span|//div[@class='x-grid3-body']/div/table/tbody/tr/td/div/div/div/img[2]")){
                if(ParentFilter.contains("//Configuration[@level='2']")){
                	if(tagName.equals("span")){// all child spans in different levels under Manage User Resource and Installation, such as agent resource on level 2, 3 
                		PageFilter="//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]";
                	}
                	if(tagName.equals("img")){ //for domain img, add Default Environment img
                		PageFilter="//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]|//div[@class='x-grid3-body']/div[2]/table/tbody/tr/td/div/div/div/img[2]";
                	}                	
                }
                //Manage User Resource and Installation
                if(ParentFilter.equals("//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]")){
                	PageFilter="//li[contains(@id,'configBrowser.tabpanel__x-auto')]/a/em/span/span";
                }
                if(ParentFilter.equals("//li[contains(@id,'configBrowser.tabpanel__x-auto')]/a/em/span/span")){
                	PageFilter="//table[contains(@id,'configBrowser.')]//button";
                }
                
                if(ParentFilter.equals("//table[contains(@id,'configBrowser.')]//button[5]")){
                	PageFilter="//leaf@1_not_unique";        	
                }
                          
                //for Default Environment 
                if(ParentFilter.equals("//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]|//div[@class='x-grid3-body']/div[2]/table/tbody/tr/td/div/div/div/img[2]")){
                	PageFilter="//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]";    	
                }
                //for Default Environment 
                if(ParentFilter.equals("//div[@class='x-grid3-body']/div[@class='x-grid3-row  x-unselectable-single']/table/tbody/tr/td/div/div/div/span[2]")){
                	PageFilter="//leaf@1_not_unique";	
                }
                
                //by michelle installation
                if(ParentFilter.equals("//div[@class='x-panel-body x-panel-body-noheader x-panel-body-noborder']/div[3]//span)")){
                	PageFilter="//div[@class='x-grid3-viewport']//a[@id='_anchor']";    	
                }
                
                if(ParentFilter.equals("//div[@class='x-grid3-viewport']//a[@id='_anchor'][4]")){
                	PageFilter="//leaf@1_not_unique";	
                }
			break;	
	    	
	    	case "Clarity": 
	    		
			break;	
	    	
    	}
    	ManageDB mdb = new ManageDB();
		mdb.getConnection(DBPosition);
		ResultSet rs = null;		
		String nodeParentSeq=","+String.valueOf(ParentSeq)+",";
		String DBXpathID="";
		String DBParentName="";	String DBParentSeq=""; 
		String DBXPATHName="";String DBXPATH="";String DBPageFilter=""; String DBNodeLevelID="";
		String DBtagName="";String DBAttribute="";String DBAttributeCondition="";String DBAttributeValue="";		
		String sql="select * from XpathList where ProjectID='"+ProjectID+"'"; 
		boolean bTag=false;boolean bAttribute=false;boolean bParent=false; boolean bLevel=false; boolean bMatch=false;
		try{
			rs=mdb.getSQL(sql);
			while(rs.next()){		
				DBXpathID=rs.getString("XpathID");
				DBParentName=rs.getString("ParentName");
				DBParentSeq=","+rs.getString("ParentSeq")+",";		
				DBNodeLevelID=rs.getString("NodeLevelID");
				DBtagName=rs.getString("tagName");
				DBAttribute=rs.getString("Attribute");
				DBAttributeCondition=rs.getString("AttributeCondition");
				DBAttributeValue=rs.getString("AttributeValue");
				DBXPATHName=rs.getString("XPATHName");
				DBXPATH=rs.getString("XPATH");
				DBXPATH=DBXPATH.replace('&', '\'');
				DBPageFilter=DBXPATH+"|//name_"+DBXPATHName;
				bTag=false;
				bAttribute=false;
				bParent=false;
				bLevel=false;
				
				if(!DBtagName.equals("-1")){
					bTag=true;
				}
				if(!DBParentName.equals("-1")){
					bParent=true;				
				}
				if(!DBNodeLevelID.equals("-1")){
					bLevel=true;
				}
				if(!DBAttribute.equals("-1")){		
					bAttribute=true;
				}
//				if(!bTag&&!bParent&&!bAttribute){
//					PageFilter=DBPageFilter;
//					System.out.println("[XPATH:] 1 Seq="+ParentSeq+",PageFilter = "+PageFilter);
//				}
				if(bTag&&!bParent&&!bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							if(tagName.equals(DBtagName)){
								PageFilter=DBPageFilter;			
								logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);							
							}
						}
					}else{
						if(tagName.equals(DBtagName)){
							PageFilter=DBPageFilter;			
							logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							//System.out.println("[XPATH:] 2 tagName="+tagName+" Seq="+ParentSeq+",PageFilter = "+PageFilter);
							//break;
						}
					}
				}
				if(!bTag&&bParent&&!bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							if(ParentFilter.contains(DBParentName)){
								if(DBParentSeq.length()>2){
									if(DBParentSeq.contains(nodeParentSeq)){ 
										PageFilter=DBPageFilter;	
										logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										//System.out.println("[XPATH:] 3 seq="+ParentSeq+",PageFilter = "+PageFilter);
										//break;
									}
								}else{
									PageFilter=DBPageFilter;	
									logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									//System.out.println("[XPATH:] 31 seq="+ParentSeq+",ParentFilter="+ParentFilter+",PageFilter = "+PageFilter);
									//break;
								}
							}
						}
					}
					else{
						if(ParentFilter.contains(DBParentName)){
							if(DBParentSeq.length()>2){
								if(DBParentSeq.contains(nodeParentSeq)){ 
									PageFilter=DBPageFilter;	
									logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									//System.out.println("[XPATH:] 3 seq="+ParentSeq+",PageFilter = "+PageFilter);
									//break;
								}
							}else{
								PageFilter=DBPageFilter;	
								logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								//System.out.println("[XPATH:] 31 seq="+ParentSeq+",ParentFilter="+ParentFilter+",PageFilter = "+PageFilter);
								//break;
							}
						}
					}
					if(PageFilter.equals(ParentFilter)){
						PageFilter="//leaf";
					}
				}
				if(bTag&&bParent&&!bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							if(ParentFilter.contains(DBParentName)){
								if(tagName.equals(DBtagName)){
									if(DBParentSeq.length()>2){
										if(DBParentSeq.contains(nodeParentSeq)){ 
											PageFilter=DBPageFilter;	
											logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											//System.out.println("[XPATH:] 4 tagName="+tagName+" seq="+ParentSeq+",PageFilter = "+PageFilter);
											//break;
										}
									}else{
										PageFilter=DBPageFilter;	
										logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										//System.out.println("[XPATH:] 41 seq="+ParentSeq+",tagName="+tagName+" ParentFilter="+ParentFilter+",PageFilter = "+PageFilter);
										//break;
									}
								}
							}
						}
					}
					else{
						if(ParentFilter.contains(DBParentName)){
							if(tagName.equals(DBtagName)){
								if(DBParentSeq.length()>2){
									if(DBParentSeq.contains(nodeParentSeq)){ 
										PageFilter=DBPageFilter;	
										logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										//System.out.println("[XPATH:] 4 tagName="+tagName+" seq="+ParentSeq+",PageFilter = "+PageFilter);
										//break;
									}
								}else{
									PageFilter=DBPageFilter;	
									logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									//System.out.println("[XPATH:] 41 seq="+ParentSeq+",tagName="+tagName+" ParentFilter="+ParentFilter+",PageFilter = "+PageFilter);
									//break;
								}
							}
						}
					}
					if(PageFilter.equals(ParentFilter)){
						PageFilter="//leaf";
					}
				}				
				if(!bTag&&!bParent&&bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
							if(bMatch){
								PageFilter=DBPageFilter;
								logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								//System.out.println("[XPATH:] 5 seq="+ParentSeq+",PageFilter = "+PageFilter);
								//break;
							}
						}
					}
					else{
						bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
						if(bMatch){
							PageFilter=DBPageFilter;
							logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
							RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
							//System.out.println("[XPATH:] 5 seq="+ParentSeq+",PageFilter = "+PageFilter);
							//break;
						}
					}
				}
				if(bTag&&!bParent&&bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							if(tagName.equals(DBtagName)){
								bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
								if(bMatch){
									PageFilter=DBPageFilter;
									logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									//System.out.println("[XPATH:] 6 seq="+ParentSeq+",PageFilter = "+PageFilter);
									//break;
								}
							}
						}
					}
					else{
						if(tagName.equals(DBtagName)){
							bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
							if(bMatch){
								PageFilter=DBPageFilter;
								logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
								RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
								//System.out.println("[XPATH:] 6 seq="+ParentSeq+",PageFilter = "+PageFilter);
								//break;
							}
						}
					}
				}
				if(!bTag&&bParent&&bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							if(ParentFilter.contains(DBParentName)){
								if(DBParentSeq.length()>2){
									if(DBParentSeq.contains(nodeParentSeq)){ 
										bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
										if(bMatch){
											PageFilter=DBPageFilter;
											logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											//System.out.println("[XPATH:] 7 seq="+ParentSeq+",PageFilter = "+PageFilter);
											//break;
										}
									}
								}else{
									bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
									if(bMatch){
										PageFilter=DBPageFilter;
										logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										//System.out.println("[XPATH:] 71 seq="+ParentSeq+",PageFilter = "+PageFilter);
										//break;
									}
								}
							}
						}
					}
					else{
						if(ParentFilter.contains(DBParentName)){
							if(DBParentSeq.length()>2){
								if(DBParentSeq.contains(nodeParentSeq)){ 
									bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
									if(bMatch){
										PageFilter=DBPageFilter;
										logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										//System.out.println("[XPATH:] 7 seq="+ParentSeq+",PageFilter = "+PageFilter);
										//break;
									}
								}
							}else{
								bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
								if(bMatch){
									PageFilter=DBPageFilter;
									logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
									RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
									//System.out.println("[XPATH:] 71 seq="+ParentSeq+",PageFilter = "+PageFilter);
									//break;
								}
							}
						}
					}
					
					if(PageFilter.equals(ParentFilter)){
						PageFilter="//leaf";
					}
				}
				if(bTag&&bParent&&bAttribute){
					if(bLevel){
						if(DBNodeLevelID.equals(LevelID)){
							if(tagName.equals(DBtagName)){
								if(ParentFilter.contains(DBParentName)){
									if(DBParentSeq.length()>2){
										if(DBParentSeq.contains(nodeParentSeq)){ 
											bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
											if(bMatch){
												PageFilter=DBPageFilter;										
												logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
												RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
												logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
												RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
												//System.out.println("[XPATH:] 8 seq="+ParentSeq+",PageFilter = "+PageFilter);
												//break;
											}
										}
									}else{
										bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
										if(bMatch){
											PageFilter=DBPageFilter;
											logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											//break;
										}
									}
								}
								if(PageFilter.equals(ParentFilter)){
									PageFilter="//leaf";
								}
							}
						}
					}
					else{
						if(tagName.equals(DBtagName)){
							if(ParentFilter.contains(DBParentName)){
								if(DBParentSeq.length()>2){
									if(DBParentSeq.contains(nodeParentSeq)){ 
										bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
										if(bMatch){
											PageFilter=DBPageFilter;										
											logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
											RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
											//System.out.println("[XPATH:] 8 seq="+ParentSeq+",PageFilter = "+PageFilter);
											//break;
										}
									}
								}else{
									bMatch=DOMFilter.matchPageFilterByAttribute(DBAttribute,DBAttributeCondition,DBAttributeValue,tagName,nodeId,nodeHref,nodeName,nodeValue,nodeClass,nodeType,onkeydown,onclick,nodeTitle,nodeText);
									if(bMatch){
										PageFilter=DBPageFilter;
										logInfo="[XPATH:] seq "+ParentSeq+" XPATHName=<a target=_blank href=../develop/xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+DBXpathID+"><font color=blue>"+DBXPATHName+"</font></a>";
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										logInfo="[XPATH:] seq "+ParentSeq+",NodePath=<a target=_blank href=dom.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath+"><font color=blue>"+NodePath+"</font></a>"; 
										RunDB.addLog(ProjectID,DBPosition,ParentNodePath,3,logInfo);
										//break;
									}
								}
							}
							if(PageFilter.equals(ParentFilter)){
								PageFilter="//leaf";
							}
						}
					}
				}				
			}//while
			if(PageFilter.equals("")){
				PageFilter=ProductFilter;
			}
			rs.close();
			rs=null;
		}catch(Exception er){}		
		mdb.close();    	
		return PageFilter;
	}
	public static String setUnique(boolean bURLtoTag,String ProjectID,int LevelID,String ProductName,String ParentFilter,String ParentNodePath,String NodePath,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText,String nodeSrc)
	{
		ParentFilter=ParentFilter.replace('&','\'');
		String uniqueObject=ProjectID+"_"+NodePath; //NodePath=ParentNodePath+"_"+seq;
		String nodeTextPLOC = nodeText;
		int iPosition1 = nodeText.indexOf("]]"); int iPosition2 = nodeText.indexOf("[["); 
		if(iPosition1!=-1 && iPosition2!=-1){
			nodeTextPLOC=nodeText.substring(iPosition1-6,iPosition1+2);
		}		
		String nodeTitlePLOC = nodeTitle;
		iPosition1 = nodeTitle.indexOf("]]"); iPosition2 = nodeTitle.indexOf("[["); 
		if(iPosition1!=-1 && iPosition2!=-1){
			nodeTitlePLOC=nodeTitle.substring(iPosition1-6,iPosition1+2);
		}
		
    	switch(ProductName){
	    	case "Any": //Any
				if(tagName.equals("a")){ //By default, all different URLs are accepted.
					if(nodeHref.contains("http")){
						uniqueObject=ProjectID+"_unique_"+nodeHref; // to be unique URL
					}
				}
			break;
	    	case "ITPAM": 
	    		//menu
	    		if(tagName.equals("span")){ 
	    			//mainly for configuration, or others
	    			if(!nodeText.equals("")){
	    				uniqueObject=ProjectID+"_unique_"+"configuration_or_others"+"_"+ nodeTextPLOC;  //only mysql3 or other UTF8 DB
	    			}
                    if(nodeText.contains("Reports")){ 
                            uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Reports"; 
                    } 
                    if(nodeText.contains("Configuration Browser")){ 
                    	uniqueObject=ProjectID+"_common_"; //non unique
	    			}else{
	                    if(nodeText.contains("Configuration ")){ 
	                           uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Configuration "; 
	                    } 
	    			}
                    if(nodeText.contains("Operations")){ 
                        uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Operations"; 
	    			}
                    /** by Song
                    if(nodeText.contains("Designer")){ 
                        uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Designer"; 
                    }
                    if(nodeText.contains("Library")){ 
                        uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Library"; 
                    }
                    ***/
                    if(LevelID==1){
                    	if(nodeText.trim().equals("")){
                    		uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Home"; 
                    	}
                    }
                    if(nodeText.contains("Home")){ 
                        uniqueObject=ProjectID+"_unique_"+"menu"+"_"+"Home"; 
                    } 
	            }	    		
	    		//menu
	    		
	    		//First line
	    		//nodeId='PAM_HOME_PAGE_userSettingsLink' nodeText='PAMADMIN Lastname' nodeText='? Help?[[00B4]]'
	    		if(tagName.equals("a")){ 
                    if(nodeId.contains("PAM_HOME_PAGE")){ 
                            uniqueObject=ProjectID+"_unique_"+nodeId; 
                    } 
	            } 
	    		
	    		if(ParentFilter.equals("//input[@type='image']")){
	    			if(tagName.equals("input")){ 
	    				uniqueObject=ProjectID+"_unique_"+nodeName; 
	    			}
                }
	    		if(ParentFilter.contains("operations_button")){
	    			uniqueObject=ProjectID+"_unique_"+nodeTextPLOC; 
            	}
			break;	
	    	case "ServiceCatalog": 
	    		//batch, not set filter --- Try 
				//img|//span|//td[@onclick]"
	    		/*
				if(tagName.equals("span")){
					if(!nodeText.equals("")){
						if(!nodeText.contains("Definition")){
							uniqueObject=ProjectID+"_unique_"+"span"+"_"+nodeText;
						}
					}
				}
				if(tagName.equals("td")){
					if(!nodeText.equals("")){
						uniqueObject=ProjectID+"_unique_"+"td"+"_"+nodeText;
					}
				}
				if(tagName.equals("img")){
					nodeSrc=nodeSrc.substring(nodeSrc.lastIndexOf('/'));
					if(!nodeSrc.equals("")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+nodeSrc;
					}
				}
				*/
				////////////////TEST above
				
	    		if(tagName.equals("a")){ //By default, all different URLs are accepted.
					if(nodeHref.contains("wpf?Node=")){ //may same with mainMenuFilter
						uniqueObject=ProjectID+"_unique_"+nodeHref; // to be unique URL
					}
					if(nodeTitle.contains("Click to Edit")){
						uniqueObject=ProjectID+"_unique_"+ nodeTitlePLOC;
					}
					if(nodeHref.contains("usm/wpf?Node=icguinode.ruletypeedit")){
						uniqueObject=ProjectID+"_unique_"+ "wpf?Node=icguinode.ruletypeedit&amp;Args=";
					}
					
					if(nodeTitle.contains("Click to Edit Fiscal Period")){
						uniqueObject=ProjectID+"_unique_"+ nodeTitlePLOC;
					}
					
					if(nodeHref.contains("javascript:showConfigGroup")){
                        uniqueObject=ProjectID+"_unique_"+ nodeHref;
                    }

					
					if(nodeTitle.contains("Click to view rules")){
						uniqueObject=ProjectID+"_unique_"+ nodeTitlePLOC;
					} 
					
					if(nodeClass.equals("about")){
						uniqueObject=ProjectID+"_unique_"+ nodeClass;
					} 
					if(nodeHref.contains("logout")){
						uniqueObject=ProjectID+"_unique_"+ nodeHref;
					}
					
					if(nodeId.equals("a_BTO")){
						uniqueObject=ProjectID+"_unique_"+ "a" + nodeId;
					}
					if(nodeId.equals("chsearch_link")){
						uniqueObject=ProjectID+"_unique_"+ "a" + nodeId;
					}
					if(nodeText.contains("BTO")){
						uniqueObject=ProjectID+"_unique_"+ "a" + "BTO";
					}
					if(nodeHref.equals("javascript:savequery()") | nodeHref.equals("javascript:loadquery()") ){
			    		uniqueObject=ProjectID+"_unique_"+ "a" + nodeHref;
			    	}
					if(nodeHref.contains("javascript:detail(")){
			    		uniqueObject=ProjectID+"_unique_"+ "a" + "javascript:detail(";
			    	}	
					if(nodeHref.equals("javascript:chsearch()")){
			    		uniqueObject=ProjectID+"_unique_"+ "a" + "javascript:chsearch()";
		    		}
					if(nodeText.contains("BTO:spadmin")){
			    		uniqueObject=ProjectID+"_unique_"+ "a" + "BTO:spadmin";
			    	}
					if(nodeHref.contains("javascript:detail(")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+ "images/details.gif";
					}
				}
	    		if(tagName.equals("img") && onclick.equals("showdetails(this)")){
		    		uniqueObject=ProjectID+"_unique_"+ "a" + onclick;
		    	}
		    	if(tagName.equals("input") && nodeId.equals("changeBuBtn")){
		    		uniqueObject=ProjectID+"_unique_"+ "a" + nodeId;
		    	}
				//http://liahu01enu2008s:8080/usm/wpf?Node=iclaunchpad.portal_management
				if(tagName.equals("img")&&nodeSrc.equals("images/wplus.gif")){
					uniqueObject=ProjectID+"_unique_"+"img"+"_"+nodeSrc; 
				}
				
				
				//service offering tab
				if(tagName.equals("img")&& nodeClass.contains("x-tree3-node-joint") && ParentFilter.contains("(//img[contains(@src,'/usm/gwt/sbIde/clear.cache.gif')])[8]")){
					uniqueObject=ProjectID+"_unique_"+"img"+"_"+ ParentFilter;
				}
				//policy tab
				if(tagName.equals("img")&& nodeClass.contains("x-tree3-node-joint") && ParentFilter.contains("//div[contains(@id,'ca_usm_gxt_common_asynch_tree__ca_usm_gxt_common_asynch_tree_x-auto-')]/img[2]")){
					uniqueObject=ProjectID+"_unique_"+"img"+"_"+ ParentFilter;
				}
					
				
				
				if(tagName.equals("span")&&nodeClass.equals("foldernamesel")){
					uniqueObject=ProjectID+"_unique_"+"span"+"_"+nodeClass; 
				}
				
				if(tagName.equals("span") && onclick.equals("selectobject(this)") && nodeId.contains("span_") && nodeClass.contains("foldername")){
					uniqueObject=ProjectID+"_unique_"+"span"+"_"+nodeId;
				}
				
				
				if(nodeName.equals("go")||nodeName.equals("savecontentspan")||nodeName.equals("previewspan")){
					uniqueObject=ProjectID+"_unique_"+"input"+"_"+nodeName; 
				}
				
				if(tagName.equals("input")){
					if(nodeName.contains("depBtn")){
						uniqueObject=ProjectID+"_unique_"+"input"+"_"+ nodeName;
					}
					if(nodeName.contains("buBtn")){
						uniqueObject=ProjectID+"_unique_"+"input"+"_"+ nodeName;
					}
					if(nodeTitle.contains("Start Aggregation")){
						uniqueObject=ProjectID+"_unique_"+"input"+"_"+ nodeTitlePLOC;
					}
					
					if(nodeTitle.contains("Schedule Aggregation")){
						uniqueObject=ProjectID+"_unique_"+"input"+"_"+ nodeTitlePLOC;
					}
					
					if(nodeTitle.contains("View Scheduled Tasks")){
						uniqueObject=ProjectID+"_unique_"+"input"+"_"+ nodeTitlePLOC;
					}
					if(nodeId.equals("searchButton") && nodeTitle.contains("Change Business Unit")){
						uniqueObject=ProjectID+"_unique_"+"input"+"_"+ "Change Business Unit";
					}
				}
				
				if(tagName.equals("span")){
					if(nodeText.contains("Offerings")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+"Offerings"; 
					}
					if(nodeText.contains("Option Groups")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+ "Option Groups"; 
					}
					if(nodeText.contains("Search")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+ "Search"; 
					}
					if(nodeText.contains("Permissions")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+ "Permissions"; 
					}
					if(nodeText.contains("Featured Offerings")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+ "Featured Offerings"; 
					}
					if(nodeText.contains("Definition")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+ "Definition"; 
					}
					if(nodeText.contains("Related Offerings")){
						uniqueObject=ProjectID+"_unique_"+"span"+"_"+ "Related Offerings"; 
					}
				}
				
				if(tagName.equals("td") && nodeTitle.contains("Show Dashboard Administration")){
					uniqueObject=ProjectID+"_unique_"+"td"+"_"+ nodeTitlePLOC;
				}
				
				if(tagName.equals("td") && nodeTitle.contains("Define Aggregation Logic")){
					uniqueObject=ProjectID+"_unique_"+"td"+"_"+ nodeTitlePLOC;
				}		
				
				
				if(tagName.equals("img")){
					if(nodeTitle.contains("Duplicate and define Aggregation Logic")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+ nodeTitlePLOC;
					}
					if(nodeTitle.contains("Define Aggregation Logic")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+ nodeTitlePLOC;
					}
					if(nodeTitle.contains("Click to View")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+ nodeTitlePLOC;
					}
					if(nodeTitle.contains("Click to Edit")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+ nodeTitlePLOC;
					}
					if(nodeTitle.contains("Delete Profile")){
						uniqueObject=ProjectID+"_unique_"+"img"+"_"+ nodeTitlePLOC;
					}
				}
				
				//miao request
				if(ParentFilter.contains("b_home_request_open")){					
					if(nodeText.contains("Submitted") || nodeText.contains("Hold")){
						uniqueObject=ProjectID+"_unique_"+"b_home_request_open_status"+"_"+ nodeTextPLOC;
					}
				}
				//miao catalog service offering ->related offering
				if(ParentFilter.contains("service_designer_right")){
					if(tagName.equals("a")){
						uniqueObject=ProjectID+"_unique_"+"service_designer_right_tree"+"_"+ nodeText;
					}
    			}
				
			break;
			
	    	case "SiteMinder":	//SiteMinder
	        	if(tagName.equals("a")&&nodeHref.contains("View")){
	        		uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_View";
	        	}
	        	if(tagName.equals("a")&&nodeHref.contains("Modify")){
	        		uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_Modify";
	        	}
	        	if(tagName.equals("a")&&nodeHref.contains("Delete")){
	        		uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_Delete";
	        	}
	        	if(tagName.equals("input")&&nodeName.contains("Edit")){
	        		uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_input_Edit";
	        	}
	        	if(tagName.equals("option")){
	        		uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_"+nodeValue;
	        	}
			break;
			
			case "Clarity": //Clarity
				if(tagName.equals("a")){ //By default, all different URLs are accepted.
					if(nodeHref.contains("http://")){
						uniqueObject=ProjectID+"_unique_"+nodeHref; // to be unique URL
					}
				}
				
				//mingzhe
				if(tagName.equals("a")){ 
					if(nodeHref.contains("javascript:navigateToURLTarget")){
						uniqueObject=ProjectID+"_unique_"+nodeHref; 
					}
				}	
				if(tagName.equals("a")){ 
					if(nodeHref.contains("javascript:clarity.uitk.exportToFile")){
						uniqueObject=ProjectID+"_unique_"+nodeHref; 
					}
				}
				
				
//				if(tagName.equals("a")){ //Even though URLs are different, Robot still only accept valid URLs.
//					if(nodeHref.contains("odf_view")){
//						//<a xmlns:urlencoder="java:java.net.URLEncoder" class="none" tabindex="-1" id="revmgr.departmentProperties" href="#action:revmgr.departmentProperties&amp;odf_pk=5000003&amp;odf_view=departmentProperties" target="" title="Shared Services">Shared Services</a>
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_odf_view";
//					}
//					//<a target=_blank href="#action:revmgr.departmentProperties&amp;object_code=department&amp;id=5000004&amp;odf_pk=5000004&amp;odf_view=departmentProperties" class="" title="Properties">Properties</a>
//					//<a target=_blank href="#action:revmgr.departmentInvestments&amp;object_code=department&amp;id=5000004&amp;odf_pk=5000004&amp;obs_unit_id=5000045" class="" title="Investments">Investments</a>
//					//<a target=_blank href="#action:revmgr.departmentResources&amp;object_code=department&amp;id=5000004&amp;odf_pk=5000004&amp;obs_unit_id=5000045" class="" title="Resources">Resources</a>
//					//<a target=_blank href="#action:revmgr.departmentSubscriptions&amp;object_code=department&amp;id=5000004&amp;odf_pk=5000004" class="" title="Subscriptions">Subscriptions</a>
//					if(nodeHref.contains("action:revmgr.departmentResources")){
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_action:revmgr.departmentResources";
//					}
//					if(nodeHref.contains("action:revmgr.departmentSubscriptions")){
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_action:revmgr.departmentSubscriptions";
//					}
//					if(nodeHref.contains("action:revmgr.departmentInvestments")){
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_action:revmgr.departmentInvestments";
//					}
//					if(nodeHref.contains("action:revmgr.departmentProperties")){
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_action:revmgr.departmentProperties";
//					}
//					
//					if(nodeHref.contains("editResource")){
//						//<a xmlns:urlencoder="java:java.net.URLEncoder" class="none" tabindex="-1" id="projmgr.editResource" href="#action:projmgr.editResource&amp;id=5002016&amp;odf_return_to=revmgr.departmentResources%26id%3D5000004%26obs_unit_id%3D5000045%26object_code%3Ddepartment%26odf_pk%3D5000004" target="" title="Evans, Nick">Evans, Nick</a>
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_editResource";
//					}
//					if(nodeHref.contains("action:pma.investmentProperties")){
//						//<a xmlns:urlencoder="java:java.net.URLEncoder" class="none" tabindex="-1" id="pma.investmentProperties" href="#action:pma.investmentProperties&amp;id=5001092&amp;odf_return_to&amp;investment_code=project" target="" title="CRM Contact Center Development">CRM Contact Center Development</a>
//						uniqueObject=ProjectID+"_unique_"+ParentNodePath+"_a_action:pma.investmentProperties";
//					}
//	        	}
//				System.out.println(uniqueObject);
			break;			
		
    	}
    	
    	//for all product
    	if(!bURLtoTag){
			if(tagName.equals("a")){ //By default, all different URLs are accepted.
				if(nodeHref.contains("http")){
					uniqueObject=ProjectID+"_unique_"+nodeHref; // to be unique URL
				}
			}
		}
		return uniqueObject;
	}
	
	public static int setLeafForNode(String ProjectID,String DBPosition,String ProductName,int LevelID, String tagName,String ParentFilter,String MyPageFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText, String nodeNgclick)
	{
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
	
	public static int setBranch(String ProductName,String tagName,String ParentFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		ParentFilter=ParentFilter.replace('&','\'');
		//condition 1:  different page, same vector with parent page
		int branch = 0;
		if(nodeTitle==null){nodeTitle="";} 
    	switch(ProductName){
			case "SiteMinder":	//SiteMinder
				
			break;
		}	
		return branch;
	}
	public static int setChildNum(String ProductName,String tagName,String ParentFilter,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		ParentFilter=ParentFilter.replace('&','\'');
		int iChildNum = 0;
    	switch(ProductName){
			case "Any":	
				
			break;
			case "ITPAM":	
				
			break;
		}	
		return iChildNum;
	}

	public static String setPageFilter(String ParentNodePath,String NodePath,int LevelID,String DBPosition,String ProjectID,String ProductName,String ParentFilter,int ParentSeq,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
		{
			ParentFilter=ParentFilter.replace('&','\'');
			String ProductFilter=customlibrary.FilterSetting.setProductFilter(ProductName);
			String PageFilter = ProductFilter;
			String logInfo="";
			// Note: The symbol & cannot be included in PageFilter.   
	    	switch(ProductName){	    
		    	
		    	case "APM-WebView": 
		    		if(ParentFilter.contains("menu")){
		    			if(nodeText.contains("INVESTIGATOR")){
		    				PageFilter="//table/tbody/tr/td";
		    				PageFilter += "/div/div/img[2]"; //ignore /div[n]/div/img[2]
		//    				PageFilter += "|//table/tbody/tr/td";
		//    				PageFilter += "/div/div/span[2]";  //ignore"//div/span[2]"; 
		    				PageFilter += "|//i_layer_one";
		    			}
		    			if(nodeText.contains("MANAGEMENT")){
		    				PageFilter="//table/tbody/tr/td";
		    				PageFilter += "/div/div/img[2]";   //Generate SuperDomain
		    				PageFilter +="|//div[@class='GMDBJMCDNM GMDBJMCDJN']/table/tbody/tr/td/div[@class='GMDBJMCDON']";     				
		    				PageFilter += "|//m_layer_one";
		    			}
		    			if(nodeText.contains("CONSOLE")){
		    				PageFilter="(//div[@class='GMDBJMCDMIC'])[2]|//div[@id='webview-consolelens-dialog-launch-button']";
		    				PageFilter += "|//c_layer_one";
		    			}
		    		}
		    		
		    		//MANAGEMENT
		    		if(ParentFilter.contains("m_layer_one") && tagName.equals("div")){
		    			PageFilter = "//div[@id='webview-MMEditor-elements-menu']/div[1]/div/div/span" ;
						PageFilter += "|//m_div_layer_two";
		    		}
		    		if(ParentFilter.contains("m_div_layer_two") && nodeText.contains("Action") ){
		    			PageFilter = "//div[@id='webview-MMEditor-action-menu']/div/div/div/span";
		    		}
		    		if(ParentFilter.contains("m_div_layer_two") && nodeText.contains("Alert") ){
		    			PageFilter = "//div[@id='webview-MMEditor-alert-menu']/div/div/div/span";
		    		}
		    		
		    		if(ParentFilter.contains("m_layer_one") && tagName.equals("img")){
		    			PageFilter="//table/tbody/tr/td";
		    			PageFilter += "/div/div[2]";  //recursive factor	    			
						PageFilter += "/div/div/img[2]";  //Generate Management Modules
						PageFilter += "|//m_layer_two";
		    		}
		    		
		    		
		    		if(ParentFilter.contains("m_layer_two")){
		    			PageFilter="//table/tbody/tr/td";
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "/div/div/img[2]";  //Generate ADA Extension for APM
						PageFilter += "|//m_layer_three";
		    		}
		    		if(ParentFilter.contains("m_layer_three")){
		    			PageFilter="//table/tbody/tr/td";
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "//div/img[2]";   //Generate multiple image items to click    
		    			//For example, //table/tbody/tr/td/div/div[2]/div/div[2]/div/div[2]/div[8]/div/img[2]
						PageFilter += "|//m_layer_four";
		    		}	
		    		if(ParentFilter.contains("m_layer_four")){
						PageFilter = "//table/tbody/tr/td";
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div/span[2]";  //ignore"//div/span[2]"; 
						PageFilter += "|//m_layer_five";
		    		}	
		    		
		    		//INVESTIGATOR
		    		if(ParentFilter.contains("i_layer_one")){
		    			PageFilter="//table/tbody/tr/td";
		    			PageFilter += "/div/div[2]";  //recursive factor	    			
						PageFilter += "/div/div/img[2]";  //ignore /div[n]/div/img[2]
		//				PageFilter += "|//table/tbody/tr/td";
		//				PageFilter += "/div/div[2]";  //recursive factor
		//				PageFilter += "/div/div/span[2]";  //ignore"//div/span[2]"; 
						PageFilter += "|//i_layer_two";
		    		}
		    		if(ParentFilter.contains("i_layer_two")){
		    			PageFilter="//table/tbody/tr/td";
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "/div/div[2]";  //recursive factor
		    			PageFilter += "/div/div/img[2]";  //ignore /div[n]/div/img[2]
		//				PageFilter += "|//table/tbody/tr/td";
		//				PageFilter += "/div/div[2]";  //recursive factor
		//				PageFilter += "/div/div[2]";  //recursive factor
		//				PageFilter += "/div/div/span[2]";  //ignore"//div/span[2]"; 
						PageFilter += "|//i_layer_three";
		    		}
		    		if(ParentFilter.contains("i_layer_three")){
		//    			PageFilter="//table/tbody/tr/td";
		//    			PageFilter += "/div/div[2]";  //recursive factor
		//    			PageFilter += "/div/div[2]";  //recursive factor
		//    			PageFilter += "/div/div[2]";  //recursive factor
		//    			PageFilter += "/div/div/img[2]";  //ignore /div[n]/div/img[2]
		//				PageFilter += "|//table/tbody/tr/td";
						PageFilter = "//table/tbody/tr/td";
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div[2]";  //recursive factor
						PageFilter += "/div/div/span[2]";  //ignore"//div/span[2]"; 
						PageFilter += "|//em/span/span";  //right tab
						PageFilter += "|//i_layer_four";
		    		}
		    		if(ParentFilter.contains("i_layer_four")){
						PageFilter = "//em/span/span";  //right tab
						PageFilter += "|//i_layer_five";
		    		}
		    		
				break;
		    	case "CloudMonitor":
		    		if(ParentFilter.contains("menu")){
		    			PageFilter="//input[contains(@class,'formbtn')]";
		    			PageFilter+="|//a[contains(@href,'.php')]";
		    			PageFilter+="|//common_view";
		    		}
		    		if(ParentFilter.contains("common_view")){
	//	    			PageFilter="//a[@onclick]";
	//	    			PageFilter+="|//input[contains(@class,'formbtn')]";
	//	    			PageFilter+="|//view_end";
		    			PageFilter="//leaf";
		    		}
		    		if(ParentFilter.contains("view_end")){
		    			PageFilter="//leaf";
		    		}
		    		
		    		//settings.php node 009
					if(nodeHref.contains("settings.php")){					
						//step1 //input[contains(@onclick,'new_monitor_dialog')]
						//step2 //a[@content='advanced_monitor_div']  and //tr[@class='centered more-row']//a
						//step3 //div[@class='monitor_row']/span
						//step4 //a[contains(@onclick,'expert')] or //span[@id='expert']/a
						//step5 //a[@class='fancy ga-event']
						PageFilter="//input[contains(@onclick,'new_monitor_dialog')]|//new_monitor"; //step1
					}
					if(ParentFilter.contains("new_monitor")){ 
						PageFilter="//tr[@class='centered more-row']//a";
						PageFilter+="|//a[@content='advanced_monitor_div']";
						PageFilter+="|//monitor_more";  //step2
						//tr[@class='centered more-row']//a
						//div[@class='monitor_row']/span
						
						//a[@id='advanced_synthetic_monitor_tab']
						//a[@content='advanced_monitor_div']
					}
					
					if(ParentFilter.contains("monitor_more")){
						PageFilter="//div[@class='monitor_row']/span|//show_monitior"; //step3
					}
					if(ParentFilter.contains("show_monitior")){
						PageFilter="//a[contains(@onclick,'expert')]|//monitior_detail"; //step4
					}
					if(ParentFilter.contains("monitior_detail")){
						PageFilter="//a[@class='fancy ga-event']|//leaf"; //step5
					}
					
					//my_subscription.php node 013
		    		if(nodeHref.contains("my_subscription.php")){
						PageFilter="//a|//my_subscription";
					}	    		
		    		if(ParentFilter.contains("my_subscription")){
		    			PageFilter="//leaf";
		    		}	    		
		    		
		    		//learn_more.php node 023
		    		if(nodeHref.contains("learn_more.php")){
		    			PageFilter="//div[@class='learnmorebox']//td";		    			
						PageFilter+="|//learn_more";					
					}		
		    		if(ParentFilter.contains("learn_more")){
		    			PageFilter="//div[@class='learnmorecontent']//a[@class='blue more']";
		    			PageFilter+="|//show_more";
		    		}	   
		    		if(ParentFilter.contains("show_more")){
		    			PageFilter="//leaf";
		    		}
		    		
		    		if(nodeHref.contains("logout")){
		    			//PageFilter="//a[starts-with(@id,'MENU_')]";	
		    			PageFilter="//a";	 
						PageFilter+="|//logout";					
					}	
		    		if(ParentFilter.contains("logout")){
		    			PageFilter="//leaf";
		    		}
		    		
		    		//oppop_management.php 022
		    		if(nodeHref.contains("oppop_management.php")){
		    			PageFilter="//button[@class='x-btn-center']";		
						PageFilter+="|//oppop_ok";					
					}
		    		if(ParentFilter.contains("oppop_ok")){
		    			PageFilter="//button[@class='x-tab-center']";
		    			PageFilter+="|//oppop_tab";		
		    		}
		    		if(ParentFilter.contains("oppop_tab")){
		    			PageFilter="//leaf";
		    		}
		    		
		    		if(nodeHref.contains("plans.php")||nodeHref.contains("website_monitoring_features.php")){
		    			PageFilter="//a";		
		    			PageFilter+="|//url_show";		
		    		}
		    		if(ParentFilter.contains("url_show")){
		    			PageFilter="//leaf";
		    		}
				break;
		    	
		    	case "MCC":
	    			if(ParentFilter.contains("menu")){ 
	    				
	    				if(nodeHref.contains("tenant:dashboard")){  //http://sagan02-i152272.ca.com:7080/admin/#tenant:dashboard
	    					PageFilter="//leaf";
	    				}
	    				if(nodeHref.contains("tenant:your-company")){ //http://sagan02-i152272.ca.com:7080/admin/#tenant:your-company
	    					PageFilter="//a[@ng-click]"; 
	    					PageFilter+="|//div[@ng-click]"; 
	    					
	    					PageFilter+="|//button[@ng-click]"; 
	    					
	    					PageFilter+="|//input[@type='radio']"; 
	    					
	    				}
	    				if(nodeHref.contains("tenant:people")){ //http://sagan02-i152272.ca.com:7080/admin/#tenant:people
	    					PageFilter="//a[@ng-click]";
	    					
	    					PageFilter+="|//button";
	    					
	    				}
	    				if(nodeHref.contains("tenant:settings")){ //http://sagan02-i152272.ca.com:7080/admin/#tenant:settings
	    					PageFilter="//a[@ng-click]";
	    					
	    					PageFilter+="|//button[@ng-click]";
	    					
	    				}
	    				if(nodeHref.contains("tenant:logs")){ //http://sagan02-i152272.ca.com:7080/admin/#tenant:logs
	    					PageFilter="//a[@ng-click]";
	    					
	    				}
	    			}
	    			else{
	    				PageFilter="//a[@ng-click]";
	    				PageFilter+="|//button";
	    				PageFilter+="|//div[@ng-click]"; 
	    				PageFilter+="|//input[@type='radio']"; 
	    			}
	    		break;
	    		
	    		
	    		
	    	}
	    	
	    	return PageFilter;
		}
	
}
