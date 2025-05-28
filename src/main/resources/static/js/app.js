const cursoContainer = document.getElementById("container-cursos");

const API = "http://localhost:8080/api/v1/cursos"
const API_CARRITO = "http://localhost:8080/api/v1/carrito"

fetch(API)
.then(response => response.json())
.then(data => {
    data.forEach(curso => {
        console.log(curso)
        const {id, nombreCurso, fechaCreacion, docenteCurso, descripcion, horasDuracion, precio} = curso;
        cursoContainer.innerHTML += `
            <div class="curso-container">
                <div class="curso">
                    <h2>${nombreCurso}</h2>
                    <p>${descripcion}</p>
                    <p>Docente: ${docenteCurso}</p>
                    <p>Fecha de creacion: ${fechaCreacion}</p>
                    <p>Duracion: ${horasDuracion} Horas</p>
                    <p>$${precio}</p>
                </div>
                <button onclick="agregarAlCarrito(${id})" class="btn btn-primary">Agregar al carrito</button>
            </div> 
        `
    })
})
.catch(error => {
    console.error("Error al obtener los cursos:", error);
});

async function agregarAlCarrito(id) {
    try {
        await fetch(`${API_CARRITO}/agregar/${id}`, {method: 'POST'});
        alert("Curso agregado al carrito");
    } catch (error) {
        console.error("Error al agregar al carrito:", error);
    }
}