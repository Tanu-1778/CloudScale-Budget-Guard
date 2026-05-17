package com.cloud.servlet;

import com.cloud.dao.ScheduleDAO;
import com.cloud.model.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Schedule requests
 */
@WebServlet("/api/schedules/*")
public class ScheduleServlet extends HttpServlet {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Schedule s = scheduleDAO.findFirst();
        if (s != null) {
            objectMapper.writeValue(resp.getWriter(), s);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Schedule updated = objectMapper.readValue(req.getReader(), Schedule.class);
        Schedule existing = scheduleDAO.findFirst();
        
        if (existing != null) {
            updated.setId(existing.getId());
            scheduleDAO.update(updated);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), updated);
        } else {
            scheduleDAO.save(updated);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), updated);
        }
    }
}
