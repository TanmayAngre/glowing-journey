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
<script>
function validddd(){
	var no = document.forms["revokeform"]["ticketno"];
	if(no.value==""){
		document.getElementById("er1").innerHTML = "Ticket number cannot be empty.";
		return false;
	}
	if(!no.value.match(/^\d+$/)){
    	document.getElementById("er1").innerHTML = "Input must be in numerical form.";
    	return false;
    }
	return true;
}
</script>
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
<h2 class="page-header text-info">Revoking vehicle</h2>
<div class="row">
<form name="revokeform" class="col-sm-offset-3 col-sm-6" action="revokeservlet" method="post" onsubmit="return validddd()">
	<div class="form-group">
	<label>Enter your ticket number: </label>
	<input class="form-control" type="number" name="ticketno">
	<div id="er1"></div>
	</div>
	<br><br>
	<input class="form-control btn btn-info" type="submit" value="Submit">
</form>
</div>
</div>
</body>
</html>