package co.Nitish.paymentSystem.mapper;

import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.model.Account;

public class AccountMapper {

    public static Account AccountDtoToAccount(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Account account = new Account();
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setPhoneNumber(accountDto.getPhoneNumber());
        account.setEmail(accountDto.getEmail());
        // Note: Auto-generated fields are NOT set here
        return account;
    }

    public static AccountInfoDto AccountToAccountInfoDto(Account account) {
        if (account == null) {
            return null;
        }

        AccountInfoDto dto = new AccountInfoDto();
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setEmail(account.getEmail());
        dto.setUpiId(account.getUpiId());
        dto.setCardNumber(maskCardNumber(account.getCardNumber()));
        dto.setCardExpiry(account.getCardExpiry());
        dto.setCreatedAt(account.getCreatedAt());

        return dto;
    }

    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 16) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}