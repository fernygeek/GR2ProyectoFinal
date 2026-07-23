<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
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
            <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
        </nav>
        <div class="nav-user">
            <span class="nav-user-name">${sessionScope.usuario.nombre}</span>
            <a href="${pageContext.request.contextPath}/autenticarse?accion=salir">Cerrar sesión</a>
        </div>
    </header>

    <!-- ===== Contenido ===== -->
    <main class="auth-page">
        <div class="auth-card">
            <div class="auth-icon">
                <svg viewBox="0 0 24 24" width="28" height="28" fill="white">
                    <circle cx="8" cy="7" r="1.6"/>
                    <circle cx="14.5" cy="6" r="1.6"/>
                    <circle cx="18.5" cy="10" r="1.6"/>
                    <path d="M12 12c-3.3 0-6 2.1-6 4.7 0 1.4 1.2 2.3 2.6 2 .9-.2 1.7-.2 2.6 0h1.6c.9-.2 1.7-.2 2.6 0 1.4.3 2.6-.6 2.6-2 0-2.6-2.7-4.7-6-4.7z"/>
                </svg>
            </div>

            <h1 class="auth-title">Confirmación</h1>
            <p class="auth-subtitle">${mensaje}</p>

            <form action="${pageContext.request.contextPath}/citas" method="post" class="auth-form">
                <c:if test="${not empty mascota}"><input type="hidden" name="mascota" value="${mascota}"></c:if>
                <c:if test="${not empty servicio}"><input type="hidden" name="servicio" value="${servicio}"></c:if>
                <c:if test="${not empty veterinario}"><input type="hidden" name="veterinario" value="${veterinario}"></c:if>
                <c:if test="${not empty horarioSeleccionado}"><input type="hidden" name="horarioSeleccionado" value="${horarioSeleccionado}"></c:if>
                <c:if test="${not empty citaId}"><input type="hidden" name="citaId" value="${citaId}"></c:if>
                <c:if test="${not empty nuevoHorario}"><input type="hidden" name="nuevoHorario" value="${nuevoHorario}"></c:if>

                <button type="submit" name="accion" value="${accionConfirmar}" class="btn-gradient">Confirmar</button>
                <button type="submit" name="accion" value="${accionCancelar}" class="btn-gradient" style="margin-top: 12px; background: var(--color-input-bg); color: var(--color-text);">Cancelar</button>
            </form>
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
