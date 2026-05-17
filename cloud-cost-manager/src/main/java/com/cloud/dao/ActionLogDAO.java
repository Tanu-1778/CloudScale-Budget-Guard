package com.cloud.dao;

import com.cloud.model.ActionLog;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for ActionLog using JDBC
 */
public class ActionLogDAO {

    public List<ActionLog> findAll() {
        List<ActionLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM action_logs ORDER BY created_at DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ActionLog log = new ActionLog();
                log.setId(rs.getLong("id"));
                log.setType(rs.getString("type"));
                log.setMessage(rs.getString("msg"));
                log.setTimestamp(rs.getString("time"));
                log.setCreatedAt(rs.getLong("created_at"));
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public List<ActionLog> findLatest(int limit) {
        List<ActionLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM action_logs ORDER BY created_at DESC LIMIT ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ActionLog log = new ActionLog();
                    log.setId(rs.getLong("id"));
                log.setType(rs.getString("type"));
                log.setMessage(rs.getString("msg"));
                log.setTimestamp(rs.getString("time"));
                log.setCreatedAt(rs.getLong("created_at"));
                logs.add(log);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return logs;
}

public void save(ActionLog log) {
    String sql = "INSERT INTO action_logs (type, msg, time, created_at) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, log.getType());
        ps.setString(2, log.getMessage());
        ps.setString(3, log.getTimestamp());
            ps.setLong(4, log.getCreatedAt() != null ? log.getCreatedAt() : System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
