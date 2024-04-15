window.onload = function() {
    listarEventos();
}

let listarEventos = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getEventos",
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const eventos = await peticion.json();

    let contenidoTabla = "";

    for(let evento of eventos) {
        let contenidoFila = 
        `<tr>
            <td>${evento.id}</td>
            <td>${evento.nombre}</td>
            <td>${formatDate(evento.fecha, "es-ES")}</td>
            <td>${evento.lugar}</td>
            <td>
                <i class="material-icons button edit">edit</i>
                <i onClick="eliminarEvento(${evento.id})"class="material-icons button delete">delete</i>
            </td>
        <tr>`
        function formatDate(date, locale = "en-US") {
            const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', };
            return new Date(date).toLocaleDateString(locale, options);
        }

        contenidoTabla += contenidoFila;
    }

    document.querySelector("#tabla tbody").outerHTML = contenidoTabla; 
}

let eliminarEvento = async (id ) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarEvento/"+id,
    {
        method: "DELETE",
        headers: {
            "Acept": "application/json",
            "Content-Type": "application/json"
        }
    });

    listarEventos();
}