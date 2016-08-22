insert into customer (id, customer_number, name, street, city) values (1, 'C-124', 'Jones', 'Main', 'Harrison');
insert into customer (id, customer_number, name, street, city) values (2, 'C-345', 'Smith', 'Main', 'Rye');
insert into customer (id, customer_number, name, street, city) values (3, 'C-357', 'Hayes', 'Main', 'Harrison');
insert into customer (id, customer_number, name, street, city) values (4, 'C-366', 'Curry', 'North', 'Rye');
insert into customer (id, customer_number, name, street, city) values (5, 'C-385', 'Lindsay', 'Park', 'Pittsfield');
insert into customer (id, customer_number, name, street, city) values (6, 'C-415', 'Turner', 'Putnam', 'Stamford');
insert into customer (id, customer_number, name, street, city) values (7, 'C-493', 'Williams', 'Nassau', 'Princeton');
insert into customer (id, customer_number, name, street, city) values (8, 'C-635', 'Adams', 'Spring', 'Pittsfield');
insert into customer (id, customer_number, name, street, city) values (9, 'C-690', 'Johnson', 'Alma', 'Palo Alto');
insert into customer (id, customer_number, name, street, city) values (10, 'C-732', 'Glenn', 'Sand Hill', 'Woodside');
insert into customer (id, customer_number, name, street, city) values (11, 'C-737', 'Brooks', 'Senator', 'Brooklyn');
insert into customer (id, customer_number, name, street, city) values (12, 'C-787', 'Green', 'Walnut', 'Stamford');
insert into customer (id, customer_number, name, street, city) values (13, 'C-850', 'Jackson', 'University', 'Salt Lake');
insert into customer (id, customer_number, name, street, city) values (14, 'C-934', 'Majeris', 'First', 'Rye');
insert into customer (id, customer_number, name, street, city) values (15, 'C-993', 'McBride', 'Safety', 'Rye');

insert into branch (id, name, city, assets) values (16, 'Downtown', 'Brooklyn', 900000);
insert into branch (id, name, city, assets) values (17, 'Redwood', 'Palo Alto', 2100000);
insert into branch (id, name, city, assets) values (18, 'Perryridge', 'Horseneck', 1700000);
insert into branch (id, name, city, assets) values (19, 'Mianus', 'Horseneck', 400200);
insert into branch (id, name, city, assets) values (20, 'Round Hill', 'Horseneck', 8000000);
insert into branch (id, name, city, assets) values (21, 'Pownal', 'Bennington', 400000);
insert into branch (id, name, city, assets) values (22, 'North Town', 'Rye', 3700000);
insert into branch (id, name, city, assets) values (23, 'Brighton', 'Brooklyn', 7000000);
insert into branch (id, name, city, assets) values (24, 'Central', 'Rye',  400280);

insert into account (id, account_number, branch_id, balance) values (25, 'A-101', 16, 500);
insert into account (id, account_number, branch_id, balance) values (26, 'A-215',  19, 700);
insert into account (id, account_number, branch_id, balance) values (27, 'A-102',  18, 400);
insert into account (id, account_number, branch_id, balance) values (28, 'A-305',  20, 350);
insert into account (id, account_number, branch_id, balance) values (29, 'A-201',  18, 900);
insert into account (id, account_number, branch_id, balance) values (30, 'A-222',  17, 700);
insert into account (id, account_number, branch_id, balance) values (31, 'A-217',  23, 750);
insert into account (id, account_number, branch_id, balance) values (32, 'A-333',  24, 850);
insert into account (id, account_number, branch_id, balance) values (33, 'A-444',  22, 625);

insert into loan (id, loan_number, branch_id, amount) values (34, 'L-17',  16, 1000);
insert into loan (id, loan_number, branch_id, amount) values (35, 'L-23',  17, 2000);
insert into loan (id, loan_number, branch_id, amount) values (36, 'L-15',  18, 1500);
insert into loan (id, loan_number, branch_id, amount) values (37, 'L-14',  16, 1500);
insert into loan (id, loan_number, branch_id, amount) values (38, 'L-93',  19, 500);
insert into loan (id, loan_number, branch_id, amount) values (39, 'L-11',  20, 900);
insert into loan (id, loan_number, branch_id, amount) values (40, 'L-16',  18, 1300);
insert into loan (id, loan_number, branch_id, amount) values (41, 'L-20',  22, 7500);
insert into loan (id, loan_number, branch_id, amount) values (42, 'L-21',  24, 570);

insert into division (id, name) values (43, 'North');
insert into division (id, name) values (44, 'SouthWest');
insert into division (id, name) values (45, 'Center');

insert into customer_loan (owners_id, loans_id) values (1, 34);
insert into customer_loan (owners_id, loans_id) values (2, 35);
insert into customer_loan (owners_id, loans_id) values (2, 39);
insert into customer_loan (owners_id, loans_id) values (2, 42);
insert into customer_loan (owners_id, loans_id) values (3, 36);
insert into customer_loan (owners_id, loans_id) values (4, 38);
insert into customer_loan (owners_id, loans_id) values (7, 34);
insert into customer_loan (owners_id, loans_id) values (8, 40);
insert into customer_loan (owners_id, loans_id) values (13, 37);
insert into customer_loan (owners_id, loans_id) values (15, 41);

insert into customer_account (owners_id, accounts_id) values (1, 31);
insert into customer_account (owners_id, accounts_id) values (2, 26);
insert into customer_account (owners_id, accounts_id) values (2, 33);
insert into customer_account (owners_id, accounts_id) values (3, 25);
insert into customer_account (owners_id, accounts_id) values (3, 27);
insert into customer_account (owners_id, accounts_id) values (5, 30);
insert into customer_account (owners_id, accounts_id) values (6, 28);
insert into customer_account (owners_id, accounts_id) values (9, 25);
insert into customer_account (owners_id, accounts_id) values (9, 29);
insert into customer_account (owners_id, accounts_id) values (14, 32);

insert into useraccount (id, username, password) values (46, 'admin', 'testAdmin');

select setval('hibernate_sequence', 47);
