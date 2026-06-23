-- Devsu Banking Assessment — Database Schema
-- Database name: banking

CREATE TABLE persona (
    id             BIGSERIAL    PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    genero         VARCHAR(20)  NOT NULL,
    edad           INT          NOT NULL,
    identificacion VARCHAR(20)  NOT NULL UNIQUE,
    direccion      VARCHAR(200),
    telefono       VARCHAR(20)
);

CREATE TABLE cliente (
    id         BIGINT       PRIMARY KEY REFERENCES persona (id),
    clienteid  VARCHAR(50)  NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estado     BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE cuenta (
    id            BIGSERIAL      PRIMARY KEY,
    numero_cuenta VARCHAR(20)    NOT NULL UNIQUE,
    tipo_cuenta   VARCHAR(20)    NOT NULL,
    saldo_inicial NUMERIC(15, 2) NOT NULL DEFAULT 0,
    estado        BOOLEAN        NOT NULL DEFAULT TRUE,
    cliente_id    BIGINT         NOT NULL REFERENCES cliente (id)
);

CREATE TABLE movimiento (
    id              BIGSERIAL      PRIMARY KEY,
    fecha           TIMESTAMP      NOT NULL DEFAULT NOW(),
    tipo_movimiento VARCHAR(10)    NOT NULL,
    valor           NUMERIC(15, 2) NOT NULL,
    saldo           NUMERIC(15, 2) NOT NULL,
    cuenta_id       BIGINT         NOT NULL REFERENCES cuenta (id)
);
