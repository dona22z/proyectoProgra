async function editarEstudiante(id) {
    const nuevoCorreo = prompt("Ingrese el nuevo correo:");
    const nuevoNumero = prompt("Ingrese el nuevo número (10 dígitos):");
    const nuevoIdioma = prompt("Ingrese el nuevo idioma (Español, Inglés, Francés):");

    if (!nuevoCorreo || !nuevoNumero || !nuevoIdioma) {
        alert("Todos los campos son obligatorios.");
        return;
    }

    if (!/^\d{10}$/.test(nuevoNumero)) {
        alert("El número debe tener exactamente 10 dígitos.");
        return;
    }

    const datos = {
        id: id,
        correo: nuevoCorreo.trim(),
        numero: nuevoNumero.trim(),
        idioma: nuevoIdioma.trim()
    };

    const request = await fetch('students' + id, {
        method: 'PATCH',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    if (request.ok) {
        alert("Estudiante actualizado.");
        location.reload();
    } else {
        alert("Error al actualizar el estudiante.");
    }
}
$(document).ready(function () {
    cargarEstudiantes();
});

async function cargarEstudiantes() {
    const request = await fetch('students' , {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const estudiantes = await request.json();
    console.log(estudiantes);

    let listadoHtml = '';
    for (let estudiante of estudiantes) {

        let botonEditar = `<button onclick="editarEstudiante(${estudiante.id})">Editar</button>`;

        let estudianteHtml = `
            <tr>
                <td>${estudiante.id}</td>
                <td>${estudiante.nombre}</td>
                <td>${estudiante.correo}</td>
                <td>${estudiante.numero}</td>
                <td>${estudiante.idioma}</td>
                <td>${botonEliminar} ${botonEditar}</td>
            </tr>
        `;

        listadoHtml += estudianteHtml;
    }

    document.querySelector('#estudiantes tbody').innerHTML = listadoHtml;

    // Inicializar DataTable después de llenar la tabla
    $('#estudiantes').DataTable();
}
