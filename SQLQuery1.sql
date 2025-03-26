Create database QLBanHang1

Use QLBanHang1



-- Bảng Nhà cung cấp (Supplier)
CREATE TABLE Supplier (
    SupplierID INT IDENTITY(1,1) PRIMARY KEY,
    SupplierName NVARCHAR(100) NOT NULL,
    Contact NVARCHAR(100),
    Phone NVARCHAR(20)
);

select * FROM Product ;

CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(100) NOT NULL,
    Price DECIMAL(18,2) NOT NULL CHECK (Price >= 0), -- Giá không được âm
    Quantity INT NOT NULL CHECK (Quantity >= 0), -- Số lượng không được âm
    Unit NVARCHAR(50),
    SupplierID NVARCHAR(100),
    Category NVARCHAR(50) NOT NULL CHECK (Category IN (N'Thực phẩm', N'Đồ uống', N'Gia vị', N'Đồ dùng cá nhân')),
    MinStock INT NOT NULL DEFAULT 5
);


INSERT INTO Product (ProductName, Price, Quantity, Unit, SupplierID, Category, MinStock)
VALUES 
(N'Gạo ST25', 25000, 50, N'Kg', N'Công ty ABC', N'Thực phẩm', 10),
(N'Coca Cola', 10000, 100, N'Lon', N'Công ty XYZ', N'Đồ uống', 20),
(N'Nước mắm Nam Ngư', 20000, 30, N'Chai', N'Công ty 123', N'Gia vị', 5),
(N'Kem đánh răng P/S', 15000, 20, N'Tuýp', N'Công ty P/S', N'Đồ dùng cá nhân', 10),
(N'Trứng gà ta', 5000, 12, N'Quả', N'Trang trại Gia Hưng', N'Thực phẩm', 15),
(N'Bia Heineken', 18000, 80, N'Chai', N'Công ty Bia VN', N'Đồ uống', 25),
(N'Muối i-ốt', 7000, 40, N'Kg', N'Công ty Muối VN', N'Gia vị', 5),
(N'Dầu gội Clear', 35000, 15, N'Chai', N'Công ty Unilever', N'Đồ dùng cá nhân', 8);

SELECT name, definition
FROM sys.check_constraints
WHERE parent_object_id = OBJECT_ID('dbo.Product');

SELECT DISTINCT Category FROM Product;



CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(100) NOT NULL,
    Price DECIMAL(18,2) NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity >= 0),
    Unit NVARCHAR(50),
    Supplier NVARCHAR(100),
    Category NVARCHAR(50) CHECK (Category IN ('Thực phẩm', 'Đồ uống', 'Gia vị', 'Đồ dùng cá nhân')),
    MinStock INT NOT NULL DEFAULT 5
);



CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(255) NOT NULL,
    Price DECIMAL(18,2) NOT NULL,
    Quantity INT NOT NULL,
    Unit NVARCHAR(50),
    SupplierID NVARCHAR(255),
    MinStock INT NOT NULL
);
GO
SELECT * FROM Product

INSERT INTO Product (ProductName, Price, Quantity, Unit, SupplierID, MinStock) VALUES
(N'Táo Mỹ', 50000, 100, 'kg', N'Nhà cung cấp A', 10),
(N'Chuối Laba', 30000, 200, 'kg', N'Nhà cung cấp B', 15),
(N'Nho Đỏ', 70000, 50, 'kg', N'Nhà cung cấp C', 5),
(N'Sữa Vinamilk', 25000, 150, N'hộp', 'Vinamilk', 20),
(N'Gạo ST25', 200000, 30, 'bao', N'Nhà cung cấp D', 5);
GO


-- Bảng Nhân viên (Employee)
CREATE TABLE Employee (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100) NOT NULL,
    Role NVARCHAR(50) NOT NULL, -- Thu ngân, quản lý kho, chủ cửa hàng
    Phone NVARCHAR(20),
    Email NVARCHAR(100)
);

SELECT * FROM Employee1

