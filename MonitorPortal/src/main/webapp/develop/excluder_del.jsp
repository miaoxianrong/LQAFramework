<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String excluderID=request.getParameter("excluderID");
String sql="delete from excluderList where excluderID="+excluderID+" and ProjectID='"+ProjectID+"'";
workM.getConnection();
workM.updateSQL(sql);
workM.close();
response.sendRedirect("excluder.jsp?ProjectID="+ProjectID);
%>
		
		