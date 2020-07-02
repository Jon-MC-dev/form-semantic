from app.muduloConexion import Conexion

class Persona:
    """docstring for ."""

    def __init__(self,app):
        self.idPersona=0
        self.nombre=""
        self.apellidoP=""
        self.apellidoM=""
        self.habilidades=""
        self.algoMas=""
        self.objConexion = Conexion(app)

    def getAllPersonas(self):
        self.objConexion.Conectar()
        personas = self.objConexion.traerRegistros("SELECT * FROM personas")
        print(personas)
        lista = ""
        for persona in  personas:
            lista+='{'
            lista+='"idPersona":"'+str((persona[0]))+'",'
            lista+='"nombre":"'+persona[1]+'",'
            lista+='"apellidoP":"'+persona[2]+'",'
            lista+='"apellidoM":"'+persona[3]+'",'
            lista+='"habilidades":"'+persona[4]+'",'
            lista+='"algoMas":"'+persona[5]+'"'
            lista+='},'
        lista = lista[:-1]
        return '{"data":['+lista+']}'

    def newPersona(self):
        self.objConexion.Conectar()
        self.idPersona = self.objConexion.getNextID("personas","idPersona");
        return self.objConexion.IEA("INSERT INTO personas VALUES ('"+str(self.idPersona)+"', '"+self.nombre+"', '"+self.apellidoP+"', '"+self.apellidoM+"', '"+self.habilidades+"', '"+self.algoMas+"')")

    def updatePersona(self):
        self.objConexion.Conectar()
        return self.objConexion.IEA("UPDATE personas SET nombre = '"+self.nombre+"', apellidoP = '"+self.apellidoP+"', apellidoM = '"+self.apellidoM+"', habilidades = '"+self.habilidades+"', algoMas = '"+self.algoMas+"' WHERE idPersona = "+str(self.idPersona)+";")

    def deletePersona(self):
        self.objConexion.Conectar()
        return self.objConexion.IEA("DELETE FROM personas WHERE idPersona = "+str(self.idPersona))


    def setIdPersona(self,idPersona):
        self.idPersona=idPersona
    def setNombre(self,nombre):
        self.nombre=nombre
    def setApellidoP(self,apellidoP):
        self.apellidoP=apellidoP
    def setApellidoM(self,apellidoM):
        self.apellidoM=apellidoM
    def setHabilidades(self,habilidades):
        self.habilidades=habilidades
    def setAlgoMas(self,algoMas):
        self.algoMas=algoMas
