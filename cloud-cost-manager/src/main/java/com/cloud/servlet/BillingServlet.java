package com.cloud.servlet;

import com.cloud.dao.BillingDAO;
import com.cloud.model.Billing;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Billing requests
 */
@WebServlet("/api/billing/*")
public class BillingServlet extends HttpServlet {
    private final BillingDAO billingDAO = new BillingDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String deptIdStr = req.getParameter("departmentId");
        List<Billing> billings;
        if (deptIdStr != null && !deptIdStr.isEmpty()) {
            billings = billingDAO.findByDepartmentId(Long.parseLong(deptIdStr));
        } else {
            billings = billingDAO.findAll();
        }
        objectMapper.writeValue(resp.getWriter(), billings);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Billing b = objectMapper.readValue(req.getReader(), Billing.class);
        billingDAO.save(b);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), b);
    }
}
