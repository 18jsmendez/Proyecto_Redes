def escribirArchivo(nombre, cedula,cantidadRouters,cantidadInterfazPorRouters,cantidadDepar,lugarRed,TecnologiaAcceso,dicPCs):
    archivo = open("Pedido.txt","w")
    archivo.write("\n----------Requerimientos para mi nueva red----------\n")
    archivo.write("Cliente: "+nombre+"\n")
    archivo.write("Cédula del Cliente: "+cedula+"\n")
    archivo.write("La cantidad de Routers a usar: "+str(cantidadRouters)+"\n")
    archivo.write("Cantidad de interfaces de los Routers: "+str(cantidadInterfazPorRouters)+"\n")
    archivo.write("Cantidad de departamentos que tendrá esta red: "+str(cantidadDepar)+"\n")
    for clave,valor in dicPCs.items():
        archivo.write("    Departamento "+str(clave)+": "+str(valor)+" PCs\n")

    archivo.write("\n----------Entorno para el que se realizará la red----------\n")

    if lugarRed == 1:
        archivo.write("La red servirá para un Cyber\n")
    elif lugarRed == 2: 
        archivo.write("La red servirá para una miniempresa\n")
    elif lugarRed == 3:
        archivo.write("La red servirá para una empresa mediana\n")
    
    archivo.write("\n----------Tecnología de Acceso a Internet----------\n")
    if lugarRed == 1:
        archivo.write("Fibra óptica\n")
    elif lugarRed == 2: 
        archivo.write("Cable de Banda Ancha\n")
    elif lugarRed == 3:
        archivo.write("WAN inalámbrica\n")
    elif lugarRed == 2: 
        archivo.write("Linea de Suscriptor Digital (DSL)\n")
    elif lugarRed == 3:
        archivo.write("Linea Arrendada\n")
    elif lugarRed == 2: 
        archivo.write("Satélite\n")
    
    archivo.close()






