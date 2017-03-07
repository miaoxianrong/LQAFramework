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

function checkDate(theDate)
{
  var reg = /^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;  
  var result=true;
  if(!reg.test(theDate))
    result = false;
  else{
    var arr_hd=theDate.split("-");
    var dateTmp;
    dateTmp= new Date(arr_hd[0],parseFloat(arr_hd[1])-1,parseFloat(arr_hd[2]));
    if(dateTmp.getFullYear()!=parseFloat(arr_hd[0])
       || dateTmp.getMonth()!=parseFloat(arr_hd[1]) -1 
        || dateTmp.getDate()!=parseFloat(arr_hd[2])){
        result = false
    }
  }
  return result;
}
function getPos(obj)
{
	if(getOs()=='MSIE')
	{
 obj.focus();
 var currentRange=document.selection.createRange();
 var workRange=currentRange.duplicate();
 obj.select();
 var allRange=document.selection.createRange();

 var len=0;
 while(workRange.compareEndPoints("StartToStart",allRange)>0)
 {
  workRange.moveStart("character",-1);
  len++;
 }

 currentRange.select()
 zyjbz = danju.zyjpzbz.value;

 if (len >= obj.value.length)
  if( zyjbz=="0" && obj.value.length>0)
  {
  	danju.zyjpzbz.value="1";
  }
  else
  {
  	danju.zyjpzbz.value=0;
  	return go1();
  }
 else if (len == 0)
 	if(zyjbz=="0" && obj.value.length>0)
   	danju.zyjpzbz.value="1";
  else
  {
  	danju.zyjpzbz.value=0;
	  return go1();
  }
 else
  return false;
}
}

function MoveUpDownCursor()
{
 var args=MoveUpDownCursor.arguments;
 var temp;
 var row;
 if (args.length==0) return; //没有参数退出
 if (event.keyCode==38 || event.keyCode==40) //上移下移
 {
 	temp=event.srcElement.id;
	row = document.danju.dqh.value;
	if (event.keyCode==38)
	  if (parseInt(row)>0)
		 	row = parseInt(row)-1;
		else
		 	row = document.danju.lines.value;
  if (event.keyCode==40)
	  if (parseInt(row)<document.danju.lines.value)
		 	row = parseInt(row)+1;
		else
		 	row = 0;

	switch(temp){
		case "leibmc":document.danju.leibmc[row].focus();break;
		case "cpbh": document.danju.cpbh[row].focus();break;
		case "cpmc": document.danju.cpmc[row].focus();break;
		case "cpdw": document.danju.cpdw[row].focus();break;
		case "shul": document.danju.shul[row].focus();break;
		case "danj": document.danju.danj[row].focus();break;
		case "jine": document.danju.jine[row].focus();break;
		case "ckmc": document.danju.ckmc[row].focus();break;
		case "cpgg": document.danju.cpgg[row].focus();break;
		case "cpxh": document.danju.cpxh[row].focus();break;
		case "cpcd": document.danju.cpcd[row].focus();break;
		case "cpzk": document.danju.cpzk[row].focus();break;
		case "zhdj": document.danju.zhdj[row].focus();break;
		case "zhje": document.danju.zhje[row].focus();break;
		case "cpxlh": document.danju.cpxlh[row].focus();break;
		case "slv": document.danju.slv[row].focus();break;
		case "se": document.danju.se[row].focus();break;
		case "shdj": document.danju.shdj[row].focus();break;
		case "shje": document.danju.shje[row].focus();break;
		case "sfzp_ls": document.danju.sfzp_ls[row].focus();break;
		case "cpbz": document.danju.cpbz[row].focus();break;
		case "jqpjj": document.danju.jqpjj[row].focus();break;
		case "diaocj": document.danju.diaocj[row].focus();break;
		case "diaocje": document.danju.diaocje[row].focus();break;
		case "diaorj": document.danju.diaorj[row].focus();break;
		case "diaorje": document.danju.diaorje[row].focus();break;
		case "yuanj": document.danju.yuanj[row].focus();break;
		case "yuanje": document.danju.yuanje[row].focus();break;
		case "xianj": document.danju.xianj[row].focus();break;
		case "bianje": document.danju.bianje[row].focus();break;
		case "pydj": document.danju.pydj[row].focus();break;
		case "kc": document.danju.kc[row].focus();break;
		case "kce": document.danju.kce[row].focus();break;
		case "sc": document.danju.sc[row].focus();break;
		case "sce": document.danju.sce[row].focus();break;
		case "py": document.danju.py[row].focus();break;
		case "pye": document.danju.pye[row].focus();break;
		case "pk": document.danju.pk[row].focus();break;
		case "pke": document.danju.pke[row].focus();break;
		case "rksl": document.danju.rksl[row].focus();break;
		case "cksl": document.danju.cksl[row].focus();break;
	}
 }
}  

