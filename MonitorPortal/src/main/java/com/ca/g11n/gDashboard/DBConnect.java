package com.ca.g11n.gDashboard;

import java.sql.*;

public class DBConnect
{
	private static Connection conn = null;
	private static ResultSet rs = null; 
	private static Statement stmt = null;
     
    public boolean GetConnection()
     {
    	 String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    	 String url = "jdbc:sqlserver://"; 
    	 String host = "liuyu04-gtc";  
    	 String port = "1435";  
    	 String dbName= "I18N";  
    	 String user = "sa";  
    	 String password = "Tng22l";
    	
         if (conn!=null)return true;
    	 try
         {
        	 Class.forName(driver);
             conn = DriverManager.getConnection(url + host + ":" + port + ";databaseName=" + dbName, user, password);
         }
         catch(Exception exception)
         {
             System.out.println("Failed to get Data Connection:" + exception.getMessage());
             return false;
         }
		 return true;
     }

    public boolean TestConnnetion() {  
        if (!GetConnection())
        {
        	return false;
        }
        CloseConnection();  
        return true;  
        }  
     
    public ResultSet executeQuery(String s)
    {
    	GetConnection();
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(s);
        }
        catch(SQLException sqlexception)
        {
            System.err.print("Query failed:" + sqlexception.getMessage());
            CloseConnection();
        }
        return rs;
    }
    
    public int executeUpdate(String s)
    {
        int i = 0;
        try
        {
            stmt = conn.createStatement();
            i = stmt.executeUpdate(s);
        }
        catch(SQLException sqlexception)
        {
            System.err.print("Update failed:" + sqlexception.getMessage());
            CloseConnection();
        }
        return i;
    }

    public void CloseConnection()
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
                    conn.close();
                    conn = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                    conn=null;
                }
            }
        }
        catch(Exception exception)
        {
            System.out.println("Failed to close Data connection:" + exception.getMessage());
        }
    }
}