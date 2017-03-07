<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<%
String ProjectID=request.getParameter("ProjectID");
String NodePath=request.getParameter("NodePath");
%>
<%
String PageContainer=""; 
workM.getConnection();
String sql="select PageContainer from NodeList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
ResultSet rs1 =workM.getURL(sql); 
if(rs1.next()){
	PageContainer=rs1.getString("PageContainer");	
	if(PageContainer.compareTo("")==0){
		PageContainer="//body/";
	}
	PageContainer=PageContainer.replace('&','\'');
}
rs1.close();
workM.close();
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Update Container</title>

</head>
<base target="_self">
<body bgcolor="#F8FDFF">

<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="update_container_do.jsp" name="kh">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
      <input type=hidden name="NodePath" value="<%=NodePath%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Update Container</b></span></td>
      </tr>           
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right width=30%>NodePath: </td><td>         
	  <%=NodePath%>
	</td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Update Page Container:</td><td> 
          <input onkeydown=Frm_Down(); name="PageContainer" class="tb" style="WIDTH: 156px"  value="<%=PageContainer%>"></span>
          </td>
      </tr>     
      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Update  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
