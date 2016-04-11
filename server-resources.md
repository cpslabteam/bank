#Server Resources

All server resources that are or will be present in the Bank REST API.

* [Branch](#branch)
* [Account](#account)
* [Loan](#loan)
* [Customer](#customer)

###Branch
- **/branches**
  - _GET():_ _returns_ list branches
  - _POST(name, city, assets):_ creates branch _returns_ created branch
- **/branches/{branch}**
  - _GET():_ _returns_ branch
  - _PUT(name, city, assets):_ updates branch _returns_ updated branch
  - _DELETE():_ deletes branch
- **/branches/{branch}/accounts**
  - _GET():_ _returns_ list accounts in branch
  - _POST(accountnumber, balance):_ creates account in branch _returns_ created account
- **/branches/{branch}/accounts/{account}**
  - _GET():_ _returns_ account
  - _PUT(accountnumber, balance):_ updates account _returns_ updated account 
  - _DELETE():_ deletes account 
- **/branches/{branch}/loans**
  - _GET():_ _returns_ list loans in branch
  - _POST(loannumber, ammount):_ creates loan in branch _returns_ created loan
- **/branches/{branch}/loans/{loan}**
  - _GET():_ _returns_ loan
  - _PUT(loannumber, ammount):_ updates loan _returns_ updated loan
  - _DELETE():_ deletes loan

###Account
- **/accounts**
  - _GET():_ _returns_ list accounts
  - _POST(accountnumber, balance, branchid):_ creates account _returns_ created account
- **/accounts/{account}**
  - _GET():_ _returns_ account
  - _PUT(accountnumber, balance):_ updates account _returns_ updated account
  - _DELETE():_ deletes account
- **/accounts/{account}/branch**
  - _GET():_ _returns_ branch
  - _PUT(id):_ changes branch _returns_ branch
- **/accounts/{account}/owners**
  - _GET():_ _returns_ owners
  - _PUT(id):_ adds owner _returns_ added owner
- **/accounts/{account}/owners/{owner}**
  - _GET():_ _returns_ owner
  - _DELETE():_ remove owner

###Loan
- **/loans**
  - _GET():_ _returns_ list loans
  - _POST(loannumber, ammount, branchid):_ creates loan _returns_ created loan
- **/loans/{loan}**
  - _GET():_ _returns_ loan
  - _PUT(loannumber, ammount):_ updates loan _returns_ updated loan
  - _DELETE():_ deletes loan
- **/loans/{loan}/branch**
  - _GET():_ _returns_ branch
  - _PUT(id):_ changes branch _returns_ branch
- **/loans/{loan}/owners**
  - _GET():_ _returns_ owners
  - _PUT(id):_ adds owner _returns_ added owner
- **/loans/{loan}/owners/{owner}**
  - _GET():_ _returns_ owner
  - _DELETE():_ remove owner

###Customer
- **/customers**
  - _GET():_ _returns_ list customers
  - _POST(name, city, street):_ creates customer _returns_ created customer
- **/customers/{customer}**
  - _GET():_ _returns_ customer
  - _PUT(name, city, street):_ updates customer _returns_ updated customer
  - _DELETE():_ deletes customer
- **/customers/{customer}/accounts**
  - _GET():_ _returns_ list accounts
  - _PUT(id):_ adds account _returns_ added account
  - _POST(accountnumber, balance, branchid):_ creates and adds account _returns_ created account
- **/customers/{customer}/accounts/{account}**
  - _GET():_ _returns_ account
  - _DELETE():_ removes account
- **/customers/{customer}/accounts/{account}/deposit**
  - _POST(ammount):_ deposits ammount in account _returns_ account
- **/customers/{customer}/accounts/{account}/withdraw**
  - _POST(ammount):_ withdraws ammount from account _returns_ account
- **/customers/{customer}/loans**
  - _GET():_ _returns_ list loans
  - _PUT(id):_ add loan _returns_ added loan
  - _POST(loannumber, ammount, branchid):_ creates and adds loan _returns_ created loan
- **/customers/{customer}/loans/{loan}**
  - _GET():_ _returns_ loan
  - _DELETE():_ deletes loan
- **/customers/{customer}/loans/{loan}/deposit**
  - _POST(ammount):_ deposits ammount in loan _returns_ loan