package eu.dariusgovedas.bankaccountservice.controllers;

import eu.dariusgovedas.bankaccountservice.helpers.CSVConverter;
import eu.dariusgovedas.bankaccountservice.helpers.ResponseMessage;
import eu.dariusgovedas.bankaccountservice.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file){
        String message;

        if(CSVConverter.hasCSVFormat(file)){
            try {
                bankAccountService.save(file);
                message = "Uploaded file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception exception){
                message = "Could not upload file: " + file.getOriginalFilename() + "!!!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a .csv file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
