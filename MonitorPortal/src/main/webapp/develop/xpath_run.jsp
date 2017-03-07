<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>

<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<script type="text/javascript" src="./js/selection.js"></script>
<title>Run XPATH</title>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">

<%
String XpathID="";
String LevelID="";
String PathSeq="";
String XpathPath="";
String ProjectID="";
String UserID="";
String ProjectName="";
String ParentName="";
String ActualParentName="";
String ParentSeq="";
String ParentFilter="";
String tagName="";
String Attribute="";
String AttributeCondition="";
String AttributeValue="";
String XPATHName="";
String XPATHDesc="";
String seq="";
String XPATH="";
String DOMType="";
String popup="";
String dropdown="";
String doubleclick="";
String mouseright="";
String mouseover="";
String tips="";
String frame="";
String iframe="";
String leaf="";
String fromXML="";
String fromCSV="";
String MyPageFilter="";
String capture="";
String Feature="";
String screenName="";

XpathID=request.getParameter("XpathID");
ProjectID=request.getParameter("ProjectID");
workM.getConnection();

String DebugServer="http://localhost";
String sql_debug="select DebugServer from ProjectParameter where ProjectID='"+ProjectID+"'";
ResultSet rs_debug=workM.getSQL(sql_debug);
if(rs_debug.next()){
	DebugServer=rs_debug.getString("DebugServer");
}
rs_debug.close();

String sql="select * from XpathList where XpathID="+XpathID+" and ProjectID='"+ProjectID+"'";
ResultSet RS=workM.getSQL(sql);
if(RS.next()){	       
	ParentName=RS.getString("ParentName");
	
	ParentSeq=RS.getString("ParentSeq");		
	tagName=RS.getString("tagName");
	Attribute=RS.getString("Attribute");
	AttributeCondition=RS.getString("AttributeCondition");
	AttributeValue=RS.getString("AttributeValue");
	XPATHName=RS.getString("XPATHName");
	XPATHDesc=RS.getString("XPATHDesc");
	seq=RS.getString("seq");   		
	XPATH=RS.getString("XPATH");
		XPATH=XPATH.replace('&', '\''); 		
	MyPageFilter=XPATH+"|//"+XPATHName;
	DOMType=RS.getString("DOMType");
	popup=RS.getString("popup");
	dropdown=RS.getString("dropdown");
	doubleclick=RS.getString("doubleclick");
	mouseright=RS.getString("mouseright");
	mouseover=RS.getString("mouseover");
	tips=RS.getString("tips");
	frame=RS.getString("frame");
	iframe=RS.getString("iframe");
	leaf=RS.getString("leaf");	
}
RS.close();
RS=null;

String matchfield="";
sql="select * from NodeList where special=1 and ProjectID='"+ProjectID+"'";
if(ParentName.compareTo("-1")!=0){
	sql=sql+" and ParentFilter like '%//name_"+ParentName+"'";
	matchfield+="ParentName";
}
if(tagName.compareTo("-1")!=0){
	sql=sql+" and tagName='"+tagName+"'";
	matchfield+="tagName";
}
if(Attribute.compareTo("-1")!=0){
	if(Attribute.compareTo("nodeId")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeId like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeId='"+AttributeValue+"'";
		}
		matchfield+="nodeId";
	}
	if(Attribute.compareTo("nodeHref")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeHref like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeHref='"+AttributeValue+"'";
		}
		matchfield+="nodeHref";
	}
	if(Attribute.compareTo("nodeText")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeText like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeText='"+AttributeValue+"'";
		}
		matchfield+="nodeText";
	}
	if(Attribute.compareTo("nodeTitle")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeTitle like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeTitle='"+AttributeValue+"'";
		}
		matchfield+="nodeTitle";
	}
	if(Attribute.compareTo("nodeClass")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeClass like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeClass='"+AttributeValue+"'";
		}
		matchfield+="nodeClass";
	}
	if(Attribute.compareTo("nodeName")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeName like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeName='"+AttributeValue+"'";
		}
		matchfield+="nodeName";
	}
	if(Attribute.compareTo("nodeValue")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeValue like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeValue='"+AttributeValue+"'";
		}
		matchfield+="nodeValue";
	}
	if(Attribute.compareTo("nodeType")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and nodeType like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and nodeType='"+AttributeValue+"'";
		}
		matchfield+="nodeType";
	}
	if(Attribute.compareTo("onclick")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and onclick like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and onclick='"+AttributeValue+"'";
		}
		matchfield+="onclick";
	}
	if(Attribute.compareTo("onkeydown")==0){
		if(AttributeCondition.compareTo("contains")==0){
			sql=sql+" and onkeydown like '%"+AttributeValue+"%'";
		}
		if(AttributeCondition.compareTo("equals")==0){
			sql=sql+" and onkeydown='"+AttributeValue+"'";
		}
		matchfield+="onkeydown";
	}
}
//out.print(sql);
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>

