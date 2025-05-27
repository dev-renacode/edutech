const nombre = sessionStorage.getItem("nombreUsuario");

// Función para actualizar la UI según el estado de la sesión
const actualizarUI = () => {
    const mensaje = document.getElementById("mensaje");
    const authElements = document.querySelectorAll('.auth-required');
    const noAuthElements = document.querySelectorAll('.no-auth');

    if(nombre) {
        mensaje.textContent = `Bienvenid@, ${nombre}`;
        // Mostrar elementos que requieren autenticación
        authElements.forEach(el => el.style.display = 'block');
        // Ocultar elementos para usuarios no autenticados
        noAuthElements.forEach(el => el.style.display = 'none');
    } else {
        mensaje.textContent = "";
        // Ocultar elementos que requieren autenticación
        authElements.forEach(el => el.style.display = 'none');
        // Mostrar elementos para usuarios no autenticados
        noAuthElements.forEach(el => el.style.display = 'block');
        
        // Redirigir a login si no hay sesión
        if (!window.location.href.includes('login.html')) {
            window.location.href = "login.html";
        }
    }
}

// Función para cerrar sesión
const cerrarSesion = () => {
    sessionStorage.clear();
    window.location.href = "login.html";
}

// Inicializar la UI
actualizarUI();