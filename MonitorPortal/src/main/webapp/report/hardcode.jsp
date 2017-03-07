<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Hardcode screen - string</title>
</head>
<%
String ProjectID="";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
%>
<frameset rows="*" cols="40%,*" framespacing="0" frameborder="NO" border="0">
    <frame src="hardcode_string.jsp?ProjectID=<%=ProjectID%>" name="leftFrame">
    <frame src="hardcode_screen.jsp?ProjectID=<%=ProjectID%>" name="rightFrame">�
</frameset>

<noframes><body>
</body></noframes>

