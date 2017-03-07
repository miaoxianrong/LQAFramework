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
String sql="select includerID,includerName from includerList where ProjectID='"+ProjectID+"' order by includerID desc";
ResultSet RS=workM.getSQL(sql);
String Hint=""; 
String includerID="";
String includerName="";
int i=0;
while(RS.next()){
	i++;
	includerID=RS.getString("includerID");
	includerName=RS.getString("includerName");
	if(includerName.indexOf(Tezhengzhi)!=-1){
		Hint=Hint+"<a target=_blank href='includer_edit.jsp?includerID="+includerID+"'>"+includerName+"</a> ";
	}
}
RS.close();workM.close();
out.print(Hint);
%>
