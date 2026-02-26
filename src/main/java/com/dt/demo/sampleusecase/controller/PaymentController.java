package com.dt.demo.sampleusecase.controller;

import com.dt.demo.sampleusecase.domain.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);


    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> pay(@RequestBody Payment req) throws Exception {
        Date date = new Date();
        log.info("Payment initiated for user={}, amount={}, method={}, date={}", req.getUserId(), req.getAmount(), req.getMethod(), date);

        // Simulate processing time
        Thread.sleep(500 + new Random().nextInt(1500));

        // Simulate random failure (great for demos)
        if (Math.random() < 0.2) {
            log.error("Payment failed for user={}", req.getUserId());
            throw new RuntimeException("Payment gateway error");
        }

        log.info("Payment success for user={}", req.getUserId());

        Map<String, Object> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("transactionId", UUID.randomUUID().toString());
        result.put("method", req.getMethod());
        result.put("Date", date);

        return ResponseEntity.ok(result);
    }

}
