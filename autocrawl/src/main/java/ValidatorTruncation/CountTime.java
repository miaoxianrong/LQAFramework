package ValidatorTruncation;

import java.awt.Font;
import java.awt.FontMetrics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CountTime {
	
	ValidatorUtil util = new ValidatorUtil();
	
	public static String getStringDateShort() {
		
		  Date currentTime = new Date();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
		  String dateString = formatter.format(currentTime);
		  return dateString;
		  
		 } 
	public static String  TimeCount(long begin) {
		

		 String runtime =null;
		 
		try{ 
			
		 long k=0,end,times;
			
		 SimpleDateFormat sdf=new SimpleDateFormat("mm:ss:SS"); 
	    
	     Date mydate2=new Date();
	     end=mydate2.getTime();
	     times=end-begin;
	     SimpleDateFormat sdftimes=new SimpleDateFormat("mm:ss:SS"); 
	          
	     runtime = sdftimes.format(times);
		      
	          }catch (Exception e){
	        	  System.out.println ();
	          }
	          return runtime+""+"(minutes)";
	 
	}
}
