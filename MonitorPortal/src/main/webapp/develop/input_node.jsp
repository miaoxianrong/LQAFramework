<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
if(XpathID==null){
	XpathID="";
}
String NodePath=request.getParameter("NodePath");
if(NodePath==null){
	NodePath="000";
}
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<title>Input Value</title>
<script type="text/javascript">
function showHintincluderName(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintincluderName").innerHTML="";
  return;
  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHintincluderName").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_includerName.jsp?ProjectID=<%=ProjectID%>&Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--


function validata()
{
   var errfound = false;
   
   if( document.kh.includerName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.includerName.focus();
   }   
   else{
   	 errfound = true;
   }
   return errfound;
}    

function Frm_Down(){

  if (event.keyCode==13)
  {
      event.keyCode=9;
  }
}  
//-->
</script>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%
workM.getConnection();
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="includer_add_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=3 align=center><span class="zi"><b>Input value list</b></span></td>
      </tr>     
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right width=30%>NodePath: </td><td>         
	  <%=NodePath%>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <a href="inputvalue_add.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>"><IMG alt='Edit'  border=0 name=22.1.1.6.2 src="images/add.gif"></a>    
	</td>
      </tr>
      <tr bgcolor="#F8FDFF">
        <td height=100% align=right colspan=3> 
          <table bgColor="#333333" class="t" id=tableid border="0" cellPadding="0" cellSpacing="1" width="100%">
	  <tbody>
	    <tr bgcolor="#C3DBE8">
	      <td width="25" height="20" align=center>&nbsp;No.</td>
	      <td width="150" align="center">Title</td>
	      <td width="100" align="center">Name</td>
	      <td width="100" align="center">Value</td>
	      <td width="50" align="center">Edit</td>
	      <td width="50" align="center">Delete</td>
	    </tr>
	    	<%
	    	  String InputProperty=""; String InputName=""; String InputTitle="";
	    	  String InputValue=""; String NameValueListID="";	    	  
	          String sql="select * from InputNodeList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
	          ResultSet rs2=workM.getSQL(sql);
	          boolean bNotExist=true;
	          if(rs2.next()){
	          	bNotExist=false;
	          }
	          rs2.close();
	  	  //out.print(sql);
	          if(bNotExist){
		          sql="insert into InputNodeList set ProjectID='"+ProjectID+"'";
			  sql=sql+",NodePath='"+NodePath+"'";
			  workM.updateSQL(sql);
		  }
	          sql="select * from NameValueList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
	          ResultSet rs1=workM.getSQL(sql);
	          while(rs1.next()){
	            NameValueListID=rs1.getString("NameValueListID");
	            InputTitle=rs1.getString("InputTitle");
	            InputProperty=rs1.getString("InputProperty");
	            InputName=rs1.getString("InputName");
	            InputValue=rs1.getString("InputValue");
	            %>	
	   	    <tr bgcolor="#F8FDFF">
		      <td width="25" align="center" height="20"><input id="no" type=hidden value="" class="bb" readonly></td>      
		      <td width="150" align="center"><%=InputTitle%></td>
		      <td width="100" align="center"><%=InputName%></td>
		      <td width="100" align="center"><%=InputValue%></td>
		      <td width="50" align="center">
		      <a href="inputvalue_edit.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>&NameValueListID=<%=NameValueListID%>"><IMG alt='Edit'  border=0 name=22.1.1.6.2 src="images/edit.gif"></a>
		      </td>
		      <td width="50" align="center">
		      <a href="inputvalue_del.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>&NameValueListID=<%=NameValueListID%>"><IMG alt='Edit'  border=0 name=22.1.1.6.2 src="images/delete.gif"></a>
		      </td>
		    </tr>
		    <%
		  }
		  rs1.close();
		  workM.close();
		  // When running, mark nodepath
		  %>	
	  </tbody>
	  </table>
        </td>
      </tr>
    
      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=3 align=center>
        
           <input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
