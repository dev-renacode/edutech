fetch("http://localhost:8080/api/v1/cursos")
.then(response => response.json())
.then(data => {
    console.log(data);
})
.catch(error => {
    console.error("Error al obtener los cursos:", error);
});
//TESTEO XD