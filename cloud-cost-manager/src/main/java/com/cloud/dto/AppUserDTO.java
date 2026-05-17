package com.cloud.dto;

/**
 * DTO used for both register requests and login responses.
 */
public class AppUserDTO {

    private Long   id;
    private String username;
    private String password;   // plain-text (sent back only on register)
    private String role;
    private String displayName;
    private String email;
    private Long organizationId;
    private Long departmentId;
    private Long employeeId;

    public AppUserDTO() {}

    public Long   getId()                     { return id; }
    public void   setId(Long id)              { this.id = id; }

    public String getUsername()               { return username; }
    public void   setUsername(String u)       { this.username = u; }

    public String getPassword()               { return password; }
    public void   setPassword(String p)       { this.password = p; }

    public String getRole()                   { return role; }
    public void   setRole(String r)           { this.role = r; }

    public String getDisplayName()            { return displayName; }
    public void   setDisplayName(String n)    { this.displayName = n; }

    public String getEmail()                  { return email; }
    public void   setEmail(String e)          { this.email = e; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
}
