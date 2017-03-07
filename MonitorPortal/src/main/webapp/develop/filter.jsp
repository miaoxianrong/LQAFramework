<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />


<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<script type="text/javascript" src="./js/selection.js"></script>
<title>Filter List</title>
<script language="javascript">
<!--
function del()
{
  return confirm('Confirm delete?');
}

function checkvalue_1(theValue)                  
{                  
	var tsxx="Jump page";
	if (theValue=="")                  
	{                  
		alert("Pleasee input["+tsxx+"]");                  
		return false;                  
	}		                  
	else if (theValue.indexOf(" ")>=0)                  
	{                  
		alert("["+tsxx+"]Error! \n\n Please input integer number \n Not include space");                  
		return false;                  
	}                  
	else if (isNaN(theValue))                   
	{	                  
		alert("["+tsxx+"] Error! \n\n Please input integer number");                  
		return false;                  
	}                  
	return true;                  
}  


//-->
</script>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%
workM.getConnection(); 
String sql=""; ResultSet RS=null;
String ReasonID,NotFoundReason,Content;
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="00000000";
}
boolean bNotCommon=true;
if(ProjectID.compareTo("00000000")==0){
	bNotCommon=false;
}
if(bNotCommon){
	//sql="delete from ReasonList where ProjectID='00000265'";
	//workM.updateSQL(sql);
	boolean bImportCommon=false;
	sql="select * from ReasonList where ImportCommon=1 and ProjectID='"+ProjectID+"'";
	ResultSet rs_check=workM.getSQL(sql); 
	if(rs_check.next()){
		bImportCommon=false;
	}else{
		bImportCommon=true; 
	}
	rs_check.close();
	if(bImportCommon){
		String CommonReason="";
		String eachReason="";
		String eachNotFoundReason="";
		String eachContent="";
		sql="select * from ReasonList where ProjectID='00000000'";
		ResultSet rs_get=workM.getSQL(sql); 
		while(rs_get.next()){
			eachNotFoundReason=rs_get.getString("NotFoundReason");
			eachContent=rs_get.getString("Content");
			CommonReason+=eachNotFoundReason+"FENGEFU"+eachContent+" ";
		}
		rs_get.close();	
		StringTokenizer stArray=new StringTokenizer(CommonReason);
		int iFen=0;
		while(stArray.hasMoreTokens()){
			eachReason = stArray.nextToken();
			iFen=eachReason.indexOf("FENGEFU");
			if(iFen>0){
				eachNotFoundReason=eachReason.substring(0,iFen);			
				eachContent=eachReason.substring(iFen+7);
				sql="insert into ReasonList set ";
				sql=sql+"ProjectID='"+ProjectID+"'";
				sql=sql+",NotFoundReason='"+eachNotFoundReason+"'";
				sql=sql+",Content='"+eachContent+"'";
				sql=sql+",ImportCommon=1";
				workM.updateSQL(sql);
				//out.print(sql+"<BR>");
				//out.print("eachNotFoundReason="+eachNotFoundReason+" eachContent="+eachContent+"<BR>");
			}
			
		}
	}
}
%>
<%

int iCount;int nCount;
//search
String OrderSJ=request.getParameter("OrderSJ");
boolean bSearch = false;
String Search=request.getParameter("Search");
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
int iOnePage=200;//
int iTotalRecords=0;
int iTotalPages=0;
iTotalRecords=0;
if(bSearch){
	iOnePage=200;
	sql="select count(*) from ReasonList where ProjectID='"+ProjectID+"' ";
	if(SearchType.compareTo("RegDate")==0||SearchType.compareTo("ProjectDate")==0){
		sql+=" and "+SearchType+" <= '"+SearchContent+"'";		
	}
	else{
		sql+=" and "+SearchType+" like '%"+SearchContent+"%'";
	}
}
else{
	sql="select count(*) from ReasonList where ProjectID='"+ProjectID+"' ";
}
ResultSet RS1=null;

