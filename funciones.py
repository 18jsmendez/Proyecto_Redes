def escribirArchivo(nombre, cedula,cantidadRouters,cantidadInterfazPorRouters,cantidadDepar,lugarRed,TecnologiaAcceso,dicPCs):
    archivo = open("Pedido.txt","w")
    archivo.write("\n----------Requerimientos para mi nueva red----------\n")
    archivo.write("Cliente: "+nombre)
    archivo.write("Cédula del Cliente: "+cedula+"\n")
    archivo.write("La cantidad de Routers a usar: "+str(cantidadRouters))
    archivo.write("Cantidad de interfaces de los Routers: "+str(cantidadInterfazPorRouters)+"\n")
    archivo.write("Cantidad de departamentos que tendrá esta red: "+str(cantidadDepar))
    for clave,valor in dicPCs:
        archivo.write("Departamento "+str(clave)+": "+str(valor)+" PCs")

    archivo.write("\n----------Entorno para el que se realizará la red----------\n")

    if lugarRed == 1:
        archivo.write("La red servirá para un Cyber")
    elif lugarRed == 2: 
        archivo.write("La red servirá para una miniempresa")
    elif lugarRed == 3:
        archivo.write("La red servirá para una empresa mediana")
    
    archivo.write("\n----------Tecnología de Acceso a Internet----------\n")
    if lugarRed == 1:
        archivo.write("Fibra óptica")
    elif lugarRed == 2: 
        archivo.write("Cable de Banda Ancha")
    elif lugarRed == 3:
        archivo.write("WAN inalámbrica")
    elif lugarRed == 2: 
        archivo.write("Linea de Suscriptor Digital (DSL)")
    elif lugarRed == 3:
        archivo.write("Linea Arrendada")
    elif lugarRed == 2: 
        archivo.write("Satélite")
    
    archivo.close()






