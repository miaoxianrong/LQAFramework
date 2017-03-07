<%
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="";
}
%>
<html>
<head>
<title>LQA Framework</title>

</script>
</head>
<frameset rows="111,*,22" cols="*" framespacing="0" frameborder="NO" border="0">
  <frame src="index/top.jsp?ProjectID=<%=ProjectID%>" name="topFrame" scrolling="NO" noresize >
  <frameset name=content rows="*" cols="189,5,*" frameborder="yes" framespacing="1" bordercolor="#6B8EDF">
			<frame name="leftFrame" scrolling="no" src="index/left.jsp?ProjectID=<%=ProjectID%>" frameborder="no" noresize bordercolor="#045a82" borderColorDark="#000" bgColor="#ff000" borderColorLight="#045a82">
			<frame name="center" src="index/center.jsp" frameborder="no" scrolling="NO" noresize bordercolor="#ffffff">
			<frame name="mainFrame" src="../report/navigation.jsp?ProjectID=<%=ProjectID%>" frameborder="no" scrolling="auto" bordercolor="#ffffff">
		</frameset>
  <frame src="index/bottom.jsp" name="bottomFrame" scrolling="NO" noresize>
</frameset>
<noframes><body>
</body></noframes>
</html>