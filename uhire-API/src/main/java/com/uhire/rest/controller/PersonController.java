package com.uhire.rest.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mongodb.client.result.UpdateResult;
import com.uhire.rest.exception.ResourceNotFoundException;
import com.uhire.rest.model.Person;
import com.uhire.rest.repository.EmployeeRepository;
import com.uhire.rest.repository.PersonRepository;
import com.uhire.rest.service.InstanceInfoService;

@RestController
@RequestMapping(path = "/persons")
@CrossOrigin(origins= {"http://localhost:3003", "http://localhost:3001", "http://localhost:3000", "https://uhire.jmscottnovels.com"}, allowCredentials = "true")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private InstanceInfoService instanceInfoService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	     
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
		Person person = personRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No Person found with id: " + id)
		);
		
		return ResponseEntity.ok(person);
	}
	
	// TODO: more testing on isEmployee; CHANGE TO CONDITIONS, NOT JUST BOOLEAN, AS WE MIGHT NOT CARE IF THY ARE OR AREN'T; deploy to frontend
	@GetMapping(path = "/name/{name}")
	public List<Person> getPersonsByName(@PathVariable String name, @RequestParam(required = false) boolean isEmployee) {
		Collection<Person> persons = personRepository.findByFirstNameLikeIgnoreCase(name);
		Collection<Person> personsByLast = personRepository.findByLastNameLikeIgnoreCase(name);
		for(Person pbl : personsByLast) {
			if(!persons.stream().filter(p -> p.getId().equals(pbl.getId())).findFirst().isPresent()) {
				persons.add(pbl);
			}
		}
		if(!isEmployee) {
			persons.removeIf(p -> employeeRepository.getById(p.getId()).getStatus() != null );
		} else {
			persons.removeIf(p -> employeeRepository.getById(p.getId()).getStatus() == null );
		}
		return (List<Person>) persons;
	}
	
	// TODO: prevent duplicates
	@GetMapping(path = "/firstName/{firstName}/lastName/{lastName}")
	public List<Person> getPersonsByName(@PathVariable String firstName, @PathVariable String lastName, @RequestParam(required = false) boolean isEmployee) {
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
	public ResponseEntity<Void> createPerson(@Validated @RequestBody Person person) {
		person.setId(null); // ensure mongo is creating id
		
		Person newPerson = personRepository.save(person);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
				.buildAndExpand(newPerson.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// *******************************************************
	// MongoRepository.save() replaces instead of updating.
	// The following PUT method needs to be used on all involved super classes
	// in order to no overwrite fields that don't exist in the super class
	// TODO: verify this PUT
	// *******************************************************
	@PutMapping(path = "/{id}")
	public ResponseEntity<UpdateResult> updatePerson(
			@PathVariable String id,
			@Validated @RequestBody Person person) throws ResourceNotFoundException, AddressException, MessagingException {
		personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No person found with id " + id) );
		
		Query query = new Query().addCriteria(Criteria.where("_id").is(id));
		
		Update update = new Update()
		.set("firstName", person.getFirstName())
		.set("lastName", person.getLastName())
		.set("email", person.getEmail())
		.set("age", person.getAge());
		
		UpdateResult savedPerson = mongoTemplate.updateFirst(query, update, Person.class);
		
		return new ResponseEntity<UpdateResult>(savedPerson, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Person> deletePerson(@PathVariable String id) throws ResourceNotFoundException {
		Person deletedPerson = personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No person found with id " + id) );
		personRepository.deleteById(id);
		
		return new ResponseEntity<Person>(deletedPerson, HttpStatus.OK);
	}
	
}
