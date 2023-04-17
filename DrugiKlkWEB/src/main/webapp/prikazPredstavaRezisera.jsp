<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz predstava za datog rezisera</title>
</head>
<body style='background-color: olive;'>
	<div align="center">
		<form action="/Pozoriste/predstave/getPredstaveZaRezisera" method="get">
			<h3>
				Reziser: <select name="idReziser"><c:forEach items="${reziseri}" var="r">
						<option value="${r.idReziser }">${r.ime }${r.prezime }</option>
						<br>
					</c:forEach></select><input type="submit" value="Prikazi">
			</h3>
		</form>


		<c:if test ="${empty predstave }">Nema trenutno predstava za rezisera :( </c:if><br>
		<c:if test="${!empty predstave }">
			<form action="/Pozoriste/predstave/getIzvestaj" method="get">
				Prikaz predstava za rezisera: "${reziser}" <br>
				<table border="1">
					<tr>
						<th>Naziv</th>
						<th>Trajanje</th>
						<th>Opis</th>
					</tr>
					<c:forEach items="${predstave}" var="p">
						<tr>
							<td>${p.naziv }</td>
							<td>${p.trajanje }</td>
							<td>${p.opis }</td>
						</tr>
					</c:forEach>



				</table>

				<input type="submit" value="Generisi izvestaj">
			</form>
		</c:if>


	</div>
</body>
</html>