<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.io.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String ProductComment=request.getParameter("ProductComment");
ProductComment=ProductComment.replace(',',';');
ProductComment=ProductComment.replace('\'', '"');
ProductComment=ProductComment.trim();
if(ProductComment.length()>250){
	ProductComment=ProductComment.substring(0,248);
}
String sql="update ProjectReport set ProductComment='"+ProductComment+"'";
sql=sql+" where ProjectID='"+ProjectID+"'";
workM.updateSQL(sql);
workM.close();

response.sendRedirect("../report/analysis_post.jsp?ProjectID="+ProjectID);
%>
