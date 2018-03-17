# User rest point

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

- Bring up the application using 

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

both maven install and maven verify can be used to run tests. SearchControllerTests.java run a set of integration tests against the rest api


## Built With

* [Spring boot](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Batch](https://rometools.github.io/rome/) - Used to generate RSS Feeds
* [HSQL]


## Authors

* Alan Gauci