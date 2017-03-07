<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />
<html>
<head>

<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title>Mapping Analysis</title>
<script language="JavaScript"> 
function myrefresh() 
{ 
window.location.reload(); 
} 
//setTimeout('myrefresh()',5000); 
</script> 
<script language="javascript">
<!--
function del()
{
  return confirm('Del?');
}

//-->
</script>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">
<%
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="12984";
}
String bgcolor="#F8FDFF";
workM.getConnection();
%>

<%  
   String sql="select * from ProjectReport where ProjectID='"+ProjectID+"'"; 
   ResultSet rs_get =workM.getURL(sql); 
   String ProductName="";String UserName="";
   if(rs_get.next())
   {
   	ProjectID=rs_get.getString("ProjectID");
	ProductName=rs_get.getString("ProductName");
	UserName=rs_get.getString("UserName");
   }
   rs_get.close();
   int iAll=0; int iMapped=0; int iNotMapped=0; int iNotMappedNewFuzzy=0;  int iMappingRate=0;    
   int iUniqueMappingRate=0; int iPLOC=0; int iTextToScreenNotMapped=0;
   int iNew=0; int iFuzzy=0; 
   int iNewRate=0; int iFuzzyRate=0; int iNewFuzzyRate=0; 
   int iMappedNew=0; int iMappedFuzzy=0; int IsUnique=0;
   int iMatchingRate=0; int iUnknown=0; int iI18NMappingError=0; int iI18UniqueError=0; 
   sql="select count(*) from I18NFile where ProjectID='"+ProjectID+"'"; 
   ResultSet rs=workM.getURL(sql); 
   if(rs.next()){
   	iAll=rs.getInt(1);
   }
   rs.close();
   sql="select count(*) from I18NFile where IsUnique=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs_unique=workM.getURL(sql); 
   if(rs_unique.next()){
   	IsUnique=rs_unique.getInt(1);
   }
   rs_unique.close();
   
   sql="select count(*) from I18NFile where mapped=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1=workM.getURL(sql); 
   if(rs1.next()){
   	iMapped=rs1.getInt(1);
   }
   rs1.close();
   
   sql="select count(*) from I18NFile where mapped=1 and NewString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1a=workM.getURL(sql); 
   if(rs1a.next()){
   	iMappedNew=rs1a.getInt(1);
   }
   rs1a.close();
   sql="select count(*) from I18NFile where mapped=1 and FuzzyString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs1b=workM.getURL(sql); 
   if(rs1b.next()){
   	iMappedFuzzy=rs1b.getInt(1);
   }
   rs1b.close();
   
   sql="select count(*) from I18NFile where mapped=0 and  ProjectID='"+ProjectID+"'"; 
   ResultSet rs2=workM.getURL(sql); 
   if(rs2.next()){
   	iNotMapped=rs2.getInt(1);
   }
   rs2.close();
   sql="select count(*) from I18NFile where mapped=0 and NewFuzzy=1 and  ProjectID='"+ProjectID+"'"; 
   ResultSet rs2_newfuzzy=workM.getURL(sql); 
   if(rs2_newfuzzy.next()){
   	iNotMappedNewFuzzy=rs2_newfuzzy.getInt(1);
   }
   rs2_newfuzzy.close();
   
   sql="select count(*) from UIText where ProjectID='"+ProjectID+"'"; 
   ResultSet rs3=workM.getURL(sql); 
   if(rs3.next()){
   	iPLOC=rs3.getInt(1);
   }
   rs3.close();
   sql="select count(*) from I18NFile where mapped=0 and ReasonID=0 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs4=workM.getURL(sql); 
   if(rs4.next()){
   	iUnknown=rs4.getInt(1);
   }
   rs4.close();
   sql="select count(*) from I18NFile where NewString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs5=workM.getURL(sql); 
   if(rs5.next()){
   	iNew=rs5.getInt(1);
   }
   rs5.close();
   sql="select count(*) from I18NFile where FuzzyString=1 and ProjectID='"+ProjectID+"'"; 
   ResultSet rs6=workM.getURL(sql); 
   if(rs6.next()){
   	iFuzzy=rs6.getInt(1);
   }
   rs6.close();
   sql="select count(*) from UIText where mapped=0 and ProjectID='"+ProjectID+"'";
   ResultSet rs7=workM.getURL(sql); 
   if(rs7.next()){
   	iTextToScreenNotMapped=rs7.getInt(1);
   }
   rs7.close();
   if(iAll==0){
   	iMatchingRate=0;
   	iMappingRate=0;
   	iUniqueMappingRate=0;
   	iNewRate=0;
   	iFuzzyRate=0;
   }else{
   	iMatchingRate=(10000*iPLOC)/iAll;
   	iMappingRate=(10000*iMapped)/iAll;
   	iUniqueMappingRate=(10000*iMapped)/IsUnique;
   	iNewRate=(10000*iMappedNew)/iNew;
   	iFuzzyRate=(10000*iMappedFuzzy)/iFuzzy;
   	iNewFuzzyRate=(10000*(iMappedFuzzy+iMappedNew))/(iNew+iFuzzy);
   }
   String sMatchingRate=String.valueOf(iMatchingRate);
   if(sMatchingRate.length()>2){
   	sMatchingRate=sMatchingRate.substring(0,sMatchingRate.length()-2)+"."+sMatchingRate.substring(sMatchingRate.length()-2);
   }else{
   	sMatchingRate="0."+sMatchingRate;
   }
   String sMappingRate=String.valueOf(iMappingRate);   
   if(sMappingRate.length()>2){
   	sMappingRate=sMappingRate.substring(0,sMappingRate.length()-2)+"."+sMappingRate.substring(sMappingRate.length()-2);
   }else{
   	sMappingRate="0."+sMappingRate;
   }
   String sUniqueMappingRate=String.valueOf(iUniqueMappingRate);
   if(sUniqueMappingRate.length()>2){
   	sUniqueMappingRate=sUniqueMappingRate.substring(0,sUniqueMappingRate.length()-2)+"."+sUniqueMappingRate.substring(sUniqueMappingRate.length()-2);
   }else{
   	sUniqueMappingRate="0."+sUniqueMappingRate;
   }
   String sNewRate=String.valueOf(iNewRate);
   if(sNewRate.length()>2){
   	sNewRate=sNewRate.substring(0,sNewRate.length()-2)+"."+sNewRate.substring(sNewRate.length()-2);
   }else{
   	sNewRate="0."+sNewRate;
   }
   String sFuzzyRate=String.valueOf(iFuzzyRate);
   if(sFuzzyRate.length()>2){
   	sFuzzyRate=sFuzzyRate.substring(0,sFuzzyRate.length()-2)+"."+sFuzzyRate.substring(sFuzzyRate.length()-2);
   }else{
   	sFuzzyRate="0."+sFuzzyRate;
   }
   String sNewFuzzyRate=String.valueOf(iNewFuzzyRate);
   if(sNewFuzzyRate.length()>2){
   	sNewFuzzyRate=sNewFuzzyRate.substring(0,sNewFuzzyRate.length()-2)+"."+sNewFuzzyRate.substring(sNewFuzzyRate.length()-2);
   }else{
   	sNewFuzzyRate="0."+sNewFuzzyRate;
   }
   iI18NMappingError=iPLOC-iMapped; 
