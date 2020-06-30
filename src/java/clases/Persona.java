package clases;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;

 public class Persona {

    private int idPersona;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String habilidades;
    private String algoMas;

    private Conexion conexion;

    public Persona() {
        conexion = new Conexion();
        conexion.Conectar();
    }

    public String getAllPersonas() {
        String lista = "";
        ResultSet listaPersonas = conexion.ejecutarConsulta("SELECT * FROM personas");
        try {
            while (listaPersonas.next()) {
                lista += "{";
                lista += "\"idPersona\":\"" + listaPersonas.getString("idPersona") + "\",";
                lista += "\"nombre\":\"" + listaPersonas.getString("nombre") + "\",";
                lista += "\"apellidoP\":\"" + listaPersonas.getString("apellidoP") + "\",";
                lista += "\"apellidoM\":\"" + listaPersonas.getString("apellidoM") + "\",";
                lista += "\"habilidades\":\"" + listaPersonas.getString("habilidades") + "\",";
                lista += "\"algoMas\":\"" + listaPersonas.getString("algoMas") + "\"";
                lista += "},";
            }     
            try{
                lista = lista.substring(0, lista.length() - 1);
            }catch(java.lang.StringIndexOutOfBoundsException err){
                err.printStackTrace();
            }
            System.out.println(lista);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "{\"data\":[" + lista + "]}";
    }
    
    
  public int newPersona()
  {
    this.idPersona = this.conexion.getNextID("personas","idPersona");
    return (this.conexion.ejecutarRUD("INSERT INTO personas VALUES ('"+this.idPersona+"', '"+this.nombre+"', '"+this.apellidoP+"', '"+this.apellidoM+"', '"+this.habilidades+"', '"+this.algoMas+"')"));
  }

  public int updatePersona()
  {
    return (conexion.ejecutarRUD("UPDATE personas SET nombre = '"+this.nombre+"', apellidoP = '"+this.apellidoP+"', apellidoM = '"+this.apellidoM+"', habilidades = '"+this.habilidades+"', algoMas = '"+this.algoMas+"' WHERE idPersona = "+this.idPersona+";"));
  }

  public int deletePersona()
  {
    return (conexion.ejecutarRUD("DELETE FROM personas WHERE idPersona = "+this.idPersona));
  }
  
    public static void main(String[] args) {
        Persona p=new Persona();
        System.out.println(p.getAllPersonas());
        
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public String getAlgoMas() {
        return algoMas;
    }

    public void setAlgoMas(String algoMas) {
        this.algoMas = algoMas;
    }

}
