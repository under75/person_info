package ru.sartfoms.personinfo.service;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.repository.OkatoRepository;

@Service
public class OkatoService {
	private final OkatoRepository okatoRepository;

	public OkatoService(OkatoRepository okatoRepository) {
		this.okatoRepository = okatoRepository;
	}

	public Object findAll() {
		return okatoRepository.findAllByOrderByTitle();
	}
}
