<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
String includerID=request.getParameter("includerID");
if(includerID==null){
	includerID="";
}
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Edit</title>
<script type="text/javascript">
function showHintincluderName(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintincluderName").innerHTML="";
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
    document.getElementById("txtHintincluderName").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_includerName.jsp?ProjectID=<%=ProjectID%>&Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--


function validata()
{
    var errfound = false;
   
   if( document.kh.includerName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.includerName.focus();
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
String includerName="";
String tagName="";
String Attribute="";
String AttributeCondition="";
String AttributeValue="";
String ParentName="";
String ParentSeq="";
String sql="select * from includerList where includerID="+includerID;
ResultSet rs1=workM.getSQL(sql);
if(rs1.next()){
	ParentName=rs1.getString("ParentName");	
	ParentSeq=rs1.getString("ParentSeq");
	tagName=rs1.getString("tagName");
	Attribute=rs1.getString("Attribute");
	AttributeCondition=rs1.getString("AttributeCondition");
	AttributeValue=rs1.getString("AttributeValue");		
	includerName=rs1.getString("includerName");	
}
rs1.close();
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="includer_edit_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
      <input type=hidden name="includerID" value="<%=includerID%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Edit Includer</b></span></td>
      </tr>      
     
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Parent XPATH: </td><td> 
          <SELECT name=ParentName size=1 class="tb">
          	<OPTION value="-1">No</OPTION>
          <%
          String ListParentName="";
          sql="select XPATHName from XpathList where ProjectID='"+ProjectID+"'";
          ResultSet rs=workM.getSQL(sql);
          while(rs.next()){
          	ListParentName=rs.getString("XPATHName");
          %>
		<OPTION value="<%=ListParentName%>" <%if(ListParentName.compareTo(ParentName)==0){out.print(" selected ");}%>><%=ListParentName%></OPTION>
	  <%
	  }
	  rs.close();
	  workM.close();
	  %>
	  </SELECT><input type=hidden name="ParentSeq" value="">
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
        <td height="25" align=right>AttributeCondition: </td><td> 
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
          <input onkeydown=Frm_Down(); name="includerName" class="tb" style="WIDTH: 156px"  value="<%=includerName%>" onkeyup="showHintincluderName(this.value)">(*)&nbsp;&nbsp;<span id="txtHintincluderName"></span>
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
