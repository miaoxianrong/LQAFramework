

function validata(pagecount)
{
	var errfound = false;
	
	if( !checkvalue( document.jump.pageno.value,pagecount ))
	    document.jump.pageno.focus();
	else
	    errfound=true;
	    
	return errfound;
}

function checkvalue(theValue,pagecount)                  
{                  
	if (theValue=="")                  
	{                  
		alert("����\n\n��������ȷ���֣�");                  
		return false;                  
	}		                  
	else if (theValue.indexOf(" ")>=0)                  
	{                  
		alert("����\n\n��������ȷ���֣�\n������ո�");                  
		return false;                  
	}                  
	else if (isNaN(theValue))                   
	{	                  
		alert("����\n\n��������ȷ���֣�");                  
		return false;                  
	}                  
	else if (theValue.indexOf('.')>=0)                  
	{                  
		alert("����\n\n������������")                  
		return false;                  
	}                  
	else if (parseInt(theValue)<=0)                  
	{                  
		alert("����\n\n��������ȷ���֣�");                  
		return false;                  
	}                  
	else if(theValue>pagecount)
	{
	    alert("����!\n\n����ҳ�뷶Χ!");
	    return false;
	}
	//document.jgwh.submit();
	return true;                  
}                  

function keyDown(zhs){

  var keycode=event.keyCode	 
  var realkey=String.fromCharCode(event.keyCode)
  var maxh = eval(zhs)-1;

  //alert("keycode:"+keycode+"\nrealkey:"+realkey); 
if( zhs > 0 )
{  
  if (keycode==13 && document.form1.KEY_NO.value!="")
  {
		 if(document.form1.xzfs.value=="2")
		 	document.all['Row'+document.form1.KEY_NO.value].ondblclick();
		 else
		 	document.all['Row'+document.form1.KEY_NO.value].click();
  }   

  //40='�¼�ͷ'
  if(keycode==40){

	if(document.form1.KEY_NO.value=="")
	{
	  Row0.style.backgroundColor='#CCFFCC';
    document.form1.KEY_NO.value="0";
	}
	else if(eval(document.form1.KEY_NO.value)==maxh)
	{
    document.all['Row'+maxh].style.backgroundColor='#F8FDFF';
	  Row0.style.backgroundColor='#CCFFCC';
    document.form1.KEY_NO.value="0";
	}
	else
	{
    document.all['Row'+document.form1.KEY_NO.value].style.backgroundColor='#F8FDFF';
    document.form1.KEY_NO.value=parseFloat(document.form1.KEY_NO.value)+1;
    document.all['Row'+document.form1.KEY_NO.value].style.backgroundColor='#CCFFCC';
	}
  }

  //�ϼ�ͷ
  if(keycode==38){

	if(document.form1.KEY_NO.value=="")
	{
    document.all['Row'+maxh].style.backgroundColor='#CCFFCC';
    document.form1.KEY_NO.value=maxh;
	}
	else if(document.form1.KEY_NO.value=="0")
	{
	  Row0.style.backgroundColor='#F8FDFF';
    document.all['Row'+maxh].style.backgroundColor='#CCFFCC';
    document.form1.KEY_NO.value=maxh;
	}
	else
	{
    document.all['Row'+document.form1.KEY_NO.value].style.backgroundColor='#F8FDFF';
    document.form1.KEY_NO.value=parseFloat(document.form1.KEY_NO.value)-1;
    document.all['Row'+document.form1.KEY_NO.value].style.backgroundColor='#CCFFCC';
	}
	
  }
}
}