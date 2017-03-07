<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();

String ProjectID=request.getParameter("ProjectID");
String XpathID=request.getParameter("XpathID");
String XPATHName=request.getParameter("XPATHName");
XPATHName=XPATHName.trim();
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

String MyLevelID=""; int iMyLevelID=0;
String MyParentName="";
String MyXpathPath=""; 
String MyNewXpathPath="";
String ChildSQL=""; ResultSet ChildRS=null;
String ChildXpathPath="";String ChildXpathID=""; 
String AllChildXpathIDXpathPath=""; String eachChildXpathIDXpathPath="";
String sql0="select * from XpathList where XpathID="+XpathID+" and ProjectID='"+ProjectID+"'";
ResultSet rs0=workM.getSQL(sql0);
if(rs0.next()){
	MyLevelID=rs0.getString("LevelID");
	iMyLevelID=Integer.parseInt(MyLevelID);
	MyParentName=rs0.getString("ParentName");
	MyXpathPath=rs0.getString("XpathPath");
}	
rs0.close();

String sql="update XpathList set ";
sql=sql+"tagName='"+tagName+"'";
sql=sql+",Attribute='"+Attribute+"'";
sql=sql+",AttributeCondition='"+AttributeCondition+"'";
sql=sql+",AttributeValue='"+AttributeValue+"'";

int iMyPathSeq=0; String PathSeq=""; 
String LevelID="";
String XpathPath="";
if(MyParentName.compareTo(ParentName)!=0){
//if(true){
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
		rs1=null;
		
		sql=sql+",LevelID=1";	
		sql=sql+",ParentName='"+ParentName+"'";
		sql=sql+",PathSeq='"+iMyPathSeq+"'";
		MyNewXpathPath=""+iMyPathSeq;
		sql=sql+",XpathPath='"+MyNewXpathPath+"'";	
	}else{
		sql=sql+",ParentName='"+ParentName+"'";
			
		String sql2="select * from XpathList where XPATHName='"+ParentName+"' and ProjectID='"+ProjectID+"'";
		ResultSet rs2=workM.getSQL(sql2);
		if(rs2.next()){
			LevelID=rs2.getString("LevelID");
			iMyLevelID=Integer.parseInt(LevelID)+1;
			sql=sql+",LevelID="+iMyLevelID;	
			XpathPath=rs2.getString("XpathPath");
			//out.print("XpathPath="+XpathPath+"<BR>");
		}
		rs2.close();
		rs2=null;
		
		String sql3="select * from XpathList where ParentName='"+ParentName+"' and ProjectID='"+ProjectID+"' order by PathSeq desc";
		ResultSet rs3=workM.getSQL(sql3);
		if(rs3.next()){
			PathSeq=rs3.getString("PathSeq");
			iMyPathSeq=Integer.parseInt(PathSeq)+1;
			sql=sql+",PathSeq="+iMyPathSeq;
			MyNewXpathPath=XpathPath+"-"+iMyPathSeq;
			sql=sql+",XpathPath='"+MyNewXpathPath+"'";
		}else{
			iMyPathSeq=1;
			sql=sql+",PathSeq="+iMyPathSeq;			
			MyNewXpathPath=XpathPath+"-"+iMyPathSeq;
			sql=sql+",XpathPath='"+MyNewXpathPath+"'";
		}
		rs3.close();
		rs3=null;
	}
	
	AllChildXpathIDXpathPath="";
	ChildSQL="select * from XpathList where XpathPath like '"+MyXpathPath+"-%' and ProjectID='"+ProjectID+"'";
	ChildRS=workM.getSQL(ChildSQL);
	while(ChildRS.next()){
		ChildXpathID=ChildRS.getString("XpathID");
		ChildXpathPath=ChildRS.getString("XpathPath");
		AllChildXpathIDXpathPath=AllChildXpathIDXpathPath+ChildXpathID+"_"+ChildXpathPath+",";
		
	}
	ChildRS.close();
	ChildRS=null;
	
	int iStart=0; int iEnd=0; int iLen=0; 
	StringTokenizer stLevel = null; int iLevel=0; String tempSeq="";
	StringTokenizer stArray=new StringTokenizer(AllChildXpathIDXpathPath,",");
	while(stArray.hasMoreTokens()){
		eachChildXpathIDXpathPath = stArray.nextToken();
		iEnd=eachChildXpathIDXpathPath.indexOf("_");
		ChildXpathID=eachChildXpathIDXpathPath.substring(0,iEnd);
		iLen=MyXpathPath.length();
		iStart=iEnd+iLen+1;
		ChildXpathPath=eachChildXpathIDXpathPath.substring(iStart+1);
		ChildXpathPath=MyNewXpathPath+"-"+ChildXpathPath;
		ChildSQL="update XpathList set ";
		ChildSQL=ChildSQL+" XpathPath='"+ChildXpathPath+"' ";
		stLevel=new StringTokenizer(ChildXpathPath,"-");
		iLevel=0;
		while(stLevel.hasMoreTokens()){
			tempSeq=stLevel.nextToken();
			iLevel++;
		}		
		ChildSQL=ChildSQL+",LevelID="+iLevel;
		ChildSQL=ChildSQL+" where XpathID="+ChildXpathID; 
		workM.updateSQL(ChildSQL);
		
		//out.print("ChildXpathPath="+ChildXpathPath+"<BR>");
		//out.print("iLevel="+iLevel+"<BR>");	
			
		
	}	
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

validSeq="";iCount=0;
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
}else{
	sql=sql+",popup=0";
}
if(DOMType.indexOf("newtab")!=-1){
	sql=sql+",newtab=1";	
}else{
	sql=sql+",newtab=0";
}
if(DOMType.indexOf("dropdown")!=-1){
	sql=sql+",dropdown=1";	
}else{
	sql=sql+",dropdown=0";
}
if(DOMType.indexOf("doubleclick")!=-1){
	sql=sql+",doubleclick=1";	
}else{
	sql=sql+",doubleclick=0";
}
if(DOMType.indexOf("mouseright")!=-1){
	sql=sql+",mouseright=1";	
}else{
	sql=sql+",mouseright=0";
}
if(DOMType.indexOf("mouseover")!=-1){
	sql=sql+",mouseover=1";	
}else{
	sql=sql+",mouseover=0";
}
if(DOMType.indexOf("tips")!=-1){
	sql=sql+",tips=1";	
}else{
	sql=sql+",tips=0";
}
if(DOMType.indexOf("frame")!=-1){
	sql=sql+",frame=1";	
}else{
	sql=sql+",frame=0";
}
if(DOMType.indexOf("iframe")!=-1){
	sql=sql+",iframe=1";	
}else{
	sql=sql+",iframe=0";
}
if(leaf.compareTo("yes")==0){
	sql=sql+",leaf=1";	
}else{
	sql=sql+",leaf=0";
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
sql=sql+" where XpathID="+XpathID+" and ProjectID='"+ProjectID+"'";
workM.updateSQL(sql);
workM.close();
response.sendRedirect("xpath.jsp?ProjectID="+ProjectID);
//out.print(sql+"<BR>");
%>
