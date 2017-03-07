function chanpselect(cpid,cpmc,cpgg,cpxh,cpcd,cpdw,pjj,cpbm,lbmc,i,xsw)
{
	var ycbz = '0';
	dqhs = document.danju.dqhs.value;

if(parent.danju.tmsm_tmp.value=='0')
	dqsl = 1;
else
	dqsl = parent.danju.tmsl.value

if(cptmcfpd(cpid,dqsl)==false)
{
	if(i==-1)
	{
		if( dqhs==-1)
		{
			for(var j=0;j<document.danju.chanpmc.length;j++)
		 	{   
		 		if( document.danju.chanpmc[j].value=='' )
		 		{
		 			i=j;
		 			break; 
		 		}
		 	}
		 	if(i==-1) i=j;
		}
		else
		{
			i=dqhs+1;
		}
		
		if(i>document.danju.lines.value) add();
		
		document.danju.dqhs.value = dqhs;
	}
	
	if(cpid!=document.danju.chanpmc[i].value)
	{
	cksz(i);
  document.danju.chanpmc[i].value = cpid;
	document.danju.cpmc[i].value = cpmc;
	document.danju.cpgg[i].value = cpgg;
	document.danju.cpxh[i].value = cpxh;
	document.danju.cpcd[i].value = cpcd;
  document.danju.cpdw[i].value = cpdw;
  document.danju.cpbh[i].value = cpbm;
  document.danju.leibmc[i].value = lbmc;
  document.danju.ddmxid[i].value = 0;
  document.danju.shul[i].value = dqsl;
  document.danju.danj[i].value = pjj;
  document.danju.jine[i].value = "";
  document.danju.cpzk[i].value = 1;
  document.danju.slv[i].value = 0;
	document.danju.sfzp[i].value = "0";
	document.danju.sfzp_ls[i].value = "";

  if(document.danju.mxid[i].value=='') document.danju.mxid[i].value = 0;

  sun_je(i,xsw);

  document.danju.xlhbh[i].value = 0;
	}

	if(parent.danju.tmsm_tmp.value=='0')
	{
	switch(document.danju.dqjdxm.value){
		case "cpxlh":document.danju.cpxlh[i].focus();break;
		case "shul":document.danju.shul[i].focus();break;
		case "danj":document.danju.danj[i].focus();break;
		case "cpzk":document.danju.cpzk[i].focus();break;
		case "slv":document.danju.slv[i].focus();break;
	}
	}
	else
	{
		parent.danju.tmgjz.focus();
		parent.danju.tmgjz.select();
	}
}
}

function chanpselect_dd(ckid,ckmc,cpbm,lbmc,cpid,cpmc,cpgg,cpxh,cpcd,cpxlh,xlhbh,cpdw,cpsl,cpdj,cpzk,slv,bz,ddmxid,i,xsw)
{
	var ycbz = '0';
	dqhs = document.danju.dqhs.value;

	if(i==-1)
	{
		if( dqhs==-1)
		{
			for(var j=0;j<document.danju.chanpmc.length;j++)
		 	{   
		 		if( document.danju.chanpmc[j].value=='' )
		 		{
		 			i=j;
		 			break; 
		 		}
		 	}
		 	if(i==-1) i=j;
		}
		else
		{
			i=dqhs+1;
		}
		
		if(i>document.danju.lines.value) add();
		
		document.danju.dqhs.value = dqhs;
	}
	
  document.danju.ckbh[i].value = ckid;
  document.danju.ckmc[i].value = ckmc;
  document.danju.cpbh[i].value = cpbm;
  document.danju.leibmc[i].value = lbmc;
  document.danju.chanpmc[i].value = cpid;
	document.danju.cpmc[i].value = cpmc;
	document.danju.cpgg[i].value = cpgg;
	document.danju.cpxh[i].value = cpxh;
	document.danju.cpcd[i].value = cpcd;
	document.danju.cpxlh[i].value = cpxlh;
	document.danju.xlhbh[i].value = xlhbh;
  document.danju.cpdw[i].value = cpdw;
  document.danju.shul[i].value = cpsl;
  document.danju.danj[i].value = cpdj;
  document.danju.cpzk[i].value = cpzk;
  document.danju.slv[i].value = slv;
	document.danju.sfzp[i].value = "0";
	document.danju.sfzp_ls[i].value = "";
  document.danju.cpbz[i].value = bz;
  document.danju.ddmxid[i].value = ddmxid;

  if(document.danju.mxid[i].value=='') document.danju.mxid[i].value = 0;

  sun_je(i,xsw);


	switch(document.danju.dqjdxm.value){
		case "cpxlh":document.danju.cpxlh[i].focus();break;
		case "shul":document.danju.shul[i].focus();break;
		case "danj":document.danju.danj[i].focus();break;
		case "cpzk":document.danju.cpzk[i].focus();break;
		case "slv":document.danju.slv[i].focus();break;
	}
}

