package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

//This interface allows us to perform database operations like save,file,delete
//It extends JpaRepository to inherit in-built CRUD Operations

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, String> {

}

//This interface extends the JpaRepository to inherit the built in CURD operations