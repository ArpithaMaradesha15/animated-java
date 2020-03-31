package dbcheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dbcheck {

	public static void main(String[] args) {
		
		 
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/root?useSSL=false","root","worldlife");  
		//here sonoo is database name, root is username and password  
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from urldetails");  
		while(rs.next())  
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
		con.close();  
		}catch(Exception e){ System.out.println(e);}  
		
		/*
		// TODO Auto-generated method stub
		 String Url = "jdbc:mysql://localhost:3306/responsetime;user=root;Password=worldlife";
         try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             System.out.println("Trying to connect");
             Connection connection = DriverManager.getConnection(Url);

             System.out.println("Connection Established Successfull and the DATABASE NAME IS:"
                     + connection.getMetaData().getDatabaseProductName());
         } catch (Exception e) {
System.out.println("Unable to make connection with DB");
             e.printStackTrace();
         }*/
	}

}
