-- Table: persona
CREATE TABLE IF NOT EXISTS public.persona (
    edad INT4 NOT NULL,
    id INT8 NOT NULL,
    genero VARCHAR(20) NOT NULL,
    identificacion VARCHAR(20) NOT NULL,
    telefono VARCHAR(20),
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    PRIMARY KEY (id)
);

-- Index
CREATE UNIQUE INDEX IF NOT EXISTS persona_identificacion_key
ON public.persona USING btree (identificacion);


-- Table: cliente
CREATE TABLE IF NOT EXISTS public.cliente (
    estado BOOLEAN NOT NULL,
    id INT8 NOT NULL,
    clienteid VARCHAR(50) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    CONSTRAINT fkkpvkbjg32bso6riqge70hwcel
        FOREIGN KEY (id)
        REFERENCES public.persona(id),
    PRIMARY KEY (id)
);

-- Index
CREATE UNIQUE INDEX IF NOT EXISTS cliente_clienteid_key
ON public.cliente USING btree (clienteid);


-- Table: cuenta
CREATE TABLE IF NOT EXISTS public.cuenta (
    estado BOOLEAN NOT NULL,
    saldo_inicial NUMERIC(15,2) NOT NULL,
    cliente_id INT8 NOT NULL,
    id INT8 NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    CONSTRAINT fk4p224uogyy5hmxvn8fwa2jlug
        FOREIGN KEY (cliente_id)
        REFERENCES public.cliente(id),
    PRIMARY KEY (id)
);

-- Index
CREATE UNIQUE INDEX IF NOT EXISTS cuenta_numero_cuenta_key
ON public.cuenta USING btree (numero_cuenta);


-- Table: movimiento
CREATE TABLE IF NOT EXISTS public.movimiento (
    saldo NUMERIC(15,2) NOT NULL,
    valor NUMERIC(15,2) NOT NULL,
    cuenta_id INT8 NOT NULL,
    fecha TIMESTAMP NOT NULL,
    id INT8 NOT NULL,
    tipo_movimiento VARCHAR(10) NOT NULL,
    CONSTRAINT fk4ea11fe7p3xa1kwwmdgi9f2fi
        FOREIGN KEY (cuenta_id)
        REFERENCES public.cuenta(id),
    PRIMARY KEY (id)
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