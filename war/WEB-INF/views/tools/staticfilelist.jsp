<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
	<head>
		<title>Static file list</title>
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
		<h1>All Static Files</h1>
	
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
						<input type="Submit" value="Delete" onClick="return confirmSubmit()" ></input></td>
					</form>
			</tr>	
		</c:forEach>
		</table>
		
		<br>
		<table>
			<tr>
				<td><a href="./new">Upload New Static File</a></td>
				<td><a href="../../">Home</a></td>
			</tr>
		</table>
	</body>
</html>