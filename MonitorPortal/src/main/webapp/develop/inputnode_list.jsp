<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection(); 
String sql=""; ResultSet RS=null;
String NodePath="";
String InputNodeListID="";
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<script type="text/javascript" src="./js/selection.js"></script>
<title>Input value List</title>
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
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="12984";
}
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
	sql="select count(*) from InputNodeList where ProjectID='"+ProjectID+"' ";
	if(SearchType.compareTo("RegDate")==0||SearchType.compareTo("ProjectDate")==0){
		sql+=" and "+SearchType+" <= '"+SearchContent+"'";		
	}
	else{
		sql+=" and "+SearchType+" like '%"+SearchContent+"%'";
	}
}
else{
	sql="select count(*) from InputNodeList where ProjectID='"+ProjectID+"'";
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
	sql="select *  from InputNodeList where InputNodeListID>0 ";
	if(SearchType.compareTo("RegDate")==0||SearchType.compareTo("ProjectDate")==0){
		sql+=" and "+SearchType+" <= '"+SearchContent+"'";		
	}
	else{
		sql+=" and "+SearchType+" like '%"+SearchContent+"%'";
	}	
	sql=sql+" and ProjectID='"+ProjectID+"' order by "+SearchType+" "+OrderSJ;
}
else{
	sql="select * from InputNodeList where InputNodeListID>0 ";
	sql=sql+" and ProjectID='"+ProjectID+"' order by InputNodeListID asc";
}
//out.print(sql+"<BR>");
RS =workM.getSQL(sql); 
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>

<table width="800" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="inputvalue_list.jsp" name="Maintain" >
  <input type=hidden name="ProjectID" value="<%=ProjectID%>">
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="3" align=center><span class="zi"><b>NodePath for Input Value List</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"><SELECT name=SearchType size=1 class="tb">
<OPTION value="NodePath">NodePath</OPTION>
</SELECT>&nbsp;<SELECT name=OrderSJ size=1 class="tb">
<OPTION value="ASC">ASC</OPTION>
<OPTION value="DESC">DESC</OPTION>
</SELECT>&nbsp;<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"> 
      <div align="left">Total <%=iTotalRecords%> records <%=iCurPage%>/<%=iTotalPages%> page  Turn to
        <input name=OrderValue  class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">page</a> </div></td>

  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"> 
      <div align="center">Select</div></td>
      
    <td width="100" height="25"> 
      <div align="center">NodePath</div></td>
  
     
    <td width="150" height="25"> 
      <div align="center"><A href="about:blank" onclick="nn=Judge(Maintain); if (nn>0) {if (confirm('Confirm delete?')){Maintain.action='includer_del_mul.jsp?OrderValue=<%=iCurPage%>';OnSubmit(Maintain);Maintain.submit(); return false;} else {return false;}}  else {alert('Please select xpath');return false;}"><IMG alt='select'  border=0 name=22.1.1.6.1 src="images/delmul.gif"></A></div></td>
  </tr>

<%  
  iCount=0;nCount=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {//	  	
	  	InputNodeListID=RS.getString("InputNodeListID");	  	
	  	NodePath=RS.getString("NodePath");
	  %>
	  <tr bgcolor="#F8FDFF" onmouseover="this.bgColor='#EEF2F4';" onmouseout="this.bgColor='#F8FDFF';"> 
	    <td height="25"> 
	      <div align="center"><INPUT name=now_dn type=checkbox value="<%=InputNodeListID%>"></div></td>
	    <td height="25"> 
	      <div align="center"><a href="input_node.jsp?ProjectID=<%=ProjectID%>&NodePath=<%=NodePath%>"><%=NodePath%></a></div>
	    </td>   
	    <td height="25"> 
	      <div align="center">
	      <a href="inputnode_del.jsp?ProjectID=<%=ProjectID%>&InputNodeListID=<%=InputNodeListID%>&NodePath=<%=NodePath%>&OrderValue=<%=iCurPage%>" onclick="return del()"><IMG alt='Delete'  border=0 name=22.1.1.6.2 src="images/delete.gif"></a></div></td>
	      
	  </tr>
  	  <%
  	  }  	
  }
 
  %>
  <tr bgcolor="#F8FDFF"> 
    <td height="25">
    <div align="center">
    <A onclick=SelectResult() style="CURSOR: hand"><IMG border=0 name=XuanZe1 src="images/icon_2_1.gif"></A>
    </div>
    </td>
    <td height="25"> </td>
    <td height="25"></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="3"> 
     <div align="center">
     <A href="inputvalue_list.jsp?NextPage=No&ProjectID=<%=ProjectID%>" style="TEXT-DECORATION: none"><FONT color=black>
	<%if(iCurPage==1){%> First <%} else {%>Previous <%}%></FONT> </A> 
	<A href="inputvalue_list.jsp?NextPage=Yes&ProjectID=<%=ProjectID%>" style="TEXT-DECORATION: none"><FONT color=black>
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


