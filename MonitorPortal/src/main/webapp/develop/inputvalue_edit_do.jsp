<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();
String ProjectID=request.getParameter("ProjectID");
String NodePath=request.getParameter("NodePath");
String NameValueListID=request.getParameter("NameValueListID");

String InputProperty=request.getParameter("InputProperty");
String InputName=request.getParameter("InputName");
String InputValue=request.getParameter("InputValue");
String InputTitle=request.getParameter("InputTitle");
String InputRandom=request.getParameter("InputRandom");

if(InputRandom.compareTo("1")==0){
	String Today="";int iCountDays=0;
	java.util.Date now=new java.util.Date();
	int iYear=now.getYear()+1900;
	int iMonth=now.getMonth()+1;
	int iDay=now.getDate();
	iCountDays=(int)((iYear-2012)*365.25+(iMonth-1)*30.5+iDay); 
	int iHour=now.getHours();
	int iMinute=now.getMinutes();
	int iSeconds=now.getSeconds();
	String sYear=String.valueOf(iYear);
	String sMonth=String.valueOf(iMonth);
	if(iMonth<10){
		sMonth="0"+sMonth;
	}
	String sDay=String.valueOf(iDay);
	if(iDay<10){
		sDay="0"+sDay;
	}
	String sHour=String.valueOf(iHour);
	if(iHour<10){
		sHour="0"+sHour;
	}
	String sMinute=String.valueOf(iMinute);
	if(iMinute<10){
		sMinute="0"+sMinute;
	}
	String sScecond=String.valueOf(iSeconds);
	if(iSeconds<10){
		sScecond="0"+sScecond;
	}
	Today=sYear+sMonth+sDay+sHour+sMinute+sScecond;
	InputValue=InputValue+Today;
}

String sql="update NameValueList set "+"ProjectID='"+ProjectID+"'";
sql=sql+",InputProperty='"+InputProperty+"'";
sql=sql+",InputTitle='"+InputTitle+"'";
sql=sql+",InputName='"+InputName+"'";
sql=sql+",InputValue='"+InputValue+"'";
sql=sql+",InputRandom='"+InputRandom+"'";
sql=sql+" where NameValueListID="+NameValueListID;
workM.updateSQL(sql);
workM.close();
response.sendRedirect("input_node.jsp?ProjectID="+ProjectID+"&NodePath="+NodePath);
//out.print(sql+"<BR>");
%>
