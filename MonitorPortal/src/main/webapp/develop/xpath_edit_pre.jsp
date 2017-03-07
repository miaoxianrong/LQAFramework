<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String XPATHName=request.getParameter("XPATHName");
String XpathID="";
boolean bFound=false;
String sql="select XpathID from XpathList where ProjectID='"+ProjectID+"' and XPATHName='"+XPATHName+"'";
ResultSet rs0=workM.getSQL(sql);
if(rs0.next()){
	XpathID=rs0.getString("XpathID");
	bFound=true;
}
rs0.close();
rs0=null;

workM.close();
if(bFound){
	response.sendRedirect("xpath_edit.jsp?ProjectID="+ProjectID+"&XpathID="+XpathID);
}else{
	String Infomation="XpathID is not found.";
	response.sendRedirect("../management/invalid_op.jsp?Infomation="+Infomation);
}
%>
