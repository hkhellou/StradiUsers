Pyecto creado en Kotlin con arquitectura Clean Architecture para tener separadas las capas y permitir una mejor escalabilidad del proyecto y hacerlo más fácil de mantener.

Para el llamadas API he usado la libreria Retrofit ya que es la más común  y comoda de implementar por la amplia documentación y comuniad de la que dispone. 

Para la inyección de dependencias he usado la libreria Hilt ya que es la oficial de Google y es mas facil de implmentar y está mejor estrucutrada, además Hilt se ejecuta en tiempo de compilación
en vez de en tiempo de ejecución como en el caso de Koin, esto nos permite ver los errores en compilación y evitar futuros crashes en nuestra app.

A la hora de hacer las llamadas a los servicios he usado corrutinas para evitar saturar el hilo principal y que se produzcan crashes o lentitud en la app.

El patrón que he usado en la app es MVVM para  mi es el mas fácil de usar ya que es muy claro y gracias a las variables de tipo livedata que nos permiten tener un mejor manejo sobre nuestra información.
