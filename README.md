# Demo_Java_JUnit

This project is sandbox for demonstrating Java and JUnit. The Java application is a simplified engine for a hypothetical on-line bookstore. This application leverages the database created for a C# Web API project with similar functionality (refer to https://github.com/MOwed06/Demo_CSharp_API).

This project is a first step toward an eventual Java Web API application. This initial implementation reads a user-defined file, parses that file into a request message, sends the request to correct application service, and displays the response. The application continues in a loop until the user selects 'Q' to quit the application.

<br>

## Project Structure

### Services
- AuthorizationService ~ Authenticates user requests. This service is called internally from the other services as a pre-condition to performing the user's requested operation.
- AccountService
  - GET_ACCOUNT ~ Retrieve account info (and transaction history) for a selected user. Requires admin role.
  - ADD_ACCOUNT ~ Add new account. Requires admin role.
- BookService
  - GET_BOOK ~ Retrieve details of a specific book.
  - GET_BOOK_BY_GENRE ~ Retrieve overview list of books by selected genre.

### Database

### Requests

### Responses





<br>

### Notes still progress ...
- message files in data directory
- design structure
  - requests package = objects sent to app (equivalent to incoming API message)
  - responses package = objects returned from app
- run from command line
  - name json file
- log files
- add operations ... service will validate object before add, send error back to user if necessary
- some note about adding to database
- authService - throw exception if invalid, refer to AuthService.java

<br>

### Feature Backlog
- implement TransactionService 
- convert AddUpdate dto objects for range validation with bean package
- add support to delete user (to simplify unit test)

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

