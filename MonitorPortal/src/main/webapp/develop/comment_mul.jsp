<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<%
String ProjectID=request.getParameter("ProjectID");
//88774647,13522713469 
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>Add mulitiple comments</title>
<script type="text/javascript">
function showHintexcluderName(str)
{
var xmlhttp;
if (str.length==0)
  { 
  document.getElementById("txtHintexcluderName").innerHTML="";
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
    document.getElementById("txtHintexcluderName").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","gethint_excluderName.jsp?ProjectID=<%=ProjectID%>&Tezhengzhi="+str,true);
xmlhttp.send();
}
</script>
<script language="javascript">
<!--


function validata()
{
    var errfound = false;
   
   if( document.kh.excluderName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.excluderName.focus();
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
String KeyID="";
String FileName="";
String Content="";
String UnitID="";
String InScope="";
String CommentByDev="";
String PreAnalysis=request.getParameter("PreAnalysis");
if(PreAnalysis==null&&PreAnalysis.compareTo("-1")!=0){
	PreAnalysis="0";
}
String mapped=request.getParameter("mapped");
if(mapped==null||mapped.compareTo("null")==0){
	mapped="-1";
}
String ReasonID=request.getParameter("ReasonID");
if(ReasonID==null||ReasonID.compareTo("null")==0){
	ReasonID="-1";
}
String NewFuzzyAll=request.getParameter("NewFuzzyAll");
if(NewFuzzyAll==null||NewFuzzyAll.compareTo("null")==0){
	NewFuzzyAll="-1";
}
String name_array=request.getParameter("name_array");
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="comment_mul_do.jsp">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
      <input type=hidden name="NewFuzzyAll" value="<%=NewFuzzyAll%>">
      <input type=hidden name="ReasonID" value="<%=ReasonID%>">
      <input type=hidden name="mapped" value="<%=mapped%>">
      <input type=hidden name="PreAnalysis" value="<%=PreAnalysis%>">      
      <input type=hidden name="name_array" value="<%=name_array%>"> 
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Comment by Developer</b></span></td>
      </tr>      
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Select info:</td><td> 
            <b>No.</b> = <%=name_array%>
	</td>
      </tr>
       <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>In scope:</td><td>           
          <input type="radio" name="InScope" value="0" checked>Yes
          <input type="radio" name="InScope" value="1">No
	</td>
      </tr>
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right vAlign=top>Comment/Steps:<BR>(<250 char)</td><td> 
          <textarea rows="8" name="CommentByDev" onFocus=select()  style="WIDTH: 856px"></textarea>
         </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value="  Save/Update  ">&nbsp;&nbsp;&nbsp;&nbsp;<input name="button" type=button onclick="history.back(-1);" value=" Back ">
          </div></td>
      </tr>
      </form>
      
</table>
</td>
</tr>
</table>
</body>
</html>

		
		