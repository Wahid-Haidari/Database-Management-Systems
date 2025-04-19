-- Query 1 -----------------------------------------------------

INSERT INTO Worker
    (worker_name, addr, salary, max_num_prod_per_day)
VALUES 
    ('Wahid', 'Norman', 3000, 4); 
    --These values are just examples. They are not stored in the database

INSERT INTO QualityController
    (qual_cont_name, addr, salary, product_type)
VALUES
    ('Ghulam', 'Norman', 3000, 'type1');
    --These values are just examples. They are not stored in the database

INSERT INTO TechnicalStaff
    (tech_staff_name, addr, salary, education_record)
VALUES
    ('Shohruz', 'Norman', 300, 'BS');
    --These values are just examples. They are not stored in the database

INSERT INTO GradTechnicalStaff
    (tech_staff_name, addr, salary, education_record)
VALUES

    ('Shohruz', 'Norman', 3000, 'MS');
    --These values are just examples. They are not stored in the database

INSERT INTO UndergradTechnicalStaff
    (tech_staff_name, addr, salary, education_record)
VALUES
    ('Shohruz', 'Norman', 3000, 'BS');
    --These values are just examples. They are not stored in the database

    
--Before doing this query we should have done query 4, to have some data in the Account1, Account2, and Account3 tables
--Query 2 ----------------------------------------------------------------

INSERT INTO Product
    (product_id, production_date, time_spent, produced_by, tested_by, repaired_by)
VALUES
    (1, '2018-02-22', 24, 'Wahid', 'Ghulam', 'Shohruz');
    --These values are just examples. They are not stored in the database
    
INSERT INTO Product1
    (product1_id, production_date, time_spent, produced_by, tested_by, repaired_by, product1_name, software_used, account1_num)
VALUES
    (1, '2019-02-22', 23, 'Wahid', 'Ghulam', 'Shohruz', 'book', 'sofware1', 1);
   --These values are just examples. They are not stored in the database

INSERT INTO Product2
    (product2_id, production_date, time_spent, produced_by, tested_by, repaired_by, size, color, account2_num)
VALUES
    (2, '2019-02-22', 23, 'Wahid', 'Ghulam', 'Shohruz', 'M', 'red', 1);
    --These values are just examples. They are not stored in the database

INSERT INTO Product3
    (product3_id, production_date, time_spent, produced_by, tested_by, repaired_by, size, weight, account3_num)
VALUES
    (1, '2019-02-22', 23, 'Wahid', 'Ghulam', 'Shohruz', 'm', 15, 1);
    --These values are just examples. They are not stored in the database

INSERT INTO Produce
    (worker_name, product_id)
VALUES 
    ('Wahid', 1);
    --These values are just examples. They are not stored in the database

INSERT INTO Test
    (qual_cont_name, product_id)
VALUES
    ('Ghulam', 1);
    --These values are just examples. They are not stored in the database

INSERT INTO Repair
    (tech_staff_name, product_id, repair_date)
VALUES
    ('Shohruz', 1, '2019-09-19');
    --These values are just examples. They are not stored in the database

INSERT INTO RepairP1
    (tech_staff_name, product1_id, account1_num)
VALUES
    ('Shohruz', 1, 1);
    --These values are just examples. They are not stored in the database

INSERT INTO Request
    (qual_cont_name, product_id, tech_staff_name)
VALUES
    ('Ghulam', 1, 'Shohruz');


--Query 3 ---------------------------------------------------------------

INSERT INTO Customer
    (customer_name, addr)
VALUES
    ('Jack', 'NYC'); 
    --These values are just examples. They are not stored in the database

INSERT INTO Purchase
    (product_id, customer_name)
VALUES 
    (1, 'Jack');
    --These values are just examples. They are not stored in the database

--Query 4 ---------------------------------------------------------------

INSERT INTO Account1
    (account1_num, establish_date, product1_cost)
VALUES
    (1, '2022-01-01', 1);
    --These values are just examples. They are not stored in the database

INSERT INTO Account2
    (account2_num, establish_date, product2_cost)
VALUES
    (2, '2022-01-01', 4);
   --These values are just examples. They are not stored in the database
    

INSERT INTO Account3
    (account3_num, establish_date, product3_cost)
VALUES
    (1, '2022-01-01', 1);
    --These values are just examples. They are not stored in the database

-- Query 5 ------------------------------------------------------------

INSERT INTO Complaint
    (complaint_id, complaint_date, description, treatment)
VALUES 
    (1, '2010-10-10', 'book is old', 'refund');
    --These values are just examples. They are not stored in the database

INSERT INTO Make -- Used for when a customer makes complaint
    (customer_name, product_id, complaint_id)