function Data_validation(ckxx,jbrxx,bmxx,wlxx,jexx){
  var Return_Value = false;

	rq = parent.danju.ruk_year.value + "-" + parent.danju.ruk_month.value + "-" + parent.danju.ruk_day.value;

	if( !checkvalue(parent.danju.ruk_year.value) )
		parent.danju.ruk_year.focus();
	else if( !checkvalue(parent.danju.ruk_month.value) )
		parent.danju.ruk_month.focus();
	else if( !checkvalue(parent.danju.ruk_day.value) )
		parent.danju.ruk_day.focus();
	else if( !checkDate(rq) )
	{
    alert(rq+ "不正确的日期，请重新输入！");
		parent.danju.ruk_year.focus();
	}
  else if( parent.danju.cangk.selectedIndex == 0 )
	{
		parent.danju.cangk.focus();
		alert("请选择【"+ckxx+"】");
	}
  else if( parent.danju.yewy.selectedIndex == 0 )
	{
		parent.danju.yewy.focus();
		alert("请选择【"+jbrxx+"】");
	}
  else if( parent.danju.szbm.value == 0 )
	{
		alert("请选择【"+bmxx+"】");
	}
  else if( parent.danju.gongys.value == "" )
	{
		alert("请选择【"+wlxx+"】");
	}
	else if( parent.danju.sfje.value == "" )
	{
		alert("请输入【"+jexx+"】");
	}
	else if( parent.danju.yhje.value == "" )
	{
		alert("请输入【优惠金额】");
	}
	else
			Return_Value=true;
			
	return Return_Value;	
}

function oRk(i) { 
  if( Data_validation("收货仓库","采购员","所在部门","供应商","实付金额") )
	{ 
		theURL = "cp_search.php?op=cgrk&lines=" + i + "&cangk=" + document.danju.ckbh[i].value + "&kehbh=" + parent.danju.gongys.value;
		div_win(theURL);
  }
}

function oRk2(i) { 
  if( Data_validation("收货仓库","采购员","所在部门","供应商","实付金额") )
	{ 
		theURL = "../cunhuo/cp_search_tm.php?op=cgrk&lines=" + i + "&cangk=" + document.danju.ckbh[i].value + "&cpbh=" + document.danju.cpbh[i].value + "&kehbh=" + parent.danju.gongys.value;
		usercheck('check',theURL);
	}
}

function oTh(i) { 
  if( Data_validation("发货仓库","经办人","所在部门","供应商","实收金额") )
	{
		theURL = "cp_search.php?op=cgth&lines=" + i + "&cangk=" + document.danju.ckbh[i].value + "&kehbh=" + parent.danju.gongys.value;
		div_win(theURL);
   }
}

function oTh2(i) { 
  if( Data_validation("发货仓库","经办人","所在部门","供应商","实收金额") )
	{
		theURL = "../cunhuo/cp_search_tm.php?op=cgth&lines=" + i + "&cangk=" + document.danju.ckbh[i].value + "&cpbh=" + document.danju.cpbh[i].value + "&kehbh=" + parent.danju.gongys.value;
		usercheck('check',theURL);
   }
}

