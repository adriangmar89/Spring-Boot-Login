# API Spring Boot - Gestión de Usuarios  

Esta es una API RESTful construida con **Spring Boot** y **MySQL**. Su objetivo es gestionar usuarios mediante un sistema de **login** y **registro**, utilizando **JWT** para la autenticación.  

La API permite:  
- Registrar nuevos usuarios.  
- Iniciar sesión y generar tokens JWT para autenticación.  
- Almacenar información de usuarios en una base de datos MySQL.  

Tecnologías utilizadas:  
- **Spring Boot**: Framework para construir aplicaciones Java.  
- **Spring Security**: Para manejar la autenticación y autorización.  
- **JWT (JSON Web Tokens)**: Para la autenticación segura.  
- **MySQL**: Base de datos relacional para almacenar datos de usuarios.  
- **Maven**: Gestión de dependencias y compilación del proyecto.  

## Requisitos  

Para ejecutar el proyecto correctamente, asegúrate de tener instalado lo siguiente:  

- **Java 21**: Necesario para compilar y ejecutar el proyecto.  
- **Maven**: Se utiliza para gestionar las dependencias y la compilación.  
- **MySQL**: Base de datos utilizada para almacenar la información de los usuarios.  

## Configuración de la base de datos  

Antes de ejecutar la aplicación, configura la base de datos en el archivo `application.properties` o `application.yml`.  

Ejemplo de configuración en `application.properties`:  

```properties
spring.application.name=login
spring.datasource.url=jdbc:mysql://localhost:3306/login
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Configuración de JWT (clave secreta)
jwt.secret=Dx0qS3PB3DPgw45b2gHp8HtJtyrhksCiJ0hopMLB009wmYGZ7J
```

## Comandos para ejecutar la API  

### Windows  

Ejecutar la API con Maven Wrapper:  

```sh
mvnw.cmd spring-boot:run
```

### Linux / MacOS  

Dar permisos de ejecución al wrapper de Maven:  

```sh
chmod +x mvnw
```

Ejecutar la API:  

```sh
./mvnw spring-boot:run
```

---

## Rutas de la API  

### **Autenticación**  

#### **Registro de usuario**  

- **Endpoint**:  
  ```http
  POST http://localhost:8080/auth/register
  ```
- **Body (JSON)**:  
  ```json
  {
    "userName": "User",
    "email": "User@gmail.com",
    "password": "Prueba123",
    "role": "User"
  }
  ```

#### **Inicio de sesión**  

- **Endpoint**:  
  ```http
  POST http://localhost:8080/auth/login
  ```
- **Body (JSON)**:  
  ```json
  {
    "email": "User@gmail.com",
    "password": "Prueba123"
  }
  ```

#### **Ruta segura**  

- **Endpoint**:  
  ```http
  POST http://localhost:8080/api/test
  ```
- **Headers**:  
  ```
  Authorization: Bearer <tu_token_jwt>
  ```
