<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String ParentName=request.getParameter("ParentName");
String ParentSeq=request.getParameter("ParentSeq");
String tagName=request.getParameter("tagName");
String Attribute=request.getParameter("Attribute");
String AttributeCondition=request.getParameter("AttributeCondition");
String AttributeValue=request.getParameter("AttributeValue");
AttributeValue=AttributeValue.trim();
String domtypeName=request.getParameter("domtypeName");
String DOMType=request.getParameter("DOMType");
String leaf=request.getParameter("leaf");
String sql="insert into domtypeList set "+"ProjectID='"+ProjectID+"'";
sql=sql+",ParentName='"+ParentName+"'";

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

sql=sql+",tagName='"+tagName+"'";
sql=sql+",Attribute='"+Attribute+"'";
sql=sql+",AttributeCondition='"+AttributeCondition+"'";
sql=sql+",AttributeValue='"+AttributeValue+"'";
sql=sql+",domtypeName='"+domtypeName+"'";
sql=sql+",DOMType='"+DOMType+"'";
if(DOMType.indexOf("popup")!=-1){
	sql=sql+",popup=1";	
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
workM.updateSQL(sql);
workM.close();
response.sendRedirect("domtype.jsp?ProjectID="+ProjectID);
//out.print(sql+"<BR>");
%>
