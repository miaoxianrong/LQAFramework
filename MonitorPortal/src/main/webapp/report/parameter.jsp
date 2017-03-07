<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Edit</title>
<script type="text/javascript">
function showHintexcluderName(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintexcluderName").innerHTML="";
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
    document.getElementById("txtHintexcluderName").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_excluderName.jsp?ProjectID=<%=ProjectID%>&Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--


function validata()
{
    var errfound = false;
   
   if( document.kh.excluderName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.excluderName.focus();
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
String dropno="";
String sql="select * from ProjectParameter where ProjectID='"+ProjectID+"'";
ResultSet rs1=workM.getSQL(sql);
if(rs1.next()){
	dropno="1";//rs1.getString("dropno");		
}
rs1.close();
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="parameter_edit_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Edit Parameter</b></span></td>
      </tr>      
     <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Product Name: </td><td> 
          <input onkeydown=Frm_Down(); name="ProductName" class="tb" style="WIDTH: 343px"  onFocus=select()  value="MCC">
         </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Language: </td><td> 
          <input onkeydown=Frm_Down(); name="ProductName" class="tb" style="WIDTH: 343px"  onFocus=select()  value="fr">
         </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>URL: </td><td> 
          <input onkeydown=Frm_Down(); name="ProductName" class="tb" style="WIDTH: 343px"  onFocus=select()  value="http://10.131.67.229:8080/admin/">
         </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Security Name: </td><td> 
          <input onkeydown=Frm_Down(); name="ProductName" class="tb" style="WIDTH: 343px"  onFocus=select()  value="MCC">
         </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Security Password: </td><td> 
          <input onkeydown=Frm_Down(); name="ProductName" class="tb" style="WIDTH: 343px"  onFocus=select()  value="MCC">
         </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>User Name: </td><td> 
          <input onkeydown=Frm_Down(); name="ProductName" class="tb" style="WIDTH: 343px"  onFocus=select()  value="MCC">
         </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Drop No: </td><td> 
          <SELECT name=ParentName size=1 class="tb">
          	<OPTION value="-1">All</OPTION>
          	<OPTION value="0">0</OPTION>
          	<OPTION value="1">1</OPTION>
          	<OPTION value="2">2</OPTION>
          	<OPTION value="3">3</OPTION>
          	<OPTION value="4">4</OPTION>
          	
	  </SELECT>
	</td>
      </tr>
      
      
      
      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Save  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
