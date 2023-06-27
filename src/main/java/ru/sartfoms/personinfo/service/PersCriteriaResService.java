package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.PersCriteriaRes;
import ru.sartfoms.personinfo.repository.PersCriteriaResRepository;

@Service
public class PersCriteriaResService {
	private final PersCriteriaResRepository persCriteriaResRepository;
	
	public PersCriteriaResService(PersCriteriaResRepository persCriteriaResRepository) {
		this.persCriteriaResRepository = persCriteriaResRepository;
	}

	public Collection<PersCriteriaRes> findAllByRid(Long rid) {
		return persCriteriaResRepository.findAllByRid(rid);
	}
}
