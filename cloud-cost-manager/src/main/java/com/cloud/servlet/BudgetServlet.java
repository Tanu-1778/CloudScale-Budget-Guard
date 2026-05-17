package com.cloud.servlet;

import com.cloud.dao.BudgetDAO;
import com.cloud.model.Budget;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Servlet for handling Budget requests
 */
@WebServlet("/api/budget/*")
public class BudgetServlet extends HttpServlet {
    private final BudgetDAO budgetDAO = new BudgetDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String role = req.getParameter("role");
        String orgIdStr = req.getParameter("organizationId");
        String deptIdStr = req.getParameter("departmentId");
        String empIdStr = req.getParameter("employeeId");

        System.out.println("Budget GET request - Role: " + role + ", OrgID: " + orgIdStr + ", DeptID: " + deptIdStr + ", EmpID: " + empIdStr);

        Budget b = null;
        if (empIdStr != null && !empIdStr.isEmpty()) {
            b = budgetDAO.findByEmployeeId(Long.parseLong(empIdStr));
            System.out.println("Searching by EmpID: " + empIdStr + (b != null ? " - Found" : " - Not Found"));
        } else if (deptIdStr != null && !deptIdStr.isEmpty()) {
            b = budgetDAO.findByDepartmentId(Long.parseLong(deptIdStr));
            System.out.println("Searching by DeptID: " + deptIdStr + (b != null ? " - Found" : " - Not Found"));
        } else if (orgIdStr != null && !orgIdStr.isEmpty()) {
            b = budgetDAO.findByOrganizationId(Long.parseLong(orgIdStr));
            System.out.println("Searching by OrgID: " + orgIdStr + (b != null ? " - Found" : " - Not Found"));
        }

        if (b == null) {
            b = budgetDAO.findGlobal();
            System.out.println("Falling back to Global Budget" + (b != null ? " - Found" : " - Not Found"));
        }

        if (b != null) {
            objectMapper.writeValue(resp.getWriter(), b);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Budget b = objectMapper.readValue(req.getReader(), Budget.class);
        budgetDAO.save(b);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), b);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Budget b = objectMapper.readValue(req.getReader(), Budget.class);
        
        // Try to find existing budget by ID first, then by entity links
        Budget existing = null;
        if (b.getId() != null) {
            // Find by ID - you might want to add findById to BudgetDAO
            // For now, let's use the links if provided
        }
        
        if (b.getEmployeeId() != null) existing = budgetDAO.findByEmployeeId(b.getEmployeeId());
        else if (b.getDepartmentId() != null) existing = budgetDAO.findByDepartmentId(b.getDepartmentId());
        else if (b.getOrganizationId() != null) existing = budgetDAO.findByOrganizationId(b.getOrganizationId());
        else existing = budgetDAO.findGlobal();
        
        if (existing != null) {
            b.setId(existing.getId());
            budgetDAO.update(b);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), b);
        } else {
            // If it doesn't exist, create it
            budgetDAO.save(b);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), b);
        }
    }
}
