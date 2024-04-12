// Obtener referencia al botón
const botonRegistro = document.getElementById('btnRegistro');
const botonLogin = document.getElementById('btnLogin');

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

