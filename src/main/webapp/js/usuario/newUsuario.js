// Obtener referencia al botón


const botonNewUsuario = document.getElementById('botonNewUsuario');

// Agregar un evento de clic al botón
function redirection() {
    // Redirigir a otro HTML
    location.href = "../../inicio.html";
}

botonNewUsuario.addEventListener('click', async function () {
    newUsuario();
});

let newUsuario = async () => {
    let campos = {};
    campos.nombre = document.getElementById("nombre").value;
    campos.apellido = document.getElementById("apellidos").value;
    campos.nombreUsuario = document.getElementById("nombreUsuario").value;
    campos.contrasenya = document.getElementById("pass").value;
    campos.email = document.getElementById("email").value;
    campos.direccion = document.getElementById("direccion").value;
    campos.telefono = document.getElementById("telefono").value;
    campos.fechaNacimiento = document.getElementById("fechaNacimiento").value;
    campos.dni = document.getElementById("dni").value;

    const peticion = await fetch("http://localhost:8080/rest/resource/register",
        {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(campos)
        });
}


