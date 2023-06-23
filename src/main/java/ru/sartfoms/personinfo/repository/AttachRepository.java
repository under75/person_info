package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.Attach;
import ru.sartfoms.personinfo.entity.CompositeKey;

public interface AttachRepository extends JpaRepository<Attach, CompositeKey> {

	Collection<Attach> findAllByRid(Long rid);

}
