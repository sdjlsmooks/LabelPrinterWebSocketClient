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

class PrintPassZebraJob implements Printable {
   private JSONObject jo;
   private static Font largeBoldFont = new Font("Calibri", 1, 35);
   private static Font smallPlainFont = new Font("Calibri", 0, 15);

   BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
      BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, 1);
      Graphics2D graphics2D = resizedImage.createGraphics();
      graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, (ImageObserver)null);
      graphics2D.dispose();
      return resizedImage;
   }

   public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
      try {
         if (page > 1) {
            return 1;
         }

         if (page == 1) {
            //this.printLabel(g);
        	this.printIntentionallyBlank(g);
            return 0;
         }

         this.printLabel(g);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return 0;
   }

   private void printIntentionallyBlank(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setFont(smallPlainFont);
      g2.drawString("INTENTIONALLY BLANK", 10, 40);
   }

   private void printLabel(Graphics g) throws IOException {
      Graphics2D g2 = (Graphics2D)g;
      g2.setFont(largeBoldFont);
      g2.setPaint(Color.black);
      int strokeThickness = 7;
      Stroke oldStroke = g2.getStroke();
      g2.setStroke(new BasicStroke((float)strokeThickness));
      g2.drawRoundRect(10, 10, 200, 60, 10, 10);
      JSONObject data = (JSONObject)this.jo.get("data");
      Double temperature = 0.0D;
      String imageString = (String)data.get("signAvatar");
      if (imageString != null) {
         byte[] imageBytes = Base64.getDecoder().decode(imageString);
         BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
         img = this.resizeImage(img, 60, 60);
         g.drawImage(img, 15, 10, (ImageObserver)null);
      }

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
      Font oldFont = g2.getFont();
      g2.setFont(smallPlainFont);
      g.drawString("Dt: " + formatter.format(today), 75, 25);
      g.drawString("Temp: " + (double)Math.round(temperature * 10.0D) / 10.0D, 75, 37);
      g2.setFont(largeBoldFont);
      g2.drawString("PASS", 75, 65);
      g2.setStroke(oldStroke);
      g2.setFont(oldFont);
   }

   PrintPassZebraJob(JSONObject objectToPrint) {
      this.jo = objectToPrint;
   }
}