String.prototype.Trim = function()
{
  return this.replace(/(^\s*)|(\s*$)/g, "");
} 

function sele(i)
{
	document.danju.cpbh[i].focus();
}

function FrmD(){
  if (event.keyCode==13)
  {
			Next_tab();
  }
}

function qrh(i)
{
	document.danju.dqh.value = i;
}

function checkvalue(theValue)                  
{                  
	if (theValue.indexOf(" ")>=0)                  
	{                  
		alert("错误！\n\n请输入正确数字！\n勿包含空格！");                  
		return false;                  
	}                  
	else if (isNaN(theValue))                   
	{	                  
		alert("错误！\n\n请输入正确数字！");                  
		return false;                  
	}                  
	else if (theValue.indexOf('.')>=0)                  
	{                  
		alert("错误！\n\n请输入整数！")                  
		return false;                  
	}                  
	else if (parseInt(theValue)<0)                  
	{                  
		alert("错误！\n\n请输入正确数字！");                  
		return false;                  
	}                  
	return true;                  
} 

function oGys(theURL) { 
 	popDIV.style.top = "10px";
 	popDIV.style.left = "100px";
 	popDIV.style.width = "760px";
 	popDIV.style.height = "400px";
 	popTitle.innerHTML = "往来单位";
 	popDI.style.display = "none";
 	pop_show("popDIV");
 	pop_show("mbDIV")
	divcontent.innerHTML = "<iframe frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
}

function oGysBm(theURL) { 
	if( event.keyCode ==13){
		usercheck('check',theURL);
	}
}

function onfx(w)
{
	w.style.background='#ffffff';
} 
function onfy(w)
{
	parent.danju.tmsm_tmp.value='0';
	document.danju.shuj_tmp.value = w.value;
	w.style.background='#66FFFF';
	w.select();
}

function Round(a_Num,a_Bit) 
{ 
   return( Math.round(a_Num * Math.pow (10 , a_Bit)) / Math.pow(10 , a_Bit)) ; 
}

//操作按钮
function start_flash()
{
	var targelem = parent.document.getElementById('loader_container');
	targelem.style.display = '';
	targelem.style.visibility = '';
	setInterval(parent.animate,20);
}
function checkstring(theValue,info)                  
{                  
	if (theValue=='')                  
	{                  
		alert(info+"错误！\n\n请勿为空！");                  
		return false;                  
	} 
	else {              
		return true;        
	}          
}     
function checkvalue(theValue,info)                  
{                  
	if (theValue=='')                  
	{                  
		alert(info+"错误！\n\n请勿为空！");                  
		return false;                  
	} 
	else if (theValue.indexOf(" ")>=0)                  
	{                  
		alert(info+"错误！\n\n请勿包含空格！");                  
		return false;                  
	}                  
	else if (isNaN(theValue))                   
	{	                  
		alert(info+"错误！\n\n请输入数字！");                  
		return false;                  
	}                  
	else if (theValue.indexOf('.')>=0)                  
	{                  
		alert(info+"错误！\n\n请输入整数！") ;                 
		return false;                  
	}                  
	else if (parseInt(theValue)<0)                  
	{                  
		alert(info+"错误！\n\n请输入零或正数！");                  
		return false;                  
	}                  
	return true;                  
}     
function savedanj_new()
{
	var founderr=false;
        if(!checkstring(parent.danju.DwName.value,'供应商/客户'))
	{
	    parent.danju.DwName.focus();
	}
        else if(!checkvalue(parent.danju.WaitDays.value,'天数'))
	{
	    parent.danju.WaitDays.focus();
	}
	else if(!checkvalue(parent.danju.qixian.value,'有效期'))
	{
	    parent.danju.qixian.focus();
	}
	else if(!checkvalue(parent.danju.Shifujinge.value,'实付金额'))
	{
	    parent.danju.Shifujinge.focus();
	}
	else{
	   parent.danju.submit();
	}  
	
}
function save_danj_new()
{
	var founderr=false;
        if(!checkstring(parent.danju.DwName.value,'供应商/客户'))
	{
	    parent.danju.DwName.focus();
	}
	else if(!checkvalue(parent.danju.Shifujinge.value,'实付金额'))
	{
	    parent.danju.Shifujinge.focus();
	}
	else{
	   parent.danju.submit();
	}  
	
}
function save_wuliu_fahuo()
{
	var founderr=false;
        if(!checkstring(parent.danju.WuliuName.value,'物流单位'))
	{
	    parent.danju.WuliuName.focus();
	}
	else if(!checkstring(parent.danju.Kuaididanhao.value,'快递单号'))
	{
	    parent.danju.Kuaididanhao.focus();
	}
	else{
	   parent.danju.submit();
	}  
	
}
function savedanj(drt)
{
	alert('TEST！');
	if( checkall(drt) )
	{
		if( parent.danju.shenh.value !=1 ) 
		{
			if(parent.danju.op.value!='save')
			{
				start_flash();
				parent.danju.op.value='save';
				data_s(-1);
				parent.danju.submit();
			}
			else
				alert('数据正在保存中！请稍候...');
		} 
		else 
			alert('本单据已审核！不能修改！');
		
	}
}
function modify(drt)
{
	if( checkall(drt) )
	{
		if( parent.danju.shenh.value !=1 ) 
		{ 
			if(parent.danju.op.value!='saveas')
			{
				start_flash();
				parent.danju.op.value='saveas';
				data_s(-1);
				parent.danju.submit();
			}
			else
				alert('数据正在保存中！请稍候...');
		} 
		else 
			alert('本单据已审核！不能修改！');
	}
}

