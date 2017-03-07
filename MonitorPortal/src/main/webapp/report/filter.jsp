<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>

<title>Filter</title>
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
String ListID,tagName,nodeId,screenName,nodeClass,nodeType,nodeTitle,childNum,nodeText,nodeHref,ParentFilter,PageFilter, FilterName;
int i_name=0; String newStringNumber="";
String nodeName,nodeValue,ProjectID,LevelID,RootURLNodePath,seq,Active,onclick,onkeydown,Feature;
String get_special,DOMType,clickable,branch,leaf,vector,uniqueObject,NodePath,TagOrURL; 
String PageContainer="";
String mainFeature,getSubMenu,mainMenuFilter,includerFilter,excluderFilter,noLessLeafFilter,forceSetBranchFilter,noMoreUniqueFilter;
boolean bSpecial=false;String bgcolor="#F8FDFF";String touchonce="";String touchtime="";String clickfail="";String clicktimes="";
%>
<%
RootURLNodePath="4"; 
LevelID="2";
String sql_aux=" and special=1 ";
String getListID=request.getParameter("ListID");
if(getListID!=null){
	if(getListID.compareTo("null")!=0){
		sql_aux=" and ListID="+getListID+" ";
	}
}
workM.getConnection();
ProjectID="12984";Active="0";
//ProjectID="00000372";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
int iLevel2childNum0=0;
String sql2="select count(*) from NodeList where LevelID=2 and childNum=0 and leaf=0 and ProjectID='"+ProjectID+"'";
ResultSet rs2=workM.getSQL(sql2);
if(rs2.next()){
	iLevel2childNum0=rs2.getInt(1);
}
int iLevel2childNum1=0;
sql2="select count(*) from NodeList where LevelID=2 and childNum>0 and ProjectID='"+ProjectID+"'";
rs2=workM.getSQL(sql2);
if(rs2.next()){
	iLevel2childNum1=rs2.getInt(1);
}
rs2.close();

if(iLevel2childNum0==0){
	iLevel2childNum0=1;
}

int iCoverage2=100*iLevel2childNum1/(iLevel2childNum0+iLevel2childNum1);


int iLevel3childNum0=0;
String sql3="select count(*) from NodeList where LevelID=3 and childNum=0 and leaf=0 and ProjectID='"+ProjectID+"'";
ResultSet rs3=workM.getSQL(sql3);
if(rs3.next()){
	iLevel3childNum0=rs3.getInt(1);
}
int iLevel3childNum1=0;
sql3="select count(*) from NodeList where LevelID=3 and childNum>0 and ProjectID='"+ProjectID+"'";
rs3=workM.getSQL(sql3);
if(rs3.next()){
	iLevel3childNum1=rs3.getInt(1);
}
rs3.close();

