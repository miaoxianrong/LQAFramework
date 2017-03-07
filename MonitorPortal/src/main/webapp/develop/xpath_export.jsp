<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>

<%@ page language="java" import="java.io.File"%>
<%@ page language="java" import="java.io.FileWriter" %>

<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String XpathID="";
String LevelID="";
String PathSeq="";
String XpathPath="";
String ProjectID="";
String UserID="";
String ProjectName="";
String ParentName="";
String ParentSeq="";
String tagName="";
String Attribute="";
String AttributeCondition="";
String AttributeValue="";
String XPATHName="";
String XPATHDesc="";
String seq="";
String XPATH="";
String TagOrURL="";
String NodeLevelID="";
String DOMType="";
String popup="";
String newtab="";
String dropdown="";
String doubleclick="";
String mouseright="";
String mouseover="";
String tips="";
String frame="";
String iframe="";
String leaf="";
String fromXML="";
String fromCSV="";

ProjectID=request.getParameter("ProjectID");
String sql="select * from XpathList where ProjectID='"+ProjectID+"'";
workM.getConnection();

String FullPageSource="";
FullPageSource="LevelID,PathSeq,XpathPath,ProjectID,UserID,ProjectName,ParentName,ParentSeq,NodeLevelID,tagName,Attribute,AttributeCondition,AttributeValue,XPATHName,XPATHDesc,";
FullPageSource+="seq,XPATH,TagOrURL,DOMType,popup,newtab,dropdown,doubleclick,mouseright,mouseover,tips,frame,iframe,leaf,fromXML,fromCSV";
FullPageSource+="\n";

ResultSet RS=workM.getSQL(sql);
while(RS.next()){
	XpathID=RS.getString("XpathID");
        LevelID=RS.getString("LevelID");
        PathSeq=RS.getString("PathSeq"); 
        XpathPath=RS.getString("XpathPath");
        ProjectID=RS.getString("ProjectID");
        UserID=RS.getString("UserID");
        	if(UserID.compareTo("")==0){UserID="NULL";}
        ProjectName=RS.getString("ProjectName");
        	if(ProjectName.compareTo("")==0){ProjectName="NULL";}
	ParentName=RS.getString("ParentName");
	ParentSeq=RS.getString("ParentSeq");
		ParentSeq=ParentSeq.replace(',','-');
		if(ParentSeq.compareTo("")==0){ParentSeq="NULL";}
	tagName=RS.getString("tagName");
	Attribute=RS.getString("Attribute");
	AttributeCondition=RS.getString("AttributeCondition");
	AttributeValue=RS.getString("AttributeValue");
		if(AttributeValue.compareTo("")==0){AttributeValue="NULL";}
	XPATHName=RS.getString("XPATHName");
	XPATHDesc=RS.getString("XPATHDesc");
		if(XPATHDesc.compareTo("")==0){XPATHDesc="NULL";}
	seq=RS.getString("seq");   
		seq=seq.replace(',','-');
		if(seq.compareTo("")==0){seq="NULL";}
	NodeLevelID=RS.getString("NodeLevelID");
	XPATH=RS.getString("XPATH");
		XPATH=XPATH.replace(',',';');	
		if(XPATH.compareTo("")==0){XPATH="NULL";}	
	TagOrURL=RS.getString("TagOrURL");	
	DOMType=RS.getString("DOMType");
	popup=RS.getString("popup");
	newtab=RS.getString("newtab");
	dropdown=RS.getString("dropdown");
	doubleclick=RS.getString("doubleclick");
	mouseright=RS.getString("mouseright");
	mouseover=RS.getString("mouseover");
	tips=RS.getString("tips");
	frame=RS.getString("frame");
	iframe=RS.getString("iframe");
	leaf=RS.getString("leaf");
	fromXML="0";
	fromCSV="1";
	
	FullPageSource+=LevelID+","+PathSeq+","+XpathPath+","+ProjectID+","+UserID+","+ProjectName+","+ParentName+",";
	FullPageSource+=ParentSeq+","+NodeLevelID+","+tagName+","+Attribute+","+AttributeCondition+","+AttributeValue+","+XPATHName+","+XPATHDesc+",";
	FullPageSource+=seq+","+XPATH+","+TagOrURL+","+DOMType+","+popup+","+newtab+","+dropdown+","+doubleclick+","+mouseright+","+mouseover+",";
	FullPageSource+=tips+","+frame+","+iframe+","+leaf+","+fromXML+","+fromCSV+"\n";
	//FullPageSource+="\n";
	
}
//out.print(FullPageSource+"<BR>");
RS.close();
RS=null;
sql="select ProductName from ProjectReport where ProjectID='"+ProjectID+"'";
ResultSet rs1=workM.getSQL(sql);
if(rs1.next()){
	ProjectName=rs1.getString("ProductName");
}
rs1.close();
rs1=null;
workM.close();
String FolderFileName="c:\\framework\\xpath\\"+ProjectID+"_"+ProjectName+"_xpath.csv";
//FolderFileName="D:\\working\\workspace\\Robot\\csv\\"+ProjectID+".csv";
try{  
	File webfile = new File(FolderFileName);
	if(webfile.exists()){
		webfile.delete();
	}
	FileWriter writer = new FileWriter(FolderFileName, true);  
	writer.write(FullPageSource);  
	writer.close();
}catch(IOException e){  
	       
}
response.sendRedirect("xpath_export_do.jsp?ProjectID="+ProjectID+"&ProjectName="+ProjectName);
%>
		
		