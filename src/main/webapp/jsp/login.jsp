<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="US-ASCII">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Login Page</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">

</head>
<body class="text-center">
<form action="LoginServlet" method="post" class="form-signin">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    ${sessionScope.message}
<%=request.getParameter("logout") == null ? "":"Successfully logged out" %>
    <input id="inputLogin" class="form-control" placeholder="Login" required="" type="text" name="user">
    <input id="inputPassword" class="form-control" placeholder="Password" required="" type="password" name="pwd">
    <button class="btn btn-lg btn-primary btn-block" type="submit" >Sign in</button>
</form>
</body>
</html>