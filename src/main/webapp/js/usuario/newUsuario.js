// Obtener referencia al botón
const botonRegistro = document.getElementById('btnRegistro');
const botonLogin = document.getElementById('btnLogin');

// Agregar un evento de clic al botón
botonRegistro.addEventListener('click', function () {
    // Redirigir a otro HTML
    var ruta = "newUsuario.html";
    window.location.href = ruta;
});

//Realizar función de login
botonLogin.addEventListener('click', async function () {
    /*if(login() === true) {
        alert("Login correcto");
    } else {
        alert("Login incorrecto");
    }*/
    alert(login());
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

    const string = await peticion.json();

    let contenido1 = "";
    for(let contenido in string) {
        contenido1 += contenido;
    }
    return contenido1;
}