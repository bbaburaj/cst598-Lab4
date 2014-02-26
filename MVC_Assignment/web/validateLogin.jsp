<%@page import="model.UserBean"%>
<%@page import="dao.*"%>
<html>
<head>
</head>
<body>
<%

String msg = "";
String userId = request.getParameter("userid");
String passwd = request.getParameter("passwd");
String role = request.getParameter("login_type");
if (userId == null || userId.length() == 0 || passwd == null || passwd.length() == 0)
	{	msg = "The userID or password cannot be empty";   }
else if (!userId.equals(passwd))
	{	msg = "The userID or password is not valid";   }
else {
	UserBean user = NewsDAOFactory.getTheDAO().getUser(userId,role);
	if (user == null) {
		msg = "No such user " + userId;
		response.sendRedirect("newUser.html");
	} else {
		msg = "Successful login, you are " + user.toString();
	}
}

out.println("<p>Login result " + msg + "</p>");
%>
<br/>
<pre>
At this point you would know whether you had a successful login or not, and
if so have a UserBean representing the existing user. If the login works (same
userid and password), but the user does not exist, then you should ask if a
new user should be created and under which role (subscriber or reporter).
NOTE: You do not have to keep this logic in a JSP. In fact you shouldn't!
</pre>
</body>
</html>