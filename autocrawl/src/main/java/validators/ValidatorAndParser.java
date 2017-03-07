package validators;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import validators.ValidatorAndDetectTruncation;
import validators.ValidatorUtil;

public class ValidatorAndParser {
	
	ValidatorUtil util = new ValidatorUtil();

	int[] locate_element(WebDriver driver, String label, ArrayList<String> tag) throws Exception
	{
		ArrayList<WebElement> objs = new ArrayList<WebElement>();
		ValidatorAndDetectTruncation  dt = new ValidatorAndDetectTruncation();
		
		JComponent jl = new JLabel();
		String node = "";
		int text_width=0;
		int text_height = 0;
		int truncation_count_h = 0;
		int truncation_count_v = 0;
		int count[]={0,0};
		String font;
		for(int i=0; i<tag.size();i++)
		{
				 node = "//" + tag.get(i) + "[text()='"+ label + "']";		
				
				 List<WebElement> objs_temp = driver.findElements(By.xpath(node));		
					for(int j=0;j<objs_temp.size();j++)
					{
						objs.add(objs_temp.get(j));
						System.out.println("Node:" + node);
						
						
						int element_width = objs_temp.get(j).getSize().width;	
						int element_height = objs_temp.get(j).getSize().height;	
						
						int font_size = (int)Float.parseFloat(objs_temp.get(j).getCssValue("font-size").replace("px", ""));
						System.out.println("Dimension: " + objs_temp.get(j).getSize() + "\nFont-size:" + font_size);
						int font_weight = Integer.parseInt(objs_temp.get(j).getCssValue("font-weight"));
					    System.out.println("Font weight: " + font_weight); 
					    String font_family = objs_temp.get(j).getCssValue("font-family");
					    System.out.println("Font family: " + font_family); 	
					    
					    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); 	
					    System.out.println("element_width " + "xxxxxxxxxxxxx"+element_width); 	
					    System.out.println("element_height " + "xxxxxxxxxxxxx"+element_height); 	
					    int element_area = element_width * element_height;
					    System.out.println("element_area " + "xxxxxxxxxxxxx"+element_area); 	
					    
					    
					    if(font_family.indexOf(",") != -1)
					    {
					    	font = font_family.substring(0, font_family.indexOf(","));
					    }
					    else
					    {
					    	font = font_family;
					    }
					    System.out.println("Font: " + font); 
					    
					 if(element_width> 0)
					 {
						 if(font_weight == 400)
						 {
							 Font f = new Font(font, Font.PLAIN, font_size);
							 FontMetrics fm = jl.getFontMetrics(f);
							 text_width =  fm.stringWidth(label);
							 text_height = fm.getHeight();
							 
							 
							 System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); 	
							 System.out.println("Text Width: " + text_width); 
							 System.out.println("Text Height: " + text_height); 
							 int text_area = text_width * text_height;
							 System.out.println("Text area: " + text_area);
							 System.out.println("Element area: " + element_area);
							 System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); 
							 
							 
						 }
						 if(font_weight == 700)
						 {
							 Font f = new Font(font, Font.BOLD, font_size);
							 FontMetrics fm = jl.getFontMetrics(f);
							 text_width =  fm.stringWidth(label);
							 text_height = fm.getHeight();
							 System.out.println("Text Width: " + text_width); 
							 System.out.println("Text Height: " + text_height); 
							 int text_area = text_width * text_height;
							 System.out.println("Text area: " + text_area);
							 System.out.println("Element area: " + element_area);
						 }
						 
						int difference_h =element_width - text_width;
						int difference_v = element_height - text_height;
						boolean truncation_h = false;
						boolean truncation_v = false;
						
						if (difference_h < -5)
							{
							truncation_h = true;
							util.highlight_Element(driver, objs_temp.get(j));
							truncation_count_h++;
							System.out.println("xxxxxxxxxxxxxxxx"+truncation_count_h);
							//dt.getScrnTruncation(driver);
							}
						if (difference_v<-5)
							{
							truncation_v = true;
							truncation_count_v++;
							util.highlight_Element(driver, objs_temp.get(j));
							System.out.println("xxxxxxxxxxxxxxxx"+truncation_count_v);
							//dt.getScrnTruncation(driver);
							}
						 System.out.println("Horizonal truncation: " + truncation_h); 
						 System.out.println("Vertical truncation: " + truncation_v); 
						 System.out.println("\n");
						
						
					 }
					 
					
					
					
					}
				
				
		}
		count[0] = truncation_count_h;
		count[1] =truncation_count_v;
	return count;
		
	}





}
