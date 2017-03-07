<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title>Mapping Analysis</title>
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
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="950" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="7" align=center><span class="zi"><b>Mapping Analysis</b></span></td>
  </tr>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25">ProjectID</td>
    <td height="25">ProductName</td>
    <td height="25">File</td>
    <td height="25"><B># of total Unit</B></td>    
    <td height="25"><B># of Mapped Unit</B></td>
    <td height="25"><B>Mapped %</B></td>
    <td height="25"><B>Not Mapped</B></td>
  </tr>
<%  
   String sql="select * from ProjectReport where ProjectID='"+ProjectID+"'"; 
   ResultSet rs_get =workM.getURL(sql); 
   String ProductName="";String UserName="";
   if(rs_get.next())
   {
   	ProjectID=rs_get.getString("ProjectID");
	ProductName=rs_get.getString("ProductName");
	UserName=rs_get.getString("UserName");
   }
   rs_get.close();
   int iAll=0; int iMapped=0; int iNotMapped=0; int iNotMappedNewFuzzy=0;  int iMappingRate=0;    
   int iUniqueMappingRate=0;  int iTextToScreenNotMapped=0;
   int iALLOfTool=0; int iMappedOfTool=0; int iNewOfTool=0; int iFuzzyOfTool=0; int iMappedNewOfTool=0; int iMappedFuzzyOfTool=0;
   int iNew=0; int iFuzzy=0;    
   int iNewRate=0; int iFuzzyRate=0; int iNewFuzzyRate=0; 
   int iMappedNew=0; int iMappedFuzzy=0; int IsUnique=0;
   int iMatchingRate=0; int iUnknown=0; int iI18NMappingError=0; int iI18UniqueError=0; 
   sql="select count(*) from I18NFile where ProjectID='"+ProjectID+"'"; 
   ResultSet rs=workM.getURL(sql); 
   if(rs.next()){
   	iAll=rs.getInt(1);
   }
   rs.close();
   sql="select count(*) from I18NFile where IsUnique=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs_unique=workM.getURL(sql); 
   if(rs_unique.next()){
   	IsUnique=rs_unique.getInt(1);
   }
   rs_unique.close();
   
   sql="select count(*) from I18NFile where mapped=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1=workM.getURL(sql); 
   if(rs1.next()){
   	iMapped=rs1.getInt(1);
   }
   rs1.close();
   sql="select count(*) from UIText where mapped=1 and ProjectID='"+ProjectID+"'"; 
   rs1=workM.getURL(sql); 
   if(rs1.next()){
   	iMappedOfTool=rs1.getInt(1);
   }
   rs1.close();
   
   sql="select count(*) from I18NFile where mapped=1 and NewString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1a=workM.getURL(sql); 
   if(rs1a.next()){
   	iMappedNew=rs1a.getInt(1);
   }
   rs1a.close();
   sql="select count(*) from UIText where mapped=1 and NewString=1 and ProjectID='"+ProjectID+"'"; 
   rs1a=workM.getURL(sql); 
   if(rs1a.next()){
   	iMappedNewOfTool=rs1a.getInt(1);
   }
   rs1a.close();
   
   sql="select count(*) from I18NFile where mapped=1 and FuzzyString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1b=workM.getURL(sql); 
   if(rs1b.next()){
   	iMappedFuzzy=rs1b.getInt(1);
   }
   rs1b.close();
   sql="select count(*) from UIText where mapped=1 and FuzzyString=1 and ProjectID='"+ProjectID+"'"; 
   rs1b=workM.getURL(sql); 
   if(rs1b.next()){
   	iMappedFuzzyOfTool=rs1b.getInt(1);
   }
   rs1b.close();
   
   sql="select count(*) from I18NFile where mapped=0 and  ProjectID='"+ProjectID+"'"; 
   ResultSet rs2=workM.getURL(sql); 
   if(rs2.next()){
   	iNotMapped=rs2.getInt(1);
   }
   rs2.close();
   sql="select count(*) from I18NFile where mapped=0 and NewFuzzy=1 and  ProjectID='"+ProjectID+"'"; 
   ResultSet rs2_newfuzzy=workM.getURL(sql); 
   if(rs2_newfuzzy.next()){
   	iNotMappedNewFuzzy=rs2_newfuzzy.getInt(1);
   }
   rs2_newfuzzy.close();
   
   sql="select count(*) from UIText where ProjectID='"+ProjectID+"'"; 
   ResultSet rs3=workM.getURL(sql); 
   if(rs3.next()){
   	iALLOfTool=rs3.getInt(1);
   }
   rs3.close();
   sql="select count(*) from I18NFile where mapped=0 and ReasonID=0 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs4=workM.getURL(sql); 
   if(rs4.next()){
   	iUnknown=rs4.getInt(1);
   }
   rs4.close();
   sql="select count(*) from I18NFile where NewString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs5=workM.getURL(sql); 
   if(rs5.next()){
   	iNew=rs5.getInt(1);
   }
   rs5.close();
   sql="select count(*) from UIText where NewString=1 and ProjectID='"+ProjectID+"'"; 
   rs5=workM.getURL(sql); 
   if(rs5.next()){
   	iNewOfTool=rs5.getInt(1);
   }
   rs5.close();
   
   sql="select count(*) from I18NFile where FuzzyString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs6=workM.getURL(sql); 
   if(rs6.next()){
   	iFuzzy=rs6.getInt(1);
   }
   rs6.close();
   sql="select count(*) from UIText where FuzzyString=1 and ProjectID='"+ProjectID+"'"; 
   rs6=workM.getURL(sql); 
   if(rs6.next()){
   	iFuzzyOfTool=rs6.getInt(1);
   }
   rs6.close();
   
   sql="select count(*) from UIText where mapped=0 and ProjectID='"+ProjectID+"'";
   ResultSet rs7=workM.getURL(sql); 
   if(rs7.next()){
   	iTextToScreenNotMapped=rs7.getInt(1);
   }
   rs7.close();
   if(iAll==0){
   	iMatchingRate=0;
   	iMappingRate=0;
   	iUniqueMappingRate=0;
   	iNewRate=0;
   	iFuzzyRate=0;
   }else{
   	
   	iMatchingRate=(10000*iALLOfTool)/iAll;
   	iMappingRate=(10000*iMapped)/iAll;
   	iUniqueMappingRate=(10000*iMapped)/IsUnique;
   	if(iNew==0){
   		iNewRate=0;
   	}else{
   		iNewRate=(10000*iMappedNew)/iNew;
   	}
   	if(iFuzzy==0){
   		iFuzzyRate=0;
   	}else{
   		iFuzzyRate=(10000*iMappedFuzzy)/iFuzzy;
   	}
   	if(iNew==0&&iFuzzy==0){
   		iNewFuzzyRate=0;
   	}else{
   		iNewFuzzyRate=(10000*(iMappedFuzzy+iMappedNew))/(iNew+iFuzzy);
   	}
   }
   String sMatchingRate=String.valueOf(iMatchingRate);
   if(sMatchingRate.length()>2){
   	sMatchingRate=sMatchingRate.substring(0,sMatchingRate.length()-2)+"."+sMatchingRate.substring(sMatchingRate.length()-2);
   }else{
   	sMatchingRate="0."+sMatchingRate;
   }
   String sMappingRate=String.valueOf(iMappingRate);   
   if(sMappingRate.length()>2){
   	sMappingRate=sMappingRate.substring(0,sMappingRate.length()-2)+"."+sMappingRate.substring(sMappingRate.length()-2);
   }else{
   	sMappingRate="0."+sMappingRate;
   }
   String sUniqueMappingRate=String.valueOf(iUniqueMappingRate);
   if(sUniqueMappingRate.length()>2){
   	sUniqueMappingRate=sUniqueMappingRate.substring(0,sUniqueMappingRate.length()-2)+"."+sUniqueMappingRate.substring(sUniqueMappingRate.length()-2);
   }else{
   	sUniqueMappingRate="0."+sUniqueMappingRate;
   }
   String sNewRate=String.valueOf(iNewRate);
   if(sNewRate.length()>2){
   	sNewRate=sNewRate.substring(0,sNewRate.length()-2)+"."+sNewRate.substring(sNewRate.length()-2);
   }else{
   	sNewRate="0."+sNewRate;
   }
   String sFuzzyRate=String.valueOf(iFuzzyRate);
   if(sFuzzyRate.length()>2){
   	sFuzzyRate=sFuzzyRate.substring(0,sFuzzyRate.length()-2)+"."+sFuzzyRate.substring(sFuzzyRate.length()-2);
   }else{
   	sFuzzyRate="0."+sFuzzyRate;
   }
   String sNewFuzzyRate=String.valueOf(iNewFuzzyRate);
   if(sNewFuzzyRate.length()>2){
   	sNewFuzzyRate=sNewFuzzyRate.substring(0,sNewFuzzyRate.length()-2)+"."+sNewFuzzyRate.substring(sNewFuzzyRate.length()-2);
   }else{
   	sNewFuzzyRate="0."+sNewFuzzyRate;
   }
   iI18NMappingError=iALLOfTool-iMapped; 
