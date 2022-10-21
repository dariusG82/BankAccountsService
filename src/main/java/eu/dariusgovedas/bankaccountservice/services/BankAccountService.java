package eu.dariusgovedas.bankaccountservice.services;

import eu.dariusgovedas.bankaccountservice.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
@AllArgsConstructor
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;
}
