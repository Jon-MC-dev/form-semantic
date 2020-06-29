<?php
/**
 *
 */
 include 'Conexion.php';
class Persona
{

  private $_idPersona;
  private $_nombre;
  private $_apellidoP;
  private $_apellidoM;
  private $_habilidades;
  private $_algoMas;

  private $_objConexion;

  function __construct()
  {
    $this->_objConexion=new Conexion();
    $this->_objConexion->Conectar();
  }

  public function getAllPersonas()
  {
    $_personas = ($this->_objConexion->traerRegistros("SELECT * FROM personas"));
    $_lista="";
    foreach ($_personas as $key) {
      $_lista.='{';
      $_lista.='"idPersona":"'.$key['idPersona'].'",';
      $_lista.='"nombre":"'.$key['nombre'].'",';
      $_lista.='"apellidoP":"'.$key['apellidoP'].'",';
      $_lista.='"apellidoM":"'.$key['apellidoM'].'",';
      $_lista.='"habilidades":"'.$key['habilidades'].'",';
      $_lista.='"algoMas":"'.$key['algoMas'].'"';
      $_lista.='},';
    }
    $_lista = trim($_lista, ',');
    return '{"data":['.$_lista.']}';
  }

  public function newPersona()
  {
    $this->_idPersona = $this->_objConexion->getNextID("personas","idPersona");
    return ($this->_objConexion->IEA("INSERT INTO personas VALUES ('$this->_idPersona', '$this->_nombre', ' $this->_apellidoP', '$this->_apellidoM', '$this->_habilidades', '$this->_algoMas')"));
  }

  public function updatePersona()
  {
    return ($this->_objConexion->IEA("UPDATE personas SET nombre = '$this->_nombre', apellidoP = ' $this->_apellidoP', apellidoM = '$this->_apellidoM', habilidades = '$this->_habilidades', algoMas = '$this->_algoMas' WHERE idPersona = $this->_idPersona;"));
  }

  public function deletePersona()
  {
    return ($this->_objConexion->IEA("DELETE FROM personas WHERE idPersona = $this->_idPersona"));
  }



    /**
     * Get the value of Id Persona
     *
     * @return mixed
     */
    public function getIdPersona()
    {
        return $this->_idPersona;
    }

    /**
     * Set the value of Id Persona
     *
     * @param mixed $_idPersona
     *
     * @return self
     */
    public function setIdPersona($_idPersona)
    {
        $this->_idPersona = $_idPersona;

        return $this;
    }

    /**
     * Get the value of Nombre
     *
     * @return mixed
     */
    public function getNombre()
    {
        return $this->_nombre;
    }

    /**
     * Set the value of Nombre
     *
     * @param mixed $_nombre
     *
     * @return self
     */
    public function setNombre($_nombre)
    {
        $this->_nombre = $_nombre;

        return $this;
    }

    /**
     * Get the value of Apellido
     *
     * @return mixed
     */
    public function getApellidoP()
    {
        return $this->_apellidoP;
    }

    /**
     * Set the value of Apellido
     *
     * @param mixed $_apellidoP
     *
     * @return self
     */
    public function setApellidoP($_apellidoP)
    {
        $this->_apellidoP = $_apellidoP;

        return $this;
    }

    /**
     * Get the value of Apellido
     *
     * @return mixed
     */
    public function getApellidoM()
    {
        return $this->_apellidoM;
    }

    /**
     * Set the value of Apellido
     *
     * @param mixed $_apellidoM
     *
     * @return self
     */
    public function setApellidoM($_apellidoM)
    {
        $this->_apellidoM = $_apellidoM;

        return $this;
    }

    /**
     * Get the value of Habilidades
     *
     * @return mixed
     */
    public function getHabilidades()
    {
        return $this->_habilidades;
    }

    /**
     * Set the value of Habilidades
     *
     * @param mixed $_habilidades
     *
     * @return self
     */
    public function setHabilidades($_habilidades)
    {
        $this->_habilidades = $_habilidades;

        return $this;
    }

    /**
     * Get the value of Algo Mas
     *
     * @return mixed
     */
    public function getAlgoMas()
    {
        return $this->_algoMas;
    }

    /**
     * Set the value of Algo Mas
     *
     * @param mixed $_algoMas
     *
     * @return self
     */
    public function setAlgoMas($_algoMas)
    {
        $this->_algoMas = $_algoMas;

        return $this;
    }

}

 ?>
