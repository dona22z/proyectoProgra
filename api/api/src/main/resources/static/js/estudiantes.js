let estudiantesGlobal = [];

$(document).ready(function () {
    cargarEstudiantes();
});

async function cargarEstudiantes() {
    const request = await fetch('students', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const estudiantes = await request.json();
    estudiantesGlobal = estudiantes;

    let listadoHtml = '';
    for (let estudiante of estudiantes) {
        let botonEliminar = `<button onclick="eliminarEstudiante(${estudiante.id})">Eliminar</button>`;
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
    $('#estudiantes').DataTable();
}

async function eliminarEstudiante(id) {
    if (!confirm('¿Quieres eliminar este estudiante?')) return;

    const request = await fetch('students/' + id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    location.reload();
}

function editarEstudiante(id) {
    const estudiante = estudiantesGlobal.find(e => e.id === id);

    if (!estudiante) {
        alert("Estudiante no encontrado.");
        return;
    }

    document.getElementById("editar-id").value = estudiante.id;
    document.getElementById("editar-correo").value = estudiante.correo;
    document.getElementById("editar-numero").value = estudiante.numero;
    document.getElementById("editar-idioma").value = estudiante.idioma;

    // Llenar segundo modal también
    document.getElementById("inputFullName").value = estudiante.nombre;
    document.getElementById("inputFullEmail").value = estudiante.correo;
    document.getElementById("inputFullNumber").value = estudiante.numero;
    document.getElementById("inputFullLanguage").value = estudiante.idioma;

    // Mostrar modal principal
    document.getElementById("modal-editar").style.display = "block";
}

function cerrarModal() {
    document.getElementById("modal-editar").style.display = "none";
}

// Guardar edición parcial o completa desde el primer modal
async function guardarCambios(completo = false) {
    const id = document.getElementById("editar-id").value;
    const correo = document.getElementById("editar-correo").value.trim();
    const numero = document.getElementById("editar-numero").value.trim();
    const idioma = document.getElementById("editar-idioma").value.trim();

    if (completo && (!correo || !numero || !idioma)) {
        alert("Todos los campos son obligatorios para edición completa.");
        return;
    }

    if (numero && !/^\d{10}$/.test(numero)) {
        alert("El número debe tener exactamente 10 dígitos.");
        return;
    }

    if (correo && !/\S+@\S+\.\S+/.test(correo)) {
        alert('Correo electrónico no válido.');
        return;
    }

    let datos = {};
    if (correo) datos.correo = correo;
    if (numero) datos.numero = numero;
    if (idioma) datos.idioma = idioma;

    const request = await fetch(`students/${id}`, {
        method: completo ? 'PUT' : 'PATCH',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    if (request.ok) {
        alert("Estudiante actualizado.");
        cerrarModal();
        cargarEstudiantes();
    } else {
        alert("Error al actualizar el estudiante.");
    }
}

// Abrir segundo modal (edición completa)
function abrirModalCompleto() {
    document.getElementById("modal-editar").style.display = "none";
    document.getElementById("modalCompleto").style.display = "block";
}

// Cerrar segundo modal (cruz pequeña)
document.getElementById("closeModalCompleto").onclick = function () {
    document.getElementById("modalCompleto").style.display = "none";
};

// Guardar cambios desde el segundo modal
document.getElementById("btnGuardarCompleto").onclick = async function () {
    const id = document.getElementById("editar-id").value;
    const nombre = document.getElementById("inputFullName").value.trim();
    const correo = document.getElementById("inputFullEmail").value.trim();
    const numero = document.getElementById("inputFullNumber").value.trim();
    const idioma = document.getElementById("inputFullLanguage").value.trim();

    if (!nombre || !correo || !numero || !idioma) {
        alert("Todos los campos son obligatorios.");
        return;
    }

    if (!/^\d{10}$/.test(numero)) {
        alert("El número debe tener exactamente 10 dígitos.");
        return;
    }

    if (!/\S+@\S+\.\S+/.test(correo)) {
        alert("Correo electrónico no válido.");
        return;
    }

    const datos = { nombre, correo, numero, idioma };

    const request = await fetch(`students/${id}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    if (request.ok) {
        alert("Estudiante actualizado completamente.");
        document.getElementById("modalCompleto").style.display = "none";
        cargarEstudiantes();
    } else {
        alert("Error al actualizar.");
    }
};
