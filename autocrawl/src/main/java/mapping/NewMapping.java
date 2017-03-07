package mapping;

import org.openqa.selenium.WebDriver;

import Interface.IMapping;
import Interface.IMode;

public class NewMapping implements IMapping{
	WebDriver driver;
	String screenName;
	String container;
	int symbol_index;
	IMode mode;
	String projectid;
	String dropid;
	
	boolean checktipornot = false;
	
	public NewMapping(){
		
	}
	public NewMapping(WebDriver driver, String screenName, String container, int symbol,String projectid, String dropid,boolean tipornot){
		this.driver = driver;
		this.screenName = screenName;
		this.container = container;
		this.symbol_index = symbol;
		
		this.projectid = projectid;
		this.dropid = dropid;
		
		checktipornot = tipornot;
	}
	
	public void start(){
		new Log().createLogFilePath(this.projectid);
		
		//handle tag with text
		System.out.println("begin text");
		mode = new ByText(symbol_index,container,  driver, screenName,projectid);
		mode.find();
		System.out.println("begin attribute");
		//handle tag with attribute
		mode = new ByAttribute("value", symbol_index,container,  driver, screenName,projectid);
		mode.find();
		//System.out.println("begin tagless");
		//handle tagless strings
		//mode = new Tagless(symbol_index,container,  driver, screenName);
	    //mode.find();
		//handle tips
	    //System.out.println(checktipornot);
	    if(checktipornot){
	    	System.out.println("begin tip");	    	
			//mode = new Bytooltip(symbol_index,container, driver, screenName,projectid,dropid);
			//mode.find();
	    }
		
	}	
}
