package ru.sartfoms.personinfo.service;

import static ru.sartfoms.personinfo.util.DateValidator.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ru.sartfoms.personinfo.entity.PersCriteria;
import ru.sartfoms.personinfo.model.PersCritParameters;
import ru.sartfoms.personinfo.repository.PersCriteriaRepository;
import ru.sartfoms.personinfo.util.DateValidator;

@Service
public class PersCriteriaService {
	private static final Integer PAGE_SIZE = 10;
	private final PersCriteriaRepository persCriteriaRepository;

	public PersCriteriaService(PersCriteriaRepository persCriteriaRepository) {
		this.persCriteriaRepository = persCriteriaRepository;
	}

	public Page<PersCriteria> getDataPage(PersCritParameters persCritSParam, String userName, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		Page<PersCriteria> dataPage;
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		if (persCritSParam.getDateFrom() != null && !persCritSParam.getDateFrom().isEmpty()
				&& persCritSParam.getDateTo() != null && !persCritSParam.getDateTo().isEmpty()
				&& DateValidator.isValid(persCritSParam.getDateFrom())
				&& DateValidator.isValid(persCritSParam.getDateTo())) {
			LocalDate start = LocalDate.parse(persCritSParam.getDateFrom());
			LocalDate end = LocalDate.parse(persCritSParam.getDateTo()).plusDays(1);
			dataPage = persCriteriaRepository.findByUserAndDtInsBetweenOrderByDtInsDesc(userName, start.atStartOfDay(),
					end.atStartOfDay(), pageRequest);
		} else if (persCritSParam.getDateFrom() != null && !persCritSParam.getDateFrom().isEmpty()
				&& DateValidator.isValid(persCritSParam.getDateFrom())) {
			LocalDate start = LocalDate.parse(persCritSParam.getDateFrom());
			dataPage = persCriteriaRepository.findByUserAndDtInsAfterOrderByDtInsDesc(userName, start.atStartOfDay(),
					pageRequest);
		} else if (persCritSParam.getDateTo() != null && !persCritSParam.getDateTo().isEmpty()
				&& DateValidator.isValid(persCritSParam.getDateTo())) {
			LocalDate end = LocalDate.parse(persCritSParam.getDateTo()).plusDays(1);
			dataPage = persCriteriaRepository.findByUserAndDtInsBeforeOrderByDtInsDesc(userName, end.atStartOfDay(),
					pageRequest);
		} else {
			dataPage = persCriteriaRepository.findByUserOrderByDtInsDesc(userName, pageRequest);
		}

		return dataPage;
	}

	public void validate(@Valid PersCritParameters searchParams, BindingResult bindingResult) {
		String lastName = searchParams.getLastName().trim();
		String firstName = searchParams.getFirstName().trim();
		String patronymic = searchParams.getPatronymic().trim();
		if (searchParams.getPcyNum().isEmpty() && searchParams.getDudlNum().isEmpty()
				&& searchParams.getSnils().isEmpty() && lastName.isEmpty() && firstName.isEmpty()
				&& patronymic.isEmpty() && searchParams.getErn().trim().isEmpty()) {
			bindingResult.addError(new ObjectError("globalError", "Должна быть указана минимум одна структура: - ФИО - Полис - ДУдЛ - СНИЛС - ЕРН"));
			return;
		}
		if (areDatesValid(searchParams, bindingResult)) {
		}
		if (searchParams.getDudlType() != null && searchParams.getDudlNum().trim().isEmpty()) {
			bindingResult.rejectValue("dudlNum", "");
		} else if (!searchParams.getDudlNum().trim().isEmpty() && searchParams.getDudlType() == null) {
			bindingResult.rejectValue("dudlType", "");
		}
		if (!searchParams.getPolicyType().isEmpty() && searchParams.getPcyNum().trim().isEmpty()) {
			bindingResult.rejectValue("pcyNum", "");
		}
		if (!searchParams.getOksm().isEmpty() && searchParams.getTerrOkato().isEmpty()) {
			bindingResult.rejectValue("terrOkato", "");
		}
	}