function verify(drt)
{
  if( checkall(drt) )
  {
		if( parent.danju.zbid.value != '' )  
		{
			if( parent.danju.shenh.value == 0 ) 
			{
				if(parent.danju.op.value!='shenh')
				{
					start_flash();
					parent.danju.op.value='shenh';
					data_s(-1);
					parent.danju.submit();
				}
				else
					alert('数据正在审核中！请稍候...');

			}
			else 
				alert('本单据已审核!');
		}
		else 
		{
			alert('没有保存!');
		}
  }
}

function cancelVerify()
{
	if( parent.danju.shenh.value == 1 ) 
	{
		if( parent.danju.pzsh.value == 0)
		{
			parent.danju.op.value='chex';
			//data_s(-1);
			parent.danju.submit();
		}
		else
			alert('对应凭证已审核！请先撤销凭证审核！');
	}
	else 
		alert('没有审核本单据!');
}

function printdanj(url,info)
{
	if( parent.danju.zbid.value != '' ){
		if(info=='销售出库单')
		{
			if(parent.danju.dyfs.value=='1')
			{
				if(parent.danju.zjdy.value=='0')
					Caizhanggui_Print(2);
				else
					Caizhanggui_Print(1);
			}
			else
				window.open(url,'caizhangguiprint','width=690,height=540,left=100,top=0,resizable=yes,scrollbars=yes');
		}
		else
			window.open(url,'caizhangguiprint','width=690,height=540,left=100,top=0,resizable=yes,scrollbars=yes');
	}
	else{
		alert('没有保存【'+info+'】');
	}
}

function add()
{
	if(parent.danju.shenh.value !=1) 
	{
		copyPpxh1(document.danju.lines.value);
	}
	else
		alert('本单据已审核！不能增加！');
}

function srch(i){

  if (event.keyCode==13)
  {
      copyPpxh1(i);
      FrmD();
  }
}

function copyPpxh1(i)
{
	if( i >= 0 && i==document.danju.lines.value )
	{
		addRow($('tableid'));
		addbh();
	}
}

function addbh()
{
	var i,j;
	i = document.danju.no.length-1;
	j = document.danju.no.length-2;
	danju.no[i].value=eval(danju.no[j].value)+1;
	danju.lines.value = eval(danju.lines.value) + 1 ;
	
	qksj(i);
	danju.mxid[i].value = '';
}

function delline(i,xsw)
{
	if(parent.danju.shenh.value !=1) 
	{
		if(confirm('真的删除吗?'))
		{
			qksj(i);
			sum_all(xsw);
			return true;
		}
		else
			return false; 
	}
	else
	{
		alert('本单据已审核！不能删除！');
		return false;
	}
}
function delXline(i)
{
  parent.danju.Shifujinge.value="";
  return confirm('真的删除 No.'+i+' 吗?');
}
function selectgys(gysbh,gysmc)
{
	document.danju.gongys.value = gysbh;
	document.danju.DwName.value = gysmc;
	document.danju.Shifujinge.focus();
}

