package co.Nitish.paymentSystem.mapper;


import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import co.Nitish.paymentSystem.dto.UpiTransactionDto;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.model.UpiTransaction;
import co.Nitish.paymentSystem.repository.AccountRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class UpiTransactionMapper {


    public static UpiTransaction mapToUpiTransaction(UpiPaymentRequestDto upiPaymentRequestDto, Account sender){
        return new UpiTransaction(null,
                sender.getAccountHolderName(),
                upiPaymentRequestDto.getFromUpiId(),
               upiPaymentRequestDto.getToUpiId(),
                upiPaymentRequestDto.getAmount(),
                upiPaymentRequestDto.getRemark(),
                LocalDateTime.now()
        );
    }
     public static UpiTransactionDto mapToUpiTransactionDto(UpiTransaction upiTransaction){
         return new UpiTransactionDto(
                 upiTransaction.getUpiHolderName(),
                 upiTransaction.getFromUpiId(),
                 upiTransaction.getToUpiId(),
                 upiTransaction.getAmount(),
                 upiTransaction.getRemark(),
                 upiTransaction.getTransactionTime()
         );
     }
}
