package com.ca.lqa;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdateServlet() {
        super();
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  
	    String IDname = request.getParameter("project_idname");
	    String[] value = IDname.split(": ");
		String ClarityProjectID= value[0];
		String pname = value[1];
		pname = pname.replaceAll("'", "''");
		
		String projectAlias = "NA";	
		
		String lang = request.getParameter("project_language");
		String[] v = lang.split(": ");
		String langs = v[1];	
		
		String url = request.getParameter("project_url");
		url = url.replaceAll("'", "''");
		System.out.println("url: " + url);
		
		String username = request.getParameter("project_username");
		username = username.replaceAll("'", "''");
		String password = request.getParameter("project_password");
		password = password.replaceAll("'", "''");
		String TenantID = request.getParameter("project_TenantID");
		TenantID = TenantID.replaceAll("'", "''");
		String mail = request.getParameter("project_email");
		mail = mail.replaceAll("'", "''");
		String drop = request.getParameter("project_drop");
		
		String build = request.getParameter("project_build");
		build = build.replaceAll("'", "''");
		
		String pid  = ClarityProjectID+"-"+drop+"-"+build; 
	
		String estimate = "555";
		

		
		                            if (IDname != null) {
                                    if (IDname.indexOf("Client Automation") != -1) 
                                                {projectAlias = "ITCM"; }
                                                else if (IDname.indexOf("Configuration Automation") != -1) 
                                                  {projectAlias = "CCA"; 
                                                  estimate = "100";}
                                                else if (IDname.indexOf("Process Automation") != -1) 
                                                  {projectAlias = "ITPAM"; 
                                                  }
                                                else if (IDname.indexOf("ITKO") != -1) 
                                                  {projectAlias = "ITKO"; 
                                                  estimate = "190";}
                                                else if (IDname.indexOf("Performance Management") != -1) 
                                                  {projectAlias = "CAPM"; }
                                                else if (IDname.indexOf("CEM") != -1) 
                                                  {projectAlias = "APM-CEM"; 
                                                  estimate = "150";}
                                                else if (IDname.indexOf("Team Center") != -1) 
                                                {projectAlias = "APM-TeamCenter"; 
                                                estimate = "50";}
                                                else if (IDname.indexOf("Webview") != -1) 
                                                {projectAlias = "APM-WebView"; 
                                                estimate = "230";}
                                                else if (IDname.indexOf("DCIM") != -1) 
                                                  {projectAlias = "DCIM"; }
                                                else if (IDname.indexOf("Mediation Manager") != -1) 
                                                  {projectAlias = "CAMM"; }
                                                else if (IDname.indexOf("Privileged Identity Manager") != -1) 
                                                  {projectAlias = "ControlMinder"; 
                                                  estimate = "650";}
                                                else if (IDname.indexOf("Nimsoft") != -1) 
                                                  {projectAlias = "Nimsoft"; }
                                                else if (IDname.indexOf("UIM") != -1) 
                                                  {projectAlias = "Nimsoft"; }
                                                else if (IDname.indexOf("ecoGovernance") != -1) 
                                                  {projectAlias = "ecoGovernance"; }
                                                else if (IDname.indexOf("CSM") != -1) 
                                                  {projectAlias = "CSM"; }
                                                else if (IDname.indexOf("Business Service Insight") != -1) 
                                                  {projectAlias = "BSI"; }
                                                else if (IDname.indexOf("Service Catalog") != -1) 
                                                  {projectAlias = "Service Catalog"; }
                                                else if (IDname.indexOf("Service Desk Manager") != -1) 
                                                  {projectAlias = "DSM"; }
                                                else if (IDname.indexOf("Clarity") != -1) 
                                                  {projectAlias = "Clarity"; 
                                                  estimate = "2000";}
                                                else if (IDname.indexOf("PPM") != -1) 
                                                {projectAlias = "Clarity"; 
                                                estimate = "2000";}
                                                else if (IDname.indexOf("MAA") != -1) 
                                                  {projectAlias = "MAA"; 
                                                  estimate = "430";}
                                                else if (IDname.indexOf("MAM") != -1) 
                                                  {projectAlias = "MAM"; }
                                                else if (IDname.indexOf("MCC") != -1) 
                                                  {projectAlias = "MCC"; 
                                                  estimate = "60";}
                                                else if (IDname.indexOf("SiteMinder") != -1) 
                                                {projectAlias = "CASM"; 
                                                estimate="1000";}
                                                else if (IDname.indexOf("Cloud Monitor") != -1) 
                                                {projectAlias = "CloudMonitor"; 
                                                estimate="280";}
                                                else if (IDname.indexOf("Synthetic Monitor") != -1) 
                                                {projectAlias = "CloudMonitor"; 
                                                estimate="280";}                                    
		                            }            
		                            
		    System.out.println("estimate: " + estimate);
		    
			String driverName = "com.mysql.jdbc.Driver";  		
		  
		    Connection conn = null;  
		  
		    Statement stmt = null;
		    
		    ResultSet rst = null;

		    try {  
		  
		    Class.forName(driverName);  
		  
		    conn = DriverManager.getConnection("jdbc:mysql://miaxi01lab:3306/framedb","dbadmin","cabji18n");  

		    stmt = conn.createStatement();			
		    
		    
		    rst = stmt.executeQuery("select ProjectID from projectparameter where ProjectID ='" + pid + "'");
		    
		    if(rst.next()){
		    	int rs = stmt.executeUpdate("UPDATE projectparameter SET buildno='" + build + "', Estimate='" + estimate + "', ProjectName='" + pname + "', ProductAlias='" + projectAlias + "', Language='" + langs +"', URL='" + url +"', UserName='" + username +"', Password='" + password + "', TenantID='" + TenantID +"', EmailContact='" + mail + "', ProjectStatus='1', DropNum = '" + drop +"' WHERE ProjectID='" + pid + "'");	

		    	if (rs >= 0) {
		    	request.setAttribute("result", "Finished!");
				
		    	} else 
		    		request.setAttribute("result", "Failed!");
		    
		    }
		    else{		    	
		    	int rs = stmt.executeUpdate("INSERT INTO projectparameter (buildno, ProjectID, ProductAlias, ProjectName, Language, URL, UserName, Password, TenantID, EmailContact, AdminContact, ProjectStatus, DropNUM, ClarityProjectID, Estimate) VALUES ('" + build + "', '" + pid + "', '" + projectAlias + "','" + pname + "','" + langs +"','" + url +"','" + username +"','" + password + "','" + TenantID +"','" + mail + "','admin@ca.com','1','" + drop +"','" + ClarityProjectID + "','" + estimate + "')");	
   	
		    	if (rs >= 0) {
		    	request.setAttribute("result", "Finished!");
				
		    	} else 
		    		request.setAttribute("result", "Failed!");
		    
		    	}
		    }catch(Exception e){
		      
		    	request.setAttribute("result", "Error! Project info configuration incorrectly. Please re-launch.");
		    	e.printStackTrace();
		    }finally{
	
		     
		       try{
		          if(stmt!=null)
		             stmt.close();
		       }catch(SQLException se2){
		       }
		       try{
		          if(conn!=null)
		             conn.close();
		       }catch(SQLException se){
		          se.printStackTrace();
		       }
		    }  
		  

		    String nextJSP = "/result.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
			
	}

}
