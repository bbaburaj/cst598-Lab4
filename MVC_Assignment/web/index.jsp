<%@page import="model.UserBean"%>
<html>
<body bgcolor="#E6E6FA"/>
<h2>NEW News</h2>
<p>NEW news is a news resource about NEW stuff.</p>
<p>
NEW news is a fictional organization managing fictional news items
	for a non-fictional web programming class.
</p>
<pre>
</pre>
<%
  UserBean user = (UserBean)session.getAttribute("user");
  String msg = "";
  if(user!=null){
  	msg = "Logged in as <b>"+user.getUserId()+"</b>\r\n<td><a href=\"./logout.jsp\">Logout</a></td>";
  } else{
  	msg="<td><a href=\"./login.html\">Login</a></td>";
  }
  out.println("<br>"+msg+"</br>");
 %>
<br/>
<table border="0">
<tr>
<td><a href="./about.html">About</a></td>
<td><a href="<%=request.getContextPath()%>/news?action=view">View News</a></td>
</tr>
</table>
</body>
</html>
