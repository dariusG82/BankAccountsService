package eu.dariusgovedas.bankaccountservice.repositories;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("FROM BankAccount a WHERE a.operationDate BETWEEN :start AND :end")
    List<BankAccount> findAccountsData(@Param("start") LocalDate dateFrom, @Param("end") LocalDate dateTo);

}
