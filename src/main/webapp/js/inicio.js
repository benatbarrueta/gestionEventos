// Obtener referencia al botón
const botonRegistro = document.getElementById('btnRegistro');
const botonLogin = document.getElementById('btnLogin');

botonRegistro.addEventListener('click', redirectionRegistro);

// Agregar un evento de clic al botón
function redirectionRegistro() {
    // Redirigir a otro HTML
    location.href = "../html/usuario/newUsuario.html";
}

function redirectionCliente(id){
    location.href = "../html/principalCliente.html" + "?id=" + id;
}

function redirectionAdministrador(id){
    location.href = "../html/principalAdministrador.html" + "?id=" + id;
}

function redirectionGerente(id){
    location.href = "../html/principalGerente.html" + "?id=" + id;
}

function redirectionVendedor(){
    location.href = "../html/principalVendedor.html";
}

function redirectionNoCliente(id){
    location.href = "../html/principalTrabajador.html" + "?id=" + id;

}

//Realizar función de login
botonLogin.addEventListener('click', async function () {
    try {
        const user = await login();
        if(user != null && user.rol == "CLIENTE"){
            redirectionCliente(user.dni);
        } else if(user != null && user.rol == "ADMINISTRADOR"){
            redirectionAdministrador(user.dni);
        } else if(user != null && user.rol == "GERENTE"){
            redirectionGerente(user.dni);
        } else if(user != null && user.rol == "VENDEDOR"){
            redirectionVendedor();
        } else if (user != null && user.rol != "CLIENTE" && user.rol != "ADMINISTRADOR" && user.rol != "GERENTE" && user.rol != "VENDEDOR"){
            redirectionNoCliente(user.dni);
        } else {
            // alert("Usuario o contraseña incorrectos");
            console.log(result)
            document.getElementById("nombreusuario").value = "";
            document.getElementById("password").value = "";
        }
    } catch (error) {
        console.log("Error en el incio de sesión: " + error);
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

        const user = await response.json();


        
        if (response.ok) {
            return user;
        } else {
            // La solicitud falló, devolvemos false o un mensaje de error
            const responseData = await response.json();
            return null;
        }
    } catch (error) {
        // Capturamos y manejamos cualquier error de la solicitud
        console.log("Error en el inicio de sesión: " + error);
    }
};

