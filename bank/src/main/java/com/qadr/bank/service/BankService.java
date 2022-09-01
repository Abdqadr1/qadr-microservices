package com.qadr.bank.service;

import com.qadr.bank.errors.CustomException;
import com.qadr.bank.model.Bank;
import com.qadr.bank.repo.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service @Transactional
public class BankService {
    public static final int BANKS_PER_PAGE = 8;
    @Autowired private BankRepo bankRepo;

    private void validateBank(Bank bank){
        Optional<Bank> bankOptional;
        if(bank.getCode() != null && !bank.getCode().isBlank()){
            List<Bank> banks = bankRepo.findByCode(bank.getCode());
            if (banks.size() > 0 && banks.stream().anyMatch(b -> b.getId().equals(bank.getId()))) {
                throw new CustomException("Sort code already exists!", HttpStatus.BAD_REQUEST);
            }
        }

        bankOptional = bankRepo.findByAlias(bank.getAlias());
        if (bankOptional.isPresent() && !bankOptional.get().getId().equals(bank.getId())) {
            throw new CustomException("Alias already exists!", HttpStatus.BAD_REQUEST);
        }
    }

    public Bank addBank(Bank bank) {
        validateBank(bank);
        bank.setCreatedTime(new Date());
        bank.setEnabled(true);
        return bankRepo.save(bank);
    }

    public Bank updateBank(Bank newBank) {
        Integer id = newBank.getId();
        Bank bankInDb = getBankById(id);
        validateBank(newBank);
        newBank.setCreatedTime(bankInDb.getCreatedTime());
        newBank.setUpdatedTime(new Date());
        newBank.setEnabled(bankInDb.isEnabled());
        return bankRepo.save(newBank);
    }

    public void updateEnabled(Integer id, boolean enabled){
        getBankById(id);
        bankRepo.updateEnabled(id, enabled);
    }

    public Bank getBankById(int id) {
        Optional<Bank> bankOptional =  bankRepo.findById(id);
        if (bankOptional.isEmpty()) throw new CustomException("Bank not found", HttpStatus.NOT_FOUND);
        return bankOptional.get();
    }

    public Bank getBankByAlias(String alias) {
        return bankRepo.findByAlias(alias).orElseThrow(
                ()-> new CustomException("Could not find bank with alias "+alias, HttpStatus.NOT_FOUND));
    }
    public List<Bank> getBanksByType(String type) {
        return bankRepo.findByType(type);
    }

    public List<Bank> getAllBanks() {
        return bankRepo.findAll(Sort.by("name").ascending());
    }

    public Bank deleteBankById(int id) {
        Bank bank = bankRepo.findById(id)
                .orElseThrow(
                        ()-> new CustomException("Bank not found", HttpStatus.NOT_FOUND)
                );
        bankRepo.deleteById(id);
        return bank;
    }


    public Map<String, Object> getBankPage(int pageNumber, String keyword) {
        Sort sort = Sort.by("name").ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, BANKS_PER_PAGE, sort);
        Page<Bank> page;
        if(keyword != null && !keyword.isBlank()){
            page = bankRepo.searchBanks(keyword, pageRequest);
        }else{
            page = bankRepo.findAll(pageRequest);
        }
        int startCount = (pageNumber-1) * BANKS_PER_PAGE + 1;
        int endCount = BANKS_PER_PAGE * pageNumber;
        endCount = (endCount > page.getTotalElements()) ? (int) page.getTotalElements() : endCount;
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", pageNumber);
        map.put("startCount", startCount);
        map.put("endCount", endCount);
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("banks", page.getContent());
        map.put("numberPerPage", BANKS_PER_PAGE);
        return map;
    }
}
