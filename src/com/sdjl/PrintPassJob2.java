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

class PrintPassJob2 implements Printable {
	
	private JSONObject jo;
		
	public PrintPassJob2(JSONObject jo) {
		this.jo = jo;
		
	}
	
	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    return resizedImage;
	}

    public int print(Graphics g, PageFormat pf, int page) throws
	PrinterException {
		try {
			if (page > 0) { /* We have only one page, and 'page' is zero-based */
				return NO_SUCH_PAGE;
			}

			/*
			 * User (0,0) is typically outside the imageable area, so we must translate by
			 * the X and Y values in the PageFormat to avoid clipping
			 */
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());

			Stroke oldStroke = g2d.getStroke();
			//g2d.setColor(Color.GREEN);
			int strokeThickness = 7;
			g2d.setStroke(new BasicStroke(strokeThickness));
			g2d.drawRoundRect(15, 5, 135, 60,5,5);
			g2d.setStroke(oldStroke);
			g2d.setColor(Color.BLACK);
			
			
			// Retrieve Temperature
	        // typecasting obj to JSONObject 
	        // NOTE - The scanned photo from the device is in here.  Use
	        //        it or the original file contents for the label.
			JSONObject data = (JSONObject)jo.get("data");
	        Double temperature = 0.0;
	        if (data != null) {
	        	if (data.get("temperature") instanceof Long) {
	        		temperature = ((Long)(data.get("temperature"))).doubleValue();	
	        	}
	        	else {
	        		temperature = (Double)(data.get("temperature"));
	        	}
	        }

			
			//DateTime today = new DateTime();
	        Calendar todayCal = Calendar.getInstance();
	        //todayCal.set(Calendar.YEAR, todayCal.get(Calendar.YEAR)-1); // Around 12/21/2021 Year started to be reported incorrectly in java
	                                                                      // Bug turned out to be YYYY should have been yyyy
	        Date today = todayCal.getTime();
	        //Date today = Calendar.getInstance().getTime();
			SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
			
			String imageString = (String)data.get("signAvatar");
			
			if (imageString != null) {					
					byte[] imageBytes = Base64.getDecoder().decode(imageString);
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));									
					img = resizeImage(img, 50, 50);
					g.drawImage(img, 10, 10,  null);
			}
			/* Now we perform our rendering */			
			g.drawString("Dt: "+formatter.format(today), 65, 20);
			System.out.println("today3: "+formatter.format(today));
			g.drawString("Temp: "+((double)Math.round(temperature*10))/10,65,32);
			
			g.setFont(new Font("Calibri", Font.BOLD, 35));
			//g.setColor(Color.GREEN);
			g.drawString("PASS", 60, 60);
			g.setFont(new Font("Calibri", Font.BOLD, 35));
			g.setColor(Color.BLACK);			
			//g.drawString(formatter.format(today.toDate()), 10, 95);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}
}
