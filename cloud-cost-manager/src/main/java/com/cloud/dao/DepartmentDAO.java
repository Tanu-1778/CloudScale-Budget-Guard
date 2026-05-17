package com.cloud.dao;

import com.cloud.model.Department;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Department using JDBC
 */
public class DepartmentDAO {

    public List<Department> findAll() {
        List<Department> depts = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                depts.add(mapResultSetToDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depts;
    }

    public List<Department> findByOrganizationId(Long orgId) {
        List<Department> depts = new ArrayList<>();
        String sql = "SELECT * FROM departments WHERE organization_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orgId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) depts.add(mapResultSetToDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depts;
    }

    public Department findById(Long id) {
        String sql = "SELECT * FROM departments WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToDepartment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Department d) {
        String sql = "INSERT INTO departments (organization_id, name, headcount, head, email, budget, spent, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, d.getOrganizationId());
            ps.setString(2, d.getName());
            ps.setInt(3, d.getHeadcount());
            ps.setString(4, d.getHead());
            ps.setString(5, d.getEmail());
            ps.setLong(6, d.getBudget());
            ps.setLong(7, d.getSpent());
            ps.setString(8, d.getStatus());
            ps.setLong(9, System.currentTimeMillis());
            ps.setLong(10, System.currentTimeMillis());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) d.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getLong("id"));
        d.setOrganizationId(rs.getLong("organization_id"));
        d.setName(rs.getString("name"));
        d.setHeadcount(rs.getInt("headcount"));
        d.setHead(rs.getString("head"));
        d.setEmail(rs.getString("email"));
        d.setBudget(rs.getLong("budget"));
        d.setSpent(rs.getLong("spent"));
        d.setStatus(rs.getString("status"));
        return d;
    }
}
