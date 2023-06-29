package ru.sartfoms.personinfo.controller;

import static ru.sartfoms.personinfo.service.PersonDataService.policyType;
import static ru.sartfoms.personinfo.service.PersonDataService.resultType;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;

import ru.sartfoms.personinfo.entity.Address;
import ru.sartfoms.personinfo.entity.Attach;
import ru.sartfoms.personinfo.entity.Contact;
import ru.sartfoms.personinfo.entity.Dudl;
import ru.sartfoms.personinfo.entity.Ern;
import ru.sartfoms.personinfo.entity.House;
import ru.sartfoms.personinfo.entity.InsuranceStatus;
import ru.sartfoms.personinfo.entity.InsuranceStatusRes;
import ru.sartfoms.personinfo.entity.MPIError;
import ru.sartfoms.personinfo.entity.MergeAncessorOip;
import ru.sartfoms.personinfo.entity.PersCriteria;
import ru.sartfoms.personinfo.entity.PersCriteriaRes;
import ru.sartfoms.personinfo.entity.Person;
import ru.sartfoms.personinfo.entity.PersonData;
import ru.sartfoms.personinfo.entity.Policy;
import ru.sartfoms.personinfo.entity.Snils;
import ru.sartfoms.personinfo.entity.SocialStatus;
import ru.sartfoms.personinfo.exception.ExcelGeneratorException;
import ru.sartfoms.personinfo.model.AncessorOipParameters;
import ru.sartfoms.personinfo.model.InsurStatusParameters;
import ru.sartfoms.personinfo.model.PersCritParameters;
import ru.sartfoms.personinfo.model.PersDataParameters;
import ru.sartfoms.personinfo.service.AddressService;
import ru.sartfoms.personinfo.service.AttachService;
import ru.sartfoms.personinfo.service.ContactService;
import ru.sartfoms.personinfo.service.DudlService;
import ru.sartfoms.personinfo.service.DudlTypeService;
import ru.sartfoms.personinfo.service.ErnService;
import ru.sartfoms.personinfo.service.ExcelService;
import ru.sartfoms.personinfo.service.InsuranceStatusResService;
import ru.sartfoms.personinfo.service.InsuranceStatusService;
import ru.sartfoms.personinfo.service.MPIErrorService;
import ru.sartfoms.personinfo.service.MergeAncessorOipService;
import ru.sartfoms.personinfo.service.OkatoService;
import ru.sartfoms.personinfo.service.OksmService;
import ru.sartfoms.personinfo.service.PersCriteriaResService;
import ru.sartfoms.personinfo.service.PersCriteriaService;
import ru.sartfoms.personinfo.service.PersonDataService;
import ru.sartfoms.personinfo.service.PersonService;
import ru.sartfoms.personinfo.service.PolicyService;
import ru.sartfoms.personinfo.service.SnilsService;
import ru.sartfoms.personinfo.service.SocialStatusService;

@Controller
public class AppController {
	private final PersonDataService personDataService;
	private final PersonService personService;
	private final PolicyService policyService;
	private final MPIErrorService mpiErrorService;
	private final DudlService dudlService;
	private final AddressService addressService;
	private final AttachService attachService;
	private final ContactService contactService;
	private final SnilsService snilsService;
	private final SocialStatusService socialStatusService;
	private final ErnService ernService;
	private final ExcelService excelService;
	private final PersCriteriaService persCriteriaService;
	private final OkatoService okatoService;
	private final OksmService oksmService;
	private final DudlTypeService dudlTypeService;
	private final PersCriteriaResService persCriteriaResService;
	private final MergeAncessorOipService mergeAncessorOipService;
	private final InsuranceStatusService insuranceStatusService;
	private final InsuranceStatusResService insuranceStatusResService;
	@Autowired
	SmartValidator validator;

