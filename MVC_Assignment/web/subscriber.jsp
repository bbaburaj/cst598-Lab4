<html>
<head><title>Subscriber Request</title></head>
<body bgcolor="#E6E6FA"/>
<form action="<%=request.getContextPath()%>/news" method=post>
<input type="hidden" name="action" value="becomeSubscriber" />
<h2>Add me as a Subscriber</h2>
User ID <input type="text" name="userId"><br/><br/>
<input type="submit" name="subscriber" value="Yes">
<input type="submit" name="subscriber" value="No">
</form>
</body>
</html>
