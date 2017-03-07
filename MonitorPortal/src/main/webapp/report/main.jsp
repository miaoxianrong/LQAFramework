<head>

<title></title>
</head>
<%
String ProjectID="";
String getProjectID=request.getParameter("pid");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
%>
<frameset rows="*" cols="50%,*" framespacing="0" frameborder="NO" border="0">
    <frame src="guimap.jsp?ProjectID=<%=ProjectID%>" name="leftFrame">
    <frame src="screen.jsp?ProjectID=<%=ProjectID%>" name="rightFrame">ß
</frameset>

<noframes><body>
</body></noframes>

