<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>

<title>Curiosity Monitor</title>
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
String ListID,tagName,nodeId,screenName,nodeClass,nodeType,nodeTitle,childNum,nodeText,nodeHref,PageFilter;
String nodeName,nodeValue,ProjectID,LevelID,RootURLNodePath,seq,Active,onclick,onkeydown,Feature;
String get_special,DOMType,visible,branch,leaf,vector,uniqueObject,NodePath,TagOrURL,newPloc,plocNumber;
String mainFeature,getSubMenu,mainMenuFilter,includerFilter,excluderFilter,noLessLeafFilter,forceSetBranchFilter,noMoreUniqueFilter;
boolean bSpecial=false;String bgcolor="#F8FDFF";String playonce="";String touchtime="";String clickfail="";
%>
<%

RootURLNodePath="4"; 
LevelID="2";
String sql_aux=" and screenName<>'' and iHardcode=1 ";
workM.getConnection();

ProjectID="12984";Active="0";

String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}

int iTotalScreenNum=0;
String sql2="select count(*) from NodeList where touchonce=1 and ProjectID='"+ProjectID+"'";
ResultSet rs2=workM.getSQL(sql2);
if(rs2.next()){
	iTotalScreenNum=rs2.getInt(1);
}
if(iTotalScreenNum==0){
	iTotalScreenNum=1;
}
rs2.close();

int iExecutedNode=0;
String sql_ExecutedNode="select count(*) from NodeList where iHardcode>0 and ProjectID='"+ProjectID+"'";
ResultSet rs_ExecutedNode=workM.getSQL(sql_ExecutedNode);
if(rs_ExecutedNode.next()){
	iExecutedNode=rs_ExecutedNode.getInt(1);
}
rs_ExecutedNode.close();

int iHardcodeNum=0;
sql2="select count(*) from NodeList where iHardcode=1 and ProjectID='"+ProjectID+"'";
rs2=workM.getSQL(sql2);
if(rs2.next()){
	iHardcodeNum=rs2.getInt(1);
}
rs2.close();

int iProgress = 100*iExecutedNode/iTotalScreenNum;
int iCoverage = 100*iHardcodeNum/iTotalScreenNum;

int iHardcodeStringNum=0;
sql2="select count(*) from UniqueStringList where ProjectID='"+ProjectID+"'";
rs2=workM.getSQL(sql2);
if(rs2.next()){
	iHardcodeStringNum=rs2.getInt(1);
}
rs2.close();


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
		sql+=" where ListID>0 ";
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
sql+=" order by NodePath asc"; //touchtime
//sql+=" order by touchtime desc"; //touchtime
//out.print(sql);
//if(true){
RS =workM.getURL(sql); 
%>
<table width="100%" border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="guilist.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="3" align=left><div align="left">
    
    Total:<font color=blue><B><%=iTotalScreenNum%></B></font>
    <% if(false){%>
    Running progress:<font color=blue><B><%=iProgress%>%</B></font>
    
    <%}%>
    <BR>
    Screen:<font color=red><B><%=iHardcodeNum%></B></font>, 
    Coverage:<font color=red><B><%=iCoverage%>%</B></font>
    <BR>
    Total hard code strings:
    <a target='rightFrame' href='hardcode_screen_string.jsp?ProjectID=<%=ProjectID%>&NodePath=000&screenName=000.png'>
    <font color=red><B><%=iHardcodeStringNum%> ------>></B></font></a>
    </div></td>
  </tr>  
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"><div align="center">Select</div></td>
    <td height="25"><div align="center">Screen</div></td>
    <td height="25"><div align="center">Comment</div></td>
  </tr>

<%  
  int iLevelID=0;
  iCount=0;nCount=0;
  int iSpecial=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {//·ÖÒ³
	  	ListID=RS.getString("ListID");
	  	NodePath=RS.getString("NodePath");	  	
	  	screenName=NodePath+".png";	  	
%>

  <tr bgcolor="<%=bgcolor%>"> 
    <td height="25"> 
      <div align="center"><INPUT name=now_dn type=checkbox value="<%=ListID%>"></div>
    </td>
     <td height="25"><div align="left">
    	<a target='rightFrame' href='hardcode_screen_string.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>&screenName=<%=screenName%>'><%=screenName%></a>
    </div></td>
     <td height="25"><div align="left">
    	
    </div></td>

  </tr>
  <%
  	 }  	
  }
  RS.close();
  workM.close();
  %>
   <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"> 
      <div align="left">Total nodes: <%=iSpecial%> </div></td>
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

