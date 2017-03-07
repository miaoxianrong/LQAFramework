<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title>Report</title>
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
String ReportID,ProjectID,ProductName,UserName,Deepth2Coverage,Deepth3Coverage,ScreenNumber,PlocNodeNumber,PlayNumber,Owner,plocNumber,fuzzyNumber,newStringNumber;
%>
<%
ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="12984";
}
String bgcolor="#F8FDFF";
String sql="select * from ProjectReport where ProjectID='"+ProjectID+"' order by ProductName asc"; 
workM.getConnection();
ResultSet RS =workM.getURL(sql); 
%>
<table width=90% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" valign=top>
<table width="950" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
<form method="POST" action="navigation.jsp" name="Maintain" >
  <input type=hidden name='ProjectID' value='<%=ProjectID%>'>
  <tr bgcolor="#C3DBE8"> 
    <td height="25" colspan="3" align=center><span class="zi"><b>Product information</b></span></td>
  </tr>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25">ProjectID(drop) </td>
    <td height="25">ProductName</td>
    <td height="25">UserName</td>
  </tr>
<%  
  String AllProjectID="";
  int i=0;
  while(RS.next())
  {
  	i++;
	ReportID=RS.getString("ReportID");	  		  	
	ProjectID=RS.getString("ProjectID");	
	if(i==1){
		AllProjectID=ProjectID;
	}else{
		AllProjectID+=","+ProjectID;
	}
   }
   RS.close();
   String eachProjectID=""; 
   StringTokenizer stProjectID =new StringTokenizer(AllProjectID,","); 
   while(stProjectID.hasMoreTokens()){
   	eachProjectID=stProjectID.nextToken();
   	
   	int iScreenNum=0;
	String sql_screen="select count(*) from NodeList where validNode=1 and ProjectID='"+eachProjectID+"'";
	ResultSet rs_screen=workM.getSQL(sql_screen);
	if(rs_screen.next()){
		iScreenNum=rs_screen.getInt(1);
	}
	rs_screen.close();
		
	
	int iLevel2childNum0=0;
	String sql2="select count(*) from NodeList where LevelID=2 and childNum=0 and leaf=0 and ProjectID='"+eachProjectID+"'";
	ResultSet rs2=workM.getSQL(sql2);
	if(rs2.next()){
		iLevel2childNum0=rs2.getInt(1);
	}
	int iLevel2childNum1=0;
	sql2="select count(*) from NodeList where LevelID=2 and childNum>0 and ProjectID='"+eachProjectID+"'";
	rs2=workM.getSQL(sql2);
	if(rs2.next()){
		iLevel2childNum1=rs2.getInt(1);
	}
	rs2.close();
	
	if(iLevel2childNum0==0){
		iLevel2childNum0=1;
	}
	
	int iCoverage2=100*iLevel2childNum1/(iLevel2childNum0+iLevel2childNum1);
	
   	int iLevel3childNum0=0;
   	
	String sql3="select count(*) from NodeList where LevelID=3 and childNum=0 and leaf=0 and ProjectID='"+eachProjectID+"'";
	ResultSet rs3=workM.getSQL(sql3);
	if(rs3.next()){
		iLevel3childNum0=rs3.getInt(1);
	}
	int iLevel3childNum1=0;
	sql3="select count(*) from NodeList where LevelID=3 and childNum>0 and ProjectID='"+eachProjectID+"'";
	rs3=workM.getSQL(sql3);
	if(rs3.next()){
		iLevel3childNum1=rs3.getInt(1);
	}
	rs3.close();
	
	if(iLevel3childNum0==0){
		iLevel3childNum0=1;
	}
	int iCoverage3=100*iLevel3childNum1/(iLevel3childNum0+iLevel3childNum1);


	int iTotalplocNumber=0; int iEachNodeplocNumber=0;
	String sql5="select plocNumber from NodeList where ProjectID='"+eachProjectID+"'";
	sql5="select count(NodePath) from PlocTable where ProjectID='"+eachProjectID+"'";
	ResultSet rs5=workM.getSQL(sql5);
	if(rs5.next()){
		iTotalplocNumber=rs5.getInt(1);
	}
	/*
	while(rs5.next()){
		iEachNodeplocNumber = Integer.parseInt(rs5.getString("plocNumber"));
		iTotalplocNumber=iTotalplocNumber+iEachNodeplocNumber;
	}
	*/
	rs5.close();
	sql5="update ProjectReport set plocNumber="+iTotalplocNumber;
	sql5+=" where ProjectID='"+eachProjectID+"'";
	workM.updateSQL(sql5);
	
	int iTotalFuzzyNumber=0; int iEachNodeFuzzyNumber=0;
	String sql6="select fuzzyNumber from NodeList where ProjectID='"+eachProjectID+"'";	
	sql6="select count(NodePath) from NewFuzzyTable where ProjectID='"+eachProjectID+"'";
	ResultSet rs6=workM.getSQL(sql6);
	if(rs6.next()){
		iTotalFuzzyNumber=rs6.getInt(1);
	}
	/*
	while(rs6.next()){
		iEachNodeFuzzyNumber = Integer.parseInt(rs6.getString("fuzzyNumber"));
		iTotalFuzzyNumber=iTotalFuzzyNumber+iEachNodeFuzzyNumber;
	}
	*/
	rs6.close();
	
	sql6="update ProjectReport set fuzzyNumber="+iTotalFuzzyNumber;
	sql6+=" where ProjectID='"+eachProjectID+"'";
	workM.updateSQL(sql6);	
	
	int iTotalNewNumber=0; int iEachNodeNewNumber=0;
	String sql7="select newStringNumber from NodeList where newString=1 and ProjectID='"+eachProjectID+"'";
	sql7="select count(NodePath) from NodeAllText where ProjectID='"+eachProjectID+"'";
	ResultSet rs7=workM.getSQL(sql7);
	if(rs7.next()){
		iTotalNewNumber=rs7.getInt(1);
	}
	/*
	while(rs7.next()){ //348
		iEachNodeNewNumber = Integer.parseInt(rs7.getString("newStringNumber"));		
		iTotalNewNumber=iTotalNewNumber+iEachNodeNewNumber;
		
	}
	*/
	rs7.close();
	
	sql7="update ProjectReport set newStringNumber="+iTotalNewNumber;
	sql7+=" where ProjectID='"+eachProjectID+"'";
	workM.updateSQL(sql7);		
	
	
	int iPlocNodeNumber=0;
	String sql8="select count(*) from NodeList where newPloc=1 and ProjectID='"+eachProjectID+"'";
	ResultSet rs8=workM.getSQL(sql8);
	if(rs8.next()){
		iPlocNodeNumber=rs8.getInt(1);
	}
	rs8.close();
	sql8="update ProjectReport set PlocNodeNumber="+iPlocNodeNumber;
	sql8+=" where ProjectID='"+eachProjectID+"'";
	workM.updateSQL(sql8);		
	
	
	int iPlayNumber=0;
	String sql9="select count(*) from NodeList where playonce=1 and ProjectID='"+eachProjectID+"'";
	ResultSet rs9=workM.getSQL(sql9);
	if(rs9.next()){
		iPlayNumber=rs9.getInt(1);
	}
	rs9.close();
	
	sql="update ProjectReport set ScreenNumber="+iScreenNum;
	sql+=",Deepth2Coverage="+iCoverage2;
	sql+=",Deepth3Coverage="+iCoverage3;
	sql+=",PlayNumber="+iPlayNumber;
	sql+=" where ProjectID='"+eachProjectID+"'";
	workM.updateSQL(sql);
   }
   sql="select * from ProjectReport where ProjectID='"+ProjectID+"' order by ProductName asc"; 
   ResultSet rs_get =workM.getURL(sql); 
   int iDeepth3Coverage = 0;
   while(rs_get.next())
   {
   	ProjectID=rs_get.getString("ProjectID");
	ProductName=rs_get.getString("ProductName");
	UserName=rs_get.getString("UserName");
	Deepth2Coverage=rs_get.getString("Deepth2Coverage");	 
	Deepth3Coverage=rs_get.getString("Deepth3Coverage");	 
	iDeepth3Coverage=Integer.parseInt(Deepth3Coverage);
	ScreenNumber=rs_get.getString("ScreenNumber");
	PlocNodeNumber=rs_get.getString("PlocNodeNumber");
	PlayNumber=rs_get.getString("PlayNumber");
	plocNumber=rs_get.getString("plocNumber");
	fuzzyNumber=rs_get.getString("fuzzyNumber");
	newStringNumber=rs_get.getString("newStringNumber");
	if(iDeepth3Coverage<30){
		bgcolor="#F8CDFF";
	}else{
		if(iDeepth3Coverage<50){
			bgcolor="yellow";
		}
		else{
			if(iDeepth3Coverage<69){	
				bgcolor="yellow";	
			}else{
				bgcolor="green";	
			}
		}
	}
	//if(iDeepth3Coverage>0){
	Owner="unknown";
	if(ProjectID.indexOf("00000372")!=-1){ Owner="Mingzhe"; }
	if(ProjectID.indexOf("00000165")!=-1){ Owner="Michelle"; }
	if(ProjectID.indexOf("00000641")!=-1){ Owner="Huiping"; }
	if(ProjectID.indexOf("12984")!=-1){ Owner="Xianrong"; }
%>
  <tr bgcolor="<%=bgcolor%>">     
    <td height="25"><%=ProjectID%></td>
    <td height="25"><%=ProductName%></td>
    <td height="25"><%=UserName%></td>   
  </tr>
  <%  	 
  	//}
  }  
  rs_get.close();
  workM.close();
  %>


</form> 

  </table>
	
</td>
</tr>
</table>

</body>
</html>

