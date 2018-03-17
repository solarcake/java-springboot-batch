# User Rest: Serving users via rest

This application provides rest endpoints for searching of users which are loading in the application from a csv file called "Workbook.csv" located in the resources folder.

Note that this has been moved from its original path. 

As an IDE I used intellij, the technology list is given at the end of the file but the basic process is as follows.

- Spring batch is used in order to load the entries contained in the csv file to a in memory database (hsql). This was done in order to serve the csv contents quicker and be able to run queries against them
- JPA repositories were used in order to reduce bolier plate controllers in serving the data. Therefore the json format of the responses is presented using HAL.
- A number of search end points are also provided (this can be expanded easily)
- RestDocs are generated using org.springframework.restdocs and ascii doctor.

## Getting Started

Before running the rest API application the following prerequisites must be installed

### Prerequisites

 - The application is packaged using maven, the latest version of maven could be downloaded and installed
   to configure this.
  
 - The application is written in java so java must be installed as well. The application is packaged using java 1.8. This also needs
   to be configured in order to use maven.


```
Maven can be downloaded here:  https://maven.apache.org/.
Java can be downloaded here: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
```

### Installing

- Checkout the repository
- Run maven install in the root directory. This would generated the necessary jar files for deployment

```
mvn install
```

- Bring up the application using maven as well, run the following commang

 ```
 mvn spring-boot:run
 ```

- The application is exposed on port 8080

 ```
http://localhost:8080/users
 ```
Will return all the users of the application. More endpoints are listed below. Please refer to the docs/html section for a full list of this rest API
 ```
{
  "_embedded" : {
    "users" : [ {
      "name" : "John",
      "surname" : "Johnson",
      "address" : "Voorstraat 32",
      "postCode" : "3122gg",
      "phone" : "020 3849381",
      "creditLimit" : 10000.00,
      "birthday" : "31/12/1986",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users/0"
        },
        "user" : {
          "href" : "http://localhost:8080/users/0"
        }
      }
    }, {
      "name" : "Paul",
      "surname" : "Anderson",
      "address" : "Dorpsplein 3A",
      "postCode" : "4532 AA",
      "phone" : "030 3458986",
      "creditLimit" : 109093.00,
      "birthday" : "02/01/1965",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users/1"
        },
        "user" : {
          "href" : "http://localhost:8080/users/1"
        }
      } 
      ...
   ```
## End points

Requirement 1

### Single user

Request
 ```
 http://localhost:8080/users/1
 ```
Response
 ```
{
  "name" : "Paul",
  "surname" : "Anderson",
  "address" : "Dorpsplein 3A",
  "postCode" : "4532 AA",
  "phone" : "030 3458986",
  "creditLimit" : 109093.00,
  "birthday" : "02/01/1965",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/1"
    },
    "user" : {
      "href" : "http://localhost:8080/users/1"
    }
  }
}
 ```
 
### All Users 
```
 http://localhost:8080/users
```

Requirement 2

### Finding users based on criteria
http://localhost:8080/users/search

- lists all search criteria endpoints

http://localhost:8080/users/search/findByNameIgnoreCaseOrSurnameIgnoreCase{?name,surname}
 
- name and surname can be provided and both will ignore case. Multiple users can be returned

```
http://localhost:8080/users/search/findByNameIgnoreCaseOrSurnameIgnoreCase?surname=smith

http://localhost:8080/users/search/findByNameIgnoreCaseOrSurnameIgnoreCase?surname=smith&name=John

```
 http://localhost:8080/users/search/findByCreditLimitLessThanEqual{?creditLimit}

- creditLimit can be an integer or a decimal value. All users within the limit will be returned

```
http://localhost:8080/users/search/findByCreditLimitLessThanEqual?creditLimit=54.50

```

## Running the tests

Both maven install and maven verify can be used to run tests. SearchControllerTests.java run a set of integration tests against the rest api


## Built With

* [Spring boot](https://projects.spring.io/spring-boot/) - Creating standalone spring applications
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Batch](https://projects.spring.io/spring-batch/) - Lightweight comprehensive batch framework
* [HSQL](http://hsqldb.org/) - In memory database


## Authors

* Alan Gauci