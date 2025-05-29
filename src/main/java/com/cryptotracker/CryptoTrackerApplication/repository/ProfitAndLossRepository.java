package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repository to access profit_and_loss table
public interface ProfitAndLossRepository extends JpaRepository<ProfitAndLoss, Long> {
    List<ProfitAndLoss> findByUserId(Long userId);
}