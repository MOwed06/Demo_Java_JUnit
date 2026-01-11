# Demo_Java_JUnit

This project is sandbox for demonstrating Java and JUnit. The Java application is a simplified engine for a hypothetical on-line bookstore. This application leverages the database created for a C# Web API project with similar functionality (refer to https://github.com/MOwed06/Demo_CSharp_API).

This project is a first step toward an eventual Java Web API application.  

Notes in progress ...
- message files in data directory
- design structure
  - requests package = objects sent to app (equivalent to incoming API message)
  - responses package = objects returned from app
- run from command line
  - name json file
- log files
- add operations ... service will validate object before add, send error back to user if necessary
- some note about adding to database

Still to do
- add UserService
- add TransactionService


<br>

## Command Line Execution

```
mvn clean package
java -jar target\LittleBooks-1.0-SNAPSHOT.jar
```


<br>

## Test Execution

```
mvn test
```

