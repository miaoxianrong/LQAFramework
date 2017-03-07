<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
workM.getConnection();

String ProjectID=request.getParameter("ProjectID");
String TargetProjectID=request.getParameter("TargetProjectID");

String sql="";
String AllReason="";
String eachReason="";
String eachNotFoundReason="";
String eachLocation="";
String eachContent="";
sql="select * from ReasonList where ProjectID='"+ProjectID+"'";
ResultSet rs_get=workM.getSQL(sql); 
while(rs_get.next()){
	eachNotFoundReason=rs_get.getString("NotFoundReason");
	eachContent=rs_get.getString("Content");
	eachLocation=rs_get.getString("Location");
	AllReason+=eachLocation+eachNotFoundReason+"FENGEFU"+eachContent+" ";
}
rs_get.close();	
StringTokenizer stArray=new StringTokenizer(AllReason);
int iFen=0;
while(stArray.hasMoreTokens()){
	eachReason = stArray.nextToken();
	iFen=eachReason.indexOf("FENGEFU");
	if(iFen>0){
		eachLocation=eachReason.substring(0,1);
		eachNotFoundReason=eachReason.substring(2,iFen);			
		eachContent=eachReason.substring(iFen+7);
		sql="insert into ReasonList set ";
		sql=sql+"ProjectID='"+TargetProjectID+"'";
		sql=sql+",NotFoundReason='"+eachNotFoundReason+"'";
		sql=sql+",Location='"+eachLocation+"'";
		sql=sql+",Content='"+eachContent+"'";
		sql=sql+",ImportCommon=1";
		workM.updateSQL(sql);
		//out.print(sql+"<BR>");
		//out.print("eachNotFoundReason="+eachNotFoundReason+" eachContent="+eachContent+"<BR>");
	}
	
}
workM.close();
response.sendRedirect("reason.jsp?ProjectID="+TargetProjectID);
//out.print(sql+"<BR>");
%>
