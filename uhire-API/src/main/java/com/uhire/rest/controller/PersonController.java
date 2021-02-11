package com.uhire.rest.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uhire.rest.exception.ResourceNotFoundException;
import com.uhire.rest.model.Person;
import com.uhire.rest.repository.PersonRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/persons")
@CrossOrigin(origins= {"http://localhost:3003", "http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;
	     
	@GetMapping(path = "/health-check")
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	@GetMapping
	public List<Person> getPersonsAll() {
		return personRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) throws ResourceNotFoundException {
		Person emp = personRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No Person found with id: " + id)
		);
		
		return ResponseEntity.ok(emp);
	}
	
	// TODO: prevent duplicates
	@GetMapping(path = "/name/{name}")
	public List<Person> getPersonsByName(@PathVariable String name) {
		List<Person> persons = personRepository.findByFirstNameLikeIgnoreCase(name);
		List<Person> personsByLast = personRepository.findByLastNameLikeIgnoreCase(name);
		for(Person pbl : personsByLast) {
			if(!persons.stream().filter(p -> p.getId().equals(pbl.getId())).findFirst().isPresent()) {
				persons.add(pbl);
			}
		}
		return persons;
	}
	
	// TODO: prevent duplicates
	@GetMapping(path = "/firstName/{firstName}/lastName/{lastName}")
	public List<Person> getPersonsByName(@PathVariable String firstName, @PathVariable String lastName) {
		List<Person> persons = personRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(firstName, lastName);
		List<Person> personsByLast = personRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(lastName, firstName);
		for(Person pbl : personsByLast) {
			if(!persons.stream().filter(p -> p.getId().equals(pbl.getId())).findFirst().isPresent()) {
				persons.add(pbl);
			}
		}
		return persons;
	}	
	
	@PostMapping
	public ResponseEntity<Void> createPerson(@Validated @RequestBody Person emp) {
		emp.setId(null); // ensure mongo is creating id
		
		Person newPerson = personRepository.save(emp);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
				.buildAndExpand(newPerson.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Person> updatePerson(
			@PathVariable String id,
			@Validated @RequestBody Person emp) throws ResourceNotFoundException, AddressException, MessagingException {
		personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No person found with id " + id) );
		Person savedPerson = personRepository.save(emp);
		
		return new ResponseEntity<Person>(savedPerson, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Person> deletePerson(@PathVariable String id) throws ResourceNotFoundException {
		Person deletedPerson = personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No person found with id " + id) );
		personRepository.deleteById(id);
		
		return new ResponseEntity<Person>(deletedPerson, HttpStatus.OK);
	}
	
}
