<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p><%= request.getAttribute("message") != null ?  request.getAttribute("message") : ""%> </p>
<form action="ChangePasswordServlet" method="get">
		UserName: <input type="text" name="UserName"> <br> 
		Old Password: <input type="password" name="OldPassword"> <br>
		New Password: <input id="newPwd" type="password" name="NewPassword"> <br> 
		Confirm Password:<input id="cnfPwd" type="password" name="ConfirmPassword"> <br>
		 
		<input type="submit" value="submitPassword"> 
		<input type="reset" value="reset">	
	</form>
</body>
</html>