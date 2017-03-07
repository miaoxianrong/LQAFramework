package core;

public class MenuFilter {
	public static boolean mainMenuIncluder(String ProductName,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText)
	{
		boolean bValid = false;
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
	    	case "SAA": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "CloudMonitor": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "MAA": 
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
			case "Aspera":	
				bValid = true;
				bProdMatch=true;
			break;
	    	case "APM": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "APM-CEM": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "ITPAM": 
				bValid = true;	
				bProdMatch=true;
			break;
	    	case "ServiceCatalog": 
				bValid = true;	
				bProdMatch=true;
			break;
			case "SiteMinder":	
				bValid = true;
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
		return bValid;
	}
	public static boolean mainMenuFilter(String ProductName,String tagName,String nodeClass,String nodeHref){
		boolean bMainMenu=false;
		boolean bProdMatch=false; 
		switch(ProductName){			
			case "MCC":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "SAAS":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "APM":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "APM-CEM":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "MAA":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "CloudMonitor":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "ControlMinder":	
				bMainMenu=true;
				bProdMatch=true;
			break;
			case "Nimsoft":	
				bMainMenu=true;
			break;
			case "Clarity":	
				if(tagName.equals("a")){
					if(nodeHref!=null){
						if(nodeHref.contains("#action:")){
							bMainMenu=true;
						}
					}
				}
				bProdMatch=true;
			break;
			case "Aspera":	
//				if(tagName.equals("a")){
//					if(nodeHref!=null){
//						if(nodeHref.contains("/ca-manual/")){
//							bMainMenu=true;
//						}
//					}
//				}
				if(tagName.equals("img")){
					bMainMenu=true;
				}
				bProdMatch=true;
			break;
			case "SiteMinder":	
				if(tagName.equals("a")){
					if(nodeHref!=null){
						if(nodeHref.contains("?task.tag=")){
							bMainMenu=true;
						}
					}
				}
				bProdMatch=true;
			break;
			case "ServiceCatalog": 
				if(tagName.equals("a")){
					if(nodeHref.contains("wpf?Node=iclaunchpad")){
						bMainMenu=true;
					}
				}
				bProdMatch=true;
			break;
		}
		if(!bProdMatch){
			bMainMenu=true;
		}
		return bMainMenu;
	}
	public static boolean mainFeatureFilter(String ProductName,String tagName,String nodeHref,String nodeClass,String nodeTarget){
		boolean bMainFeature=false;
		boolean bProdMatch=false; 
		switch(ProductName){
			case "SiteMinder":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "MCC":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "SAAS":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "MAA":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "ControlMinder":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "CloudMonitor":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "APM":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "APM-CEM":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "Nimsoft":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "Aspera":	
				bMainFeature=true;
				bProdMatch=true;
			break;
			case "Clarity":	
				if(tagName.equals("a")){
					if(nodeHref!=null){
						if(nodeHref.contains("#action:")){
							bMainFeature=true;
						}
					}
				}
				bProdMatch=true;
			break;
			case "ServiceCatalog": 
				if(tagName.equals("a")){
					if(nodeClass.equals("tabs")){
						if(nodeTarget.equals("_top")){
							bMainFeature=true;
						}
					}
				}
				bProdMatch=true;
			break;
		}
		if(!bProdMatch){
			bMainFeature=true;
		}
		return bMainFeature;
	}
	public static boolean mainMenuExcluder(String ProductName,String tagName,String nodeId,String nodeHref,String nodeName,String nodeValue,String nodeClass,String nodeType,String onkeydown,String onclick,String nodeTitle,String nodeText,String nodeSrc,String nodeTarget,String nodeNgclick)
	{
		boolean bValid = true;
    	switch(ProductName){
	    	case "ITKO": 
	    		if(nodeHref.contains("logout")){
					bValid = false;					
				}
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
	    	case "ServiceCatalog": 
				if(tagName.equals("a")){
					if(nodeHref.contains("help")||nodeHref.contains("Node=iclaunchpad.pad")){ //||nodeHref.contains("logout")
						bValid = false;
					}
					if(nodeClass.contains("subtab_")){
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
			break;
			
			case "Clarity": //Clarity
//				if(tagName.equals("a")){
//					if(nodeClass.equals("null")){
//						bValid = false;
//					}
//				}
			break;
			
			
    	}
		
		return bValid;
	}
}
