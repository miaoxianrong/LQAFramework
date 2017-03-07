package mapping;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class concurrentSetID implements Runnable{
	JavascriptExecutor js;
	int i;
	WebElement e;
	public void run(){
		js.executeScript(
				"arguments[0].setAttribute('loc_id', arguments[1]);",
				e, i);
	}
}
