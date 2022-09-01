package com.qadr.bank.repo;

import com.qadr.bank.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepo extends JpaRepository<Bank, Integer> {
    List<Bank> findByType(String type);
    Optional<Bank> findByAlias(String name);
    List<Bank> findByCode(String code);

    @Modifying
    @Query("UPDATE Bank b SET b.enabled=?2 WHERE b.id=?1")
    void updateEnabled(Integer id, boolean enabled);

    @Query("SELECT b FROM Bank b WHERE " +
            "b.name LIKE %?1% OR " +
            "b.alias LIKE %?1% OR " +
            "b.code LIKE %?1% OR " +
            "b.longCode LIKE %?1% OR " +
            "b.type LIKE %?1% OR " +
            "b.currency LIKE %?1%")
    Page<Bank> searchBanks(String keyword, Pageable pageable);

}
