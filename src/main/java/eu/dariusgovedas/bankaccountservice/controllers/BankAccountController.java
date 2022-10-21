package eu.dariusgovedas.bankaccountservice.controllers;

import eu.dariusgovedas.bankaccountservice.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csv")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

}
