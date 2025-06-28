const API_CARRITO = "http://192.168.1.25:8080/api/v1/carrito"
const API_ORDEN = "http://192.168.1.25:8080/api/v1/ordenes"
const API_STOCK = "http://192.168.1.25:8080/api/stock"

const total = document.getElementById('total');
const subtotal = document.getElementById('subtotal');
const descuento = document.getElementById('descuento');
const btnProcederPago = document.getElementById('btnProcederPago');

const obtenerStock = async (idProducto) => {
    try {
        const response = await fetch(`${API_STOCK}/producto/${idProducto}`);
        return response.ok ? await response.json() : null;
    } catch {
        return null;
    }
};

const obtenerCarrito = async () => {
    const response = await fetch(`${API_CARRITO}/ver`);
    return response.ok ? await response.json() : [];
};

const cargarCarrito = async () => {
    try {
        const cursos = await obtenerCarrito();
        const tbody = document.querySelector('#tabla-carrito tbody');
        if (!tbody) return;
        let html = '';
        if (cursos.length === 0) {
            html = '<tr><td colspan="3" class="text-center">El carrito está vacío</td></tr>';
        } else {
            html = cursos.map(curso => `
                <tr>
                    <td>
                        <h5 class="mb-1">${curso.nombreCurso}</h5>
                        <p class="text-muted mb-0">Instructor: ${curso.docenteCurso}</p>
                    </td>
                    <td class="text-center">
                        <span class="h5">$${curso.precio}</span>
                    </td>
                    <td class="text-end">
                        <button onclick="eliminarCurso(${curso.id})" class="btn btn-outline-danger btn-sm">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `).join('');
        }
        tbody.innerHTML = html;
        calcularTotal(cursos);
    } catch {
        alert("Error al cargar el carrito");
    }
};

window.eliminarCurso = async (id) => {
    try {
        const stock = await obtenerStock(id);
        await fetch(`${API_CARRITO}/eliminar/${id}`, {method: 'DELETE'});
        await fetch(`${API_STOCK}/${id}/cantidad/${stock.cantidadDisponible + 1}`, {method: 'PUT'});
        alert("Curso eliminado del carrito");
        cargarCarrito();
    } catch {
        alert("Error al eliminar el curso");
    }
};

const calcularTotal = (cursos = []) => {
    const totalCarrito = cursos.reduce((sum, curso) => sum + curso.precio, 0);
    subtotal.textContent = `$${totalCarrito}`;
    total.textContent = `$${totalCarrito}`;
};

cargarCarrito();
