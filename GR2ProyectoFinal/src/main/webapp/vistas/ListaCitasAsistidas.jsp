<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Atender citas — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
    <style>
        .citas-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .citas-table th, .citas-table td { padding: 12px 14px; border-bottom: 1px solid var(--color-border); text-align: left; }
        .citas-table th { color: var(--color-text-muted); font-weight: 600; }
        .accion { color: var(--color-primary); font-weight: 600; }
    </style>
</head>
<body>
    <header class="navbar navbar-panel">
        <a href="${pageContext.request.contextPath}/atenderCitas" class="brand"><span class="brand-name">PetCare</span></a>
        <nav class="nav-links"><a href="${pageContext.request.contextPath}/atenderCitas" class="active">Atender citas</a></nav>
        <div class="nav-user">
            <span class="nav-user-name">${sessionScope.usuario.nombre}</span>
            <a href="${pageContext.request.contextPath}/autenticarse?accion=salir">Cerrar sesión</a>
        </div>
    </header>
    <main class="auth-page">
        <div class="auth-card" style="max-width: 900px;">
            <h1 class="auth-title">Citas listas para atender</h1>
            <p class="auth-subtitle">Se muestran únicamente sus citas marcadas como asistidas por recepción.</p>
            <c:choose>
                <c:when test="${empty listaCitasAsistidas}">
                    <div class="auth-error">No tiene citas asistidas pendientes de atención.</div>
                </c:when>
                <c:otherwise>
                    <table class="citas-table">
                        <thead><tr><th>Fecha</th><th>Hora</th><th>Cliente</th><th>Mascota</th><th>Servicio</th><th>Estado</th><th>Acción</th></tr></thead>
                        <tbody>
                            <c:forEach var="c" items="${listaCitasAsistidas}">
                                <tr>
                                    <td>${c.fecha}</td><td>${c.hora}</td><td>${c.cliente.nombre}</td>
                                    <td>${c.mascota.nombre}</td><td>${c.servicio.nombreServicio}</td><td>${c.estado}</td>
                                    <td><a class="accion" href="${pageContext.request.contextPath}/atenderCitas?accion=atenderLaCita&amp;citaId=${c.id}">Atender</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</body>
</html>
