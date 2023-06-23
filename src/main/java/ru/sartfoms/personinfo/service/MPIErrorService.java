package ru.sartfoms.personinfo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.MPIError;
import ru.sartfoms.personinfo.repository.MPIErrorRepository;

@Service
public class MPIErrorService {
	private final MPIErrorRepository mpiErrorRepository;
	
	public MPIErrorService(MPIErrorRepository mpiErrorRepository) {
		this.mpiErrorRepository = mpiErrorRepository;
	}

	public Collection<MPIError> findAllByRid(Long rid) {
		return mpiErrorRepository.findAllByRid(rid);
	}

}
