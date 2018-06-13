<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.books.entities.*" %>

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
<label> publishDate:
${book.publishDate}
</label>
<br>
<label> authors:
<c:forEach items="${book.authors}" var="author">
     ${author},
</c:forEach>
</label>
<br>
<label> publisher:
${book.publisher}
</label>
<br>
<input type="submit" value="confirm changes">
</form>
</body>
</html>