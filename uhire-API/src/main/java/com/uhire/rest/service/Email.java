package com.uhire.rest.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.uhire.rest.model.Employee;
import com.uhire.rest.model.EmployeeJobFunctionNeed;
import com.uhire.rest.model.Person;

public class Email {

	public static void processNeedsCompleted(List<EmployeeJobFunctionNeed> needs, Employee employee) throws AddressException, MessagingException {
		// ***!!!!!!!!! CHANGE THIS TO GET THE MANAGERS' EMAILS TO SEND NOTICE OF COMPLETION
		String recipients = "";
		String id = employee.getId();
		String name = employee.getFirstName() + " " + employee.getLastName();
		for(EmployeeJobFunctionNeed need : needs) {
			recipients += need.getCreateUser().getEmail();
//			if(!need.getNeed().getNoticeRecipients().isEmpty()) {	//!!!!!!
//				for(Person person : need.getNeed().getNoticeRecipients()) {	//!!!!!!!
//					recipients += person.getEmail() + ",";
//				}
//			}
		}
		
		if(recipients.length() > 0) {
			recipients = recipients.substring(0, recipients.length() - 1);	// remove trailing comma after loop
			send(id, name, recipients);			
		}
	}

	public static void newNeedNotice(List<Person> noticeRecipients, Employee employee) throws AddressException, MessagingException {
		String recipients = "";
		String id = employee.getId();
		String name = employee.getFirstName() + " " + employee.getLastName();
		if(!noticeRecipients.isEmpty()) {
			for(Person recipient : noticeRecipients) {
				recipients += recipient.getEmail() + ",";
			}
		}
		
		if(recipients.length() > 0) {
			recipients = recipients.substring(0, recipients.length() - 1);	// remove trailing comma after loop
			send(id, name, recipients);			
		}		
	}
	
	private static void send(String id, String name, String recipients) throws AddressException, MessagingException {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.mailtrap.io");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
		  
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("userName", "password");
			}
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("noreply@jmscottnovels.com"));
		message.setRecipients(
			Message.RecipientType.TO, InternetAddress.parse(recipients));
		message.setSubject("A new employee, " + name + ", has onboarding needs");

		String msg = "Click <a href=\"https://uhire.jmscottnovels.com/" + id + "\">here</a> to assist " + name + " with their job function needs.";

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		//Transport.send(message);
		System.out.println("Message sent:" + InternetAddress.toString(message.getAllRecipients()));
	}
}
