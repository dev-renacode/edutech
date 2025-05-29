const API_CARRITO = "http://localhost:8080/api/v1/carrito"
const API_ORDEN = "http://localhost:8080/api/v1/ordenes"

const total = document.getElementById('total');
const subtotal = document.getElementById('subtotal');
const descuento = document.getElementById('descuento');
const btnProcederPago = document.getElementById('btnProcederPago');

async function cargarCarrito() {
    try {
        const response = await fetch(`${API_CARRITO}/ver`);
        const cursos = await response.json();
        
        const tbody = document.querySelector('#tabla-carrito tbody');
        tbody.innerHTML = '';

        console.log(cursos);

        if (cursos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3" class="text-center">El carrito está vacío</td></tr>';
        } else {
            cursos.forEach(curso => {
                const {docenteCurso, id, nombreCurso, precio} = curso;
                const fila = `
                <tr>
                    <td>
                        <h5 class="mb-1">${nombreCurso}</h5>
                        <p class="text-muted mb-0">Instructor: ${docenteCurso}</p>
                    </td>
                    <td class="text-center">
                        <span class="h5">$${precio}</span>
                    </td>
                    <td class="text-end">
                        <button onclick="eliminarCurso(${id})" class="btn btn-outline-danger btn-sm">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
                `
                tbody.innerHTML += fila;
            })
        }
    } catch (error) {
        console.error("Error al cargar el carrito:", error);
    }
}

async function eliminarCurso(id) {
    try {
        await fetch(`${API_CARRITO}/eliminar/${id}`, {method: 'DELETE'});
        alert("Curso eliminado del carrito");
        cargarCarrito();
        calcularTotal();
    } catch (error) {
        console.error("Error al eliminar el curso:", error);
    }
}

async function calcularTotal() {
    const response = await fetch(`${API_CARRITO}/ver`);
    const cursos = await response.json();
    const totalCarrito = cursos.reduce((sum, curso) => sum + curso.precio, 0);
    subtotal.textContent = `$${totalCarrito}`;
    total.textContent = `$${totalCarrito}`;
}

cargarCarrito();
calcularTotal();
