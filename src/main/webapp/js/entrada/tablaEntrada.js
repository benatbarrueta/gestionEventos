window.onload = function() {
    listarEntradas();
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

    for(let entrada of entradas) {
        let contenidoFila = 
        `<tr>
            <td>${entrada.id}</td>
            <td>${entrada.evento.nombre}</td>
            <td>${entrada.precio}</td>
            <td>${entrada.sector}</td>
            <td>
                <i class="material-icons button edit">edit</i>
                <i onClick="eliminarEntrada(${entrada.id})"class="material-icons button delete">delete</i>
            </td>
        <tr>`

        contenidoTabla += contenidoFila;
    }

    document.querySelector("#tablaEntrada tbody").outerHTML = contenidoTabla; 
}

let eliminarentrada = async (id ) => {
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