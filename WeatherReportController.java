package com.pactero.weatherreport.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pactero.weatherreport.model.City;
import com.pactero.weatherreport.service.WeatherReportService;

@Controller
@RequestMapping("/city.htm")
public class WeatherReportController {
	
	@Autowired WeatherReportService weatherReportService;
	
	@Autowired
    @Qualifier("cityValidator")
    private Validator validator;
	
	@InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model) {
		City city = new City();
		model.addAttribute("city", city);
		initModelList(model);
		return "city";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model, @Validated City city, BindingResult result) {
		model.addAttribute("city", city);
		String returnVal = "successCity";
		if(result.hasErrors()) {
			initModelList(model);
			returnVal = "city";
		} else {
			
			City weatherReportForGivenCity = weatherReportService.getWeatherReportForGivenCity(city);
			
			model.addAttribute("city", weatherReportForGivenCity);
		}		
		return returnVal;
	}
	
	private void initModelList(Model model) {
		List<String> cityList = new ArrayList<String>();
		cityList.add("Sydney");
		cityList.add("Melbourne");
		cityList.add("Wollongong");
		model.addAttribute("cities", cityList);
	}

}
