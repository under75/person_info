package ru.sartfoms.personinfo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ru.sartfoms.personinfo.entity.InsuranceStatus;
import ru.sartfoms.personinfo.model.InsurStatusParameters;
import ru.sartfoms.personinfo.repository.InsuranceStatusRepository;
import ru.sartfoms.personinfo.util.DateValidator;

@Service
public class InsuranceStatusService {
	private static final Integer PAGE_SIZE = 10;
	private final InsuranceStatusRepository insuranceStatusRepository;

	public InsuranceStatusService(InsuranceStatusRepository insuranceStatusRepository) {
		this.insuranceStatusRepository = insuranceStatusRepository;
	}

	public Page<InsuranceStatus> getDataPage(InsurStatusParameters searchParams, String userName,
			Optional<Integer> page) {
		int currentPage = page.orElse(1);
		Page<InsuranceStatus> dataPage;
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = insuranceStatusRepository.findByUserAndDtInsBetweenOrderByDtInsDesc(userName,
					start.atStartOfDay(), end.atStartOfDay(), pageRequest);
		} else if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			dataPage = insuranceStatusRepository.findByUserAndDtInsAfterOrderByDtInsDesc(userName, start.atStartOfDay(),
					pageRequest);
		} else if (searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = insuranceStatusRepository.findByUserAndDtInsBeforeOrderByDtInsDesc(userName, end.atStartOfDay(),
					pageRequest);
		} else {
			dataPage = insuranceStatusRepository.findByUserOrderByDtInsDesc(userName, pageRequest);
		}

		return dataPage;
	}

	public void validate(InsurStatusParameters searchParams, BindingResult bindingResult) {
		if (searchParams.getOip().trim().isEmpty() && searchParams.getPolicyNum().trim().isEmpty()
				&& searchParams.getDudlNum().trim().isEmpty() && searchParams.getSnils().trim().isEmpty()) {
			bindingResult.addError(new ObjectError("globalError",
					"Для успешного поиска, необходимо ввести обезличенный идентификатор персоны(ОИП) либо данные одного из документов(Полис, ДУдЛ, СНИЛС)"));
		} else if (searchParams.getDudlType() != null && searchParams.getDudlNum().trim().isEmpty()) {
			bindingResult.rejectValue("dudlNum", "");
		} else if (!searchParams.getDudlNum().trim().isEmpty() && searchParams.getDudlType() == null) {
			bindingResult.rejectValue("dudlType", "");
		}
		if (searchParams.getBirthDay() != null && !searchParams.getBirthDay().isEmpty()
				&& !DateValidator.isValid(searchParams.getBirthDay())) {
			bindingResult.rejectValue("birthDay", "");
		}
		if (searchParams.getDt() != null && !searchParams.getDt().isEmpty()
				&& !DateValidator.isValid(searchParams.getDt())) {
			bindingResult.rejectValue("dt", "");
		}
		if (searchParams.getDudlEffDate() != null && !searchParams.getDudlEffDate().isEmpty()
				&& !DateValidator.isValid(searchParams.getDudlEffDate())) {
			bindingResult.rejectValue("dudlEffDate", null);
		}
	}

	public void saveRequest(InsurStatusParameters searchParams, String userName) {
		InsuranceStatus personData = new InsuranceStatus();
		if (!searchParams.getOip().trim().isEmpty())
			personData.setOip(searchParams.getOip().trim());
		if (!searchParams.getPolicyType().isEmpty())
			personData.setPcyType(searchParams.getPolicyType());
		if (!searchParams.getPolicySer().trim().isEmpty())
			personData.setPcySer(searchParams.getPolicySer().trim());
		if (!searchParams.getPolicyNum().trim().isEmpty())
			personData.setPcy(searchParams.getPolicyNum().trim());
		if (searchParams.getDudlType() != null)
			personData.setDudlType(searchParams.getDudlType());
		if (!searchParams.getDudlSer().trim().isEmpty())
			personData.setDudlSer(searchParams.getDudlSer().trim());
		if (!searchParams.getDudlNum().trim().isEmpty())
			personData.setDudlNum(searchParams.getDudlNum().trim());
		if (!searchParams.getDudlEffDate().isEmpty())
			personData.setDudlEffDate(LocalDate.parse(searchParams.getDudlEffDate()));
		if (!searchParams.getSnils().trim().isEmpty())
			personData.setSnils(searchParams.getSnils().trim());
		if (!searchParams.getBirthDay().isEmpty())
			personData.setBirthDay(LocalDate.parse(searchParams.getBirthDay()));
		if (!searchParams.getDt().isEmpty()) {
			personData.setDt(LocalDate.parse(searchParams.getDt()));
		}
		personData.setUser(userName);
		personData.setDtIns(LocalDateTime.now());

		insuranceStatusRepository.save(personData);
	}
}
