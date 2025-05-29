package com.cryptotracker.CryptoTrackerApplication.service;

import java.util.List;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;

public interface CryptoPriceService {
	
	public void simulatePrices();
	public List<CryptoPrice> getAllPrices();
	public CryptoPrice getPrice(String symbol);

}

//This interface includs three methods to simualte prices, fetching all prices and getting price by symbol