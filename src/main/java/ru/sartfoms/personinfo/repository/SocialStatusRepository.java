package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.SocialStatus;

public interface SocialStatusRepository extends JpaRepository<SocialStatus, CompositeKey> {

	Collection<SocialStatus> findAllByRid(Long rid);

}
