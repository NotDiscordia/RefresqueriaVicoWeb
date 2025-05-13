-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS VicoDB;
USE VicoDB;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('Administrador', 'Empleado') NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    numero_celular VARCHAR(20)
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    vendidos INT NOT NULL DEFAULT 0
);

-- Tabla de ventas
CREATE TABLE IF NOT EXISTS Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
);

-- Detalle de cada venta
CREATE TABLE IF NOT EXISTS Detalle_Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES Ventas(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);

-- Movimientos en inventario
CREATE TABLE IF NOT EXISTS Movimientos_Inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    tipo ENUM('Venta', 'Ajuste', 'Compra') NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES Productos(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
);

-- Cortes de caja diarios
CREATE TABLE IF NOT EXISTS Cortes_Caja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    total_ventas DECIMAL(10,2) NOT NULL,
    total_efectivo DECIMAL(10,2) NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
);
