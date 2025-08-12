package co.Nitish.paymentSystem.mapper;


import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import co.Nitish.paymentSystem.dto.UpiTransactionDto;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.model.UpiTransaction;
import co.Nitish.paymentSystem.repository.AccountRepository;

import java.time.LocalDateTime;

public class UpiTransactionMapper {


    public static UpiTransaction mapToUpiTransaction(UpiPaymentRequestDto upiPaymentRequestDto, Account sender, Account receiver){
        return new UpiTransaction(null,
                sender.getAccountHolderName(),
                sender.getUpiId(),
                receiver.getUpiId(),
                upiPaymentRequestDto.getAmount(),
                "payment to swiggy",
                LocalDateTime.now()
        );
    }
}
