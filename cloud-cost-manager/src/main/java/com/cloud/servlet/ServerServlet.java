package com.cloud.servlet;

import com.cloud.dao.ServerDAO;
import com.cloud.model.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling Server requests
 */
@WebServlet("/api/servers/*")
public class ServerServlet extends HttpServlet {
    private final ServerDAO serverDAO = new ServerDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            String orgIdStr = req.getParameter("organizationId");
            String deptIdStr = req.getParameter("departmentId");
            String empIdStr = req.getParameter("employeeId");
            
            System.out.println("GET /api/servers - Filters: org=" + orgIdStr + ", dept=" + deptIdStr + ", emp=" + empIdStr);
            
            List<Server> servers;
            
            if (empIdStr != null && !empIdStr.isEmpty() && !empIdStr.equals("undefined") && !empIdStr.equals("null")) {
                System.out.println("Fetching servers for employee ID: " + empIdStr);
                servers = serverDAO.findByEmployeeId(Long.parseLong(empIdStr));
            } else if (deptIdStr != null && !deptIdStr.isEmpty() && !deptIdStr.equals("undefined") && !deptIdStr.equals("null")) {
                System.out.println("Fetching servers for department ID: " + deptIdStr);
                servers = serverDAO.findByDepartmentId(Long.parseLong(deptIdStr));
            } else if (orgIdStr != null && !orgIdStr.isEmpty() && !orgIdStr.equals("undefined") && !orgIdStr.equals("null")) {
                System.out.println("Fetching servers for organization ID: " + orgIdStr);
                servers = serverDAO.findByOrganizationId(Long.parseLong(orgIdStr));
            } else {
                System.out.println("No valid filters provided, returning all servers from database.");
                servers = serverDAO.findAll();
            }
            System.out.println("Database returned " + (servers == null ? "NULL" : servers.size()) + " servers.");
            
            if (servers == null || servers.isEmpty()) {
                System.out.println("CRITICAL: Servers list is empty. Check if 'servers' table has data!");
            }
            
            objectMapper.writeValue(resp.getWriter(), servers);
        } else {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                Server s = serverDAO.findById(id);
                if (s != null) {
                    objectMapper.writeValue(resp.getWriter(), s);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            Server s = objectMapper.readValue(req.getReader(), Server.class);
            System.out.println("POST /api/servers - Saving server: " + s.getName() + " (Org: " + s.getOrganizationId() + ", Dept: " + s.getDepartmentId() + ", Emp: " + s.getEmployeeId() + ")");
            serverDAO.save(s);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(), s);
        } catch (Exception e) {
            System.err.println("POST /api/servers - FAILED: " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            Server existing = serverDAO.findById(id);
            if (existing == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Server updated = objectMapper.readValue(req.getReader(), Server.class);
            updated.setId(id);
            serverDAO.update(updated);
            
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), updated);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                serverDAO.delete(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
