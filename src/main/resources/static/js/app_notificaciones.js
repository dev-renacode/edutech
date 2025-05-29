const API_NOTIFICACIONES = "http://localhost:8080/api/v1/notificaciones";
const nombreUsuario = sessionStorage.getItem("nombreUsuario");
const notificaciones = document.getElementById("notificaciones");

async function guardarOrden() {
    try {
        const resp = await fetch(`${API_CARRITO}/ver`);
        const cursos = await resp.json();

        if (cursos.length === 0) return alert("El carrito está vacío");

        const ordenId = Date.now();
        const fecha = new Date().toLocaleDateString('es-ES');

        const agrupados = cursos.reduce((acc, c) => {
        if (!acc[c.id]) acc[c.id] = {
            ordenId,            
            usuario: nombreUsuario,
            nombreCurso: c.nombreCurso,
            precio: c.precio,
            cantidad: 0,
            total: 0,
            fecha: fecha
        };
        acc[c.id].cantidad += 1;
        acc[c.id].total    = acc[c.id].precio * acc[c.id].cantidad;
        return acc;
        }, {});

        const ordenes = Object.values(agrupados);
        
        for (const o of ordenes) {
            await fetch(`${API_ORDEN}/guardar`, {
                method : 'POST',
                headers: { 'Content-Type': 'application/json' },
                body   : JSON.stringify(o)
            });
        }

        await fetch(`${API_NOTIFICACIONES}/guardar`, {
            method : 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                mensaje : `Se ha realizado una nueva orden con el id ${ordenId}`,
                usuario : nombreUsuario,
                idOrden : ordenId,
                leido   : false
            })
        });

        await fetch(`${API_CARRITO}/vaciar`, { method: 'DELETE' });
        cargarCarrito();
        calcularTotal();

        mostrarNotificaciones();

        console.log(`Órdenes guardadas con ordenId=${ordenId}:`, ordenes);
        
    } catch (err) {
        console.error('Error enviando órdenes:', err);
    }
}

async function mostrarNotificaciones() {
    try {
        const resp = await fetch(`${API_NOTIFICACIONES}/listar`);
        const notificaciones = await resp.json();
        const notificacionesDropdown = document.getElementById('notificaciones-dropdown');
        const notificacionesBadge = document.getElementById('notificaciones-badge');

        while (notificacionesDropdown.children.length > 2) {
            notificacionesDropdown.removeChild(notificacionesDropdown.lastChild);
        }

        const notificacionesNoLeidas = notificaciones.filter(n => !n.leido).length;
        if (notificacionesNoLeidas > 0) {
            notificacionesBadge.textContent = notificacionesNoLeidas;
            notificacionesBadge.style.display = 'block';
        } else {
            notificacionesBadge.style.display = 'none';
        }

        if (notificaciones.length === 0) {
            const li = document.createElement('li');
            li.innerHTML = '<span class="dropdown-item text-muted">No hay notificaciones</span>';
            notificacionesDropdown.appendChild(li);
            return;
        }

        const ordenesResp = await fetch("http://localhost:8080/api/v1/ordenes/listar");
        const ordenes = await ordenesResp.json();

        notificaciones.forEach(notificacion => {
            const ordenesDeEstaNoti = ordenes.filter(o => o.ordenId === notificacion.idOrden);

            let detalleOrden = '';
            let totalCompra = 0;
            if (ordenesDeEstaNoti.length > 0) {
                detalleOrden = '<div style="margin-top: 0.5em;">';
                ordenesDeEstaNoti.forEach(o => {
                    detalleOrden += `
                        <div style="
                            background: #f8f9fa;
                            border-radius: 8px;
                            padding: 0.5em 0.8em;
                            margin-bottom: 0.5em;
                            border: 1px solid #e0e0e0;
                        ">
                            <div style="font-size: 0.97em; margin-bottom: 0.2em;">
                                <span style="font-weight: 600;">Curso:</span> ${o.nombreCurso}
                            </div>
                            <div style="font-size: 0.97em;">
                                <span style="font-weight: 600;">Cantidad:</span> ${o.cantidad}
                            </div>
                            <div style="font-size: 0.97em;">
                                <span style="font-weight: 600;">Total:</span> $${o.total}
                            </div>
                        </div>
                    `;
                    totalCompra += o.total;
                });
                detalleOrden += `<div style=\"text-align:right; font-weight:600; margin-top:0.5em;\">Total de la compra: $${totalCompra}</div>`;
                detalleOrden += '</div>';
            }

            const li = document.createElement('li');
            li.style.marginBottom = '0.7em';
            li.innerHTML = `
                <a class="dropdown-item${notificacion.leido ? ' noti-leida' : ''}" 
                   href="#" 
                   data-id="${notificacion.id}" 
                   style="white-space: normal; word-break: break-word;${notificacion.leido ? 'background: #e9ecef;' : ''}">
                    <small class="text-muted" style="display: block; margin-bottom: 0.2em;">${notificacion.fecha ? new Date(notificacion.fecha).toLocaleString() : ''}</small>
                    <span style="white-space: normal; word-break: break-word; font-weight: 400;">${notificacion.mensaje}</span>
                    ${detalleOrden}
                </a>
            `;
            notificacionesDropdown.appendChild(li);
        });

        notificacionesDropdown.querySelectorAll('.dropdown-item').forEach(item => {
            item.addEventListener('click', async function(e) {
                e.preventDefault();
                const notiId = this.getAttribute('data-id');
                if (!notiId) return;

                await fetch(`http://localhost:8080/api/v1/notificaciones/marcar-leida/${notiId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' }
                });
                
                mostrarNotificaciones();
            });
        });
    } catch (error) {
        console.error('Error al cargar notificaciones:', error);
    }
}

document.addEventListener('DOMContentLoaded', mostrarNotificaciones);

setInterval(mostrarNotificaciones, 30000);

btnProcederPago.addEventListener('click', guardarOrden);