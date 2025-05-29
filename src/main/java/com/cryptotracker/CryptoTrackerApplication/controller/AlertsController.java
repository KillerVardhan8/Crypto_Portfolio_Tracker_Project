package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.dto.AlertsDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Alerts;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;
import com.cryptotracker.CryptoTrackerApplication.service.AlertsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alerts")
public class AlertsController {

    private static final Logger logger = LoggerFactory.getLogger(AlertsController.class);

    @Autowired
    private AlertsService alertsService;

    @Autowired
    private UserRepository userRepository; 
    
    @PostMapping("/createAlert")
    public ResponseEntity<Map<String, String>> saveNewAlert(@RequestBody AlertsDTO alertdto) {
        try {
            alertsService.addAlert(alertdto);
            return ResponseEntity.ok(Map.of("message", "Alert created successfully."));
        } catch (Exception e) {
            logger.error("Error creating alert: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Unable to create alert: " + e.getMessage()));
        }
    }
    // Created a post endpoint in which the user can add alert.

    @GetMapping("/getAlerts/{id}")
    public ResponseEntity<?> getAlerts(@PathVariable Long id) {
        try {
        	User user = userRepository.findById(id).orElseThrow(()->new Exception("User not found"));
            List<Alerts> alertsList = alertsService.getAlerts(user.getUserId());
            return ResponseEntity.ok(alertsList);
        } catch (Exception e) {
            logger.error("Error fetching alerts for user {}: ", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unable to get alerts for userID " + id));
        }
    }
    // Created a get endpoint in which the user can get the list of all alerts by sending userID as params .

    @GetMapping("/getAll")
    public ResponseEntity<List<Alerts>> getAllAlerts() {
        List<Alerts> alertsList = alertsService.getAllAlerts();
        return ResponseEntity.ok(alertsList);
    }
    // Created a get endpoint in which the can get all his created alerts. 

    @GetMapping("/getPending/{id}")
    public ResponseEntity<?> getPendingAlerts(@PathVariable Long id){
        List<Alerts> alertsList = alertsService.getPendingAlerts(id);
        return ResponseEntity.ok(alertsList);
    }
    // Created a get endpoint in which the user can get the list of all the pending alerts.
    
    @GetMapping("/getTriggered/{id}")
    public ResponseEntity<?> getTriggeredAlerts(@PathVariable Long id){
        List<Alerts> alertsList = alertsService.getTriggeredAlerts(id);
        return ResponseEntity.ok(alertsList);
    }
    // Created a get endpoint in which the user can get the list of all the triggered alerts.
}
