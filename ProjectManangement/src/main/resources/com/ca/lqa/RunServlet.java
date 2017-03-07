package com.ca.lqa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;


@WebServlet("/RunServlet")
public class RunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RunServlet() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String IDdropname = request.getParameter("project_drop_name");
	    String[] value = IDdropname.split(": ");
		String pid = value[0];
		String pdrop = value[1];
		String pbuild = value[2];
		String pname = value[3];

		
		String[] value1 = pdrop.split(" ");
		String dropnum = value1[1];
		
		String[] value2 = pid.split("-");
		String cpid = value2[0];
		
		String driverName = "com.mysql.jdbc.Driver";  		
		  
	    Connection conn = null;  
	  
	    Statement stmt = null;
	    Statement stmt1 = null;
	    
	    ResultSet rs = null;

	    try {  
	  
	    Class.forName(driverName);  
	  
	    conn = DriverManager.getConnection("jdbc:mysql://miaxi01lab:3306/framedb","dbadmin","cabji18n");  

	    stmt = conn.createStatement();	
	    stmt1 = conn.createStatement();
	   
	    rs = stmt.executeQuery("select ProjectStatus from projectparameter where ProjectID ='" + pid + "'");
	    
	    
	    if(rs.next()){
	    	
	    	String pstatus = rs.getString("ProjectStatus");
	    	request.setAttribute("pstatus", pstatus);
		    System.out.println("running status: " + pstatus);
		    if (pstatus.equals("1")) {
		    	request.setAttribute("runstatus", "img/queue.png");
		    	request.setAttribute("up", "img/notup.png");
		    	
		    	request.setAttribute("barcolor", "progress-bar progress-bar-warning progress-bar-striped active");
		    	} 
		    else if (pstatus.equals("2")) {
		    	request.setAttribute("runstatus", "img/running.png");
		    	request.setAttribute("up", "img/up.png");
				
		    	request.setAttribute("barcolor", "progress-bar progress-bar-success progress-bar-striped active");
		    	} 
		    
		    else if (pstatus.equals("4")) {
		    		request.setAttribute("runstatus", "img/finsihed.png");
		            request.setAttribute("up", "img/notup.png");
		            
		            request.setAttribute("barcolor", "progress-bar progress-bar-info progress-bar-striped");
		            request.setAttribute("res", "100% Complete");
		    }

		    else {
		    	request.setAttribute("runstatus", "img/suspended.png");
		    	request.setAttribute("up", "img/notup.png");
		    	 
		    	request.setAttribute("barcolor", "progress-bar progress-bar-danger progress-bar-striped active");
		    	} 
		 
	    } else{
	    	request.setAttribute("runstatus", "img/notrun.png");
	    	request.setAttribute("up", "img/notup.png");
	    	}
	    
	    rs = stmt.executeQuery("select count(*) from nodelist where ProjectID ='" + pid + "' AND screenname");	    
	    
	    while(rs.next()){
	    	
	    	int screentotal = rs.getInt(1);
	    	stmt1.executeUpdate("UPDATE projectparameter SET ScreenNum ='" + screentotal + "' WHERE ProjectID='" + pid + "'"); //demo disable
	    	
	    }	    
	  
	    rs = stmt.executeQuery("select RunStartTime from ProjectStartTime where ProjectID ='" + pid + "'");
		   
	    while(rs.next()){
	    	String RunStartTime = rs.getString("RunStartTime");
	    	request.setAttribute("RunStartTime", RunStartTime);   	

	    }
	    
	    rs = stmt.executeQuery("select RunEndTime from ProjectEndTime where ProjectID ='" + pid + "'");
		   
	    while(rs.next()){

	    	String RunEndTime = rs.getString("RunEndTime");
	    	request.setAttribute("RunEndTime", RunEndTime);

	    }
	    
      rs = stmt.executeQuery("select Estimate, ScreenNum, ProjectStatus, url from projectparameter where ProjectID ='" + pid + "'");
	   
	    while(rs.next()){
	    	
	    	String url = rs.getString("url");
	    	request.setAttribute("url", url);
	    	
	    	int screen = rs.getInt("ScreenNum");
	    	request.setAttribute("screennum", screen);
	    	
	    	int pstatus1 = rs.getInt("ProjectStatus");
	    	int est = rs.getInt("Estimate");
	    	request.setAttribute("est", est);
	    	
	    	if (screen >= est) {
		    	screen = est - 10;				    	
		    	System.out.println("screens for percent: " + screen);
		    	} 

	        DecimalFormat df = new DecimalFormat("0.00");
	    	float percentlong = (screen * 100.0f)/est;
	    	String percent = df.format(percentlong);
	    	
	    	if (pstatus1 == 1)
	    	{
	    		percent = "0";
	    		request.setAttribute("percent", percent);
	    	}
	    	else if(pstatus1 == 4)
	    	{
	    		percent = "100";
	    		request.setAttribute("percent", percent);
	    	}
	    	else {	
	    		request.setAttribute("percent", percent);     	    		    	
	    	}
    	
	    }
	     
	    }catch(Exception e){
	     
	       e.printStackTrace();
	    }
	    finally{
	       
	       try{
	          if(stmt!=null)
	             stmt.close();
	          if(stmt1 !=null)
	        	 stmt1.close();
	       }catch(SQLException se2){
	       }
	       try{
	          if(conn!=null)
	             conn.close();
	       }catch(SQLException se){
	          se.printStackTrace();
	       }
	    }  
	    
	    request.setAttribute("cpid", cpid);
	    request.setAttribute("pname", pname);
	    request.setAttribute("dropnum", dropnum);
	    request.setAttribute("pid", pid);
	    request.setAttribute("IDdropname", IDdropname);
	    request.setAttribute("pbuild", pbuild);
	    
	    
	    String nextJSP = "/status.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);

	}
}

