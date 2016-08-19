create sequence hibernate_sequence start 1 increment 1;

create table Account (
  id int8 default nextval('hibernate_sequence') not null,
  account_number varchar(255),
  branch_id int8,
  balance double precision,
  primary key (id)
);

create table Customer_Loan (
  owners_id int8,
  loans_id int8,
  primary key (owners_id, loans_id)
);

create table Branch (
  id int8 default nextval('hibernate_sequence') not null,
  name varchar(255),
  city varchar(255),
  assets double precision,
  primary key (id)
);

create table Customer (
  id int8 default nextval('hibernate_sequence') not null,
  customer_number varchar(255),
  name varchar(255),
  street varchar(255),
  city varchar(255),
  primary key (id)
);

create table Customer_Account (
  owners_id int8,
  accounts_id int8,
  primary key (owners_id, accounts_id)
);

create table Loan (
  id int8 default nextval('hibernate_sequence') not null,
  loan_number varchar(255),
  branch_id int8,
  amount double precision,
  primary key (id)
);

alter table Account 
  add constraint BRANCH_ID_FK 
  foreign key (branch_id) 
  references Branch;

alter table Customer_Loan 
  add constraint LOAN_ID_FK 
  foreign key (loans_id) 
  references Loan;

alter table Customer_Loan 
  add constraint CUSTOMER_ID_FK 
  foreign key (owners_id) 
  references Customer;

alter table Customer_Account 
  add constraint ACCOUNT_ID_FK 
  foreign key (accounts_id) 
  references Account;

alter table Customer_Account 
  add constraint CUSTOMER_ID_FK 
  foreign key (owners_id) 
  references Customer;
  
alter table Loan 
  add constraint BRANCH_ID_FK 
  foreign key (branch_id) 
  references Branch;