package com.qadr.bank.feign;

import com.qadr.bank.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * fallback factory for hystrix client
 * If one needs access to the cause that made the fallback trigger,
 * one can use the fallbackFactory attribute inside @FeignClient.
 * */

@Component
@Slf4j
public class CountryClientFallback implements FallbackFactory<CountryClient> {

    @Override
    public CountryClient create(Throwable cause) {
        return code -> {
            log.info("could not fetch country with the code "+ code);
            return new Country(code);
        };
    }
}
