// Call the dataTables jQuery plugin
$(document).ready(function () {

  cargarUsuarios();

  $('#dataTable').DataTable();
});

async function cargarUsuarios() {

  console.log('token', localStorage.token);

  const usuario = await fetch("api/usuarios", {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token
    }
  });

  const request = await usuario.json();


  let usuarios = []

  request.forEach(user => {

    usuarios.push(`<tr>
        <td>${user.id}</td>
        <td>${user.nombre} ${user.apellido}</td>
        <td>${user.email}</td>
        <td>${user.telefono}</td>
        <td>
          <a href="#" onClick={eliminarUsuario(${user.id})} class="btn btn-danger btn-circle btn-sm">
            <i class="fas fa-trash"></i>
          </a>
        </td>
      </tr>`)

  })

  document.querySelector('#dataTable tbody').outerHTML = usuarios

  console.log("JAVA REQUEST: ", request);

}

async function eliminarUsuario(id){

  if(!confirm('Desea eliminar el usuario?')){
    return;
  }

  const  deleteUsuario = await fetch("api/usuario-delete/"+id, {
    method: 'DELETE',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token /* Enviar authorizacion el token con el que iniciamos session */
    }
  });

  if(deleteUsuario.status === 200){
    alert("Usuario Eliminado")
    location.reload()
  }else{
    alert("Error")
  }
}