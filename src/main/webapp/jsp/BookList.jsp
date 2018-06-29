<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.books.entities.*" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<head>
</head>
<body>
<t:wrapper>
</t:wrapper>
 <a href=/books/search>find book by author</a>
<ul>
      <c:forEach items="${list}" var="book">
        <li>  <a href=/books/${book.id}>${book}</a></li>
      </c:forEach>
      </ul>
      <a href=/books/new>add new book</a>

</body>
</html>