<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<html>
	<head>
		<title>Static file list</title>
		<link rel="stylesheet" type="text/css" href="/style/admin.css" media="all" />
</head>
	<body>
	<ui:navigation/>
	<div class="block">
		<h1>All Static Files</h1>
		<h2>cached for 1 day</h2>
	
		<table>
		<tr><th>Name</th><th>Content Type</th><th>Raw resource</th><th colspan="2">Owner</th></tr>	
		<c:forEach var="file" items="${files}">
			<tr>
				<td class="name">${file.name}</td>
				<td>${file.type}</td>
				<td><a href="http://resource.guim.co.uk/global/static/file/${file.path}">http://resource.guim.co.uk/global/static/file/${file.path}</a></td>			
				<td>${file.owner}</td>
				<td>
					<ui:deleteButton message="Are you sure you want to delete this file?" idToDelete="${file.id}"/>
				</td>
			</tr>	
		</c:forEach>
		</table>
		</div>
	</body>
</html>