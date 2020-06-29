<?php
class Conexion
{
  private $_objConexion;
  private $_direccionServer = "localhost";
  private $_nombreUser = "root";
  private $_nombreBD = "dbpersonas";
  private $_contrasena = "";
  function __construct()
  {
  }

  public function Conectar()
  {
    $this->_objConexion = new PDO("mysql:host=".$this->_direccionServer.";dbname=".$this->_nombreBD, $this->_nombreUser, $this->_contrasena);
    return $this->_objConexion;
  }

  public function traerRegistros($Sql='')
  {
    return $this->_objConexion->query($Sql, PDO::FETCH_ASSOC);
  }



    public function getMaxID($tbl,$campo)
    {
      $Resultado = $this->traerRegistros("SELECT MAX($campo) AS Maximo FROM $tbl");
      $idMAximo=-1;
      if ($Resultado) {
        foreach ($Resultado as $key) {
          $idMAximo=$key['Maximo'];
        }
      }
      return $idMAximo;
    }


    /**
    * iEA Insertar Eliminar Actualizar
    */
    public function IEA($Sql)
    {
      $Sql=$this->_objConexion->prepare($Sql);
      $Resultado = $Sql->execute();
      return $Resultado;
    }

    /**
    * iEA Insertar Eliminar Actualizar
    */
    public function getNextID($tbl,$campo)
    {

      $idActual = $this->getMaxID($tbl,$campo);
      if ($idActual==null) {
        $idActual=0;
      }
      $idActual=$idActual+1;
      return $idActual;
    }



}



 ?>
