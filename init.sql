CREATE DATABASE IF NOT EXISTS franquicias_db;
USE franquicias_db;

CREATE TABLE IF NOT EXISTS franquicias (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sucursales (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    franquicia_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (franquicia_id) REFERENCES franquicias(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS productos (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS producto_sucursal (
    id INT NOT NULL AUTO_INCREMENT,
    producto_id INT NOT NULL,
    sucursal_id INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id) ON DELETE CASCADE
);