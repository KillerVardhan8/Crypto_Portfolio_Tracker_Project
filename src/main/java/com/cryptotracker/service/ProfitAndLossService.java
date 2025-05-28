package com.cryptotracker.service;

import com.crypto.entity.ProfitAndLoss;
import com.crypto.repository.ProfitAndLossRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring service component
public class ProfitAndLossService {

    private final ProfitAndLossRepository repository; // Repository for data access

    // Constructor injection of the repository
    public ProfitAndLossService(ProfitAndLossRepository repository) {
        this.repository = repository;
    }

    // Saves a ProfitAndLoss entity to the database
    public ProfitAndLoss save(ProfitAndLoss profitAndLoss) {
        return repository.save(profitAndLoss);
    }

    // Retrieves all ProfitAndLoss records from the database
    public List<ProfitAndLoss> findAll() {
        return repository.findAll();
    }

    // Finds a ProfitAndLoss entity by its ID
    public Optional<ProfitAndLoss> findById(Long id) {
        return repository.findById(id);
    }

    // Deletes a ProfitAndLoss entity by its ID
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}