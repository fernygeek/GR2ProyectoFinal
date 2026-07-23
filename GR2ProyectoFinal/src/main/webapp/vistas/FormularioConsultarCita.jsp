<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultar cita — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
    <style>
        .consulta-card { width: min(1120px, calc(100% - 32px)); max-width: 1120px !important; }
        .tabla-responsive { width: 100%; margin-top: 24px; overflow-x: auto; }
        .citas-table { width: 100%; min-width: 900px; border-collapse: collapse; }
        .citas-table th, .citas-table td {
            text-align: left;
            padding: 14px 12px;
            border-bottom: 1px solid var(--color-border);
            font-size: 0.95rem;
            vertical-align: middle;
        }
        .citas-table th { color: var(--color-text-muted); font-weight: 600; white-space: nowrap; }
        .citas-table tbody tr:hover { background: #fcf8ff; }
        .col-fecha, .col-hora, .col-estado, .col-acciones { white-space: nowrap; }
        .col-acciones { width: 175px; }
        .btn-accion {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-height: 38px;
            padding: 8px 13px;
            border-radius: 9px;
            background: linear-gradient(135deg, #9333ea, #ec4899);
            color: #fff;
            font-size: 0.86rem;
            font-weight: 700;
            line-height: 1.15;
            text-align: center;
            text-decoration: none;
            transition: transform .15s ease, filter .15s ease;
        }
        .btn-accion:hover { filter: brightness(1.06); transform: translateY(-1px); }
        .sin-accion { color: var(--color-text-muted); }
        .mensaje-exito {
            margin-bottom: 18px;
            padding: 12px 14px;
            border: 1px solid #86efac;
            border-radius: 10px;
            background: #f0fdf4;
            color: #166534;
            font-weight: 600;
            text-align: center;
        }
        @media (max-width: 700px) {
            .consulta-card { width: calc(100% - 20px); padding-left: 20px; padding-right: 20px; }
        }
    </style>
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
            <a href="${pageContext.request.contextPath}/consultarCita" class="active">Consultar Cita</a>
        </nav>
        <div class="nav-user">
            <span class="nav-user-name">${sessionScope.usuario.nombre}</span>
            <a href="${pageContext.request.contextPath}/autenticarse?accion=salir">Cerrar sesión</a>
        </div>
    </header>

    <!-- ===== Contenido ===== -->
    <main class="auth-page">
        <div class="auth-card consulta-card">
            <div class="auth-icon">
                <svg viewBox="0 0 24 24" width="28" height="28" fill="white">
                    <circle cx="8" cy="7" r="1.6"/>
                    <circle cx="14.5" cy="6" r="1.6"/>
                    <circle cx="18.5" cy="10" r="1.6"/>
                    <path d="M12 12c-3.3 0-6 2.1-6 4.7 0 1.4 1.2 2.3 2.6 2 .9-.2 1.7-.2 2.6 0h1.6c.9-.2 1.7-.2 2.6 0 1.4.3 2.6-.6 2.6-2 0-2.6-2.7-4.7-6-4.7z"/>
                </svg>
            </div>

            <h1 class="auth-title">Consultar Cita</h1>
            <p class="auth-subtitle">Ingresa el número de cédula del cliente</p>

            <c:if test="${not empty mensajeNoExistenCitasRegistradas}">
                <div class="auth-error">${mensajeNoExistenCitasRegistradas}</div>
            </c:if>

            <c:if test="${not empty mensajeAsistencia}">
                <div class="mensaje-exito" role="status">${mensajeAsistencia}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/consultarCita" method="post" class="auth-form">
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

                <button type="submit" class="btn-gradient">Buscar</button>
            </form>

            <c:if test="${not empty listaCitasAgendadas}">
                <div class="tabla-responsive">
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
                        <c:forEach var="c" items="${listaCitasAgendadas}">
                            <tr>
                                <td class="col-fecha">${c.fecha}</td>
                                <td class="col-hora">${c.hora}</td>
                                <td>${c.mascota.nombre}</td>
                                <td>${c.servicio.nombreServicio}</td>
                                <td>${c.veterinario.nombre}</td>
                                <td class="col-estado">${c.estado}</td>
                                <td class="col-acciones">
                                    <c:if test="${c.estado == 'PENDIENTE'}">
                                        <c:if test="${esRecepcionista}">
                                            <a class="btn-accion" href="${pageContext.request.contextPath}/consultarCita?accion=marcarAsistencia&amp;citaId=${c.id}">Marcar como asistida</a>
                                        </c:if>
                                        <c:if test="${esVeterinario}"><span class="sin-accion">Pendiente de asistencia en recepción</span></c:if>
                                    </c:if>
                                    <c:if test="${c.estado == 'ASISTIDA'}">
                                        <c:if test="${esRecepcionista && c.atendida}">
                                            <a class="btn-accion" href="${pageContext.request.contextPath}/registrarPago?accion=registrarPago&amp;citaId=${c.id}">Registrar pago</a>
                                        </c:if>
                                        <c:if test="${esRecepcionista && !c.atendida}">
                                            <span class="sin-accion">Esperando atención</span>
                                        </c:if>
                                        <c:if test="${esVeterinario && !c.atendida}">
                                            <a class="btn-accion" href="${pageContext.request.contextPath}/atenderCitas?accion=atenderLaCita&amp;citaId=${c.id}">Atender</a>
                                        </c:if>
                                        <c:if test="${esVeterinario && c.atendida}">
                                            <span class="sin-accion">Registre el pago en recepción</span>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${c.estado == 'COMPLETADA' || c.estado == 'CANCELADA'}">
                                        <span class="sin-accion">—</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>
            </c:if>
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