<table width="1200" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="xpath.jsp" name="Maintain" >
  <input type=hidden name="ProjectID" value="<%=ProjectID%>">
  <input type=hidden name="XpathID" value="<%=XpathID%>">
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="18" align=center><span class="zi"><b>Node-<a href='xpath.jsp?ProjectID=<%=ProjectID%>'><font color=blue>XPATH</font></a></b></span></td>
  </tr>  
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"><div align="center">Select</div></td>
    <td width="100" height="25"><div align="center">NodePath</div></td>
    <td width="100" height="25"><div align="center">Screen</div></td>
    <td width="100" height="25"><div align="center">PageFilter(XPATH)</div></td>
    <td width="100" height="25"><div align="center">New XPATH</div></td>
    <td width="100" height="25"><div align="center">ParentFilter(XPATH)</div></td>
    <td width="100" height="25"><div align="center">Tag</div></td>
    <td width="100" height="25"><div align="center">id</div></td>
    <td width="100" height="25"><div align="center">href</div></td>   
    <td width="100" height="25"><div align="center">text</div></td>  
    <td width="100" height="25"><div align="center">title</div></td>
    <td width="100" height="25"><div align="center">class</div></td>
    <td width="100" height="25"><div align="center">name</div></td>
    <td width="100" height="25"><div align="center">value</div></td>
    <td width="100" height="25"><div align="center">type</div></td>
    <td width="100" height="25"><div align="center">onclick</div></td>  
    <td width="100" height="25"><div align="center">onkeydown</div></td>
    <td width="150" height="25"> 
      <div align="center"><A href="about:blank" onclick="nn=Judge(Maintain); if (nn>0) {if (confirm('Confirm update?')){Maintain.action='xpath_run_update_mul.jsp';OnSubmit(Maintain);Maintain.submit(); return false;} else {return false;}}  else {alert('Please select node');return false;}"><IMG alt='select'  border=0 name=22.1.1.6.1 src="images/applymul.gif"></A></div></td>
  </tr>
