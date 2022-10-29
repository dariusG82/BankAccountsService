package eu.dariusgovedas.bankaccountservice.helpers;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AccountBalance {

    String accountNumber;
    LocalDate dateFrom;
    LocalDate dateTo;
    BigDecimal balance;
}
