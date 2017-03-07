<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>

<title>Monitor</title>
<script language="JavaScript"> 
function myrefresh() 
{ 
window.location.reload(); 
} 
//setTimeout('myrefresh()',6000); 
</script> 
<script language="javascript">
<!--
function del()
{
  return confirm('Del?');
}

//-->
</script>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%
String ListID,tagName,nodeId,screenName,nodeClass,nodeType,nodeTitle,childNum,nodeText,nodeHref;
String nodeName,nodeValue,ProjectID,LevelID,ParentSeq,seq,Active,onclick,onkeydown;
String get_special,visible,clickable,branch,leaf,vector,uniqueObject,NodePath;
String mainFeature,getSubMenu,mainMenuFilter,includerFilter,excluderFilter,noLessLeafFilter,forceSetBranchFilter,noMoreUniqueFilter;
boolean bSpecial=false;String bgcolor="#F8FDFF";String touchonce="";String touchtime="";String clickfail="";
%>
<%
ParentSeq="4"; 
LevelID="2";
String sql_aux=" and LevelID>0";
workM.getConnection();
ProjectID="12984";Active="0";
//ProjectID="00000372";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}

String searchNodePath="";
String getNodePath=request.getParameter("node");
if(getNodePath!=null){
	searchNodePath=getNodePath;
	if(searchNodePath.compareTo("")==0){
		sql_aux=sql_aux+"";
	}else{
		sql_aux=sql_aux+" and NodePath like '"+searchNodePath+"-%'";
		String debugging=request.getParameter("debugging");
		if(debugging!=null){
			if(debugging.compareTo("quick")==0){
				String sql1="insert into NodeTest set NodePath='"+getNodePath+"',ProjectID='"+ProjectID+"',Active=1";
				workM.updateSQL(sql1);
			}
		}
	}
}

