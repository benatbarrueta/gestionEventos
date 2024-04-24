window.onload = function () {
    cargarDatosUsuario();
    redireccionPrincipalAdministrador();
    
}
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('dni');

const botonEditUsuario = document.getElementById('botonEditUsuario');

function redireccionPrincipalAdministrador(){
    location.href = "../../html/principalAdministrador.html?id=" + userId;

}


let cargarDatosUsuario = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getUsuarioId/" + userId,
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });


    const user = await peticion.json();
    alert(user.tipoUsuario);
    document.getElementById("nombre").value = user.nombre;
    document.getElementById("apellidos").value = user.apellidos;
    document.getElementById("email").value = user.email;
    document.getElementById("telefono").value = user.telefono;
    document.getElementById("dni").value = user.dni;

    
}

let updateUsuario = async () => {
    rol = document.getElementById("opciones").value;

    console.log(rol);

    const peticion = await fetch("http://localhost:8080/rest/resource/actualizarRolUsuario/" + userId + "/" + rol,
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        
    });

    return peticion.status;
}

botonEditUsuario.addEventListener('click', async function () {
    try {
        const status = await updateUsuario();

        if (status === 200) {
            redireccionPrincipalAdministrador();
        } else {
            alert("Error al actualizar el usuario, intentelo de nuevo.");
        }
    } catch (error) {
        alert("Error al actualizar el usuario ", error);
    }
});