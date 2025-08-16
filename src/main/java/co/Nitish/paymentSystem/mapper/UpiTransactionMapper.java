package co.Nitish.paymentSystem.mapper;


import co.Nitish.paymentSystem.dto.UpiCreditTransactionDto;
import co.Nitish.paymentSystem.dto.UpiDebitTransactionDto;
import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import co.Nitish.paymentSystem.dto.UpiTransactionDto;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.model.UpiTransaction;
import java.time.LocalDateTime;

public class UpiTransactionMapper {


    public static UpiTransaction mapToUpiTransaction(UpiPaymentRequestDto upiPaymentRequestDto, Account sender){
        return new UpiTransaction(null,
                sender.getAccountHolderName(),
                upiPaymentRequestDto.getFromUpiId(),
               upiPaymentRequestDto.getToUpiId(),
                upiPaymentRequestDto.getAmount(),
                upiPaymentRequestDto.getRemark(),
                "null",
                LocalDateTime.now()
        );
    }
     public static UpiTransactionDto mapToUpiTransactionDto(UpiTransaction upiTransaction) {
         return new UpiTransactionDto(
                 upiTransaction.getUpiHolderName(),
                 upiTransaction.getFromUpiId(),
                 upiTransaction.getToUpiId(),
                 upiTransaction.getAmount(),
                 upiTransaction.getRemark(),
                 upiTransaction.getStatus(),
                 upiTransaction.getTransactionTime()
         );
     }
         public static UpiTransaction mapDebitTransaction(UpiPaymentRequestDto dto, Account sender, Account receiver) {
             return new UpiTransaction(
                     null,
                     sender.getAccountHolderName(),
                     sender.getUpiId(),
                     receiver.getUpiId(),
                     dto.getAmount(),
                     dto.getRemark(),
                     "DEBITED",
                     LocalDateTime.now()
             );
         }

         public static UpiTransaction mapCreditTransaction(UpiPaymentRequestDto dto, Account sender, Account receiver) {
             return new UpiTransaction(
                     null,
                     receiver.getAccountHolderName(),
                     sender.getUpiId(),
                     receiver.getUpiId(),
                     dto.getAmount(),
                     "Received from " + sender.getUpiId(),
                     "CREDITED",
                     LocalDateTime.now()
             );

         }
    public static UpiTransaction mapFailedTransaction(UpiPaymentRequestDto dto, Account sender, Account receiver) {
        return new UpiTransaction(
                null,
                sender.getAccountHolderName(),
                sender.getUpiId(),
                receiver.getUpiId(),
                dto.getAmount(),
                dto.getRemark(),
                "FAILED",
                LocalDateTime.now()
        );
    }
    public static UpiDebitTransactionDto mapToDebitDto(UpiTransaction txn) {
        return new UpiDebitTransactionDto(
                txn.getToUpiId(),              // receiver UPI
                txn.getAmount(),
                txn.getStatus(),
                txn.getRemark(),
                txn.getTransactionTime()
        );
    }
     public static UpiCreditTransactionDto mapToCreditDto(UpiTransaction txn){
         return new UpiCreditTransactionDto(
                 txn.getFromUpiId(),   //sender upiId
                 txn.getAmount(),
                 txn.getStatus(),
                txn.getRemark(),
                 txn.getTransactionTime()
         );
     }

}
