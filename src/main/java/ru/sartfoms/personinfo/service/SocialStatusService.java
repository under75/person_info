package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.SocialStatus;
import ru.sartfoms.personinfo.repository.SocialStatusRepository;

@Service
public class SocialStatusService {
	private final SocialStatusRepository socialStatusRepository;
	
	public SocialStatusService(SocialStatusRepository socialStatusRepository) {
		this.socialStatusRepository = socialStatusRepository;
	}

	public Collection<SocialStatus> findAllByRid(Long rid) {
		return socialStatusRepository.findAllByRid(rid);
	}

}
