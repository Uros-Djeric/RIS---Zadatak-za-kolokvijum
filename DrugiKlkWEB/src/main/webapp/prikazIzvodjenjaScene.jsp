<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Izvodjenja Scene</title>
</head>
<body style='background-color: rgb(50, 50, 50);'>
	<div align="center">
		<form action="/Pozoriste/predstave/getIzvodjenjaScene" method="get">
			<h4>
				Odabir scene: <select name="idScena">
					<c:forEach items="${scene}" var="s">
						<option value="${s.idScena}">${s.naziv}</option>
					</c:forEach>
				</select> <input type="submit" value="Prikazi">
			</h4>
		</form>

		<c:if test="${!empty izvodjenja}">


			<table border="1">
				<tr>
					<th>Datum</th>
					<th>Naziv</th>
					<th>Trajanje</th>
					<th></th>
				</tr>
				<c:forEach items = "${izvodjenja}" var = "i">
				<tr>
					<td>${i.datum}</td>
					<td>${i.predstava.naziv}</td>
					<td>${i.predstava.trajanje }</td>
					<td><a href="/Pozoriste/predstave/getUloge?idIzvodjenje=${i.idIzvodjenje}">Uloge</a></td>
				</tr></c:forEach>
			</table>
			
		</c:if>
	</div>
</body>
</html>