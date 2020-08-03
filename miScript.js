/*
Registrar el serviceWorker
ServiceWorker
*/



let servicioWorker=null;
if('serviceWorker' in navigator) {
  navigator.serviceWorker.register('miServiceWorker.js').then(function(succes) {

    servicioWorker = succes;
    console.log('Service Worker Registrado ',succes);
    window.Notification.requestPermission();
    console.log("Segun se obtubo un permiso");
}).catch((error)=>{
    console.log("serviceWorker No registrado",error);
  });
}else {
  console.log("El serviceWorker no se instalara");
}




let idEliminar;

var selecciones={
  "1":"Empatía",
  "2":"Saber escuchar",
  "3":"Liderazgo",
  "4":"Empatía",
  "5":"Flexibilidad",
  "6":"Optimismo",
  "7":"Confianza",
  "8":"Capacidad de comunicación",
  "9":"Ser capaz de convencer",
  "10":"Honestidad e integridad"
};

$('#multipleSelect').dropdown({
  maxSelections: 3,
  message: {
    maxSelections: 'Maximo {maxCount} selecciones',
  }
});

$('#multipleSelect2').dropdown({
  maxSelections: 3,
  message: {
    maxSelections: 'Maximo {maxCount} selecciones',
  }
});
let validaciones={
on: 'submit',
inline : true,
fields: {
  nombre: {
    identifier: 'nombre',
    rules: [
      {
        type   : 'empty',
        prompt : 'Porfavor escribe tu nombre'
      }
    ]
  },
  apellidP: {
    identifier: 'apellidP',
    rules: [
      {
        type   : 'empty',
        prompt : 'Porfavor escribe tu apellido'
      }
    ]
  },
  apellidM: {
    identifier: 'apellidoM',
    rules: [
      {
        type   : 'empty',
        prompt : 'Porfavor escribe tu apellido'
      }
    ]
  },
  multipleHabilidades: {
    identifier  : 'multipleHabilidades',
    rules: [
      {
        type   : 'minCount[3]',
        prompt : 'Porfavor selecciona almenos 3'
      }
    ]
  },
  algoMas: {
    identifier: 'algoMas',
    rules: [
      {
        type   : 'empty',
        prompt : 'Cuentanos algo sobre ti'
      }
    ]
  }

}
};

var miForm=$('#formAdd').form(validaciones);
var miFormEdit=$('#formEditar').form(validaciones);
let datosJSON;
$('#formAdd').on("submit",function (even) {
even.preventDefault();
if(miForm.form('is valid')) {
  let jsoDatos = miForm.form('get values');
  let datos=new FormData();
  datos.append('datos',JSON.stringify(jsoDatos));
  fetch("Controlador.php?newPersona",
    {
      method: 'POST',
      body: datos
  }).then(res => res.text())
  .catch(error => console.error('Error:', error))
  .then((response) => {
    if (response==="1") {
      console.log('%cSuccess:', 'Color:blue');
      miForm.form('clear');
      cargarTabla();
    }
  });
}
});


let index;
function cargarTabla() {
fetch('Controlador.php?getAllPersonas')
.then(function(response) {
  return response.json();
})
.then(function(myJson) {
  datosJSON = Object.assign({}, myJson);
  //servicioWorker.dispatchEvent(eventoNotifi);
  var tablaHtml='';
  myJson=myJson.data;
  for (var i = 0; i < myJson.length; i++) {
    myJson[i].habilidades=JSON.parse(myJson[i].habilidades);
    tablaHtml+=`
       <tr class="my-${myJson[i].idPersona}" >
       <td>${myJson[i].nombre}</td>
       <td>${myJson[i].apellidoP}</td>
       <td>${myJson[i].apellidoM}</td>
       <td>${selecciones[myJson[i].habilidades[0]]+", "+selecciones[myJson[i].habilidades[1]]+", "+selecciones[myJson[i].habilidades[2]]}</td>
       <td>${myJson[i].algoMas}</td>
       <td>
       <div class="ui buttons">
         <button class="ui button teal btn-editar" onClick="$('#modalEditar').modal('show');" >Editar</button>
         <div class="or" data-text="ó"></div>
         <button class="ui negative button" onClick="$('#modalEliminar').modal('show');idEliminar=${myJson[i].idPersona};">Eliminar</button>
       </div>
       </td>
       </tr>
    `;
  }
  $("#idData tbody").html(tablaHtml);

  $(".btn-editar").on("click",function () {
    var id=$(this).closest('tr').attr('class').substr(3);
    index = datosJSON.data.find(function (elemento) {
      if (elemento.idPersona===id) {
        return elemento;
      }
    });
    console.log(index);
     miFormEdit.form('set values', {
     nombre     : index.nombre,
     apellidP   : index.apellidoP,
     apellidoM   : index.apellidoM,
     algoMas : index.algoMas
   });
   //console.log(index.habilidades);
   //index.habilidades=JSON.parse(index.habilidades);
   $('#multipleSelect2').dropdown('set selected', [index.habilidades[0].toString(),index.habilidades[1].toString(),index.habilidades[2].toString()]);
  });


});
}
cargarTabla();





$("#btn-eliminar").on("click",function () {
  let datos=new FormData();
  datos.append('id',idEliminar);
  fetch("Controlador.php?eliminarPersona",{
      method: 'POST',
      body: datos
  }).then(res => res.text())
  .catch(error => console.error('Error:', error))
  .then((response) => {
    if (response==="1") {
      console.log('%cSuccess Eliminar:', 'Color:green');
      $("#modalEliminar").modal("hide");
      cargarTabla();
    }
  });

});































$('#formEditar').on("submit",function (even) {
  console.log("Para editar");
even.preventDefault();
if(miFormEdit.form('is valid')) {
  let jsoDatos = miFormEdit.form('get values');
  let datos=new FormData();
  datos.append('datos',JSON.stringify(jsoDatos));
  datos.append('id',index.idPersona);
  fetch("Controlador.php?actualizarPersona",
    {
      method: 'POST',
      body: datos
  }).then(res => res.text())
  .catch(error => console.error('Error:', error))
  .then((response) => {
    if (response==="1") {
      console.log('%cSuccess Actualizar:', 'Color:red');
      miFormEdit.form('clear');
      $("#modalEditar").modal("hide");
      cargarTabla();
    }
  });
}else {
  console.log("No es valido");
}
})
