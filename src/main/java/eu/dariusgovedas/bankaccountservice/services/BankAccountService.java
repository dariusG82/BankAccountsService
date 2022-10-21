package eu.dariusgovedas.bankaccountservice.services;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import eu.dariusgovedas.bankaccountservice.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
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
        } catch (IOException exception) {
            throw new RuntimeException("Fail to store csv data: " + exception.getMessage());
        }
    }

    public List<BankAccount> getAllAccountsData() {
        return bankAccountRepository.findAll();
    }

    public InputStream getBankAccountDataForPeriod(String startDate, String endDate) {
        List<BankAccount> accountList = getPeriodData(startDate, endDate);

        return CSVConverter.bankAccountToCsv(accountList);
    }

    private List<BankAccount> getPeriodData(String startDate, String endDate) {
        LocalDate dateFrom = LocalDate.EPOCH;
        LocalDate dateTo = LocalDate.now();

        if (startDate != null) {
            try {
                dateFrom = LocalDate.parse(startDate);
            } catch (Exception ignored) {
            }
        }
        if (endDate != null) {
            try {
                dateTo = LocalDate.parse(endDate);
            } catch (Exception ignored) {
            }
        }
        return bankAccountRepository.findAccountsData(dateFrom, dateTo);
    }
}
