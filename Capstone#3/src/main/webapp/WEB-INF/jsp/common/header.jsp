<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>National Park Geek</title>
	
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <c:url var="cssUrl" value="/css/site.css"/>
    <link rel="stylesheet" href="${cssUrl}" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>

<body class="container">
    <header>
    		<c:url value="/" var="homePageHref" />
    		<c:url value="/img/logo.png" var="logoSrc" />
        <a href="${homePageHref}">
        		<img src="${logoSrc}" alt="National Park Geek logo" />
        </a>
	</header>
    <nav class="nav-bar d-flex justify-content-center">
        <ul class="nav">
	        <li class="nav-item"><a href="<c:url value="/"/>">Home</a></li>
	        <li class="nav-item"><a href="<c:url value="/survey"/>">Survey</a></li>
	        <li class="nav-item"><a href="<c:url value="/surveyResults"/>">Favorite Parks</a></li>
	    </ul>
    </nav>