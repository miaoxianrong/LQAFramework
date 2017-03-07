package Interface;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface IMode {
	
	List<WebElement> searchByXPath();
	void LogText();
	void LogAttribute(String attribute);
	void find();
}
