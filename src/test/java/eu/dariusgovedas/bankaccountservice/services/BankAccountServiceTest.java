package eu.dariusgovedas.bankaccountservice.services;

import eu.dariusgovedas.bankaccountservice.repositories.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BankAccountServiceTest {

    @InjectMocks
    private BankAccountService service;

    @Mock
    private BankAccountRepository repository;

    @Test
    void save() throws IOException {
        InputStream stream = Files.newInputStream(Path.of("src/test/resources/sample.csv"));
        MultipartFile file = new MockMultipartFile("sample.csv", stream);

        assertDoesNotThrow(() -> service.save(file));
    }
}