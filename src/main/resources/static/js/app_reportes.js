const enviarReporte = document.getElementById('enviarReporte');
const reporte = document.getElementById('report');
const titulo = document.getElementById('title');

enviarReporte.addEventListener('click', (e) => {
    e.preventDefault();

    fetch('http://localhost:8080/api/v1/notificaciones/guardar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            titulo: titulo.value,
            mensaje: descripcion.value
        })
    })
    .then(response => response.json())
    .then(data => {
        alert('Notificación guardada:', data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
});