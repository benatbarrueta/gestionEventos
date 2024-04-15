// Get the ID from the URL parameter
const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('id');

// Call the getEvento function when the window is loaded
window.addEventListener('load', () => {
    console.log("HOLA");
    getEvento(eventId)
        .then(data => {
            const evento = data;
            // Use the eventId in your code
            // For example, display the ID on the page

            document.getElementById("titulo").innerText = "Información del evento " + evento.nombre;
            document.getElementById("nombre").innerText = "Nombre: ";
            document.getElementById("lugar").innerText = "Lugar: ";
            document.getElementById("fecha").innerText = "Fecha: ";
            document.getElementById("descripcion").innerText = "Descripción: ";
            document.getElementById("aforo").innerText = "Aforo: ";
            document.getElementById("organizador").innerText = "Organizador: ";
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

let getEvento = async (id) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getEventoId/" + id,
        {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

    return await peticion.json();
}
