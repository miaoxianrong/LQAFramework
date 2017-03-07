<%@ page language="java" import="java.io.File"%>
<%@ page language="java" import="java.io.BufferedWriter"%>
<%@ page language="java" import="java.io.FileWriter"%>
<%@ page language="java" import="java.io.IOException"%>
<%@ page language="java" import="java.io.BufferedReader"%>
<%@ page language="java" import="java.io.FileReader"%>
<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<%

String Lang=request.getParameter("Lang");
String NodePath=request.getParameter("NodePath");
NodePath="001";
String KeyID=request.getParameter("KeyID");
KeyID="]]";
String fileSnapShot="c:\\framework\\webserver\\web\\"+NodePath+".html";

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

StringTokenizer stArray=null; int iKey=0; String PartLine=""; String PartLineUpdate=""; int iFenhaoStart=0; int iSignal=0;

while(!bLastLine) {			
	iFound=fileline.indexOf(KeyID);	
	if(iFound!=-1){
		stArray=new StringTokenizer(fileline,"]]");
		iKey=0;iSignal=0;
		while(stArray.hasMoreTokens()){
			iKey++;
			PartLine=stArray.nextToken();
			iFenhaoStart=PartLine.lastIndexOf("\"");
			if(iFenhaoStart==-1){
				iSignal=PartLine.lastIndexOf(">");
				out.print("iSignal="+iSignal+"<BR>");
			}else{
				PartLineUpdate=PartLine.substring(0,iFenhaoStart)+"Frech";
			}
			
		}
		//out.print("111111111="+fileline+"<BR>");
	}else{
		//out.print("22="+fileline+"<BR>");
	}	
	FullPageSource=FullPageSource+fileline+"\n";;
	try{
		fileline = br.readLine();
	}catch(Exception e2){}
	if(fileline==null){
		bLastLine=true;
	}
}

String FileName=NodePath+"_"+Lang+".html";
String FolderFileName="c:\\framework\\webserver\\web\\"+FileName;

/*
try{  
    File webfile = new File(FolderFileName);
    if(webfile.exists()){    	
    	webfile.delete();
    }
    FileWriter writer = new FileWriter(FolderFileName, true);  
    writer.write(FullPageSource);  
    writer.close();
}catch(IOException e){  
    //e.printStackTrace();  
}
*/  
response.sendRedirect("../web/"+FileName);		
%>