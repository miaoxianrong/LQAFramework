package mapping;

import org.openqa.selenium.WebDriver;




public class ByText extends _Mode {
	
	String projectId="";
	public ByText(){
		
	}
	public ByText(int symbol,String container,  WebDriver driver, String screenshotName, String projectid){
		this.symbol_index = symbol;
		this.driver = driver;
		this.screenshotName = screenshotName;
		this.container = container;
		this.projectId = projectid;
		this.XPath = container + "//*[contains(text(),'" + Symbol.getLeftByIndex(symbol_index) + "')]";
	}
	
	@Override
	public void find(){
		
		this.elementsList = searchByXPath();
		System.out.println("* <<" + elementsList.size() + ">> elements has PLOC ID");
		LogContext.add("* <<" + elementsList.size() + ">> elements has PLOC ID");
		LogText(this.projectId);
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
