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

import javax.jws.WebService;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.ResolutionSyntax;
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
	
	
	private static String printer="Dymo";
	
	
	@OnMessage
	public void onMessage(String message) {
		// The raw data received from the temperature scanner
		System.out.println("-------------------------------->>>>>>");
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println("Date: " + timeStamp + " Received msg: ");
		System.out.println(message);
		
		// Parse out the JSON from device.
		try {
			Object obj = new JSONParser().parse(new StringReader(message));
			JSONObject jo = (JSONObject) obj;			
			if (jo != null) {
				JSONObject dataObject = (JSONObject)jo.get("data");
				if (dataObject != null) {
					Double temperature = 0.0;
					if (dataObject.get("temperature") instanceof Long) {
						Long tempLong = (Long)dataObject.get("temperature");
						temperature = tempLong.doubleValue();
					}
					else {
						temperature = (Double)dataObject.get("temperature");
					}
					// wearMask == 1 --> TRUE (wearing mask)
					// wearMask == 0 --> FALSE (not wearing mask)
					//Long wearMask = (Long)dataObject.get("wearMask");
					//if (wearMask == 1) { // turn off for now, the mask detection on the device does not work very well.
						if ((temperature != null) && (temperature >= 96.8 ) && (temperature <= 99.5)) {
							// Print pass
							printPass(jo);
						}
					//}
				}
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static void wait4TerminateSignal() {
		synchronized (waitLock) {
			try {
				waitLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	private void printPass(JSONObject jo) {
		try {
			
			PrinterJob job = PrinterJob.getPrinterJob();
		    PageFormat pf = job.defaultPage();
		    Paper paper = pf.getPaper();
			Printable passable = new PrintPassJob2(jo);
			PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
			boolean zdPrinter = printer.contains("ZD420");
			if (!zdPrinter) {
				passable = new PrintPassJob2(jo);		
			    paper.setSize(1.0 * 72, 3.47 * 72);
			    paper.setImageableArea(0.1 * 72, 0.1 * 72, 1.0 * 72, 3.47 * 72);					
				attributes.add(OrientationRequested.LANDSCAPE);
				PrinterResolution pr = new PrinterResolution(313, 313, ResolutionSyntax.DPI);
				attributes.add(pr);
			}
			else {
				passable = new PrintPassZebraJob(jo);
			    paper.setSize((3.0*72), (1.12 * 72));
			    paper.setImageableArea(0, 0, 3.0 * 72, 1.00 * 72);
				PrinterResolution pr = new PrinterResolution(203, 203, ResolutionSyntax.DPI);
				attributes.add(OrientationRequested.PORTRAIT);
				attributes.add(pr);
			}

		    pf.setPaper(paper);
		
			/* Create an array of PrintServices */			
			PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);			
			/* Scan found services to see if anyone suits our needs */
			PrintService labelPrinter = null;
			for (int i = 0; i < services.length; i++) {
				System.out.println("Printer: '" + services[i].getName()+"'");
				//
				//if (services[i].getName().contentEquals("\\\\ADMINRECEP\\DYMO LabelWriter Wireless")) {
				if (services[i].getName().contentEquals(printer)) {
					labelPrinter = services[i];
					System.out.println("Found Printer");
					break;
				}
			}
		

			job.setPrintService(labelPrinter);
			job.setPrintable(passable,pf);
			//job.setPrintable(passable);			
			job.print(attributes);
		} catch (PrinterException ex) {
			/* The job did not successfully complete */
		}
	}	
	
	public static void main(String[] args) {
		WebSocketContainer container = null;//
		Session session = null;
				
		if (args.length < 6) {
			System.out.println("Usage:  java -jar LabelPrinter.jar -h(ost) <host> -p(ort) <port> -printer <printer name>");
			System.exit(1);
		}
		String host="10.20.2.248";
		String port="9000";
		
		try {
			
			// Create GNU like options
			Options opts = new Options();
			opts.addOption("h", "host", true, "Host")
			 	.addOption("p", "port", true, "Port")
				.addOption("P", "printer", true, "Printer");
			
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(opts, args);
			host = cmd.getOptionValue("host");
			port = cmd.getOptionValue("port");
			printer = cmd.getOptionValue("printer");
			
			System.out.println("GNU Host='"+host+"'");
			System.out.println("GNU Port='"+port+"'");
			System.out.println("GNU Printer='"+printer+"'");
			
			// security needed to access the device.  Per documentation the access hash is
			// timestamp=<timestamp>&sign=MD5(timestamp#password)
			String timestamp;
			timestamp = "" + Calendar.getInstance().getTime().getTime();
			String sign;
			String password = "123456";
			String remark = "remark=test";
			sign = timestamp + "#" + password;
			String md5Sign = md5Java(sign);

			System.out.println("timestamp=" + timestamp);
			System.out.println("sign='" + sign + "'");
			System.out.println("md5Sign='" + md5Sign + "'");

			// BaseURL for the device
			//String baseURL = "ws://10.20.2.248:9000/api/record?timestamp=";
			String baseURL = "ws://"+host+":"+port+"/api/record?timestamp=";
			String request = baseURL + timestamp + "&sign=" + md5Sign;// +"&"+remark;
			System.out.println("Request='" + request + "'");

			container = ContainerProvider.getWebSocketContainer();
			//session = container.connectToServer(WSClient.class, URI.create("ws://10.20.2.248:9000/api/record?timestamp=1599743858281&sign=cd4bc5c543c184551fd3dc548acde02d"));			
			session = container.connectToServer(WSClient.class, URI.create(request));
			wait4TerminateSignal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	// Helper function needed to create password hash for the
	// device.
	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			// converting byte array to Hexadecimal
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		return digest;
	}
}
