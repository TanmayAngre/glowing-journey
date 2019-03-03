<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Parking Vehicle Details...</h2>
<br>
<form action="slotservlet" method="post">
	<label>Enter the name of vehicle: </label>
	<input type="text" name="name">
	<br>
	<label>Enter the registration number of vehicle: </label>
	<input type="text" name="regno" size="10" maxlength="10">
	<br>
	<label>Enter the type of vehicle: </label>
	<select name="type">
		<option value="twowheeler">Two Wheeler</option>
		<option value="minifour">Mini Four Wheeler</option>
		<option value="maxfour">Max Four Wheeler</option>
	</select>
	<br>
	<input type="submit" name="submit" value="Submit Details">
</form>
</body>
</html>