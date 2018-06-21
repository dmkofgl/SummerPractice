<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.books.entities.*" %>
<html>
<head>
</head>
<body>
<form method="post">
<label> Author name:
<input type="text" name="query">
</label>
<input type="submit" value="filter">
</form>
<ul>
      <c:forEach items="${list}" var="book">
        <li>  <a href=/books/${book.id}>${book}</a></li>
      </c:forEach>
      </ul>
      <a href=/books/list>to list</a>
</body>
</html>