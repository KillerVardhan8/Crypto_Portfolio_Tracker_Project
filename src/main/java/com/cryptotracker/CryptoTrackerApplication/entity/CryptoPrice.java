package com.cryptotracker.CryptoTrackerApplication.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class CryptoPrice {
	
	
	@Id
	private String symbol;
	private double price;
	private LocalDateTime timestamp;

	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	
	public CryptoPrice() {
		
	}
	
	
	public CryptoPrice(String symbol, double price, LocalDateTime timestamp) {
		this.symbol = symbol;
		this.price = price;
		this.timestamp = timestamp;
		
		/*CryptoPrice is a JPA Entity representing the prices of a crypto currency. 
		 * It contains the symbol(Primary key), current price and timestamp of the last update. */
	}
	
}
