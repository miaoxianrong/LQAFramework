package customlibrary;

import core.loginStart;

public class Parameter {
	public static int setMaxNumber(String ProductName){
		int iMaxNumber=3000;
		switch(ProductName){
			case "MAA":
				iMaxNumber=500;
			break;
			case "SiteMinder":
				iMaxNumber=2000;
			break;
			case "CASM":
				iMaxNumber=2000;
			break;
			case "UIM":
				iMaxNumber=800;
			break;
			case "MCC":
				iMaxNumber=500;
			break;
			case "ITKO":
				iMaxNumber=200;
			break;
			case "Clarity":
				iMaxNumber=3000;
			break;
			case "CloudMonitor":
				iMaxNumber=500;
			break;
			case "ControlMinder":
				iMaxNumber=900;
			break;
			case "APM-CEM":
				iMaxNumber=1000;
			break;
			case "APM-TeamCenter":
				iMaxNumber=1000;
			break;
			case "APM-WebView":
				iMaxNumber=1000;
			break;
		}			
		iMaxNumber=3000;
		return iMaxNumber;
	}
	public static String setHub(String ProductName,String ProductURL){
		String Hub=loginStart.readXML("Hub");//"http://miaxi01hub:4444/wd/hub";
		if(ProductName.equals("CloudMonitor")){
			Hub="http://155.35.98.106:4444/wd/hub"; //sandbox
			System.out.println("[Info:] For CloudMonitor, Hub is changed to "+Hub);
		}
		if(ProductURL.contains("miaxi01jpn.ca.com:8443")){
			if(Hub.contains("88.84")){
				System.out.println("[Info:] For local SiteMinder on miaxi01jpn, Hub is "+Hub);
			}else{
				Hub="http://155.35.98.106:4444/wd/hub"; //sandbox
				System.out.println("[Info:] For local SiteMinder on miaxi01jpn, Hub is "+Hub);
			}
		}
		return Hub;
	}
	public static String setPreferedBrowser(String ProductName,String Hub){
		String PreferedBrowser="Chrome";
		if(ProductName.equals("CloudMonitor")){
			PreferedBrowser="FireFox";
			System.out.println("[Info:] For CloudMonitor, Prefered browser is changed to "+PreferedBrowser);
		}
		if(Hub.contains("155.35.98.106")||Hub.contains("155.35.88.84")){
			PreferedBrowser="FireFox";
		}
		return PreferedBrowser;
	}
	public static int setMaxDepth(String ProductName){
		int iMaxDepth=15;
		switch(ProductName){
			case "MAA":
				iMaxDepth=3;
			break;
			case "SiteMinder":
				iMaxDepth=5;
			break;
			case "CASM":
				iMaxDepth=5;
			break;
			case "UIM":
				iMaxDepth=3;
			break;
			case "MCC":
				iMaxDepth=4;
			break;
			case "ITKO":
				iMaxDepth=4;
			break;
			case "Clarity":
				iMaxDepth=5;
			break;
			case "CloudMonitor":
				iMaxDepth=6;
			break;
			case "ControlMinder":
				iMaxDepth=3;
			break;
			case "APM-CEM":
				iMaxDepth=4;
			break;
			case "APM-TeamCenter":
				iMaxDepth=4;
			break;
			case "APM-WebView":
				iMaxDepth=6;
			break;
		}			
		
		return iMaxDepth;
	}
}
