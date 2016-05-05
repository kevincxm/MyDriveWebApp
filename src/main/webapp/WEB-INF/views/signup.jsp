<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- saved from url=(0040)http://getbootstrap.com/examples/signin/ -->
<html lang="en" ng-app="ui.bootstrap.demo">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<link rel="icon"
	href="https://pbs.twimg.com/profile_images/497458290012352513/TQnmSoMg.jpeg">
<title>MyDrive</title>

<!-- Bootstrap core CSS -->
<link href="http://getbootstrap.com/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="http://getbootstrap.com/examples/signin/signin.css"
	rel="stylesheet">

<style>
body {
    background-color: #fff;
    height: auto;
}
.container-fluid {
	height: auto;
	padding-top: 100px;
	padding-bottom: 600px;
	background-color:#EBEDEF;
}
#navigator {
	width: 100%;
	background-color:#286090;
}
</style>

<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-animate.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.14.2.js"></script>
    <spring:url value="/static/js/clientService.js" var="clientServiceJs" />
    <script src="${clientServiceJs}"></script>

</head>
<body ng-controller="SignupCtrl" >

	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container" id="navigator">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="" style="color:#fff;">MyDrive</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden active"><a href=""></a></li>
					<li class="page-scroll"><a href="" style="color:#fff;">About</a></li>
					<li class="page-scroll"><a href="" style="color:#fff;">Contact</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
	</nav>

	<div class="container-fluid">
		<form class="form-signin">
			<h2 class="form-signin-heading">Member SignUp</h2>
			<br/>
			<label for="InputName"  class="sr-only">Enter Name</label>	
			<input type="text" class="form-control" ng-model = "inputName" name="InputName" id="InputName" placeholder="Enter Name" required>
			<br />
			<label for="InputEmail"  class="sr-only">Enter Email</label>
			<input type="email" class="form-control" ng-model = "inputEmail" id="InputEmail" name="InputEmail" placeholder="Enter Email" required>
			<br />
			<label for="InputEmail"  class="sr-only">Enter Password</label>
			<input type="password" class="form-control" ng-model = "inputPW" id="InputPasswordFirst" name="InputPassword" placeholder="Enter Password" required>
			<br />
			<label for="InputEmail"  class="sr-only">Confirm Password</label>	
			<input type="password" class="form-control" ng-model = "inputPWC" id="InputPasswordSecond" name="InputPassword" placeholder="Confirm Password" required>
			<br />
			<button class="btn btn-lg btn-primary btn-block" type="submit" ng-click = "checkAndSignup(inputName, inputEmail, inputPW, inputPWC)">Submit</button>
		</form>
		
	</div>
</body>
</html>