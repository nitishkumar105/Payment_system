package co.Nitish.paymentSystem.mapper;

import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.model.Account;

public class AccountMapper {

    public static Account AccountDtoToAccount(AccountDto accountDto){
         return  new Account(null,
                 accountDto.getAccountHolderName(),
                 accountDto.getAccountNumber(),
                 accountDto.getAmount(),
                 accountDto.getUpiId(),
                 accountDto.getCardNumber(),
                 accountDto.getCardExpiry(),
                 accountDto.getCardCvv()
         );
    }
     public static AccountInfoDto AccountToAccountInfoDto(Account account){
          return new AccountInfoDto(
                  account.getAccountHolderName(),
                  account.getUpiId(),
                  account.getCardNumber(),
                  account.getAccountNumber(),
                  account.getBalance()
          );
     }
}
