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
            <td>${usuario.id}</td>
            <td>${usuario.nombre}</td>
            <td>${usuario.nombreUsuario}</td>
            <td>${usuario.contrasenya}</td>
            <td>${usuario.email}</td>
            <td>${usuario.direccion}</td>
            <td>${usuario.telefono}</td>
            <td>${usuario.dni}</td>
            <td>${usuario.tipoUsuario}</td>
            <td>
                <i class="material-icons button edit">edit</i>
                <i onClick="eliminarUsuario(${usuario.id})"class="material-icons button delete">delete</i>
            </td>
        <tr>`

        contenidoTabla += contenidoFila;
    }

    document.querySelector("#tabla tbody").outerHTML = contenidoTabla; 
}