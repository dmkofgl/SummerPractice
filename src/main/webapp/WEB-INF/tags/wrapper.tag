<%@tag description="Wrapper Tag" pageEncoding="UTF-8"%>
<html><body>
<header>

<form action="/LogoutServlet" method="post">
<img src='/images/index.png'>
<span>ReadIT</span>
<div style="position:absolute;right:0; top:0">
<span align=left> ${sessionScope.user.login}</span>
<input type="submit" value="Logout" >
</div>
</form>
</header>

  <jsp:doBody/>
</body></html>