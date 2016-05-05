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
.row{
	margin-top:20px;
}
</style>

<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-animate.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.14.2.js"></script>
    <spring:url value="/static/js/clientService.js" var="clientServiceJs" />
    <script src="${clientServiceJs}"></script>

</head>
<body ng-controller="myCtrl" data-ng-init="init()" >

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
		<div >
		    <input type="file" file-model="myFile"/>
		    <button ng-click="uploadFile()">upload me</button>
		</div>
		
		  <div class="row">
		    <div class="col-xs-10 col-sm-6 col-md-4 col-xs-offset-1 col-sm-offset-3 col-md-offset-4 input-group">
		      <span class="input-group-addon" id="basic-addon1"><i class="glyphicon glyphicon-search"></i></span>
		      <input type="text" class="form-control" placeholder="Search keyword here" ng-model="searchText">
		    </div>
		  </div>
		
		  <div class="row">
		    <div class="col-sm-12 col-md-10 col-md-push-1 text-center">
		     
		        <table class="table">
		          <tr class = "event-checkIn-tb-h">
		            <td><b>Name</b></td>
		            <td><b>Size</b></td>
		            <td><b>Type</b></td>
		            <td><b>Action</b></td>
		          </tr>
		          <tr class="event-checkIn-tb-black" ng-repeat="a in FileList | filter:searchText">
		            <td ng-class="isGrey[a._id]"><p>{{a.fileName}}</p></td>
		            <td ng-class="isGrey[a._id]"><p>{{a.fileSize}}</p></td>
		            <td ng-class="isGrey[a._id]"><p>{{a.fileType}}</p></td>
		            <td class = "event-checkIn-tb-td">
		              <div class="row">
		                <button type="button" class="btn btn-primary glyphicon glyphicon-download-alt event-checkIn-btn" ng-model="checkinBtnY" ng-click="highlight(a._id)"></button>
		                <button type="button" class="btn btn-danger glyphicon glyphicon-remove event-checkIn-btn" ng-model="checkinBtnN" ng-click="deHighlight(a._id)"></button>
		              </div>
		            </td>
		          </tr>
		        </table>
		
		    </div>
		  </div>
	</div>
</body>
</html>


