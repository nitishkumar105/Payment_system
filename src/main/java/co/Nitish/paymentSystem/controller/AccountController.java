package co.Nitish.paymentSystem.controller;


import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {

     public final AccountService accountService;
     AccountController(AccountService accountService){
          this.accountService=accountService;
     }
      @GetMapping
      public String hello(){
          return "Hello I'm default Rest api to account";
      }
     @PostMapping("/create")
      public ResponseEntity<AccountInfoDto> createAccount(@RequestBody AccountDto accountDto){
             AccountInfoDto accountInfoDto=accountService.createAccount(accountDto);
            return   ResponseEntity.status(HttpStatus.CREATED).body(accountInfoDto);

      }
        @GetMapping("/getAllAccount")
       public ResponseEntity<List<AccountInfoDto>> getAllAccount(){
             List<AccountInfoDto> accountInfoDtoList=accountService.getAllAccount();
             return ResponseEntity.ok(accountInfoDtoList);
       }
        @GetMapping("/getAccount")
        public ResponseEntity<AccountInfoDto> getAccountByAccountNumber(@RequestParam String accountNumber ){
           return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
        }
}
