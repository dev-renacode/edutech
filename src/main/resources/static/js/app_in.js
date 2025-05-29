const nombre = sessionStorage.getItem("nombreUsuario");
const usuarioAutenticado = sessionStorage.getItem("usuarioAutenticado");

const actualizarUI = () => {
    const mensaje = document.getElementById("mensaje");
    const authElements = document.querySelectorAll('.auth-required');
    const noAuthElements = document.querySelectorAll('.no-auth');

    if(usuarioAutenticado) {
        mensaje.textContent = `Bienvenid@, ${nombre}`;
        authElements.forEach(el => el.style.display = 'block');
        noAuthElements.forEach(el => el.style.display = 'none');
    } else {
        mensaje.textContent = "";
        authElements.forEach(el => el.style.display = 'none');
        noAuthElements.forEach(el => el.style.display = 'block');
        
        if (!window.location.href.includes('login.html')) {
            window.location.href = "login.html";
        }
    }
}

const cerrarSesion = () => {
    sessionStorage.clear();
    window.location.href = "login.html";
}

actualizarUI();