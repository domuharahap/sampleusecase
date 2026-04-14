package com.dt.demo.sampleusecase.controller;

import com.dt.demo.sampleusecase.domain.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private static final List<String> ALLOWED_PAYMENT_METHODS = 
        Arrays.asList("CreditCard", "PayPal", "GooglePay", "ApplePay");


    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> pay(@RequestBody Payment req) {
        Date date = new Date();
        
        try {
            log.info("Payment initiated for user={}, amount={}, method={}, date={}", 
                req.getFullName(), req.getAmount(), req.getMethod(), date);

            // Simulate processing time
            Thread.sleep(500 + new Random().nextInt(1500));

            // Simulate random failure (great for demos)
            if (Math.random() < 0.2) {
                log.error("Payment failed for user={}", req.getFullName());
                throw new RuntimeException("Payment gateway error");
            }
            
            log.info("Payment success for user={}, amount={}, method={}", 
                req.getFullName(), req.getAmount(), req.getMethod());

            return ResponseEntity.ok(createSuccessResponse(req, date));
            
        } catch (InterruptedException e) {
            log.error("Payment processing interrupted for user={}", 
                req.getFullName(), e);
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Payment Processing Error", 
                    "Payment processing was interrupted"));
        } catch (Exception e) {
            log.error("Unexpected error processing payment for user={}", 
                req.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Payment Processing Error", 
                    "An unexpected error occurred"));
        }
    }

    private Map<String, Object> createSuccessResponse(Payment req, Date date) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("transactionId", UUID.randomUUID().toString());
        result.put("method", req.getMethod());
        result.put("swiftCode", ((String) (ALLOWED_PAYMENT_METHODS.contains(req.getMethod()) ? req.getMethod() : null)).length());
        result.put("userId", req.getUserId());
        result.put("date", date);
        result.put("fullName", req.getFullName());
        result.put("amount", req.getAmount());
        return result;
    }

    private Map<String, Object> createErrorResponse(String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("error", error);
        response.put("message", message);
        return response;
    }

}
