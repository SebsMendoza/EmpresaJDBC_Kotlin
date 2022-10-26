--Tabla departamento
DROP TABLE IF EXISTS departamento;
CREATE TABLE departamento
(
    id          INT PRIMARY KEY,
    nombre      TEXT NOT NULL,
    presupuesto REAL
);
INSERT INTO departamento (id, nombre, presupuesto)
VALUES (1, 'Análisis', 200000);
INSERT INTO departamento (id, nombre, presupuesto)
VALUES (2, 'Programación', 400000);
INSERT INTO departamento (id, nombre, presupuesto)
VALUES (3, 'BBDD', 300000);

DROP TABLE IF EXISTS programador;
CREATE TABLE programador
(
    id      INT PRIMARY KEY,
    nombre  TEXT NOT NULL,
    fecha   DATETIME,
    id_dept INT,
    FOREIGN KEY (id_dept) REFERENCES departamento (id)
);
INSERT INTO programador (id, nombre, fecha, id_dept)
VALUES (1, 'Mario', CURRENT_TIME(), 1);
INSERT INTO programador (id, nombre, fecha, id_dept)
VALUES (2, 'Alfredo', CURRENT_TIME(), 1);
INSERT INTO programador (id, nombre, fecha, id_dept)
VALUES (3, 'Sebastian', CURRENT_TIME(), 2);
INSERT INTO programador (id, nombre, fecha, id_dept)
VALUES (4, 'Sandra', CURRENT_TIME(), 3);
