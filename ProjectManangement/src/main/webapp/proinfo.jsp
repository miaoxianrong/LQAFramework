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
   <script src="./js/jquery.js"></script>
</head>

<body>
<%
String driverName = "com.mysql.jdbc.Driver";  	

List<String> ids = new ArrayList<String>();  


Connection conn = null;  

Statement stmt = null;

ResultSet rst = null;

try {  

Class.forName(driverName);  

conn = DriverManager.getConnection("jdbc:mysql://webauto:3306/framedb","dbadmin","cabji18n");  

stmt = conn.createStatement();			

rst = stmt.executeQuery("select project_id, project_name from v_projectactive");

    
    while(rst.next()){
         String id = rst.getString("project_id");
         String name = rst.getString("project_name");
           
         ids.add(id + ": " + name); 
      }


 	request.setAttribute("prolist", ids);
     
    }catch(Exception e){
       
       e.printStackTrace();
    }finally{
      
     try{
           if(rst!=null)
            rst.close();
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
<!--           class="logo_colour", allows you to change the colour of the text -->
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
          <!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
          <li class="selected">
          <a href="proinfo.jsp">Launch Auto Crawl</a>
          </li>
          <li>
          <a href="run.jsp">Status and Report</a>
          </li>

        </ul>
      </div>
      <p class="greybar"></p>
    </div>

    <div id="site_content">

        <form action="UpdateServlet" method="post">
          <div class="form_settings">
			<h1>Welcome to Launch Auto Crawl for UI-in-Context </h1>
			<p>Please input following information correctly and click "Launch" button. 
			Then Auto Crawl will automatically capture all UI-in-Context screens. 
			After finished, they will be uploaded to server automatically.  </p>
            <h1>Project Information Configuration</h1>
			<p><span><b>*</b>Project ID and Name</span>
			    <select name="project_idname" id="project_idname" required>
			    
    			<c:forEach items="${requestScope.prolist}" var="item">
     			<option value="<c:out value="${item}"/>">
      			<c:out value="${item}" />
     			</option>
    			</c:forEach>
   			</select>
			</p><br>
			<p><span><b>*</b>Pseudo build Language</span>
			<select class="contact" name="project_language" required>
				
				<option>Czech: cz</option>
				<option>Chinese(Simplified): zh-cn</option>
				<option>Chinese(Traditional): zh-tw</option>
				<option>Danish: da</option>
				<option>Dutch: nl</option>
				<option>English: en-us</option>
				<option>Finish: fi</option>
				<option>French: fr</option>
				<option>German: de</option>
				<option>Hungarian: hu</option>
				<option>Italian: it</option>
				<option>Japanese: ja</option>
				<option>Korean: ko</option>				
				<option>Norwegian: no</option>		
				<option>Russian: ru</option>		
				<option>Spanish: es</option>
				<option>Swedish: sv</option>
				<option>Turkish: tr</option>
				<option>Polish: pl</option>
				<option>Portuguese/Brazil: pt-br</option>
			</select>
			</p><br>
			<p><span><b>*</b>Drop No.</span>
			<select class="contact" name="project_drop" required>
				
				<option>0</option>
				<option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
				<option>6</option>
				<option>7</option>
				<option>8</option>
				<option>9</option>
				<option>10</option>
				<option>11</option>
				<option>12</option>
				<option>13</option>
				<option>14</option>
				<option>15</option>
				<option>16</option>
				<option>17</option>
				<option>18</option>
				<option>19</option>
				<option>20</option>
			</select>
			</p>
			<br>
			<p><span><b>*</b>Build Number</span><input class="contact" type="text" name="project_build" title="Input build number" value="" required="required"/></p>

			<h1>Product Access Info</h1>
			<p>Please make sure your product server working well and can login successfully. Then please input following items: </p>
			<p><span><b>*</b>Product URL</span><input class="contact" type="text" name="project_url" placeholder="http://" title="Input product URL here, for example: http://www.ca.com" value="" required="required"/></p>
			<p><span><b>*</b>User Name</span><input class="contact" type="text" name="project_username" title="Input the user name to login the product" value="" required="required"/></p>
			<p><span><b>*</b>Password</span><input class="contact" type="password" name="project_password" title="Input the password to login the product" value=""/></p>	
			<p><span>TenantID (Optional)</span><input class="contact" type="text" name="project_TenantID" title="Some products need this field to login, such as MXM products. If your products don't have it, just leave it blank." value="" /></p>
			<h1>Developer Contact Info</h1>
			<p>Please input correct mail address for getting important notice mail later.</p>
			<p><span><b>*</b>Email</span><input class="contact" type="text" name="project_email" title="Input your mail here to get important notice mail later as pmfkey@ca.com, for example: admin@ca.com. You could also input several emails separated by semicolon ';', for example: admin01@ca.com; admin02@ca.com; admin03@ca.com" placeholder="pmfkey@ca.com" value="" required="required"/></p>		
			<p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="pro_submitted" value="Launch" /></p>
       	
          </div>
        </form>
        <!-- Testing -->
<!--        <button id="test">test</button> -->
         <script src="./js/launch.js"></script>
         <script>
         function test(){
         	waitAndstart();
         }
     	 $('#test').on('click', function () {
	
				window.setInterval(waitAndstart, 1000);
		});
    	</script>
    	<!-- Testing End -->
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
