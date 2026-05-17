package com.cloud.dao;

import com.cloud.model.AppUser;
import com.cloud.util.DBUtil;

import java.sql.*;

/**
 * DAO implementation for AppUser using JDBC
 */
public class AppUserDAO {

    public AppUser findByUsername(String username) {
        String sql = "SELECT * FROM app_users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AppUser user = new AppUser();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setDisplayName(rs.getString("display_name"));
                    user.setEmail(rs.getString("email"));
                    user.setOrganizationId(rs.getLong("organization_id"));
                    if (rs.wasNull()) user.setOrganizationId(null);
                    user.setDepartmentId(rs.getLong("department_id"));
                    if (rs.wasNull()) user.setDepartmentId(null);
                    user.setEmployeeId(rs.getLong("employee_id"));
                    if (rs.wasNull()) user.setEmployeeId(null);
                    user.setCreatedAt(rs.getLong("created_at"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(AppUser user) {
        String sql = "INSERT INTO app_users (username, password, role, display_name, email, organization_id, department_id, employee_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getDisplayName());
            ps.setString(5, user.getEmail());
            if (user.getOrganizationId() != null) ps.setLong(6, user.getOrganizationId()); else ps.setNull(6, Types.BIGINT);
            if (user.getDepartmentId() != null) ps.setLong(7, user.getDepartmentId()); else ps.setNull(7, Types.BIGINT);
            if (user.getEmployeeId() != null) ps.setLong(8, user.getEmployeeId()); else ps.setNull(8, Types.BIGINT);
            ps.setLong(9, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(AppUser user) {
        String sql = "UPDATE app_users SET password=?, role=?, display_name=?, email=?, organization_id=?, department_id=?, employee_id=? WHERE username=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getDisplayName());
            ps.setString(4, user.getEmail());
            if (user.getOrganizationId() != null) ps.setLong(5, user.getOrganizationId()); else ps.setNull(5, Types.BIGINT);
            if (user.getDepartmentId() != null) ps.setLong(6, user.getDepartmentId()); else ps.setNull(6, Types.BIGINT);
            if (user.getEmployeeId() != null) ps.setLong(7, user.getEmployeeId()); else ps.setNull(7, Types.BIGINT);
            ps.setString(8, user.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
