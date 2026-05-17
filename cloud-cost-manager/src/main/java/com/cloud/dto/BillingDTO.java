package com.cloud.dto;

/**
 * Billing Data Transfer Object
 */
public class BillingDTO {
    private Long id;
    private String month;
    private Double spent;
    private Double budget;
    private Double savings;
    private Long departmentId;

    public BillingDTO() {}

    public BillingDTO(Long id, String month, Double spent, Double budget, Double savings) {
        this.id = id;
        this.month = month;
        this.spent = spent;
        this.budget = budget;
        this.savings = savings;
    }

    public BillingDTO(Long id, String month, Double spent, Double budget, Double savings, Long departmentId) {
        this.id = id;
        this.month = month;
        this.spent = spent;
        this.budget = budget;
        this.savings = savings;
        this.departmentId = departmentId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public Double getSavings() { return savings; }
    public void setSavings(Double savings) { this.savings = savings; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}
