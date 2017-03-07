<%
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="";
}
String password=request.getParameter("password");
if(password.compareTo("Tng22l")==0){
	response.sendRedirect("../main.jsp?ProjectID="+ProjectID);
}else{
	response.sendRedirect("../../management/invalid.jsp?ProjectID="+ProjectID);
}
//response.sendRedirect("../../report/navigation.jsp?ProjectID="+ProjectID);
%>


