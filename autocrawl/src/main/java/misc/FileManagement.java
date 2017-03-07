package misc;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
public class FileManagement {
public static final int BUFFER = 1024;
	
	public static void zipFile(String baseDir,String fileName) throws Exception{  
		System.out.println("--> Output to Zip : " + fileName);
        List fileList=getSubFiles(new File(baseDir));  
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(fileName));  
        ZipEntry ze=null;  
        byte[] buf=new byte[BUFFER];  
        int readLen=0;  
        for(int i = 0; i <fileList.size(); i++) {  
            File f=(File)fileList.get(i);  
            ze=new ZipEntry(getAbsFileName(baseDir, f));  
            ze.setSize(f.length());  
            ze.setTime(f.lastModified());     
            zos.putNextEntry(ze);  
            InputStream is=new BufferedInputStream(new FileInputStream(f));  
            while ((readLen=is.read(buf, 0, BUFFER))!=-1) {  
                zos.write(buf, 0, readLen);  
            }  
            is.close();  
        }  
        zos.close();  
    }  
 
    private static String getAbsFileName(String baseDir, File realFileName){  
        File real=realFileName;  
        File base=new File(baseDir);  
        String ret=real.getName();  
        while (true) {  
            real=real.getParentFile();  
            if(real==null)   
                break;  
            if(real.equals(base))   
                break;  
            else  
                ret=real.getName()+"/"+ret;  
        }  
        return ret;  
    }  
    private static List getSubFiles(File baseDir){  
        List ret=new ArrayList();  
        File[] tmp=baseDir.listFiles();  
        for (int i = 0; i <tmp.length; i++) {  
            if(tmp[i].isFile())  
                ret.add(tmp[i]);  
            if(tmp[i].isDirectory())  
                ret.addAll(getSubFiles(tmp[i]));  
        }  
        return ret;  
    }  
    
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public static ArrayList<String> generateFileList(String folderName){
    	ArrayList<String> fileList = new ArrayList<String>();
    	File folder = new File(folderName);
		String[] list = folder.list();
		for(String filename : list){
			fileList.add(filename);
		}
	return fileList;
 
    }

 
	        public static String getMD5(String fileName, String strDate, String PID) {
	                if( fileName == null ) return null;
	                if( !new File(fileName).exists() ) return null;
	                
	                String MD5 = "";
	             
	                try {
	                    
	                	MessageDigest md = MessageDigest.getInstance("MD5");
	                        FileInputStream in = new FileInputStream(fileName);
	                        byte[] data = new byte[256];
	                    int len;
	                    while ((len = in.read(data)) >=0) {
	                       md.update(data, 0, len);
	                    }
	                    in.close();
	                    
	                    byte[] digest = md.digest();
	                    for (int i = 0; i < digest.length; i++) {
	                         int d = digest[i];
	                         if (d < 0) {
	                                 d += 256;
	                         }
	                         if (d < 16) {
	                                 MD5+="0";
	                         }
	                         MD5+=Integer.toString(d, 16);
	                    }
	                 toolbox.writeToLOG(MD5, "./" + PID + "_" + strDate + "_MD5.txt"); 
	                } catch (IOException e) {
	                        e.printStackTrace();
	                } catch (NoSuchAlgorithmException e) {
	                        e.printStackTrace();
	                }
	                System.out.println("--> Creating MD5: " + MD5);    
	                MD5 = PID + "_" + strDate + "_MD5.txt";
	                return MD5;
	        }




	   /**
	    * Upload a file to a FTP server.
	    *
	    * @param ftpServer , FTP server address (optional port ':portNumber').
	    * @param user , Optional user name to login.
	    * @param password , Optional password for user.
	    * @param fileName , Destination file name on FTP server (with optional
	    *            preceding relative path, e.g. "myDir/myFile.txt").
	    * @param source , Source file to upload.
	    */
	   public static void upload(String ftpServer, String port, String user, String password,
	                String strTargetFilePath, String strSourceFile) throws Exception {
//	           System.out.println("--> Transferring: " + strSourceFile);
	        FTPClient ftpClient = new FTPClient();
	        File sourceFile = new File(strSourceFile);
	        boolean b = false;
	        String strReply="";

//	        if (ftpServer == null || strTargetFilePath == null || strSourceFile == null || !(new File(strSourceFile).exists())) {
//	                throw new Exception(resourceBundle.getString("I18N_1_INVALID_PARAMETER_PASSED"));
//	        }
	      
//	        if (dlgProgress != null) {
//	                        dlgProgress.setMessageLabel(MessageFormat.format(resourceBundle.getString("I18N_1_UPLOADING_0"), sourceFile.getName()));
//	                dlgProgress.showDialog();
//	        }
	        
	        //
	        // connect to the server 
	        // and check the result
	        //
	        System.out.println("Start to connect");
	        try {
	                ftpClient.connect(ftpServer, Integer.parseInt(port));
	        }
	        catch (IOException io) {
	        	System.out.println("Error:"+io.getMessage());
	                throw new Exception(MessageFormat.format("Cannot connect to FTP server {0}:\n{1}", ftpServer, io.toString()));
	        }
	        
	        strReply = ftpClient.getReplyString();
//	        CLogger.LOGGER.fine(strReply);
	        System.out.println("strReply");
	        //
	        // login to the server
	        //
	        b = ftpClient.login(user, password);
	        strReply = ftpClient.getReplyString();
//	        CLogger.LOGGER.fine(strReply);

	        if (!b) {
	                ftpClient.disconnect();
	                throw new Exception(MessageFormat.format("FTP server refused login:\n{0}", strReply));
	        }

	        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
	                ftpClient.disconnect();
	            throw new Exception(MessageFormat.format("FTP server refused connection:\n{0}", strReply));
	        }   

	        ftpClient.enterLocalPassiveMode();
	        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

	        //
	        // create target directories
	        //
	        String uploadDir = new File(strTargetFilePath).getParent().replaceAll("\\\\",  "/");
	        
	        String[] arrDirs = uploadDir.split("/");
	        for (String strDir : arrDirs) {
	                
	                boolean cd = ftpClient.changeWorkingDirectory(strDir);

	                if (!cd) {
	                        boolean mkdir = ftpClient.makeDirectory(strDir);
//	                CLogger.LOGGER.fine(ftpClient.getReplyString());

	                        if (!mkdir) {
	                        ftpClient.logout();
	                        ftpClient.disconnect();
//	                        throw new Exception(resourceBundle.getString("I18N_1_DIRECTORY_STRUCTURE_CANNOT_BE"));
	                    }
	                    cd = ftpClient.changeWorkingDirectory(strDir);
//	                CLogger.LOGGER.fine(ftpClient.getReplyString());
	                }
	        }
	        
	        //
	        // check source  file doesn't exist
	        //
	        FTPFile[] files = ftpClient.listFiles(".");
	        for (FTPFile ftpFile : files) {
	                if (ftpFile.getName() != null && ftpFile.getName().equals(sourceFile.getName())) {
	                ftpClient.logout();
	                ftpClient.disconnect();
	                throw new Exception(MessageFormat.format("Target file {0} exists.", sourceFile.getName()));
	                }
	        }
	        
	        // 
	        // upload file
	        //
	        InputStream fis = new FileInputStream(strSourceFile);
	        OutputStream os = ftpClient.storeFileStream(sourceFile.getName());

	        int iSum = 0;
	        byte buf[] = new byte[8192];
	        int bytesRead = fis.read(buf);
	        while (bytesRead != -1) {
	            iSum+=bytesRead;

	            os.write(buf, 0, bytesRead);
	            bytesRead = fis.read(buf);
//	            if (dlgProgress != null) {
//	                dlgProgress.setValue((int)Math.min(Integer.MAX_VALUE, iSum));
//	            }
	        }
//	        if (dlgProgress != null) {
//	                dlgProgress.setValue((int)Math.min(Integer.MAX_VALUE, sourceFile.length()));
//	        }
	        
	        fis.close();
	        os.close();
	        ftpClient.completePendingCommand();        

//	        if (dlgProgress != null) {
//	                dlgProgress.dispose();
//	        }
	        
	        //
	        // close handles
	        //
	        ftpClient.logout();
	        ftpClient.disconnect();
	    }
}
