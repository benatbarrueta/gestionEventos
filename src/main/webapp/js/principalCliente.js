window.onload = function () {
    listarEntradas();
    listarEventos();
}

const botonVerMasEventos = document.getElementById("botonEventos");
const botonVerMasEntradas = document.getElementById("botonEntradas");

botonVerMasEventos.addEventListener('click', redirectionEventos);
botonVerMasEntradas.addEventListener('click', redirectionEntradas);

function redirectionEventos() {
    window.location.href = "../html/evento/tablaEventoCliente.html";
}

function redirectionInfoEvento(id){
    window.location.href = "../html/evento/infoEventoCliente.html?id=" + id;

}

function redirectionEntradas() {
    window.location.href = "../html/entrada/tablaEntradaCliente.html";
}

function redirectionCompra(id) {
    window.location.href = "../html/entrada/compraEntrada.html?id=" + id;
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
}

let eliminarEntrada = async (id) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarEntrada/" + id,
        {
            method: "DELETE",
            headers: {
                "Acept": "application/json",
                "Content-Type": "application/json"
            }
        });

    listarEntradas();
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
                        <span onclick = "redirectionCompra(${evento.id})" id = "compra" class="material-symbols-outlined button shop">shopping_cart</span>
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
