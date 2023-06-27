package ru.sartfoms.personinfo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.sartfoms.personinfo.entity.Dudl;
import ru.sartfoms.personinfo.entity.Person;
import ru.sartfoms.personinfo.entity.PersonData;
import ru.sartfoms.personinfo.entity.Policy;
import ru.sartfoms.personinfo.entity.Snils;
import ru.sartfoms.personinfo.exception.ExcelGeneratorException;
import ru.sartfoms.personinfo.model.PersRowData;
import ru.sartfoms.personinfo.repository.DudlRepository;
import ru.sartfoms.personinfo.repository.PersonDataRepository;
import ru.sartfoms.personinfo.repository.PersonRepository;
import ru.sartfoms.personinfo.repository.PolicyRepository;
import ru.sartfoms.personinfo.repository.SnilsRepository;
import ru.sartfoms.personinfo.util.PersExcelGenerator;

@Service
public class ExcelService {
	private final PersonDataRepository personDataRepository;
	private final PolicyRepository policyRepository;
	private final DudlRepository dudlRepository;
	private final SnilsRepository snilsRepository;
	private final PersonRepository personRepository;

	public ExcelService(PersonDataRepository personDataRepository, PolicyRepository policyRepository,
			DudlRepository dudlRepository, SnilsRepository snilsRepository, PersonRepository personRepository) {
		this.personDataRepository = personDataRepository;
		this.policyRepository = policyRepository;
		this.dudlRepository = dudlRepository;
		this.snilsRepository = snilsRepository;
		this.personRepository = personRepository;
	}

	public InputStream createExcel(Collection<Long> selectedRows) throws IOException, ExcelGeneratorException {
		Collection<PersRowData> all = new ArrayList<>();
		for (Long rid : selectedRows) {
			all.addAll(getDataForExcel(rid));
		}

		return new PersExcelGenerator(all).toExcel();
	}

