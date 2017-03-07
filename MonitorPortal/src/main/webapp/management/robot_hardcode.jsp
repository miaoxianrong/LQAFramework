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
        <td height=25 colspan=2 align=center><span class="zi"><b>Download build to do <font color='red'>hardcode</font> detecting</b></span></td>
      </tr>      
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Build: </td>
        <td><a href="../build/robot/robot.jar">version 1.2</a></td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Paramter sample: </td>
        <td><a href="../build/robot/parameter.xml">PluginAction=Hardcode</a></td>
      </tr>
       <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>URL sample: </td>
        <td><a href="../build/robot/url.txt">url.txt</a></td>
      </tr>
       <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Terminology sample: </td>
        <td><a href="../build/robot/Terminology.txt">Terminology.txt</a></td>
      </tr>
     <tr bgcolor="#63DBE8"> 
        <td height=25 colspan=2 align=left><span class="zi">
        <b>Note:</b><br>
        Now, it is executed by robot.jar on command line.<br>
        It will be able to be executed online and API(RESTful web service) on next release.
        </span></td>
      </tr>   
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
