let API_URL = "http://10.15.239.162:8080/api/v1/usuarios/login";
const form = document.getElementById("form");

const login = async() => {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !password) {
        alert("Por favor, complete todos los campos");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        
        if(data.result === "OK"){
            // Guardar datos del usuario
            sessionStorage.setItem("idUsuario", data.id);
            sessionStorage.setItem("nombreUsuario", data.nombre);
            sessionStorage.setItem("usuarioAutenticado", "true");
            
            // Redirigir al usuario
            window.location.href = "http://10.15.239.162:8080/index.html";
        } else {
            alert("Credenciales incorrectas. Por favor, intente nuevamente.");
        }
    } catch (error) {
        console.error("Error en el login:", error);
        alert("Error al intentar iniciar sesión. Por favor, intente nuevamente.");
    }
}

form.addEventListener("submit", (e) => {
    e.preventDefault();
    login();
});