package eu.dariusgovedas.bankaccountservice.services;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import eu.dariusgovedas.bankaccountservice.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    public void save(MultipartFile file) {
        try {
            List<BankAccount> accounts = CSVConverter.csvToBankAccount(file.getInputStream());
            bankAccountRepository.saveAll(accounts);
        } catch (IOException exception){
            throw new RuntimeException("Fail to store csv data: " + exception.getMessage());
        }
    }
}
