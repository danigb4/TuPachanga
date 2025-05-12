# 🐳 Gestión de Entorno Docker

Este proyecto incluye un script para automatizar la preparación, limpieza e inicio de un entorno
Docker usando `docker-compose`.

---

## 📁 Estructura del Proyecto

```text
.
├── sql/                    # Scripts de base de datos
├── docker-compose.yml      # Definición de servicios Docker
├── start-env.sh            # Script de inicialización automatizado
└── README.md               # Esta documentación
```

---

## ⚙️ Requisitos Previos

Asegúrate de tener instalados los siguientes programas:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

> 🪟 **Si usas Windows**, se recomienda ejecutar el script `start-env.sh` desde **Git Bash** para
> evitar problemas de compatibilidad con comandos como `chmod` o `./start-env.sh`.

Puedes verificar si están instalados ejecutando:

```bash
docker --version
docker-compose --version
```

---

## 🚀 Instrucciones de Uso

1. **Accede al directorio donde se encuentra el archivo .sh**:

   ```bash
   cd docker
   ```

2. **Dale permisos de ejecución al script:**

   ```bash
   chmod +x start-db.sh
   ```

3. **Ejecuta el script:**

   ```bash
   ./start-db.sh
   ```

   Este script realiza las siguientes acciones:

    - Cambia los permisos de la carpeta `sql` (`chmod 755` y `chown 999:999`)
    - Detiene y elimina contenedores Docker previos
    - Inicia nuevamente los contenedores en segundo plano

---

## 🚀 Conectarse a la base de datos

1. **Comprueba que el contenedor está en funcionamiento**

    ```bash
    docker ps
    ```

2. **Si te aparece, conéctate a él**

    ```bash
    docker exec -it postgres_tupachanga_app bash
    ```
3. **Conéctate a la base de datos**

    ```bash
    psql -U oretania -d tupachanga
    ```
4. **Muestra las tablas y haz un select para recuperar datos de prueba**

    ```bash
    \dt
    select * from users;
    ``` 