# Galeria de Arte

Proyecto de INF2236 - Programacion Avanzada para administrar obras, artistas, salas, exhibiciones, clientes, ventas y prestamos.

## Requisitos

- JDK 11.
- NetBeans 21 o inferior, con soporte para proyectos Maven.
- Entorno de escritorio: incluso el modo consola utiliza una ventana para seleccionar la interfaz.

El proyecto no requiere una base de datos ni bibliotecas externas declaradas en el pom.xml.

## Abrir y ejecutar en NetBeans

1. Descomprimir el proyecto.
2. En NetBeans, seleccionar Archivo > Abrir proyecto.
3. Elegir la carpeta que contiene pom.xml.
4. Comprobar que el proyecto utiliza JDK 11.
5. Preparar salas.txt como se indica en la siguiente seccion.
6. Ejecutar Limpiar y construir (Clean and Build).
7. Ejecutar el proyecto (Run Project). Si solicita la clase principal, seleccionar:

   com.mycompany.galeria.de.arte.Main

8. Elegir Consola o Ventanas en el cuadro inicial. En modo consola, ingresar las opciones desde la ventana de salida de NetBeans.

## Archivo de datos

El programa lee y escribe salas.txt en el directorio desde el que se ejecuta. Para la ejecucion habitual desde NetBeans, colocar el archivo junto a pom.xml y utilizar esa carpeta como directorio de trabajo.

En el ZIP original, los datos iniciales estan en src/main/java/salas.txt. Copiar ese archivo a la carpeta de pom.xml antes de la primera ejecucion. No sobrescribir datos existentes si se desean conservar.

Aunque su extension es .txt, el archivo usa el formato Java Properties, con claves y valores. No es CSV.

Si el archivo no existe, el programa inicia sin datos. Si existe pero contiene datos invalidos, informa un error y no continua.

La carga se realiza al iniciar. El guardado se intenta al salir del menu principal de consola con la opcion 0, o al cerrar la ventana principal con la X. Si el guardado de ventanas falla, la ventana permanece abierta y muestra un mensaje.

## Funcionalidades incluidas

- Registro y consulta de obras de tipo Oleo, Escultura y Fotografia.
- Gestion de salas y exhibiciones.
- Busqueda de obras en bodega y exhibiciones.
- Traslados entre bodega y exhibicion.
- Ventas, prestamos y devoluciones.
- Calculo del seguro segun el tipo de obra.
- Interaccion por consola y ventanas.
- Persistencia de salas, exhibiciones, obras y registros de operaciones, con datos de artistas y clientes asociados.

## Limitaciones de esta version

El proyecto compila y la carga y el guardado basicos fueron comprobados. Algunas operaciones de ventas, prestamos, traslados y eliminacion de obras exhibidas todavia modifican copias de colecciones. Esto puede impedir registrar cambios, duplicar referencias entre listas y generar un archivo que no pueda cargarse despues.

Conservar una copia de salas.txt antes de realizar pruebas. Un mensaje de operacion exitosa no garantiza que esas operaciones hayan actualizado correctamente las colecciones originales.

## Integrantes

- Tomas Andres Aguilera Pena
- Constanza Andrea Contreras Ordenes
- Thomas Ignacio Mancilla Ortega

## Repositorio

https://github.com/thomas-mancilla/galeria-de-arte
