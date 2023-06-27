package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.Oksm;

public interface OksmRepository extends JpaRepository<Oksm, String> {

	Collection<Oksm> findByOrderByName1();

}