VALUES 
    ('Jack', 1, 1);
--These values are just examples. They are not stored in the database

INSERT INTO Got
    (complaint_id, product_id, tech_staff_name)
VALUES
    (1, 1, 'Shohruz'),
    (2, 2, 'Shohruz');

-- Query 6 ------------------------------------------------------------

INSERT INTO Accident
    (accident_number, accident_date, num_work_day)
VALUES
    (1, '2010-09-20', 3);
    --These values are just examples. They are not stored in the database

INSERT INTO ProduceCause
    (accident_number, worker_name, product_id)
VALUES
    (1, 'Wahid', 1);
    --These values are just examples. They are not stored in the database

INSERT INTO RepairCause
    (accident_number, tech_staff_name, product_id)
VALUES
    (1, 'Shohruz', 1);
    --These values are just examples. They are not stored in the database
    

-- Query 7 -------------------------------------------------------------

SELECT production_date, time_spent
FROM Product
WHERE product_id = 1

-- Query 8 ------------------------------------------------------------

SELECT Product.*
FROM Produce JOIN Product ON Produce.product_id = Product.product_id
WHERE worker_name = 'Jack';

-- Query 9 -----------------------------------------------------------

SELECT COUNT (Make.product_id) as tot_product
FROM Make
JOIN Test ON Make.product_id = Test.product_id
WHERE qual_cont_name = 'Ali';

-- Query 10 ----------------------------------------------------------

SELECT SUM (product3_cost) AS total_product3_cost
FROM Product3
JOIN Account3 ON  Product3.account3_num = Account3.account3_num
JOIN Request ON Request.product_id = Product3.product3_id
JOIN Repair ON Repair.product_id = Product3.product3_id
WHERE Request.qual_cont_name = 'John';


-- Query 11 ----------------------------------------------------------

SELECT Customer.customer_name
FROM Customer, Product2, Purchase
WHERE Customer.customer_name = Purchase.customer_name 
    AND product_id = Product2.product2_id 
    AND Product2.color = 'red'
ORDER BY customer_name;

-- Query 12 ---------------------------------------------------------

WITH Employee as (
SELECT salary, addr , worker_name  as employee_name
FROM Worker
UNION
SELECT salary, addr , qual_cont_name as employee_name 
FROM QualityController 
UNION
SELECT salary, addr, tech_staff_name as employee_name
FROM TechnicalStaff
) 
select employee_name, addr as address, salary
FROM Employee Where salary > 100;

-- Query 13 -------------------------------------------------------

SELECT SUM (Accident.num_work_day) as total_number_of_workdays
FROM Accident
JOIN RepairCause ON RepairCause.accident_number = Accident.accident_number
JOIN Got ON Got.product_id = RepairCause.product_id;

-- Query 14 ---------------------------------------------------------

--Since there are three different products, and three different accounts,
--we join product and product1, product and product2, product and product3,
--and union all of them.
WITH unionized as (
SELECT product_id, Product.production_date,  product1_cost as cost
FROM Product 
    JOIN Product1 ON Product.product_id = Product1.product1_id
    JOIN Account1 ON Product1.account1_num = Account1.account1_num
    
UNION ALL

SELECT product_id, Product.production_date, product2_cost as cost
FROM Product 
    JOIN Product2 ON Product.product_id = Product2.product2_id
    JOIN Account2 ON Product2.account2_num = Account2.account2_num

UNION ALL

SELECT product_id, Product.production_date, product3_cost as cost
FROM Product 
    JOIN Product3 ON Product.product_id = Product3.product3_id
    JOIN Account3 ON Product3.account3_num = Account3.account3_num
)
select AVG(cost) FROM unionized
WHERE YEAR(production_date) = 2019;

-- Query 15 -------------------------------------------------------

-- Before deleting from accident, we should delete from ProduceCasue because
-- the data in ProduceCasue refer to Accident
DELETE ProduceCause FROM ProduceCause pc
     RIGHT JOIN Accident ac on ac.accident_number = pc.accident_number 
WHERE accident_date >= '2010-01-01'
    AND accident_date <= '2015-01-01';
    --The values here are just examples. They are not run in the database.

-- Before deleting from accident, we should delete from RepairCasue because
-- the data in RepairCasue refer to Accident
DELETE RepairCause FROM RepairCause rc
     RIGHT JOIN Accident ac on ac.accident_number = rc.accident_number 
WHERE accident_date >= '2010-01-01'
    AND accident_date <= '2015-01-01';
    --The values here are just examples. They are not run in the database.

DELETE FROM Accident
    WHERE accident_date >= '2010-01-01'
    AND accident_date <= '2015-01-01';
    --The values here are just examples. They are not run in the database.
