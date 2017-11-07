# TBD-2-2017-V2

Para correr BackEnd:

Correr base de datos relacional:
- importar el archivo sql tweetgames-db.sql

RestApi:
- Entrar a carpeta de proyecto restApi-tweetGame
- Abrir terminal dentro de la carpeta y ejecutar gradle.war
- Desplegar el e archivo .war generado en tomcat

Recolector de Tweets:

- Inicializar MongoDB: sudo service mongod  start
- Entrar en carpeta twitter-streaming-master
- Abrir terminal y ejecutar el comando: gradle fatjar
- Para inicializar el recolector ejecutar: java -cp build/libs/twitter-streaming-master-1.0.jar cl.citi
aps.twitter.streaming.TwitterStreaming

Indexador y llenado de base de datos relaciona:

- Abrir el proyecto en un ide y ubicar la clase luceneIndexador o simplemente ubicar la clase externamente.
- En el constructor de la clase se encuentra una linea de codigo de la siguiente forma:
  this.directory = new File("/home/juanpablo/Escritorio/USACH/TBD/Backend/index_files");
- En el sector donde se encuentra la direccion, cambiarla a gusto por alguna carpeta donde se quiera guardar los documentos que se vayan indexando.
- En la carpeta del proyecto abrir la terminal y ejecutar: gradle fatjar (es obligatorio que sea fatjar)
- Para inicializar los procesos de indexación y llenado de base de datos relacional realizas:
- En la clase tweetGameMain descomentar los distintos procesos que se requieren, si se desea realizar todo el poceso: descomentar todo
  y ejecutar comando:
  java -cp build/libs/tweetgame-1.0-SNAPSHOT.jar main.TweetGameMain
  
  
Para correr FrontEnd:

- Entrar en carpeta Frontend-tweetgames
- Abrir terminal y ejecutar: npm run dev
