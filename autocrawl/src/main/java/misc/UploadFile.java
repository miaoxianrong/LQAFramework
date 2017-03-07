package misc;

import java.util.Date;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class UploadFile {
	
	public static void TransferToFTP(String ProjectID){
		String zipFile = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss"); 
	    format.setTimeZone(TimeZone.getTimeZone("GMT"));
	    Date date = new Date();
	    String strDate = format.format(date);
	    String PID = ProjectID;   
	    zipFile =PID + "_" + strDate + ".zip";	   
        try {
             mapping.uiContext.zipFile("./output/"+ProjectID+"/screen/", zipFile);
        } catch (Exception e1) {e1.printStackTrace();}
        String fileMD5 = misc.FileManagement.getMD5(zipFile, strDate, PID);
        int iLength=0;
        iLength=ProjectID.indexOf("-");
        if(iLength>-1){
        	PID=ProjectID.substring(0,iLength);
        }
        try {
        	//PID = projectid.getText().trim();
            
            /*------------upload to xlat.ca.com-----------------------------------*/
            //mapping.uiContext.upload("iijku01-vm95397", "10021", "lfUser", "LlF1u0sne@r", "/LQAframework/UIcontext/" + PID + "/" + fileMD5, fileMD5);
           // mapping.uiContext.upload("xlat.ca.com", "10021", "lfUser", "LlF1u0sne@r", "/LQAframework/UIcontext/" + PID + "/" + fileMD5, fileMD5);
           // emptylabel.setText(" Uploading " + fileMD5);
            //mapping.uiContext.upload("iijku01-vm95397", "10021", "lfUser", "LlF1u0sne@r", "/LQAframework/UIcontext/"  + PID + "/" + zipFile, zipFile);
           // mapping.uiContext.upload("xlat.ca.com", "10021", "lfUser", "LlF1u0sne@r", "/LQAframework/UIcontext/"  + PID + "/" + zipFile, zipFile);
            uploadFile("xlat.ca.com", 10021, "lfUser", "LlF1u0sne@r","LQAframework/UIcontext/" + PID , fileMD5 ); 
            uploadFile("xlat.ca.com", 10021, "lfUser", "LlF1u0sne@r","LQAframework/UIcontext/" + PID , zipFile );
            /*------------upload to ftpcai-----------------------------------*/
           
            uploadFile("ftpcai", 21, "ftpallvdrprj", "u1KsgP02","CA_outgoing/LQAframework/UIcontext/" + PID , fileMD5 ); 
            uploadFile("ftpcai", 21, "ftpallvdrprj", "u1KsgP02","CA_outgoing/LQAframework/UIcontext/" + PID , zipFile ); 

        } catch (Exception e1) { 
        	e1.printStackTrace();
        	System.out.println(e1.getMessage());
        }
        System.out.println("[info:] /****************** Upload done **********************/");
	}
	public static void uploadFile(String s_server, int s_port, String s_user, String s_password ,String s_targetpath, String s_localfile ){
	       
	       FTPClient ftpClient = new FTPClient();
	        try{
	              ftpClient.connect(s_server, s_port);                      
	              ftpClient.login(s_user, s_password);  
	              
	              ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	              //source file
	              FileInputStream in = new FileInputStream(new File("./" + s_localfile)); 
	              
	              ftpClient.changeWorkingDirectory(s_targetpath);  
	              ftpClient.storeFile(s_localfile, in);
	              
	        }catch(java.io.IOException ex){}
	        finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (java.io.IOException ex) {
	                ex.printStackTrace();
	            } 
	        }
	    }

}
