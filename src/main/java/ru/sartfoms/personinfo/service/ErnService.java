package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Ern;
import ru.sartfoms.personinfo.repository.ErnRepository;

@Service
public class ErnService {
	private final ErnRepository ernRepository;

	public ErnService(ErnRepository ernRepository) {
		this.ernRepository = ernRepository;
	}

	public Collection<Ern> findAllByRid(Long rid) {
		return ernRepository.findAllByRid(rid);
	}
}
