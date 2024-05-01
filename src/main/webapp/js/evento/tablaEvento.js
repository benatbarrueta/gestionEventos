window.onload = function() {
    listarEventos();
}

const paginaPrincipal = document.getElementById("paginaPrincipal");

paginaPrincipal.addEventListener('click', redirectionPaginaPrincipal);

function redirectionPaginaPrincipal() {
    paginaPrincipal.href = "../../html/principalVendedor.html";
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

    if(eventos.length > 0) {
        for(let evento of eventos) {
            let contenidoFila = 
            `<tr>
                <td>${evento.id}</td>
                <td>${evento.nombre}</td>
                <td>${evento.fecha}</td>
                <td>${evento.lugar}</td>
                <td>
                    <i class="material-icons button edit">edit</i>
                    <i onClick="eliminarEvento(${evento.id})"class="material-icons button delete">delete</i>
                </td>
            <tr>`
    
            contenidoTabla += contenidoFila;
        }
    } else {
        contenidoTabla += 
        `<tr>
            <td colspan="5">No hay eventos registrados</td>
        </tr>`
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