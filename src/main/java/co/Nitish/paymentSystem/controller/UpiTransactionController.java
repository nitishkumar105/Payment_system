package co.Nitish.paymentSystem.controller;

import co.Nitish.paymentSystem.dto.UpiPaymentRequestDto;
import co.Nitish.paymentSystem.dto.UpiTransactionDto;
import co.Nitish.paymentSystem.service.UpiPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/upi")
public class UpiTransactionController {
     public final UpiPaymentService upiPaymentService;
     UpiTransactionController(UpiPaymentService upiPaymentService){
         this.upiPaymentService=upiPaymentService;
     }


     // pay through upi
     @PostMapping("/pay")
     public ResponseEntity<String> pay(@RequestBody UpiPaymentRequestDto upiPaymentRequestDto){
         String result= upiPaymentService.pay(upiPaymentRequestDto);
          return ResponseEntity.status(HttpStatus.CREATED).body(result);
     }
}
