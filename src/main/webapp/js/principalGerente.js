window.onload = function() {
    listarUsuarios();
    listarEventos();
    listarEntradas();
}

let listarUsuarios = async () => {
    const peticion = await fetch("http://localhost:8080/rest/resource/getUsuarios",
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const usuarios = await peticion.json();

    let contenidoTabla = "";

    if(usuarios.length > 0) {
        for(let usuario of usuarios) {
            let contenidoFila = 
            `<tr>
                <td>${usuario.dni}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.apellidos}</td>
                <td>${usuario.email}</td>
                <td>${usuario.telefono}</td>
                <td>${rol}</td>
                <td>
                    <i onClick="redirectionEditarRolUsuario(${usuario.dni})"class="material-icons button edit">edit</i>
                    <i onClick="eliminarUsuario(${usuario.dni})"class="material-icons button delete">delete</i>
                </td>
            <tr>`
    
            contenidoTabla += contenidoFila;
        }
    } else {
        contenidoTabla += '<tr><td colspan="7">No hay usuarios registrados</td></tr>'
    }

    document.querySelector("#tabla tbody").outerHTML = contenidoTabla; 
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
        contenidoTabla += '<tr><td colspan="5">No hay eventos registrados</td></tr>'
    }

    document.querySelector("#tablaEventos tbody").outerHTML = contenidoTabla;
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
    } else {
        contenidoTabla += '<tr><td colspan="5">No hay entradas registradas</td></tr>'
    }
    
    document.querySelector("#tablaEntrada tbody").outerHTML = contenidoTabla;
}