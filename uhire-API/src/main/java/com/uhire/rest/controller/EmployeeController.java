package com.uhire.rest.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import com.uhire.rest.model.JobPosition;
import com.uhire.rest.model.User;
import com.uhire.rest.model.lists.TaskStatus;
import com.uhire.rest.repository.EmployeeJobFunctionNeedRepository;
import com.uhire.rest.repository.EmployeeRepository;
import com.uhire.rest.repository.JobPositionRepository;
import com.uhire.rest.repository.PersonRepository;
import com.uhire.rest.repository.TaskStatusRepository;
import com.uhire.rest.service.Email;
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

	@Autowired
	private JobPositionRepository jobPositionRespository;
	
	@Autowired
	private EmployeeJobFunctionNeedRepository employeeJobFunctionNeedRepository;
	
	@Autowired
	private TaskStatusRepository taskStatusRepository;
	     
	@GetMapping(path = "/health-check")
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping
	public List<Employee> getEmployeesAll() {
		return employeeRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) throws ResourceNotFoundException {
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
	public ResponseEntity<Long> createEmployee(@Validated @RequestBody Employee employee, @RequestParam long userId) throws ResourceNotFoundException {
		personRepository.findById(userId).orElseThrow( () -> new ResourceNotFoundException("No one found with id " + userId) );
		employee.setId(null); // ensure mongo is creating id
		
		employee = getDefaultsFromPosition(employee, userId);
		
		Employee newEmp = employeeRepository.save(employee);
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
		//		.buildAndExpand(newEmp.getId()).toUri();
		return new ResponseEntity<Long>(newEmp.getId(), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Employee> updateEmployee(
			@PathVariable long id,
			@RequestParam(required = false) boolean positionChanged,
			@RequestParam long userId,
			@Validated @RequestBody Employee employee) throws ResourceNotFoundException, AddressException, MessagingException {
		personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No one found with id " + id) );
		personRepository.findById(userId).orElseThrow( () -> new ResourceNotFoundException("No one found with id " + userId) );
		int statusCompletedId = taskStatusRepository.findByName("COMPLETED").getId();
		
		if(positionChanged) {
			employee = getDefaultsFromPosition(employee, userId);
		}
		
		List<EmployeeJobFunctionNeed> needs = employeeJobFunctionNeedRepository.findByEmployeeId(employee.getId());
		Employee savedEmployee = employeeRepository.save(employee);
		
		if(!needs.isEmpty() && savedEmployee.isOnboardingComplete(needs, statusCompletedId)) {
			Email.processNeedsCompleted(needs, savedEmployee);
		}
		
		return new ResponseEntity<Employee>(savedEmployee, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable long id) throws ResourceNotFoundException {
		Employee deletedEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No employee found with id " + id) );
		employeeRepository.deleteById(id);
		
		return new ResponseEntity<Employee>(deletedEmployee, HttpStatus.OK);
	}
	
	//*********************************************************************************
	// TaskStatus ID 1 is hardcoded as the only value that allows delete operation to be applied,
	// as this is the only state in which the request hasn't been sent yet.
	// ********************************************************************************
	private Employee getDefaultsFromPosition(Employee employee, long userId) {
		JobPosition position = jobPositionRespository.getById(employee.getPosition().getId()); // the front end only deals with the id, so grab the full object
		if(employee.getPay() == null || employee.getPay().compareTo(new BigDecimal("0")) == 0 ) {
			employee.setPay(position.getDefaultPay()) ;
		}
		
		if(employee.getPayType() == null) {
			employee.setPayType(position.getDefaultPayType());
		}
		
		if(employee.getWorkFrequency() == null) {
			employee.setWorkFrequency(position.getDefaultWorkFrequency());
		}
		
		List<JobFunctionNeed> defaultNeeds = position.getDefaultNeeds();
		List<EmployeeJobFunctionNeed> newNeedList = populateEmployeeNeedsFromJobDefaults(employee, defaultNeeds, userId);
		
		employeeJobFunctionNeedRepository.saveAll(newNeedList);
		return employee;
	}
	
	// hard coded ID 1
	// this will only ever be called for new entries, so createUser and modifyUser will be the same person
	private List<EmployeeJobFunctionNeed> populateEmployeeNeedsFromJobDefaults(Employee employee, List<JobFunctionNeed> needs, String userId) {
		User user = new User(userId);
		Date date = new Date();
		List<EmployeeJobFunctionNeed> newNeedList = new ArrayList<>();
		for(JobFunctionNeed need : needs) {
			EmployeeJobFunctionNeed employeeNeed = new EmployeeJobFunctionNeed(employee, need, new TaskStatus(1), user, user, date, date);
			if(!employeeJobFunctionNeedRepository.findByNeedIdAndEmployeeId(need.getId(), employee.getId()).isPresent()) {
				newNeedList.add(employeeNeed);
			}
		}
		return newNeedList;
	}
	
}
