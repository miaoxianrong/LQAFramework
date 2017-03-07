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
String sql="select uniqueID,uniqueName from uniqueList where ProjectID='"+ProjectID+"' order by uniqueID desc";
ResultSet RS=workM.getSQL(sql);
String Hint=""; 
String uniqueID="";
String uniqueName="";
int i=0;
while(RS.next()){
	i++;
	uniqueID=RS.getString("uniqueID");
	uniqueName=RS.getString("uniqueName");
	if(uniqueName.indexOf(Tezhengzhi)!=-1){
		Hint=Hint+"<a target=_blank href='unique_edit.jsp?uniqueID="+uniqueID+"'>"+uniqueName+"</a> ";
	}
}
RS.close();workM.close();
out.print(Hint);
%>
