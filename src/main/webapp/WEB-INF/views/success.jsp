<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>File Upload Success</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body style="background-color:#286090;">
	<div class="success" style="background-color:#EBEDEF;">
		File  <strong>${fileName}</strong> uploaded successfully.
		<br/><br/>
		<a href="<c:url value='/upload' />">Back</a>	
	</div>
</body>
</html>