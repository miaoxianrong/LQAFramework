<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>

<title>Found (by automation)</title>
<!--META http-equiv=Content-Type content="text/html; charset=UTF-8"-->
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
String UITextID,KeyID,Content,NodePath,mapped,AllKeyID; 
String sql_aux=" and UITextID>0 ";
sql_aux=""; 
String get_mapped=request.getParameter("mapped");
if(get_mapped!=null){
	if(get_mapped.compareTo("0")==0){
		sql_aux+=" and mapped=0";
	}
	if(get_mapped.compareTo("1")==0){
		sql_aux+=" and mapped=1";
	}
}
String NewFuzzyAll=request.getParameter("NewFuzzyAll");
if(NewFuzzyAll!=null){
	if(NewFuzzyAll.compareTo("New")==0){
		sql_aux+=" and NewString=1";
	}
	if(NewFuzzyAll.compareTo("Fuzzy")==0){
		sql_aux+=" and FuzzyString=1";
	}
	if(NewFuzzyAll.compareTo("NewFuzzy")==0){
		sql_aux+=" and NewFuzzy=1";
	}
}
workM.getConnection();
String sql;
String ProjectID="12984";String bgcolor="#F8FDFF";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
String NewString="";
String FuzzyString="";
String NewFuzzy="";
/*
	AllKeyID=",";
	sql="select KeyID from UIText where ProjectID='"+ProjectID+"' ";
	ResultSet rs_get_all=workM.getURL(sql); 
	while(rs_get_all.next()){
		KeyID=rs_get_all.getString("KeyID");
		AllKeyID+=KeyID+",";
	}
	rs_get_all.close();
	
	ResultSet rs_search=null; boolean bMapped=false;
	StringTokenizer stArray=new StringTokenizer(AllKeyID,",");
	while(stArray.hasMoreTokens()){
		KeyID = stArray.nextToken();
		sql="select * from I18NFile where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"' ";
		rs_search=workM.getURL(sql); 
		if(rs_search.next()){
			NewString=rs_search.getString("NewString");
			FuzzyString=rs_search.getString("FuzzyString");
			NewFuzzy=rs_search.getString("NewFuzzy");
			bMapped=true;
			mapped="1";		
		}else{
			bMapped=false;
			mapped="0";
		}
		rs_search.close();
		if(bMapped){
			sql="update UIText set mapped=1 ";
			sql+=",NewString="+NewString;
			sql+=",FuzzyString="+FuzzyString;
			sql+=",NewFuzzy="+NewFuzzy;
			sql+=" where KeyID='"+KeyID+"' and ProjectID='"+ProjectID+"' ";
			workM.updateSQL(sql);
		}
	}
	rs_search=null;
*/
int iCount;int nCount; ResultSet RS;
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
	sql="select count(*)  from UIText";
	if(SearchContent.compareTo("")==0){
		sql+=" where UITextID>0 ";
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
	sql="select count(*) from UIText";	
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
	sql="select *  from UIText";	
	if(SearchContent.compareTo("")==0){
		sql+=" where UITextID>0 ";
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
	sql="select * from UIText";	
	sql+=" where ProjectID='"+ProjectID+"' "+ sql_aux;
}
sql+=" order by UITextID desc"; 
//out.print(sql);
//if(true){
RS =workM.getURL(sql); 
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="800" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="tool_found.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="1" align=center><span class="zi"><b>Found (by automation)</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="1">
    	    Mapping:
    	    <SELECT name=mapped size=1 class="tb">
	    		<OPTION value="All">---ALL---</OPTION>	
			<OPTION value="1">mapped</OPTION>
			<OPTION value="0">not mapped</OPTION>
			
	    </SELECT>&nbsp;  
    	    New/Fuzzy:
    	    <SELECT name=NewFuzzyAll size=1 class="tb">
	    		<OPTION value="All">---ALL---</OPTION>	
			<OPTION value="New">New</OPTION>
			<OPTION value="Fuzzy">Fuzzy</OPTION>
			<OPTION value="NewFuzzy">New/Fuzzy</OPTION>
	    </SELECT>&nbsp;  
	    PUID/PLOCString:
	    <SELECT name=SearchType size=1 class="tb">
	    	<OPTION value="KeyID">PUID</OPTION>	
		<OPTION value="Content">PLOCString</OPTION>
	    </SELECT>&nbsp;    
	<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="1"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#C3DBE8"> 
    <td height="25"  align=center><span class="zi"><b>PUID</b></span></td>      
   
  </tr>

<%  
  iCount=0;nCount=0;
  String StringType="";
  while(RS.next())
  {
  	  nCount++;
  	  StringType="";
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	KeyID=RS.getString("KeyID");
	  	mapped=RS.getString("mapped");
	  	NewString=RS.getString("NewString");
	  	FuzzyString=RS.getString("FuzzyString");
	  	if(NewString.compareTo("1")==0){
	  		StringType="New";
	  	}
	  	if(FuzzyString.compareTo("1")==0){
	  		StringType="Fuzzy";
	  	}
	  	Content=RS.getString("Content");
	  	if(mapped.compareTo("0")==0){
		    	bgcolor="#A8FDFF";
		}else{
			bgcolor="#F8FDFF";
		}
	  	
%>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"><div align="right">    
    <%=KeyID%>  
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

