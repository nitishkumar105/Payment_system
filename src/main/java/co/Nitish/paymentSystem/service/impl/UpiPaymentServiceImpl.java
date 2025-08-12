package co.Nitish.paymentSystem.service.impl;

import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import co.Nitish.paymentSystem.dto.UpiTransactionDto;
import co.Nitish.paymentSystem.mapper.UpiTransactionMapper;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.model.UpiTransaction;
import co.Nitish.paymentSystem.repository.AccountRepository;
import co.Nitish.paymentSystem.repository.UpiTransactionRepository;
import co.Nitish.paymentSystem.service.UpiPaymentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpiPaymentServiceImpl implements UpiPaymentService {


      public final UpiTransactionRepository upiTransactionRepository;
      public final AccountRepository accountRepository;
      UpiPaymentServiceImpl(UpiTransactionRepository upiTransactionRepository,AccountRepository accountRepository){
          this.upiTransactionRepository=upiTransactionRepository;
          this.accountRepository=accountRepository;
      }
    @Override
    public String pay(UpiPaymentRequestDto upiPaymentRequestDto) {
        // Step 1: Find sender and receiver
        Account sender = accountRepository.findByUpiId(upiPaymentRequestDto.getFromUpiId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findByUpiId(upiPaymentRequestDto.getToUpiId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // Step 2: Check balance
        if (sender.getBalance() < upiPaymentRequestDto.getAmount()) {
            throw new RuntimeException("Insufficient balance in sender's account.");
        }

        // Step 3: Transfer money
        sender.setBalance(sender.getBalance() - upiPaymentRequestDto.getAmount());
        receiver.setBalance(receiver.getBalance() + upiPaymentRequestDto.getAmount());

        // Step 4: Save updated accounts
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Step 5: Create & save transaction
        var upiTransaction = UpiTransactionMapper.mapToUpiTransaction(
                upiPaymentRequestDto, sender, receiver
        );
        upiTransactionRepository.save(upiTransaction);

        return "Transaction successful from " + sender.getUpiId() +
                " to " + receiver.getUpiId() +
                " for amount: " + upiPaymentRequestDto.getAmount();
    }

}
