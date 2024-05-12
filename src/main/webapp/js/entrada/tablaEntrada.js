window.onload = function() {
    listarEntradas();
}

const paginaPrincipal = document.getElementById("paginaPrincipal");
const principalCliente = document.getElementById("principalCliente");

paginaPrincipal.addEventListener('click', redirectionPaginaPrincipal);
principalCliente.addEventListener('click', redirectionPrincipalCliente);

function redirectionPaginaPrincipal() {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('id');

    paginaPrincipal.href = "../../html/principalVendedor.html?id=" + userId;
}

function redirectionPrincipalCliente() {
    principalCliente.href = "../../html/principalCliente.html";
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

    if(entradas.length > 0) {
        for(let entrada of entradas) {
            let contenidoFila = 
            `<tr>
                <td>${entrada.id}</td>
                <td>${entrada.evento.nombre}</td>
                <td>${entrada.precio}</td>
                <td>${entrada.sector}</td>
                <td>
                    <i class="material-icons button edit">edit</i>
                    <i onClick="eliminarEntrada(${entrada.id})" class="material-icons button delete">delete</i>
                </td>
            <tr>`
    
            contenidoTabla += contenidoFila;
        }
    } else{
        contenidoTabla = "<tr><td colspan='5'>No hay entradas</td></tr>";
    }

    document.querySelector("#tablaEntrada tbody").outerHTML = contenidoTabla;
}

let eliminarEntrada = async (id) => {
    const peticion = await fetch("http://localhost:8080/rest/resource/eliminarEntrada/"+id,
    {
        method: "DELETE",
        headers: {
            "Acept": "application/json",
            "Content-Type": "application/json"
        }
    });

    listarEntradas();
}