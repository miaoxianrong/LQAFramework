<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection(); 
String sql=""; ResultSet RS=null;
String XpathID,XpathPath,XPATHName,ParentName,Feature,XPATH,seq,leaf,DOMType,LevelID,ParentSeq,NodePath,nodeName,nodeType,onclick,Active;
String tagName,Attribute,AttributeCondition,AttributeValue,ResourceBundle;
String MyProjectID="";
int iLevelID=0;
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>

<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<script type="text/javascript" src="./js/selection.js"></script>
<title>XPATH  List</title>
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
	ProjectID="00000";
}
String ProjectName="";
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
	sql="select count(*) from XpathList where XpathID>0 and ProjectID='"+ProjectID+"' ";
	if(SearchType.compareTo("RegDate")==0||SearchType.compareTo("ProjectDate")==0){
		sql+=" and "+SearchType+" <= '"+SearchContent+"'";		
	}
	else{
		sql+=" and "+SearchType+" like '%"+SearchContent+"%'";
	}
}
else{
	sql="select count(*) from XpathList where XpathID>0 and ProjectID='"+ProjectID+"'";
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
	sql="select *  from XpathList where XpathID>0 ";
	if(SearchType.compareTo("RegDate")==0||SearchType.compareTo("ProjectDate")==0){
		sql+=" and "+SearchType+" <= '"+SearchContent+"'";		
	}
	else{
		sql+=" and "+SearchType+" like '%"+SearchContent+"%'";
	}	
	sql=sql+" and XpathID>0 and ProjectID='"+ProjectID+"' order by "+SearchType+" "+OrderSJ;
}
else{
	sql="select * from XpathList where XpathID>0 ";
	sql=sql+" and ProjectID='"+ProjectID+"' order by XpathPath asc";
}
RS =workM.getSQL(sql); 
%>
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>

<table width="1200" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="xpath.jsp" name="Maintain" >
  <input type=hidden name="ProjectID" value="<%=ProjectID%>">
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="18" align=center><span class="zi"><b>XPATH List - Drop 1</b></span></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="17"><SELECT name=SearchType size=1 class="tb">
<OPTION value="XPATHName">XPATHName</OPTION>
<OPTION value="XPATH">XPATH</OPTION>
</SELECT>&nbsp;<SELECT name=OrderSJ size=1 class="tb">
<OPTION value="ASC">ASC</OPTION>
<OPTION value="DESC">DESC</OPTION>
</SELECT>&nbsp;<INPUT name=SearchContent type=text class="tb" style="WIDTH: 100px" onFocus=select() value="">&nbsp;<INPUT name=Search type=submit value=" Search "> 

</td>
<td align="center">
    <%
    if(iTotalRecords==0){
    %>
    <a href="xpath_add.jsp?ProjectID=<%=ProjectID%>"><IMG alt='add'  border=0 name=22.1.1.6.2 src="images/add.gif"></a>
    <%
    }
    %>
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="17"> 
      <div align="left">Total <%=iTotalRecords%> records <%=iCurPage%>/<%=iTotalPages%> page  Turn to
        <input name=OrderValue   class="tb" style="WIDTH: 30px" onFocus=select()>
        <a href="javascript:document.Maintain.submit();" onclick="if(checkvalue_1(document.Maintain.OrderValue.value)) return true;else return false;">page</a> 
        <%
	if(iTotalRecords==0){
	%>
        <a href="xpath_add.jsp?ProjectID=<%=ProjectID%>"><IMG alt='add'  border=0 name=22.1.1.6.2 src="images/add.gif"></a>
        <%
    	}
    	%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--a href="metadata.jsp?ProjectID=<%=ProjectID%>"><IMG alt='add'  border=0 name=22.1.1.6.2 src="images/metadata.gif"></a-->
        </div></td>
    <td align="center">
    <a href="xpath_export.jsp?ProjectID=<%=ProjectID%>"><IMG alt='export'  border=0 name=22.1.1.6.2 src="images/export.gif"></a>
    <!--a href="#?xpath_import.jsp?ProjectID=<%=ProjectID%>"><IMG alt='import'  border=0 name=22.1.1.6.2 src="images/import.gif"></a-->
    </td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td width="50" height="25"> 
      <div align="center">Select</div></td>
    <td width="100" height="25"> 
      <div align="center">Name<BR>(Depth 1)</div></td>
    <td width="100" height="25"> 
      <div align="center">Name<BR>(Depth 2)</div></td>
    <td width="100" height="25"> 
      <div align="center">Name<BR>(Depth 3)</div></td>
    <td width="100" height="25"> 
      <div align="center">Name<BR>(Depth 4)</div></td>
    <td width="100" height="25"> 
      <div align="center">Name<BR>(Depth 5)</div></td>
    <td width="100" height="25"> 
      <div align="center">Name<BR>(Depth 6+)</div></td>   
    <td width="100" height="25"> 
      <div align="center">XpathPath</div></td>
    <td width="100" height="25"> 
      <div align="center">ParentName</div></td>
    <td width="100" height="25"> 
      <div align="center">XPATH</div></td>
    <td width="100" height="25"> 
      <div align="center">Apply</div></td>
    <td width="100" height="25"> 
      <div align="center">Excluder</div></td>
    <td width="100" height="25"> 
      <div align="center">InputValue</div></td>
    <td width="100" height="25"> 
      <div align="center">seq</div></td>    
    <td width="100" height="25"> 
      <div align="center">Condition</div></td>
     <td width="240" height="25"> 
      <div align="center">DOM Type</div></td>
    <td width="150" height="25"> 
      <div align="center">Leaf</div></td>
    <td width="150" height="25"> 
      <div align="center"><A href="about:blank" onclick="nn=Judge(Maintain); if (nn>0) {if (confirm('Confirm delete?')){Maintain.action='xpath_del_mul.jsp?OrderValue=<%=iCurPage%>';OnSubmit(Maintain);Maintain.submit(); return false;} else {return false;}}  else {alert('Please select xpath');return false;}"><IMG alt='select'  border=0 name=22.1.1.6.1 src="images/delmul.gif"></A></div></td>
  </tr>

