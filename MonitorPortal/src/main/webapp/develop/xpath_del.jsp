<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
String sql="delete from XpathList where XpathID="+XpathID+" and ProjectID='"+ProjectID+"'";
workM.getConnection();
workM.updateSQL(sql);
workM.close();
response.sendRedirect("xpath.jsp?ProjectID="+ProjectID);
%>
		
		