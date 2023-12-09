CREATE DATABASE IF NOT EXISTS task_db;
USE task_db;

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role BIGINT NOT NULL,
    FOREIGN KEY (role) REFERENCES roles(id)
);

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT,
    status TEXT,
    author_id BIGINT,
    assignee_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (assignee_id) REFERENCES users(id)
);

CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment TEXT NOT NULL,
    usr BIGINT NOT NULL,
    tsk BIGINT NOT NULL,
    FOREIGN KEY (usr) REFERENCES users(id),
    FOREIGN KEY (tsk) REFERENCES tasks(id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');