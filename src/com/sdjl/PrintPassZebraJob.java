package com.sdjl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

class PrintPassZebraJob implements Printable {
	
	  private JSONObject jo;
	
	  private static Font largeBoldFont = new Font("Calibri", Font.BOLD, 35);
	  private static Font smallPlainFont = new Font("Calibri", Font.PLAIN, 15);
	  //private static Font sFont = new Font("Calibri", Font.PLAIN, 12);

		BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
		    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		    Graphics2D graphics2D = resizedImage.createGraphics();
		    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		    graphics2D.dispose();
		    return resizedImage;
		}
	  
	  public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
	    if (page > 1) {
	      return NO_SUCH_PAGE;
	    }
	    else if (page == 1) {
	    	Graphics2D g2 = (Graphics2D)g;
	    	g2.setFont(smallPlainFont);
	    	g2.drawString("INTENTIONALLY BLANK",10,40);
	    	return PAGE_EXISTS;
	    }
    	try {	    	
    		Graphics2D g2 = (Graphics2D) g;
				
    		g2.setFont(largeBoldFont);
    		g2.setPaint(Color.black);
    		int strokeThickness = 7;
    		Stroke oldStroke = g2.getStroke();
    		g2.setStroke(new BasicStroke(strokeThickness));
    		g2.drawRoundRect(10, 10, 200, 60, 10,10);

    		JSONObject data = (JSONObject)jo.get("data");
    		Double temperature = 0.0;

		
			// Print picture
			String imageString = (String)data.get("signAvatar");
			
			if (imageString != null) {					
					byte[] imageBytes = Base64.getDecoder().decode(imageString);
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));									
					img = resizeImage(img, 60, 60);
					g.drawImage(img, 15, 10,  null);
			}	    
			
			
			// Retrieve Temperature
			// typecasting obj to JSONObject 
			// NOTE - The scanned photo from the device is in here.  Use
			//        it or the original file contents for the label.
			if (data != null) {
				if (data.get("temperature") instanceof Long) {
					temperature = ((Long)(data.get("temperature"))).doubleValue();	
				}
				else {
					temperature = (Double)(data.get("temperature"));
				}
			}
			
			
			// Print out today + temperature
			Calendar todayCal = Calendar.getInstance();
			Date today = todayCal.getTime();
			SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
						
			Font oldFont = g2.getFont();
			g2.setFont(smallPlainFont);
			g.drawString("Dt: "+formatter.format(today), 75, 25);		
			g.drawString("Temp: "+((double)Math.round(temperature*10))/10,75,37);
			
			
			g2.setFont(largeBoldFont);
			g2.drawString("PASS", 75, 65);
			g2.setStroke(oldStroke);
			g2.setFont(oldFont);;
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return PAGE_EXISTS;
	  }
	  
	  PrintPassZebraJob(JSONObject objectToPrint) {
		  jo = objectToPrint;
	  }

}