function selectwuliu(WuliuBH,WuliuName)
{
	document.danju.WuliuBH.value = WuliuBH;
	document.danju.WuliuName.value = WuliuName;
	document.danju.Kuaididanhao.focus();
}

function selectkh(gysbh,gysmc)
{
	document.danju.keh.value = gysbh;
	document.danju.kehmc.value = gysmc;
	document.danju.beiz.focus();
}

function chanpselect3(kmbh,kmmc,l)
{
	parent.danju.kmbh.value=kmbh;
	parent.danju.kmmc.value=kmmc;
}

function zpsz(i)
{
	if(danju.sfzp_ls[i].value=='')
	{
		danju.sfzp_ls[i].value = "赠品";
		danju.sfzp[i].value = "1";
		danju.cpzk[i].value = 1;
		danju.danj[i].value = 0;
		danju.jine[i].value = 0;
		sun_je(i,danju.je_blws.value);
	}
	else
	{
		danju.sfzp[i].value = "0";
		danju.sfzp_ls[i].value = "";
	}
}

function getSelect_Value(obj_id)
{
	var obj = parent.document.getElementById(obj_id); //selectid
	var index = obj.selectedIndex; // 选中索引
	var value = obj.options[index].value; // 选中值
	return value;	
}

function getSelect_Text(obj_id)
{
	var obj = parent.document.getElementById(obj_id); //selectid
	var index = obj.selectedIndex; // 选中索引
	var text = obj.options[index].text; // 选中文本
	return text;	
}

function getSelect_Index(obj_id)
{
	var obj = parent.document.getElementById(obj_id); //selectid
	var index = obj.selectedIndex; // 选中索引
	return index;	
}

function cksz(i)
{
	if( parent.danju.shenh.value != 1 )
	{
		if(danju.ckbh[i].value=='' && getSelect_Index('cangk')!=0)
		{
			danju.ckmc[i].value=getSelect_Text('cangk');
			danju.ckbh[i].value=getSelect_Value('cangk');
		}
 }
}

function ckxz(i)
{
  theURL = "../caigou/cangk_cx.php?lines="+i;

 	parent.popDIV.style.top = "10px";
 	parent.popDIV.style.left = "100px";
 	parent.popDIV.style.width = "250px";
 	parent.popDIV.style.height = "240px";
 	parent.popTitle.innerHTML = "仓库选择";
 	parent.popDI.style.display = "none";

 	parent.pop_show("popDIV");
 	parent.pop_show("mbDIV")
	parent.divcontent.innerHTML = "<iframe frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
}

function selectck(ckbh,ckmc,i)
{
	document.danju.ckbh[i].value = ckbh;
	document.danju.ckmc[i].value = ckmc;
}
function oDddr(theURL) { 
	if( checkall_dd() )
	{
	 	parent.popDIV.style.top = "10px";
	 	parent.popDIV.style.left = "100px";
	 	parent.popDIV.style.width = "580px";
	 	parent.popDIV.style.height = "400px";
	 	parent.popTitle.innerHTML = "订单导入";
	 	parent.popDI.style.display = "";
	
	 	parent.pop_show("popDIV");
	 	parent.pop_show("mbDIV")
		parent.divcontent.innerHTML = "<iframe name='cpxz' id='cpxz' frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
	}
}

function changeck()
{
	for(var i=0;i<document.danju.ckbh.length;i++)
 	{
		if(danju.ckmc[i].value!='')
		{
			danju.ckmc[i].value=parent.document.getElementById("cangk").options[parent.window.document.getElementById("cangk").selectedIndex].text;
			danju.ckbh[i].value=parent.window.document.getElementById("cangk").value;
		}
 	}
}

function div_win(url)
{
 	pop_width = width-70;
 	parent.popDIV.style.top = "5px";
 	parent.popDIV.style.left = "50px";
 	parent.popDIV.style.width = pop_width+"px";
 	parent.popDIV.style.height = "75%";
 	parent.popTitle.innerHTML = "选择窗口";
 	parent.popDI.style.display = "";

 	parent.pop_show("popDIV");
 	parent.pop_show("mbDIV")
	parent.divcontent.innerHTML = "<iframe name='cpxz' id='cpxz' frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
}

