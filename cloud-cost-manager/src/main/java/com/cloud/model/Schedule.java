package com.cloud.model;

public class Schedule {
    private Long id;
    private Integer savingHour;
    private Integer performanceHour;
    private Boolean enabled;
    private Long createdAt;
    private Long updatedAt;

    public Schedule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSavingHour() { return savingHour; }
    public void setSavingHour(Integer savingHour) { this.savingHour = savingHour; }

    public Integer getPerformanceHour() { return performanceHour; }
    public void setPerformanceHour(Integer performanceHour) { this.performanceHour = performanceHour; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
}
