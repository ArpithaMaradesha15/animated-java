package ApplicationEmail;





import java.util.Date;

import java.util.Properties;

import java.util.Scanner;



import javax.activation.DataHandler;

import javax.activation.FileDataSource;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Multipart;

import javax.mail.Session;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeBodyPart;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;



public class ApplyJobsEmail {

	

	//A)Private data to use Gmail SMTP Server

	private static final String FromEmail = "test@gmail.com";

	private static final String USERNAME = "arpitha.m.15393@gmail.com";

	private static final String PASSWORD = "xxxxxxxxxxxxxxxxxx";

	private static final String SMTP_SERVER = "smtp.gmail.com";



	

	public static void main(String [] args){

		

		//B)Fetch email and job posting to apply

		Scanner scanner = new Scanner(System.in);

	    System.out.println("Enter Email address of the company");

	    String emailadd = scanner.nextLine();

	    System.out.println("Enter the job opening");

	    String Jobpst = scanner.nextLine();

	    scanner.close();

        //C) Email content

		final String ToEmail = emailadd;

		final String SubjectEmail = "Application for the Job "+Jobpst;

		final String EmailContent = "Dear Sir/Madam,"

		+ "\n\nThis is in reference to the possible job positing of "+Jobpst+" in your organisation . I would really like to be a part of your organisation. I hereby would like to apply for the same.  Please find my resume in the attachment." 

		+ "\n\nLooking forward to hearing from you."

		+ "\n\nIn case you need any further information, please do not hesitate to contact me."

		+ "\n\nBest Regards "

		+ "\nArpitha Maradesha"

		+ "\n+491737156833 ";





		       // C) Set system properties

			   Properties prp = System.getProperties();

			   prp.setProperty("mail.transport.protocol", "smtp");     

			   prp.setProperty("mail.host", "smtp.gmail.com");  

			   prp.put("mail.smtp.auth", "true");  

			   prp.put("mail.smtp.port", "465");  

			   prp.put("mail.debug", "true");  

			   prp.put("mail.smtp.socketFactory.port", "465");  

			   prp.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  

			   prp.put("mail.smtp.socketFactory.fallback", "false"); 

			   prp.put("mail.smtp.starttls.enable", "true");

                // Get Instance of the session

		        Session session = Session.getInstance(prp, null);

		        Message mssg = new MimeMessage(session);



		        try {

		        	

		        	//D)  Set recipients email

		        	mssg.setRecipients(Message.RecipientType.TO,

		            InternetAddress.parse(ToEmail, false));

					//Set the email address to which email has to be sent

		        	mssg.setFrom(new InternetAddress(FromEmail));

					// Sub of the email

		        	mssg.setSubject(SubjectEmail);

					// Date of the email

		        	mssg.setSentDate(new Date());

		        	

		        	//E)  add CV to email

		        	MimeBodyPart mbp = new MimeBodyPart();

		        	FileDataSource fds = new FileDataSource("/Users/arpithamaradesha/Desktop/Resume and Cover letter/ArpithaCV_G.pdf");

		        	mbp.setDataHandler(new DataHandler(fds));

		        	mbp.setFileName(fds.getName());

		        	

		        	//F) add email content

		        	MimeBodyPart et = new MimeBodyPart();

		        	et.setText(EmailContent);

		        	

		        	//G) Add email content and CV to multipart content

		        	Multipart mmp = new MimeMultipart();

		        	mmp.addBodyPart(mbp);

		        	mmp.addBodyPart(et);

		        	mssg.setContent(mmp);





		        	

					//H) get SMTP session : SMTPTransport

		            SMTPTransport ts = (SMTPTransport) session.getTransport("smtp");

					// Connect with personal email address

		            ts.connect(SMTP_SERVER, USERNAME, PASSWORD);

					//I) Send mail from SMTP 

		            ts.sendMessage(mssg, mssg.getAllRecipients());

		            ts.close();



		        } catch (MessagingException e) {

		            e.printStackTrace();

		        }



	 }  

}
