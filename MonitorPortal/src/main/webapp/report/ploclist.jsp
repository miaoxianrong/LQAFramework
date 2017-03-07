<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>

<title>Ploc String</title>

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

%>
<%
String PlocID,KeyID,Content,NodePath,screenName,Feature;
String sql_aux=" and PlocID>0 ";sql_aux=""; 
Feature="";screenName="";
workM.getConnection();
String ProjectID="12984";String bgcolor="#F8FDFF";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}

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
//
String CurPage;
int iCurPage=0;
int iOnePage=500;
int iTotalRecords=0;
int iTotalPages=0;
iTotalRecords=0;
if(bSearch){
	iOnePage=500;
	sql="select count(*)  from PlocTable";
	if(SearchContent.compareTo("")==0){
		sql+=" where PlocID>0 ";
	}
	else{
		if(SearchType.equals("LevelID")||SearchType.equals("seq")||SearchType.equals("clickfail")){
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
	sql+=" and ProjectID='"+ProjectID+"' "+ sql_aux;
}
else{
	sql="select count(*) from PlocTable";	
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
	sql="select *  from PlocTable";	
	if(SearchContent.compareTo("")==0){
		sql+=" where PlocID>0 ";
	}
	else{
		if(SearchType.equals("LevelID")||SearchType.equals("seq")||SearchType.equals("clickfail")){
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
	sql+=" and ProjectID='"+ProjectID+"' "+ sql_aux;
}
else{
	sql="select * from PlocTable";	
	sql+=" where ProjectID='"+ProjectID+"' "+ sql_aux;
}
sql+=" order by PlocID desc"; 
//out.print(sql);
//if(true){
RS =workM.getURL(sql); 
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="700" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="ploclist.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="3" align=center><span class="zi"><b>PLOCString List</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3">
	    <SELECT name=SearchType size=1 class="tb">
	    	<OPTION value="KeyID">KeyID</OPTION>	
		<OPTION value="Content">PLOCString</OPTION>
		<OPTION value="NodePath">NodePath</OPTION>		
	    </SELECT>&nbsp;    
	<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#C3DBE8"> 
    <td height="25"  align=center><span class="zi"><b>KeyID</b></span></td>       
    <td height="25"  align=center><span class="zi"><b>Screen</b></span></td>
    <td height="25"  align=center><span class="zi"><b>PLOCString</b></span></td>
  </tr>

<%  
  iCount=0;nCount=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	KeyID=RS.getString("KeyID");		  	
	  	Content=RS.getString("Content");
	  	NodePath=RS.getString("NodePath"); 	 
	  	screenName=NodePath+".png";
%>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"><div align="right">
    <a target='_blank' href='plocshow.jsp?NodePath=<%=NodePath%>&KeyID=<%=KeyID%>'>
    <%=KeyID%>
    </a>
    </div></td>
    <td height="25"><div align="left">
    <a target='rightFrame' href='guiscreen.jsp?ProjectID=<%=ProjectID%>&Feature=<%=Feature%>&screenName=<%=screenName%>'><%=screenName%></a>
   
    </div></td>
    <td height="25"><div align="left">
    <a target='_blank' href='../web/<%=NodePath%>.html'>
    <%=Content%>
    </a>
    </div></td>
    
  </tr>
  <%
  	 }  	
  }
  RS.close();
  workM.close();
  %>


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

