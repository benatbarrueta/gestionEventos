const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('idEvento');
const userId = urlParams.get('idUsuario');

let botonCrearResenya = document.getElementById("btnCrearResenya");

botonCrearResenya.addEventListener("click", redirectionResenya);

function redirectionResenya() {
    window.location.href = "../../html/resenya/crearResenya.html?idEvento=" + eventId + "&idUsuario=" + userId;
}