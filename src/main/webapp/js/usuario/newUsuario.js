// Obtener referencia al botón
const botonRegistro = document.getElementById('btnRegistro');
const botonLogin = document.getElementById('btnLogin');
const botonNewUsuario = document.getElementById('botonNewUsuario');

// Agregar un evento de clic al botón
function redirection() {
    // Redirigir a otro HTML
    location.href = "../../html/usuario/newUsuario.html";
}

//Realizar función de login
botonLogin.addEventListener('click', async function () {
    try {
        const result = await login();

        alert(result);
    } catch (error) {
        alert(error);
    }

});



let login = async () => {
    let campos = {}

    campos.nombreUsuario = document.getElementById("nombreusuario").value;
    campos.contrasenya = document.getElementById("password").value;

    const peticion = await fetch("http://localhost:8080/rest/resource/login",
        {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(campos)
        });

        return true;
        /*if (peticion.status === 200) {
            return true;
        } else {
            return false;
        }*/
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
    campos.rol
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


