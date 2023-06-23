package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.Person;

public interface PersonRepository extends JpaRepository<Person, CompositeKey> {

	Collection<Person> findAllByRid(Long rid);

}
