create sequence hibernate_sequence start 1 increment 1;

create table Account (
  id int8 default nextval('hibernate_sequence') not null,
  account_number varchar(255) not null,
  branch_id int8 not null,
  balance NUMERIC(20,2) not null,
  primary key (id)
);

create table Customer_Loan (
  owners_id int8 not null,
  loans_id int8 not null,
  primary key (owners_id, loans_id)
);

create table Branch (
  id int8 default nextval('hibernate_sequence') not null,
  name varchar(255) not null,
  city varchar(255) not null,
  assets NUMERIC(20,2) not null,
  primary key (id)
);

create table Customer (
  id int8 default nextval('hibernate_sequence') not null,
  name varchar(255) not null,
  street varchar(255) not null,
  city varchar(255) not null,
  primary key (id)
);

create table Customer_Account (
  owners_id int8 not null,
  accounts_id int8 not null,
  primary key (owners_id, accounts_id)
);

create table Loan (
  id int8 default nextval('hibernate_sequence') not null,
  loan_number varchar(255) not null,
  branch_id int8 not null,
  amount NUMERIC(20,2) not null,
  primary key (id)
);

alter table Account 
  add constraint UNIQUE_ACCOUNT_NUMBER unique (account_number);

alter table Branch 
  add constraint UNIQUE_NAME unique (name);

alter table Loan 
  add constraint UNIQUE_LOAN_NUMBER unique (loan_number);

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