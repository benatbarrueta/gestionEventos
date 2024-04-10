
let boton = document.getElementById("btnNewEvento");

boton.addEventListener("click", evento => {
    newEvento();
});

let newEvento = async () => {
    let campos = {};
    
    campos.nombre = document.getElementById("nombre").value;
    campos.lugar = document.getElementById("lugar").value;
    campos.fecha = document.getElementById("fecha").value;
    campos.aforo = 0;
    campos.aforoTotal = document.getElementById("aforo").value;
    campos.descripcion = document.getElementById("descripcion").value;
    campos.organizador = document.getElementById("organizador").value;
    
    alert(campos.nombre)
    const peticion = await fetch("http://localhost:8080/rest/resource/crearEvento",
        {
            
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(campos)
        });
}