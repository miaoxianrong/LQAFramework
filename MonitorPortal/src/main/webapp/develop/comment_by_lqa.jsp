<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.io.*" %>
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
String ProductComment="";
String ProductName="";
workM.getConnection();
String sql="select * from ProjectReport where ProjectID='"+ProjectID+"'";
ResultSet rs1=workM.getSQL(sql);
if(rs1.next()){	
	ProductName=rs1.getString("ProductName");	
	ProductComment=rs1.getString("ProductComment");
	ProductComment=ProductComment.replace(';',',');
	ProductComment=ProductComment.replace('"', '\'');
}
rs1.close();
workM.close();
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="comment_by_lqa_do.jsp">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">      
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>[<%=ProjectID%>-<%=ProductName%>] Comment by LQA</b></span></td>
      </tr>     
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right vAlign=top>Comment/Steps:<BR>(<250 char)</td><td> 
          <textarea rows="8" name="ProductComment" onFocus=select()  style="WIDTH: 856px"><%=ProductComment%></textarea>
         </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Save/Update  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
      
</table>
</td>
</tr>
</table>
</body>
</html>
