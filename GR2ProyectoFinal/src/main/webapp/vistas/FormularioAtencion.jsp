<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar atención — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
</head>
<body>

    <!-- ===== Navbar ===== -->
    <header class="navbar navbar-panel">
        <a href="${pageContext.request.contextPath}/consultarCita" class="brand">
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
            <a href="${pageContext.request.contextPath}/consultarCita">Consultar Cita</a>
        </nav>
        <div class="nav-user">
            <span class="nav-user-name">${sessionScope.usuario.nombre}</span>
            <a href="${pageContext.request.contextPath}/autenticarse?accion=salir">Cerrar sesión</a>
        </div>
    </header>

    <!-- ===== Contenido ===== -->
    <main class="auth-page">
        <div class="auth-card">
            <h1 class="auth-title">Registrar Atención</h1>
            <p class="auth-subtitle">Completa la información de la consulta</p>

            <form action="${pageContext.request.contextPath}/atenderCitas" method="post" class="auth-form">
                <input type="hidden" name="accion" value="${accionGuardar}">

                <label for="fecha" class="field-label">Fecha</label>
                <div class="field-with-icon">
                    <input type="date" id="fecha" name="fecha" required>
                </div>

                <label for="edadMascota" class="field-label">Edad de la mascota</label>
                <div class="field-with-icon">
                    <input type="number" id="edadMascota" name="edadMascota" placeholder="3" required>
                </div>

                <label for="pesoMascota" class="field-label">Peso de la mascota (kg)</label>
                <div class="field-with-icon">
                    <input type="number" step="0.1" id="pesoMascota" name="pesoMascota" placeholder="12.5" required>
                </div>

                <label for="sintomas" class="field-label">Síntomas</label>
                <div class="field-with-icon">
                    <input type="text" id="sintomas" name="sintomas" required>
                </div>

                <label for="exploracion" class="field-label">Exploración</label>
                <div class="field-with-icon">
                    <input type="text" id="exploracion" name="exploracion" required>
                </div>

                <label for="diagnostico" class="field-label">Diagnóstico</label>
                <div class="field-with-icon">
                    <input type="text" id="diagnostico" name="diagnostico" required>
                </div>

                <label for="receta" class="field-label">Receta</label>
                <div class="field-with-icon">
                    <input type="text" id="receta" name="receta" required>
                </div>

                <label for="tratamiento" class="field-label">Tratamiento</label>
                <div class="field-with-icon">
                    <input type="text" id="tratamiento" name="tratamiento" required>
                </div>

                <button type="submit" class="btn-gradient">Guardar</button>
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
                <p>📞 +54 11 1234-5678</p>
                <p>✉️ info@petcare.com</p>
                <p>📍 Av. Libertador 1234, CABA</p>
            </div>
        </div>
        <p class="footer-copy">© 2026 PetCare. Todos los derechos reservados.</p>
    </footer>
</body>
</html>
