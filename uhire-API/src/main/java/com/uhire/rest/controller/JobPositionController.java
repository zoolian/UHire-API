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
import com.uhire.rest.model.JobPosition;
import com.uhire.rest.repository.JobPositionRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/job-positions")
@CrossOrigin(origins= {"http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class JobPositionController {

	@Autowired
	private JobPositionRepository jobPositionRepository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;
	     
	@GetMapping
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping
	public List<JobPosition> getJobPositionsAll() {
		return jobPositionRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<JobPosition> getJobPositionById(@PathVariable String id) throws ResourceNotFoundException {
		JobPosition position = jobPositionRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No job found with id: " + id)
		);
		
		return ResponseEntity.ok(position);
	}
	
	@PostMapping
	public ResponseEntity<Void> createJobPosition(@Validated @RequestBody JobPosition position) {
		position.setId(null); // ensure mongo is creating id
		
		JobPosition newPosition = jobPositionRepository.save(position);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newPosition.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<JobPosition> updateJobPosition(
			@PathVariable String id,
			@Validated @RequestBody JobPosition position) throws ResourceNotFoundException {
		jobPositionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job found with id " + id) );
		JobPosition savedPosition = jobPositionRepository.save(position);
		
		return new ResponseEntity<JobPosition>(savedPosition, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<JobPosition> deleteJobPosition(@PathVariable String id) throws ResourceNotFoundException {
		JobPosition deletedPosition = jobPositionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No job found with id " + id) );
		jobPositionRepository.deleteById(id);
		
		return new ResponseEntity<JobPosition>(deletedPosition, HttpStatus.OK);
	}
	
}
