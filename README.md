# cibus
### *Reservar. Ir. Comer.*
###### Proyecto de Laboratorio TIC 1 - Universidad de Montevideo

#### ¿Qué se precisa hacer para poder correr el programa?

1. Clonear e importar al IDE, agregando a commons como dependencia de backend y frontend.
2. Por motivos de RMI, hallar ip local del servidor y tenerla anotada.
3. Dentro de la carpeta resources de frontend, agregar un application.yml (`frontend\src\main\resources\application.yml`) con el siguiente layout:
    ```
    server:
      port: 1099
      host: {ip-local-del-servidor}
    ```
4.  Crear una base de datos (nosotros la llamamos Cibus y la levantamos en el `port 3306`, sólo habría que cambiar la url)
5. Dentro de la carpeta resources de backend, agregar un application.yml (`backend\src\main\resources\application.yml`) con el siguiente layout (en el url, puede hacer falta anexar con ? `useSSL=true`) (¡prestar atención al `ddl-auto`, debe ser `create`!):
    ```
    spring:
      application:
        name: cibus-backend
      datasource:
        driverClassName: com.mysql.cj.jdbc.Driver
        url: {url-de-tu-base}
        username: {tu-username}
        password: {tu-password}
      jpa:
        show-sql: true
        hibernate:
          ddl-auto: create
        properties:
          hibernate:
            format_sql: true
            type: trace
        database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    logging:
      level:
        org:
          hibernate:
            type: trace

    server:
      port: 1099
      host: {tu-ip-local}
    ```
6. Correr ***una sola vez*** FillingDatabaseTest.
7. Cambiar `ddl-auto` a `update`. Cada vez que se quiera resetear la base de datos, poner en `create`, correr FillingDatabaseTest, poner en `update`, y listo.
8. Correr CibusBackendApplication.
9. Correr AppStarter.
10. *Reservar. Ir. Comer.*
