package com.cloud.dao;

import com.cloud.model.Billing;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Billing using JDBC
 */
public class BillingDAO {

    public List<Billing> findAll() {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                billings.add(mapResultSetToBilling(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }

    public List<Billing> findByDepartmentId(Long deptId) {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE department_id = ? OR department_id IS NULL ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    billings.add(mapResultSetToBilling(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }

    public void save(Billing b) {
        String sql = "INSERT INTO billing (month, spent, budget, savings, department_id, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getMonth());
            ps.setDouble(2, b.getSpent());
            ps.setDouble(3, b.getBudget());
            ps.setDouble(4, b.getSavings());
            if (b.getDepartmentId() != null) ps.setLong(5, b.getDepartmentId()); else ps.setNull(5, Types.BIGINT);
            ps.setLong(6, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Billing mapResultSetToBilling(ResultSet rs) throws SQLException {
        Billing b = new Billing();
        b.setId(rs.getLong("id"));
        b.setMonth(rs.getString("month"));
        b.setSpent(rs.getDouble("spent"));
        b.setBudget(rs.getDouble("budget"));
        b.setSavings(rs.getDouble("savings"));
        long deptId = rs.getLong("department_id");
        if (!rs.wasNull()) b.setDepartmentId(deptId);
        b.setCreatedAt(rs.getLong("created_at"));
        return b;
    }
}
