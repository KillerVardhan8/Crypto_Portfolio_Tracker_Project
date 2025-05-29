package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.service.CryptoPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/price")
public class CryptoPriceController {

    private static final Logger logger = LoggerFactory.getLogger(CryptoPriceController.class);

    @Autowired
    private CryptoPriceServiceImpl priceService;

    @GetMapping("/all")
    public List<CryptoPrice> getAll() {
        List<CryptoPrice> list = priceService.getAllPrices();
        logger.info("Fetched all prices from service: {}", list);
        return list;
    }

    @GetMapping("/{symbol}")
    public CryptoPrice getOne(@PathVariable String symbol) {
        CryptoPrice price = priceService.getPrice(symbol);
        logger.info("Fetched price for symbol {}: {}", symbol, price);
        return price;
        
        /*CryptoPriceController handles Rest API request related to 
         * currency prices. It provides end points to retrieve all prices and 
         * fetch a specific price by symbol. */
    }
}  


