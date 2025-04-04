CREATE TABLE IF NOT EXISTS franquicias (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sucursales (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    franquicia_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (franquicia_id) REFERENCES franquicias(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS productos (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    stock INT NOT NULL,
    sucursal_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
    ON DELETE CASCADE
);