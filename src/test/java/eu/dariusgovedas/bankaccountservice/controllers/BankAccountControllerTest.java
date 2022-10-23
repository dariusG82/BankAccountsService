package eu.dariusgovedas.bankaccountservice.controllers;

import eu.dariusgovedas.bankaccountservice.entities.BankAccount;
import eu.dariusgovedas.bankaccountservice.services.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService service;

    @Test
    void uploadFile() throws Exception {
        InputStream stream = Files.newInputStream(Path.of("src/test/resources/sample.csv"));
        MockMultipartFile file = new MockMultipartFile("file", "file.csv", String.valueOf(MediaType.valueOf("text/csv")), stream);

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/api/csv/upload");
        mockMvc.perform(multipartRequest.file(file))
                .andExpect(status().isOk());
    }

    @Test
    void getAllAccounts() throws Exception {
        BankAccount account = new BankAccount(
                "LT12345",
                LocalDate.now(),
                LocalTime.now(),
                "Darius",
                "",
                BigDecimal.valueOf(25),
                "EUR"
        );

        List<BankAccount> accounts = List.of(account);

        given(service.getAllAccountsData()).willReturn(accounts);

        mockMvc.perform(get("/api/csv/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber", is(account.getAccountNumber())));
    }
}