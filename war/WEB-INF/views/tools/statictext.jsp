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
			<c:when test="${statictext.new}">
				<h1>New Static Text</h1>
			</c:when>
			<c:otherwise>
				<h1>Edit Static Text</h1>
			</c:otherwise>
		</c:choose>
		
		<c:forEach var="error" items="${errors}">
			<p class="error">${error}</p>
		</c:forEach>
		
		<form method="post">
			
			<input type="hidden" name="id" value="${statictext.id}"/>
			
			<fieldset>
			<label for="name">Name</label>
			<input id="name" name="name" value="${statictext.name}"/>
			</fieldset>
			
			<fieldset>
			<label for="type">Type</label>
			<input id="type" name="type" value="${statictext.type}"/>
			</fieldset>
			
			<br/>
			
			<fieldset>
			<label for="path">Path</label>
			<span class="read-only">/statictext/</span><input class="short" id="path" name="path" value="${statictext.path}"/>
			</fieldset>
			
			<fieldset>
			<label for="owner">Owner</label>
			<input class="read-only" readonly="readonly" id="owner" name="owner" value="${statictext.owner}"/>
			</fieldset>
			
			<br/>
			
			<fieldset class="large">
				<textarea name="text">${statictext.text}</textarea>
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