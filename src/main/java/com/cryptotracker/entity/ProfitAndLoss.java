package com.crypto.entity;

import jakarta.persistence.*;

// Entity representing the profit and loss record for a user or transaction
@Entity
@Table(name = "profit_and_loss")
public class ProfitAndLoss {

    // Primary key, auto-generated for each profit and loss entry
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Amount of profit associated with this record
    private Double profit;

    // Amount of loss associated with this record
    private Double loss;

    // Default constructor required by JPA
    public ProfitAndLoss() {}

    // Constructor to initialize profit and loss fields
    public ProfitAndLoss(Double profit, Double loss) {
        this.profit = profit;
        this.loss = loss;
    }

    // Getter for unique identifier of this record
    public Long getId() {
        return id;
    }

    // Getter for profit amount
    public Double getProfit() {
        return profit;
    }

    // Setter for profit amount
    public void setProfit(Double profit) {
        this.profit = profit;
    }

    // Getter for loss amount
    public Double getLoss() {
        return loss;
    }

    // Setter for loss amount
    public void setLoss(Double loss) {
        this.loss = loss;
    }
}