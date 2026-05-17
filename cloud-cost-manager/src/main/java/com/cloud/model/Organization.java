package com.cloud.model;

public class Organization {
    private Long id;
    private String name;
    private String plan;
    private Integer users;
    private Integer servers;
    private Long monthlySpend;
    private String status;
    private String license;
    private String expiry;
    private String contact;
    private String joinedDate;
    private String cloudProvider;
    private String paymentMethod;
    private String paymentStatus;
    private String storageLimit;
    private Long createdAt;
    private Long updatedAt;

    public Organization() {}

    public Organization(String name, String plan, Integer users, Integer servers, Long monthlySpend,
                       String status, String license, String expiry, String contact, String joinedDate,
                       String cloudProvider, String paymentMethod, String paymentStatus, String storageLimit) {
        this.name = name;
        this.plan = plan;
        this.users = users;
        this.servers = servers;
        this.monthlySpend = monthlySpend;
        this.status = status;
        this.license = license;
        this.expiry = expiry;
        this.contact = contact;
        this.joinedDate = joinedDate;
        this.cloudProvider = cloudProvider;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.storageLimit = storageLimit;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }

    public Integer getUsers() { return users; }
    public void setUsers(Integer users) { this.users = users; }

    public Integer getServers() { return servers; }
    public void setServers(Integer servers) { this.servers = servers; }

    public Long getMonthlySpend() { return monthlySpend; }
    public void setMonthlySpend(Long monthlySpend) { this.monthlySpend = monthlySpend; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLicense() { return license; }
    public void setLicense(String license) { this.license = license; }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getJoinedDate() { return joinedDate; }
    public void setJoinedDate(String joinedDate) { this.joinedDate = joinedDate; }

    public String getCloudProvider() { return cloudProvider; }
    public void setCloudProvider(String cloudProvider) { this.cloudProvider = cloudProvider; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getStorageLimit() { return storageLimit; }
    public void setStorageLimit(String storageLimit) { this.storageLimit = storageLimit; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
}
