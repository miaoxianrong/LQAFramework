<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String NodePath=request.getParameter("NodePath");
String NameValueListID=request.getParameter("NameValueListID");

String sql="delete from NameValueList ";
sql=sql+" where NameValueListID="+NameValueListID;
workM.updateSQL(sql);
workM.close();
response.sendRedirect("input_node.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath);
//out.print(sql+"<BR>");
%>
