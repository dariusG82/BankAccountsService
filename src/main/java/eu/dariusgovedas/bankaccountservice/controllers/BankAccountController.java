package eu.dariusgovedas.bankaccountservice.controllers;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.helpers.AccountBalance;
import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import eu.dariusgovedas.bankaccountservice.helpers.ResponseMessage;
import eu.dariusgovedas.bankaccountservice.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") @Valid MultipartFile file) {
        String message;

        if (CSVConverter.hasCSVFormat(file)) {
            try {
                bankAccountService.save(file);
                message = "Uploaded file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception exception) {
                message = "Could not upload file: " + file.getOriginalFilename() + "!!!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a .csv file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/accounts/all")
    public ResponseEntity<List<BankAccount>> getAllAccounts() {

        try {
            List<BankAccount> accounts = bankAccountService.getAllAccountsData();

            if (accounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<Resource> getFile(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        String fileName = "accountsData.csv";
        InputStreamResource file = new InputStreamResource(bankAccountService.getBankAccountDataForPeriod(startDate, endDate));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }


    @GetMapping("/accounts/{accountNr}")
    public ResponseEntity<AccountBalance> getAccountBalance(
            @PathVariable String accountNr,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo
            ) {

        AccountBalance balance = bankAccountService.getAccountBalance(accountNr, dateFrom, dateTo);

        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
