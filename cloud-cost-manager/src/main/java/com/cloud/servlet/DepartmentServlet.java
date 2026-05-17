package com.cloud.servlet;

import com.cloud.dao.DepartmentDAO;
import com.cloud.model.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Department requests
 */
@WebServlet("/api/departments/*")
public class DepartmentServlet extends HttpServlet {
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            String orgId = req.getParameter("organizationId");
            List<Department> depts;
            if (orgId != null) depts = departmentDAO.findByOrganizationId(Long.parseLong(orgId));
            else depts = departmentDAO.findAll();
            objectMapper.writeValue(resp.getWriter(), depts);
        } else {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                Department d = departmentDAO.findById(id);
                if (d != null) objectMapper.writeValue(resp.getWriter(), d);
                else resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Department d = objectMapper.readValue(req.getReader(), Department.class);
        departmentDAO.save(d);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), d);
    }
}
