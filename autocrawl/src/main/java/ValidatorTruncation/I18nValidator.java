package ValidatorTruncation;


import org.openqa.selenium.WebDriver;

public interface I18nValidator {
	
	public  void detectHardcode(WebDriver driver) ;
//	public  void detectTruncation(WebDriver driver,String xpath) ;
	public  void detectiFormat(WebDriver driver);
	public  void detectAll ();
	public  void detectTruncation(String ProjectID, int DropNumber,String DBPosition, String NodePath, WebDriver driver, String xpath);
}
