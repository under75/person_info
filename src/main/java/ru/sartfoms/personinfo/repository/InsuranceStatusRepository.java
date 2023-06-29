package ru.sartfoms.personinfo.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.InsuranceStatus;

public interface InsuranceStatusRepository extends JpaRepository<InsuranceStatus, Long> {

	Page<InsuranceStatus> findByUserAndDtInsBetweenOrderByDtInsDesc(String userName, LocalDateTime atStartOfDay,
			LocalDateTime atStartOfDay2, PageRequest pageRequest);

	Page<InsuranceStatus> findByUserAndDtInsAfterOrderByDtInsDesc(String userName, LocalDateTime atStartOfDay,
			PageRequest pageRequest);

	Page<InsuranceStatus> findByUserAndDtInsBeforeOrderByDtInsDesc(String userName, LocalDateTime atStartOfDay,
			PageRequest pageRequest);

	Page<InsuranceStatus> findByUserOrderByDtInsDesc(String userName, PageRequest pageRequest);

}