RS1 =workM.getSQL(sql); 
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
	sql="select *  from ReasonList where ProjectID='"+ProjectID+"' ";
	if(SearchType.compareTo("RegDate")==0||SearchType.compareTo("ProjectDate")==0){
		sql+=" and "+SearchType+" <= '"+SearchContent+"'";		
	}
	else{
		sql+=" and "+SearchType+" like '%"+SearchContent+"%'";
	}	 	
	sql=sql+" and ReasonID>0 order by "+SearchType+" "+OrderSJ;
}
else{
	sql="select * from ReasonList where ProjectID='"+ProjectID+"' ";
	sql=sql+" order by ReasonID asc";
}
RS =workM.getSQL(sql); 
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>

<table width="800" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="reason.jsp" name="Maintain" >

  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="4" align=center><span class="zi"><b>Filter List</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="4"><SELECT name=SearchType size=1 class="tb">
	<OPTION value="NotFoundReason">NotFoundReason</OPTION>
	<OPTION value="Content">Content</OPTION>
	</SELECT>&nbsp;<SELECT name=OrderSJ size=1 class="tb">
	<OPTION value="ASC">ASC</OPTION>
	<OPTION value="DESC">DESC</OPTION>
	</SELECT>&nbsp;<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 
	<a href="reason_import.jsp?ProjectID=<%=ProjectID%>"><IMG alt='import'  border=0 name=22.1.1.6.2 src="images/import.gif"></a>
      </td>
	
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"> 
      <div align="left">Total <%=iTotalRecords%> records <%=iCurPage%>/<%=iTotalPages%> page  Turn to
        <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">page</a> 
        
        </div></td>
    <td align="center">
    <a href="reason_add.jsp?ProjectID=<%=ProjectID%>"><IMG alt='add'  border=0 name=22.1.1.6.2 src="images/add.gif"></a>
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"> 
      <div align="center">Select</div></td>
    <td width="100" height="25"> 
      <div align="center">NotFoundReason</div></td>    
    <td width="100" height="25"> 
      <div align="center">Content</div></td>
    <td width="100" height="25"> 
      <div align="center">Action</div></td>
    </tr>

<%  
  iCount=0;nCount=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	iCount++;
	  	
	  	ReasonID=RS.getString("ReasonID");
	  	
	  	NotFoundReason=RS.getString("NotFoundReason");
	  	
	  	Content=RS.getString("Content");	 
	  	Content=Content.replace('&', '\'');
		Content=Content.replace('@', ','); 
	  	
 		  	
%>

  <tr bgcolor="#F8FDFF" onmouseover="this.bgColor='#EEF2F4';" onmouseout="this.bgColor='#F8FDFF';"> 
    <td height="25"> 
      <div align="center"><INPUT name=now_dn type=checkbox value="<%=ReasonID%>"></div></td>
    <td height="25">
     
      <div align="center"><a target=_blank href="../report/total.jsp?ProjectID=00000265&ReasonID=<%=ReasonID%>&NewFuzzyAll=NewFuzzy"><%=NotFoundReason%></a></div>
     
    </td>
    <td height="25" bgcolor='#F8CDFF'> 
      <div align="center"><a target=_blank href="../report/total.jsp?ProjectID=00000265&ReasonID=<%=ReasonID%>&NewFuzzyAll=NewFuzzy#?reason_edit.jsp?ReasonID=<%=ReasonID%>&ProjectID=<%=ProjectID%>"><%=Content%></a></div></td>
    <td height="25"> 
      <div align="center">
      <a href="#?reason_del.jsp?ReasonID=<%=ReasonID%>&ProjectID=<%=ProjectID%>" onclick="return del()"><IMG alt='Delete'  border=0 name=22.1.1.6.3 src="images/delete.gif"></a>
      
      </div></td>
      
  </tr>
  <%
  	 }  	
  }
  
  %>
  
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="4"> 
     <div align="center">
     <A href="reason.jsp?NextPage=No" style="TEXT-DECORATION: none"><FONT color=black>
	<%if(iCurPage==1){%> First <%} else {%>Previous <%}%></FONT> </A> 
	<A href="reason.jsp?NextPage=Yes" style="TEXT-DECORATION: none"><FONT color=black>
	<%if(iCurPage==iTotalPages) {%>Last <%} else {%> Next <%}%></FONT> </A> 
	 </div></td>
  </tr>
<INPUT name=name_array type=hidden> 

</form> 

  </table>
	
</td>
</tr>
</table>
<%  	 
	RS.close();workM.close();
  		
 
%>
</body>
</html>


