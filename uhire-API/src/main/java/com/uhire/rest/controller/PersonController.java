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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
	     
	@GetMapping(path = "/health-check")
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("{healthy: true, instanceInfo: " + instanceInfoService.retrieveInstanceInfo() + "}");
	}
	
	// CONSIDER: override findAll to only return Person fields
	@GetMapping
	public List<Person> getPersonsAll() {
		return personRepository.findAll();
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable long id) throws ResourceNotFoundException {
		Person person = (Person) personRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("No Person found with id: " + id)
		);
		
		return ResponseEntity.ok(person);
	}
	
	// Handle single name entry
	@GetMapping(path = "/name/{name}")
	public List<Person> getPersonsByName(@PathVariable String name, @RequestParam(required = false) String isEmployee) {
		List<Person> persons = personRepository.findByFirstNameLikeIgnoreCase(name);
		List<Person> personsByLast = personRepository.findByLastNameLikeIgnoreCase(name);
		
		return combineAndFilterByIsEmployee(persons, personsByLast, isEmployee);
	}
	
	// TODO: prevent duplicates
	// Handle entry of two strings. Front end can deal with a comma. This method can handle first and last in either order.
	@GetMapping(path = "/firstName/{firstName}/lastName/{lastName}")
	public List<Person> getPersonsByName(@PathVariable String firstName, @PathVariable String lastName, @RequestParam(required = false) String isEmployee) {
		List<Person> persons = personRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(firstName, lastName);
		List<Person> personsByLast = personRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(lastName, firstName);
		
		return combineAndFilterByIsEmployee(persons, personsByLast, isEmployee);
	}	
	
	@PostMapping
	public ResponseEntity<Void> createPerson(@Validated @RequestBody Person person) {
		person.setId(0); // ensure mongo is creating id
		
		Person newPerson = personRepository.save(person);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}")
				.buildAndExpand(newPerson.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// *******************************************************
	// JpaRepository.save() replaces instead of updating.
	// The following PUT method needs to be used on all involved super classes
	// in order to not overwrite fields that don't exist in the super class
	// TODO: verify this PUT
	// *******************************************************
	@PutMapping(path = "/{id}")
	public ResponseEntity<Person> updatePerson(
			@PathVariable long id,
			@Validated @RequestBody Person person) throws ResourceNotFoundException {
		personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No person found with id " + id) );

		Person savedPerson = personRepository.save(person);
		
		return new ResponseEntity<>(savedPerson, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Person> deletePerson(@PathVariable long id) throws ResourceNotFoundException {
		Person deletedPerson = (Person) personRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No person found with id " + id) );
		personRepository.deleteById(id);
		
		return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
	}

	// Combline two lists for when we search names considering [first last] or [last, first]
	private List<Person> combineAndFilterByIsEmployee(List<Person> persons1, List<Person> persons2, String isEmployee) {
		for(Person pbl : persons2) {
			if(persons1.stream().noneMatch(p -> p.getId() == pbl.getId())) {
				persons1.add(pbl);
			}
		}
		
		switch(isEmployee) {
		case "E":
			persons1.removeIf(p -> employeeRepository.getById(p.getId()).getStatus() == null );
			break;
		case "NE":
			persons1.removeIf(p -> employeeRepository.getById(p.getId()).getStatus() != null );
			break;
		}
		return persons1;
	}
	
}
