package com.cloud.servlet;

import com.cloud.dao.OrganizationDAO;
import com.cloud.model.Organization;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Organization requests
 */
@WebServlet("/api/organizations/*")
public class OrganizationServlet extends HttpServlet {
    private final OrganizationDAO organizationDAO = new OrganizationDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Organization> orgs = organizationDAO.findAll();
            objectMapper.writeValue(resp.getWriter(), orgs);
        } else {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                Organization o = organizationDAO.findById(id);
                if (o != null) objectMapper.writeValue(resp.getWriter(), o);
                else resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Organization o = objectMapper.readValue(req.getReader(), Organization.class);
        organizationDAO.save(o);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), o);
    }
}
