const botonEditUsuario = document.getElementById('botonEditUsuario');
const botonEliminar = document.getElementById('botonDeleteUsuario');

const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('id');


botonEditUsuario.addEventListener('click', async function () {
    try {
        const status = await deleteUsuario();
        const status2 = await newUsuario();

        if (status === 200 && status2 === 200) {
            //redirection();
            alert("Usuario editado con éxito.");
        }else {
            alert("Error editando el usuario, intentelo de nuevo.");
        }
    } catch (error) {
        alert("Error al editar el usuario ", error);
    }
});

botonEliminar.addEventListener('click', async function () {
    try {
        const status = await deleteUsuario();

        if (status === 200) {
            //redirection();
            alert("Usuario eliminado con éxito.");
        } else {
            alert("Error eliminando el usuario, intentelo de nuevo.");
        }
    } catch (error) {
        alert("Error al eliminar el usuario ", error);
    }
});

let deleteUsuario = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarCuenta/" + eventId,
    {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    return peticion.status;
}

let newUsuario = async () => {
    let campos = {};
    campos.nombre = document.getElementById("nombre").value;
    campos.apellidos = document.getElementById("apellidos").value;
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
    return peticion.status;
}

let editUsuario = async () => {
    
}