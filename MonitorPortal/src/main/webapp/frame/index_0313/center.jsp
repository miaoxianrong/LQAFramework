
<html><%@include file="../shared/sql_try.jsp" %>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	line-height: 180%;
	color: #2E252A;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../img/web.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript>
function switchSysBar(){
	if (form1.switchPoint.value==1){
		form1.switchPoint.value=0;
		top.content.cols="1,5,*"
	}
	else{
		form1.switchPoint.value=1
		top.content.cols="189,5,1406"		
	}
}
</script>
</head>
<body bgcolor="#FFFFFF">
<!-- ImageReady Slices (01-2.psd) -->
<table width="5"  height="100%" border="0" cellspacing="0" cellpadding="0">
<form name="form1">
  <tr valign="top">
    <td width="5" height="423" valign="middle" background="../img/center_bg.jpg"><img src="../img/center_dh.jpg" width="5" height="43" style="CURSOR: hand" onclick="switchSysBar()"></td>
  </tr>
  <input type="hidden" name="switchPoint" value="1">
</form>  
</table>
<!-- End ImageReady Slices -->
</body>
</html><%@include file="../shared/sql_catch.jsp" %>