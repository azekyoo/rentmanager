<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Client Reservations</title>
</head>
<body>
    <h1>Client Reservations</h1>
    <table border="1">
        <tr>
            <th>Reservation ID</th>
            <th>Client ID</th>
            <th>Start Date</th>
            <th>End Date</th>
        </tr>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td>${reservation.id}</td>
                <td>${reservation.client_id}</td>
                <td>${reservation.debut}</td>
                <td>${reservation.fin}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
