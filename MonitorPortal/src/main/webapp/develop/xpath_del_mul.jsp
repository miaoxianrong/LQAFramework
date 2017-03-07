<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String sql="";
String XpathID="";
String ProjectID=request.getParameter("ProjectID");
String OrderValue=request.getParameter("OrderValue");
String name_array=request.getParameter("name_array");
StringTokenizer stArray=new StringTokenizer(name_array,",");
boolean bExecute=false;
while(stArray.hasMoreTokens()){
	XpathID = stArray.nextToken();
		sql="delete from XpathList where XpathID="+XpathID+" and ProjectID='"+ProjectID+"'";
		//out.println(sql+"<BR>");
		workM.updateSQL(sql);
		bExecute=true;
}
if(bExecute){
	workM.close();
}
response.sendRedirect("xpath.jsp?ProjectID="+ProjectID);
%>
		
		