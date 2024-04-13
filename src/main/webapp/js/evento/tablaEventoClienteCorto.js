window.onload = function () {
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

    let contador = 0;

    for (let evento of eventos) {
        if(contador < 10){
            let contenidoFila =
            `<tr>
            <td>${evento.id}</td>
            <td>${evento.nombre}</td>
            <td>${evento.fecha}</td>
            <td>${evento.lugar}</td>
            <td>
                <i class="material-icons button edit">edit</i>
            </td>
        <tr>`
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