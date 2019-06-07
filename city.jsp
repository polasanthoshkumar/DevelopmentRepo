<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<title>Australia cities Weather Report</title>
</head>

<body>
	<h2>Please select your city to View Weather</h2>

	<form:form method="POST" commandName="city">
		<table>
			<tr>
				<td>Please select:</td>
				<td><form:select path="cityName">
					  <form:option value="" label="...." />
					  <form:options items="${cities}" />
				       </form:select>
                                </td>
				<td><form:errors path="cityName" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Submit"></td>
			</tr>
			<tr>
		</table>
	</form:form>

</body>
</html>