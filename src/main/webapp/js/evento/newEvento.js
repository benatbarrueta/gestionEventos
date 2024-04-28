
let boton = document.getElementById("btnNewEvento");

function redirectionPrincipalVendedor() {
    window.location.href = "../../html/principalVendedor.html";
}

boton.addEventListener("click", async function () {
    try {
        const status = await newEvento();

        if (status === 200) {
            redirectionPrincipalVendedor();
        } else {
            alert("Error creando el usuario, intentelo de nuevo.");
        }
    } catch (error) {
        alert("Error al crear el usuario ", error);
    }
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

    const peticion = await fetch("http://localhost:8080/rest/resource/crearEvento",
    {

        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(campos)
    });

    return peticion.status;
}