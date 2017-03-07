<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Import</title>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%
workM.getConnection();
String TargetProjectID=request.getParameter("ProjectID");
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="reason_import_list.jsp" name="kh">
      <input type=hidden name="TargetProjectID" value="<%=TargetProjectID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Import Filter</b></span></td>
      </tr>  
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Project ID and name: </td><td> 
          <SELECT name=ProjectID size=1 class="tb">
	          <%
	          String ProjectID="";String ProductName="";
	          String sql="select ProjectID,ProductName from ProjectReport order by ReportID desc";
	          ResultSet rs=workM.getSQL(sql);
	          while(rs.next()){
	          	ProjectID=rs.getString("ProjectID");
	          	ProductName=rs.getString("ProductName");
	          %>
			<OPTION value="<%=ProjectID%>"><%=ProjectID%>-<%=ProductName%></OPTION>
		  <%
		  }
		  rs.close();
		  workM.close();
		  %>
		  </SELECT>
	</td>
      </tr>
      
      
      
      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Import Now ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
