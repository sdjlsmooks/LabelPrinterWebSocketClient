package com.sdjl;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
   private static String host = null;
   
   private static SQLServerConnection connection = SQLServerConnection.getInstance();
   
	private void insertJSONData(String sourceIP, JSONObject jo) {

		if (jo == null) {
			return; // sanity check
		}
		Object code = jo.get("code");
		if ((code != null) && (code.equals("20000"))) { // 20000 ==> "Connected" message the scanner
			return;
		}
		JSONObject data = (JSONObject) jo.get("data");
		if (data == null) {
			return;
		}
		String sql = "INSERT INTO [Temp_Scanners].[dbo].Scanner_Data (scanner_ip, startTime, userId, gender, jobNumber, position, userType, purpose, birthday,department,prompt,description,mobile,remark,entryTime,title,endTime,name,mail,icNumber,idNumber,comeFrom,photo,employee_id,signTime,type,inputType,similarity,temperatureState,wearMask,wearMaskAlarm,temperature,Temperatureunit,status,signAvatar)  VALUES ("
				+ "?," + // scanner_ip VARCHAR(50), 1
				"?," + // startTime VARCHAR(50), 2
				"?," + // userId VARCHAR(50), 3
				"?," + // gender VARCHAR(50), 4
				"?," + // jobNumber VARCHAR(50), 5
				"?," + // position VARCHAR(50), 6
				"?," + // userType VARCHAR(50), 7
				"?," + // purpose VARCHAR(50), 8
				"?," + // birthday VARCHAR(50), 9
				"?," + // department VARCHAR(50), 10
				"?," + // prompt VARCHAR(50), 11
				"?," + // description VARCHAR(50), 12
				"?," + // mobile VARCHAR(50), 13
				"?," + // remark VARCHAR(3072), 14
				"?," + // entryTime VARCHAR(50), 15
				"?," + // title VARCHAR(200), 16
				"?," + // endTime VARCHAR(50), 17
				"?," + // name VARCHAR(50), 18
				"?," + // mail VARCHAR(100), 19
				"?," + // icNumber VARCHAR(50), 20
				"?," + // idNumber VARCHAR(50), 21
				"?," + // comeFrom VARCHAR(50), 22
				"?," + // photo VARCHAR(50), 23
				"?," + // employee_id VARCHAR(50), 24
				"?," + // signTime VARCHAR(50), 25
				"?," + // type VARCHAR(50), 26
				"?," + // inputType VARCHAR(50), 27
				"?," + // similarity VARCHAR(50), 28
				"?," + // temperatureState VCR(50),29
				"?," + // wearMask VARCHAR(50), 30
				"?," + // wearMaskAlarm VARCHAR(50)31
				"?," + // temperature VARCHAR(50), 32
				"?," + // Temperatureunit VARCHAR(50), 33
				"?," + // status VARCHAR(50), 34
				"?" + // signAvatar VARCHAR(MAX) 35
				")";

		try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {

			
			// START 12/7/2021 - ADD NULL CHECKS FOR ALL JSON DATA
			// 
			
			ps.setString(1, sourceIP);
			
			Object startTimeObj = data.get("startTime");
			if (startTimeObj != null) {
				ps.setString(2, startTimeObj.toString());
			}
			else {
				ps.setString(2, "");
				
			}
			Object userIdObj = data.get("userId");
			if (userIdObj != null) {
				ps.setString(3, userIdObj.toString());
			}
			else {
				ps.setString(3, "");
			}
			Object genderObj = data.get("gender");
			if (genderObj != null) {
				ps.setString(4, genderObj.toString());
			}
			else {
				ps.setString(4, "");
			}
			Object jobNumberObj = data.get("jobNumber");
			if (jobNumberObj != null ) {
				ps.setString(5, jobNumberObj.toString());
			}
			Object positionObj = data.get("position");
			if (positionObj != null) {
				ps.setString(6, positionObj.toString());
			}
			else {
				ps.setString(6, positionObj.toString());
			}
			Object userTypeObj = data.get("userType");
			if (userTypeObj != null) {
				ps.setString(7, userTypeObj.toString());
			}
			else {
				ps.setString(7, "");
			}
				
			Object purposeObj = data.get("purpose");
			if (purposeObj != null) {
				ps.setString(8, purposeObj.toString());
			}
			else {
				ps.setString(8, "");
			}
			Object birthdayObj = data.get("birthday");
			if (birthdayObj != null) {
				ps.setString(9, birthdayObj.toString());
			}
			else {
				ps.setString(9, "");
			}
			Object departmentObj = data.get("department");
			if (departmentObj != null) {
				ps.setString(10, departmentObj.toString());
			}
			else {
				ps.setString(10, "");
			}
			Object promptObj = data.get("prompt");
			if (promptObj != null) {
				ps.setString(11, promptObj.toString());
			}
			else {
				ps.setString(11, "");
			}
			Object descriptionObj = data.get("description");
			if (descriptionObj != null) {
				ps.setString(12, descriptionObj.toString());
			}
			else {
				ps.setString(12, "");
			}
			Object mobileObj = data.get("mobile");
			if (mobileObj != null) {
				ps.setString(13, mobileObj.toString());
			}
			else {
				ps.setString(13, "");
			}
			Object remarkObj = data.get("remark");
			if (remarkObj != null) {
				ps.setString(14, remarkObj.toString());
			}
			else {
				ps.setString(14, "");
			}
			Object entryTimeObj = data.get("entryTime");
			if (entryTimeObj != null) {
				ps.setString(15, entryTimeObj.toString());
			}
			else {
				ps.setString(15, "");
			}
			Object titleObj = data.get("title");
			if (titleObj != null) {
				ps.setString(16, titleObj.toString());
			}
			else {
				ps.setString(16, "");
			}
			Object endTimeObj = data.get("endTime");
			if (endTimeObj != null) {
				ps.setString(17, endTimeObj.toString());
			}
			else {
				ps.setString(17, "");
			}
			Object nameObj = data.get("name");
			if (nameObj != null) {
				ps.setString(18, nameObj.toString());
			}
			else {
				ps.setString(18, "");
			}
			Object mailObj = data.get("mail");
			if (mailObj != null) {
				ps.setString(19, mailObj.toString());
			}
			else {
				ps.setString(19, "");
			}
	
			Object icNumberObj = data.get("icNumber");
			if (icNumberObj != null) {
				ps.setString(20, icNumberObj.toString());
			}
			else {
				ps.setString(20, "");
			}
			Object idNumberObj = data.get("idNumber");
			if (idNumberObj != null) {
				ps.setString(21, idNumberObj.toString());
			}
			else {
				ps.setString(21, "");
			}
			Object comeFromObj = data.get("comeFrom");
			if (comeFromObj != null) {
				ps.setString(22, comeFromObj.toString());
			}
			else {
				ps.setString(22, "");
			}
			Object photoObj = data.get("photo");
			if (photoObj != null) {
				ps.setString(23, photoObj.toString());
			}
			else {
				ps.setString(23, "");
			}
			Object idObj = data.get("id");
			if (idObj != null) {
				ps.setString(24, idObj.toString());
			}
			else {
				ps.setString(24, "");
			}
			
			Object signTimeObj = data.get("signTime");
			if (signTimeObj != null) {
				ps.setString(25, signTimeObj.toString());
			}
			else {
				ps.setString(25, "");
			}
			Object typeObj = data.get("type");
			if (typeObj != null) {
				ps.setString(26, typeObj.toString());
			}
			else {
				ps.setString(26, "");
			}
			Object inputTypeObj = data.get("inputType");
			if (inputTypeObj != null) {
				ps.setString(27, inputTypeObj.toString());
			}
			else {
				ps.setString(27, "");
			}
			Object similarityObj = data.get("similarity");
			if (similarityObj != null) {
				ps.setString(28, similarityObj.toString());
			}
			else {
				ps.setString(28, "");
			}
			Object temperatureStateObj = data.get("temperatureState");
			if (temperatureStateObj != null) {
				ps.setString(29, temperatureStateObj.toString());
			}
			else {
				ps.setString(29, "");
			}
			Object wearMaskObj = data.get("wearMask");
			if (wearMaskObj != null) {
				ps.setString(30, wearMaskObj.toString());
			}
			else {
				ps.setString(30, "");
			}
			Object wearMaskAlarmObj = data.get("wearMaskAlarm");
			if (wearMaskAlarmObj != null) {
				ps.setString(31, wearMaskAlarmObj.toString());
			}
			else {
				ps.setString(31, "");
			}
			Object temperatureObj = data.get("temperature");			
			if (temperatureObj != null) {
				ps.setString(32, temperatureObj.toString());
			}
			else {
				ps.setString(32, "");
			}
			Object temperatureUnitObj = data.get("Temperatureunit");
			if (temperatureUnitObj != null) {
				
				ps.setString(33, temperatureUnitObj.toString());
			}
			else {
				ps.setString(33, "");
			}
			Object statusObj = data.get("status");
			if (statusObj != null) {
				ps.setString(34, statusObj.toString());
			}
			else {
				ps.setString(34, "");
			}
				
			Object signAvatarObj = data.get("signAvatar");			
			if (signAvatarObj != null) {
				ps.setString(35, signAvatarObj.toString());
			}
			else {
				ps.setString(35, "");
			}
			System.out.println("SDJL - Before Database Insert");			
			ps.execute();
			System.out.println("SDJL - After Database Insert");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println("-------------------------------->>>>>>");
		String timeStamp = (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")).format(Calendar.getInstance().getTime());
		System.out.println("Date: " + timeStamp + " Received msg: ");
		System.out.println(message);

		try {
			Object obj = (new JSONParser()).parse(new StringReader(message));
			// Not strictly needed here, but useful during debugging.
			JSONObject jo = (JSONObject) obj;
//         System.out.println("START JSON");
//         FileWriter fw  = new FileWriter(new File("c:\\temp\\testjson.json"));
//         System.out.println(message);
//         fw.write(message);
//         fw.close();
//         System.out.println("END JSON");

			if (jo != null) {
				JSONObject dataObject = (JSONObject) jo.get("data");
				if (dataObject != null) {
					Double temperature = 0.0D;
					if (dataObject.get("temperature") instanceof Long) {
						Long tempLong = (Long) dataObject.get("temperature");
						temperature = tempLong.doubleValue();
					} else {
						temperature = (Double) dataObject.get("temperature");
					}

					if ((temperature != null) && (temperature >= 96.8D) && (temperature <= 99.5D)) {
						insertJSONData(host, jo);
						
						// Check to see if there is a registered photo in the system.  If there is
						// a registered photo as well as a daily photo, no need to print a sticker
						
						Object photo = jo.get("photo"); // If there is a photo the user has a registered photo.
						System.out.println("ID Number: "+photo);
						if ((photo != null) && (photo.toString().trim().length() == 0)) {
							printPass(jo);
							System.out.println("GUEST PASS");
						}
						else {
							System.out.println("REGISTERED STAFF PASS - NO STICKER");
						}
						
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

      host = "10.20.2.248";
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
	
	
	