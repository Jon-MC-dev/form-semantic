import json
from app.moduloPersona import Persona
from flask import Flask, render_template, request
app = Flask(__name__)
persona = Persona(app)

def restablecerValores(request):
    datos = request.form['datos']
    datos = json.loads(datos)
    print("[DATOS] : ")
    print(datos)
    print(":--")
    persona.setNombre(datos["nombre"])
    persona.setApellidoP(datos["apellidP"])
    persona.setApellidoM(datos["apellidoM"])
    habilidades=datos["multipleHabilidades"];
    persona.setHabilidades("["+habilidades[0]+","+habilidades[1]+","+habilidades[2]+"]")
    print(str(datos["multipleHabilidades"]))
    persona.setAlgoMas(datos["algoMas"])
    print("El nombre: "+datos["nombre"])
    return persona

@app.route('/')
def Index():
    return render_template("index.html")

@app.route('/getAllPersonas')
def getAllPersonas():
    return persona.getAllPersonas()


@app.route('/newPersona',methods=['GET', 'POST'])
def newPersona():
    if request.method=='POST':
        return str(restablecerValores(request).newPersona())

@app.route('/updatePersona',methods=['GET', 'POST'])
def updatePersona():
    if request.method=='POST':
        id = request.form['id']
        persona.setIdPersona(id)
        return str(restablecerValores(request).updatePersona())

@app.route('/deletePersona',methods=['GET', 'POST'])
def deletePersona():
    if request.method=='POST':
        id = request.form['id']
        persona.setIdPersona(id)
        return str(persona.deletePersona())

if __name__ == '__main__':
    app.run(port=3000 ,debug=True)



    #str(persona.newPersona())
