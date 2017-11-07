# TBD-2-2017-V2

Para correr BackEnd:

Correr base de datos relacional:
- importar el archivo sql tweetgames-db.sql

RestApi:
- Entrar a carpeta de proyecto restApi-tweetGame
- ubicar el archivo aplication.resources y cambiar valores de coneccion mysql por los que se tiene en su servicio mysql.
- Abrir terminal dentro de la carpeta y ejecutar gradle.war
- Desplegar el e archivo .war generado en tomcat

Recolector de Tweets:

- Inicializar MongoDB: sudo service mongod  start
- Entrar en carpeta twitter-streaming-master
- Abrir terminal y ejecutar el comando: gradle fatjar
- Para inicializar el recolector ejecutar: java -cp build/libs/twitter-streaming-master-1.0.jar cl.citiaps.twitter.streaming.TwitterStreaming

Indexador y llenado de base de datos relacional y servicio neo4j:

- Abrir el proyecto en un ide y ubicar la clase luceneIndexador o simplemente ubicar la clase externamente.
- En el constructor de la clase se encuentra una linea de codigo de la siguiente forma:
  this.directory = new File("/home/juanpablo/Escritorio/USACH/TBD/Backend/index_files");
- En el sector donde se encuentra la direccion, cambiarla a gusto por alguna carpeta donde se quiera guardar los documentos que se vayan indexando.
- En la carpeta del proyecto abrir la terminal y ejecutar: gradle fatjar (es obligatorio que sea fatjar)
- Tener encendido el servicio de neo4j
- En la clase TweetGameMain se encuentra el constructor del controlador neo4j inicilizado en el main, cambiar parametros 2 y 3 que corresponden al user y password que usted definió para su servicio en neo4j.
- Para inicializar los procesos de indexación y llenado de base de datos con la relacion en neo4j relacional ejecutar: java -cp build/libs/tweetgame-1.0-SNAPSHOT.jar main.TweetGameMain
  
  
Para correr FrontEnd:

- Entrar en carpeta Frontend-tweetgames
- Abrir terminal y ejecutar: npm run dev
