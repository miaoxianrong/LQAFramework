<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<%
String NodePath=request.getParameter("NodePath");
if(NodePath==null){
	NodePath="";
}

workM.getConnection();
String targetURL="";
String sql="select nodeHref from NodeList where ProjectID='00000372' and NodePath='"+NodePath+"'";
ResultSet rs= workM.getURL(sql); 
if(rs.next()){
	targetURL=rs.getString("nodeHref");
}
rs.close();
workM.close();
//out.print(targetURL);
if(targetURL.compareTo("")==0){
	targetURL="http://yepse01-i45663:81/niku/nu#action:projmgr.adminlinkList";//"http://liahu01enu2008s:8080/usm/wpf";
}
out.print(targetURL);
targetURL="http://yepse01-i45663:81/niku/nu#action:projmgr.adminlinkList";//"http://liahu01enu2008s:8080/usm/wpf";
response.sendRedirect(targetURL);
%>