package co.Nitish.paymentSystem.controller;


import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

     public final AccountService accountService;
     AccountController(AccountService accountService){
          this.accountService=accountService;
     }

     @PostMapping("/create")
      public ResponseEntity<AccountInfoDto> createAccount(@RequestBody AccountDto accountDto){
             AccountInfoDto accountInfoDto=accountService.createAccount(accountDto);
            return   ResponseEntity.status(HttpStatus.CREATED).body(accountInfoDto);

      }
}
