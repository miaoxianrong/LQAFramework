package jerseytest;


import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.gson.Gson;


	@Path("/rest")
	public class HelloWorldService {
		
//		public Util client = new Util();
//		public static MemCachedClient memClient = Util.memClient();
		public static Gson gson = new Gson();
		Resp resp = new Resp();
		
		double count=0;
		int interval = 0;
		volatile static int index = 0;
		static int PERMIT_COUNT = 5;
    	static Semaphore PERMIT = new Semaphore(PERMIT_COUNT);
		static final ExecutorService POOL=Executors.newCachedThreadPool();
//		public ResponseMsg response = new ResponseMsg();
//		IProduct product = new Products.lv1Product();
//		User user = new User();
		
		//To test if the services are online.
	    @Path("/helloworld")
	    @GET
	    @Produces("application/json;charset=utf-8")
	    public String welcome() {	    	
	    	
	        return gson.toJson("Hello World!");
	    }
	    /*
	     * Start a thread
	     */
	    @Path("/start")
	    @POST
	    @Consumes("application/json;charset=utf-8")
	    @Produces("application/json;charset=utf-8")
	    public String start(String message)  {
	    			System.out.println(message);
	    			String exec_a_task = "schtasks /Run /TN task_" + index;
	    			Command.exec(exec_a_task);
	    			index++;
	    			if(index > 20){
	    				index = 0;
	    			}
	    	
	    	
	    	   
//	    		String DBPosition=loginStart.readXML("DBServer");
	    		resp.Message = exec_a_task;
	    		resp.Succeed = true;
  			    return gson.toJson(resp);
	    }

	    @Path("/pstatus")
	    @GET
	    @Produces("application/json;charset=utf-8")
	    public String getProjectStatus(){
	    	
	    	return "suspend";
	    
	    }
	    @Path("/pstatus")
	    @POST
	    @Produces("application/json;charset=utf-8")
	    public String postProjectStatus(){
	    	
	    	return "suspend";
	    
	    }
	    
//	    @Path("/tasks")
//	    @POST
//	    @Produces("application/json;charset=utf-8")
//	    @Consumes("application/json;charset=utf-8")
//	    public String addTask(String message) {	    	
//	    	System.out.println(message);
//	    	LQATask task = gson.fromJson(message, LQATask.class);
//	    	tasklist.add(task);
//	        return gson.toJson(tasklist);
//	    }
	    
	    @Path("/exec")
	    @GET
	    @Produces("application/json;charset=utf-8")
	    public String changeStatus() {	    	
	    	
//	    	count = Math.floor(Math.random()*10);
//	    	task.screen = (int) count;
	    	
	    	/*
	    	 * 
	    	 * Use a new thread to execute auto-crawl
	    	 * 
	    	 */
	    		Thread thread = new Thread("pid-pname-drop-index" + index){
	    		   public void run(){
	    			   synchronized(this){ 
	    				   while(interval < 60){
		    				   interval++;
		    				   try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		    			   }
	    			   }
	    			   
	    			}
	    		};
	    		
	    		index++;
	    		thread.start();	    		
	        return gson.toJson("");
	    }
	    
	    /*
	     * Get thread list by thread name
	     */
	    @Path("/pause")
	    @GET
	    @Produces("application/json;charset=utf-8")
	    public String getThreadList() {
	    	ArrayList<String> tlist = new ArrayList<String>();
	    	ThreadGroup currentGroup = 
  			      Thread.currentThread().getThreadGroup();
  			      int count = currentGroup.activeCount();
  			      Thread[] threads = new Thread[count];
  			      currentGroup.enumerate(threads);
  			      for (int i = 0; i < count; i++){
  			    	  String tname = threads[i].getName();
  			    	  State tstatus = threads[i].getState();
  			    	  
  			    	  if(tname.contains("index")){
	    			          System.out.println("Threads：" + i + " = " + tname + " " + tstatus);
	    			          tlist.add(tname);
  			    	  }
  			      }
  			    return gson.toJson(tlist);
	    }
	    
	    /*
	     * Pause a thread until notify()
	     */
	    @Path("/pause")
	    @POST
	    @Produces("application/json;charset=utf-8")
	    public String suspend(@QueryParam("index") String tindex) {
	    	ThreadGroup currentGroup = 
  			      Thread.currentThread().getThreadGroup();
  			      int count = currentGroup.activeCount();
  			      Thread[] threads = new Thread[count];
  			      currentGroup.enumerate(threads);
  			      for (int i = 0; i < count; i++){
  			    	  String tname = threads[i].getName();
  			    	  if(tname.contains("index" + tindex)){
//	    			          System.out.println();
	    			          resp.Message = "Threads：" + i + " = " + tname + " is found, try to pause it!";
	    			          try {
								threads[i].wait();
								resp.Succeed = true;
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    	  			      
	    	  			      
//	    			          tlist.add(tname);
  			    	  }
  			      }

  			    return gson.toJson(resp);
	    }
	    

	    
	    
	    
	    
	    @Path("/record")
	    @GET
	    @Produces("application/json;charset=utf-8")
	    public String record(
	    		@QueryParam("pid") String pid,
	    		@QueryParam("lang") String lang,
	    		@QueryParam("drop") int drop,
	    		@QueryParam("url") String url,
	    		@QueryParam("user") String user,
	    		@QueryParam("pwd") String pwd,
	    		@QueryParam("hub") String hub,
	    		@QueryParam("email") String email,
	    		@QueryParam("i18n") String i18n
	    		) {	
	    	/*
    		 * 
    		 * store parameters in a POJO class Para
    		 * 
    		 */
    		Para paras = new Para();
    		paras.pid = pid;
    		paras.lang = lang;
    		paras.email = email;
    		paras.hub = hub;
    		paras.i18n = i18n;
    		paras.pwd = pwd;
    		paras.user = user;
    		paras.drop = drop;
    		paras.url = url;
	    	
	    	/*
	    	 * 
	    	 * Use a new thread to execute auto-crawl
	    	 * 
	    	 */
	    		Thread thread = new Thread(){
	    		   public void run(){
	    		    System.out.println("start auto-crawl");
//	    			Robot.start();	    		   
	    			}
	    		};
	    		thread.start();
	    		
	    	
	    		/*
	    		 * 
	    		 * Use POJO class Resp to generate service response
	    		 * 
	    		 */	    		
	    		resp.Succeed = true;
	    		resp.Message = "Start auto-crawl succeed!";
	    		resp.data = paras;
	    	
	    	return gson.toJson(resp);
	    }
	    @Path("/test")
	    @GET
	    @Produces("application/json;charset=utf-8")
	    public String test(){
	    	WebDriver driver  = new FirefoxDriver();
			driver.get("http://one.ca.com");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebElement e = driver.findElement(By.xpath("//body"));
			String html = e.getAttribute("innerHTML");
			System.out.println(html);
			driver.quit();
	        return gson.toJson(html);

	    }
	    	/*
	    	 * 
	    	 * POJO for parameters
	    	 * 
	    	 */
	    	class Para{
	    		String pid;
	    		String lang;
	    		int drop;
	    		String url;
	    		String user;
	    		String pwd;
	    		String hub;
	    		String email;
	    		String i18n;
	    	}
	    	
	    	/*
	    	 * 
	    	 * POJO for response JSON data
	    	 * 
	    	 */
	    	class Resp{
	    		//status is a must have
	    		boolean Succeed;
	    		
	    		//optional
	    		String Message;
	    		Object data;	    		
	    	}
	}

