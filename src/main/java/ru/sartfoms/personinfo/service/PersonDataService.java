package ru.sartfoms.personinfo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ru.sartfoms.personinfo.entity.DudlType;
import ru.sartfoms.personinfo.entity.PersonData;
import ru.sartfoms.personinfo.model.PersDataParameters;
import ru.sartfoms.personinfo.repository.DudlTypeRepository;
import ru.sartfoms.personinfo.repository.PersonDataRepository;
import ru.sartfoms.personinfo.util.DateValidator;

@Service
public class PersonDataService {
	private static final Integer PAGE_SIZE = 10;
	private final DudlTypeRepository dudlTypeRepository;
	private final PersonDataRepository personDataRepository;

	public PersonDataService(DudlTypeRepository dudlTypeRepository, PersonDataRepository personDataRepository) {
		this.dudlTypeRepository = dudlTypeRepository;
		this.personDataRepository = personDataRepository;
	}

	public final static Map<String, String> policyType = new HashMap<>();
	{
		policyType.put("С", "Полис ОМС старого образца");
		policyType.put("В", "Временное свидетельство в форме бумажного бланка");
		policyType.put("Е", "Временное свидетельство в форме электронного документа");
		policyType.put("П", "Бумажный полис ОМС единого образца");
		policyType.put("Э", "Электронный полис ОМС единого образца");
		policyType.put("К", "Полис ОМС в составе универсальной электронной карты");
		policyType.put("Ц", "Цифровой полис ОМС");
		policyType.put("Х", "Состояние на учёте без полиса ОМС");
		policyType.put("М", "Состояние на учёте МФЦ");
	}

	public enum Show {
		Person, OmsPolicy, Dudl, Address, Attach, Contact, Snils, SocialStatus, Ern, All
	}

	public final static Map<Show, String> resultType = new LinkedHashMap<>();
	{
		resultType.put(Show.Person, "Информация о персоне");
		resultType.put(Show.OmsPolicy, "Информация о полисе");
		resultType.put(Show.Dudl, "ДУДЛ");
		resultType.put(Show.Address, "Адрес");
		resultType.put(Show.Attach, "Прикрепление");
		resultType.put(Show.Contact, "Данные контакта");
		resultType.put(Show.Snils, "Данные СНИЛС");
		resultType.put(Show.SocialStatus, "Данные соц. статуса");
		resultType.put(Show.Ern, "Данные ЕРН");
	}

	public Collection<DudlType> getDudlTypes() {
		return dudlTypeRepository.findAllByOrderByDocName();
	}

	public Page<PersonData> getPersDataPage(PersDataParameters searchParams, String userName, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		Page<PersonData> dataPage;
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = personDataRepository.findByUserAndDtInsBetweenOrderByDtInsDesc(userName, start.atStartOfDay(), end.atStartOfDay(),
					pageRequest);
		} else if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			dataPage = personDataRepository.findByUserAndDtInsAfterOrderByDtInsDesc(userName, start.atStartOfDay(), pageRequest);
		} else if (searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = personDataRepository.findByUserAndDtInsBeforeOrderByDtInsDesc(userName, end.atStartOfDay(), pageRequest);
		} else {
			dataPage = personDataRepository.findByUserOrderByDtInsDesc(userName, pageRequest);
		}

		return dataPage;
	}

	public PersonData saveRequest(PersDataParameters searchParams, String userName) {

		PersonData personData = new PersonData();
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
		if (!searchParams.getFirstName().trim().isEmpty())
			personData.setFirstName(searchParams.getFirstName().trim());
		if (!searchParams.getLastName().trim().isEmpty())
			personData.setLastName(searchParams.getLastName().trim());
		if (!searchParams.getPatronymic().trim().isEmpty())
			personData.setPatronymic(searchParams.getPatronymic().trim());
		if (!searchParams.getSnils().trim().isEmpty())
			personData.setSnils(searchParams.getSnils().trim());
		if (!searchParams.getBirthDay().isEmpty())
			personData.setBirthDay(LocalDate.parse(searchParams.getBirthDay()));
		personData.setHistorical(searchParams.getHistorical() == true ? searchParams.getHistorical() : null);
		if (searchParams.getHistorical()) {
			if (!searchParams.getDtFrom().isEmpty())
				personData.setDtFrom(LocalDate.parse(searchParams.getDtFrom()));
			if (!searchParams.getDtTo().isEmpty())
				personData.setDtTo(LocalDate.parse(searchParams.getDtTo()).plusDays(1));
		} else if (!searchParams.getDt().isEmpty()) {
			personData.setDt(LocalDate.parse(searchParams.getDt()));
		}
		personData.setShow(searchParams.getShow() != null ? searchParams.getShow().replaceAll(",", " ") : null);
		personData.setUser(userName);
		personData.setDtIns(LocalDateTime.now());

		return personDataRepository.save(personData);
	}

	public void validate(PersDataParameters searchParams, BindingResult bindingResult) {
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
		if (searchParams.getDt() != null && !searchParams.getDt().isEmpty() && !DateValidator.isValid(searchParams.getDt())) {
			bindingResult.rejectValue("dt", "");
		}
		if (searchParams.getDtFrom() != null && !searchParams.getDtFrom().isEmpty()
				&& !DateValidator.isValid(searchParams.getDtFrom())) {
			bindingResult.rejectValue("dtFrom", "");
		}
		if (searchParams.getDtTo() != null && !searchParams.getDtTo().isEmpty()
				&& !DateValidator.isValid(searchParams.getDtTo())) {
			bindingResult.rejectValue("dtTo", "");
		}
		
	}

	public PersonData getPersonDataByRid(Long rid) {
		return personDataRepository.getReferenceById(rid);
	}
}
