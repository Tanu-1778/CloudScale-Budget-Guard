package com.cloud.dao;

import com.cloud.model.Employee;
import com.cloud.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Employee using JDBC
 */
public class EmployeeDAO {

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> findByDepartmentId(Long deptId) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void save(Employee e) {
        String sql = "INSERT INTO employees (organization_id, department_id, first_name, last_name, email, role, designation, salary, status, join_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, e.getOrganizationId());
            ps.setLong(2, e.getDepartmentId());
            ps.setString(3, e.getFirstName());
            ps.setString(4, e.getLastName());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getRole());
            ps.setString(7, e.getDesignation());
            ps.setLong(8, e.getSalary());
            ps.setString(9, e.getStatus());
            ps.setString(10, e.getJoinDate());
            ps.setLong(11, System.currentTimeMillis());
            ps.setLong(12, System.currentTimeMillis());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) e.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e1) {
            e.printStackTrace();
        }
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getLong("id"));
        e.setOrganizationId(rs.getLong("organization_id"));
        e.setDepartmentId(rs.getLong("department_id"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setEmail(rs.getString("email"));
        e.setRole(rs.getString("role"));
        e.setDesignation(rs.getString("designation"));
        e.setSalary(rs.getLong("salary"));
        e.setStatus(rs.getString("status"));
        e.setJoinDate(rs.getString("join_date"));
        return e;
    }
}