function checkall()
{
	var errfound = false;
	var ckxx,jbrxx,jexx,dqmxhs=0;
	if(document.danju.bdlx.value == "r"){
		ckxx = "收货仓库";
		jbrxx = "采购员";
		jexx = "实付金额";
	}
	else{
		ckxx = "发货仓库";
		jbrxx = "经办人";
		jexx = "实收金额";
	}
	bmxx="所在部门";
	wlxx="供应商";
  
  errfound = Data_validation(ckxx,jbrxx,bmxx,wlxx,jexx);
   
	for(var i=0;i<document.danju.chanpmc.length;i++)
 	{   
 		if( document.danju.chanpmc[i].value!='' )
 		{
 			dqmxhs=1;
 			document.danju.shul[i].value=document.danju.shul[i].value.Trim();
 			document.danju.danj[i].value=document.danju.danj[i].value.Trim();
 			document.danju.cpzk[i].value=document.danju.cpzk[i].value.Trim();
 			document.danju.slv[i].value=document.danju.slv[i].value.Trim();

 			if(document.danju.shul[i].value=='')
 			{
 				document.danju.shul[i].focus();
 				alert("请输入第"+(i+1)+"行数量！");
 				return false;
 			}
 			if(document.danju.danj[i].value=='')
 			{
 				document.danju.danj[i].focus();
 				alert("请输入第"+(i+1)+"行单价！");
 				return false;
 			}
 			if(document.danju.cpzk[i].value=='')
 			{
 				document.danju.cpzk[i].focus();
 				alert("请输入第"+(i+1)+"行折扣！");
 				return false;
 			}
 			if(document.danju.slv[i].value=='')
 			{
 				document.danju.slv[i].focus();
 				alert("请输入第"+(i+1)+"行税率！");
 				return false;
 			}
 		}
 	} 

	if(errfound && dqmxhs==0)
	{
		alert('请选择'+czg_cptc+'信息');
		errfound=false;
	}
  return errfound;
}  

function checkall_dd()
{
	var errfound = false;
	var ckxx,jbrxx,jexx,dqmxhs=0;
	if(document.danju.bdlx.value == "r"){
		ckxx = "收货仓库";
		jbrxx = "采购员";
		jexx = "实付金额";
	}
	else{
		ckxx = "发货仓库";
		jbrxx = "经办人";
		jexx = "实收金额";
	}
	bmxx="所在部门";
	wlxx="供应商";
  
  errfound = Data_validation(ckxx,jbrxx,bmxx,wlxx,jexx);

  return errfound;
}  

function data_s(k)
{   
	var cpid_tmp="",ddmxid_tmp="",shul_tmp="",danj_tmp="",jine_tmp="",sfzp_tmp="",cpzk_tmp="",ckbh_tmp="",cpbz_tmp="",tmp_mxid="",tmp_scid="",tmp_xlhbh="",tmp_cpxlh="",tmp_slv="";
	
	for(var i=0;i<document.danju.chanpmc.length;i++){   
 		if( document.danju.chanpmc[i].value!='' &&  document.danju.mxid[i].value!='')
 		{
 			cpid_tmp = cpid_tmp + ',' + document.danju.chanpmc[i].value ; 
 			ddmxid_tmp = ddmxid_tmp + ',' + document.danju.ddmxid[i].value ; 
 			shul_tmp = shul_tmp + ',' + document.danju.shul[i].value ; 
	 		danj_tmp = danj_tmp + ',' + document.danju.danj[i].value ; 
	 		jine_tmp = jine_tmp + ',' + document.danju.jine[i].value ; 
 			sfzp_tmp = sfzp_tmp + ',' + document.danju.sfzp[i].value ; 
 			cpzk_tmp = cpzk_tmp + ',' + document.danju.cpzk[i].value ; 
 			ckbh_tmp = ckbh_tmp + ',' + document.danju.ckbh[i].value ; 
 			cpbz_tmp = cpbz_tmp + ',' + document.danju.cpbz[i].value.replace(/,/g,"，");
 			tmp_mxid = tmp_mxid + ',' + document.danju.mxid[i].value ;
 			tmp_xlhbh = tmp_xlhbh + ',' + document.danju.xlhbh[i].value ;
 			tmp_cpxlh = tmp_cpxlh + ',' + document.danju.cpxlh[i].value ;
 			tmp_slv = tmp_slv + ',' + document.danju.slv[i].value ;
 		}
 		else if(document.danju.mxid[i].value!='')
 		{
 			if(tmp_scid=='')
 				tmp_scid = document.danju.mxid[i].value ;
 			else
 				tmp_scid = tmp_scid + ',' + document.danju.mxid[i].value ;
 		}
 	} 
  parent.danju.slv_s.value = tmp_slv; 
  parent.danju.xlhbh_s.value = tmp_xlhbh; 
  parent.danju.cpxlh_s.value = tmp_cpxlh; 
  parent.danju.scid_s.value = tmp_scid;
  parent.danju.mxid_s.value = tmp_mxid;
	parent.danju.chanpmc_s.value = cpid_tmp; 
	parent.danju.ddmxid_s.value = ddmxid_tmp; 
	parent.danju.shul_s.value = shul_tmp; 
	parent.danju.danj_s.value = danj_tmp;  
	parent.danju.jine_s.value = jine_tmp;  
	parent.danju.sfzp_s.value = sfzp_tmp;  
	parent.danju.cpzk_s.value = cpzk_tmp;  
	parent.danju.ckbh_s.value = ckbh_tmp;  
	parent.danju.cpbz_s.value = cpbz_tmp;  
}

