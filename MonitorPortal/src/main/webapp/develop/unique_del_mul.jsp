<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String sql="";
String uniqueID="";
String ProjectID=request.getParameter("ProjectID");
String OrderValue=request.getParameter("OrderValue");
String name_array=request.getParameter("name_array");
StringTokenizer stArray=new StringTokenizer(name_array,",");
boolean bExecute=false;
while(stArray.hasMoreTokens()){
	uniqueID = stArray.nextToken();
		sql="delete from uniqueList where uniqueID="+uniqueID+" and ProjectID='"+ProjectID+"'";
		out.println(sql+"<BR>");
		workM.updateSQL(sql);
		bExecute=true;
}
if(bExecute){
	workM.close();
}
response.sendRedirect("unique.jsp?ProjectID="+ProjectID);
%>
		
		