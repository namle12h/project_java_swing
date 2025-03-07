Create database QLBanHang1

Use QLBanHang1



-- Bảng Nhà cung cấp (Supplier)
CREATE TABLE Supplier (
    SupplierID INT IDENTITY(1,1) PRIMARY KEY,
    SupplierName NVARCHAR(100) NOT NULL,
    Contact NVARCHAR(100),
    Phone NVARCHAR(20)
);


CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(255) NOT NULL,
    Price DECIMAL(18,2) NOT NULL,
    Quantity INT NOT NULL,
    Unit NVARCHAR(50),
    SupplierID INT,
	MinStock INT NOT NULL
  
);

INSERT INTO Product (ProductName, Price, Quantity, Unit, SupplierID, MinStock) VALUES
('Táo Mỹ', 50000, 100, 'kg', 'Nhà cung cấp A', 10),
('Chuối Laba', 30000, 200, 'kg', 'Nhà cung cấp B', 15),
('Nho Đỏ', 70000, 50, 'kg', 'Nhà cung cấp C', 5),
('Sữa Vinamilk', 25000, 150, 'hộp', 'Vinamilk', 20),
('Gạo ST25', 200000, 30, 'bao', 'Nhà cung cấp D', 5);
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








