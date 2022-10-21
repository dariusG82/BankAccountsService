package eu.dariusgovedas.bankaccountservice.helpers;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CSVConverter {


    public static String[] HEADERS = {"Id","AccountNumber", "OperationDate", "OperationTime", "Beneficiary", "Comment", "Amount", "Currency"};

    public static List<BankAccount> csvToBankAccount(InputStream inputStream){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            CSVParser parser = new CSVParser(
                    reader,
                    CSVFormat.Builder.create()
                            .setHeader(HEADERS)
                            .setSkipHeaderRecord(true)
                            .setIgnoreHeaderCase(true)
                            .setTrim(true)
                            .build()
            );

            List<BankAccount> bankAccounts = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = parser.getRecords();

            for (CSVRecord record : csvRecords){
                BankAccount account = new BankAccount(
                        record.get("AccountNumber"),
                        LocalDate.parse(record.get("OperationDate")),
                        LocalTime.parse(record.get("OperationTime")),
                        record.get("Beneficiary"),
                        record.get("Comment"),
                        new BigDecimal(record.get("Amount")),
                        record.get("Currency")
                );

                bankAccounts.add(account);
            }

            return bankAccounts;

        } catch (IOException exception){
            throw new RuntimeException("Failed to parse CSV file: " + exception.getMessage());
        }
    }
}
