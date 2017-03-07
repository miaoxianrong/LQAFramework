<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>LQA Framework Project</title>
  	<%@ page import="java.io.File"%>
  	<%@ page import="java.sql.*"%>
  	<%@ page import="java.util.ArrayList, java.util.List" isELIgnored="false"%>
  	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=windows-1252" />
  <link rel="stylesheet" type="text/css" href="style/style.css" title="style" />

</head>

<body>
<%
	String driverName = "com.mysql.jdbc.Driver";  		
  
    List<String> ids = new ArrayList<String>();  
  
    Connection conn = null;  
  
    Statement stmt = null;
 
    ResultSet rs = null;
    
    try {  
  
    Class.forName(driverName);  
  
    conn = DriverManager.getConnection("jdbc:mysql://webauto:3306/framedb","dbadmin","cabji18n");  
  
    stmt = conn.createStatement();
    
    rs = stmt.executeQuery("select ProjectID, DropNum, ProjectName, buildno, ClarityProjectID from projectparameter Where ProjectStatus > 0 order by ParaID desc");
    
    while(rs.next()){
        
         String puid = rs.getString("ProjectID");
         String pdrop = rs.getString("DropNum");
         String pname = rs.getString("ProjectName");
         String pbuild = rs.getString("buildno");
         String pclarityid = rs.getString("ClarityProjectID");
         
         ids.add(puid + ": drop " + pdrop + ": build " + pbuild + ": " + pname);
		
      	}
 		request.setAttribute("prolist", ids);
       
    }catch(Exception e){
       e.printStackTrace();
    }finally{
     try{
           if(rs!=null)
            rs.close();
        }catch(SQLException se2){
        }
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
   
 %>
  <div id="main">
    <div id="header">
      <div id="logo">
        <div id="logo_text">
          <h1><a href="proinfo.jsp"><img src="img/logo.png"></a></h1>
        </div>

      <div class="rightmenu">
        	<a href="mailto:miaxi01@ca.com">Contact us</a>
        </div> 
       </div> 
      <div class="bluebar">
      Welcome!
      </div>  
      <p></p>   
      <div id="menubar">
        <ul id="menu">
          <li><a href="proinfo.jsp">Launch Auto Crawl</a></li>
          <li class="selected"><a href="run.jsp">Status and Report</a></li>
        </ul>
      </div>
      <p class="greybar"></p>
    </div>
    <div id="site_content">
      <div id="content">
       <!--  <h1>Auto Crawl Running Status</h1>--> 
        <form action="RunServlet" method="post">
          <div class="form_settings">
    		<h1>Status and Report </h1>
			<p>This page is for you to monitor the auto crawl running status and check the report. 
    		</p>
    		<br>
    		<p><span><b>*</b>Select a project then Search:</span>		
			    <select name="project_drop_name" id="project_drop_name" required>
			    
    			<c:forEach items="${requestScope.prolist}" var="item">
     			<option value="<c:out value="${item}"/>">
      			<c:out value="${item}" />
     			</option>
    			</c:forEach>
   			</select>
   			<input class="view" type="submit" name="pro_submitted" value="Search" />
	</p>
	<p>
	<br>
	<br>
			</p>
          </div>
        </form>
      </div>
      </div>
    </div>
    <div id="footer">
      Localization Engineer Team @CTC
      <br>
       Managed by <a href="mailto:wu$ir01@ca.com">Wu, Iris</a>
      <br>
       Developed by <a href="mailto:miaxi01@ca.com">Miao, Xianrong</a>
      <span>|</span>
       <a href="mailto:liuyu04@ca.com">Liu, Yue</a>
        <span>|</span>
       <a href="mailto:xioza01@ca.com">Xiong, Zaiwei</a>
        <span>|</span>
       <a href="mailto:genzh01@ca.com">Geng, Zhijie</a>
        <span>|</span>
       <a href="mailto:sonmi02@ca.com">Song, Mingzhu</a>
       <span>|</span>
       <a href="mailto:liahu01@ca.com">Liang, Huiping</a>
        <span>|</span>
       <a href="mailto:caimi03@ca.com">Cai, Mingzhe</a>
       <br>
    </div>
  </div>
</body>
</html>
