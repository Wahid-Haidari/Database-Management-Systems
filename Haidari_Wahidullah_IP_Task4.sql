--Individual Project
-- While working on the database design, it's useful to start from scratch every time
-- Hence, we drop tables in reverse order they are created (so the foreign key constraints are not violated)

DROP TABLE IF EXISTS Got;
DROP TABLE IF EXISTS Make;
DROP TABLE IF EXISTS Complaint;
DROP TABLE IF EXISTS Purchase;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS RepairCause;
DROP TABLE IF EXISTS ProduceCause;
DROP TABLE IF EXISTS Accident;
DROP TABLE IF EXISTS RepairP1;
DROP TABLE IF EXISTS Product3;
DROP TABLE IF EXISTS Product2;
DROP TABLE IF EXISTS Product1;
DROP TABLE IF EXISTS Account3;
DROP TABLE IF EXISTS Account2;
DROP TABLE IF EXISTS Account1;
DROP TABLE IF EXISTS Request;
DROP TABLE IF EXISTS Repair;
DROP TABLE IF EXISTS Test;
DROP TABLE IF EXISTS Produce;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS UndergradTechnicalStaff;
DROP TABLE IF EXISTS GradTechnicalStaff;
DROP TABLE IF EXISTS TechnicalStaff;
DROP TABLE IF EXISTS QualityCOntroller;
DROP TABLE IF EXISTS Worker;

-- Create tables

--Worker
CREATE TABLE Worker (
    worker_name VARCHAR(64) PRIMARY KEY,
    addr VARCHAR(100),
    salary REAL,
    max_num_prod_per_day INT
);

--Quality Controller
CREATE TABLE QualityController(
    qual_cont_name VARCHAR(64) PRIMARY KEY,
    addr VARCHAR(100),
    salary REAL,
    product_type VARCHAR(10)
    CONSTRAINT CHK_product_type CHECK (product_type IN ('type1', 'type2', 'type3'))
);

--Technical Staff
CREATE TABLE TechnicalStaff(
    tech_staff_name VARCHAR(64) PRIMARY KEY,
    addr VARCHAR(100),
    salary REAL,
    education_record VARCHAR(3),
    technical_position VARCHAR(30),
    CONSTRAINT CHK_education_record CHECK (education_record IN ('BS', 'MS', 'PhD'))
    
);

--Grad Technical Staff
CREATE TABLE GradTechnicalStaff(
    tech_staff_name VARCHAR(64) PRIMARY KEY,
    addr VARCHAR(100),
    salary REAL,
    education_record VARCHAR(3),
    technical_position VARCHAR(30),
    CONSTRAINT CHK_grad_education_record CHECK (education_record IN ('MS', 'PhD'))
);

--Undergrad Technical Staff
CREATE TABLE UndergradTechnicalStaff(
    tech_staff_name VARCHAR(64) PRIMARY KEY,
    addr VARCHAR(100),
    salary REAL,
    education_record VARCHAR(2),
    technical_position VARCHAR(30),
    CONSTRAINT CHK_undergrad_education_record CHECK (education_record IN ('BS'))
);

--Account1
CREATE TABLE Account1(
    account1_num INT PRIMARY KEY,
    establish_date DATE NOT NULL,
    product1_cost REAL
);

--Account2
CREATE TABLE Account2(
    account2_num INT PRIMARY KEY,
    establish_date DATE NOT NULL,
    product2_cost REAL
);

--Account3
CREATE TABLE Account3(
    account3_num INT PRIMARY KEY,
    establish_date DATE NOT NULL,
    product3_cost REAL
);

--Product
CREATE TABLE Product(
    product_id INT PRIMARY KEY,
    production_date DATE NOT NULL, -- YYYY-MM-DD
    time_spent REAL NOT NULL, --THis is in hours
    produced_by VARCHAR(64) NOT NULL,
    tested_by VARCHAR(64) NOT NULL,
    repaired_by VARCHAR(64)
);

--Product1
CREATE TABLE Product1(
    product1_id INT,
    production_date DATE NOT NULL,
    time_spent REAL NOT NULL, --THis is in hours
    produced_by VARCHAR(64) NOT NULL,
    tested_by VARCHAR(64) NOT NULL,
    repaired_by VARCHAR(64), --It is repaired only by a technical staff
    product1_name VARCHAR(64),
    software_used VARCHAR(64),
    account1_num INT,
    PRIMARY KEY (product1_id, account1_num),
    FOREIGN KEY (account1_num) REFERENCES Account1
);

--Product2
CREATE TABLE Product2(
    product2_id INT,
    production_date DATE NOT NULL,
    time_spent REAL NOT NULL, --THis is in hours
    produced_by VARCHAR(64) NOT NULL,
    tested_by VARCHAR(64) NOT NULL,
    repaired_by VARCHAR(64),
    size CHAR(1), -- S, M, L
    color VARCHAR(10),
    account2_num INT,
    PRIMARY KEY (product2_id, account2_num),
    FOREIGN KEY (account2_num) REFERENCES Account2,
    CONSTRAINT CHK_product2_size CHECK (size IN ('S', 'M', 'L'))
);

