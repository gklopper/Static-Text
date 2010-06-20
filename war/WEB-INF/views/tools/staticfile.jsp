<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>

<html>
	<head>
		<title>Static file editor</title>
		<link rel="stylesheet" type="text/css" href="/style/admin.css" media="all" />
</head>
	<body>
	<ui:navigation/>
	<div class="block">
		<h1>New Static File</h1>
		
		<form method="post" action="${uploadUrl}" enctype="multipart/form-data">
			
			<fieldset>
			<label for="name">Name</label>
			<input id="name" name="name" value="${file.name}"/>
			</fieldset>
			
			<fieldset>
			<label for="path">Path</label>
			<span class="read-only">/staticfile/</span><input class="short" id="path" name="path" value="${file.path}"/>
			</fieldset>
			
			<br/>
			
			<fieldset>
			<label for="type">Type</label>
			<input id="type" name="type" value="${file.type}"/>
			</fieldset>
			
			<fieldset>
			<label for="owner">Owner</label>
			<input class="read-only" readonly="readonly" id="owner" name="owner" value="${file.owner}"/>
			</fieldset>
			
			<br/>
			
			<fieldset>
				<input type="file" name="uploadedFile"/>
			</fieldset>

			<fieldset>
				<input class="submit" type="Submit" value="Save"/>
			</fieldset>
					
		</form>
		
		<br>
</div>
	</body>
</html>