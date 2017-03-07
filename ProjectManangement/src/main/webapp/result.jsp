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
		<h1>Project configuration ${requestScope.result} </h1>
		<P>Auto Crawl will be launched according to your configuration soon automatically. Please go to <a href="run.jsp">Status and Report</a> to monitor the running status and check the report.</P>
	<br>
	<br>
    </div>

    <script src="./js/jquery.js"></script>
    <script>
    			function test(){
         	                     var hostname = "webauto";
		                         var request = "http://" + hostname + ":8080/robot/services/rest/start";
		
		         $.ajax({
		            url: request,
		            data:"",
		            type: "post",
		            success: function (data) {
		            	console.log(JSON.stringify(data));
		            	
		            },
		            error : function (e){
		
		            //alert('failed');
			        console.log(e);
		            },
		         });
		         
         }
    			
    			var result = '${requestScope.result}';
    			if('Finished!' == result){
    			    console.log('Launch web service');
    			    test();
    			}
    			else{
    				console.log(result);
    			}
    </script>

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
