package ru.sartfoms.personinfo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.InsuranceStatusRes;

public interface InsuranceStatusResRepository extends JpaRepository<InsuranceStatusRes, Long> {

	Collection<InsuranceStatusRes> findAllByRid(Long rid);

}
