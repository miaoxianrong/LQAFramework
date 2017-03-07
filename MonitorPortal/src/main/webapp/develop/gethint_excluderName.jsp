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
String sql="select excluderID,excluderName from excluderList where ProjectID='"+ProjectID+"' order by excluderID desc";
ResultSet RS=workM.getSQL(sql);
String Hint=""; 
String excluderID="";
String excluderName="";
int i=0;
while(RS.next()){
	i++;
	excluderID=RS.getString("excluderID");
	excluderName=RS.getString("excluderName");
	if(excluderName.indexOf(Tezhengzhi)!=-1){
		Hint=Hint+"<a target=_blank href='excluder_edit.jsp?excluderID="+excluderID+"'>"+excluderName+"</a> ";
	}
}
RS.close();workM.close();
out.print(Hint);
%>
