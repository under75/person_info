package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Snils;
import ru.sartfoms.personinfo.repository.SnilsRepository;

@Service
public class SnilsService {
	private final SnilsRepository snilsRepository;
	
	public SnilsService(SnilsRepository snilsRepository) {
		this.snilsRepository = snilsRepository;
	}

	public Collection<Snils> findAllByRid(Long rid) {
		return snilsRepository.findAllByRid(rid);
	}
}
