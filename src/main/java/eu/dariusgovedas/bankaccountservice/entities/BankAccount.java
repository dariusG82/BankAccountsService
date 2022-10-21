package eu.dariusgovedas.bankaccountservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue
    private long id;
    private String accountNumber;
    private LocalDate operationDate;
    private LocalTime operationTime;
    private String beneficiary;
    private String comment;
    private BigDecimal amount;
    private String currency;

    public BankAccount(String accountNumber, LocalDate operationDate, LocalTime operationTime, String beneficiary, String comment, BigDecimal amount, String currency) {
        this.accountNumber = accountNumber;
        this.operationDate = operationDate;
        this.operationTime = operationTime;
        this.beneficiary = beneficiary;
        this.comment = comment;
        this.amount = amount;
        this.currency = currency;
    }
}