%>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table border="1" cellpadding="0" cellspacing="0">
				<tr> 
					<td bgcolor='#FDE5E2' colspan="4"><span class="table_column_header_text">Project1</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_column_header_text">Language</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_column_header_text">Drop No</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_column_header_text">Schedule Start (ET)</span></td>
					<td bgColor='#FDE5E2' colspan="2"><span class="table_column_header_text">Result of Pre Process</span></td>
					<td bgColor='#FDE5E2' colspan="12"><span class="table_column_header_text">Result of Post Process</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_column_header_text">Schedule Finish (ET)</span></td>
				</tr>
				<tr>
					<td bgColor='#FDE5E2' rowspan="2"><span class="table_column_header_text">FY</span></td>
					<td bgColor='#FDE5E2' rowspan="2"><span class="table_column_header_text">Qtr</span></td>
					<td bgColor='#FDE5E2' rowspan="2"><span class="table_column_header_text">Active</span></td>
					<td bgColor='#FDE5E2' rowspan="2"><span class="table_column_header_text">Project Id</span></td>
					<td bgColor='#FDE5E2' colspan="2"><span class="table_column_header_text">Pseudo Localize</span></td>
					<td bgColor='#FDE5E2' colspan="4"><span class="table_column_header_text">Translation Unit Mapping</span></td>
					<td bgColor='#FDE5E2' colspan="7"><span class="table_column_header_text">UI Context Zip from LQA Framework</span></td>
					<td bgColor='#FDE5E2' rowspan="2"><span class="table_column_header_text">Last Translated At (ET)</span></td>
				</tr>
				<tr>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">Pseudo Localized At (ET)</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text"># of Unsynched Unit</span></td>
					<th bgColor='#FDE5E2'><span class="table_column_header_text">Status</span></th>
					<th bgColor='#FDE5E2'><span class="table_column_header_text"># of Unit</span></th>
					<th bgColor='#FDE5E2'><span class="table_column_header_text"># of Mapped Unit</span></th>
					<th bgColor='#FDE5E2'><span class="table_column_header_text">Mapped %</span></th>
					<th bgColor='#FDE5E2'><span class="table_column_header_text">Screenshot</span></th>
					<th bgColor='#FDE5E2' colspan="2"><span class="table_column_header_text">Pseudo Unique ID</span></th>
					<th bgColor='#FDE5E2' colspan="4"><span class="table_column_header_text"># of Element</span></th>
				</tr>

				<tr bgColor=#FDF8E2>
					<td class="data" rowspan="1">FY15</td>
					<td class="data" rowspan="1">Q2</td>
					<td class="data" rowspan="1">true</td>
					<td class="data" rowspan="1">00000815</td>
					<td class="data" rowspan="3">German</td>
					<td class="data" rowspan="3">Drop 1</td>
					<td class="data" rowspan="3">08/08/2014 00:00:00</td>
					<td class="data" rowspan="3">09/10/2014 04:36:49</td>
					<td class="table_data_numeric" rowspan="3">0</td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">Total</span></td>
					<td class="table_data_numeric">1,905</td>
					<td class="data_numeric_green_bold">230</td>
					<td class="data_numeric_blue_bold">12.07%</td>
					<td class="data" colspan="7">00000815_20140916072616.zip (Captured at: 09/16/2014 03:26:16 (ET) )</td>
					<td class="data" rowspan="3">09/16/2014 03:35:08</td>
					<td class="data" rowspan="3">09/18/2014 09:20:46</td>
				</tr>
				<tr bgColor=#FDF8E2>
					<td class="data" colspan="4" rowspan="2">NBI - CA - MCC 14.3</td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">New</span></td>
					<td class="table_data_numeric">127</td>
					<td class="table_data_numeric">8</td>
					<td class="data_numeric_blue_bold">6.30%</td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text"># of Screen</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text"># of PUID</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text"># of PUID No Rep</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">Total</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">With PUID</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">With PUID No Rep</span></td>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">Without Screen</span></td>
				</tr>
				<tr bgColor=#FDF8E2>
					<td bgColor='#FDE5E2'><span class="table_column_header_text">Fuzzy</span></td>
					<td class="table_data_numeric">145</td>
					<td class="table_data_numeric">26</td>
					<td class="data_numeric_blue_bold">17.93%</td>
					<td class="data_numeric_blue">63</td>
					<td class="table_data_numeric">1,355</td>
					<td class="table_data_numeric">245</td>
					<td class="table_data_numeric">1,285 </td>
					<td class="table_data_numeric">1,285 (100.00%)</td>
					<td class="data_numeric_green_bold">240 </td>
					<td class="data_numeric_red">1 (0.08%)</td>
				</tr>


				<tr>
					<td bgColor='#FDE5E2' colspan="8" rowspan="3"><span class="table_row_header_text">Average</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">0</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">Total</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">1,905</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">230</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">12.07%</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">63</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">1,355</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">245</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">1,285</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">1,285</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">240</span></td>
					<td bgColor='#FDE5E2' rowspan="3"><span class="table_row_header_text">1</span></td>
					<td bgColor='#FDE5E2' colspan="2" rowspan="3"><span class="table_row_header_text">&nbsp;</span></td>
				</tr>
				<tr>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">New</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">127</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">8</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">6.30%</span></td>
				</tr>
				<tr>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">Fuzzy</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">145</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">26</span></td>
					<td bgColor='#FDE5E2'><span class="table_row_header_text">17.93%</span></td>
				</tr>
			</table>
		</td>
	</tr>
</table>  

</body>
</html>

