<html>
<head>
<LINK href="img/style.css" rel=stylesheet type=text/css>
<title>Workbench</title>
<script language="javascript">
<!--
function checkvalue(theValue)                  
{                  
	if (theValue.indexOf(" ")>=0)                  
	{                  
		alert("Error: \n\n Remove space, please");                  
		return false;                  
	}                  
	else if (isNaN(theValue))                   
	{	                  
		alert("Error:\n\n Please input digital number");                  
		return false;                  
	}                  
	else if (theValue.indexOf('.')>=0)                  
	{                  
		alert("Error: \n\n Please input integer")                  
		return false;                  
	}                  
	else if (parseInt(theValue)<0)                  
	{                  
		alert("Error: \n\n Please input >=0 integer");                  
		return false;                  
	}                  
	return true;                  
}
function validata()
{
   var errfound = false;
   
   if( document.kh.PID.value == '' )
   {
       window.alert("Please input project ID");
       document.kh.PID.focus();
   }
   else if(!checkvalue(document.kh.PID.value))
   {       
       document.kh.PID.focus();
   }
   else if(document.kh.PNAME.value == '')
   {       
       window.alert("Please input project name");
       document.kh.PNAME.focus();
   }  
   else if(document.kh.UserName.value == '')
   {       
       window.alert("Please input user name");
       document.kh.UserName.focus();
   } 
   else if(document.kh.Password.value == '')
   {       
       window.alert("Please input password");
       document.kh.Password.focus();
   }
   else if(document.kh.RePassword.value == '')
   {       
       window.alert("Please input confirm password");
       document.kh.RePassword.focus();
   }
   else if(document.kh.Password.value!= document.kh.RePassword.value)
   {
       window.alert("Password mismatch");
       document.kh.RePassword.focus();
   }
   else if(document.kh.URL.value == '')
   {       
       window.alert("Please input URL");
       document.kh.URL.focus();
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

<table width=500 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="Project_Run.jsp" name="kh" onsubmit="return validata();">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><b>Project Information</b></span></td>
      </tr>      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Project ID:</td><td> 
          <input onkeydown=Frm_Down(); name=PID class="tb" style="WIDTH: 156px"   onFocus=select()  value="12951"></td>
      </tr>   
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Project Name:</td><td> 
          <input onkeydown=Frm_Down(); name=PNAME class="tb" style="WIDTH: 156px"   onFocus=select()  value="ARCServe RHA"></td>
      </tr>   
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>User Name:</td><td> 
          <input onkeydown=Frm_Down(); name=UserName class="tb" style="WIDTH: 143px"  onFocus=select()  value="administrator"></td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Password:</td><td> 
          <input onkeydown=Frm_Down(); type=password name=Password class="tb" style="WIDTH: 143px"  onFocus=select()  value="12345"></td>
      </tr>
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Confirm password:</td><td> 
          <input name=RePassword type=password  class="tb" style="WIDTH: 156px"  onFocus=select()  onkeydown=Frm_Down(); value="12345">
      	</td>
      </tr>      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>URL:</td><td> 
          <input name=URL class="tb" style="WIDTH: 356px"  onFocus=select()  onkeydown=Frm_Down(); value="http://155.35.75.191/clarity">
      	</td>
      </tr>      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" colspan=2 align=center>
        <input name="submit" type=submit value=" Submit ">
           
          </div></td>
      </tr>
      </form>
</table>

</td>
</tr>
</table>



</body>
</html>
