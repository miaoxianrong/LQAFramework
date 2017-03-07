<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.io.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String IsBefore=request.getParameter("IsBefore");
if(IsBefore==null&&IsBefore.compareTo("-1")!=0){
	IsBefore="0";
}
String mapped=request.getParameter("mapped");
if(mapped==null||mapped.compareTo("null")==0){
	mapped="-1";
}
String ReasonID=request.getParameter("ReasonID");
if(ReasonID==null||ReasonID.compareTo("null")==0){
	ReasonID="-1";
}
String NewFuzzyAll=request.getParameter("NewFuzzyAll");
if(NewFuzzyAll==null||NewFuzzyAll.compareTo("null")==0){
	NewFuzzyAll="-1";
}
String ProjectID=request.getParameter("ProjectID");
String I18NFileID=request.getParameter("I18NFileID");
String InScope=request.getParameter("InScope");
String CommentByDev=request.getParameter("CommentByDev");
CommentByDev=CommentByDev.replace(',',';');
CommentByDev=CommentByDev.replace('\'', '"');
CommentByDev=CommentByDev.trim();
if(CommentByDev.length()>250){
	CommentByDev=CommentByDev.substring(0,248);
}
String sql="update I18NFile set CommentByDev='"+CommentByDev+"'";
sql=sql+",InScope="+InScope;
//sql=sql+",IsBefore="+IsBefore;
sql=sql+" where I18NFileID="+I18NFileID+" and ProjectID='"+ProjectID+"'";
workM.updateSQL(sql);
workM.close();

response.sendRedirect("../report/total.jsp?InScope="+InScope+"&mapped="+mapped+"&ReasonID="+ReasonID+"&NewFuzzyAll="+NewFuzzyAll+"&ProjectID="+ProjectID+"&IsBefore="+IsBefore);
//out.print("ReasonID="+ReasonID+"<BR>");
//select count(*) from I18NFile where ProjectID='00000265' and ReasonID>0 and mapped=1;
%>
