<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>

<title>Total (i18n file)</title>

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
String I18NFileID,UnitID,KeyID,Content,NodePath,mapped,Status,LStatus,NotFoundReason,FileName,FileNameListID; 
String NewString, FuzzyString, StringType,NotScopeConfirmedByDEV,CommentByDev,InScope;
StringType="";
String sql_aux=" and I18NFileID>0 ";
sql_aux=""; 
String IsBefore=request.getParameter("IsBefore");
//out.print("IsBefore="+IsBefore);
//IsBefore="0";
//if(IsBefore==null&&IsBefore.compareTo("-1")!=0){
	//IsBefore="0";
//}
String getFileNameListID=request.getParameter("FileNameListID");
if(getFileNameListID!=null){
	if(getFileNameListID.compareTo("-1")!=0){
		sql_aux+=" and FileNameListID='"+getFileNameListID+"'";
	}
}else{
	getFileNameListID="-1";
}
String getInScope=request.getParameter("InScope");
if(getInScope!=null){
	if(getInScope.compareTo("0")==0){
		sql_aux+=" and InScope=0";
	}
	if(getInScope.compareTo("1")==0){
		sql_aux+=" and InScope=1";
	}	
}
String get_mapped=request.getParameter("mapped");
if(get_mapped!=null&&get_mapped.compareTo("-1")!=0){
	if(get_mapped.compareTo("0")==0){
		sql_aux+=" and mapped=0";
	}
	if(get_mapped.compareTo("1")==0){
		sql_aux+=" and mapped=1";
	}	
}
//out.print("sql_aux="+sql_aux+"<BR>");

