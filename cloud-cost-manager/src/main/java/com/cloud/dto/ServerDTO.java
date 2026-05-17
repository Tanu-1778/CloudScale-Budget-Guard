package com.cloud.dto;

/**
 * Server Data Transfer Object
 */
public class ServerDTO {
    private Long id;
    private String name;
    private String type;
    private String region;
    private String status;
    private String mode;
    private Double cpu;
    private Double mem;
    private Double cost;
    private String uptime;
    private Boolean isProtected;
    private Long departmentId;

    public ServerDTO() {}

    public ServerDTO(Long id, String name, String type, String region, String status, String mode,
                     Double cpu, Double mem, Double cost, String uptime, Boolean isProtected, Long departmentId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.region = region;
        this.status = status;
        this.mode = mode;
        this.cpu = cpu;
        this.mem = mem;
        this.cost = cost;
        this.uptime = uptime;
        this.isProtected = isProtected;
        this.departmentId = departmentId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Double getCpu() { return cpu; }
    public void setCpu(Double cpu) { this.cpu = cpu; }

    public Double getMem() { return mem; }
    public void setMem(Double mem) { this.mem = mem; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public String getUptime() { return uptime; }
    public void setUptime(String uptime) { this.uptime = uptime; }

    public Boolean getIsProtected() { return isProtected; }
    public void setIsProtected(Boolean isProtected) { this.isProtected = isProtected; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}
