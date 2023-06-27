package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, CompositeKey> {

	Collection<Contact> findAllByRid(Long rid);

}
