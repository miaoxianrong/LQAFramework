<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String Location=request.getParameter("Location");
String NotFoundReason=request.getParameter("NotFoundReason");
NotFoundReason=NotFoundReason.trim();
String Content=request.getParameter("Content");
Content=Content.trim();
Content=Content.replace('\'', ' ');
Content=Content.replace(',', '@');
String sql="insert into ReasonList set ";
sql=sql+"ProjectID='"+ProjectID+"'";
sql=sql+",NotFoundReason='"+NotFoundReason+"'";
sql=sql+",Location='"+Location+"'";
sql=sql+",Content='"+Content+"'";
workM.updateSQL(sql);
workM.close();
response.sendRedirect("reason.jsp?ProjectID="+ProjectID);
%>