	private boolean areDatesValid(@Valid PersCritParameters persCritSParam, BindingResult bindingResult) {
		boolean res = true;
		if (!persCritSParam.getBirthDayFrom().isEmpty() && !DateValidator.isValid(persCritSParam.getBirthDayFrom())) {
			bindingResult.rejectValue("birthDayFrom", null);
			res = false;
		} else if (!persCritSParam.getBirthDayTo().isEmpty()
				&& !DateValidator.isValid(persCritSParam.getBirthDayTo())) {
			bindingResult.rejectValue("birthDayTo", null);
			res = false;
		} else if (!persCritSParam.getDeathDateFrom().isEmpty()
				&& !DateValidator.isValid(persCritSParam.getDeathDateFrom())) {
			bindingResult.rejectValue("deathDateFrom", null);
			res = false;
		} else if (!persCritSParam.getDeathDateTo().isEmpty()
				&& !DateValidator.isValid(persCritSParam.getDeathDateTo())) {
			bindingResult.rejectValue("deathDateTo", null);
			res = false;
		} else if (!persCritSParam.getBirthDay().isEmpty() && !DateValidator.isValid(persCritSParam.getBirthDay())) {
			bindingResult.rejectValue("birthDay", null);
			res = false;
		} else if (!persCritSParam.getDt().isEmpty() && !DateValidator.isValid(persCritSParam.getDt())) {
			bindingResult.rejectValue("dt", null);
			res = false;
		} else if (!persCritSParam.getDudlEffDate().isEmpty()
				&& !DateValidator.isValid(persCritSParam.getDudlEffDate())) {
			bindingResult.rejectValue("dudlEffDate", null);
			res = false;
		}

		return res;
	}

	public void saveRequest(@Valid PersCritParameters persCritSParam, String userName) {
		PersCriteria critData = new PersCriteria();
		critData.setDtIns(LocalDateTime.now());
		critData.setUser(userName);
		critData.setTerrOkato(persCritSParam.getTerrOkato());
		critData.setLastName(persCritSParam.getLastName().trim());
		critData.setFirstName(persCritSParam.getFirstName().trim());
		critData.setPatronymic(persCritSParam.getPatronymic().trim());
		critData.setOldsfp(persCritSParam.getOldsfp());
		StringBuilder dostBuilder = new StringBuilder();
		if (critData.getFirstName().isEmpty())
			dostBuilder.append("3");
		if (critData.getLastName().isEmpty())
			dostBuilder.append("2");
		if (critData.getPatronymic().isEmpty())
			dostBuilder.append("1");
		critData.setDost(dostBuilder.length() > 0 ? dostBuilder.toString() : null);
		if (persCritSParam.getOksm().isEmpty()) {
			critData.setNoCitizenship(persCritSParam.getNoCitizenship());
		} else {
			critData.setOksm(persCritSParam.getOksm());
		}
		critData.setBirthDayFrom(!persCritSParam.getBirthDayFrom().isEmpty()
				? LocalDate.parse(persCritSParam.getBirthDayFrom(), DATE_TIME_FORMATTER)
				: null);
		critData.setBirthDayTo(!persCritSParam.getBirthDayTo().isEmpty()
				? LocalDate.parse(persCritSParam.getBirthDayTo(), DATE_TIME_FORMATTER)
				: null);
		critData.setDeathDateFrom(!persCritSParam.getDeathDateFrom().isEmpty()
				? LocalDate.parse(persCritSParam.getDeathDateFrom(), DATE_TIME_FORMATTER)
				: null);
		critData.setDeathDateTo(!persCritSParam.getDeathDateTo().isEmpty()
				? LocalDate.parse(persCritSParam.getDeathDateTo(), DATE_TIME_FORMATTER)
				: null);
		critData.setOip(persCritSParam.getOip().trim());
		critData.setPcyType(persCritSParam.getPolicyType());
		critData.setPcySer(persCritSParam.getPcySer().trim());
		critData.setPcyNum(persCritSParam.getPcyNum().trim());
		critData.setDudlType(persCritSParam.getDudlType());
		critData.setDudlSer(persCritSParam.getDudlSer().trim());
		critData.setDudlNum(persCritSParam.getDudlNum().trim());
		critData.setDudlEffDate(!persCritSParam.getDudlEffDate().isEmpty()
				? LocalDate.parse(persCritSParam.getDudlEffDate(), DATE_TIME_FORMATTER)
				: null);
		critData.setSnils(persCritSParam.getSnils().trim());
		critData.setBirthDay(!persCritSParam.getBirthDay().isEmpty()
				? LocalDate.parse(persCritSParam.getBirthDay(), DATE_TIME_FORMATTER)
				: null);
		critData.setErn(persCritSParam.getErn().trim());
		critData.setDt(!persCritSParam.getDt().isEmpty() ? LocalDate.parse(persCritSParam.getDt(), DATE_TIME_FORMATTER)
				: null);

		persCriteriaRepository.save(critData);
	}
}