	private Collection<PersRowData> getDataForExcel(Long rid) {
		Collection<PersRowData> result = new HashSet<>();

		PersonData persData = personDataRepository.getReferenceById(rid);
		Collection<Policy> policies = policyRepository.findAllByRid(rid);
		Collection<Dudl> dudls = dudlRepository.findAllByRid(rid);
		Collection<Snils> snilses = snilsRepository.findAllByRid(rid);
		Person person = personRepository.findAllByRid(rid).stream().findAny().get();

		if (person == null)
			return result;

		PersRowData row;
		if (snilses.size() == 0) {
			if (dudls.size() > 0) {
				for (Dudl dudl : dudls) {
					if (policies.size() > 0) {
						for (Policy pol : policies) {
							row = new PersRowData();
							row.setOip(persData.getOipRes());
							row.setEnp(pol.getEnp());
							row.setPcyEffDt(pol.getPcyDateB());
							row.setPcyType(pol.getPcyType());
							row.setPcyStatus(pol.getPcyStatus());
							row.setDsourceType(pol.getDsourceType());
							row.setGender(pol.getGender() == 1 ? "М" : "Ж");
							row.setLastName(pol.getLastName());
							row.setFirstName(pol.getFirstName());
							row.setPatronymic(pol.getPatronymic());
							row.setBirthDay(pol.getBirthDay());
							row.setBlankNum(pol.getBlankNum());
							row.setDudlNum(dudl.getDudlNum());
							row.setDudlSer(dudl.getDudlSer());
							row.setDudlDateB(dudl.getDudlDateB());
							row.setDudlDateE(dudl.getDudlDateE());
							row.setDudlStatus(dudl.getDudlStatus());
							row.setPcyExpDt(pol.getPcyDateE());

							result.add(row);
						}
					} else {
						row = new PersRowData();
						row.setOip(persData.getOipRes());
						row.setGender(person.getGender() == 1 ? "М" : "Ж");
						row.setLastName(person.getLastName());
						row.setFirstName(person.getFirstName());
						row.setPatronymic(person.getPatronymic());
						row.setBirthDay(person.getBirthDay());
						row.setDudlNum(dudl.getDudlNum());
						row.setDudlSer(dudl.getDudlSer());
						row.setDudlDateB(dudl.getDudlDateB());
						row.setDudlDateE(dudl.getDudlDateE());
						row.setDudlStatus(dudl.getDudlStatus());

						result.add(row);
					}
				}
			} else {
				if (policies.size() > 0) {
					for (Policy pol : policies) {
						row = new PersRowData();
						row.setOip(persData.getOipRes());
						row.setEnp(pol.getEnp());
						row.setPcyEffDt(pol.getPcyDateB());
						row.setPcyType(pol.getPcyType());
						row.setPcyStatus(pol.getPcyStatus());
						row.setDsourceType(pol.getDsourceType());
						row.setGender(pol.getGender() == 1 ? "М" : "Ж");
						row.setLastName(pol.getLastName());
						row.setFirstName(pol.getFirstName());
						row.setPatronymic(pol.getPatronymic());
						row.setBirthDay(pol.getBirthDay());
						row.setBlankNum(pol.getBlankNum());
						row.setPcyExpDt(pol.getPcyDateE());

						result.add(row);
					}
				} else {
					row = new PersRowData();
					row.setOip(persData.getOipRes());
					row.setGender(person.getGender() == 1 ? "М" : "Ж");
					row.setLastName(person.getLastName());
					row.setFirstName(person.getFirstName());
					row.setPatronymic(person.getPatronymic());
					row.setBirthDay(person.getBirthDay());

					result.add(row);
				}
			}
		} else {
			for (Snils snils : snilses) {
				if (dudls.size() > 0) {
					for (Dudl dudl : dudls) {
						if (policies.size() > 0) {
							for (Policy pol : policies) {
								row = new PersRowData();
								row.setOip(persData.getOipRes());
								row.setEnp(pol.getEnp());
								row.setPcyEffDt(pol.getPcyDateB());
								row.setPcyType(pol.getPcyType());
								row.setPcyStatus(pol.getPcyStatus());
								row.setDsourceType(pol.getDsourceType());
								row.setGender(pol.getGender() == 1 ? "М" : "Ж");
								row.setLastName(pol.getLastName());
								row.setFirstName(pol.getFirstName());
								row.setPatronymic(pol.getPatronymic());
								row.setBirthDay(pol.getBirthDay());
								row.setBlankNum(pol.getBlankNum());
								row.setDudlNum(dudl.getDudlNum());
								row.setDudlSer(dudl.getDudlSer());
								row.setDudlDateB(dudl.getDudlDateB());
								row.setDudlDateE(dudl.getDudlDateE());
								row.setDudlStatus(dudl.getDudlStatus());
								row.setSnils(snils.getSnils());
								row.setPcyExpDt(pol.getPcyDateE());

								result.add(row);
							}
						} else {
							row = new PersRowData();
							row.setOip(persData.getOipRes());
							row.setGender(person.getGender() == 1 ? "М" : "Ж");
							row.setLastName(person.getLastName());
							row.setFirstName(person.getFirstName());
							row.setPatronymic(person.getPatronymic());
							row.setBirthDay(person.getBirthDay());
							row.setDudlNum(dudl.getDudlNum());
							row.setDudlSer(dudl.getDudlSer());
							row.setDudlDateB(dudl.getDudlDateB());
							row.setDudlDateE(dudl.getDudlDateE());
							row.setDudlStatus(dudl.getDudlStatus());
							row.setSnils(snils.getSnils());

							result.add(row);
						}
					}
				} else {
					if (policies.size() > 0) {
						for (Policy pol : policies) {
							row = new PersRowData();
							row.setOip(persData.getOipRes());
							row.setEnp(pol.getEnp());
							row.setPcyEffDt(pol.getPcyDateB());
							row.setPcyType(pol.getPcyType());
							row.setPcyStatus(pol.getPcyStatus());
							row.setDsourceType(pol.getDsourceType());
							row.setGender(pol.getGender() == 1 ? "М" : "Ж");
							row.setLastName(pol.getLastName());
							row.setFirstName(pol.getFirstName());
							row.setPatronymic(pol.getPatronymic());
							row.setBirthDay(pol.getBirthDay());
							row.setBlankNum(pol.getBlankNum());
							row.setSnils(snils.getSnils());
							row.setPcyExpDt(pol.getPcyDateE());

							result.add(row);
						}
					} else {
						row = new PersRowData();
						row.setOip(persData.getOipRes());
						row.setGender(person.getGender() == 1 ? "М" : "Ж");
						row.setLastName(person.getLastName());
						row.setFirstName(person.getFirstName());
						row.setPatronymic(person.getPatronymic());
						row.setBirthDay(person.getBirthDay());
						row.setSnils(snils.getSnils());

						result.add(row);
					}
				}
			}
		}
		try {
			return result.stream()
					.sorted(Comparator.comparing(PersRowData::getPcyEffDt).thenComparing(PersRowData::getDudlDateB))
					.collect(Collectors.toList());
		} catch (NullPointerException e) {
			return result;
		}
	}

	public InputStream createExcel(Long rid) throws IOException, ExcelGeneratorException {
		return new PersExcelGenerator(getDataForExcel(rid)).toExcel();
	}

}
