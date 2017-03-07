<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();

String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
String ListID="";

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


String name_array=request.getParameter("name_array");
StringTokenizer stArray=new StringTokenizer(name_array,",");
while(stArray.hasMoreTokens()){
	ListID = stArray.nextToken();
	sql="update NodeList set ";
	sql+=" PageFilter='"+MyPageFilter+"'";
	if(DOMType.compareTo("-1")!=0){
		sql+=",DOMType='"+DOMType+"'";
	}
	sql+=",leaf="+leaf;
	sql+=" where ListID="+ListID+" and ProjectID='"+ProjectID+"'";
	//out.print(sql+"<BR>");
	workM.updateSQL(sql);
}

workM.close();
response.sendRedirect("xpath_run.jsp?ProjectID="+ProjectID+"&XpathID="+XpathID);
%>
		
		