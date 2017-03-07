<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String NodePath=request.getParameter("NodePath");
String PageContainer=request.getParameter("PageContainer");
PageContainer=PageContainer.replace('\'','&');
String sql="update NodeList set "+"PageContainer='"+PageContainer+"'";
sql=sql+" where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
workM.updateSQL(sql);
workM.close();
response.sendRedirect("../report/filter.jsp?ProjectID="+ProjectID);
//out.print(sql+"<BR>");
%>
