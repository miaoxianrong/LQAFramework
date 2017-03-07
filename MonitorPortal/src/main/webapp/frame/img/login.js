function getOs() 
{ 
   var OsObject = ""; 
   if(navigator.userAgent.indexOf("MSIE")>0) { 
        return "MSIE"; 
   } 
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
        return "Firefox"; 
   } 
   if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
        return "Safari"; 
   }  
   if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
        return "Camino"; 
   } 
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){ 
        return "Gecko"; 
   } 
   
}

if(getOs()!='MSIE')
{	
	var inputs = document.getElementsByTagName("input"); 
	for (i = 0; i < inputs.length; i++) 
	{ 
		inputs[i].onkeydown = function(e){ 
		if (e.keyCode == 13) { 
		var input = getInputByTab(parseInt(this.getAttribute("tab")) + 1); 
			if (input) { 
				input.focus(); 
				return false; 
			} 
		} 
		} 
	}
}

function getInputByTab(t) 
{ 
	for (i =0; i < inputs.length; i++) { 
		if (inputs[i].getAttribute("tab") == t) 
		return inputs[i]; 
	} 
	return false; 
} 

function FrmD(){
  if (event.keyCode==13)
  {
		if(getOs()=='MSIE')	event.keyCode=9;
  }
}

function re_input(){

  document.login.userid.value="";
  document.login.password.value="";
}

function validata()
{
	var founderr = false;
	if(document.login.userid.value=='')
	{
		alert("请输入用户名!");	
		document.login.userid.focus();
	}
	else if(document.login.password.value=='')
	{
		alert("请输入密码!");
		document.login.password.focus();
	}
	else
	{
		founderr = true;
		tmp = document.login.mmsj.value;
		password = tmp + hex_md5(document.login.password.value);
		document.login.password.value=hex_md5(password);
	}
	
	return founderr;
}

document.login.userid.focus();

if(document.login.op.value=='zdxz')
	alert('已达到本版本站点限制数！不能登陆！');
else if(document.login.login.value=='1')
	alert("用户名或密码不正确!");