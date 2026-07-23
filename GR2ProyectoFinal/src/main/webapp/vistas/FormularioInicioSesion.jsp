<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar sesión — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
    <style>
        .demo-credentials {
            background: var(--color-input-bg);
            border: 1px dashed var(--color-border);
            border-radius: var(--radius-md);
            padding: 12px 16px;
            margin-bottom: 24px;
            text-align: left;
            font-size: 0.85rem;
            color: var(--color-text-muted);
        }
        .demo-credentials-title {
            font-weight: 700;
            color: var(--color-text);
            margin-bottom: 6px;
        }
        .demo-credentials code {
            background: #ffffff;
            border: 1px solid var(--color-border);
            border-radius: 4px;
            padding: 1px 5px;
            color: var(--color-text);
        }
    </style>
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
            <a href="${pageContext.request.contextPath}/login" class="active">Ingresar</a>
            <a href="${pageContext.request.contextPath}/registrarse" class="btn-nav-primary">Registrarse</a>
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

            <h1 class="auth-title">Bienvenido</h1>
            <p class="auth-subtitle">Ingresa a tu cuenta de PetCare</p>

            <c:if test="${not empty error}">
                <div class="auth-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/autenticarse" method="post" class="auth-form">

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

                <label for="clave" class="field-label">Contraseña</label>
                <div class="field-with-icon">
                    <svg class="field-icon" viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="4" y="10" width="16" height="10" rx="2"/>
                        <path d="M8 10V7a4 4 0 0 1 8 0v3"/>
                    </svg>
                    <input type="password" id="clave" name="clave" placeholder="••••••••" required>
                    <button type="button" class="toggle-password" aria-label="Mostrar contraseña" onclick="togglePassword()">
                        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7z"/>
                            <circle cx="12" cy="12" r="3"/>
                        </svg>
                    </button>
                </div>

                <div class="demo-credentials">
                    <p class="demo-credentials-title">Credenciales de prueba</p>
                    <p>Administrador — Cédula: <code>0000000001</code> · Clave: <code>admin123</code></p>
                    <p>Cliente — Cédula: <code>2000000001</code> · Clave: <code>cliente123</code></p>
                    <p>Recepcionista — Cédula: <code>3000000001</code> · Clave: <code>recep123</code></p>
                    <p>Veterinario (Dra. Ana Torres) — Cédula: <code>1000000002</code> · Clave: <code>vet123</code></p>
                </div>

                <button type="submit" class="btn-gradient">Iniciar Sesión</button>
            </form>

            <p class="auth-footer-text">
                ¿No tienes cuenta?
                <a href="${pageContext.request.contextPath}/registrarse" class="link-primary">Regístrate aquí</a>
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
                <p>Cuidamos de tus mascotas con amor y profesionalismo. Agenda tu turno fácil y rápido.</p>
            </div>

            <div class="footer-col">
                <h4>Contacto</h4>
                <p>📞 +593123456</p>
                <p>✉️ info@petcare.com</p>
            </div>

        </div>
    </footer>

    <script>
        function togglePassword() {
            const input = document.getElementById('clave');
            input.type = input.type === 'password' ? 'text' : 'password';
        }
    </script>
</body>
</html>
