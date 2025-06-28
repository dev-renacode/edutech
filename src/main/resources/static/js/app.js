const cursoContainer = document.getElementById("container-cursos");

const API = "http://192.168.1.25:8080/api/v1/cursos"
const API_CARRITO = "http://192.168.1.25:8080/api/v1/carrito"
const API_STOCK = "http://192.168.1.25:8080/api/stock"

const obtenerStock = async (idProducto) => {
    try {
        const response = await fetch(`${API_STOCK}/producto/${idProducto}`);
        return response.ok ? await response.json() : null;
    } catch {
        return null;
    }
};

const crearStockInicial = async (idProducto) => {
    try {
        await fetch(API_STOCK, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({idProducto, cantidadDisponible: 50, cantidadMinima: 10})
        });
    } catch {}
};

const mostrarCursos = async () => {
    try {
        const response = await fetch(API);
        const cursos = await response.json();
        let html = '';
        for (const curso of cursos) {
            let stock = await obtenerStock(curso.id);
            if (!stock) {
                await crearStockInicial(curso.id);
                stock = await obtenerStock(curso.id);
            }
            const stockClass = stock.estado === 'AGOTADO' ? 'stock-agotado' :
                               stock.estado === 'BAJO_STOCK' ? 'stock-bajo' : 'stock-disponible';
            html += `
                <div class="curso-container">
                    <div class="curso">
                        <h2>${curso.nombreCurso}</h2>
                        <p>${curso.descripcion}</p>
                        <p>Docente: ${curso.docenteCurso}</p>
                        <p>Fecha de creacion: ${curso.fechaCreacion}</p>
                        <p>Duracion: ${curso.horasDuracion} Horas</p>
                        <p>$${curso.precio.toLocaleString('es-CL')}</p>
                        <div class="stock-info ${stockClass}">
                            <p>Stock: ${stock.cantidadDisponible} unidades</p>
                            <p>Estado: ${stock.estado}</p>
                        </div>
                    </div>
                    <button 
                        onclick="agregarAlCarrito(${curso.id})" 
                        class="btn btn-primary"
                        ${stock.estado === 'AGOTADO' ? 'disabled' : ''}>
                        ${stock.estado === 'AGOTADO' ? 'Agotado' : 'Agregar al carrito'}
                    </button>
                </div>`;
        }
        cursoContainer.innerHTML = html;
    } catch (error) {
        alert("Error al obtener los cursos");
    }
};

mostrarCursos();

window.agregarAlCarrito = async (id) => {
    try {
        const stock = await obtenerStock(id);
        if (stock && stock.cantidadDisponible > 0) {
            await fetch(`${API_CARRITO}/agregar/${id}`, {method: 'POST'});
            await fetch(`${API_STOCK}/${stock.id}/cantidad/${stock.cantidadDisponible - 1}`, {method: 'PUT'});
            alert("Curso agregado al carrito");
            mostrarCursos();
        } else {
            alert("No hay stock disponible para este curso");
        }
    } catch {
        alert("Error al agregar al carrito");
    }
};