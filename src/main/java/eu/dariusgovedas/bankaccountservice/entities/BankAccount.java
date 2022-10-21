package eu.dariusgovedas.bankaccountservice.entities;

import eu.dariusgovedas.bankaccountservice.validation.ValidDate;
import eu.dariusgovedas.bankaccountservice.validation.ValidTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String accountNumber;
    @ValidDate
    private LocalDate operationDate;
    @ValidTime
    private LocalTime operationTime;
    @NotEmpty
    private String beneficiary;
    private String comment;
    @Digits(integer = Integer.MAX_VALUE, fraction = 2)
    private BigDecimal amount;
    @NotEmpty
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
