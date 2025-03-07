Create database QLBanHang1

Use QLBanHang1



-- Bảng Nhà cung cấp (Supplier)
CREATE TABLE Supplier (
    SupplierID INT IDENTITY(1,1) PRIMARY KEY,
    SupplierName NVARCHAR(100) NOT NULL,
    Contact NVARCHAR(100),
    Phone NVARCHAR(20)
);

DROP TABLE Product ;

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

-- Bảng Khách hàng (Customer)
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








