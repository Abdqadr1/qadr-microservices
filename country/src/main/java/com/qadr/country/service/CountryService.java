package com.qadr.country.service;

import com.qadr.country.errors.CustomException;
import com.qadr.country.model.Country;
import com.qadr.country.repo.CountryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public record CountryService(CountryRepo countryRepo) {
    public static final int COUNTRY_PER_PAGE = 25;

    public Country saveCountry(Country country){
        validate(country);
        country.setCode(country.getCode().toUpperCase());
        return countryRepo.save(country);
    }

    private void validate(Country country){
        if(country.getId() == null) return;
        Optional<Country> optional = countryRepo.findByName(country.getName());
        if(optional.isPresent() && !optional.get().getId().equals(country.getId())){
            throw new CustomException("Country name already exists", HttpStatus.BAD_REQUEST);
        }
        optional = countryRepo.findByCode(country.getCode());
        if(optional.isPresent() && !optional.get().getId().equals(country.getId())){
            throw new CustomException("Country code already exists", HttpStatus.BAD_REQUEST);
        }
//        optional = countryRepo.findByCallCode(country.getCallCode());
//        if(optional.isPresent() && !optional.get().getId().equals(country.getId())){
//            throw new CustomException("Country call code already exists", HttpStatus.BAD_REQUEST);
//        }
    }

    public Country getById(Integer id){
        return countryRepo.findById(id)
                .orElseThrow(() -> new CustomException("Could not find country with id " + id, HttpStatus.BAD_REQUEST));

    }

    public Map<String, Object> getCountryPage(Integer pageNumber, String keyword){
        Sort sort = Sort.by("name").ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, COUNTRY_PER_PAGE, sort);
        Page<Country> page;
        if(keyword != null && !keyword.isBlank()){
            page = countryRepo.searchCountries(keyword, pageRequest);
        }else{
            page = countryRepo.findAll(pageRequest);
        }
        int startCount = (pageNumber-1) * COUNTRY_PER_PAGE + 1;
        int endCount = COUNTRY_PER_PAGE * pageNumber;
        endCount = (endCount > page.getTotalElements()) ? (int) page.getTotalElements() : endCount;
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", pageNumber);
        map.put("startCount", startCount);
        map.put("endCount", endCount);
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("countries", page.getContent());
        map.put("numberPerPage", COUNTRY_PER_PAGE);
        return map;
    }

    public List<Country> findAll() {
        return countryRepo.findAll();
    }

    public List<Country> findByContinent(String continent) {
        return countryRepo.findByContinent(continent);
    }

    public void deleteCountry(Integer id) {
        getById(id);
        countryRepo.deleteById(id);
    }

    public Country findByCode(String code) {
        return countryRepo.findByCode(code)
                .orElseThrow(() -> new CustomException("Could not find country with code "+code,
                        HttpStatus.BAD_REQUEST));
    }

    public Country findByName(String name) {
        return countryRepo.findByName(name)
                .orElseThrow(() -> new CustomException("Could not find country with name "+name,
                        HttpStatus.BAD_REQUEST));
    }

    public Country findByCallCode(String code) {
        return countryRepo.findByCallCode(code)
                .orElseThrow(()-> new CustomException("Could not find country with call code "+code,
                        HttpStatus.BAD_REQUEST));
    }
}
