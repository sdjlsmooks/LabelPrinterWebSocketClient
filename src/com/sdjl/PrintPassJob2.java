package com.sdjl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
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
      BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, 1);
      Graphics2D graphics2D = resizedImage.createGraphics();
      graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, (ImageObserver)null);
      graphics2D.dispose();
      return resizedImage;
   }

   public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
      if (page > 1) {
         return 1;
      } else if (page == 1) {
         this.printLabel(g, pf);
         return 0;
      } else {
         this.printLabel(g, pf);
         return 0;
      }
   }

   private void printLabel(Graphics g, PageFormat pf) {
      try {
         Graphics2D g2d = (Graphics2D)g;
         g2d.translate(pf.getImageableX(), pf.getImageableY());
         Stroke oldStroke = g2d.getStroke();
         int strokeThickness = 7;
         g2d.setStroke(new BasicStroke((float)strokeThickness));
         g2d.drawRoundRect(15, 5, 135, 60, 5, 5);
         g2d.setStroke(oldStroke);
         g2d.setColor(Color.BLACK);
         JSONObject data = (JSONObject)this.jo.get("data");
         Double temperature = 0.0D;
         if (data != null) {
            if (data.get("temperature") instanceof Long) {
               temperature = ((Long)data.get("temperature")).doubleValue();
            } else {
               temperature = (Double)data.get("temperature");
            }
         }

         Calendar todayCal = Calendar.getInstance();
         Date today = todayCal.getTime();
         SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
         String imageString = (String)data.get("signAvatar");
         if (imageString != null) {
            byte[] imageBytes = Base64.getDecoder().decode(imageString);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            img = this.resizeImage(img, 50, 50);
            g.drawImage(img, 10, 10, (ImageObserver)null);
         }

         g.drawString("Dt: " + formatter.format(today), 65, 20);
         System.out.println("today3: " + formatter.format(today));
         g.drawString("Temp: " + (double)Math.round(temperature * 10.0D) / 10.0D, 65, 32);
         g.setFont(new Font("Calibri", 1, 35));
         g.drawString("PASS", 60, 60);
         g.setFont(new Font("Calibri", 1, 35));
         g.setColor(Color.BLACK);
      } catch (IOException var14) {
         var14.printStackTrace();
      }

   }
}
