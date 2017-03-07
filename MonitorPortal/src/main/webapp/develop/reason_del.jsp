<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String ReasonID=request.getParameter("ReasonID");
String sql="delete from ReasonList where ProjectID='"+ProjectID+"' and ReasonID="+ReasonID;
workM.getConnection();
workM.updateSQL(sql);
workM.close();
response.sendRedirect("reason.jsp?ProjectID="+ProjectID);
%>
		
		