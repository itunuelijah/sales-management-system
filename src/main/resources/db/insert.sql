set foreign_key_checks = 0; -- not for postgres

INSERT INTO products (id, name, price, quantiy, description)
VALUES
    ('Product 1', 10.99, 10,  'Description 1'),
    ('Product 2', 5.99, 20, 'Description 2'),
    ('Product 3', 20.99, 30, 'Description 3');


INSERT INTO customers (customer_id, name, phone_number, email, address)
VALUES
    (1L,'customer1', '09-123-457-7290', 'email1', 'address1'),
    (2L,'customer2', '09-124-454-7891', 'email2', 'address2'),
    (3L,'customer3', '09-127-457-7897', 'email3', 'address3');


INSERT INTO orders (id, product_id, customer_id, quantity, price, total_price, version, date_created)
VALUES

    (1L, 1L, 1L, 5, 100OO.0, 23000, 1L, '2022-02-14 10:00:00'),
    (2L, 1L, 2L, 10, 50OOO.0, 436000, 2L, '2022-02-15 10:00:00'),
    (3L, 2L, 3L, 15, 200OOO.0, 54360000, 3L, '2022-02-14 11:00:00');


INSERT INTO order_lines (orderLine_id, product_id, quantity, price , total)
VALUES
       (1,1L, 50, 2000000,100),
       (2,2L, 100, 25500000,200),
       (3,3L, 150, 350000000,300),
       (4,4L, 200, 17900000,400);



set foreign_key_checks = 1;