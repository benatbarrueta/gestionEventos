const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('idEvento');
const userId = urlParams.get('idUsuario');

let boton = document.getElementById("btnNewResenya");

function redirectionResenya() {
    window.location.href = "../../html/resenya/tablaResenyaEvento.html?idEvento=" + eventId + "&idUsuario=" + userId;
}

boton = addEventListener("click", async function () {
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

    campos.texto = document.getElementById("texto").value;
    campos.puntuacion = document.getElementById("puntuacion").value;
    campos.usuario = userId;
    campos.evento = eventId;

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