<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection(); 
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="11111111";
}
String Tezhengzhi=request.getParameter("Tezhengzhi");
String sql="select XpathID,XPATHName from XpathList where ProjectID='"+ProjectID+"' order by XpathID desc";
ResultSet RS=workM.getSQL(sql);
String Hint=""; 
String XpathID="";
String XPATHName="";
int i=0;
while(RS.next()){
	i++;
	XpathID=RS.getString("XpathID");
	XPATHName=RS.getString("XPATHName");
	if(XPATHName.indexOf(Tezhengzhi)!=-1){
		Hint=Hint+"<a target=_blank href='xpath_edit.jsp?XpathID="+XpathID+"'>"+XPATHName+"</a> ";
	}
}
RS.close();workM.close();
out.print(Hint);
%>
