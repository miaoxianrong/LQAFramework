package jerseytest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class Command {
	public static void exec(String commandStr) { 
//		System.out.println("start");
        BufferedReader br = null;  
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            
//            System.out.println(p);
            p.waitFor();
//            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
//            String line = null;  
//            StringBuilder sb = new StringBuilder();  
//            while ((line = br.readLine()) != null) {  
//                sb.append(line + "\n");  
//            }  
//            System.out.println(sb.toString()); 
            String line;

            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while((line = error.readLine()) != null){
                System.out.println(line);
            }
            error.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((line=input.readLine()) != null){
                System.out.println(line);
            }

            input.close();
             
            OutputStream outputStream = p.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println();
            printStream.flush();
            printStream.close();
             
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}