<%  
  iCount=0;nCount=0;
  while(RS.next())
  {
  	  nCount++;
  	  if(nCount>=iFirstRecord&&nCount<=iLastRecord)
	  {
	  	iCount++;
	  	MyProjectID=RS.getString("ProjectID");
	  	ProjectName=RS.getString("ProjectName");
	  	XpathID=RS.getString("XpathID");
	  	ParentName=RS.getString("ParentName");
	  	if(ParentName.compareTo("-1")==0){
	  		ParentName="";
	  	}
	  	ParentSeq=RS.getString("ParentSeq");
	  	if(ParentSeq.compareTo("")!=0){
	  		ParentSeq="<BR>["+ParentSeq+"]";
	  	}
	  	XpathPath=RS.getString("XpathPath");
	  	XPATHName=RS.getString("XPATHName");
	  	tagName=RS.getString("tagName");
	  	Attribute=RS.getString("Attribute");
	  	AttributeCondition=RS.getString("AttributeCondition");
	  	AttributeValue=RS.getString("AttributeValue");
	  	DOMType=RS.getString("DOMType");
	  	if(DOMType.compareTo("-1")==0){
	  		DOMType="";
	  	}
	  	XPATH=RS.getString("XPATH");	 
	  	XPATH=XPATH.replace('&', '\''); 
	  	seq=RS.getString("seq");		
	  	leaf=RS.getString("leaf");
	  	if(leaf.compareTo("1")==0){
	  		leaf="leaf";
	  	}else{
	  		leaf="";
	  	}
	  	ResourceBundle=RS.getString("ResourceBundle");
	  	LevelID=RS.getString("LevelID");
	  	iLevelID=Integer.parseInt(LevelID);
	  	Active=RS.getString("Active");
	  	
%>

  <tr bgcolor="#F8FDFF" onmouseover="this.bgColor='#EEF2F4';" onmouseout="this.bgColor='#F8FDFF';"> 
    <td height="25"> 
      <div align="center"><INPUT name=now_dn type=checkbox value="<%=XpathID%>"></div></td>
    <td height="25" bgcolor='#F8CDFF'>
      <% if(iLevelID==1){%>
      <div align="center"><a href="xpath_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATHName%></a></div>
      <% }%>
    </td>
    <td height="25" bgcolor='#F8CDFF'> 
      <% if(iLevelID==2){%>
      <div align="center"><a href="xpath_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATHName%></a></div>
      <% }%>
    </td>
    <td height="25" bgcolor='#F8CDFF'> 
      <% if(iLevelID==3){%>
      <div align="center"><a href="xpath_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATHName%></a></div>
      <% }%>
    </td>
    <td height="25" bgcolor='#F8CDFF'>
      <% if(iLevelID==4){%>
      <div align="center"><a href="xpath_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATHName%></a></div>
      <% }%>
    </td>
    <td height="25" bgcolor='#F8CDFF'>
      <% if(iLevelID==5){%>
      <div align="center"><a href="xpath_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATHName%></a></div>
      <% }%>
    </td>
    <td height="25" bgcolor='#F8CDFF'>
      <% if(iLevelID>5){%>
      <div align="center"><a href="xpath_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATHName%></a></div>
      <% }%>
    </td>    
    <td height="25"> 
      <div align="center"><a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XpathPath%></a></div></td>
    <td height="25"> 
      <div align="center"><a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=ParentName%></a><%=ParentSeq%></div></td>
    <td height="25"> 
      <div align="center"><a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=XPATH%></a></div></td>
    <td height="25"> 
      <div align="center"><a href="xpath_run.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><IMG alt='run'  border=0 src="images/apply.gif"></a></div></td>
    <td height="25"> 
      <div align="center"><a href="excluder_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><IMG alt='excluder'  border=0 name=22.1.1.6.1 src="images/excluder.gif"></a></div></td>
    <td height="25"> 
      <div align="center"><a href="inputvalue_add.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><IMG alt='InputValue' border=0 name=22.1.1.6.1 src="images/inputvalue.gif"></a></div></td>    
    <td height="25"> 
      <div align="center"><a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=seq%></a></div></td>   
    <td height="25"> 
      <div align="center"><a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>">
      <%
      if(tagName.compareTo("-1")!=0){
      	out.print("tag.equals(\""+tagName+"\")");
      }
      if(Attribute.compareTo("-1")!=0){
      	out.print(" "+Attribute+"."+AttributeCondition+"(\""+AttributeValue+"\")");
      }
      %>
      </a></div></td>
    <td height="25"> 
      <div align="center">
      	<%=DOMType%>
      </div></td>
    <td height="25"> 
      <div align="center"><a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><%=leaf%></a></div></td>
    <td height="25"> 
      <div align="center">
      <!--a href="xpath_edit.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>"><IMG alt='Edit'  border=0 name=22.1.1.6.2 src="images/cn_1_6_e.gif"></a--> 
      <%
      if(iCount>0){
      %>
      <a href="xpath_del.jsp?ProjectID=<%=ProjectID%>&XpathID=<%=XpathID%>&OrderValue=<%=iCurPage%>" onclick="return del()"><IMG alt='Delete'  border=0 name=22.1.1.6.3 src="images/delete.gif"></a>
      <%
      }
      %>
      </div></td>
      
  </tr>
  <%
  	 }  	
  }
  
  %>
  <tr bgcolor="#F8FDFF"> 
    <td height="25"><div align="center"><A onclick=SelectResult() style="CURSOR: hand"><IMG border=0 name=XuanZe1 src="images/icon_2_1.gif"></A></div></td>
    <td height="25" colspan="16"> </td>
    <td height="25"><div align="center">
    
    <% boolean bTest=false;
     if(bTest){%>
     <A href="#" onclick="Do_Layer0('visible','Layer0');return false;"><IMG alt='select'  border=0 name=22.1.1.6.1 src="images/cn_1_6_1.gif"></A>
     <%}%>
    </div></td>
  </tr>
  <tr bgcolor="#F8FDFF"> 
    <td height="25" colspan="18"> 
     <div align="center">
     <A href="xpath.jsp?NextPage=No&ProjectID=<%=ProjectID%>" style="TEXT-DECORATION: none"><FONT color=black>
	<%if(iCurPage==1){%> First <%} else {%>Previous <%}%></FONT> </A> 
	<A href="xpath.jsp?NextPage=Yes&ProjectID=<%=ProjectID%>" style="TEXT-DECORATION: none"><FONT color=black>
	<%if(iCurPage==iTotalPages) {%>Last <%} else {%> Next <%}%></FONT> </A> 
	 </div></td>
  </tr>
