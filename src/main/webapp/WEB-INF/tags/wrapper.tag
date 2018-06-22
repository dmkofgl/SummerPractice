<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<html><body>
<header>
<span>Logo</span>
<span> Login successful. ${sessionScope.user}</span>

<form action="/LogoutServlet" method="post">
<input type="submit" value="Logout" >
</form>
</header>
</header>
  <jsp:doBody/>
</body></html>