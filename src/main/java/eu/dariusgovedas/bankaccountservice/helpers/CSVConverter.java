package eu.dariusgovedas.bankaccountservice.helpers;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVConverter {

    public static String TYPE = "text/csv";
    public static String[] HEADERS = {"Id", "AccountNumber", "OperationDate", "OperationTime", "Beneficiary", "Comment", "Amount", "Currency"};

    public static List<BankAccount> csvToBankAccount(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
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

            for (CSVRecord record : csvRecords) {
                bankAccounts.add(getBankAccount(record));
            }

            return bankAccounts;

        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse CSV file: " + exception.getMessage());
        }
    }

    public static ByteArrayInputStream bankAccountToCsv(List<BankAccount> accountList) {
        final CSVFormat csvFormat = CSVFormat.Builder.create()
                .setQuoteMode(QuoteMode.MINIMAL).build();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), csvFormat)) {

            for (BankAccount account : accountList) {
                csvPrinter.printRecord(getAccountRecordList(account));
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException exception) {
            throw new RuntimeException("Failed to import data to csv file: " + exception.getMessage());
        }
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }


    private static BankAccount getBankAccount(CSVRecord record) {
        return new BankAccount(
                record.get("AccountNumber"),
                LocalDate.parse(record.get("OperationDate")),
                LocalTime.parse(record.get("OperationTime")),
                record.get("Beneficiary"),
                record.get("Comment"),
                new BigDecimal(record.get("Amount")),
                record.get("Currency")
        );
    }

    private static List<String> getAccountRecordList(BankAccount account) {
        return Arrays.asList(
                account.getAccountNumber(),
                account.getOperationDate().toString(),
                account.getOperationTime().toString(),
                account.getBeneficiary(),
                account.getComment(),
                account.getAmount().toString(),
                account.getCurrency()
        );
    }
}
