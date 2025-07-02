package com.example.traditionalmarket.utils;

import com.example.traditionalmarket.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarketDataInitializer implements CommandLineRunner {

    private final MarketService marketService;

    @Override
    public void run(String... args) {
        marketService.importFromJsonFile();
    }
}
