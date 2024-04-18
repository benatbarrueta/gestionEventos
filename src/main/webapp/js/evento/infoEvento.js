// Get the ID from the URL parameter
const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('id');

// Call the getEvento function when the window is loaded
window.addEventListener('load', () => {
    
});

function cargarEvento(){
    console.log("HOLA");
    getEvento(eventId)
        .then(data => {
            const evento = data;
            // Use the eventId in your code
            // For example, display the ID on the page
            document.getElementById("titulo").value = evento.nombre;
            document.getElementById("nombre").value = evento.nombre;
            document.getElementById("lugar").value = evento.lugar;
            document.getElementById("fecha").value = formatDate(evento.fecha, "es-ES");
            document.getElementById("descripcion").value = evento.descripcion;
            document.getElementById("aforo").value = evento.aforoTotal;
            document.getElementById("organizador").value =  evento.organizador;

            function formatDate(date, locale = "en-US") {
                const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', };
                return new Date(date).toLocaleDateString(locale, options);
            }

        })
        .catch(error => {
            console.error('Error:', error);
        }); 
}

let getEvento = async (id) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getEventoId/" + id,
        {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

    return await peticion.json();s
}
