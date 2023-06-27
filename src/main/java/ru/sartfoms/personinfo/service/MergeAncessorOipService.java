package ru.sartfoms.personinfo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.MergeAncessorOip;
import ru.sartfoms.personinfo.model.AncessorOipParameters;
import ru.sartfoms.personinfo.repository.MergeAncessorOipRepository;
import ru.sartfoms.personinfo.util.DateValidator;

@Service
public class MergeAncessorOipService {
	private static final Integer PAGE_SIZE = 10;
	private final MergeAncessorOipRepository mergeAncessorOipRepository;

	public MergeAncessorOipService(MergeAncessorOipRepository mergeAncessorOipRepository) {
		this.mergeAncessorOipRepository = mergeAncessorOipRepository;
	}

	public Page<MergeAncessorOip> getDataPage(AncessorOipParameters searchParams, String userName,
			Optional<Integer> page) {
		int currentPage = page.orElse(1);
		Page<MergeAncessorOip> dataPage;
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = mergeAncessorOipRepository.findByUserAndDtInsBetweenOrderByDtInsDesc(userName,
					start.atStartOfDay(), end.atStartOfDay(), pageRequest);
		} else if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			dataPage = mergeAncessorOipRepository.findByUserAndDtInsAfterOrderByDtInsDesc(userName,
					start.atStartOfDay(), pageRequest);
		} else if (searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = mergeAncessorOipRepository.findByUserAndDtInsBeforeOrderByDtInsDesc(userName, end.atStartOfDay(),
					pageRequest);
		} else {
			dataPage = mergeAncessorOipRepository.findByUserOrderByDtInsDesc(userName, pageRequest);
		}

		return dataPage;
	}

	public void saveRequest(AncessorOipParameters searchParams, String userName) {
		MergeAncessorOip entity = new MergeAncessorOip();
		entity.setOip(searchParams.getOip());
		entity.setDtIns(LocalDateTime.now());
		entity.setUser(userName);

		mergeAncessorOipRepository.save(entity);
	}
}
