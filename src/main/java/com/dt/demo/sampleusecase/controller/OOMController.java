package com.dt.demo.sampleusecase.controller;

import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class OOMController {
    private static final Logger logger = LoggerFactory.getLogger(OOMController.class);

    private static final List<byte[]> MEMORY_HOLDER = new ArrayList<>();
    private final List<Thread> CPU_WORKERS = new ArrayList<>();

    @GetMapping("/")
    public String api() {
        Faker faker = new Faker();
        return "/use-case return fake name, <br/> /use-case-oom will make memory exhausted"+ faker.name().fullName();
    }

    @GetMapping("/use-case")
    public String getFakeName() {
        try {
            Faker faker = new Faker();
            return "hallo "+ faker.name().fullName();
        }catch (OutOfMemoryError oom) {
            logger.error("{} ERROR ", oom.getCause());
            return oom.getMessage();
        }
    }

    @GetMapping("/use-case-oom")
    public String leakMemory() {
        return createAllocateMemory();
    }

    private String createAllocateMemory() {
        byte[] payload = new byte[5 * 1024 * 1024];

        MEMORY_HOLDER.add(payload);

        //return "Allocated memory blocks: " + MEMORY_HOLDER.size();

        int totalBlocks = MEMORY_HOLDER.size();
        long estimatedHeapUsed = totalBlocks * 5; // Simple math for display

        return "<html>" +
                "<head><title>Memory Leak Simulator</title></head>" +
                "<body style='font-family: sans-serif; text-align: center; padding-top: 50px;'>" +
                "   <h2>System Status</h2>" +
                "   <div style='margin-bottom: 20px; padding: 15px; background: #f4f4f4; display: inline-block; border-radius: 8px;'>" +
                "       <strong>Allocated memory blocks:</strong> " + totalBlocks + "<br>" +
                "       <strong>Estimated Leak Size:</strong> " + estimatedHeapUsed + " MB" +
                "   </div>" +
                "   <form action='/api/use-case-oom' method='get'>" +
                "       <p>Click below to simulate a 'New User Login' (Allocates 5MB)</p>" +
                "       <button type='submit' style='padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer;'>" +
                "           Login New User" +
                "       </button>" +
                "   </form>" +
                "   <p style='color: red; margin-top: 20px;'>Warning: Repeated clicks will trigger a JVM OutOfMemoryError.</p>" +
                "</body>" +
                "</html>";
    }

    @GetMapping("/use-case-cpu")
    @ResponseBody
    public String consumeCpu() {
        return startCpuLoad();
    }

    private String startCpuLoad() {
        // Spin up a new thread that performs heavy math in a loop
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Perform heavy calculations to keep the CPU core busy
                Math.tan(Math.atan(Math.tan(Math.atan(Math.random()))));
            }
        });

        worker.setName("CPU-Consumer-" + CPU_WORKERS.size());
        worker.start();
        CPU_WORKERS.add(worker);

        return "<html>" +
                "<head><title>CPU Stress Test</title></head>" +
                "<body style='font-family: sans-serif; text-align: center; padding-top: 50px;'>" +
                "   <h2 style='color: #d9534f;'>CPU Load Generator</h2>" +
                "   <div style='margin-bottom: 20px; padding: 15px; background: #fff3cd; display: inline-block; border-radius: 8px; border: 1px solid #ffeeba;'>" +
                "       <strong>Active CPU Threads:</strong> " + CPU_WORKERS.size() + "<br>" +
                "       <strong>System Impact:</strong> Each click adds 1 thread at 100% usage on one core." +
                "   </div>" +
                "   <form action='/api/use-case-cpu' method='get'>" +
                "       <p>Simulate a heavy background process or 'Complex Data Export'</p>" +
                "       <button type='submit' style='padding: 10px 20px; background-color: #dc3545; color: white; border: none; border-radius: 5px; cursor: pointer;'>" +
                "           Trigger Heavy CPU Task" +
                "       </button>" +
                "   </form>" +
        //        "   <p style='margin-top: 20px;'><a href='/api/cpu/stop' style='color: #007bff;'>Stop All CPU Threads</a></p>" +
                "</body>" +
                "</html>";
    }

}
