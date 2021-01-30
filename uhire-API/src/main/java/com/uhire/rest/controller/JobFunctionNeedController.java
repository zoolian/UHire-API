package com.uhire.rest.controller;

import java.net.URI;
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

import com.uhire.rest.exception.ResourceNotFoundException;
import com.uhire.rest.model.JobFunctionNeed;
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
	
}
