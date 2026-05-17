package com.cloud.dao;

import com.cloud.model.Budget;
import com.cloud.util.DBUtil;

import java.sql.*;

/**
 * DAO implementation for Budget using JDBC
 */
public class BudgetDAO {

    public Budget findGlobal() {
        String sql = "SELECT * FROM budgets WHERE organization_id IS NULL AND department_id IS NULL AND employee_id IS NULL LIMIT 1";
        return findOne(sql);
    }

    public Budget findByOrganizationId(Long id) {
        String sql = "SELECT * FROM budgets WHERE organization_id = ? LIMIT 1";
        return findOne(sql, id);
    }

    public Budget findByDepartmentId(Long id) {
        String sql = "SELECT * FROM budgets WHERE department_id = ? LIMIT 1";
        return findOne(sql, id);
    }

    public Budget findByEmployeeId(Long id) {
        String sql = "SELECT * FROM budgets WHERE employee_id = ? LIMIT 1";
        return findOne(sql, id);
    }

    private Budget findOne(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Budget b = new Budget();
                    b.setId(rs.getLong("id"));
                    b.setTotal(rs.getDouble("total"));
                    b.setSpent(rs.getDouble("spent"));
                    b.setAlertThreshold(rs.getInt("alert_threshold"));
                    b.setKillThreshold(rs.getInt("kill_threshold"));
                    b.setStrategy(rs.getString("strategy"));
                    long orgId = rs.getLong("organization_id");
                    if (!rs.wasNull()) b.setOrganizationId(orgId);
                    long deptId = rs.getLong("department_id");
                    if (!rs.wasNull()) b.setDepartmentId(deptId);
                    long empId = rs.getLong("employee_id");
                    if (!rs.wasNull()) b.setEmployeeId(empId);
                    b.setCreatedAt(rs.getLong("created_at"));
                    b.setUpdatedAt(rs.getLong("updated_at"));
                    return b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Budget b) {
        String sql = "INSERT INTO budgets (total, spent, alert_threshold, kill_threshold, strategy, organization_id, department_id, employee_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, b.getTotal());
            ps.setDouble(2, b.getSpent());
            ps.setInt(3, b.getAlertThreshold());
            ps.setInt(4, b.getKillThreshold());
            ps.setString(5, b.getStrategy());
            if (b.getOrganizationId() != null) ps.setLong(6, b.getOrganizationId()); else ps.setNull(6, Types.BIGINT);
            if (b.getDepartmentId() != null) ps.setLong(7, b.getDepartmentId()); else ps.setNull(7, Types.BIGINT);
            if (b.getEmployeeId() != null) ps.setLong(8, b.getEmployeeId()); else ps.setNull(8, Types.BIGINT);
            ps.setLong(9, System.currentTimeMillis());
            ps.setLong(10, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Budget b) {
        StringBuilder sql = new StringBuilder("UPDATE budgets SET updated_at = ?");
        if (b.getTotal() != null) sql.append(", total = ?");
        if (b.getSpent() != null) sql.append(", spent = ?");
        if (b.getAlertThreshold() != null) sql.append(", alert_threshold = ?");
        if (b.getKillThreshold() != null) sql.append(", kill_threshold = ?");
        if (b.getStrategy() != null) sql.append(", strategy = ?");
        sql.append(" WHERE id = ?");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setLong(idx++, System.currentTimeMillis());
            if (b.getTotal() != null) ps.setDouble(idx++, b.getTotal());
            if (b.getSpent() != null) ps.setDouble(idx++, b.getSpent());
            if (b.getAlertThreshold() != null) ps.setInt(idx++, b.getAlertThreshold());
            if (b.getKillThreshold() != null) ps.setInt(idx++, b.getKillThreshold());
            if (b.getStrategy() != null) ps.setString(idx++, b.getStrategy());
            ps.setLong(idx++, b.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
