package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Policy;
import ru.sartfoms.personinfo.repository.PolicyRepository;

@Service
public class PolicyService {
	private final PolicyRepository policyRepository;
	
	public PolicyService(PolicyRepository policyRepository) {
		this.policyRepository = policyRepository;
	}

	public Collection<Policy> findAllByRid(Long rid) {
		return policyRepository.findAllByRid(rid);
	}

}
