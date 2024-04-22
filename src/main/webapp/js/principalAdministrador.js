window.onload = function() {
    listarUsuarios();
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

    for(let usuario of usuarios) {
        
        let contenidoFila = 
        `<tr>
            <td>${usuario.dni}</td>
            <td>${usuario.nombre}</td>
            <td>${usuario.apellidos}</td>
            <td>${usuario.email}</td>
            <td>${usuario.telefono}</td>
            <td>${usuario.tipoUsuario}</td>
            <td>
                <i class="material-icons button edit">edit</i>
                <i onClick="eliminarUsuario(${usuario.dni})"class="material-icons button delete">delete</i>
            </td>
        <tr>`

        contenidoTabla += contenidoFila;
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

