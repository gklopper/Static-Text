<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>

<html>
	<head>
		<title>Static text editor</title>
		<link rel="stylesheet" type="text/css" href="/style/admin.css" media="all" />
</head>
	<body>
<ui:navigation/>
<div class="block">

		<c:choose>
			<c:when test="${project.new}">
				<h1>New Project</h1>	
			</c:when>
			<c:otherwise>
				<h1>Edit project</h1>
			</c:otherwise>
		</c:choose>
		
		
		<c:forEach var="error" items="${errors}">
			<p class="error">${error}</p>
		</c:forEach>
		
		<form method="post">
			
			<input type="hidden" name="id" value="${project.id}"/>
			
			<fieldset>
			<label for="name">Name</label>
			<input id="name" name="name" value="${project.name}"/>
			</fieldset>
			<br/>
			<fieldset>
			<label for="owner">Owner</label>
			<input class="read-only" readonly="readonly" id="owner" name="owner" value="${project.owner}"/>
			</fieldset>
			<br/>
			<fieldset class="large">
				<input class="submit" type="Submit" value="Save"/>
			</fieldset>
					
		</form>
		
		<br>
</div>
	</body>
</html>