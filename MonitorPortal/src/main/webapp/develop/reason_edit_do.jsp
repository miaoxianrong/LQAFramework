<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String ReasonID=request.getParameter("ReasonID");
String Location=request.getParameter("Location");
String NotFoundReason=request.getParameter("NotFoundReason");
NotFoundReason=NotFoundReason.trim();
String Content=request.getParameter("Content");
Content=Content.trim();
Content=Content.replace('\'', '&');
Content=Content.replace(',', '@');

String sql="update ReasonList set ";
sql=sql+"NotFoundReason='"+NotFoundReason+"'";
sql=sql+",Location='"+Location+"'";
sql=sql+",Content='"+Content+"'";
sql=sql+" where ProjectID='"+ProjectID+"' and ReasonID="+ReasonID;
workM.updateSQL(sql);
workM.close();
response.sendRedirect("reason_edit.jsp?ReasonID="+ReasonID+"&ProjectID="+ProjectID);
//out.print(sql+"<BR>");
%>
