package com.cloud.model;

/**
 * Budget Entity - Represents the cloud budget configuration
 */
public class Budget {

    private Long id;

    private Double total;

    private Double spent;

    private Integer alertThreshold;

    private Integer killThreshold;

    private String strategy;

    private Long organizationId;

    private Long departmentId;

    private Long employeeId;

    private Long createdAt = System.currentTimeMillis();

    private Long updatedAt = System.currentTimeMillis();

    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

    public Budget() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }

    public Integer getAlertThreshold() { return alertThreshold; }
    public void setAlertThreshold(Integer alertThreshold) { this.alertThreshold = alertThreshold; }

    public Integer getKillThreshold() { return killThreshold; }
    public void setKillThreshold(Integer killThreshold) { this.killThreshold = killThreshold; }

    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    public Double getPercentage() {
        return (spent / total) * 100;
    }
}
