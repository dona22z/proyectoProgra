$(document).ready(function () {
    //
});

async function agregarEstudiantes() {
    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value.trim();
    datos.correo = document.getElementById('txtCorreo').value.trim();
    datos.numero = document.getElementById('numNumero').value.trim();
    datos.idioma = document.getElementById('selectorIdioma').value;

    if (!datos.nombre || !datos.correo || !datos.numero || !datos.idioma) {
        alert('Por favor, complete todos los campos.');
        return;
    }

    if (!/^\d{10}$/.test(datos.numero)) {
        alert('El número debe tener exactamente 10 dígitos numéricos.');
        return;
    }

    if (!/\S+@\S+\.\S+/.test(datos.correo)) {
        alert('Correo electrónico no válido.');
        return;
    }
    if ((datos.idioma).toLowerCase != "español"|| "ingles"){
        alert('Correo electrónico.');
        return;
    }


    try {
        const response = await fetch('students', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            alert('Estudiante agregado correctamente.');

            document.getElementById('txtNombre').value = '';
            document.getElementById('txtCorreo').value = '';
            document.getElementById('numNumero').value = '';
            document.getElementById('selectorIdioma').value = '';
        } else {
            const error = await response.text();
            alert('Error al agregar estudiante: ' + error);
        }
    } catch (err) {
        console.error('Error de red:', err);
        alert('Hubo un problema al conectar con el servidor.');
    }

}