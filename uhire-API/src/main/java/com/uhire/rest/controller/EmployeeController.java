package com.uhire.rest.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uhire.rest.exception.ResourceNotFoundException;
import com.uhire.rest.model.Employee;
import com.uhire.rest.model.EmployeeJobFunctionNeed;
import com.uhire.rest.model.JobFunctionNeed;
import com.uhire.rest.model.Person;
import com.uhire.rest.model.lists.TaskStatus;
import com.uhire.rest.repository.EmployeeRepository;
import com.uhire.rest.repository.PersonRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/employees")
@CrossOrigin(origins= {"http://localhost:3003", "http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;

	@Autowired
	private PersonRepository personRepository;
	     
	@GetMapping(path = "/health-check")
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping
	public List<Employee> getEmployeesAll() {
		return employeeRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No Employee found with id: " + id)
		);
		
		return ResponseEntity.ok(employee);
	}
	
	@GetMapping(path = "/name/{name}")
	public List<Employee> getEmployeesByName(@PathVariable String name) {
		List<Employee> employees = employeeRepository.findByFirstNameLikeIgnoreCase(name);
		employees.addAll(employeeRepository.findByLastNameLikeIgnoreCase(name));
		return employees;
	}
	
	@GetMapping(path = "/firstName/{firstName}/lastName/{lastName}")
	public List<Employee> getEmployeesByName(@PathVariable String firstName, @PathVariable String lastName) {
		List<Employee> employees = employeeRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(firstName, lastName);
		employees.addAll(employeeRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(lastName, firstName));
		return employees;
	}	
	
	// TODO: implement EmployeeJobFunctionNeed saving employee and need fields
	// TODO: integrity check with exception throw
	@PostMapping
	public ResponseEntity<String> createEmployee(@Validated @RequestBody Employee employee) {
		employee.setId(null); // ensure mongo is creating id
		
		employee = getDefaultsFromPosition(employee);
		
		Employee newEmp = employeeRepository.save(employee);
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
		//		.buildAndExpand(newEmp.getId()).toUri();
		return new ResponseEntity<String>(newEmp.getId(), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Employee> updateEmployee(
			@PathVariable String id,
			@RequestParam(required = false) boolean positionChanged,
			@Validated @RequestBody Employee employee) throws ResourceNotFoundException, AddressException, MessagingException {
		personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No one found with id " + id) );
		Employee savedEmployee = employeeRepository.save(employee);
		
		if(positionChanged) {
			employee = getDefaultsFromPosition(employee);
		}
		
//		if(savedEmployee.isOnboardingComplete()) {
//			String recipients = "";
//			for(EmployeeJobFunctionNeed need : savedEmployee.getNeeds()) {
//				for(Person person : need.getNeed().getNoticeRecipients()) {
//					recipients += person.getEmail() + ",";
//				}
//			}
//			recipients = recipients.substring(0, recipients.length() - 1);	// remove trailing comma after loop
//			processNeedsCompleted(savedEmployee.getId(), savedEmployee.getFirstName() + " " + savedEmployee.getLastName(), recipients);
//		}
		return new ResponseEntity<Employee>(savedEmployee, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String id) throws ResourceNotFoundException {
		Employee deletedEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No employee found with id " + id) );
		employeeRepository.deleteById(id);
		
		return new ResponseEntity<Employee>(deletedEmployee, HttpStatus.OK);
	}
	
	private void processNeedsCompleted(String id, String name, String recipients) throws AddressException, MessagingException {
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
	
	private Employee getDefaultsFromPosition(Employee employee) {
		if(employee.getPay() == null || employee.getPay().compareTo(new BigDecimal("0")) == 0 ) {
			employee.setPay(employee.getPosition().getDefaultPay());
		}
		
		if(employee.getPayType() == null) {
			employee.setPayType(employee.getPosition().getDefaultPayType());
		}
		
		if(employee.getWorkFrequency() == null) {
			employee.setWorkFrequency(employee.getPosition().getDefaultWorkFrequency());
		}
		
		List<EmployeeJobFunctionNeed> newNeedList = new ArrayList<>();
		for(JobFunctionNeed need : employee.getPosition().getDefaultNeeds()) {
			EmployeeJobFunctionNeed employeeNeed = new EmployeeJobFunctionNeed(need, new TaskStatus(1));
			newNeedList.add(employeeNeed);
			System.out.println(employeeNeed);
		}
		System.out.println(newNeedList);
		employee.setNeeds(newNeedList);
		return employee;
	}
	
}
