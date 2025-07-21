// user table

CREATE TABLE admin_users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  is_head BOOLEAN DEFAULT FALSE,
  is_admin BOOLEAN DEFAULT FALSE
);


INSERT INTO admin_users (username, password, is_head, is_admin) VALUES
('admin', 'password', TRUE, TRUE),
('division1', 'divpass1', TRUE, FALSE),
('user1', 'userpass1', FALSE, FALSE);



CREATE TABLE capital_budget_heads (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE
);

======================================================
CREATE TABLE monthly_expenditures (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    financial_year VARCHAR(9) NOT NULL,        -- e.g. '2024-2025'
    month INT NOT NULL,                        -- 1=Jan, ..., 12=Dec
    code VARCHAR(20) NOT NULL,
    head_of_account VARCHAR(255) NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



======================================================EDRMS==========================

CREATE TABLE processes (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name TEXT NOT NULL,
    parent_id INTEGER REFERENCES processes(id) ON DELETE CASCADE,
    level INT NOT NULL, -- to simplify UI rendering
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
