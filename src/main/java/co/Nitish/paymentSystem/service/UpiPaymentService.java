package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.dto.UpiDebitTransactionDto;
import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import co.Nitish.paymentSystem.dto.UpiTransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UpiPaymentService {
      String pay(UpiPaymentRequestDto upiPaymentRequestDto);
      List<UpiTransactionDto> getAllPaymentList();
      List<UpiDebitTransactionDto> getDebitedTransaction(String upiId);
}
