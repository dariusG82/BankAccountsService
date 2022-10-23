package eu.dariusgovedas.bankaccountservice.helper;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static eu.dariusgovedas.bankaccountservice.helpers.CSVConverter.bankAccountToCsv;
import static org.assertj.core.api.Assertions.assertThat;

public class CSVConverterTest {

    @Test
    public void isCsvToBankAccountSuccessful() throws IOException {
        InputStream stream = Files.newInputStream(Path.of("src/test/resources/sample.csv"));

        List<BankAccount> accounts = CSVConverter.csvToBankAccount(stream);

        assertThat(accounts.size() > 0).isTrue();
    }

    @Test
    public void isBankAccountToCsvSuccessful() {
        List<BankAccount> accounts = new ArrayList<>();
        BankAccount account = new BankAccount(
                "LT277300010089649201",
                LocalDate.of(2022, 9, 4),
                LocalTime.of(12, 22, 32),
                "Darius Govedas",
                "",
                BigDecimal.valueOf(35.55),
                "EUR");
        accounts.add(account);

        assertThat(bankAccountToCsv(accounts)).isNotInstanceOf(IOException.class);
    }
}
