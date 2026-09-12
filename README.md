ADMINISTRACION DE GALERIAS DE ARTE

Proyecto en Java para administrar obras de arte, salas, exhibiciones, ventas y prestamos.

REQUISITOS

JDK 11 y NetBeans 21 o inferior.

COMO EJECUTAR

Descargar el proyecto y descomprimirlo.
Abrir en NetBeans la carpeta que contiene pom.xml.
Limpiar y construir el proyecto.
Ejecutar la clase Main del paquete com.mycompany.galeria.de.arte.
Elegir Consola en la ventana inicial.
Escribir las opciones en la consola de NetBeans.

FUNCIONES

Agregar, listar, buscar, editar y eliminar salas y exhibiciones.
Registrar, editar y eliminar obras.
Trasladar obras entre bodega y exhibiciones.
Buscar obras en bodega por titulo o estado.
Registrar ventas y consultar su historial.
Registrar prestamos y devolver obras a bodega.
Consultar los clientes que realizaron compras.

GUARDADO DE DATOS

Los datos se cargan al iniciar y se guardan al salir del menu principal con la opcion 0.
Se utiliza el archivo salas.txt en la carpeta desde la que se ejecuta el programa.
El archivo guarda salas, exhibiciones, obras, ventas y prestamos activos.
Tambien guarda los datos de artistas y los clientes de esas operaciones.

ESTADO ACTUAL

Usar el modo consola. Los botones del modo ventanas todavia no tienen funciones conectadas y el guardado al cerrar la ventana esta pendiente.
Las busquedas por titulo y estado solo revisan bodega.
Algunas operaciones seleccionan obras por titulo, por lo que los titulos repetidos pueden causar confusiones.
Falta validar las fechas de los prestamos.

PRUEBA BASICA

Crear una sala, una exhibicion y varias obras.
Trasladar una obra, vender otra y prestar otra.
Salir con la opcion 0 y abrir nuevamente.
Comprobar los datos y devolver la obra prestada.
