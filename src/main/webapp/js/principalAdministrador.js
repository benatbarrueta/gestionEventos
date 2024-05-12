window.onload = function() {
    listarUsuarios();
}


redirectionEditarRolUsuario = (dni) => {
    window.location.href = "../html/usuario/editarUsuarioAdministrador.html?id=" + dni;
}


let listarUsuarios = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getUsuarios",
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const usuarios = await peticion.json();

    let contenidoTabla = "";

    if(usuarios.length > 0) {
        for(let usuario of usuarios) {
            let contenidoFila = 
            `<tr>
                <td>${usuario.dni}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.apellidos}</td>
                <td>${usuario.email}</td>
                <td>${usuario.telefono}</td>
                <td>${usuario.rol}</td>
                <td>
                    <i onClick="redirectionEditarRolUsuario(${usuario.dni})"class="material-icons button edit">edit</i>
                    <i onClick="eliminarUsuario(${usuario.dni})"class="material-icons button delete">delete</i>
                </td>
            <tr>`
    
            contenidoTabla += contenidoFila;
        }
    } else {
        contenidoTabla += '<tr><td colspan="7">No hay usuarios registrados</td></tr>'
    }

    document.querySelector("#tabla tbody").outerHTML = contenidoTabla; 
}

let eliminarUsuario = async (dni) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarCuenta/" + dni,
    {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const respuesta = await peticion.json();

    if(respuesta) {
        listarUsuarios();
    } else {
        alert("No se ha podido eliminar el usuario");
    }
}

