window.onload = function () {
    cargarDatosUsuario();
    redirectionPrincipalCliente();
}

const principalCliente = document.getElementById("principalCliente");

const botonEditUsuario = document.getElementById('botonEditUsuario');
const botonEliminar = document.getElementById('botonDeleteUsuario');

const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('id');

var fechaNacimiento;


botonEditUsuario.addEventListener('click', async function () {
    try {
        const status = await deleteUsuario();
        
        if (status === 200) {
            const status2 = await newUsuario();
            if (status2 === 200) {
                //redirection();
                alert("Usuario editado con éxito.");
            } else {
                alert("Error al editar el usuario, intentelo de nuevo.");
            }
        }else {
            alert("Error editando el usuario, intentelo de nuevo mas tarde.");
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
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarCuenta/" + userId,
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
    campos.fechaNacimiento = fechaNacimiento;
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
    document.getElementById("nombre").value = user.nombre;
    document.getElementById("apellidos").value = user.apellidos;
    document.getElementById("nombreUsuario").value = user.nombreUsuario;
    document.getElementById("pass").value = user.contrasenya;
    document.getElementById("email").value = user.email;
    document.getElementById("direccion").value = user.direccion;
    document.getElementById("telefono").value = user.telefono;
    document.getElementById("fechaNacimiento").value = formatDate((user.fechaNacimiento), "es-ES");
    document.getElementById("dni").value = user.dni;

    fechaNacimiento = user.fechaNacimiento;

    function formatDate(date, locale = "en-US") {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(date).toLocaleDateString(locale, options);
    }
}

function redirectionPrincipalCliente(){
    
    principalCliente.href = "../../html/principalCliente.html?id=" + userId;
}