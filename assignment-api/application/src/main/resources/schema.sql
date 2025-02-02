DROP TABLE IF EXISTS owners;
DROP TABLE IF EXISTS companies;

CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE owners (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    social_security_number VARCHAR(11) NOT NULL,
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);
