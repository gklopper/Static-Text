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
		<tr><th>Name</th><th>Content Type</th><th>Raw resource</th><th>Owner</th></tr>	
		<c:forEach var="file" items="${files}">
			<tr>
				<td class="name">${file.name}</td>
				<td>${file.type}</td>
				<td><a href="http://resource.guim.co.uk/global/static/file/${file.path}">http://resource.guim.co.uk/global/static/file/${file.path}</a></td>			
				<td>${file.owner}</td>
			</tr>	
		</c:forEach>
		</table>

	</body>
</html>