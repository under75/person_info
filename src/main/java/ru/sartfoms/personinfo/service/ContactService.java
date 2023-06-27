package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Contact;
import ru.sartfoms.personinfo.repository.ContactRepository;

@Service
public class ContactService {
	private final ContactRepository contactRepository;
	
	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	public Collection<Contact> findAllByRid(Long rid) {
		return contactRepository.findAllByRid(rid);
	}
}
