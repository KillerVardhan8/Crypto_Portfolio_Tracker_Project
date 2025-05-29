package com.cryptotracker.CryptoTrackerApplication.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.repository.AlertsRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;

import jakarta.transaction.Transactional;
import com.cryptotracker.CryptoTrackerApplication.dto.AlertsDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Alerts;
import com.cryptotracker.CryptoTrackerApplication.entity.AlertsDirection;
import com.cryptotracker.CryptoTrackerApplication.entity.AlertsStatus;

@Service
public class AlertsServiceImpl implements AlertsService {

    private static final Logger logger = LoggerFactory.getLogger(AlertsServiceImpl.class);

    @Autowired
    private AlertsRepository alertRepository;

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Alerts addAlert(AlertsDTO dto) {
        // Creates a new alert using data from the DTO and saves it to the database.
        Alerts alert = new Alerts(dto.getUserId(), dto.getSymbol(), dto.getTriggerPrice(), dto.getDirection());
        logger.debug("Alert Created: {}", alert.getId());
        return alertRepository.save(alert);
    }

    @Override
    public List<Alerts> getAlerts(Long userID) {
        // Fetches and returns all alerts for a specific user by userID.
    	List<Alerts> userAlertList = alertRepository.findByUserId(userID);
        logger.debug("Alerts count for userID {} : {}", userID, userAlertList.size());
        return userAlertList;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkAlerts() {
        // This scheduled method runs every 60 seconds to check all pending alerts.
        logger.debug("Scheduler started at {}", LocalDateTime.now());
        try {
            List<Alerts> alerts = alertRepository.findByStatus(AlertsStatus.PENDING);
            logger.debug("Found {} pending alerts", alerts.size());

            for (Alerts alert : alerts) {
                try {
                    // For each pending alert, check if the trigger condition is met.
                    logger.debug("Processing alert ID: {}", alert.getId());
                    String symbol = alert.getSymbol();
                    Double triggerPrice = alert.getTriggerPrice();
                    Optional<CryptoPrice> priceOptional = cryptoPriceRepository.findBySymbol(symbol);

                    if (priceOptional.isPresent()) {
                        Double currentPrice = priceOptional.get().getPrice();
                        // If the alert is for "Above" and the current price is above the trigger, it triggers the alert.
                        if (alert.getDirection() == AlertsDirection.Above && currentPrice > triggerPrice) {
                            updateTriggeredAlert(alert);
                        // If the alert is for "Below" and the current price is below the trigger, it triggers the alert.
                        } else if (alert.getDirection() == AlertsDirection.Below && currentPrice < triggerPrice) {
                            updateTriggeredAlert(alert);
                        }
                    } else {
                        // Log a warning if no price is found for the symbol.
                        logger.warn("No price found for symbol: {}", symbol);
                    }
                } catch (Exception e) {
                    // Log any errors that occur while processing a specific alert.
                    logger.error("Error processing alert ID {}: ", alert.getId(), e);
                }
            }
        } catch (Exception e) {
            // Log any errors that occur in the scheduler itself.
            logger.error("Error in checkAlerts scheduler: ", e);
        }
        logger.debug("Scheduler completed at {}", LocalDateTime.now());
    }

    private void updateTriggeredAlert(Alerts alert) {
        // Updates the alert status to TRIGGERED and sets the triggered time.
        alert.setStatus(AlertsStatus.TRIGGERED);
        alert.setTriggeredAt(LocalDateTime.now());
        alertRepository.save(alert);
        logger.debug("Alert triggered alertID:{}", alert.getId());

        // Get the details of user and send an email.
        User user = userRepository.findById(alert.getUserId())
            .orElse(null);
        
        if (user != null && user.getEmail() != null) {
            String email = user.getEmail();
            String subject = "Crypto Alert Triggered!";
            String body = "Your alert for " + alert.getSymbol() + " at price " + alert.getTriggerPrice() + " has been triggered.";
            emailService.sendEmail(email, subject, body);
            logger.debug("Email sent to user {} with userID {}", user.getName(), user.getUserId());
        }
    }

    @Override
    public List<Alerts> getAllAlerts() {
        // Returns a list of all alerts of all users.
    	List<Alerts> alerts = alertRepository.findAll();
        logger.debug("All alerts count: {}", alerts.size());
        return alerts;
    }
    
    @Override
    public List<Alerts> getPendingAlerts(Long userId){
        // Returns all alerts of the user that are still pending.
        logger.debug("Pending alerts count: {} for userID : {}", alertRepository.findAll().size(), userId);
        return alertRepository.findByUserIdAndStatus(userId, AlertsStatus.PENDING);
    }

    @Override
    public List<Alerts> getTriggeredAlerts(Long userId){
        // Returns all alerts of the user that have been triggered.
        logger.debug("Triggered alerts count: {} for userID : {}", alertRepository.findAll().size(), userId);
        return alertRepository.findByUserIdAndStatus(userId, AlertsStatus.TRIGGERED);
    }
}
