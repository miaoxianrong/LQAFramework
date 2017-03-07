<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String ProjectName="";
String XPATHName=request.getParameter("XPATHName");
XPATHName=XPATHName.trim();
XPATHName=XPATHName.replace(',','-');
String FirstChar=XPATHName.charAt(0)+""; int iFirst=0;
try{
	iFirst=Integer.parseInt(FirstChar);	
	XPATHName="z"+XPATHName;
}catch(Exception e){}
String tagName=request.getParameter("tagName");
String Attribute=request.getParameter("Attribute");
String AttributeCondition=request.getParameter("AttributeCondition");
String AttributeValue=request.getParameter("AttributeValue");
AttributeValue=AttributeValue.trim();
String XPATH=request.getParameter("XPATH");
XPATH=XPATH.trim();
String PageContainer=request.getParameter("PageContainer");
PageContainer=PageContainer.trim();
PageContainer=PageContainer.replace('\'', '&');

String ParentName=request.getParameter("ParentName");
String ParentSeq=request.getParameter("ParentSeq");
String NodeLevelID=request.getParameter("NodeLevelID");

String seq=request.getParameter("seq");
String DOMType=request.getParameter("DOMType");
String leaf=request.getParameter("leaf");

String sql="select ProductName from ProjectReport where ProjectID='"+ProjectID+"'";
ResultSet rs0=workM.getSQL(sql);
if(rs0.next()){
	ProjectName=rs0.getString("ProductName");
}
rs0.close();
rs0=null;

boolean bExistSameXPATHName=false; String AllXPATHName="";
sql="select XPATHName from XpathList where XPATHName like '%"+XPATHName+"%' and ProjectID='"+ProjectID+"'";
ResultSet rs9=workM.getSQL(sql);
while(rs9.next()){
	bExistSameXPATHName=true;
	AllXPATHName+=rs9.getString("XPATHName")+" ";
}
rs9.close();

