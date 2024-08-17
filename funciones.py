import random as rd


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


def segmentar_archivo(ruta_archivo, tamano_segmento=1024):
    with open(ruta_archivo, 'rb') as archivo:
        datos = archivo.read()
    
    segmentos = []
    for i in range(0, len(datos), tamano_segmento):
        segmento = datos[i:i+tamano_segmento]
        numero_secuencia = i // tamano_segmento
        segmentos.append((numero_secuencia, segmento))
    
    return segmentos


def introducir_errores(segmentos):
    segmentos_con_errores = []
    for numero_secuencia, segmento in segmentos:
        # Decidir si se introduce un error o no
        if rd.choice([True, False]):
            tipo_error = rd.choice(['ninguno', 'perdida', 'alteracion'])
            
            if tipo_error == 'perdida':
                continue  # Simula la pérdida del paquete
            
            elif tipo_error == 'alteracion':
                # Simula la alteración de datos cambiando un byte
                byte_a_alterar = rdm.randint(0, len(segmento) - 1)
                segmento_alterado = bytearray(segmento)
                segmento_alterado[byte_a_alterar] ^= 0xFF  # Alterar el byte
                segmento = bytes(segmento_alterado)
        
        segmentos_con_errores.append((numero_secuencia, segmento))
    
    # Mezcla para simular envío fuera de orden
    rd.shuffle(segmentos_con_errores)
    
    return segmentos_con_errores

def recibir_y_verificar_segmentos(segmentos):
    segmentos_ordenados = sorted(segmentos, key=lambda x: x[0])
    archivo_recibido = bytearray()

    errores_detectados = []
    secuencia_esperada = 0

    for numero_secuencia, segmento in segmentos_ordenados:
        if numero_secuencia != secuencia_esperada:
            errores_detectados.append(f"Falta el segmento {secuencia_esperada}")
            secuencia_esperada = numero_secuencia

        # Verificar integridad del segmento (podemos añadir una verificación simple)
        if verificar_integridad(segmento):
            archivo_recibido.extend(segmento)
        else:
            errores_detectados.append(f"Error de integridad en el segmento {numero_secuencia}")
        
        secuencia_esperada += 1
    
    return archivo_recibido, errores_detectados

def verificar_integridad(segmento):
    # Esta función puede hacer algo más complejo como una verificación de checksum
    return True  # Placeholder, siempre devuelve True


def guardar_archivo_y_reporte(ruta_archivo, datos, errores_detectados):
    with open(ruta_archivo, 'wb') as archivo:
        archivo.write(datos)
    
    with open(ruta_archivo + "_reporte.txt", 'w') as reporte:
        for error in errores_detectados:
            reporte.write(error + '\n')

    for numero_secuencia, segmento in segmentos_ordenados:
        if numero_secuencia != secuencia_esperada:
            errores_detectados.append(f"Falta el segmento {secuencia_esperada}")
            secuencia_esperada = numero_secuencia

        # Verificar integridad del segmento (podemos añadir una verificación simple)
        if verificar_integridad(segmento):
            archivo_recibido.extend(segmento)
        else:
            errores_detectados.append(f"Error de integridad en el segmento {numero_secuencia}")
        
        secuencia_esperada += 1
    
    return archivo_recibido, errores_detectados

def verificar_integridad(segmento):
    # Esta función puede hacer algo más complejo como una verificación de checksum
    return True  # Placeholder, siempre devuelve True


#ya subo como usar las funciones en el main :)

