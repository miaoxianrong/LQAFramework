<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="Analysis" scope="page" class="record.AnalysisFilter" />

<%
String ProjectID="00000799";
String s1= Analysis.RunAnalysis();
//out.print(s1);
response.sendRedirect("../report/analysis_post.jsp?ProjectID="+ProjectID);
%>
