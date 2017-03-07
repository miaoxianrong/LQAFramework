package misc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class record {
	/*
	FTP:
	@echo off
	rem username
	set ftpUser=Anonymous
	rem password
	set ftpPass=yue.liu@ca.com
	rem server
	set ftpIP=liuyu04-hub
	
	
	set ftpFile=%temp%\TempFTP.txt
	>"%ftpFile%" (
	    echo,%ftpUser%
	    echo,%ftpPass%
	    echo get latest.jar
	    echo bye
	)
	start ftp -v -i -s:"%ftpFile%" %ftpIP%
	java -jar latest.jar

	UTF-8

				  * Output:                         
					OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream("./screen/text-to-screen.txt", true), "utf-8");
					Input:
					BufferedReader br = new BufferedReader(new InputStreamReader(
					    new FileInputStream("text.txt"), "UTF-8"));
				  * 

				 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mappingFile), "UTF-8"));
				 
	 * case "ServiceCatalog":
	ProductInfo.java
	|//a[@title='Click to Edit'][1]|
	ProductFilter+="|//a[@id='list_view']";
	DOMFilter.java
	getPageFilter()
	
	
	//a[@id='anchor_1']")){
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
	    		
	
	*/
	
}
