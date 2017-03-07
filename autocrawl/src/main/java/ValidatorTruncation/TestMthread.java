package ValidatorTruncation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class TestMthread {
	
	
	
	public TestMthread () throws Exception{}
	 

	public static void main(String[] args) throws Exception {
		
		//WebDriver driver = new RemoteWebDriver (new URL("http://155.35.75.217:4444/wd/hub"),DesiredCapabilities.firefox());
		WebDriver driver = new FirefoxDriver();
		
		System.out.println("testMthreads");
		String timeBegin = CountTime.getStringDateShort();
		System.out.println("Starting execute at:"+timeBegin);
		Date date = new Date();
		long longtime = date.getTime();
		
		//testMethod ();
				
		String testLink = "file:///C:/test/html/testbutton.html";//"file:///C:/test/html/testbutton.html";  //file:///C:/test/html/testbutton.html  file://C:/test/html/testcase.html
		
		String Nimsoft="http://ghasa02-i114792:8585/"; 
		String CSM ="http://poc.clouditsm.ca.com:8585";
		String Lisa = "http://win7forlisa.ca.com:1507/devtest";
		
		String ProjectID = "00000707";
		int DropNumber = 3;
		String DBPosition = "155.35.98.106";
		String NodePath = "1-1";
		
		driver.get(Lisa);
		ValidatorUtil.waitMin(3);
		
		String temp= "//body/*";//"//body";//"//*[contains(text(),'FOR')]";//args[0];
		WebElement element = driver.findElement(By.xpath(temp));
		
		I18nValidator tc = new truncationCheck();
		//tc.detectTruncation(driver, "");
		temp="//body/*";
		if (temp!=null&&temp.length()!=0){
			
			try {
				
				/*//TextFormat.SetLocalId(driver, element, 1);
				String source = driver.getPageSource();
				System.out.println(source);
				List<WebElement> eles = ValidatorUtil.elementList(driver, "//body/*");
				for(WebElement e: eles) {
					//ValidatorUtil.highlight_Element(driver, e);
				}*/
				
				startSingleCheck(ProjectID,DropNumber,DBPosition,NodePath,driver,temp);
				System.out.println("Start single checking!");
			}catch (Exception error){
				
				error.printStackTrace();
			}
			
		}else {
			try {
				System.out.println("Start MulitiCheck checking!");
				startMulitiCheck(driver,ProjectID);
				
			}catch (Exception error_Muliticheck) {
				 error_Muliticheck.printStackTrace();
			}
			
		}
	
		//driver.close();
		System.out.println("test done!");
		System.out.println("Total executing time:"+CountTime.TimeCount(longtime));
		System.out.println("Ending execute at:"+CountTime.getStringDateShort() );
		
}
public static boolean isNumber (String str) {
		
		boolean isNumber = false;
		isNumber = str.matches("[0-9]+");
		
		if (isNumber){
			
			isNumber=true;
		}
		
		return isNumber;
	}
	public static void testMethod (){
		
		/*
		
		ArrayList list = new ArrayList();
		list.add("String A");
		list.add("String B");
		list.add("String c");
		list.add("String D");
		list.add("String E");
		list.add("String F");
		list.add("String G");
		list.add("String H");
		list.add("String I");
		list.add("String J");
		
		list.add("String k");
		list.add("String L");
		list.add("String M");
		
		System.out.println(list.size());
		System.out.println(list.get(12).toString());
		
		int k=0;
		int j=0;
		int i=0;
		
		while (k<list.size()){
			
			k++;
			
		for (i=0+j;i<3*k;i++){
			
		if (i<list.size()){
			j++;
		
			System.out.println("listText:"+list.get(i).toString()+":"+i);
		
		
			
			}  
		
			
		}
		
		System.out.println("this is "+k+ " executing it ");
		
		if(i==list.size()-1){
			System.out.println("Total run times:"+k);	
			break;
		}
		
			
		
		
		
	}
	
		 */
	}
	
	public static void startSingleCheck (String ProjectID,int DropNumber,String DBPosition,String NodePath,WebDriver driver,String xpath)  throws Exception {
		
		CurrentTDetect ct = new CurrentTDetect(ProjectID,DropNumber,DBPosition,NodePath,driver,xpath);
		
		Thread td = new Thread(ct);
		td.setName(xpath);
		td.start();
		td.join();
		
		System.out.println("done startSingleCheck");
		
		
	}
	public static void startMulitiCheck (WebDriver driver,String projectID)  throws Exception {
		
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
		CurrentTucationDetect ct = new CurrentTucationDetect (driver,text,set,projectID);
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
	driver.close();

	


	}
}
