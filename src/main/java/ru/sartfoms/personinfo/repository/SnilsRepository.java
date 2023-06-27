package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.CompositeKey;
import ru.sartfoms.personinfo.entity.Snils;

public interface SnilsRepository extends JpaRepository<Snils, CompositeKey> {

	Collection<Snils> findAllByRid(Long rid);

}
