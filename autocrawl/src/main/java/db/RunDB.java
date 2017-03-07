package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

import core.loginStart;
import misc.toolbox;



public class RunDB {	
	public static void doPost(String ProjectID,String DBPosition){
		String XPATH="";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{			
			boolean bGetProjectID=false;
			String ClarityProjectID= ProjectID;
			String pname = "ProjectName";
			pname = pname.replaceAll("'", "''");
			
			String projectAlias = "NA";	
			
			String langs = "ja";				
			
			String url = "URL";
			url = url.replaceAll("'", "''");
			//System.out.println("url: " + url);
			
			String username = "UserName";
			username = username.replaceAll("'", "''");
			String password = "password";
			password = password.replaceAll("'", "''");
			String TenantID = "TenantID";
			TenantID = TenantID.replaceAll("'", "''");
			String mail = "mail";
			mail = mail.replaceAll("'", "''");
			String drop = "1";
			
			String build = "build1";
			build = build.replaceAll("'", "''");			
			String pid  = ClarityProjectID+"-"+drop+"-"+build; 		
			String estimate = "555";
			
			String sql="select ProjectID from projectparameter where ProjectID ='"+pid+"'";
			
			m1.updateSQL(sql);
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bGetProjectID=true;
				//System.out.println("[Info:] Get projectparameter for ProjectID "+pid);
			}else{
				//System.out.println("[Info:] Not Get projectparameter for ProjectID "+pid);
			}
			rs.close();
			rs=null;
			if(bGetProjectID){
				System.out.println("[Info:] update projectparameter for ProjectID "+pid);
				sql="update projectparameter set buildno='"+build+"'";
				sql=sql+",Estimate='555'"; //int
				sql=sql+",ProjectName='ProjectName'";
				sql=sql+",ProductAlias='ProductAlias'";
				sql=sql+",Language='ja'";
				sql=sql+",URL='URL'";
				sql=sql+",UserName='UserName'";
				sql=sql+",Password='Password'";				
				sql=sql+",TenantID='TenantID'";
				sql=sql+",EmailContact='EmailContact'";
				sql=sql+",ProjectStatus='1'"; //tinyint
				sql=sql+",DropNum='"+drop+"'"; //int
				sql=sql+" where ProjectID='"+pid+"'";
			}else{
				//System.out.println("[Info:] Insert projectparameter for ProjectID "+pid);
				
				sql="INSERT INTO projectparameter (buildno, ProjectID, ProductAlias, ProjectName, Language, URL, UserName, Password, TenantID, EmailContact, AdminContact, ProjectStatus, DropNUM, ClarityProjectID, Estimate) VALUES ('" + build + "', '" + pid + "', '" + projectAlias + "','" + pname + "','" + langs +"','" + url +"','" + username +"','" + password + "','" + TenantID +"','" + mail + "','admin@ca.com','1','" + drop +"','" + ClarityProjectID + "','" + estimate + "')";
				
			}
			int result=0;
			result=m1.executeSQL(sql);		
			if(result>=0){
				//System.out.println("[Info:] Success for ProjectID "+pid);
			}else{
				System.out.println("[Alert:] Fail for ProjectID "+pid);
			}
			//sql="delete from projectparameter where ProjectID ='"+pid+"'";
			//result=m1.executeSQL(sql);		
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.doPost or DB connection");}
		m1.close();
	}
	public static boolean clearProject(String ProjectID,String ProductName,String DebugServer,String EmailContact,String DBPosition, String RunMode){
		boolean bDebug=false; if(RunMode.equals("Debug")){bDebug=true;}
		boolean bClearSuccess=false;
		boolean bExist=false;
		String UserName=EmailContact;
		String AllUserName=",liahu01,genzh01,liuyu04,sonmi02,xioza01,caimi03,miaxi01,";		
		int iSignal=EmailContact.indexOf("@ca.com");
		if(iSignal>0){
			UserName=EmailContact.substring(0,iSignal);
			bExist=true;
		}
		//bExist=AllUserName.contains(","+UserName+",");		
		if(bExist){
			bClearSuccess=true;
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);	
			//try{
						
				//m1.updateSQL("delete from ProjectReport");
				m1.updateSQL("delete from NodeList where ProjectID='"+ProjectID+"'");
				m1.updateSQL("delete from NodeLog where ProjectID='"+ProjectID+"'");
				//if(bDebug){
					m1.updateSQL("delete from PlocTable where ProjectID='"+ProjectID+"'");
					m1.updateSQL("delete from NewFuzzyTable where ProjectID='"+ProjectID+"'");
					m1.updateSQL("delete from NodeAllText where ProjectID='"+ProjectID+"'");
					m1.updateSQL("delete from NodeContext where ProjectID='"+ProjectID+"'");
				//} 
				/************	
				String sql="";
				sql="insert into ProjectReport set ";
	    		sql+="ProjectID='"+ProjectID+"'";
	    		sql+=",ProductName='"+ProductName+"'";
	    		sql+=",UserName='"+UserName+"'";
				boolean bInsert=m1.updateUnique(sql);
				if(!bInsert){
					sql="update ProjectReport set ";
					sql+="ProductName='"+ProductName+"'";
		    		sql+=",UserName='"+UserName+"'";
		    		sql+=" where ProjectID='"+ProjectID+"'";		    		
		    		m1.updateUnique(sql);
				}
				
				sql="insert into ProjectParameter set ";
	    		sql+="ProjectID='"+ProjectID+"'";
	    		sql+=",ProductName='"+ProductName+"'";
	    		sql+=",UserName='"+UserName+"'";
	    		sql+=",DebugServer='"+DebugServer+"'";
	    		bInsert=m1.updateUnique(sql);
				if(!bInsert){
					sql="update ProjectParameter set ";
					sql+="ProductName='"+ProductName+"'";
		    		sql+=",UserName='"+UserName+"'";
		    		sql+=",DebugServer='"+DebugServer+"'";
		    		sql+=" where ProjectID='"+ProjectID+"'";
		    		m1.updateUnique(sql);
				}
				************/
				//select ProjectID, DebugServer from ProjectParameter;
				
			//}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearDB or DB connection");}
			m1.close();
		}else{
			System.out.println("[Alert:] Stop running and project information will be deleted soon. EmailContact should be valid pmf@ca.com, please confirm it.");
			ManageDB m2 = new ManageDB();
			m2.getConnection(DBPosition);	
			m2.updateSQL("delete from ProjectParameter where ProjectID='"+ProjectID+"'");
			m2.updateSQL("delete from ProjectReport where ProjectID='"+ProjectID+"'");
			m2.close();
			System.exit(0);
		}
		return bClearSuccess;
	}	
	public static void clearSubLevel(String ProjectID,String DBPosition){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			m1.updateSQL("delete from nodelist where levelid>1 and ProjectID='"+ProjectID+"'");
			m1.updateSQL("update nodelist set touchonce=0,childnum=0,branch=1 where ProjectID='"+ProjectID+"'");
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearDB or DB connection");}
		m1.close();
	}
	public static void updateDB(String sql,String DBPosition){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.clearDB or DB connection");}
		m1.close();
	}
	public static void initPlay(String ProjectID,String DBPosition){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);  
		try{
			
			m1.updateSQL("update NodeList set playonce=0, capture=0, screenName='', screenDesc='', inQueue=0 where ProjectID='"+ProjectID+"'");
			//not set validNode=0;
			m1.updateSQL("delete from HardcodeList where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from UniqueStringList where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from truncationlist where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from iformatlist where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NodeLog where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from PlocTable where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NewFuzzyTable where ProjectID='"+ProjectID+"'");
			m1.updateSQL("delete from NodeAllText where ProjectID='"+ProjectID+"'");			
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.initPlay or DB connection");}
		m1.close();
	}
	public static void addLog(String ProjectID,String DBPosition,String NodePath,int Severity, String Content){		
		//System.out.println(Content.replace('&', '\''));
		if(Content.length()>250){
			Content=Content.substring(0,250);
		}
//		double timer_begin_second = System.currentTimeMillis()/1000.000;
		if(true){
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);  
			try{				
				m1.updateSQL("insert into NodeLog set ProjectID='"+ProjectID+"',NodePath='"+NodePath+"',Severity="+Severity+",Content='"+Content+"'");
			}catch(Exception ex){
				System.out.println("[Alert:] Check RunDB.initPlay or DB connection. Error reason:"+ex.getMessage());
			}
			m1.close();
		}
