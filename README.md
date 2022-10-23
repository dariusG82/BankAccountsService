# BankAccountsService
BankAccountsService assignment

To run service:
  open command line
  go to project directory
  run command
  
    mvc spring-boot:run
    
  service is up and running

Endpoints:

  IMPORT CSV File:

    http://localhost:8080/api/csv/upload

    @RequestParam("file")
  
 
  EXPORT csv file
  
    http://localhost:8080/api/csv/download
    
    @RequestParam(required = false) startDate,
    @RequestParam(required = false) endDate
    
  GET ACCOUNT BALANCE
    
    http://localhost:8080/api/csv/balance
    
    @RequestParam accountNr,
    @RequestParam(required = false) dateFrom,
    @RequestParam(required = false) dateTo
    
  Additional end point - GET ALL ACCOUNTS DATA in Json format
  
    http://localhost:8080/api/csv/accounts
