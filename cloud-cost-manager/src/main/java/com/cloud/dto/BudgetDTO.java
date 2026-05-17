package com.cloud.dto;

/**
 * Budget Data Transfer Object
 */
public class BudgetDTO {
    private Double total;
    private Double spent;
    private Double pct;
    private Integer alertThreshold;
    private Integer killThreshold;
    private String strategy;
    private Long organizationId;
    private Long departmentId;
    private Long employeeId;

    public BudgetDTO() {}

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }

    public Double getPct() { return pct; }
    public void setPct(Double pct) { this.pct = pct; }

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
}
