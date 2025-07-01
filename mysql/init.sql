-- BD_DITTO 
DROP DATABASE IF EXISTS BD_DITTO;
CREATE DATABASE BD_DITTO;
USE BD_DITTO;

-- Tabla Menu
CREATE TABLE Menu (
    idMenu INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    icono VARCHAR(50),
    url VARCHAR(50)
);

-- Tabla Rol
CREATE TABLE Rol (
    idRol INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP
);



-- Tabla MenuRol
CREATE TABLE MenuRol (
    idMenuRol INT PRIMARY KEY AUTO_INCREMENT,
    idMenu INT,
    idRol INT,
    FOREIGN KEY (idMenu) REFERENCES Menu(idMenu),
    FOREIGN KEY (idRol) REFERENCES Rol(idRol)
);

-- Tabla Usuario
CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombreCompleto VARCHAR(100),
    correo VARCHAR(40),
    idRol INT,
    clave VARCHAR(255),
    esActivo TINYINT(1) DEFAULT 1,
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idRol) REFERENCES Rol(idRol)
);

-- Tabla: Categoria
CREATE TABLE Categoria (
    idCategoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    esActivo TINYINT(1) DEFAULT 1,
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: Producto
CREATE TABLE Producto (
    idProducto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    idCategoria INT,
    stock INT,
    precio DECIMAL(10,2),
    esActivo TINYINT(1) DEFAULT 1,
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria)
);

-- Tabla: Venta
CREATE TABLE Venta (
    idVenta INT PRIMARY KEY AUTO_INCREMENT,
    numeroDocumento VARCHAR(40),
    tipoPago VARCHAR(50),
    total DECIMAL(10,2),
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: DetalleVenta (detalle de productos vendidos por venta)
CREATE TABLE DetalleVenta (
    idDetalleVenta INT PRIMARY KEY AUTO_INCREMENT,
    idVenta INT,
    idProducto INT,
    cantidad INT,
    precio DECIMAL(10,2),
    total DECIMAL(10,2),
    FOREIGN KEY (idVenta) REFERENCES Venta(idVenta),
    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
);

-- Tabla: NumeroDocumento (controla el número del comprobante)
CREATE TABLE NumeroDocumento (
    idNumeroDocumento INT PRIMARY KEY AUTO_INCREMENT,
   ultimo_Numero INT NOT NULL UNIQUE,
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP
);



-- Insertar Registros
INSERT INTO Rol (nombre) VALUES ('Admin');
INSERT INTO Rol (nombre) VALUES ('User');
INSERT INTO Rol (nombre) VALUES ('Supervisor');


INSERT INTO Menu (nombre, icono, url) VALUES ('DashBoard', 'dashboard', '/pages/dashboard');
INSERT INTO Menu (nombre, icono, url) VALUES ('Usuarios', 'group', '/pages/usuario');
INSERT INTO Menu (nombre, icono, url) VALUES ('Productos', 'collections_bookmark', '/pages/producto');
INSERT INTO Menu (nombre, icono, url) VALUES ('Venta', 'currency_exchange', '/pages/venta');
INSERT INTO Menu (nombre, icono, url) VALUES ('Historial Ventas', 'edit_note', '/pages/historial');
INSERT INTO Menu (nombre, icono, url) VALUES ('Reportes', 'receipt', '/pages/reporte');

-- Menus para administrador
INSERT INTO MenuRol (idMenu, idRol) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1);

-- Menus para empleado
INSERT INTO MenuRol (idMenu, idRol) VALUES
(4, 2),
(5, 2);

-- Menus para supervisor
INSERT INTO MenuRol (idMenu, idRol) VALUES
(3, 3),
(4, 3),
(5, 3),
(6, 3);



INSERT INTO Usuario (nombreCompleto, correo, idRol, clave) 
VALUES ('Admin User', 'admin@example.com', 1, 'admin123');

INSERT INTO Usuario (nombreCompleto, correo, idRol, clave) 
VALUES ('Regular User', 'user@example.com', 2, 'user123');

INSERT INTO Usuario (nombreCompleto, correo, idRol, clave) 
VALUES ('Supervisor user', 'supervisor@example.com', 3, 'supervisor123');



INSERT INTO NumeroDocumento (ultimo_Numero) 
VALUES (1000);

-- Insertar Categoría
INSERT INTO Categoria (nombre) VALUES
('Tecnología y Accesorios'),
('Moda y Accesorios Personales');

-- Insertar Productos para Tecnología y Accesorios (idCategoria = 1)
INSERT INTO Producto (nombre, idCategoria, stock, precio) VALUES
('Audífonos Bluetooth', 1, 50, 39.90),
('Soporte para Celular', 1, 60, 12.00),
('Memoria USB 128GB', 1, 100, 45);

