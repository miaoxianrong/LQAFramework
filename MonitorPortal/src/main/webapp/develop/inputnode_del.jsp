<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String NodePath=request.getParameter("NodePath");
String InputNodeListID=request.getParameter("InputNodeListID");
String OrderValue=request.getParameter("OrderValue");

String sql="delete from InputNodeList ";
sql=sql+" where InputNodeListID="+InputNodeListID;
workM.updateSQL(sql);
sql="delete from NameValueList ";
sql=sql+" where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
workM.updateSQL(sql);
workM.close();
response.sendRedirect("inputnode_list.jsp?ProjectID="+ProjectID+"&OrderValue="+OrderValue);
//out.print(sql+"<BR>");
%>
