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

<title>New xpath</title>
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
workM.getConnection();

%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="xpath_add_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Create new xpath</b></span></td>
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
	  ParentSeq: <input onkeydown=Frm_Down(); name="ParentSeq" class="tb" style="WIDTH: 143px"   onFocus=select() value="">(Sample: 1,3,5)
	</td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
       <td height="25" align=right>Node's LevelID: </td><td> 
          <SELECT name=NodeLevelID size=1 class="tb">
          	<OPTION value="-1">No</OPTION>         
		<OPTION value="1">1</OPTION>
		<OPTION value="2">2</OPTION>
		<OPTION value="3">3</OPTION>
		<OPTION value="4">4</OPTION>
		<OPTION value="5">5</OPTION>
		<OPTION value="6">6</OPTION>
		<OPTION value="7">7</OPTION> 
		<OPTION value="8">8</OPTION>
		<OPTION value="9">9</OPTION>
	  </SELECT>	  
	</td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Parent Tag: </td><td> 
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
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Parent Attribute: </td><td> 
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
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Condition: </td><td> 
          <input type="radio" name="AttributeCondition" value="contains" checked="true">contains
          <input type="radio" name="AttributeCondition" value="equals">equals
        </td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Attribute value: </td><td> 
          <input onkeydown=Frm_Down(); name="AttributeValue" class="tb" style="WIDTH: 343px"  onFocus=select()  value="">
         </td>
      </tr>
      

      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Name:</td><td> 
          <input onkeydown=Frm_Down(); name="XPATHName" class="tb" style="WIDTH: 356px"  value="" onkeyup="showHintXPATHName(this.value)">(*)&nbsp;&nbsp;<span id="txtHintXPATHName"></span>
          
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right valign="top">XPATH: </td><td> 
          <textarea rows="8" name="XPATH" onFocus=select()  style="WIDTH: 856px"></textarea>
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right valign="top">XPATH Seq: </td><td>           
          <input onkeydown=Frm_Down(); name="seq" class="tb" style="WIDTH: 143px"  onFocus=select() value="">(Sample: 1,2,5)
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Container:</td><td> 
          <input onkeydown=Frm_Down(); name="PageContainer" class="tb" style="WIDTH: 656px"  value="">          
          </td>
      </tr>
      <tr bgcolor="#F8CDFF"> 
        <td height="25" align=right>TagOrURL: </td><td> 
          <input type="radio" name="TagOrURL" value="TagOrURL" checked="true">Default
          <input type="radio" name="TagOrURL" value="Tag">Tag
          <input type="radio" name="TagOrURL" value="URL">URL
      	</td>
      </tr>   
      <tr bgcolor="#F8CDFF"> 
        <td height="25" align=right>DOMType: </td><td> 
          <input type="radio" name="DOMType" value="-1" checked="true">No
          <input type="radio" name="DOMType" value="popup">popup
          <input type="radio" name="DOMType" value="newtab">newtab
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
        <td height="25" align=right>Leaf(Child): </td><td> 
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
