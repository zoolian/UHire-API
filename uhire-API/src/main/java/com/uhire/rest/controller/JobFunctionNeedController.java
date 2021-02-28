package com.uhire.rest.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

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

import com.uhire.rest.exception.ForbiddenException;
import com.uhire.rest.exception.ResourceNotFoundException;
import com.uhire.rest.model.EmployeeJobFunctionNeed;
import com.uhire.rest.model.JobFunctionNeed;
import com.uhire.rest.model.lists.TaskStatus;
import com.uhire.rest.repository.EmployeeJobFunctionNeedRepository;
import com.uhire.rest.repository.JobFunctionNeedRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/job-function-needs")
@CrossOrigin(origins = {"http://localhost:3003", "http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class JobFunctionNeedController {

	@Autowired
	private JobFunctionNeedRepository jobFunctionNeedRepository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;

	@Autowired
	private EmployeeJobFunctionNeedRepository employeeJobFunctionNeedRepository;
	     
	@GetMapping(path = "/health-check")
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping
	public List<JobFunctionNeed> getJobFunctionNeedsAll() {
		return jobFunctionNeedRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<JobFunctionNeed> getJobFunctionNeedById(@PathVariable String id) throws ResourceNotFoundException {
		JobFunctionNeed need = jobFunctionNeedRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No job need found with id: " + id)
		);
		
		return ResponseEntity.ok(need);
	}
	
	//****************************************************************************
	// POST and PUT calls both require an array list of type Person.
	// Fields to populate: id, firstName, lastName, email.
	// Reason: JobFunctionNeed object cannot contain a DBRef for Person list.
	//****************************************************************************
	@PostMapping
	public ResponseEntity<Void> createJobFunctionNeed(@Validated @RequestBody JobFunctionNeed need) {
		need.setId(null); // ensure mongo is creating id
		need.setEnabled(true);
		
		JobFunctionNeed newNeed = jobFunctionNeedRepository.save(need);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
				.buildAndExpand(newNeed.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<JobFunctionNeed> updateJobFunctionNeed(
			@PathVariable String id,
			@Validated @RequestBody JobFunctionNeed need) throws ResourceNotFoundException {
		jobFunctionNeedRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job need found with id " + id) );
		JobFunctionNeed savedNeed = jobFunctionNeedRepository.save(need);
		
		return new ResponseEntity<JobFunctionNeed>(savedNeed, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<JobFunctionNeed> deleteJobFunctionNeed(@PathVariable String id) throws ResourceNotFoundException {
		JobFunctionNeed deletedNeed = jobFunctionNeedRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job need found with id " + id) );
		jobFunctionNeedRepository.deleteById(id);
		
		return new ResponseEntity<JobFunctionNeed>(deletedNeed, HttpStatus.OK);
	}
	
	// **	This section is for employee need tasks	**
	
	@GetMapping(path = "/employee")
	public List<EmployeeJobFunctionNeed> getEmployeeNeedsAll() {
		return employeeJobFunctionNeedRepository.findAll();
	}
	
	@GetMapping(path = "/employee/id/{id}")
	public ResponseEntity<EmployeeJobFunctionNeed> getEmployeeNeedById(@PathVariable String id) throws ResourceNotFoundException {
		EmployeeJobFunctionNeed need = employeeJobFunctionNeedRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No job need found with id: " + id)
		);
		
		return ResponseEntity.ok(need);
	}
	
	@GetMapping(path = "/employee/employee/{id}")
	public List<EmployeeJobFunctionNeed> getEmployeeNeedsByEmployeeId(@PathVariable String id) {
		return employeeJobFunctionNeedRepository.findByEmployeeId(id);
	}
	
	@PostMapping(path = "/employee")
	public ResponseEntity<Void> createEmployeeNeed(@Validated @RequestBody EmployeeJobFunctionNeed need) {
		need.setId(null); // ensure mongo is creating id
		need.setStatus(new TaskStatus(1));
		
		EmployeeJobFunctionNeed newNeed = employeeJobFunctionNeedRepository.save(need);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/employee/id/{id}")
				.buildAndExpand(newNeed.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(path = "/employee/list")
	public ResponseEntity<Void> createEmployeeNeeds(@Validated @RequestBody List<EmployeeJobFunctionNeed> needs) {
		for(EmployeeJobFunctionNeed n : needs) {
			n.setId(null);
			n.setStatus(new TaskStatus(1));
			// prevent duplicates
			if(employeeJobFunctionNeedRepository.findByNeedIdAndEmployeeId(n.getNeed().getId(), n.getEmployee().getId()).isPresent()) {
				needs.remove(n);
			}
		}
		
		if(!needs.isEmpty()) {
			employeeJobFunctionNeedRepository.saveAll(needs);
		}
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(path = "/employee/{id}")
	public ResponseEntity<EmployeeJobFunctionNeed> updateEmployeeNeed(
			@PathVariable String id,
			@Validated @RequestBody EmployeeJobFunctionNeed need) throws ResourceNotFoundException {
		employeeJobFunctionNeedRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job need found with id " + id) );
		need.setModifyDate(new Date());	// back end setter to guarantee precision
		EmployeeJobFunctionNeed savedNeed = employeeJobFunctionNeedRepository.save(need);
		
		return new ResponseEntity<EmployeeJobFunctionNeed>(savedNeed, HttpStatus.OK);
	}
	
	//*********************************************************************************
	// TaskStatus ID 1 is hardcoded as the only value that allows delete operation to be applied,
	// as this is the only state in which the request hasn't been sent yet.
	// ********************************************************************************
	@DeleteMapping(path = "/employee/{id}")
	public ResponseEntity<EmployeeJobFunctionNeed> deleteEmployeeNeed(@PathVariable String id) throws ResourceNotFoundException, ForbiddenException {
		EmployeeJobFunctionNeed deletedNeed = employeeJobFunctionNeedRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job need found with id " + id) );
		if(deletedNeed.getStatus().getId() != 1) {
			throw new ForbiddenException("Task already being processed");
		}
		employeeJobFunctionNeedRepository.deleteById(id);
		
		return new ResponseEntity<EmployeeJobFunctionNeed>(deletedNeed, HttpStatus.OK);
	}
	
}
