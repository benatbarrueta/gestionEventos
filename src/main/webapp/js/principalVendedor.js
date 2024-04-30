window.onload = function(){
    listarEntradas();
    listarEventos();
}

const botonAnyadir = document.getElementById("botonAnyadir");
const verMasEventos = document.getElementById("botonEventos");
const verMasEntradas = document.getElementById("botonEntradas");

botonAnyadir.addEventListener('click', redirectionAnyadirEvento);

verMasEventos.addEventListener('click', redirectionTablaEventos);

verMasEntradas.addEventListener('click', redirectionTablaEntradas);

function redirectionAnyadirEvento() {
    window.location.href = "../html/evento/newEvento.html";
}

function redirectionEditarEventos(id) {
    window.location.href = "../html/evento/editarEvento.html?id=" + id;
}

function redirectionTablaEventos() {
    window.location.href = "../html/evento/tablaEvento.html";
}

function redirectionTablaEntradas() {
    window.location.href = "../html/entrada/tablaEntrada.html";
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
                            <span onClick="redirectionEditarEventos(${evento.id})" class="material-symbols-outlined button description">description</span>
                            <span onClick="eliminarEvento(${evento.id})" class="material-symbols-outlined button delete">delete</span>
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

let listarEntradas = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getEntradas",
        {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

    const entradas = await peticion.json();

    let contenidoTabla = "";

    if (entradas.length > 0) {
        for (let entrada of entradas) {
            let contenidoFila =
                `<tr>
                <td>${entrada.id}</td>
                <td>${entrada.evento.nombre}</td>
                <td>${entrada.precio} €</td>
                <td>${entrada.sector}</td>
                <td>
                    <span onClick="eliminarEntrada(${entrada.id})" class="material-symbols-outlined button delete">delete</span>
                </td>
            <tr>`
    
            contenidoTabla += contenidoFila;
        }
        document.querySelector("#tablaEntrada tbody").outerHTML = contenidoTabla;
    } else {
        let contenidoFila = '<tr><td colspan="5">No hay entradas registradas</td></tr>'

        contenidoTabla += contenidoFila;
    }
}