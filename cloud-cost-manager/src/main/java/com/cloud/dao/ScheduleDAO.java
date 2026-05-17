package com.cloud.dao;

import com.cloud.model.Schedule;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Schedule using JDBC
 */
public class ScheduleDAO {

    public List<Schedule> findAll() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                schedules.add(mapResultSetToSchedule(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public Schedule findFirst() {
        String sql = "SELECT * FROM schedules LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapResultSetToSchedule(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Schedule s) {
        String sql = "INSERT INTO schedules (saving_hour, performance_hour, enabled, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getSavingHour());
            ps.setInt(2, s.getPerformanceHour());
            ps.setBoolean(3, s.getEnabled() != null ? s.getEnabled() : true);
            ps.setLong(4, System.currentTimeMillis());
            ps.setLong(5, System.currentTimeMillis());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) s.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Schedule s) {
        String sql = "UPDATE schedules SET saving_hour=?, performance_hour=?, enabled=?, updated_at=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getSavingHour());
            ps.setInt(2, s.getPerformanceHour());
            ps.setBoolean(3, s.getEnabled());
            ps.setLong(4, System.currentTimeMillis());
            ps.setLong(5, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Schedule mapResultSetToSchedule(ResultSet rs) throws SQLException {
        Schedule s = new Schedule();
        s.setId(rs.getLong("id"));
        s.setSavingHour(rs.getInt("saving_hour"));
        s.setPerformanceHour(rs.getInt("performance_hour"));
        s.setEnabled(rs.getBoolean("enabled"));
        s.setCreatedAt(rs.getLong("created_at"));
        s.setUpdatedAt(rs.getLong("updated_at"));
        return s;
    }
}
