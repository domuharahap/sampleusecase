package com.dt.demo.sampleusecase.controller;

import com.dt.demo.sampleusecase.domain.MovieQuoteService;
import com.dt.demo.sampleusecase.domain.User;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.dt.demo.sampleusecase.domain.UserGenerator.generateUser;

@RestController
@RequestMapping("/api")
public class OOMController {
    private static final List<byte[]> memoryLeakList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(OOMController.class);
    private static final MovieQuoteService movieQuoteService = new MovieQuoteService();

    private static final List<byte[]> MEMORY_HOLDER = new ArrayList<>();

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

        return "Allocated memory blocks: " + MEMORY_HOLDER.size();
    }


}
