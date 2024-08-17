from funciones import escribirArchivo

dicPCs={}

#Datos personales
nombre = input("Nombre del remitente: ")
cedula = input("Cédula del remitente: ")

#Datos de red
cantidadRouters = int(input("Ingrese la cantidad de routers que desea instalar: "))
cantidadInterfacesPorRouters = int(input("Ingrese la cantidad de interfaces que tienen los routers: "))
cantidadDepar = int(input("Ingrese la cantidad de departamentos: "))

for n in range(cantidadDepar):
    cantPCporDepar=int(input("     Ingrese la cantidad de PCs en el departamento "+str(n+1)+" :" ))
    dicPCs.setdefault((n+1),cantPCporDepar)

#Tipo del lugar donde se realizará la red
print("\n¿Para qué necesita su red? Tome en cuenta la siguiente tabla:")
print("    Para Cyber, ingrese 1\n    Para miniempresas, ingrese 2\n    Para empresas medianas, ingrese 3")
lugarRed = int(input("Ingrese el número correspondiente: "))

#Tipo de tecnologia de acceso a internet
print("\n¿Cúal es su tecnología de acceso a internte? Tome en cuenta la siguiente tabla:")
print("    Si es Fibra Óptica, ingrese 1\n    Si es Cable de banda ancha, ingrese 2\n    Si es WAN inalámbrica, ingrese 3\n    Si es Linea de Suscriptor digital (DSL), ingrese 4\n    Si es Linea Arrendada, ingrese 5\n    Si es por Satélite, ingrese 6")
tecnologiaAcceso = int(input("Ingrese el número correspondiente: "))

print(dicPCs)

escribirArchivo(nombre,cedula,cantidadRouters,cantidadInterfacesPorRouters,cantidadDepar,lugarRed,tecnologiaAcceso,dicPCs)
