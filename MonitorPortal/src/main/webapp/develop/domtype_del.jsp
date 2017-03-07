<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String domtypeID=request.getParameter("domtypeID");
String sql="delete from domtypeList where domtypeID="+domtypeID+" and ProjectID='"+ProjectID+"'";
workM.getConnection();
workM.updateSQL(sql);
workM.close();
response.sendRedirect("domtype.jsp?ProjectID="+ProjectID);
%>
		
		