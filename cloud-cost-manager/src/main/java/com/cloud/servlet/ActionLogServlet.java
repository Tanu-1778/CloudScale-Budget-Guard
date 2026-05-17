package com.cloud.servlet;

import com.cloud.dao.ActionLogDAO;
import com.cloud.model.ActionLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Action Log requests
 * Replaces ActionLogController
 */
@WebServlet("/api/action-logs/*")
public class ActionLogServlet extends HttpServlet {
    private final ActionLogDAO actionLogDAO = new ActionLogDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo != null && pathInfo.equals("/latest")) {
            String limitStr = req.getParameter("limit");
            int limit = (limitStr != null) ? Integer.parseInt(limitStr) : 50;
            List<ActionLog> logs = actionLogDAO.findLatest(limit);
            objectMapper.writeValue(resp.getWriter(), logs);
        } else {
            List<ActionLog> logs = actionLogDAO.findAll();
            objectMapper.writeValue(resp.getWriter(), logs);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ActionLog log = objectMapper.readValue(req.getReader(), ActionLog.class);
        actionLogDAO.save(log);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), log);
    }
}
