package com.journaldev.jsf.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.lang3.time.*;



import com.journaldev.jsf.util.DataConnect;
import com.journaldev.jsf.util.SessionUtils;
import com.mysql.cj.xdevapi.Statement;



class MyClass extends Thread{
	
	private volatile boolean exit = false; 
    private volatile static int functionthreadid;
    private volatile static int totalcount;
    private volatile static long totalrsptime;
   

private String url;
private int timex;
private String actionf;
public MyClass(String url,int timex,String actionf) {
	this.url = url;
	this.timex = timex;
	this.actionf = actionf;

}
	
	public void run() {
		long avgresponsetime = 0;
		  long elapsedtime =0;
		 functionthreadid = (int) Thread.currentThread().getId();
		 final String GET_URL = url;
			final String USER_AGENT = "Mozilla/5.0";
			long ltimex = timex;  
			
		int count=0;
		while(count<30 && !Thread.currentThread().isInterrupted() && actionf.equalsIgnoreCase("start")) {
		    System.out.println("runninggggg id!!!!"+Thread.currentThread().getId());
	           int responseCode = 0;
	//	StopWatch watch = new StopWatch();
		try {
			 URL obj = new URL(GET_URL); 
			 long start = System.currentTimeMillis();

         //   watch.start();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			 responseCode = con.getResponseCode();
			 long stop = System.currentTimeMillis();
            elapsedtime = stop - start;
	          //  watch.stop();
	            CloseableHttpResponse response = null;
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
		updatedbrsptime(		Long.toString(elapsedtime),responseCode);
           System.out.println("stop time"+elapsedtime);
		System.out.println("response code"+ responseCode);
       }        
		  avgresponsetime = Long.sum(avgresponsetime, elapsedtime);
		  totalrsptime = avgresponsetime;
		    count++;
		    System.out.println("count"+count);
		    totalcount = count;
		    try {
				Thread.sleep(ltimex);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		   
		}
		 
	   
	    if(Thread.currentThread().isInterrupted()) {
            System.out.println("stopping thread: ");

	    }
	}

	
	private boolean updatedbrsptime(String Rsp_time,int Rsp_Status) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("INSERT INTO rspdetails(URLID,Rsp_time,Rsp_Status)	VALUES ((SELECT MAX(id) FROM urldetails),?,?)");
					//"uname = ? and password = ?");
			ps.setString(1, Rsp_time);
			ps.setLong(2, Rsp_Status);

			int rs = ps.executeUpdate();

				return true;
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		
	}

	public static int getValue() {
        return functionthreadid;
    }
	public static int getCount() {
        return totalcount;
    }
	public static long gettotalrsptime() {
        return totalrsptime;
    }
	
}

public class LoginDAO {
	 static long functionthreadid;   

	private boolean exit;
	  protected static final String USER_AGENT = null;

	private static String 	starttime = null;
	private static String 	totlcountString = null;
	private static String stoptime= null;
	private static String Ttlrestime = null;
	

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static Vector validate(String url, int timex, String action) {

	final	String actionf = action;
		LoginDAO obj = new LoginDAO();
		   int Ttlcount;
		try {
			   
			   MyClass Thread = new MyClass(url, timex, actionf);
			if(actionf.equalsIgnoreCase("start")) {
				 	starttime= java.time.LocalDateTime.now().toString();

					updatedb(url,timex);
				   Thread.start();
				   Thread.join();
				   }
			   else  if(actionf.equalsIgnoreCase("stop")) {
					 stoptime= java.time.LocalDateTime.now().toString();

				   int value = MyClass.getValue();
				   System.out.println("get value from run id!!!!!!!!"+value);
				   Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
				 for(Thread thread : setOfThread){
				     if(thread.getId()==value){
				         thread.interrupt();
				     }
				 }
				    Ttlcount = MyClass.getCount();
				   totlcountString = Integer.toString(Ttlcount);
				    long Ttlrestimel = (MyClass.gettotalrsptime()/Ttlcount);
				    Ttlrestime=  String.valueOf(Ttlrestimel);
			  }
	      
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            obj.close();
	        }
	Vector v=new Vector();
	v.add(starttime);
	v.add(totlcountString);
	v.add(stoptime);
	v.add(Ttlrestime);
			return (v); 
	}
	
	private void close() {
		// TODO Auto-generated method stub
        try {
        	httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static boolean updatedb(String url, int timex) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert  into `urldetails`(`URL`,`TIMEX`) values (  ? ,?)");
			ps.setString(1, url);
			ps.setLong(2, timex);

			int rs = ps.executeUpdate();

				return true;
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
	}

	
		

}

