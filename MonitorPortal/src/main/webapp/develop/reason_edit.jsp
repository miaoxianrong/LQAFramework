<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="00000000";
}
String ReasonID=request.getParameter("ReasonID");
if(ReasonID==null){
	ReasonID="1";
}
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Edit Content</title>
<script type="text/javascript">
function showHintNotFoundReason(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintNotFoundReason").innerHTML="";
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
    document.getElementById("txtHintNotFoundReason").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_NotFoundReason.jsp?Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--
function del()
{
  return confirm('Confirm delete?');
}

function validata()
{
   var errfound = false;
   
   if( document.kh.NotFoundReason.value == '' )
   {
       window.alert("Please input name!");
       document.kh.NotFoundReason.focus();
   }
   else if( document.kh.Content.value == '' )
   {
       window.alert("Please input Content!");
       document.kh.Content.focus();
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

String NotFoundReason="";
String Content="";
String Location="";
String sql="select * from ReasonList where ProjectID='"+ProjectID+"' and ReasonID="+ReasonID;
ResultSet rs1=workM.getSQL(sql);
if(rs1.next()){
	
	ReasonID=rs1.getString("ReasonID");
	
	NotFoundReason=rs1.getString("NotFoundReason");
	Location=rs1.getString("Location");
	Content=rs1.getString("Content");
	Content=Content.replace('&', '\'');
	Content=Content.replace('@', ','); 
	
	
}
rs1.close();
workM.close();
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=800 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="reason_edit_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
      <input type=hidden name="ReasonID" value="<%=ReasonID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Edit <a href="reason.jsp?ProjectID=<%=ProjectID%>"><font color=blue>Filter</font></a></b></span></td>
      </tr>      
     
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Filter Name:</td><td> 
          <input onkeydown=Frm_Down(); name="NotFoundReason" class="tb" style="WIDTH: 356px"  value="<%=NotFoundReason%>"></span>
          </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Unit: </td><td> 
          <input type="radio" name="Location" value="ST" <%if(Location.compareTo("ST")==0){%> checked <%}%>>String
          <input type="radio" name="Location" value="ID" <%if(Location.compareTo("ID")==0){%> checked <%}%>>ID
          <input type="radio" name="Location" value="FN" <%if(Location.compareTo("FN")==0){%> checked <%}%>>FileName
        </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right valign="top">Contain: </td><td> 
          <textarea rows="8" name="Content" onFocus=select()  style="WIDTH: 556px"><%=Content%></textarea></td>
      </tr>
      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Update  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="reason_del.jsp?ReasonID=<%=ReasonID%>&ProjectID=<%=ProjectID%>" onclick="return del()"><IMG alt='Delete'  border=0 name=22.1.1.6.3 src="images/delete.gif"></a>
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
