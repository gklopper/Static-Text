<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<html>
	<head>
		<title>Static text list</title>
		<link rel="stylesheet" type="text/css" href="/style/admin.css" media="all" />
</head>
	<body>
	<ui:navigation/>
<div class="block">

		<h1>All Static Text</h1>
		<h2>cached for 1 hour</h2>
	
		<table>
		<tr><th colspan="2">Name</th><th>Content Type</th><th>Component path</th><th>Raw resource</th><th>Owner</th></tr>	
		<c:forEach var="text" items="${texts}">
			<tr>
				<td class="name first">${text.name}</td>
				<td><a href="/admin/statictext/${text.id}">edit</a></td>
				<td>${text.type}</td>
				<td>/statictext/${text.path}</td>
				<td><a href="http://resource.guim.co.uk/global/static/text/${text.path}">http://resource.guim.co.uk/global/static/text/${text.path}</a></td>			
				<td>${text.owner}</td>
			</tr>	
		</c:forEach>
		</table>
		</div>
	</body>
	
</html>