//C:\work\backup\Web UI\powershell\ROOT
int iCount;int nCount; String sql; ResultSet RS;
//search
String OrderSJ=request.getParameter("OrderSJ");
boolean bSearch = false;
String Search=request.getParameter("Search");
String special=request.getParameter("special");
String SearchType=request.getParameter("SearchType");
String SearchContent=request.getParameter("SearchContent");
if(Search!=null){
	SearchContent=SearchContent.trim();
	bSearch = true;
}
else{
	bSearch = false;
}
//·ÖÒ³
String CurPage;
int iCurPage=0;
int iOnePage=1000;
int iTotalRecords=0;
int iTotalPages=0;
iTotalRecords=0;
if(bSearch){
	iOnePage=1000;
	sql="select count(*)  from NodeList";
	if(SearchContent.compareTo("")==0){
		sql+=" where ListID>0 ";
	}
	else{
		if(SearchType.equals("ParentSeq")||SearchType.equals("LevelID")||SearchType.equals("seq")){
			sql+=" where "+SearchType+" = "+SearchContent;
		}
		else{
			//sql+=" where "+SearchType+" like '%"+SearchContent+"%'";
			sql+=" where "+SearchType+" = '"+SearchContent+"' ";
		}
	}
	if(special.compareTo("0")==0||special.compareTo("1")==0){
		sql+=" and special="+special;			
	}	
	sql+=" and ProjectID='"+ProjectID+"' and ParentSeq>=0"+ sql_aux;
	
}
else{
	sql="select count(*) from NodeList";	
	sql+=" where ProjectID='"+ProjectID+"' and ParentSeq>=0"+ sql_aux;
}
//out.print(sql);
//out.print("<BR>");
if(true){

ResultSet RS1=workM.getURL(sql); 
if(RS1.next()){
	iTotalRecords = RS1.getInt(1);
}
RS1.close();
iTotalPages=iTotalRecords/iOnePage;
if(iTotalPages*iOnePage<iTotalRecords){
	iTotalPages++;
}
String OrderValue=request.getParameter("OrderValue");
int iJump=0;
String NextPage=request.getParameter("NextPage");
if(NextPage!=null){
	CurPage=(String)session.getValue("CurPage");
	CurPage="1";
	if(NextPage.compareTo("Yes")==0){
		iCurPage=Integer.parseInt(CurPage)+1;
	}
	if(NextPage.compareTo("No")==0){
		iCurPage=Integer.parseInt(CurPage)-1;
	}
	if(NextPage.compareTo("Refresh")==0){
		iCurPage=Integer.parseInt(CurPage);
	}
	if(iCurPage<1){
		iCurPage=1;
	}
	if(iCurPage>iTotalPages){
		iCurPage=iTotalPages;
	}
	session.putValue("CurPage",String.valueOf(iCurPage));
}
else{
	if(OrderValue!=null){
		try{
			iJump = Integer.parseInt(OrderValue);
			if(iJump<1) iJump = 1;
		}catch(Exception e){
			iJump = 1;
		}
		iCurPage=iJump;
		if(iCurPage>iTotalPages){
			iCurPage=iTotalPages;
		}
		session.putValue("CurPage",String.valueOf(iCurPage));
	}
	else{
		session.putValue("CurPage","1");
		iCurPage=1;
	}
}
int iFirstRecord=0;
int iLastRecord=0;
iFirstRecord=(iCurPage-1)*iOnePage+1;
if(iCurPage*iOnePage<iTotalRecords){
	iLastRecord=iCurPage*iOnePage; 
}
else{
	iLastRecord=iTotalRecords;
}
if(bSearch){
	sql="select *  from NodeList";
	if(SearchContent.compareTo("")==0){
		sql+=" where ListID>0 ";
	}
	else{
		if(SearchType.equals("ParentSeq")||SearchType.equals("LevelID")||SearchType.equals("seq")){
			sql+=" where "+SearchType+" = "+SearchContent;
		}
		else{
			//sql+=" where "+SearchType+" like '%"+SearchContent+"%'";
			sql+=" where "+SearchType+" = '"+SearchContent+"' ";
		}
	}
	if(special.compareTo("0")==0||special.compareTo("1")==0){
		sql+=" and special="+special;			
	}	
	sql+=" and ProjectID='"+ProjectID+"' and ParentSeq>=0"+ sql_aux;
}
else{
	sql="select * from NodeList";	
	sql+=" where ProjectID='"+ProjectID+"' and ParentSeq>=0"+ sql_aux;
}
sql+=" order by listid asc"; //touchtime
//sql+=" order by touchtime desc"; //touchtime
//out.print(sql);
//if(true){
RS =workM.getURL(sql); 
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>
<table width="1200" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="monitor1.jsp" name="Maintain" >
<input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="16" align=left><span class="zi"><b>
    Navigation:&nbsp;&nbsp;&nbsp;&nbsp;
    <a href='monitor1.jsp?ProjectID=<%=ProjectID%>'>All</a>
    <%
    String middleNodePath="";
    String getSeq="";
    StringTokenizer stAllNodes =new StringTokenizer(searchNodePath,"-");
    int j=0;
    while(stAllNodes.hasMoreTokens()){
     	getSeq=stAllNodes.nextToken();
     	j++;
	if(j==1){
		middleNodePath=getSeq;
	}else{
		middleNodePath=middleNodePath+"-"+getSeq;
	}
     	%>
     	&nbsp;&nbsp;<a href='monitor1.jsp?ProjectID=<%=ProjectID%>&node=<%=middleNodePath%>'><%=middleNodePath%></a>
     	<%
    }
    %>
    </b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="16">
	    <SELECT name=special size=1 class="tb">
	        <OPTION value="1">Special</OPTION>
		<OPTION value="0">Not Special</OPTION>
		<OPTION value="2">All</OPTION>
	    </SELECT>&nbsp;
	    <SELECT name=SearchType size=1 class="tb">
	    	<OPTION value="ParentSeq">ParentSeq</OPTION>	
		<OPTION value="LevelID">LevelID</OPTION>
		<OPTION value="seq">seq</OPTION>
		<OPTION value="tagName">tagName</OPTION>
		<OPTION value="nodeType">nodeType</OPTION>	
		<OPTION value="nodeClass">nodeClass</OPTION>
		<OPTION value="nodeName">nodeName</OPTION>
		<OPTION value="nodeValue">nodeValue</OPTION>
		<OPTION value="onclick">onclick</OPTION>	
		<OPTION value="onkeydown">onkeydown</OPTION>
		<OPTION value="nodeId">nodeId</OPTION>
		<OPTION value="nodeHref">nodeHref</OPTION>
	    </SELECT>&nbsp;    
	<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="16"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"><div align="center">Select</div></td>
    <td height="25"><div align="center">NodePath</div></td>
    <td height="25"><div align="center">childNum</div></td>       
    <td height="25"><div align="center">branch</div></td>
    <td height="25"><div align="center">leaf</div></td>
    <td height="25"><div align="center">mainFeature</div></td>
    <td height="25"><div align="center">getSubMenu</div></td>
    <td height="25"><div align="center">mainMenuFilter</div></td>
    <td height="25"><div align="center">includerFilter</div></td>
    <td height="25"><div align="center">excluderFilter</div></td>
    <td height="25"><div align="center">noLessLeafFilter</div></td>
    <td height="25"><div align="center">forceSetBranchFilter</div></td>
    <td height="25"><div align="center">noMoreUniqueFilter</div></td>
    <td height="25"><div align="center">Screen</div></td>
    <td height="25"><div align="center">nodeTitle</div></td>
    <td height="25"><div align="center">tagName</div></td>
  </tr>

<%  
  iCount=0;nCount=0;
  int iSpecial=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {//·ÖÒ³
	  	ListID=RS.getString("ListID");
	  	ParentSeq=RS.getString("ParentSeq");
	  	LevelID=RS.getString("LevelID");
	  	seq=RS.getString("seq");
	  	uniqueObject=RS.getString("uniqueObject");
	  	vector=RS.getString("vector");
	  	NodePath=RS.getString("NodePath");
	  	get_special=RS.getString("special");
	  	if(get_special.compareTo("1")==0){get_special="special";}
	  	visible=RS.getString("visible");
	  	if(visible.compareTo("1")==0){visible="visible";}
	  	clickable=RS.getString("clickable");
	  	if(clickable.compareTo("1")==0){clickable="clickable";}
	  	branch=RS.getString("branch");
	  	if(branch.compareTo("1")==0){branch="branch";}
	  	childNum=RS.getString("childNum");
	  	leaf=RS.getString("leaf");
	  	if(leaf.compareTo("1")==0){leaf="leaf";}
	  	
	  	mainFeature=RS.getString("mainFeature");
	  	if(mainFeature.compareTo("1")==0){mainFeature="mainFeature";}
	  	getSubMenu=RS.getString("getSubMenu");
	  	if(getSubMenu.compareTo("1")==0){getSubMenu="getSubMenu";}
	  	mainMenuFilter=RS.getString("mainMenuFilter");
	  	if(mainMenuFilter.compareTo("1")==0){mainMenuFilter="mainMenuFilter";}
	  	includerFilter=RS.getString("includerFilter");
	  	if(includerFilter.compareTo("1")==0){includerFilter="includerFilter";}
	  	excluderFilter=RS.getString("excluderFilter");
	  	if(excluderFilter.compareTo("1")==0){excluderFilter="excluderFilter";}
	  	noLessLeafFilter=RS.getString("noLessLeafFilter");
	  	if(noLessLeafFilter.compareTo("1")==0){noLessLeafFilter="noLessLeafFilter";}
	  	forceSetBranchFilter=RS.getString("forceSetBranchFilter");
	  	if(forceSetBranchFilter.compareTo("1")==0){forceSetBranchFilter="forceSetBranchFilter";}
	  	noMoreUniqueFilter=RS.getString("noMoreUniqueFilter");
	  	if(noMoreUniqueFilter.compareTo("1")==0){noMoreUniqueFilter="noMoreUniqueFilter";}
	  	
	  	
	  	if(get_special.compareTo("special")==0){ //&&visible.compareTo("1")==0&&clickable.compareTo("1")==0){
	  		bSpecial=true;
	  		bgcolor="#AEF2F4";
	  		iSpecial++;
	  	}
	  	else{
	  		bSpecial=false;
	  		bgcolor="#F8FDFF";
	  	}
	  	touchonce=RS.getString("touchonce");
	  	if(touchonce.compareTo("1")==0){
	  		touchonce="touchonce";
	  		bgcolor="green";
	  	}
	  	clickfail=RS.getString("clickfail");
	  	if(clickfail.compareTo("1")==0){
	  		clickfail="clickfail";
	  		bgcolor="yellow";
	  	}
	  	tagName=RS.getString("tagName");
	  	screenName=RS.getString("screenName");
	  	nodeId=RS.getString("nodeId");
	  	nodeClass=RS.getString("nodeClass");
	  		if(nodeClass!=null){nodeClass=nodeClass.replace('&', '\'');} else { nodeClass=""; }
	  	nodeType=RS.getString("nodeType");
	  	nodeTitle=RS.getString("nodeTitle");
	  	nodeText=RS.getString("nodeText");
		  	if(nodeText!=null){
		  		nodeText=nodeText.replace('&', '\'');
		  		int len=nodeText.length();
		  		if(len>20){
		  			nodeText=nodeText.substring(0,20);
		  		}
		  	} else { nodeText=""; }
	  	nodeHref=RS.getString("nodeHref");
	  		if(nodeHref!=null){
			  	
		  	}else { nodeHref=""; }
	  	nodeName=RS.getString("nodeName");
	  	nodeValue=RS.getString("nodeValue");
	  	onclick=RS.getString("onclick");
	  		if(onclick!=null){onclick=onclick.replace('&', '\'');} else { onclick=""; }
	  	onkeydown=RS.getString("onkeydown");
	  		if(onkeydown!=null){onkeydown=onkeydown.replace('&', '\'');} else { onkeydown=""; }
	  	touchtime="";//RS.getString("touchtime");
%>

  <tr bgcolor="<%=bgcolor%>"> 
    <td height="25"> 
      <div align="center"><INPUT name=now_dn type=checkbox value="<%=ListID%>"></div></td>
    <td title='<%=nodeHref%>' height="25"><div align="left">
    	<a href='monitor.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    <%if(bSpecial){%>
    	<BR><a href='monitor.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>&debugging=quick'><font color=red>Run</font></a>
    <%}%>
    </div></td>
    <td height="25"><div align="center"><%=childNum%>
    <%if(bSpecial){
    	String runcolor="";
    	if(childNum.compareTo("0")==0){
    		runcolor="color";
    	}else{
    		runcolor="childNum";
    	}
    	%>
    	<BR><a href='monitor.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>&debugging=quick'><font color='<%=runcolor%>'>Run</font></a>
    <%}%>
    </div></td>   
    <td height="25"><div align="center"><%=branch%></div></td> 
    <td height="25"><div align="center"><%=leaf%></div></td> 
    <td height="25"><div align="center"><%=mainFeature%></div></td> 
    <td height="25"><div align="center"><%=getSubMenu%></div></td> 
    <td height="25"><div align="center"><%=mainMenuFilter%></div></td> 
    <td height="25"><div align="center"><%=includerFilter%></div></td> 
    <td height="25"><div align="center"><%=excluderFilter%></div></td> 
    <td height="25"><div align="center"><%=noLessLeafFilter%></div></td> 
    <td height="25"><div align="center"><%=forceSetBranchFilter%></div></td> 
    <td height="25"><div align="center"><%=noMoreUniqueFilter%></div></td> 
    <td height="25"><div align="center"><%=screenName%></div></td>
    <td height="25"><div align="center"><a target=_blank href='<%=nodeHref%>'><%=nodeTitle%></a></div></td>   
    <td height="25"><div align="center">[<%=tagName%>]</div></td>   
  </tr>
  <%
  	 }  	
  }
  RS.close();
  workM.close();
  %>
   <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="16"> 
      <div align="left">Total special nodes: <%=iSpecial%> </div></td>
  </tr>

</form> 

  </table>
	
</td>
</tr>
</table>
<%
}
%>
</body>
</html>

