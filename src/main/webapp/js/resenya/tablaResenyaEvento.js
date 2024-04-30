window.onload = function () {
    listarResenyas();
}

const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('idEvento');
const userId = urlParams.get('idUsuario');

let botonCrearResenya = document.getElementById("btnCrearResenya");

botonCrearResenya.addEventListener("click", redirectionResenya);

function redirectionResenya() {
    window.location.href = "../../html/resenya/crearResenya.html?idEvento=" + eventId + "&idUsuario=" + userId;
}

let listarResenyas = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getReseñasEvento/" + eventId,
        {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });
alert(eventId)
    const resenyas = await peticion.json();

    let contenidoTabla = "";

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

