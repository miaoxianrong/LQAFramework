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
function checkvalue(theValue)                  
{                  
	if (theValue.indexOf(" ")>=0)                  
	{                  
		alert(theValue+" 非正整数！");                   
		return false;                  
	}                  
	else if (isNaN(theValue))                   
	{	                  
		alert(theValue+" 非正整数！");                      
		return false;                  
	}                  
	else if (theValue.indexOf('.')>=0)                  
	{                  
		alert(theValue+" 非正整数！");                    
		return false;                  
	}                  
	else if (parseInt(theValue)<0)                  
	{                  
		alert(theValue+" 非正整数！");                   
		return false;                  
	}                  
	return true;                  
}
function OnSubmit(thisForm)
{
	txt="";
	tmpi_shuliang="";
	tmpi_jiage="";
	tmpShuilv="";
	temp_KuBH="";
	if (!thisForm.now_dn.length)
	{
		txt=txt + thisForm.now_dn.value + ","
		tmpi_shuliang=tmpi_shuliang + thisForm.i_shuliang.value + ","
		tmpi_jiage=tmpi_jiage + thisForm.i_jiage.value + ","
		tmpShuilv=tmpShuilv + thisForm.Shuilv.value + ","
		temp_KuBH=temp_KuBH + thisForm.KuBH.value + ","
	}
	else
	{
		k=0;
		for (i = 0; i <thisForm.now_dn.length; i++)
		{
			
			if (thisForm.now_dn[i].checked==true)
			{
				k++;
				txt=txt + thisForm.now_dn[i].value + ","
				if(checkvalue(thisForm.i_shuliang[i].value)){
					tmpi_shuliang=tmpi_shuliang + thisForm.i_shuliang[i].value + ","	
				}
				else{
					tmpi_shuliang=tmpi_shuliang + "1" + ","	
				}
				
				if(checkvalue(thisForm.i_jiage[i].value)){
					tmpi_jiage=tmpi_jiage + thisForm.i_jiage[i].value + ","	
				}
				else{
					tmpi_jiage=tmpi_jiage + "100" + ","	
				}
				
				if(checkvalue(thisForm.Shuilv[i].value)){
					tmpShuilv=tmpShuilv + thisForm.Shuilv[i].value + ","
					temp_KuBH=temp_KuBH + thisForm.KuBH[i].value + ","
				}
				else{
					tmpShuilv=tmpShuilv + "1" + ","
					temp_KuBH=temp_KuBH + "1" + ","		
				}
			}
			
		}	
		thisForm.selected_count.value=k;
	}
	thisForm.name_array.value=txt;
	thisForm.shuliang_array.value=tmpi_shuliang;
	thisForm.jiage_array .value=tmpi_jiage
	thisForm.shuilv_array.value=tmpShuilv;
	thisForm.kubh_array.value=temp_KuBH;
	thisForm.all_count.value=thisForm.now_dn.length;
	txt="";
	tmpi_shuliang="";
	tmpi_jiage="";
	tmpShuilv="";
	temp_KuBH="";
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
