package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.InsuranceStatusRes;
import ru.sartfoms.personinfo.repository.InsuranceStatusResRepository;

@Service
public class InsuranceStatusResService {
	private final InsuranceStatusResRepository insuranceStatusResRepository;
	
	public InsuranceStatusResService(InsuranceStatusResRepository insuranceStatusResRepository) {
		this.insuranceStatusResRepository = insuranceStatusResRepository;
	}

	public Collection<InsuranceStatusRes> findAllByRid(Long rid) {
		return insuranceStatusResRepository.findAllByRid(rid);
	}
}
