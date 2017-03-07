<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title>Report</title>
<script language="JavaScript"> 
function myrefresh() 
{ 
window.location.reload(); 
} 
//setTimeout('myrefresh()',5000); 
</script> 
<script language="javascript">
<!--
function del()
{
  return confirm('Del?');
}

//-->
</script>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%
String ReportID,ProjectID,ProductName;
%>
<%
ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="";
}
ProductName="";
String bgcolor="#F8FDFF";
String sql="select ProductName from ProjectReport where ProjectID='"+ProjectID+"'"; 
workM.getConnection();
ResultSet RS =workM.getURL(sql); 
if(RS.next()){
	ProductName=RS.getString("ProductName");
}
RS.close();
workM.close();
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="950" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="navigation.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="15" align=center><span class="zi"><b>Develop</b></span></td>
  </tr>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25">ProjectID</td>
    <td height="25">ProductName</td>
    <td height="25">XPATH</td>
    <td height="25">UniqueObject</td>
    <td height="25">Excluder</td>
    <td height="25">Includer</td>
    <td height="25">DOM Type</td>
    <td height="25">Input value</td>
    <td height="25">SetChildNum</td>
    <td height="25">Analysis</td>
    <td height="25">XpathRunResult</td>    
    <td height="25">Alert</td>
    <td height="25">Debug</td>
    <td height="25">GUIMap</td>
    <td height="25">Navigation</td>
  </tr>
  <%
  bgcolor="#F8CDFF";
  %>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"><%=ProjectID%></td>
    <td height="25"><%=ProductName%></td>
    <td height="25"><a href='../develop/xpath.jsp?ProjectID=<%=ProjectID%>'>XPATH</a></td>
    <td height="25"><a href='../develop/unique.jsp?ProjectID=<%=ProjectID%>'>UniqueObject</a></td>
    <td height="25"><a href='../develop/excluder.jsp?ProjectID=<%=ProjectID%>'>Excluder</a></td>
    <td height="25"><a href='../develop/includer.jsp?ProjectID=<%=ProjectID%>'>Includer</a></td>
    <td height="25"><a href='../develop/domtype.jsp?ProjectID=<%=ProjectID%>'>DOM Type</a></td>
    <td height="25"><a href='#?../develop/inputvalue.jsp?ProjectID=<%=ProjectID%>'>Input value</a></td>
    <td height="25"><a href='#?../develop/SetChildNum.jsp?ProjectID=<%=ProjectID%>'>SetChildNum</a></td>
    <td height="25"><a href='dom.jsp?ProjectID=<%=ProjectID%>'>DOM</a></td>
    <td height="25"><a href='filter.jsp?ProjectID=<%=ProjectID%>'>XpathRunResult</a></td>    
    <td height="25"><a href='alert.jsp?ProjectID=<%=ProjectID%>'>Alert</a></td>
    <td height="25"><a href='status.jsp?ProjectID=<%=ProjectID%>'>Debug</a></td>
    <td height="25"><a href='guimap.jsp?ProjectID=<%=ProjectID%>'>GUIMap</a></td>
    <td height="25"><a href='navigation.jsp?ProjectID=<%=ProjectID%>'>Navigation</a></td>
  </tr>


</form> 

  </table>
	
</td>
</tr>
</table>

</body>
</html>

