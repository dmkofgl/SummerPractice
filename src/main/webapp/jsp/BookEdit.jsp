<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.books.entities.*,java.text.*,java.util.Date" %>

<html>
<head>
</head>
<body>
<center><h1>${action}</h1></center>
<form method="post">
<label> name
<input type="text" value="${book.name}" name="name">
</label>
<br>
<%
  Book book = (Book) request.getAttribute("book");
  %>
<label> publish date:
<input type="hidden" value='${book.id}' name="bookId">
<input type="date" name="publishDate"  value='<%out.print(String.format("%tF",book.getPublishDate()));%>'>
</label>
<br>
<label> authors:</label>
<c:forEach items="${book.authors}" var="author">
     <a href="#" > ${author}</a>
      <button type="submit" name="removeAuthor" value="${author.id}">remove</button>
      <br>
</c:forEach>
 <input type="button" value="add"></a><br>

<br>
<label> publisher:
${book.publisher}
</label>
<br>
<input type="submit" value="confirm changes">
</form>
</body>
</html>