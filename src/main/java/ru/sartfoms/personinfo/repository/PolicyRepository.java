package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, CompositeKey> {

	Collection<Policy> findAllByRid(Long rid);

}
