package ru.sartfoms.personinfo.controller;

import static ru.sartfoms.personinfo.service.PersonDataService.policyType;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.sartfoms.personinfo.model.PersDataParameters;
import ru.sartfoms.personinfo.service.PersonDataService;

@Controller
public class AppController {
	private final PersonDataService personDataService;

	public AppController(PersonDataService service) {
		this.personDataService = service;
	}

	@RequestMapping("/")
	public String index() {

		return "index";
	}

	@RequestMapping("/persdata")
	public String personDataForm(Model model, @ModelAttribute("searchParams") PersDataParameters searchParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {

		model.addAttribute("policyTypes", policyType);
		model.addAttribute("dudlTypes", personDataService.getDudlTypes());

		return "person-data-form";
	}

}
