package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.MPIError;

public interface MPIErrorRepository extends JpaRepository<MPIError, CompositeKey> {

	Collection<MPIError> findAllByRid(Long rid);

}
