package mapping;


import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.ArrayList;

import Interface.ILog;

public class Log implements ILog{
	String logPath = "./logs/";
	String log_file;
	ArrayList<String> LogContext;	
	
	public Log(){
		
	}
	public Log(String path){
		this.logPath = path;
	}
	public void WriteToMainLog(ArrayList<String> LogContext){
		log_file = logPath + "main.log";
		this.LogContext = LogContext;
		this.WriteToLog();

	}
	public void WriteToLog()
	 {
		try{

			OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream(log_file, true), "utf-8");
			int i=0;
			//bw.write("kaishi \r\n");
			for(String s: LogContext){
				if(i % 5 ==0){
					bw.write("\r\n"); 
				}
				
				bw.write(s + "\r\n");				
									
				i++;
			}
			//bw.write("\r\n");
		    bw.flush();
			bw.close();
		}
			catch(IOException e)
			{
				
			}
		
	 }
	public void WriteToElementsLog(ArrayList<String> LogContext){
		log_file = logPath + "elements.log";
		this.LogContext = LogContext;
		this.WriteToLog();
	}
	public void WriteToTaglessLog(ArrayList<String> LogContext){
		log_file = logPath + "tagless.log";
		this.LogContext = LogContext;
		this.WriteToLog();
	}
	public void WriteToText2Screen(ArrayList<String> LogContext, String projectid){
		log_file = "./output/" + projectid + "/screen/text-to-screen.txt";
		this.LogContext = LogContext;
		this.WriteToLog();
	}
	public void createLogFilePath(String projectid){
		//toolbox.mkDir("./screen/" + projectid);		
		toolbox.mkDir(logPath);
	}
	public void setLogFilePath(String path){
		this.logPath = path;
	}
	@Override
	public void createLogFilePath() {
		// TODO Auto-generated method stub
		
	}
}
