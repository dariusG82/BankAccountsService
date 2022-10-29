# BankAccountsService
BankAccountsService assignment

To run service:
  
open command line -> go to project directory -> run command
  
    mvc spring-boot:run
    
  service is up and running

Endpoints:

  IMPORT CSV File:

    POST  http://localhost:8080/api/csv/accounts

    @RequestParam("file")
  
 
  EXPORT CSV file
  
    GET http://localhost:8080/api/csv/accounts
    
    Optional parameter - startDate,
    Optional parameter - endDate
    
  GET ACCOUNT BALANCE
    
    GET  http://localhost:8080/api/csv/accounts/{accountNr}
    
    Mandatory parameter - accountNr - bank account number,
    Optional parameter - dateFrom,
    Oprional parameter - dateTo
    
  Additional end point - GET ALL ACCOUNTS DATA in Json format
  
    GET  http://localhost:8080/api/csv/accounts/all
