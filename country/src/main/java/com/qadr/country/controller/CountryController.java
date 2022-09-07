package com.qadr.country.controller;
import com.qadr.country.model.Country;
import com.qadr.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/country")
public class CountryController {
    @Autowired private CountryService countryService;

    @GetMapping("/all")
    public List<Country> getAllCountries(){
        return countryService.findAll();
    }

    @GetMapping("/continent/{continent}")
    public List<Country> getCountriesByContinent(@PathVariable String continent){
        return countryService.findByContinent(continent);
    }

    @GetMapping("/call_code/{code}")
    public Country findCountryByCallCode(@PathVariable String code){
        return countryService.findByCallCode(code);
    }

    @GetMapping("/code/{code}")
    public Country findCountryByCode(@PathVariable String code){
        return countryService.findByCode(code);
    }

    @GetMapping("/name/{name}")
    public Country findCountryByName(@PathVariable String name){
        return countryService.findByName(name);
    }

    @GetMapping("/id/{id}")
    public Country findCountryByName(@PathVariable Integer id){
        return countryService.getById(id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public void deleteCountry(@PathVariable Integer id){
        countryService.deleteCountry(id);
    }

    @PostMapping("/admin/save")
    public Country saveCountry(Country country){
        return countryService.saveCountry(country);
    }

    @GetMapping("/admin/page/{number}")
    public Map<String, Object> getCountryPage(@PathVariable("number") Integer pageNumber,
                                              @RequestParam(value = "keyword", defaultValue = "") String keyword){
        return countryService.getCountryPage(pageNumber, keyword);
    }
}
