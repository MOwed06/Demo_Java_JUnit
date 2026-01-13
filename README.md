# Demo_Java_JUnit

This project is sandbox for demonstrating Java and JUnit. The Java application is a simplified engine for a hypothetical on-line bookstore. This application leverages the database created for a C# Web API project with similar functionality (refer to https://github.com/MOwed06/Demo_CSharp_API).

This project is a first step toward an eventual Java Web API application. This initial implementation reads a user-defined file, parses that file into a request message, sends the request to correct application service, and displays the response. The application continues in a loop until the user selects 'Q' to quit the application.

### Application Operation

- The project executes as console application.
- The user selects actions by inputting a file name (which must exist in the data directory).
- The application parses the file as request message.
- Responses from "book store" are displayed in the console.
- Select 'Q' to terminate execution.

## Structure

### Services
- AuthorizationService
  - Authenticates user requests defined by received AuthRequest object.
  - This service is called internally from the other services as a pre-condition to performing the user's requested operation.
- AccountService
  - GET_ACCOUNT ~ Retrieve account info (and transaction history) for a selected user. Requires admin role.
  - ADD_ACCOUNT ~ Add new account. Requires admin role.
- BookService
  - GET_BOOK ~ Retrieve details of a specific book.
  - GET_BOOK_BY_GENRE ~ Retrieve overview list of books by selected genre.

### Database

- A persistent sqlite database is maintained as part of the project.
  - data/BigBooks.db
- The database package objects correspond directly to table elements.
  - Account
  - AccountTransaction
  - Book
  - BookReview

### Requests

- RequestMessage is the base class for all request messages.
  - RequestMessages approximate the behavior API requests.
  - ApplicationRunner.java parses the selected input file according to the MessageType property.
  - ApplicationRunner directs the message to the associated Service for processing.
- GetMessage
  - Common get request.
  - The combination of messageType and queryParameters resolve the intended operation.
- AccountAddMessage
  - Create user account per properties of child AccountAddDto object.

### Responses
- Response records are transformed from the database entities.

<br>

### Notes still progress ...
- log files
- add operations ... service will validate object before add, send error back to user if necessary
- some note about adding to database

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
Example application run:
```
PS C:\GitHub\Demo_Java_JUnit> java -jar target\LittleBooks-1.0-SNAPSHOT.jar

Enter message data file (or 'Q' to quit): GetUser22.json
Key: 22, Email: Philip.Grant@demo.com, Admin: false, Active: true, Wallet: 179.339996
Key: 34, PURCHASE, Date: 2025-11-13 11:18:24, Amount: -23.700001, Book: 29, Qty: 1
Key: 35, PURCHASE, Date: 2025-11-14 11:18:25, Amount: -10.370000, Book: 15, Qty: 1
Key: 36, PURCHASE, Date: 2025-11-15 11:18:25, Amount: -17.299999, Book: 40, Qty: 1
Key: 37, PURCHASE, Date: 2025-11-16 11:18:26, Amount: -19.280001, Book: 62, Qty: 1

Enter message data file (or 'Q' to quit): GetBook17.json
Key: 17, Title: 'I guess you'd find the fountain just as empty., Author: Dylan Vickers, Genre: FICTION, Price: 18.540001, Available: true, Rating: null, Reviews: 0

Enter message data file (or 'Q' to quit): q
Execution complete

```

<br>

## Test Execution

```
mvn test
```

