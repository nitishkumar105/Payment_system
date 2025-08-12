package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UpiPaymentService {
      String pay(UpiPaymentRequestDto upiPaymentRequestDto);

}
