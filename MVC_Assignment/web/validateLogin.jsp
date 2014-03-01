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
String action = "";
if (userId == null || userId.length() == 0 || passwd == null || passwd.length() == 0)
	{	msg = "The userID or password cannot be empty";   }
else if (!userId.equals(passwd))
	{	msg = "The userID or password is not valid";   }
else {
	UserBean user = NewsDAOFactory.getTheDAO().getUser(userId,role);
	if (user == null) {
		session.setAttribute("userid",userId);
		response.sendRedirect(request.getContextPath()+"/news?action=add");
	} else {
		session.setAttribute("user",user);
		response.sendRedirect(request.getContextPath()+"/news?action=success");
	}
}
out.println("<p>Login result " + msg + "</p>");
%>
<br/>

</body>
</html>