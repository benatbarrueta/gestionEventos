window.onload = function () {
    listarEntradas();
    listarEventos();
    redirectionModificarUsuario();
}

const botonVerMasEventos = document.getElementById("botonEventos");
const botonVerMasEntradas = document.getElementById("botonEntradas");
const editarUsuario = document.getElementById("modificarUsuario");

botonVerMasEventos.addEventListener('click', redirectionEventos);
botonVerMasEntradas.addEventListener('click', redirectionEntradas);

const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('id');

function redirectionModificarUsuario(){
    editarUsuario.href = "usuario/editUser.html?id=" + userId;
}

function redirectionEventos() {
    window.location.href = "../html/evento/tablaEventoCliente.html";
}

function redirectionInfoEvento(id){
    window.location.href = "../html/evento/infoEventoCliente.html?idEvento=" + id + "&idUsuario=" + userId;

}

function redirectionEntradas() {
    window.location.href = "../html/entrada/tablaEntradaCliente.html";
}

function redirectionCompra(id) {
    window.location.href = "../html/entrada/compraEntrada.html?idEvento=" + id + "&idUsuario=" + userId;
}

function redirectionResenya(id) {
    window.location.href = "../html/resenya/tablaResenyaEvento.html?idEvento=" + id + "&idUsuario=" + userId;
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

    if(entradas.length > 0) {
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
    } else {
        contenidoTabla += '<tr><td colspan="5">No hay entradas registradas</td></tr>'
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

    if(eventos.length > 0) {
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
                            <span onclick = "redirectionResenya(${evento.id})" id = "resenya" class="material-symbols-outlined button resenya" >add_circle</span>
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
        contenidoTabla += '<tr><td colspan="5">No hay eventos registrados</td></tr>'
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
