package ru.sartfoms.personinfo.controller;

import static ru.sartfoms.personinfo.service.PersonDataService.policyType;
import static ru.sartfoms.personinfo.service.PersonDataService.resultType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.sartfoms.personinfo.entity.Address;
import ru.sartfoms.personinfo.entity.Dudl;
import ru.sartfoms.personinfo.entity.House;
import ru.sartfoms.personinfo.entity.MPIError;
import ru.sartfoms.personinfo.entity.Person;
import ru.sartfoms.personinfo.entity.PersonData;
import ru.sartfoms.personinfo.entity.Policy;
import ru.sartfoms.personinfo.entity.AsAddrObj;
import ru.sartfoms.personinfo.entity.Attach;
import ru.sartfoms.personinfo.model.PersDataParameters;
import ru.sartfoms.personinfo.service.AddressService;
import ru.sartfoms.personinfo.service.AttachService;
import ru.sartfoms.personinfo.service.DudlService;
import ru.sartfoms.personinfo.service.MPIErrorService;
import ru.sartfoms.personinfo.service.PersonDataService;
import ru.sartfoms.personinfo.service.PersonService;
import ru.sartfoms.personinfo.service.PolicyService;

@Controller
public class AppController {
	private final PersonDataService personDataService;
	private final PersonService personService;
	private final PolicyService policyService;
	private final MPIErrorService mpiErrorService;
	private final DudlService dudlService;
	private final AddressService addressService;
	private final AttachService attachService;
	@Autowired
	SmartValidator validator;

	public AppController(PersonDataService personDataService, PersonService personService, PolicyService policyService,
			MPIErrorService mpiErrorService, DudlService dudlService, AddressService addressService, AttachService attachService) {
		this.personDataService = personDataService;
		this.personService = personService;
		this.policyService = policyService;
		this.mpiErrorService = mpiErrorService;
		this.dudlService = dudlService;
		this.addressService = addressService;
		this.attachService = attachService;
	}

	@RequestMapping("/")
	public String index() {

		return "index";
	}

	@GetMapping("/persdata")
	public String personDataForm(Model model, @ModelAttribute("searchParams") PersDataParameters searchParams) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("policyTypes", policyType);
		model.addAttribute("resultTypes", resultType);
		model.addAttribute("dudlTypes", personDataService.getDudlTypes());

		Optional<Integer> page = Optional.of(1);
		Page<PersonData> persDataPage = personDataService.getPersDataPage(searchParams, userName, page);
		model.addAttribute("persDataPage", persDataPage);

		return "person-data-form";
	}

	@PostMapping("/persdata")
	public String personDataForm(Model model, @ModelAttribute("searchParams") PersDataParameters searchParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("policyTypes", policyType);
		model.addAttribute("resultTypes", resultType);
		model.addAttribute("dudlTypes", personDataService.getDudlTypes());

		if (!page.isPresent()) {
			personDataService.validate(searchParams, bindingResult);
			validator.validate(searchParams, bindingResult);
		}

		if (!bindingResult.hasErrors() && !page.isPresent())
			personDataService.saveRequest(searchParams, userName);

		Page<PersonData> persDataPage = personDataService.getPersDataPage(searchParams, userName, page);
		model.addAttribute("persDataPage", persDataPage);

		return "person-data-form";
	}

	@PostMapping("/persdata/res")
	public String personDataResult(Model model, @RequestParam("rid") Long rid) {

		PersonData personData = personDataService.getPersonDataByRid(rid);
		if (personData == null)
			return "error/404";

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "person-data-err";
		}

		Collection<String> showList = Arrays
				.asList(personData.getShow() != null ? personData.getShow().split(" ") : new String[0]);
		Collection<Person> persons = personService.findAllByRid(rid);
		Collection<Policy> policies = policyService.findAllByRid(rid);
		Collection<Dudl> dudls = dudlService.findAllByRid(rid);
		Collection<Address> addresses = addressService.findAllByRid(rid);
		Map<Integer, House> houses = new HashMap<>();
//		Map<Integer, AsAddrObj> streets = new HashMap<>();
		Map<Integer, String> addrChains = new HashMap<>();
		addresses.forEach(t -> {
			if (t.getHsguid() != null) {
				houses.put(t.getNr(), addressService.getHouseByObjectguid(t.getHsguid()));
			}
			if (t.getAoguid() != null) {
//				streets.put(t.getNr(), addressService.getAddrByObjectguid(t.getAoguid()));
				addrChains.put(t.getNr(), addressService.getAddressChain(t.getAoguid()));
			}
		});
//
		Collection<Attach> attachies = attachService.findAllByRid(rid);
//		Collection<Contact> contacts = service.getContactsByRid(rid);
//		Collection<Snils> snilses = service.getSnilsesByRid(rid);
//		Collection<SocialStatus> statuses = service.getSocialStatusesByRid(rid);
//		Collection<Ern> erns = service.getErnsByRid(rid);
//
//		model.addAttribute("showList", showList);
//		model.addAttribute("resultTypes", resultType);
//		model.addAttribute("personData", personData);
//		model.addAttribute("persons", persons);
//		model.addAttribute("policies", policies);
//		model.addAttribute("dudls", dudls);
//		model.addAttribute("addresses", addresses);
//		model.addAttribute("houses", houses);
//		model.addAttribute("streets", streets);
//		model.addAttribute("attaches", attachies);
//		model.addAttribute("contacts", contacts);
//		model.addAttribute("snilses", snilses);
//		model.addAttribute("statuses", statuses);
//		model.addAttribute("erns", erns);

		model.addAttribute("resultTypes", resultType);
		model.addAttribute("personData", personData);
		model.addAttribute("showList", showList);
		model.addAttribute("persons", persons);
		model.addAttribute("policies", policies);
		model.addAttribute("dudls", dudls);
		model.addAttribute("addresses", addresses);
		model.addAttribute("houses", houses);
//		model.addAttribute("streets", streets);
		model.addAttribute("addrChains", addrChains);
		model.addAttribute("attaches", attachies);

		return "person-data-res";
	}

}