//		double timer_end_second = System.currentTimeMillis()/1000.000;
//		double time_spent_second = timer_end_second - timer_begin_second;	
//		System.out.println("\n[Info:] addLog time spent: " + time_spent_second + " seconds");
	}
	public static void clearClickFail(String ProjectID,String DBPosition){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);  
		try{
			m1.updateSQL("update NodeList set clickfail=0 where ProjectID='"+ProjectID+"'");			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.initPlay or DB connection");}
		m1.close();
	}
	public static void clearTouchClickFail(String ProjectID,String DBPosition,String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);  
		try{
			
			m1.updateSQL("update NodeList set touchonce=0,clickfail=0 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.initPlay or DB connection");}
		m1.close();
	}
	public static void checkChild(String ProjectID,String DBPosition,String NodePath){
	
		String sql="select childnum from NodeList where ProjectID='"+ProjectID+"' and NodePath ='"+NodePath+"'";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);  
		try{
			
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.initPlay or DB connection");}
		m1.close();
	}
	public static void deleteTree(String ProjectID,String DBPosition,String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);  
		try{
			
			m1.updateSQL("delete from NodeList where ProjectID='"+ProjectID+"' and NodePath like '"+NodePath+"%'");
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.initPlay or DB connection");}
		m1.close();
	}
	public static void deleteChildTree(String ProjectID,String DBPosition,String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);  
		try{
			
			m1.updateSQL("delete from NodeList where ProjectID='"+ProjectID+"' and NodePath like '"+NodePath+"-%'");
			m1.updateSQL("update NodeList set touchonce=0,clickfail=0,clicktimes=0,childNum=0 where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'");
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.initPlay or DB connection");}
		m1.close();
	}
	public static ArrayList<Integer> getNodeSeq(String ProjectID,String DBPosition,int ParentSeq,int LevelId, String clickMode){ //remove it later
		
		ArrayList<Integer> arraySeq= new ArrayList<Integer>();
		String sql="select * from NodeList where ProjectId='"+ProjectID+"' and ParentSeq="+ParentSeq+" and LevelId="+LevelId;
			//sql+=" and special=1 ";
			//sql+=" and visible=1 and clickable=1";
			//sql+=" and "+ clickMode + " =1 "; //clickMode = special, clickable
		sql+=" and special=1 and clickable=1";
		sql+=" order by seq ASC"; 
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs_get=m1.getSQL(sql);
			while(rs_get.next()){
				arraySeq.add(Integer.valueOf(rs_get.getString("seq")));		
			}
			rs_get.close();
			//rs_get=null;
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getSeq or DB connection");}
		m1.close();
		return arraySeq;
	}
	public static int getLevel1NodeNum(String ProjectID,String DBPosition){ 
		
		int iNodeNum=0;
		String sql="select count(*) from NodeList where LevelID=1 and ProjectId='"+ProjectID+"'";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs_get=m1.getSQL(sql);
			if(rs_get.next()){
				iNodeNum=rs_get.getInt(1);		
			}
			rs_get.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getSeq or DB connection");}
		m1.close();
		return iNodeNum;
	}
	
	public static String getPathVector(String ProjectID,String DBPosition, String vector){
		
		String NodePath = "";
		String sql="select NodePath from NodeList where vector='"+vector+"' and ProjectID='"+ProjectID+"'";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs_get=m1.getSQL(sql);
			if(rs_get.next()){
				NodePath=rs_get.getString("NodePath");		
			}
			rs_get.close();
			//rs_get=null;
			
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
		return NodePath;
	}
	public static void setClickTimes(String ProjectID,String DBPosition, String NodePath){
		
		String ClickTimes = ""; int iClickTimes=0; //for agile
		if(!NodePath.equals("")){
			String sql="select clicktimes from NodeList where NodePath='"+NodePath+"' and ProjectID='"+ProjectID+"'";
			ManageDB m1 = new ManageDB();
			m1.getConnection(DBPosition);
			try{
				
				ResultSet rs_get=m1.getSQL(sql);
				if(rs_get.next()){
					ClickTimes=rs_get.getString("clicktimes");		
				}
				rs_get.close();
				iClickTimes=Integer.valueOf(ClickTimes)+1;
				sql="update NodeList set clicktimes="+iClickTimes+" where NodePath='"+NodePath+"' and ProjectID='"+ProjectID+"'";
				m1.updateSQL(sql);
				
			}catch(Exception ex){
				System.out.println("[DB Error:] Check RunDB.setClickTimes or DB connection. Error message:"+ex.getMessage());
			}
			m1.close();
		}
	}
	
	public static void setClickFail(String ProjectID,String DBPosition, String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			//for the whole tree, set clickfail=1, how to do for the queued node path of clickPath, then?
			String sql="update NodeList set clickfail=1 where NodePath ='"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
	}
	public static void setPageFilter(String ProjectID,String DBPosition, String NodePath,String PageFilter){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			//for the whole tree, set clickfail=1, how to do for the queued node path of clickPath, then?
			String sql="update NodeList set PageFilter='"+PageFilter+"' where NodePath ='"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
		
	}
	public static void setFirstXpath(String ProjectID,String DBPosition,String ProductName, String FirstXPATH){
		String XPATH="";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			
			boolean bGetXpath=false;
			String sql="select * from XpathList where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bGetXpath=true;
			}
			rs.close();
			rs=null;
			if(!bGetXpath){
				System.out.println("[Info:] Insert MenuFilter into DB");
				sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
				sql=sql+",ParentName='Original'";
				sql=sql+",XPATHName='"+ProductName+"Menu'";
				sql=sql+",PathSeq=1";
				sql=sql+",XpathPath='1'";
				XPATH=FirstXPATH;
				XPATH=XPATH.replace('\'', '&');
				sql=sql+",XPATH='"+XPATH+"'";
				m1.updateSQL(sql);			
			}
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
	}
	public static void setFirstSecondXpath(String ProjectID,String DBPosition,String ProductName, String FirstXPATH,String SecondXPATH){
		String XPATH="";
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			
			boolean bGetXpath=false;
			String sql="select * from XpathList where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bGetXpath=true;
			}
			rs.close();
			rs=null;
			if(!bGetXpath){
				sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
				sql=sql+",ParentName='Original'";
				sql=sql+",XPATHName='First"+ProductName+"'";
				sql=sql+",PathSeq=1";
				sql=sql+",XpathPath='1'";
				XPATH=FirstXPATH;
				XPATH=XPATH.replace('\'', '&');
				sql=sql+",XPATH='"+XPATH+"'";
				m1.updateSQL(sql);
			
				sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
				sql=sql+",ParentName='First"+ProductName+"'";
				sql=sql+",XPATHName='Second"+ProductName+"'";
				sql=sql+",PathSeq=1";
				sql=sql+",LevelID=2";
				sql=sql+",XpathPath='1-1'";
				XPATH=SecondXPATH;
				XPATH=XPATH.replace('\'', '&');
				sql=sql+",XPATH='"+XPATH+"'";
				m1.updateSQL(sql);
			}
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}	
		m1.close();
	}
	public static void importFromCSVFile(String ProjectID,String DBPosition,String ImportType,String ImportFile) 
	{
		String filename=ImportFile;
		File fileElement= new File(filename); 		
		if(fileElement.exists()){
			RunDB.addLog(ProjectID, DBPosition, "000", 1, "[Info:] Detect "+ImportFile+". Start to get "+ImportType);	
			System.out.println("[Info:] Detect "+ImportFile+". Start to get "+ImportType);
			String sql=""; 
			String fileline=""; 
			ManageDB mdb = new ManageDB();
			mdb.getConnection(DBPosition);
			if(ImportType.equals("XPATH")){
				sql="delete from XpathList where ProjectID='"+ProjectID+"'";
				sql+=" and fromCSV=1";
				mdb.updateSQL(sql);	
			}
			if(ImportType.equals("Excluder")){
				sql="delete from excluderList where ProjectID='"+ProjectID+"'";				
				mdb.updateSQL(sql);	
			}
			BufferedReader br =null; boolean bFileRead=false;
			try{
				br = new BufferedReader (new FileReader(fileElement));
				bFileRead=true;
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
			int iCount=0; int iColumn=0; String stValue="";
			while(!bLastLine) {
				if(iCount>0){
					StringTokenizer st1 =new StringTokenizer(fileline,",");
					iColumn=0;
					if(ImportType.equals("XPATH")){
						sql="insert into XpathList set ";
					}
					if(ImportType.equals("Excluder")){
						sql="insert into excluderList set ";
					}
					while(st1.hasMoreTokens()){
						iColumn++;
						stValue=st1.nextToken();
						if(stValue.equals("NULL")){
							stValue="";
						}
						stValue=stValue.replace('\'', '&');
						if(ImportType.equals("XPATH")){							
							switch(iColumn){
								case 2:	
									sql+="LevelID="+stValue;
								break;
								case 3:	
									sql+=",PathSeq="+stValue;
								break;
								case 4:	
									sql+=",XpathPath='"+stValue+"'";
								break;
								case 5:	
									sql+=",ProjectID='"+ProjectID+"'";
								break;
								case 6:	
									sql+=",UserID='"+stValue+"'";
								break;
								case 7:	
									sql+=",ProjectName='"+stValue+"'";
								break;
								case 8:	
									sql+=",ParentName='"+stValue+"'";
								break;
								case 9:	
									stValue=stValue.replace('-',',');
									sql+=",ParentSeq='"+stValue+"'";
								break;
								case 10:	
									sql+=",NodeLevelID='"+stValue+"'";
								break;
								case 11:	
									sql+=",tagName='"+stValue+"'";
								break;
								case 12:	
									sql+=",Attribute='"+stValue+"'";
								break;
								case 13:	
									sql+=",AttributeCondition='"+stValue+"'";
								break;
								case 14:	
									sql+=",AttributeValue='"+stValue+"'";
								break;
								case 15:	
									sql+=",XPATHName='"+stValue+"'";
								break;
								case 16:	
									sql+=",XPATHDesc='"+stValue+"'";
								break;
								case 17:	
									stValue=stValue.replace('-',',');
									sql+=",seq='"+stValue+"'";
								break;
								case 18:	
									stValue=stValue.replace(';',',');
									sql+=",XPATH='"+stValue+"'";
								break;
								case 19:	
									sql+=",TagOrURL='"+stValue+"'";
								break;
								case 20:	
									sql+=",DOMType='"+stValue+"'";
								break;
								case 21:	
									sql+=",popup="+stValue;
								break;
								case 22:	
									sql+=",newtab="+stValue;
								break;
								case 23:	
									sql+=",dropdown="+stValue;
								break;
								case 24:	
									sql+=",doubleclick="+stValue;
								break;
								case 25:	
									sql+=",mouseright="+stValue;
								break;
								case 26:	
									sql+=",mouseover="+stValue;
								break;
								case 27:	
									sql+=",tips="+stValue;
								break;
								case 28:	
									sql+=",frame="+stValue;
								break;
								case 29:	
									sql+=",iframe="+stValue;
								break;
								case 30:	
									sql+=",leaf="+stValue;
								break;
								case 31:	
									sql+=",fromXML="+stValue;
								break;
								case 32:	
									sql+=",fromCSV="+stValue;
								break;	
								case 33:	
									sql+=",Active="+stValue;
								break;
							}	
						}//if(ImportType.equals("XPATH")){	
						if(ImportType.equals("Excluder")){							
							switch(iColumn){
								case 2:	
									sql+="ProjectID='"+ProjectID+"'";
								break;
								case 3:	
									sql+=",UserID='"+stValue+"'";
								break;								
								case 4:	
									sql+=",ProjectName='"+stValue+"'";
								break;
								case 5:	
									sql+=",ParentName='"+stValue+"'";
								break;
								case 6:	
									sql+=",ParentSeq='"+stValue+"'";
								break;
								case 7:	
									sql+=",tagName='"+stValue+"'";
								break;
								case 8:	
									sql+=",Attribute='"+stValue+"'";
								break;
								case 9:	
									sql+=",AttributeCondition='"+stValue+"'";
								break;
								case 10:	
									sql+=",AttributeValue='"+stValue+"'";
								break;
								case 11:	
									sql+=",excluderName='"+stValue+"'";
								break;
								case 12:	
									sql+=",Active="+stValue;
								break;				
							}	
						}//if(ImportType.equals("Excluder")){	
					}
					//System.out.println("[sql:]"+sql);
					mdb.updateSQL(sql);
				}
				iCount++;				
				try{
					fileline = br.readLine();
				}catch(Exception e2){
					System.out.println("[Error:]"+e2.getMessage());
				}
				if(fileline==null){
					bLastLine=true;
				}else{
					bLastLine=false;
				}				
				
			}//end while				
			if(bFileRead){
				try{
					br.close();
				}catch(Exception e3){}
			}
			
			mdb.close();
			iCount--;
			RunDB.addLog(ProjectID,DBPosition,"000",1, "[Info:] Get "+iCount+" "+ImportType);
			System.out.println("[Info:] Import Done. Get "+iCount+" "+ImportType);
		}else{
			System.out.println("[Alert:] Stop running. Cannot detect "+ImportFile+". Please check file format. For example, c:\\\\filename.csv");
			toolbox.waitSecond(10000000);
		}
	}
	public static void getXpathFromElementsXML(String ProjectID,String DBPosition) 
	{
		String filename="./elements.xml";
		File fileElement= new File(filename); 
		if(fileElement.exists()){
			System.out.println("[Info:] Detect elements.xml. Start to get Xpath from elements.xml.");
			RunDB.addLog(ProjectID, DBPosition, "000", 1, "[Info:] Detect elements.xml. Start to get Xpath from elements.xml.");		
			String sql=""; 
			String fileline=""; String newline="";
			String XPATHName=""; String XPATH="";
			ManageDB mdb = new ManageDB();
			mdb.getConnection(DBPosition);
			sql="delete from XpathList where fromXML=1 and ProjectID='"+ProjectID+"'";
			mdb.updateSQL(sql);
			int iStart=0; int iEnd=0; int iLength=0; int iSignal=0;
			BufferedReader br =null; boolean bFileRead=false;
			try{
				br = new BufferedReader (new FileReader(fileElement));
				bFileRead=true;
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
			int iCount=0;
			while(!bLastLine) {		
				fileline=fileline.trim();					
				iSignal=fileline.indexOf("</");
				if(iSignal!=-1){
					newline=fileline.substring(0,iSignal);
					iStart=newline.indexOf("<");
					iEnd=newline.indexOf(">");
					if(iStart>-1&&iEnd>-1&&iEnd>iStart){
						XPATHName=newline.substring(iStart+1,iEnd);
						iStart=newline.indexOf(">");
						XPATH=newline.substring(iStart+1);
						iStart=XPATH.indexOf("(");
						iEnd=XPATH.lastIndexOf(")");
						iLength=XPATH.length();
						if(iStart==0){							
							if(iEnd+1==iLength){
								XPATH=XPATH.substring(1,iLength-1);
							}else{
								XPATH=XPATH.substring(1,iLength);
							}
						}						
						sql="insert into XpathList set "+"ProjectID='"+ProjectID+"'";
						sql=sql+",XPATHName='"+XPATHName+"'";
						XPATH=XPATH.replace('\'', '&');
						sql=sql+",XPATH='"+XPATH+"'";
						
						String PathSeq=""; int iMyPathSeq=1;
						String sql1="select PathSeq from XpathList where LevelID=1 and ProjectID='"+ProjectID+"' order by PathSeq desc";
						try{
							ResultSet rs1=mdb.getSQL(sql1);
							if(rs1.next()){
								PathSeq=rs1.getString("PathSeq");
								iMyPathSeq=Integer.parseInt(PathSeq)+1;
							}else{
								iMyPathSeq=1;
							}
							rs1.close();
							rs1=null;
						}catch(Exception es){}
						sql=sql+",PathSeq="+iMyPathSeq;
						sql=sql+",XpathPath='"+iMyPathSeq+"'";		
						
						sql=sql+",fromXML=1";
						
						mdb.updateSQL(sql);
					}
				}
				iCount++;				
				try{
					fileline = br.readLine();
				}catch(Exception e2){
					System.out.println("[Error:]"+e2.getMessage());
				}
				if(fileline==null){
					bLastLine=true;
				}else{
					bLastLine=false;
				}				
			}//end while				
			if(bFileRead){
				try{
					br.close();
				}catch(Exception e3){}
			}
			
			mdb.close();
			RunDB.addLog(ProjectID,DBPosition,"000",1, "[Info:] Get "+iCount+" XPath");
		}//if
	}
	public static String clearFirstXpathForStandalone(String ProjectID,String DBPosition){
		String FirstXpath="";		
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{	
			String sql="delete from XpathList where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
		return FirstXpath;
	}
	public static String getFirstXpath(String ProjectID,String ProductName,String DBPosition){
		String FirstXpath="";		
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			String sql="select * from XpathList where ProjectID='"+ProjectID+"' and ParentName='-1' and tagName='-1' and Attribute='-1' order by XpathID asc";
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				FirstXpath=rs.getString("XPATH")+"|//name_"+rs.getString("XPATHName");		
				FirstXpath=FirstXpath.replace('&','\'');	
			}			
			rs.close();
			rs=null;			
			
			//System.out.println("[Info:] Get First Xpath from DB. Xpath="+FirstXpath);
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
		return FirstXpath;
	}
	public static void setClickExtractFail(String ProjectID,String DBPosition, String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			//for the whole tree, set clickfail=1, how to do for the queued node path of clickPath, then?
			String sql="update NodeList set clickfail=1,extractfail=1 where NodePath ='"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
	}
	public static void setLeaf(String ProjectID,String DBPosition, String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			String sql="update NodeList set leaf=1 where NodePath ='"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setLeaf or DB connection");}
		m1.close();
	}
	public static void setTreeClickFail(String ProjectID,String DBPosition, String middleNodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			//for the whole tree, set clickfail=1, how to do for the queued node path of clickPath, then?
			String sql="update NodeList set clickfail=1 where NodePath like '"+middleNodePath+"%' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
	}
	public static void setExtractFail(String ProjectID,String DBPosition, String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{		
			
			//for the whole tree, set clickfail=1, how to do for the queued node path of clickPath, then?
			String sql="update NodeList set extractfail=1 where NodePath = '"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
	}
	public static void clearExtractFail(String ProjectID,String DBPosition, String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		try{					
			//for the whole tree, set clickfail=1, how to do for the queued node path of clickPath, then?
			String sql="update NodeList set extractfail=0 where NodePath = '"+NodePath+"' and ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
	}
	public static boolean isExtractFail(String ProjectID,String DBPosition, String NodePath){
		ManageDB m1 = new ManageDB();
		m1.getConnection(DBPosition);
		boolean bExtractFail=false; 		
		try{			
			
			String sql="select extractfail from NodeList where extractfail=1 and NodePath = '"+NodePath+"' and ProjectID='"+ProjectID+"'";
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bExtractFail=true;
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPathVector or DB connection");}
		m1.close();
		return bExtractFail;
	}
	public static void setProjectIDInactive(String DBPosition,String ProjectID){ 
		String sql="update ProjectParameter set ProjectStatus=0 where ProjectID='"+ProjectID+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectIDInactive or DB connection.");}
		m1.close();
	}
	public static void setProjectIDFinish(String DBPosition,String ProjectID){ 
		String sql="update ProjectParameter set ProjectStatus=4, Active=0 where ProjectID='"+ProjectID+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			m1.updateSQL(sql);
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectIDInactive or DB connection.");}
//		String NodePath="";
//		sql="select NodePath from NodeList where special=1 and ProjectID='"+ProjectID+"'";
//		try{
//			ResultSet rs=m1.getSQL(sql);
//			while(rs.next()){
//				NodePath=rs.getString("NodePath");
//			}
//			rs.close();
//		}catch(Exception ex){
//			System.out.println("[DB Error:] Check RunDB.setProjectIDFinish queue or DB connection."+ex.getMessage());
//		}
		m1.close();
	}
	public static String getProjectIDforWebService(String DBPosition, int iCrawlID){ 
		iCrawlID=0;
		String ProjectID="NoActiveProject";
		boolean bGetQueue=false;
		String sql="select ProjectID from ProjectParameter where ProjectStatus=1 order by ParaID asc"; //Active=0;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				ProjectID=rs.getString("ProjectID");
				System.out.println("[Info:] Get project in queue, ProjectID="+ProjectID);
				bGetQueue=true;
			}else{
				//System.out.println("[Info:] Do not get project in queue, turn to find the project which is booked by the current CrawlID #"+iCrawlID);
				System.out.println("[Info:] Do not get project in queue, auto crawl will exit.");
				
				bGetQueue=false;
			}	
			rs.close();
//			if(bGetQueue){
//				sql="update ProjectParameter set ProjectStatus=2,iCrawlID="+iCrawlID+" where ProjectID='"+ProjectID+"'";
//				m1.updateSQL(sql);
//			}
		}catch(Exception ex){
			System.out.println("[DB Error:] Check RunDB.getProjectID queue or DB connection."+ex.getMessage());
		}
//		try{
//			if(!bGetQueue){
//				sql="select ProjectID from ProjectParameter where ProjectStatus=2 and iCrawlID="+iCrawlID+" order by ParaID asc";
//				ResultSet  rs1=m1.getSQL(sql);
//				if(rs1.next()){
//					ProjectID=rs1.getString("ProjectID");
//					System.out.println("[Info:] Get project from my agent queue, ProjectID="+ProjectID);
//				}else{
//					System.out.println("[Info:] Do not get project in queue and the current iCrawlID #"+iCrawlID);
//				}
//				rs1.close();
//			}
//		}catch(Exception ex){
//			System.out.println("[DB Error:] Check RunDB.getProjectID agent or DB connection."+ex.getMessage());
//		}
		m1.close();
		if(!bGetQueue){
			System.out.println("[Info:] Exited.");
			System.exit(1);
		}
		return ProjectID;
	}
	public static String getProjectID(String DBPosition, int iCrawlID){ 
		String ProjectID="NoActiveProject";
		boolean bGetQueue=false;
		String sql="select ProjectID from ProjectParameter where ProjectStatus=1 order by ParaID asc";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				ProjectID=rs.getString("ProjectID");
				System.out.println("[Info:] Get project in queue, ProjectID="+ProjectID);
				bGetQueue=true;
			}else{
				System.out.println("[Info:] Do not get project in queue, turn to find the project which is booked by the current CrawlID #"+iCrawlID);
				bGetQueue=false;
			}	
			rs.close();
//			if(bGetQueue){
//				sql="update ProjectParameter set ProjectStatus=2,iCrawlID="+iCrawlID+" where ProjectID='"+ProjectID+"'";
//				m1.updateSQL(sql);
//			}
		}catch(Exception ex){
			System.out.println("[DB Error:] Check RunDB.getProjectID queue or DB connection."+ex.getMessage());
		}
		try{
			if(!bGetQueue){
				sql="select ProjectID from ProjectParameter where ProjectStatus=2 and iCrawlID="+iCrawlID+" order by ParaID asc";
				ResultSet  rs1=m1.getSQL(sql);
				if(rs1.next()){
					ProjectID=rs1.getString("ProjectID");
					System.out.println("[Info:] Get project from my agent queue, ProjectID="+ProjectID);
				}else{
					System.out.println("[Info:] Do not get project in queue and the current iCrawlID #"+iCrawlID);
				}
				rs1.close();
			}
		}catch(Exception ex){
			System.out.println("[DB Error:] Check RunDB.getProjectID agent or DB connection."+ex.getMessage());
		}
		m1.close();
		if(!bGetQueue){
			System.out.println("[Info:] Exited.");
			System.exit(1);
		}
		return ProjectID;
	}
	public static String getEmailContact(String DBPosition, String ProjectID){ 
		String EmailContact="";
		String sql="select EmailContact from ProjectParameter where ProjectID='"+ProjectID+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				EmailContact=rs.getString("EmailContact");
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getEmailContact or DB connection.");}
		m1.close();
		int iPos=EmailContact.indexOf("@ca.com");
		if(iPos<7){			
			EmailContact="miaxi01@ca.com";
		}
		return EmailContact;
	}
	public static String getProductName(String DBPosition, String ProjectID){ 
		String sql="select ProductAlias from ProjectParameter where ProjectID='"+ProjectID+"'";
		String ProductName="unknown_product";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				ProductName=rs.getString("ProductAlias");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.UnTouch or DB connection.");}
		m1.close();
		return ProductName;
	}	
	public static String getProjectName(String DBPosition, String ProjectID){ 
		String sql="select ProjectName from ProjectParameter where ProjectID='"+ProjectID+"'";
		String ProjectName="unknown_product";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				ProjectName=rs.getString("ProjectName");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.UnTouch or DB connection.");}
		m1.close();
		return ProjectName;
	}	
	public static void setProjectdStatus(String DBPosition, String ProjectID, int iCrawlID){ 
		String sql="update ProjectParameter set ProjectStatus=2,iCrawlID="+iCrawlID+" where ProjectID='"+ProjectID+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectStatus or DB connection.");}
		m1.close();
	}	
	public static int getRunningProjectNumber(String DBPosition, String ProjectID){ 
		int iRunningProjectNumber=0;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select count(ParaID) from ProjectParameter where Active=1";
		//System.out.println("Test: "+sql);
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				iRunningProjectNumber=rs.getInt(1);
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getRunningProjectNumber or DB connection.");}		
		m1.close();
		return iRunningProjectNumber;
	}
	public static void removeProject(String DBPosition, String ProjectID){ 
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="delete from ProjectParameter where ProjectStatus=1 and Active=0 and ProjectID='"+ProjectID+"'";	
		m1.updateSQL(sql);
		m1.close();		
	}
	public static void setProjectStartTime(String DBPosition, String ProjectID){ 
		boolean bExistingSameRunningProject=false;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select ID from ProjectStartTime where ProjectID='"+ProjectID+"'";
		//System.out.println("Test: "+sql);
		/*
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bExistingSameRunningProject=true;
			}else{
				bExistingSameRunningProject=false;
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectStartTime or DB connection.");}
		*/
		//if(bExistingSameRunningProject){
			sql="delete from ProjectStartTime where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);	
			sql="delete from ProjectEndTime where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);	
		//}
		sql="insert into ProjectStartTime set ProjectID='"+ProjectID+"'";
		m1.updateSQL(sql);		
		m1.close();		
	}
	public static void setProjectEndTime(String DBPosition, String ProjectID){ 
		boolean bExistingSameRunningProject=false;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select ID from ProjectEndTime where ProjectID='"+ProjectID+"'";
		//System.out.println("Test: "+sql);
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bExistingSameRunningProject=true;
			}else{
				bExistingSameRunningProject=false;
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectEndTime or DB connection.");}
		if(bExistingSameRunningProject){
			sql="delete from ProjectEndTime where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);	
		}
		sql="insert into ProjectEndTime set ProjectID='"+ProjectID+"'";
		m1.updateSQL(sql);		
		m1.close();		
	}
	public static String getProjectStartTime(String DBPosition, String ProjectID){ 
		String RunStartTime="";
		boolean bExistingSameRunningProject=false;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select RunStartTime from ProjectStartTime where ProjectID='"+ProjectID+"'";
		//System.out.println("Test: "+sql);
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				RunStartTime=rs.getString("RunStartTime");
				bExistingSameRunningProject=true;
			}else{
				bExistingSameRunningProject=false;
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getProjectEndTime or DB connection.");}		
		m1.close();		
		return RunStartTime;
	}
	public static String getProjectEndTime(String DBPosition, String ProjectID){ 
		String RunEndTime="";
		boolean bExistingSameRunningProject=false;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select RunEndTime from ProjectEndTime where ProjectID='"+ProjectID+"'";
		//System.out.println("Test: "+sql);
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				RunEndTime=rs.getString("RunEndTime");
				bExistingSameRunningProject=true;
			}else{
				bExistingSameRunningProject=false;
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getProjectEndTime or DB connection.");}		
		m1.close();		
		return RunEndTime;
	}
	public static boolean setProjectRunningStatusForWebService(String DBPosition, String ProjectID){ 
		boolean bExistingSameRunningProject=false;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select ParaID from ProjectParameter where ProjectID='"+ProjectID+"' and ProjectStatus=1 and Active=1";
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bExistingSameRunningProject=true;
			}else{
				bExistingSameRunningProject=false;
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectRunningStatusForWebService or DB connection.");}
		if(bExistingSameRunningProject){
			//change to 2 after 1 is set by Michelle			
			sql="update ProjectParameter set ProjectStatus=2, Active=1 where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
		}else{
			sql="update ProjectParameter set ProjectStatus=2, Active=1 where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
		}
		m1.close();
		return bExistingSameRunningProject;
	}
	public static boolean setProjectRunningStatus(String DBPosition, String ProjectID, int iCrawlID){ 
		boolean bExistingSameRunningProject=false;
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select ParaID from ProjectParameter where ProjectID='"+ProjectID+"' and ProjectStatus=1 and Active=1";
		try{
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				bExistingSameRunningProject=true;
			}else{
				bExistingSameRunningProject=false;
			}
			rs.close();	
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.setProjectRunningStatus or DB connection.");}
		if(bExistingSameRunningProject){
				
		}else{
			sql="update ProjectParameter set ProjectStatus=2, Active=1,iCrawlID="+iCrawlID+" where ProjectID='"+ProjectID+"'";
			m1.updateSQL(sql);
		}
		m1.close();
		return bExistingSameRunningProject;
	}		
	public static void setProjectStatusToPause(String DBPosition, String ProjectID, String NodePath){ 
		String sql="update ProjectParameter set ProjectStatus=3, Active=0 where ProjectID='"+ProjectID+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		m1.updateSQL(sql);			
		m1.close();
		System.out.println("[Info:] Set status to Suspend. ");
	}	
	public static void setProjectStatusToPauseAndExit(String DBPosition, String ProjectID, String NodePath){
		if(NodePath.equals("000")){
			String sql="update ProjectParameter set ProjectStatus=3, Active=0 where ProjectID='"+ProjectID+"'";
			ManageDB m1=new ManageDB();
			m1.getConnection(DBPosition);
			m1.updateSQL(sql);
			m1.close();
			System.out.println("[Exception:] Set status to Suspend for main page exception. Set project end time, Pause and exit.");
			RunDB.setProjectEndTime(DBPosition, ProjectID);
			System.exit(0);
		}else{
			System.out.println("[Info:] Can not set status to Suspend for non-main page exception. ");
		}
	}
	public static void setProjectFinishStatus(String DBPosition, String ProjectID, int iCrawlID){ 
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="update ProjectParameter set ProjectStatus=4, Active=0,iCrawlID="+iCrawlID+" where ProjectID='"+ProjectID+"'";
		m1.updateSQL(sql);
		m1.close();
	}	
	public static String getProductURL(String DBPosition, String ProjectID){ 
		String sql="select URL from ProjectParameter where ProjectID='"+ProjectID+"'";
		String ProductURL="";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				ProductURL=rs.getString("URL");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getProductURL or DB connection.");}
		m1.close();
		return ProductURL;
	}
	public static String getUserName(String DBPosition, String ProjectID){ 
		String sql="select UserName from ProjectParameter where ProjectID='"+ProjectID+"'";
		String UserName="";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				UserName=rs.getString("UserName");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getUserName or DB connection.");}
		m1.close();
		return UserName;
	}
	public static String getPassWord(String DBPosition, String ProjectID){ 
		String sql="select Password from ProjectParameter where ProjectID='"+ProjectID+"'";
		String PassWord="";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				PassWord=rs.getString("Password");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPassWord or DB connection.");}
		m1.close();
		return PassWord;
	}
	public static String getTenantID(String DBPosition, String ProjectID){ 
		String sql="select TenantID from ProjectParameter where ProjectID='"+ProjectID+"'";
		String TenantID="";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				TenantID=rs.getString("TenantID");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getTenantID or DB connection.");}
		m1.close();
		return TenantID;
	}
	public static String getLanguage(String DBPosition, String ProjectID){ 
		String sql="select Language from ProjectParameter where ProjectID='"+ProjectID+"'";
		String Language="";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				Language=rs.getString("Language");				
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getLanguage or DB connection.");}
		m1.close();
		return Language;
	}
	public static String getPluginAction(String DBPosition, String ProjectID){ 
		String sql="select HardcodeStatus,TruncationStatus,iFormatStatus from ProjectParameter where ProjectID='"+ProjectID+"'";
		String PluginAction="";
		String HardcodeStatus="";
		String TruncationStatus="";
		String iFormatStatus="";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				HardcodeStatus=rs.getString("HardcodeStatus");	
				TruncationStatus=rs.getString("TruncationStatus");	
				iFormatStatus=rs.getString("iFormatStatus");	
			}
			rs.close();
			
			if(HardcodeStatus==null){
				HardcodeStatus="";
			}
			if(HardcodeStatus.equals("1")){
				PluginAction+=",Hardcode";				
			}
			if(TruncationStatus==null){
				TruncationStatus="";
			}
			if(TruncationStatus.equals("1")){
				PluginAction+=",Truncation";
			}
			if(iFormatStatus==null){
				iFormatStatus="";
			}
			if(iFormatStatus.equals("1")){
				PluginAction+=",iFormat";
			}
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getPluginAction or DB connection.");}
		String PluginActionFromParameter=loginStart.readXML("PluginAction"); if(PluginActionFromParameter==null){PluginActionFromParameter="";}
		PluginAction=PluginActionFromParameter;//+","+PluginAction;
		sql="update ProjectParameter set PluginAction='"+PluginAction+"' where ProjectID='"+ProjectID+"'";
		m1.updateSQL(sql);
		m1.close();
		return PluginAction;
	}
	public static String resetPluginActionNotUIContext(String DBPosition, String ProjectID){ 
		String sql="select PluginAction from ProjectParameter where ProjectID='"+ProjectID+"'";
		//update ProjectParameter set PluginAction='UIContext,Hardcode' where ProjectID='707';
		String PluginAction="";		
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				PluginAction=rs.getString("PluginAction");					
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.resetPluginActionNotUIContext or DB connection.");}
		int iLen=PluginAction.indexOf(",");
		if(iLen>-1){
			PluginAction=PluginAction.substring(iLen+1);
		}
		sql="update ProjectParameter set PluginAction='"+PluginAction+"' where ProjectID='"+ProjectID+"'";
		m1.updateSQL(sql);
		m1.close();			
		return PluginAction;
	}
	public static String regetPluginAction(String DBPosition, String ProjectID){ 
		String sql="select PluginAction from ProjectParameter where ProjectID='"+ProjectID+"'";
		String PluginAction="";		
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				PluginAction=rs.getString("PluginAction");					
			}
			rs.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.regetPluginAction or DB connection.");}		
		m1.close();		
		return PluginAction;
	}
	public static boolean UnTouch(String ProjectID,String DBPosition,int iLevelID,int iStartID, int iEndID, String FeatureTree,String PluginAction,String RunMode){ // peer to getNodePath
		int iUnTouch=0; 
		boolean bHasUnTouch=false;
		boolean bPlay=false; if(RunMode.equals("Play")||RunMode.equals("PlayResume")){bPlay=true;}
		String sql_aux="";
		if(!FeatureTree.equals("")){
			sql_aux=" and NodePath like '"+FeatureTree+"%'";
		}
		String sql="";
		if(bPlay){
			sql="select count(NodePath) from NodeList where playonce=0 ";
			sql+=" and validNode=1 ";
			sql+=" and capture=0 ";
			sql+=" and clickable=1 and special=1 and clickfail=0";			
			if(PluginAction.contains("UIContext")){
				//sql+=" and newPloc=1 ";
			}	
			
//			sql+=" and LevelID="+iLevelID; //avoid mapping stop
			sql+=" and LevelID<="+iLevelID;
			
			sql+=" and ListID>="+iStartID;
			if(iEndID>iStartID){
				sql+=" and ListID<="+iEndID;
			}
			sql+=sql_aux;
			sql+=" and ProjectID='"+ProjectID+"'";
		}else{
			sql="select count(NodePath) from NodeList where touchonce=0 ";
			sql+=" and clickable=1 and special=1 and clickfail=0";
			sql+=" and LevelID="+iLevelID;
			
			sql+=" and ListID>="+iStartID;
			if(iEndID>iStartID){
				sql+=" and ListID<="+iEndID;
			}
			sql+=sql_aux;
			sql+=" and ProjectID='"+ProjectID+"'";
		}
		//select special,touchonce,visible,clickable,validnode,capture,screenname,playonce,clickfail,clicktimes from nodelist where nodepath='013-202-214-287';
		//System.out.println(sql);
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				iUnTouch=rs.getInt(1);
				if(iUnTouch>0){
					System.out.println("[Info:] "+iUnTouch+" nodes are in queue of depth(LevelID) "+iLevelID);
				}else{
					System.out.println("[Info:] No node is in queue of depth(LevelID) "+iLevelID);
				}
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.UnTouch or DB connection.");}
		m1.close();
		//toolbox.waitSecond(10000000);
		
		if(iUnTouch>0){
			bHasUnTouch=true;
		}else{
			bHasUnTouch=false;
		}
		return bHasUnTouch;
	}
	public static boolean UngetSubMenu(String ProjectID,String DBPosition){ 
		int iUngetSubMenu=0; 
		boolean bHasUngetSubMenu=false;
		
		String sql="select count(*) from NodeList where TagOrURL='URL' and mainFeature=1 and getSubMenu=0 and ProjectID='"+ProjectID+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				iUngetSubMenu=rs.getInt(1);
//				if(iUngetSubMenu>0){
//					System.out.println("\n[Info:] "+iUngetSubMenu+" nodes are in queue. ");
//				}else{
//					System.out.println("\n[Info:] No node is in queue of the current cycle");
//				}
			}
			rs.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.UnTouch or DB connection.");}
		m1.close();
		if(iUngetSubMenu>0){
			bHasUngetSubMenu=true;
		}else{
			bHasUngetSubMenu=false;
		}
		return bHasUngetSubMenu;
	}
	public static int getScreenNum(String ProjectID,String DBPosition){ 
		int iScreenNum=0; 
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select count(*) from NodeList where screenName<>'' and special=1 and ProjectID='"+ProjectID+"'";
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				iScreenNum=rs.getInt(1);
			}
			rs.close();
			//rs=null;
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getScreenNum or DB connection.");}
		m1.close();
		return iScreenNum;
	}
	public static int getPlocNum(String ProjectID,String DBPosition){ 
		int iPlocNum=0; 
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select count(*) from NodeList where playonce=1 and ProjectID='"+ProjectID+"'";
		try{
			
			ResultSet rs=m1.getSQL(sql);
			if(rs.next()){
				iPlocNum=rs.getInt(1);
			}
			rs.close();
			//rs=null;
			
		}catch(Exception ex){System.out.println("[DB Error:] Check RunDB.getScreenNum or DB connection.");}
		m1.close();
		return iPlocNum;
	}
	public static ArrayList<String> getNodePath(int iAgentID,String ProjectID,String DBPosition,int max_instance,int iLevelID,int iStartID, int iEndID,String FeatureTree,String PluginAction,String RunMode){ // peer to UnTouch
		ArrayList<String> NodePath= new ArrayList<String> ();
		boolean bDebug=false; if(RunMode.equals("Debug")||RunMode.equals("DebugResume")){bDebug=true;}
		boolean bRecord=false; if(RunMode.equals("Record")||RunMode.equals("RecordResume")){bRecord=true;}
		boolean bPlay=false; if(RunMode.equals("Play")||RunMode.equals("PlayResume")){bPlay=true;}
		String oneNode="";
		String sql_pre="select NodePath ";
		String sql_count="select count(NodePath) ";
		String sql_aux="";
		if(!FeatureTree.equals("")){
			sql_aux=" and NodePath like '"+FeatureTree+"%'";
		}
		if(iAgentID>0){
			sql_aux=" and inQueue=0";
		}
		String sql="";
		if(bPlay){ //test it
			//For UnTouch sql="select count(NodePath) from NodeList where clickable=1 and playonce=0 and special=1 and clickfail=0";
			sql=" from NodeList where playonce=0 ";
			sql+=" and validNode=1 ";
			sql+=" and capture=0 ";
			sql+=" and clickable=1 and special=1 and clickfail=0";			
			if(PluginAction.contains("UIContext")){
				 //sql+=" and newPloc=1 ";
			}
//			sql=" from NodeList where clickable=1 and playonce=0 and special=1 and clickfail=0";
//			sql+=" and LevelID="+iLevelID;  //avoid mapping stop
			sql+=" and LevelID<="+iLevelID;
			
			sql+=" and ListID>="+iStartID;
			if(iEndID>iStartID){
				sql+=" and ListID<="+iEndID;
			}
			
			sql+=sql_aux;			
			sql+=" and ProjectID='"+ProjectID+"'";
			sql_count=sql_count+sql;
			sql=sql_pre+sql;
			sql+=" order by ListID ASC";
		}
		if(bRecord){ //For UnTouoch, it is the same with bDebug 
			sql=" from NodeList where touchonce=0 ";
			sql+=" and clickable=1 and special=1 and clickfail=0";
			sql+=" and LevelID="+iLevelID;
			
			sql+=" and ListID>="+iStartID;
			if(iEndID>iStartID){
				sql+=" and ListID<="+iEndID;
			}
			
			sql+=sql_aux;
			sql+=" and ProjectID='"+ProjectID+"'";
			sql_count=sql_count+sql;
			sql=sql_pre+sql;
			sql+=" order by ListID ASC";
		}
		if(bDebug){
			sql=" from NodeList where clickable=1 and touchonce=0 and special=1 and clickfail=0";
			sql+=" and LevelID="+iLevelID;
			
			sql+=" and ListID>="+iStartID;
			if(iEndID>iStartID){
				sql+=" and ListID<="+iEndID;
			}
			
			sql+=sql_aux;
			sql+=" and ProjectID='"+ProjectID+"'";
			sql_count=sql_count+sql;
			sql=sql_pre+sql;
			sql+=" order by ListID ASC";
		}
		int iSession= 0 ; 
		if(iAgentID==0){
			ManageDB m1=new ManageDB();
			m1.getConnection(DBPosition);
			try{
				
				ResultSet rs_get=m1.getSQL(sql);
				while(rs_get.next()){					
					iSession++;		
					if(iSession<=max_instance){
						oneNode=rs_get.getString("NodePath");
						NodePath.add(oneNode);
					}
					else{
						break;
					}
				}
				rs_get.close();
				//rs_get=null;
				
			}catch(Exception ex){}
			m1.close();
		}else{
			ManageDB m2=new ManageDB();
			m2.getConnection(DBPosition);
			try{
				int iCurPage=iAgentID;
				int iOnePage=5;
				int iTotalRecords=0;
				int iTotalPages=0;
				
				ResultSet rs0=m2.getSQL(sql_count);
				iTotalRecords = rs0.getInt(1);
				rs0.close();
				iTotalPages=iTotalRecords/iOnePage;
				if(iTotalPages*iOnePage<iTotalRecords){
					iTotalPages++;
				}
				if(iCurPage<1){
					iCurPage=1;
				}
				if(iCurPage>iTotalPages){
//					iCurPage=iTotalPages;
				}else{
					int iFirstRecord=0;
					int iLastRecord=0;
					iFirstRecord=(iCurPage-1)*iOnePage+1;
					if(iCurPage*iOnePage<iTotalRecords){
						iLastRecord=iCurPage*iOnePage; 
					}
					else{
						iLastRecord=iTotalRecords;
					}
					int nCount=0;
					ResultSet rs=m2.getSQL(sql);
					while(rs.next()){
						nCount++;
					  	if(nCount>=iFirstRecord&&nCount<=iLastRecord)
						{
					  		NodePath.add(rs.getString("NodePath"));	
						}
					}
					rs.close();
					
				}
			}catch(Exception ex2){}
			m2.close();
		}
		ArrayList<String> nodePathArray = NodePath;
		int iTotal=nodePathArray.size();
		String oneNodePath=""; 
		ManageDB m3 = new ManageDB();
		m3.getConnection(DBPosition);
		for(int k=0;k<iTotal;k++){
			oneNodePath=nodePathArray.get(k);
			sql="update NodeList set inQueue=1 where NodePath='"+oneNodePath+"' and ProjectID='"+ProjectID+"'";
			m3.updateSQL(sql);
		}
		m3.close();
		return NodePath;
	}
	public static String getTestPath(String ProjectID,String DBPosition){ 
		String NodePath= "";
		String sql="select NodePath from NodeTest where Active=1 and ProjectID='"+ProjectID+"' order by TestID desc";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			ResultSet rs_get=m1.getSQL(sql);
			if(rs_get.next()){
				NodePath=rs_get.getString("NodePath");	
			}
			rs_get.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Database may stop, please check it.");}
		m1.close();
		return NodePath;
	}
	public static boolean checkNodePath(String ProjectID,String DBPosition,String NodePath){ 
		boolean bValidNode = false;
		String sql="select NodePath from NodeList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{			
			ResultSet rs_get=m1.getSQL(sql);
			if(rs_get.next()){
				bValidNode = true;
			}else{
				bValidNode = false;
			}
			rs_get.close();			
		}catch(Exception ex){System.out.println("[DB Error:] Database may stop, please check it.");}
		m1.close();
		return bValidNode;
	}
	public static void resetTestPath(String ProjectID,String DBPosition,String NodePath){ 
		String sql="update NodeTest set Active=0 where  ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"'";
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		try{
			
			m1.updateSQL(sql);
			
		}catch(Exception ex){System.out.println("[DB Error:] Database may stop, please check it.");}
		m1.close();
	}
	public static ArrayList<String> getLayer1MenuURL(String ProjectID,String DBPosition){ 
		ArrayList<String> NodePath= new ArrayList<String> ();
		ManageDB m1=new ManageDB();
		m1.getConnection(DBPosition);
		String sql="select NodePath from NodeList where TagOrURL='URL' and mainFeature=1 and getSubMenu=0  and ProjectID='"+ProjectID+"'";
		try{
			
			ResultSet rs_get=m1.getSQL(sql);
			while(rs_get.next()){
				NodePath.add(rs_get.getString("NodePath"));	
			}
			rs_get.close();
			
		}catch(Exception ex){System.out.println("[DB Error:] Database may stop, please check it.");}
		m1.close();
		return NodePath;
	}

}