	public AppController(PersonDataService personDataService, PersonService personService, PolicyService policyService,
			MPIErrorService mpiErrorService, DudlService dudlService, AddressService addressService,
			AttachService attachService, ContactService contactService, SnilsService snilsService,
			SocialStatusService socialStatusService, ErnService ernService, ExcelService excelService,
			PersCriteriaService persCriteriaService, OkatoService okatoService, OksmService oksmService,
			DudlTypeService dudlTypeService, PersCriteriaResService persCriteriaResService,
			MergeAncessorOipService mergeAncessorOipService, InsuranceStatusService insuranceStatusService, InsuranceStatusResService insuranceStatusResService) {
		this.personDataService = personDataService;
		this.personService = personService;
		this.policyService = policyService;
		this.mpiErrorService = mpiErrorService;
		this.dudlService = dudlService;
		this.addressService = addressService;
		this.attachService = attachService;
		this.contactService = contactService;
		this.snilsService = snilsService;
		this.socialStatusService = socialStatusService;
		this.ernService = ernService;
		this.excelService = excelService;
		this.persCriteriaService = persCriteriaService;
		this.okatoService = okatoService;
		this.oksmService = oksmService;
		this.dudlTypeService = dudlTypeService;
		this.persCriteriaResService = persCriteriaResService;
		this.mergeAncessorOipService = mergeAncessorOipService;
		this.insuranceStatusService = insuranceStatusService;
		this.insuranceStatusResService = insuranceStatusResService;
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
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		Optional<Integer> page = Optional.of(1);
		Page<PersonData> dataPage = personDataService.getDataPage(searchParams, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "person-data-form";
	}

	@PostMapping("/persdata")
	public String personDataForm(Model model, @ModelAttribute("searchParams") PersDataParameters searchParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("policyTypes", policyType);
		model.addAttribute("resultTypes", resultType);
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		if (!page.isPresent()) {
			personDataService.validate(searchParams, bindingResult);
			validator.validate(searchParams, bindingResult);
		}

		if (!bindingResult.hasErrors() && !page.isPresent())
			personDataService.saveRequest(searchParams, userName);

		Page<PersonData> dataPage = personDataService.getDataPage(searchParams, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "person-data-form";
	}

	@PostMapping("/persdata/excel")
	@ResponseBody
	public ResponseEntity<?> download(Model model, @ModelAttribute("searchParams") PersDataParameters searchParams) {
		if (searchParams.getSelectedRows() == null) {
			return new ResponseEntity<String>("<h1>Не выбраны строки для отчета в Excel</h1>", HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<?> resource;
		try {
			resource = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(new InputStreamResource(excelService.createExcel(searchParams.getSelectedRows())));
		} catch (IOException | ExcelGeneratorException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resource;
	}

	@PostMapping("/persdata/res")
	public String personDataResult(Model model, @RequestParam("rid") Long rid) {

		PersonData personData = personDataService.getPersonDataByRid(rid);
		if (personData == null)
			return "error/404";

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "mpi-err";
		}

		Collection<String> showList = Arrays
				.asList(personData.getShow() != null ? personData.getShow().split(" ") : new String[0]);
		Collection<Person> persons = personService.findAllByRid(rid);
		Collection<Policy> policies = policyService.findAllByRid(rid);
		Collection<Dudl> dudls = dudlService.findAllByRid(rid);
		Collection<Address> addresses = addressService.findAllByRid(rid);
		Map<Integer, House> houses = new HashMap<>();
		Map<Integer, String> addrChains = new HashMap<>();
		addresses.forEach(t -> {
			if (t.getHsguid() != null) {
				houses.put(t.getNr(), addressService.getHouseByObjectguid(t.getHsguid()));
			}
			if (t.getAoguid() != null) {
				addrChains.put(t.getNr(), addressService.getAddressChain(t.getAoguid()));
			}
		});

		Collection<Attach> attachies = attachService.findAllByRid(rid);
		Collection<Contact> contacts = contactService.findAllByRid(rid);
		Collection<Snils> snilses = snilsService.findAllByRid(rid);
		Collection<SocialStatus> statuses = socialStatusService.findAllByRid(rid);
		Collection<Ern> erns = ernService.findAllByRid(rid);

		model.addAttribute("resultTypes", resultType);
		model.addAttribute("personData", personData);
		model.addAttribute("showList", showList);
		model.addAttribute("persons", persons);
		model.addAttribute("policies", policies);
		model.addAttribute("dudls", dudls);
		model.addAttribute("addresses", addresses);
		model.addAttribute("houses", houses);
		model.addAttribute("addrChains", addrChains);
		model.addAttribute("attaches", attachies);
		model.addAttribute("contacts", contacts);
		model.addAttribute("snilses", snilses);
		model.addAttribute("statuses", statuses);
		model.addAttribute("erns", erns);

		return "person-data-res";
	}

	@GetMapping("/persdata/res/excel")
	@ResponseBody
	public ResponseEntity<?> download(Model model, @RequestParam("rid") Long rid) {
		ResponseEntity<?> resource;
		try {
			resource = ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report" + rid + ".xlsx")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(new InputStreamResource(excelService.createExcel(rid)));
		} catch (IOException | ExcelGeneratorException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resource;
	}

	@GetMapping("/perscrit")
	public String persCriteriaForm(Model model) throws ParseException {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		PersCritParameters formParams = new PersCritParameters();
		model.addAttribute("searchParams", formParams);
		model.addAttribute("okatos", okatoService.findAll());
		model.addAttribute("oksms", oksmService.findAll());
		model.addAttribute("policyTypes", policyType);
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		Page<PersCriteria> dataPage = persCriteriaService.getDataPage(formParams, userName, Optional.of(1));
		model.addAttribute("dataPage", dataPage);

		return "person-crit-form";
	}

	@PostMapping("/perscrit")
	public String persCriteriaForm(Model model, @ModelAttribute("searchParams") PersCritParameters persCritSParam,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) throws ParseException {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("okatos", okatoService.findAll());
		model.addAttribute("oksms", oksmService.findAll());
		model.addAttribute("policyTypes", policyType);
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		if (!page.isPresent()) {
			persCriteriaService.validate(persCritSParam, bindingResult);
			validator.validate(persCritSParam, bindingResult);
		}

		if (!bindingResult.hasErrors() && !page.isPresent()) {
			persCriteriaService.saveRequest(persCritSParam, userName);
		}

		Page<PersCriteria> dataPage = persCriteriaService.getDataPage(persCritSParam, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "person-crit-form";
	}

	@PostMapping("/perscrit/res")
	public String persCriteriaResult(Model model, @RequestParam("rid") Long rid) {

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "mpi-err";
		}

		Collection<PersCriteriaRes> persons = persCriteriaResService.findAllByRid(rid);
		model.addAttribute("persons", persons);

		return "person-crit-res";

	}

	@GetMapping("/ancessor")
	public String mergeAncessorOip(Model model) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		AncessorOipParameters formParams = new AncessorOipParameters();
		model.addAttribute("searchParams", formParams);

		Optional<Integer> page = Optional.of(1);
		Page<MergeAncessorOip> dataPage = mergeAncessorOipService.getDataPage(formParams, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "ancessor-oip";

	}

	@PostMapping("/ancessor")
	public String mergeAncessorOip(Model model, @ModelAttribute("searchParams") AncessorOipParameters searchParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (!page.isPresent())
			validator.validate(searchParams, bindingResult);

		if (!bindingResult.hasErrors() && !page.isPresent())
			mergeAncessorOipService.saveRequest(searchParams, userName);

		Page<MergeAncessorOip> dataPage = mergeAncessorOipService.getDataPage(searchParams, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "ancessor-oip";

	}

	@PostMapping("/ancessor/res")
	public String ancessorOipResult(Model model, @RequestParam("rid") Long rid) {

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "mpi-err";
		}

		return "error/404";
	}
	
	@GetMapping("/insurstat")
	public String insuranceStatus(Model model) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		InsurStatusParameters formParams = new InsurStatusParameters();
		model.addAttribute("searchParams", formParams);
		model.addAttribute("policyTypes", policyType);
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		Optional<Integer> page = Optional.of(1);
		Page<InsuranceStatus> dataPage = insuranceStatusService.getDataPage(formParams, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "insurance-status-form";
	}
	
	@PostMapping("/insurstat")
	public String insuranceStatus(Model model, @ModelAttribute("searchParams") InsurStatusParameters searchParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("policyTypes", policyType);
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		if (!page.isPresent()) {
			insuranceStatusService.validate(searchParams, bindingResult);
			validator.validate(searchParams, bindingResult);
		}

		if (!bindingResult.hasErrors() && !page.isPresent())
			insuranceStatusService.saveRequest(searchParams, userName);

		Page<InsuranceStatus> dataPage = insuranceStatusService.getDataPage(searchParams, userName, page);
		model.addAttribute("dataPage", dataPage);

		return "insurance-status-form";
	}
	
	@PostMapping("/insurstat/res")
	public String insuranceStatusResult(Model model, @RequestParam("rid") Long rid) {

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "mpi-err";
		}
		
		model.addAttribute("policyTypes", policyType);
		Collection<InsuranceStatusRes> persons = insuranceStatusResService.findAllByRid(rid);
		model.addAttribute("persons", persons);

		return "insurance-status-res";

	}

}
