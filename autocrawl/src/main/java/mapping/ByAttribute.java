package mapping;




import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;


public class ByAttribute extends _Mode{
	
	String projectid="";
	public ByAttribute(){
		
	}
	public ByAttribute(String attribute, int symbol, String container, WebDriver driver, String screenshotName, String projectid){
		this.attribute = attribute;
		this.screenshotName = screenshotName;
		this.container = container;
		this.driver = driver;
		this.symbol_index = symbol;
		this.projectid = projectid;
		this.XPath = container + "//*[contains(@" + attribute + ",'" + Symbol.getLeftByIndex(symbol_index)+ "')]";

	}
	
	@Override
	public void find(){
		try{
			this.elementsList = searchByXPath();
			System.out.println("* <<" + elementsList.size() + ">> elements has @" + attribute + " attribute with PLOC ID");
			LogContext.add("* <<" + elementsList.size() + ">> elements has @" + attribute + " attribute with PLOC ID");
			LogAttribute(this.attribute,this.projectid);
		}
		catch(InvalidSelectorException e){
			System.out.println("The expression of attribute is not a legal expression: " + this.attribute);
		}
		
	}
	@Override
	public void LogText() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void LogAttribute(String attribute) {
		// TODO Auto-generated method stub
		
	}
}
