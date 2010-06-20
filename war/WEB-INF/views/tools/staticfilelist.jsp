<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<html>
	<head>
		<title>Static file list</title>
		<link rel="stylesheet" type="text/css" href="/style/admin.css" media="all" />
</head>
<script LANGUAGE="JavaScript">
	<!--
		function confirmSubmit() {
			var agree=confirm("Are you sure you wish to delete the file?");
			if (agree) {
				return true ;
			} else {
				return false ;
			}
		}
	// -->
</script>

	<body>
	<ui:navigation/>
	<div class="block">
		<h1>All Static Files</h1>
		<h2>cached for 1 day</h2>
	
		<table>
		<tr><th>Name</th><th>Content Type</th><th>Raw resource</th><th>Owner</th></tr>	
		<c:forEach var="file" items="${files}">
			<tr>
				<td class="name">${file.name}</td>
				<td>${file.type}</td>
				<td><a href="http://resource.guim.co.uk/global/static/file/${file.path}">http://resource.guim.co.uk/global/static/file/${file.path}</a></td>			
				<td>${file.owner}</td>
				<td>
					<form method="post">
						<input type="hidden" name="fileToDelete" value="${file.id}"/>
						<input class="submit" type="Submit" value="Delete" onClick="return confirmSubmit()" ></input></td>
					</form>
			</tr>	
		</c:forEach>
		</table>
		</div>
	</body>
</html>