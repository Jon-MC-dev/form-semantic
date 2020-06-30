package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/dbpersonas";
    private final String USUARIO = "root";
    private final String CONTRASENA = "";
    private Connection conexion;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;

    public boolean establecerConexion(String driver, String url, String usuario, String contrasena) {
        boolean conectado = false;
        try {
            Class.forName(driver);//Levantare el driver
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            conectado = true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            conectado = false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            conectado = false;
        }
        return conectado;
    }
    
    public boolean Conectar(){
     return establecerConexion(DRIVER, URL, USUARIO, CONTRASENA);   
    }

    public ResultSet ejecutarConsulta(String sql) {
        try {
            System.out.println("Consulta SQL " + sql);
            st = conexion.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;

    }
    
    public int getMaxID(String tabla,String campo){
        int maxID=-1;
        ResultSet resultado=this.ejecutarConsulta("SELECT MAX("+campo+") AS Maximo FROM "+tabla);
        try {
            while(resultado.next()){
                maxID = resultado.getInt("Maximo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maxID;
    }
    
    public int getNextID(String tabla,String campo){
        return this.getMaxID(tabla, campo)+1;
    }
    
        public int ejecutarRUD(String sql) { // Insertar actualizar eliminar
        System.out.println("Consulta " + sql);
        int filas_afectadas = 0;
        try {
            pst = conexion.prepareStatement(sql);
            filas_afectadas = pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return filas_afectadas;

    }

    public static void main(String[] args) {
        Conexion c = new Conexion();
        
        System.out.println(c.Conectar());
                
    }
}
