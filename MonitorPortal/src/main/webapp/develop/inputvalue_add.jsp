<%
String ProjectID=request.getParameter("ProjectID");
String NodePath=request.getParameter("NodePath");
if(NodePath==null){
	NodePath="";
}
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<title>New</title>
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
   
   if( document.kh.InputName.value == '' )
   {
       window.alert("Please input name!");
       document.kh.InputName.focus();
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

<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="inputvalue_add_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
      <input type=hidden name="NodePath" value="<%=NodePath%>">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Create new input value</b></span></td>
      </tr>           
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right width=30%>NodePath: </td><td>         
	  <%=NodePath%>
	</td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Input Field Title:</td><td> 
          <input onkeydown=Frm_Down(); name="InputTitle" class="tb" style="WIDTH: 556px"  value=""></span>
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Input Field Property:</td><td> 
          <input onkeydown=Frm_Down(); name="InputProperty" class="tb" style="WIDTH: 156px"  value="name">(Sample: name, MXX: ng-model)</span>
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Input Field Name:</td><td> 
          <input onkeydown=Frm_Down(); name="InputName" class="tb" style="WIDTH: 356px"  value="">(Sample: subject.Name, MXX: Number)</span>
          </td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Input Field Value:</td><td> 
          <input onkeydown=Frm_Down(); name="InputValue" class="tb" style="WIDTH: 356px"  value="">(Sample: TESTName)
          </td>
      </tr>    
       <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Unique value:</td><td> 
          <input type="radio" name="InputRandom" value="0" checked>No
          <input type="radio" name="InputRandom" value="1">Yes 
          (Unique string will be appended after input value)
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
