<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ page import="java.util.*" %>
<%@ page import="com.ca.g11n.gDashboard.*" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	String userName = "";	
	String pmf="";
	int iLength=0;
	int iPosition=0;
	String remoteUser = (String)request.getRemoteUser();
	if( remoteUser != null && remoteUser.indexOf( '\\' ) > 0 ) {
		userName = remoteUser.substring( remoteUser.indexOf( '\\' ) + 1 );
		iLength=userName.length();
		iPosition=iLength-7;
		pmf=userName.substring(iPosition);
	}else{
	
	}
	if( remoteUser != null){
		userName = remoteUser;
		iLength=userName.length();
		iPosition=iLength-7;
		pmf=userName.substring(iPosition);
	}
	response.sendRedirect("./ProjectManagement/proinfo.jsp?pmf="+pmf);
%>