--Product3
CREATE TABLE Product3(
    product3_id INT,
    production_date DATE NOT NULL,
    time_spent REAL NOT NULL, --THis is in hours
    produced_by VARCHAR(64) NOT NULL,
    tested_by VARCHAR(64) NOT NULL,
    repaired_by VARCHAR(64),
    size CHAR(1), -- S, M, L
    weight REAL, -- In grams
    account3_num INT,
    PRIMARY KEY (product3_id, account3_num),
    FOREIGN KEY (account3_num) REFERENCES Account3,
    CONSTRAINT CHK_product3_size CHECK (size IN ('S', 'M', 'L'))
);

--Produce
CREATE TABLE Produce(
    worker_name VARCHAR(64),
    product_id INT,
    PRIMARY KEY (worker_name, product_id),
    FOREIGN KEY (worker_name) REFERENCES Worker,
    FOREIGN KEY (product_id) REFERENCES Product
);

CREATE NONCLUSTERED INDEX produce_nonclustered ON Produce (worker_name);

--Check
CREATE TABLE Test(
    qual_cont_name VARCHAR(64),
    product_id INT,
    PRIMARY KEY (qual_cont_name, product_id),
    FOREIGN KEY (qual_cont_name) REFERENCES QualityController,
    FOREIGN KEY (product_id) REFERENCES Product
);

--Repair
CREATE TABLE Repair(
    tech_staff_name VARCHAR(64),
    product_id INT,
    repair_date DATE,
    PRIMARY KEY (tech_staff_name, product_id),
    FOREIGN KEY (tech_staff_name) REFERENCES TechnicalStaff,
    FOREIGN KEY (product_id) REFERENCES Product,

);

--Repair for product1 and graduate technical staff
CREATE TABLE RepairP1(
    tech_staff_name VARCHAR(64),
    product1_id INT,
    account1_num INT,
    repair_date DATE,
    PRIMARY KEY (tech_staff_name, product1_id),
    FOREIGN KEY (tech_staff_name) REFERENCES GradTechnicalStaff,
    FOREIGN KEY (product1_id, account1_num) REFERENCES Product1
);

--Request
CREATE TABLE Request(
    qual_cont_name VARCHAR(64),
    product_id INT,
    tech_staff_name VARCHAR(64),
    PRIMARY KEY (qual_cont_name, product_id, tech_staff_name),
    FOREIGN KEY (qual_cont_name) REFERENCES QualityController,
    FOREIGN KEY (product_id) REFERENCES Product,
    FOREIGN KEY (tech_staff_name) REFERENCES TechnicalStaff
); 

CREATE NONCLUSTERED INDEX request_nonclustered ON Request(product_id);

--Accident

CREATE TABLE Accident(
    accident_number INT PRIMARY KEY,
    accident_date DATE NOT NULL,
    num_work_day INT
);

CREATE NONCLUSTERED INDEX accident_clustered ON Accident (accident_date); -- DOUBLE CHECK

--Produce Cause
CREATE TABLE ProduceCause(
    accident_number INT,
    worker_name VARCHAR(64),
    product_id INT,
    PRIMARY KEY(accident_number,worker_name, product_id),
    FOREIGN KEY (accident_number) REFERENCES Accident,
    FOREIGN KEY (worker_name) REFERENCES Worker,
    FOREIGN KEY (product_id) REFERENCES Product,
);

--Repair Cause
CREATE TABLE RepairCause(
    accident_number INT,
    tech_staff_name VARCHAR(64),
    product_id INT,
    PRIMARY KEY(accident_number,tech_staff_name, product_id),
    FOREIGN KEY (accident_number) REFERENCES Accident,
    FOREIGN KEY (tech_staff_name) REFERENCES TechnicalStaff,
    FOREIGN KEY (product_id) REFERENCES Product,
); 

--Customer
CREATE TABLE Customer(
    customer_name VARCHAR(64) PRIMARY KEY,
    addr VARCHAR(100) 
);

--Purchase
CREATE TABLE Purchase(
    product_id INT,
    customer_name VARCHAR(64),
    PRIMARY KEY (product_id, customer_name),
    FOREIGN KEY (product_id) REFERENCES Product,
    FOREIGN KEY (customer_name) REFERENCES Customer,
);

--Complaint
CREATE TABLE Complaint(
    complaint_id INT PRIMARY KEY,
    complaint_date DATE,
    description VARCHAR(1000),
    treatment VARCHAR(1000), 
);

--Make. Make is a table for storing data, when making complains. It shoud not be mistaken by Produce.
CREATE TABLE Make(
    customer_name VARCHAR(64),
    product_id INT,
    complaint_id INT,
    PRIMARY KEY (customer_name, product_id, complaint_id),
    FOREIGN KEY (customer_name) REFERENCES Customer,
    FOREIGN KEY (product_id) REFERENCES Product,
    FOREIGN KEY (complaint_id) REFERENCES Complaint,
); 

-- Nonclusted index because azure has already made a clustered index based all three primary keys.
CREATE NONCLUSTERED INDEX make_nonclustered ON Make (product_id);

--Got
CREATE TABLE Got(
    complaint_id INT,
    product_id INT,
    tech_staff_name VARCHAR(64),
    PRIMARY KEY (complaint_id, product_id, tech_staff_name),
    FOREIGN KEY (complaint_id) REFERENCES Complaint,
    FOREIGN KEY (product_id) REFERENCES Product,
    FOREIGN KEY (tech_staff_name) REFERENCES TechnicalStaff
    
); 

CREATE NONCLUSTERED INDEX got_nonclustered ON Got (product_id);
