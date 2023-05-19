package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.DudlType;

public interface DudlTypeRepository extends JpaRepository<DudlType, String> {

	Collection<DudlType> findAllByOrderByDocName();

}
