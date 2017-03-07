<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.io.File"%>
<%@ page language="java" import="java.io.FileWriter" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title>Analysis report</title>
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
String FolderFileName="c:\\framework\\webserver\\analysis\\analysis_data.csv";
String FullPageSource="";
try{  
   File webfile = new File(FolderFileName);
   if(webfile.exists()){
	webfile.delete();
   }
   
   String ProductComment="";
   String UserName="";
   String ProjectID=request.getParameter("ProjectID");
   if(ProjectID==null){
	ProjectID="00000000";
   }
   String bgcolor="#F8FDFF";
   workM.getConnection();
   ResultSet rs=null; String sql=""; int iCoverage=0;
   sql="select * from ReasonList";
   String AllColumn="";
   String MyNotFoundReason="";
   String AllNotFoundReason="Total,MainUI(Found),MainUI(Confirming),OutOfScope,";   
   AllNotFoundReason="Total,MainUI,OutOfScope,"; 
   ResultSet rs11=null;
   rs11=workM.getURL(sql); 
   while(rs11.next()){
	MyNotFoundReason=rs11.getString("NotFoundReason");   	
	if(AllNotFoundReason.indexOf(MyNotFoundReason)==-1){
		AllNotFoundReason=AllNotFoundReason+MyNotFoundReason+",";
	}else{
		//out.print(MyNotFoundReason+","+"<BR>");
	}
   }
   rs11.close();
   AllColumn="ProjectID,ProductName,"+AllNotFoundReason+"Comment";
   FullPageSource=AllColumn+"\n";
   String ColumnName="";
   int iPos=0;
   String UpdateAllNotFoundReason="";
   String EachLine="";String EachColoum=""; int iLine=0;

   int iAllPID=0;
   sql="select count(*) from  ProjectReport";
   //select * from  ProjectReport where ProjectID='12990';
   // update ProjectReport set UserName='liahu01' where ProjectID='00000853';
   //delete from ProjectReport where ProjectID='12990';
   ResultSet rs13=null;
   rs13=workM.getURL(sql); 
   if(rs13.next()){
   	iAllPID=rs13.getInt(1);
   }
   rs13.close();
   
   String[] pidArray = new String[iAllPID];
   String[] pnameArray = new String[iAllPID];
   String[] pcommentArray = new String[iAllPID];
   String[] puserArray = new String[iAllPID];
   String ProductName="";
   sql="select * from ProjectReport";
   String MyProjectID="";
   String MyProductName="";
   String MyProductComment="";
   String MyUserName="";
   String AllProjectID="";
   ResultSet rs12=null;
   rs12=workM.getURL(sql); 
   int iPID=0;   
   while(rs12.next()){
   	MyProjectID=rs12.getString("ProjectID");
   	MyProductName=rs12.getString("ProductName");
   	MyProductComment=rs12.getString("ProductComment");
   	MyUserName=rs12.getString("UserName");
   	pidArray[iPID]=MyProjectID;
   	pnameArray[iPID]=MyProductName;
   	pcommentArray[iPID]=MyProductComment;
   	puserArray[iPID]=MyUserName;
   	//pcommentArray[iPID]=
   	iPID++;   	
   	AllProjectID+=MyProjectID+",";
   }
   rs12.close();
   for(int k=0;k<iPID;k++){
   	   ProjectID=pidArray[k];
   	   ProductName=pnameArray[k]; 
   	   ProductComment=pcommentArray[k]; 
   	   UserName=puserArray[k];
           UpdateAllNotFoundReason="PID_"+ProjectID+"~ProjectID,"+ProductName+"~ProductName,"+AllNotFoundReason;
	   
		
	   int iAll=0; int iMapped=0; int iNotMapped=0; int iUnknown=0; int iPLOC=0; int iI18NMappingError=0; 
	   int iTextToScreenNotMapped=0; int iInScope=0; int iOutScope=0;
	   sql="select count(*) from DropFile where NewFuzzy=1 and ProjectID='"+ProjectID+"'"; 
	   ResultSet rs0=workM.getURL(sql); 
	   if(rs0.next()){
	   	iAll=rs0.getInt(1);
	   }
	   rs0.close();
	   if(iAll>0){
		   ColumnName="Total"+",";
		   iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
		   UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos)+String.valueOf(iAll)+"~"+UpdateAllNotFoundReason.substring(iPos);
	   
		   
		   if(iAll==0){
		   	iAll=1;
		   }
		     
		   
		   sql="select count(*) from DropFile where mapped=1 and NewFuzzy=1 and ProjectID='"+ProjectID+"'"; 
		   ResultSet rs1=workM.getURL(sql); 
		   if(rs1.next()){
		   	iMapped=rs1.getInt(1);
		   }
		   rs1.close();
		   
		   //ColumnName="MainUI(Found)"+",";
		   //iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
		   //UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos)+String.valueOf(iMapped)+"~"+UpdateAllNotFoundReason.substring(iPos);
		   
		   sql="select count(*) from DropFile where mapped=0 and NewFuzzy=1 and  ProjectID='"+ProjectID+"'"; 
		   ResultSet rs2=workM.getURL(sql); 
		   if(rs2.next()){
		   	iNotMapped=rs2.getInt(1);
		   }
		   rs2.close();
		   if(false){
		   	sql="select count(*) from UIText where ProjectID='"+ProjectID+"'"; 
		   	ResultSet rs4=workM.getURL(sql); 
			   if(rs4.next()){
			   	iPLOC=rs4.getInt(1);
			   }
		   	rs4.close();
		   	iI18NMappingError=iPLOC-iMapped; 
		   }
		   sql="select count(*) from DropFile where mapped=0 and ReasonID=0 and NewFuzzy=1 and  ProjectID='"+ProjectID+"'"; 
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
		   sql="select count(*) from DropFile where InScope=0 and mapped=0 and ReasonID=0 and NewFuzzy=1 and ProjectID='"+ProjectID+"'";
		   ResultSet rs8=workM.getURL(sql); 
		   if(rs8.next()){
		   	iInScope=rs8.getInt(1);
		   }
		   rs8.close();
		   
		   /*
		   ColumnName="MainUI(Found)"+",";
		   iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
		   UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos)+String.valueOf(iMapped)+"~"+UpdateAllNotFoundReason.substring(iPos);
		   		   
		   ColumnName="MainUI(Confirming)"+",";
		   iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
		   UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos)+String.valueOf(iInScope)+"~"+UpdateAllNotFoundReason.substring(iPos);
		   */
		   ColumnName="MainUI"+",";
		   iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
		   UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos)+String.valueOf(iMapped+iInScope)+"~"+UpdateAllNotFoundReason.substring(iPos);
		   
		   
		   sql="select count(*) from DropFile where InScope=1 and mapped=0 and ReasonID=0 and NewFuzzy=1 and ProjectID='"+ProjectID+"'";
		   ResultSet rs9=workM.getURL(sql); 
		   if(rs9.next()){
		   	iOutScope=rs9.getInt(1);
		   }
		   rs9.close();
		   
		   ColumnName="OutOfScope"+",";
		   iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
		   UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos)+String.valueOf(iOutScope)+"~"+UpdateAllNotFoundReason.substring(iPos);
		   
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
		    <td height="25" colspan="4" align=center><span class="zi">[<%=ProjectID%>-<%=ProductName%>] TU(Translation Unit) list (By <%=UserName%>)</span></td>
		  </tr>    
		  <tr bgcolor="<%=bgcolor%>">     
		    <td height="25">Total</td>
		    <td height="25">
		    	<a target=_blank href="all.jsp?ProjectID=<%=ProjectID%>&ReasonID=-1&InScope=-1&mapped=-1&NewFuzzyAll=NewFuzzy&PreAnalysis=0"><font color='blue'><%=iAll%></font></a>
		    </td>
		    <td height="25"></td>
		    <td height="25"></td>
		  </tr>
		  <tr bgcolor="#A3DBE8">     
		    <td height="25"><font color='red'>Main UI</font>&nbsp;(<font color=green>Found</font>)</td>
		    <td height="25">
		    <a target=_blank href="all.jsp?ProjectID=<%=ProjectID%>&ReasonID=-1&InScope=-1&NewFuzzyAll=NewFuzzy&mapped=1&PreAnalysis=0"><font color='green'><%=iMapped%></font></a>
		    </td>
		    <td height="25"><%=(100*iMapped)/iAll%>%</td>
		    <td height="25"><font color='red'>High priority</font></td>
		  </tr>
		  <tr bgcolor="#F3DBE8">    
		    <td height="25" colspan=4>
		    Comment by LQA:<BR>
		    <font color='blue'><%=ProductComment%></font>
		    </td>
		  </tr>	
		  <tr bgcolor="white">     
		    <td height="25"></td>
		    <td height="25"></td>
		    <td height="25"></td>
		    <td height="25"></td>
		  </tr>
		  <tr bgcolor="#A3DBE8">     
		    <td height="25"><font color=black>Not found</font></td>
		    <td height="25">
		    <a target=_blank href="all.jsp?ProjectID=<%=ProjectID%>&ReasonID=-1&InScope=-1&NewFuzzyAll=NewFuzzy&mapped=0&PreAnalysis=0"><font color='black'><%=iNotMapped%></font></a>
		    </td>
		    <td height="25"></td>
		    <td height="25"></td>
		  </tr>
		  
		    <tr bgcolor="<%=bgcolor%>">     
		    <td height="25"><font color='red'>Main UI</font>&nbsp;(<font color=red>Not found, confirming</font>)</td>
		    <td height="25">   
		    <a target=_blank href='all.jsp?ProjectID=<%=ProjectID%>&ReasonID=0&NewFuzzyAll=NewFuzzy&InScope=0&mapped=0&PreAnalysis=0'><font color='red'><%=iInScope%></font></a>
		    </td>
		    <td height="25"><font color='red'><%=(100*iInScope)/iAll%>%</font></td>
		    <td height="25"><font color='red'>High priority</font></td>
		  </tr>
		  
		  <%
		  if(iOutScope>0){
		  %>
		  <tr bgcolor="<%=bgcolor%>">     
		    <td height="25"><font color='black'>Out of scope (confirmed by DEV)</font></td>
		    <td height="25">
		    <a target=_blank href='all.jsp?ProjectID=<%=ProjectID%>&mapped=-1&ReasonID=0&NewFuzzyAll=NewFuzzy&InScope=1&PreAnalysis=0'><font color='blue'><%=iOutScope%></font></a>
		    </td>
		    <td height="25"><font color='black'><%=(100*iOutScope)/iAll%>%</font></td>
		    <td height="25"><font color='green'>Low priority</font></td>
		  </tr>
		  <%
		  }
		  %>
		  <%
		  String EachReasonID="";
		  String AllReasonID=",";
		  String sql_reason="select * from ReasonList where ProjectID='"+ProjectID+"'";
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
		  	sql="select count(*) from DropFile where ReasonID="+ReasonID+" and mapped=0 and NewFuzzy=1 and ProjectID='"+ProjectID+"'"; 
		  	rs=workM.getURL(sql); 
		  	if(rs.next()){
		  		iNumber=rs.getInt(1);
		  		if(iAll>0){
		  			iCoverage=(100*iNumber)/iAll;
		  		}else{
		  			iCoverage=0;
		  		}
		  		if(iNumber>0){
		  		   ColumnName=","+NotFoundReason+",";
				   iPos=UpdateAllNotFoundReason.indexOf(ColumnName);   
				   UpdateAllNotFoundReason=UpdateAllNotFoundReason.substring(0,iPos+1)+String.valueOf(iNumber)+"~"+UpdateAllNotFoundReason.substring(iPos+1);
				   
			  	%>
			  	<tr bgcolor="<%=bgcolor%>">     
				    <td height="25"><%=NotFoundReason%></td>
				    <td height="25">
				    <a target=_blank href='all.jsp?ProjectID=<%=ProjectID%>&ReasonID=<%=ReasonID%>&NewFuzzyAll=NewFuzzy&mapped=0&PreAnalysis=0&InScope=-1'><font color='blue'><%=iNumber%></font></a>
				    </td> 
				    <td height="25"><%=iCoverage%>%</td>     
				    <td height="25">
				    <font color='green'>Low priority</font>
				    </td>
				</tr>
			  	<%
			  	}
		  	}
		  	rs.close();
		  }
		  rs=null;
		  
		  %>	  
		</form> 
		  	  
		  </table>
			
		</td>
		</tr>
		</table>
		<%
		
		//out.print(UpdateAllNotFoundReason+"<BR>");
		StringTokenizer columnArray=new StringTokenizer(UpdateAllNotFoundReason,",");
		EachLine="";
		while(columnArray.hasMoreTokens()){
			EachColoum = columnArray.nextToken();
			iLine=EachColoum.indexOf("~");
			if(iLine==-1){
				EachLine=EachLine+"0,";
			}else{
				EachLine=EachLine+EachColoum.substring(0,iLine)+",";
			}
		}		
		EachLine=EachLine+ProductComment;
		//out.print(UpdateAllNotFoundReason+"<BR>");
		//out.print(EachLine+"<BR>");
		FullPageSource=FullPageSource+EachLine+"\n";
	}
    }
    workM.close();
    
    FileWriter writer = new FileWriter(FolderFileName, true);  
    writer.write(FullPageSource);  
    writer.close();
    
}catch(IOException e){  
	       
}
    
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
		<tr><td height=10 align=right></td></tr>
		<tr>
		<td align=center class="zi" valign=top>
		<table width="550" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">	
		<tr bgcolor="#C3DBE8"> 
		    <td height="25" colspan="4" align=center><span class="zi">
		    <a target=_blank href="../analysis/analysis_data.csv"><font color='blue'>Download -> Report</font></a>		    
		    </span></td>
		  </tr>    
	</table>
			
		</td>
		</tr>
</table>	  	
</body>
</html>