-- Insertar Productos para Moda y Accesorios Personales (idCategoria = 2)
INSERT INTO Producto (nombre, idCategoria, stock, precio) VALUES
('Reloj Deportivo', 2, 20, 49.90),
('Cartera de Cuero', 2, 15, 59.00),
('Pulsera de Acero', 2, 30, 20.00);


-- ------------------------------------------------



-- Listar Productos x Categoría

DELIMITER $$
CREATE PROCEDURE ListarProductosXCategoria()
BEGIN
    SELECT 
        p.idProducto,
        p.nombre AS nombreProducto,
        c.nombre AS nombreCategoria,
        p.stock,
        p.precio,
        p.esActivo,
        p.fechaRegistro
    FROM Producto p
    INNER JOIN Categoria c ON p.idCategoria = c.idCategoria;
END$$
DELIMITER ;

CALL ListarProductosXCategoria
-- Listar Usuarios con su rol

DELIMITER $$
CREATE PROCEDURE ListarUsuariosxRol()
BEGIN
    SELECT 
        u.idUsuario,
        u.nombreCompleto,
        u.correo,
        r.nombre AS rol,
        u.esActivo,
        u.fechaRegistro
    FROM Usuario u
    INNER JOIN Rol r ON u.idRol = r.idRol;
END$$
DELIMITER ;



-- Listar detalles de ventas con información de productos y totales

DELIMITER $$
CREATE PROCEDURE ListarDetalleDeVentas()
BEGIN
    SELECT 
        v.idVenta,
        v.numeroDocumento,
        v.tipoPago,
        p.nombre AS producto,
        dv.cantidad,
        dv.precio,
        dv.total,
        v.fechaRegistro
    FROM DetalleVenta dv
    INNER JOIN Venta v ON dv.idVenta = v.idVenta
    INNER JOIN Producto p ON dv.idProducto = p.idProducto
    ORDER BY v.idVenta;
END$$
DELIMITER ;

-- REGISTRAR PRODUCTO
DROP PROCEDURE IF EXISTS ActualizarProducto;
DELIMITER $$
CREATE PROCEDURE RegistrarProducto(
	IN p_nombre VARCHAR(100),
    IN p_idCategoria INT,
    IN p_stock INT,
    IN p_precio DECIMAL(10,2),
    IN p_esActivo TINYINT
)
BEGIN
    INSERT INTO Producto(nombre, idCategoria, stock, precio, esActivo)
    VALUES (p_nombre, p_idCategoria, p_stock, p_precio, p_esActivo);
END$$
DELIMITER ;


-- ACTUALIZAR PRODUCTO

DELIMITER $$
CREATE PROCEDURE ActualizarProducto(
    IN p_idProducto INT,
    IN p_nombre VARCHAR(100),
    IN p_idCategoria INT,
    IN p_stock INT,
    IN p_precio DECIMAL(10,2),
    IN p_esActivo TINYINT
)
BEGIN
    UPDATE Producto
    SET nombre = p_nombre,
        idCategoria = p_idCategoria,
        stock = p_stock,
        precio = p_precio,
        esActivo = p_esActivo
    WHERE idProducto = p_idProducto;
END$$
DELIMITER ;


-- Eliminar Producto

DELIMITER $$
CREATE PROCEDURE DesactivarProducto(
    IN p_idProducto INT
)
BEGIN
    UPDATE Producto
    SET esActivo = 0
    WHERE idProducto = p_idProducto;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE ObtenerProductosPorIds(
    IN ids TEXT
)
BEGIN
    SET @query = CONCAT('SELECT * FROM Producto WHERE FIND_IN_SET(idProducto, "', ids, '")');
    PREPARE stmt FROM @query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END$$
DELIMITER ;

-- ÍNDICES

 -- Buscar productos por nombre (para búsquedas con LIKE)
CREATE INDEX idx_producto_nombre ON Producto(nombre);

-- Mejorar JOIN con Categoria
CREATE INDEX idx_producto_categoria ON Producto(idCategoria);

-- Para filtrar por estado (si haces consultas por esActivo)
CREATE INDEX idx_producto_estado ON Producto(esActivo);

-- Si haces muchas búsquedas por nombre de categoría
CREATE INDEX idx_categoria_nombre ON Categoria(nombre);

-- Para filtros por estado activo
CREATE INDEX idx_categoria_estado ON Categoria(esActivo);

-- Buscar usuarios por correo (login)
CREATE UNIQUE INDEX idx_usuario_correo ON Usuario(correo);

-- Relación con Rol (JOIN)
CREATE INDEX idx_usuario_rol ON Usuario(idRol);

-- Filtro por estado
CREATE INDEX idx_usuario_estado ON Usuario(esActivo);






