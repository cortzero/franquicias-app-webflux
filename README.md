# Franquicias App
Aplicación reactiva que nos ayuda gestionar _franquicias_, las _sucursales_ de cada y los _productos_ que cada sucursal poseeo, así como su stock en cada una de ellas. La aplicación se desarrolló utilizando Spring Boot, Spring WebFlux, Spring Data R2DBC y MySQL para la persistencia de los datos.

## Modelo Entidad-Relación
![Modelo ER](https://github.com/cortzero/franquicias-app-webflux/blob/master/franquicias_model.png)

A continuación se explica las variables de entorno:

- `DB_URL`: La URL de la base de datos. Por ejemplo, `r2dbc:mysql://localhost:3306/franquicias_db`.
- `DB_USERNAME`: El nombre de usuario de la base de datos.
- `DB_PASSWORD`: La contraseña del usuario de la base de datos.

## Ejecutar la aplicación
Para ejecutar la aplicación se puede usar docker compose. 

Primero hay que crear el .jar ejecutando el siguiente comando maven:

`.\mvnw.cmd clean install -DskipTests`

Luego de que se haya creado el archivo .jar, en la carpeta raíz, ejecutar el siguiente comando:

`docker compose up`

Se crearan los contenedores para la base de datos MySQL y la aplicación Spring Boot. El puerto por defecto de la aplicación es el `8080`, por lo que estará lista para recibir peticiones.

## Endpoints

A continuación se describen los endpoints de la aplicación.

### Crear franquicia
Método: `POST`\
URL: `/api/franquicias`
```json
{
    "nombre": "Nombre"
}
```

### Listar franquicias
Método: `GET`\
URL `/api/franquicias`

### Modificar el nombre de la franquicia
Método: `PUT`\
URL: `/api/franquicias/{id}`
```json
{
    "nombre": "Nombre"
}
```

### Crear sucursal
Método: `POST`\
URL: `/api/sucursales`
```json
{
    "nombre": "Nombre",
    "franquiciaId": 1
}
```

### Listar sucursales
Método: `GET`\
URL: `/api/sucursales`

### Modificar el nombre de una sucursal
Método: `PUT`\
URL: `/api/sucursales/{id}`
```json
{
    "nombre": "Nombre"
}
```

### Crear producto
Método: `POST`\
URL: `/api/productos`
```json
{
    "nombre": "Nombre"
}
```

### Listar productos
Método: `GET`\
URL: `/api/productos`

### Modificar el nombre de un producto
Método: `PUT`\
URL: `/api/productos/{id}`
```json
{
    "nombre": "Nombre"
}
```

### Agregar un producto a una sucursal
Método: `POST`\
URL: `/api/sucursales/{id}/productos`
```json
{
    "productoId": 1,
    "stock": 1
}
```

### Eliminar un producto de una sucursal
Método: `DELETE`\
URL: `/api/sucursales/{id}/productos/{id}`

### Modificar el stock de un producto de una sucursal
Método: `POST`\
URL: `/api/sucursales/{id}/productos/{id}`
```json
{
    "stock": 60
}
```

### Listar el producto con más stock de cada sucursal de una franquicia específica
Método: `GET`\
URL: `/api/franquicias/{id}/sucursales/max-stock-products`

