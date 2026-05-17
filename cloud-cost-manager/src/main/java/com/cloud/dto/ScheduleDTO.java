package com.cloud.dto;

/**
 * Schedule Data Transfer Object
 */
public class ScheduleDTO {
    private Integer savingHour;
    private Integer performanceHour;
    private Boolean enabled;

    public ScheduleDTO() {}

    public ScheduleDTO(Integer savingHour, Integer performanceHour, Boolean enabled) {
        this.savingHour = savingHour;
        this.performanceHour = performanceHour;
        this.enabled = enabled;
    }

    public Integer getSavingHour() { return savingHour; }
    public void setSavingHour(Integer savingHour) { this.savingHour = savingHour; }

    public Integer getPerformanceHour() { return performanceHour; }
    public void setPerformanceHour(Integer performanceHour) { this.performanceHour = performanceHour; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}
