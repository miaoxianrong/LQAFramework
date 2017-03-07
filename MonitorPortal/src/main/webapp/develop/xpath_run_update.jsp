<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();

String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
String ListID=request.getParameter("ListID");

String XPATHName="";
String XPATH="";
String MyPageFilter="";
String DOMType="";
String leaf="";
String sql="select * from XpathList where XpathID="+XpathID+" and ProjectID='"+ProjectID+"'";

ResultSet RS=workM.getSQL(sql);
if(RS.next()){
	XPATHName=RS.getString("XPATHName");
	XPATH=RS.getString("XPATH");
	MyPageFilter=XPATH+"|//name_"+XPATHName;
	DOMType=RS.getString("DOMType");
	leaf=RS.getString("leaf");
}
RS.close();
RS=null;

String NodePath="";
sql="select NodePath from NodeList where ListID="+ListID+" and ProjectID='"+ProjectID+"'";
ResultSet myrs=workM.getSQL(sql);
if(myrs.next()){
	NodePath=myrs.getString("NodePath");
}
myrs.close();
myrs=null;
if(NodePath.compareTo("")!=0){
	sql="update NodeList set touchonce=0, capture=0, screenName='', screenDesc='' ";	
	sql+=" where ListID="+ListID+" and ProjectID='"+ProjectID+"'";
	workM.updateSQL(sql);
	//out.print(sql+"<BR>");
	sql="delete from NodeList where NodePath like '"+NodePath+"-%'";
	sql+=" and ProjectID='"+ProjectID+"'";
	workM.updateSQL(sql);
	//out.print(sql+"<BR>");
}

sql="update NodeList set ";
sql+=" PageFilter='"+MyPageFilter+"'";
if(DOMType.compareTo("-1")!=0){
	sql+=",DOMType='"+DOMType+"'";
}
sql+=",leaf="+leaf;
sql+=" where ListID="+ListID+" and ProjectID='"+ProjectID+"'";

//out.print(sql);

workM.updateSQL(sql);
workM.close();
response.sendRedirect("xpath_run.jsp?ProjectID="+ProjectID+"&XpathID="+XpathID);
%>
		
		