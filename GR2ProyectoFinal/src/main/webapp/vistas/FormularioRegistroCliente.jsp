<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear cuenta — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
</head>
<body>

    <!-- ===== Navbar ===== -->
    <header class="navbar">
        <a href="${pageContext.request.contextPath}/login" class="brand">
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
            <a href="${pageContext.request.contextPath}/login">Ingresar</a>
            <a href="${pageContext.request.contextPath}/registrarse" class="btn-nav-primary active">Registrarse</a>
        </nav>
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

            <h1 class="auth-title">Crear Cuenta</h1>
            <p class="auth-subtitle">Únete a la familia PetCare</p>

            <c:if test="${not empty error}">
                <div class="auth-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/registrarse" method="post" class="auth-form">

                <label for="cedula" class="field-label">Cédula</label>
                <div class="field-with-icon">
                    <svg class="field-icon" viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="2" y="5" width="20" height="14" rx="2"/>
                        <circle cx="8" cy="12" r="2"/>
                        <path d="M14 10h6M14 14h4"/>
                    </svg>
                    <input type="text" id="cedula" name="cedula" placeholder="1712345678"
                           minlength="10" maxlength="10" pattern="[0-9]{10}" inputmode="numeric"
                           title="La cédula debe contener exactamente 10 dígitos" required>
                </div>

                <label for="nombre" class="field-label">Nombre Completo</label>
                <div class="field-with-icon">
                    <svg class="field-icon" viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="12" cy="8" r="4"/>
                        <path d="M4 21c0-4.4 3.6-8 8-8s8 3.6 8 8"/>
                    </svg>
                    <input type="text" id="nombre" name="nombre" placeholder="Juan Pérez" required>
                </div>

                <label for="correo" class="field-label">Email</label>
                <div class="field-with-icon">
                    <svg class="field-icon" viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="3" y="5" width="18" height="14" rx="2"/>
                        <path d="M3 7l9 6 9-6"/>
                    </svg>
                    <input type="email" id="correo" name="correo" placeholder="tu@email.com" required>
                </div>

                <label for="clave" class="field-label">Contraseña</label>
                <div class="field-with-icon">
                    <svg class="field-icon" viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="4" y="10" width="16" height="10" rx="2"/>
                        <path d="M8 10V7a4 4 0 0 1 8 0v3"/>
                    </svg>
                    <input type="password" id="clave" name="clave" placeholder="••••••••" required minlength="8">
                    <button type="button" class="toggle-password" aria-label="Mostrar contraseña" onclick="togglePassword('clave')">
                        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7z"/>
                            <circle cx="12" cy="12" r="3"/>
                        </svg>
                    </button>
                </div>

                <button type="submit" class="btn-gradient">Crear Cuenta</button>
                <button type="submit" name="accion" value="cancelarRegistro" formnovalidate class="btn-gradient" style="margin-top: 12px; background: var(--color-input-bg); color: var(--color-text);">Cancelar</button>
            </form>

            <p class="auth-footer-text">
                ¿Ya tienes cuenta?
                <a href="${pageContext.request.contextPath}/login" class="link-primary">Inicia sesión aquí</a>
            </p>
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

    <script>
        function togglePassword(inputId) {
            const input = document.getElementById(inputId);
            input.type = input.type === 'password' ? 'text' : 'password';
        }
    </script>
</body>
</html>
