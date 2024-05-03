const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('idEvento');
const userId = urlParams.get('idUsuario');

const principal = document.getElementById("paginaPrincipal");

var user = null;
var evento = null;

principal.addEventListener("click", redirectionPrincipal);

function redirectionPrincipal(){
    principal.href = "../../html/principalCliente.html?idUsuario=" + userId;
}

window.onload = async function () {
    try {
        //user = await getUsuario();
        evento = await getEvento();
    } catch (error) {
        alert("Error al cargar el usuario ", error);
    }

}

let botonNewResenya = document.getElementById("btnNewResenya");

function redirectionResenya() {
    window.location.href = "../../html/resenya/tablaResenyaEvento.html?idEvento=" + eventId + "&idUsuario=" + userId;
}

botonNewResenya.addEventListener("click", async function () {
    try {
        const status = await newResenya();

        if (status === 200) {
            redirectionResenya();
        } else {
            alert("Error creando la reseña, intentelo de nuevo.");
        }
    } catch (error) {
        alert("Error al crear la reseña ", error);
    }
});

let newResenya = async () => {
    let campos = {};

    campos.comentario = document.getElementById("comentario").value;
    campos.puntuacion = document.getElementById("puntuacion").value;
    campos.evento = evento;
    campos.usuario = user;

    const peticion = await fetch("http://localhost:8080/rest/resource/crearResenya",
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

let getUsuario = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getUsuarioId/" + userId,
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const usuario = await peticion.json();
    return usuario;
}

let getEvento = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getEventoId/" + eventId,
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const evento = await peticion.json();
    return evento;
}