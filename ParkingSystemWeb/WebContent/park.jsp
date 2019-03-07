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
<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>
<script>
function validd(){
	var model = document.forms["parkform"]["name"];
	var regno = document.getElementById("regno").value;
	var regno1 = document.forms["parkform"]["regno"];
	var type = document.forms["parkform"]["type"];
	if(model.value == ""){
		document.getElementById("error1").innerHTML = "Model name cannot be empty.";
		model.focus();
		return false;
	}
	if(regno == ""){
		document.getElementById("error2").innerHTML = "Registration number cannot be empty.";
		regno1.focus();
		return false;
	}
	if(regno.length != 10){
		document.getElementById("error2").innerHTML = "You need to enter a valid registration number. Has to be of length 10.";
		regno1.focus();
		return false;
	}
	if(type.value == ""){
		document.getElementById("error3").innerHTML = "You need to select one of the types.";
		type.focus();
		return false;
	}
	document.getElementById("error1").innerHTML = "";
	document.getElementById("error2").innerHTML = "";
	document.getElementById("error3").innerHTML = "";
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
<h2 class="page-header text-info">Parking Vehicle Details</h2>
<br>

<form name="parkform" class="col-sm-offset-3 col-sm-6" action="slotservlet" onsubmit="return validd()" method="post">
	<div class="row">
	<div class="form-group col-md-6">
	<label class="sr-only">Enter the name of vehicle: </label>
	<input class="form-control" type="text" name="name" placeholder="Vehicle model name" value="">
	<div id="error1"></div>
	</div>
	<div class="form-group col-md-6">
	<label class="sr-only">Enter the registration number of vehicle: </label>
	<input class="form-control" type="text" id="regno" name="regno" size="10" maxlength="10" placeholder="Vehicle Registration Number">
	<div id="error2"></div>
	</div>
	</div>
	<br>
	<div class="row">
	<div class="form-group">	
	<select name="type" class="form-control">
	<option style="display:none;" value="">-- Select an option --</option>
    <option value="twowheeler">Two Wheeler</option>
    <option value="minifour">Mini Four Vehicle</option>
    <option value="maxfour">Max Four Vehicle</option>
  </select>
	<div id="error3"></div>
	</div>
	</div>
	<br>
	<div class="row">
	<input class="form-control btn btn-info" type="submit" name="submit" value="Submit Details">
	</div>
</form>

</div>

</body>
</html>