<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Citas — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
    <style>
        .citas-card { max-width: 960px; text-align: left; }
        .citas-table { width: 100%; border-collapse: collapse; margin-top: 12px; }
        .citas-table th, .citas-table td {
            text-align: left;
            padding: 12px 14px;
            border-bottom: 1px solid var(--color-border);
            font-size: 0.95rem;
        }
        .citas-table th { color: var(--color-text-muted); font-weight: 600; }
        .citas-actions { display: flex; gap: 10px; }
        .btn-small {
            padding: 6px 14px;
            border-radius: var(--radius-md);
            font-size: 0.85rem;
            font-weight: 600;
            border: none;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-small.reagendar { background: var(--color-input-bg); color: var(--color-text); }
        .btn-small.cancelar { background: #fee2e2; color: #b91c1c; }
    </style>
</head>
<body>

    <!-- ===== Navbar ===== -->
    <header class="navbar navbar-panel">
        <a href="${pageContext.request.contextPath}/citas" class="brand">
            <span class="brand-icon">
                <svg viewBox="0 0 24 24" width="20" height="20" fill="white">
                    <circle cx="8" cy="7" r="1.6"/>
                    <circle cx="14.5" cy="6" r="1.6"/>
                    <circle cx="18.5" cy="10" r="1.6"/>
                    <path d="M12 12c-3.3 0-6 2.1-6 4.7 0 1.4 1.2 2.3 2.6 2 .9-.2 1.7-.2 2.6 0h1.6c.9-.2 1.7-.2 2.6 0 1.4.3 2.6-.6 2.6-2 0-2.6-2.7-4.7-6-4.7z"/>
                </svg>
            </span>
            <span class="brand-name">PetCare</span>
        </a>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/citas" class="active">Mis Citas</a>
        </nav>
        <div class="nav-user">
            <span class="nav-user-name">${sessionScope.usuario.nombre}</span>
            <a href="${pageContext.request.contextPath}/autenticarse?accion=salir">Cerrar sesión</a>
        </div>
    </header>

    <!-- ===== Contenido ===== -->
    <main class="auth-page">
        <div class="auth-card citas-card">
            <h1 class="auth-title">Mis Citas</h1>
            <p class="auth-subtitle">Agenda, reagenda o cancela tus citas</p>

            <a href="${pageContext.request.contextPath}/citas?accion=agendarCita" class="btn-gradient" style="display: inline-block; width: auto; padding: 12px 24px;">Agendar Cita</a>

            <table class="citas-table">
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Mascota</th>
                        <th>Servicio</th>
                        <th>Veterinario</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${listaCitas}">
                        <tr>
                            <td>${c.fecha}</td>
                            <td>${c.hora}</td>
                            <td>${c.mascota.nombre}</td>
                            <td>${c.servicio.nombreServicio}</td>
                            <td>${c.veterinario.nombre}</td>
                            <td>${c.estado}</td>
                            <td class="citas-actions">
                                <c:if test="${c.estado == 'PENDIENTE'}">
                                    <a class="btn-small reagendar" href="${pageContext.request.contextPath}/citas?accion=reagendar&amp;citaId=${c.id}">Reagendar</a>
                                    <a class="btn-small cancelar" href="${pageContext.request.contextPath}/citas?accion=solicitarCancelacion&amp;citaId=${c.id}">Cancelar</a>
                                </c:if>
                                <c:if test="${c.estado != 'PENDIENTE'}">
                                    <span style="color: var(--color-text-muted);">—</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>

    <!-- ===== Footer ===== -->
    <footer class="site-footer">
        <div class="footer-grid">
            <div class="footer-brand">
                <div class="brand">
                    <span class="brand-icon">
                        <svg viewBox="0 0 24 24" width="18" height="18" fill="white">
                            <circle cx="8" cy="7" r="1.6"/>
                            <circle cx="14.5" cy="6" r="1.6"/>
                            <circle cx="18.5" cy="10" r="1.6"/>
                            <path d="M12 12c-3.3 0-6 2.1-6 4.7 0 1.4 1.2 2.3 2.6 2 .9-.2 1.7-.2 2.6 0h1.6c.9-.2 1.7-.2 2.6 0 1.4.3 2.6-.6 2.6-2 0-2.6-2.7-4.7-6-4.7z"/>
                        </svg>
                    </span>
                    <span class="brand-name light">PetCare</span>
                </div>
                <p>Cuidamos de tus mascotas con amor y profesionalismo. Agenda tu turno fácilmente.</p>
            </div>

            <div class="footer-col">
                <h4>Contacto</h4>
                <p>📞 +593123456</p>
                <p>✉️ info@petcare.com</p>
                <p>📍 Av. Libertador 1234, CABA</p>
            </div>
        </div>
        <p class="footer-copy">© 2026 PetCare. Todos los derechos reservados.</p>
    </footer>
</body>
</html>
