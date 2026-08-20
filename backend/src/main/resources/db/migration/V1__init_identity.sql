CREATE TABLE user_identity (
    id BIGSERIAL PRIMARY KEY,
    user_type VARCHAR(20) NOT NULL,
    identifier VARCHAR(50) NOT NULL UNIQUE,
    display_name_th VARCHAR(100) NOT NULL,
    display_name_en VARCHAR(100) NOT NULL,
    status_or_emp_type VARCHAR(50) NOT NULL,
    faculty_or_organization VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    level_name VARCHAR(50),
    stats_id VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_identity_identifier ON user_identity(identifier);
