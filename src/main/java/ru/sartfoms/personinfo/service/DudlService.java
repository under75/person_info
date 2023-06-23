package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Dudl;
import ru.sartfoms.personinfo.repository.DudlRepository;

@Service
public class DudlService {
	private final DudlRepository dudlRepository;

	public DudlService(DudlRepository dudlRepository) {
		this.dudlRepository = dudlRepository;
	}

	public Collection<Dudl> findAllByRid(Long rid) {
		return dudlRepository.findAllByRid(rid);
	}

}
