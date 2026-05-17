package com.cloud.dao;

import com.cloud.model.Server;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Server using JDBC
 */
public class ServerDAO {

    public List<Server> findAll() {
        List<Server> servers = new ArrayList<>();
        String sql = "SELECT * FROM servers";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                servers.add(mapResultSetToServer(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.findAll: " + e.getMessage());
            e.printStackTrace();
        }
        return servers;
    }

    public List<Server> findByDepartmentId(Long deptId) {
        List<Server> servers = new ArrayList<>();
        String sql = "SELECT * FROM servers WHERE department_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    servers.add(mapResultSetToServer(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.findByDepartmentId: " + e.getMessage());
            e.printStackTrace();
        }
        return servers;
    }

    public List<Server> findByOrganizationId(Long orgId) {
        List<Server> servers = new ArrayList<>();
        String sql = "SELECT * FROM servers WHERE organization_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orgId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    servers.add(mapResultSetToServer(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.findByOrganizationId: " + e.getMessage());
            e.printStackTrace();
        }
        return servers;
    }

    public List<Server> findByEmployeeId(Long empId) {
        List<Server> servers = new ArrayList<>();
        String sql = "SELECT * FROM servers WHERE employee_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    servers.add(mapResultSetToServer(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.findByEmployeeId: " + e.getMessage());
            e.printStackTrace();
        }
        return servers;
    }

    public Server findById(Long id) {
        String sql = "SELECT * FROM servers WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToServer(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.findById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void save(Server s) {
        String sql = "INSERT INTO servers (name, type, region, status, mode, cpu, memory, cost, uptime, is_protected, organization_id, department_id, employee_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getType());
            ps.setString(3, s.getRegion());
            ps.setString(4, s.getStatus());
            ps.setString(5, s.getMode());
            ps.setDouble(6, s.getCpu() != null ? s.getCpu() : 0.0);
            ps.setDouble(7, s.getMemory() != null ? s.getMemory() : 0.0);
            ps.setDouble(8, s.getCost() != null ? s.getCost() : 0.0);
            ps.setString(9, s.getUptime());
            ps.setBoolean(10, s.getIsProtected() != null ? s.getIsProtected() : false);
            if (s.getOrganizationId() != null) ps.setLong(11, s.getOrganizationId()); else ps.setNull(11, Types.BIGINT);
            if (s.getDepartmentId() != null) ps.setLong(12, s.getDepartmentId()); else ps.setNull(12, Types.BIGINT);
            if (s.getEmployeeId() != null) ps.setLong(13, s.getEmployeeId()); else ps.setNull(13, Types.BIGINT);
            ps.setLong(14, System.currentTimeMillis());
            ps.setLong(15, System.currentTimeMillis());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) s.setId(generatedKeys.getLong(1));
            }
            System.out.println("Server saved successfully: " + s.getName());
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.save: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Server s) {
        String sql = "UPDATE servers SET status=?, mode=?, cpu=?, memory=?, updated_at=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStatus());
            ps.setString(2, s.getMode());
            ps.setDouble(3, s.getCpu());
            ps.setDouble(4, s.getMemory());
            ps.setLong(5, System.currentTimeMillis());
            ps.setLong(6, s.getId());
            ps.executeUpdate();
            System.out.println("Server updated successfully: ID " + s.getId());
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM servers WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            System.out.println("Server deleted successfully: ID " + id);
        } catch (SQLException e) {
            System.err.println("Error in ServerDAO.delete: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Server mapResultSetToServer(ResultSet rs) throws SQLException {
        Server s = new Server();
        s.setId(rs.getLong("id"));
        s.setName(rs.getString("name"));
        s.setType(rs.getString("type"));
        s.setRegion(rs.getString("region"));
        s.setStatus(rs.getString("status"));
        s.setMode(rs.getString("mode"));
        s.setCpu(rs.getDouble("cpu"));
        s.setMemory(rs.getDouble("memory"));
        s.setCost(rs.getDouble("cost"));
        s.setUptime(rs.getString("uptime"));
        s.setIsProtected(rs.getBoolean("is_protected"));
        long orgId = rs.getLong("organization_id");
        if (!rs.wasNull()) s.setOrganizationId(orgId);
        long deptId = rs.getLong("department_id");
        if (!rs.wasNull()) s.setDepartmentId(deptId);
        long empId = rs.getLong("employee_id");
        if (!rs.wasNull()) s.setEmployeeId(empId);
        s.setCreatedAt(rs.getLong("created_at"));
        s.setUpdatedAt(rs.getLong("updated_at"));
        return s;
    }
}
