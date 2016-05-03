<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Spring 4 MVC File Upload Example</title>
	<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="form-container">
		<h1>Welcome to FileUploader Example</h1>
		
		Click on below links to see FileUpload in action.<br/><br/>
		
		<a href="<c:url value='/singleUpload' />">Single File Upload</a>  OR  <a href="<c:url value='multiUpload' />">Multi File Upload</a>
	</div> 
</body>
</html>