if(iLevel3childNum0==0){
	iLevel3childNum0=1;
}
int iCoverage3=100*iLevel3childNum1/(iLevel3childNum0+iLevel3childNum1);
int iCoverage = iCoverage3;
if(iCoverage3>iCoverage2){
	iCoverage = iCoverage2;
}
String searchNodePath="";
String MenuHref="";
String getNodePath=request.getParameter("node");
if(getNodePath!=null){	
	String sql9="select RootURLNodePath from NodeList where NodePath='"+getNodePath+"' and ProjectID='"+ProjectID+"'";
	ResultSet rs9=workM.getSQL(sql9);
	if(rs9.next()){
		RootURLNodePath=rs9.getString("RootURLNodePath");
	}	
	sql9="select MenuHref from NodeList where NodePath='"+RootURLNodePath+"' and ProjectID='"+ProjectID+"'";
	rs9=workM.getSQL(sql9);
	if(rs9.next()){
		MenuHref=rs9.getString("MenuHref");
	}
	rs9.close();
	
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
String getSpecial=request.getParameter("sp");
if(getSpecial!=null){
	if(getSpecial.compareTo("1")==0){
		sql_aux=sql_aux+" and special=1 ";
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
int iOnePage=100;
int iTotalRecords=0;
int iTotalPages=0;
iTotalRecords=0;
if(bSearch){
	iOnePage=100;
	sql="select count(*)  from NodeList";
	if(SearchContent.compareTo("")==0){
		sql+=" where Active=0 ";
	}
	else{
		if(SearchType.equals("LevelID")||SearchType.equals("seq")){
			sql+=" where "+SearchType+" = "+SearchContent;
		}
		else{
			if(SearchType.equals("NodePath")){
				sql+=" where "+SearchType+" = '"+SearchContent+"' ";
			}
			else{
				sql+=" where "+SearchType+" like '%"+SearchContent+"%'";			
			}
		}
	}
	if(special.compareTo("0")==0||special.compareTo("1")==0){
		sql+=" and special="+special;			
	}	
	sql+=" and ProjectID='"+ProjectID+"' "+ sql_aux;
	
}
else{
	sql="select count(*) from NodeList";	
	sql+=" where ProjectID='"+ProjectID+"' "+ sql_aux;
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
		sql+=" where Active=0 ";
	}
	else{
		if(SearchType.equals("LevelID")||SearchType.equals("seq")){
			sql+=" where "+SearchType+" = "+SearchContent;
		}
		else{
			if(SearchType.equals("NodePath")){
				sql+=" where "+SearchType+" = '"+SearchContent+"' ";
			}
			else{
				sql+=" where "+SearchType+" like '%"+SearchContent+"%'";			
			}
		}
	}
	if(special.compareTo("0")==0||special.compareTo("1")==0){
		sql+=" and special="+special;			
	}	
	sql+=" and ProjectID='"+ProjectID+"' "+ sql_aux;
}
else{
	sql="select * from NodeList";	
	sql+=" where ProjectID='"+ProjectID+"' "+ sql_aux;
}
sql+=" order by nodepath asc"; //touchtime
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
<form method="POST" action="filter.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="21" align=left><span class="zi"><b>
    Navigation(<font color=red><B>Coverage: Deepth2=<%=iCoverage2%>%,Deepth3=<%=iCoverage3%>%</B></font>):&nbsp;&nbsp;&nbsp;&nbsp;
    <a href='filter.jsp?ProjectID=<%=ProjectID%>'>All</a>
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
     	&nbsp;&nbsp;<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=middleNodePath%>'><%=middleNodePath%></a>
     	<%
    }
    %>
    </b>&nbsp;&nbsp;&nbsp;&nbsp;
    <%
    if(MenuHref.compareTo("")!=0){
    %>
    <a target=_blank href="<%=MenuHref%>"><font color=blue>RootLink</font></a>
    <%
    }
    %>
    </span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="21">
	    <SELECT name=special size=1 class="tb">
	        <OPTION value="1">Special</OPTION>
		<OPTION value="0">Not Special</OPTION>
		<OPTION value="2">All</OPTION>
	    </SELECT>&nbsp;
	    <SELECT name=SearchType size=1 class="tb">
	    	<OPTION value="NodePath">NodePath</OPTION>	
		<OPTION value="LevelID">LevelID</OPTION>
		<OPTION value="seq">seq</OPTION>
		<OPTION value="tagName">tag</OPTION>
		<OPTION value="nodeType">type</OPTION>	
		<OPTION value="nodeClass">class</OPTION>
		<OPTION value="nodeName">name</OPTION>
		<OPTION value="nodeValue">value</OPTION>
		<OPTION value="onclick">onclick</OPTION>	
		<OPTION value="onkeydown">onkeydown</OPTION>
		<OPTION value="nodeId">id</OPTION>
		<OPTION value="nodeHref">href</OPTION>
	    </SELECT>&nbsp;    
	<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="21"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"><div align="center">Select</div></td>
    <td height="25"><div align="center">Depth 1</div></td>
    <td height="25"><div align="center">Depth 2</div></td>
    <td height="25"><div align="center">Depth 3</div></td>
    <td height="25"><div align="center">Depth 4</div></td>
    <td height="25"><div align="center">Depth 5</div></td>
    <td height="25"><div align="center">Depth 6+</div></td>
    <td height="25"><div align="center">text</div></td>  
    <td height="25"><div align="center">tag</div></td>    
    <td height="25"><div align="center">PageContainer</div></td>
    <td height="25"><div align="center">InputValue</div></td>
    <td height="25"><div align="center">type</div></td>
    <td height="25"><div align="center">class</div></td> 
    <td height="25"><div align="center">name</div></td>
    <td height="25"><div align="center">title</div></td>
    <td height="25"><div align="center">DOMType</div></td>    
    <td height="25"><div align="center">childNum</div></td>  
    <td height="25"><div align="center">Screen</div></td>

   
    
  </tr>

<%  
  int iLevelID=0;
  iCount=0;nCount=0;
  int iSpecial=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	ListID=RS.getString("ListID");
	  	RootURLNodePath=RS.getString("RootURLNodePath");
	  	LevelID=RS.getString("LevelID");
	  	iLevelID=Integer.parseInt(LevelID);
	  	seq=RS.getString("seq");
	  	Feature=RS.getString("Feature");
	  	TagOrURL=RS.getString("TagOrURL");
	  	uniqueObject=RS.getString("uniqueObject");
	  	vector=RS.getString("vector");
	  	NodePath=RS.getString("NodePath");
	  	ParentFilter=RS.getString("ParentFilter");
	  	ParentFilter=ParentFilter.replace('&','\'');
	  	PageFilter=RS.getString("PageFilter");
	  	PageFilter=PageFilter.replace('&','\'');
	  	PageContainer=RS.getString("PageContainer");
	  	PageContainer=PageContainer.replace('&','\'');
	  	if(PageContainer.compareTo("")==0){
	  		PageContainer="//body";
	  	}
	  	i_name=PageFilter.indexOf("name_");
	  	if(i_name!=-1){
	  		FilterName=PageFilter.substring(i_name+5);
	  	}else{
	  		FilterName="NONE";
	  	}	  	
	  	get_special=RS.getString("special");
	  	if(get_special.compareTo("1")==0){get_special="special";}
	  	DOMType=RS.getString("DOMType");
	  	if(DOMType.compareTo("HTML")==0){DOMType="";}
	  	clickable=RS.getString("clickable");
	  	if(clickable.compareTo("1")==0){clickable="clickable";}
	  	branch=RS.getString("branch");
	  	childNum=RS.getString("childNum");
	  	if(branch.compareTo("1")==0){branch="branch";}
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
	  	
	  	
	  	if(get_special.compareTo("special")==0){ //&&DOMType.compareTo("1")==0&&clickable.compareTo("1")==0){
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
	  		bgcolor="green"; //update nodelist set touchonce=0,capture=0,clickfail=0 where projectid='0000954' and levelid=4;
	  	}
	  	clickfail=RS.getString("clickfail");
	  	if(clickfail.compareTo("1")==0){
	  		clickfail="clickfail";
	  		bgcolor="yellow";
	  	}
	  	clicktimes=RS.getString("clicktimes");
	  	tagName=RS.getString("tagName");
	  	screenName=RS.getString("screenName");
	  	nodeId=RS.getString("nodeId");
	  	nodeClass=RS.getString("nodeClass");
	  		if(nodeClass!=null){nodeClass=nodeClass.replace('&', '\'');} else { nodeClass=""; }
	  	nodeType=RS.getString("nodeType");
	  	nodeTitle=RS.getString("nodeTitle");
	  	touchonce=RS.getString("touchonce");
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
		if(Feature.compareTo("")!=0){
			Feature=Feature+"/";
		}
		newStringNumber=RS.getString("newStringNumber");
		
%>

    <tr bgcolor="#AEF2F4"> 
    <td height="25" bgcolor="<%=bgcolor%>"> 
      <div align="center">
      <INPUT name=now_dn type=checkbox value="<%=ListID%>">
      </div>
      </td>
    <td height="25" bgcolor="<%=bgcolor%>"><div align="left">
    	<%if(iLevelID==1){%>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    	<%}%>
    </div></td>
     <td height="25" bgcolor="<%=bgcolor%>"><div align="left">
    	<%if(iLevelID==2){%>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    	<%}%>
    </div></td>
     <td height="25" bgcolor="<%=bgcolor%>"><div align="left">
    	<%if(iLevelID==3){%>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    	<%}%>
    </div></td>
     <td height="25" bgcolor="<%=bgcolor%>"><div align="left">
    	<%if(iLevelID==4){%>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    	<%}%>
    </div></td>
    <td height="25" bgcolor="<%=bgcolor%>"><div align="left">
    	<%if(iLevelID==5){%>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    	<%}%>
    </div></td>
    <td height="25" bgcolor="<%=bgcolor%>"><div align="left">
    	<%if(iLevelID>5){%>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>'><%=NodePath%></a>
    	<%}%>
    </div></td>
    <td height="25"><div align="center"><%=nodeText%></div></td>  
    <td height="25"><div align="center">[<%=tagName%>]
    <%
    if(TagOrURL.compareTo("URL")==0){
    %>
    <BR>(<a target=_blank href='<%=nodeHref%>'><font color='blue'><%=TagOrURL%></font></a>)
    <%}%>
    </div></td>
    <td height="25"><div align="center">
    <a href="../develop/update_container.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>"><%=PageContainer%></a>
    </div></td>
    <td height="25"><div align="center">
    <a href="../develop/input_node.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>"><IMG alt='InputValue' border=0 name=22.1.1.6.1 src="img/inputvalue.gif"></a>
    </div></td>
    <td height="25"><div align="center"><%=nodeType%></div></td>
    <td height="25"><div align="center">[<%=nodeClass%>]</div></td>
    <td height="25"><div align="center"><%=nodeName%></div></td>
     <td height="25"><div align="center"><%=nodeTitle%></div></td>
    <td height="25"><div align="center"><%=DOMType%></div></td>    
    <td height="25"><div align="center"><%=childNum%>
    <%if(bSpecial){
    	String runcolor=""; String sDisplay="";
    	if(childNum.compareTo("0")==0){
    		runcolor="red";
    		sDisplay="Debug";
    	}else{
    		runcolor="blue";
    	}
    	%>
    	<BR><font color='RED'><%=sDisplay%></font>
    	<a href='filter.jsp?ProjectID=<%=ProjectID%>&node=<%=NodePath%>&debugging=quick'><font color='<%=runcolor%>'></font></a>
    <%}%>
    </div></td>
    <td height="25"><div align="center">
    <a target=_blank href='../screen/<%=Feature%><%=screenName%>'><%=screenName%></a></div></td>
    
      
    
  </tr>
  <%
  	 }  	
  }
  RS.close();
  workM.close();
  %>
   <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="21"> 
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

