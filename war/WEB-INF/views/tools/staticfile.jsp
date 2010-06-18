<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
	<head>
		<title>Static file editor</title>
		<style type="text/css">
			
			h1 {
				font-size: 20px;
			}
			
			label {
				width: 200px;
				font-weight: bold;
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
		</style>
</head>
	<body>
		<h1>New Static File</h1>
		
		<form method="post" action="${uploadUrl}" enctype="multipart/form-data">
			
			<fieldset>
			<label for="name">Name</label>
			<input id="name" name="name" value="${file.name}"/>
			</fieldset>
			
			<fieldset>
			<label for="path">Path</label>
			<span class="default">/staticfile/</span><input class="short" id="path" name="path" value="${file.path}"/>
			</fieldset>
			
			<fieldset>
			<label for="type">Type</label>
			<input id="type" name="type" value="${file.type}"/>
			</fieldset>
			
			<fieldset>
			<label for="owner">Owner</label>
			<input class="default" readonly="readonly" id="owner" name="owner" value="${file.owner}"/>
			</fieldset>
			
			<fieldset>
				<input type="file" name="uploadedFile"/>
			</fieldset>
			<br>
			<fieldset>
				<input type="Submit" value="Save"/>
			</fieldset>
					
		</form>
		
		<br>
		<table>
			<tr>
				<td><a href="./list">All Static Files</a></td>
				<td><a href="../../">Home</a></td>
			</tr>
		</table>

	</body>
</html>