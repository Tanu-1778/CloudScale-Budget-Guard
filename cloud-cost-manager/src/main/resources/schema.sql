-- CloudScale Budget Guard - Database Schema for MySQL

CREATE DATABASE IF NOT EXISTS clouddb;
USE clouddb;

-- Table: app_users
CREATE TABLE IF NOT EXISTS app_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    display_name VARCHAR(100),
    email VARCHAR(100),
    organization_id BIGINT,
    department_id BIGINT,
    employee_id BIGINT,
    created_at BIGINT
);

-- Table: organizations
CREATE TABLE IF NOT EXISTS organizations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    plan VARCHAR(50),
    users INT,
    servers INT,
    monthly_spend BIGINT,
    status VARCHAR(20),
    license VARCHAR(50),
    expiry VARCHAR(20),
    contact VARCHAR(100),
    joined_date VARCHAR(20),
    cloud_provider VARCHAR(50),
    payment_method VARCHAR(50),
    payment_status VARCHAR(20),
    storage_limit VARCHAR(50),
    created_at BIGINT,
    updated_at BIGINT
);

-- Table: departments
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organization_id BIGINT,
    name VARCHAR(100) NOT NULL,
    employee_count INT,
    manager_name VARCHAR(100),
    manager_email VARCHAR(100),
    budget BIGINT,
    spent BIGINT,
    status VARCHAR(20),
    created_at BIGINT,
    updated_at BIGINT
);

-- Table: employees
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organization_id BIGINT,
    department_id BIGINT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    designation VARCHAR(50),
    role VARCHAR(50),
    salary BIGINT,
    status VARCHAR(20),
    joined_date VARCHAR(20),
    created_at BIGINT,
    updated_at BIGINT
);

-- Table: servers
CREATE TABLE IF NOT EXISTS servers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    region VARCHAR(50),
    status VARCHAR(20),
    mode VARCHAR(20),
    cpu DOUBLE,
    memory DOUBLE,
    cost DOUBLE,
    uptime VARCHAR(50),
    is_protected BOOLEAN,
    organization_id BIGINT,
    department_id BIGINT,
    employee_id BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

-- Table: budgets
CREATE TABLE IF NOT EXISTS budgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organization_id BIGINT,
    department_id BIGINT,
    employee_id BIGINT,
    total_budget DOUBLE,
    spent DOUBLE,
    remaining DOUBLE,
    currency VARCHAR(10),
    last_updated BIGINT
);

-- Table: billing
CREATE TABLE IF NOT EXISTS billing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organization_id BIGINT,
    invoice_number VARCHAR(50),
    amount DOUBLE,
    status VARCHAR(20),
    billing_date VARCHAR(20),
    due_date VARCHAR(20),
    created_at BIGINT
);

-- Table: schedules
CREATE TABLE IF NOT EXISTS schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    server_id BIGINT,
    start_time VARCHAR(20),
    end_time VARCHAR(20),
    days_of_week VARCHAR(100),
    action VARCHAR(20),
    is_active BOOLEAN,
    created_at BIGINT
);

-- Table: action_logs
CREATE TABLE IF NOT EXISTS action_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20),
    msg TEXT,
    time VARCHAR(20),
    created_at BIGINT
);
