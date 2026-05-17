package com.cloud.servlet;

import com.cloud.dao.EmployeeDAO;
import com.cloud.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Employee requests
 */
@WebServlet("/api/employees/*")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            String deptId = req.getParameter("departmentId");
            List<Employee> employees;
            if (deptId != null) employees = employeeDAO.findByDepartmentId(Long.parseLong(deptId));
            else employees = employeeDAO.findAll();
            objectMapper.writeValue(resp.getWriter(), employees);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee e = objectMapper.readValue(req.getReader(), Employee.class);
        employeeDAO.save(e);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), e);
    }
}
