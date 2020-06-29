<?php
include 'Persona.php';
$_objPersona=new Persona();

function establecerValores($_objPersona)
{
  $datosJson=json_decode($_POST['datos']);
  $habilidades=$datosJson->multipleHabilidades;
  $habilidades="[".$habilidades[0].",".$habilidades[1].",".$habilidades[2]."]";
  $_objPersona->setNombre($datosJson->nombre);
  $_objPersona->setApellidoP($datosJson->apellidP);
  $_objPersona->setApellidoM($datosJson->apellidoM);
  $_objPersona->setHabilidades($habilidades);
  $_objPersona->setAlgoMas($datosJson->algoMas);
  $_objPersona->setIdPersona((isset($_POST['id'])) ? $_POST['id'] : "");
}
if (isset($_GET['getAllPersonas'])) {
  echo $_objPersona->getAllPersonas();
}elseif (isset($_GET['newPersona'])) {
  establecerValores($_objPersona); // Establecer valores
  echo $_objPersona->newPersona();
}else if (isset($_GET['actualizarPersona'])) {
  establecerValores($_objPersona); // Establecer valores
  echo $_objPersona->updatePersona();
}elseif (isset($_GET['eliminarPersona'])) {
  $_objPersona->setIdPersona($_POST['id']);
  echo $_objPersona->deletePersona();
}

 ?>
