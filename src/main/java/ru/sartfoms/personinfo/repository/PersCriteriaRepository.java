package ru.sartfoms.personinfo.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.personinfo.entity.PersCriteria;

public interface PersCriteriaRepository extends JpaRepository<PersCriteria, Long> {

	Page<PersCriteria> findByUserAndDtInsBetweenOrderByDtInsDesc(String userName, LocalDateTime start,
			LocalDateTime end, PageRequest pageRequest);

	Page<PersCriteria> findByUserAndDtInsAfterOrderByDtInsDesc(String userName, LocalDateTime start,
			PageRequest pageRequest);

	Page<PersCriteria> findByUserAndDtInsBeforeOrderByDtInsDesc(String userName, LocalDateTime end,
			PageRequest pageRequest);

	Page<PersCriteria> findByUserOrderByDtInsDesc(String userName, PageRequest pageRequest);

}
