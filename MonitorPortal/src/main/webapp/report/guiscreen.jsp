<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<html>
<head>
<title></title>
</script>
</head>
<body>
<%
String ProjectID="";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}

workM.getConnection();

String DebugServer="http://localhost";
String sql_debug="select DebugServer from ProjectParameter where ProjectID='"+ProjectID+"'";
ResultSet rs_debug=workM.getSQL(sql_debug);
if(rs_debug.next()){
	DebugServer=rs_debug.getString("DebugServer");
}
rs_debug.close();


workM.close();

String Feature="";
String getFeature=request.getParameter("Feature");
if(getFeature!=null){
	Feature=getFeature;
}
String screenName="00_main_page.png";
String getscreenName=request.getParameter("screenName");
if(getscreenName!=null){
	screenName=getscreenName;
}else{
	screenName="000.png";
}
%>
<table width='100%' height='100%'>
<tr><td  align=left vAlign=top><img border=0 src='../jar/output/<%=ProjectID%>/screen/<%=screenName%>'></td></tr>
</table>
</body>
</html>