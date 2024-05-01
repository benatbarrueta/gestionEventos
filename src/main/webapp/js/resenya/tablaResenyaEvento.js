window.onload = function () {
    listarResenyas();
}

const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('idEvento');
const userId = urlParams.get('idUsuario');

let botonCrearResenya = document.getElementById("btnCrearResenya");
let botonPrincipalUsuario = document.getElementById("btnVolverPrincipalUsuario");

botonCrearResenya.addEventListener("click", redirectionResenya);
botonPrincipalUsuario.addEventListener("click", redirectionPrincipalUsuario);

function redirectionResenya() {
    window.location.href = "../../html/resenya/crearResenya.html?idEvento=" + eventId + "&idUsuario=" + userId;
}

function redirectionPrincipalUsuario() {  
    window.location.href = "../../html/principalCliente.html?idUsuario=" + userId;
}

let listarResenyas = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getResenyasEvento/" + eventId,
        {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

    const resenyas = await peticion.json();

    let contenidoTabla = "";
    alert(resenyas.length)
    for (let resenya of resenyas) {
        let contenidoFila =
            `<tr>
            <td>${resenya.id}</td>
            <td>${resenya.usuario.nombre}</td>
            <td>${resenya.evento.nombre}</td>
            <td>${resenya.comentario}</td>
            <td>${resenya.puntuacion}</td>
            
        <tr>`

        contenidoTabla += contenidoFila;
    }

    document.querySelector("#tabla tbody").outerHTML = contenidoTabla;
}



