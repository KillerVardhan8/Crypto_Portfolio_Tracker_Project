package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CryptoPriceServiceImpl implements CryptoPriceService {

    private static final Logger logger = LoggerFactory.getLogger(CryptoPriceService.class);

    @Autowired
    private CryptoPriceRepository priceRepo;

    private static final Map<String, Double> basePrices = Map.of(
        "BTC", 25000.0,
        "ETH", 1800.0,
        "DOGE", 0.07,
        "SOL", 2345.0
    );

    @Scheduled(fixedRate = 60000)
    public void simulatePrices() {
        logger.info("Simulating crypto prices...");

        List<CryptoPrice> prices = new ArrayList<>();
        Random rand = new Random();

        for (String symbol : basePrices.keySet()) {
            double base = basePrices.get(symbol);
            double variation = (rand.nextDouble() - 0.5) * 0.1;
            double newPrice = base + (base * variation);

            CryptoPrice price = new CryptoPrice(symbol, Math.round(newPrice * 100.0) / 100.0, LocalDateTime.now());
            prices.add(price);
        }

        logger.debug("Simulated prices: {}", prices);
        priceRepo.saveAll(prices);
        logger.info("Prices saved to the database.");
    }

    public List<CryptoPrice> getAllPrices() {
        logger.info("Fetching all crypto prices...");
        List<CryptoPrice> result = priceRepo.findAll();
        logger.debug("Fetched prices: {}", result);
        return result;
    }

    public CryptoPrice getPrice(String symbol) {
        logger.info("Fetching price for symbol: {}", symbol);
        return priceRepo.findById(symbol.toUpperCase()).orElse(null);
        
        /*This class is responsible for simulating and managing crypto currency prices. It uses a 
         * scheduled task to periodically generate random price for Crypto currencies. And aso
         *  it stores them in database and provide methods to retrieve all prices or a specific one.  */
    }
}
