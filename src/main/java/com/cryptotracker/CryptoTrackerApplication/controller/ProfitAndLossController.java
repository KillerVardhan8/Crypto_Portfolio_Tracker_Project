package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.dto.*;
import com.cryptotracker.CryptoTrackerApplication.service.ProfitAndLossServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profit-and-loss")
public class ProfitAndLossController {

    @Autowired
    private ProfitAndLossServiceImpl pnlService;

    // Call this to calculate and update the DB, and return PnL for a user
    @PostMapping("/calculate/{userId}")
    public ProfitAndLossResponseDTO calculateAndPersist(@PathVariable Long userId) {
        return pnlService.calculateAndPersist(userId);
    }

    // Get last saved profit and loss result for a user
    @GetMapping("/{userId}")
    public ProfitAndLossResponseDTO getLatest(@PathVariable Long userId) {
        return pnlService.getLatest(userId);
    }
}