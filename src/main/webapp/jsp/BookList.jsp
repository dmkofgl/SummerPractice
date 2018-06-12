<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.books.entities.*" %>
<html>
<head>
</head>
<body>
  <%
  List<Book> list = (List<Book>)request.getAttribute("list");
  int id=0;
        for(Book book : list){
        out.println("<a href=books/"+id+">" + book + "</a><br>");
          id++;
        }
      %>
</body>
</html>