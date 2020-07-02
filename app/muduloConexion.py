from flask_mysqldb import MySQL
class Conexion:
    """docstring for ."""

    def __init__(self,app):
        self.direccionServer = "localhost"
        self.nombreUser = "root"
        self.nombreBD = "dbpersonas"
        self.contrasena = ""
        self.mysql = ""
        app.config['MYSQL_HOST'] = self.direccionServer
        app.config['MYSQL_USER'] = self.nombreUser
        app.config['MYSQL_PASSWORD'] = self.contrasena
        app.config['MYSQL_DB'] = self.nombreBD
        self.mysql = MySQL(app)

    def Conectar(self):
        self.objConexion = self.mysql.connection.cursor()

    def traerRegistros(self , sql):
        self.objConexion.execute(sql)
        return self.objConexion.fetchall()

    def getMaxID(self , tbl, campo):
        resultado=self.traerRegistros("SELECT MAX("+campo+") AS Maximo FROM "+tbl)
        print(resultado)
        idMAximo = -1
        for registro in resultado:
            if not registro[0] is None:
                idMAximo=registro[0]

        return idMAximo

    def getNextID(self,tbl , campo):
        idActual = self.getMaxID(tbl,campo)
        return idActual+1

    def IEA(self,sql):
        print("[SENTENCIA SQL] "+sql)
        filas_afectadas = 0;
        filas_afectadas = self.objConexion.execute(sql)
        print("Tipo de filas_afectadas")
        print(type(filas_afectadas))
        print((filas_afectadas))
        print("----")
        self.objConexion.connection.commit()
        print("---- commit")
        return filas_afectadas
