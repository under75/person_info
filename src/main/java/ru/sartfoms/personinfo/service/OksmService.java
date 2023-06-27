package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Oksm;
import ru.sartfoms.personinfo.repository.OksmRepository;

@Service
public class OksmService {
	private final OksmRepository oksmRepository;
	
	public OksmService(OksmRepository oksmRepository) {
		this.oksmRepository = oksmRepository;
	}

	public Collection<Oksm> findAll() {
		return oksmRepository.findByOrderByName1();
	}
}
