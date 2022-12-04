$(document).ready(function () {
    //TODO
});

async function registrarUsuario() {

    let datos = {};
    datos.nombre = document.getElementById('exampleFirstName').value
    datos.apellido = document.getElementById('exampleLastName').value
    datos.email = document.getElementById('exampleInputEmail').value
    datos.telefono = document.getElementById('exampleInputTelf').value
    datos.password = document.getElementById('exampleInputPassword').value
    let repassword = document.getElementById('exampleRepeatPassword').value

    if (datos.password !== repassword) {
        return alert('Las contrasena deben ser iguales')
    }

    const usuario = await fetch("api/registrar-usuario", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    if (usuario.status === 200) {
        alert('Usuario registrado')
    }

}

