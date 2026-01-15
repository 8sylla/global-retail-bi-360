-- =============================================
-- 1. NETTOYAGE (Si vous devez relancer le script)
-- =============================================
DROP TABLE IF EXISTS Fact_Sales;
DROP TABLE IF EXISTS Dim_Manager;
DROP TABLE IF EXISTS Dim_Customer;
DROP TABLE IF EXISTS Dim_Product;
DROP TABLE IF EXISTS Dim_Location;

-- =============================================
-- 2. CRÉATION DES TABLES DE DIMENSION
-- =============================================

-- Dimension LOCATION (Géographie)
CREATE TABLE Dim_Location (
    Location_Key SERIAL PRIMARY KEY,
    City VARCHAR(100),
    State VARCHAR(100),
    Country VARCHAR(100),
    Region VARCHAR(100),
    Market VARCHAR(50)
);

-- Dimension PRODUCT (Produit)
CREATE TABLE Dim_Product (
    Product_Key SERIAL PRIMARY KEY,
    Product_ID_Source VARCHAR(50),
    Category VARCHAR(50),
    Sub_Category VARCHAR(50),
    Product_Name VARCHAR(255)
);

-- Dimension CUSTOMER (Client)
CREATE TABLE Dim_Customer (
    Customer_Key SERIAL PRIMARY KEY,
    Customer_ID_Source VARCHAR(50),
    Customer_Name VARCHAR(100),
    Segment VARCHAR(50)
);

-- Dimension MANAGER (Issu du fichier Excel RH)
CREATE TABLE Dim_Manager (
    Manager_Key SERIAL PRIMARY KEY,
    Region VARCHAR(100),
    Manager_Name VARCHAR(100),
    Yearly_Target NUMERIC(18,2)
);

-- =============================================
-- 3. CRÉATION DE LA TABLE DE FAIT
-- =============================================
CREATE TABLE Fact_Sales (
    Fact_ID SERIAL PRIMARY KEY,
    
    -- Clés étrangères (Relations vers les dimensions)
    Location_Key INT REFERENCES Dim_Location(Location_Key),
    Product_Key INT REFERENCES Dim_Product(Product_Key),
    Customer_Key INT REFERENCES Dim_Customer(Customer_Key),
    Manager_Key INT REFERENCES Dim_Manager(Manager_Key),
    
    -- Attributs de la commande
    Order_ID VARCHAR(50),
    Order_Date DATE,
    Ship_Date DATE,
    Ship_Mode VARCHAR(50),
    
    -- Métriques (Chiffres)
    Sales NUMERIC(18,2),
    Quantity INT,
    Discount NUMERIC(18,2),
    Profit NUMERIC(18,2),
    Shipping_Cost NUMERIC(18,2),
    
    -- Info logistique (Issu du XML)
    Is_Returned BOOLEAN DEFAULT FALSE, -- BOOLEAN en Postgres
    Return_Reason VARCHAR(100) -- 'Defective', 'Late', etc.
);