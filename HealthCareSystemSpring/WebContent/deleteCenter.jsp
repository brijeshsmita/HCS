<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Center</title>
</head>
<body>

	<jsp:include page="ShowCenters.jsp"></jsp:include>
	<br> Enter the center Id to be deleted:
	<br>
	<form action="deleteCenterSubmit" method="POST">
		Center Id:<input type="text" name="centerId"><br> <input
			type="submit" value="delete center">
	</form>


	<hr>
	Are you sure you want to delete the following center? Enter the center Id again to confirm!
	<form action="confirmDeleteCenter" method="POST">
		<table border="1">
			<tr>
				<td>Diagnostic Center Id</td>
				<td>Diagnostic Center Name</td>
				<td>Diagnostic Center Phone No</td>
				<td>Diagnostic Center Address</td>
			</tr>

			<tr>
				<td><input type="text" name="centerId" value="${center.centerId}"></td>
				<td>${center.centerName}</td>
				<td>${center.centerContactNo}</td>
				<td>${center.centerAddress}</td>
			</tr>
		</table>
		<input type="submit" value="Confirm Delete">
	</form>
	<span> ${deleteMessage }</span>
</body>
</html>