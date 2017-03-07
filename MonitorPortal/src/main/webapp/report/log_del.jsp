<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String sql="delete from NodeLog";// where ProjectID='"+ProjectID+"'";
workM.getConnection();
workM.updateSQL(sql);
workM.close();
response.sendRedirect("log.jsp?ProjectID="+ProjectID);
%>
		
		