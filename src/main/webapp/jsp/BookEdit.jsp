<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.books.entities.*,java.text.*,java.util.*,com.books.services.*" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<script>
	function my_f(Id) {
	    var o=document.getElementById(Id)
	    o.style.display = (o.style.display == 'none')? 'block': 'none'
	}
	</script>
</head>
<body>
<t:wrapper>
</t:wrapper>
<form method="post" th:object="${book}" th:action>
<label> name
<input type="text" name="name"  th:field="*{name}" th:text="${book}">
</label>
<br>
<label> publish date:
<input type="hidden" value='${book.id}' name="bookId" th:field="*{id}">
<input type="date" name="publishDate"   th:field="*{publishDate}" >
</label>
<br>
<label> authors:</label>
<c:forEach items="${book.authors}" var="author">
     <a href="#" > ${author}</a>
<input type="hidden" value='${author.id}' name="authorsId">
      <button type="submit" name="removeAuthor" value="${author.id}">remove</button>
      <br>
</c:forEach>
 <input type="button" value="add" onclick="my_f('textid')"><br>

	<div id="textid" style="display:none">
	<ul>
	<c:forEach items="${canAuthorsAdd}" var="author">
	<li> <button type="submit" name="addedAuthor" value="${author.id}">add</button> ${author}</li>
	</c:forEach>
	</ul>

	</div>
<br>
<label> publisher:${book.publisher}
<input type="hidden" value='${book.publisher.id}' name="publisherId">
 <input type="button" value="change" onclick="my_f('publishers')"><br>
</label>
<div id="publishers" style="display:none">
<c:forEach items="${publishers}" var="publisher">
<li><button type="submit" name="changePublisher" value="${publisher.id}">change</button>  ${publisher} </li>
</c:forEach>
</div>
<br>
<button type="submit" formAction="save">confirm changes</button>
</form>
</body>
</html>