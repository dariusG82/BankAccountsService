package eu.dariusgovedas.bankaccountservice.services;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.exceptions.BankAccountNotFoundException;
import eu.dariusgovedas.bankaccountservice.helpers.AccountBalance;
import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import eu.dariusgovedas.bankaccountservice.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

    public AccountBalance getAccountBalance(String accountNr, String startDate, String endDate) {
        LocalDate dateFrom = getDate(LocalDate.EPOCH, startDate);
        LocalDate dateTo = getDate(LocalDate.now(), endDate);

        List<BankAccount> bankAccounts = bankAccountRepository.findAccountDataForPeriod(accountNr, dateFrom, dateTo);

        BigDecimal balance = getAccountBalance(accountNr, bankAccounts);

        AccountBalance accountBalance = new AccountBalance();

        accountBalance.setAccountNumber(accountNr);
        accountBalance.setDateFrom(dateFrom);
        accountBalance.setDateTo(dateTo);
        accountBalance.setBalance(balance);

        return accountBalance;
    }

    private BigDecimal getAccountBalance(String accountNr, List<BankAccount> bankAccounts) {
        if (bankAccounts.isEmpty()) {
            throw new BankAccountNotFoundException(String.format("Bank account %s not found", accountNr));
        }

        BigDecimal result = BigDecimal.ZERO;

        for (BankAccount account : bankAccounts) {
            result = result.add(account.getAmount());
        }
        return result;
    }

    private LocalDate getDate(LocalDate date, String dateToParse) {
        LocalDate newDate = date;
        if (dateToParse != null) {
            try {
                newDate = LocalDate.parse(dateToParse);
            } catch (Exception ignored) {
            }
        }
        return newDate;
    }

    private List<BankAccount> getPeriodData(String startDate, String endDate) {
        LocalDate dateFrom = getDate(LocalDate.EPOCH, startDate);
        LocalDate dateTo = getDate(LocalDate.now(), endDate);

        return bankAccountRepository.findAccountsData(dateFrom, dateTo);
    }
}
