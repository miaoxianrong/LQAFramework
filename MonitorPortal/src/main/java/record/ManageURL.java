package record;
import java.sql.*;
public class ManageURL 
{
	String sDBDriver = "org.gjt.mm.mysql.Driver";
	String sConnStr = "jdbc:mysql://webauto:3306/framedb?user=dbadmin&password=cabji18n&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null; 
	public ManageURL() 
	{
		try
		{	
			Class.forName(sDBDriver);
		}
		catch(java.lang.ClassNotFoundException e) 
		{
			System.err.println("faq(): " + e.getMessage());
		}
	}
	public void getConnection()
        {    	
	    	 try
	         {
	        	conn = DriverManager.getConnection(sConnStr);
	        	stmt = conn.createStatement();
	        	//System.out.println("Success to get Data Connection.");
	         }
	         catch(Exception exception)
	         {
	             	System.out.println("Failed to get Data Connection:" + exception.getMessage());
	         }
       }
       public ResultSet getSQL(String sql) 
	{
		rs = null;
		try{ 
			rs = stmt.executeQuery(sql);
			//System.out.println("Success: ResultSet to get sql="+sql);
		}
		catch(SQLException ex) 
		{
			System.err.println("Error:.executeQuery: " + ex.getMessage());
			System.out.println(sql);
		}
		return rs;
	}
	public void updateSQL(String sql) 
	{
		try{ 
			stmt.execute(sql);
			//System.out.println("Success: Insert/Update/Delete, sql="+sql);
		}
		catch(SQLException ex) 
		{
			System.err.println("Error: Insert/Update/Delete error " + ex.getMessage());
		}
	}	
	public ResultSet getURL(String sql) 
	{
		rs = null;
		try{ 
			rs = stmt.executeQuery(sql);
			//System.out.println("Success: ResultSet to get sql="+sql);
		}
		catch(SQLException ex) 
		{
			System.err.println("Error:.executeQuery: " + ex.getMessage());
		}
		return rs;
	}
	public void updateLink(String sql) 
	{
		try{ 
			stmt.execute(sql);
			//System.out.println("Success: Insert/Update/Delete, sql="+sql);
		}
		catch(SQLException ex) 
		{
			System.err.println("Error: Insert/Update/Delete error " + ex.getMessage());
		}
	}
	public boolean insertURL(String ProjectID, String ROOT_URL, String PAGE_URL){
		boolean bSuccessInsert=true;
		try{ 	
			
			String sql="insert into LinkTable set ";
			sql+="ProjectID='"+ProjectID+"'";
			sql+=",ROOT_URL='"+ROOT_URL+"'";
			sql+=",PAGE_URL='"+PAGE_URL+"'";
			this.getConnection();
			
			String sql_aux="select * from LinkTable where PAGE_URL='"+PAGE_URL+"'";
			ResultSet rs_search=this.getURL(sql_aux);
			if(rs_search.next()){
				bSuccessInsert=false;
			}
			rs_search.close();
			if(bSuccessInsert){
				this.updateLink(sql);
			}
			this.close();
			//System.out.println("Success: Insert/Update/Delete, sql="+sql);
			
		}
		catch(Exception ex) 
		{
			System.err.println("Error: Insert/Update/Delete error " + ex.getMessage());
		}		
		return bSuccessInsert;
	}
	
	public void deleteURL(String ProjectID){
		this.getConnection();
		this.updateLink("delete from LinkTable where ProjectID='"+ProjectID+"'");
		this.close();		
	}
	public void createTable(String sql) 
	{
		
		try 
		{
			getConnection();
			stmt.executeUpdate(sql);
		}
		catch(SQLException ex) 
		{
			System.err.println("create table failure: " + ex.getMessage());
		}
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