package ValidatorTruncation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class truncationCheck implements I18nValidator {
	
	static int k=0;
	@Override
	public void detectHardcode(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detectTruncation(String ProjectID,int DropNumber,String DBPosition,String NodePath,WebDriver driver,String xpath) {
			
		if (xpath.length()==0){
		
			//xpath = "//a|//input|//label|//button|//span";
			xpath="//label[contains(text(),'')]|//a[contains(text(),'')]|//input[contains(text(),'')]|//button[contains(text(),'')]|//span[contains(text(),'')]";
			
		try {
			//startSingleCheck (driver,xpath); 
			startSingleCheck (ProjectID,DropNumber,DBPosition,NodePath,driver,xpath); 
			//startMulitiCheck(driver);
			
		}catch (Exception error) {
			error.printStackTrace();
		}
		}else {
			try {
				//startMulitiCheck(driver);
				startMulitiCheck(ProjectID,DropNumber,DBPosition,NodePath,driver);
			}catch (Exception error) {
				error.printStackTrace();
			}
			
		}
	}

	@Override
	public void detectiFormat(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detectAll() {
		// TODO Auto-generated method stub
		
	}
private void startSingleCheck (String ProjectID,int DropNumber,String DBPosition,String NodePath,WebDriver driver,String xpath)  throws Exception {
		
		CurrentTDetect ct = new CurrentTDetect(ProjectID,DropNumber,DBPosition,NodePath,driver,xpath);
		
		Thread td = new Thread(ct);
		td.setName(xpath);
		td.start();
		td.join();
		
		System.out.println("Done Truncation Check");
		
		
	}
public static boolean isNumber (String str) {
	
	boolean isNumber = false;
	isNumber = str.matches("[0-9]+");
	
	if (isNumber){
		
		isNumber=true;
	}
	
	return isNumber;
}
public static void startMulitiCheck (String ProjectID,int DropNumber,String DBPosition,String NodePath,WebDriver driver)  throws Exception {
	
	  // multi threads
	
System.out.println("Start multi threads checking!");

List<Thread> threadList = new ArrayList<Thread>();
LinkedHashSet<String> set = new LinkedHashSet<String>();
Pattern p = Pattern.compile(".*");
Matcher m = p.matcher(driver.findElement(By.cssSelector("body")).getText());


while(m.find())
{

	if(!m.group().toString().equals(""))
	{
		String text = m.group().toString().trim();
		System.out.println("Find:" + m.group().toString().trim());
		if (!isNumber(text)){
			set.add(text);	
		}
		
		
	}
}

System.out.println("set.size"+set.size());
Iterator itr = set.iterator();
int tempTime = set.size();

while (itr.hasNext()){
	
	String text = itr.next().toString();
	CurrentTucationDetect ct = new CurrentTucationDetect (driver,text,set,ProjectID);
	Thread td = new Thread(ct);
	td.setName(text);
	td.start();
	threadList.add(td);
	
}
for (Thread td_:threadList){
	try {
		td_.join();	
	}catch (Exception error) {
		error.printStackTrace();
	}
	
}
System.out.println("Closing browser!");
ValidatorUtil.waitMin(3);
//driver.close();




}
public int hightlightElements(WebDriver driver,String xpath) {
	
	int number =0;
	
	if (xpath!=null&&xpath.length()!=0){
	List<WebElement> eles = ValidatorUtil.elementList(driver, xpath);
    number= eles.size();
	
	for(WebElement e: eles) {
		ValidatorUtil.highlight_Element(driver, e);
		}
	}else {
		System.out.println("need to specify the area for hightlight");
	}
		return number;
	}

}
