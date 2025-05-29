const enviarReporte = document.getElementById('enviarReporte');
const reporte = document.getElementById('report');
const titulo = document.getElementById('title');
const nombreUsuario = sessionStorage.getItem('nombreUsuario');

enviarReporte.addEventListener('click', (e) => {
    e.preventDefault();
    
    //fecha con formato dd/mm/yyyy
    const fecha = new Date().toLocaleDateString('es-ES');
    fetch('http://localhost:8080/api/v1/reportes/guardar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            titulo: titulo.value,
            mensaje: descripcion.value,
            usuario: nombreUsuario,
            fecha: fecha
        })
    })
    .then(response => response.json())
    .then(data => {
        alert('Reporte guardado:', data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
});