<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<script type="text/javascript" src="./js/selection_5.js"></script>

<title>PLOC Strings(Drop file)</title>

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
String DropFileID,UnitID,KeyID,Content,NodePath,mapped,Status,LStatus,NotFoundReason,FileName,FileNameListID; 
String NewString, FuzzyString, StringType,NotScopeConfirmedByDEV,CommentByDev,InScope,IsBefore;
StringType="";
String eachReasonID="";
String sql_aux=" and DropFileID>0 ";
sql_aux=""; 
String PreAnalysis=request.getParameter("PreAnalysis");
//out.print("PreAnalysis="+PreAnalysis);
//PreAnalysis="0";
if(PreAnalysis==null&&PreAnalysis.compareTo("-1")!=0){
	PreAnalysis="0";
}
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
int iOnePage=1000;
int iTotalRecords=0;
int iTotalPages=0;
iTotalRecords=0;
if(bSearch){
	iOnePage=1000;
	sql="select count(*)  from DropFile";
	if(SearchContent.compareTo("")==0){
		sql+=" where DropFileID>0 ";
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
	sql="select count(*) from DropFile";	
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
	sql="select *  from DropFile";	
	if(SearchContent.compareTo("")==0){
		sql+=" where DropFileID>0 ";
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
	sql="select * from DropFile";	
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
<table width=95% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="all.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <input type=hidden name='InScope' value='<%=getInScope%>'>
  <input type=hidden name='mapped' value='<%=get_mapped%>'>
  <input type=hidden name='ReasonID' value='<%=ReasonID%>'>  
  <input type=hidden name='NewFuzzyAll' value='<%=NewFuzzyAll%>'>
  <input type=hidden name='PreAnalysis' value='<%=PreAnalysis%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="6" align=center><span class="zi"><b>PLOC Strings(Drop file)</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="6">    	   
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
    <td height="25" colspan="6"> 
    		<a href="all.jsp?FileNameListID=-1&InScope=<%=getInScope%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&PreAnalysis=<%=PreAnalysis%>">
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
	    	    		<a target=_blank href="all.jsp?FileNameListID=<%=EachFileNameListID%>&InScope=<%=getInScope%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&PreAnalysis=<%=PreAnalysis%>">
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
    <td height="25" colspan="6"> 
      <div align="left">Total <%=iTotalRecords%> <%=iCurPage%>/<%=iTotalPages%> Page Turn to <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">Page</a> </div></td>
  </tr>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" width="50"> 
      <div align="center"><b>Select</div></td>
    <td height="25" align=center><span class="zi"><b>No.</b></span></td>       
    <td height="25" align=center><span class="zi"><b>New feature string</b></span></td>
    <td height="25" width="80" align=center><span class="zi"><b>Scope?</b></span></td>
    <td height="25" width="250" align=center><span class="zi"><b>Dev's comment(steps)</b></span></td>
    <td height="25" width="80" align=center><span class="zi"><b>Action</b></span></td>
  </tr> 

<%  
  RS =workM.getURL(sql);  
  iCount=0;nCount=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	DropFileID=RS.getString("DropFileID");	
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
	  	eachReasonID=RS.getString("ReasonID");
	  	NotFoundReason=RS.getString("NotFoundReason");
	  	if(NotFoundReason.compareTo("")==0){
	  		NotFoundReason="N/A, not filtered";
	  	}
	  	if(mapped.compareTo("0")==0){
		    	bgcolor="#A8FDFF";
		}else{
			bgcolor="#F8FDFF";
		}		
		//NotScopeConfirmedByDEV=RS.getString("NotScopeConfirmedByDEV");
		InScope=RS.getString("InScope");
		IsBefore=RS.getString("IsBefore");
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
    <td height="25"> 
      <div align="center"><INPUT name=now_dn type=checkbox value="<%=DropFileID%>"></div></td>   
    <td height="25"><div align="center">    
    <%=DropFileID%>
    </div></td>
    <td height="25"><div align="left">   
    <b>String</b> = <font color=purple><%=Content%></font><BR>
    <b>ID</b> = <%=UnitID%><BR>
    <b>File</b> = <a target=_blank href="all.jsp?FileNameListID=<%=FileNameListID%>&InScope=<%=InScope%>&mapped=<%=mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&PreAnalysis=<%=PreAnalysis%>"><%=FileName%></a><BR>
    <b>PUID</b> = <%=KeyID%> <BR>
    <b>Filter</b> = <%=NotFoundReason%> 
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
    //if(PreAnalysis.compareTo("0")==0){        
	    //if(mapped.compareTo("0")==0){ 
		    	if(CommentByDev.compareTo("")==0){ 
			    //add comment
			}else{
				if(PreAnalysis.compareTo(IsBefore)==0){
					%>
					<%=CommentByDev%>
					<%  
					//update
				}else{
					if(PreAnalysis.compareTo("1")==0){
						%>
						<font color='grey'>Post-analysis comment:</font><BR>
						<%
					}else{
						%>
						<font color='grey'>Pre-analysis comment:</font><BR>
						<%
					}
					%>
					<font color='grey'><%=CommentByDev%></font>
					<%
				}  	
			}
		
	    //}else{
	    	%>
	    		
	    	<%
	    	//out.println("Found and captured with one screen");
	    //}
    //}
    %>
    </div></td>
    <td height="25"><div align="center">
    <%
    //if(mapped.compareTo("0")==0){ 
    	if(CommentByDev.compareTo("")==0){ 
    		%>
		<a href="../develop/comment_by_dev.jsp?InScope=<%=InScope%>&DropFileID=<%=DropFileID%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&PreAnalysis=<%=PreAnalysis%>"><IMG alt='confirm'  border=0 src="img/comment.gif"></a>
		<%
	}else{
		if(PreAnalysis.compareTo(IsBefore)==0){
			%>
			<a href="../develop/comment_by_dev.jsp?InScope=<%=InScope%>&DropFileID=<%=DropFileID%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&PreAnalysis=<%=PreAnalysis%>"><font color=blue>update</font></a>
			<%  
		}else{
			%>
			<a href="../develop/comment_by_dev.jsp?InScope=<%=InScope%>&DropFileID=<%=DropFileID%>&mapped=<%=get_mapped%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=<%=NewFuzzyAll%>&ProjectID=<%=ProjectID%>&PreAnalysis=<%=PreAnalysis%>"><font color=blue>update</font></a>
			<%  
		}
    	}
    //}
    %>
    </div></td>
  </tr>
  <%
  	 }  	
  }
  RS.close();
  workM.close();
  %>
  <tr bgcolor="#F8FDFF"> 
    <td height="25"><div align="center"><A onclick=SelectResult() style="CURSOR: hand"><IMG border=0 name=XuanZe1 src="images/icon_2_1.gif"></A></div></td>
    <td height="25" colspan="4"><div align="center">
    	
    </div></td>
    <td height="25"><div align="center">
    <A href="about:blank" onclick="nn=Judge(Maintain); if (nn>0) {if (confirm('Confirm: Add comments for multiple selection?')){Maintain.action='../develop/comment_mul.jsp?OrderValue=<%=iCurPage%>';OnSubmit(Maintain);Maintain.submit(); return false;} else {return false;}}  else {alert('Please select String');return false;}"><IMG alt='select'  border=0 name=22.1.1.6.1 src="img/mulcomment.gif"></A>
    </div></td>
  </tr>
<INPUT name=name_array type=hidden>
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

