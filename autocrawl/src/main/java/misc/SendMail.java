package misc;
import java.sql.ResultSet;
import java.util.Date; 
import java.util.Properties; 
import java.util.StringTokenizer;

import javax.mail.Message; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 

import com.codecowboy.jxmail.JXMail;
import com.codecowboy.jxmail.JXMessage;

import db.ManageDB;
import db.RunDB;
public class SendMail {
//	private String subject;
//	private String address; 
//	private String message;
//	final private String mailServer = "mail.ca.com";
//  final private String sentFromName = "LQA_Framework@ca.com";
	public static void send(String DBPosition,String ProjectID,String ProductName, String NodePath, String subject, String message, String address, String AttachedFile){
		String ClarityProjectID="";
		String DropNum="";
		String ProjectName="";
		String sql="select ClarityProjectID, DropNum, ProjectName from ProjectParameter  where ProjectId='"+ProjectID+"'";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs_get=m1.getSQL(sql);
			if(rs_get.next()){
				ClarityProjectID=rs_get.getString("ClarityProjectID");
				DropNum=rs_get.getString("DropNum");	
				ProjectName=rs_get.getString("ProjectName");				
			}
			rs_get.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check SendMail.send or DB connection");}
		m1.close();
		message=message+"\n";
		message=message+"Project ID:          "+ClarityProjectID+"\n";
		message=message+"Drop No.:            "+DropNum+"\n";
		message=message+"Product name:      "+ProjectName+"\n";
		message=message+"Abbreviation:        "+ProductName+"\n";
		message=message+"\n";
		message=message+"(Note: Please do not reply this email directly since it is sent out automatically.)\n";	
		message=message+"\n";	
		message=message+"\n";		
		message=message+"\n";	
		message=message+"LQA Framework UI-Context Service \n";
		message=message+"------------------------------------------------------------------------ \n";				
		message=message+"Engineering Services Effective Team \n";
		message=message+"Email: miaxi01@ca.com; sonmi02@ca.com; wu$ir01@ca.com \n";	
		message=message+"\n";			
		String EmailAddress="";
		
    	JXMessage msg = new JXMessage();
					
    	msg.setMailServer("mail.ca.com");
    	msg.setFromName("LQA_Framework@ca.com");    	
    	//msg.addToAddress(address);
    	subject="Notification email";
    	msg.setMailSubject(subject);
    	msg.setMessage(message);
    	if(!AttachedFile.isEmpty()){
    		msg.addFile(AttachedFile);	
    	}
//	    msg.setReplyTo("miaxi01@ca.com");	
    
    	JXMail mail = new JXMail();			    
    	address=address.replaceAll(" ", ";");
    	address=address.replaceAll(",", ";");
    	StringTokenizer st1 =new StringTokenizer(address,";");		
		while(st1.hasMoreTokens()){
	    	EmailAddress=st1.nextToken();
	    	EmailAddress=EmailAddress.trim();
	    	msg.addToAddress(EmailAddress);		
		 	try
		    {
		        mail.send(msg);
		        System.out.println("[Info:] A notification email has been sent out to "+EmailAddress);
		        System.out.println("[Info:] Email subject: "+subject);
		    }
		    catch(Exception ex1)
		    {
		    	System.out.println("[Alert:] A notification email cannot be sent out to ["+EmailAddress+"]");
		        System.out.println("[Alert:] Email send failure!"+ex1.getMessage());
		    	System.out.println("[Alert:] The email cannot be sent due to invalid email address ["+EmailAddress+"] or the environment/machine which does not support mail.ca.com.");
		    }
		}
	}
	public static void sendExceptionAlertEmail(String ProjectID,String DBPosition,String NodePath,String ProductName, String EmailSubject, String EmailBody,String AttachedFile){
		String EmailContact=RunDB.getEmailContact(DBPosition,ProjectID);
		ProductName=RunDB.getProductName(DBPosition,ProjectID);
		String ProjectName=RunDB.getProjectName(DBPosition,ProjectID);
		EmailSubject="["+ProjectID+"-"+ProjectName+"]"+ EmailSubject;
		EmailBody=EmailBody+"\n";
		SendMail.send(DBPosition,ProjectID,ProductName,NodePath,EmailSubject,EmailBody,EmailContact,AttachedFile);
		System.out.println("[EmailSubject:] "+EmailSubject);	
		System.out.println("[EmailBody:] "+EmailBody);			
	}
	public static void sendLoginAlertEmail(String ProjectID,String DBPosition,String NodePath,String ProductName, String ErrorDescription,String AttachedFile){
		String EmailSubject="";
		String EmailBody="";
		String EmailContact="";
//		String getErrorDescription="";
//		StringTokenizer st1 =new StringTokenizer(ErrorDescription,"{");
//		String newErrorDescription="";
//		while(st1.hasMoreTokens()){
//			getErrorDescription=st1.nextToken();
//			newErrorDescription+=getErrorDescription+"\n";
//		}		
//		if(!newErrorDescription.isEmpty()){
//			ErrorDescription=newErrorDescription;
//		}
		if(NodePath.equals("000")){
			System.out.println("[Alert:] Login error for nodepath "+NodePath);
			EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
			
			EmailSubject="["+ProjectID+"-"+ProductName+"]"+"Auto crawl can not be launched since login information such as URL, username, password, TenantID is not correct.";
			EmailBody=ErrorDescription+"\n";
		}else{
			System.out.println("[Alert:] Login error for nodepath "+NodePath);
			EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
			String ProjectName=RunDB.getProjectName(DBPosition,ProjectID);
			EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"Auto crawl cannot run now since product server may be shutdown suddenly.";
			EmailBody=ErrorDescription+"\n";
		}			
		ProductName=RunDB.getProductName(DBPosition,ProjectID);
		if(NodePath.equals("000")){
			
		}else{
			EmailBody+="NodePath:        "+NodePath+"\n";
		}
		
		SendMail.send(DBPosition,ProjectID,ProductName,NodePath,EmailSubject,EmailBody,EmailContact,AttachedFile);
		System.out.println("[EmailSubject:] "+EmailSubject);	
		System.out.println("[EmailBody:] "+EmailBody);			
	}
	public static void sendNotPLOCBuildAlertEmail(String ProjectID,String DBPosition,String NodePath,String ProductName, String ErrorDescription,String AttachedFile){
		String EmailSubject="";
		String EmailBody=""; 
		String EmailContact="";
//		String getErrorDescription="";
//		StringTokenizer st1 =new StringTokenizer(ErrorDescription,"{");
//		String newErrorDescription="";
//		while(st1.hasMoreTokens()){
//			getErrorDescription=st1.nextToken();
//			newErrorDescription+=getErrorDescription+"\n";
//		}		
//		if(!newErrorDescription.isEmpty()){
//			ErrorDescription=newErrorDescription;
//		}
		if(NodePath.equals("000")){
			System.out.println("[Alert:] It is not a PLOC build since no PLOC strings are found.");
			EmailContact = RunDB.getEmailContact(DBPosition,ProjectID);
			String ProjectName=RunDB.getProjectName(DBPosition,ProjectID);
			EmailSubject="["+ProjectID+"-"+ProjectName+"]"+"[Alert:] It is not a PLOC build since no PLOC strings are found.";
			EmailBody=ErrorDescription+"\n";
			//RunDB.setProjectErrorStatus(DBPosition, ProjectID);
		}		
		ProductName=RunDB.getProductName(DBPosition,ProjectID);
		SendMail.send(DBPosition,ProjectID,ProductName,NodePath,EmailSubject,EmailBody,EmailContact,AttachedFile);
		SendMail.send(DBPosition,ProjectID,ProductName,NodePath,EmailSubject,EmailBody,"miaxi01@ca.com",AttachedFile);
		System.out.println("[EmailSubject:] "+EmailSubject);	
		System.out.println("[EmailBody:] "+EmailBody);	
		if(NodePath.equals("000")){
			//System.exit(0);
		}
	}
}
