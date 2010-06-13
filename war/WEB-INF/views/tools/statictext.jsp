<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
	<head>
		<title>Static text editor</title>
		<style type="text/css">
			
			h1 {
				font-size: 20px;
			}
			
			label {
				width: 200px;
				font-weight: bold;
			}
			
			.default {
				color: graytext;
			}
			
			fieldset {
				display: inline;
				border-style: solid;
				border-color: silver;
				border-top: none;
				border-left: none;
				border-width: 1px;
			}
			
			input {
				width: 200px;
			}
			
			.short {
				width: 136px;
			}
			
			.large {
				display: block;
				border-right: none;
				width: 600px;
			}
			
			textarea {
				width: 600px;
				height: 300px;
			}
			
			.error {
				font-weight: bold;
				color: red;
			}
			
		</style>
</head>
	<body>
		<c:choose>
			<c:when test="${statictext.new}">
				<h1>Edit Static Text</h1>
			</c:when>
			<c:otherwise>
				<h1>New Static Text</h1>
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
			<label for="path">Path</label>
			<span class="default">/statictext/</span><input class="short" id="path" name="path" value="${statictext.path}"/>
			</fieldset>
			
			<fieldset>
			<label for="type">Type</label>
			<input id="type" name="type" value="${statictext.type}"/>
			</fieldset>
			
			<fieldset>
			<label for="owner">Owner</label>
			<input class="default" readonly="readonly" id="owner" name="owner" value="${statictext.owner}"/>
			</fieldset>
			
			<fieldset class="large">
				<textarea name="text">${statictext.text}</textarea>
			</fieldset>
			
			<fieldset>
				<input type="Submit" value="Save"/>
			</fieldset>
					
		</form>
		

	</body>
</html>