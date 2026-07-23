<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agendar cita — PetCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/petcare.css">
    <style>
        .opciones-grid { display: flex; flex-direction: column; gap: 10px; margin-top: 12px; }
        .opcion-btn {
            padding: 12px 16px;
            border-radius: var(--radius-md);
            border: 1px solid var(--color-border);
            background: var(--color-input-bg);
            color: var(--color-text);
            font-weight: 600;
            cursor: pointer;
            text-align: left;
            width: 100%;
        }
        .opcion-btn:hover { background: var(--gradient-primary); color: #fff; border-color: transparent; }
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
            <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
        </nav>
        <div class="nav-user">
            <span class="nav-user-name">${sessionScope.usuario.nombre}</span>
            <a href="${pageContext.request.contextPath}/autenticarse?accion=salir">Cerrar sesión</a>
        </div>
    </header>

    <!-- ===== Contenido ===== -->
    <main class="auth-page">
        <div class="auth-card" style="max-width: ${paso == 'horario' ? '760px' : '640px'};">
            <h1 class="auth-title">Agendar cita</h1>

            <c:if test="${paso == 'mascota'}">
                <p class="auth-subtitle">Selecciona la mascota</p>
                <div class="opciones-grid">
                    <c:forEach var="m" items="${listaMascotas}">
                        <form action="${pageContext.request.contextPath}/citas" method="post">
                            <input type="hidden" name="accion" value="seleccionarMascota">
                            <input type="hidden" name="mascota" value="${m.id}">
                            <button type="submit" class="opcion-btn">${m.nombre} (${m.especie} - ${m.raza})</button>
                        </form>
                    </c:forEach>
                </div>
                <a href="${pageContext.request.contextPath}/citas?accion=agregarMascota" class="btn-gradient" style="display: block; text-align: center; margin-top: 12px; background: var(--color-input-bg); color: var(--color-text);">+ Registrar nueva mascota</a>
            </c:if>

            <c:if test="${paso == 'servicio'}">
                <p class="auth-subtitle">Selecciona el servicio</p>
                <div class="opciones-grid">
                    <c:forEach var="s" items="${listaServicios}">
                        <form action="${pageContext.request.contextPath}/citas" method="post">
                            <input type="hidden" name="accion" value="seleccionarServicio">
                            <input type="hidden" name="mascota" value="${mascota}">
                            <input type="hidden" name="servicio" value="${s.id}">
                            <button type="submit" class="opcion-btn">${s.nombreServicio}</button>
                        </form>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${paso == 'veterinario'}">
                <p class="auth-subtitle">Selecciona el veterinario</p>
                <div class="opciones-grid">
                    <c:forEach var="v" items="${listaVeterinarios}">
                        <form action="${pageContext.request.contextPath}/citas" method="post">
                            <input type="hidden" name="accion" value="seleccionarVeterinario">
                            <input type="hidden" name="mascota" value="${mascota}">
                            <input type="hidden" name="servicio" value="${servicio}">
                            <input type="hidden" name="veterinario" value="${v.cedula}">
                            <button type="submit" class="opcion-btn">${v.nombre}</button>
                        </form>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${paso == 'horario'}">
                <p class="auth-subtitle">Selecciona el horario</p>

                <div class="calendario-nav">
                    <form action="${pageContext.request.contextPath}/citas" method="post">
                        <input type="hidden" name="accion" value="verSemanaAgendamiento">
                        <input type="hidden" name="mascota" value="${mascota}">
                        <input type="hidden" name="servicio" value="${servicio}">
                        <input type="hidden" name="veterinario" value="${veterinario}">
                        <input type="hidden" name="semana" value="${semanaAnterior}">
                        <button type="submit" class="btn-semana">‹ Anterior</button>
                    </form>
                    <span class="calendario-rango">${diasSemana[0]} – ${diasSemana[4]}</span>
                    <form action="${pageContext.request.contextPath}/citas" method="post">
                        <input type="hidden" name="accion" value="verSemanaAgendamiento">
                        <input type="hidden" name="mascota" value="${mascota}">
                        <input type="hidden" name="servicio" value="${servicio}">
                        <input type="hidden" name="veterinario" value="${veterinario}">
                        <input type="hidden" name="semana" value="${semanaSiguiente}">
                        <button type="submit" class="btn-semana">Siguiente ›</button>
                    </form>
                </div>

                <table class="calendario-table">
                    <thead>
                        <tr>
                            <th></th>
                            <c:forEach var="dia" items="${diasSemana}">
                                <th>${dia}</th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="fila" items="${calendario}">
                            <tr>
                                <td>${fila.hora}</td>
                                <c:forEach var="casilla" items="${fila.casillas}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${casilla.disponible}">
                                                <form action="${pageContext.request.contextPath}/citas" method="post">
                                                    <input type="hidden" name="accion" value="registrarCita">
                                                    <input type="hidden" name="mascota" value="${mascota}">
                                                    <input type="hidden" name="servicio" value="${servicio}">
                                                    <input type="hidden" name="veterinario" value="${veterinario}">
                                                    <input type="hidden" name="horarioSeleccionado" value="${casilla.horario}">
                                                    <button type="submit" class="slot-disponible">Disponible</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="slot-no-disponible">No disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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
                <p>📞 +54 11 1234-5678</p>
                <p>✉️ info@petcare.com</p>
                <p>📍 Av. Libertador 1234, CABA</p>
            </div>
        </div>
        <p class="footer-copy">© 2026 PetCare. Todos los derechos reservados.</p>
    </footer>
</body>
</html>
