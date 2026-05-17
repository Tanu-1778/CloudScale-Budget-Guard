package com.cloud.model;

public class Employee {
    private Long id;
    private Long organizationId;
    private Long departmentId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String designation;
    private Long salary;
    private String status;
    private String joinDate;
    private Long createdAt;
    private Long updatedAt;

    public Employee() {}

    public Employee(Long organizationId, Long departmentId, String firstName, String lastName, String email,
                   String role, String designation, Long salary, String status, String joinDate) {
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.designation = designation;
        this.salary = salary;
        this.status = status;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public Long getSalary() { return salary; }
    public void setSalary(Long salary) { this.salary = salary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

	public void printStackTrace() {
		// TODO Auto-generated method stub
		
	}
}