String ReasonID=request.getParameter("ReasonID");
if(ReasonID!=null&&ReasonID.compareTo("-1")!=0){
	if(ReasonID.compareTo("-1")==0){
		if(ReasonID.compareTo("0")==0){
			sql_aux+=" and ReasonID="+ReasonID;
		}
	}else{
		sql_aux+=" and ReasonID="+ReasonID;
	}
}
String NewFuzzyAll=request.getParameter("NewFuzzyAll");
if(NewFuzzyAll!=null&&NewFuzzyAll.compareTo("-1")!=0){
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
//out.print("sql_aux="+sql_aux+"<BR>");
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
	sql="select count(*)  from I18NFile";
	if(SearchContent.compareTo("")==0){
		sql+=" where I18NFileID>0 ";
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
	sql="select count(*) from I18NFile";	
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
	sql="select *  from I18NFile";	
	if(SearchContent.compareTo("")==0){
		sql+=" where I18NFileID>0 ";
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
	sql="select * from I18NFile";	
	sql+=" where ProjectID='"+ProjectID+"' "+ sql_aux;
}
sql+=" order by FileName asc"; 
//out.print(sql);
//if(true){
String everyFileNameListID="";
String allFileNameListID=",";

ResultSet rs_detect=workM.getURL(sql); 
while(rs_detect.next()){
	everyFileNameListID=rs_detect.getString("FileNameListID");
	if(allFileNameListID.indexOf(","+everyFileNameListID+",")==-1){
		allFileNameListID=allFileNameListID+everyFileNameListID+",";
	}else{
		
	}
}
rs_detect.close();
rs_detect=null;
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="900" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="total.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <input type=hidden name='InScope' value='<%=getInScope%>'>
  <input type=hidden name='mapped' value='<%=get_mapped%>'>
  <input type=hidden name='ReasonID' value='<%=ReasonID%>'>  
  <input type=hidden name='NewFuzzyAll' value='<%=NewFuzzyAll%>'>
  <input type=hidden name='IsBefore' value='<%=IsBefore%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="5" align=center><span class="zi"><b>Total (i18n file)</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="5">    	   
	    Resource file:
    	    <SELECT name=FileNameListID size=1 class="tb">    	    	
    	    	<OPTION value="-1">----------------------------ALL----------------------------</OPTION>
    	    	<%
    	    	String EachFileNameListID="";
    	    	String EachFileName="";
    	    	String sql_filename="select * from FileNameList where ProjectID='"+ProjectID+"'"; 
    	    	ResultSet rs_filename =workM.getURL(sql_filename); 
    	    	while(rs_filename.next()){
    	    		EachFileNameListID=rs_filename.getString("FileNameListID");
	  		EachFileName=rs_filename.getString("FileName");
	  		if(allFileNameListID.indexOf(","+EachFileNameListID+",")!=-1){
    	    	%>
			<OPTION value="<%=EachFileNameListID%>"><%=EachFileName%></OPTION>
		<%
			}
		}
		rs_filename.close();
		rs_filename=null;
		%>
		
	    </SELECT>&nbsp;  
	    PUID/PLOCString:
	    <SELECT name=SearchType size=1 class="tb">	    	
		<OPTION value="Content">PLOCString</OPTION>
		<OPTION value="KeyID">PUID</OPTION>	
	    </SELECT>&nbsp;    
	<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="5"> 
    		<a href="total.jsp?FileNameListID=-1&InScope=<%=getInScope%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&IsBefore=<%=IsBefore%>">
    			<%
    	    		if(getFileNameListID.compareTo("-1")==0){
    	    			out.print("<b><font color=purple>");
    	    		}
    	    		%>
    			Resource file list(all)
    			<%
    	    		if(getFileNameListID.compareTo("-1")==0){
    	    			out.print("</font></b>");
    	    		}
    	    		%>
    		</a><BR>
    		<%
     		rs_filename =workM.getURL(sql_filename); 
    	    	while(rs_filename.next()){
    	    		EachFileNameListID=rs_filename.getString("FileNameListID");
	  		EachFileName=rs_filename.getString("FileName");
	  		if(allFileNameListID.indexOf(","+EachFileNameListID+",")!=-1){	  		
	    	    		%>
	    	    		<a target=_blank href="total.jsp?FileNameListID=<%=EachFileNameListID%>&InScope=<%=getInScope%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&IsBefore=<%=IsBefore%>">
	    	    		<%
	    	    		if(getFileNameListID.compareTo(EachFileNameListID)==0){
	    	    			out.print("<b><font color=purple>");
	    	    		}
	    	    		%>
	    	    		<%=EachFileName%>
	    	    		<%
	    	    		if(getFileNameListID.compareTo(EachFileNameListID)==0){
	    	    			out.print("</font></b>");
	    	    		}
	    	    		%>
	    	    		</a>    
	    	    		<BR>
			<%
			}
		}
		rs_filename.close();
		rs_filename=null;
     		%>
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="5"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#C3DBE8"> 
    <td height="25"  align=center><span class="zi"><b>PUID</b></span></td>       
    <td height="25"  align=center><span class="zi"><b>PLOCString</b></span></td>
	<td height="25" width="80" align=center><span class="zi"><b>In Scope? </b></span></td>
	<td height="25" width="150" align=center><span class="zi"><b>Comment by Developer</b></span></td>
  </tr> 

<%  
  RS =workM.getURL(sql);  
  iCount=0;nCount=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	I18NFileID=RS.getString("I18NFileID");	
	  	KeyID=RS.getString("KeyID");	
		FileName=RS.getString("FileName");
		FileNameListID=RS.getString("FileNameListID");
	  	Content=RS.getString("Content");
	  	UnitID=RS.getString("UnitID");
	  	Status=RS.getString("Status");
	  	LStatus=RS.getString("LStatus");
	  	NewString=RS.getString("NewString");
	  	FuzzyString=RS.getString("FuzzyString");
	  	StringType="";
	  	if(NewString.compareTo("1")==0){
	  		StringType="New";
	  	}
	  	if(FuzzyString.compareTo("1")==0){
	  		StringType="Fuzzy";
	  	}
	  	mapped=RS.getString("mapped");
	  	NotFoundReason=RS.getString("NotFoundReason");
	  	if(mapped.compareTo("0")==0){
		    	bgcolor="#A8FDFF";
		}else{
			bgcolor="#F8FDFF";
		}		
		//NotScopeConfirmedByDEV=RS.getString("NotScopeConfirmedByDEV");
		InScope=RS.getString("InScope");
		//InScope=RS.getString("InScope");
		CommentByDev=RS.getString("CommentByDev");
		CommentByDev=CommentByDev.replace(';',',');
		CommentByDev=CommentByDev.replace('"', '\'');
		if(InScope.compareTo("0")==0){
			bgcolor="#F8FDFF";
		}else{
			bgcolor="#A8FDFF";
		}
%>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"><div align="right">    
    <%=KeyID%>
    </div></td>
	
    <td height="25"><div align="left">    
    <b>String</b> = <%=Content%><BR>
    <b>ID</b> = <%=UnitID%><BR>
    <b>File</b> = <a target=_blank href="total.jsp?FileNameListID=<%=FileNameListID%>&InScope=<%=InScope%>&mapped=<%=mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&IsBefore=<%=IsBefore%>"><%=FileName%></a>
    </div></td>
	
    <td height="25"><div align="center">   
    <%    
    if(InScope.compareTo("0")==0){
    	%>
    	Yes
    	<%
    }else{
    	%>
    	No
    	<%
    }
    %>
    </div></td>
    <td height="25"><div align="left">
    <%    
    if(IsBefore.compareTo("0")==0){
	    if(mapped.compareTo("0")==0){    
	    	if(CommentByDev.compareTo("")==0){ 
	    	%>
	    		<a href="../develop/confirm_by_dev.jsp?InScope=<%=InScope%>&I18NFileID=<%=I18NFileID%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&IsBefore=<%=IsBefore%>"><IMG alt='confirm'  border=0 src="img/comment.gif"></a>
		<%
		}else{
		%>
			<%=CommentByDev%>-><a href="../develop/confirm_by_dev.jsp?InScope=<%=InScope%>&I18NFileID=<%=I18NFileID%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&IsBefore=<%=IsBefore%>"><font color=blue>update</font></a>
		<%    	
		}
	    }else{
	    	%>
	    		Found and captured with one screen
	    	<%
	    	//out.println(NotFoundReason);
	    }
    }
    %>
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