%>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"><%=ProjectID%></td>
    <td height="25"><%=ProductName%></td>
    <td height="25">i18n file</td>
    <td height="25" bgcolor="#ffdfbf">
    	    <table border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#3fcfaf">
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Total</b></td>
		    	<td align=right>
		    	<b><a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>"><font color='blue'><%=iAll%></font></a></b>
		    	</td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td>Unique</td>
		    	<td align=right><%=IsUnique%></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=New"><font color='blue'><%=iNew%></font></a></b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Fuzzy</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=Fuzzy"><font color='blue'><%=iFuzzy%></font></a></b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New/Fuzzy</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=NewFuzzy"><font color='blue'><%=(iNew+iFuzzy)%></font></a></b></td>
		    </tr>		    
	    </table>	    
    </td>     
    <td height="25" bgcolor="#ffdfbf">
	    <table border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#3fcfaf">
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Total</b></td>
		    	<td align=right><b>
		    	<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&mapped=1"><font color='blue'><%=iMapped%></font></a>
		    	</b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>&nbsp;</b></td>
		    	<td align=right><b>&nbsp;</b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=New&mapped=1"><font color='blue'><%=iMappedNew%></font></a></b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Fuzzy</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=Fuzzy&mapped=1"><font color='blue'><%=iMappedFuzzy%></font></a></b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New/Fuzzy</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="total.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=NewFuzzy&mapped=1"><font color='blue'><%=(iMappedNew+iMappedFuzzy)%></font></a></b></td>
		    </tr>			    
	    </table>
    </td>
    <td height="25" bgcolor="#ffdfbf">
    	   <table border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#3fcfaf">
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Total</b></td>
		    	<td align=right><b><%=iMapped%>/<%=iAll%>=<%=sMappingRate%>%</b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td>Unique</td>
		    	<td align=right><%=iMapped%>/<%=IsUnique%>=<%=sUniqueMappingRate%>%</td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New</b></td>
		    	<td align=right><b><%=sNewRate%>%</b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Fuzzy</b></td>
		    	<td align=right><b><%=sFuzzyRate%>%</b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New/Fuzzy</b></td> 
		    	<td align=right><b><%=sNewFuzzyRate%>%</b></td>
		    </tr>		    
	    </table>	   
    </td>    
    <td height="25" align='center'>
    	<a target=_blank href="mapping_fail_reason.jsp?ProjectID=<%=ProjectID%>"><font color='blue'><b><%=iNotMapped%></b></font></a> 
    	<BR>
    	New/Fuzzy:<a target=_blank href="mapping_fail_reason_newfuzzy.jsp?ProjectID=<%=ProjectID%>"><font color='red'><b><%=iNotMappedNewFuzzy%></b></font></a> 
    </td>    
   
  </tr>
  
  
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"></td>
    <td height="25"></td>
    <td height="25">text-to-screen.txt
    <BR>(Found by automation)
    </td>
    <td height="25" bgcolor="#ffdfbf">
    	    <table border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#3fcfaf">
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Total</b></td>
		    	<td align=right>
		    	<b><a target=_blank href="tool_found.jsp?ProjectID=<%=ProjectID%>"><font color='blue'><%=iALLOfTool%></font></a></b>
		    	</td>
		    </tr>		   
		    <tr bgcolor="#dfcfaf">
		    	<td><b></b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
		    </tr>
		   <tr bgcolor="#dfcfaf">
		    	<td><b></b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
		    </tr>		    
	    </table>	    
    </td>     
    <td height="25" bgcolor="#ffdfbf">
	    <table border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#3fcfaf">
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Total</b></td>
		    	<td align=right><b>
		    	<a target=_blank href="tool_found.jsp?ProjectID=<%=ProjectID%>&mapped=1"><font color='blue'><%=iMappedOfTool%></font></a>
		    	</b></td>
		    </tr>		    
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="tool_found.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=New&mapped=1"><font color='blue'><%=iMappedNewOfTool%></font></a></b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>Fuzzy</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="tool_found.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=Fuzzy&mapped=1"><font color='blue'><%=iMappedFuzzyOfTool%></font></a></b></td>
		    </tr>
		    <tr bgcolor="#dfcfaf">
		    	<td><b>New/Fuzzy</b></td>
		    	<td align=right><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target=_blank href="tool_found.jsp?ProjectID=<%=ProjectID%>&NewFuzzyAll=NewFuzzy&mapped=1"><font color='blue'><%=(iMappedNewOfTool+iMappedFuzzyOfTool)%></font></a></b></td>
		    </tr>			    
	    </table>
    </td>
    <td height="25" bgcolor="#ffdfbf">
    	   
    </td>    
    <td height="25" align='center'>
    	<a target=_blank href='tool_found.jsp?ProjectID=<%=ProjectID%>&mapped=0'><font color='red'><B><%=iTextToScreenNotMapped%></B></font></a> - 
    	<a target=_blank href='tool_found_1.jsp?ProjectID=<%=ProjectID%>&mapped=0'><font color='red'><B><%=iTextToScreenNotMapped%></B></font></a>
    </td>
    
        
  </tr>
  <% 
  workM.close();
  %>



  </table>
  	
</td>
</tr>
</table>

</body>
</html>

