<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css?family=Montserrat|Open+Sans');
body {
    font-family: 'Open Sans', sans-serif;
    font-family: 'Montserrat', sans-serif;
}
</style>
</head>
<body>
<div class="container">
<h2 class="page-header text-info">Vehicle Parking System</h2>
<br>
<center><h5><% String res = (String)getServletContext().getAttribute("message"); if(res==null){out.print("");} else{out.print(res);} %></h5></center>
<center><h4>Select an option to park or unpark your vehicle...</h4></center>
<br><br>
<form action="selectOption">
<div class="row" align-items-center>
<div class="col-sm-offset-5 col-sm-3">
<button class="btn btn-info btn-lg" type="submit" name="Button" value="park">Park your vehicle</button>
</div>
</div>
<br><br><br>
<div class="row align-items-center">
<div class="col-sm-offset-5 col-sm-3">
<button class="btn btn-info btn-lg" type="submit" name="Button" value="remove">Remove your vehicle</button>
</div>
</div>
</form>
</div>
</body>
</html>