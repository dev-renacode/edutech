const cursoContainer = document.querySelector(".container");

fetch("http://localhost:8080/api/v1/cursos")
.then(response => response.json())
.then(data => {
    data.forEach(curso => {
        cursoContainer.innerHTML += `
            <div class="curso-container">
                <div class="curso">
                    <h2>${curso.nombreCurso}</h2>
                    <p>${curso.descripcion}</p>
                </div>
            </div> 
        `
    })
})
.catch(error => {
    console.error("Error al obtener los cursos:", error);
});
//TESTEO