<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.books.entities.*" %>
<html>
<head>
</head>
<body>

      <c:forEach items="${list}" var="book">
          <a href=/books/${book.id}>${book}</a><br>
      </c:forEach>
</body>
</html>