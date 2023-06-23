package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.Dudl;

public interface DudlRepository extends JpaRepository<Dudl, CompositeKey> {

	Collection<Dudl> findAllByRid(Long rid);

}
