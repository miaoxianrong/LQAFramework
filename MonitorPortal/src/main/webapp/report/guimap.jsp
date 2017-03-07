<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>GUIMap</title>
</head>
<%
String ProjectID="";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
%>
<frameset rows="*" cols="50%,*" framespacing="0" frameborder="NO" border="0">
    <frame src="guilist.jsp?ProjectID=<%=ProjectID%>" name="leftFrame">
    <frame src="guiscreen.jsp?ProjectID=<%=ProjectID%>" name="rightFrame">�
</frameset>

<noframes><body>
</body></noframes>

