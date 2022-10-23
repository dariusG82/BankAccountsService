package eu.dariusgovedas.bankaccountservice.services;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.exceptions.BankAccountNotFoundException;
import eu.dariusgovedas.bankaccountservice.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BankAccountServiceTest {

    @InjectMocks
    private BankAccountService service;

    @Mock
    private BankAccountRepository repository;

    @BeforeEach
    public void setup() {
        BankAccount account1 = new BankAccount(
                "LT12345", LocalDate.of(2022, 5, 25), LocalTime.now(), "Darius",
                "", BigDecimal.valueOf(25), "EUR"
        );
        BankAccount account2 = new BankAccount(
                "LT6789", LocalDate.of(2022, 6, 27), LocalTime.now(), "Darius",
                "", BigDecimal.valueOf(29), "EUR"
        );
        BankAccount account3 = new BankAccount(
                "LT6789", LocalDate.of(2022, 6, 30), LocalTime.now(), "Darius",
                "", BigDecimal.valueOf(29), "EUR"
        );

        List<BankAccount> accounts = Arrays.asList(account1, account2, account3);
        when(repository.findAll()).thenReturn(accounts);
    }

    @Test
    void save() throws IOException {
        InputStream stream = Files.newInputStream(Path.of("src/test/resources/sample.csv"));
        MultipartFile file = new MockMultipartFile("sample.csv", stream);

        assertDoesNotThrow(() -> service.save(file));
    }

    @Test
    void getAllAccountsData() {
        List<BankAccount> accounts = service.getAllAccountsData();

        assertTrue(accounts.size() > 0);
    }

    @Test
    void getAccountBalance() {
        List<BankAccount> accounts = service.getAllAccountsData();

        BigDecimal value = BigDecimal.ZERO;

        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals("LT12345")) {
                value = value.add(account.getAmount());
            }
        }

        assertTrue(value.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void getAccountBalance_accountDoesNotExistThrowException() {
        assertThrows(BankAccountNotFoundException.class, () -> service.getAccountBalance("LT000", "", ""));
    }
}