<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String uniqueID=request.getParameter("uniqueID");
String sql="delete from uniqueList where uniqueID="+uniqueID+" and ProjectID='"+ProjectID+"'";
workM.getConnection();
workM.updateSQL(sql);
workM.close();
response.sendRedirect("unique.jsp?ProjectID="+ProjectID);
%>
		
		