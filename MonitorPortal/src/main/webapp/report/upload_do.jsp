<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.io.*"%>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();

String pic_id_s=(String)request.getParameter("pic_id");
pic_id=Integer.parseInt(pic_id_s);
String sqltxt="select pic from test where pic_id="+pic_id;
ResultSet r = s.executeQuery(sqltxt);
r.next();
Blob b = r.getBlob("pic");
long size = b.length();
byte[] bs = b.getBytes(1, (int)size);
response.setContentType("image/jpeg");
OutputStream outs = response.getOutputStream();
outs.write(bs);
outs.flush();
%>