<%
int iName=0;
String ListID=""; String NodePath=""; String PageFilter=""; 
String NodeSeq=""; String NewNodeSeq=""; String NewParentSeq="";
boolean bDisplay=false;
ResultSet rs1=workM.getSQL(sql);
while(rs1.next()){
	bDisplay=false;
	ListID=rs1.getString("ListID");
	NodePath=rs1.getString("NodePath");
	NodeSeq=rs1.getString("seq");	
	PageFilter=rs1.getString("PageFilter");
		PageFilter=PageFilter.replace('&', '\'');
	ParentFilter=rs1.getString("ParentFilter");
		ParentFilter=ParentFilter.replace('&', '\'');
	iName=ParentFilter.indexOf("name_");
	if(iName>0){
		ActualParentName=ParentFilter.substring(iName+5);
	}
	capture=rs1.getString("capture");
	Feature=rs1.getString("Feature");
	screenName=rs1.getString("screenName");
	NewNodeSeq=","+NodeSeq+",";
	NewParentSeq=","+ParentSeq+",";
	if(NewParentSeq.compareTo(",,")!=0){
		if(NewParentSeq.indexOf(NewNodeSeq)!=-1){
			bDisplay=true;
		}else{
			bDisplay=false;
		}
	}else{
		bDisplay=true;
	}
	if(bDisplay){
	%>
	<tr bgcolor="#F8FDFF" onmouseover="this.bgColor='#EEF2F4';" onmouseout="this.bgColor='#F8FDFF';"> 
	    	<td height="25"><div align="center"><INPUT name=now_dn type=checkbox value="<%=ListID%>"></div></td>
	    	<td height="25"><div align="center">
	    	<a target=_blank href='../report/filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><font color='blue'><%=NodePath%></font></a>	    	
	    	</div></td>
	    	<td height="25"><div align="center">
	    	<%
	    	if(capture.compareTo("1")==0){
	    	%>
	    	<BR>
	    	<a target=_blank href='<%=DebugServer%>/screen/<%=Feature%><%=screenName%>'><font color='blue'><%=screenName%></font></a>
	    	<%
	    	}
	    	%>
	    	</div></td>
	    	<td height="25"><div align="center"><%=PageFilter%></div></td>
	    	<td height="25"><div align="center">
	    	<a target=_blank href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XPATHName=<%=XPATHName%>"><font color='blue'><%=XPATH%>|//name_<%=XPATHName%></font></a>
	    	</div></td>
	    	<td height="25"><div align="center">
	    	<a target=_blank href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XPATHName=<%=ActualParentName%>"><font color='blue'><%=ParentFilter%></font></a>
	    	<%if(matchfield.indexOf("ParentName")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("tagName")%> <%if(matchfield.indexOf("tagName")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeId")%> <%if(matchfield.indexOf("nodeId")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeHref")%> <%if(matchfield.indexOf("nodeHref")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeText")%> <%if(matchfield.indexOf("nodeText")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeTitle")%> <%if(matchfield.indexOf("nodeTitle")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeClass")%> <%if(matchfield.indexOf("nodeClass")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeName")%> <%if(matchfield.indexOf("nodeName")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeValue")%> <%if(matchfield.indexOf("nodeValue")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("nodeType")%> <%if(matchfield.indexOf("nodeType")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("onclick")%> <%if(matchfield.indexOf("onclick")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"><div align="center"><%=rs1.getString("onkeydown")%> <%if(matchfield.indexOf("onkeydown")!=-1){%>(<font color=red>match</font>)<%}%></div></td>
	    	<td height="25"> 
	      		<div align="center">
	      		<%
	      		if(PageFilter.compareTo(XPATH+"|//name_"+XPATHName)==0){
	      		%>
	      		Same or Done
	      		<%
	      		}else{
	      		%>
	      		<a href="xpath_run_update.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>&ListID=<%=ListID%>"><IMG alt='update'  border=0 name=22.1.1.6.1 src="images/apply.gif"></a>       
	      		<%
	      		}
	      		%>
	      		</div>
	      	</td>      
  	</tr>
	<%	
	}	
}
rs1.close();
rs1=null;
%>
<tr bgcolor="#F8FDFF"> 
    <td height="25"><div align="center"><A onclick=SelectResult() style="CURSOR: hand"><IMG border=0 name=XuanZe1 src="images/icon_2_1.gif"></A></div></td>
    <td height="25" colspan="16" align='center'>
    	<input name="button" type=button onclick="history.back(-1);" value="   Back   ">
    </td>
    <td height="25"><div align="center"></div></td>
  </tr>
<INPUT name=name_array type=hidden> 
</form> 
</table>
</td>
</tr>
</table>
<%
workM.close();
%>
</body>
</html>		
		