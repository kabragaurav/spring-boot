-- Create the database
CREATE DATABASE IF NOT EXISTS gauravkabra;

-- Use the database
USE gauravkabra;

-- Optionally, create a user (if needed) and grant privileges
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'toor';
GRANT ALL PRIVILEGES ON gauravkabra.* TO 'root'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

-- Check if the database exists
SHOW DATABASES;

-- Switch to the database to confirm access
USE gauravkabra;

-- Check user privileges
SHOW GRANTS FOR 'root'@'localhost';

-- Verify existing tables (should be empty initially unless JPA has created them)
SHOW TABLES;