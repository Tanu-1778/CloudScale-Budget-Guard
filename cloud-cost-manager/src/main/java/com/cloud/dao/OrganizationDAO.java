package com.cloud.dao;

import com.cloud.model.Organization;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Organization using JDBC
 */
public class OrganizationDAO {

    public List<Organization> findAll() {
        List<Organization> orgs = new ArrayList<>();
        String sql = "SELECT * FROM organizations";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orgs.add(mapResultSetToOrganization(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgs;
    }

    public Organization findById(Long id) {
        String sql = "SELECT * FROM organizations WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToOrganization(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Organization o) {
        String sql = "INSERT INTO organizations (name, plan, users, servers, monthly_spend, status, license, expiry, contact, joined_date, cloud_provider, payment_method, payment_status, storage_limit, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getName());
            ps.setString(2, o.getPlan());
            ps.setInt(3, o.getUsers());
            ps.setInt(4, o.getServers());
            ps.setLong(5, o.getMonthlySpend());
            ps.setString(6, o.getStatus());
            ps.setString(7, o.getLicense());
            ps.setString(8, o.getExpiry());
            ps.setString(9, o.getContact());
            ps.setString(10, o.getJoinedDate());
            ps.setString(11, o.getCloudProvider());
            ps.setString(12, o.getPaymentMethod());
            ps.setString(13, o.getPaymentStatus());
            ps.setString(14, o.getStorageLimit());
            ps.setLong(15, System.currentTimeMillis());
            ps.setLong(16, System.currentTimeMillis());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) o.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Organization mapResultSetToOrganization(ResultSet rs) throws SQLException {
        Organization o = new Organization();
        o.setId(rs.getLong("id"));
        o.setName(rs.getString("name"));
        o.setPlan(rs.getString("plan"));
        o.setUsers(rs.getInt("users"));
        o.setServers(rs.getInt("servers"));
        o.setMonthlySpend(rs.getLong("monthly_spend"));
        o.setStatus(rs.getString("status"));
        o.setLicense(rs.getString("license"));
        o.setExpiry(rs.getString("expiry"));
        o.setContact(rs.getString("contact"));
        o.setJoinedDate(rs.getString("joined_date"));
        o.setCloudProvider(rs.getString("cloud_provider"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setPaymentStatus(rs.getString("payment_status"));
        o.setStorageLimit(rs.getString("storage_limit"));
        return o;
    }
}
