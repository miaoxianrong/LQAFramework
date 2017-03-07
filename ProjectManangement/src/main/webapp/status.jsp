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
  <link rel="stylesheet" type="text/css" href="style/bootstrap.min.css" title="style" />
  <link rel="stylesheet" type="text/css" href="style/style.css" title="style" /> 
 <script>

function changetohide() {
	    document.getElementById("processhelp").style.visibility = "hidden";
	}
function changetodisplay() {
	    document.getElementById("processhelp").style.visibility = "visible";
	}
 </script>
</head>

<body>
  <div id="main">
    <div id="header">
      <div id="logo">
        <div id="logo_text">
          <img src="img/logo.png">
        </div>
         <div class="rightmenu">
        	<a href="mailto:miaxi01@ca.com">Contact us</a>
        	<span>|</span>
       <a onclick="newwindow()">About</a>
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
	<h1>Status and Report Details</h1>
	<p>This page display the Auto Crawl Running status in detail, the report, and the progress bar for the UI-in-Context project. </p>
 
	<h1>Legend</h1>
	 
     <div class="divmouseon" onclick="changetodisplay()" >
    	<img src="img/process.png">
    </div>
    <div id="processhelp" style="visibility: hidden" onclick="changetohide()" title="Hide note">
      <p class="help">
      <b>*Note:</b> <br>
      Auto Crawl normal process will be automatically as: Ready in <b>Queue</b>>Start <b>Running</b>><b>Finish</b> at last.<br>
      The green up arrow <img src="img/up1.png"> means the number is increasing.
      <b>Suspend</b> means configuration problem, so need user go to tab <a href="proinfo.jsp">Launch Auto Crawl</a> to input correct configuration and launch again.
</p>
    </div>
  
    <div>
	 <h1>Auto Crawl Status and Report</h1>
	    <table border="0" cellpadding="1" cellspacing="1">		
	   <tr>
	    <th>Project ID</th>
		<th>Project Name</th>
		<th>Drop No.</th> 
		<th>build No.</th>
		<th>Screen No.</th>
		<th>Start Time</th>
		<th>End Time</th>
		<th>Running Status</th>
<!-- 		<th>Product Server</th>		 -->
	</tr>
	<tr>
		<td>${cpid}</td>
		<td>${pname}</td>
		<td>${dropnum}</td>
		<td>${pbuild}</td>
		<td title="When appear the green up arrow, it means the screen number is increasing.">${screennum} <img src="<%=request.getAttribute("up") %>"></td>
		<td>${RunStartTime}</td>
		<td>${RunEndTime}</td>
		<td><img src="<%=request.getAttribute("runstatus") %>"></td>

	</tr> 
	</table>

 	<h1>Running Progress bar</h1>
 	<div id="myDiv" class="progress">
    			<div class="${barcolor}" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:${percent}%">
      				${res}
   				 </div>
  			</div>
	<BR>
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