if(!bExistSameXPATHName){
	sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
	
	sql=sql+",ProjectName='"+ProjectName+"'";
	sql=sql+",tagName='"+tagName+"'";
	sql=sql+",Attribute='"+Attribute+"'";
	sql=sql+",AttributeCondition='"+AttributeCondition+"'";
	sql=sql+",AttributeValue='"+AttributeValue+"'";
	
	int iMyPathSeq=0; String PathSeq=""; 
	int iMyLevelID=0; String LevelID="";
	String XpathPath="";
	if(ParentName.compareTo("-1")==0){
		String sql1="select PathSeq from XpathList where LevelID=1 and ProjectID='"+ProjectID+"' order by PathSeq desc";
		ResultSet rs1=workM.getSQL(sql1);
		if(rs1.next()){
			PathSeq=rs1.getString("PathSeq");
			iMyPathSeq=Integer.parseInt(PathSeq)+1;
		}else{
			iMyPathSeq=1;
		}
		rs1.close();
		sql=sql+",ParentName='"+ParentName+"'";
		sql=sql+",PathSeq="+iMyPathSeq;
		sql=sql+",XpathPath='"+iMyPathSeq+"'";
	}else{
		sql=sql+",ParentName='"+ParentName+"'";
		
		String sql2="select * from XpathList where XPATHName='"+ParentName+"' and ProjectID='"+ProjectID+"'";
		ResultSet rs2=workM.getSQL(sql2);
		if(rs2.next()){
			LevelID=rs2.getString("LevelID");
			iMyLevelID=Integer.parseInt(LevelID)+1;
			sql=sql+",LevelID="+iMyLevelID;	
			XpathPath=rs2.getString("XpathPath");
		}
		rs2.close();
		
		String sql3="select * from XpathList where ParentName='"+ParentName+"' and ProjectID='"+ProjectID+"' order by PathSeq desc";
		ResultSet rs3=workM.getSQL(sql3);
		if(rs3.next()){
			PathSeq=rs3.getString("PathSeq");
			iMyPathSeq=Integer.parseInt(PathSeq)+1;
			sql=sql+",PathSeq="+iMyPathSeq;
			sql=sql+",XpathPath='"+XpathPath+"-"+iMyPathSeq+"'";
		}else{
			iMyPathSeq=1;
			sql=sql+",PathSeq="+iMyPathSeq;
			sql=sql+",XpathPath='"+XpathPath+"-"+iMyPathSeq+"'";
		}
		rs3.close();
	}
	sql=sql+",NodeLevelID="+NodeLevelID;
	XPATH=XPATH.replace('\'', '&');
	sql=sql+",XPATH='"+XPATH+"'";
	sql=sql+",PageContainer='"+PageContainer+"'";
	String getSeq=""; int iSeq=0; String validSeq=""; int iCount=0; boolean bIntSeq=false;
	StringTokenizer st1=new StringTokenizer(ParentSeq,",");
	while(st1.hasMoreTokens()){	
		getSeq=st1.nextToken();
		try{
			iSeq=Integer.parseInt(getSeq);
			bIntSeq=true;		
		}catch(Exception ex){
			bIntSeq=false;
		}
		if(bIntSeq){
			iCount++;
			if(iCount==1){
				validSeq=getSeq;
			}else{
				validSeq=validSeq+","+getSeq;
			}
		}	
	}
	sql=sql+",ParentSeq='"+validSeq+"'";
	
	validSeq=""; iCount=0;
	StringTokenizer st2=new StringTokenizer(seq,",");
	while(st2.hasMoreTokens()){	
		getSeq=st2.nextToken();
		try{
			iSeq=Integer.parseInt(getSeq);
			bIntSeq=true;		
		}catch(Exception ex){
			bIntSeq=false;
		}
		if(bIntSeq){
			iCount++;
			if(iCount==1){
				validSeq=getSeq;
			}else{
				validSeq=validSeq+","+getSeq;
			}
		}	
	}
	sql=sql+",seq='"+validSeq+"'";
	
	sql=sql+",DOMType='"+DOMType+"'";
	if(DOMType.indexOf("popup")!=-1){
		sql=sql+",popup=1";	
	}
	if(DOMType.indexOf("newtab")!=-1){
		sql=sql+",newtab=1";	
	}
	if(DOMType.indexOf("dropdown")!=-1){
		sql=sql+",dropdown=1";	
	}
	if(DOMType.indexOf("doubleclick")!=-1){
		sql=sql+",doubleclick=1";	
	}
	if(DOMType.indexOf("mouseright")!=-1){
		sql=sql+",mouseright=1";	
	}
	if(DOMType.indexOf("mouseover")!=-1){
		sql=sql+",mouseover=1";	
	}
	if(DOMType.indexOf("tips")!=-1){
		sql=sql+",tips=1";	
	}
	if(DOMType.indexOf("frame")!=-1){
		sql=sql+",frame=1";	
	}
	if(DOMType.indexOf("iframe")!=-1){
		sql=sql+",iframe=1";	
	}
	if(leaf.compareTo("yes")==0){
		sql=sql+",leaf=1";	
	}
	/*
	String Extension[]=request.getParameterValues("Extension");
	if(Extension!=null){
		for(int i=0;i<Extension.length;i++){
			DOMType=DOMType+"_"+Extension[i];
		}
		sql=sql+",DOMType='"+DOMType+"'";
	}
	*/
	sql=sql+",XPATHName='"+XPATHName+"'";
	workM.updateSQL(sql);
}
workM.close();
if(bExistSameXPATHName){
	%>
	<html>
	<head>
	<LINK href="./img/style.css" rel=stylesheet type=text/css>
	<title>Export</title>
	</head>
	<body bgcolor="#F8FDFF">
	<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
	<tr><td height=10 align=right></td></tr>
	<tr>
	<td align=right class="zi" height=200 valign=top>
	
	
	
	<table width=500 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
	      <tr bgcolor="#C3DBE8"> 
	      <td height=25 colspan=2 align=center><span class="zi"><b>Alert</b></span></td>
	      </tr>      
	      <tr bgcolor="#F8FDFF"> 
	        <td height="25" align=right>Message:</td><td> 
	          	Add failure. There are similiar xpath name: <font color=red> <%=AllXPATHName%> </font>
	          </td>
	      </tr>
	</table>
	</td>
	</tr>
	</table>
	</body>
	</html>
	<%
}else{
	response.sendRedirect("xpath.jsp?ProjectID="+ProjectID);
}
%>
