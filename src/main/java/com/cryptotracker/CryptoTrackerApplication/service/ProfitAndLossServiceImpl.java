package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.entity.*;
import com.cryptotracker.CryptoTrackerApplication.repository.*;
import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfitAndLossServiceImpl implements ProfitAndLossService {

    @Autowired
    private CryptoHoldingRepository holdingRepo;
    @Autowired
    private CryptoPriceRepository priceRepo;
    @Autowired
    private ProfitAndLossRepository pnlRepo;

    // Scheduled batch update for all users
    @Scheduled(fixedRate = 60000)
    public void calculateAndPersistAll() {
        List<Long> userIds = holdingRepo.findAllDistinctUserIds();
        for (Long userId : userIds) {
            calculateAndPersist(userId);
        }
    }

    // Calculate/save PnL for a user
    public ProfitAndLossResponseDTO calculateAndPersist(Long userId) {
        List<CryptoHolding> holdings = holdingRepo.findByUserId(userId);

        double totalInvested = 0.0;
        double totalCurrentValue = 0.0;

        for (CryptoHolding holding : holdings) {
            double qty = holding.getQuantityHeld();
            double buyPrice = holding.getBuyPrice();
            String symbol = holding.getSymbol();

            totalInvested += qty * buyPrice;

            CryptoPrice price = priceRepo.findById(symbol).orElse(null);
            double currentPrice = (price != null) ? price.getPrice() : 0.0;
            totalCurrentValue += qty * currentPrice;
        }

        double profitLoss = totalCurrentValue - totalInvested;
        PriceStatus status = (profitLoss > 0) ? PriceStatus.PROFIT 
                            : (profitLoss < 0 ? PriceStatus.LOSS : PriceStatus.NEUTRAL);

        // Get existing or create new entity
        List<ProfitAndLoss> existingRecords = pnlRepo.findByUserId(userId);
        ProfitAndLoss entity = existingRecords.isEmpty() ? 
                            new ProfitAndLoss() : 
                            existingRecords.get(0);

        entity.setUserId(userId);
        entity.setTotalPortfolio(profitLoss);
        entity.setPriceStatus(status);
        pnlRepo.save(entity);

        return new ProfitAndLossResponseDTO(userId, totalInvested, totalCurrentValue, profitLoss, status);
    }

    // Get latest record for user
    public ProfitAndLossResponseDTO getLatest(Long userId) {
        List<ProfitAndLoss> records = pnlRepo.findByUserId(userId);
        if (records.isEmpty()) {
            return null;
        }
        ProfitAndLoss latest = records.get(0);
        return new ProfitAndLossResponseDTO(
            latest.getUserId(),
            null,  // These would need to be stored if required
            null, 
            latest.getTotalPortfolio(),
            latest.getPriceStatus()
        );
    }
}