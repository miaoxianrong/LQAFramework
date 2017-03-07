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
String includerName=request.getParameter("includerName");
String sql="insert into includerList set "+"ProjectID='"+ProjectID+"'";
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
sql=sql+",includerName='"+includerName+"'";
workM.updateSQL(sql);
workM.close();
response.sendRedirect("includer.jsp?ProjectID="+ProjectID);
//out.print(sql+"<BR>");
%>
