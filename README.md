# Simple Bank Application 

This is a simple bank application using our technology stack, and will be used in comparisons with the NSX.

[TODO's](TODO.md)

### Contents
[**Technologies**](#technologies)  
[**Documentation**](#documentation)  
[**Running Instructions**](#running-instructions)

## Technologies

* [AngularJS]
* [Restlet]
* [Hibernate]
* [Postgresql]

## Documentation

### Models

#### Data Model

![Bank Data Model][DataModel]

## Running Instructions

1. Import the *bankServer* project into Eclipse (Mars version) by importing as *File->Import->Maven->Existing Maven Projects*. 

2. Open *hibernate.cfg.xml* file in the *bankServer/src/main/java* directory, and change the database connection settings to fit your own, or adjust your properties to match the existing values. 

3. Connect to the postgres database to use, and run the *reset_bank.sql* script, located in *bankServer/scripts/database*. 

4. Run the *ServerActivor* class in *bankServer/src/main/java* to start the server. 
 
5. Call the server resources using *testpage.html* in *bankClient/testpage.html* (currently only for GET methods), or use the [Postman] app (recommended).

[//]: # (link variables)

   [AngularJS]: <http://angularjs.org>
   [Restlet]: <https://restlet.com/>
   [Postgresql]: <http://www.postgresql.org/>
   [Hibernate]: <http://hibernate.org/>
   [DataModel]: <https://github.com/cpslabteam/bank/blob/master/docs/models/BankDataModel.png>
   [Postman]: <https://www.getpostman.com>