<INPUT name=name_array type=hidden> 

</form> 

  </table>
	
	<DIV id=Layer0 style="VISIBILITY: hidden; Z-INDEX: 1">
	<TABLE class=table_DOMType_opt id=id_line10>
	  <TBODY>
	  <TR class=tr_header>
	    <TD><IMG height=14 onclick="Do_Layer('hidden','Layer0')" 
	      src="images/icon_2_2.gif" width=14></TD></TR>  
	  <TR>
	    <TD>
	    <A href="about:blank" onclick="Maintain.action='xpath_add.jsp?OrderValue=<%=iCurPage%>&ProjectID=<%=ProjectID%>';Maintain.submit();return false;">Add one xpath</A>
	    <BR>
	    <A href="about:blank" onclick="Maintain.action='xpath_clear.jsp?OrderValue=<%=iCurPage%>&ProjectID=<%=ProjectID%>';Maintain.submit();return false;">Clear xpath</A>
	    <BR>
	    <A href="about:blank" onclick="nn=Judge(Maintain); if (nn>0) {if (confirm('Confirm delete?')){Maintain.action='xpath_del_mul.jsp?OrderValue=<%=iCurPage%>&ProjectID=<%=ProjectID%>';OnSubmit(Maintain);Maintain.submit(); return false;} else {return false;}}  else {alert('Please select records');return false;}">Delete multi xpath</A>
	    </TD>
	  </TR>
	  <TR>
	    <TD>
	    <A href="about:blank" onclick="nn=Judge(Maintain); if (nn>0) {Maintain.action='export.jsp?OrderValue=<%=iCurPage%>&ProjectID=<%=ProjectID%>';OnSubmit(Maintain);Maintain.submit();return false;} else {alert('Please select records');return false;}">Export to csv file</A><BR>
	    </TD>
	  </TR>
	  </TBODY>
	</TABLE>
	</DIV>
</td>
</tr>
</table>
<%  	 
	RS.close();workM.close();
  		
 
%>
</body>
</html>


