package com.uhire.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uhire.rest.model.lists.EmployeeStatus;
import com.uhire.rest.model.lists.PayType;
import com.uhire.rest.model.lists.TaskStatus;
import com.uhire.rest.model.lists.WorkFrequency;
import com.uhire.rest.repository.EmployeeStatusRepository;
import com.uhire.rest.repository.PayTypeRepository;
import com.uhire.rest.repository.TaskStatusRepository;
import com.uhire.rest.repository.WorkFrequencyRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/lists")
@CrossOrigin(origins= {"http://localhost:3003", "http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class SimpleListsController {

	@Autowired
	private EmployeeStatusRepository employeeStatusRespository;
	
	@Autowired
	private PayTypeRepository payTypeRespository;
	
	@Autowired
	private TaskStatusRepository taskStatusRespository;
	
	@Autowired
	private WorkFrequencyRepository workFrequencyRespository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;
	     
	@GetMapping(path = "/health-check")
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping(path = "/employee-statuses")
	public List<EmployeeStatus> getEmployeeStatusesAll() {
		return employeeStatusRespository.findAll();
	}
	
	@GetMapping(path = "/pay-types")
	public List<PayType> getPayTypesAll() {
		return payTypeRespository.findAll();
	}
	
	@GetMapping(path = "/work-frequencies")
	public List<WorkFrequency> getWorkFrequenciesAll() {
		return workFrequencyRespository.findAll();
	}
	
	@GetMapping(path = "/task-statuses")
	public List<TaskStatus> getTaskStatusesAll() {
		return taskStatusRespository.findAll();
	}
	
//	@GetMapping(path = "/id/{id}")
//	public ResponseEntity<JobPosition> getJobPositionById(@PathVariable String id) throws ResourceNotFoundException {
//		JobPosition position = jobPositionRepository.findById(id).orElseThrow(
//			() -> new ResourceNotFoundException("No job found with id: " + id)
//		);
//		
//		return ResponseEntity.ok(position);
//	}
	
//	@PostMapping
//	public ResponseEntity<Void> createJobPosition(@Validated @RequestBody JobPosition position) {
//		position.setId(null); // ensure mongo is creating id
//		
//		JobPosition newPosition = jobPositionRepository.save(position);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
//				.buildAndExpand(newPosition.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//	}
//	
//	@PutMapping(path = "/{id}")
//	public ResponseEntity<JobPosition> updateJobPosition(
//			@PathVariable String id,
//			@Validated @RequestBody JobPosition position) throws ResourceNotFoundException {
//		jobPositionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job found with id " + id) );
//		JobPosition savedPosition = jobPositionRepository.save(position);
//		
//		return new ResponseEntity<JobPosition>(savedPosition, HttpStatus.OK);
//	}
//	
//	@DeleteMapping(path = "/{id}")
//	public ResponseEntity<JobPosition> deleteJobPosition(@PathVariable String id) throws ResourceNotFoundException {
//		JobPosition deletedPosition = jobPositionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job found with id " + id) );
//		jobPositionRepository.deleteById(id);
//		
//		return new ResponseEntity<JobPosition>(deletedPosition, HttpStatus.OK);
//	}
	
}
