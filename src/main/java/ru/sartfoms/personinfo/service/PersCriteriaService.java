package ru.sartfoms.personinfo.service;

import static ru.sartfoms.personinfo.util.DateValidator.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

	public void validate(@Valid PersCritParameters persCritSParam, BindingResult bindingResult) {
		String lastName = persCritSParam.getLastName().trim();
		String firstName = persCritSParam.getFirstName().trim();
		String patronymic = persCritSParam.getPatronymic().trim();
		final Long maxPeriod = 11L;
		if (!lastName.isEmpty() || !firstName.isEmpty() || !patronymic.isEmpty()) {
			final Integer minLength = 3;
			if (persCritSParam.getTerrOkato().isEmpty())
				bindingResult.rejectValue("terrOkato", null);
			if (!lastName.isEmpty() && lastName.length() < minLength)
				bindingResult.rejectValue("lastName", null);
			if (!firstName.isEmpty() && firstName.length() < minLength)
				bindingResult.rejectValue("firstName", null);
			if (!patronymic.isEmpty() && patronymic.length() < minLength)
				bindingResult.rejectValue("patronymic", null);
		} else if (!persCritSParam.getTerrOkato().isEmpty()) {
			bindingResult.addError(new ObjectError("globalError",
					"(часть Фамилии или/и часть Имени или/и часть Отчества) и ОКАТО - обязательное сочетание"));
		} else if (!persCritSParam.getBirthDayFrom().isEmpty()) {
			bindingResult.addError(new ObjectError("globalError",
					"(часть Фамилии или/и часть Имени или/и часть Отчества) и ДР (с... по...) - обязательное сочетание"));
		} else if (!persCritSParam.getOksm().isEmpty()) {
			bindingResult.addError(new ObjectError("globalError", "Гражданство и ОКАТО - обязательное сочетание"));
		} else if (!persCritSParam.getDeathDateFrom().isEmpty()) {
			bindingResult.addError(
					new ObjectError("globalError", "Дата смерти (с... по...) и ОКАТО  - обязательное сочетание"));
		} else if (persCritSParam.getPcyNum().isEmpty() && persCritSParam.getDudlNum().isEmpty()
				&& persCritSParam.getSnils().isEmpty()) {
			bindingResult.addError(new ObjectError("globalError", "Необходимо задать критерии поиска!"));
		}
		if (areDatesValid(persCritSParam, bindingResult)) {
			if (!persCritSParam.getBirthDayFrom().isEmpty() && !persCritSParam.getBirthDayTo().isEmpty()) {
				if (howManyMonthsBetweenTwoDates(persCritSParam.getBirthDayFrom(),
						persCritSParam.getBirthDayTo()) > maxPeriod)
					bindingResult.rejectValue("birthDayTo", null);
			}
			if (!persCritSParam.getDeathDateFrom().isEmpty() && !persCritSParam.getDeathDateTo().isEmpty()) {
				if (howManyMonthsBetweenTwoDates(persCritSParam.getDeathDateFrom(),
						persCritSParam.getDeathDateTo()) > maxPeriod)
					bindingResult.rejectValue("deathDateTo", null);
			}
		}
	}

	public long howManyMonthsBetweenTwoDates(String effectiveStr, String expirationStr) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		LocalDate effective = LocalDate.parse(effectiveStr, dateTimeFormatter);
		LocalDate expiration = LocalDate.parse(expirationStr, dateTimeFormatter);
		Period period = Period.between(effective, expiration);

		return period.toTotalMonths();
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
		critData.setDost(persCritSParam.getDost());
		critData.setOksm(persCritSParam.getOksm());
		critData.setNoCitizenship(persCritSParam.getNoCitizenship());
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
