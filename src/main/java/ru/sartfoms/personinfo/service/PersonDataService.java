package ru.sartfoms.personinfo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.DudlType;
import ru.sartfoms.personinfo.repository.DudlTypeRepository;


@Service
public class PersonDataService {
	private final DudlTypeRepository dudlTypeRepository;
	
	public PersonDataService(DudlTypeRepository dudlTypeRepository) {
		this.dudlTypeRepository = dudlTypeRepository;
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
	
	public Collection<DudlType> getDudlTypes() {
		return dudlTypeRepository.findAllByOrderByDocName();
	}
}
