<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
if(XpathID==null){
	XpathID="";
}
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>New</title>
<script type="text/javascript">
function showHintdomtypeName(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintdomtypeName").innerHTML="";
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
    document.getElementById("txtHintdomtypeName").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_domtypeName.jsp?ProjectID=<%=ProjectID%>&Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--


function validata()
{
   var errfound = false;
   
   if( document.kh.domtypeName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.domtypeName.focus();
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
workM.getConnection();

%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="domtype_add_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Create new dom type</b></span></td>
      </tr>     
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Parent XPATH: </td><td> 
          <SELECT name=ParentName size=1 class="tb">
          	<OPTION value="-1">No</OPTION>
          <%
          String ParentName="";String ParentXpathID="";
          String sql="select XpathID,XPATHName from XpathList where ProjectID='"+ProjectID+"'";
          ResultSet rs=workM.getSQL(sql);
          while(rs.next()){
          	ParentXpathID=rs.getString("XpathID");
          	ParentName=rs.getString("XPATHName");
          %>
		<OPTION value="<%=ParentName%>" <%if(ParentXpathID.compareTo(XpathID)==0){out.print(" selected ");}%>><%=ParentName%></OPTION>
	  <%
	  }
	  rs.close();
	  workM.close();
	  %>
	  </SELECT>	  
	  <input type=hidden name="ParentSeq" value="">
	</td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>My tag: </td><td> 
          <input type="radio" name="tagName" value="-1" checked="true">No 
          <input type="radio" name="tagName" value="a">a 
          <input type="radio" name="tagName" value="input">input 
          <input type="radio" name="tagName" value="button">button 
          <input type="radio" name="tagName" value="span">span 
          <input type="radio" name="tagName" value="div">div 
          <input type="radio" name="tagName" value="img">img 
          <input type="radio" name="tagName" value="option">option 
          <input type="radio" name="tagName" value="td">td 
          <input type="radio" name="tagName" value="em">em 
          <input type="radio" name="tagName" value="h4">h4
        </td>
      </tr>      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>My attribute: </td><td> 
          <input type="radio" name="Attribute" value="-1" checked="true">No
          <input type="radio" name="Attribute" value="nodeId">id
          <input type="radio" name="Attribute" value="nodeHref">href
          <input type="radio" name="Attribute" value="nodeText">text
          <input type="radio" name="Attribute" value="nodeTitle">title
          <input type="radio" name="Attribute" value="nodeClass">class
          <input type="radio" name="Attribute" value="nodeName">name
          <input type="radio" name="Attribute" value="nodeValue">value
          <input type="radio" name="Attribute" value="nodeType">type
          <input type="radio" name="Attribute" value="onclick">onclick
          <input type="radio" name="Attribute" value="onkeydown">onkeydown
        </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>AttributeCondition: </td><td> 
          <input type="radio" name="AttributeCondition" value="contains" checked="true">contains
          <input type="radio" name="AttributeCondition" value="equals">equals
        </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>My attribute value: </td><td> 
          <input onkeydown=Frm_Down(); name="AttributeValue" class="tb" style="WIDTH: 343px"  onFocus=select()  value="">
         </td>
      </tr>
      

      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>My name:</td><td> 
          <input onkeydown=Frm_Down(); name="domtypeName" class="tb" style="WIDTH: 156px"  value="" onkeyup="showHintdomtypeName(this.value)">(*)&nbsp;&nbsp;<span id="txtHintdomtypeName"></span>
          </td>
      </tr>
     <tr bgcolor="#F8CDFF"> 
        <td height="25" align=right>DOMType: </td><td> 
          <input type="radio" name="DOMType" value="popup" checked="true">popup
          <input type="radio" name="DOMType" value="dropdown">dropdown
          <input type="radio" name="DOMType" value="doubleclick">double click
          <input type="radio" name="DOMType" value="mouseright">mouse right
          <input type="radio" name="DOMType" value="mouseover">mouse over
          <input type="radio" name="DOMType" value="tips">tips
          <input type="radio" name="DOMType" value="frame">frame
          <input type="radio" name="DOMType" value="iframe">iframe
      	</td>
      </tr>
     <tr bgcolor="#F8CDFF"> 
        <td height="25" align=right>Leaf: </td><td> 
          <input type="radio" name="leaf" value="no" checked="true">No 
          <input type="radio" name="leaf" value="yes">Yes 
        </td>
      </tr>	
      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Save  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
