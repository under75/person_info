package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Person;
import ru.sartfoms.personinfo.repository.PersonRepository;

@Service
public class PersonService {
	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Collection<Person> findAllByRid(Long rid) {
		return personRepository.findAllByRid(rid);
	}

}
