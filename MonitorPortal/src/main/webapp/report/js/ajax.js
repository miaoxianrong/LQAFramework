var http_request=false;
function send_request(url)
{
    http_request=false;
    if(window.XMLHttpRequest)
    {
      http_request=new XMLHttpRequest();
      if(http_request.overrideMimeType)
      {
      	http_request.overrideMimeType("text/xml");
       }
     }
     else if(window.ActiveXObject)
     {
         try{
          http_request=new ActiveXObject("Msxml2.XMLHttp");
         }catch(e){
          try{
          http_request=new ActiveXobject("Microsoft.XMLHttp");
          }catch(e){}
         }
      }
      if(!http_request)
      {
         window.alert("创建XMLHttp对象失败！");
         return false;
      }
      
      http_request.onreadystatechange=processrequest;
      http_request.open("GET",url,true);
      http_request.send(null);
}
//处理返回信息的函数
function processrequest()
{
	if(http_request.readyState==4)
	{
		if(http_request.status==200)
		{
			
			var response=http_request.responseText;
	    //alert(response);
	    var json=eval("("+response+")");
	    //alert(json.czlx);
	    //alert(json.sfcg);
	    //alert(json.rs2);

       czpdf=json.czlx;
       if(json.sfcg=="0")
       {
       		alert("没有找到信息！");
       		if(czpdf=="gys")
       		{
       			document.danju.gongys.value='0';
       			document.danju.DwName.focus();
       			document.danju.DwName.select();
       		}
       		else if(czpdf=="kh")
       		{
       			document.danju.keh.value='0';
       			document.danju.kehmc.focus();
       			document.danju.kehmc.select();
       		}
       		else if(czpdf=="tmsm")
       		{
       			parent.danju.tmgjz.focus();
       			parent.danju.tmgjz.select();
       		}
       		else
       		{
       			sele(json.rs1);
       		}
       }
       else
       {
      	  if(czpdf=="cg")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11);
      	  else if(czpdf=="ck")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11);
      	  else if(czpdf=="xs")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11,json.rs12,1);
      	  else if(czpdf=="db")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11);
      	  else if(czpdf=="bj")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11,json.rs12);
      	  else if(czpdf=="zj")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11,json.rs12);
      	  else if(czpdf=="pd")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11,json.rs12);
      	  else if(czpdf=="cz")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7,json.rs8,json.rs9,json.rs10,json.rs11,json.rs12,1);
      	  else if(czpdf=="pz")
      	  	chanpselect(json.rs1,json.rs2,json.rs3,json.rs4,json.rs5,json.rs6,json.rs7);
      	  else if(czpdf=="gys")
      	  {
      	  	selectgys(json.rs1,json.rs2);
      	  }
      	  else if(czpdf=="kh")
      	  {
      	  	selectkh(json.rs1,json.rs2);
      	  }

       }
    }
    else{//页面不正常
    	alert("您所请求的页面不正常！");
    }
 }
}

function usercheck(obj,theURL){
  if (event.keyCode==13)
  {
   //document.getElementById(obj).innerHTML="正在读取数据...";
   send_request(theURL);
   reobj=obj;
 }
}

function usercheck_tm(obj,theURL){
  if (parent.event.keyCode==13)
  {
   //document.getElementById(obj).innerHTML="正在读取数据...";
   send_request(theURL);
   reobj=obj;
 }
}