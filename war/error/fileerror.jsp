<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<html>
<head>
	<title>Error uploading static file</title>
	<link rel="stylesheet" type="text/css" href="/style/admin.css" media="all" />
</head>

<body>
	<ui:navigation/>
	<div class="block">
	<h1>Could not upload file</h1>
	<div class="error">
		<c:out value="${param.error}" escapeXml="false"/>
	</div>
	</div>
</body>

</html>