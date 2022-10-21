package eu.dariusgovedas.bankaccountservice.helper;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVConverterTest {

    @Test
    public void isCsvToBankAccountSuccessful() throws IOException {
        InputStream stream = Files.newInputStream(Path.of("src/test/java/eu/dariusgovedas/bankaccountservice/helper/sample.csv"));

        List<BankAccount> accounts = CSVConverter.csvToBankAccount(stream);

        assertThat(accounts.size()>0).isTrue();
    }
}
