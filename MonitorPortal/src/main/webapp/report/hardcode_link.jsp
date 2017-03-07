<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Hard code string - Screen List</title>
</head>
<%
String ProjectID="";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
String UniqueStringListID=request.getParameter("UniqueStringListID");
%>
<frameset rows="*" cols="40%,*" framespacing="0" frameborder="NO" border="0">
    <frame src="hardcode_link_screen_list.jsp?ProjectID=<%=ProjectID%>&UniqueStringListID=<%=UniqueStringListID%>" name="leftFrame">
    <frame src="hardcode_link_screen.jsp?ProjectID=<%=ProjectID%>&UniqueStringListID=<%=UniqueStringListID%>" name="rightFrame">�
</frameset>

<noframes><body>
</body></noframes>

