<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.books.entities.*,java.text.*,java.util.*,com.books.services.*" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
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
 <input type="button" value="add" onclick="my_f('textid')"><br>
	<div id="textid" style="display:none">
	<%
	List<Person> authors =  (List<Person>)request.getAttribute("canAuthorsAdd");
	out.print("<ul>");
	for(Person author :authors){
	out.print("<li>");
	out.print(String.format("<button type='submit' name='addedAuthor' value='%s'>add</button> ",author.getId()));
out.print(author);
	out.print("</li>");
	}
	out.print("</ul>");
	%>
	</div>
<br>
<label> publisher:${book.publisher}
 <input type="button" value="change" onclick="my_f('publishers')"><br>
</label>
<div id="publishers" style="display:none">
<c:forEach items="${publishers}" var="publisher">
 ${publisher} <button type="submit" name="changePublisher" value="${publisher.id}">change</button> <br>
</c:forEach>
</div>
<br>
<input type="submit" value="confirm changes" name="confirmChange">
</form>
</body>
</html>