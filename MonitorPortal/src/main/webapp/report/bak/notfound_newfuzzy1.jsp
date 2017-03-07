<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title>Not mapped(New/Fuzzy) Reason Analysis </title>
<script language="JavaScript"> 
function myrefresh() 
{ 
window.location.reload(); 
} 
//setTimeout('myrefresh()',5000); 
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
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="12984";
}
String bgcolor="#F8FDFF";
workM.getConnection();
ResultSet rs=null; String sql=""; int iCoverage=0;

   int iAll=0; int iMapped=0; int iNotMapped=0; int iUnknown=0; int iPLOC=0; int iI18NMappingError=0; int iTextToScreenNotMapped=0;
   sql="select count(*) from I18NFile where NewFuzzy=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs0=workM.getURL(sql); 
   if(rs0.next()){
   	iAll=rs0.getInt(1);
   }
   rs0.close();
   if(iAll==0){
   	iAll=1;
   }
   sql="select count(*) from I18NFile where mapped=1 and NewFuzzy=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1=workM.getURL(sql); 
   if(rs1.next()){
   	iMapped=rs1.getInt(1);
   }
   rs1.close();
   sql="select count(*) from I18NFile where mapped=0 and NewFuzzy=1 and  ProjectID='"+ProjectID+"'"; 
   ResultSet rs2=workM.getURL(sql); 
   if(rs2.next()){
   	iNotMapped=rs2.getInt(1);
   }
   rs2.close();
   sql="select count(*) from UIText where ProjectID='"+ProjectID+"'"; 
   ResultSet rs4=workM.getURL(sql); 
   if(rs4.next()){
   	iPLOC=rs4.getInt(1);
   }
   rs4.close();
   iI18NMappingError=iPLOC-iMapped; 
   sql="select count(*) from I18NFile where mapped=0 and ReasonID=0 and NewFuzzy=1 and  ProjectID='"+ProjectID+"'"; 
   ResultSet rs3=workM.getURL(sql); 
   if(rs3.next()){
   	iUnknown=rs3.getInt(1);
   }
   rs3.close();
   sql="select count(*) from UIText where mapped=0 and NewFuzzy=1 and ProjectID='"+ProjectID+"'";
   ResultSet rs7=workM.getURL(sql); 
   if(rs7.next()){
   	iTextToScreenNotMapped=rs7.getInt(1);
   }
   rs7.close();
   //iUnknown=iUnknown-iI18NMappingError;
   
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="550" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="navigation.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="30" colspan="6" align=center><span class="zi"><%=ProjectID%> The analysis for Not found New/Fuzzy strings</td>
  </tr> 
	<tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="6" align=center>Not found stings totally: 
	<span class="zi"><%=ProjectID%>  
	<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=NewFuzzy&mapped=0"><font color='blue'><%=iNotMapped%></font></a></span>
	</td>
  </tr> 
  <tr bgcolor="#A3DBE8">     
    <td height="25">Location</td>
	<td height="25">Priority</td>
	<td height="25">String Type</td>
    <td height="25">String Number</td>
    <td height="25">Percentage</td>
    <td height="25">Comments</td>
  </tr>
  <tr bgcolor="<%=bgcolor%>">    
	<td height="25">Main UI</td>
	<td height="25">High</td>
    <td height="25"><font color='red'>Main UI Strings</font></td>
    <td height="25">
    <a target=_blank href='total.jsp?ProjectID=<%=ProjectID%>&ReasonID=0&NewFuzzyAll=NewFuzzy'><font color='blue'><%=iUnknown%></font></a>
    </td>
    <td height="25"><%=(100*iUnknown)/iAll%>%</td>
    <td height="25"><font color='red'>Display in main UI. Need developer help find the UI Screen</font></td>
  </tr>
  <%
  String EachReasonID="";
  String AllReasonID=",";
  String sql_reason="select * from ReasonList";
  ResultSet rs_reason =workM.getURL(sql_reason); 
  while(rs_reason.next()){
  	EachReasonID=rs_reason.getString("ReasonID"); 	
  	AllReasonID+=EachReasonID+",";
  }
  rs_reason.close();
  int iNumber=0;
  String ReasonID=""; String NotFoundReason="";
  StringTokenizer stArray=new StringTokenizer(AllReasonID,",");
  while(stArray.hasMoreTokens()){
  	ReasonID = stArray.nextToken();
  	sql="select NotFoundReason from ReasonList where ReasonID="+ReasonID; 
  	rs=workM.getURL(sql); 
  	if(rs.next()){
  		NotFoundReason=rs.getString("NotFoundReason");
  	}
  	rs.close();
  	sql="select count(*) from I18NFile where ReasonID="+ReasonID+" and NewFuzzy=1 and ProjectID='"+ProjectID+"'"; 
  	rs=workM.getURL(sql); 
  	if(rs.next()){
  		iNumber=rs.getInt(1);
  		if(iAll>0){
  			iCoverage=(100*iNumber)/iAll;
  		}else{
  			iCoverage=0;
  		}
	  	%>
	  	<tr bgcolor="<%=bgcolor%>">     
		    <td height="25">Not in Main UI</td>
			<td height="25">Low</td>
			<td height="25"><%=NotFoundReason%></td>
		    <td height="25">
		    <a target=_blank href='total.jsp?ProjectID=<%=ProjectID%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=NewFuzzy'><font color='blue'><%=iNumber%></font></a>
		    </td> 
		    <td height="25"><%=iCoverage%>%</td>     
		    <td height="25"><font color='green'>Optional. If developer help find the related UI Screen, then LQA will test. OR we will ignore those strings as OUT OF Scope.</font></td>
		</tr>
	  	<%
  	}
  	rs.close();
  }
  rs=null;
  workM.close();
  %>

</form> 

  </table>
	
</td>
</tr>
</table>

</body>
</html>

