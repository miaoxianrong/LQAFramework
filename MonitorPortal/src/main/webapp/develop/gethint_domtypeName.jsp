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
String sql="select domtypeID,domtypeName from domtypeList where ProjectID='"+ProjectID+"' order by domtypeID desc";
ResultSet RS=workM.getSQL(sql);
String Hint=""; 
String domtypeID="";
String domtypeName="";
int i=0;
while(RS.next()){
	i++;
	domtypeID=RS.getString("domtypeID");
	domtypeName=RS.getString("domtypeName");
	if(domtypeName.indexOf(Tezhengzhi)!=-1){
		Hint=Hint+"<a target=_blank href='domtype_edit.jsp?domtypeID="+domtypeID+"'>"+domtypeName+"</a> ";
	}
}
RS.close();workM.close();
out.print(Hint);
%>
