window.onload = function() {
    listarEventos();
}

const principalCliente = document.getElementById("paginaPrincipal");

principalCliente.addEventListener('click', redirectionPrincipalCliente);

function redirectionPrincipalCliente(){
    principalCliente.href = "../../html/principalCliente.html";
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
                <td>${evento.hora}</td>
                <td>${evento.lugar}</td>
                <td>
                    <i class="material-icons button edit">edit</i>
                    <i onClick="eliminarEvento(${evento.id})" class="material-icons button delete">delete</i>
                </td>
            </tr>`

            contenidoTabla += contenidoFila;
        }
    } else {
        contenidoTabla = "<tr><td colspan='6'>No hay eventos</td></tr>";
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