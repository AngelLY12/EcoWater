# EcoWater

**EcoWater** es una aplicación diseñada para el control de la diabetes mellitus, permitiendo el registro y monitoreo de datos relacionados con la salud, como los niveles de glucosa, dieta, y medicación. Este proyecto utiliza Spring Boot para el backend y Docker para la base de datos PostgreSQL.

## Requisitos

Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes programas:

- [Docker](https://docs.docker.com/get-docker/)


## Pasos para ejecutar el proyecto

### 1. Clonar el repositorio

Primero, clona el repositorio en tu máquina local:

```bash
git clone https://github.com/AngelLY12/EcoWater.git
```
### 2. Navegar a la carpeta del backend
Este paso es para asegurarse de que te encuentras en la carpeta correcta, donde están los archivos del backend y el docker-compose.yml.
```bash
cd ecoWater
```
### 3. Configuración del archivo docker-compose.yml
El archivo docker-compose.yml se encarga de configurar los contenedores de Docker para la aplicación y la base de datos. En este archivo, ya hemos configurado los servicios para que puedas levantar el backend y la base de datos PostgreSQL de forma sencilla.
En dado caso de querer modificar algo seria principalmente las variables de la base de datos si es que las modificaste en application.properties.
```bash
 SPRING_DATASOURCE_USERNAME: angel
      SPRING_DATASOURCE_PASSWORD: 123
POSTGRES_USER: angel
      POSTGRES_PASSWORD: 123
```

### 4. Construir y ejecutar los contenedores Docker
Para construir y ejecutar la aplicación y la base de datos en contenedores Docker, ejecuta el siguiente comando en la raíz del proyecto (donde se encuentra el archivo docker-compose.yml)
```bash
docker-compose up --build
```
Este comando hará lo siguiente:
>1. Construirá las imágenes de Docker si es necesario.

>2.Levantará los contenedores de la aplicación Spring Boot y la base de datos PostgreSQL.

>3.Mapeará los puertos necesarios para acceder a la aplicación.

### Detener los contenedores
Para detener los contenedores y liberar los recursos, ejecuta el siguiente comando:
bash
```bash
docker-compose down
```
Este comando detendrá y eliminará los contenedores, las redes y los volúmenes creados por Docker Compose.
