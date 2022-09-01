package com.qadr.bank.feign;

import com.qadr.bank.model.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "country", fallbackFactory = CountryClientFallback.class)
public interface CountryClient {

    @GetMapping("/country/code/{code}")
    Country getCountryByCode(@PathVariable("code") String code);

}
