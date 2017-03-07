<%@ page language="java" import="java.io.File"%>
<%@ page language="java" import="java.io.BufferedWriter"%>
<%@ page language="java" import="java.io.FileWriter"%>
<%@ page language="java" import="java.io.IOException"%>
<%@ page language="java" import="java.io.BufferedReader"%>
<%@ page language="java" import="java.io.FileReader"%>
<%

String NodePath=request.getParameter("NodePath");
String KeyID=request.getParameter("KeyID");

String fileSnapShot="D:\\working\\workspace\\Robot\\web\\"+NodePath+".html";

String fileline="";String FullPageSource="";
int iFound=0; int iStart=0; int iEnd=0;
BufferedReader br =null; boolean bFileRead=false;
try{
	br = new BufferedReader (new FileReader(fileSnapShot));
	bFileRead=true;
	//out.print("fileline="+fileline+"<BR>");
}catch(Exception ex){
	bFileRead=false;
}


boolean bLastLine=false;
try{
	fileline = br.readLine();
}catch(Exception e2){}
if(fileline==null){
	bLastLine=true;
}
while(!bLastLine) {			
	iFound=fileline.indexOf(KeyID);
	if(iFound!=-1){
		fileline="<font color='red'><B>Found->"+"</B></font>"+fileline;
	}
	
	FullPageSource=FullPageSource+fileline+"\n";;
	try{
		fileline = br.readLine();
	}catch(Exception e2){}
	if(fileline==null){
		bLastLine=true;
	}
}
String FileName=NodePath+"_1.html";
String FolderFileName="D:\\working\\workspace\\Robot\\web\\"+FileName;
try{  
    File webfile = new File(FolderFileName);
    if(webfile.exists()){    	
    	webfile.delete();
    }
    FileWriter writer = new FileWriter(FolderFileName, true);  
    //BufferedWriter br=new BufferedWriter(writer);
    //br.close();
    writer.write(FullPageSource);  
    writer.close();
}catch(IOException e){  
    //e.printStackTrace();  
}
response.sendRedirect("../web/"+FileName);		
%>