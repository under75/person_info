package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.DudlType;
import ru.sartfoms.personinfo.repository.DudlTypeRepository;

@Service
public class DudlTypeService {
	private final DudlTypeRepository dudlTypeRepository;
	
	public DudlTypeService(DudlTypeRepository dudlTypeRepository) {
		this.dudlTypeRepository = dudlTypeRepository;
	}

	public Collection<DudlType> findAll() {
		return dudlTypeRepository.findAll();
	}
}
