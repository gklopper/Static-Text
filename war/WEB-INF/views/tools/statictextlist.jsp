<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
	<head>
		<title>Static text list</title>
		<style type="text/css">
			h1 {
				font-size: 20px;
			}
			td, th {
				padding: 5px;
				border-right-style: solid;
				border-right-width: 1px;
				border-right-color: silver; 
			}
			
			.name {
				font-weight: bold;
			}	
		</style>
</head>
	<body>
		<h1>All Static Text</h1>
	
		<table>
		<tr><th colspan="2">Name</th><th>Content Type</th><th>Component path</th><th>Raw resource</th><th>Owner</th></tr>	
		<c:forEach var="text" items="${texts}">
			<tr>
				<td class="name">${text.name}</td>
				<td><a href="/admin/statictext/${text.id}">edit</a></td>
				<td>${text.type}</td>
				<td>/statictext/${text.path}</td>
				<td><a href="http://resource.guim.co.uk/global/static/text/${text.path}">http://resource.guim.co.uk/global/static/text/${text.path}</a></td>			
				<td>${text.owner}</td>
			</tr>	
		</c:forEach>
		</table>
		
		<br>
		<table>
			<tr>
				<td><a href="./new">Create New Static Text</a></td>
				<td><a href="../../">Home</a></td>
			</tr>
		</table>
		
	</body>
</html>