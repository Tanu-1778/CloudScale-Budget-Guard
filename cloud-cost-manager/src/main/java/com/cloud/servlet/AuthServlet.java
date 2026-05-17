package com.cloud.servlet;

import com.cloud.dao.AppUserDAO;
import com.cloud.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Servlet for handling Authentication requests
 */
@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {
    private final AppUserDAO userDAO = new AppUserDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo != null && pathInfo.equals("/login")) {
            String username, password;
            String contentType = req.getContentType();
            
            if (contentType != null && contentType.contains("application/json")) {
                Map<String, String> credentials = objectMapper.readValue(req.getReader(), Map.class);
                username = credentials.get("username");
                password = credentials.get("password");
            } else {
                username = req.getParameter("username");
                password = req.getParameter("password");
            }

            try {
                AppUser user = userDAO.findByUsername(username);
                System.out.println("Login attempt for username: " + username);
                
                if (user != null) {
                    System.out.println("User found in DB. Comparing passwords...");
                    if (user.getPassword().equals(password)) {
                        System.out.println("Login SUCCESS for user: " + username + " (Role: " + user.getRole() + ", Org: " + user.getOrganizationId() + ", Dept: " + user.getDepartmentId() + ")");
                        if (contentType != null && contentType.contains("application/json")) {
                            objectMapper.writeValue(resp.getWriter(), user);
                        } else {
                            resp.sendRedirect(req.getContextPath() + "/index.html");
                        }
                    } else {
                        System.out.println("Login FAILED: Password mismatch for user: " + username);
                        sendError(resp, req, contentType, "Invalid username or password");
                    }
                } else {
                    System.out.println("Login FAILED: User not found: " + username);
                    sendError(resp, req, contentType, "Invalid username or password");
                }
            } catch (Exception e) {
                System.err.println("Login ERROR: Database connection or query failure!");
                e.printStackTrace();
                sendError(resp, req, contentType, "Database connection error. Please check if MySQL is running.");
            }
        } else if (pathInfo != null && pathInfo.equals("/register")) {
            try {
                AppUser user = objectMapper.readValue(req.getReader(), AppUser.class);
                
                // Generate username if missing
                if (user.getUsername() == null || user.getUsername().isEmpty()) {
                    String base = user.getDisplayName().toLowerCase().replaceAll("[^a-z0-9]", "");
                    if (base.isEmpty()) base = "user";
                    user.setUsername(base + (int)(Math.random() * 900 + 100));
                }
                
                // Generate password if missing
                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 8; i++) {
                        sb.append(chars.charAt((int)(Math.random() * chars.length())));
                    }
                    user.setPassword(sb.toString());
                }
                
                userDAO.save(user);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), user);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(resp.getWriter(), Map.of("error", "Registration failed: " + e.getMessage()));
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void sendError(HttpServletResponse resp, HttpServletRequest req, String contentType, String message) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (contentType != null && contentType.contains("application/json")) {
            objectMapper.writeValue(resp.getWriter(), Map.of("error", message));
        } else {
            req.setAttribute("error", message);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