-- Bảng Khách hàng (Customer)
DROP TABLE Customer 
CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100),
    Phone NVARCHAR(20),
    Email NVARCHAR(100),
    Points INT DEFAULT 0
);

-- Bảng Hóa đơn (Invoice)
CREATE TABLE Invoice (
    InvoiceID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerID INT NULL,
    EmployeeID INT NOT NULL,
    TotalAmount DECIMAL(18,2) NOT NULL,
    InvoiceDate DATETIME DEFAULT GETDATE(),
    PaymentMethod NVARCHAR(50), -- Tiền mặt, thẻ ngân hàng, ví điện tử
    CONSTRAINT FK_Invoice_Customer FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    CONSTRAINT FK_Invoice_Employee FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

-- Bảng Chi tiết hóa đơn (Invoice_Detail)
CREATE TABLE Invoice_Detail (
    InvoiceDetailID INT IDENTITY(1,1) PRIMARY KEY,
    InvoiceID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(18,2) NOT NULL,
    CONSTRAINT FK_InvoiceDetail_Invoice FOREIGN KEY (InvoiceID) REFERENCES Invoice(InvoiceID),
    CONSTRAINT FK_InvoiceDetail_Product FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);



-- Bảng Nhập hàng (Stock_Entry)
CREATE TABLE Stock_Entry (
    EntryID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT NOT NULL,
    SupplierID INT NOT NULL,
    Quantity INT NOT NULL,
    EntryDate DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_StockEntry_Product FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    CONSTRAINT FK_StockEntry_Supplier FOREIGN KEY (SupplierID) REFERENCES Supplier(SupplierID)
);

-- Bảng Tồn kho (Inventory)
CREATE TABLE Inventory (
    InventoryID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    LastUpdated DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Inventory_Product FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

CREATE TABLE Admin (
    userAdmin Char(50) Not NULL,
	password VARCHAR(150) NOT NULL
);








-- 🔹 Tạo CSDL
CREATE DATABASE QLBanHang1;
GO
USE QLBanHang1;
GO

-- 🔹 Tạo bảng Admin (Người quản lý cấp tài khoản cho nhân viên)
CREATE TABLE Admin (
    AdminID INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL
);
GO

-- 🔹 Tạo bảng Employee (Quản lý nhân viên)
CREATE TABLE Employee (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(15) UNIQUE NOT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Thu ngân', 'Quản lý kho', 'Chủ cửa hàng')),
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

-- 🔹 Chèn dữ liệu mẫu vào bảng Admin
INSERT INTO Admin (Username, Password) VALUES
('admin', '123456'),  -- Mật khẩu mẫu (Cần mã hóa khi thực tế)
('superadmin', 'admin@123');
GO

-- 🔹 Chèn dữ liệu mẫu vào bảng Employee
INSERT INTO Employee (Name, Phone, Role) VALUES
(N'Nguyễn Văn A', '0987654321', 'Thu ngân'),
(N'Trần Thị B', '0971122334', 'Quản lý kho'),
(N'Lê Văn C', '0911223344', 'Chủ cửa hàng');
GO

SELECT * FROM Employee

DROP TABLE Employee1
CREATE TABLE Employee1 (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(20) NOT NULL,
    Role NVARCHAR(50) CHECK (Role IN (N'Thu ngân', N'Quản lý kho', N'Chủ cửa hàng')) NOT NULL,
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    ShiftStart DATETIME,
    ShiftEnd DATETIME,
    SalesPerformance INT DEFAULT 0
);


INSERT INTO Employee1 (Name, Phone, Role, Username, Password, ShiftStart, ShiftEnd, SalesPerformance)
VALUES 
(N'Nguyễn Văn A', '0987654321', N'Thu ngân', 'cashier01', '123456', '2024-03-07 08:00:00', '2024-03-07 16:00:00', 50),
(N'Trần Thị B', '0971234567', N'Quản lý kho', 'warehouse01', '123456', '2024-03-07 09:00:00', '2024-03-07 17:00:00', 30),
(N'Phạm Văn C', '0912345678', N'Chủ cửa hàng', 'admin01', 'admin123', NULL, NULL, 0);


SELECT * FROM Employee1

CREATE TABLE Customer (
    CustomerID NVARCHAR(50) PRIMARY KEY,
    CustomerName NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    email NVARCHAR(100),
    point NVARCHAR(10) NOT NULL
);

INSERT INTO Customer (CustomerID, CustomerName, phone, email, point) VALUES
('C001', 'Nguyễn Văn A', '0987654321', 'nguyenvana@gmail.com', '100'),
('C002', 'Trần Thị B', '0912345678', 'tranthib@gmail.com', '200'),
('C003', 'Lê Văn C', '0923456789', 'levanc@gmail.com', '50');

SELECT COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'customer';



CREATE TABLE invoices (
    invoice_id INT IDENTITY(1,1) PRIMARY KEY,
    total_amount Float,
    discount_amount Float,
    final_amount Float,
    invoice_date DATETIME DEFAULT GETDATE()  -- Thay CURRENT_TIMESTAMP bằng GETDATE() trong MSSQL
);

SELECT * FROM invoice_details

SELECT * FROM invoices

CREATE TABLE invoice_details (
    detail_id INT IDENTITY(1,1) PRIMARY KEY,  -- Sử dụng IDENTITY thay vì AUTO_INCREMENT
    invoice_id INT,
    product_name VARCHAR(255),
    quantity INT,
    price Float,
    total_price Float,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
);

ALTER TABLE invoices
ADD EmployeeID INT;  -- Thêm cột employee_id để lưu trữ ID của nhân viên bán hàng
ALTER TABLE invoices
ADD CONSTRAINT FK_Employee FOREIGN KEY (EmployeeID) REFERENCES Employee1(EmployeeID);

INSERT INTO invoices (customer_id, total_amount, discount_amount, final_amount, invoice_date, EmployeeID)
VALUES (1, 500000, 100000, 400000, GETDATE(), 1);


SELECT 
    c.CustomerName AS customer_name,
    id.product_name,
    id.quantity,
    id.price,
    id.total_price
FROM 
    invoice_details id
JOIN 
    invoices i ON id.invoice_id = i.invoice_id  -- Kết nối với bảng invoices
JOIN 
    customer c ON i.customer_id = c.CustomerID  -- Kết nối với bảng customer
ORDER BY 
    c.CustomerName, id.product_name;  -- Sắp xếp theo tên khách hàng và tên sản phẩm


SELECT 
    c.CustomerName AS customer_name,  -- Tên khách hàng
    id.product_name,                   -- Tên sản phẩm
    id.quantity,                       -- Số lượng sản phẩm
    id.price,                          -- Giá sản phẩm
    id.total_price,                    -- Tổng tiền cho sản phẩm
    e.Name AS employee_name            -- Tên nhân viên bán hàng
FROM 
    invoice_details id
JOIN 
    invoices i ON id.invoice_id = i.invoice_id  -- Kết nối với bảng invoices
JOIN 
    customer c ON i.customer_id = c.CustomerID  -- Kết nối với bảng customer
JOIN 
    Employee1 e ON i.employee_id = e.EmployeeID  -- Kết nối với bảng Employee1 để lấy thông tin nhân viên
ORDER BY 
    c.CustomerName, id.product_name;  -- Sắp xếp theo tên khách hàng và tên sản phẩm




	
	CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,  -- Sử dụng IDENTITY để cột tự động tăng
    CustomerName NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    email NVARCHAR(100),
    point NVARCHAR(10) NOT NULL
);

INSERT INTO Customer (CustomerName, phone, email, point)
VALUES ('Nguyễn Văn A', '0123456789', 'nguyenvana@example.com', '100');

INSERT INTO Customer (CustomerName, phone, email, point)
VALUES ('Lê Thị B', '0987654321', 'lethib@example.com', '200');


SELECT * FROM Employee1
select * from invoices




SELECT EmployeeID, Name FROM Employee1;




