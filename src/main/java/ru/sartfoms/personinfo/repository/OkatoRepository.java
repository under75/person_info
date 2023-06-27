package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.Okato;

public interface OkatoRepository extends JpaRepository<Okato, String> {

	Collection<Okato> findAllByOrderByTitle();

}
