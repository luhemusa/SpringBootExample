$(document).ready(function () {
    //TODO
});

async function login() {

    let datos = {};
    datos.email = document.getElementById('exampleInputEmail').value
    datos.password = document.getElementById('exampleInputPassword').value
    
    const usuario = await fetch("api/login", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    const request = await usuario.text();

    if (request === 'Error en inicio de sesion'){
        return alert('Usuario o contrase;a inconrrectos')
    }

    localStorage.token = request
    localStorage.email = datos.email
    window.location.href = 'usuarios.html';
   

}
