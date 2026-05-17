package com.cloud.config;

import com.cloud.model.*;
import com.cloud.util.DBUtil;
import com.cloud.dao.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initialize database with seed data on startup using DAOs
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final ServerDAO serverDAO = new ServerDAO();
    private final BudgetDAO budgetDAO = new BudgetDAO();
    private final BillingDAO billingDAO = new BillingDAO();
    private final AppUserDAO appUserDAO = new AppUserDAO();
    private final OrganizationDAO organizationDAO = new OrganizationDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private final ActionLogDAO actionLogDAO = new ActionLogDAO();

    @Override
    public void run(String... args) throws Exception {
        ensureSchemaUpToDate();
        seedAppUsers();
        initializeOrganizations();
        initializeDepartments();
        initializeEmployees();
        initializeServers();
        initializeBudget();
        initializeBilling();
        initializeSchedule();
        initializeLogs();
    }

    private void ensureSchemaUpToDate() {
        System.out.println("Checking database schema...");
        try (java.sql.Connection conn = DBUtil.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            
            // 1. Check if servers table exists
            try {
                stmt.executeQuery("SELECT 1 FROM servers LIMIT 1");
            } catch (java.sql.SQLException e) {
                System.out.println("Servers table does not exist. It will be created by schema.sql or manually.");
            }

            // 2. Add missing columns to servers table
            String[] columns = {"organization_id", "employee_id"};
            for (String col : columns) {
                java.sql.ResultSet rs = conn.getMetaData().getColumns(null, null, "servers", col);
                if (!rs.next()) {
                    System.out.println("Adding missing column '" + col + "' to servers table...");
                    try {
                        stmt.execute("ALTER TABLE servers ADD COLUMN " + col + " BIGINT");
                    } catch (java.sql.SQLException ex) {
                        System.err.println("Could not add column " + col + ": " + ex.getMessage());
                    }
                }
            }
            
            // 3. Ensure default organization mapping
            System.out.println("Ensuring all servers are linked to an organization...");
            try {
                stmt.execute("UPDATE servers SET organization_id = 1 WHERE organization_id IS NULL");
            } catch (java.sql.SQLException e) {
                System.err.println("Default org update failed: " + e.getMessage());
            }
            
        } catch (java.sql.SQLException e) {
            System.err.println("Schema update check failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seedAppUsers() {
        if (appUserDAO.findByUsername("superadmin") == null) {
            System.out.println("Seeding superadmin...");
            appUserDAO.save(new AppUser("superadmin", "super123", "superadmin", "Platform Admin", "superadmin@cloudscale.io"));
        } else {
            appUserDAO.update(new AppUser("superadmin", "super123", "superadmin", "Platform Admin", "superadmin@cloudscale.io"));
        }
        
        if (appUserDAO.findByUsername("admin") == null) {
            System.out.println("Seeding admin...");
            AppUser admin = new AppUser("admin", "admin123", "admin", "Org Admin", "admin@cloudscale.io");
            admin.setOrganizationId(1L); // Link to TechNova
            appUserDAO.save(admin);
        } else {
            AppUser admin = new AppUser("admin", "admin123", "admin", "Org Admin", "admin@cloudscale.io");
            admin.setOrganizationId(1L);
            appUserDAO.update(admin);
        }
        
        if (appUserDAO.findByUsername("manager") == null) {
            System.out.println("Seeding manager...");
            AppUser manager = new AppUser("manager", "manager123", "manager", "Engineering Manager", "ravi@company.in");
            manager.setOrganizationId(1L);
            manager.setDepartmentId(1L); // Link to Engineering
            appUserDAO.save(manager);
        } else {
            AppUser manager = new AppUser("manager", "manager123", "manager", "Engineering Manager", "ravi@company.in");
            manager.setOrganizationId(1L);
            manager.setDepartmentId(1L);
            appUserDAO.update(manager);
        }

        // NEW: Seeding second manager for Marketing
        if (appUserDAO.findByUsername("manager2") == null) {
            System.out.println("Seeding manager2...");
            AppUser manager2 = new AppUser("manager2", "manager123", "manager", "Marketing Manager", "priya@company.in");
            manager2.setOrganizationId(1L);
            manager2.setDepartmentId(2L); // Link to Marketing
            appUserDAO.save(manager2);
        } else {
            AppUser manager2 = new AppUser("manager2", "manager123", "manager", "Marketing Manager", "priya@company.in");
            manager2.setOrganizationId(1L);
            manager2.setDepartmentId(2L);
            appUserDAO.update(manager2);
        }
        
        if (appUserDAO.findByUsername("viewer") == null) {
            System.out.println("Seeding viewer...");
            AppUser viewer = new AppUser("viewer", "viewer123", "viewer", "Employee", "rohan@company.in");
            viewer.setOrganizationId(1L);
            viewer.setDepartmentId(1L);
            viewer.setEmployeeId(1L);
            appUserDAO.save(viewer);
        } else {
            AppUser viewer = new AppUser("viewer", "viewer123", "viewer", "Employee", "rohan@company.in");
            viewer.setOrganizationId(1L);
            viewer.setDepartmentId(1L);
            viewer.setEmployeeId(1L);
            appUserDAO.update(viewer);
        }
    }

    private void initializeOrganizations() {
        if (organizationDAO.findAll().isEmpty()) {
            organizationDAO.save(new Organization("TechNova Solutions", "Enterprise", 48, 12, 415000L, "active", "ENT-2026-001", "2027-03-15", "rahul@technova.in", "2025-04-01", "aws", "Credit Card", "paid", "10 TB"));
            organizationDAO.save(new Organization("FinServe India Pvt Ltd", "Business", 22, 6, 187500L, "active", "BIZ-2026-014", "2026-12-01", "priya@finserve.com", "2025-06-15", "azure", "Bank Transfer", "paid", "2 TB"));
        }
    }

    private void initializeDepartments() {
        if (departmentDAO.findAll().isEmpty()) {
            departmentDAO.save(new Department(1L, "Engineering", 24, "Ravi Kumar", "ravi@company.in", 850000L, 712000L, "active"));
            departmentDAO.save(new Department(1L, "Marketing", 10, "Priya Sharma", "priya@company.in", 180000L, 142000L, "active"));
            departmentDAO.save(new Department(1L, "Finance", 8, "Amit Verma", "amit@company.in", 120000L, 98000L, "active"));
            departmentDAO.save(new Department(1L, "HR", 6, "Sneha Patel", "sneha@company.in", 60000L, 38000L, "active"));
            departmentDAO.save(new Department(1L, "Operations", 15, "Deepak Singh", "deepak@company.in", 420000L, 395000L, "active"));
            departmentDAO.save(new Department(1L, "Design", 7, "Ayesha Mirza", "ayesha@company.in", 90000L, 67000L, "active"));
            departmentDAO.save(new Department(1L, "Sales", 12, "Karan Mehta", "karan@company.in", 150000L, 119000L, "active"));
            departmentDAO.save(new Department(1L, "Legal", 4, "Nisha Roy", "nisha@company.in", 40000L, 28000L, "active"));
        }
    }

    private void initializeEmployees() {
        if (employeeDAO.findAll().isEmpty()) {
            employeeDAO.save(new Employee(1L, 1L, "Rohan", "Gupta", "rohan@company.in", "Developer", "Developer", 95000L, "active", "2024-06-01"));
            employeeDAO.save(new Employee(1L, 1L, "Sneha", "Iyer", "sneha.i@company.in", "Engineer", "Engineer", 98000L, "active", "2024-03-15"));
            employeeDAO.save(new Employee(1L, 1L, "Aman", "Tiwari", "aman@company.in", "Developer", "Developer", 90000L, "active", "2024-08-10"));
            employeeDAO.save(new Employee(1L, 1L, "Priya", "Nair", "priya.n@company.in", "Analyst", "Analyst", 82000L, "on-leave", "2024-01-20"));
            employeeDAO.save(new Employee(1L, 2L, "Kavya", "Reddy", "kavya@company.in", "Executive", "Executive", 110000L, "active", "2023-11-05"));
            employeeDAO.save(new Employee(1L, 2L, "Dev", "Kapoor", "dev@company.in", "Analyst", "Analyst", 80000L, "active", "2024-05-20"));
            employeeDAO.save(new Employee(1L, 2L, "Ritu", "Joshi", "ritu@company.in", "Designer", "Designer", 78000L, "active", "2024-07-01"));
            employeeDAO.save(new Employee(1L, 3L, "Nikhil", "Shah", "nikhil@company.in", "Analyst", "Analyst", 88000L, "active", "2023-09-15"));
            employeeDAO.save(new Employee(1L, 3L, "Pooja", "Mehta", "pooja@company.in", "Executive", "Executive", 105000L, "active", "2023-12-01"));
            employeeDAO.save(new Employee(1L, 4L, "Ananya", "Singh", "ananya@company.in", "Manager", "HR Manager", 115000L, "active", "2023-06-10"));
            employeeDAO.save(new Employee(1L, 4L, "Rahul", "Das", "rahul.d@company.in", "Consultant", "Consultant", 75000L, "on-leave", "2024-02-14"));
            employeeDAO.save(new Employee(1L, 5L, "Suresh", "Pillai", "suresh@company.in", "Engineer", "Engineer", 92000L, "active", "2024-01-08"));
            employeeDAO.save(new Employee(1L, 5L, "Meena", "Bose", "meena@company.in", "Analyst", "Analyst", 83000L, "active", "2024-04-22"));
            employeeDAO.save(new Employee(1L, 5L, "Kiran", "Menon", "kiran@company.in", "Developer", "Developer", 91000L, "active", "2023-10-30"));
            employeeDAO.save(new Employee(1L, 6L, "Sana", "Khan", "sana@company.in", "Designer", "Designer", 87000L, "active", "2024-03-01"));
            employeeDAO.save(new Employee(1L, 6L, "Arjun", "Verma", "arjun@company.in", "Intern", "Intern", 35000L, "active", "2025-01-10"));
            employeeDAO.save(new Employee(1L, 7L, "Neha", "Pandey", "neha@company.in", "Executive", "Executive", 108000L, "active", "2023-08-05"));
            employeeDAO.save(new Employee(1L, 7L, "Vikram", "Rao", "vikram@company.in", "Analyst", "Analyst", 84000L, "active", "2024-06-15"));
            employeeDAO.save(new Employee(1L, 8L, "Divya", "Nambiar", "divya@company.in", "Consultant", "Legal Consultant", 97000L, "active", "2024-02-01"));
            employeeDAO.save(new Employee(1L, 8L, "Arun", "Iyer", "arun@company.in", "Analyst", "Analyst", 81000L, "on-leave", "2024-09-20"));
        }
    }

    private void initializeServers() {
        System.out.println("Initializing servers seeding process...");
        if (serverDAO.findAll().isEmpty()) {
            System.out.println("Database empty, seeding default servers...");
            // Global Servers (Organization 1)
            saveServer("prod-global-01", "Web", "us-east-1", "running", "performance", 72.0, 61.0, 25929.20, "14d 6h", false, 1L, null, null);
            saveServer("db-primary-01", "DB", "us-west-2", "running", "performance", 35.0, 78.0, 40587.00, "30d 2h", true, 1L, null, null);

            // Engineering Servers (Dept 1)
            saveServer("eng-prod-01", "Web", "us-east-1", "running", "performance", 72.0, 61.0, 25929.20, "14d 6h", false, 1L, 1L, null);
            saveServer("eng-db-01", "DB", "us-west-2", "running", "performance", 35.0, 78.0, 40587.00, "30d 2h", true, 1L, 1L, null);
            
            // Marketing Servers (Dept 2)
            saveServer("mkt-web-01", "Web", "us-east-1", "running", "performance", 45.0, 40.0, 15000.00, "5d 2h", false, 1L, 2L, null);
            saveServer("mkt-cdn-01", "Cache", "eu-west-1", "saving", "saving", 12.0, 30.0, 8150.60, "7d 0h", false, 1L, 2L, null);

            // Finance Servers (Dept 3)
            saveServer("fin-anal-01", "ML", "us-west-2", "stopped", "saving", 0.0, 0.0, 0.0, "0h", false, 1L, 3L, null);

            // Employee Specific Servers (Employee 1)
            saveServer("personal-dev-box", "Web", "us-east-1", "running", "saving", 20.0, 30.0, 1200.0, "1d 2h", false, 1L, 1L, 1L);
            System.out.println("Default servers seeded successfully.");
        } else {
            System.out.println("Database already contains servers, skipping seed.");
        }
    }

    private void saveServer(String name, String type, String region, String status, String mode, Double cpu, Double mem, Double cost, String uptime, boolean prot, Long orgId, Long deptId, Long empId) {
        Server s = new Server();
        s.setName(name);
        s.setType(type);
        s.setRegion(region);
        s.setStatus(status);
        s.setMode(mode);
        s.setCpu(cpu);
        s.setMemory(mem);
        s.setCost(cost);
        s.setUptime(uptime);
        s.setIsProtected(prot);
        s.setOrganizationId(orgId);
        s.setDepartmentId(deptId);
        s.setEmployeeId(empId);
        serverDAO.save(s);
    }

    private void initializeBudget() {
        if (budgetDAO.findGlobal() == null) {
            // Global Budget
            Budget globalBudget = new Budget();
            globalBudget.setTotal(1000000.0);
            globalBudget.setSpent(450000.0);
            globalBudget.setAlertThreshold(80);
            globalBudget.setKillThreshold(100);
            globalBudget.setStrategy("balanced");
            budgetDAO.save(globalBudget);
        }

        // Organization Budget (Admin: rahul@technova.in / TechNova Solutions)
        if (budgetDAO.findByOrganizationId(1L) == null) {
            Budget orgBudget = new Budget();
            orgBudget.setOrganizationId(1L);
            orgBudget.setTotal(500000.0);
            orgBudget.setSpent(350000.0);
            orgBudget.setAlertThreshold(85);
            orgBudget.setKillThreshold(95);
            orgBudget.setStrategy("balanced");
            budgetDAO.save(orgBudget);
        }

        // Department Budget (Manager: Ravi Kumar / Engineering)
        if (budgetDAO.findByDepartmentId(1L) == null) {
            Budget deptBudget = new Budget();
            deptBudget.setDepartmentId(1L);
            deptBudget.setTotal(200000.0);
            deptBudget.setSpent(120000.0);
            deptBudget.setAlertThreshold(75);
            deptBudget.setKillThreshold(90);
            deptBudget.setStrategy("aggressive");
            budgetDAO.save(deptBudget);
        }

        // NEW: Department Budget for Marketing (Manager: Priya Sharma)
        if (budgetDAO.findByDepartmentId(2L) == null) {
            Budget mktBudget = new Budget();
            mktBudget.setDepartmentId(2L);
            mktBudget.setTotal(150000.0);
            mktBudget.setSpent(45000.0);
            mktBudget.setAlertThreshold(80);
            mktBudget.setKillThreshold(95);
            mktBudget.setStrategy("balanced");
            budgetDAO.save(mktBudget);
        }

        // Employee Budget (Viewer: Rohan Gupta)
        if (budgetDAO.findByEmployeeId(1L) == null) {
            Budget empBudget = new Budget();
            empBudget.setEmployeeId(1L);
            empBudget.setTotal(50000.0);
            empBudget.setSpent(15000.0);
            empBudget.setAlertThreshold(70);
            empBudget.setKillThreshold(85);
            empBudget.setStrategy("performance");
            budgetDAO.save(empBudget);
        }
    }

    private void initializeBilling() {
        if (billingDAO.findAll().isEmpty()) {
            Billing billing1 = new Billing();
            billing1.setMonth("Jan 2026");
            billing1.setSpent(151060.0);
            billing1.setBudget(207500.0);
            billing1.setSavings(56440.0);
            billingDAO.save(billing1);

            Billing billing2 = new Billing();
            billing2.setMonth("Feb 2026");
            billing2.setSpent(174300.0);
            billing2.setBudget(207500.0);
            billing2.setSavings(33200.0);
            billingDAO.save(billing2);

            Billing billing3 = new Billing();
            billing3.setMonth("Mar 2026");
            billing3.setSpent(136950.0);
            billing3.setBudget(207500.0);
            billing3.setSavings(70550.0);
            billingDAO.save(billing3);

            Billing billing4 = new Billing();
            billing4.setMonth("Apr 2026");
            billing4.setSpent(194220.0);
            billing4.setBudget(207500.0);
            billing4.setSavings(13280.0);
            billingDAO.save(billing4);
        }
    }

    private void initializeSchedule() {
        if (scheduleDAO.findFirst() == null) {
            Schedule schedule = new Schedule();
            schedule.setSavingHour(18);
            schedule.setPerformanceHour(8);
            schedule.setEnabled(true);
            scheduleDAO.save(schedule);
        }
    }

    private void initializeLogs() {
        if (actionLogDAO.findAll().isEmpty()) {
            ActionLog log1 = new ActionLog();
            log1.setType("ok");
            log1.setMessage("System started. Guard Engine active.");
            log1.setTimestamp("08:00:01");
            log1.setCreatedAt(System.currentTimeMillis());
            actionLogDAO.save(log1);
        }
    }
}
