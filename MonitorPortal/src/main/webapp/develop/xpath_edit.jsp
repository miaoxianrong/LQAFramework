<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
if(XpathID==null){
	XpathID="";
}
String XPATHName=request.getParameter("XPATHName");
if(XPATHName==null){
	XPATHName="";
}else{
	String mysql="select XpathID from XpathList where XPATHName='"+XPATHName+"' and ProjectID='"+ProjectID+"'";
	ResultSet myrs=workM.getSQL(mysql);
	if(myrs.next()){
		XpathID=myrs.getString("XpathID");
	}
	myrs.close();
	myrs=null;
}
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Edit xpath</title>
<script type="text/javascript">
function showHintXPATHName(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintXPATHName").innerHTML="";
  return;
  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHintXPATHName").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_XPATHName.jsp?ProjectID=<%=ProjectID%>&Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--
function del()
{
  return confirm('Confirm delete?');
}

function validata()
{
   var errfound = false;
   
   if( document.kh.XPATHName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.XPATHName.focus();
   }
   else if( document.kh.XPATH.value == '' )
   {
       window.alert("Please input xpath!");
       document.kh.XPATH.focus();
   }
   else{
   	 errfound = true;
   }
   return errfound;
}    

function Frm_Down(){

  if (event.keyCode==13)
  {
      event.keyCode=9;
  }
}  
//-->
</script>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%

String LevelID="";
String XpathPath="";
String tagName="";
String Attribute="";
String AttributeCondition="";
String AttributeValue="";
String leaf="";
String XPATH="";
String PageContainer="";
String ParentName="";
String ParentSeq="";
String NodeLevelID="";
String seq="";
String DOMType="";
String sql="select * from XpathList where XpathID="+XpathID;
ResultSet rs1=workM.getSQL(sql);
if(rs1.next()){
	LevelID=rs1.getString("LevelID");
	XpathPath=rs1.getString("XpathPath");
	XPATHName=rs1.getString("XPATHName");
	tagName=rs1.getString("tagName");
	Attribute=rs1.getString("Attribute");
	AttributeCondition=rs1.getString("AttributeCondition");
	AttributeValue=rs1.getString("AttributeValue");
	leaf=rs1.getString("leaf");
	XPATH=rs1.getString("XPATH");
	XPATH=XPATH.replace('&', '\''); 
	
	PageContainer=rs1.getString("PageContainer");
	PageContainer=PageContainer.replace('&', '\''); 
	ParentName=rs1.getString("ParentName");
	ParentSeq=rs1.getString("ParentSeq");
	NodeLevelID=rs1.getString("NodeLevelID");
	seq=rs1.getString("seq");
	DOMType=rs1.getString("DOMType");
	
}
rs1.close();
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="xpath_edit_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
      <input type=hidden name="XpathID" value="<%=XpathID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Edit xpath</b></span></td>
      </tr>      
     
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Parent XPATH: </td><td>     
	  <SELECT name=ParentName size=1 class="tb">
          	<OPTION value="-1">No</OPTION>
          <%
          String ListParentName="";
          sql="select XPATHName from XpathList where ProjectID='"+ProjectID+"' and XpathPath  not like '"+XpathPath+"%'";
          ResultSet rs=workM.getSQL(sql);
          while(rs.next()){
          	ListParentName=rs.getString("XPATHName");
          	if(ListParentName.compareTo(XPATHName)!=0){
          	%>
		<OPTION value="<%=ListParentName%>" <%if(ListParentName.compareTo(ParentName)==0){out.print(" selected ");}%>><%=ListParentName%></OPTION>
	  	<%
	  	}
	  }
	  rs.close();
	  workM.close();
	  %>
	  </SELECT>
	  <!--input onkeydown=Frm_Down(); name="ParentSeq" class="tb" style="WIDTH: 343px"   value=""-->
	  ParentSeq: <input onkeydown=Frm_Down(); name="ParentSeq" class="tb" style="WIDTH: 143px"  onFocus=select() value="<%=ParentSeq%>">(Sample: 1,3,5)
	</td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
       <td height="25" align=right>Node's LevelID: </td><td> 
          <SELECT name=NodeLevelID size=1 class="tb">
          	<OPTION value="-1" <% if(NodeLevelID.compareTo("-1")==0){%> selected <%}%>>No</OPTION>         
		<OPTION value="1" <% if(NodeLevelID.compareTo("1")==0){%> selected <%}%>>1</OPTION>
		<OPTION value="2" <% if(NodeLevelID.compareTo("2")==0){%> selected <%}%>>2</OPTION>
		<OPTION value="3" <% if(NodeLevelID.compareTo("3")==0){%> selected <%}%>>3</OPTION>
		<OPTION value="4" <% if(NodeLevelID.compareTo("4")==0){%> selected <%}%>>4</OPTION>
		<OPTION value="5" <% if(NodeLevelID.compareTo("5")==0){%> selected <%}%>>5</OPTION>
		<OPTION value="6" <% if(NodeLevelID.compareTo("6")==0){%> selected <%}%>>6</OPTION>
		<OPTION value="7" <% if(NodeLevelID.compareTo("7")==0){%> selected <%}%>>7</OPTION> 
		<OPTION value="8" <% if(NodeLevelID.compareTo("8")==0){%> selected <%}%>>8</OPTION>
		<OPTION value="9" <% if(NodeLevelID.compareTo("9")==0){%> selected <%}%>>9</OPTION>
	  </SELECT>	
	</td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Tag: </td><td> 
          <input type="radio" name="tagName" value="-1" <% if(tagName.compareTo("-1")==0){%> checked="true" <%}%>>No 
          <input type="radio" name="tagName" value="a" <% if(tagName.compareTo("a")==0){%> checked="true" <%}%>>a 
          <input type="radio" name="tagName" value="input" <% if(tagName.compareTo("input")==0){%> checked="true" <%}%>>input 
          <input type="radio" name="tagName" value="button" <% if(tagName.compareTo("button")==0){%> checked="true" <%}%>>button 
          <input type="radio" name="tagName" value="span" <% if(tagName.compareTo("span")==0){%> checked="true" <%}%>>span 
          <input type="radio" name="tagName" value="div" <% if(tagName.compareTo("div")==0){%> checked="true" <%}%>>div 
          <input type="radio" name="tagName" value="img" <% if(tagName.compareTo("img")==0){%> checked="true" <%}%>>img 
          <input type="radio" name="tagName" value="option" <% if(tagName.compareTo("option")==0){%> checked="true" <%}%>>option 
          <input type="radio" name="tagName" value="td" <% if(tagName.compareTo("td")==0){%> checked="true" <%}%>>td 
          <input type="radio" name="tagName" value="em" <% if(tagName.compareTo("em")==0){%> checked="true" <%}%>>em 
          <input type="radio" name="tagName" value="h4" <% if(tagName.compareTo("h4")==0){%> checked="true" <%}%>>h4
        </td>
      </tr>      
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Attribute: </td><td> 
          <input type="radio" name="Attribute" value="-1"  <% if(Attribute.compareTo("-1")==0){%> checked="true" <%}%>>No
          <input type="radio" name="Attribute" value="nodeId" <% if(Attribute.compareTo("nodeId")==0){%> checked="true" <%}%>>id
          <input type="radio" name="Attribute" value="nodeHref" <% if(Attribute.compareTo("nodeHref")==0){%> checked="true" <%}%>>href
          <input type="radio" name="Attribute" value="nodeText" <% if(Attribute.compareTo("nodeText")==0){%> checked="true" <%}%>>text
          <input type="radio" name="Attribute" value="nodeTitle" <% if(Attribute.compareTo("nodeTitle")==0){%> checked="true" <%}%>>title
          <input type="radio" name="Attribute" value="nodeClass" <% if(Attribute.compareTo("nodeClass")==0){%> checked="true" <%}%>>class
          <input type="radio" name="Attribute" value="nodeName" <% if(Attribute.compareTo("nodeName")==0){%> checked="true" <%}%>>name
          <input type="radio" name="Attribute" value="nodeValue" <% if(Attribute.compareTo("nodeValue")==0){%> checked="true" <%}%>>value
          <input type="radio" name="Attribute" value="nodeType" <% if(Attribute.compareTo("nodeType")==0){%> checked="true" <%}%>>type
          <input type="radio" name="Attribute" value="onclick" <% if(Attribute.compareTo("onclick")==0){%> checked="true" <%}%>>onclick
          <input type="radio" name="Attribute" value="onkeydown" <% if(Attribute.compareTo("onkeydown")==0){%> checked="true" <%}%>>onkeydown
        </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Condition: </td><td> 
          <input type="radio" name="AttributeCondition" value="contains" <% if(AttributeCondition.compareTo("equals")!=0){%> checked="true" <%}%>>contains
          <input type="radio" name="AttributeCondition" value="equals" <% if(AttributeCondition.compareTo("equals")==0){%> checked="true" <%}%>>equals
        </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Attribute value: </td><td> 
          <input onkeydown=Frm_Down(); name="AttributeValue" class="tb" style="WIDTH: 343px"  onFocus=select()  value="<%=AttributeValue%>">
         </td>
      </tr>
      

      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Name:</td><td> 
          <input onkeydown=Frm_Down(); name="XPATHName" class="tb" style="WIDTH: 356px"  value="<%=XPATHName%>" onkeyup="showHintXPATHName(this.value)">(*)&nbsp;&nbsp;<span id="txtHintXPATHName"></span>
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right valign="top">XPATH: </td><td> 
          <textarea rows="8" name="XPATH" onFocus=select()  style="WIDTH: 856px"><%=XPATH%></textarea></td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right valign="top">XPATH Seq: </td><td>           
          <input onkeydown=Frm_Down(); name="seq" class="tb" style="WIDTH: 143px"  onFocus=select() value="<%=seq%>">(Sample: 1,2,5)
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Container:</td><td> 
          <input onkeydown=Frm_Down(); name="PageContainer" class="tb" style="WIDTH: 656px"  value="<%=PageContainer%>">          
          </td>
      </tr>
     <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>DOMType: </td><td>           
          <input type="radio" name="DOMType" value="-1" <% if(DOMType.indexOf("-1")!=-1){%> checked="true" <%}%>>No
          <input type="radio" name="DOMType" value="popup" <% if(DOMType.indexOf("popup")!=-1){%> checked="true" <%}%>>popup
          <input type="radio" name="DOMType" value="newtab" <% if(DOMType.indexOf("newtab")!=-1){%> checked="true" <%}%>>newtab
          <input type="radio" name="DOMType" value="dropdown" <% if(DOMType.indexOf("dropdown")!=-1){%> checked="true" <%}%>>dropdown
          <input type="radio" name="DOMType" value="doubleclick" <% if(DOMType.indexOf("doubleclick")!=-1){%> checked="true" <%}%>>double click
          <input type="radio" name="DOMType" value="mouseright" <% if(DOMType.indexOf("mouseright")!=-1){%> checked="true" <%}%>>mouse right
          <input type="radio" name="DOMType" value="mouseover" <% if(DOMType.indexOf("mouseover")!=-1){%> checked="true" <%}%>>mouse over
          <input type="radio" name="DOMType" value="tips" <% if(DOMType.indexOf("tips")!=-1){%> checked="true" <%}%>>tips
          <input type="radio" name="DOMType" value="frame" <% if(DOMType.indexOf("frame")!=-1){%> checked="true" <%}%>>frame
          <input type="radio" name="DOMType" value="iframe" <% if(DOMType.indexOf("iframe")!=-1){%> checked="true" <%}%>>iframe
      	</td>
      </tr>
     <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Child leaf: </td><td> 
          <input type="radio" name="leaf" value="no" <% if(leaf.compareTo("0")==0){%> checked="true" <%}%>>No 
          <input type="radio" name="leaf" value="yes" <% if(leaf.compareTo("1")==0){%> checked="true" <%}%>>Yes 
        </td>
      </tr>	
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Save  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="xpath_del.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>" onclick="return del()"><IMG alt='Delete'  border=0 name=22.1.1.6.3 src="images/delete.gif"></a>
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
