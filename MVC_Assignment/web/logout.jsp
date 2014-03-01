<html>
<head><title>Logout Confirmation</title></head>
<body bgcolor="#E6E6FA"/>
<form action="<%=request.getContextPath()%>/news" method=post>
<input type="hidden" name="action" value="logout" />
<h2>Would you like to logout?</h2>
<input type="submit" name="logout" value="Yes">
<input type="submit" name="logout" value="No">
</form>
</body>
</html>
