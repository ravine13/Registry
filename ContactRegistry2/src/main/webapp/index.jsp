<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet1</a>

<h2>Welcome to the Contact Registry Web App</h2>
<form action="contacts" method="GET">
    <button type="submit">View Contacts</button>
</form>
</body>
</html>