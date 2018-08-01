#Books
Summer prictice 2018.
#Getting Started
You need:
- java 1.8 or more
- maven 4.0 
Use maven plagin 'jetty' to start project.Default url:'localhost:8080'
#Task5
Create pages for displaying information about books and editing/creating books:
-GET   '/books/list '- shows page with the list of Books
-GET  ' /books/{bookId}' - shows edits form for editing book. If {bookId == 'new'} - происходит создание книги.
-POST '/books/{bookId}' - handles edits form and redirects to page List of Books
-GET   '/books/search '- shows page with the filtered list of books 
Extract application logic into Service tier
#Task6
 Add database support. Use JDBC and H2 database.
- Create initial SQL script for creating DB
- Create SQL for populating DB with records
- Implement logic for CRUD (Create/Read/Update/Delete) operations
- Refactor to persist and query books from DB. Pages designed before should use DB repository
- Support console application to use DB repository
#Task7
1. As Unauthenticated user I can NOT edit and view books.
2. As Authenticated user I can view and edit books list
3. As Authenticated user I can logout
- Create Authentication web filter and login functionality. Use Bootstrap to create Login form.
- If user entered not valid credentials it should be redirected on login page with error Message "Invalid login or password. Please try again"
- On logout do session invalidation and redirect to login page with message "Successfully logged out"
Implementation notes:
Bonus1: Create EncryptService with ability to plugin different types of password encription (MD5, SHA1...). In app password encription should be MD5.
Bonus2: Create Header for all pages so to have App Logo, App Name, Login and Logout links. Header should be implemted without Paste &Copy. Use <jsp:include> for example.
#Task8
Add Spring framework support.
- Refactor your code to use dependency injection (DI)
- Refactor DAO layer from JDBC code to use Spring JDBC
- Externazlize JDBC properties to config (so they shoouldn't be hardcoded)
- Refactor Web layer to use Spring MVC Controller for handling GET/POST request to display list of Books and creating/editing Books
 #Task9
- Use Bootstrap as CSS framework
#Task10
- overview of REST API design principles
- design and document your own REST Api usign domain in this practice 
- implement REST Api using either Spring MVC or Jax-RS Implementation
- write several tests on API using Rest Assured
- reimplement several pages of your application usign Ajax calls to REST Api endpoints