package com.cloud.dto;

/**
 * Department DTO
 */
public class DepartmentDTO {
    private Long id;
    private Long organizationId;
    private String name;
    private Integer headcount;
    private String head;
    private String email;
    private Long budget;
    private Long spent;
    private String status;

    public DepartmentDTO() {}

    public DepartmentDTO(Long id, Long organizationId, String name, Integer headcount, String head,
                        String email, Long budget, Long spent, String status) {
        this.id = id;
        this.organizationId = organizationId;
        this.name = name;
        this.headcount = headcount;
        this.head = head;
        this.email = email;
        this.budget = budget;
        this.spent = spent;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getHeadcount() { return headcount; }
    public void setHeadcount(Integer headcount) { this.headcount = headcount; }

    public String getHead() { return head; }
    public void setHead(String head) { this.head = head; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getBudget() { return budget; }
    public void setBudget(Long budget) { this.budget = budget; }

    public Long getSpent() { return spent; }
    public void setSpent(Long spent) { this.spent = spent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
