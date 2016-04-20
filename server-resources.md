#REST API

All server resources that are or will be present in the Bank REST API.

* [Branch](#branch)
* [Account](#account)
* [Loan](#loan)
* [Customer](#customer)

###Branch
- **/branches**
  - *GET():* *returns* list branches
  - *POST(name, city, assets):* creates branch *returns* created branch
- **/branches/{branch}**
  - *GET():* *returns* branch
  - *PUT(name, city, assets):* updates branch *returns* updated branch
  - *DELETE():* deletes branch
- **/branches/{branch}/accounts**
  - *GET():* *returns* list accounts in branch
  - *POST(account_number, balance):* creates account in branch *returns* created account
- **/branches/{branch}/accounts/{account}**
  - *GET():* *returns* account
  - *PUT(account_number, balance):* updates account *returns* updated account 
  - *DELETE():* deletes account
- **/branches/{branch}/loans**
  - *GET():* *returns* list loans in branch
  - *POST(loan_number, amount):* creates loan in branch *returns* created loan
- **/branches/{branch}/loans/{loan}**
  - *GET():* *returns* loan
  - *PUT(loan_number, amount):* updates loan *returns* updated loan
  - *DELETE():* deletes loan

###Account
- **/accounts**
  - *GET():* *returns* list accounts
  - *POST(account_number, balance, branch_id):* creates account *returns* created account
- **/accounts/{account}**
  - *GET():* *returns* account
  - *PUT(account_number, balance):* updates account *returns* updated account
  - *DELETE():* deletes account
- **/accounts/{account}/branch**
  - *GET():* *returns* branch
  - *PUT(id):* changes branch *returns* branch
- **/accounts/{account}/owners**
  - *GET():* *returns* owners
  - *PUT(id):* adds owner *returns* added owner
- **/accounts/{account}/owners/{owner}**
  - *GET():* *returns* owner
  - *DELETE():* remove owner

###Loan
- **/loans**
  - *GET():* *returns* list loans
  - *POST(loan_number, amount, branch_id):* creates loan *returns* created loan
- **/loans/{loan}**
  - *GET():* *returns* loan
  - *PUT(loan_number, amount):* updates loan *returns* updated loan
  - *DELETE():* deletes loan
- **/loans/{loan}/branch**
  - *GET():* *returns* branch
  - *PUT(id):* changes branch *returns* branch
- **/loans/{loan}/owners**
  - *GET():* *returns* owners
  - *PUT(id):* adds owner *returns* added owner
- **/loans/{loan}/owners/{owner}**
  - *GET():* *returns* owner
  - *DELETE():* remove owner

###Customer
- **/customers**
  - *GET():* *returns* list customers
  - *POST(name, city, street):* creates customer *returns* created customer
- **/customers/{customer}**
  - *GET():* *returns* customer
  - *PUT(name, city, street):* updates customer *returns* updated customer
  - *DELETE():* deletes customer
- **/customers/{customer}/accounts**
  - *GET():* *returns* list accounts
  - *PUT(id):* adds account *returns* added account
  - *POST(account_number, balance, branch_id):* creates and adds account *returns* created account
- **/customers/{customer}/accounts/{account}**
  - *GET():* *returns* account
  - *DELETE():* removes account
- **/customers/{customer}/accounts/{account}/deposit**
  - *POST(amount):* deposits amount in account *returns* account
- **/customers/{customer}/accounts/{account}/withdraw**
  - *POST(amount):* withdraws amount from account *returns* account
- **/customers/{customer}/loans**
  - *GET():* *returns* list loans
  - *PUT(id):* add loan *returns* added loan
  - *POST(loan_number, amount, branch_id):* creates and adds loan *returns* created loan
- **/customers/{customer}/loans/{loan}**
  - *GET():* *returns* loan
  - *DELETE():* deletes loan
- **/customers/{customer}/loans/{loan}/deposit**
  - *POST(amount):* deposits amount in loan *returns* loan