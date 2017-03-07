package db;
import java.io.BufferedReader;
import java.sql.*;
import java.security.*;
import java.security.acl.*;
import java.security.cert.*;

import javax.swing.JOptionPane;

import misc.toolbox;

public class ManageDB {
	String sDBDriver = "org.gjt.mm.mysql.Driver";
	
	//send email for DB status.
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null; 
	public ManageDB() 
	{
			
	}
	public void getConnection(String DBPosition)
    {   
		String sConnStr = "";
		boolean bConnected = false;
		try
		{	
			Class.forName(sDBDriver);
		}
		catch(java.lang.ClassNotFoundException e ) {}	
		try
	    {
			//String DBIP=JOptionPane.showInputDialog("Please input DB IP"); or 1=1!
			//where username=? and password=?  
			//where username=@aaa and password=@bbb mssql
			//PreparedStatement psmt = conn.prepareStatement(s);
			//psmt.setString(1,user);
			//psmt.setString(1,pass);
			//byte[] b;
			//java.security.spec.MGF1ParameterSpec.SHA512(b);
			
//			HttpWebRequest req=new HttpWebRequest("www.baidu.com");
//			HttpWebResponse res=req.getResonse();
//			BufferedReader br=res.getSteam(); //DataReader
//			char[] result;
//			br.read(result);
//			br.readLine(); //Html parser			
			
			if(DBPosition.equals("localhost")){
				sConnStr = "jdbc:mysql://"+DBPosition+":3306/thunis?user=octopus&password=miao@thunis&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
			}else{
				//sConnStr = "jdbc:mysql://"+DBPosition+":3306/linkrecord?user=admin&password=cabji18n&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
				sConnStr = "jdbc:mysql://"+DBPosition+":3306/framedb?user=dbadmin&password=cabji18n&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
			}
		   	conn = DriverManager.getConnection(sConnStr);
		   	stmt = conn.createStatement();
		   	bConnected = true;
	    }
	    catch(SQLException exception)
	    {
	    	//JOptionPane.showMessageDialog(null, "[DB error:] Check 1. JDBC; 2. DB IP; 3. etc/hosts");
	    	bConnected = false;
	    	System.out.println("[Alert:] Fail to get DB connection");
			System.out.println("[Solution:] Check 1. JDBC; 2. DB IP; 3. etc/hosts; 4. username,password,,etc");
			//send email
	    }			
		int i=0;
		while(!bConnected){
			i++;
			toolbox.waitSecond(9);
			if(i>100){
				i=1;
			}
			try
		    {
			   	conn = DriverManager.getConnection(sConnStr);
			   	stmt = conn.createStatement();
			   	bConnected = true;
		    }
		    catch(Exception exception)
		    {
		    	bConnected = false;
		    	System.out.println("[Alert:] Fail to get DB connection "+i+" times");
				System.out.println("[Solution:] Check DB IP,username,password,dabase name,DB service,jdbc,etc");
				//send email
		    }
			toolbox.waitSecond(9);
		}
	    	 
    }
	public boolean bGetDBConnection(String DBPosition)
    {   
		String sConnStr = "";
		boolean bConnected = false;
		try
		{	
			Class.forName(sDBDriver);
		}
		catch(java.lang.ClassNotFoundException e) {}	
		try
	    {
			if(DBPosition.equals("localhost")){
				sConnStr = "jdbc:mysql://localhost:3306/thunis?user=octopus&password=miao@thunis&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
			}else{
				sConnStr = "jdbc:mysql://"+DBPosition+":3306/linkrecord?user=admin&password=cabji18n&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
			}
		   	conn = DriverManager.getConnection(sConnStr);
		   	stmt = conn.createStatement();
		   	bConnected = true;
	    }
	    catch(Exception exception)
	    {
	    	bConnected = false;
	    	System.out.println("[Alert:] Fail to get DB connection");
			System.out.println("[Solution:] Check DB IP,username,password,dabase name,DB service,jdbc,etc");
			//send email
	    }			
		int i=0;
		while(!bConnected){
			i++;
			toolbox.waitSecond(9);
			if(i>100){
				i=1;
			}
			try
		    {
			   	conn = DriverManager.getConnection(sConnStr);
			   	stmt = conn.createStatement();
			   	bConnected = true;
		    }
		    catch(Exception exception)
		    {
		    	bConnected = false;
		    	System.out.println("[Alert:] Fail to get DB connection "+i+" times");
				System.out.println("[Solution:] Check DB IP,username,password,dabase name,DB service,jdbc,etc");
				//send email
		    }
			toolbox.waitSecond(9);
		}
	    return bConnected;	 
    }
	public ResultSet getSQL(String sql) 
	{
		rs = null;
		try{ 
			rs = stmt.executeQuery(sql);
		}
		catch(SQLException ex) 
		{
			System.out.println("[DB info:]  Check ManageDB.getSQL " + sql);
		}
		return rs;
	}
	public void updateSQL(String sql) 
	{
		try{ 
			//System.out.println("[DB info:] sql="+sql);
			stmt.execute(sql);
		}
		catch(SQLException ex) 
		{
			System.out.println("[DB info:] Check ManageDB.updateSQL or DB connection "+sql);
		}
	}		
	public int executeSQL(String sql) 
	{
		int result=0;
		try{ 
			//System.out.println("[DB info:] sql="+sql);
			result=stmt.executeUpdate(sql);
		}
		catch(SQLException ex) 
		{
			System.out.println("[DB info:] Check ManageDB.executeSQL or DB connection "+sql);
		}
		return result;
	}	
	public boolean updateUnique(String sql) 
	{
		boolean b1=false;
		try{ 
			//System.out.println("[.........:] sql="+sql);
			stmt.execute(sql);
			b1=true;
		}
		catch(SQLException ex) 
		{
			//System.out.println("[Alert:] Existing same record."+ex.getMessage());
			b1=false;
		}
		return b1;
	}
	public boolean updateUniqueSQL(int seq, String ProjectID,String ParentNodePath,String sql,boolean bDebugResumeXPATH) 
	{
		String logInfo=""; String errInfo="";
		int i=0;
		//System.out.println("[sql=] "+sql);
		boolean bUpdateSuccess=false;
		try{ 
			stmt.execute(sql);
			//i=stmt.executeUpdate(sql);
			bUpdateSuccess=true;
		}
		catch(SQLException ex) 
		{
			bUpdateSuccess=false;
			errInfo=ex.getMessage();
			errInfo=errInfo.replace('\'', ' ');			
			if(bDebugResumeXPATH){
				System.out.println("[Discard:] Element seq "+seq+" is discarded, not a valid unique node. Reason: "+errInfo+" sql="+sql);  
			}else{
				//System.out.println("[Debug:] Discard, not a valid unique node. Reason: "+ex.getMessage()+" sql="+sql);
			}
			logInfo="[Discard:] Element seq "+seq+" is discarded. Reason: "+errInfo;
			
			if(logInfo.length()>250) {logInfo=logInfo.substring(0,250);}
		}
		if(!bUpdateSuccess){
			String log_sql="insert into NodeLog set ProjectID='"+ProjectID+"',NodePath='"+ParentNodePath+"',Severity=3,Content='"+logInfo+"'";
			try{ 
				stmt.execute(log_sql);
			}
			catch(SQLException ex1) 
			{
				System.out.println("[Alert:] Can not insert into DB for discarding info "+errInfo);
			}
		}
		return bUpdateSuccess;
	}
	public void close()
	{
		try
	        {
	           if (rs != null) {
	                try {
	                	rs.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	           
	            if (stmt != null) {
	                try {
	                    stmt.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (conn != null) {
	                try {
	                    conn.close(); //still in pool
	                    conn = null; //can be set as null for single user
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    conn=null;
	                }
	            }
	        }
	        catch(Exception ex)
	        {
	            System.out.println("[DB info:] ManageDB.close(): Failed to close Data connection");
	        }
	}
}
