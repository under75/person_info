package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Attach;
import ru.sartfoms.personinfo.repository.AttachRepository;

@Service
public class AttachService {
	private final AttachRepository attachRepository;
	
	public AttachService(AttachRepository attachRepository) {
		this.attachRepository = attachRepository;
	}

	public Collection<Attach> findAllByRid(Long rid) {
		return attachRepository.findAllByRid(rid);
	}
}
