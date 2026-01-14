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
    - example message: GetUser22.json
  - ADD_ACCOUNT ~ Add new account. Requires admin role.
    - example message: AddAccountSomePerson.json
    - userEmail must be unique (not a duplicate of any existing user in db)
- BookService
  - GET_BOOK ~ Retrieve details of a specific book.
    - example message: GetBook17.json
  - GET_BOOK_BY_GENRE ~ Retrieve overview list of books by selected genre.
    - example message: GetBooksHistory.json
  - GET_BOOK_REVIEWS ~ Retrieve reviews for designated book.
    - example message: GetBookReviews09.json

_TRIPLE_QUESTION_MARK_
  - For user convenience, selected properties on ADD/UPDATE request messages may be replaced with a "???".
  - This is a demonstration feature only to simplify operations requiring property uniqueness.
  - This may be applied to the following objects:
    - AccountAddMessage.body.userEmail


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
- Response objects are records transformed from the database entities.

<br>

### Logging
- A timestamped log file in the logs directory is generated with each execution.

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
Key: 22, Email: Philip.Grant@demo.com, Admin: false, Active: true, Wallet: $179.34
Key: 34, PURCHASE, Date: 2025-11-13 11:18:24, Amount: -$23.70, Book: 29, Qty: 1
Key: 35, PURCHASE, Date: 2025-11-14 11:18:25, Amount: -$10.37, Book: 15, Qty: 1
Key: 36, PURCHASE, Date: 2025-11-15 11:18:25, Amount: -$17.30, Book: 40, Qty: 1
Key: 37, PURCHASE, Date: 2025-11-16 11:18:26, Amount: -$19.28, Book: 62, Qty: 1

Enter message data file (or 'Q' to quit): GetBooksHistory.json
Key: 4, Title: Citizen Soldiers, Author: Stephen Ambrose, Genre: HISTORY
Key: 13, Title: The American Revolution: An Intimate History, Author: Geoffrey C. Ward, Genre: HISTORY
Key: 32, Title: A Prayer In Spring, Author: Dylan Vickers, Genre: HISTORY
Key: 51, Title: The crows above the forest call;, Author: Paisley Yates, Genre: HISTORY
Key: 54, Title: What brought the kindred spider to that height,, Author: Philip Graham, Genre: HISTORY

Enter message data file (or 'Q' to quit): GetBookReviews09.json
Key: 11, Title: Gregor and the Prophecy of Bane, Score: 3, ReviewDate: 2024-05-16 00:00:00, This book was way too dark for kids.
Key: 12, Title: Gregor and the Prophecy of Bane, Score: 10, ReviewDate: 2024-05-17 00:00:00, Every child should read this book.

Enter message data file (or 'Q' to quit): AddAccountSomePerson.json
Key: 53, Email: Santiago.Long@demo.com, Admin: false, Active: true, Wallet: $123.45

Enter message data file (or 'Q' to quit): q
Execution complete

Application ended
```

<br>

## Test Execution

```
mvn test
```

