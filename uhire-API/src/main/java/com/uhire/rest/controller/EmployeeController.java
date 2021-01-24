package com.uhire.rest.controller;

import java.math.BigDecimal;
import java.net.URI;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uhire.rest.exception.ResourceNotFoundException;
import com.uhire.rest.model.Employee;
import com.uhire.rest.model.EmployeeJobFunctionNeed;
import com.uhire.rest.model.Person;
import com.uhire.rest.repository.EmployeeRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/employees")
@CrossOrigin(origins= {"http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;
	     
	@GetMapping
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping
	public List<Employee> getEmployeesAll() {
		return employeeRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) throws ResourceNotFoundException {
		Employee emp = employeeRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No Employee found with id: " + id)
		);
		
		return ResponseEntity.ok(emp);
	}
	
	// TODO: implement EmployeeJobFunctionNeed saving employee and need fields
	@PostMapping
	public ResponseEntity<Void> createEmployee(@Validated @RequestBody Employee emp) {
		emp.setId(null); // ensure mongo is creating id
		if(emp.getPay() == null || emp.getPay().compareTo(new BigDecimal("0")) == 0 ) {
			emp.setPay(emp.getPosition().getDefaultPay());
		}
		
		if(emp.getPayType() == null) {
			emp.setPayType(emp.getPosition().getDefaultPayType());
		}
		
		if(emp.getWorkFrequency() == null) {
			emp.setWorkFrequency(emp.getPosition().getDefaultWorkFrequency());
		}
		
		Employee newEmp = employeeRepository.save(emp);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newEmp.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Employee> updateEmployee(
			@PathVariable String id,
			@Validated @RequestBody Employee emp) throws ResourceNotFoundException, AddressException, MessagingException {
		employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No employee found with id " + id) );
		Employee savedEmployee = employeeRepository.save(emp);
		
		if(savedEmployee.isOnboardingComplete()) {
			String recipients = "";
			for(EmployeeJobFunctionNeed need : savedEmployee.getNeeds()) {
				for(Person person : need.getNeed().getNoticeRecipients()) {
					recipients += person.getEmail() + ",";
				}
			}
			recipients = recipients.substring(0, recipients.length()-1);
			processNeedsCompleted(savedEmployee.getId(), savedEmployee.getUser().getPerson().getFirstName() + savedEmployee.getUser().getPerson().getLastName(), recipients);
		}
		return new ResponseEntity<Employee>(savedEmployee, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String id) throws ResourceNotFoundException {
		Employee deletedEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No employee found with id " + id) );
		employeeRepository.deleteById(id);
		
		return new ResponseEntity<Employee>(deletedEmployee, HttpStatus.OK);
	}
	
	public void processNeedsCompleted(String id, String name, String recipients) throws AddressException, MessagingException {
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

		Transport.send(message);
	}
	
}
