package com.crypto.dto;

// DTO (Data Transfer Object) to encapsulate profit and loss summary data for API responses
public class ProfitAndLossResponse {
    // Total amount invested by the user across all holdings
    private double totalInvested;
    // Total current market value of all user holdings
    private double totalCurrentValue;
    // Net profit or loss calculated as (current value - invested amount)
    private double totalProfitLoss;

    // Constructor to initialize the profit and loss response with summary values
    public ProfitAndLossResponse(double totalInvested, double totalCurrentValue, double totalProfitLoss) {
        this.totalInvested = totalInvested;
        this.totalCurrentValue = totalCurrentValue;
        this.totalProfitLoss = totalProfitLoss;
    }

    // Getter for total invested amount
    public double getTotalInvested() { return totalInvested; }

    // Setter for total invested amount
    public void setTotalInvested(double totalInvested) { this.totalInvested = totalInvested; }

    // Getter for current value of holdings
    public double getTotalCurrentValue() { return totalCurrentValue; }

    // Setter for current value of holdings
    public void setTotalCurrentValue(double totalCurrentValue) { this.totalCurrentValue = totalCurrentValue; }

    // Getter for net profit or loss
    public double getTotalProfitLoss() { return totalProfitLoss; }

    // Setter for net profit or loss
    public void setTotalProfitLoss(double totalProfitLoss) { this.totalProfitLoss = totalProfitLoss; }
}