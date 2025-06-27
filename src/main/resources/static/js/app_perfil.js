const API_REPORTES = "http://10.15.233.68:8080/api/v1/reportes";
const API_ORDENES = "http://10.15.233.68:8080/api/v1/ordenes";

const reportesDiv = document.getElementById('reportes');
const nombreUsuario = sessionStorage.getItem('nombreUsuario');
const nombreUsuarioDiv = document.getElementById('nombreUsuario');
const historialComprasDiv = document.getElementById('historialCompras');

const cargarNombreUsuario = () => {
    nombreUsuarioDiv.textContent = nombreUsuario;
}

const cargarReportes = async () => {
    const resp = await fetch(`${API_REPORTES}/listar`);
    const reportes = await resp.json();
    
    reportes.forEach(reporte => {
        if(reporte.usuario === nombreUsuario){
            reportesDiv.innerHTML += `
                <div class="report-item">
                    <p class="titulo">Titulo: ${reporte.titulo}</p>
                    <p>Mensaje: ${reporte.mensaje}</p>
                    <p class="fecha">Fecha: ${reporte.fecha}</p>
                </div>
            `;
        }
    });
}

const cargarHistorialCompras = async () => {
    const resp = await fetch(`${API_ORDENES}/listar`);
    const compras = await resp.json();
    compras.forEach(compra => {
        if(compra.usuario === nombreUsuario){
            historialComprasDiv.innerHTML += `
                <div class="purchase-item">
                    <p>Id: ${compra.ordenId}</p>
                    <p class="curso">Curso: ${compra.nombreCurso}</p>
                    <p>Cantidad: ${compra.cantidad}</p>
                    <p class="fecha">Fecha: ${compra.fecha}</p>
                    <p class="total">Precio: $${compra.precio}</p>
                </div>
            `;
        }
    });
}

cargarNombreUsuario();
cargarHistorialCompras();
cargarReportes();