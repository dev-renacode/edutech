const nombre = sessionStorage.getItem("nombreUsuario");
console.log(nombre)
if(nombre){
    document.getElementById("mensaje").textContent = `Bienvenid@, ${nombre}`
}

const cerrarSesion = ( ) => {
    sessionStorage.clear();
    window.location.href = "login.html";
}