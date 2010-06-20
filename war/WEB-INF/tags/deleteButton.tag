<%@tag isELIgnored="false"%>
<%@attribute name="idToDelete" required="true"%>
<%@attribute name="message" required="true"%>
<form method="post">
	<input type="hidden" name="idToDelete" value="${pageScope.idToDelete}"/>
	<input class="submit" type="Submit" value="Delete" onClick="return confirm('${pageScope.message}')" ></input>
</form> 