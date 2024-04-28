// Get the ID from the URL parameter
const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('id');

const editarEvento = document.getElementById("botonEditarEvento");

const fecha = "";

window.onload = function () {
    cargarEvento();
}

editarEvento.addEventListener('click', async function () {
    try {
        const status = await actualizarEvento();

        if (status === 200) {
            redirectionPrincipalVendedor();
        } else {
            alert("Error actualizando el evento, intentelo de nuevo.");
        }
    } catch (error) {
        alert("Error al actualizar el evento ", error);
    }
});

function redirectionPrincipalVendedor() {
    window.location.href = "../../html/principalVendedor.html";
}

function cargarEvento() {
    getEvento(eventId)
        .then(data => {
            const evento = data;
            // Use the eventId in your code
            // For example, display the ID on the page
            document.getElementById("nombre").value = evento.nombre;
            document.getElementById("lugar").value = evento.lugar;
            document.getElementById("fecha").value = formatDate(evento.fecha, "es-ES");
            document.getElementById("descripcion").value = evento.descripcion;
            document.getElementById("aforo").value = evento.aforoTotal;
            document.getElementById("organizador").value = evento.organizador;
            document.getElementById("organizador").value = evento.organizador;

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

    return await peticion.json();
}

let actualizarEvento = async () => {
    let campos = {};
    campos.id = eventId;
    campos.nombre = document.getElementById("nombre").value;
    campos.lugar = document.getElementById("lugar").value;
    campos.aforo = document.getElementById("aforo").value;
    campos.aforoTotal = document.getElementById("aforo").value;
    campos.descripcion = document.getElementById("descripcion").value;
    campos.organizador = document.getElementById("organizador").value;

    const peticion = await fetch("http://localhost:8080/rest/resource/actualizarEvento",
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