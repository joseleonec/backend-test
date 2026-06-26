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

--- DATA

-- PERSONA
INSERT INTO public.persona (edad, id, genero, identificacion, telefono, nombre, direccion)
VALUES (35, 1, 'M', '1750000001', '098254785', 'Jose Lema', 'Otavalo sn y principal');

INSERT INTO public.persona (edad, id, genero, identificacion, telefono, nombre, direccion)
VALUES (28, 2, 'F', '1750000002', '097548965', 'Marianela Montalvo', 'Amazonas y NNUU');

INSERT INTO public.persona (edad, id, genero, identificacion, telefono, nombre, direccion)
VALUES (42, 3, 'M', '1750000003', '098874587', 'Juan Osorio', '13 junio y Equinoccial');

INSERT INTO public.persona (edad, id, genero, identificacion, telefono, nombre, direccion)
VALUES (30, 4, 'M', '1234567890', '0991234567', 'Juan Perez', 'Calle 1');


-- CLIENTE
INSERT INTO public.cliente (estado, id, clienteid, contrasena)
VALUES (true, 1, 'joselema', '$2a$10$pPqMSAblWtUmMxBV.ElBHOGNLVths8PaAirz8k//wY8/qpLNeknjy');

INSERT INTO public.cliente (estado, id, clienteid, contrasena)
VALUES (true, 2, 'marimontalvo', '$2a$10$G8lp7UubZGqjjR7.5fPLquPBG7xkn77uFMSnJ54a0Au65MX0dNVhi');

INSERT INTO public.cliente (estado, id, clienteid, contrasena)
VALUES (true, 3, 'juanosorio', '$2a$10$yWmUh9TzPk0b3j6DtbjJxO0rKB8WohByqnjhKfK0CQMEbbzcsbCxq');

INSERT INTO public.cliente (estado, id, clienteid, contrasena)
VALUES (true, 4, 'juanp', '$2a$10$Sh1qyGPBFa7Kz0XebLgaQephixNCdTOanD71g2oonSHZ1CLbMjebC');


-- CUENTA
INSERT INTO public.cuenta (estado, saldo_inicial, cliente_id, id, numero_cuenta, tipo_cuenta)
VALUES (true, 1600.00, 1, 1, '478758', 'Ahorros');

INSERT INTO public.cuenta (estado, saldo_inicial, cliente_id, id, numero_cuenta, tipo_cuenta)
VALUES (true, 1300.00, 2, 2, '225487', 'Corriente');

INSERT INTO public.cuenta (estado, saldo_inicial, cliente_id, id, numero_cuenta, tipo_cuenta)
VALUES (true, 300.00, 3, 3, '495878', 'Ahorros');

INSERT INTO public.cuenta (estado, saldo_inicial, cliente_id, id, numero_cuenta, tipo_cuenta)
VALUES (true, 0.00, 2, 4, '496825', 'Ahorros');

INSERT INTO public.cuenta (estado, saldo_inicial, cliente_id, id, numero_cuenta, tipo_cuenta)
VALUES (true, 1000.00, 1, 5, '585545', 'Corriente');


-- MOVIMIENTO
INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (1425.00, -575.00, 1, '2026-06-26 12:35:18.818321', 1, 'DEBITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (700.00, 600.00, 2, '2026-06-26 12:35:22.794138', 2, 'CREDITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (150.00, 150.00, 3, '2026-06-26 12:35:25.912274', 3, 'CREDITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (0.00, -540.00, 4, '2026-06-26 12:35:29.500090', 4, 'DEBITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (1925.00, 500.00, 1, '2026-06-26 12:36:44.447267', 5, 'CREDITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (1600.00, -325.00, 1, '2026-06-26 12:36:53.341958', 6, 'DEBITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (1300.00, 600.00, 2, '2026-06-26 12:51:47.871382', 7, 'CREDITO');

INSERT INTO public.movimiento (saldo, valor, cuenta_id, fecha, id, tipo_movimiento)
VALUES (300.00, 150.00, 3, '2026-06-26 12:52:53.181006', 8, 'CREDITO');