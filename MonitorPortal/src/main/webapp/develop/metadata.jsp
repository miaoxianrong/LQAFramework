<%
String ProjectID=request.getParameter("ProjectID");
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>
<title>MetaData</title>
</head>
<body bgcolor="#F8FDFF">
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>



<table width=800 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <tr bgcolor="#C3DBE8"> 
      <td height=25 colspan=2 align=center><span class="zi"><b>Import metadata file</b></span></td>
      </tr>      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>MetaData file</td>
        <td>c:\metadata.txt</td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>MetaData content</td>
        <td>
        "url":  "temp/test/onepagewidgets.html",<BR>
"pageContainer": "#wizard",<BR>
"resourceBundle": "widgets",<BR>
"fileName": "widgets",<BR>
"actions": [<BR>
	 {<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"event"    : "mouseEvent",<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"parameter": "mouseover",<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"selector" : "#rating1 .ux-rating-star-3 .xc30-form-checkbox",<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"container": "#tooltip-ExtC301060"<BR>
	},<BR>

        
        
        </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
      <td height=25 colspan=2 align=center><span class="zi">
      <a href="import.jsp?ProjectID=<%=ProjectID%>"><IMG alt='add'  border=0 name=22.1.1.6.2 src="images/import.gif"></a>
      </span></td>
      </tr>   
</table>
</td>
</tr>
</table>
</body>
</html>
				
		