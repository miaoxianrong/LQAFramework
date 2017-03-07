<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<%
workM.getConnection();
%>
<html>
<head>
<title>LQA Framework</title>
<link href="./img/cai_zhang_gui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./finance/js/md5.js"></script>
</head>
<body bgcolor="#125389">
<TABLE border=0 cellSpacing=0 cellPadding=0 width=100% height="100%" align=center>
  <TBODY>
  <TR>
    <TD align="center">
    <FORM method="post" name="login" action="login/login.jsp">
	
      <TABLE background="./img/framework.jpg" border=0 cellSpacing=0 cellPadding=0 width="466" height=237>
        <TBODY>
        <TR>
          <TD height=64 width="36%">&nbsp;</TD>
          <TD width="64%">&nbsp;</TD></TR>
        <TR>
          <TD height=95>&nbsp;</TD>
          <TD valign=top>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width="80%">
              <TBODY>
              <TR>
                <TD height=35 width="24%">Username: </TD>
                <TD width="76%"><INPUT type="input"  name="userid" value="LQA" tab="1" maxLength=20 class="srk" ></TD></TR>
              <TR>
                <TD height=35>Password: </TD>
                <TD><INPUT type="password" name="password" value="Tng22l" tab="2" maxLength=50 class="srk" ></TD></TR>
              <TR>
                <TD height=35>Project: </TD>
                <TD>
                <SELECT name=ProjectID size=1 class="tb">
	          <%
	          String ProjectID="";String ProductName="";
	          String sql="select ProjectID,ProductName from ProjectReport order by ReportID desc";
	          ResultSet rs=workM.getSQL(sql);
	          while(rs.next()){
	          	ProjectID=rs.getString("ProjectID");
	          	ProductName=rs.getString("ProductName");
	          %>
			<OPTION value="<%=ProjectID%>"><%=ProjectID%></OPTION>
		  <%
		  }
		  rs.close();
		  workM.close();
		  %>
		  </SELECT>
                
                </TD></TR>  
              </TBODY>
            </TABLE>                  
        </TD></TR>
        <TR>
          <TD height="23">&nbsp;</TD>
          <TD><TABLE border=0 cellSpacing=0 cellPadding=0 width="69%">
            <TBODY>
              <TR align=middle>
                <TD width="53%"><input type="image" src="./img/login.jpg" width=59 height=23></A>
                	</TD>
                <TD width="47%"><A href="#"><IMG border=0 src="./img/register.jpg" width=59 height=23></A></TD>
              </TR>
            </TBODY>
          </TABLE></TD>
        </TR>
        <TR>
          <TD>&nbsp;</TD>
          <TD>&nbsp;</TD>
        </TR>
        </TBODY></TABLE>
  <input type="hidden" id="mmsj" name="mmsj" value="5209a2ef0160d0592f54dc5e0cba7cf3">
  <input type="hidden" id="op" name="op" value="">
  <input type="hidden" id="login" name="login" value="">
  </FORM>
   </TD></TR>
</TBODY>
</TABLE>	
</body>
</html>
<script type="text/javascript" src="./img/login.js"></script>

                     