function openPpxh(cpid,cpmc,cpgg,cpxh,cpcd,cpdw,xsj,cpbm,lbmc,pjj,i,xsw,sl,djlx) 
{
  theURL = "../xiaoshou/cp_jqpjj.php?cpid="+cpid+"&cpmc="+cpmc+"&cpgg="+cpgg+"&cpxh="+cpxh+"&cpcd="+cpcd+"&cpdw="+cpdw+"&xsj="+xsj+"&cpbm="+cpbm+"&lbmc="+lbmc+"&pjj="+pjj+"&i="+i+"&xsw="+xsw+"&sl="+sl+"&djlx="+djlx

 	parent.popDIV.style.top = "10px";
 	parent.popDIV.style.left = "100px";
 	parent.popDIV.style.width = "250px";
 	parent.popDIV.style.height = "165px";
 	parent.popTitle.innerHTML = "名称："+cpmc+"->"+cpgg+"->"+cpxh;
 	parent.popDI.style.display = "none";
 	parent.pop_show("popDIV");
 	parent.pop_show("mbDIV");

	parent.divcontent.innerHTML = "<iframe frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
}

function $(elementId)
{ 
	if (document.getElementById) { 
	   return document.getElementById(elementId); 
	}else if(document.all) { 
	   return document.all[elementId]; 
	}else if(document.layers) { 
	   return document.layers[elementId]; 
	}
}

function addRow(table)
{
   var sampleRow = table.rows[table.rows.length - 1];
   var sampleCell = sampleRow.getElementsByTagName("td");
   var row = table.rows.length;
   var newRow = document.createElement("tr");
  newRow.style.backgroundColor = "f8fdff";

   for(i = 0; i < sampleCell.length; i ++ )
   {
      var newCell = document.createElement("td");
      newCell.style.width = sampleCell[i].clientWidth+"px";
      newCell.innerHTML=sampleCell[i].innerHTML.replace(new RegExp(eval(danju.lines.value), "g"), eval(danju.lines.value) + 1);
      newCell.childNodes.item(0).name = "cell" + row + i;
      newRow.appendChild(newCell);
   }
   sampleRow.parentNode.appendChild(newRow);
}

function deleteRow(table)
{
   if(table.rows.length == 2)
   window.alert("不允许删除最后一行数据！");
   else
   table.deleteRow(table.rows.length - 1);
}


function FrmNext(){
  if (event.keyCode==13)
  {
		event.keyCode=9;
  }
}

function onfxz(w)
{
	w.style.background='#F8FDFF';
} 
function onfyz(w)
{
	document.danju.shuj_tmp.value = w.value;
	document.danju.tmsm_tmp.value='1';
	document.danju.tmsl.value='1';
	w.style.background='#66FFFF';
	w.select();
}
function sjjy()
{
	if(isNaN(document.danju.tmsl.value)) 
		document.danju.tmsl.value = document.danju.shuj_tmp.value;
}
function cpmcjy(cpid) { 
	var line = -1;
	for(var i=0;i<document.danju.chanpmc.length;i++)
 	{   
 		if( cpid==document.danju.chanpmc[i].value)
 		{
			line = i;
			break;
 		}
 	}
 	return line;
}

function cptmss(url)
{
	if(isNaN(parent.danju.tmsl.value)) parent.danju.tmsl.value = 1;
	parent.danju.tmsm_tmp.value = 1;
	if(parent.danju.tmgjz.value=='')
	{
		alert('请输入条码！');
		parent.danju.tmgjz.focus();
	}
	else
	{
		usercheck_tm('check',theURL);
	}
}

function cptmcfpd(cpid,dqsl)
{
	k=cpmcjy(cpid);
	if(k!=-1)
	{
		
		if(parent.danju.tmsm_tmp.value=='0')
		{
			document.danju.shul[k].focus();
		}
		else
		{
			document.danju.shul[k].value = eval(document.danju.shul[k].value) + eval(dqsl);		
			parent.danju.tmgjz.focus();
			parent.danju.tmgjz.select();
		}
		return true;
	}
	else
	{	
		return false;
	}
}

function szmxjy(xmid) { 
	var line = -1;
	for(var i=0;i<document.danju.xmid.length;i++)
 	{   
 		if( xmid==document.danju.xmid[i].value)
 		{
			line = i;
			break;
 		}
 	}
 	return line;
}