function sun_ye(xsw)
{
	var tmp_yfje,tmp_sfje,tmp_ye,tmp_ye;
	if(isNaN(parent.danju.sfje.value)) 
		parent.danju.sfje.value = parent.danju.shuj_tmp.value;
	else if(isNaN(parent.danju.yhje.value)) 
		parent.danju.yhje.value = parent.danju.shuj_tmp.value;
	else
	{
		if(parent.danju.yfje.value=='')
			tmp_yfje=0;
		else
			tmp_yfje=parent.danju.yfje.value;
			
		tmp_sfje = parent.danju.sfje.value;
		tmp_yhje = parent.danju.yhje.value;
		tmp_ye = Round(tmp_yfje - tmp_sfje - tmp_yhje,xsw);
		parent.danju.bcye.value = tmp_ye; 
	}
}

function sun_je(i,xsw)
{
	var tmp,tmp_dj,tmp_sl,tmp_slhj=0,tmp_zk,tmp_slv;
	var dj_blws=danju.dj_blws.value;
	var je_blws=danju.je_blws.value;

	if(isNaN(document.danju.shul[i].value)) 
		document.danju.shul[i].value = document.danju.shuj_tmp.value;
	else if(isNaN(document.danju.danj[i].value)) 
		document.danju.danj[i].value = document.danju.shuj_tmp.value;
	else if(isNaN(document.danju.cpzk[i].value)) 
		document.danju.cpzk[i].value = document.danju.shuj_tmp.value;
	else if(isNaN(document.danju.slv[i].value)) 
		document.danju.slv[i].value = document.danju.shuj_tmp.value;

	tmp_dj = document.danju.danj[i].value;
	tmp_sl = document.danju.shul[i].value;
	tmp_zk = document.danju.cpzk[i].value;
	tmp_slv = document.danju.slv[i].value/100;

	tmp = Round(tmp_dj * tmp_sl,je_blws);
	document.danju.jine[i].value = tmp; 

	tmp_zhdj = Round(tmp_dj * tmp_zk,dj_blws);
	document.danju.zhdj[i].value = tmp_zhdj; 

	tmp_zhje = Round(tmp_dj * tmp_sl * tmp_zk,je_blws);
	document.danju.zhje[i].value = tmp_zhje; 
	
	tmp = Round(tmp_zhje * tmp_slv,je_blws);
	document.danju.se[i].value = tmp; 

	tmp = Round(tmp_zhdj * (1+tmp_slv),dj_blws);
	document.danju.shdj[i].value = tmp; 

	tmp = Round(tmp_zhje * (1+tmp_slv),je_blws);
	document.danju.shje[i].value = tmp; 

	sum_all(xsw);
}  

function sun_sqdj(i)
{
	var tmp,tmp_shdj,tmp_sl,tmp_slhj=0,tmp_zk,tmp_slv;
	var dj_blws=danju.dj_blws.value;
	var je_blws=danju.je_blws.value;

	if(isNaN(document.danju.shul[i].value)) 
		document.danju.shul[i].value = document.danju.shuj_tmp.value;
	else if(isNaN(document.danju.shdj[i].value)) 
		document.danju.shdj[i].value = document.danju.shuj_tmp.value;
	else if(isNaN(document.danju.cpzk[i].value)) 
		document.danju.cpzk[i].value = document.danju.shuj_tmp.value;
	else if(isNaN(document.danju.slv[i].value)) 
		document.danju.slv[i].value = document.danju.shuj_tmp.value;

	tmp_shdj = document.danju.shdj[i].value;
	tmp_sl = document.danju.shul[i].value;
	tmp_zk = document.danju.cpzk[i].value;
	tmp_slv = document.danju.slv[i].value/100;
	
	tmp = Round(tmp_shdj * tmp_sl,je_blws);
	document.danju.shje[i].value = tmp; 

	tmp_zhdj = Round(tmp_shdj/(1+tmp_slv),dj_blws);
	document.danju.zhdj[i].value = tmp_zhdj; 
	
	tmp_zhje = Round(tmp_zhdj * tmp_sl,je_blws);
	document.danju.zhje[i].value = tmp_zhje; 
	
	tmp_dj = Round(tmp_zhdj/tmp_zk,dj_blws);
	document.danju.danj[i].value = tmp_dj; 

	tmp = Round(tmp_dj * tmp_sl,je_blws);
	document.danju.jine[i].value = tmp; 

	tmp = Round(tmp_zhje * tmp_slv,je_blws);
	document.danju.se[i].value = tmp; 

	sum_all(xsw);
}

