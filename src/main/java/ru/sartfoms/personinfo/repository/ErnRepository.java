package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.Ern;

public interface ErnRepository extends JpaRepository<Ern, CompositeKey> {

	Collection<Ern> findAllByRid(Long rid);

}
