package com.sdjl;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@ClientEndpoint
public class WSClient {
   private static Object waitLock = new Object();
   private static String printer = "Dymo";

   @OnMessage
   public void onMessage(String message) {
      System.out.println("-------------------------------->>>>>>");
      String timeStamp = (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")).format(Calendar.getInstance().getTime());
      System.out.println("Date: " + timeStamp + " Received msg: ");
      System.out.println(message);

      try {
         Object obj = (new JSONParser()).parse(new StringReader(message));
         JSONObject jo = (JSONObject)obj;
         if (jo != null) {
            JSONObject dataObject = (JSONObject)jo.get("data");
            if (dataObject != null) {
               Double temperature = 0.0D;
               if (dataObject.get("temperature") instanceof Long) {
                  Long tempLong = (Long)dataObject.get("temperature");
                  temperature = tempLong.doubleValue();
               } else {
                  temperature = (Double)dataObject.get("temperature");
               }

               if ((temperature != null) && (temperature >= 96.8D) && (temperature <= 99.5D)) {
                  this.printPass(jo);
               }
            }
         }
      } catch (ParseException | IOException var8) {
         var8.printStackTrace();
      }

   }

   private static void wait4TerminateSignal() {
      synchronized(waitLock) {
         try {
            waitLock.wait();
         } catch (InterruptedException var2) {
         }

      }
   }

   private void printPass(JSONObject jo) {
      try {
         PrinterJob job = PrinterJob.getPrinterJob();
         PageFormat pf = job.defaultPage();
         Paper paper = pf.getPaper();
         new PrintPassJob2(jo);
         PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
         boolean zdPrinter = printer.contains("ZD420");
         Object passable;
         PrinterResolution pr;
         if (!zdPrinter) {
            passable = new PrintPassJob2(jo);
            paper.setSize(72.0D, 249.84D);
            paper.setImageableArea(7.2D, 7.2D, 72.0D, 249.84D);
            attributes.add(OrientationRequested.LANDSCAPE);
            pr = new PrinterResolution(313, 313, 100);
            attributes.add(pr);
         } else {
            passable = new PrintPassZebraJob(jo);
            paper.setSize(216.0D, 80.64D);
            paper.setImageableArea(0.0D, 0.0D, 216.0D, 72.0D);
            pr = new PrinterResolution(203, 203, 100);
            attributes.add(OrientationRequested.PORTRAIT);
            attributes.add(pr);
         }

         pf.setPaper(paper);
         PrintService[] services = PrintServiceLookup.lookupPrintServices((DocFlavor)null, (AttributeSet)null);
         PrintService labelPrinter = null;

         for(int i = 0; i < services.length; ++i) {
            System.out.println("Printer: '" + services[i].getName() + "'");
            if (services[i].getName().contentEquals(printer)) {
               labelPrinter = services[i];
               System.out.println("Found Printer");
               break;
            }
         }

         job.setPrintService(labelPrinter);
         job.setPrintable((Printable)passable, pf);
         job.print(attributes);
      } catch (PrinterException var11) {
      }

   }

   public static void main(String[] args) {
      WebSocketContainer container = null;
      Session session = null;
      if (args.length < 6) {
         System.out.println("Usage:  java -jar LabelPrinter.jar -h(ost) <host> -p(ort) <port> -printer <printer name>");
         System.exit(1);
      }

      String host = "10.20.2.248";
      String port = "9000";

      try {
         Options opts = new Options();
         opts.addOption("h", "host", true, "Host").addOption("p", "port", true, "Port").addOption("P", "printer", true, "Printer");
         CommandLineParser parser = new DefaultParser();
         CommandLine cmd = parser.parse(opts, args);
         host = cmd.getOptionValue("host");
         port = cmd.getOptionValue("port");
         printer = cmd.getOptionValue("printer");
         System.out.println("GNU Host='" + host + "'");
         System.out.println("GNU Port='" + port + "'");
         System.out.println("GNU Printer='" + printer + "'");
         String timestamp = "" + Calendar.getInstance().getTime().getTime();
         String password = "123456";
         String remark = "remark=test";
         String sign = timestamp + "#" + password;
         String md5Sign = md5Java(sign);
         System.out.println("timestamp=" + timestamp);
         System.out.println("sign='" + sign + "'");
         System.out.println("md5Sign='" + md5Sign + "'");
         String baseURL = "ws://" + host + ":" + port + "/api/record?timestamp=";
         String request = baseURL + timestamp + "&sign=" + md5Sign;
         System.out.println("Request='" + request + "'");
         container = ContainerProvider.getWebSocketContainer();
         session = container.connectToServer(WSClient.class, URI.create(request));
         wait4TerminateSignal();
      } catch (Exception var23) {
         var23.printStackTrace();
      } finally {
         if (session != null) {
            try {
               session.close();
            } catch (Exception var22) {
               var22.printStackTrace();
            }
         }

      }

   }

   public static String md5Java(String message) {
      String digest = null;

      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] hash = md.digest(message.getBytes("UTF-8"));
         StringBuilder sb = new StringBuilder(2 * hash.length);
         byte[] var8 = hash;
         int var7 = hash.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            byte b = var8[var6];
            sb.append(String.format("%02x", b & 255));
         }

         digest = sb.toString();
      } catch (UnsupportedEncodingException var9) {
         Logger.getLogger(var9.getClass().getName()).log(Level.SEVERE, (String)null, var9);
      } catch (NoSuchAlgorithmException var10) {
         Logger.getLogger(var10.getClass().getName()).log(Level.SEVERE, (String)null, var10);
      }

      return digest;
   }
}