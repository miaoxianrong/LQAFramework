function winopen(url,winname)
{
 window.open(url, winname, 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no maximum=yes')
 return false;
}
Pic_Flag=0;
function SelectResult()
{
	  if(Pic_Flag==0){
		makeCheck(Maintain);
		document.images.XuanZe1.src='images/icon_2_2.gif';
		document.images.XuanZe1.alt='全不选';
		 Pic_Flag=1;
	  }else{
		makeUncheck(Maintain);
		document.images.XuanZe1.src='images/icon_2_1.gif';
		document.images.XuanZe1.alt='全选';
		Pic_Flag=0;
	  }
}
function makeCheck(thisForm)
{
if(!thisForm.now_dn.length){
 thisForm.now_dn.checked=true
}else{
for (i = 0; i < thisForm.now_dn.length; i++)
 {
 thisForm.now_dn[i].checked=true
 }
}
}
function makeUncheck(thisForm)
{
if(!thisForm.now_dn.length){
 thisForm.now_dn.checked=false
}else{
for (i = 0; i < thisForm.now_dn.length; i++)
 {
 thisForm.now_dn[i].checked=false
 }
}
}
function OnSubmit(thisForm)
{
	txt="";
	if (!thisForm.now_dn.length)
	{
		txt=txt + thisForm.now_dn.value + ","
	}
	else
	{
		for (i = 0; i <thisForm.now_dn.length; i++)
		{
			if (thisForm.now_dn[i].checked==true)
			{
				txt=txt + thisForm.now_dn[i].value + ","
			}
		}	
	}
	thisForm.name_array.value=txt;
	txt="";
}

function Judge(thisForm)
{
	no=0;
	if (thisForm.now_dn==null)
	{
		 no=0;
	}
	else if (!thisForm.now_dn.length)
	{
		 no++;
	}
	else
	{
		for (i = 0; i <thisForm.now_dn.length; i++)
		{
			if(thisForm.now_dn[i].checked==true)
			{
				no=no+1;
			}
		}
		//alert (no);
	}
	return no;
}
function PostDo(fileName1)
{
	window.alert("test");
	this.form.action=fileName1;
	this.form.onsubmit();
}
function Do_Layer0(choice,thisForm)
{
	
	var A0 = eval(thisForm);
	if (thisForm=='Layer0')
	{
		
		if	(choice=='visible')  
		{ 
			
			if (navigator.userAgent.indexOf('MSIE') > -1) {		
				A0.style.visibility='visible';		
			}
			else{	
				document.A0.visibility='visible';		
			}				
		}
		if(choice=='hidden')
		{
			if (navigator.userAgent.indexOf('MSIE') > -1) 
				Layer0.style.visibility='hidden';
			else
				document.Layer0.visibility='hidden';
		}
	}
}

function showdir(thisForm,choice) 
{ 
	var A2 = eval(thisForm);
	if	(choice=='visible')  
	{
		if (navigator.userAgent.indexOf('MSIE') > -1) 
		{
			A2.style.left=window.event.x+10;
			A2.style.top=window.event.y+10;
			A2.style.visibility='visible';
		}
		else
		{
			document.A2.style.left=window.event.x;
			document.A2.style.top=window.event.y;
			document.A2.visibility='visible';
		}
	}
	if(choice=='hidden')
	{
		if (navigator.userAgent.indexOf('MSIE') > -1) 
			A2.style.visibility='hidden';
		else
			document.A2.visibility='hidden';
	}
}
function Do_Layer(choice,thisForm)
{
	var A2 = eval(thisForm);
	if	(choice=='visible')  
	{
		if (navigator.userAgent.indexOf('MSIE') > -1) 
		{
			A2.style.left=document.body.scrollLeft+document.body.clientWidth/2-A2.offsetWidth/2;
			A2.style.top=document.body.scrollTop+45;
			A2.style.visibility='visible';
		}
		else
		{
			document.A2.style.left=document.body.scrollLeft+document.body.clientWidth/2-document.A2.offsetWidth/2;
			document.A2.style.top=document.body.scrollTop+45;
			document.A2.visibility='visible';
		}
	}
	if	(choice=='hidden')
	{
		if (navigator.userAgent.indexOf('MSIE') > -1) 
			A2.style.visibility='hidden';
		else
			document.A2.visibility='hidden';
	}
}
