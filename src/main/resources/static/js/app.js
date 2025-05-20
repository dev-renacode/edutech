const cursoContainer = document.querySelector(".container");

fetch("http://localhost:8080/api/v1/cursos")
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
                <button>Agregar al carrito</button>
            </div> 
        `
    })
})
.catch(error => {
    console.error("Error al obtener los cursos:", error);
});
//TESTEO