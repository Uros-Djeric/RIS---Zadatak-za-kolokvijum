<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz izvodjenja</title>
</head>
<body style='background-color: gray'>
	<form action="/Pozoriste/predstave/getPredstava" method="get">
		<div align="center">

			<h3>
				Unos naziva predstave: <input type="text" name="naziv"> <input
					type="submit" value="Prikazi"><br>
			</h3>
		</div>
	</form>

	<div align="center">
		<c:if test= "${empty izvodjenja }">Nema izvodjenja za datu predstavu :( </c:if> <br>
		<c:if test="${!empty izvodjenja }">
			<h3>Prikaz izvodjenja za predstavu: "${predstava}"</h3>
			<br>
			<br>
			<table border="1">
				<tr>
					<th>Datum</th>
					<th>Scena</th>
					<th></th>
				</tr>
				<c:forEach items="${izvodjenja }" var="i">
					<tr>
						<td>${i.datum}</td>
						<td>${i.scena.naziv}</td>
						<td><a href="/Pozoriste/predstave/vratiKarteZaIzvodjenje?idIzvodjenja=${i.idIzvodjenje }">Karte</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>