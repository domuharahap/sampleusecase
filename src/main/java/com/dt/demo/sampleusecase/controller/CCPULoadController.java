package com.dt.demo.sampleusecase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CCPULoadController {

    @GetMapping("/use-case-cpuload")
    public String startCPULoad() {
        long startTime = System.currentTimeMillis();
        long duration = 6 * 60 * 60 * 1000; // 6 hours in milliseconds

        while (System.currentTimeMillis() - startTime < duration) {
            // Perform some CPU-intensive operations here
            // You can use a loop, mathematical operations, or any CPU-intensive task

            // Gradually increase the CPU load by adding more work over time
            int workMultiplier = (int) ((System.currentTimeMillis() - startTime) / 60000); // Increase work every minute

            for (int i = 0; i < 100 * workMultiplier; i++) {
                // Simulate CPU-intensive work
                double result = Math.sin(i) * Math.cos(i);
            }
            try {
                Thread.sleep(1000); // Sleep for 1 second to slow down the loop (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "CPU load generation completed.";
    }

}
