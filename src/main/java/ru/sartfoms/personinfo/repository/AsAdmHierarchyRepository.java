package ru.sartfoms.personinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.AsAdmHierarchy;

public interface AsAdmHierarchyRepository extends JpaRepository<AsAdmHierarchy, Long> {

	AsAdmHierarchy findByObjectidAndIsActive(Long objectid, boolean b);

}