function pjj(i,xsw)
{
	var tmp,tmp_dj,tmp_sl,tmp_slhj,tmp_jehj;
	if(isNaN(document.danju.jine[i].value)) 
		document.danju.jine[i].value = document.danju.shuj_tmp.value;
	tmp = document.danju.jine[i].value;
	tmp_sl = document.danju.shul[i].value;
	tmp_dj = Round(tmp / tmp_sl,xsw);
	document.danju.danj[i].value = tmp_dj;

	sum_all(xsw);
}   
//求总和
function sum_all(xsw)
{   
	var tmp_sl=0,tmp_je=0,tmp_zhje=0,tmp_shje=0,tmp_se=0;
	var sl_blws=danju.sl_blws.value;
	
	for(var i=0;i<document.danju.shul.length;i++)
 	{   
		if(document.danju.shul[i].value!='') 
			tmp_sl = tmp_sl + eval(document.danju.shul[i].value);
		if(document.danju.jine[i].value!='') 
			tmp_je = eval(tmp_je) + eval(document.danju.jine[i].value); 
		if(document.danju.zhje[i].value!='') 
			tmp_zhje = eval(tmp_zhje) + eval(document.danju.zhje[i].value); 
		if(document.danju.shje[i].value!='') 
			tmp_shje = eval(tmp_shje) + eval(document.danju.shje[i].value); 
		if(document.danju.se[i].value!='') 
			tmp_se = eval(tmp_se) + eval(document.danju.se[i].value); 
 	} 
	document.danju.shulhj.value = Round(tmp_sl,sl_blws); 
	document.danju.jinehj.value = Round(tmp_je,xsw); 
	document.danju.zhjehj.value = Round(tmp_zhje,xsw); 
	document.danju.shjehj.value = Round(tmp_shje,xsw); 
	document.danju.sehj.value = Round(tmp_se,xsw); 
	
	parent.danju.yfje.value = document.danju.shjehj.value;

	if(danju.cgdjjs.value=='1')
		parent.danju.sfje.value = Round(eval(parent.danju.yfje.value)-eval(parent.danju.bcye.value),xsw);
	else
		parent.danju.bcye.value = Round(eval(parent.danju.yfje.value)-eval(parent.danju.sfje.value),xsw);

	sun_ye(xsw);
}

function qksj(i)
{
	danju.chanpmc[i].value = '';
	danju.ddmxid[i].value = '';
	danju.cpbh[i].value = '';
	danju.leibmc[i].value = '';
	danju.cpmc[i].value = '';
	danju.cpdw[i].value = '';
	danju.shul[i].value = '';
	danju.danj[i].value = '';
	danju.jine[i].value = '';

	danju.cpgg[i].value = '';
	danju.cpxh[i].value = '';
	danju.cpcd[i].value = '';
  danju.ddmxid[i].value = '';

  danju.ckmc[i].value = '';
  danju.ckbh[i].value = '';

  danju.cpzk[i].value = '';
  danju.zhdj[i].value = '';
  danju.zhje[i].value = '';
  danju.sfzp[i].value = '';
  danju.sfzp_ls[i].value = '';
  danju.cpbz[i].value = '';

	danju.xlhbh[i].value = '';
	danju.cpxlh[i].value = '';

	danju.slv[i].value = '';
	danju.se[i].value = '';
	danju.shdj[i].value = '';
	danju.shje[i].value = '';
}
function cptmsm(i) { 
	var errfound = false;
	var ckxx,jbrxx,jexx,dqmxhs=0;
	if(document.danju.bdlx.value == "r"){
		ckxx = "收货仓库";
		jbrxx = "采购员";
		jexx = "实付金额";
	}
	else{
		ckxx = "发货仓库";
		jbrxx = "经办人";
		jexx = "实收金额";
	}
	bmxx="所在部门";
	wlxx="供应商";
  
	if (parent.event.keyCode==13)
	{
		if( Data_validation(ckxx,jbrxx,bmxx,wlxx,jexx) )
		{ 
			theURL = "../cunhuo/cp_search_tm.php?op=cgrk&tmbz=1&lines=" + i + "&cangk=" + parent.danju.cangk.value + "&kehbh=" + parent.danju.gongys.value + "&cpbh=" + parent.danju.tmgjz.value;
			cptmss(theURL);
		}
	}
}