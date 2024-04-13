// Obtener referencia al botón
const botonRegistro = document.getElementById('btnRegistro');
const botonLogin = document.getElementById('btnLogin');

botonRegistro.addEventListener('click', redirectionRegistro);

// Agregar un evento de clic al botón
function redirectionRegistro() {
    // Redirigir a otro HTML
    location.href = "../html/usuario/newUsuario.html";
}

function redirectionLoginSuccesful(){
    location.href = "../html/principalCliente.html";
}

//Realizar función de login
botonLogin.addEventListener('click', async function () {
    try {

        const result = await login();
        redirectionLoginSuccesful();
    } catch (error) {
        console.log("Error en el inicio de sesión: " + error);
    }
    
});

let login = async () => {
    let campos = {
        nombreUsuario: document.getElementById("nombreusuario").value,
        contrasenya: document.getElementById("password").value
    };

    try {
        const response = await fetch("http://localhost:8080/rest/resource/login", {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(campos)
        });

        if (response.ok) {
            // La solicitud fue exitosa, devolvemos true
            return true;
        } else {
            // La solicitud falló, devolvemos false o un mensaje de error
            const responseData = await response.json();
            throw new Error(responseData.message || "Error en el inicio de sesión");
        }
    } catch (error) {
        // Capturamos y manejamos cualquier error de la solicitud
        console.log("Error en el inicio de sesión: " + error);
    }
};

