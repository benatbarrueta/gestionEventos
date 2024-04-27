window.onload = function(){
    listarEventos();
}

const botonAnyadirEvento = document.getElementById("botonAnyadir");

botonAnyadirEvento.addEventListener("click", redirectionNewEvento());

let redirectionInfoEvento = (id) => {
    window.location.href = "../html/evento/infoEvento.html?id=" + id;
}

let redirectionNewEvento = () => {
    window.location.href = "../html/evento/newEvento.html";
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

    let contador = 0;

    if (eventos.length > 0) {
        for (let evento of eventos) {
            if (contador < 10) {
                let contenidoFila =
                    `<tr>
                        <td>${evento.id}</td>
                        <td>${evento.nombre}</td>
                        <td>${formatDate(evento.fecha, "es-ES")}</td>
                        <td>${evento.lugar}</td>
                        <td>
                            <span onclick = "redirectionInfoEvento(${evento.id})" id = "description" class="material-symbols-outlined button description">description</span>
                            <i onClick="eliminarEvento(${evento.dni})"class="material-icons button delete">delete</i>
                        </td>
                    <tr>`

                function formatDate(date, locale = "en-US") {
                    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', };
                    return new Date(date).toLocaleDateString(locale, options);
                }
                contenidoTabla += contenidoFila;
                contador++;
            }
        }
    } else {
        let contenidoFila = '<tr><td colspan="5">No hay eventos registrados</td></tr>'

        contenidoTabla += contenidoFila;
    }
    
    document.querySelector("#tabla tbody").outerHTML = contenidoTabla;
}

let eliminarEvento = async (id) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarEvento/" + id,
        {
            method: "DELETE",
            headers: {
                "Acept": "application/json",
                "Content-Type": "application/json"
            }
        });

    listarEventos();
}